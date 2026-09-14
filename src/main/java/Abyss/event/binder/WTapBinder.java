/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.invoker.WTapAttackEntityInvoker;
import Abyss.event.invoker.WTapMoveInputInvoker;
import Abyss.event.invoker.WTapPreMouseInputInvoker;
import Abyss.event.invoker.WTapReceivePacketInvoker;
import Abyss.module.impl.combat.WTap;

public final class WTapBinder {
    private static final long public static void U(EventBus var1, WTap var3) {
        var1.R(var3, AttackEntityEvent.class, 3, new WTapAttackEntityInvoker(var3));
        var1.R(var3, PreMouseInputEvent.class, 3, new WTapPreMouseInputInvoker(var3));
        var1.R(var3, MoveInputEvent.class, 3, new WTapMoveInputInvoker(var3));
        var1.R(var3, ReceivePacketEvent.class, 3, new WTapReceivePacketInvoker(var3));
}
    private WTapBinder() {
}
}