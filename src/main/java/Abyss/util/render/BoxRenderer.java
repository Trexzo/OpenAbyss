/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  org.lwjgl.opengl.GL11
 */
package Abyss.util.render;

import Abyss.util.MinecraftRef;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class BoxRenderer {
    private static long private static Minecraft M = MinecraftRef.c((byte)0, 0L);

    public static void D(AxisAlignedBB var0, Color var2) {
        BoxRenderer.C();
        Tessellator var9 = Tessellator.func_178181_a();
        WorldRenderer var10 = var9.func_178180_c();
        RenderManager var11 = M.func_175598_ae();
        AxisAlignedBB var12 = var0.func_72317_d(-var11.field_78730_l, -var11.field_78731_m, -var11.field_78728_n);
        double var13 = var12.field_72340_a;
        double var15 = var12.field_72338_b;
        double var17 = var12.field_72339_c;
        double var19 = var12.field_72336_d;
        double var21 = var12.field_72337_e;
        double var23 = var12.field_72334_f;
        float var25 = (float)var2.getAlpha() / 255.0f;
        float var26 = (float)var2.getRed() / 255.0f;
        float var27 = (float)var2.getGreen() / 255.0f;
        float var28 = (float)var2.getBlue() / 255.0f;
        GlStateManager.func_179131_c((float)var26, (float)var27, (float)var28, (float)var25);
        var10.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        var10.func_181662_b(var13, var15, var17).func_181675_d();
        var10.func_181662_b(var19, var15, var17).func_181675_d();
        var10.func_181662_b(var19, var15, var23).func_181675_d();
        var10.func_181662_b(var13, var15, var23).func_181675_d();
        var10.func_181662_b(var13, var21, var23).func_181675_d();
        var10.func_181662_b(var19, var21, var23).func_181675_d();
        var10.func_181662_b(var19, var21, var17).func_181675_d();
        var10.func_181662_b(var13, var21, var17).func_181675_d();
        var10.func_181662_b(var13, var15, var23).func_181675_d();
        var10.func_181662_b(var13, var21, var23).func_181675_d();
        var10.func_181662_b(var13, var21, var17).func_181675_d();
        var10.func_181662_b(var13, var15, var17).func_181675_d();
        var10.func_181662_b(var19, var15, var17).func_181675_d();
        var10.func_181662_b(var19, var21, var17).func_181675_d();
        var10.func_181662_b(var19, var21, var23).func_181675_d();
        var10.func_181662_b(var19, var15, var23).func_181675_d();
        var10.func_181662_b(var13, var21, var17).func_181675_d();
        var10.func_181662_b(var19, var21, var17).func_181675_d();
        var10.func_181662_b(var19, var15, var17).func_181675_d();
        var10.func_181662_b(var13, var15, var17).func_181675_d();
        var10.func_181662_b(var13, var15, var23).func_181675_d();
        var10.func_181662_b(var19, var15, var23).func_181675_d();
        var10.func_181662_b(var19, var21, var23).func_181675_d();
        var10.func_181662_b(var13, var21, var23).func_181675_d();
        var9.func_78381_a();
        BoxRenderer.F(var13, var15, var17, var19, var21, var23, var2);
        BoxRenderer.o();
}
    private static void F(double var0, double var2, double var4, double var6, double var8, double var10, Color var12) {
        GL11.glLineWidth((float)2.0f);
        GL11.glColor4f((float)((float)var12.getRed() / 255.0f), (float)((float)var12.getGreen() / 255.0f), (float)((float)var12.getBlue() / 255.0f), (float)((float)var12.getAlpha() / 255.0f));
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)var0, (double)var2, (double)var4);
        GL11.glVertex3d((double)var6, (double)var2, (double)var4);
        GL11.glVertex3d((double)var6, (double)var2, (double)var4);
        GL11.glVertex3d((double)var6, (double)var2, (double)var10);
        GL11.glVertex3d((double)var6, (double)var2, (double)var10);
        GL11.glVertex3d((double)var0, (double)var2, (double)var10);
        GL11.glVertex3d((double)var0, (double)var2, (double)var10);
        GL11.glVertex3d((double)var0, (double)var2, (double)var4);
        GL11.glVertex3d((double)var0, (double)var8, (double)var4);
        GL11.glVertex3d((double)var6, (double)var8, (double)var4);
        GL11.glVertex3d((double)var6, (double)var8, (double)var4);
        GL11.glVertex3d((double)var6, (double)var8, (double)var10);
        GL11.glVertex3d((double)var6, (double)var8, (double)var10);
        GL11.glVertex3d((double)var0, (double)var8, (double)var10);
        GL11.glVertex3d((double)var0, (double)var8, (double)var10);
        GL11.glVertex3d((double)var0, (double)var8, (double)var4);
        GL11.glVertex3d((double)var0, (double)var2, (double)var4);
        GL11.glVertex3d((double)var0, (double)var8, (double)var4);
        GL11.glVertex3d((double)var6, (double)var2, (double)var4);
        GL11.glVertex3d((double)var6, (double)var8, (double)var4);
        GL11.glVertex3d((double)var6, (double)var2, (double)var10);
        GL11.glVertex3d((double)var6, (double)var8, (double)var10);
        GL11.glVertex3d((double)var0, (double)var2, (double)var10);
        GL11.glVertex3d((double)var0, (double)var8, (double)var10);
        GL11.glEnd();
        GL11.glLineWidth((float)1.0f);
}
    private static void o() {
        GlStateManager.func_179098_w();
        GlStateManager.func_179126_j();
        GlStateManager.func_179084_k();
        GlStateManager.func_179117_G();
        GlStateManager.func_179121_F();
}
    private static void C() {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179090_x();
}
    public static void I(BlockPos var2, Color var3) {
        BoxRenderer.R(new AxisAlignedBB((double)var2.func_177958_n(), (double)var2.func_177956_o(), (double)var2.func_177952_p(), (double)(var2.func_177958_n() + 1), (double)(var2.func_177956_o() + 1), (double)(var2.func_177952_p() + 1)), var3);
}
    private BoxRenderer() {
}
    public static void p(BlockPos var0, long var1, Color var3) {
        var1 = a ^ var1;
        BoxRenderer.D(new AxisAlignedBB((double)var0.func_177958_n(), (double)var0.func_177956_o(), (double)var0.func_177952_p(), (double)(var0.func_177958_n() + 1), (double)(var0.func_177956_o() + 1), (double)(var0.func_177952_p() + 1)), var3);
}
    public static void R(AxisAlignedBB var2, Color var3) {
        RenderManager var6 = M.func_175598_ae();
        AxisAlignedBB var7 = var2.func_72317_d(-var6.field_78730_l, -var6.field_78731_m, -var6.field_78728_n);
        BoxRenderer.C();
        BoxRenderer.F(var7.field_72340_a, var7.field_72338_b, var7.field_72339_c, var7.field_72336_d, var7.field_72337_e, var7.field_72334_f, var3);
        BoxRenderer.o();
}
}