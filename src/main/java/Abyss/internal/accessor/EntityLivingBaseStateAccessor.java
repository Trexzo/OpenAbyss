/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.FieldAccessors;
import Abyss.internal.accessor.TypedValueStore;
import java.io.UnsupportedEncodingException;
import net.minecraft.entity.EntityLivingBase;

public final class EntityLivingBaseStateAccessor {
    private static TypedValueStore P = FieldAccessors.X(EntityLivingBase.class, "jumpTicks", "jumpTicks");

    public static void x(int var0, EntityLivingBase var2, int var3) {
        P.T(var2, var3);
}
    public static int C(EntityLivingBase var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return P.m(var2);
}
}