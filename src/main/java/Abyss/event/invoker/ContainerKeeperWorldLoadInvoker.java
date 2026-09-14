/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.WorldLoadEvent;
import Abyss.module.impl.misc.ContainerKeeper;

public final class ContainerKeeperWorldLoadInvoker
implements EventInvoker {
    final ContainerKeeper f;

    public ContainerKeeperWorldLoadInvoker(ContainerKeeper var1) {
        this.f = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.f.onWorldLoad((WorldLoadEvent)var3);
}
}