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
import Abyss.ui.screen.RiddleJokerMenuButton;
import Abyss.util.MinecraftRef;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class RiddleJokerMainMenu {
    
    
    
    private static Minecraft n;
    
    public static void t(int var0, int var1) {
        n.func_110434_K().func_110577_a(MainMenuTheme.d);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Gui.func_73734_a((int)0, (int)0, (int)var0, (int)var1, (int)-1);
        Gui.func_146110_a((int)0, (int)0, (float)0.0f, (float)0.0f, (int)var0, (int)((int)((float)var0 * 0.5625f)), (float)var0, (float)((float)var0 * 0.5625f));
}
    public static void W(int var0, long var1, int var3, List var4) {
        var1 = a ^ var1;
        int var5 = (int)((var1 ^ 0x468B4867CF8L) >>> 32);
        int var6 = (int)((var1 ^ 0x468B4867CF8L) << 32 >>> 48);
        int var7 = (int)((var1 ^ 0x468B4867CF8L) << 48 >>> 48);
        int var8 = (int)((float)var3 / 2.0f);
        int var9 = (int)((float)var3 / 15.0f);
        int var10 = (int)((float)var3 / 20.0f);
        int var11 = (int)((float)var0 / 70.0f);
        int var12 = (int)((float)var3 / 20.0f * 8.0f);
        var4.add(new RiddleJokerMenuButton(1, var5, (short)var6, var11, var8, var12, var10, I18n.func_135052_a((String)"menu.singleplayer", (Object[])new Object[0]), (short)var7));
        var4.add(new RiddleJokerMenuButton(2, var5, (short)var6, var11, var8 + var9, var12, var10, I18n.func_135052_a((String)"menu.multiplayer", (Object[])new Object[0]), (short)var7));
        var4.add(new RiddleJokerMenuButton(88, var5, (short)var6, var11, var8 + var9 * 2, var12, var10, "Alt Manager", (short)var7));
        RiddleJokerMenuButton var13 = new RiddleJokerMenuButton(14, var5, (short)var6, var11, var8 + var9 * 3, var12, var10, I18n.func_135052_a((String)"menu.online", (Object[])new Object[0]).replace("Minecraft", "").trim(), (short)var7);
        var4.add(var13);
        var4.add(new RiddleJokerMenuButton(6, var5, (short)var6, var11, var8 + var9 * 4, var12, var10, I18n.func_135052_a((String)"fml.menu.mods", (Object[])new Object[0]), (short)var7));
        var4.add(new RiddleJokerMenuButton(0, var5, (short)var6, var11, var8 + var9 * 5, var12, var10, I18n.func_135052_a((String)"menu.options", (Object[])new Object[0]), (short)var7));
        var4.add(new RiddleJokerMenuButton(4, var5, (short)var6, var11, var8 + var9 * 6, var12, var10, I18n.func_135052_a((String)"menu.quit", (Object[])new Object[0]), (short)var7));
        var4.add(new GuiButtonLanguage(5, var11, var8 + var9 * 7));
}
    public static void L(int var0, List<GuiButton> var1) {
        for (GuiButton var3 : var1) {
            if (!(var3 instanceof RiddleJokerMenuButton)) continue;
            var3.func_175211_a((int)((float)var0 / 20.0f * 8.0f));
            ((RiddleJokerMenuButton)var3).n((int)((float)var0 / 20.0f));
}
}
    static {
        n = MinecraftRef.c((byte)0, 0L);
}
}