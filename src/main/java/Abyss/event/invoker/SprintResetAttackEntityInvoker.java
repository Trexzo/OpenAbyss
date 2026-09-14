/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.AttackEntityEvent;
import Abyss.module.impl.combat.SprintReset;
import java.io.UnsupportedEncodingException;

public final class SprintResetAttackEntityInvoker
implements EventInvoker {
    final SprintReset s;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.s.onAttackEntity((AttackEntityEvent)var3);
}
    public SprintResetAttackEntityInvoker(SprintReset var1) {
        this.s = var1;
}
}