/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.combat.AimAssist;
import java.io.UnsupportedEncodingException;

public final class AimAssistPostTickInvoker
implements EventInvoker {
    final AimAssist P;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x397E85229505L;
        this.P.onPostTick(var4, (PostTickEvent)var3);
}
    public AimAssistPostTickInvoker(AimAssist var1) {
        this.P = var1;
}
}