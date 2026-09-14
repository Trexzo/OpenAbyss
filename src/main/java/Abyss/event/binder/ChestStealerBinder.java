/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.ChestStealerPreUpdateInvoker;
import Abyss.event.invoker.ChestStealerRender2DInvoker;
import Abyss.module.impl.player.ChestStealer;

public final class ChestStealerBinder {
    private static final long private ChestStealerBinder() {
}
    public static void W(EventBus var2, ChestStealer var3) {
        var2.R(var3, PreUpdateEvent.class, 3, new ChestStealerPreUpdateInvoker(var3));
        var2.R(var3, Render2DEvent.class, 3, new ChestStealerRender2DInvoker(var3));
}
}