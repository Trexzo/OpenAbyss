/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.AbyssClient;
import Abyss.event.EventInvoker;
import Abyss.event.events.EntityJoinWorldEvent;

public final class AbyssClientEntityJoinWorldInvoker
implements EventInvoker {
    final AbyssClient y;

    public AbyssClientEntityJoinWorldInvoker(AbyssClient var1) {
        this.y = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x3586DC3DF97DL;
        this.y.onEntityJoinWorld(var4, (EntityJoinWorldEvent)var3);
}
}