/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.invoker.SpeedMinePostTickInvoker;
import Abyss.module.impl.world.SpeedMine;

public final class SpeedMineBinder {
    public static void H(EventBus var2, SpeedMine var3) {
        var2.R(var3, PostTickEvent.class, 3, new SpeedMinePostTickInvoker(var3));
}
    private SpeedMineBinder() {
}
}