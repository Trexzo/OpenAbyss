/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.module.impl.visual.Ambience;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class AmbienceUpdateWalkingPlayerInvoker
implements EventInvoker {
    final Ambience S;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x13E4BA2C6F96L;
        this.S.onUpdateWalkingPlayer(var4, (UpdateWalkingPlayerEvent)var3);
}
    public AmbienceUpdateWalkingPlayerInvoker(Ambience var1) {
        this.S = var1;
}
}