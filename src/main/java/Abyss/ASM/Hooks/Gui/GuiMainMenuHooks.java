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
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiMainMenuHooks {
    private static final int ALT_MANAGER_ID = 88;
    public static boolean gateClientAccess() {
        return false;
}
    public static void onActionPerformed(GuiButton var0, CallbackInfo var1) {
        if (var0.id == 88) {
            Minecraft var4 = MinecraftRef.c((byte)0, 0L);
            var4.displayGuiScreen((GuiScreen)new AccountManagerScreen(81800336346822L, var4.currentScreen));
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
        int var6 = var5.yPosition;
        for (GuiButton var8 : var0) {
            if (var8 == null) continue;
            var8.yPosition = var8.yPosition <= var6 ? var8.yPosition - 12 : var8.yPosition + 12;
}
        var0.add(new GuiButton(88, var5.xPosition, var5.yPosition + 24, var5.width, var5.height, "Alt Manager"));
}
    public static String selfTest() {
        try {
            ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();
            buttons.add(new GuiButton(0, 10, 100, 200, 20, "Singleplayer"));
            GuiButton anchor = new GuiButton(2, 10, 120, 200, 20, "Options");
            buttons.add(anchor);
            buttons.add(new GuiButton(4, 10, 140, 200, 20, "Quit"));
            GuiMainMenuHooks.onPostInitGUI(buttons, 320, 240);
            GuiButton alt = GuiMainMenuHooks.byId(buttons, ALT_MANAGER_ID);
            if (alt == null) {
                return "FAIL missing";
}
            if (!"Alt Manager".equals(alt.displayString)) {
                return "FAIL label " + alt.displayString;
}
            if (alt.xPosition != 10 || alt.yPosition != 132 || alt.width != 200 || alt.height != 20) {
                return "FAIL bounds " + alt.xPosition + "," + alt.yPosition + " " + alt.width + "x" + alt.height;
}
            int size = buttons.size();
            GuiMainMenuHooks.onPostInitGUI(buttons, 320, 240);
            if (buttons.size() != size) {
                return "FAIL duplicate";
}
            return gateClientAccess() ? "FAIL gated" : "PASS";
}
        catch (Throwable throwable) {
            return "FAIL " + throwable.getClass().getName() + ": " + throwable.getMessage();
}
}
    private static GuiButton byId(List<GuiButton> var0, int var1) {
        for (GuiButton var4 : var0) {
            if (var4 == null || var4.id != var1) continue;
            return var4;
}
        return null;
}
}