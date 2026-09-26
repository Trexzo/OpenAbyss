/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.invoker.BridgeAssistMoveInputInvoker;
import Abyss.event.invoker.BridgeAssistPreMouseInputInvoker;
import Abyss.event.invoker.BridgeAssistSendPacketInvoker;
import Abyss.module.impl.world.BridgeAssist;

public final class BridgeAssistBinder {
    public static void v(EventBus var0, BridgeAssist var3) {
        var0.R(var3, MoveInputEvent.class, 3, new BridgeAssistMoveInputInvoker(var3));
        var0.R(var3, SendPacketEvent.class, 3, new BridgeAssistSendPacketInvoker(var3));
        var0.R(var3, PreMouseInputEvent.class, 3, new BridgeAssistPreMouseInputInvoker(var3));
}
    private BridgeAssistBinder() {
}
}