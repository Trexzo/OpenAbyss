#!/usr/bin/env bash
set -euo pipefail

: "${OPENABYSS_SERVER_JAR:?}"
: "${OPENABYSS_PRODUCTION_WORLD_MC:?}"
: "${OPENABYSS_PRODUCTION_WORLD_JAR:?}"
: "${JAVA_HOME:?}"
: "${GITHUB_WORKSPACE:?}"
: "${RUNNER_TEMP:?}"

SERVER_DIR="$RUNNER_TEMP/openabyss-production-world-server"
GAME_DIR="$RUNNER_TEMP/openabyss-production-world-game"
STDOUT="$RUNNER_TEMP/openabyss-production-world-client.stdout.log"
STDERR="$RUNNER_TEMP/openabyss-production-world-client.stderr.log"
SERVER_LOG="$RUNNER_TEMP/openabyss-production-world-server.log"
SCREENSHOT="$RUNNER_TEMP/openabyss-production-world-clickgui.png"

CLIENT_PID=""
SERVER_PID=""

cleanup() {
  set +e
  if [ -n "$CLIENT_PID" ]; then
    kill "$CLIENT_PID" 2>/dev/null || true
  fi
  if [ -n "$SERVER_PID" ]; then
    kill "$SERVER_PID" 2>/dev/null || true
  fi
}
trap cleanup EXIT

rm -rf "$SERVER_DIR" "$GAME_DIR"
mkdir -p "$SERVER_DIR" "$GAME_DIR"
printf 'eula=true\n' > "$SERVER_DIR/eula.txt"
cat > "$SERVER_DIR/server.properties" <<'PROPS'
online-mode=false
server-ip=127.0.0.1
server-port=25565
level-name=world
level-type=FLAT
generate-structures=false
spawn-animals=false
spawn-monsters=false
spawn-npcs=false
allow-nether=false
difficulty=0
gamemode=1
force-gamemode=true
spawn-protection=0
max-players=2
view-distance=3
max-tick-time=-1
enable-command-block=false
PROPS

cd "$SERVER_DIR"
"$JAVA_HOME/bin/java" -Xms256M -Xmx768M -jar "$OPENABYSS_SERVER_JAR" nogui >"$SERVER_LOG" 2>&1 &
SERVER_PID=$!

SERVER_READY=0
for _ in $(seq 1 120); do
  if grep -Eq 'Done \([0-9.]+s\)!|Done \(' "$SERVER_LOG"; then
    SERVER_READY=1
    break
  fi
  if ! kill -0 "$SERVER_PID" 2>/dev/null; then
    echo 'Production-world local server exited before ready.'
    tail -n 200 "$SERVER_LOG" || true
    exit 1
  fi
  sleep 1
done
if [ "$SERVER_READY" -ne 1 ]; then
  echo 'Production-world local server did not become ready.'
  tail -n 200 "$SERVER_LOG" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_SERVER_READY=PASS'

python "$GITHUB_WORKSPACE/tools/production_forge_linux.py" launch   --minecraft-dir "$OPENABYSS_PRODUCTION_WORLD_MC"   --game-dir "$GAME_DIR"   --abyss-jar "$OPENABYSS_PRODUCTION_WORLD_JAR"   --java "$JAVA_HOME/bin/java"   --username CIProductionWorld   --server 127.0.0.1   --port 25565   >"$STDOUT" 2>"$STDERR" &
CLIENT_PID=$!

STAGE="$GAME_DIR/abyss-runtime-stage.txt"
WORLD_READY=0
for _ in $(seq 1 180); do
  if [ -f "$STAGE" ] && grep -Fq 'world-ready-tick' "$STAGE"; then
    WORLD_READY=1
    break
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before world-ready-tick.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  sleep 1
done
if [ "$WORLD_READY" -ne 1 ]; then
  echo 'Production world-ready-tick was not observed.'
  exit 1
fi

