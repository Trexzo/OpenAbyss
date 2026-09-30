/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.module.impl.visual_utility.ChestESP;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class ChestESPPlayerRightClickInvoker
implements EventInvoker {
    final ChestESP e;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.e.onPlayerRightClick((PlayerRightClickEvent)var3);
}
    public ChestESPPlayerRightClickInvoker(ChestESP var1) {
        this.e = var1;
}
}