/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.ISound
 *  net.minecraft.client.audio.PositionedSoundRecord
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Vec3
 */
package Abyss.ASM.Hooks.Player;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.ASM.Hooks.MiscHooks;
import Abyss.AbyssClient;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.ClickBlockReturnEvent;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.event.events.PostStoppedUsingItemEvent;
import Abyss.event.events.PreStoppedUsingItemEvent;
import Abyss.event.events.TryHarvestBlockHeadEvent;
import Abyss.internal.accessor.PlayerControllerAccessor;
import Abyss.internal.accessor.PlayerControllerStateAccessor;
import Abyss.module.Modules;
import Abyss.module.impl.visual.Animations;
import Abyss.util.MinecraftRef;
import Abyss.util.packet.PacketManager;
import java.io.UnsupportedEncodingException;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class PlayerControllerHooks {
    private static final Minecraft V;
    private static final long public static void onAttackEntity(Entity var0, CallbackInfo var1) {
        AttackEntityEvent var9 = new AttackEntityEvent(var0, '\u0000', 21630, -261330477);
        AbyssClient.w.e(var9, 18670087776179L);
        if (var9.a()) {
            var1.cancel();
}
}
    public static void clickBlockReturn(BlockPos var0, CallbackInfoReturnable<Boolean> var1) {
        if (var1.getReturnValue().booleanValue()) {
            AbyssClient.w.e(new ClickBlockReturnEvent(var0), 18670087776179L);
}
}
    public static void onPreStoppedUsingItem(CallbackInfo var0) {
        if (MiscHooks.minecraftShouldCancelStoppedUsingItem()) {
            var0.cancel();
        } else {
            PreStoppedUsingItemEvent var7 = new PreStoppedUsingItemEvent();
            AbyssClient.w.e(var7, 18670087776179L);
            if (var7.a()) {
                var0.cancel();
}
}
}
    public static void onPostStoppedUsingItem() {
        AbyssClient.w.e(new PostStoppedUsingItemEvent('\u0000', 23334, 1287003355), 18670087776179L);
}
    public static void onDamageBlock(BlockPos var0, EnumFacing var1, PlayerControllerMP var2, CallbackInfoReturnable<Boolean> var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (PlayerControllerHooks.V.field_71439_g.func_71039_bw()) {
            var3.setReturnValue(false);
            var3.cancel();
        } else {
            PlayerControllerAccessor.Q(var2);
            if (PlayerControllerStateAccessor.W(var2) > 0) {
                PlayerControllerStateAccessor.w((byte)0, 7374982, 11824981, var2, PlayerControllerStateAccessor.W(var2) - 1);
                var3.setReturnValue(true);
                var3.cancel();
            } else if (var2.func_178889_l().func_77145_d() && PlayerControllerHooks.V.field_71441_e.func_175723_af().func_177746_a(var0)) {
                PlayerControllerStateAccessor.w((byte)0, 7374982, 11824981, var2, 5);
                PacketManager.b(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, var0, var1));
                PlayerControllerMP.func_178891_a((Minecraft)V, (PlayerControllerMP)var2, (BlockPos)var0, (EnumFacing)var1);
                var3.setReturnValue(true);
                var3.cancel();
            } else if (PlayerControllerAccessor.E(var2, var0)) {
                Block var23 = PlayerControllerHooks.V.field_71441_e.func_180495_p(var0).func_177230_c();
                if (var23.func_149688_o() == Material.field_151579_a) {
                    PlayerControllerStateAccessor.Q(0L, var2, false);
                    var3.setReturnValue(false);
                    var3.cancel();
                } else {
                    float var24 = var23.func_180647_a((EntityPlayer)PlayerControllerHooks.V.field_71439_g, PlayerControllerHooks.V.field_71439_g.field_70170_p, var0);
                    PlayerControllerStateAccessor.e(0L, var2, PlayerControllerStateAccessor.s(0L, var2) + var24);
                    if (PlayerControllerStateAccessor.v(var2, 0L) % 4.0f == 0.0f) {
                        V.func_147118_V().func_147682_a((ISound)new PositionedSoundRecord(new ResourceLocation(var23.field_149762_H.func_150498_e()), (var23.field_149762_H.func_150497_c() + 1.0f) / 8.0f, var23.field_149762_H.func_150494_d() * 0.5f, (float)var0.func_177958_n() + 0.5f, (float)var0.func_177956_o() + 0.5f, (float)var0.func_177952_p() + 0.5f));
}
                    PlayerControllerStateAccessor.W(0L, var2, PlayerControllerStateAccessor.v(var2, 0L) + 1.0f);
                    if (PlayerControllerStateAccessor.s(0L, var2) >= 1.0f) {
                        PlayerControllerStateAccessor.Q(0L, var2, false);
                        PacketManager.b(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, var0, var1));
                        var2.func_178888_a(var0, var1);
                        PlayerControllerStateAccessor.e(0L, var2, 0.0f);
                        PlayerControllerStateAccessor.W(0L, var2, 0.0f);
                        PlayerControllerStateAccessor.w((byte)0, 7374982, 11824981, var2, 5);
}
                    PlayerControllerHooks.V.field_71441_e.func_175715_c(PlayerControllerHooks.V.field_71439_g.func_145782_y(), PlayerControllerStateAccessor.Z(var2), (int)(PlayerControllerStateAccessor.s(0L, var2) * 10.0f) - 1);
                    var3.setReturnValue(true);
                    var3.cancel();
}
            } else {
                var3.setReturnValue(var2.func_180511_b(var0, var1));
                var3.cancel();
}
}
}
    public static void tryHarvestBlockHead(BlockPos var0) {
        AbyssClient.w.e(new TryHarvestBlockHeadEvent(var0), 18670087776179L);
}
    public static void onPlayerRightClick(WorldClient var0, ItemStack var1, BlockPos var2, EnumFacing var3, Vec3 var4, CallbackInfoReturnable<Boolean> var5) {
        PlayerRightClickEvent var12 = new PlayerRightClickEvent(var0, var1, var2, var3, var4);
        AbyssClient.w.e(var12, 18670087776179L);
        if (var12.a()) {
            var5.setReturnValue(false);
            var5.cancel();
}
}
    public static void onDamageBlockAfterSync(CallbackInfoReturnable<Boolean> var0) {
        if (PlayerControllerHooks.V.field_71439_g.func_71039_bw() && Modules.J(Animations.class).o()) {
            var0.setReturnValue(true);
            var0.cancel();
}
}
    static {
        boolean var2 = false;
        V = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
}
}