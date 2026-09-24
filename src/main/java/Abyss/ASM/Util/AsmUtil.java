/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.FieldNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package Abyss.ASM.Util;

import Abyss.ASM.Util.FieldMapping;
import Abyss.ASM.Util.FieldRef;
import Abyss.ASM.Util.MappingKind;
import Abyss.ASM.Util.MethodMapping;
import Abyss.ASM.Util.MethodRef;
import Abyss.ASM.Util.OwnerNamePair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class AsmUtil {
    private static volatile MappingKind i;
    private static String[] e;
    private static Map<MappingKind, Map<FieldRef, FieldMapping>> P;
    private static Map j;
        private static String c;
    private static Map<MappingKind, Map<FieldRef, FieldMapping>> B;
    private static Map<MappingKind, Map<MethodRef, MethodMapping>> Z;
    private static volatile boolean K;
    private static Map<MappingKind, Map<String, String>> N;
    private static Map<MappingKind, Map<String, String>> Y;
    private static Logger I;
    private static long[] g;
    private static long a;
    private static String[] d;
    
    private static Map f;
    private static String C;
    private static ThreadLocal<MappingKind> r;
    private static Map<MappingKind, Map<MethodRef, MethodMapping>> b;
    private static ThreadLocal<MappingKind> v;
    private static Integer[] h;

    public static void M(List<MappingKind> var0, MappingKind var1) {
        if (var1 != null && !var0.contains((Object)var1)) {
            var0.add(var1);
}
}
    public static MappingKind o(String var0) {
        if (var0 == null) {
            return AsmUtil.N();
}
        for (MappingKind var6 : MappingKind.values()) {
            if (!var6.name().equalsIgnoreCase(var0)) continue;
            return var6;
}
        return AsmUtil.N();
}
    public static void E(Map<String, Class<?>> var0) {
        Set<String> var3 = var0.keySet();
        boolean var4 = var3.contains("net.minecraft.client.Minecraft");
        boolean var5 = var3.contains("ave");
        MappingKind var6 = AsmUtil.Z();
        i = var6 == null ? AsmUtil.j(var0, var4, var5) : var6;
        I.info("ASM injection namespace selected: {} (minecraftClassLoaded={}, notchMinecraftClassLoaded={}, reason={})", new Object[]{AsmUtil.N().name(), var4, var5, var6 == null ? AsmUtil.d(var0, var4, var5) : "build-profile"});
}
    public static void U(Set<String> var0, String var1, String var2, boolean var3) {
        boolean var6 = false;
        for (MappingKind var10 : MappingKind.values()) {
            String var11 = AsmUtil.b(var10, var1);
            FieldMapping var12 = B.get((Object)var10).get(new FieldRef(var11, var2));
            if (var12 == null) continue;
            var0.add(FieldMapping.U(var12));
            var6 = true;
}
        if (!var6 && var3 && AsmUtil.H(var2)) {
            for (MappingKind var16 : MappingKind.values()) {
                for (Map.Entry<FieldRef, FieldMapping> var18 : B.get((Object)var16).entrySet()) {
                    if (!FieldRef.Z(var18.getKey()).equals(var2)) continue;
                    var0.add(FieldMapping.U(var18.getValue()));
}
}
}
}
    public static boolean v(FieldInsnNode var0, String var1, String var2, String ... var3) {
        String var6 = AsmUtil.b(var1);
        String var7 = AsmUtil.n(var2);
        return var0.owner.equals(AsmUtil.b(AsmUtil.c(), var6)) && var0.desc.equals(AsmUtil.Y(AsmUtil.c(), var7)) && AsmUtil.j(var0.name, AsmUtil.G(var6, var3));
}
    public static void O() {
        r.remove();
}
    public static void a() {
        v.remove();
    }
    public static boolean H(ClassNode var0) {
        for (Object var4 : var0.methods) {
            MethodNode var5 = (MethodNode)var4;
            if (!var5.name.startsWith("func_")) continue;
            return true;
}
        for (Object var7 : var0.fields) {
            FieldNode var8 = (FieldNode)var7;
            if (!var8.name.startsWith("field_")) continue;
            return true;
}
        return false;
}
    public static void h(MappingKind var0, String var1, String var2) {
        Y.get((Object)var0).put(var1, var2);
        N.get((Object)var0).put(var2, var1);
}
    public static boolean E(Class<?> var0, String var1) {
        for (Field var7 : var0.getDeclaredFields()) {
            if (!var7.getName().equals(var1)) continue;
            return true;
}
        return false;
}
    public static Set<String> i(String var0, String ... var1) {
        String var4 = AsmUtil.b(var0);
        LinkedHashSet<String> var5 = new LinkedHashSet<String>();
        for (String var9 : var1) {
            var5.add(var9);
            AsmUtil.U(var5, var4, var9, true);
}
        LinkedHashSet<String> var13 = new LinkedHashSet<String>();
        for (String var15 : var5) {
            var13.add(var15);
            for (MappingKind var12 : MappingKind.values()) {
                AsmUtil.o(var13, var12, var4, var15, true);
}
}
        return var13;
}
    public static Set<String> D(String var0, String var1, String var2) {
        LinkedHashSet<String> var5 = new LinkedHashSet<String>();
        var5.add(AsmUtil.q(var0, var1, var2));
        if (AsmUtil.H(var2)) {
            for (MappingKind var9 : MappingKind.values()) {
                String var10 = AsmUtil.Y(var9, var1);
                for (Map.Entry<MethodRef, MethodMapping> var12 : b.get((Object)var9).entrySet()) {
                    MethodRef var13 = var12.getKey();
                    if (!MethodRef.Q(var13).equals(var2) || !MethodRef.V(var13).equals(var10)) continue;
                    var5.add(MethodMapping.Z(var12.getValue()));
}
}
}
        return var5;
}
    public static void E(Set<String> var0, String var1, String var2) {
        var0.add(var2);
        FieldMapping var5 = P.get((Object)AsmUtil.c()).get(new FieldRef(var1, var2));
        if (var5 != null) {
            var0.add(FieldMapping.U(var5));
}
        for (Map.Entry<FieldRef, FieldMapping> var7 : P.get((Object)AsmUtil.c()).entrySet()) {
            if (!FieldRef.Z(var7.getKey()).equals(var2)) continue;
            var0.add(FieldMapping.U(var7.getValue()));
}
}
    public static Type l(MappingKind var0, Type var1) {
        switch (var1.getSort()) {
            case 9: {
                Type var4 = AsmUtil.l(var0, var1.getElementType());
                StringBuilder var5 = new StringBuilder();
                for (int var6 = 0; var6 < var1.getDimensions(); ++var6) {
                    var5.append('[');
}
                var5.append(var4.getDescriptor());
                return Type.getType((String)var5.toString());
}
            case 10: {
                return Type.getObjectType((String)AsmUtil.b(var0, AsmUtil.b(var1.getInternalName())));
}
}
        return var1;
}
    public static void U(Set<String> var0, String var1, String var2, String var3) {
        var0.add(var2);
        MethodMapping var6 = Z.get((Object)AsmUtil.c()).get(new MethodRef(var1, var2, var3));
        if (var6 != null) {
            var0.add(MethodMapping.Z(var6));
}
        for (Map.Entry<MethodRef, MethodMapping> var8 : Z.get((Object)AsmUtil.c()).entrySet()) {
            MethodRef var9 = var8.getKey();
            if (!MethodRef.Q(var9).equals(var2) || !MethodRef.V(var9).equals(var3)) continue;
            var0.add(MethodMapping.Z(var8.getValue()));
}
}
    public static MappingKind Z() {
        try {
            return null;
}
        catch (Throwable var1) {
            return null;
}
}
    public static void o(Set<String> var0, MappingKind var1, String var2, String var3, boolean var4) {
        boolean var7 = false;
        FieldMapping var8 = P.get((Object)var1).get(new FieldRef(var2, var3));
        if (var8 != null) {
            var0.add(FieldMapping.U(var8));
            var7 = true;
}
        if (!var7 && var4) {
            for (Map.Entry<FieldRef, FieldMapping> var10 : P.get((Object)var1).entrySet()) {
                if (!FieldRef.Z(var10.getKey()).equals(var3)) continue;
                var0.add(FieldMapping.U(var10.getValue()));
}
}
}
    public static String d(Map<String, Class<?>> var0, boolean var1, boolean var2) {
        Class<?> var5 = var0.get("net.minecraft.client.Minecraft");
        if (var5 != null) {
            if (AsmUtil.O(var5, "getMinecraft")) {
                return "Minecraft.getMinecraft present";
}
            if (AsmUtil.E(var5, "thePlayer")) {
                return "Minecraft.thePlayer present";
}
            if (AsmUtil.O(var5, "getMinecraft")) {
                return "Minecraft.getMinecraft present";
}
            return AsmUtil.E(var5, "thePlayer") ? "Minecraft.thePlayer present" : "net.minecraft class names without known singleton members";
}
        if (!var0.containsKey("ave") && !var2) {
            return var1 ? "net.minecraft class names" : "fallback";
}
        return "notch Minecraft class present";
}
    public static BufferedReader F(String var0) throws IOException {
        InputStream var3 = AsmUtil.class.getResourceAsStream(var0);
        if (var3 == null) {
            throw new IOException("missing resource " + var0);
}
        return new BufferedReader(new InputStreamReader(var3, StandardCharsets.UTF_8));
}
    public static String j(String var0) {
        return var0 == null ? null : var0.replace('.', '/');
}
    public static boolean B(String var0, MethodNode var1, String var2, String ... var3) {
        String var6 = AsmUtil.b(var0);
        String var7 = AsmUtil.n(var2);
        return var1.desc.equals(AsmUtil.Y(AsmUtil.c(), var7)) && AsmUtil.j(var1.name, AsmUtil.q(var6, var7, var3));
}
    public static void l(MappingKind var0, String var1, String var2, String var3, String var4, String var5, String var6) {
        Z.get((Object)var0).put(new MethodRef(var1, var2, var3), new MethodMapping(var4, var5, var6));
        b.get((Object)var0).put(new MethodRef(var4, var5, var6), new MethodMapping(var1, var2, var3));
}
    public static String q(String var0, String var1) {
        String var4 = AsmUtil.b(var0);
        String var5 = AsmUtil.J(var4, var1);
        FieldMapping var6 = P.get((Object)AsmUtil.c()).get(new FieldRef(var4, var5));
        return var6 == null ? var5 : FieldMapping.U(var6);
}
    public static String q(MappingKind var0, String var1, String var2) {
        String var4 = AsmUtil.b(var1);
        String var5 = AsmUtil.J(var4, var2);
        FieldMapping var6 = P.get((Object)var0).get(new FieldRef(var4, var5));
        return var6 == null ? var5 : FieldMapping.U(var6);
}
    public static void L(ClassNode var0) {
        MappingKind var3 = r.get();
        v.set(var3 == null ? AsmUtil.Q(var0) : var3);
}
    public static MappingKind Q(ClassNode var0) {
        if (var0 != null && var0.name != null) {
            String var3 = AsmUtil.j(var0.name);
            if (N.get((Object)MappingKind.NOTCH).containsKey(var3)) {
                return MappingKind.NOTCH;
}
            if (AsmUtil.H(var0)) {
                return MappingKind.SRG;
}
            return var3.startsWith("net/minecraft/") ? MappingKind.MCP : AsmUtil.N();
}
        return AsmUtil.N();
}
    public static void M(Set<String> var0, String var1, String var2, String var3, boolean var4) {
        boolean var7 = false;
        for (MappingKind var11 : MappingKind.values()) {
            String var12 = AsmUtil.b(var11, var1);
            String var13 = AsmUtil.E(var11, var2);
            for (Map.Entry<MethodRef, MethodMapping> var15 : b.get((Object)var11).entrySet()) {
                MethodRef var16 = var15.getKey();
                if (!MethodRef.O(var16).equals(var12) || !MethodRef.Q(var16).equals(var3) || !AsmUtil.L(MethodRef.V(var16)).equals(var13)) continue;
                var0.add(MethodMapping.Z(var15.getValue()));
                var7 = true;
}
}
        if (!var7 && var4 && AsmUtil.H(var3)) {
            for (MappingKind var20 : MappingKind.values()) {
                String var21 = AsmUtil.E(var20, var2);
                for (Map.Entry<MethodRef, MethodMapping> var23 : b.get((Object)var20).entrySet()) {
                    MethodRef var24 = var23.getKey();
                    if (!MethodRef.Q(var24).equals(var3) || !AsmUtil.L(MethodRef.V(var24)).equals(var21)) continue;
                    var0.add(MethodMapping.Z(var23.getValue()));
}
}
}
}
    public static void O(boolean var0) {
        i = null;
        K = var0;
        I.info("ASM runtime namespace: {}", new Object[]{AsmUtil.N().name()});
}
    public static Set<String> W(String var0, String var1) {
        LinkedHashSet<String> var4 = new LinkedHashSet<String>();
        var4.add(AsmUtil.J(var0, var1));
        if (AsmUtil.H(var1)) {
            for (MappingKind var8 : MappingKind.values()) {
                for (Map.Entry<FieldRef, FieldMapping> var10 : B.get((Object)var8).entrySet()) {
                    if (!FieldRef.Z(var10.getKey()).equals(var1)) continue;
                    var4.add(FieldMapping.U(var10.getValue()));
}
}
}
        return var4;
}
    private AsmUtil() {
}
    public static String b(MappingKind var0, String var1) {
        String var4 = AsmUtil.j(var1);
        String var5 = Y.get((Object)var0).get(var4);
        return var5 == null ? var4 : var5;
}
    public static OwnerNamePair K(String var0) {
        int var3 = var0.lastIndexOf(47);
        return new OwnerNamePair(var0.substring(0, var3), var0.substring(var3 + 1));
}
    public static String D(String var0) {
        return AsmUtil.b(AsmUtil.c(), AsmUtil.b(var0));
}
    public static String Y(MappingKind var0, String var1) {
        if (var1 != null && var1.indexOf(76) >= 0) {
            if (var1.charAt(0) != '(') {
                return AsmUtil.l(var0, Type.getType((String)var1)).getDescriptor();
}
            Type[] var4 = Type.getArgumentTypes((String)var1);
            for (int var5 = 0; var5 < var4.length; ++var5) {
                var4[var5] = AsmUtil.l(var0, var4[var5]);
}
            return Type.getMethodDescriptor((Type)AsmUtil.l(var0, Type.getReturnType((String)var1)), (Type[])var4);
}
        return var1;
}
    public static String q(String var0, String var1, String var2) {
        for (MappingKind var8 : MappingKind.values()) {
            String var9 = AsmUtil.b(var8, var0);
            String var10 = AsmUtil.Y(var8, var1);
            MethodMapping var11 = b.get((Object)var8).get(new MethodRef(var9, var2, var10));
            if (var11 == null) continue;
            return MethodMapping.Z(var11);
}
        return var2;
}
    public static void z(Set<String> var0, MappingKind var1, String var2, String var3, String var4, boolean var5) {
        boolean var8 = false;
        for (Map.Entry<MethodRef, MethodMapping> var10 : Z.get((Object)var1).entrySet()) {
            MethodRef var11 = var10.getKey();
            if (!MethodRef.O(var11).equals(var2) || !MethodRef.Q(var11).equals(var4) || !AsmUtil.L(MethodRef.V(var11)).equals(var3)) continue;
            var0.add(MethodMapping.Z(var10.getValue()));
            var8 = true;
}
        if (!var8 && var5) {
            for (Map.Entry<MethodRef, MethodMapping> var13 : Z.get((Object)var1).entrySet()) {
                MethodRef var14 = var13.getKey();
                if (!MethodRef.Q(var14).equals(var4) || !AsmUtil.L(MethodRef.V(var14)).equals(var3)) continue;
                var0.add(MethodMapping.Z(var13.getValue()));
}
}
}
    public static String v(String var0) {
        return AsmUtil.Y(AsmUtil.c(), AsmUtil.n(var0));
}
    public static String R(String var0) {
        return AsmUtil.v(var0);
}
    public static boolean y$r1(String var0) {
        String var3 = AsmUtil.j(var0);
        if (var3 == null) {
            return false;
}
        return var3.startsWith("net/minecraft/") ? true : N.get((Object)MappingKind.SRG).containsKey(var3) || N.get((Object)MappingKind.NOTCH).containsKey(var3);
}
    public static String E(MappingKind var0, String var1) {
        if (var1 != null && !var1.isEmpty()) {
            String var4 = AsmUtil.Y(var0, var1 + "V");
            return AsmUtil.L(var4);
}
        return "";
}
    public static String w(String var0, String var1) {
        return AsmUtil.q(var0, var1);
}
    public static String J(String var0, String var1) {
        for (MappingKind var7 : MappingKind.values()) {
            String var8 = AsmUtil.b(var7, var0);
            FieldMapping var9 = B.get((Object)var7).get(new FieldRef(var8, var1));
            if (var9 == null) continue;
            return FieldMapping.U(var9);
}
        return var1;
}
    public static void H(String var0, MappingKind var1) throws Throwable {
        try (BufferedReader var4 = AsmUtil.F(var0);){
            String var6;
            while ((var6 = var4.readLine()) != null) {
                String var7 = var6.trim();
                if (var7.isEmpty() || var7.startsWith("#")) continue;
                String[] var8 = var7.split("\\s+");
                if (var8.length == 3 && "CL:".equals(var8[0])) {
                    AsmUtil.h(var1, var8[1], var8[2]);
                    continue;
}
                if (var8.length == 5 && "MD:".equals(var8[0])) {
                    OwnerNamePair var22 = AsmUtil.K(var8[1]);
                    OwnerNamePair var23 = AsmUtil.K(var8[3]);
                    AsmUtil.l(var1, OwnerNamePair.U(var22), OwnerNamePair.A(var22), var8[2], OwnerNamePair.U(var23), OwnerNamePair.A(var23), var8[4]);
                    continue;
}
                if (var8.length != 3 || !"FD:".equals(var8[0])) continue;
                OwnerNamePair var9 = AsmUtil.K(var8[1]);
                OwnerNamePair var10 = AsmUtil.K(var8[2]);
                AsmUtil.o(var1, OwnerNamePair.U(var9), OwnerNamePair.A(var9), OwnerNamePair.U(var10), OwnerNamePair.A(var10));
}
}
        catch (IOException var21) {
            I.warn("Cannot load {} mappings from {}: {}", new Object[]{var1.name(), var0, var21.getMessage()});
}
}
    public static MappingKind c() {
        MappingKind var2 = v.get();
        return var2 == null ? AsmUtil.N() : var2;
}
    public static boolean t(String var0, MethodNode var1, String ... var2) {
        String var5 = AsmUtil.b(var0);
        String var6 = AsmUtil.n(var1.desc);
        return AsmUtil.j(var1.name, AsmUtil.q(var5, var6, var2));
}
    public static String X(String var0) {
        return AsmUtil.D(var0);
}
    public static Set<String> q(String var0, String var1, String ... var2) {
        LinkedHashSet<String> var5 = new LinkedHashSet<String>();
        for (String var9 : var2) {
            for (String var12 : AsmUtil.D(var0, var1, var9)) {
                AsmUtil.U(var5, var0, var12, var1);
}
}
        return var5;
}
    public static void X(MappingKind var0) {
        Y.put(var0, new LinkedHashMap());
        N.put(var0, new LinkedHashMap());
        Z.put(var0, new LinkedHashMap());
        b.put(var0, new LinkedHashMap());
        P.put(var0, new LinkedHashMap());
        B.put(var0, new LinkedHashMap());
}
    public static void r(String var0) {
        r.set(AsmUtil.o(var0));
}
    public static String b(String var0) {
        String var3 = AsmUtil.j(var0);
        if (var3 == null) {
            return null;
}
        for (MappingKind var7 : MappingKind.values()) {
            String var8 = N.get((Object)var7).get(var3);
            if (var8 == null) continue;
            return var8;
}
        return var3;
}
    public static boolean O(Class<?> var0, String var1) {
        for (Method var7 : var0.getDeclaredMethods()) {
            if (!var7.getName().equals(var1)) continue;
            return true;
}
        return false;
}
    public static Set<String> G(String var0, String ... var1) {
        LinkedHashSet<String> var4 = new LinkedHashSet<String>();
        for (String var8 : var1) {
            for (String var11 : AsmUtil.W(var0, var8)) {
                AsmUtil.E(var4, var0, var11);
}
}
        return var4;
}
    public static boolean b(MethodInsnNode var0, String var1, String var2, String ... var3) {
        String var6 = AsmUtil.b(var1);
        String var7 = AsmUtil.n(var2);
        return var0.owner.equals(AsmUtil.b(AsmUtil.c(), var6)) && var0.desc.equals(AsmUtil.Y(AsmUtil.c(), var7)) && AsmUtil.j(var0.name, AsmUtil.q(var6, var7, var3));
}
    public static String S(String var0, String var1, String var2) {
        return AsmUtil.O(var0, var1, var2);
}
    public static boolean H(String var0) {
        return var0 != null && (var0.startsWith("func_") || var0.startsWith("field_") || var0.length() > 2);
}
    public static Set<String> Q(String var0, String var1, String ... var2) {
        String var5 = AsmUtil.b(var0);
        String var6 = AsmUtil.L(AsmUtil.n(var1));
        LinkedHashSet<String> var7 = new LinkedHashSet<String>();
        for (String var11 : var2) {
            var7.add(var11);
            AsmUtil.M(var7, var5, var6, var11, true);
}
        LinkedHashSet<String> var15 = new LinkedHashSet<String>();
        for (String var17 : var7) {
            var15.add(var17);
            for (MappingKind var14 : MappingKind.values()) {
                AsmUtil.z(var15, var14, var5, var6, var17, true);
}
}
        return var15;
}
    private static int b(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x61FF;
        if (h[var3] == null) {
            byte[] var13;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = g[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])j.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    j.put(var8, var9);
}
                DESKeySpec var10 = new DESKeySpec(var4);
                SecretKey var11 = ((SecretKeyFactory)var9[1]).generateSecret(var10);
                Cipher var12 = (Cipher)var9[0];
                var12.init(2, (Key)var11, (IvParameterSpec)var9[2]);
                var13 = var12.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/ASM/Util/AsmUtil", var14);
}
            int var15 = (var13[4] & 0xFF) << 24 | (var13[5] & 0xFF) << 16 | (var13[6] & 0xFF) << 8 | var13[7] & 0xFF;
            AsmUtil.h[var3] = var15;
}
        return h[var3];
}
    public static MappingKind N() {
        if (i != null) {
            return i;
}
        return K ? MappingKind.SRG : MappingKind.MCP;
}
    public static String y(String var0) {
        return AsmUtil.D(var0).replace('/', '.');
}
    public static String L(String var0) {
        if (var0 != null && !var0.isEmpty() && var0.charAt(0) == '(') {
            int var3 = var0.indexOf(41);
            return var3 < 0 ? var0 : var0.substring(0, var3 + 1);
}
        return "";
}
    public static MappingKind j(Map<String, Class<?>> var0, boolean var1, boolean var2) {
        Class<?> var6;
        Class<?> var5 = var0.get("net.minecraft.client.Minecraft");
        if (var5 != null) {
            if (AsmUtil.O(var5, "getMinecraft") || AsmUtil.E(var5, "thePlayer")) {
                return MappingKind.MCP;
}
            if (AsmUtil.O(var5, "getMinecraft") || AsmUtil.E(var5, "thePlayer")) {
                return MappingKind.SRG;
}
}
        if ((var6 = var0.get("ave")) == null && !var2) {
            return var1 ? MappingKind.MCP : MappingKind.SRG;
}
        return MappingKind.NOTCH;
}
    public static boolean j(String var0, Set<String> var1) {
        for (String var3 : var1) {
            if (!var0.equals(var3)) continue;
            return true;
}
        return false;
}
    public static String[] z(byte[] var0) {
        ClassNode var3 = new ClassNode();
        new ClassReader(var0).accept((ClassVisitor)var3, 7);
        ArrayList<MappingKind> var4 = new ArrayList<MappingKind>();
        AsmUtil.M(var4, AsmUtil.Q(var3));
        AsmUtil.M(var4, AsmUtil.N());
        AsmUtil.M(var4, MappingKind.MCP);
        AsmUtil.M(var4, MappingKind.SRG);
        AsmUtil.M(var4, MappingKind.NOTCH);
        String[] var5 = new String[var4.size()];
        for (int var6 = 0; var6 < var4.size(); ++var6) {
            var5[var6] = var4.get(var6).name();
}
        return var5;
}
    public static void o(MappingKind var0, String var1, String var2, String var3, String var4) {
        P.get((Object)var0).put(new FieldRef(var1, var2), new FieldMapping(var3, var4));
        B.get((Object)var0).put(new FieldRef(var3, var4), new FieldMapping(var1, var2));
}
    public static String O(String var0, String var1, String var2) {
        String var5 = AsmUtil.b(var0);
        String var6 = AsmUtil.n(var2);
        String var7 = AsmUtil.q(var5, var6, var1);
        MethodMapping var8 = Z.get((Object)AsmUtil.c()).get(new MethodRef(var5, var7, var6));
        return var8 == null ? var7 : MethodMapping.Z(var8);
}
    public static String O(MappingKind var0, String var1, String var2, String var3) {
        String var5 = AsmUtil.b(var1);
        String var6 = AsmUtil.n(var3);
        String var7 = AsmUtil.q(var5, var6, var2);
        MethodMapping var8 = Z.get((Object)var0).get(new MethodRef(var5, var7, var6));
        return var8 == null ? var7 : MethodMapping.Z(var8);
}
    public static String n(String var0) {
        return AsmUtil.Y(MappingKind.MCP, var0);
}
    private static String a(byte[] var0) {
        int var1 = 0;
        int var2;
        char[] var3 = new char[var2 = var0.length];
        for (int var4 = 0; var4 < var2; ++var4) {
            int var5;
            if ((var5 = 255 & var0[var4]) < 192) {
                var3[var1++] = (char)var5;
            } else if (var5 < 224) {
                char var6 = (char)((char)(var5 & 31) << 6);
                int var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 63));
                var3[var1++] = var6;
            } else if (var4 < var2 - 2) {
                char var12 = (char)((char)(var5 & 15) << 12);
                int var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63) << 6);
                var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63));
                var3[var1++] = var12;
            }
        }
        return new String(var3, 0, var1);
    }    private static void zkm$clinit() {
        try {
            long var0 = a ^ 83507682439898L;
            f = new HashMap(13);
            byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var3 = 1; var3 < 8; ++var3) {
                var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
            }
            Cipher var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var2.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var4 = new String[41];
            int var5 = 0;
            String var6 = "\u0010\u0010\u00ec\u00ebqR\u00bc\u00fa\u00bfT*-Nm\bR\u0011\u001a\u00b0\u00f8{\u00bdPj\u0085\u00c1y\u00e9\"\u00f6F\u00def\u0088F\u00c3\u001cn\u00a4\u00bbC7l\u00bf\u00be\u00cc@\u00afr\u00cf\u00aeU\u00d8C]\u00ca8\u0013\u00fa\u00d2i\u00fd\u00b7\u00db6+\u0005\u0004\u00aa\u00d5\u00e7|\u00cb\u00bd@\u0097\u00e5\u0001\u00c13p\u00cd-\u00d8\u0089;\u00d6\u0091\u00feF\u00e3Ai\u00f8\u00fdk\u0082\u001c\u00f9\t\u008bj^B\u000e\u009aM\u00adTt2w\u00bf\u0010e\u0002'\u00dc\u00b7q\u0091\u0019\u0003\u00b0\u00a2\u00a1\u00e8\u00a5\u007f\u00ea\u0018s\u00bb0zCG\u00035\u0086+\u009ad\u0089\u00e5ak\u00c9\u001144\u00d5\u0016\u0001\u008c0\u00fe\u00b9\u00da\u00fd\u0095\u00ab\u00a2\u00a6}\u0018s\u00ed\u0081\u001bU\u0099\u00ee\u00dcU\u00c9\u001e\u008e\u00c8\u00bd\u007f\u0000\u0081Ner\u00be\u00b1\u00eaZ6\u00b9\u00195T\u00be<DB\u00f4UjB\u0015 w\u009c\u0097\u00ed\u00d6Z\u00e9+\nn\u00ed\u00c9\u00bb\u0014o\u00eb\u0085\u00956Q\u0098\u001b|\u00d1\u00ee\u00f1\u00b0;\u00b8\u00c8\u0090\u0019\u0010\u0088r/\b\u001c\u00be\u00e3\u00d4l4\u00e7\u000fN\u00c6\u00c9v\u0010M?\u00d2\u008e\u00be\u00a8\u00a4\u0016\u00a0P\n\u00b9\u0090\u00d0hV L^\u00b5\u00fa\u0005\u00a0\u00c41qo\u00ecV\u00b0\u00e7s8;\u00deE\u00f9R\u0014\u0091\u0018P\u00f4\u001b\u00dc\u00b5\u0092\u00ba\u00eb8]\u008d\u00faWwk3R\u00c7|q\u00c7.\u00ef\u00f8&\u00ec\u0010\u00ee8\u00c5\u00e3!\u0010\u00a9\u00a2\u00a4\u00bf\u00e3\u0007\u00b0\u00ae\u00f9\u00f4\u00e5\u00cdN\u00e9\u00f1\u00cf2\u00fd\u00f8\f\u00d3\u00e3\u00d2i\u0000q\u00f5\u00ab(\u00b2f_\u0018A\u008b\u00ae\u00d5\r\u000f\u00b9\u000b\u00b38\u00c2\u00f1\\f\u00c5\u00af\u0091\u00ceo\u00d0\u00e0\u0088\u00e66\u0010\u009a\u0090\u000eqe\u0019\u00bd\u001c\u00b3'=T\u00c5<\u00de\u00f58\u00f7\u00f4u/z\u001a\u00e9\u00b1\u00dd\u00dah*5\u00aeuK\u00fb\u00bf\u00b2\u00ca\u00d0\u00e42r\u00c3*\u00fd\u0000\u00f6\u0092\u00d6\u00cf\u00a5i+-*\u0007\u001b\u0083\u00de\u00b7.M\u00a4eP\u00a4\u00af\u00f4\u0010\u0085>c\u00d46 \u00d7\u009d\u001fY[G\u00d0\u00ee`\u00d8`\u0092\u00df?r\u0089*I`R\u00ff\u00a2\u00b9\u00c0\u0016'?B\u00b6\u00e5\u00ec\u00f6h\u0002l\u00e5kX*\u00876\u00c1\u00c9,7\u008d\u00c3U\u001c2^A\u00b0<u\u0010\u00f89\u0081\u00a4j\u009a\u0012\u00a1\u00d5\u0082\u0093\u0091\u00d2\u00fb\bHp\u00d8\u00da|\u0001u\u0083G\u00e2\u0015\u0089\u00d1\u00be8j\u008e w\u00c0F\u00c2\u00aaoao\u00a64;\u001bwpO\u001ed\u0019\u00a1u[\u00991\u00f6\u00af\u00ce\u00c1)s\u0004Qaj\u00ac\u00a4\u00b4\u00df]7\u0004P+T\u00b7\u009a\u000f\u00dc\u00e2 2}\u00db\u0004\u001f\u009a\u00c7\u00b5\u0013\"1[\"\u00e04\u00ef|\u00e8w\u0005\u009d\u00a5\u009d'\"\u0018\u00ce\u00f4w/\u00c6\u00988\u00fd z\u00ed\u00ccud0\u00fd\u00a1\u00aa\u0086\u008c7?\u00ac\u00b02\u009dl\u00d0\u00b2\b\u0099\u00e5[+N\u00cbBs\u00cf.\u00c9&\u0019\u00e3\u00a33\u00f0\u00e4\u00a1X!\u00a1\u00c2\u00da\u00d8\u00c7WH\u00dc\u001e\u001b\u009d\u00e70\u00ea\\\u0012\u00bcu\u00fd\u0013c\u00db\f\u00ffV\u0080m\u0019O\u00a6\u00e6N\u00c3\u00ea!\u0080\u00fc,\u0081\u0094\u00d5\u009e\u00ad1N\u0099\u007f\u00c9\u00ecg|\u00c2\u00ea\u00eaH\u0086\u00b7v\u008d\u001a\u00ba\u0010\u00a5;\u00ee\u0099\u00ca\u0086\u00db\u00c2\u00a96\u001a\u00ae\u0097\u00f3\u0000(\u00a06\u00b7\u00c4\u00e3\"\u00bc1\u0015\u00f2t6\u0081w\u00dd\u0016\u00d9\u00a0?t\u00a9\u00c0\u0090vzg|\u0085h\u00a0\u00b3\u00e4\u0097~{r)\u00bdrA\u00cf\u0090b~\u00d5\u00af\u00d9\u008d}\u0086\u00a8\u00d2`\u0083m\u00af\u00bd`\u00a9\u00a3G\u00e0\u00f8\u008b\u001c\u00ad\u0086\u0015\u00ba\u00bf\u00f6n\u00bfq\u0093\u0018\u0087k\u00b1\u00a6\u00f1\u00ac\u00cd\u00cc_\u00e3\"\u00ad\u00ab\u00e7Q\rf3G\u00b1\u00abL\u008agSX\u0082QsV\u00b1\u009c[Q\u0003\u00df\u00fdhe\u0098\u00bd\u00a6%\u0005\u00fd\u00aa\u0018\u00e8]\u0015+4\u00f4\u00cf\u00a4\u00d8\u00b0P\u00ea\u001b\r(\u00a7\u00d5aza\u00bcn\u00aa\n\u0090\u008fP\u00e9\u00d4I7J\u00f1\u00cc\u0093\u00e5\u0001\u00ed\u0010\u00ba)\u00ecV(@A\u00f4\u0091\u00a8=;t\u00ed(\u00b3 \u0011\u0080\u00af\u00ed-\u0007\u00fe \u00a9x\u0002\u00be\u00a2\u00a6\u0087\u00e3\u007f\u009a0\u00a9\u00eb\u00c9\u0080-\u0018q t\u008ba%\u0096\u0010W`\u0096s\u008c\u00dc+\"\u00d5\u00f6\u00fb\u00ea\u00b6cO\u00b0\u0018\u00e5\u00b8\u0088f\u009fb4\u00f4\u00dc\u0089\u008f\u00c8SR\u00efx\u00be`g\u0080M\u00a9mJ8\u0014\u00ef\u0018\u00f8\u0015\u00ef\u00a7q\u00a5}BN\u00d9\u0089\u000ey\u0086\u0019[\u00dd\u00aa\u00c9\u0084\u00c5\u00fd\u0005.A\u001f\u00fdx\u0010\u00afxf\u00fb\u0019\u00af/\u00f9?=\u00e2\u00b8Fns\u000f\u00a5}%U\u00b4V\u0014\u0089@\u009f\u00f2\u0081\u001e8\u00bf\u0011\u0090:\u00f9\b8\u009dI\u0096\u00e6\u00eem\u00b2~~\u0004\u00e9-\u00f2x\u00f0\u0084\fT\u00bfx\u00dc\u00ae1\u009b\u007fV~\u0096\u001f\u00b5\u008e\u00d9\u00d5\u00eb_o?t}\u0098YT\u00d1\u00ff\u00f4\u00b4\u00ccI\u00e1$3\u00f08ew\u00dby\u008ex\u00db;!\u009f\u0098\u00f5\u007f;\u00e6\u00bb,h\u00d9\u00e6pO\u00e2\u0013\u00afP\u00c1\u00cc\u0095\u00a3\u00be\n<\u00d5\u008d\u00aceZ\u0095\u00fe\u00ed\u00b5-\u00cc\u00b5\u00f3\u00fc\u00a6\u00a0\u0095\u00c7\u00c3\u00e5-b}\u0018\u00c7aP\u00ea\u00ebOI\u00af\u0003\u0017\u00ba\u00bf7\u0003L\u001e\u00d7o|\u0013\u00ba3\u00fb.@\u00ad4\u00f5\u00c0\u00d2\u00f0\u0005\u009eu\u00a6\u00fd\u00a2\u001c{\u0085H\u00e3X\u001a\u00d8O,\u001c\u00a9js\u00c4Wq\tY\u009b+\u001f\u0013\u00ff\b \u00e7\u0099\u00b9\u00dbg\u00aa\u0011\u00a6\u00fb\u00e0\u009c\u0083X\u0013\u00eb\u0096\u00e6h\u00cc\u00f5\u00c7\u0007(\u009b\u00a3I\u00a0\u00f2\u00c7\u00f9r\u0084p\u00be\u00c0\u00d2,\u00ebAs\u00cf`\u00bc\u00fe\u0004l\u0085\u0019\"\u00c1\u00a4}\u00a8\u00b5\u00b5\u0006\u00e2]\u00fbg\u008e\u0087S\u00eb\u00dd]\"\u00baL\u008dE\u0085\u00bb\u00ee\u008a\u00fd\u0090T\u0094\u00efB\u00c2\u00ea\u00bdo\u0090\u00c1\u009a\u00f1{7\u00b1+\u00f0x\u0090nM34S\u0095\u00b1\u00c1\u0013\u00a2\u00bb\u00b6\u00ac\u00c2a.o\u00a0\u0094=\u0098\u00ea\b\u00d6\u00c4\u00b2\u00fd\u00ed\u0003\u00d4w\u0088\u00aa\u00d6\u0098y\u0082.\u0015\u00947\u00b5\u0004=q\u00e82f\u0094\u0010\u0013y\f\u00fej\u00f5\u00b9\u00c5\u009d\u0095d\u0004N\u00a4\u0014\u00eb\u00f1\u009bF\u00adVpDmY\u001d\u00cdu@3\u00e9AtvN\u00b7B\u00ba(`\u009a\u0010K=\u00ee\u00fa8\u00e1\u00a4\u00afI9\u00ec\u00d44-\u00c9\u00d6(\u00bb\u00dc\u00a4\u00bd9}<\u00b3\u00df\u00d8\u009f\u0018\u00f6P\u0083\u0084\u00b2m\u00cb\u00d5\u00cc\u00b9\u008e\u0018\u00b4\u00f5\u000e\u00f7,\bq\u0085AJ\u00c5z\u00db<\u00b2Y\u0010+\u00fe\u00c3\u00cd\u00edPjT\u0016#\u001e\u00db`\u00b9\u00a4i(\u00d5\u00e6\u00e5=\u0005k\u00ff\u0011K\u0003\u009c`\"\u00efs\u000b\u00b2H\u00ff\u00dbE\u0018R\u00a0!\u00a0\u008d\u001b\u0099\u00e1:\u00e3\n\u00bdw\r\u00b2r\u00ae\u000b q\u00ab\u00ab\u001c\u00c3\u00ac\u00af1_\u00aa\u00cc\u00fdp\u00c2N_\u00e2\u0019?\u0090t\u00db\u00cbg\u00d9\u0094W\u00e0\u00f9Y\u00e7\t #\u00a9\u00daK\u00b1\u00faj\u00a9NOb\u00bd\u009a\u00de^\u00a5o?\u00b5\u00bc\u00a7\u0099\u0019E\"\u00a3\r\u00e9\u00bb&P-0Qf\u00e7\"%\u00bb'\u00946F\u00a7\u00b9\u008c\u0006\u00cc\u00e9\u00ea\u00c9\u0081\u00b1\u0086\u00d8Qm^*\u00b6,\u00d8\u00ba\u00ca\u0014\u00c4\u00a4\u00a5\u00c5Z\u001ci\u001f=\u00dbM(5T\u00fe\u00f0 wA\u00b7>\u0004\u00ad\u00a4u\u00a6\u00c5\u00d7\u00e7\u00df\u00fbL\u0006\u00ff\u00a3!8V\u00cb\u009bAT\u0090b>\u00af\u00b1\n\u009b0\u00b7\u00d7\u0019\u00e3\u00f4X\u00a1h}5b\u00af\u00e4\u00dbI<H\u00d4:\u00d5&`\u00af\u000f]\nz\u0001Qe\u0000$\u0090h\u00c4\u009cV\u0098\u00eb\u00cc\u007f^)\u00b6G\u00fd\u0080\u000f";
            int var7 = "\u0010\u0010\u00ec\u00ebqR\u00bc\u00fa\u00bfT*-Nm\bR\u0011\u001a\u00b0\u00f8{\u00bdPj\u0085\u00c1y\u00e9\"\u00f6F\u00def\u0088F\u00c3\u001cn\u00a4\u00bbC7l\u00bf\u00be\u00cc@\u00afr\u00cf\u00aeU\u00d8C]\u00ca8\u0013\u00fa\u00d2i\u00fd\u00b7\u00db6+\u0005\u0004\u00aa\u00d5\u00e7|\u00cb\u00bd@\u0097\u00e5\u0001\u00c13p\u00cd-\u00d8\u0089;\u00d6\u0091\u00feF\u00e3Ai\u00f8\u00fdk\u0082\u001c\u00f9\t\u008bj^B\u000e\u009aM\u00adTt2w\u00bf\u0010e\u0002'\u00dc\u00b7q\u0091\u0019\u0003\u00b0\u00a2\u00a1\u00e8\u00a5\u007f\u00ea\u0018s\u00bb0zCG\u00035\u0086+\u009ad\u0089\u00e5ak\u00c9\u001144\u00d5\u0016\u0001\u008c0\u00fe\u00b9\u00da\u00fd\u0095\u00ab\u00a2\u00a6}\u0018s\u00ed\u0081\u001bU\u0099\u00ee\u00dcU\u00c9\u001e\u008e\u00c8\u00bd\u007f\u0000\u0081Ner\u00be\u00b1\u00eaZ6\u00b9\u00195T\u00be<DB\u00f4UjB\u0015 w\u009c\u0097\u00ed\u00d6Z\u00e9+\nn\u00ed\u00c9\u00bb\u0014o\u00eb\u0085\u00956Q\u0098\u001b|\u00d1\u00ee\u00f1\u00b0;\u00b8\u00c8\u0090\u0019\u0010\u0088r/\b\u001c\u00be\u00e3\u00d4l4\u00e7\u000fN\u00c6\u00c9v\u0010M?\u00d2\u008e\u00be\u00a8\u00a4\u0016\u00a0P\n\u00b9\u0090\u00d0hV L^\u00b5\u00fa\u0005\u00a0\u00c41qo\u00ecV\u00b0\u00e7s8;\u00deE\u00f9R\u0014\u0091\u0018P\u00f4\u001b\u00dc\u00b5\u0092\u00ba\u00eb8]\u008d\u00faWwk3R\u00c7|q\u00c7.\u00ef\u00f8&\u00ec\u0010\u00ee8\u00c5\u00e3!\u0010\u00a9\u00a2\u00a4\u00bf\u00e3\u0007\u00b0\u00ae\u00f9\u00f4\u00e5\u00cdN\u00e9\u00f1\u00cf2\u00fd\u00f8\f\u00d3\u00e3\u00d2i\u0000q\u00f5\u00ab(\u00b2f_\u0018A\u008b\u00ae\u00d5\r\u000f\u00b9\u000b\u00b38\u00c2\u00f1\\f\u00c5\u00af\u0091\u00ceo\u00d0\u00e0\u0088\u00e66\u0010\u009a\u0090\u000eqe\u0019\u00bd\u001c\u00b3'=T\u00c5<\u00de\u00f58\u00f7\u00f4u/z\u001a\u00e9\u00b1\u00dd\u00dah*5\u00aeuK\u00fb\u00bf\u00b2\u00ca\u00d0\u00e42r\u00c3*\u00fd\u0000\u00f6\u0092\u00d6\u00cf\u00a5i+-*\u0007\u001b\u0083\u00de\u00b7.M\u00a4eP\u00a4\u00af\u00f4\u0010\u0085>c\u00d46 \u00d7\u009d\u001fY[G\u00d0\u00ee`\u00d8`\u0092\u00df?r\u0089*I`R\u00ff\u00a2\u00b9\u00c0\u0016'?B\u00b6\u00e5\u00ec\u00f6h\u0002l\u00e5kX*\u00876\u00c1\u00c9,7\u008d\u00c3U\u001c2^A\u00b0<u\u0010\u00f89\u0081\u00a4j\u009a\u0012\u00a1\u00d5\u0082\u0093\u0091\u00d2\u00fb\bHp\u00d8\u00da|\u0001u\u0083G\u00e2\u0015\u0089\u00d1\u00be8j\u008e w\u00c0F\u00c2\u00aaoao\u00a64;\u001bwpO\u001ed\u0019\u00a1u[\u00991\u00f6\u00af\u00ce\u00c1)s\u0004Qaj\u00ac\u00a4\u00b4\u00df]7\u0004P+T\u00b7\u009a\u000f\u00dc\u00e2 2}\u00db\u0004\u001f\u009a\u00c7\u00b5\u0013\"1[\"\u00e04\u00ef|\u00e8w\u0005\u009d\u00a5\u009d'\"\u0018\u00ce\u00f4w/\u00c6\u00988\u00fd z\u00ed\u00ccud0\u00fd\u00a1\u00aa\u0086\u008c7?\u00ac\u00b02\u009dl\u00d0\u00b2\b\u0099\u00e5[+N\u00cbBs\u00cf.\u00c9&\u0019\u00e3\u00a33\u00f0\u00e4\u00a1X!\u00a1\u00c2\u00da\u00d8\u00c7WH\u00dc\u001e\u001b\u009d\u00e70\u00ea\\\u0012\u00bcu\u00fd\u0013c\u00db\f\u00ffV\u0080m\u0019O\u00a6\u00e6N\u00c3\u00ea!\u0080\u00fc,\u0081\u0094\u00d5\u009e\u00ad1N\u0099\u007f\u00c9\u00ecg|\u00c2\u00ea\u00eaH\u0086\u00b7v\u008d\u001a\u00ba\u0010\u00a5;\u00ee\u0099\u00ca\u0086\u00db\u00c2\u00a96\u001a\u00ae\u0097\u00f3\u0000(\u00a06\u00b7\u00c4\u00e3\"\u00bc1\u0015\u00f2t6\u0081w\u00dd\u0016\u00d9\u00a0?t\u00a9\u00c0\u0090vzg|\u0085h\u00a0\u00b3\u00e4\u0097~{r)\u00bdrA\u00cf\u0090b~\u00d5\u00af\u00d9\u008d}\u0086\u00a8\u00d2`\u0083m\u00af\u00bd`\u00a9\u00a3G\u00e0\u00f8\u008b\u001c\u00ad\u0086\u0015\u00ba\u00bf\u00f6n\u00bfq\u0093\u0018\u0087k\u00b1\u00a6\u00f1\u00ac\u00cd\u00cc_\u00e3\"\u00ad\u00ab\u00e7Q\rf3G\u00b1\u00abL\u008agSX\u0082QsV\u00b1\u009c[Q\u0003\u00df\u00fdhe\u0098\u00bd\u00a6%\u0005\u00fd\u00aa\u0018\u00e8]\u0015+4\u00f4\u00cf\u00a4\u00d8\u00b0P\u00ea\u001b\r(\u00a7\u00d5aza\u00bcn\u00aa\n\u0090\u008fP\u00e9\u00d4I7J\u00f1\u00cc\u0093\u00e5\u0001\u00ed\u0010\u00ba)\u00ecV(@A\u00f4\u0091\u00a8=;t\u00ed(\u00b3 \u0011\u0080\u00af\u00ed-\u0007\u00fe \u00a9x\u0002\u00be\u00a2\u00a6\u0087\u00e3\u007f\u009a0\u00a9\u00eb\u00c9\u0080-\u0018q t\u008ba%\u0096\u0010W`\u0096s\u008c\u00dc+\"\u00d5\u00f6\u00fb\u00ea\u00b6cO\u00b0\u0018\u00e5\u00b8\u0088f\u009fb4\u00f4\u00dc\u0089\u008f\u00c8SR\u00efx\u00be`g\u0080M\u00a9mJ8\u0014\u00ef\u0018\u00f8\u0015\u00ef\u00a7q\u00a5}BN\u00d9\u0089\u000ey\u0086\u0019[\u00dd\u00aa\u00c9\u0084\u00c5\u00fd\u0005.A\u001f\u00fdx\u0010\u00afxf\u00fb\u0019\u00af/\u00f9?=\u00e2\u00b8Fns\u000f\u00a5}%U\u00b4V\u0014\u0089@\u009f\u00f2\u0081\u001e8\u00bf\u0011\u0090:\u00f9\b8\u009dI\u0096\u00e6\u00eem\u00b2~~\u0004\u00e9-\u00f2x\u00f0\u0084\fT\u00bfx\u00dc\u00ae1\u009b\u007fV~\u0096\u001f\u00b5\u008e\u00d9\u00d5\u00eb_o?t}\u0098YT\u00d1\u00ff\u00f4\u00b4\u00ccI\u00e1$3\u00f08ew\u00dby\u008ex\u00db;!\u009f\u0098\u00f5\u007f;\u00e6\u00bb,h\u00d9\u00e6pO\u00e2\u0013\u00afP\u00c1\u00cc\u0095\u00a3\u00be\n<\u00d5\u008d\u00aceZ\u0095\u00fe\u00ed\u00b5-\u00cc\u00b5\u00f3\u00fc\u00a6\u00a0\u0095\u00c7\u00c3\u00e5-b}\u0018\u00c7aP\u00ea\u00ebOI\u00af\u0003\u0017\u00ba\u00bf7\u0003L\u001e\u00d7o|\u0013\u00ba3\u00fb.@\u00ad4\u00f5\u00c0\u00d2\u00f0\u0005\u009eu\u00a6\u00fd\u00a2\u001c{\u0085H\u00e3X\u001a\u00d8O,\u001c\u00a9js\u00c4Wq\tY\u009b+\u001f\u0013\u00ff\b \u00e7\u0099\u00b9\u00dbg\u00aa\u0011\u00a6\u00fb\u00e0\u009c\u0083X\u0013\u00eb\u0096\u00e6h\u00cc\u00f5\u00c7\u0007(\u009b\u00a3I\u00a0\u00f2\u00c7\u00f9r\u0084p\u00be\u00c0\u00d2,\u00ebAs\u00cf`\u00bc\u00fe\u0004l\u0085\u0019\"\u00c1\u00a4}\u00a8\u00b5\u00b5\u0006\u00e2]\u00fbg\u008e\u0087S\u00eb\u00dd]\"\u00baL\u008dE\u0085\u00bb\u00ee\u008a\u00fd\u0090T\u0094\u00efB\u00c2\u00ea\u00bdo\u0090\u00c1\u009a\u00f1{7\u00b1+\u00f0x\u0090nM34S\u0095\u00b1\u00c1\u0013\u00a2\u00bb\u00b6\u00ac\u00c2a.o\u00a0\u0094=\u0098\u00ea\b\u00d6\u00c4\u00b2\u00fd\u00ed\u0003\u00d4w\u0088\u00aa\u00d6\u0098y\u0082.\u0015\u00947\u00b5\u0004=q\u00e82f\u0094\u0010\u0013y\f\u00fej\u00f5\u00b9\u00c5\u009d\u0095d\u0004N\u00a4\u0014\u00eb\u00f1\u009bF\u00adVpDmY\u001d\u00cdu@3\u00e9AtvN\u00b7B\u00ba(`\u009a\u0010K=\u00ee\u00fa8\u00e1\u00a4\u00afI9\u00ec\u00d44-\u00c9\u00d6(\u00bb\u00dc\u00a4\u00bd9}<\u00b3\u00df\u00d8\u009f\u0018\u00f6P\u0083\u0084\u00b2m\u00cb\u00d5\u00cc\u00b9\u008e\u0018\u00b4\u00f5\u000e\u00f7,\bq\u0085AJ\u00c5z\u00db<\u00b2Y\u0010+\u00fe\u00c3\u00cd\u00edPjT\u0016#\u001e\u00db`\u00b9\u00a4i(\u00d5\u00e6\u00e5=\u0005k\u00ff\u0011K\u0003\u009c`\"\u00efs\u000b\u00b2H\u00ff\u00dbE\u0018R\u00a0!\u00a0\u008d\u001b\u0099\u00e1:\u00e3\n\u00bdw\r\u00b2r\u00ae\u000b q\u00ab\u00ab\u001c\u00c3\u00ac\u00af1_\u00aa\u00cc\u00fdp\u00c2N_\u00e2\u0019?\u0090t\u00db\u00cbg\u00d9\u0094W\u00e0\u00f9Y\u00e7\t #\u00a9\u00daK\u00b1\u00faj\u00a9NOb\u00bd\u009a\u00de^\u00a5o?\u00b5\u00bc\u00a7\u0099\u0019E\"\u00a3\r\u00e9\u00bb&P-0Qf\u00e7\"%\u00bb'\u00946F\u00a7\u00b9\u008c\u0006\u00cc\u00e9\u00ea\u00c9\u0081\u00b1\u0086\u00d8Qm^*\u00b6,\u00d8\u00ba\u00ca\u0014\u00c4\u00a4\u00a5\u00c5Z\u001ci\u001f=\u00dbM(5T\u00fe\u00f0 wA\u00b7>\u0004\u00ad\u00a4u\u00a6\u00c5\u00d7\u00e7\u00df\u00fbL\u0006\u00ff\u00a3!8V\u00cb\u009bAT\u0090b>\u00af\u00b1\n\u009b0\u00b7\u00d7\u0019\u00e3\u00f4X\u00a1h}5b\u00af\u00e4\u00dbI<H\u00d4:\u00d5&`\u00af\u000f]\nz\u0001Qe\u0000$\u0090h\u00c4\u009cV\u0098\u00eb\u00cc\u007f^)\u00b6G\u00fd\u0080\u000f".length();
            int var8 = 56;
            int var24 = -1;
            block9: while (true) {
                String var26 = var6.substring(++var24, var24 + var8);
                int var10001 = -1;
                while (true) {
                    byte[] var10 = var2.doFinal(var26.getBytes("ISO-8859-1"));
                    String var37 = AsmUtil.a(var10).intern();
                    switch (var10001) {
                        case 0: {
                            var4[var5++] = var37;
                            if ((var24 += var8) >= var7) {
                                d = var4;
                                e = new String[41];
                                C = "/assets/abyss/asm/mcp-srg.srg";
                                c = "/assets/abyss/asm/mcp-notch.srg";
                                j = new HashMap(13);
                                var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var12 = 1; var12 < 8; ++var12) {
                                    var10003[var12] = (byte)(var0 << var12 * 8 >>> 56);
}
                                Cipher var11 = Cipher.getInstance("DES/CBC/NoPadding");
                                var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var13 = new long[10];
                                int var14 = 0;
                                String var15 = "\u00ae\u007f\u009d\u00b4\u001dW\u009dy\u00b9n[v\u00d3\u00d5\u00d7\u00f4\u00a7\u008f\u00c1\u00fc*\u0007\u00ef\u00f8\u00a4*b\u00c8\u0098I>M\u0007\u008a\u00efo\u00ae4\u008a\u00e6\u0085\u00fb\u00b0I\u00ce\u008dn\u00c6\u00f7ar\u00b9`\u001b\u00ae\u00c2\u008d\u00a9_\u00c3\u00b4\u00b5\u008c\u00ae";
                                int var16 = "\u00ae\u007f\u009d\u00b4\u001dW\u009dy\u00b9n[v\u00d3\u00d5\u00d7\u00f4\u00a7\u008f\u00c1\u00fc*\u0007\u00ef\u00f8\u00a4*b\u00c8\u0098I>M\u0007\u008a\u00efo\u00ae4\u008a\u00e6\u0085\u00fb\u00b0I\u00ce\u008dn\u00c6\u00f7ar\u00b9`\u001b\u00ae\u00c2\u008d\u00a9_\u00c3\u00b4\u00b5\u008c\u00ae".length();
                                int var17 = 0;
                                block12: while (true) {
                                    var10001 = var17;
                                    byte[] var18 = var15.substring(var10001, var17 += 8).getBytes("ISO-8859-1");
                                    long[] var29 = var13;
                                    var10001 = var14++;
                                    long var41 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                    int var44 = -1;
                                    while (true) {
                                        long var19 = var41;
                                        byte[] var21 = var11.doFinal(new byte[]{(byte)(var19 >>> 56), (byte)(var19 >>> 48), (byte)(var19 >>> 40), (byte)(var19 >>> 32), (byte)(var19 >>> 24), (byte)(var19 >>> 16), (byte)(var19 >>> 8), (byte)var19});
                                        long var46 = ((long)var21[0] & 0xFFL) << 56 | ((long)var21[1] & 0xFFL) << 48 | ((long)var21[2] & 0xFFL) << 40 | ((long)var21[3] & 0xFFL) << 32 | ((long)var21[4] & 0xFFL) << 24 | ((long)var21[5] & 0xFFL) << 16 | ((long)var21[6] & 0xFFL) << 8 | (long)var21[7] & 0xFFL;
                                        switch (var44) {
                                            case 0: {
                                                var29[var10001] = var46;
                                                if (var17 < var16) break;
                                                g = var13;
                                                h = new Integer[10];
                                                I = LogManager.getLogger((String)"Abyss ASM Mappings");
                                                v = new ThreadLocal();
                                                Y = new EnumMap<MappingKind, Map<String, String>>(MappingKind.class);
                                                N = new EnumMap<MappingKind, Map<String, String>>(MappingKind.class);
                                                Z = new EnumMap<MappingKind, Map<MethodRef, MethodMapping>>(MappingKind.class);
                                                b = new EnumMap<MappingKind, Map<MethodRef, MethodMapping>>(MappingKind.class);
                                                P = new EnumMap<MappingKind, Map<FieldRef, FieldMapping>>(MappingKind.class);
                                                B = new EnumMap<MappingKind, Map<FieldRef, FieldMapping>>(MappingKind.class);
                                                r = new ThreadLocal();
                                                AsmUtil.X(MappingKind.MCP);
                                                AsmUtil.X(MappingKind.SRG);
                                                AsmUtil.X(MappingKind.NOTCH);
                                                AsmUtil.H("/assets/abyss/asm/mcp-srg.srg", MappingKind.SRG);
                                                AsmUtil.H("/assets/abyss/asm/mcp-notch.srg", MappingKind.NOTCH);
                                                I.info("Loaded ASM mappings: srgMethods={}, srgFields={}, notchClasses={}, notchMethods={}, notchFields={}", new Object[]{Z.get((Object)MappingKind.SRG).size(), P.get((Object)MappingKind.SRG).size(), Y.get((Object)MappingKind.NOTCH).size(), Z.get((Object)MappingKind.NOTCH).size(), P.get((Object)MappingKind.NOTCH).size()});
                                                return;
}
                                            default: {
                                                var29[var10001] = var46;
                                                if (var17 < var16) continue block12;
                                                var15 = "\u00c5r\u00c4\u0084LF\u00b9\u007f\u008b\u00ac\u00ebe\u0001\u00b0\u00ec8";
                                                var16 = "\u00c5r\u00c4\u0084LF\u00b9\u007f\u008b\u00ac\u00ebe\u0001\u00b0\u00ec8".length();
                                                var17 = 0;
}
}
                                        int var35 = var17;
                                        var18 = var15.substring(var35, var17 += 8).getBytes("ISO-8859-1");
                                        var29 = var13;
                                        var10001 = var14++;
                                        var41 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                        var44 = 0;
}
}
}
                            var8 = var6.charAt(var24);
}
                        default: {
                            var4[var5++] = var37;
                            if ((var24 += var8) < var7) {
                                var8 = var6.charAt(var24);
                                continue block9;
}
                            var6 = "\u00aa\u0095i\u00f6\u0006JQ\u00e6\u00bcm\u00dc\u00bee\u00ce.20m\u0082\u0082|z\u00daR&P\u0004\u00b9\u00daC9\u00ea/\u00d9K\u00e2u\u00b10\u00be\f\u00bf\u001a\u008a\u0084\u0010\u00e5@b\u00d1'\u0081\u00b5\u0015P\u00fd\u008b\u00a6\u00bd\u0094=\u0012m{\n";
                            var7 = "\u00aa\u0095i\u00f6\u0006JQ\u00e6\u00bcm\u00dc\u00bee\u00ce.20m\u0082\u0082|z\u00daR&P\u0004\u00b9\u00daC9\u00ea/\u00d9K\u00e2u\u00b10\u00be\f\u00bf\u001a\u008a\u0084\u0010\u00e5@b\u00d1'\u0081\u00b5\u0015P\u00fd\u008b\u00a6\u00bd\u0094=\u0012m{\n".length();
                            var8 = 16;
                            var24 = -1;
}
}
                    var26 = var6.substring(++var24, var24 + var8);
                    var10001 = 0;
}
}
}
        catch (Throwable var22) {
            throw new RuntimeException(var22);
}
}
    static {
        a = 90580821667740L;
        zkm$clinit();
}
}