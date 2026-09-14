/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.util.RotationManager;

public final class RotationManagerReceivePacketInvoker
implements EventInvoker {
    final RotationManager W;

    public RotationManagerReceivePacketInvoker(RotationManager var1) {
        this.W = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.W.onReceivePacket((ReceivePacketEvent)var3);
}
}