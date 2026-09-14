/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.TracersPostTickInvoker;
import Abyss.event.invoker.TracersRender2DInvoker;
import Abyss.event.invoker.TracersRender3DInvoker;
import Abyss.module.impl.visual_utility.Tracers;

public final class TracersBinder {
    private static final long private TracersBinder() {
}
    public static void Z(EventBus var0, Tracers var1) {
        var0.R(var1, PostTickEvent.class, 3, new TracersPostTickInvoker(var1));
        var0.R(var1, Render3DEvent.class, 3, new TracersRender3DInvoker(var1));
        var0.R(var1, Render2DEvent.class, 3, new TracersRender2DInvoker(var1));
}
}