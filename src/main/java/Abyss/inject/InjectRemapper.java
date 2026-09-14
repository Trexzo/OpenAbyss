/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.ClassWriter
 *  org.objectweb.asm.FieldVisitor
 *  org.objectweb.asm.Handle
 *  org.objectweb.asm.Label
 *  org.objectweb.asm.MethodVisitor
 *  org.objectweb.asm.Type
 */
package Abyss.inject;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

final class InjectRemapper {
    private InjectRemapper() {
}
    static byte[] remap(byte[] classfile, MemberMapper mapper) {
        ClassReader reader = new ClassReader(classfile);
        ClassWriter writer = new ClassWriter(0);
        reader.accept((ClassVisitor)new Visitor(327680, (ClassVisitor)writer, mapper), 0);
        return writer.toByteArray();
}
    private static final class MethodRemapper
    extends MethodVisitor {
        private final MemberMapper mapper;

        MethodRemapper(int api, MethodVisitor mv, MemberMapper mapper) {
            super(api, mv);
            this.mapper = mapper;
}
        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            super.visitFieldInsn(opcode, this.mapper.mapClass(owner), this.mapper.mapFieldName(owner, name), this.mapper.mapDesc(desc));
}
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            super.visitMethodInsn(opcode, this.mapper.mapClass(owner), this.mapper.mapMethodName(owner, name, desc), this.mapper.mapMethodDesc(desc), itf);
}
        public void visitTypeInsn(int opcode, String type) {
            super.visitTypeInsn(opcode, this.mapper.mapTypeName(type));
}
        public void visitMultiANewArrayInsn(String desc, int dims) {
            super.visitMultiANewArrayInsn(this.mapper.mapDesc(desc), dims);
}
        public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object ... bsmArgs) {
            Object[] mappedArgs = bsmArgs;
            if (bsmArgs != null && bsmArgs.length > 0) {
                mappedArgs = new Object[bsmArgs.length];
                for (int i = 0; i < bsmArgs.length; ++i) {
                    mappedArgs[i] = this.mapValue(bsmArgs[i]);
}
}
            super.visitInvokeDynamicInsn(name, this.mapper.mapMethodDesc(desc), this.mapHandle(bsm), mappedArgs);
}
        public void visitLdcInsn(Object cst) {
            super.visitLdcInsn(this.mapValue(cst));
}
        public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
            super.visitFrame(type, nLocal, this.remapFrameEntries(local), nStack, this.remapFrameEntries(stack));
}
        public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
            super.visitTryCatchBlock(start, end, handler, type == null ? null : this.mapper.mapClass(type));
}
        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
            super.visitLocalVariable(name, this.mapper.mapDesc(desc), signature, start, end, index);
}
        private Object[] remapFrameEntries(Object[] entries) {
            if (entries == null) {
                return null;
}
            Object[] remapped = null;
            for (int i = 0; i < entries.length; ++i) {
                if (!(entries[i] instanceof String)) continue;
                if (remapped == null) {
                    remapped = new Object[entries.length];
                    System.arraycopy(entries, 0, remapped, 0, entries.length);
}
                remapped[i] = this.mapper.mapTypeName((String)entries[i]);
}
            return remapped == null ? entries : remapped;
}
        private Object mapValue(Object cst) {
            if (cst instanceof Type) {
                Type t2 = (Type)cst;
                if (t2.getSort() == 11) {
                    return Type.getMethodType((String)this.mapper.mapMethodDesc(t2.getDescriptor()));
}
                return Type.getType((String)this.mapper.mapDesc(t2.getDescriptor()));
}
            if (cst instanceof Handle) {
                return this.mapHandle((Handle)cst);
}
            return cst;
}
        private Handle mapHandle(Handle h) {
            return new Handle(h.getTag(), this.mapper.mapClass(h.getOwner()), this.mapper.mapMethodName(h.getOwner(), h.getName(), h.getDesc()), this.mapper.mapMethodDesc(h.getDesc()));
}
}
    private static final class Visitor
    extends ClassVisitor {
        private final MemberMapper mapper;
        private String className;

        Visitor(int api, ClassVisitor cv, MemberMapper mapper) {
            super(api, cv);
            this.mapper = mapper;
}
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.className = name;
            String[] mappedInterfaces = interfaces;
            if (interfaces != null) {
                mappedInterfaces = new String[interfaces.length];
                for (int i = 0; i < interfaces.length; ++i) {
                    mappedInterfaces[i] = this.mapper.mapClass(interfaces[i]);
}
}
            super.visit(version, access, name, signature, superName == null ? null : this.mapper.mapClass(superName), mappedInterfaces);
}
        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            FieldVisitor fv = super.visitField(access, name, this.mapper.mapDesc(desc), signature, value);
            return fv;
}
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv;
            String mappedName = this.mapper.mapMethodName(this.className, name, desc);
            String[] mappedExceptions = exceptions;
            if (exceptions != null) {
                mappedExceptions = new String[exceptions.length];
                for (int i = 0; i < exceptions.length; ++i) {
                    mappedExceptions[i] = this.mapper.mapClass(exceptions[i]);
}
}
            return (mv = super.visitMethod(access, mappedName, this.mapper.mapMethodDesc(desc), signature, mappedExceptions)) == null ? null : new MethodRemapper(this.api, mv, this.mapper);
}
        public void visitInnerClass(String name, String outerName, String innerName, int access) {
            super.visitInnerClass(this.mapper.mapClass(name), outerName == null ? null : this.mapper.mapClass(outerName), innerName, access);
}
}
    static abstract class MemberMapper {
        MemberMapper() {
}
        abstract String mapClass(String var1);

        abstract String mapMethodName(String var1, String var2, String var3);

        abstract String mapFieldName(String var1, String var2);

        final String mapTypeName(String type) {
            if (type == null) {
                return null;
}
            return type.indexOf(91) >= 0 ? this.mapDesc(type) : this.mapClass(type);
}
        final String mapDesc(String descriptor) {
            if (descriptor == null || descriptor.indexOf(76) < 0) {
                return descriptor;
}
            StringBuilder out = new StringBuilder(descriptor.length());
            int i = 0;
            while (i < descriptor.length()) {
                char c = descriptor.charAt(i);
                if (c != 'L') {
                    out.append(c);
                    ++i;
                    continue;
}
                int end = descriptor.indexOf(59, i);
                if (end < 0) {
                    out.append(descriptor.substring(i));
                    break;
}
                String internal = descriptor.substring(i + 1, end);
                out.append('L').append(this.mapClass(internal)).append(';');
                i = end + 1;
}
            return out.toString();
}
        final String mapMethodDesc(String descriptor) {
            return this.mapDesc(descriptor);
}
}
}