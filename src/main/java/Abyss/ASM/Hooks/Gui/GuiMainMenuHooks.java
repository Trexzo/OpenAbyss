/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 */
package Abyss.ASM.Hooks.Gui;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.ui.screen.AccountManagerScreen;
import Abyss.util.MinecraftRef;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiMainMenuHooks {
    private static final int ALT_MANAGER_ID = 88;
    private static long public static boolean gateClientAccess() {
        return false;
}
    public static void onActionPerformed(GuiButton var0, CallbackInfo var1) {
        if (var0.field_146127_k == 88) {
            Minecraft var4 = MinecraftRef.c((byte)0, 0L);
            var4.func_147108_a((GuiScreen)new AccountManagerScreen(81800336346822L, var4.field_71462_r));
            var1.cancel();
}
}
    public static void onPostInitGUI(List<GuiButton> var0, int var1, int var2) {
        if (var0 == null || var0.isEmpty()) {
            return;
}
        GuiButton var5 = GuiMainMenuHooks.byId(var0, 2);
        if (var5 == null || GuiMainMenuHooks.byId(var0, 88) != null) {
            return;
}
        int var6 = var5.field_146129_i;
        for (GuiButton var8 : var0) {
            if (var8 == null) continue;
            var8.field_146129_i = var8.field_146129_i <= var6 ? var8.field_146129_i - 12 : var8.field_146129_i + 12;
}
        var0.add(new GuiButton(88, var5.field_146128_h, var5.field_146129_i + 24, var5.field_146120_f, var5.field_146121_g, "Alt Manager"));
}
    private static GuiButton byId(List<GuiButton> var0, int var1) {
        for (GuiButton var4 : var0) {
            if (var4 == null || var4.field_146127_k != var1) continue;
            return var4;
}
        return null;
}
}