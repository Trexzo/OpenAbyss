/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.scoreboard.ScoreObjective
 */
package Abyss.ASM.Hooks.Gui;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.module.Modules;
import Abyss.module.impl.configuration.ScoreBoard;
import Abyss.module.impl.world.Scaffold;
import Abyss.util.render.VisualSpoofRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreObjective;

public class GuiIngameHooks {
    private static long private static long b = 1704908060083879945L;

    public static ItemStack updateTickGetCurrentItem(InventoryPlayer var0) {
        int var4;
        Scaffold var3 = Modules.J(Scaffold.class);
        if (var3 != null && var3.o() && Scaffold.fakeItem != null && Scaffold.fakeItem.c() && (var4 = var3.q()) >= 0 && var4 < (int)b) {
            return var0.func_70301_a(var4);
}
        return var0.func_70448_g();
}
    public static void renderScoreboard(ScoreObjective var0, ScaledResolution var1, CallbackInfo var2) {
        if (!VisualSpoofRenderer.H()) {
            ScoreBoard.n(var0, var1);
            var2.cancel();
}
}
}