#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import sys

text = pathlib.Path("src/main/java/Abyss/util/DeferredRendererReload.java").read_text(encoding="utf-8")

checks = {
    "cooldown_constant": "private static final long COOLDOWN_MS = 3000L;" in text,
    "bounded_retry_constant": "private static final int MAX_RETRIES = 2;" in text,
    "request_resets_retry": "retryCount = 0;" in text[text.find("public static void request()"):text.find("public static void flush()")],
    "null_renderer_keeps_pending": (
        "if (mc == null || mc.renderGlobal == null)" in text
        and "pending = false;" in text
        and text.find("if (mc == null || mc.renderGlobal == null)") < text.find("pending = false;")
    ),
    "failure_requeues": "if (retryCount < MAX_RETRIES)" in text and "pending = true;" in text,
    "success_resets_retry": "mc.renderGlobal.loadRenderers();" in text and "retryCount = 0;" in text,
    "failure_evidence": "DeferredRendererReload.recordFailure(throwable);" in text,
}

for name, ok in checks.items():
    print(f"RENDERER_RELOAD_CONTRACT {name}={'PASS' if ok else 'FAIL'}")

bad = [name for name, ok in checks.items() if not ok]
if bad:
    for name in bad:
        print(f"RENDERER_RELOAD_BAD={name}")
    print("RENDERER_RELOAD_GATE=FAIL")
    sys.exit(1)

print("RENDERER_RELOAD_GATE=PASS")
