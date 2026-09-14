/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.movement.InvMove;

public final class InvMovePostTickInvoker
implements EventInvoker {
    final InvMove S;

    public InvMovePostTickInvoker(InvMove var1) {
        this.S = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.S.onPostTick((PostTickEvent)var3);
}
}