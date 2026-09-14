/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.ClassNode
 */
package Abyss.ASM.Network;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class TransformNetHandlerPlayClient
extends TransformerBase {
    private static long d = 44525063271188L;

    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        return (var4 |= BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/network/play/server/S12PacketEntityVelocity") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.k(var1xx);
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, A, "onHandleEntityVelocity", "(" + SrgNames.X("net/minecraft/client/network/NetHandlerPlayClient") + SrgNames.X("net/minecraft/network/play/server/S12PacketEntityVelocity") + z + ")V");
        }), "handleEntityVelocity", "handleEntityVelocity")) | BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/network/play/server/S02PacketChat") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, false, (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.Y(var1xx, A, "handleChat", "(" + SrgNames.X("net/minecraft/network/play/server/S02PacketChat") + ")V");
        }), "handleChat", "handleChat");
}
    public TransformNetHandlerPlayClient() {
        super("net/minecraft/client/network/NetHandlerPlayClient");
}
}