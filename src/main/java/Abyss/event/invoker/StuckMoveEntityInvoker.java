/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.MoveEntityEvent;
import Abyss.module.impl.movement.Stuck;

public final class StuckMoveEntityInvoker
implements EventInvoker {
    final Stuck B;

    public StuckMoveEntityInvoker(Stuck var1) {
        this.B = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x647696460851L;
        this.B.onMoveEntity(var4, (MoveEntityEvent)var3);
}
}