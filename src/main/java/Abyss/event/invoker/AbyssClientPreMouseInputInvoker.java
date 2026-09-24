/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.AbyssClient;
import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class AbyssClientPreMouseInputInvoker
implements EventInvoker {
    final AbyssClient q;

    public AbyssClientPreMouseInputInvoker(AbyssClient var1) {
        this.q = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x631EFDB0266L;
        this.q.onPreMouseInput(var4, (PreMouseInputEvent)var3);
}
}