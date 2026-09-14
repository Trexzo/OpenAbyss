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

public class TransformGuiIngame
extends TransformerBase {
    private static long d = 136276918049282L;

    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        return (var4 |= BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/scoreboard/ScoreObjective") + SrgNames.X("net/minecraft/client/gui/ScaledResolution") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.J(var1xx, var1x);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, C, "renderScoreboard", "(" + SrgNames.X("net/minecraft/scoreboard/ScoreObjective") + SrgNames.X("net/minecraft/client/gui/ScaledResolution") + z + ")V");
        }), "renderScoreboard", "renderScoreboard")) | BytecodeHelper.t(var1, "()V", (var0, var1x) -> BytecodeHelper.R(var1x, BytecodeHelper.s("net/minecraft/entity/player/InventoryPlayer", "()" + SrgNames.X("net/minecraft/item/ItemStack"), "getCurrentItem", "getCurrentItem"), (var0x, var1xx) -> BytecodeHelper.Y(var0x, C, "updateTickGetCurrentItem", "(" + SrgNames.X("net/minecraft/entity/player/InventoryPlayer") + ")" + SrgNames.X("net/minecraft/item/ItemStack"))), "updateTick", "updateTick");
}
    public TransformGuiIngame() {
        super("net/minecraft/client/gui/GuiIngame");
}
}