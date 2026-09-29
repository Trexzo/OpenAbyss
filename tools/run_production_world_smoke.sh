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
  DISPLAY=:99 xdotool mouseup 1 >/dev/null 2>&1 || true
  DISPLAY=:99 xdotool keyup Shift_L >/dev/null 2>&1 || true
  DISPLAY=:99 xdotool keyup space >/dev/null 2>&1 || true
  DISPLAY=:99 xdotool keyup w >/dev/null 2>&1 || true
  DISPLAY=:99 xdotool keyup e >/dev/null 2>&1 || true
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

# Use a deterministic 1:1 GUI scale so xdotool coordinates match vanilla GuiScreen coordinates.
# The game directory is isolated and newly created above, so this does not alter a user's profile.
cat > "$GAME_DIR/options.txt" <<'OPTIONS'
guiScale:1
lang:en_US
OPTIONS

# Intentionally do NOT pass Minecraft's --server argument here.
# Direct --server skips the main menu, which also skips OpenAbyss's normal
# no-world tick cleanup (including PacketManager buffering reset).
#
# Pass diagnostic properties as explicit JVM arguments. The Java 8 launcher
# silently stopped consuming JAVA_TOOL_OPTIONS once this probe set exceeded
# roughly 1 KiB, which disabled every runtime probe at once.
PROBE_JVM_ARGS=(
  "--jvm-arg=-Dabyss.runtimeSelfTest=true"
  "--jvm-arg=-Dabyss.worldFunctionalProbe=true"
  "--jvm-arg=-Dabyss.categoryLifecycleProbe=true"
  "--jvm-arg=-Dabyss.promotedRegistryProbe=true"
  "--jvm-arg=-Dabyss.eventFunctionalProbe=true"
  "--jvm-arg=-Dabyss.movementFunctionalProbe=true"
  "--jvm-arg=-Dabyss.playerFunctionalProbe=true"
  "--jvm-arg=-Dabyss.combatFunctionalProbe=true"
  "--jvm-arg=-Dabyss.packetFunctionalProbe=true"
  "--jvm-arg=-Dabyss.macroFunctionalProbe=true"
  "--jvm-arg=-Dabyss.visualUtilityFunctionalProbe=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe2=true"
  "--jvm-arg=-Dabyss.physicalInputFunctionalProbe=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe3=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe4=true"
  "--jvm-arg=-Dabyss.invMovePhysicalProbe=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe5=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe6=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe7=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe8=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe9=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe10=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe11=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe12=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe13=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe14=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe15=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe16=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe17=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe18=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe19=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe20=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe21=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe22=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe23=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe24=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe25=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe26=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe27=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe28=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe29=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe30=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe31=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe32=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe33=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe34=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe35=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe36=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe37=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe38=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe39=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe40=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe41=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe42=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe43=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe44=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe45=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe46=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe47=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe48=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe49=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe50=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe51=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe52=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe53=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe54=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe55=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe56=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe57=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe58=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe59=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe60=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe61=true"
  "--jvm-arg=-Dabyss.highRiskFunctionalProbe62=true"
  "--jvm-arg=-Dabyss.commandRuntimeProbe=true"
  "--jvm-arg=-Dabyss.networkCommandProbe=true"
  "--jvm-arg=-Dabyss.clickGuiModeProbe=true"
)
python "$GITHUB_WORKSPACE/tools/production_forge_linux.py" launch \
  --minecraft-dir "$OPENABYSS_PRODUCTION_WORLD_MC" \
  --game-dir "$GAME_DIR" \
  --abyss-jar "$OPENABYSS_PRODUCTION_WORLD_JAR" \
  --java "$JAVA_HOME/bin/java" \
  --username CIProdWorld \
  "${PROBE_JVM_ARGS[@]}" \
  >"$STDOUT" 2>"$STDERR" &
CLIENT_PID=$!

STAGE="$GAME_DIR/abyss-runtime-stage.txt"
MENU_READY=0
for _ in $(seq 1 120); do
  if [ -f "$STAGE" ] && grep -Fq 'menu-no-world-tick' "$STAGE"; then
    MENU_READY=1
    break
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before main-menu/no-world tick.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  sleep 0.25
done
if [ "$MENU_READY" -ne 1 ]; then
  echo 'Production main-menu/no-world tick was not observed before connect.'
  cat "$STAGE" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_MENU_TICK=PASS'

WINDOW=""
for _ in $(seq 1 120); do
  WINDOW="$(DISPLAY=:99 xdotool search --onlyvisible --name 'Minecraft' 2>/dev/null | head -n 1 || true)"
  if [ -n "$WINDOW" ]; then
    break
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before Minecraft window became available.'
    exit 1
  fi
  sleep 0.25
done
if [ -z "$WINDOW" ]; then
  echo 'Production Minecraft window was not found at main menu.'
  exit 1
fi

eval "$(DISPLAY=:99 xdotool getwindowgeometry --shell "$WINDOW")"
if [ -z "${WIDTH:-}" ] || [ -z "${HEIGHT:-}" ]; then
  echo 'Could not resolve production Minecraft window geometry.'
  exit 1
fi

DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
sleep 0.5

MULTIPLAYER_X=$((WIDTH / 2))
# Runnable-era GuiMainMenuHooks inserts Alt Manager after Multiplayer and shifts
# every button at/above the Multiplayer anchor upward by 12 GUI pixels.
# Vanilla Multiplayer center is HEIGHT/4+82; recovered center is therefore +70.
MULTIPLAYER_Y=$((HEIGHT / 4 + 70))
DIRECT_X=$((WIDTH / 2))
DIRECT_Y=$((HEIGHT - 42))

echo "PRODUCTION_WORLD_MENU_GEOMETRY=${WIDTH}x${HEIGHT}"
echo "PRODUCTION_WORLD_MULTIPLAYER_CLICK=${MULTIPLAYER_X},${MULTIPLAYER_Y}"
DISPLAY=:99 xdotool mousemove --window "$WINDOW" "$MULTIPLAYER_X" "$MULTIPLAYER_Y" click 1
sleep 0.75

echo "PRODUCTION_WORLD_DIRECT_CONNECT_CLICK=${DIRECT_X},${DIRECT_Y}"
DISPLAY=:99 xdotool mousemove --window "$WINDOW" "$DIRECT_X" "$DIRECT_Y" click 1
sleep 0.50

