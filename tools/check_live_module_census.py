#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import pathlib
import re
import subprocess
import sys


CLASS_RE = re.compile(
    r"^(?:(?:public|protected|private|final|abstract|static)\s+)*"
    r"(?:class|interface|enum)\s+([\w.$]+)"
)
PUT_RE = re.compile(
    r"putstatic\s+#\d+\s+// Field "
    r"(?:(?P<owner>[\w/$]+)\.)?"
    r"(?P<field>[\w$]+):(?P<desc>LAbyss/setting/settings/\w+;)"
)
STORAGE_ESP = "Abyss.module.impl.visual_utility.StorageESP"


def load_reference(path: pathlib.Path) -> dict[str, str]:
    expected = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        sha256, class_name = line.split(" ", 1)
        expected[class_name] = sha256.lower()
    if len(expected) != 112:
        raise SystemExit(
            f"LIVE_CENSUS_REFERENCE_COUNT_BAD expected=112 actual={len(expected)}"
        )
    if STORAGE_ESP in expected:
        raise SystemExit("LIVE_CENSUS_REFERENCE_STORAGEESP_PRESENT")
    return expected


def load_census(path: pathlib.Path):
    rows = {}
    settings_total = 0
    for raw in path.read_text(encoding="utf-8-sig").splitlines():
        if not raw.strip():
            continue
        parts = raw.split("\t")
        if len(parts) != 4:
            raise SystemExit("LIVE_CENSUS_BAD_ROW=" + raw)
        class_name, module_name, category, count_s = parts
        if class_name in rows:
            raise SystemExit("LIVE_CENSUS_DUPLICATE_CLASS=" + class_name)
        count = int(count_s)
        rows[class_name] = (module_name, category, count)
        settings_total += count
    return rows, settings_total


def initialized_setting_fields(jar: pathlib.Path, class_names: list[str]):
    result: dict[str, set[str]] = {name: set() for name in class_names}

    dotted = [name for name in class_names]
    for start in range(0, len(dotted), 24):
        batch = dotted[start:start + 24]
        proc = subprocess.run(
            ["javap", "-classpath", str(jar), "-c", "-p"] + batch,
            check=False,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
        )
        if proc.returncode != 0:
            sys.stderr.write(proc.stderr)
            raise SystemExit(f"JAVAP_FAILED exit={proc.returncode}")

        current = None
        in_clinit = False
        for line in proc.stdout.splitlines():
            class_match = CLASS_RE.match(line)
            if class_match:
                current = class_match.group(1)
                in_clinit = False
                continue

            if line.strip() == "static {};":
                in_clinit = True
                continue

            if (
                current
                and line.startswith("  ")
                and not line.startswith("    ")
                and line.strip() != "static {};"
            ):
                in_clinit = False

            if not in_clinit or current not in result:
                continue

            match = PUT_RE.search(line)
            if match:
                result[current].add(match.group("field"))

    return result


def semantic_hash(module_name: str, category: str, count: int, fields: set[str]) -> str:
    payload = (
        module_name
        + "\t"
        + category
        + "\t"
        + str(count)
        + "\t"
        + ",".join(sorted(fields))
        + "\n"
    )
    return hashlib.sha256(payload.encode("utf-8")).hexdigest()


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--jar", required=True)
    parser.add_argument("--census", required=True)
    parser.add_argument(
        "--reference",
        default="tools/reference-live-module-census.txt",
    )
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    census = pathlib.Path(args.census)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")
    if not census.is_file():
        raise SystemExit(f"CENSUS_MISSING {census}")

    expected = load_reference(pathlib.Path(args.reference))
    rows, settings_total = load_census(census)

    failures = []
    if len(rows) != 112:
        failures.append(f"row-count:expected=112:actual={len(rows)}")
    if settings_total != 812:
        failures.append(f"setting-total:expected=812:actual={settings_total}")
    if STORAGE_ESP in rows:
        failures.append("storageesp-live")

    missing = sorted(set(expected) - set(rows))
    extra = sorted(set(rows) - set(expected))
    for name in missing:
        failures.append("missing:" + name)
    for name in extra:
        failures.append("extra:" + name)

    common = sorted(set(expected) & set(rows))
    fields = initialized_setting_fields(jar, common)

    mismatches = []
    for class_name in common:
        module_name, category, count = rows[class_name]
        got_fields = fields[class_name]
        if len(got_fields) != count:
            failures.append(
                f"setting-field-count:{class_name}:"
                f"census={count}:initialized-fields={len(got_fields)}:"
                f"fields={','.join(sorted(got_fields))}"
            )
            continue
        got = semantic_hash(module_name, category, count, got_fields)
        want = expected[class_name]
        if got != want:
            mismatches.append((class_name, want, got, module_name, category, count, got_fields))
            failures.append("semantic:" + class_name)

    print("LIVE_CENSUS_REFERENCE_MODULES=112")
    print(f"LIVE_CENSUS_CURRENT_MODULES={len(rows)}")
    print(f"LIVE_CENSUS_CURRENT_SETTINGS={settings_total}")
    print(f"LIVE_CENSUS_MISSING={len(missing)}")
    print(f"LIVE_CENSUS_EXTRA={len(extra)}")
    print(f"LIVE_CENSUS_SEMANTIC_MISMATCHES={len(mismatches)}")
    print(f"LIVE_CENSUS_FAILURES={len(failures)}")

    for item in mismatches[:50]:
        class_name, want, got, module_name, category, count, got_fields = item
        print(
            "LIVE_CENSUS_BAD="
            + class_name
            + " expected="
            + want
            + " actual="
            + got
            + " name="
            + module_name
            + " category="
            + category
            + " settings="
            + str(count)
            + " fields="
            + ",".join(sorted(got_fields))
        )
    for failure in failures[:100]:
        print("LIVE_CENSUS_FAILURE=" + failure)

    if failures:
        print("LIVE_CENSUS_GATE=FAIL")
        return 1

    print("LIVE_CENSUS_STORAGEESP_EXCLUDED=TRUE")
    print("LIVE_CENSUS_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
