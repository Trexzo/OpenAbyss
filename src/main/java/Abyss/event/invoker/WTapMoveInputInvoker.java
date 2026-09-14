/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.MoveInputEvent;
import Abyss.module.impl.combat.WTap;

public final class WTapMoveInputInvoker
implements EventInvoker {
    final WTap M;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x408DEFD9D9CCL;
        this.M.onMoveInput((MoveInputEvent)var3, var4);
}
    public WTapMoveInputInvoker(WTap var1) {
        this.M = var1;
}
}