# The address field is focused by GuiScreenServerList. Clear it without
# relying on Ctrl+A, which is timing-sensitive under Xvfb/xdotool.
DISPLAY=:99 xdotool key --clearmodifiers End
DISPLAY=:99 xdotool key --clearmodifiers --repeat 96 --delay 2 BackSpace
DISPLAY=:99 xdotool type --clearmodifiers --delay 25 -- '127.0.0.1:25565'
sleep 0.20
DISPLAY=:99 xdotool key --clearmodifiers Return
echo 'PRODUCTION_WORLD_UI_CONNECT_REQUEST=PASS'

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

NETWORK_STAGE="$GAME_DIR/abyss-network-stage.txt"
NETWORK_READY=0
for _ in $(seq 1 120); do
  if [ -f "$NETWORK_STAGE" ] &&
     grep -Fq 'send-hook:' "$NETWORK_STAGE" &&
     grep -Fq 'receive-hook:' "$NETWORK_STAGE"; then
    NETWORK_READY=1
    break
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before both network hooks were observed.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  sleep 0.25
done
if [ "$NETWORK_READY" -ne 1 ]; then
  echo 'Production send/receive NetworkManager hooks were not both observed.'
  cat "$NETWORK_STAGE" 2>/dev/null || true
  exit 1
fi
grep -Fq 'send-hook:' "$NETWORK_STAGE"
grep -Fq 'receive-hook:' "$NETWORK_STAGE"
echo 'PRODUCTION_WORLD_NETWORK_HOOKS=PASS'

FUNCTIONAL_READY=0
FUNCTIONAL_STALL_DUMPED=0
for FUNCTIONAL_WAIT in $(seq 1 120); do
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
  if [ "$FUNCTIONAL_STALL_DUMPED" -eq 0 ] && [ "$FUNCTIONAL_WAIT" -ge 20 ] &&
     kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo "Capturing post-world functional-stall thread dumps at wait=$FUNCTIONAL_WAIT pid=$CLIENT_PID"
    "$JAVA_HOME/bin/jstack" -l "$CLIENT_PID" >"$THREAD_DUMP" 2>&1 || true
    sleep 1
    "$JAVA_HOME/bin/jstack" -l "$CLIENT_PID" >"$THREAD_DUMP_2" 2>&1 || true
    sleep 1
    "$JAVA_HOME/bin/jstack" -l "$CLIENT_PID" >"$THREAD_DUMP_3" 2>&1 || true
    DISPLAY=:99 scrot "$STALL_SCREENSHOT" || true
    FUNCTIONAL_STALL_DUMPED=1
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
  echo '=== post-world functional stall jstack #1 ==='
  sed -n '1,260p' "$THREAD_DUMP" 2>/dev/null || true
  echo '=== post-world functional stall jstack #2 ==='
  sed -n '1,260p' "$THREAD_DUMP_2" 2>/dev/null || true
  echo '=== post-world functional stall jstack #3 ==='
  sed -n '1,260p' "$THREAD_DUMP_3" 2>/dev/null || true
  cat "$STAGE" || true
  exit 1
fi
grep -Fq 'world-functional-probe-enable-request' "$STAGE"
grep -Fq 'world-functional-probe-enabled' "$STAGE"
grep -Fq 'world-functional-probe-disable-request' "$STAGE"
echo 'PRODUCTION_WORLD_FUNCTIONAL_MODULE_LIFECYCLE=PASS'

CATEGORY_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'category-lifecycle-probe-pass:9' "$STAGE"; then
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
for module in HitBox Notifications Macro1 NameHider NoJumpDelay NoHitDelay NoHurtCam Tracers AutoTool; do
  grep -Fq "category-lifecycle-probe-module-pass:$module:" "$STAGE"
done
echo 'PRODUCTION_WORLD_CATEGORY_LIFECYCLE=PASS modules=9 all-categories plus-FullBright-command-path'



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

NETWORK_COMMAND_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'network-command-probe-ready:CommandLine:original=' "$STAGE"; then
    NETWORK_COMMAND_READY=1
    break
  fi
  if grep -Fq 'network-command-probe-fail:' "$STAGE"; then
    echo 'Production network command readiness probe failed.'
    cat "$STAGE"
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before CommandLine became ready for physical chat probe.'
    exit 1
  fi
  sleep 0.25
done
if [ "$NETWORK_COMMAND_READY" -ne 1 ]; then
  echo 'CommandLine did not become ready for physical chat probe.'
  cat "$STAGE" || true
  exit 1
fi

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

DISPLAY=:99 xdotool key --clearmodifiers t
sleep 0.35
DISPLAY=:99 xdotool type --clearmodifiers --delay 35 -- '.help'
sleep 0.20
DISPLAY=:99 xdotool key --clearmodifiers Return

CHAT_INTERCEPT_READY=0
for _ in $(seq 1 120); do
  if [ -f "$NETWORK_STAGE" ] &&
     grep -Fq 'command-intercept:.help' "$NETWORK_STAGE" &&
     grep -Fq 'command-intercept-dispatch:true:.help' "$NETWORK_STAGE"; then
    CHAT_INTERCEPT_READY=1
    break
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before typed .help interception completed.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  sleep 0.25
done
if [ "$CHAT_INTERCEPT_READY" -ne 1 ]; then
  echo 'Typed .help did not reach the recovered NetworkManager command interception path.'
  cat "$NETWORK_STAGE" 2>/dev/null || true
  exit 1
fi

sleep 0.5
if grep -Fq '.help' "$SERVER_LOG"; then
  echo 'Typed .help leaked through to the local Minecraft server.'
  grep -F '.help' "$SERVER_LOG" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_DOT_COMMAND_CANCEL=PASS command=.help'

touch "$GAME_DIR/abyss-network-command-probe-done"
NETWORK_COMMAND_RESTORED=0
for _ in $(seq 1 240); do
  if grep -Fq 'network-command-probe-pass:CommandLine:restored=' "$STAGE"; then
    NETWORK_COMMAND_RESTORED=1
    break
  fi
  if grep -Fq 'network-command-probe-fail:' "$STAGE"; then
    echo 'CommandLine restoration failed after typed chat interception.'
    cat "$STAGE"
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  sleep 0.25
done
if [ "$NETWORK_COMMAND_RESTORED" -ne 1 ]; then
  echo 'CommandLine original state was not restored after typed chat interception.'
  cat "$STAGE" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_TYPED_COMMAND_INTERCEPT=PASS'

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

  # The Java milestone is emitted when displayGuiScreen assigns the screen; allow
  # at least one render frame before capturing visual evidence.
  sleep 0.35
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

