/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render3DEvent;
import Abyss.module.impl.visual.BreakProgress;

public final class BreakProgressRender3DInvoker
implements EventInvoker {
    final BreakProgress T;

    public BreakProgressRender3DInvoker(BreakProgress var1) {
        this.T = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.T.onRender3D((Render3DEvent)var3);
}
}