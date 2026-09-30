/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.invoker.AutoDigPlaceAttackEntityInvoker;
import Abyss.event.invoker.AutoDigPlaceHeldItemChangeInvoker;
import Abyss.event.invoker.AutoDigPlacePreMouseInputInvoker;
import Abyss.event.invoker.AutoDigPlaceSendPacketInvoker;
import Abyss.module.impl.world.AutoDigPlace;

public final class AutoDigPlaceBinder {
    public static void n(EventBus var0, AutoDigPlace var2) {
        var0.R(var2, HeldItemChangeEvent.class, 3, new AutoDigPlaceHeldItemChangeInvoker(var2));
        var0.R(var2, AttackEntityEvent.class, 3, new AutoDigPlaceAttackEntityInvoker(var2));
        var0.R(var2, PreMouseInputEvent.class, 3, new AutoDigPlacePreMouseInputInvoker(var2));
        var0.R(var2, SendPacketEvent.class, 3, new AutoDigPlaceSendPacketInvoker(var2));
}
    private AutoDigPlaceBinder() {
}
}