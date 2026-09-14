/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.combat.SprintReset;

public final class SprintResetPostTickInvoker
implements EventInvoker {
    final SprintReset x;

    public SprintResetPostTickInvoker(SprintReset var1) {
        this.x = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.x.onPostTick((PostTickEvent)var3);
}
}