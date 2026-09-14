/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.MoveFlyingEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.invoker.FlyMoveFlyingInvoker;
import Abyss.event.invoker.FlyPreUpdateInvoker;
import Abyss.module.impl.movement.Fly;

public final class FlyBinder {
    private static final long private FlyBinder() {
}
    public static void x(EventBus var2, Fly var3) {
        var2.R(var3, MoveFlyingEvent.class, 3, new FlyMoveFlyingInvoker(var3));
        var2.R(var3, PreUpdateEvent.class, 3, new FlyPreUpdateInvoker(var3));
}
}