/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.ItemTagsPostTickInvoker;
import Abyss.event.invoker.ItemTagsRender3DInvoker;
import Abyss.module.impl.visual_utility.ItemTags;

public final class ItemTagsBinder {
    private static final long public static void b(EventBus var0, char var1, short var2, ItemTags var3) {
        var0.R(var3, PostTickEvent.class, 3, new ItemTagsPostTickInvoker(var3));
        var0.R(var3, Render3DEvent.class, 3, new ItemTagsRender3DInvoker(var3));
}
    private ItemTagsBinder() {
}
}