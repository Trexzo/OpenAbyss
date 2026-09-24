/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.invoker.LagRangePostTickInvoker;
import Abyss.event.invoker.LagRangePreLivingUpdateInvoker;
import Abyss.event.invoker.LagRangeRender2DInvoker;
import Abyss.event.invoker.LagRangeSendPacketInvoker;
import Abyss.module.impl.combat.LagRange;

public final class LagRangeBinder {
    private LagRangeBinder() {
}
    public static void D(EventBus var2, LagRange var3) {
        var2.R(var3, PreLivingUpdateEvent.class, 3, new LagRangePreLivingUpdateInvoker(var3));
        var2.R(var3, Render2DEvent.class, 3, new LagRangeRender2DInvoker(var3));
        var2.R(var3, PostTickEvent.class, 3, new LagRangePostTickInvoker(var3));
        var2.R(var3, SendPacketEvent.class, 3, new LagRangeSendPacketInvoker(var3));
}
}