/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.invoker.FakeLagPostTickInvoker;
import Abyss.event.invoker.FakeLagPreLivingUpdateInvoker;
import Abyss.event.invoker.FakeLagRender2DInvoker;
import Abyss.event.invoker.FakeLagSendPacketInvoker;
import Abyss.module.impl.combat.FakeLag;

public final class FakeLagBinder {
    private static final long public static void S(EventBus var2, FakeLag var3) {
        var2.R(var3, PreLivingUpdateEvent.class, 3, new FakeLagPreLivingUpdateInvoker(var3));
        var2.R(var3, PostTickEvent.class, 3, new FakeLagPostTickInvoker(var3));
        var2.R(var3, Render2DEvent.class, 3, new FakeLagRender2DInvoker(var3));
        var2.R(var3, SendPacketEvent.class, 3, new FakeLagSendPacketInvoker(var3));
}
    private FakeLagBinder() {
}
}