/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.combat.FakeLag;

public final class FakeLagPostTickInvoker
implements EventInvoker {
    final FakeLag N;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x62EE65AC6363L;
        this.N.onPostTick((PostTickEvent)var3, var4);
}
    public FakeLagPostTickInvoker(FakeLag var1) {
        this.N = var1;
}
}