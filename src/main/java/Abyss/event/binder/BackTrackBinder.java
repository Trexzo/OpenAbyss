/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.BackTrackAttackEntityInvoker;
import Abyss.event.invoker.BackTrackPreUpdateInvoker;
import Abyss.event.invoker.BackTrackRender2DInvoker;
import Abyss.module.impl.combat.BackTrack;

public final class BackTrackBinder {
    public static void P(EventBus var2, BackTrack var3) {
        var2.R(var3, PreUpdateEvent.class, 3, new BackTrackPreUpdateInvoker(var3));
        var2.R(var3, AttackEntityEvent.class, 3, new BackTrackAttackEntityInvoker(var3));
        var2.R(var3, Render2DEvent.class, 3, new BackTrackRender2DInvoker(var3));
}
    private BackTrackBinder() {
}
}