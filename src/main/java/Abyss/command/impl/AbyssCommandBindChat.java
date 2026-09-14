/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package Abyss.command.impl;

import Abyss.AbyssClient;
import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.internal.restore.AbyssCommandData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.input.Keyboard;

public final class AbyssCommandBindChat
extends Command {
    private static final String DESC = "\u00a7eBind a chat message to a specific key to send it later (Support \\n)";
    private static final String USAGE_HEADER = "Usage: ";
    private static final String[] USAGE = new String[]{"  .bindchat clear", "  .bindchat <chat message> <key>", "  .bindchat remove <key...>", "  .bindchat list"};

    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"bindchat", "bc"};
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat(DESC);
        AbyssCommands.chat("\u00a77Usage: ");
        for (int var3 = 0; var3 < USAGE.length; ++var3) {
            AbyssCommands.chat("\u00a7f" + USAGE[var3]);
}
        AbyssCommands.chat("\u00a78" + AbyssCommandBindChat.map().size() + " bind(s). Key names are LWJGL names, e.g. R, LSHIFT, NUMPAD0.");
}
    @Override
    public void j(String[] var1, long var2) {
        if (var1.length == 1 && "list".equalsIgnoreCase(var1[0])) {
            AbyssCommandBindChat.list();
            return;
}
        if (var1.length == 1 && "clear".equalsIgnoreCase(var1[0])) {
            AbyssCommandBindChat.clear();
            return;
}
        if (var1.length >= 1 && "remove".equalsIgnoreCase(var1[0])) {
            if (var1.length < 2) {
                AbyssCommands.chat("\u00a7cUsage: \u00a7f.bindchat remove <key...>");
            } else {
                AbyssCommandBindChat.remove(var1);
}
            return;
}
        if (var1.length < 2) {
            this.h(0L);
            return;
}
        AbyssCommandBindChat.add(var1);
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        ArrayList<String> var5 = new ArrayList<String>();
        if (var2 <= 1) {
            var5.addAll(Arrays.asList("list", "clear", "remove"));
}
        return var5;
}
    private static Map<Integer, String> map() {
        if (AbyssClient.H == null) {
            AbyssClient.H = new LinkedHashMap<Integer, String>();
}
        return AbyssClient.H;
}
    private static void add(String[] var0) {
        int var1 = AbyssCommandBindChat.parseKey(var0[var0.length - 1]);
        if (var1 <= 0) {
            AbyssCommands.chat("\u00a7cUnknown key \u00a7f" + var0[var0.length - 1] + "\u00a7c. Use an LWJGL key name.");
            return;
}
        String[] var2 = Arrays.copyOf(var0, var0.length - 1);
        StringBuilder var3 = new StringBuilder();
        for (int var4 = 0; var4 < var2.length; ++var4) {
            if (var4 > 0) {
                var3.append(' ');
}
            var3.append(var2[var4]);
}
        String var5 = var3.toString();
        if (var5.trim().isEmpty()) {
            AbyssCommands.chat("\u00a7cThe message is empty.");
            return;
}
        String var6 = AbyssCommandBindChat.map().put(var1, var5);
        AbyssCommands.chat((var6 == null ? "\u00a7aBound " : "\u00a7aRebound ") + "\u00a7f" + AbyssCommandBindChat.keyName(var1) + "\u00a7a -> \u00a7r" + var5);
        AbyssCommandBindChat.save();
}
    private static void remove(String[] var0) {
        int var1 = 0;
        for (int var2 = 1; var2 < var0.length; ++var2) {
            int var3 = AbyssCommandBindChat.parseKey(var0[var2]);
            if (var3 <= 0) {
                AbyssCommands.chat("\u00a7cUnknown key \u00a7f" + var0[var2] + "\u00a7c.");
                continue;
}
            if (AbyssCommandBindChat.map().remove(var3) == null) {
                AbyssCommands.chat("\u00a77Nothing was bound to \u00a7f" + AbyssCommandBindChat.keyName(var3));
                continue;
}
            AbyssCommands.chat("\u00a7c- \u00a7f" + AbyssCommandBindChat.keyName(var3));
            ++var1;
}
        if (var1 > 0) {
            AbyssCommandBindChat.save();
}
}
    private static void clear() {
        int var0 = AbyssCommandBindChat.map().size();
        if (var0 == 0) {
            AbyssCommands.chat("\u00a77There is no chat bind.");
            return;
}
        AbyssCommandBindChat.map().clear();
        AbyssCommands.chat("\u00a77" + var0 + " chat bind(s) cleared.");
        AbyssCommandBindChat.save();
}
    private static void list() {
        if (AbyssCommandBindChat.map().isEmpty()) {
            AbyssCommands.chat("\u00a77There is no chat bind.");
            return;
}
        for (Map.Entry<Integer, String> var1 : AbyssCommandBindChat.map().entrySet()) {
            AbyssCommands.chat("\u00a7f" + AbyssCommandBindChat.keyName(var1.getKey()) + " \u00a78-> \u00a7r" + var1.getValue());
}
        AbyssCommands.chat("\u00a77" + AbyssCommandBindChat.map().size() + " chat bind(s).");
}
    private static void save() {
        AbyssCommands.chat(AbyssCommandData.saveChatBinds() ? "\u00a78Saved to the \"chatBinds\" key of current.json." : "\u00a7cThe write to current.json FAILED; this bind is memory-only.");
}
    private static int parseKey(String var0) {
        int var1 = Keyboard.getKeyIndex((String)var0.toUpperCase());
        if (var1 != 0) {
            return var1;
}
        try {
            return Integer.parseInt(var0);
}
        catch (NumberFormatException var2) {
            return -1;
}
}
    private static String keyName(int var0) {
        String var1 = Keyboard.getKeyName((int)var0);
        return (var1 == null ? String.valueOf(var0) : var1).toUpperCase();
}
}