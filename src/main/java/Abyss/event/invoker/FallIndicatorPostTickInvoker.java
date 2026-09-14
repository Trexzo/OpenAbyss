/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual_utility.FallIndicator;

public final class FallIndicatorPostTickInvoker
implements EventInvoker {
    final FallIndicator U;

    public FallIndicatorPostTickInvoker(FallIndicator var1) {
        this.U = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x6528BC05ED25L;
        this.U.onPostTick((PostTickEvent)var3, var4);
}
}