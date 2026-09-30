/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreTickEvent;
import Abyss.event.invoker.AutoClickerPreTickInvoker;
import Abyss.module.impl.combat.AutoClicker;

public final class AutoClickerBinder {
    public static void N(EventBus var2, AutoClicker var3) {
        var2.R(var3, PreTickEvent.class, 3, new AutoClickerPreTickInvoker(var3));
}
    private AutoClickerBinder() {
}
}