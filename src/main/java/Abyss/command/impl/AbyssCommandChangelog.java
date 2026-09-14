/*
 * Decompiled with CFR 0.152.
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.util.BuildInfo;
import java.util.ArrayList;
import java.util.List;

public final class AbyssCommandChangelog
extends Command {
    private static final String SEP = "\u00a78-----------------------------";

    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"changelog"};
}
    @Override
    public void h(long var1) {
        this.j(new String[0], 0L);
}
    @Override
    public void j(String[] var1, long var2) {
        List<String> var4 = AbyssCommandChangelog.entries();
        AbyssCommands.chat(SEP);
        if (var4 == null || var4.isEmpty()) {
            AbyssCommands.chat("\u00a77No changelog entry for this build.");
        } else {
            for (int var5 = 0; var5 < var4.size(); ++var5) {
                AbyssCommands.chat("\u00a77- \u00a7f" + var4.get(var5));
}
}
        AbyssCommands.chat(SEP);
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        return new ArrayList();
}
    private static List<String> entries() {
        try {
            return BuildInfo.T;
}
        catch (Throwable var0) {
            return null;
}
}
}