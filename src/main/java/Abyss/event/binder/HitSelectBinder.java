/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.invoker.HitSelectAttackEntityInvoker;
import Abyss.event.invoker.HitSelectPreMouseInputInvoker;
import Abyss.event.invoker.HitSelectReceivePacketInvoker;
import Abyss.module.impl.combat.HitSelect;

public final class HitSelectBinder {
    private static final long public static void J(EventBus var0, HitSelect var3) {
        var0.R(var3, AttackEntityEvent.class, 3, new HitSelectAttackEntityInvoker(var3));
        var0.R(var3, PreMouseInputEvent.class, 3, new HitSelectPreMouseInputInvoker(var3));
        var0.R(var3, ReceivePacketEvent.class, 3, new HitSelectReceivePacketInvoker(var3));
}
    private HitSelectBinder() {
}
}