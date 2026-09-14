/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ClickMouseEvent;
import Abyss.module.impl.world.BedNuker;

public final class BedNukerClickMouseInvoker
implements EventInvoker {
    final BedNuker t;

    public BedNukerClickMouseInvoker(BedNuker var1) {
        this.t = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2759C36B3BF7L;
        this.t.onClickMouse(var4, (ClickMouseEvent)var3);
}
}