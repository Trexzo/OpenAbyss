/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.player.InvClicker;

public final class InvClickerPreUpdateInvoker
implements EventInvoker {
    final InvClicker a;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x1834E3ED2C3DL;
        this.a.onPreUpdate(var4, (PreUpdateEvent)var3);
}
    public InvClickerPreUpdateInvoker(InvClicker var1) {
        this.a = var1;
}
}