/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.event.invoker.NoInteractPlayerRightClickInvoker;
import Abyss.module.impl.player.NoInteract;

public final class NoInteractBinder {
    private static final long private NoInteractBinder() {
}
    public static void y(EventBus var2, NoInteract var3) {
        var2.R(var3, PlayerRightClickEvent.class, 3, new NoInteractPlayerRightClickInvoker(var3));
}
}