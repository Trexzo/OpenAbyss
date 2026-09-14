/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.LivingDeathEvent;
import Abyss.module.impl.visual.KillEffect;

public final class KillEffectLivingDeathInvoker
implements EventInvoker {
    final KillEffect j;

    public KillEffectLivingDeathInvoker(KillEffect var1) {
        this.j = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0xF7F9E6CFCAL;
        this.j.onLivingDeath((LivingDeathEvent)var3, var4);
}
}