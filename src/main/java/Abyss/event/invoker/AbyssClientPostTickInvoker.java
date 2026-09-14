/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.AbyssClient;
import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;

public final class AbyssClientPostTickInvoker
implements EventInvoker {
    final AbyssClient p;

    public AbyssClientPostTickInvoker(AbyssClient var1) {
        this.p = var1;
}
    @Override
    public void c(long var1, Object var3) throws Throwable {
        long var4 = var1 ^ 0x5B63491C993CL;
        this.p.onPostTick((PostTickEvent)var3, var4);
}
}