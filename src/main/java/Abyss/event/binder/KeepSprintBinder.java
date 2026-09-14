/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.PreSuperLivingUpdateEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.invoker.KeepSprintAttackEntityInvoker;
import Abyss.event.invoker.KeepSprintPreSuperLivingUpdateInvoker;
import Abyss.event.invoker.KeepSprintPreTickInvoker;
import Abyss.event.invoker.KeepSprintPreUpdateInvoker;
import Abyss.module.impl.combat.KeepSprint;

public final class KeepSprintBinder {
    private static final long private KeepSprintBinder() {
}
    public static void O(EventBus var0, short var1, short var2, KeepSprint var4) {
        var0.R(var4, PreTickEvent.class, 3, new KeepSprintPreTickInvoker(var4));
        var0.R(var4, AttackEntityEvent.class, 5, new KeepSprintAttackEntityInvoker(var4));
        var0.R(var4, PreSuperLivingUpdateEvent.class, 3, new KeepSprintPreSuperLivingUpdateInvoker(var4));
        var0.R(var4, PreUpdateEvent.class, 1, new KeepSprintPreUpdateInvoker(var4));
}
}