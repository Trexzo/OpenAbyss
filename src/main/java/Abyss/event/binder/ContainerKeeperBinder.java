/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.event.invoker.ContainerKeeperPreUpdateInvoker;
import Abyss.event.invoker.ContainerKeeperReceivePacketInvoker;
import Abyss.event.invoker.ContainerKeeperRender2DInvoker;
import Abyss.event.invoker.ContainerKeeperSendPacketInvoker;
import Abyss.event.invoker.ContainerKeeperWorldLoadInvoker;
import Abyss.module.impl.misc.ContainerKeeper;

public final class ContainerKeeperBinder {
    private ContainerKeeperBinder() {
}
    public static void Q(EventBus var0, int var1, ContainerKeeper var2) {
        var0.R(var2, WorldLoadEvent.class, 3, new ContainerKeeperWorldLoadInvoker(var2));
        var0.R(var2, PreUpdateEvent.class, 3, new ContainerKeeperPreUpdateInvoker(var2));
        var0.R(var2, SendPacketEvent.class, 3, new ContainerKeeperSendPacketInvoker(var2));
        var0.R(var2, ReceivePacketEvent.class, 3, new ContainerKeeperReceivePacketInvoker(var2));
        var0.R(var2, Render2DEvent.class, 3, new ContainerKeeperRender2DInvoker(var2));
}
}