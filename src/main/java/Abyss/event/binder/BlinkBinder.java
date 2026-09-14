/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.AttackTargetEntityEvent;
import Abyss.event.events.PostUpdateWalkingPlayerEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.BlinkAttackTargetEntityInvoker;
import Abyss.event.invoker.BlinkPostUpdateWalkingPlayerInvoker;
import Abyss.event.invoker.BlinkRender2DInvoker;
import Abyss.module.impl.player.Blink;

public final class BlinkBinder {
    private static final long private BlinkBinder() {
}
    public static void o(short var0, EventBus var2, Blink var4) {
        var2.R(var4, PostUpdateWalkingPlayerEvent.class, 3, new BlinkPostUpdateWalkingPlayerInvoker(var4));
        var2.R(var4, AttackTargetEntityEvent.class, 3, new BlinkAttackTargetEntityInvoker(var4));
        var2.R(var4, Render2DEvent.class, 3, new BlinkRender2DInvoker(var4));
}
}