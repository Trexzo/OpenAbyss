/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;

public final class AbyssCommandList
extends Command {
    private static final String SEP = "\u00a78-----------------------------";

    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"list", "l"};
}
    @Override
    public void h(long var1) {
        this.j(new String[0], 0L);
}
    @Override
    public void j(String[] var1, long var2) {
        if (ModuleManager.S == null || ModuleManager.S.isEmpty()) {
            AbyssCommands.chat("\u00a7cNo module is published.");
            return;
}
        Category var4 = var1.length > 0 ? AbyssCommandList.category(var1[0]) : null;
        AbyssCommands.chat(SEP);
        int var5 = 0;
        int var6 = 0;
        for (Module var8 : ModuleManager.S) {
            if (var8 == null || var8.b() == null || var4 != null && var8.f() != var4) continue;
            if (var8.b().startsWith("?")) {
                ++var6;
                continue;
}
            AbyssCommands.chat((var8.o() ? "\u00a7a" : "\u00a77") + var8.b() + "\u00a78 [" + (Object)((Object)var8.f()) + "]" + (var8.h() == 0 ? "" : " \u00a78-> \u00a7f" + AbyssCommandList.keyName(var8.h())));
            ++var5;
}
        AbyssCommands.chat(SEP);
        AbyssCommands.chat("\u00a77" + var5 + " module(s)" + (var6 > 0 ? "\u00a78, " + var6 + " with no confirmed name were skipped" : ""));
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        ArrayList<String> var5 = new ArrayList<String>();
        if (var2 <= 1) {
            Category[] var6 = Category.values();
            for (int var7 = 0; var7 < var6.length; ++var7) {
                var5.add(var6[var7].c());
}
}
        return var5;
}
    private static Category category(String var0) {
        Category[] var1 = Category.values();
        for (int var2 = 0; var2 < var1.length; ++var2) {
            if (!var1[var2].c().equalsIgnoreCase(var0)) continue;
            return var1[var2];
}
        return null;
}
    private static String keyName(int var0) {
        String var1 = Keyboard.getKeyName((int)var0);
        return (var1 == null ? String.valueOf(var0) : var1).toUpperCase();
}
}