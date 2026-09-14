/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.WorldLoadEvent;
import Abyss.internal.CheaterDetector;

public final class CheaterDetectorWorldLoadInvoker
implements EventInvoker {
    final CheaterDetector E;

    @Override
    public void c(long var1, Object var3) {
        this.E.onWorldLoad((WorldLoadEvent)var3);
}
    public CheaterDetectorWorldLoadInvoker(CheaterDetector var1) {
        this.E = var1;
}
}