/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.KnockbackEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PostUpdateEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.event.invoker.JumpResetKnockbackInvoker;
import Abyss.event.invoker.JumpResetMoveInputInvoker;
import Abyss.event.invoker.JumpResetPostUpdateInvoker;
import Abyss.event.invoker.JumpResetWorldLoadInvoker;
import Abyss.module.impl.combat.JumpReset;

public final class JumpResetBinder {
    private static final long public static void K(EventBus var2, JumpReset var3) {
        var2.R(var3, KnockbackEvent.class, 3, new JumpResetKnockbackInvoker(var3));
        var2.R(var3, MoveInputEvent.class, 3, new JumpResetMoveInputInvoker(var3));
        var2.R(var3, PostUpdateEvent.class, 3, new JumpResetPostUpdateInvoker(var3));
        var2.R(var3, WorldLoadEvent.class, 3, new JumpResetWorldLoadInvoker(var3));
}
    private JumpResetBinder() {
}
}