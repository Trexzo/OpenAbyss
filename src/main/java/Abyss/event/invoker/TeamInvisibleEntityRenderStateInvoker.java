/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.EntityRenderStateEvent;
import Abyss.module.impl.visual.TeamInvisible;

public final class TeamInvisibleEntityRenderStateInvoker
implements EventInvoker {
    final TeamInvisible o;

    public TeamInvisibleEntityRenderStateInvoker(TeamInvisible var1) {
        this.o = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x6930BC60BBB5L) >>> 48);
        int var5 = (int)((var1 ^ 0x6930BC60BBB5L) << 16 >>> 32);
        int var6 = (int)((var1 ^ 0x6930BC60BBB5L) << 48 >>> 48);
        this.o.onEntityRenderState((char)var4, var5, (EntityRenderStateEvent)var3, var6);
}
}