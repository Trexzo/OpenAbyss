/*
 * Decompiled with CFR 0.152.
 */
package Abyss.inject;

import Abyss.ASM.Util.MappingKind;
import Abyss.inject.InjectLog;
import Abyss.inject.InjectRemapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

final class InjectObfuscation {
    private static volatile MappingKind naming;
    private static volatile Tables tables;
    private static volatile boolean prepared;

    private InjectObfuscation() {
}
    static synchronized void prepare() {
        if (prepared) {
            return;
}
        prepared = true;
        InjectObfuscation.resolveNaming();
        if (naming != MappingKind.SRG) {
            try {
                tables = Tables.load(naming);
}
            catch (Throwable t2) {
                InjectLog.line("mapping tables failed to load: " + t2);
                tables = null;
}
}
}
    static MappingKind naming() {
        MappingKind resolved = naming;
        if (resolved != null) {
            return resolved;
}
        InjectObfuscation.prepare();
        return naming;
}
    private static void resolveNaming() {
        Class<?> minecraft;
        ClassLoader loader = InjectObfuscation.class.getClassLoader();
        try {
            minecraft = Class.forName("net.minecraft.client.Minecraft", false, loader);
}
        catch (Throwable absent) {
            naming = MappingKind.NOTCH;
            InjectLog.line("the game is obfuscated -- client classes will be remapped as they load");
            return;
}
        naming = InjectObfuscation.has(minecraft, "func_71410_x") ? MappingKind.SRG : MappingKind.MCP;
        InjectLog.line(naming == MappingKind.SRG ? "the game uses SRG members -- no remapping needed" : "the game is fully named -- client classes will be remapped as they load");
}
    private static boolean has(Class<?> owner, String method) {
        try {
            owner.getDeclaredMethod(method, new Class[0]);
            return true;
}
        catch (Throwable missing) {
            return false;
}
}
    static boolean needsRemapping() {
        return InjectObfuscation.naming() != MappingKind.SRG;
}
    static byte[] remap(String internalName, byte[] classfile) {
        Tables active = tables;
        if (active == null) {
            return classfile;
}
        try {
            return InjectRemapper.remap(classfile, active.mapper);
}
        catch (Throwable t2) {
            StackTraceElement[] frames = t2.getStackTrace();
            InjectLog.line("remap failed for " + internalName + ": " + t2 + (frames.length > 0 ? " at " + frames[0] : ""));
            return classfile;
}
}
    static String mapDescriptor(String mcpDescriptor) {
        if (mcpDescriptor == null) {
            return null;
}
        MappingKind kind = InjectObfuscation.naming();
        if (kind == null || kind == MappingKind.SRG) {
            return mcpDescriptor;
}
        Tables active = tables;
        if (active == null) {
            return mcpDescriptor;
}
        return active.mapper.mapDesc(mcpDescriptor);
}
    private static void rememberInto(Map<String, String> seen, Map<String, String> flat, String key, String value) {
        String previous = seen.put(key, value);
        if (previous == null) {
            flat.put(key, value);
        } else if (!previous.equals(value)) {
            flat.remove(key);
}
}
    private static BufferedReader open(String resource) throws IOException {
        InputStream in = InjectObfuscation.class.getResourceAsStream(resource);
        if (in == null) {
            throw new IOException("missing resource " + resource);
}
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8), 65536);
}
    private static void close(BufferedReader reader) {
        try {
            reader.close();
}
        catch (IOException iOException) {
            // empty catch block
}
}
    private static final class Tables {
        final InjectRemapper.MemberMapper mapper;
        final Map<String, String> classes = new HashMap<String, String>();
        final Map<String, String> methodExact = new HashMap<String, String>();
        final Map<String, String> methodFlat = new HashMap<String, String>();
        final Map<String, String> fieldExact = new HashMap<String, String>();
        final Map<String, String> fieldFlat = new HashMap<String, String>();

        Tables(MappingKind target) {
            this.mapper = new InjectRemapper.MemberMapper(){

                @Override
                public String mapClass(String internalName) {
                    String mapped = classes.get(internalName);
                    return mapped == null ? internalName : mapped;
}
                @Override
                public String mapMethodName(String owner, String name, String descriptor) {
                    String override;
                    if (Tables.isSrgName(name) && (override = methodFlat.get(name + "\t" + descriptor)) != null) {
                        return override;
}
                    if (!this.isGameClass(owner)) {
                        return name;
}
                    String exact = methodExact.get(owner + "\t" + name + "\t" + descriptor);
                    if (exact != null) {
                        return exact;
}
                    String flat = methodFlat.get(name + "\t" + descriptor);
                    return flat == null ? name : flat;
}
                @Override
                public String mapFieldName(String owner, String name) {
                    String inherited;
                    if (Tables.isSrgName(name) && (inherited = fieldFlat.get(name)) != null) {
                        return inherited;
}
                    if (!this.isGameClass(owner)) {
                        return name;
}
                    String exact = fieldExact.get(owner + "\t" + name);
                    if (exact != null) {
                        return exact;
}
                    String flat = fieldFlat.get(name);
                    return flat == null ? name : flat;
}
                private boolean isGameClass(String internalName) {
                    return internalName != null && (internalName.startsWith("net/minecraft/") || classes.containsKey(internalName));
}
            };
}
        private static boolean isSrgName(String name) {
            return name.startsWith("func_") || name.startsWith("field_");
}
        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        static Tables load(MappingKind target) throws IOException {
            Tables out = new Tables(target);
            boolean notch = target == MappingKind.NOTCH;
            HashMap<String, String> notchMethodExact = new HashMap<String, String>();
            HashMap notchMethodFlat = new HashMap();
            HashMap<String, String> notchFieldExact = new HashMap<String, String>();
            HashMap notchFieldFlat = new HashMap();
            if (notch) {
                HashMap seenNotchMethods = new HashMap();
                HashMap seenNotchFields = new HashMap();
                BufferedReader reader = InjectObfuscation.open("/assets/abyss/asm/mcp-notch.srg");
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        OwnerName right;
                        OwnerName left;
                        String[] p = line.trim().split("\\s+");
                        if (p.length == 3 && "CL:".equals(p[0])) {
                            out.classes.put(p[1], p[2]);
                            continue;
}
                        if (p.length == 5 && "MD:".equals(p[0])) {
                            left = OwnerName.of(p[1]);
                            right = OwnerName.of(p[3]);
                            notchMethodExact.put(left.owner + "\t" + left.name + "\t" + p[2], right.name);
                            InjectObfuscation.rememberInto(seenNotchMethods, notchMethodFlat, left.name + "\t" + p[2], right.name);
                            continue;
}
                        if (p.length != 3 || !"FD:".equals(p[0])) continue;
                        left = OwnerName.of(p[1]);
                        right = OwnerName.of(p[2]);
                        notchFieldExact.put(left.owner + "\t" + left.name, right.name);
                        InjectObfuscation.rememberInto(seenNotchFields, notchFieldFlat, left.name, right.name);
}
}
                finally {
                    InjectObfuscation.close(reader);
}
}
            int methods = 0;
            int fields = 0;
            HashMap seenMethodFlat = new HashMap();
            HashMap seenFieldFlat = new HashMap();
            BufferedReader reader = InjectObfuscation.open("/assets/abyss/asm/mcp-srg.srg");
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String notchName;
                    String runtimeName;
                    OwnerName right;
                    OwnerName left;
                    String[] p = line.trim().split("\\s+");
                    if (p.length == 5 && "MD:".equals(p[0])) {
                        left = OwnerName.of(p[1]);
                        right = OwnerName.of(p[3]);
                        runtimeName = left.name;
                        if (notch) {
                            notchName = (String)notchMethodExact.get(left.owner + "\t" + left.name + "\t" + p[2]);
                            if (notchName == null) {
                                notchName = (String)notchMethodFlat.get(left.name + "\t" + p[2]);
}
                            if (notchName == null) continue;
                            runtimeName = notchName;
}
                        out.methodExact.put(right.owner + "\t" + right.name + "\t" + p[4], runtimeName);
                        InjectObfuscation.rememberInto(seenMethodFlat, out.methodFlat, right.name + "\t" + p[4], runtimeName);
                        ++methods;
                        continue;
}
                    if (p.length != 3 || !"FD:".equals(p[0])) continue;
                    left = OwnerName.of(p[1]);
                    right = OwnerName.of(p[2]);
                    runtimeName = left.name;
                    if (notch) {
                        notchName = (String)notchFieldExact.get(left.owner + "\t" + left.name);
                        if (notchName == null) {
                            notchName = (String)notchFieldFlat.get(left.name);
}
                        if (notchName == null) continue;
                        runtimeName = notchName;
}
                    out.fieldExact.put(right.owner + "\t" + right.name, runtimeName);
                    InjectObfuscation.rememberInto(seenFieldFlat, out.fieldFlat, right.name, runtimeName);
                    ++fields;
}
}
            finally {
                InjectObfuscation.close(reader);
}
            InjectLog.line("remap tables for " + target.name() + ": " + out.classes.size() + " classes, " + methods + " methods (" + out.methodFlat.size() + " flat), " + fields + " fields (" + out.fieldFlat.size() + " flat)");
            return out;
}
        private static final class OwnerName {
            final String owner;
            final String name;

            static OwnerName of(String qualified) {
                int slash = qualified.lastIndexOf(47);
                return new OwnerName(qualified.substring(0, slash), qualified.substring(slash + 1));
}
            private OwnerName(String owner, String name) {
                this.owner = owner;
                this.name = name;
}
}
}
}