/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.ModuleTagEvent;
import Abyss.module.impl.combat.AutoBlock;
import java.io.UnsupportedEncodingException;

public final class AutoBlockModuleTagInvoker
implements EventInvoker {
    final AutoBlock t;

    public AutoBlockModuleTagInvoker(AutoBlock var1) {
        this.t = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x4C30CDE10044L;
        this.t.onModuleTag((ModuleTagEvent)var3, var4);
}
}