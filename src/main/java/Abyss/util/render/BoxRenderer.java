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
    private static long a;

    private static Minecraft M = MinecraftRef.c((byte)0, 0L);

    public static void D(AxisAlignedBB var0, Color var2) {
        BoxRenderer.C();
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        RenderManager var11 = M.getRenderManager();
        AxisAlignedBB var12 = var0.offset(-var11.viewerPosX, -var11.viewerPosY, -var11.viewerPosZ);
        double var13 = var12.minX;
        double var15 = var12.minY;
        double var17 = var12.minZ;
        double var19 = var12.maxX;
        double var21 = var12.maxY;
        double var23 = var12.maxZ;
        float var25 = (float)var2.getAlpha() / 255.0f;
        float var26 = (float)var2.getRed() / 255.0f;
        float var27 = (float)var2.getGreen() / 255.0f;
        float var28 = (float)var2.getBlue() / 255.0f;
        GlStateManager.color((float)var26, (float)var27, (float)var28, (float)var25);
        var10.begin(7, DefaultVertexFormats.POSITION);
        var10.pos(var13, var15, var17).endVertex();
        var10.pos(var19, var15, var17).endVertex();
        var10.pos(var19, var15, var23).endVertex();
        var10.pos(var13, var15, var23).endVertex();
        var10.pos(var13, var21, var23).endVertex();
        var10.pos(var19, var21, var23).endVertex();
        var10.pos(var19, var21, var17).endVertex();
        var10.pos(var13, var21, var17).endVertex();
        var10.pos(var13, var15, var23).endVertex();
        var10.pos(var13, var21, var23).endVertex();
        var10.pos(var13, var21, var17).endVertex();
        var10.pos(var13, var15, var17).endVertex();
        var10.pos(var19, var15, var17).endVertex();
        var10.pos(var19, var21, var17).endVertex();
        var10.pos(var19, var21, var23).endVertex();
        var10.pos(var19, var15, var23).endVertex();
        var10.pos(var13, var21, var17).endVertex();
        var10.pos(var19, var21, var17).endVertex();
        var10.pos(var19, var15, var17).endVertex();
        var10.pos(var13, var15, var17).endVertex();
        var10.pos(var13, var15, var23).endVertex();
        var10.pos(var19, var15, var23).endVertex();
        var10.pos(var19, var21, var23).endVertex();
        var10.pos(var13, var21, var23).endVertex();
        var9.draw();
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
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        GlStateManager.popMatrix();
}
    private static void C() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.disableTexture2D();
}
    public static void I(BlockPos var2, Color var3) {
        BoxRenderer.R(new AxisAlignedBB((double)var2.getX(), (double)var2.getY(), (double)var2.getZ(), (double)(var2.getX() + 1), (double)(var2.getY() + 1), (double)(var2.getZ() + 1)), var3);
}
    private BoxRenderer() {
}
    public static void p(BlockPos var0, long var1, Color var3) {
        var1 = a ^ var1;
        BoxRenderer.D(new AxisAlignedBB((double)var0.getX(), (double)var0.getY(), (double)var0.getZ(), (double)(var0.getX() + 1), (double)(var0.getY() + 1), (double)(var0.getZ() + 1)), var3);
}
    public static void R(AxisAlignedBB var2, Color var3) {
        RenderManager var6 = M.getRenderManager();
        AxisAlignedBB var7 = var2.offset(-var6.viewerPosX, -var6.viewerPosY, -var6.viewerPosZ);
        BoxRenderer.C();
        BoxRenderer.F(var7.minX, var7.minY, var7.minZ, var7.maxX, var7.maxY, var7.maxZ, var3);
        BoxRenderer.o();
}
    static {
        a = 76566711834522L;
    }
}