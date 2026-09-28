#!/usr/bin/env bash
set -euo pipefail

: "${OPENABYSS_SERVER_JAR:?}"
: "${OPENABYSS_PRODUCTION_WORLD_MC:?}"
: "${OPENABYSS_PRODUCTION_WORLD_JAR:?}"
: "${JAVA_HOME:?}"
: "${GITHUB_WORKSPACE:?}"
: "${RUNNER_TEMP:?}"

SERVER_DIR="$RUNNER_TEMP/openabyss-production-persistence-server"
GAME_DIR="$RUNNER_TEMP/openabyss-production-persistence-game"
SERVER_LOG="$RUNNER_TEMP/openabyss-production-persistence-server.log"
SEED_STDOUT="$RUNNER_TEMP/openabyss-production-persistence-seed.stdout.log"
SEED_STDERR="$RUNNER_TEMP/openabyss-production-persistence-seed.stderr.log"
VERIFY_STDOUT="$RUNNER_TEMP/openabyss-production-persistence-verify.stdout.log"
VERIFY_STDERR="$RUNNER_TEMP/openabyss-production-persistence-verify.stderr.log"
CLIENT_PID=""
SERVER_PID=""

cleanup() {
  set +e
  if [ -n "$CLIENT_PID" ]; then kill "$CLIENT_PID" 2>/dev/null || true; fi
  if [ -n "$SERVER_PID" ]; then kill "$SERVER_PID" 2>/dev/null || true; fi
}
trap cleanup EXIT

rm -rf "$SERVER_DIR" "$GAME_DIR"
mkdir -p "$SERVER_DIR" "$GAME_DIR"
printf 'eula=true\n' > "$SERVER_DIR/eula.txt"
cat > "$SERVER_DIR/server.properties" <<'PROPS'
online-mode=false
server-ip=127.0.0.1
server-port=25566
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

READY=0
for _ in $(seq 1 120); do
  if grep -Eq 'Done \([0-9.]+s\)!|Done \(' "$SERVER_LOG"; then READY=1; break; fi
  if ! kill -0 "$SERVER_PID" 2>/dev/null; then
    echo 'Persistence local server exited before ready.'
    tail -n 200 "$SERVER_LOG" || true
    exit 1
  fi
  sleep 1
done
test "$READY" -eq 1
echo 'PERSISTENCE_SERVER_READY=PASS'

launch_client() {
  local opts="$1"
  local user="$2"
  local out="$3"
  local err="$4"
  JAVA_TOOL_OPTIONS="$opts"     python "$GITHUB_WORKSPACE/tools/production_forge_linux.py" launch       --minecraft-dir "$OPENABYSS_PRODUCTION_WORLD_MC"       --game-dir "$GAME_DIR"       --abyss-jar "$OPENABYSS_PRODUCTION_WORLD_JAR"       --java "$JAVA_HOME/bin/java"       --username "$user"       --server 127.0.0.1       --port 25566       >"$out" 2>"$err" &
  CLIENT_PID=$!
}

wait_for_stage() {
  local file="$1"
  local marker="$2"
  local out="$3"
  local err="$4"
  local label="$5"
  for _ in $(seq 1 240); do
    if [ -f "$file" ] && grep -Fq "$marker" "$file"; then
      echo "$label=PASS"
      return 0
    fi
    if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
      echo "$label client exited before marker: $marker"
      tail -n 300 "$out" || true
      tail -n 300 "$err" || true
      cat "$file" 2>/dev/null || true
      return 1
    fi
    sleep 0.25
  done
  echo "$label timed out waiting for marker: $marker"
  cat "$file" 2>/dev/null || true
  return 1
}

STAGE="$GAME_DIR/abyss-runtime-stage.txt"
BOOT_STAGE="$GAME_DIR/abyss-bootstrap-stage.txt"
CONFIG="$GAME_DIR/Abyss/current.json"

