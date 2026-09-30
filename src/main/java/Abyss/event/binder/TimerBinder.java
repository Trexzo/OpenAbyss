/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.TimerRender2DInvoker;
import Abyss.module.impl.misc.Timer;

public final class TimerBinder {
    private TimerBinder() {
}
    public static void C(EventBus var2, Timer var3) {
        var2.R(var3, Render2DEvent.class, 3, new TimerRender2DInvoker(var3));
}
}