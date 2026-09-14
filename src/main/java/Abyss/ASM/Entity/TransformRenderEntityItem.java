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

public class TransformRenderEntityItem
extends TransformerBase {
    private static long d = 99512118562804L;

    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/entity/item/EntityItem") + "DDDF" + SrgNames.X("net/minecraft/client/resources/model/IBakedModel") + ")I", (var0, var1x) -> TransformerBase.u(var1x, Type.INT_TYPE, true, true, (var1xx, var2xx) -> {
            BytecodeHelper.k(var1xx);
            BytecodeHelper.J(var1xx, var1x);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, u, "onRenderEntityItem", "(" + SrgNames.X("net/minecraft/client/renderer/entity/RenderEntityItem") + SrgNames.X("net/minecraft/entity/item/EntityItem") + "DDDF" + SrgNames.X("net/minecraft/client/resources/model/IBakedModel") + I + ")V");
        }), "func_177077_a");
}
    public TransformRenderEntityItem() {
        super("net/minecraft/client/renderer/entity/RenderEntityItem");
}
}