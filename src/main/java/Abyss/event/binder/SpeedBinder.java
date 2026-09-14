/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.invoker.SpeedPreUpdateInvoker;
import Abyss.module.impl.movement.Speed;

public final class SpeedBinder {
    private static final long private SpeedBinder() {
}
    public static void V(EventBus var2, Speed var3) {
        var2.R(var3, PreUpdateEvent.class, 3, new SpeedPreUpdateInvoker(var3));
}
}