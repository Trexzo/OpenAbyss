/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SendPacketEvent;
import Abyss.module.impl.misc.ContainerKeeper;

public final class ContainerKeeperSendPacketInvoker
implements EventInvoker {
    final ContainerKeeper r;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x29FE5E413884L) >>> 32);
        int var5 = (int)((var1 ^ 0x29FE5E413884L) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x29FE5E413884L) << 48 >>> 48);
        this.r.onSendPacket((SendPacketEvent)var3, var4, (short)var5, var6);
}
    public ContainerKeeperSendPacketInvoker(ContainerKeeper var1) {
        this.r = var1;
}
}