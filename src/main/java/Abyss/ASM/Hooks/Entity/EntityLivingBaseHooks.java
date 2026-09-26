/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 */
package Abyss.ASM.Hooks.Entity;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.AbyssClient;
import Abyss.event.events.GetArmSwingAnimationEndEvent;
import Abyss.event.events.JumpEvent;
import Abyss.event.events.LivingDeathEvent;
import Abyss.event.events.MoveEntityWithHeadingEvent;
import Abyss.event.events.MoveFlyingEvent;
import Abyss.internal.accessor.EntityLivingBaseAccessor;
import Abyss.util.ClientUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.RotationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class EntityLivingBaseHooks {
    private static final Minecraft Z;
    public static void onMoveFlying(EntityLivingBase var0, float var1, float var2, float var3, EntityLivingBase var4) {
        if (var4 instanceof EntityPlayerSP) {
            MoveFlyingEvent var11 = new MoveFlyingEvent(var1, var2, var3);
            AbyssClient.w.e(var11, 18670087776179L);
            var1 = var11.a$r3();
            var2 = var11.b();
            var3 = var11.p();
            var0.moveFlying(var1, var2, var3);
        } else {
            var0.moveFlying(var1, var2, var3);
}
}
    public static void onJump(EntityLivingBase var0, CallbackInfo var1) {
        JumpEvent var8 = new JumpEvent(EntityLivingBaseAccessor.e(var0), RotationManager.r);
        AbyssClient.w.e(var8, 18670087776179L);
        if (!var8.a()) {
            var0.motionY = var8.o();
            if (var0.isPotionActive(Potion.jump)) {
                var0.motionY += (double)((float)(var0.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f);
}
            if (var0.isSprinting()) {
                float var9 = var8.j() * ((float)Math.PI / 180);
                var0.motionX -= (double)(MathHelper.sin((float)var9) * 0.2f);
                var0.motionZ += (double)(MathHelper.cos((float)var9) * 0.2f);
}
            var0.isAirBorne = true;
            var1.cancel();
}
}
    public static void onFunc_110146_f(EntityLivingBase var0, float var1, float var2, CallbackInfoReturnable<Float> var3) {
        boolean var7;
        float var4 = var0.rotationYaw;
        if (var0 instanceof EntityPlayerSP) {
            if (EntityLivingBaseHooks.Z.thePlayer.swingProgress > 0.0f) {
                var1 = RotationManager.I;
}
            var4 = RotationManager.I;
            EntityLivingBaseHooks.Z.thePlayer.rotationYawHead = RotationManager.I;
}
        float var5 = MathHelper.wrapAngleTo180_float((float)(var1 - var0.renderYawOffset));
        var0.renderYawOffset += var5 * 0.3f;
        float var6 = MathHelper.wrapAngleTo180_float((float)(var4 - var0.renderYawOffset));
        boolean bl = var7 = var6 < 90.0f && var6 > -90.0f;
        if (var6 < -75.0f) {
            var6 = -75.0f;
}
        if (var6 > 75.0f) {
            var6 = 75.0f;
}
        var0.renderYawOffset = var4 - var6;
        if (var6 * var6 > 2500.0f) {
            var0.renderYawOffset += var6 * 0.2f;
}
        if (!var7) {
            var2 *= -1.0f;
}
        var3.setReturnValue(Float.valueOf(var2));
        var3.cancel();
}
    public static void onGetArmSwingAnimationEnd(EntityLivingBase var0, CallbackInfoReturnable<Integer> var1) {
        if (var0 instanceof EntityPlayerSP) {
            GetArmSwingAnimationEndEvent var11 = new GetArmSwingAnimationEndEvent(1236, var1.getReturnValue());
            AbyssClient.w.e(var11, 18670087776179L);
            var1.setReturnValue((int)((float)var11.N() * ClientUtil.b((long)75703014522979L).timerSpeed));
}
}
    public static void onLivingDeath(EntityLivingBase var0, DamageSource var1) {
        LivingDeathEvent var9 = new LivingDeathEvent(23778, var1, (char)20225, (short)22806, var0);
        AbyssClient.w.e(var9, 18670087776179L);
}
    public static void onMoveEntityWithHeading(EntityLivingBase var0, CallbackInfo var1) {
        MoveEntityWithHeadingEvent var9 = new MoveEntityWithHeadingEvent((short)0, var0, (short)11036, -1066395815);
        AbyssClient.w.e(var9, 18670087776179L);
        if (var9.a()) {
            var1.cancel();
}
}
    static {
        boolean var2 = false;
        Z = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
}
}