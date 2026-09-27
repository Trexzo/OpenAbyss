#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import sys

ROOT = pathlib.Path("src/main/java")
manager = (ROOT / "Abyss/ui/screen/AccountManagerScreen.java").read_text(encoding="utf-8")
slot = (ROOT / "Abyss/internal/auth/AccountListSlot.java").read_text(encoding="utf-8")
alt = (ROOT / "Abyss/internal/auth/AltManager.java").read_text(encoding="utf-8")
reconnect = (ROOT / "Abyss/ui/screen/ReconnectHandler.java").read_text(encoding="utf-8")

checks = {
    "manager_locking": manager.count("synchronized (AltManager.Q)") >= 7,
    "manager_refresh_access_saved": 'var14.r(refreshedAccess);' in manager,
    "manager_refresh_refresh_saved": 'var14.H(refreshedRefresh);' in manager,
    "manager_verified_direct_swap": 'SessionAccessor.set(var3)' in manager,
    "manager_verified_refreshed_swap": 'SessionAccessor.set(var1xx)' in manager,
    "manager_safe_selection": 'Selected account is no longer available.' in manager,
    "slot_size_locked": slot.count("synchronized (AltManager.Q)") >= 3,
    "slot_row_bounds": 'if (var1 < 0 || var1 >= AltManager.Q.size())' in slot,
    "slot_row_snapshot": all(marker in slot for marker in (
        "username = var14.h();",
        "accountType = var14.v();",
        "unban = var14.F();",
    )),
    "alt_save_serialized": "public static synchronized void O(long var0)" in alt,
    "alt_upsert_locked": "public static Account upsert(Account incoming)" in alt
        and "synchronized (Q)" in alt,
    "reconnect_metadata_locked": "synchronized (AltManager.Q)" in reconnect,
}

for name, ok in checks.items():
    print(f"ACCOUNT_STATE_CONTRACT {name}={'PASS' if ok else 'FAIL'}")

failed = [name for name, ok in checks.items() if not ok]
if failed:
    print("ACCOUNT_STATE_CONTRACT_GATE=FAIL")
    for name in failed:
        print(f"ACCOUNT_STATE_CONTRACT_BAD={name}")
    sys.exit(1)

print("ACCOUNT_STATE_CONTRACT_GATE=PASS")
