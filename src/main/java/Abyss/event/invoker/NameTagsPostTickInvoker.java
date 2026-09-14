/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual_utility.NameTags;

public final class NameTagsPostTickInvoker
implements EventInvoker {
    final NameTags p;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x5CD80E578A0CL;
        this.p.onPostTick((PostTickEvent)var3, var4);
}
    public NameTagsPostTickInvoker(NameTags var1) {
        this.p = var1;
}
}