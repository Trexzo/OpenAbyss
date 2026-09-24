/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.LeapModeHUDReceivePacketInvoker;
import Abyss.event.invoker.LeapModeHUDRender2DInvoker;
import Abyss.module.impl.visual_utility.LeapModeHUD;

public final class LeapModeHUDBinder {
    public static void k(EventBus var0, LeapModeHUD var1) {
        var0.R(var1, ReceivePacketEvent.class, 3, new LeapModeHUDReceivePacketInvoker(var1));
        var0.R(var1, Render2DEvent.class, 3, new LeapModeHUDRender2DInvoker(var1));
}
    private LeapModeHUDBinder() {
}
}