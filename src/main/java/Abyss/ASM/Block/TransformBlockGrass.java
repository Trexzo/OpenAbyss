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

public class TransformBlockGrass
extends TransformerBase {
    private static long d = 96898560477241L;

    @Override
    public boolean s(ClassNode var1) {
        return TransformerBase.G(var1, "()" + SrgNames.X("net/minecraft/util/EnumWorldBlockLayer"), F, "getBlockLayer", "()" + SrgNames.X("net/minecraft/util/EnumWorldBlockLayer"), "getBlockLayer", "getBlockLayer");
}
    public TransformBlockGrass() {
        super("net/minecraft/block/BlockGrass");
}
}