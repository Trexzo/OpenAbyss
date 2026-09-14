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

public class TransformNetworkPlayerInfo
extends TransformerBase {
    private static long d = 69769816458693L;

    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "()" + SrgNames.X("net/minecraft/util/ResourceLocation"), (var1x, var2x) -> TransformerBase.M(var2x, Type.getReturnType((String)var2x.desc), (var1xx, var2xx) -> {
            BytecodeHelper.k(var1xx);
            BytecodeHelper.P(var1xx, var1.name, TransformerBase.j(var1, SrgNames.X("net/minecraft/util/ResourceLocation"), "locationCape", "locationCape", "f"), SrgNames.X("net/minecraft/util/ResourceLocation"));
            BytecodeHelper.k(var1xx);
            BytecodeHelper.P(var1xx, var1.name, TransformerBase.j(var1, "Lcom/mojang/authlib/GameProfile;", "gameProfile", "gameProfile", "a"), "Lcom/mojang/authlib/GameProfile;");
            BytecodeHelper.k(var1xx);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, Q, "getLocationCape", "(" + SrgNames.X("net/minecraft/util/ResourceLocation") + "Lcom/mojang/authlib/GameProfile;" + SrgNames.X("net/minecraft/client/network/NetworkPlayerInfo") + I + ")V");
        }), "getLocationCape", "getLocationCape");
}
    public TransformNetworkPlayerInfo() {
        super("net/minecraft/client/network/NetworkPlayerInfo");
}
}