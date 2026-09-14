/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.util.HypixelGameState;

public final class HypixelGameStatePostTickInvoker
implements EventInvoker {
    final HypixelGameState i;

    public HypixelGameStatePostTickInvoker(HypixelGameState var1) {
        this.i = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x6305B1F165B8L;
        this.i.onPostTick(var4, (PostTickEvent)var3);
}
}