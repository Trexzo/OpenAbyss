/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.MoveFlyingEvent;
import Abyss.module.impl.movement.Fly;

public final class FlyMoveFlyingInvoker
implements EventInvoker {
    final Fly x;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x59F1DCC51CL) >>> 48);
        int var5 = (int)((var1 ^ 0x59F1DCC51CL) << 16 >>> 32);
        int var6 = (int)((var1 ^ 0x59F1DCC51CL) << 48 >>> 48);
        this.x.onMoveFlying((char)var4, var5, (short)var6, (MoveFlyingEvent)var3);
}
    public FlyMoveFlyingInvoker(Fly var1) {
        this.x = var1;
}
}