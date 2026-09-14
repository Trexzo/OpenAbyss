/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SendPacketEvent;
import Abyss.module.impl.world.BedNuker;

public final class BedNukerSendPacketInvoker
implements EventInvoker {
    final BedNuker P;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2F99456128FBL;
        this.P.onSendPacket((SendPacketEvent)var3, var4);
}
    public BedNukerSendPacketInvoker(BedNuker var1) {
        this.P = var1;
}
}