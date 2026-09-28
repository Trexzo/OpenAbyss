#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import json
import os
import pathlib
import shutil
import sys
import urllib.request
import zipfile


MC_VERSION = "1.8.9"
FORGE_VERSION = "1.8.9-11.15.1.2318-1.8.9"
MOJANG_MANIFEST = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json"
MINECRAFT_LIBRARIES = "https://libraries.minecraft.net/"
FORGE_MAVEN = "https://maven.minecraftforge.net/"


def open_url(url: str, timeout: int):
    request = urllib.request.Request(
        url,
        headers={
            "User-Agent": "OpenAbyss-Recovery/1.0 (+https://github.com/Trexzo/OpenAbyss)",
            "Accept": "*/*",
        },
    )
    return urllib.request.urlopen(request, timeout=timeout)


def fetch_json(url: str):
    with open_url(url, 60) as response:
        return json.load(response)


def download(url: str, path: pathlib.Path, expected_sha1: str | None = None) -> None:
    if path.is_file():
        if expected_sha1:
            actual = hashlib.sha1(path.read_bytes()).hexdigest()
            if actual.lower() == expected_sha1.lower():
                return
        else:
            return
    path.parent.mkdir(parents=True, exist_ok=True)
    with open_url(url, 120) as response:
        data = response.read()
    if expected_sha1:
        actual = hashlib.sha1(data).hexdigest()
        if actual.lower() != expected_sha1.lower():
            raise RuntimeError(f"SHA1 mismatch for {url}: {actual} != {expected_sha1}")
    path.write_bytes(data)


def maven_parts(coordinate: str) -> tuple[str, str]:
    coord = coordinate
    extension = "jar"
    if "@" in coord:
        coord, extension = coord.split("@", 1)
    parts = coord.split(":")
    if len(parts) < 3:
        raise ValueError(f"Invalid Maven coordinate: {coordinate}")
    group, artifact, version = parts[:3]
    classifier = parts[3] if len(parts) >= 4 and parts[3] else None
    filename = artifact + "-" + version + (("-" + classifier) if classifier else "") + "." + extension
    relative = "/".join([group.replace(".", "/"), artifact, version, filename])
    return relative, filename


def library_allowed(lib: dict, platform: str) -> bool:
    rules = lib.get("rules")
    if not rules:
        return True
    allowed = False
    for rule in rules:
        os_rule = rule.get("os")
        matches = True
        if os_rule:
            name = os_rule.get("name")
            if name and name != platform:
                matches = False
            arch = os_rule.get("arch")
            if arch and arch not in ("x86_64", "amd64", "64"):
                matches = False
        if matches:
            allowed = rule.get("action") == "allow"
    return allowed


def download_maven(coordinate: str, library_root: pathlib.Path, base_url: str) -> pathlib.Path:
    relative, _ = maven_parts(coordinate)
    base = base_url or MINECRAFT_LIBRARIES
    base = base.replace("http://files.minecraftforge.net/maven/", FORGE_MAVEN)
    base = base.replace("http://maven.minecraftforge.net/", FORGE_MAVEN)
    if not base.endswith("/"):
        base += "/"
    dest = library_root / pathlib.PurePosixPath(relative)
    download(base + relative, dest)
    return dest


