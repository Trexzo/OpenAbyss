/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.invoker.AttackTrackerAttackEntityInvoker;
import Abyss.util.AttackTracker;

public final class AttackTrackerBinder {
    private AttackTrackerBinder() {
}
    public static void D(EventBus var0, AttackTracker var1) {
        var0.R(var1, AttackEntityEvent.class, 1, new AttackTrackerAttackEntityInvoker(var1));
}
}