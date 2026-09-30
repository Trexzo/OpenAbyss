/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreSuperLivingUpdateEvent;
import Abyss.module.impl.combat.KeepSprint;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class KeepSprintPreSuperLivingUpdateInvoker
implements EventInvoker {
    final KeepSprint m;

    public KeepSprintPreSuperLivingUpdateInvoker(KeepSprint var1) {
        this.m = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x326FD674B0BDL;
        this.m.onPreSuperLivingUpdate((PreSuperLivingUpdateEvent)var3, var4);
}
}