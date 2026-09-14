/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.RightClickMouseEvent;
import Abyss.module.impl.movement.NoSlow;

public final class NoSlowRightClickMouseInvoker
implements EventInvoker {
    final NoSlow U;

    public NoSlowRightClickMouseInvoker(NoSlow var1) {
        this.U = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2F25596513AFL;
        this.U.onRightClickMouse((RightClickMouseEvent)var3, var4);
}
}