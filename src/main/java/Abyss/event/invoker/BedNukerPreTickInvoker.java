/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.world.BedNuker;

public final class BedNukerPreTickInvoker
implements EventInvoker {
    final BedNuker e;

    public BedNukerPreTickInvoker(BedNuker var1) {
        this.e = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0xFCD2A0C2125L;
        this.e.onPreTick(var4, (PreTickEvent)var3);
}
}