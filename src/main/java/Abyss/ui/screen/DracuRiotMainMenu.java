/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.resources.I18n
 */
package Abyss.ui.screen;

import Abyss.ui.screen.DracuRiotMenuButton;
import Abyss.ui.screen.MainMenuTheme;
import Abyss.util.MinecraftRef;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class DracuRiotMainMenu {
    private static long a;

    private static final float v = 1.7777778f;
        
    private static Minecraft T;

    private static float q(int var0) {
        return Math.max(1.45f, Math.min(3.05f, (float)var0 / 205.0f));
}
    private static int R(String[] var0, float var4) {
        int var7 = 0;
        for (String var11 : var0) {
            int var12 = (int)((float)DracuRiotMainMenu.T.fontRendererObj.getStringWidth(var11) * var4);
            if (var12 <= var7) continue;
            var7 = var12;
}
        return var7 + Math.max(8, (int)(4.0f * var4));
}
    public static void h(int var0, int var1) {
        T.getTextureManager().bindTexture(MainMenuTheme.z);
        int var2 = var0;
        int var3 = (int)((float)var0 / 1.7777778f);
        if (var3 < var1) {
            var3 = var1;
            var2 = (int)((float)var1 * 1.7777778f);
}
        int var4 = (var0 - var2) / 2;
        int var5 = (var1 - var3) / 2;
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Gui.drawRect((int)0, (int)0, (int)var0, (int)var1, (int)-1);
        Gui.drawModalRectWithCustomSizedTexture((int)var4, (int)var5, (float)0.0f, (float)0.0f, (int)var2, (int)var3, (float)var2, (float)var3);
}
    public static void D(int var0, int var1, long var2, List var4) {
        long var8 = var2 ^ 0x66DC6F1060C8L;
        int[] var10000 = new int[]{1, 2, 14, 88, 6, 0, 4};
        int[] var10 = var10000;
        String[] var21 = new String[]{I18n.format((String)"menu.singleplayer", (Object[])new Object[0]), I18n.format((String)"menu.multiplayer", (Object[])new Object[0]), I18n.format((String)"menu.online", (Object[])new Object[0]).replace("Minecraft", "").trim(), "Alt Manager", I18n.format((String)"fml.menu.mods", (Object[])new Object[0]), I18n.format((String)"menu.options", (Object[])new Object[0]), I18n.format((String)"menu.quit", (Object[])new Object[0])};
        String[] var11 = var21;
        float var12 = DracuRiotMainMenu.q(var1);
        int var13 = Math.max(12, (int)((float)DracuRiotMainMenu.T.fontRendererObj.FONT_HEIGHT * var12));
        int var14 = Math.max(var13 + 3, (int)(12.0f * var12));
        int var15 = var1 - 24 - var13 - Math.max(6, (int)(3.0f * var12));
        int var16 = DracuRiotMainMenu.R(var11, var12);
        int var17 = var0 - Math.max(6, (int)((float)var0 * 0.008f)) - var16;
        for (int var18 = 0; var18 < var11.length; ++var18) {
            DracuRiotMenuButton var19 = new DracuRiotMenuButton(var10[var18], var17, var15 - var14 * (var11.length - 1 - var18), var16, var13, var8, var11[var18]);
            var19.k(var12);
            var19.U(var13);
            var4.add(var19);
}
}
    public static void u(int var0, int var1, long var2, List var4) {
        var2 = a ^ var2;
        int var6 = (int)((var2 ^ 0x6C530BDDADBEL) << 32 >>> 48);
        float var8 = DracuRiotMainMenu.q(var1);
        int var9 = var0 - Math.max(6, (int)((float)var0 * 0.008f));
        int var10 = var1 - 24;
        int var11 = Math.max(12, (int)((float)DracuRiotMainMenu.T.fontRendererObj.FONT_HEIGHT * var8));
        int var12 = Math.max(var11 + 3, (int)(12.0f * var8));
        int var13 = var10 - var11 - Math.max(6, (int)(3.0f * var8));
        int var14 = DracuRiotMainMenu.b(var4, (char)var6, var8);
        int var15 = 0;
        for (GuiButton var17 : (Iterable<GuiButton>)(var4)) {
            if (!(var17 instanceof DracuRiotMenuButton)) continue;
            ((DracuRiotMenuButton)var17).k(var8);
            var17.setWidth(var14);
            ((DracuRiotMenuButton)var17).U(var11);
            var17.xPosition = var9 - var14;
            var17.yPosition = var13 - var12 * (6 - var15);
            ++var15;
}
}
    private static int b(List var0, char var2, float var4) {
        int var7 = 0;
        for (GuiButton var9 : (Iterable<GuiButton>)(var0)) {
            int var10;
            if (!(var9 instanceof DracuRiotMenuButton) || (var10 = (int)((float)DracuRiotMainMenu.T.fontRendererObj.getStringWidth(var9.displayString) * var4)) <= var7) continue;
            var7 = var10;
}
        return var7 + Math.max(8, (int)(4.0f * var4));
}
    static {
        a = 61551126875408L;
        T = MinecraftRef.c((byte)0, 0L);
}
}