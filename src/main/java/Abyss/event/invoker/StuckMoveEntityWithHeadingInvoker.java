/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.MoveEntityWithHeadingEvent;
import Abyss.module.impl.movement.Stuck;

public final class StuckMoveEntityWithHeadingInvoker
implements EventInvoker {
    final Stuck U;

    public StuckMoveEntityWithHeadingInvoker(Stuck var1) {
        this.U = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x65967C2F0CL;
        this.U.onMoveEntityWithHeading(var4, (MoveEntityWithHeadingEvent)var3);
}
}