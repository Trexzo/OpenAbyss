/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual_utility.FKCounter;

public final class FKCounterRender2DInvoker
implements EventInvoker {
    final FKCounter B;

    public FKCounterRender2DInvoker(FKCounter var1) {
        this.B = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2E114EDDA39L;
        this.B.onRender2D((Render2DEvent)var3, var4);
}
}