/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.KnockbackEvent;
import Abyss.module.impl.combat.Velocity;

public final class VelocityKnockbackInvoker
implements EventInvoker {
    final Velocity J;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x40A53860AE60L;
        this.J.onKnockback(var4, (KnockbackEvent)var3);
}
    public VelocityKnockbackInvoker(Velocity var1) {
        this.J = var1;
}
}