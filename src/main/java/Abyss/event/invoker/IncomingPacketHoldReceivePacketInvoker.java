/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.util.packet.IncomingPacketHold;

public final class IncomingPacketHoldReceivePacketInvoker
implements EventInvoker {
    final IncomingPacketHold J;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x4D1E1B1F7187L;
        this.J.onReceivePacket((ReceivePacketEvent)var3, var4);
}
    public IncomingPacketHoldReceivePacketInvoker(IncomingPacketHold var1) {
        this.J = var1;
}
}