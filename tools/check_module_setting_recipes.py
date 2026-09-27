#!/usr/bin/env python3
from __future__ import annotations

import argparse
import collections
import hashlib
import pathlib
import re
import subprocess
import sys
import zipfile


CLASS_RE = re.compile(
    r"^(?:(?:public|protected|private|final|abstract|static)\s+)*"
    r"(?:class|interface|enum)\s+([\w.$]+)"
)
INSN_RE = re.compile(
    r"^\s+(\d+):\s+(\w+)(?:\s+([^/]*?))?(?:\s+//\s+(.*))?$"
)
SETTING_NEW_RE = re.compile(r"class (Abyss/setting/settings/\w+)")
PUT_RE = re.compile(
    r"Field (?:(?P<owner>[\w/$]+)\.)?"
    r"(?P<field>[\w$]+):(?P<desc>LAbyss/setting/settings/\w+;)"
)
CTOR_RE = re.compile(
    r'Method (Abyss/setting/settings/\w+)\."<init>":(\(.+\)V)'
)

CLICKGUI = "Abyss/module/impl/configuration/ClickGUI"
ALLOWED_CLICKGUI_ADDITIONS = {"mode", "keybind"}


def load_reference(path: pathlib.Path):
    expected = {}
    total = 0
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        count_s, sha256, class_name = line.split(" ", 2)
        count = int(count_s)
        expected[class_name] = (count, sha256.lower())
        total += count
    if len(expected) != 100:
        raise SystemExit(
            f"REFERENCE_SETTING_RECIPE_CLASS_COUNT_BAD expected=100 actual={len(expected)}"
        )
    if total != 812:
        raise SystemExit(
            f"REFERENCE_SETTING_RECIPE_COUNT_BAD expected=812 actual={total}"
        )
    return expected


def constant_token(opcode: str, argument: str, comment: str):
    if opcode.startswith("iconst_"):
        value = opcode[len("iconst_"):]
        return "I:" + ("-1" if value == "m1" else value)
    if opcode in ("bipush", "sipush"):
        return "I:" + argument.strip()
    if opcode.startswith("fconst_"):
        return "F:" + opcode[-1] + ".0"
    if opcode.startswith("dconst_"):
        return "D:" + opcode[-1] + ".0"
    if opcode.startswith("lconst_"):
        return "J:" + opcode[-1]
    if opcode in ("ldc", "ldc_w", "ldc2_w") and comment:
        if comment.startswith("String "):
            return "S:" + comment[7:]
        if comment.startswith("float "):
            return "F:" + comment[6:].rstrip("f")
        if comment.startswith("double "):
            return "D:" + comment[7:].rstrip("d")
        if comment.startswith("long "):
            return "J:" + comment[5:].rstrip("lL")
        if comment.startswith("int "):
            return "I:" + comment[4:]
        return "LDC:" + comment
    if opcode == "aconst_null":
        return "NULL"
    return None


def recipe_digest(recipes):
    rows = []
    for field_name, descriptor, tokens in recipes:
        rows.append(field_name + "|" + descriptor + "|" + "\x1f".join(tokens))
    rows.sort()
    payload = ("\n".join(rows) + ("\n" if rows else "")).encode("utf-8")
    return len(rows), hashlib.sha256(payload).hexdigest()


