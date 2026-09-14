/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.projectile.EntityArrow
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.FieldAccessors;
import Abyss.internal.accessor.TypedValueStore;
import net.minecraft.entity.projectile.EntityArrow;

public final class EntityArrowAccessor {
    private static TypedValueStore U = FieldAccessors.X(EntityArrow.class, "inGround", "inGround");

    public static boolean E(long var0, EntityArrow var2) {
        return U.n(var2);
}
}