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

public final class AbyssCommandToggle
extends Command {
    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"toggle", "t"};
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat("\u00a7eToggle a module");
        AbyssCommands.chat("\u00a77Usage:");
        AbyssCommands.chat("\u00a7f  .toggle <module...>");
}
    @Override
    public void j(String[] var1, long var2) {
        int var4 = 0;
        for (int var5 = 0; var5 < var1.length; ++var5) {
            Module var6 = AbyssCommands.module(var1[var5]);
            if (var6 == null) {
                AbyssCommands.chat("\u00a7cNo module named \u00a7f" + var1[var5] + "\u00a7c.");
                continue;
}
            if (!var6.I()) {
                AbyssCommands.chat("\u00a7c" + var6.b() + " is not a toggleable module.");
                continue;
}
            if (var6.S()) {
                AbyssCommands.chat("\u00a7c" + var6.b() + " has a fixed binding and refuses .toggle.");
                continue;
}
            boolean var7 = var6.o();
            try {
                var6.I(20724619369162L, !var7);
}
            catch (Throwable var9) {
                AbyssCommands.chat("\u00a7cModule.I threw for " + var6.b() + ": " + var9);
                continue;
}
            if (var6.o() == var7) {
                AbyssCommands.chat("\u00a7c" + var6.b() + " did not change state.");
                continue;
}
            AbyssCommands.chat((var6.o() ? "\u00a7a" : "\u00a7c") + var6.b() + "\u00a77 is now \u00a7f" + (var6.o() ? "enabled" : "disabled"));
            ++var4;
}
        if (var4 > 0) {
            AbyssCommands.chat("\u00a78Not saved yet -- use \u00a77.config save <name>\u00a78 to persist it.");
}
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        ArrayList<String> var5 = new ArrayList<String>();
        for (Module var7 : AbyssCommandSelect.all()) {
            var5.add(var7.b());
}
        return var5;
}
}