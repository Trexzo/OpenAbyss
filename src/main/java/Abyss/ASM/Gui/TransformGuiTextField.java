/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.ClassNode
 */
package Abyss.ASM.Gui;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformGuiTextField
extends TransformerBase {
    private static long d = 14047968795735L;

    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        return (var4 |= BytecodeHelper.t(var1, "(Ljava/lang/String;)V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, false, (var0x, var1xx) -> BytecodeHelper.Y(var0x, V, "onGuiChatKeyTyped", "()V")), "writeText", "writeText")) | BytecodeHelper.t(var1, "(I)V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, false, (var0x, var1xx) -> BytecodeHelper.Y(var0x, V, "onGuiChatKeyTyped", "()V")), "deleteWords", "deleteWords", "deleteFromCursor", "deleteFromCursor");
}
    public TransformGuiTextField() {
        super("net/minecraft/client/gui/GuiTextField");
}
}