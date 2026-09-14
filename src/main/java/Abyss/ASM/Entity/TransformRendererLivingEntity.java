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

public class TransformRendererLivingEntity
extends TransformerBase {
    private static long d = 48482666421625L;

    public TransformRendererLivingEntity() {
        super("net/minecraft/client/renderer/entity/RendererLivingEntity");
}
    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        return (var4 |= BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + "DDDFF)V", (var0, var1x) -> {
            boolean var4x = TransformerBase.u(var1x, Type.VOID_TYPE, false, false, (var1xx, var2xx) -> {
                BytecodeHelper.n(var1xx, var1x, 0);
                BytecodeHelper.Y(var1xx, Y, "onPreRender", "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + ")V");
            });
            return var4x | TransformerBase.M(var1x, Type.VOID_TYPE, (var1xx, var2xx) -> {
                BytecodeHelper.n(var1xx, var1x, 0);
                BytecodeHelper.Y(var1xx, Y, "onPostRender", "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + ")V");
            });
        }, "doRender", "doRender")) | BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + ")Z", (var0, var1x) -> TransformerBase.u(var1x, Type.BOOLEAN_TYPE, true, true, (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, Y, "canRenderName", "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + I + ")V");
        }), "canRenderName", "canRenderName");
}
}