/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.INetHandler
 *  net.minecraft.network.Packet
 *  net.minecraft.network.PacketThreadUtil
 *  net.minecraft.network.play.INetHandlerPlayClient
 *  net.minecraft.network.play.server.S02PacketChat
 *  net.minecraft.network.play.server.S12PacketEntityVelocity
 *  net.minecraft.util.IThreadListener
 */
package Abyss.ASM.Hooks.Network;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.AbyssClient;
import Abyss.event.events.HandleChatEvent;
import Abyss.event.events.KnockbackEvent;
import Abyss.event.events.PostKnockbackEvent;
import Abyss.util.MinecraftRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.IThreadListener;

public class NetHandlerPlayClientHooks {
    private static final Minecraft g;

    public static void onHandleEntityVelocity(NetHandlerPlayClient var0, S12PacketEntityVelocity var1, CallbackInfo var2) {
        WorldClient var12 = NetHandlerPlayClientHooks.g.theWorld;
        PacketThreadUtil.checkThreadAndEnqueue((Packet)var1, (INetHandler)var0, (IThreadListener)g);
        Entity var13 = var12.getEntityByID(var1.getEntityID());
        if (var13 == null) {
            var2.cancel();
            return;
}
        if (var13.getEntityId() == NetHandlerPlayClientHooks.g.thePlayer.getEntityId()) {
            KnockbackEvent var14 = new KnockbackEvent(var1.getMotionX(), var1.getMotionY(), var1.getMotionZ());
            AbyssClient.w.e(var14, 18670087776179L);
            if (var14.a()) {
                var2.cancel();
                return;
}
            var13.setVelocity(var14.S() / 8000.0, var14.f() / 8000.0, var14.R() / 8000.0);
            AbyssClient.w.e(new PostKnockbackEvent('\u0000', 446144442, 25937), 18670087776179L);
        } else {
            var13.setVelocity((double)var1.getMotionX() / 8000.0, (double)var1.getMotionY() / 8000.0, (double)var1.getMotionZ() / 8000.0);
}
        var2.cancel();
}
    public static void onProcessEntityVelocity(INetHandlerPlayClient var0, S12PacketEntityVelocity var1, CallbackInfo var2) {
        if (var0 instanceof NetHandlerPlayClient) {
            NetHandlerPlayClientHooks.onHandleEntityVelocity((NetHandlerPlayClient)var0, var1, var2);
}
}
    public static void handleChat(S02PacketChat var0) {
        AbyssClient.w.e(new HandleChatEvent(var0.getChatComponent()), 18670087776179L);
}
    static {
        boolean var2 = false;
        g = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
}
}