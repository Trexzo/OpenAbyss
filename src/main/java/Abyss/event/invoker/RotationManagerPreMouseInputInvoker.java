/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.util.RotationManager;

public final class RotationManagerPreMouseInputInvoker
implements EventInvoker {
    final RotationManager d;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x8732EC8F2BL;
        this.d.onPreMouseInput(var4, (PreMouseInputEvent)var3);
}
    public RotationManagerPreMouseInputInvoker(RotationManager var1) {
        this.d = var1;
}
}