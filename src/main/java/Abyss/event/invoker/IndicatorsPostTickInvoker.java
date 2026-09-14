/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual_utility.Indicators;

public final class IndicatorsPostTickInvoker
implements EventInvoker {
    final Indicators C;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x19DD970761E9L;
        this.C.onPostTick(var4, (PostTickEvent)var3);
}
    public IndicatorsPostTickInvoker(Indicators var1) {
        this.C = var1;
}
}