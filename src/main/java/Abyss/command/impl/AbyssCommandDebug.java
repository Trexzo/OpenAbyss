/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 */
package Abyss.command.impl;

import Abyss.AbyssClient;
import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.command.impl.AbyssCommandBind;
import Abyss.internal.jnic.StockCommandRegistry;
import Abyss.internal.restore.AbyssCommandData;
import Abyss.module.ModuleManager;
import Abyss.ui.ModuleTagRenderer;
import Abyss.util.ScoreboardReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public final class AbyssCommandDebug
extends Command {
    private static final String SCOREBOARD_DUMP = "debug_scoreboard.txt";
    private static final String DISPLAYNAME_DUMP = "debug_displayname.txt";
    private static final String[] USAGE = new String[]{"  .debug <username | nickname | token | userid>", "  .debug [true | false]", "  .debug <status>", "  .debug <dumpdisplayname> <player name>", "  .debug <dumpscoreboard>"};

    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"debug", "dbg"};
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat("\u00a77Usage:");
        for (int var3 = 0; var3 < USAGE.length; ++var3) {
            AbyssCommands.chat("\u00a7f" + USAGE[var3]);
}
        AbyssCommands.chat("\u00a77Debug output is currently \u00a7f" + AbyssCommandDebug.flag());
        AbyssCommands.chat("\u00a78username/nickname/token/userid read the removed login layer and are NOT restored; the rest are.");
}
    @Override
    public void j(String[] var1, long var2) {
        String var4 = var1[0].toLowerCase();
        if ("true".equals(var4) || "false".equals(var4)) {
            AbyssCommandDebug.setFlag(Boolean.parseBoolean(var4));
        } else if ("status".equals(var4)) {
            AbyssCommandDebug.status();
        } else if ("dumpscoreboard".equals(var4)) {
            AbyssCommandDebug.dumpScoreboard();
        } else if ("dumpdisplayname".equals(var4)) {
            if (var1.length < 2) {
                AbyssCommands.chat("\u00a7cUsage: \u00a7f.debug dumpdisplayname <player name>");
            } else {
                AbyssCommandDebug.dumpDisplayName(var1[1]);
}
        } else if ("username".equals(var4) || "nickname".equals(var4) || "token".equals(var4) || "userid".equals(var4)) {
            AbyssCommands.chat("\u00a7c.debug " + var4 + " reads the account session, which this build does not have: the login layer was removed and its class is still native.");
            AbyssCommands.chat("\u00a78For the in-game name use \u00a77.ign\u00a78 instead.");
        } else {
            this.h(0L);
}
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        Minecraft var6;
        ArrayList<String> var5 = new ArrayList<String>();
        if (var2 <= 1) {
            var5.addAll(Arrays.asList("true", "false", "status", "dumpscoreboard", "dumpdisplayname", "username", "nickname", "token", "userid"));
        } else if (var2 == 2 && var1.length > 0 && "dumpdisplayname".equalsIgnoreCase(var1[0]) && (var6 = Minecraft.func_71410_x()) != null && var6.field_71441_e != null) {
            for (EntityPlayer var8 : var6.field_71441_e.field_73010_i) {
                var5.add(var8.func_70005_c_());
}
}
        return var5;
}
    private static String flag() {
        try {
            return String.valueOf(ModuleTagRenderer.X);
}
        catch (Throwable var0) {
            return "unavailable (" + var0 + ")";
}
}
    private static void setFlag(boolean var0) {
        try {
            ModuleTagRenderer.X = var0;
            AbyssCommands.chat("\u00a77Debug output is now \u00a7f" + ModuleTagRenderer.X);
            AbyssCommands.chat("\u00a78That is the EventBus handler-exception printer: with it on, a throw out of any subscriber is echoed to chat once per exception class per interval.");
}
        catch (Throwable var2) {
            AbyssCommands.chat("\u00a7cCould not write the debug flag: " + var2);
}
}
    private static void status() {
        AbyssCommands.chat("\u00a77--- Abyss debug status ---");
        AbyssCommands.chat("\u00a77debug output \u00a7f" + AbyssCommandDebug.flag());
        AbyssCommands.chat("\u00a77modules published \u00a7f" + AbyssCommandDebug.moduleCount() + "\u00a77, unnamed \u00a7f" + AbyssCommands.placeholderCount());
        AbyssCommands.chat("\u00a77commands registered \u00a7f" + (StockCommandRegistry.L == null ? 0 : StockCommandRegistry.L.size()));
        AbyssCommands.chat("\u00a77chat binds \u00a7f" + (AbyssClient.H == null ? 0 : AbyssClient.H.size()));
        AbyssCommands.chat("\u00a77data dir \u00a7f" + AbyssCommandData.dirFile().getPath());
        AbyssCommands.chat("\u00a77" + AbyssCommandBind.gateNote());
}
    private static int moduleCount() {
        try {
            return ModuleManager.S == null ? -1 : ModuleManager.S.size();
}
        catch (Throwable var0) {
            return -1;
}
}
    private static void dumpScoreboard() {
        ArrayList<String> var0;
        try {
            var0 = ScoreboardReader.l();
}
        catch (Throwable var4) {
            AbyssCommands.chat("\u00a7cThe scoreboard reader threw: " + var4);
            return;
}
        if (var0 == null || var0.isEmpty()) {
            AbyssCommands.chat("\u00a77No sidebar objective is showing; nothing to dump.");
            return;
}
        Path var1 = AbyssCommandData.resolve(SCOREBOARD_DUMP);
        try {
            Files.write(var1, var0, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
}
        catch (Throwable var3) {
            AbyssCommands.chat("\u00a7cWrite failed: " + var3);
            return;
}
        for (int var2 = 0; var2 < var0.size(); ++var2) {
            AbyssCommands.chat("\u00a78" + var2 + " \u00a7r" + var0.get(var2));
}
        AbyssCommands.chat("\u00a7a" + var0.size() + " line(s) -> \u00a7f" + var1.toAbsolutePath());
}
    private static void dumpDisplayName(String var0) {
        Minecraft var1 = Minecraft.func_71410_x();
        if (var1 == null || var1.field_71441_e == null) {
            AbyssCommands.chat("\u00a7cNot in a world.");
            return;
}
        for (EntityPlayer var3 : var1.field_71441_e.field_73010_i) {
            boolean var7;
            if (var3 == null || !var0.equalsIgnoreCase(var3.func_70005_c_())) continue;
            String var4 = var3.func_145748_c_() == null ? "" : var3.func_145748_c_().func_150254_d();
            String var5 = var3.func_70005_c_() + "\n" + var4;
            Path var6 = AbyssCommandData.resolve(DISPLAYNAME_DUMP);
            try {
                Files.write(var6, var5.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
                var7 = true;
}
            catch (Throwable var9) {
                var7 = false;
}
            AbyssCommands.chat("\u00a77name \u00a7f" + var3.func_70005_c_());
            AbyssCommands.chat("\u00a77display \u00a7r" + var4);
            AbyssCommands.chat("\u00a77raw \u00a7f" + var4.replace('\u00a7', '&'));
            try {
                GuiScreen.func_146275_d((String)var4);
}
            catch (Throwable throwable) {
                // empty catch block
}
            AbyssCommands.chat(var7 ? "\u00a78copied to the clipboard and written to \u00a77" + var6.toAbsolutePath() : "\u00a78copied to the clipboard; the file write failed");
            return;
}
        AbyssCommands.chat("\u00a7cNo player named \u00a7f" + var0 + "\u00a7c is loaded.");
}
}