/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 */
package Abyss.ASM.Hooks.Entity;

import Abyss.AbyssClient;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.events.UpdateCameraAndRenderEvent;
import Abyss.internal.accessor.EntityPlayerAccessor;
import Abyss.internal.accessor.EntityRendererAccessor;
import Abyss.module.ModuleManager;
import Abyss.module.impl.configuration.Notifications;
import Abyss.module.impl.player.GhostHand;
import Abyss.module.impl.visual.NoHurtCam;
import Abyss.util.BlockUtil;
import Abyss.util.Box;
import Abyss.util.ClientUtil;
import Abyss.util.ItemUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import Abyss.util.Sneaky;
import Abyss.util.render.VisualSpoofRenderer;
import java.io.UnsupportedEncodingException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EntityRendererHooks {
    private static Box<Integer> s = null;
    private static final String[] c = new String[6];
    private static final long private static final Object[] b = new Object[6];
    private static Box<ItemStack> q = null;
    private static final Minecraft Y;
    private static boolean m;

    public static Vec3 redirectGetLook(float var0) {
        return RotationUtil.T(var0);
}
    private static void announceLoaded() {
        if (!m && Boolean.parseBoolean(System.getProperty("abyss.injection.loaded", "false")) && EntityRendererHooks.Y.field_71439_g != null) {
            m = true;
            new Thread(() -> {
                block5: {
                    try {
                        long var0 = 85534997054632L;
                        try {
                            Thread.sleep(500L);
                            String var9 = "Abyss injected!";
                            if (Notifications.graphic != null && Notifications.graphic.R("CHAT")) {
                                Notifications.G(58053444091952L, var9, true);
                                break block5;
}
                            ClientUtil.t(48081174263320L, var9);
                            Notifications.Z(350448395L, var9, '\uab6b', true, 4000.0f);
}
                        catch (InterruptedException var10) {
                            Thread.currentThread().interrupt();
}
}
                    catch (Throwable ex) {
                        throw Sneaky.rethrow(ex);
}
}
            }, "Abyss announce").start();
}
}
    private static void a() {
        EntityRendererHooks.b[0] = "\u0013T\u00076~Z\u0018";
        EntityRendererHooks.b[1] = Float.TYPE;
        EntityRendererHooks.c[1] = "java/lang/Float";
        EntityRendererHooks.b[2] = Long.TYPE;
        EntityRendererHooks.c[2] = "java/lang/Long";
        EntityRendererHooks.b[3] = Void.TYPE;
        EntityRendererHooks.c[3] = "java/lang/Void";
        EntityRendererHooks.b[4] = "X3\fDj\u0014S<\u001d\u000b\u000b\u001aX7\u0019Q";
        EntityRendererHooks.b[5] = "':M\u007fAwqn\u0015\u001aN\u001dao\u001esGx%9\u0018\u001a\u0005`{+\u0018`Mqdnr \u0003x\u007f&\u001cs@t#T";
}
    public static void postUpdateCameraAndRender() {
        if (q != null) {
            EntityPlayerAccessor.Z((EntityPlayer)EntityRendererHooks.Y.field_71439_g, (ItemStack)EntityRendererHooks.q.Z);
            q = null;
}
        if (s != null) {
            EntityPlayerAccessor.e(0L, (EntityPlayer)EntityRendererHooks.Y.field_71439_g, (Integer)EntityRendererHooks.s.Z);
            s = null;
}
}
    public static void onRender2D(float var0) throws Throwable {
        if (!VisualSpoofRenderer.x() && !VisualSpoofRenderer.H()) {
            VisualSpoofRenderer.l(var0);
            EntityRendererHooks.announceLoaded();
            ScaledResolution var8 = new ScaledResolution(Y);
            AbyssClient.w.e(new Render2DEvent(10652, -426, var0, -32122, var8), 18670087776179L);
}
}
    public static boolean bypassConfusionIfNeeded(Potion var0, EntityLivingBase var1) {
        return var0 == Potion.field_76431_k && ModuleManager.O.o() ? false : var1.func_70660_b(var0) != null;
}
    public static void updateCameraAndRender() throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var10 = -1274601225;
        VisualSpoofRenderer.P();
        if (EntityRendererHooks.Y.field_71439_g != null && ItemUtil.d()) {
            UpdateCameraAndRenderEvent var15 = new UpdateCameraAndRenderEvent(1652973691147L, false);
            AbyssClient.w.e(var15, 18670087776179L);
            if (var15.r()) {
                q = new Box<ItemStack>(EntityPlayerAccessor.o('\u0000', (EntityPlayer)EntityRendererHooks.Y.field_71439_g, '\u46ad'));
                EntityPlayerAccessor.Z((EntityPlayer)EntityRendererHooks.Y.field_71439_g, EntityRendererHooks.Y.field_71439_g.field_71071_by.func_70448_g());
                s = new Box<Integer>(EntityPlayerAccessor.J((EntityPlayer)EntityRendererHooks.Y.field_71439_g));
                EntityPlayerAccessor.e(0L, (EntityPlayer)EntityRendererHooks.Y.field_71439_g, 69000);
}
}
}
    public static void onGetMouseOverList(Minecraft var0, List<Entity> var1) {
        if (ModuleManager.Q.o() && BlockUtil.a(3484929262775L, var0.field_71442_b.func_78757_d())) {
            GhostHand.T(var1);
}
}
    public static Boolean orientCamera(Minecraft var0, float var1, float var2, float var3) {
        if (!ModuleManager.h.o()) {
            return null;
}
        Entity var4 = var0.func_175606_aa();
        float var5 = var4.func_70047_e();
        if (var4 instanceof EntityLivingBase && ((EntityLivingBase)var4).func_70608_bn()) {
            var5 += 1.0f;
            GlStateManager.func_179109_b((float)0.0f, (float)0.3f, (float)0.0f);
            if (!var0.field_71474_y.field_74325_U) {
                GlStateManager.func_179114_b((float)(var4.field_70126_B + (var4.field_70177_z - var4.field_70126_B) * var1 + 180.0f), (float)0.0f, (float)-1.0f, (float)0.0f);
                GlStateManager.func_179114_b((float)(var4.field_70127_C + (var4.field_70125_A - var4.field_70127_C) * var1), (float)-1.0f, (float)0.0f, (float)0.0f);
}
        } else if (var0.field_71474_y.field_74320_O > 0) {
            double var6 = var3 + (var2 - var3) * var1;
            if (var0.field_71474_y.field_74325_U) {
                GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)((float)(-var6)));
            } else {
                float var8 = var4.field_70177_z;
                float var9 = var4.field_70125_A;
                if (var0.field_71474_y.field_74320_O == 2) {
                    var9 += 180.0f;
}
                if (var0.field_71474_y.field_74320_O == 2) {
                    GlStateManager.func_179114_b((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
}
                GlStateManager.func_179114_b((float)(var4.field_70125_A - var9), (float)1.0f, (float)0.0f, (float)0.0f);
                GlStateManager.func_179114_b((float)(var4.field_70177_z - var8), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)((float)(-var6)));
                GlStateManager.func_179114_b((float)(var8 - var4.field_70177_z), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.func_179114_b((float)(var9 - var4.field_70125_A), (float)1.0f, (float)0.0f, (float)0.0f);
}
        } else {
            GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)-0.1f);
}
        if (!var0.field_71474_y.field_74325_U) {
            float var10 = var4.field_70126_B + (var4.field_70177_z - var4.field_70126_B) * var1 + 180.0f;
            float var11 = var4.field_70127_C + (var4.field_70125_A - var4.field_70127_C) * var1;
            float var12 = 0.0f;
            if (var4 instanceof EntityAnimal) {
                EntityAnimal var18 = (EntityAnimal)var4;
                var10 = var18.field_70758_at + (var18.field_70759_as - var18.field_70758_at) * var1 + 180.0f;
}
            GlStateManager.func_179114_b((float)var12, (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.func_179114_b((float)var11, (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.func_179114_b((float)var10, (float)0.0f, (float)1.0f, (float)0.0f);
}
        GlStateManager.func_179109_b((float)0.0f, (float)(-var5), (float)0.0f);
        double var17 = var4.field_70169_q + (var4.field_70165_t - var4.field_70169_q) * (double)var1;
        double var13 = var4.field_70167_r + (var4.field_70163_u - var4.field_70167_r) * (double)var1 + (double)var5;
        double var15 = var4.field_70166_s + (var4.field_70161_v - var4.field_70166_s) * (double)var1;
        return var0.field_71438_f.func_72721_a(var17, var13, var15, var1);
}
    public static boolean bypassBlindnessIfNeeded(Potion var0, EntityLivingBase var1) {
        return var0 == Potion.field_76440_q && ModuleManager.O.o() ? false : var1.func_70660_b(var0) != null;
}
    public static boolean hurtCameraEffect(Minecraft var0, float var1) {
        if (!(var0.func_175606_aa() instanceof EntityLivingBase)) {
            return false;
}
        EntityLivingBase var2 = (EntityLivingBase)var0.func_175606_aa();
        float var3 = (float)var2.field_70737_aN - var1;
        if (var2.func_110143_aJ() <= 0.0f) {
            float var4 = (float)var2.field_70725_aQ + var1;
            GlStateManager.func_179114_b((float)(40.0f - 8000.0f / (var4 + 200.0f)), (float)0.0f, (float)0.0f, (float)1.0f);
}
        if (var3 < 0.0f) {
            return true;
}
        var3 /= (float)var2.field_70738_aO;
        var3 = MathHelper.func_76126_a((float)(var3 * var3 * var3 * var3 * (float)Math.PI));
        float var7 = var2.field_70739_aP;
        if (ModuleManager.g.o()) {
            var3 *= (float)NoHurtCam.effect.k() / 100.0f;
            var7 *= (float)NoHurtCam.effect.k() / 100.0f;
}
        GlStateManager.func_179114_b((float)(-var7), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(-var3 * 14.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)var7, (float)0.0f, (float)1.0f, (float)0.0f);
        return true;
}
    public static void onRender3D(EntityRenderer var0, float var1) throws Throwable {
        if (!VisualSpoofRenderer.x() && !VisualSpoofRenderer.H()) {
            VisualSpoofRenderer.f();
            ScaledResolution var10 = new ScaledResolution(Y);
            RotationManager.z(var1, 0L);
            EntityRendererAccessor.k(var0, var1, 0);
            AbyssClient.w.e(new Render3DEvent(var1, var10), 18670087776179L);
}
}
    static {
        m = false;
        boolean var2 = false;
        EntityRendererHooks.a();
        Y = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
}
}