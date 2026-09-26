/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.CloseScreenEvent;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.invoker.ChestAuraCloseScreenInvoker;
import Abyss.event.invoker.ChestAuraEntityJoinWorldInvoker;
import Abyss.event.invoker.ChestAuraPlayerRightClickInvoker;
import Abyss.event.invoker.ChestAuraPreMouseInputInvoker;
import Abyss.module.impl.player.ChestAura;

public final class ChestAuraBinder {
    private ChestAuraBinder() {
}
    public static void E(EventBus var0, ChestAura var1) {
        var0.R(var1, CloseScreenEvent.class, 3, new ChestAuraCloseScreenInvoker(var1));
        var0.R(var1, PreMouseInputEvent.class, 3, new ChestAuraPreMouseInputInvoker(var1));
        var0.R(var1, PlayerRightClickEvent.class, 3, new ChestAuraPlayerRightClickInvoker(var1));
        var0.R(var1, EntityJoinWorldEvent.class, 3, new ChestAuraEntityJoinWorldInvoker(var1));
}
}