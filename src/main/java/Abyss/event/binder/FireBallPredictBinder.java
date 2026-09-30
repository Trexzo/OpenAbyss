/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.FireBallPredictPostTickInvoker;
import Abyss.event.invoker.FireBallPredictRender3DInvoker;
import Abyss.module.impl.visual_utility.FireBallPredict;

public final class FireBallPredictBinder {
    private FireBallPredictBinder() {
}
    public static void P(EventBus var2, FireBallPredict var3) {
        var2.R(var3, PostTickEvent.class, 3, new FireBallPredictPostTickInvoker(var3));
        var2.R(var3, Render3DEvent.class, 3, new FireBallPredictRender3DInvoker(var3));
}
}