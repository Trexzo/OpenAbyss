/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PickUpItemEvent;
import Abyss.module.impl.player.InvManager;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class InvManagerPickUpItemInvoker
implements EventInvoker {
    final InvManager q;

    public InvManagerPickUpItemInvoker(InvManager var1) {
        this.q = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x5F1CA8A3EA9EL;
        this.q.onPickUpItem((PickUpItemEvent)var3, var4);
}
}