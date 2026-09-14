/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostRenderEvent;
import Abyss.event.events.PreRenderEvent;
import Abyss.event.invoker.ChamsPostRenderInvoker;
import Abyss.event.invoker.ChamsPreRenderInvoker;
import Abyss.module.impl.visual.Chams;

public final class ChamsBinder {
    private static final long public static void I(EventBus var0, Chams var1) {
        var0.R(var1, PreRenderEvent.class, 3, new ChamsPreRenderInvoker(var1));
        var0.R(var1, PostRenderEvent.class, 3, new ChamsPostRenderInvoker(var1));
}
    private ChamsBinder() {
}
}