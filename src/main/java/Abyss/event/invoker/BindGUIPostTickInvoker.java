/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual.BindGUI;
import java.io.UnsupportedEncodingException;

public final class BindGUIPostTickInvoker
implements EventInvoker {
    final BindGUI t;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x50502A0F3014L;
        this.t.onPostTick(var4, (PostTickEvent)var3);
}
    public BindGUIPostTickInvoker(BindGUI var1) {
        this.t = var1;
}
}