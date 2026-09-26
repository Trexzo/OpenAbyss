/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.module.impl.world.AntiVoid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class AntiVoidUpdateWalkingPlayerInvoker
implements EventInvoker {
    final AntiVoid U;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x5D70085EB084L;
        this.U.onUpdateWalkingPlayer((UpdateWalkingPlayerEvent)var3, var4);
}
    public AntiVoidUpdateWalkingPlayerInvoker(AntiVoid var1) {
        this.U = var1;
}
}