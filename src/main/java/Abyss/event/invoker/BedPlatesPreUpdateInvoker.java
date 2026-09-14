/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.visual_utility.BedPlates;

public final class BedPlatesPreUpdateInvoker
implements EventInvoker {
    final BedPlates p;

    public BedPlatesPreUpdateInvoker(BedPlates var1) {
        this.p = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.p.onPreUpdate((PreUpdateEvent)var3);
}
}