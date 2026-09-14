/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual_utility.FallIndicator;

public final class FallIndicatorRender2DInvoker
implements EventInvoker {
    final FallIndicator l;

    public FallIndicatorRender2DInvoker(FallIndicator var1) {
        this.l = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x260710E1B108L;
        this.l.onRender2D((Render2DEvent)var3, var4);
}
}