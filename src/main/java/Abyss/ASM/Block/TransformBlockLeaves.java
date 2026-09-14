/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 */
package Abyss.ASM.Block;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;

public class TransformBlockLeaves
extends TransformerBase {
    private static long w;
    private static long d;

    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "()" + SrgNames.X("net/minecraft/util/EnumWorldBlockLayer"), (var1x, var2x) -> {
            InsnList var5 = new InsnList();
            BytecodeHelper.k(var5);
            BytecodeHelper.P(var5, var1.name, TransformerBase.j(var1, "Z", "isTransparent", "isTransparent", "P"), "Z");
            BytecodeHelper.Y(var5, S, "getBlockLayer", "(Z)" + SrgNames.X("net/minecraft/util/EnumWorldBlockLayer"));
            var5.add((AbstractInsnNode)new InsnNode((int)w));
            TransformerBase.Y(var2x);
            var2x.instructions.add(var5);
            return true;
        }, "getBlockLayer", "getBlockLayer");
}
    public TransformBlockLeaves() {
        super("net/minecraft/block/BlockLeaves");
}
    static {
        d = 39735817187027L;
        w = -5190937494450863952L;
}
}