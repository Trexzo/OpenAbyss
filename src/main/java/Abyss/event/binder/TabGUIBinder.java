/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.event.invoker.TabGUIRender2DInvoker;
import Abyss.event.invoker.TabGUISetKeyBindStateInvoker;
import Abyss.module.impl.visual.TabGUI;

public final class TabGUIBinder {
    public static void z(EventBus var0, TabGUI var1) {
        var0.R(var1, Render2DEvent.class, 3, new TabGUIRender2DInvoker(var1));
        var0.R(var1, SetKeyBindStateEvent.class, 3, new TabGUISetKeyBindStateInvoker(var1));
}
    private TabGUIBinder() {
}
}