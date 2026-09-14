/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.ClickBlockReturnEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.TryHarvestBlockHeadEvent;
import Abyss.event.invoker.BrokenBlockTrackerClickBlockReturnInvoker;
import Abyss.event.invoker.BrokenBlockTrackerPostTickInvoker;
import Abyss.event.invoker.BrokenBlockTrackerTryHarvestBlockHeadInvoker;
import Abyss.internal.BrokenBlockTracker;

public final class BrokenBlockTrackerBinder {
    private static final long private BrokenBlockTrackerBinder() {
}
    public static void z(EventBus var0, BrokenBlockTracker var1) {
        var0.R(var1, ClickBlockReturnEvent.class, 3, new BrokenBlockTrackerClickBlockReturnInvoker(var1));
        var0.R(var1, TryHarvestBlockHeadEvent.class, 3, new BrokenBlockTrackerTryHarvestBlockHeadInvoker(var1));
        var0.R(var1, PostTickEvent.class, 3, new BrokenBlockTrackerPostTickInvoker(var1));
}
}