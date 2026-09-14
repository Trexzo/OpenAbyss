/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.combat.Velocity;

public final class VelocityPreTickInvoker
implements EventInvoker {
    final Velocity J;

    public VelocityPreTickInvoker(Velocity var1) {
        this.J = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2DC9A2E8009BL;
        this.J.onPreTick((PreTickEvent)var3, var4);
}
}