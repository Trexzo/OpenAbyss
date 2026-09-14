/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.ChestESPEntityJoinWorldInvoker;
import Abyss.event.invoker.ChestESPPlayerRightClickInvoker;
import Abyss.event.invoker.ChestESPPostTickInvoker;
import Abyss.event.invoker.ChestESPRender3DInvoker;
import Abyss.module.impl.visual_utility.ChestESP;

public final class ChestESPBinder {
    private static final long public static void N(EventBus var2, ChestESP var3) {
        var2.R(var3, PostTickEvent.class, 3, new ChestESPPostTickInvoker(var3));
        var2.R(var3, Render3DEvent.class, 3, new ChestESPRender3DInvoker(var3));
        var2.R(var3, PlayerRightClickEvent.class, 3, new ChestESPPlayerRightClickInvoker(var3));
        var2.R(var3, EntityJoinWorldEvent.class, 3, new ChestESPEntityJoinWorldInvoker(var3));
}
    private ChestESPBinder() {
}
}