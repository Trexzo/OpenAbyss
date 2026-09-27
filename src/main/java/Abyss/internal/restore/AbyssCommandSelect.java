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
    public static String selfTest() {
        try {
            List<Module> live = AbyssCommandSelect.all();
            if (live.size() != 112) {
                return "FAIL all-count " + live.size() + "/112";
}
            List<Module> viaAll = AbyssCommandSelect.resolve(new String[]{"all"}, 0, 1);
            if (viaAll.size() != live.size() || !viaAll.containsAll(live)) {
                return "FAIL all-resolve " + viaAll.size() + "/" + live.size();
}
            int categoryTotal = 0;
            int categories = 0;
            for (Category category : Category.values()) {
                int expected = 0;
                for (Module module : live) {
                    if (module.f() == category) {
                        ++expected;
}
}
                List<Module> resolved = AbyssCommandSelect.resolve(new String[]{category.c()}, 0, 1);
                if (resolved.size() != expected) {
                    return "FAIL category " + category + " expected=" + expected + " actual=" + resolved.size();
}
                for (Module module : resolved) {
                    if (module.f() != category) {
                        return "FAIL category-crosswrite " + category + " -> " + module.b() + "/" + module.f();
}
}
                categoryTotal += resolved.size();
                ++categories;
}
            if (categoryTotal != live.size()) {
                return "FAIL category-total " + categoryTotal + "/" + live.size();
}
            if (live.isEmpty()) {
                return "FAIL live-empty";
}
            Module first = live.get(0);
            List<Module> named = AbyssCommandSelect.resolve(new String[]{first.b()}, 0, 1);
            if (named.size() != 1 || named.get(0) != first) {
                return "FAIL named " + first.b() + " size=" + named.size();
}
            List<Module> deduped = AbyssCommandSelect.resolve(new String[]{"all", first.b(), first.f().c(), "all"}, 0, 4);
            if (deduped.size() != live.size()) {
                return "FAIL dedupe " + deduped.size() + "/" + live.size();
}
            String missing = "__openabyss_missing_selector__";
            List<Module> unresolved = AbyssCommandSelect.resolve(new String[]{missing}, 0, 1);
            if (!unresolved.isEmpty() || UNRESOLVED.size() != 1 || !missing.equals(UNRESOLVED.get(0))) {
                return "FAIL unresolved modules=" + unresolved.size() + " notes=" + UNRESOLVED;
}
            List<String> pool = AbyssCommandSelect.pool();
            if (!pool.contains(ALL) || !pool.contains(first.b())) {
                return "FAIL pool size=" + pool.size();
}
            UNRESOLVED.clear();
            return "PASS all=112 categories=" + categories + " dedupe unresolved";
}
        catch (Throwable failure) {
            UNRESOLVED.clear();
            return "FAIL " + failure.getClass().getName() + ": " + failure.getMessage();
}
}

}