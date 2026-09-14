/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.player.ChestStealer;

public final class ChestStealerRender2DInvoker
implements EventInvoker {
    final ChestStealer q;

    public ChestStealerRender2DInvoker(ChestStealer var1) {
        this.q = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x401E2B01905FL;
        this.q.onRender2D(var4, (Render2DEvent)var3);
}
}