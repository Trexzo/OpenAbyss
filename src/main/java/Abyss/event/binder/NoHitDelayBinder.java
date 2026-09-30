/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.ClickMouseEvent;
import Abyss.event.events.PostClickMouseEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.invoker.NoHitDelayClickMouseInvoker;
import Abyss.event.invoker.NoHitDelayPostClickMouseInvoker;
import Abyss.event.invoker.NoHitDelayPostTickInvoker;
import Abyss.module.impl.player.NoHitDelay;

public final class NoHitDelayBinder {
    public static void k(EventBus var2, NoHitDelay var3) {
        var2.R(var3, ClickMouseEvent.class, 3, new NoHitDelayClickMouseInvoker(var3));
        var2.R(var3, PostClickMouseEvent.class, 3, new NoHitDelayPostClickMouseInvoker(var3));
        var2.R(var3, PostTickEvent.class, 3, new NoHitDelayPostTickInvoker(var3));
}
    private NoHitDelayBinder() {
}
}