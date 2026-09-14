/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.impl.combat.Velocity;

public final class VelocityReceivePacketInvoker
implements EventInvoker {
    final Velocity n;

    public VelocityReceivePacketInvoker(Velocity var1) {
        this.n = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x248AE1142AB0L) >>> 32);
        int var5 = (int)((var1 ^ 0x248AE1142AB0L) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x248AE1142AB0L) << 48 >>> 48);
        this.n.onReceivePacket(var4, (char)var5, var6, (ReceivePacketEvent)var3);
}
}