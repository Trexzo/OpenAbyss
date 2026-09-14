/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.AttackTargetEntityEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.TargetHUDAttackTargetEntityInvoker;
import Abyss.event.invoker.TargetHUDPreUpdateInvoker;
import Abyss.event.invoker.TargetHUDRender2DInvoker;
import Abyss.module.impl.visual_utility.TargetHUD;

public final class TargetHUDBinder {
    private static final long private TargetHUDBinder() {
}
    public static void Y(EventBus var0, TargetHUD var1) {
        var0.R(var1, Render2DEvent.class, 3, new TargetHUDRender2DInvoker(var1));
        var0.R(var1, PreUpdateEvent.class, 3, new TargetHUDPreUpdateInvoker(var1));
        var0.R(var1, AttackTargetEntityEvent.class, 3, new TargetHUDAttackTargetEntityInvoker(var1));
}
}