launch_client '-Dabyss.runtimeSelfTest=true -Dabyss.persistenceProbeSeed=true' CISeed "$SEED_STDOUT" "$SEED_STDERR"
wait_for_stage "$STAGE" 'world-ready-tick' "$SEED_STDOUT" "$SEED_STDERR" 'PERSISTENCE_SEED_WORLD_READY'
wait_for_stage "$STAGE" 'persistence-probe-seed-pass:ClickGUI.Scale=1.75,FullBright=true' "$SEED_STDOUT" "$SEED_STDERR" 'PERSISTENCE_SEED_SAVE'
wait_for_stage "$STAGE" 'persistence-matrix-seed-pass:boolean=false,percentage=67,number=1.75,mode=RAVEN,color=A1B2C3,text=LSHIFT,module=true' "$SEED_STDOUT" "$SEED_STDERR" 'PERSISTENCE_MATRIX_SEED_SAVE'

test -s "$CONFIG"
python - "$CONFIG" <<'PY'
import json, pathlib, sys
p=pathlib.Path(sys.argv[1])
root=json.loads(p.read_text(encoding='utf-8'))
block=root.get('ClickGUI')
if not isinstance(block, dict):
    raise SystemExit('PERSISTENCE_CONFIG_CLICKGUI_BLOCK_MISSING')
scale=block.get('Scale')
if abs(float(scale)-1.75)>0.001:
    raise SystemExit(f'PERSISTENCE_CONFIG_SCALE_BAD={scale!r}')
if block.get('Mode') != 'RAVEN':
    raise SystemExit(f"PERSISTENCE_CONFIG_MODE_BAD={block.get('Mode')!r}")
if block.get('Keybind') != 'LSHIFT':
    raise SystemExit(f"PERSISTENCE_CONFIG_TEXT_BAD={block.get('Keybind')!r}")

notifications=root.get('Notifications')
if not isinstance(notifications, dict):
    raise SystemExit('PERSISTENCE_CONFIG_NOTIFICATIONS_BLOCK_MISSING')
if notifications.get('Text-shadow') is not False:
    raise SystemExit(f"PERSISTENCE_CONFIG_BOOLEAN_BAD={notifications.get('Text-shadow')!r}")

velocity=root.get('Velocity')
if not isinstance(velocity, dict):
    raise SystemExit('PERSISTENCE_CONFIG_VELOCITY_BLOCK_MISSING')
if int(velocity.get('Horizontal')) != 67:
    raise SystemExit(f"PERSISTENCE_CONFIG_PERCENTAGE_BAD={velocity.get('Horizontal')!r}")

theme=root.get('Theme')
if not isinstance(theme, dict):
    raise SystemExit('PERSISTENCE_CONFIG_THEME_BLOCK_MISSING')
if theme.get('Custom-color-1') != 'A1B2C3':
    raise SystemExit(f"PERSISTENCE_CONFIG_COLOR_BAD={theme.get('Custom-color-1')!r}")

full=root.get('FullBright')
if not isinstance(full, dict):
    raise SystemExit('PERSISTENCE_CONFIG_FULLBRIGHT_BLOCK_MISSING')
if full.get('status') is not True:
    raise SystemExit(f"PERSISTENCE_CONFIG_FULLBRIGHT_STATUS_BAD={full.get('status')!r}")

print('PERSISTENCE_CONFIG_DISK_MATRIX=PASS boolean=false percentage=67 number=1.75 mode=RAVEN color=A1B2C3 text=LSHIFT module=true')
PY
CONFIG_HASH_BEFORE="$(sha256sum "$CONFIG" | awk '{print toupper($1)}')"
echo "PERSISTENCE_CONFIG_SHA256_BEFORE=$CONFIG_HASH_BEFORE"

kill "$CLIENT_PID" 2>/dev/null || true
wait "$CLIENT_PID" 2>/dev/null || true
CLIENT_PID=""

for evidence in   abyss-runtime-stage.txt abyss-bootstrap-stage.txt abyss-bootstrap-diagnostics.txt   abyss-census.tsv abyss-module-failure.txt abyss-feature-failure.txt abyss-event-failure.txt   abyss-config-failure.txt abyss-renderer-failure.txt
do
  rm -f "$GAME_DIR/$evidence"
done

