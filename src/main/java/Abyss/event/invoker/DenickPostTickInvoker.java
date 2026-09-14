/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.misc.Denick;

public final class DenickPostTickInvoker
implements EventInvoker {
    final Denick l;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x78DBE88157C5L;
        this.l.onPostTick((PostTickEvent)var3, var4);
}
    public DenickPostTickInvoker(Denick var1) {
        this.l = var1;
}
}