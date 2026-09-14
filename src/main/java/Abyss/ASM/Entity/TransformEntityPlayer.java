/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.ClassNode
 */
package Abyss.ASM.Entity;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformEntityPlayer
extends TransformerBase {
    private static long d = 36532662932892L;

    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        var4 |= BytecodeHelper.t(var1, "()V", (var0, var1x) -> TransformerBase.M(var1x, Type.VOID_TYPE, (var0x, var1xx) -> {
            long var2xx = d ^ 0x30313E9759B5L;
            BytecodeHelper.Y(var0x, m, "EntityPlayer$onPostItemUseFinish", "()V");
        }), "onItemUseFinish", "onItemUseFinish");
        return (var4 |= BytecodeHelper.t(var1, "()" + SrgNames.X("net/minecraft/util/IChatComponent"), (var0, var1x) -> TransformerBase.M(var1x, Type.getReturnType((String)var1x.desc), (var0x, var1xx) -> {
            long var2xx = d ^ 0x7A8C0C8CDA7FL;
            BytecodeHelper.k(var0x);
            BytecodeHelper.I(var0x, var1xx);
            BytecodeHelper.Y(var0x, m, "EntityPlayer$onGetDisplayName", "(" + SrgNames.X("net/minecraft/entity/player/EntityPlayer") + I + ")V");
        }), "func_145748_c", "getDisplayName")) | BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/entity/Entity") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.k(var1xx);
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, m, "EntityPlayer$onAttackTargetEntity", "(" + SrgNames.X("net/minecraft/entity/player/EntityPlayer") + SrgNames.X("net/minecraft/entity/Entity") + z + ")V");
        }), "attackTargetEntityWithCurrentItem", "attackTargetEntityWithCurrentItem");
}
    public TransformEntityPlayer() {
        super("net/minecraft/entity/player/EntityPlayer");
}
}