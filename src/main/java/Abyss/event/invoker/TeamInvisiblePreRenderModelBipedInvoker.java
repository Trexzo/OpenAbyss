/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreRenderModelBipedEvent;
import Abyss.module.impl.visual.TeamInvisible;

public final class TeamInvisiblePreRenderModelBipedInvoker
implements EventInvoker {
    final TeamInvisible c;

    public TeamInvisiblePreRenderModelBipedInvoker(TeamInvisible var1) {
        this.c = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x58E9EB0CD10EL;
        this.c.onPreRenderModelBiped(var4, (PreRenderModelBipedEvent)var3);
}
}