if ! grep -Fq 'clickgui-font-ready' "$STAGE"; then
  echo 'Production ClickGUI mode cycle began before custom font textures were ready.'
  cat "$STAGE"
  exit 1
fi
if ! grep -Fq 'clickgui-mode-probe-pass:3:restored=' "$STAGE"; then
  echo 'Production ClickGUI mode cycle did not restore the original mode.'
  cat "$STAGE"
  exit 1
fi
echo 'PRODUCTION_WORLD_CLICKGUI_FONT_READY=PASS'
echo 'PRODUCTION_WORLD_CLICKGUI_MODE_CYCLE=PASS modes=STUDIO,RAVEN,VESTIGE'

PROMOTED_READY=0
for _ in $(seq 1 1600); do
  if grep -Fq 'promoted-registry-probe-pass:20' "$STAGE"; then
    PROMOTED_READY=1
    break
  fi
  if grep -Fq 'promoted-registry-probe-fail:' "$STAGE"; then
    echo 'Promoted-registry lifecycle sweep reported a failure.'
    grep -F 'promoted-registry-probe-' "$STAGE" || true
    for failure in abyss-module-failure.txt abyss-feature-failure.txt abyss-event-failure.txt; do
      failure_path="$GAME_DIR/$failure"
      if [ -s "$failure_path" ]; then
        cat "$failure_path"
      fi
    done
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited during promoted-registry lifecycle sweep.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  sleep 0.25
done
if [ "$PROMOTED_READY" -ne 1 ]; then
  echo 'Promoted-registry lifecycle sweep did not finish.'
  grep -F 'promoted-registry-probe-' "$STAGE" || true
  exit 1
fi
PROMOTED_MODULE_PASSES="$(grep -Fc 'promoted-registry-probe-module-pass:' "$STAGE" || true)"
if [ "$PROMOTED_MODULE_PASSES" -ne 20 ]; then
  echo "Promoted-registry lifecycle sweep pass count mismatch: $PROMOTED_MODULE_PASSES"
  grep -F 'promoted-registry-probe-' "$STAGE" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PROMOTED_REGISTRY_LIFECYCLE=PASS modules=20'

HIGH_RISK2_READY=0
for _ in $(seq 1 960); do
  if grep -Fq 'high-risk-functional-probe2-pass:3' "$STAGE"; then
    HIGH_RISK2_READY=1
    break
  fi
  if grep -Eq 'high-risk-functional-probe-fail:|high-risk-functional-probe2-fail:' "$STAGE"; then
    echo 'High-risk functional probes failed before the physical-input phase.'
    grep -E 'high-risk-functional-probe|high-risk-functional-probe2' "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before high-risk functional probes completed.'
    exit 1
  fi
  sleep 0.25
done
if [ "$HIGH_RISK2_READY" -ne 1 ]; then
  echo 'High-risk functional probes did not finish before physical-input phase.'
  grep -E 'high-risk-functional-probe|high-risk-functional-probe2' "$STAGE" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_INPUT_PREREQUISITES=PASS high-risk-batches=2'

PHYSICAL_AUTO_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'physical-input-functional-probe-ready:AutoClicker' "$STAGE"; then
    PHYSICAL_AUTO_READY=1
    break
  fi
  if grep -Fq 'physical-input-functional-probe-fail:' "$STAGE"; then
    echo 'Physical-input functional probe failed before AutoClicker input.'
    cat "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before AutoClicker physical-input probe became ready.'
    exit 1
  fi
  sleep 0.25
done
if [ "$PHYSICAL_AUTO_READY" -ne 1 ]; then
  echo 'AutoClicker physical-input probe did not become ready.'
  cat "$STAGE" || true
  exit 1
fi

DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
DISPLAY=:99 xdotool mousedown 1
PHYSICAL_AUTO_EFFECT=0
for _ in $(seq 1 120); do
  if grep -Fq 'physical-input-functional-probe-effect-pass:AutoClicker:physicalAttack=true' "$STAGE"; then
    PHYSICAL_AUTO_EFFECT=1
    break
  fi
  if grep -Fq 'physical-input-functional-probe-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
DISPLAY=:99 xdotool mouseup 1
if [ "$PHYSICAL_AUTO_EFFECT" -ne 1 ]; then
  echo 'AutoClicker did not react to the real X11 mouse-down input.'
  cat "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_AUTOCLICKER=PASS input=xdotool-mousedown-1'

PHYSICAL_FASTFALL_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'physical-input-functional-probe-ready:FastFall' "$STAGE"; then
    PHYSICAL_FASTFALL_READY=1
    break
  fi
  if grep -Fq 'physical-input-functional-probe-fail:' "$STAGE"; then
    echo 'Physical-input functional probe failed before FastFall input.'
    cat "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  sleep 0.25
done
if [ "$PHYSICAL_FASTFALL_READY" -ne 1 ]; then
  echo 'FastFall physical-input probe did not become ready.'
  cat "$STAGE" || true
  exit 1
fi

DISPLAY=:99 xdotool keydown space
PHYSICAL_FASTFALL_EFFECT=0
for _ in $(seq 1 120); do
  if grep -Fq 'physical-input-functional-probe-effect-pass:FastFall:motionY=' "$STAGE"; then
    PHYSICAL_FASTFALL_EFFECT=1
    break
  fi
  if grep -Fq 'physical-input-functional-probe-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
DISPLAY=:99 xdotool keyup space
if [ "$PHYSICAL_FASTFALL_EFFECT" -ne 1 ]; then
  echo 'FastFall did not react to the real X11 Space key-down input.'
  cat "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi

PHYSICAL_INPUT_RESTORED=0
for _ in $(seq 1 240); do
  if grep -Fq 'physical-input-functional-probe-pass:2' "$STAGE"; then
    PHYSICAL_INPUT_RESTORED=1
    break
  fi
  if grep -Fq 'physical-input-functional-probe-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
if [ "$PHYSICAL_INPUT_RESTORED" -ne 1 ]; then
  echo 'Physical-input functional probe did not restore both modules cleanly.'
  cat "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
grep -Fq 'physical-input-functional-probe-restore-pass:AutoClicker' "$STAGE"
grep -Fq 'physical-input-functional-probe-restore-pass:FastFall' "$STAGE"
echo 'PRODUCTION_WORLD_PHYSICAL_FASTFALL=PASS input=xdotool-keydown-space'
echo 'PRODUCTION_WORLD_PHYSICAL_INPUT_MODULES=PASS modules=AutoClicker,FastFall'

