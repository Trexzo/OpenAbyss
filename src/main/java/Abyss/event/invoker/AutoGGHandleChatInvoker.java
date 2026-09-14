/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.HandleChatEvent;
import Abyss.module.impl.misc.AutoGG;

public final class AutoGGHandleChatInvoker
implements EventInvoker {
    final AutoGG K;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x22AF4D5EB447L;
        this.K.onHandleChat(var4, (HandleChatEvent)var3);
}
    public AutoGGHandleChatInvoker(AutoGG var1) {
        this.K = var1;
}
}