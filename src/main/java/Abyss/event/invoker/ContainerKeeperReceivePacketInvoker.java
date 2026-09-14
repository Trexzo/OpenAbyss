/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.impl.misc.ContainerKeeper;

public final class ContainerKeeperReceivePacketInvoker
implements EventInvoker {
    final ContainerKeeper u;

    public ContainerKeeperReceivePacketInvoker(ContainerKeeper var1) {
        this.u = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.u.onReceivePacket((ReceivePacketEvent)var3);
}
}