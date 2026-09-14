/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.invoker.SprintPreUpdateInvoker;
import Abyss.module.impl.movement.Sprint;

public final class SprintBinder {
    private static final long private SprintBinder() {
}
    public static void J(EventBus var2, Sprint var3) {
        var2.R(var3, PreUpdateEvent.class, 3, new SprintPreUpdateInvoker(var3));
}
}