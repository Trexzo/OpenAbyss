/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SendPacketEvent;
import Abyss.util.packet.IncomingPacketHold;

public final class IncomingPacketHoldSendPacketInvoker
implements EventInvoker {
    final IncomingPacketHold p;

    public IncomingPacketHoldSendPacketInvoker(IncomingPacketHold var1) {
        this.p = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.p.onSendPacket((SendPacketEvent)var3);
}
}