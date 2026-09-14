/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostRenderModelBipedEvent;
import Abyss.module.impl.visual.TeamInvisible;

public final class TeamInvisiblePostRenderModelBipedInvoker
implements EventInvoker {
    final TeamInvisible R;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2B5794FC406FL;
        this.R.onPostRenderModelBiped((PostRenderModelBipedEvent)var3, var4);
}
    public TeamInvisiblePostRenderModelBipedInvoker(TeamInvisible var1) {
        this.R = var1;
}
}