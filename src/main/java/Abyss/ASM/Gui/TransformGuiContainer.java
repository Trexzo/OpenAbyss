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

public class TransformGuiContainer
extends TransformerBase {
    private static long d = 139396823925430L;

    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "(IIF)V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
            BytecodeHelper.I(var0x, var1xx);
            BytecodeHelper.Y(var0x, o, "cancelDrawChest", "(" + z + ")V");
        }), "drawScreen", "drawScreen");
}
    public TransformGuiContainer() {
        super("net/minecraft/client/gui/inventory/GuiContainer");
}
}