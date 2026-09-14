/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.internal.CheaterDetector;

public final class CheaterDetectorPlayerGetNameInvoker
implements EventInvoker {
    final CheaterDetector G;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x7BC54023A1F4L) >>> 32);
        int var5 = (int)((var1 ^ 0x7BC54023A1F4L) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x7BC54023A1F4L) << 48 >>> 48);
        this.G.onPlayerGetName(var4, (PlayerGetNameEvent)var3, (short)var5, var6);
}
    public CheaterDetectorPlayerGetNameInvoker(CheaterDetector var1) {
        this.G = var1;
}
}