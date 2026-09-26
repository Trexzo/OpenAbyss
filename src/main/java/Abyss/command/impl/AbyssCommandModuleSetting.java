package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.setting.settings.TextSetting;
import java.util.ArrayList;
import java.util.List;

public final class AbyssCommandModuleSetting extends StockCommandModuleSetting {
    @Override
    public boolean J() {
        return false;
    }

    @Override
    public String[] e(long seed) {
        // This command is selected by module name rather than a fixed alias.
        return new String[]{"<module-setting>"};
    }

    @Override
    public void h(long seed) {
        AbyssCommands.chat("\u00a77Usage: \u00a7f.<module> <setting> [value]");
        AbyssCommands.chat("\u00a78With no setting/value, the recovered command lists the module's current settings.");
    }

    @Override
    public List g(String[] args, int depth, long seed) {
        ArrayList<String> out = new ArrayList<String>();
        if (args == null || args.length == 0) {
            return out;
        }

        Module module = AbyssCommands.module(args[0]);
        if (module == null) {
            return out;
        }

        List<Setting> settings = safeSettings(module);
        if (depth <= 1 || args.length <= 1) {
            for (Setting setting : settings) {
                String name = safeName(setting);
                if (name != null && !name.isEmpty()) {
                    out.add(name);
                }
            }
            return out;
        }

        Setting setting = findSetting(module, args[1]);
        if (setting instanceof BooleanSetting) {
            out.add("true");
            out.add("false");
            out.add("toggle");
        } else if (setting instanceof ModeSetting) {
            List<String> modes = ((ModeSetting)setting).S();
            if (modes != null) {
                out.addAll(modes);
            }
        } else if (setting instanceof ColorSetting) {
            out.add(((ColorSetting)setting).Q());
        } else if (setting instanceof NumberSetting) {
            NumberSetting number = (NumberSetting)setting;
            out.add(String.valueOf(number.L()));
            out.add(String.valueOf(number.i()));
            out.add(String.valueOf(number.F()));
        } else if (setting instanceof PercentageSetting) {
            out.add(String.valueOf(((PercentageSetting)setting).k()));
        } else if (setting instanceof TextSetting) {
            String current = ((TextSetting)setting).X();
            if (current != null && !current.isEmpty()) {
                out.add(current);
            }
        }
        return out;
    }

    @Override
    public void j(String[] args, long seed) {
        if (args == null || args.length == 0) {
            this.h(seed);
            return;
        }

        Module module = AbyssCommands.module(args[0]);
        if (module == null) {
            AbyssCommands.chat("\u00a7cNo module named \u00a7f" + args[0] + "\u00a7c.");
            return;
        }

        if (args.length == 1) {
            list(module);
            return;
        }

        Setting setting = findSetting(module, args[1]);
        if (setting == null) {
            AbyssCommands.chat("\u00a7c" + module.b() + " has no setting named \u00a7f" + args[1] + "\u00a7c.");
            list(module);
            return;
        }

        if (args.length == 2) {
            AbyssCommands.chat("\u00a77" + module.b() + "." + safeName(setting) + " = \u00a7f" + value(setting));
            return;
        }

        String raw = join(args, 2);
        try {
            String error = write(setting, raw);
            if (error != null) {
                AbyssCommands.chat("\u00a7c" + error);
                return;
            }
            AbyssCommands.chat("\u00a7a" + module.b() + "." + safeName(setting)
                    + "\u00a77 = \u00a7f" + value(setting));
            AbyssCommands.chat("\u00a78Not saved yet -- use \u00a77.config save <name>\u00a78 to persist it.");
        }
        catch (Throwable failure) {
            AbyssCommands.chat("\u00a7cSetting write failed: " + failure);
        }
    }

    private static List<Setting> safeSettings(Module module) {
        try {
            List<Setting> settings = module.w();
            return settings == null ? new ArrayList<Setting>() : settings;
        }
        catch (Throwable ignored) {
            return new ArrayList<Setting>();
        }
    }

    private static Setting findSetting(Module module, String requested) {
        if (requested == null) {
            return null;
        }
        String want = normalize(requested);
        for (Setting setting : safeSettings(module)) {
            String name = safeName(setting);
            if (name != null && normalize(name).equals(want)) {
                return setting;
            }
        }
        return null;
    }

    private static String normalize(String value) {
        return value.toLowerCase().replace(" ", "").replace("-", "").replace("_", "");
    }

    private static String safeName(Setting setting) {
        if (setting == null) {
            return null;
        }
        try {
            return setting.B();
        }
        catch (Throwable ignored) {
            return null;
        }
    }

    private static void list(Module module) {
        List<Setting> settings = safeSettings(module);
        if (settings.isEmpty()) {
            AbyssCommands.chat("\u00a77" + module.b() + " has no settings.");
            return;
        }
        AbyssCommands.chat("\u00a77" + module.b() + " settings:");
        for (Setting setting : settings) {
            String name = safeName(setting);
            if (name != null) {
                AbyssCommands.chat("\u00a7f  " + name + "\u00a78 = \u00a77" + value(setting));
            }
        }
    }

