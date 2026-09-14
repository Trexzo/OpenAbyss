/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.events.SetAnglesEvent;
import Abyss.event.invoker.KillAuraPreMouseInputInvoker;
import Abyss.event.invoker.KillAuraRender3DInvoker;
import Abyss.event.invoker.KillAuraSetAnglesInvoker;
import Abyss.module.impl.combat.KillAura;

public final class KillAuraBinder {
    private static final long private KillAuraBinder() {
}
    public static void e(EventBus var2, KillAura var3) {
        var2.R(var3, PreMouseInputEvent.class, 4, new KillAuraPreMouseInputInvoker(var3));
        var2.R(var3, SetAnglesEvent.class, 3, new KillAuraSetAnglesInvoker(var3));
        var2.R(var3, Render3DEvent.class, 3, new KillAuraRender3DInvoker(var3));
}
}