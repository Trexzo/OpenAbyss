/*
 * Decompiled with CFR 0.152.
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.command.impl.AbyssCommandStub;
import Abyss.internal.jnic.StockCommandRegistry;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import java.util.ArrayList;
import java.util.List;

public final class AbyssCommandHelp
extends Command {
    private static final String TITLE = "Here's a list of commands";
    private static final String LINE_PREFIX = "  \u00a7l.";
    private static final String ALIAS_SEP = "\u00a7r, \u00a7l.";
    private static final String FOOTER_HELP = "\u00a7bUse .help <command> to get usage of a specific command";
    private static final String FOOTER_MODULE = "\u00a7aUse .<module> to configure a module's setting";

    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"help"};
}
    @Override
    public void h(long var1) {
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        AbyssCommands.chat(TITLE);
        for (Command var7 : StockCommandRegistry.L) {
            try {
                boolean var9;
                String[] var8;
                if (var7 instanceof AbyssCommandStub) {
                    var8 = var7.e(0L);
                    var9 = false;
                    if (((AbyssCommandStub)var7).hasText()) {
                        ++var5;
}
                } else {
                    var8 = var7.e(0L);
                    var9 = true;
}
                AbyssCommands.chat(AbyssCommandHelp.aliasLine(var8));
                if (var9) {
                    ++var3;
                    continue;
}
                ++var4;
}
            catch (Throwable throwable) {}
}
        AbyssCommands.chat(FOOTER_HELP);
        AbyssCommands.chat(FOOTER_MODULE);
        AbyssCommands.chat("\u00a78[restored] " + var3 + " of " + (var3 + var4) + " commands have a restored body" + (var4 == 0 ? "." : "; the other " + var4 + " print the stock client's own text only (" + var5 + " with its real usage)."));
        AbyssCommands.chat("\u00a78[note] the .<module> line above is generic stock text; configure a module directly with \u00a7f.<module>\u00a78. " + AbyssCommandHelp.namedModules() + " modules are reachable by name.");
}
    @Override
    public void j(String[] var1, long var2) {
        String var4 = var1[0];
        if (var4.length() > 0 && var4.charAt(0) == '.') {
            var4 = var4.substring(1);
}
        for (Command var6 : StockCommandRegistry.L) {
            try {
                String[] var7 = var6.e(0L);
                for (int var8 = 0; var8 < var7.length; ++var8) {
                    if (var7[var8] == null || !var7[var8].equalsIgnoreCase(var4)) continue;
                    var6.h(var2);
                    return;
}
}
            catch (Throwable throwable) {
}
}
        AbyssCommands.chat("\u00a7cNo command named \u00a7f" + var4 + "\u00a7c.");
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        ArrayList<String> var5 = new ArrayList<String>();
        if (var2 <= 1) {
            for (Command var7 : StockCommandRegistry.L) {
                try {
                    String[] var8 = var7.e(0L);
                    if (var8 == null || var8.length <= 0) continue;
                    var5.add(var8[0]);
}
                catch (Throwable throwable) {}
}
}
        return var5;
}
    private static String aliasLine(String[] var0) {
        StringBuilder var1 = new StringBuilder(LINE_PREFIX);
        for (int var2 = 0; var2 < var0.length; ++var2) {
            if (var2 > 0) {
                var1.append(ALIAS_SEP);
}
            var1.append(var0[var2]);
}
        return var1.toString();
}
    private static int namedModules() {
        int var0 = 0;
        try {
            if (ModuleManager.S != null) {
                for (Module var2 : ModuleManager.S) {
                    if (var2 == null || var2.b() == null || var2.b().startsWith("?")) continue;
                    ++var0;
}
}
}
        catch (Throwable var3) {
            return -1;
}
        return var0;
}
}