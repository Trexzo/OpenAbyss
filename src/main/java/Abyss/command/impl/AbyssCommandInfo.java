/*
 * Decompiled with CFR 0.152.
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.util.BuildInfo;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class AbyssCommandInfo
extends Command {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String SEP = "\u00a78-----------------------------";

    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"info"};
}
    @Override
    public void h(long var1) {
        this.j(new String[0], 0L);
}
    @Override
    public void j(String[] var1, long var2) {
        AbyssCommands.chat(SEP);
        AbyssCommands.chat("\u00a7fAbout " + AbyssCommandInfo.field("K", "Abyss"));
        AbyssCommands.chat("\u00a77Version: \u00a7f" + AbyssCommandInfo.field("L", "?"));
        AbyssCommands.chat("\u00a77Author: \u00a7f" + AbyssCommandInfo.field("g", "?"));
        AbyssCommands.chat("\u00a77Release: \u00a7f" + AbyssCommandInfo.field("Z", "?"));
        AbyssCommands.chat("\u00a77Build: \u00a7f" + AbyssCommandInfo.field("q", "?"));
        String var4 = AbyssCommandInfo.field("W", "");
        if (!var4.isEmpty()) {
            AbyssCommands.chat("\u00a77Current user: \u00a7f" + var4);
}
        AbyssCommands.chat("\u00a77Current system time: \u00a7f" + new SimpleDateFormat(PATTERN).format(new Date()));
        AbyssCommands.chat("\u00a78" + AbyssCommandInfo.field("B", ""));
        AbyssCommands.chat(SEP);
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        return new ArrayList();
}
    private static String field(String var0, String var1) {
        try {
            Field var2 = BuildInfo.class.getDeclaredField(var0);
            var2.setAccessible(true);
            Object var3 = var2.get(null);
            return var3 == null ? var1 : String.valueOf(var3);
}
        catch (Throwable var4) {
            return var1;
}
}
}