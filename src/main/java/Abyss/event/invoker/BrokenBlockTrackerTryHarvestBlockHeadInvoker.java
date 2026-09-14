/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.TryHarvestBlockHeadEvent;
import Abyss.internal.BrokenBlockTracker;

public final class BrokenBlockTrackerTryHarvestBlockHeadInvoker
implements EventInvoker {
    final BrokenBlockTracker u;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x5D36D68EAC50L;
        this.u.onTryHarvestBlockHead((TryHarvestBlockHeadEvent)var3, var4);
}
    public BrokenBlockTrackerTryHarvestBlockHeadInvoker(BrokenBlockTracker var1) {
        this.u = var1;
}
}