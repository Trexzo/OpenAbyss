/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.player.ChestAura;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class ChestAuraPreMouseInputInvoker
implements EventInvoker {
    final ChestAura V;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x5F9DE98A8C41L;
        this.V.onPreMouseInput(var4, (PreMouseInputEvent)var3);
}
    public ChestAuraPreMouseInputInvoker(ChestAura var1) {
        this.V = var1;
}
}