#!/usr/bin/env python3
from __future__ import annotations

import argparse
import base64
import json
import re
import struct
import sys
import zlib
import zipfile

# Runnable-era abyss.jar SHA-256:
# 13814827D8341CA6F4D7510F8B07C66F998EB7F43FA70BB18B0755AB7A4474F1
# Rows: class, method, descriptor, code_length, max_stack, max_locals, exception_table_entries.
REFERENCE_B64 = """eNq9XWtv2zoS/S/9tAUMRO8H+il2+pCadIMqbRe9uFgoNpPoVpYMSU6Rf7982BYpS+SQSrdFg6QhefgYzpwZDum//npzef/SthfberMv0UWx3ZUX63p7n3cXl8X2sm2LtnuzeHOJ//0rffv9zSJYOAt3Yf29ANasqwx1l9VjiVrSxjWrhJ5R1bGv7cWpwHvy8zsK44cYx9PCua3b7q5Y/6JdHcM5FmAwBMX244WP/ypgqq74UDToPi9LhtSgI9AqyUahWIkeybMCjGPHulA/6qbcXNf5ZmryTgW4UcULG8+eFhK/wK56gUdm5KbetyipdvtuqqtCIW6tncjDcxOqMPddvSxrOu119RVtMP66S9pvbVE9Jh3aTsGOFOXBnRjLWaABfq07vggDeFqjmzWdNhlStHAsLchvu03eoVW+RU1+WW2+omqDmsmtNFq6l0DX1R7ycUONofVbiS2ZB9ENQus3tMBd/jgFcSrA6yCyETxNsVyj4hnd5utfqJsWSK4QhxdGGM/XExSs0tha0KXKptQeK9MvkBO6WOZtGwC2KvHsU1HodYTjQ5SEUFnQmilMZ0ZEK6gWYImn8a7J+53Tz8cqmcDhpyOjW5TYgVgDqZ8LrNWVUyH2ke0W52q6i8cSrIcruqttFyAdItBl1+Fv32Nl3b1MCSNfRlAiEREP5eQTSfxUdOdznwKmnoqSBdhkAsysPRYHAIMvjkrcYuAdpj2uV9XATgjQwMPVM7M5dAO5UQThNx/yX+iaamG6gd6mZP84C1uvGnjf9fUIDa02vcyk40T0WIQjUwHA2PA4/O5O1ZubYkQxYLV4ELwO18UzZjO9ZE4sF19M4Fw+2d9KkiCAcgRbza+ZGgkXaiWCJTBDJWZo43KYAgWRDisOsVCEGoC3vVGzAK6N2NczVbQC6qKEbpowXrhK6ikiDlV6CtPpbMkBkpzut7uvqEXdufeRAt2PAKD4eBg910PsoKFyDmzNPtbV5wrryvt8WvpPBTjxdz0AgRFxbupnheifSnBL6wKW9jNCu2zXFNVsSbI9C6CvBnja9C/QH5PAQAAEJD2ORhOpF1k7NJqJbL9DDVCDn5Xl+247kL4XZXm5b3LOPrlX08yJ/Z53hLAnFCvpsYAiBH1SQNSH4oDEioeZZSzCEOD5c3Dajr9PbJ/KGGHr+jWvHtH/166LqJxhT5IVLHbmBwCXSYDhmVGmZkZMwC0f4PZwMJyTSmbf1+nggCImUI7oQaaCKYBzo+f7gG6KlQeKDuLlJglVqfod1aV9zHFfuMoA5BDmjEtlCZBMUf84JCHPQBPTzEGmlt3xDJZNMO5A2x4S/e7oy8eoicn0bAyjqZRd+EZTe5c3j3iZABPMlxxwVtXgvyPsxzIAgWYoWcYh0kE2haeDIXDBFEAGWVTe1sPR48VCB3VPDVIobx9O9VC+UrC1YoFxTcQzHZGsoEqChS1JmM/WHaTIMBIgA1hl7ISJhJtslTn+cZfvjDRECqb/J4hZ8TMSF1TrvRPWDCfD83QGNS9qgA24B8XitqXCclcPxSNmjl1RVxdf6q54KNb0h/aMDQGCwAmzcpa3sPGmAaLePaEtOjLXq/TqbUIDWqHMUPL1vxftPi+zXV0/HEeeYSbxE7OWWHZkt83XTX1xQ77a2lr5INHRtN7nmnf+bPPun23e+7PN+3+i+aJd0/PfZd3NdlnoPl9YALQvxfGAp8xfUPMRdV/y7TQYX4iDCmWGlGHtu/rjxzPem6mJ74oxbEvmrgwhPuXVpkSrp3xaZfVFuMhIJAtyUpBVvd3imtdFde57JRB1QyUBjyYkfpQOklEMJpCy6QNO1eUYpCHRlP5IkXODkiXwzIlRPtvRxhMdRIh7mGTJ4UDcMRifqak+QToGkNzxRQI8v7BJmEN7PoFnOZNdNcmN8WWpMRTpClVs0xen7pE4kQurZHRKQo1pAAYYasBUQwXaliU7cxCxLjVm4Gv++8i9+mqulOEP6/UTTmNLkbTeXbEdk1igwMaExWJWbk8gYJK9JS18yNvuwzHJiamRH3n5C5sxNr9TizxSlFc/vsyl78HLl5NPgL/HLUkyzvpCPVAsDfcMcPpVo2w4BHdPV+EfNYYPmIKkeiYDozirsm5Rtm4QqqaQuCL8dEsV4RiUvp5nR8W2dJuMIekH1xxbezgmK+QF0gzMERxgjuip5peanHxdIbw7zCI3ricLqHA4WVn/1rA3ZxVfZ+sz/9mPQJuLw/5aPD51NJGK+q6TNEAsJhwSSVMcxzAFickAIsPYr2P5UsU9NrrxRM7lFEmdyOWk8mDT8I56YbMdQhvzlCXXldvrHmb8YBI+n/K0tHMgzvJKYxN9zW5/ypB8RiwE86Ponj6hfHOwN5Nn0GelhbCtB9iZIrpuYNyGKcIeZPbhWhxqDYs7JbZBGnFsNQBLAJ33HVVG1P/8gfJdXZkbB1d2wHPAWZZF9euVzgKYWQmp6rSBqH3iB0hvTxTnw6xhSLcjrAM9m41kUYdBLW4HWxrVzFiw5+DRBAqI1RNqu/7Anf5nb2tYSDCb9kD6opwTFsp8kFHcAflLwezP92UuyCgWE7y0LpiHOYk3KCeErvUwuUPrALDqgyWZE/p2PcIMQghg1qG8NPe5sPIEClsPBGRsU700su6eFeFuRopdThzDVZM/zMh89unNk0gxqAEQVlJXTf5b7gaJpfibKI6HF8HxNDA5I+bLjNihKnYHBjn/+hNju76M2I8hAX2PvupNXuWPY52ERixdK5CR6nEkcelW4xx3uHInT376FHFiXHh2vu1kV6P6EryQhAAS8aX+VHSc76Z/9y4KAIs8AqP2hMRS3CTKoi7jgGqwVwHqk5BtDyC/X+qk6lCTHzOmR2yynkGmqVKTPX6mp4AXl9v7AlVrJHOIU/1gWBzLEnfOsS/5hG2JoI71mjdbMKvlxLozMydmH8ayANIRrSq23DnyR9RdNtvsN57b02/eV5Mx8YniAx/C0esDmzeiR5LqQ9G03S1q2rpSsIOz8nyYOnZpfFa5vE2Tv1yPXQFWa6EkPZ4/+looelLEtnXs2iQyEcYaSJecB2ErxWKZN02Bmu9FW9yXSHBAyBC1q1/qVS+qzcdviQZhOKtoZkVkTHsEwoC4+lg7kltAtgqmQfmv26Z+bFDbGqRaM0VMru7GmkhAmjzZTyH/Vz3vWXaw355y46zyZ/Sfhtq4a9bJ5GDjHHhNTuO7AIniahaaNZ/ybXukhP39uwlKOLxyZ3shC/TAQFg2eYW6i21RoTWh2xeIepQHx5IFoZZ5+9xH9S8oWk3hBxtCV3d88Hg1dPMZpTCS7JADSaD5inlnV9uhf/8yiFNrxiv0pBBFglrianozxIzX1ZLhvCf0Q9pV234G9Cq+t++nZlajhcj1y9iBQAn9FL1jX1L9QeoodHwvpP/pxf5L8PCBfDk4FIejAw3njS3hLFgSYZxqkAp6VDAFMb4GSow/9DdHfWSXyzQTEO09LHNLAOnm8RQ2crZ7Eoy/cPD8d7fCMxJq0QkxMAdOgAkymUK5R3T1N4XBHhhhTVBZEenLnaUe+oAZRhHhU5hZYcxh2w7vL7OezBjgiRVbIHDmWO5B/SJ2zZMdwREeWYfj+fCB+/Yjf1BpXLYje9y8fK8iITAHbfSA+OZlXsQCo1wcMOvAb+Kt8hpe0nheaPme1x1uJpwcfz2s+Kcsl6vis7K1SPWH4wMyj2Cit9kp/jVKdLuZz1s30w+Mrh/nffFSUhVEssGtmtrk90Xl2k9CmM02NfVr0beazbEq9sa3pZMwosoqzd2AjQKJnHl77fJMHT8nD7FsizFS1bEeEmrlkT80I2tgvgUJO42sLkBeTIIDSAG/M37snvWal3xLWwfUv2GoekdcONJUzHbE4ULpQOywmaHhbNWAgnICnYdmACN3ugselA9dPNpFc5p6G4fRlr7Euxs7Pj2p70naohNjk+bjvW2tFl4ydsCYxJhZ70sEYNy02epTN5YwMw8DkdL6bAUC0zJlyQW+TZ8Y4BiTN4WmCGMzS232UvcB03PHviyCEHw+Ad/+Hzqt6TY5oRDwXioLDRuUTZR0aQJu9h0iMVkxFyeSuOYRPF/CbMYvNeQKQIypdIOnhSbchdvrqBX9NVtGL2upL03qcS0WSufBKMAstj0aAlhsWcEeMav9IaOepIswSTi45rSfdI5w14ggsIfZ0wT+vTms6VR0iQJXn/Q4ZoaLQHvTbJDXKwlpvM7DuDI/Xq5mVoLB1ffRg01YrublixBzUcEvoLJTnHKlSDJQ4dWfbcEJDkdhiTP9txaS6UPpaB42qTI1LPAM2QDJAG7vLH9lVa+FOHl+NYBnooCOkWg4Fdo3xHompndFRHE4uNFK/RiJFycS2bLqs55CA6kOqEB6xYdiF4AH2DHvMf2Oa0V6hDa2anC53DXWlLZtrG0rBfY6iXr9l/w8vf7I1S2w1k96YHPSC1z9WDTpyKa8FMITq+BvkX0AzIpMNe/AX7NxlelPwRmSl85vkRqx8vHBAcuxNw3JPjNwpS3eeF6AseUOEWe6CpiDKW905UgWsGaJSl6kSuhrLlAaGXV4ZNNPkaNTNsat+AWfTbkaWzS7BMvGUr0nCGzgemZcXowAI9LLaeg5cLodXevQUmp4DyPXiMf6hCL8yPOGJykSA2APxeoN+o+ffDQ6s5LaMtvHt7pXi+eqqhl9umJt8VJcp2aH3cLdfGLdDovdEyvGTFdl/SXM6vqN2XJvNy1sac7nRoZ9QFXG/Gghxgb81gBzuFFLv4jtauam/8JnF9+r7N97rYzD/8Z58U4ND4sw7oq11vjjBdJLmS1sIFgg+e0woUvd539VVBcA/Z1p9QuSGu0erp8IrneGxVKHV8ETM5oMqC9ROwsz6gxQ9JLs10zGACU3yUIFmtwO8SWJbJGM0fx7Sl1wl6uLu6LsXbD6GMapxVnHv3jZIjgMTd7asKiZ+SZNO4HmROT5W5NEdLdvGLVV2izZf98RqVeA0lhd5DkT9OfYYjBEp91cQIHTRJiwgiGVUbAdF+QZx4GL4uhPm+9kJL5q9NTptePlngkgiKSr0PYGa8+Bj5sk9MGUHTfzwlPdwp1xHWQSJqCsxEdQMACslKSKoJ+5JCDcwhXqAFN/Nz1Cz2GSw2DE6InHvKNW6KzSPiP2/v7GVU8NOo7LUOGvIPNVENP8bFd2QPsE1gGT3G6oWhzDsZhYK+vcQqk+u5AhXRjwt4ns1ewAs0gEgmnjoZQyg1SCImQiYlhrO0CLuWYgO4zrnFs0P1DphvJpIlOzWKiUZ1bCjcuRKaeKV/Sg2BSG62zh8e6nIz42NIAMpVQOFpQ5KpeQOdPS8mOREOHIfTcxFN4XA1umjuYKQnKqwzJTNfcwjUjHZkBfSeDfFln+l0wCAvLN2cngQ1eRKR3gOg9z///h+G6wc9"""

