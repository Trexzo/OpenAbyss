/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.RedirectIsUsingItemEvent;
import Abyss.event.events.RightClickMouseEvent;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.event.invoker.NoSlowPreUpdateInvoker;
import Abyss.event.invoker.NoSlowRedirectIsUsingItemInvoker;
import Abyss.event.invoker.NoSlowRightClickMouseInvoker;
import Abyss.event.invoker.NoSlowUpdateWalkingPlayerInvoker;
import Abyss.module.impl.movement.NoSlow;

public final class NoSlowBinder {
    private static final long private NoSlowBinder() {
}
    public static void G(EventBus var0, NoSlow var1) {
        var0.R(var1, RedirectIsUsingItemEvent.class, 3, new NoSlowRedirectIsUsingItemInvoker(var1));
        var0.R(var1, UpdateWalkingPlayerEvent.class, 4, new NoSlowUpdateWalkingPlayerInvoker(var1));
        var0.R(var1, RightClickMouseEvent.class, 3, new NoSlowRightClickMouseInvoker(var1));
        var0.R(var1, PreUpdateEvent.class, 1, new NoSlowPreUpdateInvoker(var1));
}
}