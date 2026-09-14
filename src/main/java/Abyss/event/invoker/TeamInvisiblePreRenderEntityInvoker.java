/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreRenderEntityEvent;
import Abyss.module.impl.visual.TeamInvisible;

public final class TeamInvisiblePreRenderEntityInvoker
implements EventInvoker {
    final TeamInvisible Q;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x67BC37BC7961L;
        this.Q.onPreRenderEntity((PreRenderEntityEvent)var3, var4);
}
    public TeamInvisiblePreRenderEntityInvoker(TeamInvisible var1) {
        this.Q = var1;
}
}