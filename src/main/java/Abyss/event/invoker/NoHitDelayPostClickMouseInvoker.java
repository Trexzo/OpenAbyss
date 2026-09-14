/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostClickMouseEvent;
import Abyss.module.impl.player.NoHitDelay;

public final class NoHitDelayPostClickMouseInvoker
implements EventInvoker {
    final NoHitDelay p;

    public NoHitDelayPostClickMouseInvoker(NoHitDelay var1) {
        this.p = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.p.onPostClickMouse((PostClickMouseEvent)var3);
}
}