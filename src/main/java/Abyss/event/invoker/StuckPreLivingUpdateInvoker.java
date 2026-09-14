/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.module.impl.movement.Stuck;

public final class StuckPreLivingUpdateInvoker
implements EventInvoker {
    final Stuck P;

    public StuckPreLivingUpdateInvoker(Stuck var1) {
        this.P = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x6CFED4F4601AL;
        this.P.onPreLivingUpdate((PreLivingUpdateEvent)var3, var4);
}
}