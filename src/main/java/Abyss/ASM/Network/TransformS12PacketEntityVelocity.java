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

public class TransformS12PacketEntityVelocity
extends TransformerBase {
    private static long d = 83282681363546L;

    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/network/play/INetHandlerPlayClient") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.k(var1xx);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, A, "onProcessEntityVelocity", "(" + SrgNames.X("net/minecraft/network/play/INetHandlerPlayClient") + SrgNames.X("net/minecraft/network/play/server/S12PacketEntityVelocity") + z + ")V");
        }), "processPacket", "processPacket");
}
    public TransformS12PacketEntityVelocity() {
        super("net/minecraft/network/play/server/S12PacketEntityVelocity");
}
}