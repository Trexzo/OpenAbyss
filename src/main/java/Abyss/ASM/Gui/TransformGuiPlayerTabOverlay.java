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

public class TransformGuiPlayerTabOverlay
extends TransformerBase {
    private static long d = 76852039433438L;

    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/client/network/NetworkPlayerInfo") + ")Ljava/lang/String;", (var0, var1x) -> TransformerBase.M(var1x, Type.getReturnType((String)var1x.desc), (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, p, "onPlayerGetName", "(" + SrgNames.X("net/minecraft/client/network/NetworkPlayerInfo") + I + ")V");
        }), "getPlayerName", "getPlayerName");
}
    public TransformGuiPlayerTabOverlay() {
        super("net/minecraft/client/gui/GuiPlayerTabOverlay");
}
}