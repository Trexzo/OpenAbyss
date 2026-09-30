/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostDrawScreenEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.invoker.FastCraftPostDrawScreenInvoker;
import Abyss.event.invoker.FastCraftPreUpdateInvoker;
import Abyss.module.impl.player.FastCraft;

public final class FastCraftBinder {
    private FastCraftBinder() {
}
    public static void t(EventBus var0, FastCraft var3) {
        var0.R(var3, PreUpdateEvent.class, 3, new FastCraftPreUpdateInvoker(var3));
        var0.R(var3, PostDrawScreenEvent.class, 3, new FastCraftPostDrawScreenInvoker(var3));
}
}