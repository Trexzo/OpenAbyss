/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.BedESPRender3DInvoker;
import Abyss.module.impl.visual_utility.BedESP;

public final class BedESPBinder {
    private static final long public static void Q(EventBus var0, BedESP var3) {
        var0.R(var3, Render3DEvent.class, 3, new BedESPRender3DInvoker(var3));
}
    private BedESPBinder() {
}
}