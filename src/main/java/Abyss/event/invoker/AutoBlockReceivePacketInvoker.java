/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.impl.combat.AutoBlock;

public final class AutoBlockReceivePacketInvoker
implements EventInvoker {
    final AutoBlock c;

    public AutoBlockReceivePacketInvoker(AutoBlock var1) {
        this.c = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x485E15D02205L;
        this.c.onReceivePacket((ReceivePacketEvent)var3, var4);
}
}