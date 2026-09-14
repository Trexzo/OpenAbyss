/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.HandleChatEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.FKCounterHandleChatInvoker;
import Abyss.event.invoker.FKCounterPostTickInvoker;
import Abyss.event.invoker.FKCounterRender2DInvoker;
import Abyss.module.impl.visual_utility.FKCounter;

public final class FKCounterBinder {
    private static final long private FKCounterBinder() {
}
    public static void p(EventBus var0, FKCounter var1) {
        var0.R(var1, PostTickEvent.class, 3, new FKCounterPostTickInvoker(var1));
        var0.R(var1, HandleChatEvent.class, 3, new FKCounterHandleChatInvoker(var1));
        var0.R(var1, Render2DEvent.class, 3, new FKCounterRender2DInvoker(var1));
}
}