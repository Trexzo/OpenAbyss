/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.configuration;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import java.text.MessageFormat;

public class Language
extends Module {
    public static BooleanSetting applyForArraylist;
    public static BooleanSetting applyForCategory;
    private static long b;
    public static HeaderSetting theseOptionsOnlyAffectClickGui;
    public static ModeSetting language;
    public static HeaderSetting thisOptionOnlyAffectsArrayList;
    public static BooleanSetting applyForDescriptions;
    public static BooleanSetting applyForName;
    public static BooleanSetting applyForSettings;

    public static String z(String var0, long var1) {
        String var8 = Language.s(var0);
        return var8 != null ? var8 : var0;
}
    public static String Y(String var2) {
        if (!applyForCategory.c()) {
            return var2;
}
        return language.R("ENGLISH") ? var2 : Language.z("category." + var2, 0L);
}
    public Language(char var1, int var2, int var3) {
        super(((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ b ^ 0x350A3A3B146AL);
        this.declare("Language", Category.Configuration, "Configuration of language", new Setting[0]);
}
    public static String Z(long var0, String var2) {
        if (language.R("ENGLISH")) {
            return var2;
}
        if (var2.startsWith("Macro")) {
            return language.R("ENGLISH") ? var2 : MessageFormat.format(Language.z("module.Macro.name", 0L), Character.valueOf(var2.charAt(var2.length() - 1)));
}
        return Language.z("module." + var2 + ".name", 0L);
}
    private static String s(String var2) {
        switch (var2.toLowerCase()) {
            case "clickgui.bind.press": {
                return "Press a key...";
}
            case "clickgui.bind.current": {
                return "Current bind: '\u00a7e";
}
            case "clickgui.description.edit": {
                return "Edit \"{0}\" using command";
}
            case "clickgui.studio.set": {
                return "Set";
}
            case "clickgui.studio.visible": {
                return "Visible";
}
            case "clickgui.studio.suffix": {
                return "Suffix";
}
}
        return null;
}
    public static void G(String var0) {
        var0 = var0.toUpperCase();
        language.i(var0);
}
    public static String o(String var0) {
        if (language.R("ENGLISH")) {
            return var0;
}
        if (!applyForDescriptions.c()) {
            return var0;
}
        return var0.startsWith("Macro") ? MessageFormat.format(Language.z("module.Macro.description", 0L), Character.valueOf(var0.charAt(var0.length() - 1))) : Language.z("module." + var0 + ".description", 0L);
}
    static {
        b = 47620395300042L;
        applyForArraylist = new BooleanSetting("Apply-for-arraylist", false);
        theseOptionsOnlyAffectClickGui = new HeaderSetting("These options only affect ClickGui");
        thisOptionOnlyAffectsArrayList = new HeaderSetting("This option only affects ArrayList");
        applyForCategory = new BooleanSetting("Apply-for-category", true);
        applyForSettings = new BooleanSetting("Apply-for-settings", true);
        applyForName = new BooleanSetting("Apply-for-name", true);
        applyForDescriptions = new BooleanSetting("Apply-for-descriptions", true);
        language = new ModeSetting("Language", "ENGLISH");
}
}