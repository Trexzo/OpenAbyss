/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.ClassWriter
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.FrameNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.IntInsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.LdcInsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.TypeInsnNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package Abyss.inject;

import Abyss.inject.InjectHookRegistry;
import Abyss.inject.InjectMappingBridge;
import Abyss.inject.InjectObfuscation;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class InjectHookTransformer
implements ClassFileTransformer {
    private final Map<String, String> internalToMcp = new ConcurrentHashMap<String, String>();
    private final Set<String> applied = Collections.newSetFromMap(new ConcurrentHashMap());
    private final ConcurrentLinkedQueue<String> log = new ConcurrentLinkedQueue();
    private final List<Class<?>> targets = new ArrayList();

    public InjectHookTransformer(Class<?>[] loaded) {
        HashMap<String, String> nameToMcp = new HashMap<String, String>();
        for (String mcp : InjectHookRegistry.owners()) {
            nameToMcp.put(mcp, mcp);
            this.internalToMcp.put(mcp.replace('.', '/'), mcp);
            String notch = InjectMappingBridge.notchClass(mcp);
            if (notch == null) continue;
            nameToMcp.put(notch, mcp);
            this.internalToMcp.put(notch.replace('.', '/'), mcp);
}
        HashSet<String> found = new HashSet<String>();
        for (Class<?> candidate : loaded) {
            String mcp = (String)nameToMcp.get(candidate.getName());
            if (mcp == null) continue;
            this.targets.add(candidate);
            found.add(mcp);
            ClassLoader cl = candidate.getClassLoader();
            this.log.offer("target " + mcp + " as " + candidate.getName() + " [" + (cl == null ? "bootstrap" : cl.getClass().getSimpleName()) + "]");
}
        for (String mcp : InjectHookRegistry.owners()) {
            if (found.contains(mcp)) continue;
            this.log.offer("deferred " + mcp + " -- not loaded yet, will patch as it loads");
}
}
    public List<Class<?>> targets() {
        return this.targets;
}
    public void refreshTargets() {
        for (String mcp : InjectHookRegistry.owners()) {
            String notch;
            String internal = mcp.replace('.', '/');
            if (this.internalToMcp.put(internal, mcp) == null) {
                this.log.offer("armed " + mcp);
}
            if ((notch = InjectMappingBridge.notchClass(mcp)) == null) continue;
            this.internalToMcp.put(notch.replace('.', '/'), mcp);
}
}
    public List<String> drain() {
        ArrayList<String> taken = new ArrayList<String>();
        String line = this.log.poll();
        while (line != null) {
            taken.add(line);
            line = this.log.poll();
}
        return taken;
}
    public boolean hasLog() {
        return !this.log.isEmpty();
}
    public void warmUp(byte[] sample) {
        try {
            ClassNode node = new ClassNode();
            new ClassReader(sample).accept((ClassVisitor)node, 0);
            ClassWriter writer = new ClassWriter(1);
            node.accept((ClassVisitor)writer);
            writer.toByteArray();
            InsnList list = new InsnList();
            LabelNode label = new LabelNode();
            list.add((AbstractInsnNode)new VarInsnNode(25, 0));
            list.add((AbstractInsnNode)new MethodInsnNode(184, "java/lang/Object", "toString", "()Ljava/lang/String;", false));
            list.add((AbstractInsnNode)new FieldInsnNode(178, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;"));
            list.add((AbstractInsnNode)new TypeInsnNode(192, "java/lang/Object"));
            list.add((AbstractInsnNode)new LdcInsnNode((Object)0.0));
            list.add((AbstractInsnNode)new JumpInsnNode(198, label));
            list.add((AbstractInsnNode)new InsnNode(87));
            list.add((AbstractInsnNode)label);
            list.add((AbstractInsnNode)new FrameNode(3, 0, null, 0, null));
            list.add((AbstractInsnNode)new IntInsnNode(16, 1));
            Type.getArgumentTypes((String)"(IZ)V");
}
        catch (Throwable t2) {
            this.log.offer("warm-up of the transformer failed: " + t2);
}
}
    @Override
    public byte[] transform(ClassLoader loader, String internalName, Class<?> beingRedefined, ProtectionDomain domain, byte[] original) {
        if (internalName == null) {
            return null;
}
        String mcp = this.internalToMcp.get(internalName);
        if (mcp == null) {
            return null;
}
        List<InjectHookRegistry.Hook> hooks = InjectHookRegistry.forOwner(mcp);
        if (hooks.isEmpty()) {
            return null;
}
        try {
            ClassNode node = new ClassNode();
            new ClassReader(original).accept((ClassVisitor)node, 0);
            int installed = 0;
            for (InjectHookRegistry.Hook hook : hooks) {
                MethodNode delegate;
                MethodNode target = this.findMethod(node, hook.nameOwner, hook);
                if (target == null) {
                    this.log.offer("no method " + mcp + "." + hook.method);
                    continue;
}
                int placed = this.insert(target, hook);
                if (placed == 0 && (delegate = this.delegateOf(node, target)) != null && (placed = this.insert(delegate, hook)) > 0) {
                    this.log.offer("  " + mcp + "." + hook.method + " forwards to " + delegate.name + delegate.desc + " -- hooked there instead");
}
                if (placed == 0) {
                    this.log.offer("no insertion point for " + mcp + "." + hook.method + " @" + (Object)((Object)hook.position));
                    continue;
}
                ++installed;
                this.applied.add(mcp + "." + hook.method);
}
            if (installed == 0) {
                return null;
}
            ClassWriter writer = new ClassWriter(1);
            node.accept((ClassVisitor)writer);
            this.log.offer("patched " + mcp + " (" + installed + "/" + hooks.size() + ")");
            return writer.toByteArray();
}
        catch (Throwable t2) {
            this.log.offer("FAILED " + mcp + ": " + t2);
            return null;
}
}
    private MethodNode findMethod(ClassNode node, String mcpOwner, InjectHookRegistry.Hook hook) {
        String[] names = InjectMappingBridge.methodNames(mcpOwner, hook.method, hook.descriptor);
        Set<String> descriptors = InjectMappingBridge.descriptors(hook.descriptor);
        MethodNode byName = null;
        int matches = 0;
        for (MethodNode m2 : node.methods) {
            boolean nameMatches = false;
            for (String candidate : names) {
                if (candidate == null || !candidate.equals(m2.name)) continue;
                nameMatches = true;
                break;
}
            if (!nameMatches) continue;
            if (hook.descriptor != null) {
                if (!descriptors.contains(m2.desc)) continue;
                return m2;
}
            ++matches;
            byName = m2;
}
        return matches == 1 ? byName : null;
}
    private int insert(MethodNode target, InjectHookRegistry.Hook hook) {
        boolean readsArguments;
        boolean bl = readsArguments = hook.position == InjectHookRegistry.Position.HEAD || hook.position == InjectHookRegistry.Position.MODIFY_ARGUMENT;
        if (!readsArguments && hook.argSpec.length() > 0) {
            this.log.offer("REFUSING " + hook.owner + "." + hook.method + " @" + (Object)((Object)hook.position) + ": only a head hook may read arguments -- see callback()");
            return 0;
}
        switch (hook.position) {
            case HEAD: {
                target.instructions.insert(this.callback(target, hook));
                return 1;
}
            case RETURN: {
                List<AbstractInsnNode> returns = this.returns(target);
                for (AbstractInsnNode insn : returns) {
                    target.instructions.insertBefore(insn, this.callback(target, hook));
}
                return returns.size();
}
            case MODIFY_RETURN: {
                List<AbstractInsnNode> returns = this.returns(target);
                for (AbstractInsnNode insn : returns) {
                    target.instructions.insertBefore(insn, this.invokeCallback(hook));
}
                return returns.size();
}
}
        return this.insertAtMember(target, hook);
}
    private int insertAtMember(MethodNode target, InjectHookRegistry.Hook hook) {
        switch (hook.position) {
            case BEFORE_INVOKE: {
                List<MethodInsnNode> calls = this.findCalls(target, hook);
                for (MethodInsnNode call : calls) {
                    target.instructions.insertBefore((AbstractInsnNode)call, this.callback(target, hook));
}
                return calls.size();
}
            case AFTER_INVOKE: {
                List<MethodInsnNode> calls = this.findCalls(target, hook);
                for (MethodInsnNode call : calls) {
                    target.instructions.insert((AbstractInsnNode)call, this.callback(target, hook));
}
                return calls.size();
}
            case REPLACE_INVOKE: {
                List<MethodInsnNode> calls = this.findCalls(target, hook);
                for (MethodInsnNode call : calls) {
                    target.instructions.set((AbstractInsnNode)call, this.invokeCallback(hook).getFirst());
}
                return calls.size();
}
            case BEFORE_FIELD: {
                List<FieldInsnNode> accesses = this.findFieldAccesses(target, hook);
                for (FieldInsnNode access : accesses) {
                    target.instructions.insertBefore((AbstractInsnNode)access, this.callback(target, hook));
}
                return accesses.size();
}
            case REPLACE_FIELD: {
                List<FieldInsnNode> accesses = this.findFieldAccesses(target, hook);
                for (FieldInsnNode access : accesses) {
                    target.instructions.set((AbstractInsnNode)access, this.invokeCallback(hook).getFirst());
}
                return accesses.size();
}
            case MODIFY_CONSTANT: {
                List<AbstractInsnNode> constants = this.findConstants(target, hook);
                for (AbstractInsnNode constant : constants) {
                    target.instructions.insert(constant, this.invokeCallback(hook));
}
                return constants.size();
}
            case MODIFY_STORE: {
                List<AbstractInsnNode> stores = this.findStores(target, hook);
                for (AbstractInsnNode store : stores) {
                    target.instructions.insertBefore(store, this.invokeCallback(hook));
}
                return stores.size();
}
            case MODIFY_ARGUMENT: {
                target.instructions.insert(this.modifyArgument(target, hook));
                return 1;
}
}
        return 0;
}
    private List<AbstractInsnNode> returns(MethodNode target) {
        ArrayList<AbstractInsnNode> found = new ArrayList<AbstractInsnNode>();
        for (AbstractInsnNode insn = target.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            int op = insn.getOpcode();
            if (op < 172 || op > 177) continue;
            found.add(insn);
}
        return found;
}
    private InsnList invokeCallback(InjectHookRegistry.Hook hook) {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, hook.callbackOwner, hook.callbackName, this.callbackDescriptor(hook), false));
        return list;
}
    private String callbackDescriptor(InjectHookRegistry.Hook hook) {
        return InjectObfuscation.mapDescriptor(hook.callbackDescriptor);
}
    private InsnList modifyArgument(MethodNode target, InjectHookRegistry.Hook hook) {
        boolean isStatic = (target.access & 8) != 0;
        Type[] args = Type.getArgumentTypes((String)target.desc);
        int index = Integer.parseInt(hook.argSpec.trim());
        int slot = isStatic ? 0 : 1;
        for (int i = 0; i < index; ++i) {
            slot += args[i].getSize();
}
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(args[index].getOpcode(21), slot));
        list.add((AbstractInsnNode)new MethodInsnNode(184, hook.callbackOwner, hook.callbackName, this.callbackDescriptor(hook), false));
        list.add((AbstractInsnNode)new VarInsnNode(args[index].getOpcode(54), slot));
        return list;
}
    private Set<String> ownerNames(String mcpClass) {
        LinkedHashSet<String> names = new LinkedHashSet<String>();
        names.add(mcpClass.replace('.', '/'));
        String notch = InjectMappingBridge.notchClass(mcpClass);
        if (notch != null) {
            names.add(notch.replace('.', '/'));
}
        return names;
}
    private List<MethodInsnNode> findCalls(MethodNode target, InjectHookRegistry.Hook hook) {
        Object[] objectArray;
        if (hook.targetRemap) {
            objectArray = InjectMappingBridge.methodNames(hook.targetNameOwner, hook.targetMember, hook.targetDescriptor);
        } else {
            Object[] objectArray2 = new String[1];
            objectArray = objectArray2;
            objectArray2[0] = hook.targetMember;
}
        Object[] names = objectArray;
        Set<String> owners = this.ownerNames(hook.targetOwner);
        Set<String> descriptors = InjectMappingBridge.descriptors(hook.targetDescriptor);
        ArrayList<MethodInsnNode> found = new ArrayList<MethodInsnNode>();
        int seen = 0;
        for (AbstractInsnNode insn = target.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            if (!(insn instanceof MethodInsnNode)) continue;
            MethodInsnNode call = (MethodInsnNode)insn;
            if (!owners.contains(call.owner) || hook.targetDescriptor != null && !descriptors.contains(call.desc)) continue;
            for (Object candidate : names) {
                if (candidate == null || !((String)candidate).equals(call.name)) continue;
                if (hook.ordinal < 0 || seen == hook.ordinal) {
                    found.add(call);
}
                ++seen;
                break;
}
}
        if (found.isEmpty()) {
            this.log.offer("  wanted " + Arrays.toString(names) + hook.targetDescriptor + " on any of " + owners);
            this.log.offer("  the method calls: " + this.callsIn(target));
}
        return found;
}
    private List<FieldInsnNode> findFieldAccesses(MethodNode target, InjectHookRegistry.Hook hook) {
        Object[] objectArray;
        if (hook.targetRemap) {
            objectArray = InjectMappingBridge.fieldNames(hook.targetNameOwner, hook.targetMember);
        } else {
            Object[] objectArray2 = new String[1];
            objectArray = objectArray2;
            objectArray2[0] = hook.targetMember;
}
        Object[] names = objectArray;
        Set<String> owners = this.ownerNames(hook.targetOwner);
        Set<String> descriptors = InjectMappingBridge.descriptors(hook.targetDescriptor);
        ArrayList<FieldInsnNode> found = new ArrayList<FieldInsnNode>();
        int seen = 0;
        for (AbstractInsnNode insn = target.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            if (!(insn instanceof FieldInsnNode)) continue;
            FieldInsnNode access = (FieldInsnNode)insn;
            if (!owners.contains(access.owner) || hook.targetDescriptor != null && !descriptors.contains(access.desc)) continue;
            for (Object candidate : names) {
                if (candidate == null || !((String)candidate).equals(access.name)) continue;
                if (hook.ordinal < 0 || seen == hook.ordinal) {
                    found.add(access);
}
                ++seen;
                break;
}
}
        if (found.isEmpty()) {
            this.log.offer("  wanted field " + Arrays.toString(names) + " " + hook.targetDescriptor + " on any of " + owners);
}
        return found;
}
    private List<AbstractInsnNode> findConstants(MethodNode target, InjectHookRegistry.Hook hook) {
        ArrayList<AbstractInsnNode> found = new ArrayList<AbstractInsnNode>();
        int seen = 0;
        for (AbstractInsnNode insn = target.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            Object value = this.constantValue(insn);
            if (value == null || !String.valueOf(value).equals(hook.targetMember)) continue;
            if (hook.ordinal < 0 || seen == hook.ordinal) {
                found.add(insn);
}
            ++seen;
}
        if (found.isEmpty()) {
            this.log.offer("  wanted constant " + hook.targetMember + " ordinal " + hook.ordinal + " in " + hook.method + ", saw " + seen + " candidates");
}
        return found;
}
    private Object constantValue(AbstractInsnNode insn) {
        if (insn instanceof LdcInsnNode) {
            Object value = ((LdcInsnNode)insn).cst;
            return value instanceof String ? null : value;
}
        switch (insn.getOpcode()) {
            case 2: {
                return -1;
}
            case 3: {
                return 0;
}
            case 4: {
                return 1;
}
            case 5: {
                return 2;
}
            case 6: {
                return 3;
}
            case 7: {
                return 4;
}
            case 8: {
                return 5;
}
            case 9: {
                return 0L;
}
            case 10: {
                return 1L;
}
            case 11: {
                return Float.valueOf(0.0f);
}
            case 12: {
                return Float.valueOf(1.0f);
}
            case 13: {
                return Float.valueOf(2.0f);
}
            case 14: {
                return 0.0;
}
            case 15: {
                return 1.0;
}
}
        return null;
}
    private List<AbstractInsnNode> findStores(MethodNode target, InjectHookRegistry.Hook hook) {
        int wanted = this.storeOpcode(hook.targetDescriptor);
        ArrayList<AbstractInsnNode> found = new ArrayList<AbstractInsnNode>();
        int seen = 0;
        for (AbstractInsnNode insn = target.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            if (insn.getOpcode() != wanted) continue;
            if (hook.ordinal < 0 || seen == hook.ordinal) {
                found.add(insn);
}
            ++seen;
}
        if (found.isEmpty()) {
            this.log.offer("  wanted store #" + hook.ordinal + " of " + hook.targetDescriptor + " in " + hook.method + ", saw " + seen);
}
        return found;
}
    private int storeOpcode(String descriptor) {
        if (descriptor == null || descriptor.length() == 0) {
            return 58;
}
        switch (descriptor.charAt(0)) {
            case 'B': 
            case 'C': 
            case 'I': 
            case 'S': 
            case 'Z': {
                return 54;
}
            case 'J': {
                return 55;
}
            case 'F': {
                return 56;
}
            case 'D': {
                return 57;
}
}
        return 58;
}
    private MethodNode delegateOf(ClassNode node, MethodNode forwarder) {
        MethodInsnNode call = null;
        for (AbstractInsnNode insn = forwarder.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            if (insn.getOpcode() < 0) continue;
            if (insn instanceof MethodInsnNode) {
                if (call != null) {
                    return null;
}
                call = (MethodInsnNode)insn;
                continue;
}
            if (insn instanceof VarInsnNode || insn instanceof InsnNode || insn instanceof IntInsnNode || insn instanceof LdcInsnNode || insn instanceof TypeInsnNode) continue;
            return null;
}
        if (call == null || !call.owner.equals(node.name)) {
            return null;
}
        for (MethodNode candidate : node.methods) {
            if (!candidate.name.equals(call.name) || !candidate.desc.equals(call.desc) || candidate == forwarder) continue;
            return candidate;
}
        return null;
}
    private String callsIn(MethodNode target) {
        LinkedHashSet<String> distinct = new LinkedHashSet<String>();
        for (AbstractInsnNode insn = target.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            if (!(insn instanceof MethodInsnNode)) continue;
            MethodInsnNode call = (MethodInsnNode)insn;
            distinct.add(call.owner + "." + call.name + call.desc);
}
        if (distinct.isEmpty()) {
            return "nothing at all";
}
        StringBuilder out = new StringBuilder();
        int shown = 0;
        for (String call : distinct) {
            if (shown == 24) {
                out.append(" ... and ").append(distinct.size() - shown).append(" more");
                break;
}
            out.append(shown == 0 ? "" : ", ").append(call);
            ++shown;
}
        return out.toString();
}
    private InsnList callback(MethodNode target, InjectHookRegistry.Hook hook) {
        InsnList list = new InsnList();
        boolean isStatic = (target.access & 8) != 0;
        Type[] args = Type.getArgumentTypes((String)target.desc);
        for (String piece : hook.argSpec.split(",")) {
            if ((piece = piece.trim()).length() == 0) continue;
            if (piece.equals("this")) {
                list.add((AbstractInsnNode)new VarInsnNode(25, 0));
                continue;
}
            int index = Integer.parseInt(piece);
            int slot = isStatic ? 0 : 1;
            for (int i = 0; i < index; ++i) {
                slot += args[i].getSize();
}
            list.add((AbstractInsnNode)new VarInsnNode(args[index].getOpcode(21), slot));
}
        list.add((AbstractInsnNode)new MethodInsnNode(184, hook.callbackOwner, hook.callbackName, this.callbackDescriptor(hook), false));
        if (hook.cancellable) {
            Type returned = Type.getReturnType((String)target.desc);
            if (returned.getSort() == 0) {
                this.cancelVoid(list);
            } else {
                this.cancelWithValue(list, returned);
}
}
        return list;
}
    private void cancelVoid(InsnList list) {
        LabelNode carryOn = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, carryOn));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)carryOn);
        list.add((AbstractInsnNode)new FrameNode(3, 0, null, 0, null));
}
    private void cancelWithValue(InsnList list, Type returned) {
        boolean reference = returned.getSort() == 10 || returned.getSort() == 9;
        LabelNode carryOn = new LabelNode();
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new JumpInsnNode(198, carryOn));
        if (reference) {
            list.add((AbstractInsnNode)new TypeInsnNode(192, "[Ljava/lang/Object;"));
            list.add((AbstractInsnNode)new InsnNode(3));
            list.add((AbstractInsnNode)new InsnNode(50));
            list.add((AbstractInsnNode)new TypeInsnNode(192, returned.getInternalName()));
        } else {
            list.add(this.unbox(returned));
}
        list.add((AbstractInsnNode)new InsnNode(returned.getOpcode(172)));
        list.add((AbstractInsnNode)carryOn);
        list.add((AbstractInsnNode)new FrameNode(4, 0, null, 1, new Object[]{"java/lang/Object"}));
        list.add((AbstractInsnNode)new InsnNode(87));
}
    private InsnList unbox(Type returned) {
        String method;
        String box;
        InsnList list = new InsnList();
        switch (returned.getSort()) {
            case 1: {
                box = "java/lang/Boolean";
                method = "booleanValue";
                break;
}
            case 2: {
                box = "java/lang/Character";
                method = "charValue";
                break;
}
            case 3: {
                box = "java/lang/Byte";
                method = "byteValue";
                break;
}
            case 4: {
                box = "java/lang/Short";
                method = "shortValue";
                break;
}
            case 5: {
                box = "java/lang/Integer";
                method = "intValue";
                break;
}
            case 6: {
                box = "java/lang/Float";
                method = "floatValue";
                break;
}
            case 7: {
                box = "java/lang/Long";
                method = "longValue";
                break;
}
            case 8: {
                box = "java/lang/Double";
                method = "doubleValue";
                break;
}
            default: {
                list.add((AbstractInsnNode)new TypeInsnNode(192, returned.getInternalName()));
                return list;
}
}
        list.add((AbstractInsnNode)new TypeInsnNode(192, box));
        list.add((AbstractInsnNode)new MethodInsnNode(182, box, method, "()" + returned.getDescriptor(), false));
        return list;
}
}