/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.WorldLoadEvent;
import Abyss.util.HypixelGameState;

public final class HypixelGameStateWorldLoadInvoker
implements EventInvoker {
    final HypixelGameState c;

    public HypixelGameStateWorldLoadInvoker(HypixelGameState var1) {
        this.c = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x3A586C71DDCCL;
        this.c.onWorldLoad((WorldLoadEvent)var3, var4);
}
}