EVENT_RE = re.compile(r"^on[A-Z]")


def _u1(data: bytes, off: int):
    return data[off], off + 1


def _u2(data: bytes, off: int):
    return struct.unpack_from(">H", data, off)[0], off + 2


def _u4(data: bytes, off: int):
    return struct.unpack_from(">I", data, off)[0], off + 4


def parse_class(data: bytes):
    off = 0
    magic, off = _u4(data, off)
    if magic != 0xCAFEBABE:
        raise ValueError("bad class magic")
    _minor, off = _u2(data, off)
    _major, off = _u2(data, off)
    cp_count, off = _u2(data, off)
    cp = [None] * cp_count
    i = 1
    while i < cp_count:
        tag, off = _u1(data, off)
        if tag == 1:
            n, off = _u2(data, off)
            cp[i] = ("Utf8", data[off:off+n].decode("utf-8", "replace"))
            off += n
        elif tag in (3, 4):
            cp[i] = (tag,)
            off += 4
        elif tag in (5, 6):
            cp[i] = (tag,)
            off += 8
            i += 1
        elif tag in (7, 8, 16, 19, 20):
            idx, off = _u2(data, off)
            cp[i] = (tag, idx)
        elif tag in (9, 10, 11, 12, 17, 18):
            a, off = _u2(data, off)
            b, off = _u2(data, off)
            cp[i] = (tag, a, b)
        elif tag == 15:
            off += 3
            cp[i] = (tag,)
        else:
            raise ValueError(f"unsupported constant-pool tag {tag}")
        i += 1

    def utf(idx: int):
        entry = cp[idx]
        return entry[1] if entry and entry[0] == "Utf8" else None

    def cls(idx: int):
        entry = cp[idx]
        return utf(entry[1]) if entry and entry[0] == 7 else None

    _access, off = _u2(data, off)
    this_idx, off = _u2(data, off)
    _super_idx, off = _u2(data, off)
    this_name = cls(this_idx)

    interfaces, off = _u2(data, off)
    off += 2 * interfaces

    fields, off = _u2(data, off)
    for _ in range(fields):
        off += 6
        attr_count, off = _u2(data, off)
        for __ in range(attr_count):
            _name_idx, off = _u2(data, off)
            length, off = _u4(data, off)
            off += length

    methods_count, off = _u2(data, off)
    methods = {}
    for _ in range(methods_count):
        access, off = _u2(data, off)
        name_idx, off = _u2(data, off)
        desc_idx, off = _u2(data, off)
        attr_count, off = _u2(data, off)
        name = utf(name_idx)
        desc = utf(desc_idx)
        code_length = None
        max_stack = None
        max_locals = None
        exceptions = 0
        for __ in range(attr_count):
            attr_name_idx, off = _u2(data, off)
            attr_len, off = _u4(data, off)
            attr_name = utf(attr_name_idx)
            start = off
            if attr_name == "Code":
                max_stack, off = _u2(data, off)
                max_locals, off = _u2(data, off)
                code_length, off = _u4(data, off)
                off += code_length
                exceptions, off = _u2(data, off)
                off += 8 * exceptions
                nested, off = _u2(data, off)
                for ___ in range(nested):
                    _nested_name, off = _u2(data, off)
                    nested_len, off = _u4(data, off)
                    off += nested_len
            else:
                off += attr_len
            if off != start + attr_len:
                raise ValueError(f"attribute parse drift {this_name}.{name}{desc}")
        methods[(name, desc)] = (access, code_length or 0, max_stack or 0, max_locals or 0, exceptions)
    return this_name, methods


