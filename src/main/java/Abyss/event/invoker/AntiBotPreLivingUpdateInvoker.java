/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.module.impl.misc.AntiBot;

public final class AntiBotPreLivingUpdateInvoker
implements EventInvoker {
    final AntiBot v;

    @Override
    public void c(long var1, Object var3) {
        this.v.onPreLivingUpdate((PreLivingUpdateEvent)var3);
}
    public AntiBotPreLivingUpdateInvoker(AntiBot var1) {
        this.v = var1;
}
}