    private static String value(Setting setting) {
        try {
            if (setting instanceof BooleanSetting) {
                return String.valueOf(((BooleanSetting)setting).c());
            }
            if (setting instanceof PercentageSetting) {
                return String.valueOf(((PercentageSetting)setting).k());
            }
            if (setting instanceof NumberSetting) {
                return String.valueOf(((NumberSetting)setting).L());
            }
            if (setting instanceof ColorSetting) {
                return ((ColorSetting)setting).Q();
            }
            if (setting instanceof ModeSetting) {
                return ((ModeSetting)setting).Y();
            }
            if (setting instanceof TextSetting) {
                return ((TextSetting)setting).X();
            }
        }
        catch (Throwable ignored) {
        }
        return "<unreadable>";
    }

    private static String write(Setting setting, String raw) throws Throwable {
        if (setting instanceof BooleanSetting) {
            BooleanSetting target = (BooleanSetting)setting;
            if ("toggle".equalsIgnoreCase(raw)) {
                target.W(0L);
                return null;
            }
            if ("true".equalsIgnoreCase(raw) || "on".equalsIgnoreCase(raw) || "1".equals(raw)) {
                target.v(true, 0L);
                return null;
            }
            if ("false".equalsIgnoreCase(raw) || "off".equalsIgnoreCase(raw) || "0".equals(raw)) {
                target.v(false, 0L);
                return null;
            }
            return "Expected true, false, on, off, 1, 0, or toggle.";
        }

        if (setting instanceof PercentageSetting) {
            int parsed;
            try {
                parsed = Integer.parseInt(raw);
            }
            catch (NumberFormatException failure) {
                return "Expected an integer percentage.";
            }
            if (parsed < 0 || parsed > 100) {
                return "Percentage must be between 0 and 100.";
            }
            ((PercentageSetting)setting).d(parsed);
            return null;
        }

        if (setting instanceof NumberSetting) {
            NumberSetting target = (NumberSetting)setting;
            float parsed;
            try {
                parsed = Float.parseFloat(raw);
            }
            catch (NumberFormatException failure) {
                return "Expected a number.";
            }
            if (Float.isNaN(parsed) || Float.isInfinite(parsed) || parsed < target.i() || parsed > target.F()) {
                return "Number must be between " + target.i() + " and " + target.F() + ".";
            }
            target.o((byte)0, 0L, parsed);
            return null;
        }

        if (setting instanceof ModeSetting) {
            ModeSetting target = (ModeSetting)setting;
            List<String> modes = target.S();
            if (modes != null) {
                for (String mode : modes) {
                    if (mode != null && mode.equalsIgnoreCase(raw)) {
                        target.i(mode);
                        return null;
                    }
                }
            }
            return "Unknown mode " + raw + "; expected " + String.valueOf(modes) + ".";
        }

        if (setting instanceof ColorSetting) {
            String hex = raw.startsWith("#") ? raw.substring(1) : raw;
            if (!hex.matches("(?i)[0-9a-f]{6}")) {
                return "Expected a six-digit RGB hex value.";
            }
            ((ColorSetting)setting).e(hex.toUpperCase());
            return null;
        }

        if (setting instanceof TextSetting) {
            ((TextSetting)setting).O(raw);
            return null;
        }

        return "Unsupported recovered setting type " + setting.getClass().getName() + ".";
    }

    private static String join(String[] values, int from) {
        StringBuilder out = new StringBuilder();
        for (int i = from; i < values.length; ++i) {
            if (i > from) {
                out.append(' ');
            }
            out.append(values[i]);
        }
        return out.toString();
    }

    public static String selfTest() {
        try {
            BooleanSetting bool = new BooleanSetting("Bool", false);
            String bad = write(bool, "true");
            if (bad != null || !bool.c()) {
                return "FAIL boolean " + bad;
            }

            NumberSetting number = new NumberSetting("Number", 1.0f, 0.0f, 10.0f, 0.5f);
            bad = write(number, "3.5");
            if (bad != null || number.L() != 3.5f) {
                return "FAIL number " + bad + " value=" + number.L();
            }

            PercentageSetting percentage = new PercentageSetting("Percent", 10);
            bad = write(percentage, "75");
            if (bad != null || percentage.k() != 75) {
                return "FAIL percentage " + bad + " value=" + percentage.k();
            }

            ModeSetting mode = new ModeSetting("Mode", "ONE", "TWO");
            bad = write(mode, "two");
            if (bad != null || !mode.R("TWO")) {
                return "FAIL mode " + bad + " value=" + mode.Y();
            }

            ColorSetting color = new ColorSetting("Color", "000000");
            bad = write(color, "#A1b2C3");
            if (bad != null || !"A1B2C3".equals(color.Q())) {
                return "FAIL color " + bad + " value=" + color.Q();
            }

            TextSetting text = new TextSetting("Text", "");
            bad = write(text, "hello world");
            if (bad != null || !"hello world".equals(text.X())) {
                return "FAIL text " + bad + " value=" + text.X();
            }
            return "PASS setting-types=6";
        }
        catch (Throwable failure) {
            return "FAIL " + failure.getClass().getName() + ": " + failure.getMessage();
        }
    }
}
