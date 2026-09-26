/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.IndicatorsPostTickInvoker;
import Abyss.event.invoker.IndicatorsRender2DInvoker;
import Abyss.module.impl.visual_utility.Indicators;

public final class IndicatorsBinder {
    private IndicatorsBinder() {
}
    public static void J(EventBus var0, Indicators var3) {
        var0.R(var3, PostTickEvent.class, 3, new IndicatorsPostTickInvoker(var3));
        var0.R(var3, Render2DEvent.class, 3, new IndicatorsRender2DInvoker(var3));
}
}