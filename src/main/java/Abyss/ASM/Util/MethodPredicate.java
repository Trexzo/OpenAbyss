/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.MethodNode
 */
package Abyss.ASM.Util;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public interface MethodPredicate {
    public boolean x(ClassNode var1, MethodNode var2);
}