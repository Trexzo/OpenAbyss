/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.MoveEntityEvent;
import Abyss.event.events.MoveEntityWithHeadingEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.event.invoker.StuckMoveEntityInvoker;
import Abyss.event.invoker.StuckMoveEntityWithHeadingInvoker;
import Abyss.event.invoker.StuckMoveInputInvoker;
import Abyss.event.invoker.StuckPreLivingUpdateInvoker;
import Abyss.module.impl.movement.Stuck;

public final class StuckBinder {
    public static void c(EventBus var0, Stuck var1) {
        var0.R(var1, MoveInputEvent.class, 3, new StuckMoveInputInvoker(var1));
        var0.R(var1, PreLivingUpdateEvent.class, 3, new StuckPreLivingUpdateInvoker(var1));
        var0.R(var1, MoveEntityWithHeadingEvent.class, 3, new StuckMoveEntityWithHeadingInvoker(var1));
        var0.R(var1, MoveEntityEvent.class, 3, new StuckMoveEntityInvoker(var1));
}
    private StuckBinder() {
}
}