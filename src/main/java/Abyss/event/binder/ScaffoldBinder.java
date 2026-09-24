/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.ScaffoldHeldItemChangeInvoker;
import Abyss.event.invoker.ScaffoldMoveInputInvoker;
import Abyss.event.invoker.ScaffoldPreMouseInputInvoker;
import Abyss.event.invoker.ScaffoldRender2DInvoker;
import Abyss.event.invoker.ScaffoldRender3DInvoker;
import Abyss.module.impl.world.Scaffold;

public final class ScaffoldBinder {
    public static void h(EventBus var0, byte var1, Scaffold var4) {
        var0.R(var4, HeldItemChangeEvent.class, 3, new ScaffoldHeldItemChangeInvoker(var4));
        var0.R(var4, MoveInputEvent.class, 3, new ScaffoldMoveInputInvoker(var4));
        var0.R(var4, PreMouseInputEvent.class, 3, new ScaffoldPreMouseInputInvoker(var4));
        var0.R(var4, Render2DEvent.class, 3, new ScaffoldRender2DInvoker(var4));
        var0.R(var4, Render3DEvent.class, 3, new ScaffoldRender3DInvoker(var4));
}
    private ScaffoldBinder() {
}
}