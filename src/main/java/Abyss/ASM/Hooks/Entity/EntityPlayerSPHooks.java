/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.play.client.C01PacketChatMessage
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraft.network.play.client.C03PacketPlayer$C05PacketPlayerLook
 *  net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook
 *  net.minecraft.network.play.client.C0BPacketEntityAction
 *  net.minecraft.network.play.client.C0BPacketEntityAction$Action
 *  net.minecraft.util.MovementInputFromOptions
 */
package Abyss.ASM.Hooks.Entity;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.AbyssClient;
import Abyss.event.events.CloseScreenEvent;
import Abyss.event.events.PostUpdateEvent;
import Abyss.event.events.PostUpdateWalkingPlayerEvent;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.event.events.PreSuperLivingUpdateEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.RedirectIsUsingItemEvent;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.internal.accessor.EntityPlayerSPAccessor;
import Abyss.internal.jnic.StockCommandRegistry;
import Abyss.module.Modules;
import Abyss.module.impl.misc.CommandLine;
import Abyss.module.impl.world.Scaffold;
import Abyss.util.MinecraftRef;
import Abyss.util.RotationManager;
import Abyss.util.packet.PacketManager;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.MovementInputFromOptions;

public class EntityPlayerSPHooks {
    private static long b;
    
    private static Minecraft w;
        private static Object[] c;

