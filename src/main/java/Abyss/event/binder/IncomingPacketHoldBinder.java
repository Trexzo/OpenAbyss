/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.invoker.IncomingPacketHoldReceivePacketInvoker;
import Abyss.event.invoker.IncomingPacketHoldSendPacketInvoker;
import Abyss.util.packet.IncomingPacketHold;

public final class IncomingPacketHoldBinder {
    public static void z(EventBus var0, IncomingPacketHold var3) {
        var0.R(var3, ReceivePacketEvent.class, 5, new IncomingPacketHoldReceivePacketInvoker(var3));
        var0.R(var3, SendPacketEvent.class, 3, new IncomingPacketHoldSendPacketInvoker(var3));
}
    private IncomingPacketHoldBinder() {
}
}