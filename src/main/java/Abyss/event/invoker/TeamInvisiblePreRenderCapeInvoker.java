/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreRenderCapeEvent;
import Abyss.module.impl.visual.TeamInvisible;

public final class TeamInvisiblePreRenderCapeInvoker
implements EventInvoker {
    final TeamInvisible b;

    public TeamInvisiblePreRenderCapeInvoker(TeamInvisible var1) {
        this.b = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x5D403054C08FL;
        this.b.onPreRenderCape((PreRenderCapeEvent)var3, var4);
}
}