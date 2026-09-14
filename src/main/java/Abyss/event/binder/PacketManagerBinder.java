/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.invoker.PacketManagerSendPacketInvoker;
import Abyss.util.packet.PacketManager;

public final class PacketManagerBinder {
    private static final long public static void N(EventBus var0, PacketManager var3) {
        var0.R(var3, SendPacketEvent.class, 3, new PacketManagerSendPacketInvoker(var3));
}
    private PacketManagerBinder() {
}
}