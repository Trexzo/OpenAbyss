/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.CloseScreenEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.invoker.InvMoveCloseScreenInvoker;
import Abyss.event.invoker.InvMovePostTickInvoker;
import Abyss.event.invoker.InvMovePreUpdateInvoker;
import Abyss.event.invoker.InvMoveSendPacketInvoker;
import Abyss.module.impl.movement.InvMove;

public final class InvMoveBinder {
    private static final long public static void I(EventBus var0, InvMove var3) {
        var0.R(var3, CloseScreenEvent.class, 3, new InvMoveCloseScreenInvoker(var3));
        var0.R(var3, PreUpdateEvent.class, 3, new InvMovePreUpdateInvoker(var3));
        var0.R(var3, SendPacketEvent.class, 3, new InvMoveSendPacketInvoker(var3));
        var0.R(var3, PostTickEvent.class, 3, new InvMovePostTickInvoker(var3));
}
    private InvMoveBinder() {
}
}