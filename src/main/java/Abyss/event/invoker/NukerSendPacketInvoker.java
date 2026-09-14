/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SendPacketEvent;
import Abyss.module.impl.world.Nuker;

public final class NukerSendPacketInvoker
implements EventInvoker {
    final Nuker M;

    public NukerSendPacketInvoker(Nuker var1) {
        this.M = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = (var1 ^ 0x19909DF8985CL) >>> 16;
        int var6 = (int)((var1 ^ 0x19909DF8985CL) << 48 >>> 48);
        this.M.onSendPacket((SendPacketEvent)var3, var4, (short)var6);
}
}