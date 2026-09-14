/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual_utility.FireBallPredict;

public final class FireBallPredictPostTickInvoker
implements EventInvoker {
    final FireBallPredict L;

    public FireBallPredictPostTickInvoker(FireBallPredict var1) {
        this.L = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x370EB055C592L;
        this.L.onPostTick(var4, (PostTickEvent)var3);
}
}