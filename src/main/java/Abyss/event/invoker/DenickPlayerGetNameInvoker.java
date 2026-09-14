/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.module.impl.misc.Denick;
import java.io.UnsupportedEncodingException;

public final class DenickPlayerGetNameInvoker
implements EventInvoker {
    final Denick N;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x494E8E6886EEL;
        this.N.onPlayerGetName(var4, (PlayerGetNameEvent)var3);
}
    public DenickPlayerGetNameInvoker(Denick var1) {
        this.N = var1;
}
}