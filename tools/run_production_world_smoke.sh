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
PRE_CLICKGUI_SCREENSHOT="$RUNNER_TEMP/openabyss-production-world-before-clickgui.png"
CLICKGUI_MODE_SCREENSHOT_PREFIX="$RUNNER_TEMP/openabyss-production-world-clickgui-mode"
STALL_SCREENSHOT="$RUNNER_TEMP/openabyss-production-world-stall.png"
THREAD_DUMP="$RUNNER_TEMP/openabyss-production-world-jstack.txt"
THREAD_DUMP_2="$RUNNER_TEMP/openabyss-production-world-jstack-2.txt"
THREAD_DUMP_3="$RUNNER_TEMP/openabyss-production-world-jstack-3.txt"

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

python "$GITHUB_WORKSPACE/tools/production_forge_linux.py" launch   --minecraft-dir "$OPENABYSS_PRODUCTION_WORLD_MC"   --game-dir "$GAME_DIR"   --abyss-jar "$OPENABYSS_PRODUCTION_WORLD_JAR"   --java "$JAVA_HOME/bin/java"   --username CIProdWorld   --server 127.0.0.1   --port 25565   >"$STDOUT" 2>"$STDERR" &
CLIENT_PID=$!

STAGE="$GAME_DIR/abyss-runtime-stage.txt"
WORLD_READY=0
STALL_DUMPED=0
for WAIT_ITER in $(seq 1 180); do
  if [ -f "$STAGE" ] && grep -Fq 'world-ready-tick' "$STAGE"; then
    WORLD_READY=1
    break
  fi
  if [ "$STALL_DUMPED" -eq 0 ] && [ "$WAIT_ITER" -ge 12 ] &&
     grep -Eq 'logged in with entity id|joined the game' "$SERVER_LOG" 2>/dev/null &&
     kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo "Capturing in-stall production client thread dumps at wait=${WAIT_ITER}s pid=$CLIENT_PID"
    "$JAVA_HOME/bin/jstack" -l "$CLIENT_PID" >"$THREAD_DUMP" 2>&1 || true
    sleep 1
    "$JAVA_HOME/bin/jstack" -l "$CLIENT_PID" >"$THREAD_DUMP_2" 2>&1 || true
    sleep 1
    "$JAVA_HOME/bin/jstack" -l "$CLIENT_PID" >"$THREAD_DUMP_3" 2>&1 || true
    DISPLAY=:99 scrot "$STALL_SCREENSHOT" || true
    STALL_DUMPED=1
  fi
  if [ "$STALL_DUMPED" -eq 1 ] && [ "$WAIT_ITER" -ge 20 ]; then
    echo "Production client remained without world-ready-tick after in-stall capture; ending diagnostic wait at ${WAIT_ITER}s."
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
  if [ -n "$CLIENT_PID" ] && kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo "Capturing live production client thread dump for pid=$CLIENT_PID"
    "$JAVA_HOME/bin/jstack" -l "$CLIENT_PID" >"$THREAD_DUMP" 2>&1 || {
      kill -3 "$CLIENT_PID" 2>/dev/null || true
      sleep 2
    }
  fi
  echo '=== production client stdout tail ==='
  tail -n 500 "$STDOUT" || true
  echo '=== production client stderr tail ==='
  tail -n 500 "$STDERR" || true
  echo '=== production server log tail ==='
  tail -n 300 "$SERVER_LOG" || true
  echo '=== production runtime stage ==='
  cat "$STAGE" 2>/dev/null || true
  echo '=== production bootstrap stage ==='
  cat "$GAME_DIR/abyss-bootstrap-stage.txt" 2>/dev/null || true
  echo '=== production bootstrap diagnostics ==='
  cat "$GAME_DIR/abyss-bootstrap-diagnostics.txt" 2>/dev/null || true
  exit 1
fi

grep -Fq 'entity-player-join-world' "$STAGE"
grep -Fq 'world-module-lifecycle-start' "$STAGE"
grep -Fq 'world-module-lifecycle-complete' "$STAGE"
echo 'PRODUCTION_WORLD_LIFECYCLE=PASS'

FUNCTIONAL_READY=0
for _ in $(seq 1 120); do
  if grep -Fq 'world-functional-probe-pass' "$STAGE"; then
    FUNCTIONAL_READY=1
    break
  fi
  if grep -Fq 'world-functional-probe-fail:' "$STAGE"; then
    echo 'Production live-world functional probe reported failure.'
    cat "$STAGE"
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before live-world functional probe completed.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  sleep 0.25
done
if [ "$FUNCTIONAL_READY" -ne 1 ]; then
  echo 'Production live-world functional probe did not complete.'
  cat "$STAGE" || true
  exit 1
fi
grep -Fq 'world-functional-probe-enable-request' "$STAGE"
grep -Fq 'world-functional-probe-enabled' "$STAGE"
grep -Fq 'world-functional-probe-disable-request' "$STAGE"
echo 'PRODUCTION_WORLD_FUNCTIONAL_MODULE_LIFECYCLE=PASS'

CATEGORY_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'category-lifecycle-probe-pass:7' "$STAGE"; then
    CATEGORY_READY=1
    break
  fi
  if grep -Fq 'category-lifecycle-probe-fail:' "$STAGE"; then
    echo 'Production category lifecycle probe reported failure.'
    cat "$STAGE"
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before category lifecycle probe completed.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  sleep 0.25
done
if [ "$CATEGORY_READY" -ne 1 ]; then
  echo 'Production category lifecycle probe did not complete.'
  cat "$STAGE" || true
  exit 1
