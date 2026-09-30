#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import struct
import sys
import zipfile


def canonical_content_sha256(path: str) -> str:
    digest = hashlib.sha256()
    with zipfile.ZipFile(path) as zf:
        names = sorted(name for name in zf.namelist() if not name.endswith("/"))
        for name in names:
            name_bytes = name.encode("utf-8")
            data = zf.read(name)
            digest.update(struct.pack(">I", len(name_bytes)))
            digest.update(name_bytes)
            digest.update(struct.pack(">Q", len(data)))
            digest.update(data)
    return digest.hexdigest().upper()


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("jar")
    parser.add_argument("--expect")
    args = parser.parse_args()

    actual = canonical_content_sha256(args.jar)
    print("JAR_CONTENT_SHA256=" + actual)
    if args.expect and actual != args.expect.upper():
        print("JAR_CONTENT_SHA256_MISMATCH expected=%s actual=%s" % (args.expect.upper(), actual))
        return 1
    print("JAR_CONTENT_DIGEST=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
