/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.NameTagsPostTickInvoker;
import Abyss.event.invoker.NameTagsRender3DInvoker;
import Abyss.module.impl.visual_utility.NameTags;

public final class NameTagsBinder {
    private static final long private NameTagsBinder() {
}
    public static void A(EventBus var0, NameTags var1) {
        var0.R(var1, PostTickEvent.class, 3, new NameTagsPostTickInvoker(var1));
        var0.R(var1, Render3DEvent.class, 3, new NameTagsRender3DInvoker(var1));
}
}