/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.DisconnectedInitEvent;
import Abyss.event.events.PreDrawScreenEvent;
import Abyss.event.events.ServerJoinEvent;
import Abyss.event.invoker.ReconnectHandlerDisconnectedInitInvoker;
import Abyss.event.invoker.ReconnectHandlerPreDrawScreenInvoker;
import Abyss.event.invoker.ReconnectHandlerServerJoinInvoker;
import Abyss.ui.screen.ReconnectHandler;

public final class ReconnectHandlerBinder {
    public static void v(EventBus var0, ReconnectHandler var3) {
        var0.R(var3, PreDrawScreenEvent.class, 3, new ReconnectHandlerPreDrawScreenInvoker(var3));
        var0.R(var3, DisconnectedInitEvent.class, 3, new ReconnectHandlerDisconnectedInitInvoker(var3));
        var0.R(var3, ServerJoinEvent.class, 3, new ReconnectHandlerServerJoinInvoker(var3));
}
    private ReconnectHandlerBinder() {
}
}