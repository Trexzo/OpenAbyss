/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.invoker.MegaWallsDetectorPlayerGetNameInvoker;
import Abyss.event.invoker.MegaWallsDetectorPostTickInvoker;
import Abyss.module.impl.visual_utility.MegaWallsDetector;

public final class MegaWallsDetectorBinder {
    private MegaWallsDetectorBinder() {
}
    public static void X(EventBus var0, int var1, MegaWallsDetector var4) {
        var0.R(var4, PostTickEvent.class, 3, new MegaWallsDetectorPostTickInvoker(var4));
        var0.R(var4, PlayerGetNameEvent.class, 3, new MegaWallsDetectorPlayerGetNameInvoker(var4));
}
}