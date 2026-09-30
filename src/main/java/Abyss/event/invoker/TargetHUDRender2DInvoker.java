/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual_utility.TargetHUD;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class TargetHUDRender2DInvoker
implements EventInvoker {
    final TargetHUD R;

    public TargetHUDRender2DInvoker(TargetHUD var1) {
        this.R = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = (var1 ^ 0x2CC5E58246C5L) >>> 16;
        int var6 = (int)((var1 ^ 0x2CC5E58246C5L) << 48 >>> 48);
        this.R.onRender2D(var4, (Render2DEvent)var3, (short)var6);
}
}