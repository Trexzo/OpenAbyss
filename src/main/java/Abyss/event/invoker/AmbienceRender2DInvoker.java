/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual.Ambience;

public final class AmbienceRender2DInvoker
implements EventInvoker {
    final Ambience w;

    @Override
    public void c(long var1, Object var3) {
        this.w.onRender2D((Render2DEvent)var3);
}
    public AmbienceRender2DInvoker(Ambience var1) {
        this.w = var1;
}
}