/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.BedPlatesPreUpdateInvoker;
import Abyss.event.invoker.BedPlatesRender3DInvoker;
import Abyss.module.impl.visual_utility.BedPlates;

public final class BedPlatesBinder {
    public static void y(EventBus var0, BedPlates var3) {
        var0.R(var3, Render3DEvent.class, 3, new BedPlatesRender3DInvoker(var3));
        var0.R(var3, PreUpdateEvent.class, 3, new BedPlatesPreUpdateInvoker(var3));
}
    private BedPlatesBinder() {
}
}