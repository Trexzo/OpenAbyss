/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ASM.Hooks.Gui;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.internal.ChatInputHandler;
import Abyss.module.Modules;
import Abyss.module.impl.misc.CommandLine;

public class GuiChatHooks {
    private static long public static String modifyAutoCompleteMessage(String var0) {
        return !var0.isEmpty() && Modules.J(CommandLine.class).o() && var0.startsWith(".") ? GuiChatHooks.getLastArgAfterDot(var0) : var0;
}
    public static void onAutocompletePlayerNames(CallbackInfo var0) {
        if (!ChatInputHandler.E.isEmpty()) {
            var0.cancel();
}
}
    private static String getLastArgAfterDot(String var0) {
        int var3 = var0.indexOf(46);
        if (var3 == -1) {
            return "";
}
        String var4 = var0.substring(var3 + 1);
        int var5 = var4.lastIndexOf(32);
        return var5 == -1 ? "" : var4.substring(var5 + 1);
}
    public static void onAutocompleteResponse(String[] var0, CallbackInfo var1) {
        if (!ChatInputHandler.E.isEmpty() || GuiChatHooks.isForgeModAutocompleteNoise(var0)) {
            var1.cancel();
}
}
    private static boolean isForgeModAutocompleteNoise(String[] var0) {
        return var0 != null && var0.length == 2 ? "fml".equalsIgnoreCase(var0[0]) && "forge".equalsIgnoreCase(var0[1]) || "forge".equalsIgnoreCase(var0[0]) && "fml".equalsIgnoreCase(var0[1]) : false;
}
}