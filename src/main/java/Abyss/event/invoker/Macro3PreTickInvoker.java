/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.macro.Macro3;

public final class Macro3PreTickInvoker
implements EventInvoker {
    final Macro3 Y;

    public Macro3PreTickInvoker(Macro3 var1) {
        this.Y = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.Y.onPreTick((PreTickEvent)var3);
}
}