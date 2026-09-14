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
package Abyss.ASM.Gui;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.VarInsnNode;

public class TransformGuiChat
extends TransformerBase {
    private static long d = 21638065235124L;
    private static long w = -2762240308694482886L;

    public TransformGuiChat() {
        super("net/minecraft/client/gui/GuiChat");
}
    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        var4 |= BytecodeHelper.t(var1, "(Ljava/lang/String;Ljava/lang/String;)V", (var0, var1x) -> {
            InsnList var4x = new InsnList();
            BytecodeHelper.n(var4x, var1x, 0);
            BytecodeHelper.Y(var4x, k, "modifyAutoCompleteMessage", "(Ljava/lang/String;)Ljava/lang/String;");
            var4x.add((AbstractInsnNode)new VarInsnNode((int)w, 1));
            var1x.instructions.insert(var4x);
            return true;
        }, "sendAutocompleteRequest", "sendAutocompleteRequest");
        return (var4 |= BytecodeHelper.t(var1, "()V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
            long var2xx = 95917825590422L;
            BytecodeHelper.I(var0x, var1xx);
            BytecodeHelper.Y(var0x, k, "onAutocompletePlayerNames", "(" + z + ")V");
        }), "autocompletePlayerNames", "autocompletePlayerNames")) | BytecodeHelper.t(var1, "([Ljava/lang/String;)V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, k, "onAutocompleteResponse", "([Ljava/lang/String;" + z + ")V");
        }), "onAutocompleteResponse", "onAutocompleteResponse");
}
}