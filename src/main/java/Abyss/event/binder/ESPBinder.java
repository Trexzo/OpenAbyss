/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.ESPPostTickInvoker;
import Abyss.event.invoker.ESPRender3DInvoker;
import Abyss.module.impl.visual_utility.ESP;

public final class ESPBinder {
    private ESPBinder() {
}
    public static void Y(EventBus var0, ESP var3) {
        var0.R(var3, PostTickEvent.class, 3, new ESPPostTickInvoker(var3));
        var0.R(var3, Render3DEvent.class, 1, new ESPRender3DInvoker(var3));
}
}