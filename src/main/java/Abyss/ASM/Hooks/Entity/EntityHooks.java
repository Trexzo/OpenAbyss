/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 */
package Abyss.ASM.Hooks.Entity;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.AbyssClient;
import Abyss.event.events.MoveEntityEvent;
import Abyss.event.events.SafeWalkEvent;
import Abyss.event.events.SetAnglesEvent;
import Abyss.module.ModuleManager;
import Abyss.module.impl.combat.HitBox;
import Abyss.util.MinecraftRef;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EntityHooks {
    private static final Minecraft V;
    private static final long public static void onMoveFlying(Entity var0, float var1, float var2, float var3, CallbackInfo var4) {
        float var6;
        float var5 = var0.field_70177_z;
        if (var0 instanceof EntityPlayerSP) {
            var5 = RotationManager.V;
}
        if ((var6 = var1 * var1 + var2 * var2) >= 1.0E-4f) {
            if ((var6 = MathHelper.func_76129_c((float)var6)) < 1.0f) {
                var6 = 1.0f;
}
            var6 = var3 / var6;
            float var7 = MathHelper.func_76126_a((float)(var5 * (float)Math.PI / 180.0f));
            float var8 = MathHelper.func_76134_b((float)(var5 * (float)Math.PI / 180.0f));
            var0.field_70159_w += (double)((var1 *= var6) * var8 - (var2 *= var6) * var7);
            var0.field_70179_y += (double)(var2 * var8 + var1 * var7);
}
        var4.cancel();
}
    public static void moveEntity(Entity var0, CallbackInfo var1) {
        MoveEntityEvent var9 = new MoveEntityEvent(0, 507233767, -1848, var0);
        AbyssClient.w.e(var9, 18670087776179L);
        if (var9.a()) {
            var1.cancel();
}
}
    public static Vec3 onGetLook(float var0) {
        return RotationUtil.T(var0);
}
    public static void setAngles(Entity var0, float var1, float var2, CallbackInfo var3) {
        if (var0 instanceof EntityPlayerSP) {
            SetAnglesEvent var10 = new SetAnglesEvent(var1, var2);
            AbyssClient.w.e(var10, 18670087776179L);
            if (var10.l()) {
                float var11 = var0.field_70125_A;
                float var12 = var0.field_70177_z;
                var0.field_70177_z = var10.x();
                var0.field_70125_A = var10.s();
                var0.field_70127_C += var0.field_70125_A - var11;
                var0.field_70126_B += var0.field_70177_z - var12;
                var3.cancel();
                return;
}
            if (var10.a()) {
                var3.cancel();
                return;
}
            if (RotationManager.G()) {
                var3.cancel();
}
}
}
    public static boolean onSafeWalk(Entity var0, boolean var1) {
        if (!(var0 instanceof EntityPlayerSP)) {
            return var1;
}
        SafeWalkEvent var8 = new SafeWalkEvent(93108306243249L);
        var8.z(var1);
        AbyssClient.w.e(var8, 18670087776179L);
        return var8.O();
}
    public static void getCollisionBorderSize(Entity var0, CallbackInfoReturnable<Float> var1) {
        if (ModuleManager.r.o() && var0 != null && var0.field_70170_p != null && var0 != EntityHooks.V.field_71439_g && var0 instanceof EntityLivingBase && HitBox.k((byte)0, (EntityLivingBase)var0, 95546070903943L)) {
            var1.setReturnValue(Float.valueOf(var1.getReturnValue().floatValue() + HitBox.expand.L()));
            var1.cancel();
}
}
    static {
        boolean var2 = false;
        V = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
}
}