/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostRenderCapeEvent;
import Abyss.module.impl.visual.TeamInvisible;

public final class TeamInvisiblePostRenderCapeInvoker
implements EventInvoker {
    final TeamInvisible Z;

    public TeamInvisiblePostRenderCapeInvoker(TeamInvisible var1) {
        this.Z = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = (var1 ^ 0x16565CAF4862L) >>> 8;
        int var6 = (int)((var1 ^ 0x16565CAF4862L) << 56 >>> 56);
        this.Z.onPostRenderCape(var4, (byte)var6, (PostRenderCapeEvent)var3);
}
}