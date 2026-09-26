/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SetAnglesEvent;
import Abyss.module.impl.combat.KillAura;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class KillAuraSetAnglesInvoker
implements EventInvoker {
    final KillAura K;

    public KillAuraSetAnglesInvoker(KillAura var1) {
        this.K = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x62704A358D85L;
        this.K.onSetAngles(var4, (SetAnglesEvent)var3);
}
}