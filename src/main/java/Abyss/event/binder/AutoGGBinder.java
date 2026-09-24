/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.HandleChatEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.invoker.AutoGGHandleChatInvoker;
import Abyss.event.invoker.AutoGGPostTickInvoker;
import Abyss.module.impl.misc.AutoGG;

public final class AutoGGBinder {
    public static void X(EventBus var0, AutoGG var3) {
        var0.R(var3, HandleChatEvent.class, 3, new AutoGGHandleChatInvoker(var3));
        var0.R(var3, PostTickEvent.class, 3, new AutoGGPostTickInvoker(var3));
}
    private AutoGGBinder() {
}
}