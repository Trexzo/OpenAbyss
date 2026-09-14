/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.combat.WTap;

public final class WTapPreMouseInputInvoker
implements EventInvoker {
    final WTap S;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x208B055477D3L;
        this.S.onPreMouseInput(var4, (PreMouseInputEvent)var3);
}
    public WTapPreMouseInputInvoker(WTap var1) {
        this.S = var1;
}
}