INVMOVE_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'invmove-physical-probe-ready:open-inventory' "$STAGE"; then
    INVMOVE_READY=1
    break
  fi
  if grep -Fq 'invmove-physical-probe-fail:' "$STAGE"; then
    echo 'InvMove physical-input probe failed before inventory open.'
    cat "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  sleep 0.25
done
if [ "$INVMOVE_READY" -ne 1 ]; then
  echo 'InvMove physical-input probe did not become ready.'
  cat "$STAGE" || true
  exit 1
fi

DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
DISPLAY=:99 xdotool keydown e
INVMOVE_E_SEEN=0
for _ in $(seq 1 40); do
  if grep -Fq 'invmove-physical-probe-input-seen:inventory=true' "$STAGE"; then
    INVMOVE_E_SEEN=1
    break
  fi
  if grep -Fq 'invmove-physical-probe-ready:forward-input' "$STAGE"; then
    INVMOVE_E_SEEN=1
    break
  fi
  sleep 0.05
done
sleep 0.10
DISPLAY=:99 xdotool keyup e
if [ "$INVMOVE_E_SEEN" -ne 1 ]; then
  echo 'InvMove/Minecraft did not observe the real X11 E key-down input.'
  cat "$STAGE" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_INVMOVE_INVENTORY_KEY=PASS input=xdotool-keydown-e'

INVMOVE_GUI_READY=0
for _ in $(seq 1 120); do
  if grep -Fq 'invmove-physical-probe-ready:forward-input' "$STAGE"; then
    INVMOVE_GUI_READY=1
    break
  fi
  if grep -Fq 'invmove-physical-probe-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
if [ "$INVMOVE_GUI_READY" -ne 1 ]; then
  echo 'InvMove did not observe a real inventory GUI open.'
  cat "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi

DISPLAY=:99 xdotool keydown w
INVMOVE_EFFECT=0
for _ in $(seq 1 120); do
  if grep -Fq 'invmove-physical-probe-effect-pass:forwardBinding=true:screen=' "$STAGE"; then
    INVMOVE_EFFECT=1
    break
  fi
  if grep -Fq 'invmove-physical-probe-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
DISPLAY=:99 xdotool keyup w
if [ "$INVMOVE_EFFECT" -ne 1 ]; then
  echo 'InvMove did not mirror the real X11 W key while a vanilla container screen was open.'
  cat "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_INVMOVE_FORWARD=PASS input=xdotool-keydown-w screen=GuiContainer'

DISPLAY=:99 xdotool key --clearmodifiers Escape
INVMOVE_RESTORED=0
for _ in $(seq 1 240); do
  if grep -Fq 'invmove-physical-probe-pass:1' "$STAGE"; then
    INVMOVE_RESTORED=1
    break
  fi
  if grep -Fq 'invmove-physical-probe-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
if [ "$INVMOVE_RESTORED" -ne 1 ]; then
  echo 'InvMove physical-input probe did not restore cleanly.'
  cat "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
grep -Fq 'invmove-physical-probe-close-effect-pass:forwardBinding=false' "$STAGE"
grep -Fq 'invmove-physical-probe-restore-pass:' "$STAGE"
echo 'PRODUCTION_WORLD_PHYSICAL_INVMOVE=PASS input=real-E-plus-W'

PROMOTED_FUNCTIONAL_READY=0
for _ in $(seq 1 960); do
  if grep -Fq 'high-risk-functional-probe22-pass:3' "$STAGE"; then
    PROMOTED_FUNCTIONAL_READY=1
    break
  fi
  if grep -Eq 'high-risk-functional-probe(15|16|17|18|19|20|21|22)-fail:' "$STAGE"; then
    echo 'Promoted functional probe failed after physical-input prerequisites.'
    grep -E 'high-risk-functional-probe(15|16|17|18|19|20|21|22)-' "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before promoted functional probes completed.'
    exit 1
  fi
  sleep 0.25
done
if [ "$PROMOTED_FUNCTIONAL_READY" -ne 1 ]; then
  echo 'Promoted functional probes 15-22 did not finish after physical-input prerequisites.'
  grep -E 'high-risk-functional-probe(15|16|17|18|19|20|21|22)-' "$STAGE" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PROMOTED_FUNCTIONAL_PREREQUISITES=PASS probes=15-22'

INPUTFIX_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'high-risk-functional-probe25-ready:InputFix:' "$STAGE"; then
    INPUTFIX_READY=1
    break
  fi
  if grep -Eq 'high-risk-functional-probe(23|24|25)-fail:' "$STAGE"; then
    echo 'Promoted semantic prerequisite/InputFix probe failed before keyboard injection.'
    grep -E 'high-risk-functional-probe(23|24|25)-' "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before InputFix became ready.'
    exit 1
  fi
  sleep 0.25
done
if [ "$INPUTFIX_READY" -ne 1 ]; then
  echo 'InputFix did not become ready after ClosestPlayerHUD/FallIndicator prerequisites.'
  grep -E 'high-risk-functional-probe(23|24|25)-' "$STAGE" || true
  exit 1
fi

WINDOW=""
for _ in $(seq 1 30); do
  WINDOW="$(DISPLAY=:99 xdotool search --onlyvisible --name 'Minecraft' 2>/dev/null | head -n 1 || true)"
  if [ -n "$WINDOW" ]; then
    break
  fi
  sleep 1
done
if [ -z "$WINDOW" ]; then
  echo 'Production Minecraft window was not found for InputFix probe.'
  exit 1
fi

DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
sleep 0.5
DISPLAY=:99 xdotool key --clearmodifiers t
sleep 0.35
DISPLAY=:99 xdotool type --clearmodifiers --delay 35 -- 'OPENABYSS_INPUTFIX_7E51'

INPUTFIX_PASS=0
for _ in $(seq 1 160); do
  if grep -Fq 'high-risk-functional-probe25-effect-pass:InputFix:text=true:takeovers=' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe25-pass:1' "$STAGE"; then
    INPUTFIX_PASS=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe25-fail:' "$STAGE"; then
    echo 'InputFix physical keyboard probe reported a failure.'
    cat "$STAGE"
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited during InputFix physical keyboard probe.'
    exit 1
  fi
  sleep 0.25
done
if [ "$INPUTFIX_PASS" -ne 1 ]; then
  echo 'InputFix did not observe exact physical chat text plus ASM takeover.'
  cat "$STAGE" || true
  exit 1
fi
DISPLAY=:99 xdotool key --clearmodifiers Escape
sleep 0.4
echo 'PRODUCTION_WORLD_PHYSICAL_INPUTFIX=PASS input=xdotool-chat sentinel=OPENABYSS_INPUTFIX_7E51'

