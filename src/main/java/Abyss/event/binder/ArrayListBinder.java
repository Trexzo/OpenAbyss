/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.ArrayListPostTickInvoker;
import Abyss.event.invoker.ArrayListRender2DInvoker;
import Abyss.module.impl.visual.ArrayList;

public final class ArrayListBinder {
    private static final long public static void l(EventBus var2, ArrayList var3) {
        var2.R(var3, PostTickEvent.class, 3, new ArrayListPostTickInvoker(var3));
        var2.R(var3, Render2DEvent.class, 3, new ArrayListRender2DInvoker(var3));
}
    private ArrayListBinder() {
}
}