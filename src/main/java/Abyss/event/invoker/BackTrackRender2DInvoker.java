/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.combat.BackTrack;

public final class BackTrackRender2DInvoker
implements EventInvoker {
    final BackTrack O;

    public BackTrackRender2DInvoker(BackTrack var1) {
        this.O = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x1821A5DE4709L) >>> 48);
        int var5 = (int)((var1 ^ 0x1821A5DE4709L) << 16 >>> 32);
        int var6 = (int)((var1 ^ 0x1821A5DE4709L) << 48 >>> 48);
        this.O.onRender2D((char)var4, var5, (Render2DEvent)var3, (char)var6);
}
}