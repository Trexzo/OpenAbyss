/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.impl.combat.SprintReset;

public final class SprintResetReceivePacketInvoker
implements EventInvoker {
    final SprintReset I;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x7678B680BD7DL) >>> 48);
        int var5 = (int)((var1 ^ 0x7678B680BD7DL) << 16 >>> 32);
        int var6 = (int)((var1 ^ 0x7678B680BD7DL) << 48 >>> 48);
        this.I.onReceivePacket((short)var4, var5, (ReceivePacketEvent)var3, (char)var6);
}
    public SprintResetReceivePacketInvoker(SprintReset var1) {
        this.I = var1;
}
}