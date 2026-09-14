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
    private static final long public static void onMoveFlying(EntityLivingBase var0, float var1, float var2, float var3, EntityLivingBase var4) {
        if (var4 instanceof EntityPlayerSP) {
            MoveFlyingEvent var11 = new MoveFlyingEvent(var1, var2, var3);
            AbyssClient.w.e(var11, 18670087776179L);
            var1 = var11.a$r3();
            var2 = var11.b();
            var3 = var11.p();
            var0.func_70060_a(var1, var2, var3);
        } else {
            var0.func_70060_a(var1, var2, var3);
}
}
    public static void onJump(EntityLivingBase var0, CallbackInfo var1) {
        JumpEvent var8 = new JumpEvent(EntityLivingBaseAccessor.e(var0), RotationManager.r);
        AbyssClient.w.e(var8, 18670087776179L);
        if (!var8.a()) {
            var0.field_70181_x = var8.o();
            if (var0.func_70644_a(Potion.field_76430_j)) {
                var0.field_70181_x += (double)((float)(var0.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1f);
}
            if (var0.func_70051_ag()) {
                float var9 = var8.j() * ((float)Math.PI / 180);
                var0.field_70159_w -= (double)(MathHelper.func_76126_a((float)var9) * 0.2f);
                var0.field_70179_y += (double)(MathHelper.func_76134_b((float)var9) * 0.2f);
}
            var0.field_70160_al = true;
            var1.cancel();
}
}
    public static void onFunc_110146_f(EntityLivingBase var0, float var1, float var2, CallbackInfoReturnable<Float> var3) {
        boolean var7;
        float var4 = var0.field_70177_z;
        if (var0 instanceof EntityPlayerSP) {
            if (EntityLivingBaseHooks.Z.field_71439_g.field_70733_aJ > 0.0f) {
                var1 = RotationManager.I;
}
            var4 = RotationManager.I;
            EntityLivingBaseHooks.Z.field_71439_g.field_70759_as = RotationManager.I;
}
        float var5 = MathHelper.func_76142_g((float)(var1 - var0.field_70761_aq));
        var0.field_70761_aq += var5 * 0.3f;
        float var6 = MathHelper.func_76142_g((float)(var4 - var0.field_70761_aq));
        boolean bl = var7 = var6 < 90.0f && var6 > -90.0f;
        if (var6 < -75.0f) {
            var6 = -75.0f;
}
        if (var6 > 75.0f) {
            var6 = 75.0f;
}
        var0.field_70761_aq = var4 - var6;
        if (var6 * var6 > 2500.0f) {
            var0.field_70761_aq += var6 * 0.2f;
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
            var1.setReturnValue((int)((float)var11.N() * ClientUtil.b((long)75703014522979L).field_74278_d));
}
}
    public static void onLivingDeath(EntityLivingBase var0, DamageSource var1) {
        LivingDeathEvent var9 = new LivingDeathEvent(23778, var1, '\u4f01', 22806, var0);
        AbyssClient.w.e(var9, 18670087776179L);
}
    public static void onMoveEntityWithHeading(EntityLivingBase var0, CallbackInfo var1) {
        MoveEntityWithHeadingEvent var9 = new MoveEntityWithHeadingEvent(0, var0, 11036, -1066395815);
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