/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.AbyssClient;
import Abyss.event.EventBus;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.event.invoker.AbyssClientEntityJoinWorldInvoker;
import Abyss.event.invoker.AbyssClientPostTickInvoker;
import Abyss.event.invoker.AbyssClientPreMouseInputInvoker;
import Abyss.event.invoker.AbyssClientReceivePacketInvoker;
import Abyss.event.invoker.AbyssClientSetKeyBindStateInvoker;
import Abyss.event.invoker.j_2;
import Abyss.event.invoker.oX;

public final class AbyssClientBinder {
    private static final long private AbyssClientBinder() {
}
    public static void C(EventBus var2, AbyssClient var3) {
        var2.R(var3, SetKeyBindStateEvent.class, 3, new AbyssClientSetKeyBindStateInvoker(var3));
        var2.R(var3, PostTickEvent.class, 3, new AbyssClientPostTickInvoker(var3));
        var2.R(var3, PreUpdateEvent.class, 3, new oX(var3));
        var2.R(var3, PreMouseInputEvent.class, 3, new AbyssClientPreMouseInputInvoker(var3));
        var2.R(var3, PreUpdateEvent.class, 3, new j_2(var3));
        var2.R(var3, EntityJoinWorldEvent.class, 3, new AbyssClientEntityJoinWorldInvoker(var3));
        var2.R(var3, ReceivePacketEvent.class, 3, new AbyssClientReceivePacketInvoker(var3));
}
}