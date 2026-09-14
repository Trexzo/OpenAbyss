/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.AbyssClient;
import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import java.io.UnsupportedEncodingException;

public final class oX
implements EventInvoker {
    final AbyssClient h;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x2A49DDE66A5FL;
        this.h.onPreUpdate(var4, (PreUpdateEvent)var3);
}
    public oX(AbyssClient var1) {
        this.h = var1;
}
}