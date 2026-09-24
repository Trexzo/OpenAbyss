/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.EnumCreatureAttribute
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.network.play.client.C0APacketAnimation
 *  net.minecraft.network.play.server.S12PacketEntityVelocity
 *  net.minecraft.potion.Potion
 *  net.minecraft.stats.AchievementList
 *  net.minecraft.stats.StatBase
 *  net.minecraft.stats.StatList
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.WorldSettings$GameType
 */
package Abyss.util;

import Abyss.AbyssClient;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.AttackTargetEntityEvent;
import Abyss.internal.accessor.EnchantmentHelperAccessorImpl;
import Abyss.internal.accessor.MethodAccessors;
import Abyss.internal.accessor.MinecraftAccessor;
import Abyss.internal.accessor.PlayerControllerAccessor;
import Abyss.module.Modules;
import Abyss.module.impl.combat.KeepSprint;
import Abyss.module.impl.player.AutoWeapon;
import Abyss.util.BlockUtil;
import Abyss.util.ItemUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.packet.PacketManager;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class CombatUtil {
    private static long a;

        private static long[] e;
    
    
    private static Minecraft w;

    public static float D(EntityLivingBase var0) {
        return var0.getAbsorptionAmount();
}
    public static float P(EntityLivingBase var0) {
        return var0.getHealth() + var0.getAbsorptionAmount();
}
    public static boolean T(AxisAlignedBB var0) {
        if (!CombatUtil.w.thePlayer.isInWater() && !CombatUtil.w.thePlayer.isInLava()) {
            int var1 = MathHelper.floor_double((double)var0.minY);
            if (var1 < 0) {
                return true;
}
            int var2 = MathHelper.floor_double((double)var0.minX);
            int var3 = MathHelper.floor_double((double)(var0.maxX + 1.0));
            int var4 = MathHelper.floor_double((double)var0.minZ);
            int var5 = MathHelper.floor_double((double)(var0.maxZ + 1.0));
            for (int var6 = var2; var6 < var3; ++var6) {
                for (int var7 = var4; var7 < var5; ++var7) {
                    for (int var8 = var1; var8 >= 0; --var8) {
                        if (BlockUtil.a$r1(new BlockPos(var6, var8, var7))) continue;
                        return false;
}
}
}
            return true;
}
        return false;
}
    public static float h(EntityLivingBase var0) {
        return var0.getHealth();
}
    public static int G(long var0, EntityLivingBase var2) {
        var0 = a ^ var0;
        long var3 = var0 ^ 0x7837E53C9A7EL;
        long var5 = (var0 ^ 0x72665B187E04L) >>> 16;
        int var7 = (int)((var0 ^ 0x72665B187E04L) << 48 >>> 48);
        return CombatUtil.x(var2, CombatUtil.w.thePlayer.inventory.getStackInSlot(AutoWeapon.M(var3)), var5, (short)var7);
}
    public static int x(EntityLivingBase var0, ItemStack var1, long var2, short var4) {
        long var5 = (var2 << 16 | (long)var4 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x6FD4C60AF90BL;
        return (int)Math.ceil(CombatUtil.Y(var7, var0, var1));
}
    public static boolean u() {
        return CombatUtil.T(CombatUtil.w.thePlayer.getEntityBoundingBox().expand(-1.0E-6, 0.0, -1.0E-6));
}
    private static boolean isGetHeldItem(BlockPos var0, EnumFacing var1, Vec3 var2) {
        return CombatUtil.w.playerController.onPlayerRightClick(CombatUtil.w.thePlayer, CombatUtil.w.theWorld, CombatUtil.w.thePlayer.getHeldItem(), var0, var1, var2);
}
    public static void G(int var0, BlockPos var1, EnumFacing var2) {
        if (CombatUtil.w.thePlayer.capabilities.allowEdit) {
            MinecraftAccessor.c(w, 0, 0L);
            CombatUtil.w.playerController.onPlayerDamageBlock(var1, var2);
}
}
    public static double Y(long var0, EntityLivingBase var2, ItemStack var3) {
        var0 = a ^ var0;
        int var4 = (int)((var0 ^ 0x2B24964BAC43L) >>> 48);
        int var5 = (int)((var0 ^ 0x2B24964BAC43L) << 16 >>> 48);
        double var7 = 1.0;
        if (var3 != null && (var3.getItem() instanceof ItemSword || var3.getItem() instanceof ItemAxe)) {
            var7 += ItemUtil.p((short)var4, var3, (char)var5);
}
        double var9 = 0.0;
        double var11 = 0.0;
        for (int var13 = 0; var13 < 4; ++var13) {
            ItemStack var14 = var2.getCurrentArmor(var13);
            if (var14 == null || !(var14.getItem() instanceof ItemArmor)) continue;
            var9 += (double)((ItemArmor)var14.getItem()).damageReduceAmount * 0.04;
            int var15 = EnchantmentHelper.getEnchantmentLevel((int)Enchantment.protection.effectId, (ItemStack)var14);
            if (var15 == 0) continue;
            var11 += Math.floor(0.75 * (double)(6 + var15 * var15) / 3.0);
}
        return CombatUtil.N((double)(CombatUtil.h(var2) + CombatUtil.D(var2)) / (var7 * (1.0 - (var9 + 0.04 * Math.min(Math.ceil(Math.min(var11, 25.0) * 0.75), 20.0) * (1.0 - var9)))), 1);
}
    public static boolean I(Entity var0, int var1, long var2) {
        long var4 = ((long)var1 << 32 | var2 << 32 >>> 32) ^ a;
        int var6 = (int)((var4 ^ 0x2D7A97D08787L) >>> 48);
        int var7 = (int)((var4 ^ 0x2D7A97D08787L) << 16 >>> 48);
        int var8 = (int)((var4 ^ 0x2D7A97D08787L) << 32 >>> 32);
        long var9 = var4 ^ 0x69FE904FC9E7L;
        long var11 = var4 ^ 0x63E2008A4226L;
        AttackEntityEvent var13 = new AttackEntityEvent(var0, (char)var6, (short)var7, var8);
        AbyssClient.w.e(var13, var9);
        if (!var13.a()) {
            PlayerControllerAccessor.Q(CombatUtil.w.playerController);
            PacketManager.b(new C02PacketUseEntity(var0, C02PacketUseEntity.Action.ATTACK));
            if (CombatUtil.w.playerController.getCurrentGameType() != WorldSettings.GameType.SPECTATOR) {
                CombatUtil.s(var0, var11);
}
            return true;
}
        return false;
}
    public static double N(double var0, int var2) {
        if (var2 == 0) {
            return Math.round(var0);
}
        double var3 = Math.pow(10.0, var2);
        return (double)Math.round(var0 * var3) / var3;
}
    public static boolean s(long var0, EntityLivingBase var2) {
        long var3 = var0 ^ 0x3EC37A7619DAL;
        float var5 = CombatUtil.w.thePlayer.getHealth() + CombatUtil.w.thePlayer.getAbsorptionAmount();
        float var6 = var2.getHealth() + var2.getAbsorptionAmount();
        float var7 = CombatUtil.w.thePlayer.getMaxHealth();
        float var8 = var2.getMaxHealth();
        int var9 = CombatUtil.G(var3, var2);
        int var10 = CombatUtil.G(var3, (EntityLivingBase)CombatUtil.w.thePlayer);
        float var11 = var5 / var7;
        float var12 = var6 / var8;
        float var13 = 0.0f;
        var13 = var9 <= var10 ? (var13 += 60.0f) : (var13 += 35.0f * ((float)var10 / (float)var9));
        return (var13 += (var11 - var12) * 40.0f) >= 55.0f;
}
    public static boolean u(BlockPos var0, EnumFacing var1, Vec3 var2, boolean var3, boolean var4) {
        boolean var5 = CombatUtil.isGetHeldItem(var0, var1, var2);
        if (var5) {
            if (var3) {
                CombatUtil.w.thePlayer.swingItem();
            } else {
                PacketManager.b(new C0APacketAnimation());
}
            if (var4) {
                CombatUtil.w.entityRenderer.itemRenderer.resetEquippedProgress();
}
}
        return var5;
}
    private static int k() {
        return CombatUtil.w.thePlayer.isPotionActive(Potion.digSpeed) ? 6 - (1 + CombatUtil.w.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier()) : (CombatUtil.w.thePlayer.isPotionActive(Potion.digSlowdown) ? 6 + (1 + CombatUtil.w.thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2 : 6);
}
    public static int q() {
        if (w.getNetHandler() == null) {
            return 0;
}
        NetworkPlayerInfo var0 = w.getNetHandler().getPlayerInfo(CombatUtil.w.thePlayer.getUniqueID());
        return var0 == null ? 0 : var0.getResponseTime();
}
    public static String h(float var0, float var1, long var2) {
        if (var0 <= var1 / 8.0f) {
            return "\u00a74";
}
        if (var0 > var1 / 8.0f && var0 <= var1 / 3.0f) {
            return "\u00a7c";
}
        if (var0 > var1 / 3.0f && (double)var0 <= (double)var1 / 1.5) {
            return "\u00a7e";
}
        return (double)var0 > (double)var1 / 1.5 && var0 <= var1 ? "\u00a7a" : "\u00a7a";
}
    private static void s(Entity var0, long var1) {
        long var7 = var1 ^ 0xA1C90C58BC1L;
        if (var0.canAttackWithItem() && !var0.hitByEntity((Entity)CombatUtil.w.thePlayer)) {
            float var9 = (float)CombatUtil.w.thePlayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            float var10 = EnchantmentHelperAccessorImpl.P(CombatUtil.w.thePlayer.getHeldItem(), var0 instanceof EntityLivingBase ? ((EntityLivingBase)var0).getCreatureAttribute() : EnumCreatureAttribute.UNDEFINED);
            int var11 = EnchantmentHelper.getKnockbackModifier((EntityLivingBase)CombatUtil.w.thePlayer);
            if (CombatUtil.w.thePlayer.isSprinting()) {
                ++var11;
}
            if (var9 > 0.0f || var10 > 0.0f) {
                boolean var12;
                boolean bl = var12 = CombatUtil.w.thePlayer.fallDistance > 0.0f && !CombatUtil.w.thePlayer.onGround && !CombatUtil.w.thePlayer.isOnLadder() && !CombatUtil.w.thePlayer.isInWater() && !CombatUtil.w.thePlayer.isPotionActive(Potion.blindness) && CombatUtil.w.thePlayer.ridingEntity == null;
                if (var12 && var9 > 0.0f) {
                    var9 *= 1.5f;
}
                var9 += var10;
                boolean var13 = false;
                int var14 = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)CombatUtil.w.thePlayer);
                if (var0 instanceof EntityLivingBase && var14 > 0 && !var0.isBurning()) {
                    var13 = true;
                    var0.setFire(1);
}
                double var15 = var0.motionX;
                double var17 = var0.motionY;
                double var19 = var0.motionZ;
                if (var0.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)CombatUtil.w.thePlayer), var9)) {
                    if (var11 > 0) {
                        var0.addVelocity((double)(-MathHelper.sin((float)(CombatUtil.w.thePlayer.rotationYaw * (float)Math.PI / 180.0f)) * (float)var11 * 0.5f), 0.1, (double)(MathHelper.cos((float)(CombatUtil.w.thePlayer.rotationYaw * (float)Math.PI / 180.0f)) * (float)var11 * 0.5f));
                        if (Modules.J(KeepSprint.class).o()) {
                            KeepSprint.k(0L);
                        } else {
                            CombatUtil.w.thePlayer.motionX *= 0.6;
                            CombatUtil.w.thePlayer.motionZ *= 0.6;
                            CombatUtil.w.thePlayer.setSprinting(false);
}
}
                    if (var0 instanceof EntityPlayerMP && var0.velocityChanged) {
                        ((EntityPlayerMP)var0).playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity(var0));
                        var0.velocityChanged = false;
                        var0.motionX = var15;
                        var0.motionY = var17;
                        var0.motionZ = var19;
}
                    if (var12) {
                        CombatUtil.w.thePlayer.onCriticalHit(var0);
}
                    if (var10 > 0.0f) {
                        CombatUtil.w.thePlayer.onEnchantmentCritical(var0);
}
                    if (var9 >= 18.0f) {
                        CombatUtil.w.thePlayer.triggerAchievement((StatBase)AchievementList.overkill);
}
                    CombatUtil.w.thePlayer.setLastAttacker(var0);
                    if (var0 instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments((EntityLivingBase)((EntityLivingBase)var0), (Entity)CombatUtil.w.thePlayer);
}
                    EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)CombatUtil.w.thePlayer, (Entity)var0);
                    if (var0 instanceof EntityLivingBase) {
                        CombatUtil.w.thePlayer.addStat(StatList.damageDealtStat, Math.round(var9 * 10.0f));
                        if (var14 > 0) {
                            var0.setFire(var14 * 4);
}
}
                    CombatUtil.w.thePlayer.addExhaustion(0.3f);
                } else if (var13) {
                    var0.extinguish();
}
}
            AbyssClient.w.e(new AttackTargetEntityEvent(var0), var7);
}
}
    public static String h(EntityLivingBase var0, long var1, ItemStack var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x42FFADA20DDDL;
        int var6 = (int)Math.ceil(CombatUtil.Y(var4, var0, var3));
        return "\u00a7b" + var6;
}
    public static void X(int var0, int var1, char var2) {
        ItemStack var7 = CombatUtil.w.thePlayer.getHeldItem();
        if (!(var7 != null && var7.getItem() != null && MethodAccessors.V(var7.getItem(), (EntityLivingBase)CombatUtil.w.thePlayer, var7) || CombatUtil.w.thePlayer.isSwingInProgress && CombatUtil.w.thePlayer.swingProgressInt < CombatUtil.k() / 2 && CombatUtil.w.thePlayer.swingProgressInt >= 0)) {
            CombatUtil.w.thePlayer.swingProgressInt = -1;
            CombatUtil.w.thePlayer.isSwingInProgress = true;
}
}
    static {
        a = 90005592299191L;
        w = MinecraftRef.c((byte)0, 0L);
}
}