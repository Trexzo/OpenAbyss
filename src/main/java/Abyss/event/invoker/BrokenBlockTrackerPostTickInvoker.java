/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.internal.BrokenBlockTracker;

public final class BrokenBlockTrackerPostTickInvoker
implements EventInvoker {
    final BrokenBlockTracker i;

    public BrokenBlockTrackerPostTickInvoker(BrokenBlockTracker var1) {
        this.i = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x39C3D36A6904L;
        this.i.onPostTick(var4, (PostTickEvent)var3);
}
}