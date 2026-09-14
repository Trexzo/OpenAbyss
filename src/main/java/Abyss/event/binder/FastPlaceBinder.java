/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostRightClickEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.invoker.FastPlacePostRightClickInvoker;
import Abyss.event.invoker.FastPlacePreUpdateInvoker;
import Abyss.module.impl.world.FastPlace;

public final class FastPlaceBinder {
    private static final long public static void e(EventBus var0, FastPlace var1) {
        var0.R(var1, PreUpdateEvent.class, 3, new FastPlacePreUpdateInvoker(var1));
        var0.R(var1, PostRightClickEvent.class, 3, new FastPlacePostRightClickInvoker(var1));
}
    private FastPlaceBinder() {
}
}