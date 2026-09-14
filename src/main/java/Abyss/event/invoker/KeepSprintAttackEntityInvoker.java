/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.AttackEntityEvent;
import Abyss.module.impl.combat.KeepSprint;

public final class KeepSprintAttackEntityInvoker
implements EventInvoker {
    final KeepSprint d;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x7A4AFC2AFB23L;
        this.d.onAttackEntity(var4, (AttackEntityEvent)var3);
}
    public KeepSprintAttackEntityInvoker(KeepSprint var1) {
        this.d = var1;
}
}