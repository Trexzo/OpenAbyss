/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.event.invoker.AmbienceReceivePacketInvoker;
import Abyss.event.invoker.AmbienceRender2DInvoker;
import Abyss.event.invoker.AmbienceUpdateWalkingPlayerInvoker;
import Abyss.module.impl.visual.Ambience;

public final class AmbienceBinder {
    private static final long public static void K(EventBus var2, Ambience var3) {
        var2.R(var3, UpdateWalkingPlayerEvent.class, 3, new AmbienceUpdateWalkingPlayerInvoker(var3));
        var2.R(var3, ReceivePacketEvent.class, 3, new AmbienceReceivePacketInvoker(var3));
        var2.R(var3, Render2DEvent.class, 3, new AmbienceRender2DInvoker(var3));
}
    private AmbienceBinder() {
}
}