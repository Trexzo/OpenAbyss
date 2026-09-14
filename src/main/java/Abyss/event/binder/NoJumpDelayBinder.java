/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreTickEvent;
import Abyss.event.invoker.NoJumpDelayPreTickInvoker;
import Abyss.module.impl.movement.NoJumpDelay;

public final class NoJumpDelayBinder {
    private static final long public static void T(EventBus var0, NoJumpDelay var3) {
        var0.R(var3, PreTickEvent.class, 3, new NoJumpDelayPreTickInvoker(var3));
}
    private NoJumpDelayBinder() {
}
}