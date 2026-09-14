/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.AttackTargetEntityEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreSuperLivingUpdateEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.invoker.SprintResetAttackEntityInvoker;
import Abyss.event.invoker.SprintResetAttackTargetEntityInvoker;
import Abyss.event.invoker.SprintResetMoveInputInvoker;
import Abyss.event.invoker.SprintResetPostTickInvoker;
import Abyss.event.invoker.SprintResetPreSuperLivingUpdateInvoker;
import Abyss.event.invoker.SprintResetPreUpdateInvoker;
import Abyss.event.invoker.SprintResetReceivePacketInvoker;
import Abyss.module.impl.combat.SprintReset;

public final class SprintResetBinder {
    private static final long private SprintResetBinder() {
}
    public static void G(EventBus var0, SprintReset var1) {
        var0.R(var1, PreUpdateEvent.class, 3, new SprintResetPreUpdateInvoker(var1));
        var0.R(var1, AttackEntityEvent.class, 3, new SprintResetAttackEntityInvoker(var1));
        var0.R(var1, AttackTargetEntityEvent.class, 3, new SprintResetAttackTargetEntityInvoker(var1));
        var0.R(var1, PreSuperLivingUpdateEvent.class, 3, new SprintResetPreSuperLivingUpdateInvoker(var1));
        var0.R(var1, ReceivePacketEvent.class, 3, new SprintResetReceivePacketInvoker(var1));
        var0.R(var1, MoveInputEvent.class, 3, new SprintResetMoveInputInvoker(var1));
        var0.R(var1, PostTickEvent.class, 3, new SprintResetPostTickInvoker(var1));
}
}