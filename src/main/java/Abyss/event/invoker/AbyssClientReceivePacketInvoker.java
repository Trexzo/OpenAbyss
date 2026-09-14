/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.AbyssClient;
import Abyss.event.EventInvoker;
import Abyss.event.events.ReceivePacketEvent;
import java.io.UnsupportedEncodingException;

public final class AbyssClientReceivePacketInvoker
implements EventInvoker {
    final AbyssClient q;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x6FA305E87E43L;
        this.q.onReceivePacket((ReceivePacketEvent)var3, var4);
}
    public AbyssClientReceivePacketInvoker(AbyssClient var1) {
        this.q = var1;
}
}