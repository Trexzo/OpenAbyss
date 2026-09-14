/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.BreakProgressPostTickInvoker;
import Abyss.event.invoker.BreakProgressRender3DInvoker;
import Abyss.module.impl.visual.BreakProgress;

public final class BreakProgressBinder {
    private static final long public static void Z(EventBus var0, BreakProgress var3) {
        var0.R(var3, Render3DEvent.class, 3, new BreakProgressRender3DInvoker(var3));
        var0.R(var3, PostTickEvent.class, 3, new BreakProgressPostTickInvoker(var3));
}
    private BreakProgressBinder() {
}
}