/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.HandleChatEvent;
import Abyss.module.impl.visual.KillEffect;

public final class KillEffectHandleChatInvoker
implements EventInvoker {
    final KillEffect v;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x5DEBDD3ABEFEL;
        this.v.onHandleChat(var4, (HandleChatEvent)var3);
}
    public KillEffectHandleChatInvoker(KillEffect var1) {
        this.v = var1;
}
}