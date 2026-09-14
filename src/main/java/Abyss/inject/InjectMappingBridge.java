/*
 * Decompiled with CFR 0.152.
 */
package Abyss.inject;

import Abyss.ASM.Util.AsmUtil;
import Abyss.ASM.Util.MappingKind;
import Abyss.inject.InjectLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class InjectMappingBridge {
    private static final Map<String, Class<?>> CLASS_CACHE = new HashMap();
    private static final Map<String, Field> FIELD_CACHE = new HashMap<String, Field>();
    private static final Map<String, Method> METHOD_CACHE = new HashMap<String, Method>();
    private static final Set<String> FAILURES = new LinkedHashSet<String>();

    private InjectMappingBridge() {
}
    public static List<String> failures() {
        return new ArrayList<String>(FAILURES);
}
    public static String notchClass(String mcpName) {
        try {
            String internal = mcpName.replace('.', '/');
            String notch = AsmUtil.b(MappingKind.NOTCH, internal);
            if (notch != null && !notch.equals(internal)) {
                return notch.replace('/', '.');
}
}
        catch (Throwable t2) {
            InjectLog.swallowed(t2);
}
        return null;
}
    public static String[] methodNames(String mcpOwner, String mcpMethod) {
        return InjectMappingBridge.methodNames(mcpOwner, mcpMethod, null);
}
    public static String[] methodNames(String mcpOwner, String mcpMethod, String mcpDescriptor) {
        LinkedHashSet<String> out = new LinkedHashSet<String>();
        out.add(mcpMethod);
        String desc = mcpDescriptor == null || mcpDescriptor.isEmpty() ? "()V" : mcpDescriptor;
        try {
            String internal = mcpOwner.replace('.', '/');
            out.addAll(AsmUtil.Q(internal, desc, mcpMethod));
            for (MappingKind kind : MappingKind.values()) {
                String mapped;
                String owner = AsmUtil.b(kind, internal);
                if (owner == null || (mapped = AsmUtil.q(owner, desc, mcpMethod)) == null) continue;
                out.add(mapped);
}
}
        catch (Throwable t2) {
            InjectLog.swallowed(t2);
}
        out.remove(null);
        return out.toArray(new String[out.size()]);
}
    public static String[] fieldNames(String mcpOwner, String mcpField) {
        LinkedHashSet<String> out = new LinkedHashSet<String>();
        out.add(mcpField);
        try {
            String internal = mcpOwner.replace('.', '/');
            out.addAll(AsmUtil.G(internal, mcpField));
            for (MappingKind kind : MappingKind.values()) {
                String mapped;
                String owner = AsmUtil.b(kind, internal);
                if (owner == null || (mapped = AsmUtil.J(owner, mcpField)) == null) continue;
                out.add(mapped);
}
}
        catch (Throwable t2) {
            InjectLog.swallowed(t2);
}
        out.remove(null);
        return out.toArray(new String[out.size()]);
}
    public static Set<String> descriptors(String mcpDescriptor) {
        LinkedHashSet<String> out = new LinkedHashSet<String>();
        if (mcpDescriptor == null) {
            return out;
}
        out.add(mcpDescriptor);
        try {
            for (MappingKind kind : MappingKind.values()) {
                String mapped = AsmUtil.Y(kind, mcpDescriptor);
                if (mapped == null) continue;
                out.add(mapped);
}
}
        catch (Throwable t2) {
            InjectLog.swallowed(t2);
}
        return out;
}
    public static Class<?> findClass(String mcpName) {
        String notch;
        Class<?> cached = CLASS_CACHE.get(mcpName);
        if (cached != null) {
            return cached;
}
        ClassLoader loader = InjectMappingBridge.class.getClassLoader();
        Class<?> found = InjectMappingBridge.tryClass(mcpName, loader);
        if (found == null && (notch = InjectMappingBridge.notchClass(mcpName)) != null) {
            found = InjectMappingBridge.tryClass(notch, loader);
}
        if (found == null) {
            if (FAILURES.add("class " + mcpName)) {
                InjectLog.line("unresolved class " + mcpName);
}
            return null;
}
        CLASS_CACHE.put(mcpName, found);
        return found;
}
    private static Class<?> tryClass(String name, ClassLoader loader) {
        try {
            return Class.forName(name, false, loader);
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
            return null;
}
}
    public static Field field(String owner, String mcp, Class<?> type) {
        String key = owner + "#" + mcp;
        Field cached = FIELD_CACHE.get(key);
        if (cached != null) {
            return cached;
}
        Class<?> cls = InjectMappingBridge.findClass(owner);
        if (cls == null) {
            return null;
}
        Field found = null;
        for (String candidate : InjectMappingBridge.fieldNames(owner, mcp)) {
            if (candidate != null && (found = InjectMappingBridge.declaredField(cls, candidate)) != null) break;
}
        if (found == null && type != null) {
            found = InjectMappingBridge.fieldByType(cls, type);
}
        if (found == null) {
            if (FAILURES.add("field " + owner + "." + mcp)) {
                InjectLog.line("unresolved field " + owner + "." + mcp);
}
            return null;
}
        found.setAccessible(true);
        FIELD_CACHE.put(key, found);
        return found;
}
    private static Field declaredField(Class<?> cls, String name) {
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            try {
                return c.getDeclaredField(name);
}
            catch (NoSuchFieldException noSuchFieldException) {
                continue;
}
}
        return null;
}
    private static Field fieldByType(Class<?> cls, Class<?> type) {
        Field match = null;
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                if (f.getType() != type || Modifier.isStatic(f.getModifiers())) continue;
                if (match != null) {
                    return null;
}
                match = f;
}
            if (match == null) continue;
            return match;
}
        return match;
}
    public static Method method(String owner, String mcp, Class<?> ... params) {
        StringBuilder key = new StringBuilder(owner).append('#').append(mcp);
        for (Class<?> p : params) {
            key.append(';').append(p.getName());
}
        Method cached = METHOD_CACHE.get(key.toString());
        if (cached != null) {
            return cached;
}
        Class<?> cls = InjectMappingBridge.findClass(owner);
        if (cls == null) {
            return null;
}
        Method found = null;
        for (String candidate : InjectMappingBridge.methodNames(owner, mcp)) {
            if (candidate != null && (found = InjectMappingBridge.declaredMethod(cls, candidate, params)) != null) break;
}
        if (found == null) {
            found = InjectMappingBridge.methodByParams(cls, params);
}
        if (found == null) {
            if (FAILURES.add("method " + owner + "." + mcp)) {
                InjectLog.line("unresolved method " + owner + "." + mcp);
}
            return null;
}
        found.setAccessible(true);
        METHOD_CACHE.put(key.toString(), found);
        return found;
}
    private static Method declaredMethod(Class<?> cls, String name, Class<?>[] params) {
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            try {
                return c.getDeclaredMethod(name, params);
}
            catch (NoSuchMethodException noSuchMethodException) {
                continue;
}
}
        return null;
}
    private static Method methodByParams(Class<?> cls, Class<?>[] params) {
        Method match = null;
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            for (Method m2 : c.getDeclaredMethods()) {
                Class<?>[] actual = m2.getParameterTypes();
                if (actual.length != params.length) continue;
                boolean same = true;
                for (int i = 0; i < actual.length; ++i) {
                    if (actual[i] == params[i]) continue;
                    same = false;
                    break;
}
                if (!same) continue;
                if (match != null) {
                    return null;
}
                match = m2;
}
            if (match == null) continue;
            return match;
}
        return match;
}
}