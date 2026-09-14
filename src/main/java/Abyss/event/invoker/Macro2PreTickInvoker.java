/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.macro.Macro2;

public final class Macro2PreTickInvoker
implements EventInvoker {
    final Macro2 S;

    @Override
    public void c(long var1, Object var3) {
        this.S.onPreTick((PreTickEvent)var3);
}
    public Macro2PreTickInvoker(Macro2 var1) {
        this.S = var1;
}
}