def prepare(args: argparse.Namespace) -> int:
    mc = pathlib.Path(args.minecraft_dir).resolve()
    libraries = mc / "libraries"
    versions = mc / "versions"
    assets = mc / "assets"
    bootstrap = mc / ".openabyss-bootstrap"
    for path in (libraries, versions, assets, bootstrap):
        path.mkdir(parents=True, exist_ok=True)

    manifest = fetch_json(MOJANG_MANIFEST)
    entry = next(v for v in manifest["versions"] if v["id"] == MC_VERSION)
    base_json = fetch_json(entry["url"])

    base_dir = versions / MC_VERSION
    base_dir.mkdir(parents=True, exist_ok=True)
    base_json_path = base_dir / f"{MC_VERSION}.json"
    base_json_path.write_text(json.dumps(base_json, indent=2), encoding="utf-8")
    base_jar = base_dir / f"{MC_VERSION}.jar"
    client_download = base_json["downloads"]["client"]
    download(client_download["url"], base_jar, client_download.get("sha1"))

    base_libs = 0
    for lib in base_json.get("libraries", []):
        if not library_allowed(lib, "linux"):
            continue
        downloads = lib.get("downloads") or {}
        artifact = downloads.get("artifact")
        if artifact and artifact.get("url"):
            dest = libraries / pathlib.PurePosixPath(artifact["path"])
            download(artifact["url"], dest, artifact.get("sha1"))
            base_libs += 1
        elif not downloads and lib.get("name"):
            download_maven(lib["name"], libraries, MINECRAFT_LIBRARIES)
            base_libs += 1

        classifiers = downloads.get("classifiers") or {}
        for classifier_name, classifier in classifiers.items():
            if "linux" not in classifier_name:
                continue
            if not classifier.get("url"):
                continue
            dest = libraries / pathlib.PurePosixPath(classifier["path"])
            download(classifier["url"], dest, classifier.get("sha1"))

    asset_index = base_json.get("assetIndex") or {}
    index_id = str(asset_index.get("id") or "1.8")
    index_path = assets / "indexes" / f"{index_id}.json"
    if asset_index.get("url"):
        download(asset_index["url"], index_path, asset_index.get("sha1"))

    if args.download_assets:
        index = json.loads(index_path.read_text(encoding="utf-8"))
        seen: set[str] = set()
        for item in index.get("objects", {}).values():
            h = item["hash"]
            if h in seen:
                continue
            seen.add(h)
            prefix = h[:2]
            dest = assets / "objects" / prefix / h
            download(f"https://resources.download.minecraft.net/{prefix}/{h}", dest, h)

    installer_name = f"forge-{FORGE_VERSION}-installer.jar"
    installer = bootstrap / installer_name
    download(
        f"{FORGE_MAVEN}net/minecraftforge/forge/{FORGE_VERSION}/{installer_name}",
        installer,
    )
    with zipfile.ZipFile(installer) as zf:
        profile = json.loads(zf.read("install_profile.json").decode("utf-8"))
    forge_info = profile.get("versionInfo")
    if not forge_info:
        raise RuntimeError("Forge install_profile.json has no versionInfo")
    forge_id = forge_info["id"]
    forge_dir = versions / forge_id
    forge_dir.mkdir(parents=True, exist_ok=True)
    forge_json = forge_dir / f"{forge_id}.json"
    forge_json.write_text(json.dumps(forge_info, indent=2), encoding="utf-8")

    forge_libs = 0
    for lib in forge_info.get("libraries", []):
        if lib.get("clientreq") is False or not lib.get("name"):
            continue
        name = lib["name"]
        base_url = lib.get("url") or MINECRAFT_LIBRARIES
        if name.startswith("net.minecraftforge:forge:"):
            plain_rel, _ = maven_parts(name)
            universal_rel, _ = maven_parts(name + ":universal")
            dest = libraries / pathlib.PurePosixPath(plain_rel)
            download(FORGE_MAVEN + universal_rel, dest)
            forge_libs += 1
            continue
        try:
            download_maven(name, libraries, base_url)
        except Exception:
            if base_url != FORGE_MAVEN:
                download_maven(name, libraries, FORGE_MAVEN)
            else:
                raise
        forge_libs += 1

    record = mc / "openabyss-production-profile.txt"
    record.write_text(
        "\n".join(
            [
                "OPENABYSS_PRODUCTION_FORGE_PROFILE=READY",
                f"MC_VERSION={MC_VERSION}",
                f"FORGE_VERSION={FORGE_VERSION}",
                f"FORGE_PROFILE_ID={forge_id}",
                f"BASE_LIBRARIES={base_libs}",
                f"FORGE_LIBRARIES={forge_libs}",
                f"BASE_JAR_SHA256={hashlib.sha256(base_jar.read_bytes()).hexdigest().upper()}",
                f"FORGE_JSON={forge_json}",
                f"ASSET_INDEX={index_id}",
            ]
        )
        + "\n",
        encoding="utf-8",
    )
    print(record.read_text(encoding="utf-8"), end="")
    return 0


