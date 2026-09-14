/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.DisconnectedInitEvent;
import Abyss.ui.screen.ReconnectHandler;

public final class ReconnectHandlerDisconnectedInitInvoker
implements EventInvoker {
    final ReconnectHandler b;

    public ReconnectHandlerDisconnectedInitInvoker(ReconnectHandler var1) {
        this.b = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x5805D2E214B8L;
        this.b.onDisconnectedInit(var4, (DisconnectedInitEvent)var3);
}
}