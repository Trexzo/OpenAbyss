/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.invoker.DenickPlayerGetNameInvoker;
import Abyss.event.invoker.DenickPostTickInvoker;
import Abyss.module.impl.misc.Denick;

public final class DenickBinder {
    private static final long private DenickBinder() {
}
    public static void Z(char var0, EventBus var3, Denick var4) {
        var3.R(var4, PostTickEvent.class, 3, new DenickPostTickInvoker(var4));
        var3.R(var4, PlayerGetNameEvent.class, 3, new DenickPlayerGetNameInvoker(var4));
}
}