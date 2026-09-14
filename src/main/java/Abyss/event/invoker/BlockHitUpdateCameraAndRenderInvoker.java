/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.UpdateCameraAndRenderEvent;
import Abyss.module.impl.combat.BlockHit;

public final class BlockHitUpdateCameraAndRenderInvoker
implements EventInvoker {
    final BlockHit W;

    public BlockHitUpdateCameraAndRenderInvoker(BlockHit var1) {
        this.W = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x54AB629AB6ECL;
        this.W.onUpdateCameraAndRender(var4, (UpdateCameraAndRenderEvent)var3);
}
}