/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiButtonLanguage
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.resources.I18n
 */
package Abyss.ui.screen;

import Abyss.ui.screen.MainMenuTheme;
import Abyss.ui.screen.SenrenBankaMenuButton;
import Abyss.util.MinecraftRef;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class SenrenBankaMainMenu {
    private static long private static Minecraft F;

    public static void W(int var0, int var1) {
        F.func_110434_K().func_110577_a(MainMenuTheme.a);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Gui.func_73734_a((int)0, (int)0, (int)var0, (int)var1, (int)-1);
        Gui.func_146110_a((int)0, (int)0, (float)0.0f, (float)0.0f, (int)var0, (int)((int)((float)var0 * 0.5625f)), (float)var0, (float)((float)var0 * 0.5625f));
}
    public static void l(int var0, int var1, long var2, List var4) {
        long var5 = var2 ^ 0x428A39E08F45L;
        int var7 = (int)((float)var1 / 2.9f);
        int var8 = (int)((float)var1 / 10.0f);
        int var9 = (int)((float)var1 / 12.0f);
        int var10 = (int)((float)var0 / 70.0f);
        int var11 = (int)((float)var0 / 6.0f);
        var4.add(new SenrenBankaMenuButton(1, var10, var5, var7, var11, var9, I18n.func_135052_a((String)"menu.singleplayer", (Object[])new Object[0])));
        var4.add(new SenrenBankaMenuButton(2, var10, var5, var7 + var8, var11, var9, I18n.func_135052_a((String)"menu.multiplayer", (Object[])new Object[0])));
        var4.add(new SenrenBankaMenuButton(88, var10, var5, var7 + var8 * 2, var11, var9, "Alt Manager"));
        SenrenBankaMenuButton var12 = new SenrenBankaMenuButton(14, var10, var5, var7 + var8 * 3, var11, var9, I18n.func_135052_a((String)"menu.online", (Object[])new Object[0]).replace("Minecraft", "").trim());
        var4.add(var12);
        var4.add(new SenrenBankaMenuButton(6, var10, var5, var7 + var8 * 4, var11, var9, I18n.func_135052_a((String)"fml.menu.mods", (Object[])new Object[0])));
        var4.add(new SenrenBankaMenuButton(0, var10, var5, var7 + var8 * 5, var11, var9, I18n.func_135052_a((String)"menu.options", (Object[])new Object[0])));
        var4.add(new SenrenBankaMenuButton(4, var10, var5, var7 + var8 * 6, var11, var9, I18n.func_135052_a((String)"menu.quit", (Object[])new Object[0])));
        var4.add(new GuiButtonLanguage(5, var10, var7 + var8 * 7));
}
    public static void O(int var0, int var1, List<GuiButton> var2) {
        for (GuiButton var4 : var2) {
            if (!(var4 instanceof SenrenBankaMenuButton)) continue;
            var4.func_175211_a((int)((float)var0 / 6.0f));
            ((SenrenBankaMenuButton)var4).N((int)((float)var1 / 12.0f));
}
}
    static {
        F = MinecraftRef.c((byte)0, 0L);
}
}