AUTOTOOL_READY=0
for _ in $(seq 1 480); do
  if grep -Fq 'high-risk-functional-probe32-ready:AutoTool:mouse=left' "$STAGE"; then
    AUTOTOOL_READY=1
    break
  fi
  if grep -Eq 'high-risk-functional-probe(26|27|28|29|30|31|32)-fail:' "$STAGE"; then
    echo 'AutoTool prerequisite/probe chain failed before physical mouse input.'
    grep -E 'high-risk-functional-probe(26|27|28|29|30|31|32)-' "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before AutoTool physical-input probe became ready.'
    exit 1
  fi
  sleep 0.25
done
if [ "$AUTOTOOL_READY" -ne 1 ]; then
  echo 'AutoTool physical-input probe did not become ready after probes 26-31.'
  grep -E 'high-risk-functional-probe(26|27|28|29|30|31|32)-' "$STAGE" || true
  exit 1
fi

DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
DISPLAY=:99 xdotool mousedown 1
AUTOTOOL_SWITCHED=0
for _ in $(seq 1 160); do
  if grep -Fq 'high-risk-functional-probe32-effect-pass:AutoTool:switch=0->4' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe32-ready-release:AutoTool:mouse=left' "$STAGE"; then
    AUTOTOOL_SWITCHED=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe32-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
DISPLAY=:99 xdotool mouseup 1
if [ "$AUTOTOOL_SWITCHED" -ne 1 ]; then
  echo 'AutoTool did not switch to the ranked pickaxe from real X11 LMB input.'
  grep -F 'high-risk-functional-probe32-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_AUTOTOOL_SWITCH=PASS input=xdotool-mousedown-1 slot=0->4'

AUTOTOOL_RESTORED=0
for _ in $(seq 1 160); do
  if grep -Fq 'high-risk-functional-probe32-effect-pass:AutoTool:switchBack=4->0' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe32-pass:1' "$STAGE"; then
    AUTOTOOL_RESTORED=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe32-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
if [ "$AUTOTOOL_RESTORED" -ne 1 ]; then
  echo 'AutoTool did not restore the original slot after real X11 LMB release.'
  grep -F 'high-risk-functional-probe32-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_AUTOTOOL=PASS input=real-LMB switch=0->4->0'

AIMASSIST_READY=0
for _ in $(seq 1 320); do
  if grep -Fq 'high-risk-functional-probe34-ready:AimAssist:mouse=left' "$STAGE"; then
    AIMASSIST_READY=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe34-fail:' "$STAGE"; then
    echo 'AimAssist physical targeting probe failed before mouse input.'
    grep -F 'high-risk-functional-probe34-' "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before AimAssist became ready.'
    exit 1
  fi
  sleep 0.25
done
if [ "$AIMASSIST_READY" -ne 1 ]; then
  echo 'AimAssist physical targeting probe did not become ready.'
  grep -F 'high-risk-functional-probe34-' "$STAGE" || true
  exit 1
fi

DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
DISPLAY=:99 xdotool mousedown 1
AIMASSIST_EFFECT=0
for _ in $(seq 1 160); do
  if grep -Fq 'high-risk-functional-probe34-effect-pass:AimAssist:yawChanged=true:lockAngles=true:' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe34-ready-release:AimAssist:mouse=left' "$STAGE"; then
    AIMASSIST_EFFECT=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe34-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
DISPLAY=:99 xdotool mouseup 1
if [ "$AIMASSIST_EFFECT" -ne 1 ]; then
  echo 'AimAssist did not acquire/rotate toward the fixture from real X11 LMB input.'
  grep -F 'high-risk-functional-probe34-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_AIMASSIST_EFFECT=PASS input=xdotool-mousedown-1'

AIMASSIST_RELEASED=0
for _ in $(seq 1 160); do
  if grep -Fq 'high-risk-functional-probe34-release-pass:AimAssist:cacheCleared=true' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe34-pass:1' "$STAGE"; then
    AIMASSIST_RELEASED=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe34-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
if [ "$AIMASSIST_RELEASED" -ne 1 ]; then
  echo 'AimAssist did not clear/restore after real X11 LMB release.'
  grep -F 'high-risk-functional-probe34-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_AIMASSIST=PASS input=real-LMB target=zombie rotation=live'

SPRINTRESET_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'high-risk-functional-probe36-ready:SprintReset:key=w' "$STAGE"; then
    SPRINTRESET_READY=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe36-fail:' "$STAGE"; then
    echo 'SprintReset physical forward-reset probe failed before W input.'
    grep -F 'high-risk-functional-probe36-' "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before SprintReset became ready.'
    exit 1
  fi
  sleep 0.25
done
if [ "$SPRINTRESET_READY" -ne 1 ]; then
  echo 'SprintReset physical forward-reset probe did not become ready.'
  grep -F 'high-risk-functional-probe35-' "$STAGE" || true
  grep -F 'high-risk-functional-probe36-' "$STAGE" || true
  exit 1
fi

DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
DISPLAY=:99 xdotool keydown w
SPRINTRESET_EFFECT=0
for _ in $(seq 1 160); do
  if grep -Fq 'high-risk-functional-probe36-effect-pass:SprintReset:forward=0.0:strafe=0.0:logicalW=false:physicalW=true' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe36-duration-pass:SprintReset:logicalW=true:physicalW=true' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe36-ready-release:SprintReset:key=w' "$STAGE"; then
    SPRINTRESET_EFFECT=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe36-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
DISPLAY=:99 xdotool keyup w
if [ "$SPRINTRESET_EFFECT" -ne 1 ]; then
  echo 'SprintReset did not perform LEGIT forward reset/held-key restoration from real X11 W input.'
  grep -F 'high-risk-functional-probe36-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_SPRINTRESET_EFFECT=PASS input=xdotool-keydown-w'

SPRINTRESET_RELEASED=0
for _ in $(seq 1 160); do
  if grep -Fq 'high-risk-functional-probe36-release-pass:SprintReset:logicalW=false:physicalW=false' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe36-pass:1' "$STAGE"; then
    SPRINTRESET_RELEASED=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe36-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
if [ "$SPRINTRESET_RELEASED" -ne 1 ]; then
  echo 'SprintReset did not resync logical W after real X11 W release.'
  grep -F 'high-risk-functional-probe36-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_SPRINTRESET=PASS input=real-W mode=LEGIT'

