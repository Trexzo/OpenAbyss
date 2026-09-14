/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.world.BedNuker;

public final class BedNukerPreMouseInputInvoker
implements EventInvoker {
    final BedNuker i;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x9B3B5AC307EL;
        this.i.onPreMouseInput((PreMouseInputEvent)var3, var4);
}
    public BedNukerPreMouseInputInvoker(BedNuker var1) {
        this.i = var1;
}
}