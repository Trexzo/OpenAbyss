/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.invoker.MainMenuThemePostTickInvoker;
import Abyss.event.invoker.MainMenuThemePreTickInvoker;
import Abyss.ui.screen.MainMenuTheme;

public final class MainMenuThemeBinder {
    private MainMenuThemeBinder() {
}
    public static void D(EventBus var0, MainMenuTheme var1) {
        var0.R(var1, PreTickEvent.class, 3, new MainMenuThemePreTickInvoker(var1));
        var0.R(var1, PostTickEvent.class, 3, new MainMenuThemePostTickInvoker(var1));
}
}