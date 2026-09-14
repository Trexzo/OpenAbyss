/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual.HUD;

public final class HUDRender2DInvoker
implements EventInvoker {
    final HUD H;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x530AA167FD9EL;
        this.H.onRender2D((Render2DEvent)var3, var4);
}
    public HUDRender2DInvoker(HUD var1) {
        this.H = var1;
}
}