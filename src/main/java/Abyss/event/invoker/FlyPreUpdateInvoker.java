/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.movement.Fly;

public final class FlyPreUpdateInvoker
implements EventInvoker {
    final Fly O;

    public FlyPreUpdateInvoker(Fly var1) {
        this.O = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.O.onPreUpdate((PreUpdateEvent)var3);
}
}