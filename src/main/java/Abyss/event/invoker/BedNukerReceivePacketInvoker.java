/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.impl.world.BedNuker;

public final class BedNukerReceivePacketInvoker
implements EventInvoker {
    final BedNuker c;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2864CA8B0B2EL;
        this.c.onReceivePacket((ReceivePacketEvent)var3, var4);
}
    public BedNukerReceivePacketInvoker(BedNuker var1) {
        this.c = var1;
}
}