launch_client '-Dabyss.runtimeSelfTest=true -Dabyss.persistenceProbeExpectedClickGuiScale=1.75 -Dabyss.persistenceProbeMatrix=true -Dabyss.eventRuntimeTrace=true -Dabyss.networkCommandProbe=true' CIVerify "$VERIFY_STDOUT" "$VERIFY_STDERR"
wait_for_stage "$BOOT_STAGE" 'persistence-probe-verify-pass:scale=1.75,fullbright=true' "$VERIFY_STDOUT" "$VERIFY_STDERR" 'PERSISTENCE_RESTART_BOOT_VALUE'
wait_for_stage "$BOOT_STAGE" 'persistence-matrix-verify-pass:boolean=false,percentage=67,number=1.75,mode=RAVEN,color=A1B2C3,text=LSHIFT,module=true' "$VERIFY_STDOUT" "$VERIFY_STDERR" 'PERSISTENCE_MATRIX_RESTART_BOOT_VALUE'
wait_for_stage "$STAGE" 'world-ready-tick' "$VERIFY_STDOUT" "$VERIFY_STDERR" 'PERSISTENCE_RESTART_WORLD_READY'
wait_for_stage "$STAGE" 'network-command-probe-pass:CommandLine:restored=' "$VERIFY_STDOUT" "$VERIFY_STDERR" 'PERSISTENCE_RESTART_COMMAND_WIRING'

DIAG="$GAME_DIR/abyss-bootstrap-diagnostics.txt"
test -s "$DIAG"
grep -Fq '[ABYSSDIAG] setting restore selftest= PASS strict-setting-writes percentage-range=0..100 number-range mode-options color-hex readback' "$DIAG"
grep -Eq 'Abyss\.settings by-name applied [1-9][0-9]* config value\(s\)' "$DIAG"
echo 'PERSISTENCE_BOOT_SETTING_RESTORE_DIAGNOSTICS=PASS'

CONFIG_HASH_AFTER="$(sha256sum "$CONFIG" | awk '{print toupper($1)}')"
echo "PERSISTENCE_CONFIG_SHA256_AFTER=$CONFIG_HASH_AFTER"
if [ "$CONFIG_HASH_BEFORE" != "$CONFIG_HASH_AFTER" ]; then
  echo 'Persistence config changed during read-only restart verification.'
  exit 1
fi
echo 'PERSISTENCE_CONFIG_STABLE_ACROSS_RESTART=PASS'

NETWORK_STAGE="$GAME_DIR/abyss-network-stage.txt"
EVENT_STAGE="$GAME_DIR/abyss-event-stage.txt"

test -s "$NETWORK_STAGE"
grep -Fq 'send-hook:' "$NETWORK_STAGE"
grep -Fq 'receive-hook:' "$NETWORK_STAGE"
echo 'PERSISTENCE_RESTART_NETWORK_HOOKS=PASS'

test -s "$EVENT_STAGE"
for event in PostTickEvent PreUpdateEvent EntityJoinWorldEvent SendPacketEvent ReceivePacketEvent Render2DEvent; do
  grep -Fq "Abyss.event.events.$event" "$EVENT_STAGE"
done
echo 'PERSISTENCE_RESTART_EVENT_CALLBACKS=PASS'

for failure in   abyss-module-failure.txt abyss-feature-failure.txt abyss-event-failure.txt   abyss-config-failure.txt abyss-renderer-failure.txt
do
  path="$GAME_DIR/$failure"
  if [ -s "$path" ]; then
    echo "Persistence failure journal is non-empty: $path"
    cat "$path"
    exit 1
  fi
done
echo 'PERSISTENCE_FAILURE_JOURNALS=PASS'

LOGIN_COUNT="$(grep -Ec 'logged in with entity id|joined the game' "$SERVER_LOG" || true)"
echo "PERSISTENCE_SERVER_LOGIN_COUNT=$LOGIN_COUNT"
test "$LOGIN_COUNT" -ge 2

MOD_JAR="$GAME_DIR/mods/abyss.jar"
SOURCE_HASH="$(sha256sum "$OPENABYSS_PRODUCTION_WORLD_JAR" | awk '{print toupper($1)}')"
MOD_HASH="$(sha256sum "$MOD_JAR" | awk '{print toupper($1)}')"
test "$SOURCE_HASH" = "$MOD_HASH"
echo "PERSISTENCE_RUNTIME_JAR_SHA256=$MOD_HASH"

echo 'OPENABYSS_PRODUCTION_PERSISTENCE_RESTART=PASS'
