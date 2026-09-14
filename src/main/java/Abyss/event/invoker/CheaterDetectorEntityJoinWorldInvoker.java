/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.internal.CheaterDetector;

public final class CheaterDetectorEntityJoinWorldInvoker
implements EventInvoker {
    final CheaterDetector W;

    public CheaterDetectorEntityJoinWorldInvoker(CheaterDetector var1) {
        this.W = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.W.onEntityJoinWorld((EntityJoinWorldEvent)var3);
}
}