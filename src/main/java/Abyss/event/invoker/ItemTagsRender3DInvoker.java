/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render3DEvent;
import Abyss.module.impl.visual_utility.ItemTags;

public final class ItemTagsRender3DInvoker
implements EventInvoker {
    final ItemTags Y;

    public ItemTagsRender3DInvoker(ItemTags var1) {
        this.Y = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x155132B295F8L;
        this.Y.onRender3D(var4, (Render3DEvent)var3);
}
}