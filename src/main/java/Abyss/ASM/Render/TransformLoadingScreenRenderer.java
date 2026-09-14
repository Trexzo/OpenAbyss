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
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformLoadingScreenRenderer
extends TransformerBase {
    private static long d = 133475593182422L;

    public TransformLoadingScreenRenderer() {
        super("net/minecraft/client/LoadingScreenRenderer");
}
    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "(I)V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, R, "LoadingScreenRenderer$forSkipProgress", "(I" + z + ")V");
        }), "setLoadingProgress", "setLoadingProgress");
}
}