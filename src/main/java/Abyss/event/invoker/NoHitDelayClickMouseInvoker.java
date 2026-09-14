/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ClickMouseEvent;
import Abyss.module.impl.player.NoHitDelay;

public final class NoHitDelayClickMouseInvoker
implements EventInvoker {
    final NoHitDelay l;

    public NoHitDelayClickMouseInvoker(NoHitDelay var1) {
        this.l = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.l.onClickMouse((ClickMouseEvent)var3);
}
}