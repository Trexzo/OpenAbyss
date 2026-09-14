/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.WorldLoadEvent;
import Abyss.module.impl.combat.AntiFireball;

public final class AntiFireballWorldLoadInvoker
implements EventInvoker {
    final AntiFireball w;

    public AntiFireballWorldLoadInvoker(AntiFireball var1) {
        this.w = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.w.onWorldLoad((WorldLoadEvent)var3);
}
}