/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.misc.ContainerKeeper;

public final class ContainerKeeperPreUpdateInvoker
implements EventInvoker {
    final ContainerKeeper r;

    public ContainerKeeperPreUpdateInvoker(ContainerKeeper var1) {
        this.r = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x2A0B7F200B43L) >>> 32);
        int var5 = (int)((var1 ^ 0x2A0B7F200B43L) << 32 >>> 40);
        int var6 = (int)((var1 ^ 0x2A0B7F200B43L) << 56 >>> 56);
        this.r.onPreUpdate(var4, var5, (byte)var6, (PreUpdateEvent)var3);
}
}