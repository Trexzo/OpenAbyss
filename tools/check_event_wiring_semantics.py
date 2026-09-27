#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import pathlib
import re
import subprocess
import sys
import zipfile


CLASS_RE = re.compile(r"public final class ([\w.$]+)")
EVENTBUS_CALL = "Abyss/event/EventBus.R:"
INVOKER_CLASS_RE = re.compile(
    r"^new\s+#\d+\s+// class (Abyss/event/invoker/[\w$]+)"
)
LDC_CLASS_RE = re.compile(
    r"^ldc(?:_w)?\s+#\d+\s+// class ([\w/$]+)"
)
CHECKCAST_RE = re.compile(
    r"checkcast\s+#\d+\s+// class ([\w/$]+)"
)
METHOD_RE = re.compile(
    r"// (?:Method|InterfaceMethod) "
    r"([\w/$]+)\.\"?([\w$<>]+)\"?:(\S+)"
)
LONG_RE = re.compile(r"// long (-?\d+)l")


def class_names(jar: pathlib.Path, prefix: str) -> list[str]:
    with zipfile.ZipFile(jar) as zf:
        return [
            entry[:-6].replace("/", ".")
            for entry in sorted(zf.namelist())
            if entry.startswith(prefix)
            and entry.endswith(".class")
            and "$" not in entry
        ]


def javap(jar: pathlib.Path, names: list[str]) -> str:
    command = [
        "javap",
        "-classpath",
        str(jar),
        "-p",
        "-c",
        "-s",
        *names,
    ]
    return subprocess.check_output(
        command,
        text=True,
        stderr=subprocess.DEVNULL,
    )


def method_sections(text: str):
    result: dict[str, dict[str, list[str]]] = {}
    current_class = None
    current_method = None
    code: list[str] = []
    in_code = False

    for raw in text.splitlines():
        line = raw.strip()

        match = CLASS_RE.match(line)
        if match:
            if current_class and current_method:
                result.setdefault(current_class, {})[current_method] = code
            current_class = match.group(1).replace(".", "/")
            current_method = None
            code = []
            in_code = False
            continue

        if current_class is None:
            continue

        if line.endswith(";") and "(" in line:
            if current_method:
                result.setdefault(current_class, {})[current_method] = code
            current_method = line.split("(", 1)[0].split()[-1].split(".")[-1]
            code = []
            in_code = False
            continue

        if current_method and line == "Code:":
            in_code = True
            continue

        if current_method and in_code and re.match(r"^\d+:", line):
            code.append(re.sub(r"^\d+:\s*", "", line))

    if current_class and current_method:
        result.setdefault(current_class, {})[current_method] = code

    return result


def pushed_int(line: str):
    opcode = line.split()[0]
    if opcode.startswith("iconst_"):
        value = opcode[len("iconst_"):]
        return -1 if value == "m1" else int(value)
    if opcode in ("bipush", "sipush"):
        return int(line.split()[1])

    match = re.search(r"// int (-?\d+)", line)
    return int(match.group(1)) if match else None


def binder_rows(jar: pathlib.Path):
    names = class_names(jar, "Abyss/event/binder/")
    sections = method_sections(javap(jar, names))
    rows = []

    for class_name in sorted(sections):
        for method_name, code in sections[class_name].items():
            for index, line in enumerate(code):
                if EVENTBUS_CALL not in line:
                    continue

                window = code[max(0, index - 12):index]
                invoker = None
                new_index = None
                for pos, previous in enumerate(window):
                    match = INVOKER_CLASS_RE.search(previous)
                    if match:
                        invoker = match.group(1)
                        new_index = pos

                if invoker is None or new_index is None:
                    raise RuntimeError(
                        f"binder invoker parse failure: {class_name}.{method_name}"
                    )

                priority = None
                priority_index = None
                for pos in range(new_index - 1, -1, -1):
                    value = pushed_int(window[pos])
                    if value is not None:
                        priority = value
                        priority_index = pos
                        break

                if priority is None or priority_index is None:
                    raise RuntimeError(
                        f"binder priority parse failure: {class_name}.{method_name}"
                    )

                event_class = None
                for pos in range(priority_index - 1, -1, -1):
                    match = LDC_CLASS_RE.search(window[pos])
                    if match:
                        event_class = match.group(1)
                        break

                if event_class is None:
                    raise RuntimeError(
                        f"binder event parse failure: {class_name}.{method_name}"
                    )

                rows.append(
                    (class_name, event_class, priority, invoker)
                )

    return sorted(rows)


def invoker_rows(jar: pathlib.Path):
    names = class_names(jar, "Abyss/event/invoker/")
    sections = method_sections(javap(jar, names))
    rows = []

    for class_name in sorted(sections):
        code = sections[class_name].get("c", [])
        event_class = None
        targets = []
        constants = []

        for line in code:
            match = CHECKCAST_RE.search(line)
            if match:
                cast = match.group(1)
                if cast.startswith("Abyss/event/events/") or cast.startswith(
                    "java/awt/event/"
                ):
                    event_class = cast

            match = METHOD_RE.search(line)
            if match:
                owner, name, desc = match.groups()
                if name != "<init>" and not owner.startswith("java/lang/Object"):
                    targets.append((owner, name, desc))

            match = LONG_RE.search(line)
            if match:
                constants.append(int(match.group(1)))

        if not targets:
            raise RuntimeError(
                f"invoker callback parse failure: {class_name}"
            )

        rows.append(
            (
                class_name,
                event_class,
                tuple(targets),
                tuple(constants),
            )
        )

    return sorted(rows)


def aggregate(rows) -> str:
    payload = "".join(repr(row) + "\n" for row in rows).encode("utf-8")
    return hashlib.sha256(payload).hexdigest()


def load_reference(path: pathlib.Path):
    expected = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        kind, count_s, sha256 = line.split()
        expected[kind] = (int(count_s), sha256.lower())

    if set(expected) != {"BINDER_REG", "INVOKER_CALL"}:
        raise SystemExit("EVENT_WIRING_SEMANTICS_REFERENCE_KIND_SET_BAD")
    return expected


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument(
        "--reference",
        default="tools/reference-event-wiring-semantics.txt",
    )
    args = parser.parse_args()

    jar = pathlib.Path(args.jar)
    if not jar.is_file():
        raise SystemExit(f"JAR_MISSING {jar}")

    expected = load_reference(pathlib.Path(args.reference))
    binder = binder_rows(jar)
    invoker = invoker_rows(jar)

    actual = {
        "BINDER_REG": (len(binder), aggregate(binder)),
        "INVOKER_CALL": (len(invoker), aggregate(invoker)),
    }

    failures = []
    for kind in ("BINDER_REG", "INVOKER_CALL"):
        print(f"EVENT_WIRING_SEMANTICS_{kind}_COUNT={actual[kind][0]}")
        print(f"EVENT_WIRING_SEMANTICS_{kind}_SHA256={actual[kind][1]}")
        if actual[kind] != expected[kind]:
            failures.append(
                f"{kind}:expected={expected[kind]}:actual={actual[kind]}"
            )

    print(f"EVENT_WIRING_SEMANTICS_FAILURES={len(failures)}")
    for failure in failures:
        print("EVENT_WIRING_SEMANTICS_BAD=" + failure)

    if failures:
        print("EVENT_WIRING_SEMANTICS_GATE=FAIL")
        return 1

    print("EVENT_WIRING_SEMANTICS_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
