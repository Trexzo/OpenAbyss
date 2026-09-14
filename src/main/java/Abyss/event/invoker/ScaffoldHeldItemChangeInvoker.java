/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.module.impl.world.Scaffold;

public final class ScaffoldHeldItemChangeInvoker
implements EventInvoker {
    final Scaffold I;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x7766BCF221F4L;
        this.I.onHeldItemChange((HeldItemChangeEvent)var3, var4);
}
    public ScaffoldHeldItemChangeInvoker(Scaffold var1) {
        this.I = var1;
}
}