AUTOWEAPON_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'high-risk-functional-probe40-ready:AutoWeapon:mouse=left:best=4' "$STAGE"; then
    AUTOWEAPON_READY=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe40-fail:' "$STAGE"; then
    echo 'AutoWeapon physical best-weapon probe failed before LMB input.'
    grep -F 'high-risk-functional-probe40-' "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before AutoWeapon became ready.'
    exit 1
  fi
  sleep 0.25
done
if [ "$AUTOWEAPON_READY" -ne 1 ]; then
  echo 'AutoWeapon physical best-weapon probe did not become ready.'
  grep -F 'high-risk-functional-probe40-' "$STAGE" || true
  exit 1
fi

DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
DISPLAY=:99 xdotool mousedown 1
AUTOWEAPON_EFFECT=0
for _ in $(seq 1 160); do
  if grep -Fq 'high-risk-functional-probe40-effect-pass:AutoWeapon:switch=0->4:physicalLmb=true' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe40-ready-release:AutoWeapon:mouse=left' "$STAGE"; then
    AUTOWEAPON_EFFECT=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe40-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
DISPLAY=:99 xdotool mouseup 1
if [ "$AUTOWEAPON_EFFECT" -ne 1 ]; then
  echo 'AutoWeapon did not switch from wooden sword slot 0 to diamond sword slot 4 under real X11 LMB.'
  grep -F 'high-risk-functional-probe40-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_AUTOWEAPON_EFFECT=PASS input=xdotool-mousedown-1 switch=0->4'

AUTOWEAPON_RESTORED=0
for _ in $(seq 1 240); do
  if grep -Fq 'high-risk-functional-probe40-pass:1' "$STAGE"; then
    AUTOWEAPON_RESTORED=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe40-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
if [ "$AUTOWEAPON_RESTORED" -ne 1 ]; then
  echo 'AutoWeapon physical best-weapon probe did not restore cleanly after LMB release.'
  grep -F 'high-risk-functional-probe40-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_AUTOWEAPON=PASS input=real-LMB best=diamond_sword'

FLY_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'high-risk-functional-probe42-ready:Fly:key=space' "$STAGE"; then
    FLY_READY=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe42-fail:' "$STAGE"; then
    echo 'Fly physical Space probe failed before input.'
    grep -F 'high-risk-functional-probe42-' "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before Fly became ready.'
    exit 1
  fi
  sleep 0.25
done
if [ "$FLY_READY" -ne 1 ]; then
  echo 'Fly physical Space probe did not become ready.'
  grep -F 'high-risk-functional-probe42-' "$STAGE" || true
  exit 1
fi

DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
DISPLAY=:99 xdotool keydown space
FLY_EFFECT=0
for _ in $(seq 1 160); do
  if grep -Fq 'high-risk-functional-probe42-effect-pass:Fly:verticalAccumulator=' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe42-effect-pass:Fly:motionY=' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe42-ready-release:Fly:key=space' "$STAGE"; then
    FLY_EFFECT=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe42-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
DISPLAY=:99 xdotool keyup space
if [ "$FLY_EFFECT" -ne 1 ]; then
  echo 'Fly did not apply its recovered movement path under real X11 Space.'
  grep -F 'high-risk-functional-probe42-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_FLY_EFFECT=PASS input=xdotool-keydown-space'

FLY_RESTORED=0
for _ in $(seq 1 240); do
  if grep -Fq 'high-risk-functional-probe42-pass:1' "$STAGE"; then
    FLY_RESTORED=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe42-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
if [ "$FLY_RESTORED" -ne 1 ]; then
  echo 'Fly physical Space probe did not restore cleanly after release.'
  grep -F 'high-risk-functional-probe42-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_FLY=PASS input=real-Space binder=FlyBinder'

INVCLICKER_READY=0
for _ in $(seq 1 320); do
  if grep -Fq 'high-risk-functional-probe44-ready:InvClicker:input=shift+lmb:slot=9' "$STAGE"; then
    INVCLICKER_READY=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe43-fail:' "$STAGE"; then
    echo 'InvClicker predecessor InvManager probe failed.'
    grep -F 'high-risk-functional-probe43-' "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if grep -Fq 'high-risk-functional-probe44-fail:' "$STAGE"; then
    echo 'InvClicker physical shift-click probe failed before input.'
    grep -F 'high-risk-functional-probe44-' "$STAGE" || true
    cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
    exit 1
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before InvClicker became ready.'
    exit 1
  fi
  sleep 0.25
done
if [ "$INVCLICKER_READY" -ne 1 ]; then
  echo 'InvClicker physical shift-click probe did not become ready.'
  grep -F 'high-risk-functional-probe43-' "$STAGE" || true
  grep -F 'high-risk-functional-probe44-' "$STAGE" || true
  exit 1
fi

# GuiInventory is 176x166 at guiScale=1. Main-inventory slot 9 is the
# leftmost slot of the first inventory row: guiLeft+8..25, guiTop+84..101.
# Park the pointer at its center before pressing anything. The client fixture
# does not open GuiInventory until it has already observed the held physical
# Shift+LMB state, so vanilla cannot consume the original mouse-down as a GUI
# click and create a false positive.
INVCLICKER_X=$((WIDTH / 2 - 72))
INVCLICKER_Y=$((HEIGHT / 2 + 9))
echo "PRODUCTION_WORLD_INVCLICKER_TARGET=$INVCLICKER_X,$INVCLICKER_Y"
DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
DISPLAY=:99 xdotool mousemove --window "$WINDOW" "$INVCLICKER_X" "$INVCLICKER_Y"
DISPLAY=:99 xdotool keydown Shift_L
DISPLAY=:99 xdotool mousedown 1

# Minecraft centers the OS cursor when displayGuiScreen() releases in-game
# mouse capture. Probe44 intentionally opens GuiInventory only after observing
# the held physical Shift+LMB state, so the pre-GUI pointer placement above is
# expected to be overwritten by that transition. Wait until the real
# GuiInventory is live, then re-park the still-held pointer over slot 9.
INVCLICKER_GUI_READY=0
for _ in $(seq 1 120); do
  if grep -Fq 'high-risk-functional-probe44-gui-pass:InvClicker:net.minecraft.client.gui.inventory.GuiInventory' "$STAGE"; then
    INVCLICKER_GUI_READY=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe44-fail:' "$STAGE"; then
    break
  fi
  sleep 0.10
