/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual.BindGUI;

public final class BindGUIRender2DInvoker
implements EventInvoker {
    final BindGUI v;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x14CA3FC1D3B2L;
        this.v.onRender2D(var4, (Render2DEvent)var3);
}
    public BindGUIRender2DInvoker(BindGUI var1) {
        this.v = var1;
}
}