grep -Fq 'entity-player-join-world' "$STAGE"
grep -Fq 'world-module-lifecycle-start' "$STAGE"
grep -Fq 'world-module-lifecycle-complete' "$STAGE"
echo 'PRODUCTION_WORLD_LIFECYCLE=PASS'

WINDOW=""
for _ in $(seq 1 30); do
  WINDOW="$(DISPLAY=:99 xdotool search --onlyvisible --name 'Minecraft' 2>/dev/null | head -n 1 || true)"
  if [ -n "$WINDOW" ]; then
    break
  fi
  sleep 1
done
if [ -z "$WINDOW" ]; then
  echo 'Production Minecraft window was not found.'
  exit 1
fi

DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
sleep 1
DISPLAY=:99 xdotool keydown Shift_R
sleep 0.45
DISPLAY=:99 xdotool keyup Shift_R

CLICKGUI_READY=0
for _ in $(seq 1 30); do
  if grep -Fq 'clickgui-open-success:' "$STAGE"; then
    CLICKGUI_READY=1
    break
  fi
  sleep 1
done
if [ "$CLICKGUI_READY" -ne 1 ]; then
  echo 'Production ClickGUI success milestone was not observed.'
  cat "$STAGE" || true
  exit 1
fi
if grep -Fq 'clickgui-open-nullpointer:' "$STAGE"; then
  echo 'Production ClickGUI swallowed a NullPointerException.'
  cat "$STAGE"
  exit 1
fi

DISPLAY=:99 scrot "$SCREENSHOT"
test -s "$SCREENSHOT"
echo 'PRODUCTION_WORLD_CLICKGUI=PASS'

for failure in   abyss-module-failure.txt   abyss-feature-failure.txt   abyss-event-failure.txt   abyss-config-failure.txt   abyss-renderer-failure.txt
do
  path="$GAME_DIR/$failure"
  if [ -s "$path" ]; then
    echo "Production failure journal is non-empty: $path"
    cat "$path"
    exit 1
  fi
done
echo 'PRODUCTION_WORLD_FAILURE_JOURNALS=PASS'

CENSUS="$GAME_DIR/abyss-census.tsv"
test -f "$CENSUS"
CENSUS_COUNT="$(grep -cv '^[[:space:]]*$' "$CENSUS")"
echo "PRODUCTION_WORLD_MODULE_CENSUS_COUNT=$CENSUS_COUNT"
test "$CENSUS_COUNT" -eq 112

if ! grep -Eq 'logged in with entity id|joined the game' "$SERVER_LOG"; then
  echo 'Production server did not record a real client login.'
  tail -n 200 "$SERVER_LOG"
  exit 1
fi
echo 'PRODUCTION_WORLD_SERVER_LOGIN=PASS'

MOD_JAR="$GAME_DIR/mods/abyss.jar"
test -s "$MOD_JAR"
SOURCE_HASH="$(sha256sum "$OPENABYSS_PRODUCTION_WORLD_JAR" | awk '{print toupper($1)}')"
MOD_HASH="$(sha256sum "$MOD_JAR" | awk '{print toupper($1)}')"
if [ "$SOURCE_HASH" != "$MOD_HASH" ]; then
  echo "Production runtime JAR hash mismatch: $MOD_HASH != $SOURCE_HASH"
  exit 1
fi
echo "PRODUCTION_WORLD_RUNTIME_JAR_SHA256=$MOD_HASH"

grep -Fq 'OPENABYSS_PRODUCTION_LINUX_EXPLICIT_COREMOD_PROPERTY=0' "$STDOUT"
grep -Fq 'OPENABYSS_PRODUCTION_LINUX_INSTALL_MODE=mods-folder' "$STDOUT"

DISPLAY=:99 xdotool key Escape || true
sleep 5

echo '=== production runtime milestones ==='
cat "$STAGE"
echo 'OPENABYSS_LINUX_PRODUCTION_WORLD_SMOKE=PASS'
