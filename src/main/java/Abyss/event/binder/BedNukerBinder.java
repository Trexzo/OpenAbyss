/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.ClickMouseEvent;
import Abyss.event.events.IsPressedEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.invoker.BedNukerClickMouseInvoker;
import Abyss.event.invoker.BedNukerIsPressedInvoker;
import Abyss.event.invoker.BedNukerPreMouseInputInvoker;
import Abyss.event.invoker.BedNukerPreTickInvoker;
import Abyss.event.invoker.BedNukerReceivePacketInvoker;
import Abyss.event.invoker.BedNukerRender2DInvoker;
import Abyss.event.invoker.BedNukerRender3DInvoker;
import Abyss.event.invoker.BedNukerSendPacketInvoker;
import Abyss.module.impl.world.BedNuker;

public final class BedNukerBinder {
    private static final long public static void d(EventBus var2, BedNuker var3) {
        var2.R(var3, ClickMouseEvent.class, 3, new BedNukerClickMouseInvoker(var3));
        var2.R(var3, IsPressedEvent.class, 3, new BedNukerIsPressedInvoker(var3));
        var2.R(var3, PreMouseInputEvent.class, 4, new BedNukerPreMouseInputInvoker(var3));
        var2.R(var3, PreTickEvent.class, 3, new BedNukerPreTickInvoker(var3));
        var2.R(var3, ReceivePacketEvent.class, 3, new BedNukerReceivePacketInvoker(var3));
        var2.R(var3, SendPacketEvent.class, 3, new BedNukerSendPacketInvoker(var3));
        var2.R(var3, Render3DEvent.class, 3, new BedNukerRender3DInvoker(var3));
        var2.R(var3, Render2DEvent.class, 3, new BedNukerRender2DInvoker(var3));
}
    private BedNukerBinder() {
}
}