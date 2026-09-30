/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.TrajectoriesRender3DInvoker;
import Abyss.module.impl.visual_utility.Trajectories;

public final class TrajectoriesBinder {
    private TrajectoriesBinder() {
}
    public static void U(EventBus var1, Trajectories var4) {
        var1.R(var4, Render3DEvent.class, 3, new TrajectoriesRender3DInvoker(var4));
}
}