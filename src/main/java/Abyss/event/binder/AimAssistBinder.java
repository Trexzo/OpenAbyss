/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.SetAnglesEvent;
import Abyss.event.invoker.AimAssistPostTickInvoker;
import Abyss.event.invoker.AimAssistSetAnglesInvoker;
import Abyss.module.impl.combat.AimAssist;

public final class AimAssistBinder {
    private AimAssistBinder() {
}
    public static void u(EventBus var0, AimAssist var3) {
        var0.R(var3, PostTickEvent.class, 3, new AimAssistPostTickInvoker(var3));
        var0.R(var3, SetAnglesEvent.class, 3, new AimAssistSetAnglesInvoker(var3));
}
}