/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 */
package Abyss.ui.screen;

import Abyss.ui.screen.MainMenuTheme;
import Abyss.util.MinecraftRef;
import Abyss.util.SoundEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class SenrenBankaMenuButton
extends GuiButton {
    private boolean h = false;
    private final float a;
    public int k = 20;
    private static long b = 96817688816412L;

    private void U(String var1, float var2, float var3, long var4) {
        Minecraft var9 = MinecraftRef.c((byte)0, 0L);
        String var10 = var1.replaceAll("(?i)\u00a7[\\da-f]", "");
        var9.field_71466_p.func_175065_a(var10, var2 + 1.0f, var3, -2894893, false);
        var9.field_71466_p.func_175065_a(var10, var2 - 1.0f, var3, -2894893, false);
        var9.field_71466_p.func_175065_a(var10, var2, var3 + 1.0f, -2894893, false);
        var9.field_71466_p.func_175065_a(var10, var2, var3 - 1.0f, -2894893, false);
        var9.field_71466_p.func_175065_a(var10, var2 + 1.0f, var3 + 1.0f, -2894893, false);
        var9.field_71466_p.func_175065_a(var10, var2 - 1.0f, var3 + 1.0f, -2894893, false);
        var9.field_71466_p.func_175065_a(var10, var2 + 1.0f, var3 - 1.0f, -2894893, false);
        var9.field_71466_p.func_175065_a(var10, var2 - 1.0f, var3 - 1.0f, -2894893, false);
        var9.field_71466_p.func_175065_a(var1, var2, var3, -45824, false);
}
    private void B(String var1, float var2, float var3, long var4) {
        Minecraft var9 = MinecraftRef.c((byte)0, 0L);
        String var10 = var1.replaceAll("(?i)\u00a7[\\da-f]", "");
        var9.field_71466_p.func_175065_a(var10, var2 + 1.0f, var3, -96, false);
        var9.field_71466_p.func_175065_a(var10, var2 - 1.0f, var3, -96, false);
        var9.field_71466_p.func_175065_a(var10, var2, var3 + 1.0f, -96, false);
        var9.field_71466_p.func_175065_a(var10, var2, var3 - 1.0f, -96, false);
        var9.field_71466_p.func_175065_a(var10, var2 + 1.0f, var3 + 1.0f, -96, false);
        var9.field_71466_p.func_175065_a(var10, var2 - 1.0f, var3 + 1.0f, -96, false);
        var9.field_71466_p.func_175065_a(var10, var2 + 1.0f, var3 - 1.0f, -96, false);
        var9.field_71466_p.func_175065_a(var10, var2 - 1.0f, var3 - 1.0f, -96, false);
        var9.field_71466_p.func_175065_a(var1, var2, var3, -29696, false);
}
    public SenrenBankaMenuButton(int var1, int var2, long var3, int var5, int var6, int var7, String var8) {
        super(var1, var2, var5, var6, var7, var8);
        this.a = 1.0f;
}
    public void func_146112_a(Minecraft var1, int var2, int var3) {
        if (this.field_146125_m) {
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            boolean bl = this.field_146123_n = var2 >= this.field_146128_h && var3 >= this.field_146129_i && var2 < this.field_146128_h + this.field_146120_f && var3 < this.field_146129_i + this.k;
            if (this.h && !this.field_146123_n) {
                this.h = false;
}
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            GlStateManager.func_179112_b((int)770, (int)771);
            var1.func_110434_K().func_110577_a(MainMenuTheme.n);
            float var12 = (float)this.k / 3.0f * 0.8f;
            float var13 = (float)this.k / 3.0f;
            SenrenBankaMenuButton.func_146110_a((int)(this.field_146128_h + 1), (int)((int)((float)this.field_146129_i + (float)this.k / 2.0f - var13 / 2.0f)), (float)0.0f, (float)0.0f, (int)((int)var12), (int)((int)var13), (float)var12, (float)var13);
            this.func_146119_b(var1, var2, var3);
            float var14 = 1.4f;
            if (this.field_146123_n) {
                if (!this.h) {
                    this.h = true;
                    SoundEngine.y(59424967409495L, "/assets/minecraft/mainmenu/option.ogg");
}
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a((float)var14, (float)var14, (float)var14);
                this.B(this.field_146126_j, ((float)this.field_146128_h + (float)this.k / 2.0f + 5.0f) / var14, ((float)this.field_146129_i + ((float)this.k - 8.0f) / 2.0f) / var14, 8173588228278L);
                GlStateManager.func_179121_F();
            } else {
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a((float)var14, (float)var14, (float)var14);
                this.U(this.field_146126_j, ((float)this.field_146128_h + (float)this.k / 2.0f + 5.0f) / var14, ((float)this.field_146129_i + ((float)this.k - 8.0f) / 2.0f) / var14, 137972018576089L);
                GlStateManager.func_179121_F();
}
}
}
    public void N(int var1) {
        this.k = var1;
}
}