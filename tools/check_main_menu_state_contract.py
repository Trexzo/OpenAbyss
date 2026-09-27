#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import re
import sys


def require(text: str, pattern: str, label: str, *, regex: bool = False) -> None:
    ok = bool(re.search(pattern, text, re.S)) if regex else pattern in text
    print(f"MAIN_MENU_STATE_{label}={'PASS' if ok else 'FAIL'}")
    if not ok:
        raise SystemExit(f"MAIN_MENU_STATE_GATE=FAIL missing={label}")


def main() -> int:
    theme_path = pathlib.Path("src/main/java/Abyss/ui/screen/MainMenuTheme.java")
    data_path = pathlib.Path("src/main/java/Abyss/internal/restore/AbyssCommandData.java")
    theme = theme_path.read_text(encoding="utf-8")
    data = data_path.read_text(encoding="utf-8")

    require(
        theme,
        'mode = new ModeSetting("Mode", true, "NONE", "DRACU_RIOT", "RIDDLE_JOKER", "SENREN_BANKA", "NONE");',
        "MODE_DEFAULT_OPTIONS",
    )
    require(
        theme,
        'music = new BooleanSetting("Music", true);',
        "MUSIC_DEFAULT_TRUE",
    )
    require(theme, 'z = new ResourceLocation("minecraft", "mainmenu/dracuriot/bg.png");', "DRACU_BG")
    require(theme, 'a = new ResourceLocation("minecraft", "mainmenu/senrenbanka/bg.png");', "SENREN_BG")
    require(theme, 'd = new ResourceLocation("minecraft", "mainmenu/riddlejoker/bg.png");', "RIDDLE_BG")
    require(theme, 'n = new ResourceLocation("minecraft", "mainmenu/senrenbanka/icon.png");', "SENREN_ICON")
    require(theme, 'v = new ResourceLocation("minecraft", "mainmenu/riddlejoker/button.png");', "RIDDLE_BUTTON")
    require(theme, 'w = false;', "MUSIC_RUNTIME_FLAG_FALSE")

    require(data, 'public static final String MENU = "menu.txt";', "MENU_FILENAME")
    require(data, 'public static final String MENU_MUSIC = "menu_music.txt";', "MENU_MUSIC_FILENAME")
    require(data, 'public static final String FRIENDS = "friends.txt";', "FRIENDS_FILENAME")
    require(data, 'public static final String ENEMIES = "enemies.txt";', "ENEMIES_FILENAME")
    require(data, 'public static final String CURRENT = "current.json";', "CURRENT_FILENAME")
    require(data, 'static final String CHAT_BINDS = "chatBinds";', "CHAT_BINDS_KEY")

    require(
        data,
        r"saveMenu\s*\(\s*\).*?writeText\s*\(\s*MENU\s*,\s*MainMenuTheme\.mode\.Y\(\)\s*\)",
        "SAVE_MENU_COUPLING",
        regex=True,
    )
    require(
        data,
        r"saveMenuMusic\s*\(\s*\).*?writeText\s*\(\s*MENU_MUSIC\s*,\s*String\.valueOf\(MainMenuTheme\.music\.c\(\)\)\s*\)",
        "SAVE_MUSIC_COUPLING",
        regex=True,
    )
    require(
        data,
        r"loadMenu\s*\(\s*\).*?MainMenuTheme\.mode\.S\(\).*?equalsIgnoreCase\(requested\).*?MainMenuTheme\.mode\.i\(accepted\)",
        "LOAD_MENU_VALIDATED_COUPLING",
        regex=True,
    )
    require(
        data,
        'var2.append("menu.txt INVALID(").append(requested).append(\')\');',
        "LOAD_MENU_INVALID_REPORTED",
    )
    require(
        data,
        r'loadMenu\s*\(\s*\).*?"true"\.equalsIgnoreCase\(requested\)\s*\|\|\s*"false"\.equalsIgnoreCase\(requested\).*?MainMenuTheme\.music\.v\(Boolean\.parseBoolean\(requested\),\s*0L\)',
        "LOAD_MUSIC_STRICT_COUPLING",
        regex=True,
    )
    require(
        data,
        'var2.append(" menu_music.txt INVALID(").append(requested).append(\')\');',
        "LOAD_MUSIC_INVALID_REPORTED",
    )
    require(
        data,
        'return "PASS menu=RIDDLE_JOKER music=false chatBind=54:.help friends=1 enemies=1 malformed-menu-refused',
        "MALFORMED_MENU_SELFTEST",
    )

    print("MAIN_MENU_STATE_GATE=PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
