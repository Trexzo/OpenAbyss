/*
 * Decompiled with CFR 0.152.
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.internal.restore.AbyssCommandData;
import Abyss.ui.screen.MainMenuTheme;
import java.util.ArrayList;
import java.util.List;

public final class AbyssCommandMenu
extends Command {
    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"menu"};
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat("\u00a77Usage: \u00a7f.menu <" + AbyssCommandMenu.join(AbyssCommandMenu.options(), " | ") + ">");
        AbyssCommands.chat("\u00a77Current: \u00a7f" + AbyssCommandMenu.current() + "\u00a77, music \u00a7f" + AbyssCommandMenu.music());
        AbyssCommands.chat("\u00a78Persisted to \u00a77menu.txt\u00a78 and \u00a77menu_music.txt\u00a78 next to current.json.");
}
    @Override
    public void j(String[] var1, long var2) {
        String var4 = var1[0].trim().toUpperCase();
        List<String> var5 = AbyssCommandMenu.options();
        if (var5.isEmpty()) {
            AbyssCommands.chat("\u00a7cThe menu-background setting is not available in this build.");
            return;
}
        if (!var5.contains(var4)) {
            AbyssCommands.chat("\u00a7cUnknown background \u00a7f" + var1[0] + "\u00a7c. Known: \u00a7f" + AbyssCommandMenu.join(var5, ", "));
            return;
}
        try {
            MainMenuTheme.mode.i(var4);
}
        catch (Throwable var8) {
            AbyssCommands.chat("\u00a7cThe setting refused the write: " + var8);
            return;
}
        String var6 = AbyssCommandMenu.current();
        if (!var4.equals(var6)) {
            AbyssCommands.chat("\u00a7cAsked for \u00a7f" + var4 + "\u00a7c but the setting now reads \u00a7f" + var6);
            return;
}
        boolean var7 = AbyssCommandData.saveMenu() & AbyssCommandData.saveMenuMusic();
        AbyssCommands.chat("\u00a7aMenu background \u00a7f" + var6 + (var7 ? "\u00a77, saved." : " \u00a7cbut the write to menu.txt FAILED."));
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        ArrayList<String> var5 = new ArrayList<String>();
        if (var2 <= 1) {
            var5.addAll(AbyssCommandMenu.options());
}
        return var5;
}
    private static List<String> options() {
        try {
            ArrayList var0 = MainMenuTheme.mode.S();
            return var0 == null ? new ArrayList() : var0;
}
        catch (Throwable var1) {
            return new ArrayList<String>();
}
}
    private static String current() {
        try {
            return MainMenuTheme.mode.Y();
}
        catch (Throwable var0) {
            return "?";
}
}
    private static String music() {
        try {
            return String.valueOf(MainMenuTheme.music.c());
}
        catch (Throwable var0) {
            return "?";
}
}
    private static String join(List<String> var0, String var1) {
        StringBuilder var2 = new StringBuilder();
        for (int var3 = 0; var3 < var0.size(); ++var3) {
            if (var3 > 0) {
                var2.append(var1);
}
            var2.append(var0.get(var3));
}
        return var2.toString();
}
}