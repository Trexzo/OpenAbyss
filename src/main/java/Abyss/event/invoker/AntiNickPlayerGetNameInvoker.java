/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.module.impl.misc.AntiNick;

public final class AntiNickPlayerGetNameInvoker
implements EventInvoker {
    final AntiNick G;

    @Override
    public void c(long var1, Object var3) {
        this.G.onPlayerGetName((PlayerGetNameEvent)var3);
}
    public AntiNickPlayerGetNameInvoker(AntiNick var1) {
        this.G = var1;
}
}