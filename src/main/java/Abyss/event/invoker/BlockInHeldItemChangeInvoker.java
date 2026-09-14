/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.module.impl.world.BlockIn;

public final class BlockInHeldItemChangeInvoker
implements EventInvoker {
    final BlockIn v;

    public BlockInHeldItemChangeInvoker(BlockIn var1) {
        this.v = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2DEC1E828A2CL;
        this.v.onHeldItemChange(var4, (HeldItemChangeEvent)var3);
}
}