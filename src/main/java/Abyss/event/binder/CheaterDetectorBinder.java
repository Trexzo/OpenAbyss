/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.GetDisplayNameEvent;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.event.invoker.CheaterDetectorEntityJoinWorldInvoker;
import Abyss.event.invoker.CheaterDetectorGetDisplayNameInvoker;
import Abyss.event.invoker.CheaterDetectorPlayerGetNameInvoker;
import Abyss.event.invoker.CheaterDetectorPostTickInvoker;
import Abyss.event.invoker.CheaterDetectorReceivePacketInvoker;
import Abyss.event.invoker.CheaterDetectorWorldLoadInvoker;
import Abyss.internal.CheaterDetector;

public final class CheaterDetectorBinder {
    private static final long public static void M(EventBus var0, CheaterDetector var1) {
        var0.R(var1, WorldLoadEvent.class, 3, new CheaterDetectorWorldLoadInvoker(var1));
        var0.R(var1, PostTickEvent.class, 3, new CheaterDetectorPostTickInvoker(var1));
        var0.R(var1, GetDisplayNameEvent.class, 3, new CheaterDetectorGetDisplayNameInvoker(var1));
        var0.R(var1, PlayerGetNameEvent.class, 3, new CheaterDetectorPlayerGetNameInvoker(var1));
        var0.R(var1, EntityJoinWorldEvent.class, 3, new CheaterDetectorEntityJoinWorldInvoker(var1));
        var0.R(var1, ReceivePacketEvent.class, 3, new CheaterDetectorReceivePacketInvoker(var1));
}
    private CheaterDetectorBinder() {
}
}