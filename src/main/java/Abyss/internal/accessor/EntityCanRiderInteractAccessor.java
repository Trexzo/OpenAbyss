/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import net.minecraft.entity.Entity;

public final class EntityCanRiderInteractAccessor {
    private static Accessor H = MethodAccessors.G(Entity.class, "func_184228_n", "canRiderInteract", new Class[0]);

    private EntityCanRiderInteractAccessor() {
}
    public static Accessor C() {
        return H;
}
}