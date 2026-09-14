/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.HUDRender2DInvoker;
import Abyss.module.impl.visual.HUD;

public final class HUDBinder {
    private static final long private HUDBinder() {
}
    public static void H(EventBus var0, HUD var3) {
        var0.R(var3, Render2DEvent.class, 3, new HUDRender2DInvoker(var3));
}
}