/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual_utility.MegaWallsDetector;
import java.io.UnsupportedEncodingException;

public final class MegaWallsDetectorPostTickInvoker
implements EventInvoker {
    final MegaWallsDetector A;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x4E1FB18D0DF0L;
        this.A.onPostTick(var4, (PostTickEvent)var3);
}
    public MegaWallsDetectorPostTickInvoker(MegaWallsDetector var1) {
        this.A = var1;
}
}