/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.impl.combat.WTap;

public final class WTapReceivePacketInvoker
implements EventInvoker {
    final WTap H;

    public WTapReceivePacketInvoker(WTap var1) {
        this.H = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x25463DAFA9AEL;
        this.H.onReceivePacket((ReceivePacketEvent)var3, var4);
}
}