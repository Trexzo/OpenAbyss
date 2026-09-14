/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SendPacketEvent;
import Abyss.module.impl.world.BridgeAssist;

public final class BridgeAssistSendPacketInvoker
implements EventInvoker {
    final BridgeAssist T;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x79E69AA5EC4FL;
        this.T.onSendPacket(var4, (SendPacketEvent)var3);
}
    public BridgeAssistSendPacketInvoker(BridgeAssist var1) {
        this.T = var1;
}
}