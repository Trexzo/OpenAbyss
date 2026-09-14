/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.ClassNode
 */
package Abyss.ASM.Player;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformKeyBinding
extends TransformerBase {
    private static long d = 100462606356730L;

    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        var4 |= BytecodeHelper.t(var1, "()Z", (var1x, var2x) -> TransformerBase.M(var2x, Type.BOOLEAN_TYPE, (var1xx, var2xx) -> {
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.k(var1xx);
            BytecodeHelper.P(var1xx, var1.name, TransformerBase.j(var1, "Ljava/lang/String;", "keyDescription", "keyDescription", "d"), "Ljava/lang/String;");
            BytecodeHelper.k(var1xx);
            BytecodeHelper.P(var1xx, var1.name, TransformerBase.j(var1, "I", "keyCode", "keyCode", "g"), "I");
            BytecodeHelper.Y(var1xx, R, "Keybinding$isPressed", "(" + I + "Ljava/lang/String;I)V");
        }), "isPressed", "isPressed");
        return (var4 |= BytecodeHelper.t(var1, "(IZ)V", (var0, var1x) -> {
            long var2x = d ^ 0x7D6D0B979479L;
            return TransformerBase.M(var1x, Type.VOID_TYPE, (var1xx, var2xx) -> {
                BytecodeHelper.n(var1xx, var1x, 0);
                BytecodeHelper.n(var1xx, var1x, 1);
                BytecodeHelper.Y(var1xx, R, "Keybinding$onSetKeyBindState", "(IZ)V");
            });
        }, "setKeyBindState", "setKeyBindState")) | BytecodeHelper.t(var1, "(I)V", (var0, var1x) -> {
            long var2x = d ^ 0xEDD51DCF0FL;
            return TransformerBase.u(var1x, Type.VOID_TYPE, false, false, (var1xx, var2xx) -> {
                BytecodeHelper.n(var1xx, var1x, 0);
                BytecodeHelper.Y(var1xx, R, "Keybinding$onTick", "(I)V");
            });
        }, "onTick", "onTick");
}
    public TransformKeyBinding() {
        super("net/minecraft/client/settings/KeyBinding");
}
}