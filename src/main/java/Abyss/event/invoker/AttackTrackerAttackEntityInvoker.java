/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.AttackEntityEvent;
import Abyss.util.AttackTracker;

public final class AttackTrackerAttackEntityInvoker
implements EventInvoker {
    final AttackTracker v;

    public AttackTrackerAttackEntityInvoker(AttackTracker var1) {
        this.v = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x1C42B90B0B41L;
        this.v.onAttackEntity((AttackEntityEvent)var3, var4);
}
}