def load_reference():
    raw = zlib.decompress(base64.b64decode(REFERENCE_B64))
    rows = json.loads(raw.decode("utf-8"))
    if len(rows) != 309:
        raise SystemExit(f"REFERENCE_METHOD_COUNT_BAD expected=309 actual={len(rows)}")
    return {
        (row[0], row[1], row[2]): {
            "old_code": row[3],
            "old_stack": row[4],
            "old_locals": row[5],
            "old_exceptions": row[6],
        }
        for row in rows
    }


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument("--top", type=int, default=60)
    args = parser.parse_args()

    reference = load_reference()
    current = {}

    with zipfile.ZipFile(args.jar) as zf:
        for name in zf.namelist():
            if not name.startswith("Abyss/module/impl/") or not name.endswith(".class") or "$" in name:
                continue
            class_name, methods = parse_class(zf.read(name))
            for (method_name, desc), metric in methods.items():
                key = (class_name, method_name, desc)
                if key in reference:
                    current[key] = metric

    missing = sorted(set(reference) - set(current))
    extra = sorted(set(current) - set(reference))
    failures = []
    print(f"MODULE_BODY_REFERENCE_METHODS={len(reference)}")
    print(f"MODULE_BODY_CURRENT_MATCHED={len(current)}")
    print(f"MODULE_BODY_MISSING={len(missing)}")
    print(f"MODULE_BODY_EXTRA={len(extra)}")
    for key in missing[:50]:
        print("MODULE_BODY_MISSING_ITEM=" + "|".join(key))
    if missing:
        failures.append("missing-authoritative-methods")

    rows = []
    tiny = []
    for key, old in reference.items():
        if key not in current:
            continue
        _access, new_code, new_stack, new_locals, new_exc = current[key]
        old_code = old["old_code"]
        ratio = (new_code / old_code) if old_code else 1.0
        item = (ratio, old_code, new_code, key, new_stack, new_locals, new_exc)
        if old_code >= 40:
            rows.append(item)
        if old_code >= 40 and new_code <= 8:
            tiny.append(item)
        if old["old_exceptions"] > 0 and new_exc == 0:
            failures.append("exception-region-lost:" + "|".join(key))
        if old_code >= 80 and ratio < 0.75:
            # FastPlace's runnable-era bed scan was intentionally extracted into
            # findBedInRange(int). Validate that moved body separately below.
            if key != (
                "Abyss/module/impl/world/FastPlace",
                "onPreUpdate",
                "(LAbyss/event/events/PreUpdateEvent;J)V",
            ):
                failures.append(
                    f"major-handler-collapse:{'|'.join(key)}:old={old_code}:new={new_code}"
                )

    rows.sort(key=lambda x: (x[0], -x[1], x[3]))
    print(f"MODULE_BODY_OLD_GE40={len(rows)}")
    print(f"MODULE_BODY_SUSPICIOUS_TINY={len(tiny)}")
    for ratio, old_code, new_code, key, stack, locals_, exc in rows[:args.top]:
        print(
            "MODULE_BODY_SHRINK "
            f"ratio={ratio:.4f} old={old_code} new={new_code} "
            f"stack={stack} locals={locals_} exceptions={exc} "
            f"method={'|'.join(key)}"
        )

    if tiny:
        for _ratio, old_code, new_code, key, _stack, _locals, _exc in tiny:
            failures.append(
                f"tiny-handler-collapse:{'|'.join(key)}:old={old_code}:new={new_code}"
            )

    # FastPlace special case: the runnable body inlined the bed scan. Recovery
    # deliberately extracted it to a private helper. Require that helper to be
    # substantial and exception-protected, and require the combined body to stay
    # close to the old inline body rather than merely exempting the shrink.
    fast_key = (
        "Abyss/module/impl/world/FastPlace",
        "onPreUpdate",
        "(LAbyss/event/events/PreUpdateEvent;J)V",
    )
    fast_helper_key = ("findBedInRange", "(I)Lnet/minecraft/util/BlockPos;")
    fast_class = "Abyss/module/impl/world/FastPlace"
    helper_metric = None
    try:
        with zipfile.ZipFile(args.jar) as zf:
            _name, fast_methods = parse_class(zf.read(fast_class + ".class"))
            helper_metric = fast_methods.get(fast_helper_key)
    except Exception as exc:
        failures.append("fastplace-helper-read:" + str(exc))

    if helper_metric is None:
        failures.append("fastplace-helper-missing")
    else:
        _access, helper_code, _stack, _locals, helper_exc = helper_metric
        fast_old = reference[fast_key]["old_code"]
        fast_new = current[fast_key][1] if fast_key in current else 0
        combined = fast_new + helper_code
        print(
            "FASTPLACE_EXTRACTED_HELPER "
            f"old_inline={fast_old} new_handler={fast_new} helper={helper_code} "
            f"combined={combined} helper_exceptions={helper_exc}"
        )
        if helper_code < 120:
            failures.append(f"fastplace-helper-too-small:{helper_code}")
        if helper_exc < 1:
            failures.append("fastplace-helper-exception-region-missing")
        if combined < int(fast_old * 0.90):
            failures.append(
                f"fastplace-extracted-body-too-small:old={fast_old}:combined={combined}"
            )

    print(f"MODULE_BODY_HARD_FAILURES={len(failures)}")
    for failure in failures:
        print("MODULE_BODY_HARD_FAILURE=" + failure)

    if failures:
        print("MODULE_BODY_DIFFERENTIAL_GATE=FAIL")
        return 1

    print("MODULE_BODY_DIFFERENTIAL_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
