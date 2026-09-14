/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.player.InvManager;

public final class InvManagerPreUpdateInvoker
implements EventInvoker {
    final InvManager y;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x11550E70309DL;
        this.y.onPreUpdate((PreUpdateEvent)var3, var4);
}
    public InvManagerPreUpdateInvoker(InvManager var1) {
        this.y = var1;
}
}