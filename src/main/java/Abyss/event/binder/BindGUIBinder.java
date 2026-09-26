/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.BindGUIPostTickInvoker;
import Abyss.event.invoker.BindGUIRender2DInvoker;
import Abyss.module.impl.visual.BindGUI;

public final class BindGUIBinder {
    public static void J(EventBus var2, BindGUI var3) {
        var2.R(var3, PostTickEvent.class, 3, new BindGUIPostTickInvoker(var3));
        var2.R(var3, Render2DEvent.class, 3, new BindGUIRender2DInvoker(var3));
}
    private BindGUIBinder() {
}
}