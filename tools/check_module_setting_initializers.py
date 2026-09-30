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
DESC_RE = re.compile(r"^    descriptor: (.+)$")
INSN_RE = re.compile(
    r"^\s+\d+:\s+(\w+)\s+.*?//\s+"
    r"(Field|Method|InterfaceMethod|class)\s+(.+)$"
)

CLICKGUI = "Abyss/module/impl/configuration/ClickGUI"
ALLOWED_CLICKGUI_ADDITIONS = {
    (
        "invokespecial",
        "Method",
        "Abyss/setting/settings/ModeSetting",
        '"<init>"',
        "(Ljava/lang/String;ZLjava/lang/String;[Ljava/lang/String;)V",
    ),
    (
        "invokespecial",
        "Method",
        "Abyss/setting/settings/TextSetting",
        '"<init>"',
        "(Ljava/lang/String;Ljava/lang/String;)V",
    ),
    (
        "putstatic",
        "Field",
        "<self>",
        "keybind",
        "LAbyss/setting/settings/TextSetting;",
    ),
    (
        "putstatic",
        "Field",
        "<self>",
        "mode",
        "LAbyss/setting/settings/ModeSetting;",
    ),
}


def load_reference(path: pathlib.Path):
    expected = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        count_s, sha256, class_name = line.split(" ", 2)
        expected[class_name] = (int(count_s), sha256.lower())
    if len(expected) != 106:
        raise SystemExit(
            f"REFERENCE_SETTING_CLINIT_CLASS_COUNT_BAD expected=106 actual={len(expected)}"
        )
    return expected


def normalize(opcode: str, kind: str, target: str):
    if kind == "class":
        return ("class", target)

    if ":" not in target:
        return (opcode, kind, target)

    head, descriptor = target.rsplit(":", 1)
    if "." in head:
        owner, member = head.rsplit(".", 1)
    else:
        owner, member = "<self>", head
    return (opcode, kind, owner, member, descriptor)


def digest(refs):
    ordered = sorted(refs, key=str)
    payload = "".join(
        "\t".join(map(str, ref)) + "\n"
        for ref in ordered
    ).encode("utf-8")
    return len(ordered), hashlib.sha256(payload).hexdigest()


def parse_clinit_setting_refs(jar: pathlib.Path, classes):
    dotted = [name.replace("/", ".") for name in classes]
    command = ["javap", "-classpath", str(jar), "-c", "-p", "-s"] + dotted
    proc = subprocess.run(
        command,
        check=False,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    if proc.returncode != 0:
        sys.stderr.write(proc.stderr)
        raise SystemExit(f"JAVAP_FAILED exit={proc.returncode}")

    result = {}
    current_class = None
    in_clinit = False
    descriptor_seen = False

    for line in proc.stdout.splitlines():
        class_match = CLASS_RE.match(line)
        if class_match:
            current_class = class_match.group(1).replace(".", "/")
            in_clinit = False
            descriptor_seen = False
            continue

        if current_class and line.strip() == "static {};":
            in_clinit = True
            descriptor_seen = False
            result.setdefault(current_class, set())
            continue

        if (
            current_class
            and line.startswith("  ")
            and not line.startswith("    ")
            and line.strip() != "static {};"
        ):
            in_clinit = False
            descriptor_seen = False

        if not in_clinit:
            continue

        desc_match = DESC_RE.match(line)
        if desc_match:
            descriptor_seen = True
            continue

        insn_match = INSN_RE.match(line)
        if not insn_match or not descriptor_seen:
            continue

        ref = normalize(*insn_match.groups())
        owner = ref[2] if len(ref) > 2 else ""
        descriptor = ref[-1] if len(ref) >= 5 else ""

        if owner.startswith("Abyss/setting/") or "LAbyss/setting/" in descriptor:
            result[current_class].add(ref)

    return result


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-module-setting-init-deps.txt",
    )
    args = parser.parse_args()

    expected = load_reference(pathlib.Path(args.reference))
    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    actual = parse_clinit_setting_refs(jar, sorted(expected))
    failures = []

    missing_clinit = sorted(set(expected) - set(actual))
    extra_clinit = sorted(set(actual) - set(expected))

    for class_name in missing_clinit:
        failures.append("missing-clinit:" + class_name)
    for class_name in extra_clinit:
        failures.append("extra-clinit:" + class_name)

    for class_name in sorted(set(expected) & set(actual)):
        refs = set(actual[class_name])
        if class_name == CLICKGUI:
            additions_seen = refs & ALLOWED_CLICKGUI_ADDITIONS
            if additions_seen != ALLOWED_CLICKGUI_ADDITIONS:
                failures.append(
                    "clickgui-additions:"
                    + str(sorted(ALLOWED_CLICKGUI_ADDITIONS - additions_seen, key=str))
                )
            refs -= ALLOWED_CLICKGUI_ADDITIONS

        got = digest(refs)
        if got != expected[class_name]:
            failures.append(
                f"dependency-set:{class_name}:"
                f"expected={expected[class_name][0]}/{expected[class_name][1]}:"
                f"actual={got[0]}/{got[1]}"
            )

    print(f"MODULE_SETTING_INIT_REFERENCE_CLASSES={len(expected)}")
    print(f"MODULE_SETTING_INIT_CURRENT_CLASSES={len(actual)}")
    print(f"MODULE_SETTING_INIT_MISSING_CLINIT={len(missing_clinit)}")
    print(f"MODULE_SETTING_INIT_EXTRA_CLINIT={len(extra_clinit)}")
    print(
        "MODULE_SETTING_INIT_ALLOWED_CLICKGUI_ADDITIONS="
        + str(len(ALLOWED_CLICKGUI_ADDITIONS))
    )
    print(f"MODULE_SETTING_INIT_FAILURES={len(failures)}")

    for failure in failures:
        print("MODULE_SETTING_INIT_FAILURE=" + failure)

    if failures:
        print("MODULE_SETTING_INIT_GATE=FAIL")
        return 1

    print("MODULE_SETTING_INIT_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
