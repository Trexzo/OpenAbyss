/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.impl.combat.HitSelect;

public final class HitSelectReceivePacketInvoker
implements EventInvoker {
    final HitSelect t;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x2A824BA71B9EL) >>> 48);
        int var5 = (int)((var1 ^ 0x2A824BA71B9EL) << 16 >>> 32);
        int var6 = (int)((var1 ^ 0x2A824BA71B9EL) << 48 >>> 48);
        this.t.onReceivePacket((char)var4, (ReceivePacketEvent)var3, var5, (short)var6);
}
    public HitSelectReceivePacketInvoker(HitSelect var1) {
        this.t = var1;
}
}