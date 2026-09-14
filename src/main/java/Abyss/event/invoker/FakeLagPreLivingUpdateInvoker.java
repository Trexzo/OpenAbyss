/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.module.impl.combat.FakeLag;

public final class FakeLagPreLivingUpdateInvoker
implements EventInvoker {
    final FakeLag j;

    public FakeLagPreLivingUpdateInvoker(FakeLag var1) {
        this.j = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x233019CA3125L;
        this.j.onPreLivingUpdate((PreLivingUpdateEvent)var3, var4);
}
}