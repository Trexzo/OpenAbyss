/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.ClassNode
 */
package Abyss.ASM.Gui;

import Abyss.ASM.Hooks.Gui.GuiEventHooks;
import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import java.util.Map;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformGuiDisconnected
extends TransformerBase {
    private static long d = 55710805314204L;
    private static String P = TransformerBase.e(GuiEventHooks.class);
    private static Map t;

    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "()V", (var0, var1x) -> TransformerBase.M(var1x, Type.VOID_TYPE, (var0x, var1xx) -> {
            BytecodeHelper.k(var0x);
            BytecodeHelper.Y(var0x, P, "onDisconnectedInit", "(" + SrgNames.X("net/minecraft/client/gui/GuiScreen") + ")V");
        }), "initGui", "initGui");
}
    public TransformGuiDisconnected() {
        super("net/minecraft/client/gui/GuiDisconnected");
}
    private static void b() {
}
}