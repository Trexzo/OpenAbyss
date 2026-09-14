/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreDrawScreenEvent;
import Abyss.ui.screen.ReconnectHandler;
import java.io.UnsupportedEncodingException;

public final class ReconnectHandlerPreDrawScreenInvoker
implements EventInvoker {
    final ReconnectHandler K;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x140542938A1CL;
        this.K.onPreDrawScreen((PreDrawScreenEvent)var3, var4);
}
    public ReconnectHandlerPreDrawScreenInvoker(ReconnectHandler var1) {
        this.K = var1;
}
}