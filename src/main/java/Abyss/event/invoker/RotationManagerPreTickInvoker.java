/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.util.RotationManager;

public final class RotationManagerPreTickInvoker
implements EventInvoker {
    final RotationManager P;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x33BA3283AEDBL;
        this.P.onPreTick((PreTickEvent)var3, var4);
}
    public RotationManagerPreTickInvoker(RotationManager var1) {
        this.P = var1;
}
}