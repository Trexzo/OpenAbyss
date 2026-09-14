/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.macro.Macro5;

public final class Macro5PreTickInvoker
implements EventInvoker {
    final Macro5 X;

    @Override
    public void c(long var1, Object var3) {
        this.X.onPreTick((PreTickEvent)var3);
}
    public Macro5PreTickInvoker(Macro5 var1) {
        this.X = var1;
}
}