/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.AbyssClient;
import Abyss.event.EventInvoker;
import Abyss.event.events.SetKeyBindStateEvent;
import java.io.UnsupportedEncodingException;

public final class AbyssClientSetKeyBindStateInvoker
implements EventInvoker {
    final AbyssClient W;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x546B58C270B8L;
        this.W.onSetKeyBindState((SetKeyBindStateEvent)var3, var4);
}
    public AbyssClientSetKeyBindStateInvoker(AbyssClient var1) {
        this.W = var1;
}
}