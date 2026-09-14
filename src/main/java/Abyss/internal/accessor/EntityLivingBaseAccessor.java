/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import net.minecraft.entity.EntityLivingBase;

public final class EntityLivingBaseAccessor {
    private static Accessor f = MethodAccessors.C(EntityLivingBase.class, new Class[0], new String[]{"getJumpUpwardsMotion", "getJumpUpwardsMotion"});

    public static float e(EntityLivingBase var0) {
        return Accessor.t(f, new Object[]{var0});
}
}