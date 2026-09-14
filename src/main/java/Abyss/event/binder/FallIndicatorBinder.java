/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.FallIndicatorPostTickInvoker;
import Abyss.event.invoker.FallIndicatorRender2DInvoker;
import Abyss.module.impl.visual_utility.FallIndicator;

public final class FallIndicatorBinder {
    private static final long public static void r(EventBus var2, FallIndicator var3) {
        var2.R(var3, PostTickEvent.class, 3, new FallIndicatorPostTickInvoker(var3));
        var2.R(var3, Render2DEvent.class, 3, new FallIndicatorRender2DInvoker(var3));
}
    private FallIndicatorBinder() {
}
}