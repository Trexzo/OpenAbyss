/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.combat.BackTrack;

public final class BackTrackPreUpdateInvoker
implements EventInvoker {
    final BackTrack O;

    public BackTrackPreUpdateInvoker(BackTrack var1) {
        this.O = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x7FDC312F7A65L) >>> 48);
        int var5 = (int)((var1 ^ 0x7FDC312F7A65L) << 16 >>> 32);
        int var6 = (int)((var1 ^ 0x7FDC312F7A65L) << 48 >>> 48);
        this.O.onPreUpdate((char)var4, var5, (PreUpdateEvent)var3, (short)var6);
}
}