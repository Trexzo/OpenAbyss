/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.module.impl.visual_utility.MegaWallsDetector;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class MegaWallsDetectorPlayerGetNameInvoker
implements EventInvoker {
    final MegaWallsDetector G;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = (var1 ^ 0x4494BFF0FE0AL) >>> 16;
        int var6 = (int)((var1 ^ 0x4494BFF0FE0AL) << 48 >>> 48);
        this.G.onPlayerGetName((PlayerGetNameEvent)var3, var4, (short)var6);
}
    public MegaWallsDetectorPlayerGetNameInvoker(MegaWallsDetector var1) {
        this.G = var1;
}
}