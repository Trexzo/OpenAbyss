/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.ClassNode
 */
package Abyss.ASM;

import Abyss.ASM.Hooks.Gui.GuiEventHooks;
import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import java.util.Map;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class GenericTransformer
extends TransformerBase {
    private static String y;
    private static Map t;
    private static long d;

    private static void b() {
}
    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        var4 |= BytecodeHelper.t(var1, "()V", (var0, var1x) -> TransformerBase.M(var1x, Type.VOID_TYPE, (var0x, var1xx) -> {
            long var2xx = d ^ 0x2E0A5D21A77AL;
            BytecodeHelper.k(var0x);
            BytecodeHelper.Y(var0x, y, "onInitGui", "(" + SrgNames.X("net/minecraft/client/gui/GuiScreen") + ")V");
        }), "initGui", "initGui");
        return (var4 |= BytecodeHelper.t(var1, "(IIF)V", (var0, var1x) -> TransformerBase.M(var1x, Type.VOID_TYPE, (var0x, var1xx) -> {
            long var2xx = d ^ 0x7AC68E9321A9L;
            BytecodeHelper.k(var0x);
            BytecodeHelper.Y(var0x, y, "onDrawScreen", "(" + SrgNames.X("net/minecraft/client/gui/GuiScreen") + ")V");
        }), "drawScreen", "drawScreen")) | BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/client/gui/GuiButton") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.k(var1xx);
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, y, "onActionPerformed", "(" + SrgNames.X("net/minecraft/client/gui/GuiScreen") + SrgNames.X("net/minecraft/client/gui/GuiButton") + z + ")V");
        }), "actionPerformed", "actionPerformed");
}
    public GenericTransformer(String var1) {
        super(var1);
}
    static {
        d = 108897702989157L;
        y = TransformerBase.e(GuiEventHooks.class);
}
}