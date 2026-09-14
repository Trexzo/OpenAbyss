/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.MoveInputEvent;
import Abyss.module.impl.movement.Stuck;

public final class StuckMoveInputInvoker
implements EventInvoker {
    final Stuck b;

    @Override
    public void c(long var1, Object var3) {
        this.b.onMoveInput((MoveInputEvent)var3);
}
    public StuckMoveInputInvoker(Stuck var1) {
        this.b = var1;
}
}