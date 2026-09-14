/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.misc.Timer;

public final class TimerRender2DInvoker
implements EventInvoker {
    final Timer b;

    public TimerRender2DInvoker(Timer var1) {
        this.b = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x693CE5791BDFL;
        this.b.onRender2D(var4, (Render2DEvent)var3);
}
}