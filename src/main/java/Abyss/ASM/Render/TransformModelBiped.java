/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.VarInsnNode
 */
package Abyss.ASM.Render;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.VarInsnNode;

public class TransformModelBiped
extends TransformerBase {
    private static long d = 120187238387434L;

    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        return (var4 |= BytecodeHelper.t(var1, "(FFFFFF" + SrgNames.X("net/minecraft/entity/Entity") + ")V", (var0, var1x) -> {
            InsnList var4x = new InsnList();
            BytecodeHelper.n(var4x, var1x, 4);
            BytecodeHelper.n(var4x, var1x, 6);
            BytecodeHelper.Y(var4x, j, "modifyHeadPitch", "(F" + SrgNames.X("net/minecraft/entity/Entity") + ")F");
            var4x.add((AbstractInsnNode)new VarInsnNode(56, 5));
            var1x.instructions.insert(var4x);
            return true;
        }, "setRotationAngles", "setRotationAngles")) | BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/entity/Entity") + "FFFFFF)V", (var0, var1x) -> {
            boolean var4x = TransformerBase.Q(var1x, BytecodeHelper.s("net/minecraft/client/renderer/GlStateManager", "()V", "pushMatrix", "pushMatrix"), 0, var1xx -> {
                BytecodeHelper.n(var1xx, var1x, 0);
                BytecodeHelper.Y(var1xx, j, "onRenderPre", "(" + SrgNames.X("net/minecraft/entity/Entity") + ")V");
            });
            return var4x | TransformerBase.v(var1x, BytecodeHelper.s("net/minecraft/client/renderer/GlStateManager", "()V", "popMatrix", "popMatrix"), 1, var1xx -> {
                BytecodeHelper.n(var1xx, var1x, 0);
                BytecodeHelper.Y(var1xx, j, "onRenderPost", "(" + SrgNames.X("net/minecraft/entity/Entity") + ")V");
            });
        }, "render", "render");
}
    public TransformModelBiped() {
        super("net/minecraft/client/model/ModelBiped");
}
}