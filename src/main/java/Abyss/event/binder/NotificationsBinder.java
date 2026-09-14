/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.NotificationsRender2DInvoker;
import Abyss.module.impl.configuration.Notifications;

public final class NotificationsBinder {
    private static final long public static void r(EventBus var0, Notifications var3) {
        var0.R(var3, Render2DEvent.class, 3, new NotificationsRender2DInvoker(var3));
}
    private NotificationsBinder() {
}
}