fi
for module in HitBox Notifications Macro1 NameHider NoJumpDelay NoHitDelay AutoTool; do
  grep -Fq "category-lifecycle-probe-module-pass:$module:" "$STAGE"
done
echo 'PRODUCTION_WORLD_CATEGORY_LIFECYCLE=PASS modules=7 plus-FullBright=8-categories'

COMMAND_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'command-runtime-probe-pass:' "$STAGE"; then
    COMMAND_READY=1
    break
  fi
  if grep -Fq 'command-runtime-probe-fail:' "$STAGE"; then
    echo 'Production command runtime probe reported failure.'
    cat "$STAGE"
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before command runtime probe completed.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  sleep 0.25
done
if [ "$COMMAND_READY" -ne 1 ]; then
  echo 'Production command runtime probe did not complete.'
  cat "$STAGE" || true
  exit 1
fi
grep -Fq 'command-runtime-probe-toggle-request:FullBright:' "$STAGE"
grep -Fq 'command-runtime-probe-toggle-pass:FullBright:' "$STAGE"
grep -Fq 'command-runtime-probe-restore-request:FullBright:' "$STAGE"
echo 'PRODUCTION_WORLD_COMMAND_RUNTIME=PASS commands=help,list,bind-list,config-list,unknown,toggle,module-setting'

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
DISPLAY=:99 scrot "$PRE_CLICKGUI_SCREENSHOT"
test -s "$PRE_CLICKGUI_SCREENSHOT"

touch "$GAME_DIR/abyss-clickgui-mode-probe-go"
for MODE in STUDIO RAVEN VESTIGE; do
  MODE_READY=0
  for _ in $(seq 1 120); do
    if grep -Fq "clickgui-mode-probe-open:$MODE:" "$STAGE"; then
      MODE_READY=1
      break
    fi
    if grep -Fq 'clickgui-mode-probe-fail:' "$STAGE"; then
      echo "Production ClickGUI mode probe failed before $MODE rendered."
      cat "$STAGE"
      cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
      exit 1
    fi
    if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
      echo "Production client exited before ClickGUI mode $MODE rendered."
      tail -n 300 "$STDOUT" || true
      tail -n 300 "$STDERR" || true
      exit 1
    fi
    sleep 0.25
  done
  if [ "$MODE_READY" -ne 1 ]; then
    echo "Production ClickGUI mode $MODE did not open."
    cat "$STAGE" || true
    exit 1
  fi

  MODE_LOWER="$(printf '%s' "$MODE" | tr '[:upper:]' '[:lower:]')"
  MODE_SCREENSHOT="$CLICKGUI_MODE_SCREENSHOT_PREFIX-$MODE_LOWER.png"
  DISPLAY=:99 scrot "$MODE_SCREENSHOT"
  test -s "$MODE_SCREENSHOT"

  MODE_DIFF_RAW="$(compare -metric AE "$PRE_CLICKGUI_SCREENSHOT" "$MODE_SCREENSHOT" null: 2>&1 || true)"
  MODE_DIFF="$(printf '%s' "$MODE_DIFF_RAW" | tr -cd '0-9')"
  if [ -z "$MODE_DIFF" ]; then
    echo "Could not parse $MODE framebuffer pixel difference: $MODE_DIFF_RAW"
    exit 1
  fi
  if [ "$MODE_DIFF" -lt 10000 ]; then
    echo "$MODE framebuffer changed too little: changed_pixels=$MODE_DIFF"
    exit 1
  fi
  echo "PRODUCTION_WORLD_CLICKGUI_MODE_"$MODE"_CHANGED_PIXELS=$MODE_DIFF"

  DISPLAY=:99 xdotool key Escape
  MODE_CLOSED=0
  for _ in $(seq 1 120); do
    if grep -Fq "clickgui-mode-probe-close:$MODE" "$STAGE"; then
      MODE_CLOSED=1
      break
    fi
    sleep 0.25
  done
  if [ "$MODE_CLOSED" -ne 1 ]; then
    echo "Production ClickGUI mode $MODE did not close cleanly."
    cat "$STAGE" || true
    exit 1
  fi
done

if ! grep -Fq 'clickgui-mode-probe-pass:3:restored=' "$STAGE"; then
  echo 'Production ClickGUI mode cycle did not restore the original mode.'
  cat "$STAGE"
  exit 1
fi
echo 'PRODUCTION_WORLD_CLICKGUI_MODE_CYCLE=PASS modes=STUDIO,RAVEN,VESTIGE'

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

PIXEL_DIFF_RAW="$(compare -metric AE "$PRE_CLICKGUI_SCREENSHOT" "$SCREENSHOT" null: 2>&1 || true)"
PIXEL_DIFF="$(printf '%s' "$PIXEL_DIFF_RAW" | tr -cd '0-9')"
if [ -z "$PIXEL_DIFF" ]; then
  echo "Could not parse ClickGUI framebuffer pixel difference: $PIXEL_DIFF_RAW"
  exit 1
fi
if [ "$PIXEL_DIFF" -lt 10000 ]; then
  echo "ClickGUI framebuffer changed too little: changed_pixels=$PIXEL_DIFF"
  exit 1
fi
echo "PRODUCTION_WORLD_CLICKGUI_CHANGED_PIXELS=$PIXEL_DIFF"
echo 'PRODUCTION_WORLD_CLICKGUI_FRAMEBUFFER=PASS'
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
