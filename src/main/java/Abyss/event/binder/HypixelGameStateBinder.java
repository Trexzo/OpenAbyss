/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.event.invoker.HypixelGameStatePostTickInvoker;
import Abyss.event.invoker.HypixelGameStateWorldLoadInvoker;
import Abyss.util.HypixelGameState;

public final class HypixelGameStateBinder {
    private static final long public static void F(EventBus var2, HypixelGameState var3) {
        var2.R(var3, PostTickEvent.class, 3, new HypixelGameStatePostTickInvoker(var3));
        var2.R(var3, WorldLoadEvent.class, 3, new HypixelGameStateWorldLoadInvoker(var3));
}
    private HypixelGameStateBinder() {
}
}