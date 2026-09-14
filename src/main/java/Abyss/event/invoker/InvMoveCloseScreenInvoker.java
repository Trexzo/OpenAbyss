/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.CloseScreenEvent;
import Abyss.module.impl.movement.InvMove;

public final class InvMoveCloseScreenInvoker
implements EventInvoker {
    final InvMove V;

    public InvMoveCloseScreenInvoker(InvMove var1) {
        this.V = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.V.onCloseScreen((CloseScreenEvent)var3);
}
}