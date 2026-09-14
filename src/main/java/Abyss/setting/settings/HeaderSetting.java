/*
 * Decompiled with CFR 0.152.
 */
package Abyss.setting.settings;

import Abyss.module.MacroModule;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Language;
import Abyss.setting.Setting;
import Abyss.util.MathUtil;
import java.util.ArrayList;
import java.util.List;

public class HeaderSetting
extends Setting {
    private static long public String I;

    public HeaderSetting(String var1) {
        this.q = var1;
        this.I = var1;
}
    public String L() {
        return this.I;
}
    public static List<Setting> prune(List<Setting> var0) {
        ArrayList<Setting> var1 = new ArrayList<Setting>(var0.size());
        for (int var2 = 0; var2 < var0.size(); ++var2) {
            Setting var3 = var0.get(var2);
            if (var3 instanceof HeaderSetting && !HeaderSetting.occupied(var0, var2)) continue;
            var1.add(var3);
}
        return var1;
}
    public static boolean occupied(List<Setting> var0, int var1) {
        if (var0 == null) {
            return false;
}
        for (int var2 = var1 + 1; var2 < var0.size(); ++var2) {
            Setting var3 = var0.get(var2);
            if (var3 == null) continue;
            return !(var3 instanceof HeaderSetting);
}
        return false;
}
    public String U(long var1, Module var3) {
        if (!Language.applyForSettings.c()) {
            return this.I;
}
        if (Language.language.R("ENGLISH")) {
            return this.I;
}
        int var6 = MathUtil.k(var3.x(this) + 1, 1, var3.w().size());
        return var3 instanceof MacroModule ? Language.z("setting.Macro." + var6, 0L) : Language.z("setting." + var3.b() + "." + var6, 0L);
}
    public void B(String var1) {
        this.I = var1;
}
}