    public static void onPreUpdate(CallbackInfo var0) {
        RotationManager.L = RotationManager.I;
        RotationManager.F = RotationManager.K;
        PreUpdateEvent var8 = new PreUpdateEvent(20258, 53049, 64041);
        AbyssClient.w.e(var8, 18670087776179L);
        if (var8.a()) {
            var0.cancel();
}
}
    public static void onSendChatMessage(String var0, CallbackInfo var1) {
        CommandLine var7;
        if (var0 != null && (var7 = Modules.J(CommandLine.class)) != null && var7.o()) {
            if (var0.startsWith(".")) {
                StockCommandRegistry.E(27284, '\u313f', '\u9352', var0);
            } else {
                PacketManager.b(new C01PacketChatMessage(var0));
}
            var1.cancel();
}
}
    public static void onPreSuperLivingUpdate() {
        PreSuperLivingUpdateEvent var7 = new PreSuperLivingUpdateEvent('\u0000');
        AbyssClient.w.e(var7, 18670087776179L);
}
    public static void onPreLivingUpdate(EntityPlayerSP var0, CallbackInfo var1) {
        EntityPlayerSPHooks.Q(var0);
        PreLivingUpdateEvent var9 = new PreLivingUpdateEvent(4433, -41, 4672537);
        AbyssClient.w.e(var9, 18670087776179L);
        if (var9.a()) {
            var1.cancel();
}
}
    private static void Q(EntityPlayerSP var0) {
        if (var0 != null && var0.field_71158_b == null) {
            GameSettings var7;
            Minecraft var6 = MinecraftRef.c((byte)0, 0L);
            GameSettings gameSettings = var7 = var6 == null ? null : var6.field_71474_y;
            if (var7 != null) {
                var0.field_71158_b = new MovementInputFromOptions(var7);
}
}
}
    public static void redirectIsUsingItem(EntityPlayerSP var0) {
        if (var0 != null && var0.field_71158_b != null) {
            RedirectIsUsingItemEvent var7 = new RedirectIsUsingItemEvent(0.2f);
            AbyssClient.w.e(var7, 18670087776179L);
            if (!var7.v()) {
                var0.field_71158_b.field_78902_a *= var7.q();
                var0.field_71158_b.field_78900_b *= var7.q();
}
}
}
    public static void onUpdateWalkingPlayer(EntityPlayerSP var0, CallbackInfo var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var31 = 32593;
        UpdateWalkingPlayerEvent var45 = new UpdateWalkingPlayerEvent(var0.field_70165_t, var0.field_70163_u, var0.field_70161_v, RotationManager.p(), RotationManager.s(), var0.field_70122_E, var0.func_70051_ag(), var0.func_70093_af(), false);
        AbyssClient.w.e(var45, 18670087776179L);
        if (!var45.a()) {
            boolean var47;
            boolean var46 = var0.func_70051_ag();
            if (var46 != EntityPlayerSPAccessor.N(var0) && !var45.I()) {
                if (var46) {
                    PacketManager.b(new C0BPacketEntityAction((Entity)var0, C0BPacketEntityAction.Action.START_SPRINTING));
                } else {
                    PacketManager.b(new C0BPacketEntityAction((Entity)var0, C0BPacketEntityAction.Action.STOP_SPRINTING));
}
                EntityPlayerSPAccessor.N(var0, var46, (short)0);
}
            if ((var47 = var0.func_70093_af()) != EntityPlayerSPAccessor.f(var0) && !var45.I()) {
                if (var47) {
                    PacketManager.b(new C0BPacketEntityAction((Entity)var0, C0BPacketEntityAction.Action.START_SNEAKING));
                } else {
                    PacketManager.b(new C0BPacketEntityAction((Entity)var0, C0BPacketEntityAction.Action.STOP_SNEAKING));
}
                EntityPlayerSPAccessor.l(var0, var47);
}
            if (w.func_175606_aa() == var0) {
                boolean var59;
                if (!Scaffold.Z()) {
                    if (!RotationManager.U) {
                        var0.field_70759_as = var45.O();
                        RotationManager.I = var45.O();
                        RotationManager.K = var45.P();
                    } else {
                        var0.field_70759_as = RotationManager.p();
                        RotationManager.I = RotationManager.p();
                        RotationManager.K = RotationManager.s();
}
}
                double var48 = var45.F() - EntityPlayerSPAccessor.I(0L, var0);
                double var50 = var45.s() - EntityPlayerSPAccessor.M(var0);
                double var52 = var45.U() - EntityPlayerSPAccessor.C(var0);
                double var54 = var45.O() - EntityPlayerSPAccessor.n(var0, 0L);
                double var56 = var45.P() - EntityPlayerSPAccessor.Q(var0);
                boolean var58 = var48 * var48 + var50 * var50 + var52 * var52 > 9.0E-4 || EntityPlayerSPAccessor.L(var0) >= (int)b;
                boolean bl = var59 = var54 != 0.0 || var56 != 0.0;
                if (var0.field_70154_o != null && !var45.I()) {
                    PacketManager.b(new C03PacketPlayer.C06PacketPlayerPosLook(var0.field_70159_w, -999.0, var0.field_70179_y, var45.O(), var45.P(), var45.f()));
                    var58 = false;
                } else if (!var45.I()) {
                    if (var58 && var59) {
                        PacketManager.b(new C03PacketPlayer.C06PacketPlayerPosLook(var45.F(), var45.s(), var45.U(), var45.O(), var45.P(), var45.f()));
                    } else if (var58) {
                        PacketManager.b(new C03PacketPlayer.C04PacketPlayerPosition(var45.F(), var45.s(), var45.U(), var45.f()));
                    } else if (var59) {
                        PacketManager.b(new C03PacketPlayer.C05PacketPlayerLook(var45.O(), var45.P(), var45.f()));
                    } else {
                        PacketManager.b(new C03PacketPlayer(var45.f()));
}
}
                EntityPlayerSPAccessor.K(var0, EntityPlayerSPAccessor.L(var0) + 1);
                if (var58) {
                    EntityPlayerSPAccessor.i(var0, var45.F(), 37110125974099L);
                    EntityPlayerSPAccessor.z(64804680637181L, var0, var45.s());
                    EntityPlayerSPAccessor.s('\u0000', var0, var45.U(), 941714478, (char)var31);
                    EntityPlayerSPAccessor.K(var0, 0);
}
                if (var59) {
                    EntityPlayerSPAccessor.q(var0, var45.O());
                    EntityPlayerSPAccessor.S(var0, var45.P());
}
}
            AbyssClient.w.e(new PostUpdateWalkingPlayerEvent(7839), 18670087776179L);
            var1.cancel();
}
}
    public static void onPostUpdate() {
        RotationManager.r(8215146884547L);
        AbyssClient.w.e(new PostUpdateEvent(), 18670087776179L);
}
    public static void onCloseScreen() {
        AbyssClient.w.e(new CloseScreenEvent(), 18670087776179L);
}
    static {
        w = MinecraftRef.c((byte)0, 0L);
        c = new Object[8];
        d = new String[8];
        b = 5738260080516661268L;
}
}