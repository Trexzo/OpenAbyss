/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.AttackTargetEntityEvent;
import Abyss.module.impl.player.Blink;

public final class BlinkAttackTargetEntityInvoker
implements EventInvoker {
    final Blink P;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x4148F66C605EL;
        this.P.onAttackTargetEntity((AttackTargetEntityEvent)var3, var4);
}
    public BlinkAttackTargetEntityInvoker(Blink var1) {
        this.P = var1;
}
}