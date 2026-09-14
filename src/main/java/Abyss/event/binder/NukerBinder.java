/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.invoker.NukerHeldItemChangeInvoker;
import Abyss.event.invoker.NukerPreMouseInputInvoker;
import Abyss.event.invoker.NukerSendPacketInvoker;
import Abyss.module.impl.world.Nuker;

public final class NukerBinder {
    private static final long private NukerBinder() {
}
    public static void p(EventBus var0, Nuker var3) {
        var0.R(var3, HeldItemChangeEvent.class, 3, new NukerHeldItemChangeInvoker(var3));
        var0.R(var3, PreMouseInputEvent.class, 3, new NukerPreMouseInputInvoker(var3));
        var0.R(var3, SendPacketEvent.class, 3, new NukerSendPacketInvoker(var3));
}
}