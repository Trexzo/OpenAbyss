/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;

public final class AbyssCommandBind
extends Command {
    public static final long MODULE_Z_CARRIER = 118276941480361L;
    private static final int[] PROBE_CODES = new int[]{1, 48, 57};
    private static final int MOUSE_BASE = 1000;
    private static final int MOUSE_KEYCODE_BASE = -100;
    private static Boolean trusted;
    private static String gateNote;
    private static final String DESC = "\u00a7eBind module with a specific key to toggle it later";
    private static final String USAGE_HEADER = "Usage: ";
    private static final String[] USAGE;

    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"bind", "b"};
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat(DESC);
        AbyssCommands.chat("\u00a77Usage: ");
        for (int var4 = 0; var4 < USAGE.length; ++var4) {
            AbyssCommands.chat("\u00a7f" + USAGE[var4]);
}
        AbyssCommands.chat("\u00a78Key names are LWJGL names, e.g. R, LSHIFT, NUMPAD0.");
        int var3 = AbyssCommands.placeholderCount();
        if (var3 > 0) {
            AbyssCommands.chat("\u00a78" + var3 + " module(s) have no confirmed name yet and cannot be bound.");
}
}
    @Override
    public void j(String[] var1, long var2) {
        if (var1.length >= 1 && "list".equalsIgnoreCase(var1[0])) {
            AbyssCommandBind.list();
            return;
}
        if (var1.length >= 1 && "clear".equalsIgnoreCase(var1[0])) {
            AbyssCommandBind.clear();
            return;
}
        if (var1.length >= 1 && "remove".equalsIgnoreCase(var1[0])) {
            if (var1.length < 2) {
                AbyssCommands.chat("\u00a7cUsage: \u00a7f.bind remove <key...>");
            } else {
                AbyssCommandBind.removeKeys(var1);
}
            return;
}
        if (var1.length < 2) {
            this.h(0L);
            return;
}
        if (var1.length > 2) {
            AbyssCommandBind.bindMany(var1);
            return;
}
        Module var4 = AbyssCommands.module(var1[0]);
        if (var4 == null) {
            AbyssCommands.chat("\u00a7cNo module named \u00a7f" + var1[0] + "\u00a7c.");
            return;
}
        int var5 = AbyssCommandBind.parseKey(var1[1]);
        if (var5 < 0) {
            AbyssCommands.chat("\u00a7cUnknown key \u00a7f" + var1[1] + "\u00a7c. Use an LWJGL key name, or \u00a7fnone\u00a7c.");
            return;
}
        if (var4.S()) {
            AbyssCommands.chat("\u00a7c" + var4.b() + " has a fixed key binding; it cannot be changed.");
            return;
}
        if (!AbyssCommandBind.gateOk()) {
            AbyssCommands.chat("\u00a7cRefusing to write a key binding: the Module.z carrier gate did not pass.");
            AbyssCommands.chat("\u00a78" + gateNote);
            return;
}
        try {
            var4.z(118276941480361L, var5);
}
        catch (Throwable var7) {
            AbyssCommands.chat("\u00a7cModule.z threw: " + var7);
            return;
}
        int var6 = var4.h();
        if (var6 == 0) {
            AbyssCommands.chat("\u00a7a" + var4.b() + "\u00a77 is now unbound.");
        } else if (var6 == var5) {
            AbyssCommands.chat("\u00a7a" + var4.b() + "\u00a77 bound to \u00a7f" + AbyssCommandBind.keyName(var6) + "\u00a77.");
        } else {
            AbyssCommands.chat("\u00a7a" + var4.b() + "\u00a77 bound; stored code \u00a7f" + var6 + "\u00a77 (\u00a7f" + AbyssCommandBind.keyName(var6) + "\u00a77), remapped from " + var5 + ".");
}
        AbyssCommands.chat("\u00a78Not saved yet -- use \u00a77.config save <name>\u00a78 to persist it.");
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        ArrayList<String> var5 = new ArrayList<String>();
        if (var2 <= 1) {
            if (ModuleManager.S != null) {
                for (Module var7 : ModuleManager.S) {
                    if (var7 == null || var7.b() == null || var7.b().startsWith("?")) continue;
                    var5.add(var7.b());
}
}
            var5.add("list");
            var5.add("clear");
            var5.add("remove");
        } else if (var2 == 2) {
            var5.add("none");
            for (int var6 = 1; var6 < 256; ++var6) {
                String var8 = Keyboard.getKeyName((int)var6);
                if (var8 == null) continue;
                var5.add(var8);
}
}
        return var5;
}
    private static void bindMany(String[] var0) {
        int var1 = AbyssCommandBind.parseKey(var0[var0.length - 1]);
        if (var1 < 0) {
            AbyssCommands.chat("\u00a7cUnknown key \u00a7f" + var0[var0.length - 1] + "\u00a7c. Use an LWJGL key name, or \u00a7fnone\u00a7c.");
            return;
}
        if (!AbyssCommandBind.gateOk()) {
            AbyssCommands.chat("\u00a7cRefusing to write a key binding: the Module.z carrier gate did not pass.");
            AbyssCommands.chat("\u00a78" + gateNote);
            return;
}
        int var2 = 0;
        for (int var3 = 0; var3 < var0.length - 1; ++var3) {
            Module var4 = AbyssCommands.module(var0[var3]);
            if (var4 == null) {
                AbyssCommands.chat("\u00a7cNo module named \u00a7f" + var0[var3] + "\u00a7c.");
                continue;
}
            if (var4.S()) {
                AbyssCommands.chat("\u00a7c" + var4.b() + " has a fixed key binding; skipped.");
                continue;
}
            if (!AbyssCommandBind.write(var4, var1)) continue;
            ++var2;
}
        AbyssCommands.chat("\u00a77" + var2 + " module(s) bound to \u00a7f" + AbyssCommandBind.keyName(var1) + "\u00a77.");
        if (var2 > 0) {
            AbyssCommands.chat("\u00a78Not saved yet -- use \u00a77.config save <name>\u00a78 to persist it.");
}
}
    private static void removeKeys(String[] var0) {
        if (!AbyssCommandBind.gateOk()) {
            AbyssCommands.chat("\u00a7cRefusing to write a key binding: the Module.z carrier gate did not pass.");
            AbyssCommands.chat("\u00a78" + gateNote);
            return;
}
        int var1 = 0;
        for (int var2 = 1; var2 < var0.length; ++var2) {
            int var3 = AbyssCommandBind.parseKey(var0[var2]);
            if (var3 <= 0) {
                AbyssCommands.chat("\u00a7cUnknown key \u00a7f" + var0[var2] + "\u00a7c.");
                continue;
}
            if (ModuleManager.S == null) continue;
            for (Module var5 : ModuleManager.S) {
                if (var5 == null || var5.S() || var5.h() != var3 || !AbyssCommandBind.write(var5, 0)) continue;
                AbyssCommands.chat("\u00a77" + var5.b() + " \u00a78unbound from \u00a7f" + AbyssCommandBind.keyName(var3));
                ++var1;
}
}
        AbyssCommands.chat("\u00a77" + var1 + " binding(s) removed.");
        if (var1 > 0) {
            AbyssCommands.chat("\u00a78Not saved yet -- use \u00a77.config save <name>\u00a78 to persist it.");
}
}
    private static void clear() {
        if (!AbyssCommandBind.gateOk()) {
            AbyssCommands.chat("\u00a7cRefusing to write a key binding: the Module.z carrier gate did not pass.");
            AbyssCommands.chat("\u00a78" + gateNote);
            return;
}
        int var0 = 0;
        if (ModuleManager.S != null) {
            for (Module var2 : ModuleManager.S) {
                if (var2 == null || var2.S() || var2.h() == 0 || !AbyssCommandBind.write(var2, 0)) continue;
                ++var0;
}
}
        AbyssCommands.chat("\u00a77" + var0 + " binding(s) cleared.");
        if (var0 > 0) {
            AbyssCommands.chat("\u00a78Not saved yet -- use \u00a77.config save <name>\u00a78 to persist it.");
}
}
    private static boolean write(Module var0, int var1) {
        try {
            var0.z(118276941480361L, var1);
            return true;
}
        catch (Throwable var3) {
            AbyssCommands.chat("\u00a7cModule.z threw for " + var0.b() + ": " + var3);
            return false;
}
}
    private static void list() {
        int var0 = 0;
        if (ModuleManager.S != null) {
            for (Module var2 : ModuleManager.S) {
                if (var2 == null || var2.b() == null || var2.b().startsWith("?") || var2.h() == 0) continue;
                AbyssCommands.chat("\u00a77" + var2.b() + " \u00a78-> \u00a7f" + AbyssCommandBind.keyName(var2.h()));
                ++var0;
}
}
        if (var0 == 0) {
            AbyssCommands.chat("\u00a77No module is bound.");
}
}
    private static int parseKey(String var0) {
        if ("none".equalsIgnoreCase(var0) || "null".equalsIgnoreCase(var0) || "0".equals(var0)) {
            return 0;
}
        int var1 = Keyboard.getKeyIndex((String)var0.toUpperCase());
        if (var1 != 0) {
            return var1;
}
        try {
            int var2 = Integer.parseInt(var0);
            return var2 >= 0 ? var2 : -1;
}
        catch (NumberFormatException var3) {
            return -1;
}
}
    private static String keyName(int var0) {
        if (var0 == 0) {
            return "NONE";
}
        String var1 = Keyboard.getKeyName((int)var0);
        return var1 == null ? String.valueOf(var0) : var1;
}
    static synchronized boolean gateOk() {
        String var1;
        if (trusted != null) {
            return trusted;
}
        boolean var0 = false;
        try {
            Module var2 = new Module(0L);
            var1 = "";
            for (int var3 = 0; var3 < PROBE_CODES.length; ++var3) {
                var2.z(118276941480361L, PROBE_CODES[var3]);
                if (var2.h() == PROBE_CODES[var3]) continue;
                var1 = "keyboard code " + PROBE_CODES[var3] + " stored as " + var2.h();
                break;
}
            if (var1.isEmpty()) {
                var2.z(118276941480361L, 1000);
                int var4 = var2.h();
                var2.z(118276941480361L, 1001);
                int var5 = var2.h();
                if (var4 == -100 && var5 == -99) {
                    var0 = true;
                    var1 = "canonical (identity for keyboard codes; 1000->" + var4 + ", " + 1001 + "->" + var5 + ")";
                } else {
                    var1 = "mouse fold is 1000->" + var4 + ", " + 1001 + "->" + var5 + "; expected " + -100 + ", " + -99;
}
}
}
        catch (Throwable var6) {
            var1 = "threw " + var6;
}
        gateNote = "Module.z carrier gate: " + (var0 ? "TRUSTED" : "REFUSED") + " -- " + var1;
        trusted = var0;
        return var0;
}
    static String gateNote() {
        return gateNote;
}
    static {
        gateNote = "not run";
        USAGE = new String[]{"  .bind remove <key...>", "  .bind <module...> <key>", "  .bind clear", "  .bind list"};
}
}