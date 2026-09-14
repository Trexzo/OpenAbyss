/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.world.AutoDigPlace;

public final class AutoDigPlacePreMouseInputInvoker
implements EventInvoker {
    final AutoDigPlace v;

    public AutoDigPlacePreMouseInputInvoker(AutoDigPlace var1) {
        this.v = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x288550387C2AL;
        this.v.onPreMouseInput((PreMouseInputEvent)var3, var4);
}
}