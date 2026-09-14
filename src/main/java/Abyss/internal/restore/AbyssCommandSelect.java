/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.restore;

import Abyss.command.AbyssCommands;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public final class AbyssCommandSelect {
    static final String ALL = "all";
    static final List<String> UNRESOLVED = new ArrayList<String>();

    private AbyssCommandSelect() {
}
    public static List<Module> resolve(String[] var0, int var1, int var2) {
        UNRESOLVED.clear();
        LinkedHashSet<Module> var3 = new LinkedHashSet<Module>();
        for (int var4 = var1; var4 < var2; ++var4) {
            String var5 = var0[var4];
            if (ALL.equalsIgnoreCase(var5)) {
                var3.addAll(AbyssCommandSelect.all());
                continue;
}
            Category var6 = AbyssCommandSelect.category(var5);
            if (var6 != null) {
                for (Module var8 : AbyssCommandSelect.all()) {
                    if (var8.f() != var6) continue;
                    var3.add(var8);
}
                continue;
}
            Module var9 = AbyssCommands.module(var5);
            if (var9 != null) {
                var3.add(var9);
                continue;
}
            UNRESOLVED.add(var5);
}
        return new ArrayList<Module>(var3);
}
    public static List<Module> all() {
        ArrayList<Module> var0 = new ArrayList<Module>();
        if (ModuleManager.S != null) {
            for (Module var2 : ModuleManager.S) {
                if (var2 == null || var2.b() == null || var2.b().startsWith("?")) continue;
                var0.add(var2);
}
}
        return var0;
}
    static Category category(String var0) {
        Category[] var1 = Category.values();
        for (int var2 = 0; var2 < var1.length; ++var2) {
            if (!var1[var2].c().equalsIgnoreCase(var0) && !var1[var2].toString().equalsIgnoreCase(var0)) continue;
            return var1[var2];
}
        return null;
}
    public static List<String> pool() {
        ArrayList<String> var0 = new ArrayList<String>();
        var0.add(ALL);
        Category[] var1 = Category.values();
        for (int var2 = 0; var2 < var1.length; ++var2) {
            var0.add(var1[var2].c());
}
        for (Module var4 : AbyssCommandSelect.all()) {
            var0.add(var4.b());
}
        return var0;
}
    public static void reportUnresolved() {
        for (int var0 = 0; var0 < UNRESOLVED.size(); ++var0) {
            AbyssCommands.chat("\u00a7cNo module or category named \u00a7f" + UNRESOLVED.get(var0) + "\u00a7c.");
}
}
}