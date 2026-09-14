/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.AttackEntityEvent;
import Abyss.module.impl.combat.WTap;

public final class WTapAttackEntityInvoker
implements EventInvoker {
    final WTap b;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0xB9E13E72CFL;
        this.b.onAttackEntity(var4, (AttackEntityEvent)var3);
}
    public WTapAttackEntityInvoker(WTap var1) {
        this.b = var1;
}
}