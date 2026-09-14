/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.MethodNode
 */
package Abyss.inject;

import Abyss.ASM.ClassTransform;
import Abyss.ASM.TransformerRegistry;
import Abyss.ASM.Util.AsmUtil;
import Abyss.ASM.Util.MappingKind;
import Abyss.inject.InjectBootstrap;
import Abyss.inject.InjectHookRegistry;
import Abyss.inject.InjectHookTransformer;
import Abyss.inject.InjectHooks;
import Abyss.inject.InjectLog;
import Abyss.inject.InjectMappingBridge;
import Abyss.inject.InjectObfuscation;
import Abyss.inject.mem.MemLoader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public final class InjectNativeBridge {
    private static final boolean DUMP_TARGETS = false;
    private static final boolean SAFE_MODE = InjectNativeBridge.enabledByDefault("abyss.safemode");
    private static final ThreadLocal<Set<String>> REMAPPING = new ThreadLocal();
    private static final Map<String, ClassTransform> ALIASES = new LinkedHashMap<String, ClassTransform>();
    private static final Set<String> IN_FLIGHT = Collections.newSetFromMap(new ConcurrentHashMap());
    private static final Set<String> PATCHED = Collections.newSetFromMap(new ConcurrentHashMap());
    private static final Map<String, Set<String>> CALLBACK_MEMBERS = new HashMap<String, Set<String>>();
    private static volatile Set<String> ownClasses;
    private static volatile InjectHookTransformer transformer;
    private static boolean installed;

    private InjectNativeBridge() {
}
    private static boolean enabledByDefault(String property) {
        String flag = System.getProperty(property);
        return !"0".equals(flag) && !"false".equalsIgnoreCase(flag);
}
    public static synchronized void install() {
        if (installed) {
            InjectLog.line("already installed, ignoring");
            return;
}
        installed = true;
        try {
            InjectObfuscation.prepare();
            InjectNativeBridge.loadMappings();
            InjectNativeBridge.indexCoremod();
            InjectHooks.register();
            InjectLog.line("supplementary hooks registered: " + InjectHookRegistry.size());
            transformer = new InjectHookTransformer(new Class[0]);
            InjectNativeBridge.warmUp();
            InjectNativeBridge.checkCallbacks();
}
        catch (Throwable t2) {
            InjectLog.throwable("install failed", t2);
}
}
    private static void loadMappings() {
        try {
            String probe = AsmUtil.b(MappingKind.NOTCH, "net/minecraft/client/Minecraft");
            InjectLog.line("mappings loaded, namespace " + (Object)((Object)InjectObfuscation.naming()) + ", Minecraft is " + probe + " under NOTCH");
}
        catch (Throwable t2) {
            InjectLog.throwable("mapping tables did not load", t2);
}
}
    private static void indexCoremod() {
        Map<String, ClassTransform> registry;
        try {
            registry = TransformerRegistry.H();
}
        catch (Throwable t2) {
            InjectLog.throwable("coremod registry unavailable -- only supplementary hooks will apply", t2);
            return;
}
        int aliases = 0;
        for (Map.Entry<String, ClassTransform> entry : registry.entrySet()) {
            String mcp = entry.getKey();
            ClassTransform owner = entry.getValue();
            ALIASES.put(mcp, owner);
            for (MappingKind kind : MappingKind.values()) {
                String mapped;
                try {
                    mapped = AsmUtil.b(kind, mcp);
}
                catch (Throwable t3) {
                    InjectLog.swallowed(t3);
                    continue;
}
                if (mapped == null || mapped.equals(mcp) || ALIASES.put(mapped, owner) != null) continue;
                ++aliases;
}
}
        InjectLog.line("coremod indexed: " + registry.size() + " transformers, " + aliases + " obfuscated aliases");
}
    public static void start() {
        InjectBootstrap.requestStart();
        InjectLog.line("client construction queued for the next tick");
}
    public static byte[] transform(String internalName, byte[] classfile) {
        if (internalName == null || classfile == null || classfile.length == 0) {
            return classfile;
}
        try {
            if (InjectNativeBridge.isOurs(internalName)) {
                if (InjectLog.ENABLED && internalName.contains("Hooks")) {
                    InjectLog.line("transform HOOK class: " + internalName + " (" + classfile.length + " bytes)");
}
                return InjectNativeBridge.remapOwn(internalName, classfile);
}
            if (InjectNativeBridge.skip(internalName)) {
                return classfile;
}
            byte[] current = InjectNativeBridge.coremod(internalName, classfile);
            return InjectNativeBridge.supplementary(internalName, current, classfile);
}
        catch (Throwable t2) {
            InjectLog.line("transform failed for " + internalName + ": " + t2);
            return classfile;
}
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static byte[] coremod(String internalName, byte[] classfile) {
        ClassTransform owner = InjectNativeBridge.lookup(internalName);
        if (owner == null) {
            return classfile;
}
        if (SAFE_MODE && InjectObfuscation.naming() != MappingKind.SRG && InjectNativeBridge.shouldSafeModeSkip(owner.E())) {
            if (InjectLog.ENABLED) {
                InjectLog.line("safe-mode: structural hooks replace the coremod transform for " + owner.E() + " (loaded as " + internalName + ")");
}
            InjectNativeBridge.armFallbackFor(owner.E());
            return classfile;
}
        if (!IN_FLIGHT.add(internalName)) {
            if (InjectLog.ENABLED) {
                InjectLog.line("recursive transform skipped: " + internalName);
}
            return classfile;
}
        try {
            byte[] patched = owner.S(classfile);
            if (patched == null || patched == classfile) {
                if (InjectLog.ENABLED) {
                    InjectLog.line("coremod target matched but unchanged: " + owner.E() + " (loaded as " + internalName + ")");
}
                InjectNativeBridge.armFallbackFor(owner.E());
                byte[] byArray = classfile;
                return byArray;
}
            if (PATCHED.add(internalName) && InjectLog.ENABLED) {
                InjectLog.line("coremod transformed " + owner.E() + (internalName.equals(owner.E()) ? "" : " (loaded as " + internalName + ")"));
}
            byte[] byArray = patched;
            return byArray;
}
        catch (Throwable t2) {
            if (InjectLog.ENABLED) {
                InjectLog.line("coremod transform failed for " + owner.E() + ": " + t2.getClass().getName() + ": " + t2.getMessage());
}
            InjectLog.swallowed(t2);
            InjectNativeBridge.armFallbackFor(owner.E());
            byte[] byArray = classfile;
            return byArray;
}
        finally {
            IN_FLIGHT.remove(internalName);
}
}
    private static boolean shouldSafeModeSkip(String mcpOwner) {
        return InjectHooks.hasFallback(mcpOwner) || "net/minecraft/client/gui/GuiMainMenu".equals(mcpOwner);
}
    private static void armFallbackFor(String mcpOwner) {
        try {
            if (!InjectHooks.armFallback(mcpOwner)) {
                return;
}
            InjectHookTransformer active = transformer;
            if (active != null) {
                active.refreshTargets();
}
}
        catch (Throwable t2) {
            InjectLog.swallowed(t2);
}
}
    private static byte[] supplementary(String internalName, byte[] current, byte[] original) {
        InjectHookTransformer active = transformer;
        if (active == null) {
            return current;
}
        byte[] patched = active.transform(null, internalName, null, null, current);
        byte[] out = patched == null ? current : patched;
        return out;
}
    private static ClassTransform lookup(String internalName) {
        ClassTransform direct = ALIASES.get(internalName);
        if (direct != null) {
            return direct;
}
        try {
            String canonical = AsmUtil.b(internalName);
            if (canonical != null && !canonical.equals(internalName)) {
                return ALIASES.get(canonical);
}
}
        catch (Throwable t2) {
            InjectLog.swallowed(t2);
}
        return null;
}
    private static boolean skip(String internalName) {
        String dotted = internalName.replace('/', '.');
        return dotted.startsWith("java.") || dotted.startsWith("javax.") || dotted.startsWith("sun.") || dotted.startsWith("com.sun.") || dotted.startsWith("jdk.") || dotted.startsWith("org.") || dotted.startsWith("com.google.") || dotted.startsWith("net.minecraftforge.") || dotted.startsWith("net.minecraft.launchwrapper.") || dotted.startsWith("optifine.") || dotted.startsWith("com.spiderfrog.") || dotted.startsWith("Abyss.");
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static byte[] remapOwn(String internalName, byte[] classfile) {
        Set<String> active = REMAPPING.get();
        if (active != null && active.contains(internalName)) {
            if (InjectLog.ENABLED) {
                InjectLog.line("remapOwn SKIP (recursion guard): " + internalName);
}
            return classfile;
}
        if (active == null) {
            active = new HashSet<String>();
            REMAPPING.set(active);
}
        active.add(internalName);
        try {
            if (!InjectObfuscation.needsRemapping()) {
                if (InjectLog.ENABLED) {
                    InjectLog.line("remapOwn SKIP (no remap needed): " + internalName);
}
                byte[] byArray = classfile;
                return byArray;
}
            if (InjectNativeBridge.skipRemap(internalName)) {
                if (InjectLog.ENABLED) {
                    InjectLog.line("remapOwn SKIP (skipRemap): " + internalName);
}
                byte[] byArray = classfile;
                return byArray;
}
            byte[] remapped = InjectObfuscation.remap(internalName, classfile);
            if (InjectLog.ENABLED) {
                InjectLog.line("remapOwn OK: " + internalName + " (" + classfile.length + " -> " + remapped.length + " bytes" + (remapped == classfile ? ", UNCHANGED" : ", changed") + ")");
}
            byte[] byArray = remapped;
            return byArray;
}
        finally {
            active.remove(internalName);
            if (active.isEmpty()) {
                REMAPPING.remove();
}
}
}
    private static boolean isOurs(String internalName) {
        Set<String> known = ownClasses;
        if (known != null && !known.isEmpty()) {
            return known.contains(internalName);
}
        return internalName.startsWith("Abyss/");
}
    private static boolean skipRemap(String internalName) {
        return internalName.contains("$$Lambda") || internalName.contains("/0x") || internalName.startsWith("Abyss/inject/InjectRemapper") || internalName.startsWith("Abyss/inject/InjectObfuscation");
}
    public static synchronized void retransformLoaded() {
        if (transformer == null) {
            InjectLog.line("retransform skipped: install() has not run");
            return;
}
        try {
            List<String> names = InjectNativeBridge.retransformTargets();
            InjectLog.line("asking for " + names.size() + " already-loaded names");
            InjectNativeBridge.retransform(names.toArray(new String[names.size()]));
            for (String line : transformer.drain()) {
                InjectLog.line("  " + line);
}
            ArrayList<String> unresolved = new ArrayList<String>(InjectMappingBridge.failures());
            if (!unresolved.isEmpty()) {
                InjectLog.line("unresolved mappings (" + unresolved.size() + "):");
                for (String failure : unresolved) {
                    InjectLog.line("  " + failure);
}
}
}
        catch (Throwable t2) {
            InjectLog.throwable("retransform failed", t2);
}
}
    private static List<String> retransformTargets() {
        HashSet<String> wanted = new HashSet<String>();
        wanted.addAll(ALIASES.keySet());
        for (String owner : InjectHookRegistry.owners()) {
            wanted.add(owner);
            for (MappingKind kind : MappingKind.values()) {
                try {
                    String mapped = AsmUtil.b(kind, owner);
                    if (mapped == null) continue;
                    wanted.add(mapped);
}
                catch (Throwable t2) {
                    InjectLog.swallowed(t2);
}
}
}
        ArrayList<String> out = new ArrayList<String>(wanted.size());
        for (String name : wanted) {
            out.add(name.replace('/', '.'));
}
        Collections.sort(out);
        return out;
}
    public static String[] transformTargets() {
        HashSet<String> names = new HashSet<String>();
        names.addAll(ALIASES.keySet());
        for (String owner : InjectHookRegistry.owners()) {
            names.add(owner.replace('.', '/'));
            for (MappingKind kind : MappingKind.values()) {
                try {
                    String mapped = AsmUtil.b(kind, owner);
                    if (mapped == null) continue;
                    names.add(mapped);
}
                catch (Throwable t2) {
                    InjectLog.swallowed(t2);
}
}
}
        return names.toArray(new String[names.size()]);
}
    /*
     * WARNING - void declaration
     */
    public static void prewarm() {
        int var6_11 = 0;
        MappingKind kind = InjectObfuscation.naming();
        if (kind != MappingKind.SRG) {
            InjectLog.line("prewarm skipped on " + kind.name() + " runtime -- classes stay lazy for launcher compatibility");
            return;
}
        ClassLoader loader = InjectNativeBridge.class.getClassLoader();
        Set<String> own = ownClasses;
        if (own == null || own.isEmpty()) {
            own = MemLoader.classNames();
}
        int defined = 0;
        int failed = 0;
        for (String string : own) {
            if (string == null || !string.startsWith("Abyss/")) continue;
            try {
                Class.forName(string.replace('/', '.'), false, loader);
                ++defined;
}
            catch (Throwable t2) {
                ++failed;
}
}
        int spellings = 0;
        for (String internal : InjectNativeBridge.transformTargets()) {
            try {
                Class.forName(internal.replace('/', '.'), false, loader);
                ++spellings;
}
            catch (Throwable throwable) {
                // empty catch block
}
}
        boolean bl = false;
        for (String internal : own) {
            if (internal == null || !internal.startsWith("Abyss/event/invoker/") && !internal.startsWith("Abyss/event/binder/")) continue;
            try {
                Class.forName(internal.replace('/', '.'), true, loader);
}
            catch (Throwable throwable) {}
}
        try {
            Class.forName("Abyss.util.ChatFormatting", true, loader);
            ++var6_11;
}
        catch (Throwable throwable) {
            // empty catch block
}
        InjectLog.line("prewarm: defined " + defined + " own classes (" + failed + " refused), " + spellings + " target spellings, initialized " + (int)var6_11 + " pure-logic classes");
}
    public static void flushTransformLog() {
        InjectHookTransformer active = transformer;
        if (active == null || !active.hasLog()) {
            return;
}
        for (String line : active.drain()) {
            InjectLog.line("  " + line);
}
}
    private static native void retransform(String[] var0);

    private static void warmUp() {
        try {
            ownClasses = InjectNativeBridge.readOwnJar();
            byte[] sample = InjectNativeBridge.readOwn("Abyss/inject/InjectLog.class");
            if (sample != null) {
                InjectNativeBridge.transform("Abyss/inject/InjectLog", sample);
                InjectHookTransformer active = transformer;
                if (active != null) {
                    active.warmUp(sample);
}
}
            InjectLog.line("warm-up complete: " + (ownClasses == null ? 0 : ownClasses.size()) + " own classes known" + (sample == null ? ", no sample class in the jar" : ""));
}
        catch (Throwable t2) {
            InjectLog.throwable("warm-up failed", t2);
}
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Set<String> readOwnJar() {
        HashSet<String> found = new HashSet<String>();
        JarFile jar = null;
        try {
            jar = new JarFile(InjectNativeBridge.ownJarPath());
            int shaded = 0;
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (!name.endsWith(".class")) continue;
                if ((name = name.substring(0, name.length() - ".class".length())).startsWith("org/") || name.startsWith("com/") || name.startsWith("io/") || name.startsWith("net/")) {
                    ++shaded;
                    continue;
}
                found.add(name);
}
            InjectLog.line("own classes: " + found.size() + " (" + shaded + " shaded, left alone)");
}
        catch (Throwable t2) {
            try {
                InjectLog.line("cannot list own jar (" + t2 + ") -- falling back to the Abyss/ prefix");
}
            catch (Throwable throwable) {
                InjectNativeBridge.close(jar);
                throw throwable;
}
            InjectNativeBridge.close(jar);
}
        InjectNativeBridge.close(jar);
        return found;
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private static byte[] readOwn(String entry) {
        JarFile jar = null;
        try {
            jar = new JarFile(InjectNativeBridge.ownJarPath());
            JarEntry found = jar.getJarEntry(entry);
            if (found == null) {
                return null;
}
            InputStream in = jar.getInputStream(found);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] chunk = new byte[8192];
            int read = in.read(chunk);
            while (read > 0) {
                out.write(chunk, 0, read);
                read = in.read(chunk);
}
            byte[] byArray = out.toByteArray();
            in.close();
            return byArray;
}
        catch (Throwable throwable) {
            return null;
}
        finally {
            InjectNativeBridge.close(jar);
}
}
    private static File ownJarPath() throws Exception {
        String location = InjectNativeBridge.class.getProtectionDomain().getCodeSource().getLocation().toString();
        if (location.startsWith("jar:")) {
            int bang = location.indexOf("!/");
            location = location.substring(4, bang < 0 ? location.length() : bang);
}
        return new File(new URI(location));
}
    private static void close(JarFile jar) {
        if (jar == null) {
            return;
}
        try {
            jar.close();
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void dump(String name, byte[] bytes) {
        try {
            File dir = new File(System.getProperty("java.io.tmpdir"), "abyss-inject-dump");
            if (!dir.isDirectory() && !dir.mkdirs()) {
                return;
}
            File out = new File(dir, name.replace('/', '.') + ".class");
            try (FileOutputStream stream = new FileOutputStream(out);){
                stream.write(bytes);
}
            InjectLog.line("dumped " + out.getName() + " (" + bytes.length + " bytes)");
}
        catch (Throwable t2) {
            InjectLog.line("could not dump " + name + ": " + t2);
}
}
    private static void checkCallbacks() {
        int checked = 0;
        int wrong = 0;
        for (String owner : InjectHookRegistry.owners()) {
            for (InjectHookRegistry.Hook hook : InjectHookRegistry.forOwner(owner)) {
                ++checked;
                String problem = InjectNativeBridge.describeMismatch(hook);
                if (problem == null) continue;
                ++wrong;
                InjectLog.line("BAD CALLBACK " + hook.owner + "." + hook.method + " @" + (Object)((Object)hook.position) + " -> " + problem);
}
}
        InjectLog.line("callbacks checked: " + checked + (wrong == 0 ? ", all present" : ", " + wrong + " WRONG"));
}
    private static String describeMismatch(InjectHookRegistry.Hook hook) {
        Set<String> members = InjectNativeBridge.membersOf(hook.callbackOwner);
        if (members.isEmpty()) {
            return null;
}
        if (members.contains(hook.callbackName + hook.callbackDescriptor)) {
            return null;
}
        StringBuilder sameName = new StringBuilder();
        for (String member : members) {
            if (!member.startsWith(hook.callbackName + "(")) continue;
            sameName.append(" saw ").append(member.substring(hook.callbackName.length()));
}
        return hook.callbackOwner + "." + hook.callbackName + hook.callbackDescriptor + " is not a static method there;" + (sameName.length() == 0 ? " nothing of that name" : sameName);
}
    private static Set<String> membersOf(String internalName) {
        Set<String> known = CALLBACK_MEMBERS.get(internalName);
        if (known != null) {
            return known;
}
        known = new HashSet<String>();
        byte[] bytes = InjectNativeBridge.readOwn(internalName + ".class");
        if (bytes != null) {
            try {
                ClassNode node = new ClassNode();
                new ClassReader(bytes).accept((ClassVisitor)node, 7);
                for (Object raw : node.methods) {
                    MethodNode method = (MethodNode)raw;
                    if ((method.access & 8) == 0) continue;
                    known.add(method.name + method.desc);
}
}
            catch (Throwable t2) {
                InjectLog.line("cannot read " + internalName + " out of our own jar: " + t2);
}
}
        CALLBACK_MEMBERS.put(internalName, known);
        return known;
}
}