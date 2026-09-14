/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostRightClickEvent;
import Abyss.module.impl.world.FastPlace;

public final class FastPlacePostRightClickInvoker
implements EventInvoker {
    final FastPlace J;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x475CEBFC417EL;
        this.J.onPostRightClick((PostRightClickEvent)var3, var4);
}
    public FastPlacePostRightClickInvoker(FastPlace var1) {
        this.J = var1;
}
}