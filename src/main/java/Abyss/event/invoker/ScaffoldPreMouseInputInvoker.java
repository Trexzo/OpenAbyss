/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.world.Scaffold;
import java.io.UnsupportedEncodingException;

public final class ScaffoldPreMouseInputInvoker
implements EventInvoker {
    final Scaffold R;

    public ScaffoldPreMouseInputInvoker(Scaffold var1) {
        this.R = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x1B6833E6839FL;
        this.R.onPreMouseInput(var4, (PreMouseInputEvent)var3);
}
}