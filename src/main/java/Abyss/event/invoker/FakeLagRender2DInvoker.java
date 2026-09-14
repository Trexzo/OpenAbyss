/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.combat.FakeLag;

public final class FakeLagRender2DInvoker
implements EventInvoker {
    final FakeLag l;

    public FakeLagRender2DInvoker(FakeLag var1) {
        this.l = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0xF279BA96907L;
        this.l.onRender2D(var4, (Render2DEvent)var3);
}
}