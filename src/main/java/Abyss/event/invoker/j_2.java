/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.AbyssClient;
import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import java.io.UnsupportedEncodingException;

public final class j_2
implements EventInvoker {
    final AbyssClient C;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x65801DF0251EL;
        this.C.d(var4, (PreUpdateEvent)var3);
}
    public j_2(AbyssClient var1) {
        this.C = var1;
}
}