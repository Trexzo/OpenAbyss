/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 */
package Abyss.command;

import Abyss.AbyssClient;
import Abyss.command.Command;
import Abyss.command.impl.AbyssCommandBind;
import Abyss.command.impl.AbyssCommandBindChat;
import Abyss.command.impl.AbyssCommandChangelog;
import Abyss.command.impl.AbyssCommandCheaters;
import Abyss.command.impl.AbyssCommandConfig;
import Abyss.command.impl.AbyssCommandDebug;
import Abyss.command.impl.AbyssCommandHelp;
import Abyss.command.impl.AbyssCommandIgn;
import Abyss.command.impl.AbyssCommandInfo;
import Abyss.command.impl.AbyssCommandList;
import Abyss.command.impl.AbyssCommandMenu;
import Abyss.command.impl.AbyssCommandNames;
import Abyss.command.impl.AbyssCommandPartySpam;
import Abyss.command.impl.AbyssCommandReset;
import Abyss.command.impl.AbyssCommandStub;
import Abyss.command.impl.AbyssCommandSuffix;
import Abyss.command.impl.AbyssCommandToggle;
import Abyss.command.impl.AbyssCommandVisible;
import Abyss.command.impl.StockCommandModuleSetting;
import Abyss.internal.jnic.StockCommandRegistry;
import Abyss.internal.restore.AbyssCommandSelect;
import Abyss.internal.restore.AbyssCommandData;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.impl.misc.CommandLine;
import Abyss.ui.swing.ConfigManagerWindow;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public final class AbyssCommands {
    public static final char PREFIX = '.';
    private static boolean installed;

    private AbyssCommands() {
}
    public static void install(List<String> var0) {
        if (installed) {
            return;
}
        installed = true;
        if (StockCommandRegistry.L == null) {
            StockCommandRegistry.L = new LinkedHashSet<Command>();
}
        StockCommandRegistry.J = new StockCommandModuleSetting();
        StockCommandRegistry.L.add(new AbyssCommandBind());
        StockCommandRegistry.L.add(new AbyssCommandBindChat());
        StockCommandRegistry.L.add(new AbyssCommandChangelog());
        StockCommandRegistry.L.add(new AbyssCommandCheaters());
        StockCommandRegistry.L.add(new AbyssCommandConfig());
        StockCommandRegistry.L.add(new AbyssCommandDebug());
        StockCommandRegistry.L.add(new AbyssCommandNames(0, "\u00a7eManage your blacklisted players", new String[]{"  .enemy list", "  .enemy <add | remove> <name...>", "  .enemy clear"}, "enemy", "e", "target", "bl", "blacklist"));
        StockCommandRegistry.L.add(new AbyssCommandNames(1, "\u00a7eManage your whitelisted players", new String[]{"  .friend <add | remove> <name...>", "  .friend list", "  .friend clear"}, "friend", "f", "wl", "whitelist"));
        StockCommandRegistry.L.add(new AbyssCommandVisible(false, "\u00a7eHide module in the ArrayList", "hide", "h"));
        StockCommandRegistry.L.add(new AbyssCommandHelp());
        StockCommandRegistry.L.add(new AbyssCommandInfo());
        StockCommandRegistry.L.add(new AbyssCommandIgn());
        StockCommandRegistry.L.add(new AbyssCommandList());
        StockCommandRegistry.L.add(new AbyssCommandMenu());
        StockCommandRegistry.L.add(new AbyssCommandPartySpam());
        StockCommandRegistry.L.add(new AbyssCommandReset());
        StockCommandRegistry.L.add(new AbyssCommandVisible(true, "\u00a7eShow module in the ArrayList", "show", "s"));
        StockCommandRegistry.L.add(new AbyssCommandSuffix());
        StockCommandRegistry.L.add(new AbyssCommandToggle());
        try {
            AbyssCommandData.load();
}
        catch (Throwable throwable) {
            AbyssClient.recordFeatureFailure("AbyssCommands", "command-data-load", throwable);
}
        int var10 = 0;
        int var11 = 0;
        for (Command var12 : StockCommandRegistry.L) {
            if (var12 instanceof AbyssCommandStub) {
                if (!((AbyssCommandStub)var12).hasText()) continue;
                ++var11;
                continue;
}
            ++var10;
}
        try {
            AbyssCommands.note(var0);
}
        catch (Throwable throwable) {
            AbyssClient.recordFeatureFailure("AbyssCommands", "registry-note", throwable);
}
        if (var0 != null) {
            // empty if block
}
}
    private static void note(List<String> var0) {
        String var2;
        Module var1 = ModuleManager.o == null ? null : ModuleManager.o.get(CommandLine.class);
        String string = var2 = var1 == null ? null : var1.b();
        if (var2 == null || var2.startsWith("?")) {
            // empty if block
}
}
    public static boolean dispatch(String var0) {
        if (var0 == null) {
            return false;
}
        String var1 = var0.trim();
        if (var1.length() < 2 || var1.charAt(0) != '.') {
            return false;
}
        try {
            String var2 = var1.substring(1);
            String[] var3 = var2.split("\\s+");
            if (var3.length == 0 || var3[0].isEmpty()) {
                return false;
}
            Command var4 = AbyssCommands.find(var3[0]);
            boolean moduleSetting = false;
            if (var4 == null && AbyssCommands.module(var3[0]) != null && StockCommandRegistry.J != null) {
                var4 = StockCommandRegistry.J;
                moduleSetting = true;
}
            if (var4 == null) {
                AbyssCommands.chat("\u00a7cUnknown command \u00a7f" + var3[0] + "\u00a7c. Known: " + AbyssCommands.names());
                return true;
}
            if (moduleSetting) {
                var4.j(var3, 0L);
                return true;
}
            String[] var5 = new String[var3.length - 1];
            System.arraycopy(var3, 1, var5, 0, var5.length);
            if (var5.length == 0) {
                var4.h(0L);
            } else {
                var4.j(var5, 0L);
}
            return true;
}
        catch (Throwable var6) {
            AbyssCommands.chat("\u00a7cCommand failed: " + var6);
            return true;
}
}
    private static Command find(String var0) {
        if (StockCommandRegistry.L == null) {
            return null;
}
        for (Command var2 : StockCommandRegistry.L) {
            try {
                String[] var3 = var2.e(0L);
                if (var3 == null) continue;
                for (int var4 = 0; var4 < var3.length; ++var4) {
                    if (var3[var4] == null || !var3[var4].equalsIgnoreCase(var0)) continue;
                    return var2;
}
}
            catch (Throwable throwable) {
                AbyssClient.recordFeatureFailure("AbyssCommands", "alias-resolve", throwable);
}
}
        return null;
}
    public static String selfTest() {
        try {
            if (StockCommandRegistry.L == null) {
                return "FAIL registry-null";
}
            int count = 0;
            int primaryResolved = 0;
            for (Command command : StockCommandRegistry.L) {
                if (command == null) {
                    return "FAIL null-command";
}
                String[] aliases = command.e(0L);
                if (aliases == null || aliases.length == 0 || aliases[0] == null || aliases[0].trim().isEmpty()) {
                    return "FAIL aliases " + command.getClass().getName();
}
                String fallback = AbyssCommands.nativeBaseFallback(command);
                if (fallback != null) {
                    return "FAIL native-base-fallback " + command.getClass().getName() + "." + fallback;
}
                ++count;
                if (AbyssCommands.find(aliases[0]) != command) {
                    return "FAIL resolve " + aliases[0] + " -> " + command.getClass().getName();
}
                ++primaryResolved;
}
            if (count != 19) {
                return "FAIL count " + count + "/19";
}
            if (StockCommandRegistry.J == null) {
                return "FAIL module-setting-null";
}
            String moduleSettingTest = StockCommandModuleSetting.selfTest();
            if (!moduleSettingTest.startsWith("PASS")) {
                return "FAIL module-setting " + moduleSettingTest;
}
            int placeholders = AbyssCommands.placeholderCount();
            if (placeholders != 0) {
                return "FAIL module-placeholders " + placeholders;
}
            if (!AbyssCommandBind.gateOk()) {
                return "FAIL keybind-gate " + AbyssCommandBind.gateNote();
}
            String configTest = AbyssCommandConfig.selfTest();
            if (!configTest.startsWith("PASS")) {
                return "FAIL config-metadata " + configTest;
}
            String resetTest = AbyssCommandReset.selfTest();
            if (!resetTest.startsWith("PASS")) {
                return "FAIL reset " + resetTest;
}
            String selectTest = AbyssCommandSelect.selfTest();
            if (!selectTest.startsWith("PASS")) {
                return "FAIL selector " + selectTest;
}
            return "PASS commands=" + count + " primaryAliases=" + primaryResolved
                    + " moduleSetting=PASS keybind=TRUSTED config=PASS reset=PASS selector=PASS";
}
        catch (Throwable throwable) {
            return "FAIL " + throwable.getClass().getName() + ": " + throwable.getMessage();
}
}
    private static String nativeBaseFallback(Command command) {
        try {
            if (command.getClass().getMethod("J").getDeclaringClass() == Command.class) {
                return "J";
}
            if (command.getClass().getMethod("e", Long.TYPE).getDeclaringClass() == Command.class) {
                return "e";
}
            if (command.getClass().getMethod("g", String[].class, Integer.TYPE, Long.TYPE).getDeclaringClass() == Command.class) {
                return "g";
}
            if (command.getClass().getMethod("h", Long.TYPE).getDeclaringClass() == Command.class) {
                return "h";
}
            if (command.getClass().getMethod("j", String[].class, Long.TYPE).getDeclaringClass() == Command.class) {
                return "j";
}
            return null;
}
        catch (Throwable failure) {
            return "reflection:" + failure.getClass().getName();
}
}

    private static String names() {
        StringBuilder var0 = new StringBuilder();
        if (StockCommandRegistry.L != null) {
            for (Command var2 : StockCommandRegistry.L) {
                try {
                    String[] var3 = var2.e(0L);
                    if (var3 == null || var3.length <= 0 || var3[0] == null) continue;
                    if (var0.length() > 0) {
                        var0.append(", ");
}
                    var0.append('.').append(var3[0]);
}
                catch (Throwable throwable) {
                    AbyssClient.recordFeatureFailure("AbyssCommands", "command-name-list", throwable);
}
}
}
        return var0.toString();
}
    public static void chat(String var0) {
        try {
            Minecraft var1;
            if (ConfigManagerWindow.D == null) {
                ConfigManagerWindow.D = new ArrayList<String>();
}
            if ((var1 = Minecraft.getMinecraft()) != null && var1.ingameGUI != null) {
                var1.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(var0));
}
            ConfigManagerWindow.D.add(var0);
}
        catch (Throwable throwable) {
            AbyssClient.recordFeatureFailure("AbyssCommands", "chat-output", throwable);
}
}
    public static Module module(String var0) {
        if (ModuleManager.S == null || var0 == null) {
            return null;
}
        for (Module var2 : ModuleManager.S) {
            String var3;
            if (var2 == null || (var3 = var2.b()) == null || var3.startsWith("?") || !var3.equalsIgnoreCase(var0)) continue;
            return var2;
}
        return null;
}
    public static int placeholderCount() {
        int var0 = 0;
        try {
            if (ModuleManager.S != null) {
                for (Module var2 : ModuleManager.S) {
                    if (var2 == null || var2.b() == null || !var2.b().startsWith("?")) continue;
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