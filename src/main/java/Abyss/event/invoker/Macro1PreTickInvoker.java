/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.macro.Macro1;

public final class Macro1PreTickInvoker
implements EventInvoker {
    final Macro1 P;

    public Macro1PreTickInvoker(Macro1 var1) {
        this.P = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.P.onPreTick((PreTickEvent)var3);
}
}