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
    private static final long public static void onGetDisplayName(EntityPlayer var0, CallbackInfoReturnable<IChatComponent> var1) {
        GetDisplayNameEvent var8 = new GetDisplayNameEvent(var0, var1.getReturnValue());
        AbyssClient.w.e(var8, 18670087776179L);
        var1.setReturnValue(var8.c());
}
    public static void onPostItemUseFinish() {
        AbyssClient.w.e(new PostItemUseFinishEvent(), 18670087776179L);
}
    public static void onAttackTargetEntity(EntityPlayer var0, Entity var1, CallbackInfo var2) {
        if (var1.func_70075_an() && !var1.func_85031_j((Entity)var0)) {
            float var11 = (float)var0.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
            int var12 = 0;
            float var13 = var1 instanceof EntityLivingBase ? EnchantmentHelperAccessorImpl.P(var0.func_70694_bm(), ((EntityLivingBase)var1).func_70668_bt()) : EnchantmentHelperAccessorImpl.P(var0.func_70694_bm(), EnumCreatureAttribute.UNDEFINED);
            var12 += EnchantmentHelper.func_77501_a((EntityLivingBase)var0);
            if (var0.func_70051_ag()) {
                ++var12;
}
            if (var11 > 0.0f || var13 > 0.0f) {
                boolean var14;
                boolean bl = var14 = var0.field_70143_R > 0.0f && !var0.field_70122_E && !var0.func_70617_f_() && !var0.func_70090_H() && !var0.func_70644_a(Potion.field_76440_q) && var0.field_70154_o == null && var1 instanceof EntityLivingBase;
                if (var14 && var11 > 0.0f) {
                    var11 *= 1.5f;
}
                var11 += var13;
                boolean var15 = false;
                int var16 = EnchantmentHelper.func_90036_a((EntityLivingBase)var0);
                if (var1 instanceof EntityLivingBase && var16 > 0 && !var1.func_70027_ad()) {
                    var15 = true;
                    var1.func_70015_d(1);
}
                double var17 = var1.field_70159_w;
                double var19 = var1.field_70181_x;
                double var21 = var1.field_70179_y;
                boolean var23 = var1.func_70097_a(DamageSource.func_76365_a((EntityPlayer)var0), var11);
                if (var23) {
                    IEntityMultiPart var26;
                    if (var12 > 0) {
                        var1.func_70024_g((double)(-MathHelper.func_76126_a((float)(var0.field_70177_z * (float)Math.PI / 180.0f)) * (float)var12 * 0.5f), 0.1, (double)(MathHelper.func_76134_b((float)(var0.field_70177_z * (float)Math.PI / 180.0f)) * (float)var12 * 0.5f));
                        if (Modules.J(KeepSprint.class).o()) {
                            KeepSprint.k(0L);
                        } else {
                            var0.field_70159_w *= 0.6;
                            var0.field_70179_y *= 0.6;
                            var0.func_70031_b(false);
}
}
                    if (var1 instanceof EntityPlayerMP && var1.field_70133_I) {
                        ((EntityPlayerMP)var1).field_71135_a.func_147359_a((Packet)new S12PacketEntityVelocity(var1));
                        var1.field_70133_I = false;
                        var1.field_70159_w = var17;
                        var1.field_70181_x = var19;
                        var1.field_70179_y = var21;
}
                    if (var11 >= 18.0f) {
                        EntityPlayerHooks.D.field_71439_g.func_71029_a((StatBase)AchievementList.field_75999_E);
}
                    var0.func_130011_c(var1);
                    if (var1 instanceof EntityLivingBase) {
                        EnchantmentHelper.func_151384_a((EntityLivingBase)((EntityLivingBase)var1), (Entity)var0);
}
                    EnchantmentHelper.func_151385_b((EntityLivingBase)var0, (Entity)var1);
                    ItemStack var24 = EntityPlayerHooks.D.field_71439_g.func_71045_bC();
                    Entity var25 = var1;
                    if (var1 instanceof EntityDragonPart && (var26 = ((EntityDragonPart)var1).field_70259_a) instanceof EntityLivingBase) {
                        var25 = (EntityLivingBase)var26;
}
                    Entity var29 = var25;
                    if (var24 != null && var29 instanceof EntityLivingBase) {
                        var24.func_77961_a((EntityLivingBase)var29, var0);
                        if (var24.field_77994_a <= 0) {
                            EntityPlayerHooks.D.field_71439_g.func_71028_bD();
}
}
                    if (var1 instanceof EntityLivingBase) {
                        EntityPlayerHooks.D.field_71439_g.func_71064_a(StatList.field_75951_w, Math.round(var11 * 10.0f));
                        if (var16 > 0) {
                            var1.func_70015_d(var16 * 4);
}
}
                    EntityPlayerHooks.D.field_71439_g.func_71020_j(0.3f);
                } else if (var15) {
                    var1.func_70066_B();
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