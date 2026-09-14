/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 */
package Abyss.ui.screen;

import Abyss.util.SoundEngine;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class DracuRiotMenuButton
extends GuiButton {
    private static String b;
    public int v = 20;
    private boolean o = false;
    
    private static long[] c;
    private float B = 1.0f;
    
    private void l(FontRenderer var1, String var2, float var3, float var4, int var5) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)this.B, (float)this.B, (float)1.0f);
        var1.func_175065_a(var2, var3 / this.B, var4 / this.B, var5, false);
        GlStateManager.func_179121_F();
}
    public void func_146112_a(Minecraft var1, int var2, int var3) {
        if (this.field_146125_m) {
            FontRenderer var10 = var1.field_71466_p;
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            boolean bl = this.field_146123_n = var2 >= this.field_146128_h && var3 >= this.field_146129_i && var2 < this.field_146128_h + this.field_146120_f && var3 < this.field_146129_i + this.v;
            if (this.o && !this.field_146123_n) {
                this.o = false;
}
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b((int)770, (int)771);
            this.func_146119_b(var1, var2, var3);
            if (this.field_146123_n) {
                if (!this.o) {
                    this.o = true;
                    SoundEngine.y(59424967409495L, b);
}
                this.C(var10, true);
            } else {
                this.C(var10, false);
}
}
}
    public DracuRiotMenuButton(int var1, int var2, int var3, int var4, int var5, long var6, String var8) {
        super(var1, var2, var3, var4, var5, var8);
}
    public void U(int var1) {
        this.v = var1;
        this.field_146121_g = var1;
}
    private void C(FontRenderer var1, boolean var4) {
        int var5 = (int)((float)var1.func_78256_a(this.field_146126_j) * this.B);
        float var6 = this.field_146128_h + this.field_146120_f - var5;
        float var7 = this.field_146129_i;
        int var8 = var4 ? -2003125 : -6402497;
        int var10 = var4 ? -3898 : -9790;
        this.l(var1, this.field_146126_j, var6 + this.B, var7 + this.B, -10014421);
        this.l(var1, this.field_146126_j, var6 - this.B, var7, var8);
        this.l(var1, this.field_146126_j, var6 + this.B, var7, var8);
        this.l(var1, this.field_146126_j, var6, var7 - this.B, var8);
        this.l(var1, this.field_146126_j, var6, var7 + this.B, var8);
        this.l(var1, this.field_146126_j, var6, var7, var10);
}
    public void k(float var1) {
        this.B = var1;
}
    static {
        b = "/assets/minecraft/mainmenu/option.ogg";
        e = new HashMap(13);
        c = new long[]{-1264485471761501984L, 6215361741798071984L, 5107200575086882811L, -7531211150311414439L, 5782579429849861360L, 189833895540201679L, 6008160011562758701L, 6417825392000962573L, 8675782347306971290L, -6815582354269240955L, 753185595500222673L};
}
}