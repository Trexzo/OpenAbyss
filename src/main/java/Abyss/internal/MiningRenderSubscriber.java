/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.MiningRenderSubscriberBinder;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.MiningEngine;

public class MiningRenderSubscriber
implements EventSubscriber {
    @Override
    public final void x(long var1, EventBus var3) {
        MiningRenderSubscriberBinder.n(var3, this);
}
    public void onRender3D(long var1, Render3DEvent var3) {
        var1 = 0x665A437245E9L ^ var1;
        long var4 = var1 ^ 0x3B133D6584BCL;
        MiningEngine.uq.v(var4);
}
    private static void a() {
}
    static {
        MiningRenderSubscriber.a();
}
}