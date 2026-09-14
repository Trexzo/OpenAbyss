/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.event.invoker.FastFallUpdateWalkingPlayerInvoker;
import Abyss.module.impl.movement.FastFall;

public final class FastFallBinder {
    private static final long public static void O(EventBus var0, FastFall var3) {
        var0.R(var3, UpdateWalkingPlayerEvent.class, 3, new FastFallUpdateWalkingPlayerInvoker(var3));
}
    private FastFallBinder() {
}
}