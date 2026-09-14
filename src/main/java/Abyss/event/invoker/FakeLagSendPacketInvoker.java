/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SendPacketEvent;
import Abyss.module.impl.combat.FakeLag;

public final class FakeLagSendPacketInvoker
implements EventInvoker {
    final FakeLag O;

    public FakeLagSendPacketInvoker(FakeLag var1) {
        this.O = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x7418E53A33D4L;
        this.O.onSendPacket(var4, (SendPacketEvent)var3);
}
}