done
if [ "$INVCLICKER_GUI_READY" -ne 1 ]; then
  DISPLAY=:99 xdotool mouseup 1
  DISPLAY=:99 xdotool keyup Shift_L
  echo 'InvClicker did not open GuiInventory before post-open pointer placement.'
  grep -F 'high-risk-functional-probe44-' "$STAGE" || true
  exit 1
fi

DISPLAY=:99 xdotool mousemove --window "$WINDOW" "$INVCLICKER_X" "$INVCLICKER_Y"
echo 'PRODUCTION_WORLD_INVCLICKER_POST_GUI_TARGET=PASS cursor=repositioned-after-displayGuiScreen'

INVCLICKER_EFFECT=0
for _ in $(seq 1 240); do
  if grep -Fq 'high-risk-functional-probe44-effect-pass:InvClicker:shiftClick=9->0:item=apple*3:physicalLmb=true:physicalShift=true' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe44-ready-release:InvClicker:input=shift+lmb' "$STAGE"; then
    INVCLICKER_EFFECT=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe44-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
DISPLAY=:99 xdotool mouseup 1
DISPLAY=:99 xdotool keyup Shift_L

if [ "$INVCLICKER_EFFECT" -ne 1 ]; then
  echo 'InvClicker did not synthesize the inventory shift-click from held physical Shift+LMB.'
  grep -F 'high-risk-functional-probe44-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_INVCLICKER_EFFECT=PASS input=held-before-GUI-shift+lmb slot=9->0'

INVCLICKER_RESTORED=0
for _ in $(seq 1 320); do
  if grep -Fq 'high-risk-functional-probe44-pass:1' "$STAGE"; then
    INVCLICKER_RESTORED=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe44-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
if [ "$INVCLICKER_RESTORED" -ne 1 ]; then
  echo 'InvClicker physical shift-click probe did not restore cleanly after input release.'
  grep -F 'high-risk-functional-probe44-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi
echo 'PRODUCTION_WORLD_PHYSICAL_INVCLICKER=PASS input=real-Shift+LMB binder=InvClickerBinder'

BLINK_SENTINEL='OPENABYSS_BLINK_45'
BLINK_BUFFERED=0
for _ in $(seq 1 320); do
  if grep -Fq "high-risk-functional-probe45-buffered-pass:Blink:sentinel=$BLINK_SENTINEL" "$STAGE" &&
     grep -Fq 'high-risk-functional-probe45-ready-flush:Blink:gate=abyss-blink-probe-flush-go' "$STAGE"; then
    BLINK_BUFFERED=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe45-fail:' "$STAGE"; then
    break
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before Blink buffered the sentinel packet.'
    exit 1
  fi
  sleep 0.25
done
if [ "$BLINK_BUFFERED" -ne 1 ]; then
  echo 'Blink did not buffer the real sentinel chat packet.'
  grep -F 'high-risk-functional-probe45-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi

sleep 0.75
if grep -Fq "$BLINK_SENTINEL" "$SERVER_LOG"; then
  echo 'Blink sentinel reached the local server before flush release.'
  grep -F "$BLINK_SENTINEL" "$SERVER_LOG" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_BLINK_WITHHELD=PASS sentinel=absent-server-side'

touch "$GAME_DIR/abyss-blink-probe-flush-go"

BLINK_FLUSHED=0
for _ in $(seq 1 320); do
  if grep -Fq 'high-risk-functional-probe45-flush-pass:Blink:buffering=false:queued=0' "$STAGE" &&
     grep -Fq 'high-risk-functional-probe45-pass:1' "$STAGE"; then
    BLINK_FLUSHED=1
    break
  fi
  if grep -Fq 'high-risk-functional-probe45-fail:' "$STAGE"; then
    break
  fi
  sleep 0.25
done
if [ "$BLINK_FLUSHED" -ne 1 ]; then
  echo 'Blink did not flush and restore after release gate.'
  grep -F 'high-risk-functional-probe45-' "$STAGE" || true
  cat "$GAME_DIR/abyss-feature-failure.txt" 2>/dev/null || true
  exit 1
fi

BLINK_SERVER_DELIVERED=0
for _ in $(seq 1 120); do
  if grep -Fq "$BLINK_SENTINEL" "$SERVER_LOG"; then
    BLINK_SERVER_DELIVERED=1
    break
  fi
  sleep 0.25
done
if [ "$BLINK_SERVER_DELIVERED" -ne 1 ]; then
  echo 'Blink flushed internally but the sentinel did not reach the local server.'
  tail -n 250 "$SERVER_LOG" || true
  grep -F 'high-risk-functional-probe45-' "$STAGE" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_BLINK_FLUSH_DELIVERED=PASS sentinel=server-observed'
echo 'PRODUCTION_WORLD_HIGH_RISK_FUNCTIONAL45=PASS modules=Blink'

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
sleep 2

# Sustained post-probe world stability. The runtime-self-test heartbeat is emitted
# every 100 in-world ticks, so a real span proves the game thread continued to
# tick with player/world/net handler alive after all deep probes completed.
SOAK_START_MS="$(date +%s%3N)"
echo "PRODUCTION_WORLD_SOAK_START_MS=$SOAK_START_MS"
for SOAK_STEP in $(seq 1 12); do
  sleep 5
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo "Production client exited during post-probe soak at step $SOAK_STEP."
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  for failure in abyss-module-failure.txt abyss-feature-failure.txt abyss-event-failure.txt abyss-config-failure.txt abyss-renderer-failure.txt; do
    failure_path="$GAME_DIR/$failure"
    if [ -s "$failure_path" ]; then
      echo "Production failure journal became non-empty during soak: $failure_path"
      cat "$failure_path"
      exit 1
    fi
  done
done

mapfile -t SOAK_HEARTBEATS < <(
  awk -F '\t' -v start="$SOAK_START_MS" '
    $1 ~ /^[0-9]+$/ && $1 >= start && $2 ~ /^world-heartbeat:[0-9]+$/ { print $1 "\t" $2 }
  ' "$STAGE"
)
SOAK_HEARTBEAT_COUNT="${#SOAK_HEARTBEATS[@]}"
echo "PRODUCTION_WORLD_SOAK_HEARTBEATS=$SOAK_HEARTBEAT_COUNT"
if [ "$SOAK_HEARTBEAT_COUNT" -lt 9 ]; then
  echo 'Too few in-world heartbeats were observed during the 60-second soak.'
  printf '%s\n' "${SOAK_HEARTBEATS[@]}" || true
  exit 1
fi

