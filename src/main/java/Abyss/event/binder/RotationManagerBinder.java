/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.event.invoker.RotationManagerMoveInputInvoker;
import Abyss.event.invoker.RotationManagerPreMouseInputInvoker;
import Abyss.event.invoker.RotationManagerPreTickInvoker;
import Abyss.event.invoker.RotationManagerReceivePacketInvoker;
import Abyss.event.invoker.RotationManagerUpdateWalkingPlayerInvoker;
import Abyss.util.RotationManager;

public final class RotationManagerBinder {
    private RotationManagerBinder() {
}
    public static void M(EventBus var0, RotationManager var1) {
        var0.R(var1, PreTickEvent.class, 5, new RotationManagerPreTickInvoker(var1));
        var0.R(var1, UpdateWalkingPlayerEvent.class, 3, new RotationManagerUpdateWalkingPlayerInvoker(var1));
        var0.R(var1, MoveInputEvent.class, 3, new RotationManagerMoveInputInvoker(var1));
        var0.R(var1, PreMouseInputEvent.class, 5, new RotationManagerPreMouseInputInvoker(var1));
        var0.R(var1, ReceivePacketEvent.class, 3, new RotationManagerReceivePacketInvoker(var1));
}
}