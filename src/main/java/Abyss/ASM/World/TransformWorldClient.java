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
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformWorldClient
extends TransformerBase {
    private static long d = 32551282002734L;

    public TransformWorldClient() {
        super("net/minecraft/client/multiplayer/WorldClient");
}
    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "(III)V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
            BytecodeHelper.I(var0x, var1xx);
            BytecodeHelper.Y(var0x, T, "onDoVoidFogParticles", "(" + z + ")V");
        }), "doVoidFogParticles", "doVoidFogParticles");
}
}