def parse_setting_recipes(jar: pathlib.Path):
    with zipfile.ZipFile(jar) as zf:
        classes = sorted(
            name[:-6].replace("/", ".")
            for name in zf.namelist()
            if name.startswith("Abyss/module/impl/")
            and name.endswith(".class")
            and "$" not in name
        )

    recipes_by_class = collections.defaultdict(list)

    for start in range(0, len(classes), 30):
        batch = classes[start:start + 30]
        proc = subprocess.run(
            ["javap", "-classpath", str(jar), "-c", "-p", "-s"] + batch,
            check=False,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
        )
        if proc.returncode != 0:
            sys.stderr.write(proc.stderr)
            raise SystemExit(f"JAVAP_FAILED exit={proc.returncode}")

        current_class = None
        in_clinit = False
        active = None

        for line in proc.stdout.splitlines():
            class_match = CLASS_RE.match(line)
            if class_match:
                current_class = class_match.group(1).replace(".", "/")
                in_clinit = False
                active = None
                continue

            if line.strip() == "static {};":
                in_clinit = True
                active = None
                continue

            if (
                current_class
                and line.startswith("  ")
                and not line.startswith("    ")
                and line.strip() != "static {};"
            ):
                in_clinit = False
                active = None

            if not in_clinit:
                continue

            insn = INSN_RE.match(line)
            if not insn:
                continue

            _offset, opcode, argument, comment = insn.groups()
            argument = (argument or "").strip()
            comment = comment or ""

            if opcode == "new" and comment:
                setting_match = SETTING_NEW_RE.search(comment)
                if setting_match:
                    setting_class = setting_match.group(1)
                    active = {
                        "setting": setting_class,
                        "tokens": ["NEW:" + setting_class],
                        "ctor": None,
                    }
                    continue

            if active is None:
                continue

            token = constant_token(opcode, argument, comment)
            if token is not None:
                active["tokens"].append(token)
                continue

            if opcode == "anewarray" and comment.startswith("class "):
                active["tokens"].append("ANEW:" + comment[6:])
                continue

            if opcode in ("newarray", "multianewarray"):
                active["tokens"].append(opcode.upper() + ":" + (comment or argument))
                continue

            if opcode in (
                "aastore", "iastore", "fastore", "dastore",
                "lastore", "bastore", "castore", "sastore"
            ):
                active["tokens"].append(opcode.upper())
                continue

            if opcode in (
                "getstatic", "getfield", "invokestatic",
                "invokevirtual", "invokeinterface"
            ) and comment:
                active["tokens"].append(opcode.upper() + ":" + comment)
                continue

            if opcode == "invokespecial" and comment:
                ctor_match = CTOR_RE.search(comment)
                if ctor_match and ctor_match.group(1) == active["setting"]:
                    ctor = ctor_match.group(1) + ctor_match.group(2)
                    active["ctor"] = ctor
                    active["tokens"].append("CTOR:" + ctor)
                else:
                    active["tokens"].append("INVOKESPECIAL:" + comment)
                continue

            if opcode == "putstatic" and comment:
                put_match = PUT_RE.search(comment)
                if put_match and put_match.group("desc").startswith(
                    "LAbyss/setting/settings/"
                ):
                    if active["ctor"] is None:
                        raise SystemExit(
                            "SETTING_RECIPE_NO_CTOR "
                            + current_class + "|" + put_match.group("field")
                        )
                    recipes_by_class[current_class].append(
                        (
                            put_match.group("field"),
                            put_match.group("desc"),
                            tuple(active["tokens"]),
                        )
                    )
                    active = None

    return recipes_by_class


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-module-setting-recipes.txt",
    )
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    expected = load_reference(pathlib.Path(args.reference))
    current = parse_setting_recipes(jar)

    failures = []
    current_reference_recipes = 0

    missing_classes = sorted(set(expected) - set(current))
    unexpected_classes = sorted(set(current) - set(expected))
    for class_name in missing_classes:
        failures.append("missing-class:" + class_name)
    for class_name in unexpected_classes:
        failures.append("unexpected-class:" + class_name)

    clickgui_additions = []
    if CLICKGUI in current:
        retained = []
        for recipe in current[CLICKGUI]:
            if recipe[0] in ALLOWED_CLICKGUI_ADDITIONS:
                clickgui_additions.append(recipe)
            else:
                retained.append(recipe)
        current[CLICKGUI] = retained

    addition_names = {recipe[0] for recipe in clickgui_additions}
    if addition_names != ALLOWED_CLICKGUI_ADDITIONS:
        failures.append(
            "clickgui-additions:"
            + "expected=" + repr(sorted(ALLOWED_CLICKGUI_ADDITIONS))
            + ":actual=" + repr(sorted(addition_names))
        )

    mismatches = []
    for class_name in sorted(set(expected) & set(current)):
        got = recipe_digest(current[class_name])
        current_reference_recipes += got[0]
        if got != expected[class_name]:
            mismatches.append((class_name, expected[class_name], got))
            failures.append(
                f"recipe:{class_name}:"
                f"expected={expected[class_name][0]}/{expected[class_name][1]}:"
                f"actual={got[0]}/{got[1]}"
            )

    total_current = sum(len(items) for items in current.values()) + len(clickgui_additions)

    print(f"MODULE_SETTING_RECIPE_REFERENCE_CLASSES={len(expected)}")
    print("MODULE_SETTING_RECIPE_REFERENCE_RECIPES=812")
    print(f"MODULE_SETTING_RECIPE_CURRENT_TOTAL={total_current}")
    print(f"MODULE_SETTING_RECIPE_CURRENT_REFERENCE={current_reference_recipes}")
    print(
        "MODULE_SETTING_RECIPE_ALLOWED_CLICKGUI_ADDITIONS="
        + str(len(clickgui_additions))
    )
    print(f"MODULE_SETTING_RECIPE_MISSING_CLASSES={len(missing_classes)}")
    print(f"MODULE_SETTING_RECIPE_UNEXPECTED_CLASSES={len(unexpected_classes)}")
    print(f"MODULE_SETTING_RECIPE_MISMATCHED_CLASSES={len(mismatches)}")
    print(f"MODULE_SETTING_RECIPE_FAILURES={len(failures)}")

    for class_name, want, got in mismatches[:50]:
        print(
            "MODULE_SETTING_RECIPE_BAD="
            f"{class_name} expected={want[0]}/{want[1]} actual={got[0]}/{got[1]}"
        )
    for failure in failures[:100]:
        print("MODULE_SETTING_RECIPE_FAILURE=" + failure)

    if (
        failures
        or current_reference_recipes != 812
        or total_current != 814
    ):
        print("MODULE_SETTING_RECIPE_GATE=FAIL")
        return 1

    print("MODULE_SETTING_RECIPE_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
