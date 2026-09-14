/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.ClassNode
 */
package Abyss.ASM.Render;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformEffectRenderer
extends TransformerBase {
    private static long d = 72291789126318L;

    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        return (var4 |= BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/util/BlockPos") + SrgNames.X("net/minecraft/util/EnumFacing") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
            BytecodeHelper.I(var0x, var1xx);
            BytecodeHelper.Y(var0x, b, "cancelHitParticles", "(" + z + ")V");
        }), "addBlockHitEffects", "addBlockHitEffects")) | BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/util/BlockPos") + SrgNames.X("net/minecraft/block/state/IBlockState") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
            BytecodeHelper.I(var0x, var1xx);
            BytecodeHelper.Y(var0x, b, "cancelDestroyParticles", "(" + z + ")V");
        }), "addBlockDestroyEffects", "addBlockDestroyEffects");
}
    public TransformEffectRenderer() {
        super("net/minecraft/client/particle/EffectRenderer");
}
}