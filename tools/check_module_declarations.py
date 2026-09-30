#!/usr/bin/env python3
from __future__ import annotations

import argparse
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
CATEGORY_RE = re.compile(
    r"Field Abyss/module/Category\.([\w$]+):LAbyss/module/Category;"
)


def load_reference(path: pathlib.Path):
    expected = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        sha256, class_name = line.split(" ", 1)
        expected[class_name] = sha256.lower()
    if len(expected) != 113:
        raise SystemExit(
            f"REFERENCE_MODULE_DECL_COUNT_BAD expected=113 actual={len(expected)}"
        )
    return expected


def int_constant(opcode: str, argument: str, comment: str):
    if opcode.startswith("iconst_"):
        value = opcode[len("iconst_"):]
        return -1 if value == "m1" else int(value)
    if opcode in ("bipush", "sipush"):
        return int(argument.strip())
    if opcode in ("ldc", "ldc_w") and comment.startswith("int "):
        return int(comment[4:])
    return None


def digest(item):
    payload = "\t".join(
        [
            item["descriptor"],
            item["name"],
            item["category"],
            item["description"],
            "" if item["bind"] is None else str(item["bind"]),
        ]
    ) + "\n"
    return hashlib.sha256(payload.encode("utf-8")).hexdigest()


def parse_declarations(jar: pathlib.Path):
    with zipfile.ZipFile(jar) as zf:
        classes = sorted(
            name[:-6].replace("/", ".")
            for name in zf.namelist()
            if name.startswith("Abyss/module/impl/")
            and name.endswith(".class")
            and "$" not in name
        )

    result = {}

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
        current_simple = None
        in_constructor = False
        descriptor = None
        instructions = []

        def finish_constructor():
            nonlocal descriptor, instructions
            if (
                not current_class
                or not in_constructor
                or descriptor is None
                or not instructions
            ):
                descriptor = None
                instructions = []
                return

            declare_index = None
            for index, (_op, _arg, comment) in enumerate(instructions):
                if (
                    "Method declare:" in comment
                    or "Method Abyss/module/Module.declare:" in comment
                ):
                    declare_index = index
                    break

            if declare_index is None:
                descriptor = None
                instructions = []
                return

            super_index = -1
            for index in range(declare_index - 1, -1, -1):
                if 'Method Abyss/module/Module."<init>"' in instructions[index][2]:
                    super_index = index
                    break

            declaration_window = instructions[super_index + 1:declare_index]
            strings = [
                comment[7:]
                for opcode, _argument, comment in declaration_window
                if opcode in ("ldc", "ldc_w") and comment.startswith("String ")
            ]
            categories = []
            for _opcode, _argument, comment in declaration_window:
                category_match = CATEGORY_RE.search(comment)
                if category_match:
                    categories.append(category_match.group(1))

            if len(strings) < 2 or len(categories) != 1:
                raise SystemExit(
                    "MODULE_DECL_PARSE_BAD "
                    + current_class
                    + " descriptor="
                    + descriptor
                    + " strings="
                    + repr(strings)
                    + " categories="
                    + repr(categories)
                )

            bind = None
            for index in range(declare_index + 1, len(instructions)):
                _opcode, _argument, comment = instructions[index]
                if (
                    "Method z:(JI)V" not in comment
                    and "Method Abyss/module/Module.z:(JI)V" not in comment
                ):
                    continue
                for back in range(index - 1, max(declare_index, index - 8), -1):
                    opcode, argument, prior_comment = instructions[back]
                    value = int_constant(opcode, argument, prior_comment)
                    if value is not None:
                        bind = value
                        break
                break

            if current_class in result:
                raise SystemExit(
                    "MODULE_DECL_MULTIPLE_CONSTRUCTORS=" + current_class
                )

            result[current_class] = {
                "descriptor": descriptor,
                "name": strings[0],
                "category": categories[0],
                "description": strings[1],
                "bind": bind,
            }

            descriptor = None
            instructions = []

        for line in proc.stdout.splitlines():
            class_match = CLASS_RE.match(line)
            if class_match:
                finish_constructor()
                current_class = class_match.group(1).replace(".", "/")
                current_simple = current_class.rsplit("/", 1)[-1]
                in_constructor = False
                descriptor = None
                instructions = []
                continue

            if (
                current_class
                and line.startswith("  ")
                and not line.startswith("    ")
                and line.strip().endswith(";")
            ):
                finish_constructor()
                constructor_pattern = (
                    r"^  (?:public|protected|private) .*?"
                    + re.escape(current_simple)
                    + r"\("
                )
                in_constructor = re.match(constructor_pattern, line) is not None
                descriptor = None
                instructions = []
                continue

            if in_constructor and line.strip().startswith("descriptor: "):
                descriptor = line.strip()[12:]
                continue

            if in_constructor:
                insn = INSN_RE.match(line)
                if insn:
                    _offset, opcode, argument, comment = insn.groups()
                    instructions.append(
                        (opcode, (argument or "").strip(), comment or "")
                    )

        finish_constructor()

    return result


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-module-declarations.txt",
    )
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    expected = load_reference(pathlib.Path(args.reference))
    current = parse_declarations(jar)

    missing = sorted(set(expected) - set(current))
    extra = sorted(set(current) - set(expected))
    mismatches = []

    for class_name in sorted(set(expected) & set(current)):
        got = digest(current[class_name])
        if got != expected[class_name]:
            mismatches.append((class_name, expected[class_name], got, current[class_name]))

    print(f"MODULE_DECL_REFERENCE={len(expected)}")
    print(f"MODULE_DECL_CURRENT={len(current)}")
    print(f"MODULE_DECL_MISSING={len(missing)}")
    print(f"MODULE_DECL_EXTRA={len(extra)}")
    print(f"MODULE_DECL_MISMATCHED={len(mismatches)}")

    for class_name in missing:
        print("MODULE_DECL_MISSING_ITEM=" + class_name)
    for class_name in extra:
        print("MODULE_DECL_EXTRA_ITEM=" + class_name)
    for class_name, want, got, item in mismatches[:50]:
        print(
            "MODULE_DECL_BAD="
            + class_name
            + " expected="
            + want
            + " actual="
            + got
            + " current="
            + repr(item)
        )

    if missing or extra or mismatches:
        print("MODULE_DECL_GATE=FAIL")
        return 1

    clickgui = current.get("Abyss/module/impl/configuration/ClickGUI")
    if not clickgui or clickgui["bind"] != 54:
        print("MODULE_DECL_GATE=FAIL clickgui-bind")
        return 1

    print("MODULE_DECL_CLICKGUI_BIND=54")
    print("MODULE_DECL_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
