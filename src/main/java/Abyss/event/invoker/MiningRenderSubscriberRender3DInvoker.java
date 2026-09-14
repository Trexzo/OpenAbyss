/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.MiningRenderSubscriber;

public final class MiningRenderSubscriberRender3DInvoker
implements EventInvoker {
    final MiningRenderSubscriber s;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x5151FCA253C5L;
        this.s.onRender3D(var4, (Render3DEvent)var3);
}
    public MiningRenderSubscriberRender3DInvoker(MiningRenderSubscriber var1) {
        this.s = var1;
}
}