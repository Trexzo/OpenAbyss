/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.impl.visual_utility.LeapModeHUD;
import java.io.UnsupportedEncodingException;

public final class LeapModeHUDReceivePacketInvoker
implements EventInvoker {
    final LeapModeHUD l;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x49453287627L;
        this.l.onReceivePacket(var4, (ReceivePacketEvent)var3);
}
    public LeapModeHUDReceivePacketInvoker(LeapModeHUD var1) {
        this.l = var1;
}
}