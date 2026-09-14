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

public class TransformEntityLivingBase
extends TransformerBase {
    private static long d = 53417989733027L;

    public TransformEntityLivingBase() {
        super("net/minecraft/entity/EntityLivingBase");
}
    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        var4 |= BytecodeHelper.t(var1, "(FF)F", (var0, var1x) -> TransformerBase.u(var1x, Type.FLOAT_TYPE, true, true, (var1xx, var2xx) -> {
            BytecodeHelper.k(var1xx);
            BytecodeHelper.J(var1xx, var1x);
            BytecodeHelper.I(var1xx, var2xx);
            BytecodeHelper.Y(var1xx, m, "EntityLivingBase$onFunc_110146_f", "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + "FF" + I + ")V");
        }), "updateDistance", "updateDistance");
        var4 |= BytecodeHelper.t(var1, "()V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
            long var2xx = d ^ 0x12F081BB3B80L;
            BytecodeHelper.k(var0x);
            BytecodeHelper.I(var0x, var1xx);
            BytecodeHelper.Y(var0x, m, "EntityLivingBase$onJump", "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + z + ")V");
        }), "jump", "jump");
        var4 |= BytecodeHelper.t(var1, "(FF)V", (var0, var1x) -> {
            boolean var4x = TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
                long var2xx = d ^ 0x42DA894ACC35L;
                BytecodeHelper.k(var0x);
                BytecodeHelper.I(var0x, var1xx);
                BytecodeHelper.Y(var0x, m, "EntityLivingBase$onMoveEntityWithHeading", "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + z + ")V");
            });
            return var4x | BytecodeHelper.R(var1x, BytecodeHelper.s("net/minecraft/entity/EntityLivingBase", "(FFF)V", "moveFlying", "moveFlying"), (var0x, var1xx) -> {
                long var2xx = d ^ 0x207D201DF747L;
                BytecodeHelper.k(var0x);
                BytecodeHelper.Y(var0x, m, "EntityLivingBase$onMoveFlying", "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + "FFF" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + ")V");
            });
        }, "moveEntityWithHeading", "moveEntityWithHeading");
        return (var4 |= BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/util/DamageSource") + ")V", (var0, var1x) -> TransformerBase.u(var1x, Type.VOID_TYPE, false, false, (var1xx, var2xx) -> {
            BytecodeHelper.k(var1xx);
            BytecodeHelper.n(var1xx, var1x, 0);
            BytecodeHelper.Y(var1xx, m, "EntityLivingBase$onLivingDeath", "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + SrgNames.X("net/minecraft/util/DamageSource") + ")V");
        }), "onDeath", "onDeath")) | BytecodeHelper.t(var1, "()I", (var0, var1x) -> TransformerBase.M(var1x, Type.INT_TYPE, (var0x, var1xx) -> {
            long var2xx = d ^ 0x7F76024E8F00L;
            BytecodeHelper.k(var0x);
            BytecodeHelper.I(var0x, var1xx);
            BytecodeHelper.Y(var0x, m, "EntityLivingBase$onGetArmSwingAnimationEnd", "(" + SrgNames.X("net/minecraft/entity/EntityLivingBase") + I + ")V");
        }), "getArmSwingAnimationEnd", "getArmSwingAnimationEnd");
}
}