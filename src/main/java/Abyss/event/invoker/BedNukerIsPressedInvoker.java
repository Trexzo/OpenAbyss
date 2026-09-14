/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.IsPressedEvent;
import Abyss.module.impl.world.BedNuker;

public final class BedNukerIsPressedInvoker
implements EventInvoker {
    final BedNuker N;

    public BedNukerIsPressedInvoker(BedNuker var1) {
        this.N = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x7B8715625AB0L;
        this.N.onIsPressed(var4, (IsPressedEvent)var3);
}
}