def resolve_artifact(lib: dict, libraries: pathlib.Path) -> pathlib.Path | None:
    downloads_prop = lib.get("downloads")
    downloads = downloads_prop or {}
    artifact = downloads.get("artifact")
    if artifact and artifact.get("path"):
        return libraries / pathlib.PurePosixPath(artifact["path"])

    # Modern Mojang metadata can intentionally contain only native
    # classifiers (jinput-platform/lwjgl-platform) and no classpath artifact.
    if downloads_prop is not None:
        return None

    name = lib.get("name")
    if not name:
        return None
    relative, _ = maven_parts(name)
    return libraries / pathlib.PurePosixPath(relative)


def resolve_native(lib: dict, libraries: pathlib.Path) -> pathlib.Path | None:
    if not library_allowed(lib, "linux"):
        return None
    natives = lib.get("natives") or {}
    classifier = natives.get("linux")
    if not classifier:
        return None
    classifier = classifier.replace("$" + "{arch}", "64")
    downloads = lib.get("downloads") or {}
    entry = (downloads.get("classifiers") or {}).get(classifier)
    if entry and entry.get("path"):
        path = libraries / pathlib.PurePosixPath(entry["path"])
        if path.is_file():
            return path
    name = lib.get("name")
    if not name:
        return None
    parts = name.split(":")
    if len(parts) < 3:
        return None
    relative, _ = maven_parts(":".join(parts[:3] + [classifier]))
    path = libraries / pathlib.PurePosixPath(relative)
    return path if path.is_file() else None


def extract_natives(libs: list[dict], libraries: pathlib.Path, natives_dir: pathlib.Path) -> int:
    if natives_dir.exists():
        shutil.rmtree(natives_dir)
    natives_dir.mkdir(parents=True)
    jars: set[pathlib.Path] = set()
    for lib in libs:
        native = resolve_native(lib, libraries)
        if native:
            jars.add(native)
    for jar in jars:
        with zipfile.ZipFile(jar) as zf:
            for info in zf.infolist():
                if info.is_dir() or info.filename.startswith("META-INF/"):
                    continue
                name = pathlib.PurePosixPath(info.filename).name
                if not name.endswith(".so"):
                    continue
                (natives_dir / name).write_bytes(zf.read(info))
    return len(jars)


