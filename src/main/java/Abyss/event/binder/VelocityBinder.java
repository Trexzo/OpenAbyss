/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.KnockbackEvent;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.event.invoker.VelocityKnockbackInvoker;
import Abyss.event.invoker.VelocityPreLivingUpdateInvoker;
import Abyss.event.invoker.VelocityPreMouseInputInvoker;
import Abyss.event.invoker.VelocityPreTickInvoker;
import Abyss.event.invoker.VelocityReceivePacketInvoker;
import Abyss.event.invoker.VelocityWorldLoadInvoker;
import Abyss.module.impl.combat.Velocity;

public final class VelocityBinder {
    private static final long private VelocityBinder() {
}
    public static void T(EventBus var0, Velocity var1) {
        var0.R(var1, KnockbackEvent.class, 3, new VelocityKnockbackInvoker(var1));
        var0.R(var1, PreMouseInputEvent.class, 4, new VelocityPreMouseInputInvoker(var1));
        var0.R(var1, ReceivePacketEvent.class, 3, new VelocityReceivePacketInvoker(var1));
        var0.R(var1, PreLivingUpdateEvent.class, 3, new VelocityPreLivingUpdateInvoker(var1));
        var0.R(var1, PreTickEvent.class, 3, new VelocityPreTickInvoker(var1));
        var0.R(var1, WorldLoadEvent.class, 3, new VelocityWorldLoadInvoker(var1));
}
}