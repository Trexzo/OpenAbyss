/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ServerJoinEvent;
import Abyss.ui.screen.ReconnectHandler;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class ReconnectHandlerServerJoinInvoker
implements EventInvoker {
    final ReconnectHandler B;

    public ReconnectHandlerServerJoinInvoker(ReconnectHandler var1) {
        this.B = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0xF44EC5DA6C2L;
        this.B.onServerJoin(var4, (ServerJoinEvent)var3);
}
}