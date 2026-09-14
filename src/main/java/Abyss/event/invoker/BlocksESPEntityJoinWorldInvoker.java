/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.module.impl.visual_utility.BlocksESP;

public final class BlocksESPEntityJoinWorldInvoker
implements EventInvoker {
    final BlocksESP W;

    public BlocksESPEntityJoinWorldInvoker(BlocksESP var1) {
        this.W = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.W.onEntityJoinWorld((EntityJoinWorldEvent)var3);
}
}