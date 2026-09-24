/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.invoker.InvClickerPreUpdateInvoker;
import Abyss.module.impl.player.InvClicker;

public final class InvClickerBinder {
    private InvClickerBinder() {
}
    public static void T(EventBus var2, InvClicker var3) {
        var2.R(var3, PreUpdateEvent.class, 3, new InvClickerPreUpdateInvoker(var3));
}
}