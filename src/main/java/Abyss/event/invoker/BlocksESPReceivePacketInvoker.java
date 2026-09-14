/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.impl.visual_utility.BlocksESP;

public final class BlocksESPReceivePacketInvoker
implements EventInvoker {
    final BlocksESP n;

    public BlocksESPReceivePacketInvoker(BlocksESP var1) {
        this.n = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.n.onReceivePacket((ReceivePacketEvent)var3);
}
}