/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.DrawScreenEvent;
import Abyss.event.events.PickUpItemEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.invoker.InvManagerDrawScreenInvoker;
import Abyss.event.invoker.InvManagerPickUpItemInvoker;
import Abyss.event.invoker.InvManagerPreUpdateInvoker;
import Abyss.module.impl.player.InvManager;

public final class InvManagerBinder {
    private InvManagerBinder() {
}
    public static void L(EventBus var2, InvManager var3) {
        var2.R(var3, PreUpdateEvent.class, 3, new InvManagerPreUpdateInvoker(var3));
        var2.R(var3, DrawScreenEvent.class, 3, new InvManagerDrawScreenInvoker(var3));
        var2.R(var3, PickUpItemEvent.class, 3, new InvManagerPickUpItemInvoker(var3));
}
}