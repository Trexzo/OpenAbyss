/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ClickBlockReturnEvent;
import Abyss.internal.BrokenBlockTracker;

public final class BrokenBlockTrackerClickBlockReturnInvoker
implements EventInvoker {
    final BrokenBlockTracker Z;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x680599392626L;
        this.Z.onClickBlockReturn((ClickBlockReturnEvent)var3, var4);
}
    public BrokenBlockTrackerClickBlockReturnInvoker(BrokenBlockTracker var1) {
        this.Z = var1;
}
}