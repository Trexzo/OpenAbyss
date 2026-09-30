/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.EnumCreatureAttribute
 *  net.minecraft.entity.IEntityMultiPart
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.boss.EntityDragonPart
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.S12PacketEntityVelocity
 *  net.minecraft.potion.Potion
 *  net.minecraft.stats.AchievementList
 *  net.minecraft.stats.StatBase
 *  net.minecraft.stats.StatList
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.MathHelper
 */
package Abyss.ASM.Hooks.Entity;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.AbyssClient;
import Abyss.event.events.AttackTargetEntityEvent;
import Abyss.event.events.GetDisplayNameEvent;
import Abyss.event.events.PostItemUseFinishEvent;
import Abyss.internal.accessor.EnchantmentHelperAccessorImpl;
import Abyss.module.Modules;
import Abyss.module.impl.combat.KeepSprint;
import Abyss.util.MinecraftRef;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;

public class EntityPlayerHooks {
    private static final Minecraft D;
    public static void onGetDisplayName(EntityPlayer var0, CallbackInfoReturnable<IChatComponent> var1) {
        GetDisplayNameEvent var8 = new GetDisplayNameEvent(var0, var1.getReturnValue());
        AbyssClient.w.e(var8, 18670087776179L);
        var1.setReturnValue(var8.c());
}
    public static void onPostItemUseFinish() {
        AbyssClient.w.e(new PostItemUseFinishEvent(), 18670087776179L);
}
    public static void onAttackTargetEntity(EntityPlayer var0, Entity var1, CallbackInfo var2) {
        if (var1.canAttackWithItem() && !var1.hitByEntity((Entity)var0)) {
            float var11 = (float)var0.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            int var12 = 0;
            float var13 = var1 instanceof EntityLivingBase ? EnchantmentHelperAccessorImpl.P(var0.getHeldItem(), ((EntityLivingBase)var1).getCreatureAttribute()) : EnchantmentHelperAccessorImpl.P(var0.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
            var12 += EnchantmentHelper.getKnockbackModifier((EntityLivingBase)var0);
            if (var0.isSprinting()) {
                ++var12;
}
            if (var11 > 0.0f || var13 > 0.0f) {
                boolean var14;
                boolean bl = var14 = var0.fallDistance > 0.0f && !var0.onGround && !var0.isOnLadder() && !var0.isInWater() && !var0.isPotionActive(Potion.blindness) && var0.ridingEntity == null && var1 instanceof EntityLivingBase;
                if (var14 && var11 > 0.0f) {
                    var11 *= 1.5f;
}
                var11 += var13;
                boolean var15 = false;
                int var16 = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)var0);
                if (var1 instanceof EntityLivingBase && var16 > 0 && !var1.isBurning()) {
                    var15 = true;
                    var1.setFire(1);
}
                double var17 = var1.motionX;
                double var19 = var1.motionY;
                double var21 = var1.motionZ;
                boolean var23 = var1.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)var0), var11);
                if (var23) {
                    IEntityMultiPart var26;
                    if (var12 > 0) {
                        var1.addVelocity((double)(-MathHelper.sin((float)(var0.rotationYaw * (float)Math.PI / 180.0f)) * (float)var12 * 0.5f), 0.1, (double)(MathHelper.cos((float)(var0.rotationYaw * (float)Math.PI / 180.0f)) * (float)var12 * 0.5f));
                        if (Modules.J(KeepSprint.class).o()) {
                            KeepSprint.k(0L);
                        } else {
                            var0.motionX *= 0.6;
                            var0.motionZ *= 0.6;
                            var0.setSprinting(false);
}
}
                    if (var1 instanceof EntityPlayerMP && var1.velocityChanged) {
                        ((EntityPlayerMP)var1).playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity(var1));
                        var1.velocityChanged = false;
                        var1.motionX = var17;
                        var1.motionY = var19;
                        var1.motionZ = var21;
}
                    if (var11 >= 18.0f) {
                        EntityPlayerHooks.D.thePlayer.triggerAchievement((StatBase)AchievementList.overkill);
}
                    var0.setLastAttacker(var1);
                    if (var1 instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments((EntityLivingBase)((EntityLivingBase)var1), (Entity)var0);
}
                    EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)var0, (Entity)var1);
                    ItemStack var24 = EntityPlayerHooks.D.thePlayer.getCurrentEquippedItem();
                    Entity var25 = var1;
                    if (var1 instanceof EntityDragonPart && (var26 = ((EntityDragonPart)var1).entityDragonObj) instanceof EntityLivingBase) {
                        var25 = (EntityLivingBase)var26;
}
                    Entity var29 = var25;
                    if (var24 != null && var29 instanceof EntityLivingBase) {
                        var24.hitEntity((EntityLivingBase)var29, var0);
                        if (var24.stackSize <= 0) {
                            EntityPlayerHooks.D.thePlayer.destroyCurrentEquippedItem();
}
}
                    if (var1 instanceof EntityLivingBase) {
                        EntityPlayerHooks.D.thePlayer.addStat(StatList.damageDealtStat, Math.round(var11 * 10.0f));
                        if (var16 > 0) {
                            var1.setFire(var16 * 4);
}
}
                    EntityPlayerHooks.D.thePlayer.addExhaustion(0.3f);
                } else if (var15) {
                    var1.extinguish();
}
}
            AbyssClient.w.e(new AttackTargetEntityEvent(var1), 18670087776179L);
}
        var2.cancel();
}
    static {
        boolean var2 = false;
        D = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
}
}