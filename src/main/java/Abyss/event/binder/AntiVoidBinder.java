/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.event.invoker.AntiVoidSetKeyBindStateInvoker;
import Abyss.event.invoker.AntiVoidUpdateWalkingPlayerInvoker;
import Abyss.module.impl.world.AntiVoid;

public final class AntiVoidBinder {
    private static final long private AntiVoidBinder() {
}
    public static void Q(EventBus var0, AntiVoid var3) {
        var0.R(var3, UpdateWalkingPlayerEvent.class, 3, new AntiVoidUpdateWalkingPlayerInvoker(var3));
        var0.R(var3, SetKeyBindStateEvent.class, 3, new AntiVoidSetKeyBindStateInvoker(var3));
}
}