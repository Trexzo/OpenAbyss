/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.MiningRenderSubscriberRender3DInvoker;
import Abyss.internal.MiningRenderSubscriber;

public final class MiningRenderSubscriberBinder {
    private static final long private MiningRenderSubscriberBinder() {
}
    public static void n(EventBus var0, MiningRenderSubscriber var3) {
        var0.R(var3, Render3DEvent.class, 3, new MiningRenderSubscriberRender3DInvoker(var3));
}
}