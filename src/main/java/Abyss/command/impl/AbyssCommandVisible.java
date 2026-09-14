/*
 * Decompiled with CFR 0.152.
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.internal.restore.AbyssCommandSelect;
import Abyss.module.Category;
import Abyss.module.Module;
import java.util.ArrayList;
import java.util.List;

public final class AbyssCommandVisible
extends Command {
    private final boolean visible;
    private final String desc;
    private final String[] aliases;

    public AbyssCommandVisible(boolean var1, String var2, String ... var3) {
        this.visible = var1;
        this.desc = var2;
        this.aliases = var3;
}
    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return this.aliases;
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat(this.desc);
        AbyssCommands.chat("\u00a77Usage:");
        AbyssCommands.chat("\u00a7f  ." + this.aliases[0] + " <module... | category... | \"all\">");
}
    @Override
    public void j(String[] var1, long var2) {
        List<Module> var4 = AbyssCommandSelect.resolve(var1, 0, var1.length);
        AbyssCommandSelect.reportUnresolved();
        if (var4.isEmpty()) {
            return;
}
        int var5 = 0;
        int var6 = 0;
        for (int var7 = 0; var7 < var4.size(); ++var7) {
            Module var8 = var4.get(var7);
            if (var8.S() || var8.f() == Category.Macro) {
                ++var6;
                continue;
}
            try {
                var8.Y(0L, this.visible, (short)0);
}
            catch (Throwable var10) {
                AbyssCommands.chat("\u00a7cModule.Y threw for " + var8.b() + ": " + var10);
                continue;
}
            if (var8.D() == this.visible) {
                ++var5;
                continue;
}
            AbyssCommands.chat("\u00a7c" + var8.b() + " did not take the value.");
}
        AbyssCommands.chat("\u00a77" + var5 + " of " + var4.size() + " module(s) are now \u00a7f" + (this.visible ? "shown" : "hidden") + "\u00a77 in the ArrayList." + (var6 > 0 ? " \u00a78" + var6 + " skipped (fixed binding or Macro; the stock writer ignores the argument for those)" : ""));
        if (var5 > 0) {
            AbyssCommands.chat("\u00a78Not saved yet -- use \u00a77.config save <name>\u00a78 to persist it.");
}
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        return new ArrayList<String>(AbyssCommandSelect.pool());
}
}