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
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.VarInsnNode;

public class TransformFontRenderer
extends TransformerBase {
    private static long d = 75314626025260L;

    public TransformFontRenderer() {
        super("net/minecraft/client/gui/FontRenderer");
}
    public boolean X(ClassNode var1, String var2, String ... var3) {
        return BytecodeHelper.t(var1, var2, (var0, var1x) -> {
            InsnList var4x = new InsnList();
            var4x.add((AbstractInsnNode)new VarInsnNode(25, 1));
            BytecodeHelper.Y(var4x, x, "replaceString", "(Ljava/lang/String;)Ljava/lang/String;");
            var4x.add((AbstractInsnNode)new VarInsnNode(58, 1));
            var1x.instructions.insert(var4x);
            return true;
        }, var3);
}
    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        return (var4 |= this.X(var1, "(Ljava/lang/String;FFIZ)I", "renderString", "renderString")) | this.X(var1, "(Ljava/lang/String;)I", "getStringWidth", "getStringWidth");
}
}