/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render3DEvent;
import Abyss.module.impl.visual_utility.FireBallPredict;

public final class FireBallPredictRender3DInvoker
implements EventInvoker {
    final FireBallPredict r;

    public FireBallPredictRender3DInvoker(FireBallPredict var1) {
        this.r = var1;
}
    @Override
    public void c(long var1, Object var3) throws Throwable {
        long var4 = var1 ^ 0x61E76D3648BDL;
        this.r.onRender3D(var4, (Render3DEvent)var3);
}
}