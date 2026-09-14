/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.AttackEntityEvent;
import Abyss.module.impl.combat.HitSelect;

public final class HitSelectAttackEntityInvoker
implements EventInvoker {
    final HitSelect N;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x5441B183B835L;
        this.N.onAttackEntity(var4, (AttackEntityEvent)var3);
}
    public HitSelectAttackEntityInvoker(HitSelect var1) {
        this.N = var1;
}
}