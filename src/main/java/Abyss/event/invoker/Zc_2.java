/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.LivingDeathEvent;
import Abyss.module.impl.visual.KillEffect;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class Zc_2
implements EventInvoker {
    final KillEffect M;

    public Zc_2(KillEffect var1) {
        this.M = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x64DEBE2F9820L;
        this.M.t((LivingDeathEvent)var3, var4);
}
}