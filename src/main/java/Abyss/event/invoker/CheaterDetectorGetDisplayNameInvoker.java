/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.GetDisplayNameEvent;
import Abyss.internal.CheaterDetector;
import java.io.UnsupportedEncodingException;

public final class CheaterDetectorGetDisplayNameInvoker
implements EventInvoker {
    final CheaterDetector s;

    public CheaterDetectorGetDisplayNameInvoker(CheaterDetector var1) {
        this.s = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x4401026DA6F3L;
        this.s.onGetDisplayName((GetDisplayNameEvent)var3, var4);
}
}