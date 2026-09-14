/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.BlockRendererDispatcher
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.client.resources.model.IBakedModel
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.IBlockAccess
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package Abyss.util.render;

import Abyss.internal.accessor.EntityRendererAccessor;
import Abyss.internal.accessor.MinecraftAccessor;
import Abyss.internal.accessor.RenderManagerAccessor;
import Abyss.ui.raven.RavenClickGuiScreen;
import Abyss.util.ClientUtil;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.render.ColorUtil;
import Abyss.util.render.CustomFont;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class RenderUtil {
    private static Frustum Z;
    
    private static RenderItem R;
    private static FloatBuffer D;
    private static Frustum l;
        private static IntBuffer A;
        private static Map k;
    private static int a;
    protected static float X;
    private static FloatBuffer G;
            private static Minecraft T;
    private static long b;
        private static long[] i;
    private static FloatBuffer Y;
    private static int n;

    public static void s(EntityLivingBase var0, double var1, int var3, float var4, int var5, int var6, char var7, char var8) {
        long var9 = ((long)var6 << 32 | (long)var7 << 48 >>> 32 | (long)var8 << 48 >>> 48) ^ b;
        long var11 = var9 ^ 0xF8175622B6FL;
        double var13 = var0.field_70142_S + (var0.field_70165_t - var0.field_70142_S) * (double)ClientUtil.b((long)var11).field_74281_c - RenderUtil.T.func_175598_ae().field_78730_l;
        double var15 = var0.field_70137_T + (var0.field_70163_u - var0.field_70137_T) * (double)ClientUtil.b((long)var11).field_74281_c - RenderUtil.T.func_175598_ae().field_78731_m;
        double var17 = var0.field_70136_U + (var0.field_70161_v - var0.field_70136_U) * (double)ClientUtil.b((long)var11).field_74281_c - RenderUtil.T.func_175598_ae().field_78728_n;
        GlStateManager.func_179094_E();
        float var19 = (float)(var5 >> 24 & 0xFF) / 255.0f;
        float var20 = (float)(var5 >> 16 & 0xFF) / 255.0f;
        float var21 = (float)(var5 >> 8 & 0xFF) / 255.0f;
        float var22 = (float)(var5 & 0xFF) / 255.0f;
        RenderUtil.T.field_71460_t.func_175072_h();
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)var4);
        GL11.glColor4f((float)var20, (float)var21, (float)var22, (float)var19);
        GL11.glBegin((int)1);
        for (int var29 = 0; var29 < var3 * 2; ++var29) {
            double var30 = Math.PI * 2 * (double)var29 / (double)var3 + Math.toRadians(180.0);
            GL11.glVertex3d((double)(var13 + Math.cos(var30) * var1), (double)var15, (double)(var17 + Math.sin(var30) * var1));
}
        GL11.glEnd();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        RenderUtil.T.field_71460_t.func_180436_i();
        GlStateManager.func_179121_F();
}
    public static Color x(int var0, long var1) {
        Color var3 = new Color(var0);
        return new Color(var3.getRed(), var3.getGreen(), var3.getBlue(), 255);
}
    public static void j(AxisAlignedBB var0, int var1, long var2, boolean var4, boolean var5) {
        GL11.glPushMatrix();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)2.0f);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2881);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        float var8 = (float)(var1 >> 24 & 0xFF) / 255.0f;
        float var9 = (float)(var1 >> 16 & 0xFF) / 255.0f;
        float var10 = (float)(var1 >> 8 & 0xFF) / 255.0f;
        float var11 = (float)(var1 & 0xFF) / 255.0f;
        if (var4) {
            GL11.glColor4f((float)var9, (float)var10, (float)var11, (float)1.0f);
            RenderGlobal.func_181561_a((AxisAlignedBB)var0);
}
        if (var5) {
            GL11.glColor4f((float)var9, (float)var10, (float)var11, (float)var8);
            RenderUtil.q(var0, 65724172677490L, var9, var10, var11);
}
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)2881);
        GL11.glPopMatrix();
}
    public static void Z(AxisAlignedBB var0, double var1, double var3, double var5, double var7, double var9, long var11, double var13, int var15) {
        var11 = b ^ var11;
        int var21 = (int)((var11 ^ 0x431FDD0C9BAL) >>> 32);
        int var22 = (int)((var11 ^ 0x431FDD0C9BAL) << 32 >>> 48);
        float var27 = MinecraftAccessor.o((Minecraft)RenderUtil.T).field_74281_c;
        double var28 = var1 + (var7 - var1) * (double)var27 - RenderUtil.T.func_175598_ae().field_78730_l;
        double var30 = var3 + (var9 - var3) * (double)var27 - RenderUtil.T.func_175598_ae().field_78731_m;
        double var32 = var5 + (var13 - var5) * (double)var27 - RenderUtil.T.func_175598_ae().field_78728_n;
        GlStateManager.func_179094_E();
        if (var15 == 0) {
            long[] var24 = new long[]{0L};
            var15 = RenderUtil.M(var21, (short)var22, 2L, var24);
}
        float var34 = (float)(var15 >> 24 & 0xFF) / 255.0f;
        float var35 = (float)(var15 >> 16 & 0xFF) / 255.0f;
        float var36 = (float)(var15 >> 8 & 0xFF) / 255.0f;
        float var37 = (float)(var15 & 0xFF) / 255.0f;
        AxisAlignedBB var38 = var0;
        AxisAlignedBB var39 = new AxisAlignedBB(var38.field_72340_a - var7 + var28, var38.field_72338_b - var9 + var30, var38.field_72339_c - var13 + var32, var38.field_72336_d - var7 + var28, var38.field_72337_e - var9 + var30, var38.field_72334_f - var13 + var32);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)2.0f);
        GL11.glColor4f((float)var35, (float)var36, (float)var37, (float)var34);
        RenderUtil.D(var39, var35, var36, var37, var34);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GlStateManager.func_179121_F();
}
    public static void l(AxisAlignedBB var0, int var1, int var5, int var6, int var7) {
        Tessellator var10 = Tessellator.func_178181_a();
        WorldRenderer var11 = var10.func_178180_c();
        var11.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var1, var5, var6, var7).func_181675_d();
        var10.func_78381_a();
}
    public static void M() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
}
    public static void N(long var0, char var2) {
        GL11.glEnable((int)3089);
}
    public static void m(ItemStack var0, int var1, int var2) {
        GlStateManager.func_179094_E();
        RenderHelper.func_74520_c();
        R.func_180450_b(var0, var1, var2);
        RenderHelper.func_74518_a();
        GlStateManager.func_179121_F();
}
    public static void H(float var0, float var1, long var2, float var4, float var5, int var6) {
        var2 = b ^ var2;
        int var7 = (int)((var2 ^ 0x395E9770DBFL) >>> 48);
        int var8 = (int)((var2 ^ 0x395E9770DBFL) << 16 >>> 48);
        float var12 = (float)(var6 >> 24 & 0xFF) / 255.0f;
        float var13 = (float)(var6 >> 16 & 0xFF) / 255.0f;
        float var14 = (float)(var6 >> 8 & 0xFF) / 255.0f;
        float var15 = (float)(var6 & 0xFF) / 255.0f;
        Color var10 = new Color(var13, var14, var15, var12);
        float var11 = var5;
        RenderUtil.G(var0, var1, var4, (char)var7, var11, (char)var8, var10);
}
    public static void n(double var0, double var2, double var4, long var6, int var8, int var9) {
        if (var8 >= 3) {
            float var10 = (float)(var9 >> 24 & 0xFF) / 255.0f;
            float var11 = (float)(var9 >> 16 & 0xFF) / 255.0f;
            float var12 = (float)(var9 >> 8 & 0xFF) / 255.0f;
            float var13 = (float)(var9 & 0xFF) / 255.0f;
            Tessellator var14 = Tessellator.func_178181_a();
            WorldRenderer var15 = var14.func_178180_c();
            GlStateManager.func_179147_l();
            GlStateManager.func_179090_x();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            GL11.glColor4f((float)var11, (float)var12, (float)var13, (float)var10);
            var15.func_181668_a(6, DefaultVertexFormats.field_181705_e);
            for (int var16 = 0; var16 < var8; ++var16) {
                double var17 = Math.PI * 2 * (double)var16 / (double)var8 + Math.toRadians(180.0);
                var15.func_181662_b(var0 + Math.sin(var17) * var4, var2 + Math.cos(var17) * var4, 0.0).func_181675_d();
}
            var14.func_78381_a();
            GlStateManager.func_179098_w();
            GlStateManager.func_179084_k();
}
}
    public static void O(double var0, double var2, double var4, long var6, double var8, double var10, double var12, int var14, boolean var15, boolean var16) {
        double var19 = var0 - RenderUtil.T.func_175598_ae().field_78730_l;
        double var21 = var2 - RenderUtil.T.func_175598_ae().field_78731_m;
        double var23 = var4 - RenderUtil.T.func_175598_ae().field_78728_n;
        AxisAlignedBB var25 = new AxisAlignedBB(var19, var21, var23, var19 + var8, var21 + var10, var23 + var12);
        RenderUtil.j(var25, var14, 137192391982620L, var15, var16);
}
    public static int E(int var0, double var1, long var3) {
        if (var1 < 0.0 || var1 > 1.0) {
            var1 = 0.5;
}
        int var5 = var0 >> 16 & 0xFF;
        int var6 = var0 >> 8 & 0xFF;
        int var7 = var0 & 0xFF;
        int var8 = (int)(var1 * 255.0);
        return var8 << 24 | var5 << 16 | var6 << 8 | var7;
}
    public static void q(long var0) {
        GL11.glDisable((int)3089);
}
    public static void q(ItemStack var0, int var1, int var2, String var3) {
        GlStateManager.func_179094_E();
        RenderHelper.func_74520_c();
        R.func_180450_b(var0, var1, var2);
        R.func_180453_a(RenderUtil.T.field_71466_p, var0, var1, var2, var3);
        RenderHelper.func_74518_a();
        GlStateManager.func_179121_F();
}
    public static void w() {
        GlStateManager.func_179126_j();
        GlStateManager.func_179141_d();
        GlStateManager.func_179089_o();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
}
    public static void C(BlockPos var0, double var1, int var3, long var4, int var6, int var7, int var8) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var4 = b ^ var4;
        int var16 = (int)((var4 ^ 0x7E94D6A0A712L) >>> 32);
        RenderUtil.l(new AxisAlignedBB((double)var0.func_177958_n(), (double)var0.func_177956_o(), (double)var0.func_177952_p(), (double)var0.func_177958_n() + 1.0, (double)var0.func_177956_o() + var1, (double)var0.func_177952_p() + 1.0).func_72317_d(-RenderManagerAccessor.k(0L, T.func_175598_ae()), -RenderManagerAccessor.y(var16, T.func_175598_ae()), -RenderManagerAccessor.W(0L, T.func_175598_ae())), var3, var6, var7, var8);
}
    public static void h(AxisAlignedBB var0, int var1, int var2, int var3, int var4, long var5, boolean var7, boolean var8) {
        var5 = b ^ var5;
        GL11.glPushMatrix();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)2.0f);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2881);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        if (var7) {
            GL11.glColor4f((float)((float)var1 / 255.0f), (float)((float)var2 / 255.0f), (float)((float)var3 / 255.0f), (float)1.0f);
            RenderGlobal.func_181561_a((AxisAlignedBB)var0);
}
        if (var8) {
            GL11.glColor4f((float)((float)var1 / 255.0f), (float)((float)var2 / 255.0f), (float)((float)var3 / 255.0f), (float)((float)var4 / 255.0f));
            RenderUtil.D(var0, (float)var1 / 255.0f, (float)var2 / 255.0f, (float)var3 / 255.0f, (float)var4 / 255.0f);
}
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)2881);
        GL11.glPopMatrix();
}
    public static void R(int var0, long var1) {
        RenderUtil.O(var0, (float)(var0 >> 24 & 0xFF) / 255.0f, 0L);
}
    public static void R(Entity var0, long var1, int var3, float var4) {
        double var8 = var0.field_70142_S + (var0.field_70165_t - var0.field_70142_S) * (double)var4 - RenderUtil.T.func_175598_ae().field_78730_l;
        double var10 = var0.field_70137_T + (var0.field_70163_u - var0.field_70137_T) * (double)var4 - RenderUtil.T.func_175598_ae().field_78731_m;
        double var12 = var0.field_70136_U + (var0.field_70161_v - var0.field_70136_U) * (double)var4 - RenderUtil.T.func_175598_ae().field_78728_n;
        float var14 = (float)(var3 >> 24 & 0xFF) / 255.0f;
        float var15 = (float)(var3 >> 16 & 0xFF) / 255.0f;
        float var16 = (float)(var3 >> 8 & 0xFF) / 255.0f;
        float var17 = (float)(var3 & 0xFF) / 255.0f;
        double var18 = 0.45;
        double var20 = 0.1;
        double var22 = (double)var0.field_70131_O + 0.1 - (var0.func_70093_af() ? 0.2 : 0.0);
        AxisAlignedBB var24 = new AxisAlignedBB(var8 - var18, var10 + var22, var12 - var18, var8 + var18, var10 + var22 + var20, var12 + var18);
        GlStateManager.func_179094_E();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)2.0f);
        GL11.glColor4f((float)var15, (float)var16, (float)var17, (float)var14);
        RenderGlobal.func_181561_a((AxisAlignedBB)var24);
        RenderUtil.D(var24, var15, var16, var17, var14);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GlStateManager.func_179121_F();
}
    public static boolean p(AxisAlignedBB var0, double var1) {
        l.func_78547_a(RenderUtil.T.func_175606_aa().field_70165_t, RenderUtil.T.func_175606_aa().field_70163_u, RenderUtil.T.func_175606_aa().field_70161_v);
        return l.func_78546_a(var0.func_72314_b(var1, var1, var1));
}
    public static void V(BlockPos var0, long var1, int var3, int var4) {
        RenderUtil.r(var0.func_177958_n(), var0.func_177956_o(), var0.func_177952_p(), 1.0, 1.0, 1.0, var3, var4);
}
    public static void k(float var0, float var1, byte var2, float var3, float var4, float var5, long var6) {
        int var12;
        ScaledResolution var10 = new ScaledResolution(T);
        int var11 = var10.func_78325_e();
        switch (var11) {
            case 2: {
                var12 = 540;
                break;
}
            case 3: {
                var12 = 1080;
                break;
}
            case 4: {
                var12 = 270;
                break;
}
            default: {
                var12 = var10.func_78328_b();
}
}
        GL11.glScissor((int)((int)(var0 * (float)var11 * var5)), (int)((int)(((float)var12 - var1) * (float)var11 * var5)), (int)((int)(var3 * (float)var11 * var5)), (int)((int)(var4 * (float)var11 * var5)));
}
    public static void P(double var0, double var2, double var4, double var6) {
        ScaledResolution var8 = new ScaledResolution(T);
        int var9 = var8.func_78325_e();
        int var10 = var8.func_78328_b();
        GL11.glScissor((int)((int)(var0 * (double)var9)), (int)((int)(((double)var10 - var2 - var6) * (double)var9)), (int)((int)(var4 * (double)var9)), (int)((int)(var6 * (double)var9)));
}
    public static void R(AxisAlignedBB var0, int var1, long var2) {
        int var12 = ColorUtil.l(var1, 0L);
        int var13 = ColorUtil.U(0L, var1);
        int var14 = ColorUtil.d(0L, var1);
        int var15 = ColorUtil.g(0L, var1);
        Tessellator var16 = Tessellator.func_178181_a();
        WorldRenderer var17 = var16.func_178180_c();
        var17.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var16.func_78381_a();
        var17.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var16.func_78381_a();
        var17.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var16.func_78381_a();
        var17.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var16.func_78381_a();
        var17.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var16.func_78381_a();
        var17.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var17.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var12, var13, var14, var15).func_181675_d();
        var16.func_78381_a();
}
    public static void G(float var0, float var1, float var2, char var3, float var4, char var5, Color var6) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179090_x();
        GlStateManager.func_179097_i();
        GL11.glLineWidth((float)2.5f);
        GL11.glColor4f((float)((float)var6.getRed() / 255.0f), (float)((float)var6.getGreen() / 255.0f), (float)((float)var6.getBlue() / 255.0f), (float)((float)var6.getAlpha() / 255.0f));
        GL11.glBegin((int)2);
        GL11.glVertex2f((float)var0, (float)var1);
        GL11.glVertex2f((float)var2, (float)var1);
        GL11.glVertex2f((float)var2, (float)var4);
        GL11.glVertex2f((float)var0, (float)var4);
        GL11.glEnd();
        GlStateManager.func_179098_w();
        GlStateManager.func_179126_j();
        GlStateManager.func_179084_k();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179121_F();
}
    public static void A(Entity var0, long var1, int var3, float var4, double var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var1 = b ^ var1;
        int var22 = (int)((var1 ^ 0x2E3D5F2D780BL) >>> 32);
        double var25 = MathUtil.h(var0.field_70165_t, var0.field_70142_S, ClientUtil.H(0L));
        double var27 = MathUtil.h(var0.field_70163_u, var0.field_70137_T, ClientUtil.H(0L));
        double var29 = MathUtil.h(var0.field_70161_v, var0.field_70136_U, ClientUtil.H(0L));
        RenderUtil.r(var0.func_174813_aQ().func_72314_b(var5, var5, var5).func_72317_d(var25 - var0.field_70165_t, var27 - var0.field_70163_u, var29 - var0.field_70161_v).func_72317_d(-RenderManagerAccessor.k(0L, T.func_175598_ae()), -RenderManagerAccessor.y(var22, T.func_175598_ae()), -RenderManagerAccessor.W(0L, T.func_175598_ae())), ColorUtil.l(var3, 0L), ColorUtil.U(0L, var3), ColorUtil.d(0L, var3), var4);
}
    public static void A$fill(Entity var0, long var1, int var3, float var4, double var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        double[][] var16;
        RenderUtil.A(var0, var1, var3, var4, var5);
        int var8 = (int)((var1 ^ b ^ 0x2E3D5F2D780BL) >>> 32);
        double var9 = MathUtil.h(var0.field_70165_t, var0.field_70142_S, ClientUtil.H(0L));
        double var11 = MathUtil.h(var0.field_70163_u, var0.field_70137_T, ClientUtil.H(0L));
        double var13 = MathUtil.h(var0.field_70161_v, var0.field_70136_U, ClientUtil.H(0L));
        AxisAlignedBB var15 = var0.func_174813_aQ().func_72314_b(var5, var5, var5).func_72317_d(var9 - var0.field_70165_t, var11 - var0.field_70163_u, var13 - var0.field_70161_v).func_72317_d(-RenderManagerAccessor.k(0L, T.func_175598_ae()), -RenderManagerAccessor.y(var8, T.func_175598_ae()), -RenderManagerAccessor.W(0L, T.func_175598_ae()));
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)((float)ColorUtil.l(var3, 0L) / 255.0f), (float)((float)ColorUtil.U(0L, var3) / 255.0f), (float)((float)ColorUtil.d(0L, var3) / 255.0f), (float)0.25f);
        GL11.glBegin((int)7);
        for (double[] var20 : var16 = new double[][]{{var15.field_72340_a, var15.field_72338_b, var15.field_72339_c, var15.field_72336_d, var15.field_72337_e, var15.field_72339_c}, {var15.field_72340_a, var15.field_72338_b, var15.field_72334_f, var15.field_72336_d, var15.field_72337_e, var15.field_72334_f}}) {
            GL11.glVertex3d((double)var20[0], (double)var20[1], (double)var20[2]);
            GL11.glVertex3d((double)var20[3], (double)var20[1], (double)var20[2]);
            GL11.glVertex3d((double)var20[3], (double)var20[4], (double)var20[5]);
            GL11.glVertex3d((double)var20[0], (double)var20[4], (double)var20[5]);
}
        GL11.glVertex3d((double)var15.field_72340_a, (double)var15.field_72338_b, (double)var15.field_72339_c);
        GL11.glVertex3d((double)var15.field_72340_a, (double)var15.field_72338_b, (double)var15.field_72334_f);
        GL11.glVertex3d((double)var15.field_72340_a, (double)var15.field_72337_e, (double)var15.field_72334_f);
        GL11.glVertex3d((double)var15.field_72340_a, (double)var15.field_72337_e, (double)var15.field_72339_c);
        GL11.glVertex3d((double)var15.field_72336_d, (double)var15.field_72338_b, (double)var15.field_72339_c);
        GL11.glVertex3d((double)var15.field_72336_d, (double)var15.field_72338_b, (double)var15.field_72334_f);
        GL11.glVertex3d((double)var15.field_72336_d, (double)var15.field_72337_e, (double)var15.field_72334_f);
        GL11.glVertex3d((double)var15.field_72336_d, (double)var15.field_72337_e, (double)var15.field_72339_c);
        GL11.glVertex3d((double)var15.field_72340_a, (double)var15.field_72338_b, (double)var15.field_72339_c);
        GL11.glVertex3d((double)var15.field_72336_d, (double)var15.field_72338_b, (double)var15.field_72339_c);
        GL11.glVertex3d((double)var15.field_72336_d, (double)var15.field_72338_b, (double)var15.field_72334_f);
        GL11.glVertex3d((double)var15.field_72340_a, (double)var15.field_72338_b, (double)var15.field_72334_f);
        GL11.glVertex3d((double)var15.field_72340_a, (double)var15.field_72337_e, (double)var15.field_72339_c);
        GL11.glVertex3d((double)var15.field_72336_d, (double)var15.field_72337_e, (double)var15.field_72339_c);
        GL11.glVertex3d((double)var15.field_72336_d, (double)var15.field_72337_e, (double)var15.field_72334_f);
        GL11.glVertex3d((double)var15.field_72340_a, (double)var15.field_72337_e, (double)var15.field_72334_f);
        GL11.glEnd();
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
}
    public static void K(CustomFont var0, String var1, long var2, float var4, float var5, int var6) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var7 = var2 ^ 0x4130B737A1ACL;
        String var9 = var1.replaceAll("(?i)\u00a7[\\da-f]", "");
        var0.v(var9, var4 + 1.0f, var5, 0, var7, false);
        var0.v(var9, var4 - 1.0f, var5, 0, var7, false);
        var0.v(var9, var4, var5 + 1.0f, 0, var7, false);
        var0.v(var9, var4, var5 - 1.0f, 0, var7, false);
        var0.v(var1, var4, var5, var6, var7, false);
}
    public static void Y(AxisAlignedBB var0, int var1, int var2, long var3) {
        GL11.glPushMatrix();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)2.0f);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2881);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        float var5 = (float)(var1 >> 16 & 0xFF) / 255.0f;
        float var6 = (float)(var1 >> 8 & 0xFF) / 255.0f;
        float var7 = (float)(var1 & 0xFF) / 255.0f;
        GL11.glColor4f((float)var5, (float)var6, (float)var7, (float)((float)var2 / 255.0f));
        RenderGlobal.func_181561_a((AxisAlignedBB)var0);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)2881);
        GL11.glPopMatrix();
}
    public static void u(float var0, int var1, float var2, char var3, float var4, int var5, int var6) {
        float var9 = var4 / 2.0f;
        float var10 = var0 + var9;
        float var11 = var2 + var9;
        float var12 = (float)(var5 >> 24 & 0xFF) / 255.0f;
        float var13 = (float)(var5 >> 16 & 0xFF) / 255.0f;
        float var14 = (float)(var5 >> 8 & 0xFF) / 255.0f;
        float var15 = (float)(var5 & 0xFF) / 255.0f;
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)var13, (float)var14, (float)var15, (float)var12);
        Tessellator var16 = Tessellator.func_178181_a();
        WorldRenderer var17 = var16.func_178180_c();
        var17.func_181668_a(6, DefaultVertexFormats.field_181705_e);
        int var18 = Math.max(24, (int)((double)var9 * 1.5));
        double var19 = Math.PI * 2 / (double)var18;
        for (int var21 = 0; var21 <= var18; ++var21) {
            double var22 = (double)var21 * var19;
            float var24 = (float)((double)var10 + Math.sin(var22) * (double)var9);
            float var25 = (float)((double)var11 + Math.cos(var22) * (double)var9);
            var17.func_181662_b((double)var24, (double)var25, 0.0).func_181675_d();
}
        var16.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
    public static void u(float var0, float var1, float var2, ResourceLocation var5) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2929);
        T.func_110434_K().func_110577_a(var5);
        Gui.func_146110_a((int)((int)var0), (int)((int)var1), (float)0.0f, (float)0.0f, (int)((int)var2), (int)((int)var2), (float)((int)var2), (float)((int)var2));
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
}
    public static void X(AxisAlignedBB var0, int var3, int var4, int var5, int var6, float var7) {
        GL11.glLineWidth((float)var7);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        RenderGlobal.func_181563_a((AxisAlignedBB)var0, (int)var3, (int)var4, (int)var5, (int)var6);
        GL11.glDisable((int)2848);
        GL11.glLineWidth((float)2.0f);
}
    public static void j(float var0, float var1, float var2, float var3, float var4, long var5, int var7) {
        RenderUtil.P(var0, var1, var2, var3, var4, var4, var4, var4, var7);
}
    public static void J(float var2, float var3, float var4, float var5, float var6, int var7, int var8, int var9) {
        var2 *= 2.0f;
        var3 *= 2.0f;
        var4 *= 2.0f;
        var5 *= 2.0f;
        GL11.glPushAttrib((int)1);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)9);
        RenderUtil.P(0L, var7);
        for (int var12 = 0; var12 <= 90; var12 += 3) {
            double var13 = (float)var12 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var2 + var6) + Math.sin(var13) * (double)var6 * -1.0), (double)((double)(var3 + var6) + Math.cos(var13) * (double)var6 * -1.0));
}
        for (int var20 = 90; var20 <= 180; var20 += 3) {
            double var27 = (float)var20 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var2 + var6) + Math.sin(var27) * (double)var6 * -1.0), (double)((double)(var5 - var6) + Math.cos(var27) * (double)var6 * -1.0));
}
        for (int var21 = 0; var21 <= 90; var21 += 3) {
            double var28 = (float)var21 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var4 - var6) + Math.sin(var28) * (double)var6), (double)((double)(var5 - var6) + Math.cos(var28) * (double)var6));
}
        for (int var22 = 90; var22 <= 180; var22 += 3) {
            double var29 = (float)var22 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var4 - var6) + Math.sin(var29) * (double)var6), (double)((double)(var3 + var6) + Math.cos(var29) * (double)var6));
}
        GL11.glEnd();
        GL11.glPushMatrix();
        GL11.glShadeModel((int)7425);
        GL11.glLineWidth((float)2.0f);
        GL11.glBegin((int)2);
        if ((long)var8 != 0L) {
            RenderUtil.P(0L, var8);
}
        for (int var23 = 0; var23 <= 90; var23 += 3) {
            double var30 = (float)var23 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var2 + var6) + Math.sin(var30) * (double)var6 * -1.0), (double)((double)(var3 + var6) + Math.cos(var30) * (double)var6 * -1.0));
}
        for (int var24 = 90; var24 <= 180; var24 += 3) {
            double var31 = (float)var24 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var2 + var6) + Math.sin(var31) * (double)var6 * -1.0), (double)((double)(var5 - var6) + Math.cos(var31) * (double)var6 * -1.0));
}
        if (var9 != 0) {
            RenderUtil.P(0L, var9);
}
        for (int var25 = 0; var25 <= 90; var25 += 3) {
            double var32 = (float)var25 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var4 - var6) + Math.sin(var32) * (double)var6), (double)((double)(var5 - var6) + Math.cos(var32) * (double)var6));
}
        for (int var26 = 90; var26 <= 180; var26 += 3) {
            double var33 = (float)var26 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var4 - var6) + Math.sin(var33) * (double)var6), (double)((double)(var3 + var6) + Math.cos(var33) * (double)var6));
}
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GL11.glPopAttrib();
        GL11.glLineWidth((float)1.0f);
        GL11.glShadeModel((int)7424);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
    public static void g(char var0, char var1, ResourceLocation var2, int var3, float var4, float var5, float var6, float var7, int var8) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2929);
        T.func_110434_K().func_110577_a(var2);
        GL11.glColor4f((float)(var8 >> 16 & 0xFF), (float)(var8 >> 8 & 0xFF), (float)(var8 & 0xFF), (float)1.0f);
        Gui.func_146110_a((int)((int)var4), (int)((int)var5), (float)0.0f, (float)0.0f, (int)((int)(var6 / var7)), (int)((int)(var6 / var7)), (float)((int)(var6 / var7)), (float)((int)(var6 / var7)));
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
}
    public static void n(int var0, double var1, long var3) {
        float var5 = (float)(var0 >> 16 & 0xFF) / 255.0f;
        float var6 = (float)(var0 >> 8 & 0xFF) / 255.0f;
        float var7 = (float)(var0 & 0xFF) / 255.0f;
        GlStateManager.func_179131_c((float)var5, (float)var6, (float)var7, (float)((float)var1));
}
    public static void c(long var0, double var2, double var4, double var6, double var8, int var10) {
        float var11 = (float)(var10 >> 24 & 0xFF) / 255.0f;
        float var12 = (float)(var10 >> 16 & 0xFF) / 255.0f;
        float var13 = (float)(var10 >> 8 & 0xFF) / 255.0f;
        float var14 = (float)(var10 & 0xFF) / 255.0f;
        GlStateManager.func_179094_E();
        Tessellator var15 = Tessellator.func_178181_a();
        WorldRenderer var16 = var15.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)var12, (float)var13, (float)var14, (float)var11);
        var16.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        var16.func_181662_b(var2, var8, 0.0).func_181675_d();
        var16.func_181662_b(var6, var8, 0.0).func_181675_d();
        var16.func_181662_b(var6, var4, 0.0).func_181675_d();
        var16.func_181662_b(var2, var4, 0.0).func_181675_d();
        var15.func_78381_a();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179117_G();
        GlStateManager.func_179121_F();
}
    public static void J(Vec3 var0, double var1, double var3, double var5, int var7, float var8, long var9) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        float var20 = (float)(var7 >> 24 & 0xFF) / 255.0f;
        float var21 = (float)(var7 >> 16 & 0xFF) / 255.0f;
        float var22 = (float)(var7 >> 8 & 0xFF) / 255.0f;
        float var23 = (float)(var7 & 0xFF) / 255.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179131_c((float)var21, (float)var22, (float)var23, (float)var20);
        boolean var24 = RenderUtil.T.field_71474_y.field_74336_f;
        RenderUtil.T.field_71474_y.field_74336_f = false;
        EntityRendererAccessor.k(RenderUtil.T.field_71460_t, ClientUtil.H(0L), 2);
        RenderUtil.T.field_71474_y.field_74336_f = var24;
        GL11.glLineWidth((float)var8);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)var0.field_72450_a, (double)var0.field_72448_b, (double)var0.field_72449_c);
        GL11.glVertex3d((double)(var1 - RenderManagerAccessor.k(0L, T.func_175598_ae())), (double)(var3 - RenderManagerAccessor.y(19146, T.func_175598_ae())), (double)(var5 - RenderManagerAccessor.W(0L, T.func_175598_ae())));
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GlStateManager.func_179117_G();
        GlStateManager.func_179121_F();
}
    public static void P(long var0, int var2) {
        GL11.glColor4f((float)((float)(var2 >> 16 & 0xFF) / 255.0f), (float)((float)(var2 >> 8 & 0xFF) / 255.0f), (float)((float)(var2 & 0xFF) / 255.0f), (float)((float)(var2 >> 24 & 0xFF) / 255.0f));
}
    public static void c(BlockPos var0, long var1, int var3, boolean var4, boolean var5) {
        RenderUtil.O(var0.func_177958_n(), var0.func_177956_o(), var0.func_177952_p(), 25559446473706L, 1.0, 1.0, 1.0, var3, var4, var5);
}
    public static void v(EntityLivingBase var0, int var1, double var2, float var4, long var5) {
        var5 = b ^ var5;
        int var11 = (int)((var5 ^ 0x17B708D67688L) >>> 56);
        if (RenderUtil.l((Entity)var0)) {
            Minecraft var14 = MinecraftRef.c((byte)var11, 0L);
            EntityRendererAccessor.k(var14.field_71460_t, ClientUtil.H(0L), 0);
            ScaledResolution var15 = new ScaledResolution(var14);
            double var16 = var0.field_70142_S + (var0.field_70165_t - var0.field_70142_S) * (double)var4 - var14.func_175598_ae().field_78730_l;
            double var18 = var0.field_70137_T + (var0.field_70163_u - var0.field_70137_T) * (double)var4 - var14.func_175598_ae().field_78731_m;
            double var20 = var0.field_70136_U + (var0.field_70161_v - var0.field_70136_U) * (double)var4 - var14.func_175598_ae().field_78728_n;
            AxisAlignedBB var22 = var0.func_174813_aQ().func_72314_b(0.1 + var2, 0.1 + var2, 0.1 + var2);
            AxisAlignedBB var23 = new AxisAlignedBB(var22.field_72340_a - var0.field_70165_t + var16, var22.field_72338_b - var0.field_70163_u + var18, var22.field_72339_c - var0.field_70161_v + var20, var22.field_72336_d - var0.field_70165_t + var16, var22.field_72337_e - var0.field_70163_u + var18, var22.field_72334_f - var0.field_70161_v + var20);
            Vec3[] var24 = new Vec3[]{new Vec3(var23.field_72340_a, var23.field_72338_b, var23.field_72339_c), new Vec3(var23.field_72340_a, var23.field_72338_b, var23.field_72334_f), new Vec3(var23.field_72340_a, var23.field_72337_e, var23.field_72339_c), new Vec3(var23.field_72340_a, var23.field_72337_e, var23.field_72334_f), new Vec3(var23.field_72336_d, var23.field_72338_b, var23.field_72339_c), new Vec3(var23.field_72336_d, var23.field_72338_b, var23.field_72334_f), new Vec3(var23.field_72336_d, var23.field_72337_e, var23.field_72339_c), new Vec3(var23.field_72336_d, var23.field_72337_e, var23.field_72334_f)};
            double var25 = Double.MAX_VALUE;
            double var27 = Double.MAX_VALUE;
            double var29 = Double.MIN_VALUE;
            double var31 = Double.MIN_VALUE;
            boolean var33 = false;
            for (Vec3 var37 : var24) {
                double var38 = var37.field_72450_a;
                double var40 = var37.field_72448_b;
                double var42 = var37.field_72449_c;
                Vec3 var44 = RenderUtil.I(var15.func_78325_e(), var38, var40, var42);
                if (var44 == null || var44.field_72449_c >= 1.0003684 || var44.field_72449_c <= 0.0) continue;
                var33 = true;
                double var45 = var44.field_72450_a;
                double var47 = var44.field_72448_b;
                if (var45 < var25) {
                    var25 = var45;
}
                if (var47 < var27) {
                    var27 = var47;
}
                if (var45 > var29) {
                    var29 = var45;
}
                if (!(var47 > var31)) continue;
                var31 = var47;
}
            if (var33) {
                var14.field_71460_t.func_78478_c();
                ScaledResolution var54 = new ScaledResolution(var14);
                int var55 = var54.func_78326_a();
                int var56 = var54.func_78328_b();
                var25 = Math.max(0.0, var25);
                var27 = Math.max(0.0, var27);
                var29 = Math.min((double)var55, var29);
                var31 = Math.min((double)var56, var31);
                float var57 = (float)(var1 >> 16 & 0xFF) / 255.0f;
                float var58 = (float)(var1 >> 8 & 0xFF) / 255.0f;
                float var39 = (float)(var1 & 0xFF) / 255.0f;
                GL11.glPushMatrix();
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2929);
                GL11.glEnable((int)2848);
                GL11.glLineWidth((float)1.0f);
                GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.4f);
                GL11.glBegin((int)2);
                GL11.glVertex2d((double)var25, (double)var27);
                GL11.glVertex2d((double)var29, (double)var27);
                GL11.glVertex2d((double)var29, (double)var31);
                GL11.glVertex2d((double)var25, (double)var31);
                GL11.glEnd();
                GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.4f);
                GL11.glBegin((int)2);
                GL11.glVertex2d((double)(var25 + 1.0), (double)(var27 + 1.0));
                GL11.glVertex2d((double)(var29 - 1.0), (double)(var27 + 1.0));
                GL11.glVertex2d((double)(var29 - 1.0), (double)(var31 - 1.0));
                GL11.glVertex2d((double)(var25 + 1.0), (double)(var31 - 1.0));
                GL11.glEnd();
                GL11.glColor4f((float)var57, (float)var58, (float)var39, (float)1.0f);
                GL11.glBegin((int)2);
                GL11.glVertex2d((double)(var25 + 0.5), (double)(var27 + 0.5));
                GL11.glVertex2d((double)(var29 - 0.5), (double)(var27 + 0.5));
                GL11.glVertex2d((double)(var29 - 0.5), (double)(var31 - 0.5));
                GL11.glVertex2d((double)(var25 + 0.5), (double)(var31 - 0.5));
                GL11.glEnd();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2929);
                GL11.glDisable((int)2848);
                GL11.glPopMatrix();
}
}
}
    public static void d(long var0, double var2, double var4, double var6, double var8, int var10, int var11, byte var12) {
        float var15 = (float)(var10 >> 24 & 0xFF) / 255.0f;
        float var16 = (float)(var10 >> 16 & 0xFF) / 255.0f;
        float var17 = (float)(var10 >> 8 & 0xFF) / 255.0f;
        float var18 = (float)(var10 & 0xFF) / 255.0f;
        float var19 = (float)(var11 >> 24 & 0xFF) / 255.0f;
        float var20 = (float)(var11 >> 16 & 0xFF) / 255.0f;
        float var21 = (float)(var11 >> 8 & 0xFF) / 255.0f;
        float var22 = (float)(var11 & 0xFF) / 255.0f;
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179103_j((int)7425);
        Tessellator var23 = Tessellator.func_178181_a();
        WorldRenderer var24 = var23.func_178180_c();
        var24.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var24.func_181662_b(var6, var4, (double)X).func_181666_a(var16, var17, var18, var15).func_181675_d();
        var24.func_181662_b(var2, var4, (double)X).func_181666_a(var16, var17, var18, var15).func_181675_d();
        var24.func_181662_b(var2, var8, (double)X).func_181666_a(var20, var21, var22, var19).func_181675_d();
        var24.func_181662_b(var6, var8, (double)X).func_181666_a(var20, var21, var22, var19).func_181675_d();
        var23.func_78381_a();
        GlStateManager.func_179103_j((int)7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
}
    public static void U(long var0) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
}
    public static void q(AxisAlignedBB var0, long var1, float var3, float var4, float var5) {
        RenderUtil.D(var0, var3, var4, var5, 0.25f);
}
    public static void k(float var0, float var1, float var2, float var3, long var4, float var6, int var7) {
        float var8 = (float)(var7 >> 24 & 0xFF) / 255.0f;
        float var9 = (float)(var7 >> 16 & 0xFF) / 255.0f;
        float var10 = (float)(var7 >> 8 & 0xFF) / 255.0f;
        float var11 = (float)(var7 & 0xFF) / 255.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GL11.glLineWidth((float)var6);
        GL11.glColor4f((float)var9, (float)var10, (float)var11, (float)var8);
        GL11.glBegin((int)1);
        GL11.glVertex2f((float)var0, (float)var1);
        GL11.glVertex2f((float)var2, (float)var3);
        GL11.glEnd();
        GlStateManager.func_179084_k();
        GlStateManager.func_179098_w();
        GlStateManager.func_179121_F();
}
    public static Color t(Color var0, Color var1) {
        return RenderUtil.s(var0, var1, 0.5);
}
    private static void e(WorldRenderer var0, BakedQuad var1, float var2, float var5, float var6, float var7, Tessellator var8) {
        int[] var9 = var1.func_178209_a();
        int var11 = var9.length / 4;
        var0.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        for (int var12 = 0; var12 < 4; ++var12) {
            int var13 = var12 * var11;
            float var14 = Float.intBitsToFloat(var9[var13]);
            float var15 = Float.intBitsToFloat(var9[var13 + 1]);
            float var16 = Float.intBitsToFloat(var9[var13 + 2]);
            var0.func_181662_b((double)var14, (double)var15, (double)var16).func_181666_a(var2, var5, var6, var7).func_181675_d();
}
        var8.func_78381_a();
}
    public static void D(AxisAlignedBB var0, float var1, float var4, float var5, float var6) {
        Tessellator var10 = Tessellator.func_178181_a();
        WorldRenderer var11 = var10.func_178180_c();
        var11.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var10.func_78381_a();
        var11.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var10.func_78381_a();
        var11.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var10.func_78381_a();
        var11.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var10.func_78381_a();
        var11.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var10.func_78381_a();
        var11.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var11.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var1, var4, var5, var6).func_181675_d();
        var10.func_78381_a();
}
    public static void l(int var0, long var1) {
        float var3 = (float)(var0 >> 24 & 0xFF) / 255.0f;
        float var4 = (float)(var0 >> 16 & 0xFF) / 255.0f;
        float var5 = (float)(var0 >> 8 & 0xFF) / 255.0f;
        float var6 = (float)(var0 & 0xFF) / 255.0f;
        GlStateManager.func_179131_c((float)var4, (float)var5, (float)var6, (float)var3);
}
    private static long c(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x3B89;
        if (j[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = i[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])k.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    k.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/util/render/RenderUtil", var14);
}
            long var15 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
            RenderUtil.j[var3] = var15;
}
        return j[var3];
}
    public static void l(long var0, AxisAlignedBB var2, int var3) {
        var0 = b ^ var0;
        GlStateManager.func_179094_E();
        float var7 = (float)(var3 >> 24 & 0xFF) / 255.0f;
        float var8 = (float)(var3 >> 16 & 0xFF) / 255.0f;
        float var9 = (float)(var3 >> 8 & 0xFF) / 255.0f;
        float var10 = (float)(var3 & 0xFF) / 255.0f;
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)2.0f);
        GL11.glColor4f((float)var8, (float)var9, (float)var10, (float)var7);
        RenderUtil.D(var2, var8, var9, var10, var7);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GlStateManager.func_179121_F();
}
    public static boolean l(Entity var0) {
        return RenderUtil.F(var0.func_174813_aQ());
}
    public static void P(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var8, int var11) {
        if (!(var2 <= var0) && !(var3 <= var1)) {
            float var14 = var2 - var0;
            float var15 = var3 - var1;
            var4 = Math.min(var4, Math.min(var14, var15) / 2.0f);
            var5 = Math.min(var5, Math.min(var14, var15) / 2.0f);
            var8 = Math.min(var8, Math.min(var14, var15) / 2.0f);
            var6 = Math.min(var6, Math.min(var14, var15) / 2.0f);
            float var16 = (float)(var11 >> 24 & 0xFF) / 255.0f;
            float var17 = (float)(var11 >> 16 & 0xFF) / 255.0f;
            float var18 = (float)(var11 >> 8 & 0xFF) / 255.0f;
            float var19 = (float)(var11 & 0xFF) / 255.0f;
            GlStateManager.func_179147_l();
            GlStateManager.func_179090_x();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            GlStateManager.func_179131_c((float)var17, (float)var18, (float)var19, (float)var16);
            Tessellator var20 = Tessellator.func_178181_a();
            WorldRenderer var21 = var20.func_178180_c();
            var21.func_181668_a(9, DefaultVertexFormats.field_181705_e);
            for (int var23 = 0; var23 <= 90; var23 += 6) {
                double var24 = Math.toRadians(var23);
                var21.func_181662_b((double)(var0 + var4) - Math.sin(var24) * (double)var4, (double)(var1 + var4) - Math.cos(var24) * (double)var4, 0.0).func_181675_d();
}
            for (int var30 = 90; var30 <= 180; var30 += 6) {
                double var33 = Math.toRadians(var30);
                var21.func_181662_b((double)(var0 + var6) - Math.sin(var33) * (double)var6, (double)(var3 - var6) - Math.cos(var33) * (double)var6, 0.0).func_181675_d();
}
            for (int var31 = 0; var31 <= 90; var31 += 6) {
                double var34 = Math.toRadians(var31);
                var21.func_181662_b((double)(var2 - var8) + Math.sin(var34) * (double)var8, (double)(var3 - var8) + Math.cos(var34) * (double)var8, 0.0).func_181675_d();
}
            for (int var32 = 90; var32 <= 180; var32 += 6) {
                double var35 = Math.toRadians(var32);
                var21.func_181662_b((double)(var2 - var5) + Math.sin(var35) * (double)var5, (double)(var1 + var5) + Math.cos(var35) * (double)var5, 0.0).func_181675_d();
}
            var20.func_78381_a();
            GlStateManager.func_179098_w();
            GlStateManager.func_179084_k();
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
}
    public static void r(double var0, double var2, double var6, double var8, double var10, double var12, int var14, int var15) {
        double var18 = var0 - RenderUtil.T.func_175598_ae().field_78730_l;
        double var20 = var2 - RenderUtil.T.func_175598_ae().field_78731_m;
        double var22 = var6 - RenderUtil.T.func_175598_ae().field_78728_n;
        AxisAlignedBB var24 = new AxisAlignedBB(var18, var20, var22, var18 + var8, var20 + var10, var22 + var12);
        RenderUtil.Y(var24, var14, var15, 0L);
}
    private static boolean F(AxisAlignedBB var0) {
        Z.func_78547_a(RenderUtil.T.func_175606_aa().field_70165_t, RenderUtil.T.func_175606_aa().field_70163_u, RenderUtil.T.func_175606_aa().field_70161_v);
        return Z.func_78546_a(var0);
}
    public static Vec3 I(int var0, double var3, double var5, double var7) {
        GL11.glGetFloat((int)2982, (FloatBuffer)D);
        GL11.glGetFloat((int)2983, (FloatBuffer)G);
        GL11.glGetInteger((int)2978, (IntBuffer)A);
        boolean var9 = GLU.gluProject((float)((float)var3), (float)((float)var5), (float)((float)var7), (FloatBuffer)D, (FloatBuffer)G, (IntBuffer)A, (FloatBuffer)Y);
        return var9 ? new Vec3((double)(Y.get(0) / (float)var0), (double)(((float)Display.getHeight() - Y.get(1)) / (float)var0), (double)Y.get(2)) : null;
}
    public static void W(AxisAlignedBB var0, long var1, int var3, int var4, boolean var5, boolean var6) {
        GL11.glPushMatrix();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)2.0f);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2881);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        float var10 = (float)(var3 >> 16 & 0xFF) / 255.0f;
        float var11 = (float)(var3 >> 8 & 0xFF) / 255.0f;
        float var12 = (float)(var3 & 0xFF) / 255.0f;
        float var13 = (float)var4 / 255.0f;
        if (var5) {
            GL11.glColor4f((float)var10, (float)var11, (float)var12, (float)1.0f);
            RenderGlobal.func_181561_a((AxisAlignedBB)var0);
}
        if (var6) {
            GL11.glColor4f((float)var10, (float)var11, (float)var12, (float)var13);
            RenderUtil.D(var0, var10, var11, var12, var13);
}
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)2881);
        GL11.glPopMatrix();
}
    public static void X() {
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
    public static void N(double var0, double var2, long var4, double var6, double var8, float var10) {
        int var13;
        ScaledResolution var11 = new ScaledResolution(T);
        int var12 = var11.func_78325_e();
        switch (var12) {
            case 2: {
                var13 = 540;
                break;
}
            case 3: {
                var13 = 1080;
                break;
}
            case 4: {
                var13 = 270;
                break;
}
            default: {
                var13 = var11.func_78328_b();
}
}
        GL11.glScissor((int)((int)(var0 * (double)var12 * (double)var10)), (int)((int)(((double)var13 - var2 - var8) * (double)var12 * (double)var10)), (int)((int)(var6 * (double)var12 * (double)var10)), (int)((int)(var8 * (double)var12 * (double)var10)));
}
    public static void N(EntityLivingBase var0, long var1, int var3) {
        float var15 = MinecraftAccessor.o((Minecraft)RenderUtil.T).field_74281_c;
        double var16 = var0.field_70142_S + (var0.field_70165_t - var0.field_70142_S) * (double)var15 - RenderUtil.T.func_175598_ae().field_78730_l;
        double var18 = var0.field_70137_T + (var0.field_70163_u - var0.field_70137_T) * (double)var15 - RenderUtil.T.func_175598_ae().field_78731_m;
        double var20 = var0.field_70136_U + (var0.field_70161_v - var0.field_70136_U) * (double)var15 - RenderUtil.T.func_175598_ae().field_78728_n;
        GlStateManager.func_179094_E();
        if (var3 == 0) {
            long[] var12 = new long[]{0L};
            var3 = RenderUtil.M(21658, (short)-14239, 2L, var12);
}
        float var22 = (float)(var3 >> 24 & 0xFF) / 255.0f;
        float var23 = (float)(var3 >> 16 & 0xFF) / 255.0f;
        float var24 = (float)(var3 >> 8 & 0xFF) / 255.0f;
        float var25 = (float)(var3 & 0xFF) / 255.0f;
        AxisAlignedBB var26 = var0.func_174813_aQ().func_72314_b(0.1, 0.1, 0.1);
        AxisAlignedBB var27 = new AxisAlignedBB(var26.field_72340_a - var0.field_70165_t + var16, var26.field_72338_b - var0.field_70163_u + var18, var26.field_72339_c - var0.field_70161_v + var20, var26.field_72336_d - var0.field_70165_t + var16, var26.field_72337_e - var0.field_70163_u + var18, var26.field_72334_f - var0.field_70161_v + var20);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)2.0f);
        GL11.glColor4f((float)var23, (float)var24, (float)var25, (float)var22);
        RenderUtil.D(var27, var23, var24, var25, var22);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GlStateManager.func_179121_F();
}
    public static Color s(Color var0, Color var1, double var2) {
        float var4 = (float)var2;
        float var5 = 1.0f - var4;
        float[] var6 = new float[3];
        float[] var7 = new float[3];
        var0.getColorComponents(var6);
        var1.getColorComponents(var7);
        return new Color(var6[0] * var4 + var7[0] * var5, var6[1] * var4 + var7[1] * var5, var6[2] * var4 + var7[2] * var5);
}
    public static void N(int var0, Entity var2, int var3, float var4) {
        if (var2 instanceof EntityLivingBase) {
            double var8 = var2.field_70142_S + (var2.field_70165_t - var2.field_70142_S) * (double)var4 - RenderUtil.T.func_175598_ae().field_78730_l;
            double var10 = var2.field_70137_T + (var2.field_70163_u - var2.field_70137_T) * (double)var4 - RenderUtil.T.func_175598_ae().field_78731_m;
            double var12 = var2.field_70136_U + (var2.field_70161_v - var2.field_70136_U) * (double)var4 - RenderUtil.T.func_175598_ae().field_78728_n;
            GlStateManager.func_179094_E();
            GL11.glTranslated((double)var8, (double)(var10 - 0.2), (double)var12);
            GL11.glRotated((double)(-RenderUtil.T.func_175598_ae().field_78735_i), (double)0.0, (double)1.0, (double)0.0);
            GlStateManager.func_179097_i();
            GL11.glScalef((float)0.03f, (float)0.03f, (float)0.03f);
            int var14 = Color.black.getRGB();
            Gui.func_73734_a((int)-18, (int)-1, (int)-21, (int)74, (int)var14);
            Gui.func_73734_a((int)18, (int)-1, (int)21, (int)74, (int)var14);
            Gui.func_73734_a((int)-18, (int)-1, (int)21, (int)2, (int)var14);
            Gui.func_73734_a((int)-18, (int)71, (int)21, (int)74, (int)var14);
            Gui.func_73734_a((int)-19, (int)0, (int)-20, (int)73, (int)var3);
            Gui.func_73734_a((int)19, (int)0, (int)20, (int)73, (int)var3);
            Gui.func_73734_a((int)-19, (int)0, (int)20, (int)1, (int)var3);
            Gui.func_73734_a((int)-19, (int)72, (int)20, (int)73, (int)var3);
            GlStateManager.func_179126_j();
            GlStateManager.func_179121_F();
}
}
    public static void r(Vec3 var0, long var1, double var3, double var5, double var7, float var9, float var10, float var11, float var12, float var13) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        GlStateManager.func_179094_E();
        GlStateManager.func_179131_c((float)var9, (float)var10, (float)var11, (float)var12);
        boolean var23 = RenderUtil.T.field_71474_y.field_74336_f;
        RenderUtil.T.field_71474_y.field_74336_f = false;
        EntityRendererAccessor.k(RenderUtil.T.field_71460_t, ClientUtil.H(0L), 2);
        RenderUtil.T.field_71474_y.field_74336_f = var23;
        GL11.glLineWidth((float)var13);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)var0.field_72450_a, (double)var0.field_72448_b, (double)var0.field_72449_c);
        GL11.glVertex3d((double)(var3 - RenderManagerAccessor.k(0L, T.func_175598_ae())), (double)(var5 - RenderManagerAccessor.y(13236, T.func_175598_ae())), (double)(var7 - RenderManagerAccessor.W(0L, T.func_175598_ae())));
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GlStateManager.func_179117_G();
        GlStateManager.func_179121_F();
}
    public static void L() {
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
        GlStateManager.func_179090_x();
        GlStateManager.func_179129_p();
        GlStateManager.func_179118_c();
        GlStateManager.func_179097_i();
}
    public static Color R(Color var0, float var1) {
        float var2 = 0.003921569f * (float)var0.getRed();
        float var3 = 0.003921569f * (float)var0.getGreen();
        float var4 = 0.003921569f * (float)var0.getBlue();
        return new Color(var2, var3, var4, var1);
}
    public static void m(float var0, float var1, long var2, float var4, float var5, float var6, int var7) {
        float var8 = (float)(var7 >> 24 & 0xFF) / 255.0f;
        float var9 = (float)(var7 >> 16 & 0xFF) / 255.0f;
        float var10 = (float)(var7 >> 8 & 0xFF) / 255.0f;
        float var11 = (float)(var7 & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glColor4f((float)var9, (float)var10, (float)var11, (float)var8);
        GL11.glLineWidth((float)var6);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)var0, (double)var1);
        GL11.glVertex2d((double)var0, (double)var5);
        GL11.glVertex2d((double)var4, (double)var5);
        GL11.glVertex2d((double)var4, (double)var1);
        GL11.glVertex2d((double)var0, (double)var1);
        GL11.glVertex2d((double)var4, (double)var1);
        GL11.glVertex2d((double)var0, (double)var5);
        GL11.glVertex2d((double)var4, (double)var5);
        GL11.glEnd();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
}
    public static void n(String var0, float var1, long var2, float var4, int var5, int var6) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var2 = b ^ var2;
        String var7 = var0.replaceAll(RenderUtil.a(15291, 0x17FD313439485986L ^ var2), "");
        RenderUtil.T.field_71466_p.func_175065_a(var7, var1 + 1.0f, var4, var6, false);
        RenderUtil.T.field_71466_p.func_175065_a(var7, var1 - 1.0f, var4, var6, false);
        RenderUtil.T.field_71466_p.func_175065_a(var7, var1, var4 + 1.0f, var6, false);
        RenderUtil.T.field_71466_p.func_175065_a(var7, var1, var4 - 1.0f, var6, false);
        RenderUtil.T.field_71466_p.func_175065_a(var0, var1, var4, var5, false);
}
    public static void o(short var0, double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14, char var15, boolean var16, int var17, boolean var18) {
        long var19 = ((long)var0 << 48 | (long)var15 << 48 >>> 16 | (long)var17 << 32 >>> 32) ^ b;
        long var21 = var19 ^ 0x76950FE04E44L;
        double var23 = var1 - RenderUtil.T.func_175598_ae().field_78730_l;
        double var25 = var3 - RenderUtil.T.func_175598_ae().field_78731_m;
        double var27 = var5 - RenderUtil.T.func_175598_ae().field_78728_n;
        AxisAlignedBB var29 = new AxisAlignedBB(var23, var25, var27, var23 + var7, var25 + var9, var27 + var11);
        RenderUtil.W(var29, var21, var13, var14, var16, var18);
}
    private static void p(IBakedModel var0, float var1, float var2, float var3, float var4) {
        Tessellator var9 = Tessellator.func_178181_a();
        WorldRenderer var10 = var9.func_178180_c();
        for (EnumFacing var14 : EnumFacing.values()) {
            for (BakedQuad var16 : var0.func_177551_a(var14)) {
                RenderUtil.e(var10, var16, var1, var2, var3, var4, var9);
}
}
        for (BakedQuad var19 : var0.func_177550_a()) {
            RenderUtil.e(var10, var19, var1, var2, var3, var4, var9);
}
}
    public static void m(float var0, float var1, float var2, float var3, float var4, int var5, int var6, long var7, int var9, int var10) {
        var0 *= 2.0f;
        var1 *= 2.0f;
        var2 *= 2.0f;
        var3 *= 2.0f;
        GL11.glPushAttrib((int)1);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)9);
        RenderUtil.P(0L, var5);
        for (int var13 = 0; var13 <= 90; var13 += 3) {
            double var14 = (float)var13 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var0 + var4) + Math.sin(var14) * (double)var4 * -1.0), (double)((double)(var1 + var4) + Math.cos(var14) * (double)var4 * -1.0));
}
        for (int var21 = 90; var21 <= 180; var21 += 3) {
            double var28 = (float)var21 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var0 + var4) + Math.sin(var28) * (double)var4 * -1.0), (double)((double)(var3 - var4) + Math.cos(var28) * (double)var4 * -1.0));
}
        for (int var22 = 0; var22 <= 90; var22 += 3) {
            double var29 = (float)var22 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var2 - var4) + Math.sin(var29) * (double)var4), (double)((double)(var3 - var4) + Math.cos(var29) * (double)var4));
}
        for (int var23 = 90; var23 <= 180; var23 += 3) {
            double var30 = (float)var23 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var2 - var4) + Math.sin(var30) * (double)var4), (double)((double)(var1 + var4) + Math.cos(var30) * (double)var4));
}
        GL11.glEnd();
        GL11.glPushMatrix();
        GL11.glShadeModel((int)7425);
        GL11.glLineWidth((float)2.0f);
        GL11.glBegin((int)2);
        if ((long)var6 != 0L) {
            RenderUtil.P(0L, var6);
}
        for (int var24 = 0; var24 <= 90; var24 += 3) {
            double var31 = (float)var24 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var0 + var4) + Math.sin(var31) * (double)var4 * -1.0), (double)((double)(var1 + var4) + Math.cos(var31) * (double)var4 * -1.0));
}
        for (int var25 = 90; var25 <= 180; var25 += 3) {
            double var32 = (float)var25 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var0 + var4) + Math.sin(var32) * (double)var4 * -1.0), (double)((double)(var3 - var4) + Math.cos(var32) * (double)var4 * -1.0));
}
        if (var9 != 0) {
            RenderUtil.P(0L, var9);
}
        for (int var26 = 0; var26 <= 90; var26 += 3) {
            double var33 = (float)var26 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var2 - var4) + Math.sin(var33) * (double)var4), (double)((double)(var3 - var4) + Math.cos(var33) * (double)var4));
}
        if (var10 != 0) {
            RenderUtil.P(0L, var10);
}
        for (int var27 = 90; var27 <= 180; var27 += 3) {
            double var34 = (float)var27 * ((float)Math.PI / 180);
            GL11.glVertex2d((double)((double)(var2 - var4) + Math.sin(var34) * (double)var4), (double)((double)(var1 + var4) + Math.cos(var34) * (double)var4));
}
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GL11.glPopAttrib();
        GL11.glLineWidth((float)1.0f);
        GL11.glShadeModel((int)7424);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
    public static void G() {
        GlStateManager.func_179084_k();
}
    public static void I(double var3, double var5, double var7, double var9, int var11, int var12) {
        float var15 = (float)var12 / 255.0f;
        float var16 = (float)(var11 >> 16 & 0xFF) / 255.0f;
        float var17 = (float)(var11 >> 8 & 0xFF) / 255.0f;
        float var18 = (float)(var11 & 0xFF) / 255.0f;
        GlStateManager.func_179094_E();
        Tessellator var19 = Tessellator.func_178181_a();
        WorldRenderer var20 = var19.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)var16, (float)var17, (float)var18, (float)var15);
        var20.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        var20.func_181662_b(var3, var9, 0.0).func_181675_d();
        var20.func_181662_b(var7, var9, 0.0).func_181675_d();
        var20.func_181662_b(var7, var5, 0.0).func_181675_d();
        var20.func_181662_b(var3, var5, 0.0).func_181675_d();
        var19.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
}
    public static Color n(int var0, int var1, long var2) {
        return RenderUtil.s(RenderUtil.x(var0, 0L), RenderUtil.x(var1, 0L), 0.5);
}
    public static void V(float var0, float var1, float var2, float var3) {
        ScaledResolution var4 = new ScaledResolution(T);
        int var5 = var4.func_78325_e();
        int var6 = var4.func_78328_b();
        GL11.glScissor((int)((int)(var0 * (float)var5)), (int)((int)(((float)var6 - var1) * (float)var5)), (int)((int)(var2 * (float)var5)), (int)((int)(var3 * (float)var5)));
}
    public static void m(int var0, String var1, int var2, int var3, short var4, int var5) {
        long var6 = ((long)var0 << 32 | (long)var3 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ b;
        long var8 = var6 ^ 0x35408D3E0A09L;
        long var10 = var6 ^ 0x52B103D3C0B1L;
        long var14 = var6 ^ 0x4AC68FFF3E8CL;
        if (!var1.isEmpty()) {
            CustomFont var18 = RavenClickGuiScreen.t();
            String[] var19 = var1.split("\n");
            double var20 = var18.R(var19[0], var14);
            double var22 = var18.o(var10);
            for (String var27 : var19) {
                RenderUtil.c(0L, var2 + 5, (double)var5 + var22 - 2.0, (double)(var2 + 6) + var20 + 1.0, (double)var5 + var22 * 2.0, n);
                var18.v(var27, var2 + 6, (float)((double)var5 + var22 - 1.0), a, var8, false);
                var5 += (int)Math.round(var22);
}
}
}
    public static void r(AxisAlignedBB var2, int var3, int var4, int var6, float var7) {
        RenderUtil.L();
        GL11.glLineWidth((float)var7);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        RenderGlobal.func_181563_a((AxisAlignedBB)var2, (int)var3, (int)var4, (int)var6, (int)255);
        GL11.glDisable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GlStateManager.func_179117_G();
        RenderUtil.w();
}
    public static void J(long var0, CustomFont var2, String var3, float var4, float var5, int var6, int var7) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var8 = var0 ^ 0x7013B2CB4FFEL;
        String var10 = var3.replaceAll("(?i)\u00a7[\\da-f]", "");
        var2.v(var10, var4 + 1.0f, var5, var7, var8, false);
        var2.v(var10, var4 - 1.0f, var5, var7, var8, false);
        var2.v(var10, var4, var5 + 1.0f, var7, var8, false);
        var2.v(var10, var4, var5 - 1.0f, var7, var8, false);
        var2.v(var3, var4, var5, var6, var8, false);
}
    public static void M(BlockPos var0, long var1, int var3, int var4, boolean var5, boolean var6) {
        var1 = b ^ var1;
        int var7 = (int)((var1 ^ 0x792E905E0B0CL) >>> 48);
        int var8 = (int)((var1 ^ 0x792E905E0B0CL) << 16 >>> 48);
        int var9 = (int)((var1 ^ 0x792E905E0B0CL) << 32 >>> 32);
        RenderUtil.o((short)var7, var0.func_177958_n(), var0.func_177956_o(), var0.func_177952_p(), 1.0, 1.0, 1.0, var3, var4, (char)var8, var5, var9, var6);
}
    public static void O(int var0, float var1, long var2) {
        float var4 = (float)(var0 >> 16 & 0xFF) / 255.0f;
        float var5 = (float)(var0 >> 8 & 0xFF) / 255.0f;
        float var6 = (float)(var0 & 0xFF) / 255.0f;
        GlStateManager.func_179131_c((float)var4, (float)var5, (float)var6, (float)var1);
}
    public static void m(float var0, float var1, float var2, float var3, long var4, float var6, float var7, float var8, float var9, float var10) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GL11.glLineWidth((float)var6);
        GL11.glColor4f((float)var7, (float)var8, (float)var9, (float)var10);
        GL11.glBegin((int)1);
        GL11.glVertex2f((float)var0, (float)var1);
        GL11.glVertex2f((float)var2, (float)var3);
        GL11.glEnd();
        GlStateManager.func_179084_k();
        GlStateManager.func_179098_w();
        GlStateManager.func_179121_F();
}
    public static int M(int var0, short var1, long var2, long ... var5) {
        long var8 = System.currentTimeMillis() + (var5.length > 0 ? var5[0] : 0L);
        return Color.getHSBColor((float)(var8 % 7500L) / 7500.0f, 1.0f, 1.0f).getRGB();
}
    public static void n(BlockPos var0, double var1, int var3, int var4, int var5, long var6, int var8, float var9) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var6 = b ^ var6;
        int var16 = (int)((var6 ^ 0x4EBAC0EC0AC9L) >>> 32);
        RenderUtil.X(new AxisAlignedBB((double)var0.func_177958_n(), (double)var0.func_177956_o(), (double)var0.func_177952_p(), (double)var0.func_177958_n() + 1.0, (double)var0.func_177956_o() + var1, (double)var0.func_177952_p() + 1.0).func_72317_d(-RenderManagerAccessor.k(0L, T.func_175598_ae()), -RenderManagerAccessor.y(var16, T.func_175598_ae()), -RenderManagerAccessor.W(0L, T.func_175598_ae())), var3, var4, var5, var8, var9);
}
    public static void V(EntityLivingBase var0, long var1, double var3, double var5, double var7, int var9) {
        long var10 = var1 ^ 0x63892D801643L;
        RenderUtil.Z(var0.func_174813_aQ().func_72314_b(0.1, 0.1, 0.1), var0.field_70142_S, var0.field_70137_T, var0.field_70136_U, var3, var5, var10, var7, var9);
}
    static {
        b = 23109265913955L;
        n = new Color(0, 0, 0, 100).getRGB();
        a = new Color(229, 229, 229, 255).getRGB();
        D = BufferUtils.createFloatBuffer((int)16);
        G = BufferUtils.createFloatBuffer((int)16);
        A = BufferUtils.createIntBuffer((int)16);
        Y = BufferUtils.createFloatBuffer((int)3);
        Z = new Frustum();
        l = new Frustum();
        R = MinecraftRef.c((byte)0, 0L).func_175599_af();
        T = MinecraftRef.c((byte)0, 0L);
        e = new HashMap(13);
        c = new String[]{"\u00cb\u0081\u00af\u00b3\u0096\u00e4\u00c1\u00e1\u0081\u00efC\u00e5\u00c3R\u008a0", "\u0012pl\u00e7\u0087O\u0006F-n\u008b\u0010\u001d\u008bX\u00f8"};
        d = new String[2];
        h = new HashMap(13);
        f = new long[]{-5088694070550099896L, 8155316329243142677L, -7422892396150652391L, 742474754744504564L, -8729608455096803099L, 2470290002738801662L, 6239973166703145587L, 8030495419858915825L, -1298946361898594236L, -1545267937410721481L, 1419340624431703604L, -5476428765416965539L, -7003657242432300719L, -2495216312109787061L, 2343146126028375485L, -4478592037699991809L, -6488717641568478588L, -1744428310593751694L, 8324249328261687948L, 7812365694904717541L, 6917391024997969355L, -3175613616820735565L, 7312472316776562945L, -4558771325751885656L, 4347900262008322153L, 6498785137216718408L, 6616957481526128662L, -9202409458979254657L, 7588322063655537140L, -779626535569165101L, -5512695679228330404L, -7092401608600228187L, 2306158580603804895L, -4596565801282309343L, 8812805725538072174L, 7497747270486841069L, 6417900034124518443L, 2374880926906000685L, 2160321411613336491L, 3103534474377545248L, 6517086263121659013L, 2019193806670065591L, 4978688397698424480L, 8029601312073824740L, 4122547359624308305L, 3963083747440586265L, 4914873387444537348L, 6356898738722732384L, -5846833725223146037L, 7317652451318471854L, 5466057205853538398L, -1884582349168091766L, 5895617322528213452L, 7367905349025222830L, -1037739916195323074L, 6188277999662890523L, 4857569544702086880L, -5519463796391533552L, -7526714701279271715L, -330132507026886760L, -9034445706494238907L, 517012506083509977L, 4476265700608652599L, 6810216486767436539L, 4850569218703218001L, -965043872831246782L, 2979301730645324363L, -4404520798714946088L, 5440983928867488244L, 331685453876583593L, 6339265489527405045L, -5381969615694432066L, 6175778928660765973L, -3297697833550516828L, -2459954745810165526L, 3062878923982220060L};
        k = new HashMap(13);
        i = new long[]{5653317873009865945L, 1291119500788730589L, 6010980468042206603L, -5828199305021914624L};
        j = new Long[4];
}
}