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

public class TransformNetworkManager
extends TransformerBase {
    private static long d = 45344561273536L;

    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        return (var4 |= BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/network/Packet") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, q, "onSendPacket", "(" + SrgNames.X("net/minecraft/network/Packet") + z + ")V");
        }), "sendPacket", "sendPacket")) | BytecodeHelper.t(var1, "(Lio/netty/channel/ChannelHandlerContext;" + SrgNames.X("net/minecraft/network/Packet") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var1xx, var2xx) -> {
            BytecodeHelper.n(var1xx, var1x, 1);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, q, "onReceivePacket", "(" + SrgNames.X("net/minecraft/network/Packet") + z + ")V");
        }), "channelRead0");
}
    public TransformNetworkManager() {
        super("net/minecraft/network/NetworkManager");
}
}