SOAK_FIRST_MS="$(printf '%s\n' "${SOAK_HEARTBEATS[0]}" | cut -f1)"
SOAK_LAST_INDEX=$((SOAK_HEARTBEAT_COUNT - 1))
SOAK_LAST_MS="$(printf '%s\n' "${SOAK_HEARTBEATS[$SOAK_LAST_INDEX]}" | cut -f1)"
SOAK_SPAN_MS=$((SOAK_LAST_MS - SOAK_FIRST_MS))
echo "PRODUCTION_WORLD_SOAK_HEARTBEAT_SPAN_MS=$SOAK_SPAN_MS"
if [ "$SOAK_SPAN_MS" -lt 45000 ]; then
  echo "World heartbeat span was too short during soak: $SOAK_SPAN_MS ms"
  exit 1
fi

if grep -Eq 'CIProdWorld (lost connection|left the game)' "$SERVER_LOG"; then
  echo 'Production server recorded a disconnect before soak completed.'
  grep -E 'CIProdWorld (lost connection|left the game)' "$SERVER_LOG" || true
  exit 1
fi

echo 'PRODUCTION_WORLD_POST_PROBE_SOAK=PASS seconds=60'

# Prove live-world teardown and reconnect in the same JVM. This catches stale
# PacketManager buffers, stale EventBus owners, and one-shot world lifecycle bugs
# that a process-restart smoke cannot expose.
DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
DISPLAY=:99 xdotool key --clearmodifiers Escape
sleep 0.50

DISCONNECT_X=$((WIDTH / 2))
DISCONNECT_Y=$((HEIGHT / 4 + 104))
echo "PRODUCTION_WORLD_DISCONNECT_CLICK=$DISCONNECT_X,$DISCONNECT_Y"
DISPLAY=:99 xdotool mousemove --window "$WINDOW" "$DISCONNECT_X" "$DISCONNECT_Y" click 1

SESSION_EXIT_READY=0
for _ in $(seq 1 120); do
  if grep -Fq 'world-session-exit:1' "$STAGE" &&      grep -Fq 'world-session-menu-cleanup:1:packetBuffer=false:u=0:v=0:a=0' "$STAGE"; then
    SESSION_EXIT_READY=1
    break
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited while disconnecting first world session.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  sleep 0.25
done
if [ "$SESSION_EXIT_READY" -ne 1 ]; then
  echo 'First world session did not exit through clean menu teardown.'
  DISPLAY=:99 scrot "$RUNNER_TEMP/openabyss-production-world-reconnect-exit-fail.png" || true
  cat "$STAGE" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_SESSION1_EXIT_CLEANUP=PASS'

# Depending on the vanilla parent screen, Disconnect may return to GuiMultiplayer
# or directly to the main menu. Normalize with one Escape before re-entering the
# same Multiplayer -> Direct Connect path used for the first session.
sleep 0.75
DISPLAY=:99 xdotool windowfocus --sync "$WINDOW"
DISPLAY=:99 xdotool key --clearmodifiers Escape
sleep 0.40
DISPLAY=:99 scrot "$RUNNER_TEMP/openabyss-production-world-reconnect-menu.png" || true
DISPLAY=:99 xdotool mousemove --window "$WINDOW" "$MULTIPLAYER_X" "$MULTIPLAYER_Y" click 1
sleep 0.75
DISPLAY=:99 xdotool mousemove --window "$WINDOW" "$DIRECT_X" "$DIRECT_Y" click 1
sleep 0.50
DISPLAY=:99 xdotool key --clearmodifiers End
DISPLAY=:99 xdotool key --clearmodifiers --repeat 96 --delay 2 BackSpace
DISPLAY=:99 xdotool type --clearmodifiers --delay 25 -- '127.0.0.1:25565'
sleep 0.20
DISPLAY=:99 xdotool key --clearmodifiers Return
echo 'PRODUCTION_WORLD_UI_RECONNECT_REQUEST=PASS'

SECOND_JOIN_READY=0
for _ in $(seq 1 240); do
  if grep -Fq 'world-session-join:2' "$STAGE" &&      grep -Fq 'world-session-lifecycle-complete:2' "$STAGE"; then
    SECOND_JOIN_READY=1
    break
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited before second world session became live.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    tail -n 300 "$SERVER_LOG" || true
    exit 1
  fi
  sleep 0.25
done
if [ "$SECOND_JOIN_READY" -ne 1 ]; then
  echo 'Second production world session did not reach lifecycle completion.'
  DISPLAY=:99 scrot "$RUNNER_TEMP/openabyss-production-world-reconnect-join-fail.png" || true
  cat "$STAGE" || true
  tail -n 300 "$SERVER_LOG" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_SESSION2_JOIN_LIFECYCLE=PASS'

SECOND_HEARTBEAT_READY=0
for _ in $(seq 1 160); do
  if grep -Fq 'world-session-heartbeat:2:300' "$STAGE"; then
    SECOND_HEARTBEAT_READY=1
    break
  fi
  if ! kill -0 "$CLIENT_PID" 2>/dev/null; then
    echo 'Production client exited during second-session heartbeat soak.'
    tail -n 300 "$STDOUT" || true
    tail -n 300 "$STDERR" || true
    exit 1
  fi
  for failure in abyss-module-failure.txt abyss-feature-failure.txt abyss-event-failure.txt abyss-config-failure.txt abyss-renderer-failure.txt; do
    failure_path="$GAME_DIR/$failure"
    if [ -s "$failure_path" ]; then
      echo "Production failure journal became non-empty after reconnect: $failure_path"
      cat "$failure_path"
      exit 1
    fi
  done
  sleep 0.25
done
if [ "$SECOND_HEARTBEAT_READY" -ne 1 ]; then
  echo 'Second world session did not sustain 300 ticks.'
  cat "$STAGE" || true
  exit 1
fi
echo 'PRODUCTION_WORLD_SESSION2_HEARTBEAT=PASS ticks=300'

LOGIN_COUNT="$(grep -Ec 'CIProdWorld.*logged in with entity id' "$SERVER_LOG" || true)"
echo "PRODUCTION_WORLD_SERVER_LOGIN_COUNT=$LOGIN_COUNT"
if [ "$LOGIN_COUNT" -lt 2 ]; then
  echo 'Production server did not record two real logins for same-process reconnect.'
  tail -n 300 "$SERVER_LOG" || true
  exit 1
fi

echo 'PRODUCTION_WORLD_SAME_PROCESS_RECONNECT=PASS sessions=2'
echo '=== production runtime milestones ==='
cat "$STAGE"
echo 'OPENABYSS_LINUX_PRODUCTION_WORLD_SMOKE=PASS'
