/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SendPacketEvent;
import Abyss.module.impl.combat.LagRange;

public final class LagRangeSendPacketInvoker
implements EventInvoker {
    final LagRange y;

    public LagRangeSendPacketInvoker(LagRange var1) {
        this.y = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = (var1 ^ 0x36A18766651CL) >>> 32;
        int var6 = (int)((var1 ^ 0x36A18766651CL) << 32 >>> 32);
        this.y.onSendPacket(var4, var6, (SendPacketEvent)var3);
}
}