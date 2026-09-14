/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 */
package Abyss.ASM.Player;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;

public class TransformItemInWorldManager
extends TransformerBase {
    private static long d = 73939092852010L;

    @Override
    public boolean s(ClassNode var1) {
        return BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/util/BlockPos") + ")Z", (var0, var1x) -> {
            InsnList var4 = new InsnList();
            BytecodeHelper.n(var4, var1x, 0);
            BytecodeHelper.Y(var4, R, "ItemInWorldManager$tryHarvestBlockHead", "(" + SrgNames.X("net/minecraft/util/BlockPos") + ")V");
            var1x.instructions.insert(var4);
            return true;
        }, "tryHarvestBlock", "tryHarvestBlock");
}
    public TransformItemInWorldManager() {
        super("net/minecraft/server/management/ItemInWorldManager");
}
}