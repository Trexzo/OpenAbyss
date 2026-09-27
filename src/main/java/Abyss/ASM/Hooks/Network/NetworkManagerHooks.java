/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C01PacketChatMessage
 */
package Abyss.ASM.Hooks.Network;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.AbyssClient;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.internal.accessor.PacketAccessor;
import Abyss.internal.jnic.StockCommandRegistry;
import Abyss.module.Modules;
import Abyss.module.impl.misc.CommandLine;
import Abyss.util.packet.OutgoingPacketState;
import Abyss.util.packet.PacketManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class NetworkManagerHooks {
    private static final Set<String> RUNTIME_MARKERS =
            Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    private static synchronized void runtimeMarker(String marker) {
        if (marker == null || !RUNTIME_MARKERS.add(marker)) {
            return;
}
        try {
            try (OutputStreamWriter out = new OutputStreamWriter(
                    new FileOutputStream(new File("abyss-network-stage.txt"), true), "UTF-8")) {
                out.write(System.currentTimeMillis() + "\t" + marker + "\n");
}
}
        catch (Throwable ignored) {
}
}

    public static void onSendPacket(Packet<?> var0, CallbackInfo var1) {
        if (var0 != null) {
            runtimeMarker("send-hook:" + var0.getClass().getName());
}
        if (var0 != null && NetworkManagerHooks.isKnownPacket(PacketAccessor.U, var0)) {
            if (var0 instanceof C01PacketChatMessage && Modules.J(CommandLine.class).o() && ((C01PacketChatMessage)var0).getMessage().startsWith(".")) {
                String commandMessage = ((C01PacketChatMessage)var0).getMessage();
                runtimeMarker("command-intercept:" + commandMessage);
                boolean handled = StockCommandRegistry.E(27284, '\u313f', '\u9352', commandMessage);
                runtimeMarker("command-intercept-dispatch:" + handled + ":" + commandMessage);
                var1.cancel();
}
            if (PacketManager.v.contains(var0)) {
                PacketManager.v.remove(var0);
            } else {
                SendPacketEvent var13 = new SendPacketEvent(var0);
                AbyssClient.w.e(var13, 18670087776179L);
                if (var13.a()) {
                    var1.cancel();
                } else {
                    OutgoingPacketState.D(0L, var0);
}
}
}
}
    public static void onReceivePacket(Packet<?> var0, CallbackInfo var1) {
        if (var0 != null) {
            runtimeMarker("receive-hook:" + var0.getClass().getName());
}
        if (var0 != null && NetworkManagerHooks.isKnownPacket(PacketAccessor.m, var0)) {
            if (PacketManager.a.contains(var0)) {
                PacketManager.a.remove(var0);
            } else {
                ReceivePacketEvent var8 = new ReceivePacketEvent(var0);
                AbyssClient.w.e(var8, 18670087776179L);
                if (var8.a()) {
                    var1.cancel();
}
}
}
}
    private static boolean isKnownPacket(Set<Class<?>> var0, Packet<?> var1) {
        Class<?> var2 = var1.getClass();
        for (Class<?> var4 : var0) {
            if (!var4.isAssignableFrom(var2)) continue;
            return true;
}
        return false;
}
}