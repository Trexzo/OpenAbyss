/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SendPacketEvent;
import Abyss.util.packet.PacketManager;

public final class PacketManagerSendPacketInvoker
implements EventInvoker {
    final PacketManager U;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x740C5258CC44L;
        this.U.onSendPacket(var4, (SendPacketEvent)var3);
}
    public PacketManagerSendPacketInvoker(PacketManager var1) {
        this.U = var1;
}
}