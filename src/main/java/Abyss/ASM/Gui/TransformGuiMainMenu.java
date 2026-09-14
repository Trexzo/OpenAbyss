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
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformGuiMainMenu
extends TransformerBase {
    private static long d = 53853411023713L;

    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        var4 |= BytecodeHelper.t(var1, "()V", (var0, var1x) -> {
            boolean var5 = TransformerBase.M(var1x, Type.VOID_TYPE, (var0x, var1xx) -> {
                BytecodeHelper.k(var0x);
                BytecodeHelper.Y(var0x, v, "guiMainMenuPostInit", "(" + SrgNames.X("net/minecraft/client/gui/GuiMainMenu") + ")V");
            });
            boolean var6 = TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
                long var2xx = 129995624578677L;
                BytecodeHelper.k(var0x);
                BytecodeHelper.I(var0x, var1xx);
                BytecodeHelper.Y(var0x, v, "guiMainMenuInit", "(" + SrgNames.X("net/minecraft/client/gui/GuiMainMenu") + z + ")V");
            });
            return var5 | var6;
        }, "initGui", "initGui");
        return (var4 |= BytecodeHelper.t(var1, "(IIF)V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.k(var1xx);
            BytecodeHelper.J(var1xx, var1x);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, v, "guiMainMenuDraw", "(" + SrgNames.X("net/minecraft/client/gui/GuiMainMenu") + "IIF" + z + ")V");
        }), "drawScreen", "drawScreen")) | BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/client/gui/GuiButton") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, v, "guiMainMenuAction", "(" + SrgNames.X("net/minecraft/client/gui/GuiButton") + z + ")V");
        }), "actionPerformed", "actionPerformed");
}
    public TransformGuiMainMenu() {
        super("net/minecraft/client/gui/GuiMainMenu");
}
}