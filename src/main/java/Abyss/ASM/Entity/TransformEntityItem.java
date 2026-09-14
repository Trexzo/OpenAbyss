/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.ClassNode
 */
package Abyss.ASM.Entity;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformEntityItem
extends TransformerBase {
    private static long d = 101780087572982L;

    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/entity/player/EntityPlayer") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, false, (var1xx, var2xx) -> {
            BytecodeHelper.k(var1xx);
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.Y(var1xx, m, "EntityItem$onPickUpItem", "(" + SrgNames.X("net/minecraft/entity/item/EntityItem") + SrgNames.X("net/minecraft/entity/player/EntityPlayer") + ")V");
        }), "onCollideWithPlayer", "onCollideWithPlayer");
}
    public TransformEntityItem() {
        super("net/minecraft/entity/item/EntityItem");
}
}