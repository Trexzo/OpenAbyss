/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.AttackTargetEntityEvent;
import Abyss.module.impl.combat.SprintReset;

public final class SprintResetAttackTargetEntityInvoker
implements EventInvoker {
    final SprintReset H;

    public SprintResetAttackTargetEntityInvoker(SprintReset var1) {
        this.H = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.H.onAttackTargetEntity((AttackTargetEntityEvent)var3);
}
}