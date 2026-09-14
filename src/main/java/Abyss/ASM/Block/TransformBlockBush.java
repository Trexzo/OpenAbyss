/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.ClassNode
 */
package Abyss.ASM.Block;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.tree.ClassNode;

public class TransformBlockBush
extends TransformerBase {
    private static long d = 63269248874563L;

    @Override
    public boolean s(ClassNode var1) {
        return TransformerBase.G(var1, "()" + SrgNames.X("net/minecraft/util/EnumWorldBlockLayer"), a, "getBlockLayer", "()" + SrgNames.X("net/minecraft/util/EnumWorldBlockLayer"), "getBlockLayer", "getBlockLayer");
}
    public TransformBlockBush() {
        super("net/minecraft/block/BlockBush");
}
}