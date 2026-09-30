/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.ClosestPlayerHUDPostTickInvoker;
import Abyss.event.invoker.ClosestPlayerHUDRender2DInvoker;
import Abyss.module.impl.visual_utility.ClosestPlayerHUD;

public final class ClosestPlayerHUDBinder {
    private ClosestPlayerHUDBinder() {
}
    public static void k(EventBus var0, ClosestPlayerHUD var3) {
        var0.R(var3, PostTickEvent.class, 3, new ClosestPlayerHUDPostTickInvoker(var3));
        var0.R(var3, Render2DEvent.class, 3, new ClosestPlayerHUDRender2DInvoker(var3));
}
}