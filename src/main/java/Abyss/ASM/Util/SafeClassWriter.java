/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassWriter
 */
package Abyss.ASM.Util;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

class SafeClassWriter
extends ClassWriter {
    private static long F = 120390589417918L;
    private final ClassLoader g;

    SafeClassWriter(ClassReader var1, int var2) {
        super(var1, var2);
        ClassLoader var3 = Thread.currentThread().getContextClassLoader();
        this.g = var3 == null ? SafeClassWriter.class.getClassLoader() : var3;
}
    protected String getCommonSuperClass(String var1, String var2) {
        if (var1.equals(var2)) {
            return var1;
}
        try {
            Class<?> var5 = this.i(var1);
            Class<?> var6 = this.i(var2);
            if (var5.isAssignableFrom(var6)) {
                return var1;
}
            if (var6.isAssignableFrom(var5)) {
                return var2;
}
            if (!var5.isInterface() && !var6.isInterface()) {
                while ((var5 = var5.getSuperclass()) != null && !var5.isAssignableFrom(var6)) {
}
                return var5 == null ? "java/lang/Object" : var5.getName().replace('.', '/');
}
            return "java/lang/Object";
}
        catch (Throwable var7) {
            return "java/lang/Object";
}
}
    private Class<?> i(String var1) throws ClassNotFoundException {
        return Class.forName(var1.replace('/', '.'), false, this.g);
}
}