def launch(args: argparse.Namespace) -> int:
    mc = pathlib.Path(args.minecraft_dir).resolve()
    game = pathlib.Path(args.game_dir).resolve()
    abyss = pathlib.Path(args.abyss_jar).resolve()
    java = pathlib.Path(args.java).resolve()
    if not abyss.is_file():
        raise RuntimeError(f"OpenAbyss JAR missing: {abyss}")
    if not java.is_file():
        raise RuntimeError(f"Java missing: {java}")

    versions = mc / "versions"
    libraries = mc / "libraries"
    base_json = json.loads((versions / MC_VERSION / f"{MC_VERSION}.json").read_text(encoding="utf-8"))
    record = {}
    for line in (mc / "openabyss-production-profile.txt").read_text(encoding="utf-8").splitlines():
        if "=" in line:
            key, value = line.split("=", 1)
            record[key] = value
    forge_id = record["FORGE_PROFILE_ID"]
    forge_json = json.loads((versions / forge_id / f"{forge_id}.json").read_text(encoding="utf-8"))
    base_jar = versions / MC_VERSION / f"{MC_VERSION}.jar"

    libs = list(forge_json.get("libraries", [])) + list(base_json.get("libraries", []))
    classpath: list[pathlib.Path] = []
    missing: list[pathlib.Path] = []
    for lib in libs:
        if lib.get("clientreq") is False:
            continue
        if not library_allowed(lib, "linux"):
            continue
        artifact = resolve_artifact(lib, libraries)
        if artifact is None:
            continue
        if artifact.is_file():
            if artifact not in classpath:
                classpath.append(artifact)
        else:
            missing.append(artifact)
    if missing:
        raise RuntimeError("Missing runtime libraries:\n" + "\n".join(str(p) for p in missing))
    classpath.append(base_jar)

    game.mkdir(parents=True, exist_ok=True)
    mods = game / "mods"
    mods.mkdir(exist_ok=True)
    for stale in mods.glob("*.jar"):
        if stale.name.lower() == "abyss.jar" or stale.name.startswith("OpenAbyss-"):
            stale.unlink()
    installed = mods / "abyss.jar"
    shutil.copy2(abyss, installed)
    if hashlib.sha256(installed.read_bytes()).digest() != hashlib.sha256(abyss.read_bytes()).digest():
        raise RuntimeError("Installed mods-folder JAR hash mismatch")

    natives_dir = game / "runtime-natives"
    native_archives = extract_natives(libs, libraries, natives_dir)
    if not any(natives_dir.glob("liblwjgl*.so")):
        raise RuntimeError("LWJGL Linux native was not extracted")
    if not any(natives_dir.glob("libopenal*.so")):
        raise RuntimeError("OpenAL Linux native was not extracted")

    assets = mc / "assets"
    asset_index = str((base_json.get("assetIndex") or {}).get("id") or "1.8")
    main_class = forge_json.get("mainClass") or "net.minecraft.launchwrapper.Launch"
    cp = os.pathsep.join(str(p) for p in classpath)

    cmd = [
        str(java),
        "-Xmx2G",
        f"-Djava.library.path={natives_dir}",
        "-Dminecraft.launcher.brand=OpenAbyssProductionSmoke",
        "-Dminecraft.launcher.version=1",
    ]
    cmd += list(args.jvm_arg or [])
    cmd += [
        "-cp",
        cp,
        main_class,
        "--username",
        args.username,
        "--version",
        forge_id,
        "--gameDir",
        str(game),
        "--assetsDir",
        str(assets),
        "--assetIndex",
        asset_index,
        "--uuid",
        "00000000000000000000000000000001",
        "--accessToken",
        "0",
        "--userProperties",
        "{}",
        "--userType",
        "Legacy",
        "--tweakClass",
        "net.minecraftforge.fml.common.launcher.FMLTweaker",
    ]
    if args.server:
        cmd += ["--server", args.server]
        if args.port:
            cmd += ["--port", str(args.port)]

    print(f"OPENABYSS_PRODUCTION_LINUX_JVM_ARG_COUNT={len(args.jvm_arg or [])}", flush=True)
    for jvm_arg in args.jvm_arg or []:
        if jvm_arg.startswith("-Dabyss."):
            print("OPENABYSS_PRODUCTION_LINUX_JVM_ARG=" + jvm_arg, flush=True)
    print(f"OPENABYSS_PRODUCTION_LINUX_CLASSPATH_COUNT={len(classpath)}", flush=True)
    print(f"OPENABYSS_PRODUCTION_LINUX_NATIVE_ARCHIVES={native_archives}", flush=True)
    print(f"OPENABYSS_PRODUCTION_LINUX_JAR_SHA256={hashlib.sha256(abyss.read_bytes()).hexdigest().upper()}", flush=True)
    print("OPENABYSS_PRODUCTION_LINUX_EXPLICIT_COREMOD_PROPERTY=0", flush=True)
    print("OPENABYSS_PRODUCTION_LINUX_INSTALL_MODE=mods-folder", flush=True)
    print("OPENABYSS_PRODUCTION_LINUX_EXEC=" + " ".join(cmd[:7]) + " ...", flush=True)
    os.chdir(game)
    print(f"OPENABYSS_PRODUCTION_LINUX_CWD={pathlib.Path.cwd()}", flush=True)
    os.execvpe(str(java), cmd, os.environ.copy())
    return 0


def main() -> int:
    parser = argparse.ArgumentParser()
    sub = parser.add_subparsers(dest="command", required=True)

    p = sub.add_parser("prepare")
    p.add_argument("--minecraft-dir", required=True)
    p.add_argument("--download-assets", action="store_true")
    p.set_defaults(func=prepare)

    p = sub.add_parser("launch")
    p.add_argument("--minecraft-dir", required=True)
    p.add_argument("--game-dir", required=True)
    p.add_argument("--abyss-jar", required=True)
    p.add_argument("--java", required=True)
    p.add_argument("--username", default="CIProdWorld")
    p.add_argument("--jvm-arg", action="append", default=[])
    p.add_argument("--server")
    p.add_argument("--port", type=int)
    p.set_defaults(func=launch)

    args = parser.parse_args()
    return args.func(args)


if __name__ == "__main__":
    sys.exit(main())
