/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.module.impl.world.Nuker;

public final class NukerHeldItemChangeInvoker
implements EventInvoker {
    final Nuker D;

    public NukerHeldItemChangeInvoker(Nuker var1) {
        this.D = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0xA836897226EL) >>> 32);
        int var5 = (int)((var1 ^ 0xA836897226EL) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0xA836897226EL) << 48 >>> 48);
        this.D.onHeldItemChange(var4, var5, (char)var6, (HeldItemChangeEvent)var3);
}
}