/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.macro.Macro4;

public final class Macro4PreTickInvoker
implements EventInvoker {
    final Macro4 W;

    public Macro4PreTickInvoker(Macro4 var1) {
        this.W = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.W.onPreTick((PreTickEvent)var3);
}
}