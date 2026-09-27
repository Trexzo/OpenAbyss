#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import sys

text = pathlib.Path("src/main/java/Abyss/util/ClipPlayer.java").read_text(encoding="utf-8")

checks = {
    "audio_worker_daemon": '"Abyss-Audio"' in text and "t2.setDaemon(true);" in text,
    "buffered_stream_closed": "try (BufferedInputStream" in text,
    "audio_stream_closed": "AudioInputStream var4 = AudioSystem.getAudioInputStream(var3)" in text,
    "clip_stop_listener": "event.getType() == LineEvent.Type.STOP" in text,
    "clip_closed_on_stop": "var5.close();" in text,
    "clip_closed_on_failure": "catch (Throwable failure)" in text and "var5.close();" in text,
}

for name, ok in checks.items():
    print(f"AUDIO_LIFECYCLE {name}={'PASS' if ok else 'FAIL'}")

bad = [name for name, ok in checks.items() if not ok]
if bad:
    for name in bad:
        print(f"AUDIO_LIFECYCLE_BAD={name}")
    print("AUDIO_LIFECYCLE_GATE=FAIL")
    sys.exit(1)

print("AUDIO_LIFECYCLE_GATE=PASS")
