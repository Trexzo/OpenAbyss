/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.ClassNode
 */
package Abyss.ASM.World;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformWorld
extends TransformerBase {
    private static long d = 103346272902059L;

    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        return (var4 |= BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/entity/Entity") + ")Z", (var0, var1x) -> TransformerBase.u(var1x, Type.BOOLEAN_TYPE, false, false, (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.Y(var1xx, R, "World$onEntityJoinWorld", "(" + SrgNames.X("net/minecraft/entity/Entity") + ")V");
        }), "spawnEntityInWorld", "spawnEntityInWorld")) | BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/entity/Entity") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, false, (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.Y(var1xx, R, "World$onEntityJoinWorld", "(" + SrgNames.X("net/minecraft/entity/Entity") + ")V");
        }), "onEntityAdded", "onEntityAdded");
}
    public TransformWorld() {
        super("net/minecraft/world/World");
}
}