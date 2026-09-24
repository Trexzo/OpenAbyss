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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class EntityRendererHooks {
    private static Box<Integer> s = null;
    private static final String[] c = new String[6];
    private static final Object[] b = new Object[6];
    private static Box<ItemStack> q = null;
    private static final Minecraft Y;
    private static boolean m;

    public static Vec3 redirectGetLook(float var0) {
        return RotationUtil.T(var0);
}
    private static void announceLoaded() {
        if (!m && Boolean.parseBoolean(System.getProperty("abyss.injection.loaded", "false")) && EntityRendererHooks.Y.thePlayer != null) {
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
            EntityPlayerAccessor.Z((EntityPlayer)EntityRendererHooks.Y.thePlayer, (ItemStack)EntityRendererHooks.q.Z);
            q = null;
}
        if (s != null) {
            EntityPlayerAccessor.e(0L, (EntityPlayer)EntityRendererHooks.Y.thePlayer, (Integer)EntityRendererHooks.s.Z);
            s = null;
}
}
    public static void onRender2D(float var0) throws Throwable {
        if (!VisualSpoofRenderer.x() && !VisualSpoofRenderer.H()) {
            VisualSpoofRenderer.l(var0);
            EntityRendererHooks.announceLoaded();
            ScaledResolution var8 = new ScaledResolution(Y);
            AbyssClient.w.e(new Render2DEvent(10652, (short)65110, var0, (short)33414, var8), 18670087776179L);
}
}
    public static boolean bypassConfusionIfNeeded(Potion var0, EntityLivingBase var1) {
        return var0 == Potion.confusion && ModuleManager.O.o() ? false : var1.getActivePotionEffect(var0) != null;
}
    public static void updateCameraAndRender() throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var10 = -1274601225;
        VisualSpoofRenderer.P();
        if (EntityRendererHooks.Y.thePlayer != null && ItemUtil.d()) {
            UpdateCameraAndRenderEvent var15 = new UpdateCameraAndRenderEvent(1652973691147L, false);
            AbyssClient.w.e(var15, 18670087776179L);
            if (var15.r()) {
                q = new Box<ItemStack>(EntityPlayerAccessor.o('\u0000', (EntityPlayer)EntityRendererHooks.Y.thePlayer, '\u46ad'));
                EntityPlayerAccessor.Z((EntityPlayer)EntityRendererHooks.Y.thePlayer, EntityRendererHooks.Y.thePlayer.inventory.getCurrentItem());
                s = new Box<Integer>(EntityPlayerAccessor.J((EntityPlayer)EntityRendererHooks.Y.thePlayer));
                EntityPlayerAccessor.e(0L, (EntityPlayer)EntityRendererHooks.Y.thePlayer, 69000);
}
}
}
    public static void onGetMouseOverList(Minecraft var0, List<Entity> var1) {
        if (ModuleManager.Q.o() && BlockUtil.a(3484929262775L, var0.playerController.getBlockReachDistance())) {
            GhostHand.T(var1);
}
}
    public static Boolean orientCamera(Minecraft var0, float var1, float var2, float var3) {
        if (!ModuleManager.h.o()) {
            return null;
}
        Entity var4 = var0.getRenderViewEntity();
        float var5 = var4.getEyeHeight();
        if (var4 instanceof EntityLivingBase && ((EntityLivingBase)var4).isPlayerSleeping()) {
            var5 += 1.0f;
            GlStateManager.translate((float)0.0f, (float)0.3f, (float)0.0f);
            if (!var0.gameSettings.debugCamEnable) {
                GlStateManager.rotate((float)(var4.prevRotationYaw + (var4.rotationYaw - var4.prevRotationYaw) * var1 + 180.0f), (float)0.0f, (float)-1.0f, (float)0.0f);
                GlStateManager.rotate((float)(var4.prevRotationPitch + (var4.rotationPitch - var4.prevRotationPitch) * var1), (float)-1.0f, (float)0.0f, (float)0.0f);
}
        } else if (var0.gameSettings.thirdPersonView > 0) {
            double var6 = var3 + (var2 - var3) * var1;
            if (var0.gameSettings.debugCamEnable) {
                GlStateManager.translate((float)0.0f, (float)0.0f, (float)((float)(-var6)));
            } else {
                float var8 = var4.rotationYaw;
                float var9 = var4.rotationPitch;
                if (var0.gameSettings.thirdPersonView == 2) {
                    var9 += 180.0f;
}
                if (var0.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
}
                GlStateManager.rotate((float)(var4.rotationPitch - var9), (float)1.0f, (float)0.0f, (float)0.0f);
                GlStateManager.rotate((float)(var4.rotationYaw - var8), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.translate((float)0.0f, (float)0.0f, (float)((float)(-var6)));
                GlStateManager.rotate((float)(var8 - var4.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.rotate((float)(var9 - var4.rotationPitch), (float)1.0f, (float)0.0f, (float)0.0f);
}
        } else {
            GlStateManager.translate((float)0.0f, (float)0.0f, (float)-0.1f);
}
        if (!var0.gameSettings.debugCamEnable) {
            float var10 = var4.prevRotationYaw + (var4.rotationYaw - var4.prevRotationYaw) * var1 + 180.0f;
            float var11 = var4.prevRotationPitch + (var4.rotationPitch - var4.prevRotationPitch) * var1;
            float var12 = 0.0f;
            if (var4 instanceof EntityAnimal) {
                EntityAnimal var18 = (EntityAnimal)var4;
                var10 = var18.prevRotationYawHead + (var18.rotationYawHead - var18.prevRotationYawHead) * var1 + 180.0f;
}
            GlStateManager.rotate((float)var12, (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.rotate((float)var11, (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)var10, (float)0.0f, (float)1.0f, (float)0.0f);
}
        GlStateManager.translate((float)0.0f, (float)(-var5), (float)0.0f);
        double var17 = var4.prevPosX + (var4.posX - var4.prevPosX) * (double)var1;
        double var13 = var4.prevPosY + (var4.posY - var4.prevPosY) * (double)var1 + (double)var5;
        double var15 = var4.prevPosZ + (var4.posZ - var4.prevPosZ) * (double)var1;
        return var0.renderGlobal.hasCloudFog(var17, var13, var15, var1);
}
    public static boolean bypassBlindnessIfNeeded(Potion var0, EntityLivingBase var1) {
        return var0 == Potion.blindness && ModuleManager.O.o() ? false : var1.getActivePotionEffect(var0) != null;
}
    public static boolean hurtCameraEffect(Minecraft var0, float var1) {
        if (!(var0.getRenderViewEntity() instanceof EntityLivingBase)) {
            return false;
}
        EntityLivingBase var2 = (EntityLivingBase)var0.getRenderViewEntity();
        float var3 = (float)var2.hurtTime - var1;
        if (var2.getHealth() <= 0.0f) {
            float var4 = (float)var2.deathTime + var1;
            GlStateManager.rotate((float)(40.0f - 8000.0f / (var4 + 200.0f)), (float)0.0f, (float)0.0f, (float)1.0f);
}
        if (var3 < 0.0f) {
            return true;
}
        var3 /= (float)var2.maxHurtTime;
        var3 = MathHelper.sin((float)(var3 * var3 * var3 * var3 * (float)Math.PI));
        float var7 = var2.attackedAtYaw;
        if (ModuleManager.g.o()) {
            var3 *= (float)NoHurtCam.effect.k() / 100.0f;
            var7 *= (float)NoHurtCam.effect.k() / 100.0f;
}
        GlStateManager.rotate((float)(-var7), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(-var3 * 14.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.rotate((float)var7, (float)0.0f, (float)1.0f, (float)0.0f);
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