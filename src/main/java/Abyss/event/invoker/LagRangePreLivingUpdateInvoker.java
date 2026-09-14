/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.module.impl.combat.LagRange;

public final class LagRangePreLivingUpdateInvoker
implements EventInvoker {
    final LagRange p;

    public LagRangePreLivingUpdateInvoker(LagRange var1) {
        this.p = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0xA4B27DFBD29L;
        this.p.onPreLivingUpdate((PreLivingUpdateEvent)var3, var4);
}
}