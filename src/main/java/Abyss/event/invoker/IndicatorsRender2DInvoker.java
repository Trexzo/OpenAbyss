/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual_utility.Indicators;

public final class IndicatorsRender2DInvoker
implements EventInvoker {
    final Indicators Z;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x47DE76123C9EL;
        this.Z.onRender2D(var4, (Render2DEvent)var3);
}
    public IndicatorsRender2DInvoker(Indicators var1) {
        this.Z = var1;
}
}