/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.combat.KeepSprint;
import java.io.UnsupportedEncodingException;

public final class KeepSprintPreUpdateInvoker
implements EventInvoker {
    final KeepSprint N;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x431C3ED29CABL;
        this.N.onPreUpdate((PreUpdateEvent)var3, var4);
}
    public KeepSprintPreUpdateInvoker(KeepSprint var1) {
        this.N = var1;
}
}