/*
 * Decompiled with CFR 0.152.
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.internal.restore.AbyssCommandSelect;
import Abyss.module.Module;
import java.util.ArrayList;
import java.util.List;

public final class AbyssCommandSuffix
extends Command {
    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"suffix", "sfx"};
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat("\u00a7eEdit modules suffix visibility");
        AbyssCommands.chat("\u00a77Usage:");
        AbyssCommands.chat("\u00a7f  .suffix <module... | category... | \"all\"> [\"true\" | \"false\"]");
}
    @Override
    public void j(String[] var1, long var2) {
        int var4 = var1.length;
        Boolean var5 = null;
        String var6 = var1[var4 - 1];
        if ("true".equalsIgnoreCase(var6) || "false".equalsIgnoreCase(var6)) {
            var5 = Boolean.parseBoolean(var6.toLowerCase());
            --var4;
}
        if (var4 == 0) {
            this.h(0L);
            return;
}
        List<Module> var7 = AbyssCommandSelect.resolve(var1, 0, var4);
        AbyssCommandSelect.reportUnresolved();
        if (var7.isEmpty()) {
            return;
}
        int var8 = 0;
        for (int var9 = 0; var9 < var7.size(); ++var9) {
            Module var10 = var7.get(var9);
            boolean var11 = var5 == null ? !var10.r() : var5;
            try {
                var10.C(var11);
}
            catch (Throwable var13) {
                AbyssCommands.chat("\u00a7cModule.C threw for " + var10.b() + ": " + var13);
                continue;
}
            if (var10.r() == var11) {
                ++var8;
                continue;
}
            AbyssCommands.chat("\u00a7c" + var10.b() + " did not take the value.");
}
        if (var7.size() == 1 && var8 == 1) {
            AbyssCommands.chat("\u00a77" + var7.get(0).b() + " suffix is now \u00a7f" + var7.get(0).r());
        } else {
            AbyssCommands.chat("\u00a77" + var8 + " of " + var7.size() + " module(s) set to \u00a7f" + (var5 == null ? "the opposite of what they were" : var5));
}
        if (var8 > 0) {
            AbyssCommands.chat("\u00a78Not saved yet -- use \u00a77.config save <name>\u00a78 to persist it.");
}
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        ArrayList<String> var5 = new ArrayList<String>();
        if (var2 <= 1) {
            var5.addAll(AbyssCommandSelect.pool());
        } else {
            var5.add("true");
            var5.add("false");
            var5.addAll(AbyssCommandSelect.pool());
}
        return var5;
}
}