/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.internal.CheaterDetector;

public final class CheaterDetectorReceivePacketInvoker
implements EventInvoker {
    final CheaterDetector C;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x3067DE298A32L;
        this.C.onReceivePacket((ReceivePacketEvent)var3, var4);
}
    public CheaterDetectorReceivePacketInvoker(CheaterDetector var1) {
        this.C = var1;
}
}