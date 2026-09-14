/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.VarInsnNode
 */
package Abyss.ASM.Block;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.VarInsnNode;

public class TransformBlockModelShapes
extends TransformerBase {
    private static long d = 6178367048455L;

    public TransformBlockModelShapes() {
        super("net/minecraft/client/renderer/BlockModelShapes");
}
    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/block/state/IBlockState") + ")" + SrgNames.X("net/minecraft/client/resources/model/IBakedModel"), (var0, var1x) -> {
            for (AbstractInsnNode var4 = var1x.instructions.getFirst(); var4 != null; var4 = var4.getNext()) {
                if (var4.getOpcode() != 176) continue;
                InsnList var5 = new InsnList();
                int var6 = BytecodeHelper.t(var1x, Type.getObjectType((String)"net/minecraft/client/resources/model/IBakedModel"));
                var5.add((AbstractInsnNode)new VarInsnNode(58, var6));
                BytecodeHelper.n(var5, var1x, 0);
                var5.add((AbstractInsnNode)new VarInsnNode(25, var6));
                BytecodeHelper.Y(var5, K, "getModelForState", "(" + SrgNames.X("net/minecraft/block/state/IBlockState") + SrgNames.X("net/minecraft/client/resources/model/IBakedModel") + ")" + SrgNames.X("net/minecraft/client/resources/model/IBakedModel"));
                var1x.instructions.insertBefore(var4, var5);
}
            return true;
        }, "getModelForState", "getModelForState");
}
}