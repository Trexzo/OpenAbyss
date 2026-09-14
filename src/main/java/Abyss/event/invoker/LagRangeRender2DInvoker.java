/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.combat.LagRange;

public final class LagRangeRender2DInvoker
implements EventInvoker {
    final LagRange v;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x5F5F5776616BL) >>> 48);
        long var5 = (var1 ^ 0x5F5F5776616BL) << 16 >>> 16;
        this.v.onRender2D((short)var4, (Render2DEvent)var3, var5);
}
    public LagRangeRender2DInvoker(LagRange var1) {
        this.v = var1;
}
}