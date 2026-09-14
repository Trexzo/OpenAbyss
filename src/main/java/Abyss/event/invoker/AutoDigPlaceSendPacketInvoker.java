/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SendPacketEvent;
import Abyss.module.impl.world.AutoDigPlace;

public final class AutoDigPlaceSendPacketInvoker
implements EventInvoker {
    final AutoDigPlace w;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x534B4BC573EBL) >>> 32);
        int var5 = (int)((var1 ^ 0x534B4BC573EBL) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x534B4BC573EBL) << 48 >>> 48);
        this.w.onSendPacket(var4, (char)var5, (char)var6, (SendPacketEvent)var3);
}
    public AutoDigPlaceSendPacketInvoker(AutoDigPlace var1) {
        this.w = var1;
}
}