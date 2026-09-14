/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.WorldLoadEvent;
import Abyss.module.impl.combat.Velocity;

public final class VelocityWorldLoadInvoker
implements EventInvoker {
    final Velocity O;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x44B6BCDA7608L;
        this.O.onWorldLoad((WorldLoadEvent)var3, var4);
}
    public VelocityWorldLoadInvoker(Velocity var1) {
        this.O = var1;
}
}