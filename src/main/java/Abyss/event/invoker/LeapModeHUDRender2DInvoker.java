/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual_utility.LeapModeHUD;

public final class LeapModeHUDRender2DInvoker
implements EventInvoker {
    final LeapModeHUD J;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x27AEB4266514L;
        this.J.onRender2D(var4, (Render2DEvent)var3);
}
    public LeapModeHUDRender2DInvoker(LeapModeHUD var1) {
        this.J = var1;
}
}