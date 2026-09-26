/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.network.play.server.S08PacketPlayerPosLook
 *  net.minecraft.network.play.server.S08PacketPlayerPosLook$EnumFlags
 *  net.minecraft.util.MathHelper
 */
package Abyss.util;

import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.RotationManagerBinder;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.internal.restore.AbyssNameMap;
import Abyss.module.impl.visual.Freelook;
import Abyss.util.ClientUtil;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.MoveUtil;
import Abyss.util.Sneaky;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.MathHelper;

public class RotationManager
implements EventSubscriber {
    public static float F;
    public static float I;
    public static boolean X;
    public static float b;
    public static boolean U;
    private static boolean J;
    private static float A;
    public static RotationMode o;
    public static float r;
    private static Minecraft m;
    private static float R;
    public static float V;
    private static long[] c;
    private static float M;
    public static float K;
    private static Object[] f;
    public static float G;
    private static Integer[] d;
    public static float L;
    private static String[] g;
    private static long a;
    private static Map e;
    
    private static float u;
        private static float T;
    private static final byte[] KEY_OFFSETS;
    public static float h;

    public static void x(float var0, long var1, float var3) {
        var1 = a ^ var1;
        int var4 = (int)((var1 ^ 0x66A3F7AD6F63L) >>> 48);
        int var5 = (int)((var1 ^ 0x66A3F7AD6F63L) << 16 >>> 48);
        RotationManager.W((short)var4, (short)var5, var0, var3);
}
    private static Method b(Class var0, String var1, Class var2, int var3, Class[] var4) {
        Method var5 = RotationManager.a(var0, var1, var2, var3, var4);
        if (var5 != null) {
            return var5;
}
        Class<?>[] var6 = var0.getInterfaces();
        if (var6 != null) {
            for (int var7 = 0; var7 < var6.length; ++var7) {
                var5 = RotationManager.b(var6[var7], var1, var2, var3, var4);
                if (var5 == null) continue;
                return var5;
}
}
        return null;
}
    public static void I(float var0, long var1) {
        RotationManager.o(0L, var0, 180.0f);
}
    public static void h(float var0, float var1, float var2, long var3) {
        float var5 = MathUtil.M(RotationManager.p(), var0);
        var5 = Math.abs(var5) <= 1.0f ? 0.0f : MathUtil.c(var5, var2);
        var5 = MathUtil.q(var5, -var1, var1);
        var5 = MathUtil.H(var5);
        if (Freelook.c()) {
            Freelook.N += var5;
        } else {
            RotationManager.m.thePlayer.rotationYaw += var5;
}
}
    public static void A(long var0, float var2) {
        long var3 = var0 ^ 0x2375A498733EL;
        RotationManager.V(var3, var2, 180.0f);
}
    public static void q(float var0, long var1, float var3) {
        RotationManager.f(var0, 180.0f, var3, 0L);
}
    public static void S(float var0, float var1, float var2, long var3) {
        long var5 = var3 ^ 0x27CF5B9B5E79L;
        RotationManager.o(0L, var0, var2);
        RotationManager.V(var5, var1, var2);
}
    private static Object a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, Object[] var4) throws Throwable {
        int var5 = var4.length - 2;
        long var6 = (Long)var4[var5];
        long var9 = (Long)var4[++var5];
        MethodHandle var8 = RotationManager.a(var0, var1, var2, var3, var6, var9);
        var1.setTarget(MethodHandles.explicitCastArguments(var8, var3));
        return var8.asSpreader(Object[].class, var4.length).invoke(var4);
}
    private static Method a(Class var0, String var1, Class var2, int var3, Class[] var4) {
        block0: for (Method var8 : var0.getDeclaredMethods()) {
            Class<?>[] var9;
            if (!var8.getName().equals(var1) || var8.getReturnType() != var2 || (var9 = var8.getParameterTypes()).length != var3) continue;
            for (int var10 = 0; var10 < var3; ++var10) {
                if (var9[var10] != var4[var10]) continue block0;
}
            return var8;
}
        return null;
}
    public static void f(float var0, float var1, float var2, long var3) {
        float var5;
        if (var0 > 90.0f) {
            var0 = 90.0f;
}
        if (var0 < -90.0f) {
            var0 = -90.0f;
}
        var5 = Math.abs(var5 = MathUtil.q(MathUtil.M(G, var0), -var1, var1)) <= 1.0f ? 0.0f : MathUtil.c(var5, var2);
        var5 = MathUtil.H(var5);
        G += var5;
        X = true;
}
    private static Field b(Class var0, String var1, Class var2) {
        Field var3 = RotationManager.a(var0, var1, var2);
        if (var3 != null) {
            return var3;
}
        Class<?>[] var4 = var0.getInterfaces();
        if (var4 != null) {
            for (int var5 = 0; var5 < var4.length; ++var5) {
                var3 = RotationManager.b(var4[var5], var1, var2);
                if (var3 == null) continue;
                return var3;
}
}
        return null;
}
    private static int a(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x6257;
        if (d[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = c[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])e.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    e.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/util/RotationManager", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            RotationManager.d[var3] = var15;
}
        return d[var3];
}
    public static void v(long var0, float var2) {
        RotationManager.a(59024409194504L, var2, 180.0f);
}
    public static boolean G() {
        return J && RotationManager.m.thePlayer != null && !Freelook.c();
}
    public static void l(float var0, long var1, float var3, float var4) {
        var0 = MathHelper.clamp_float((float)var0, (float)-90.0f, (float)90.0f);
        float var5 = MathUtil.M(RotationManager.s(), var0);
        var5 = Math.abs(var5) <= 1.0f ? 0.0f : MathUtil.c(var5, var4);
        var5 = MathUtil.q(var5, -var3, var3);
        var5 = MathUtil.H(var5);
        RotationManager.R(RotationManager.s() + var5);
}
    public static void W(short var0, short var1, float var2, float var3) {
        u = var2;
        A = MathHelper.clamp_float((float)var3, (float)-90.0f, (float)90.0f);
        J = true;
        R = 0.0f;
        M = MathHelper.wrapAngleTo180_float((float)(u - RotationManager.p()));
        T = MathHelper.clamp_float((float)(A - RotationManager.s()), (float)-90.0f, (float)90.0f);
        RotationManager.z(0.0f, 0L);
}
    public static void g(float var0, float var1, long var2, float var4) {
        long var5 = var2 ^ 0x1486F830B6F1L;
        RotationManager.K(var0, var4);
        RotationManager.a(var5, var1, var4);
}
    public static void o(long var0, float var2, float var3) {
        float var6 = MathUtil.q(MathUtil.M(r, var2), -var3, var3);
        var6 = MathUtil.H(var6);
        RotationManager.P(var6);
}
    public static void B(long var0, float var2, float var3) {
        RotationManager.v(var2, 180.0f, 0L, var3);
}
    public static void w(boolean var0) {
        U = var0;
}
    public void onPreMouseInput(long var1, PreMouseInputEvent var3) {
        if (ClientUtil.I()) {
            b = r;
            h = G;
            if (!X) {
                if (r != RotationManager.p()) {
                    if (RotationManager.m.thePlayer.isRiding()) {
                        r = RotationManager.p();
                    } else {
                        r += MathUtil.M(r, RotationManager.p());
                        RotationManager.m.thePlayer.prevRotationYaw = r;
                        RotationManager.r(r);
}
                    V = RotationManager.p();
}
                if (G != RotationManager.s()) {
                    RotationManager.m.thePlayer.prevRotationPitch = G = RotationManager.s();
}
                if (RotationManager.m.thePlayer.isRiding()) {
                    I = r;
                    K = G;
}
}
}
}
    public static void e(long var0) {
        RotationManager.z(1.0f, 0L);
}
    public static void f(float var0, float var1, long var2) {
        var2 = a ^ var2;
        long var4 = var2 ^ 0x56E472342CE1L;
        RotationManager.zkm$unresolved$0$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_oN_h_OR_Abyss_oN_f_y_slots_21_30(var0, 180.0f, var1, var4, var2);
}
    private static int a(long var0, long var2) {
        int var16;
        int var4 = (int)((var0 ^= var2 << 48 | var2) >>> 46);
        if (g[var4] != null) {
            return var4;
}
        Object var5 = f[var4];
        if (!(var5 instanceof String)) {
            return var4;
}
        byte var6 = KEY_OFFSETS[(int)(var0 >>> 42 & 0x3FL)];
        int[] var7 = new int[6];
        for (int var8 = 0; var8 < 6; ++var8) {
            int var9 = 7 * (5 - var8);
            int var10 = (int)(var0 >>> var9 & 0x7FL);
            if ((var10 -= var6) < 0) {
                var10 += 128;
}
            var7[var8] = var10;
}
        char[] var13 = ((String)var5).toCharArray();
        for (int var14 = 0; var14 < var13.length && (var16 = var7[var14 % var7.length]) != 0; ++var14) {
            var13[var14] = (char)(var13[var14] ^ var16);
}
        RotationManager.g[var4] = new String(var13);
        return var4;
}
    public static void V(long var0, int var2, float var3) {
        RotationManager.K(var3, 180.0f);
}
    public static void Q(float var0, long var1, float var3, float var4) {
        var1 = a ^ var1;
        RotationManager.L(var0, var3, 180.0f, var4);
}
    public static void r(float var0) {
        if (Freelook.c()) {
            Freelook.N = var0;
        } else {
            RotationManager.m.thePlayer.rotationYaw = var0;
}
}
    public static float s() {
        return Freelook.c() ? Freelook.M() : RotationManager.m.thePlayer.rotationPitch;
}
    public static void k(long var0) {
        J = false;
        R = Float.NaN;
        M = 0.0f;
        T = 0.0f;
}
    private static void R(float var0) {
        var0 = MathHelper.clamp_float((float)var0, (float)-90.0f, (float)90.0f);
        if (Freelook.c()) {
            Freelook.v = var0;
        } else {
            RotationManager.m.thePlayer.rotationPitch = var0;
}
}
    public static void R(float var0, long var1, float var3) {
        RotationManager.l(var0, 0L, 180.0f, var3);
}
    @Override
    public final void x(long var1, EventBus var3) {
        RotationManagerBinder.M(var3, this);
}
    public static void n(RotationMode var0) {
        o = var0;
}
    public static void r(long var0) {
        if (RotationManager.G()) {
            EntityPlayerSP var2 = RotationManager.m.thePlayer;
            var2.prevRotationYaw = var2.rotationYaw;
            var2.prevRotationPitch = var2.rotationPitch;
            var2.prevRenderArmYaw = var2.rotationYaw - (var2.renderArmYaw - var2.prevRenderArmYaw) * 2.0f;
            var2.renderArmYaw = var2.rotationYaw;
}
}
    private static Field c(long var0, long var2) {
        int var4 = RotationManager.a(var0, var2);
        Object var5 = f[var4];
        if (!(var5 instanceof String)) {
            return (Field)var5;
}
        String var6 = g[var4];
        int var7 = var6.indexOf(8);
        Class var8 = RotationManager.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
        int var9 = var6.indexOf(8, ++var7);
        String var10 = var6.substring(var7, var9);
        Class var11 = RotationManager.b(Long.parseLong(var6.substring(++var9), 36), 0L);
        Class var12 = var8;
        while (true) {
            Field var13;
            if ((var13 = RotationManager.a(var12, var10, var11)) != null) {
                RotationManager.f[var4] = var13;
                return var13;
}
            Class<?>[] var14 = var12.getInterfaces();
            if (var14 != null) {
                for (int var15 = 0; var15 < var14.length; ++var15) {
                    var13 = RotationManager.b(var14[var15], var10, var11);
                    if (var13 == null) continue;
                    RotationManager.f[var4] = var13;
                    return var13;
}
}
            if (var12.getName().equals("java.lang.Object")) {
                StringBuffer var19 = new StringBuffer();
                var19.append("NoSuchFieldException in ").append(var8.getName()).append(' ').append(var11.getName()).append(' ').append(var10);
                throw new RuntimeException(var19.toString());
}
            if ((var12 = var12.getSuperclass()) != null) continue;
            var12 = RotationManager.b(892700819680181L, 0L);
}
}
    public static void q(float var0, float var1, long var2, float var4) {
        long var5 = var2 ^ 0x20889C78EFE0L;
        RotationManager.V(var0, var1, var5, 180.0f, var4);
}
    public static void L(float var2, float var3, float var4, float var5) {
        RotationManager.v(var2, var4, 0L, var5);
        RotationManager.f(var3, var4, var5, 0L);
}
    private static MethodHandle a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, long var4, long var6) {
        char var8 = var2.charAt(0);
        MethodHandle var9 = null;
        Field var10 = null;
        Method var11 = null;
        try {
            if (var8 != 'z' && var8 != '\u00c5' && var8 != 'O' && var8 != '\u00db') {
                var11 = RotationManager.d(var4, var6);
                Class<?> var17 = var11.getDeclaringClass();
                String var19 = var11.getName();
                MethodType var20 = MethodType.methodType(var11.getReturnType(), var11.getParameterTypes());
                var9 = var8 == '\u00fb' ? var0.findVirtual(var17, var19, var20) : (var8 == 'H' ? var0.findStatic(var17, var19, var20) : var0.findSpecial(var17, var19, var20, var17));
            } else {
                var10 = RotationManager.c(var4, var6);
                Class<?> var12 = var10.getDeclaringClass();
                String var18 = var10.getName();
                Class<?> var14 = var10.getType();
                var9 = var8 == 'z' ? var0.findGetter(var12, var18, var14) : (var8 == '\u00c5' ? var0.findSetter(var12, var18, var14) : (var8 == 'O' ? var0.findStaticGetter(var12, var18, var14) : var0.findStaticSetter(var12, var18, var14)));
}
            return MethodHandles.dropArguments(var9, var3.parameterCount() - 2, new Class[]{Long.TYPE, Long.TYPE});
}
        catch (Exception var15) {
            StringBuilder var13 = new StringBuilder();
            var13.append(var15.getClass().getName()).append(" : ").append(var10 != null ? var10.toString() : (var11 != null ? var11.toString() : " null ")).append(" : ").append(var15.toString());
            throw new RuntimeException(var13.toString());
}
}
    public static void N(long var0, float var2, float var3) {
        long var4 = var0 ^ 0x5517A256A3A9L;
        RotationManager.S(var2, var3, 180.0f, var4);
}
    public static void Z(char var0, float var1, char var2, float var3, int var4) {
        long var5 = ((long)var0 << 48 | (long)var2 << 48 >>> 16 | (long)var4 << 32 >>> 32) ^ a;
        long var7 = var5 ^ 0x279A852ACA05L;
        RotationManager.g(var1, var3, var7, 180.0f);
}
    public void onUpdateWalkingPlayer(int var1, char var2, char var3, UpdateWalkingPlayerEvent var4) {
        var4.E(r);
        var4.l(G);
}
    public void onMoveInput(MoveInputEvent var1, long var2) {
        if (o.equals((Object)RotationMode.NONE)) {
            V = RotationManager.p();
        } else if (o.equals((Object)RotationMode.SILENT)) {
            V = r;
            float var4 = MathHelper.wrapAngleTo180_float((float)(MoveUtil.i(RotationManager.p(), MoveUtil.f(), MoveUtil.K()) - r + 22.5f));
            float var5 = var1.t();
            float var6 = var1.R();
            if (var5 == 0.0f && var6 == 0.0f) {
                return;
}
            switch ((int)(var4 + 180.0f) / 45 % 8) {
                case 0: {
                    var5 = -1.0f;
                    var6 = 0.0f;
                    break;
}
                case 1: {
                    var5 = -1.0f;
                    var6 = 1.0f;
                    break;
}
                case 2: {
                    var5 = 0.0f;
                    var6 = 1.0f;
                    break;
}
                case 3: {
                    var5 = 1.0f;
                    var6 = 1.0f;
                    break;
}
                case 4: {
                    var5 = 1.0f;
                    var6 = 0.0f;
                    break;
}
                case 5: {
                    var5 = 1.0f;
                    var6 = -1.0f;
                    break;
}
                case 6: {
                    var5 = 0.0f;
                    var6 = -1.0f;
                    break;
}
                case 7: {
                    var5 = -1.0f;
                    var6 = -1.0f;
}
}
            var1.i(var5);
            var1.A(var6);
        } else if (o.equals((Object)RotationMode.STRICT)) {
            V = r;
}
}
    private static Class b(long var0, long var2) {
        Class<?> var5 = null;
        int var4 = RotationManager.a(var0, 0L);
        Object var6 = f[var4];
        try {
            if (var6 instanceof String) {
                RotationManager.f[var4] = var5 = Class.forName(AbyssNameMap.map(g[var4]));
                return var5;
}
}
        catch (Exception var8) {
            throw new RuntimeException(var8.toString());
}
        return (Class)var6;
}
    public static void v(float var0, float var1, long var2, float var4) {
        float var7 = MathUtil.M(r, var0);
        var7 = Math.abs(var7) <= 1.0f ? 0.0f : MathUtil.c(var7, var4);
        var7 = MathUtil.q(var7, -var1, var1);
        var7 = MathUtil.H(var7);
        RotationManager.P(var7);
}
    private static CallSite a(MethodHandles.Lookup var0, String var1, MethodType var2) {
        MutableCallSite var3 = new MutableCallSite(var2);
        try {
            var3.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(MethodHandles.lookup().findStatic(RotationManager.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/Object;", RotationManager.class.getClassLoader())).asCollector(Object[].class, var2.parameterCount()), 0, var0, var3, var1, var2), var2));
            return var3;
}
        catch (Exception var5) {
            throw new RuntimeException("Abyss/util/RotationManager : " + var1 + " : " + var2.toString(), var5);
}
}
    public void onReceivePacket(ReceivePacketEvent var1) {
        if (var1.d instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook var2 = (S08PacketPlayerPosLook)var1.d;
            float var3 = var2.getYaw();
            float var4 = var2.getPitch();
            if (var2.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
                var3 += r;
}
            if (var2.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
                var4 += G;
}
            r = var3;
            G = var4;
            V = var3;
            b = r;
            h = G;
            if (Freelook.c()) {
                Freelook.B(r);
                Freelook.v(G);
}
}
}
    public static float p() {
        return Freelook.c() ? Freelook.v() : RotationManager.m.thePlayer.rotationYaw;
}
    public static void z(float var0, long var1) {
        if (RotationManager.G()) {
            float var3;
            var0 = MathHelper.clamp_float((float)var0, (float)0.0f, (float)1.0f);
            if (Float.isNaN(R)) {
                R = 0.0f;
}
            if (!((var3 = var0 - R) <= 0.0f)) {
                EntityPlayerSP var4 = RotationManager.m.thePlayer;
                float var5 = M * var3;
                float var6 = T * var3;
                if (var5 != 0.0f) {
                    var4.prevRotationYaw = var4.rotationYaw;
                    var4.rotationYaw += var5;
}
                if (var6 != 0.0f) {
                    var4.prevRotationPitch = var4.rotationPitch;
                    var4.rotationPitch = MathHelper.clamp_float((float)(var4.rotationPitch + var6), (float)-90.0f, (float)90.0f);
}
                R = var0;
}
}
}
    public static void K(float var0, float var3) {
        float var4 = RotationManager.p();
        float var5 = MathUtil.q(MathUtil.M(var4, var0), -var3, var3);
        var5 = MathUtil.H(var5);
        if (Freelook.c()) {
            Freelook.N += var5;
        } else {
            RotationManager.m.thePlayer.rotationYaw += var5;
}
}
    public static void a(long var0, float var2, float var3) {
        var2 = MathHelper.clamp_float((float)var2, (float)-90.0f, (float)90.0f);
        float var4 = MathUtil.q(MathUtil.M(RotationManager.s(), var2), -var3, var3);
        var4 = MathUtil.H(var4);
        RotationManager.R(RotationManager.s() + var4);
}
    private static Field a(Class var0, String var1, Class var2) {
        for (Field var6 : var0.getDeclaredFields()) {
            if (!var6.getName().equals(var1) || var6.getType() != var2) continue;
            return var6;
}
        return null;
}
    public static void V(float var0, float var1, long var2, float var4, float var5) {
        long var8 = var2 ^ 0x72D8DEEF880DL;
        RotationManager.h(var0, var4, var5, var8);
        RotationManager.l(var1, 0L, var4, var5);
}
    public static void V(long var0, float var2, float var3) {
        if (var2 > 90.0f) {
            var2 = 90.0f;
}
        if (var2 < -90.0f) {
            var2 = -90.0f;
}
        float var4 = MathUtil.q(MathUtil.M(G, var2), -var3, var3);
        var4 = MathUtil.H(var4);
        G += var4;
        X = true;
}
    private static void P(float var2) {
        r += var2;
        X = true;
}
    private static void a() {
        RotationManager.f[0] = "";
        RotationManager.g[0] = "Abyss.util.RotationManager";
        RotationManager.f[1] = Long.TYPE;
        RotationManager.g[1] = "java/lang/Long";
        RotationManager.f[2] = Float.TYPE;
        RotationManager.g[2] = "java/lang/Float";
        RotationManager.f[3] = Void.TYPE;
        RotationManager.g[3] = "java/lang/Void";
        RotationManager.f[4] = Short.TYPE;
        RotationManager.g[4] = "java/lang/Short";
        RotationManager.f[5] = Integer.TYPE;
        RotationManager.g[5] = "java/lang/Integer";
        RotationManager.f[6] = Boolean.TYPE;
        RotationManager.g[6] = "java/lang/Boolean";
        RotationManager.f[7] = "";
        RotationManager.g[7] = "Abyss.event.events.UpdateWalkingPlayerEvent";
        RotationManager.f[8] = "";
        RotationManager.g[8] = "Abyss.event.events.MoveInputEvent";
        RotationManager.f[9] = "";
        RotationManager.g[9] = "Abyss.util.MoveUtil";
        RotationManager.f[10] = "";
        RotationManager.g[10] = "net.minecraft.util.MathHelper";
        RotationManager.f[11] = "";
        RotationManager.g[11] = "Abyss.enums.RotationMode";
        RotationManager.f[12] = "";
        RotationManager.g[12] = "java.lang.Object";
        RotationManager.f[13] = "";
        RotationManager.g[13] = "Abyss.util.MathUtil";
        RotationManager.f[14] = "";
        RotationManager.g[14] = "Abyss.util.ClientUtil";
        RotationManager.f[15] = "";
        RotationManager.g[15] = "net.minecraft.client.entity.EntityPlayerSP";
        RotationManager.f[16] = "";
        RotationManager.g[16] = "Abyss.event.binder.RotationManagerBinder";
        RotationManager.f[17] = "";
        RotationManager.g[17] = "Abyss.event.EventBus";
        RotationManager.f[18] = "\u001abWG\u0016\u0004N(N%PoM3VTQ\bD+[F(";
        RotationManager.f[19] = "\u0017\u0001\u000f7B-K\u001a\\y%\u001b+HRfT/LAJkFV\u0011\u0011Vt\u001an\u001a\u001d\t:%";
        RotationManager.f[20] = "*mg\u0015jU~'~w.>}<f\u0006-Yt$k\u0014T\u0004$8tHl\u000f(g:w";
        RotationManager.f[21] = "XBB<}\t\f\b[^#b\u000f\u0013C/:\u0005\u0006\u000bN=C[V\u0013R'$RN\u001e@^z\u0002V\u0002Z9s\u001a[\u0010#&*\u0012N\u0010X9?\u00046IC:1]\u000eBOe\u007fb";
        RotationManager.f[22] = "";
        RotationManager.g[22] = "f9ij6c2sp\bW\b34mimcz6rm\b34mimcz6rm\b1hhyqo8pek\b1hhyqo8pek\b41hpr2fckt\b2hlz709d34\b";
        RotationManager.f[23] = "\njZV9}^ C4`\u0016\u001c2KLdm\u0003']4>v\u0004*BS7n\t8;\rgv\u0015\"\\\u0004\u007f{\u0007[\u0001Tcd[c\nX<*d";
        RotationManager.f[24] = "";
        RotationManager.g[24] = "f9ij6c2sp\bL\bpaxpksotn\b1hhyqo8pek\b1hhyqo8pek\b1hhyqo8pek\b1hhyqo8pek\b2hlz709d34\b";
        RotationManager.f[25] = "p|;\u0017\u0010y$6\"ua\u0012\"$'\u0012\u0013yup!\b.";
        RotationManager.f[26] = "%(FOz=qb_-?VryG\\=1{aJND";
        RotationManager.f[27] = "v@MC9n\"\nT!y\u0005!\u0011LP~b(\tAB\u0007<x\u0011]X`5`\u001cO!\u007flh\tOZ`y~q\u0015Agta\u0016\u001cYjf\u0018KLEu: @@\u001a;\u0005";
        RotationManager.f[28] = "|e4\u001eo\r(/-|\tfj=%\u00042\u001du(3|h\u0006r%,\u001ba\u001e\u007f7UF1\u0002`kmM=].T";
        RotationManager.f[29] = "";
        RotationManager.g[29] = "f9ij6c2sp\ba\bpaxpksotn\b1hhyqo8pek\b1hhyqo8pek\b2hlz709d34\b";
        RotationManager.f[30] = "Zi5L[|\u000e#,.\u000b\u0017\r84_\u001cp\u0004 9Me.T8%W\u0002'L57.\\wT)-IUoY;TV\fgL;/I\u0019q4b4J\u0017(\fi8\u0015Y\u0017";
        RotationManager.f[31] = "){(\u0011\u001aOn'!\u001c ^\u0017}-\u0016QFpt5\u001bC?-$)\u0004\u001f\u0007&(vJ ";
        RotationManager.f[32] = "F\u0018\u0005\f-2\u0001D\f\u0001\u0017\u0018x\u001e\u0000\u000bf;\u001f\u0017\u0018\u0006tB";
        RotationManager.f[33] = "Ez\t\u0010a+Hb\u0003\u000f\u000b\u0014t8P\u0002lh\u001fo\u0004\u0004vU";
        RotationManager.f[34] = "~\u000e2i\f\u0019*D+\u000blr)_3zK\u0015 G>h2Kp_\"rUBhR0\u000bJ\u001b`G0pU\u000ev?jkR\u0003iXcs_\u0011\u0010\u00063kC\u000bw\u000f+fQr*_7y\rJ!Sh72";
        RotationManager.f[35] = "T|vyGI\u0012d{r}wj;3|\u0005NPia\u007f\u00014";
        RotationManager.f[36] = "#`MMZPw*T/>;t1L^\u001d\\})ALd\u0001-5^\u0010\\\n!j\u0010/";
        RotationManager.f[37] = "";
        RotationManager.g[37] = "f9ij6c2sp\bz\b1hhyqo8pek\bpaxpksotn\b2hlz709d34\b";
        RotationManager.f[38] = "7K\u007f[y\u000ep\u0017vVC\u0002\tMz\\2\u0007nDbQ ~";
        RotationManager.f[39] = "vL1yNN0T<rtRH\u000e-|\u0005J/\u00075q\u00173qW-m\rTxO \u007ft\n(W<e\u0013\u00030Z.\u001cMS(F4{DK%TM";
        RotationManager.f[40] = "\u0004b7\u0005-}C>>\b\u0017D:d2\u0002ft]m*\u000ft\r\u0000=6\u0010(5\u000b1i^\u0017";
        RotationManager.f[41] = "o\u000b,x9w-F~n]',\u001cvWbwhF'W2Ih\u001a}q$.a\u0002pc]p1\u001aly:y)\u0017~\u0000";
        RotationManager.f[42] = "#\u0011aB|HlNjO\u0000v\u0012Gg@qJuN\u007fMc3+\u001egQyT\"\u0006jC\u0000\nr\u001evYg\u0003j\u0013d ";
        RotationManager.f[43] = "";
        RotationManager.g[43] = "f9ij6c2sp\bV\bpaxpksotn\b1hhyqo8pek\b1hhyqo8pek\b2hlz709d34\b";
        RotationManager.f[44] = "";
        RotationManager.g[44] = "f9ij6c2sp\bl\b1hhyqo8pek\bpaxpksotn\b1hhyqo8pek\b1hhyqo8pek\b2hlz709d34\b";
        RotationManager.f[45] = "";
        RotationManager.g[45] = "f9ij6c2sp\bK\b1hhyqo8pek\bpaxpksotn\b1hhyqo8pek\b2hlz709d34\b";
        RotationManager.f[46] = "W804\u001a\u0017\u0003r)VK|\u0000i1']\u001b\tq<5$EYi /CLAd2V\\\u0015Iq2-C\u0000_\th6D\r@na.I\u001f9312VC\u00018=m\u0018|";
        RotationManager.f[47] = "Y$}hDX\t82/'&3)m,_]] e5_c]sb2\u0019\b\t9{P_\nC:`+@\u001fUB90C\u0011\fz2<\u001c_3";
        RotationManager.f[48] = "\u0011uI^$&Mn\u001a\u0010C9-<\u0014\u000f2$J5\f\u0002 ]\u0017e\u0010\u001d|e\u001ciOSC";
        RotationManager.f[49] = "\"\u0014$]o\u007fv^=?\n\u0014uE%N(s|](\\Q-,E4F6$4H&?ht,T<Xal!FEG8d4F>X-rL\u001f%[#+t\u0014)\u0004m\u0014";
        RotationManager.f[50] = "\u007fSr\u0006ra1]v\u001a\u0010k9Qg\u0003k\u0006pW`\u001f+h~KeZ\u0010:)QiZ{m}Wsg";
        RotationManager.f[51] = "c\u0017W[>R%\u000fZP\u0004A]P\u0012^|Ug\u0002@]x/";
        RotationManager.f[52] = "1\u0011vyb<e[o\u001b?W'Igc?,8\\q\u001bf7;R(#m;d\u001c\u0017";
        RotationManager.f[53] = "H<\u0001eHK@7Wv%K\\;\u0006X\u001a\u001d\u0018dPXLH!a\u0004rB\u0018J6PtX%";
}
    public void onPreTick(PreTickEvent var1, long var2) {
        if (RotationManager.G()) {
            RotationManager.z(1.0f, 0L);
            RotationManager.k(0L);
}
}
    public static void O(long var0) {
        var0 = a ^ var0;
        X = false;
        o = RotationMode.NONE;
        U = false;
        RotationManager.k(0L);
        if (r != RotationManager.p()) {
            if (RotationManager.m.thePlayer.isRiding()) {
                r = RotationManager.p();
            } else {
                r += MathUtil.M(r, RotationManager.p());
                RotationManager.m.thePlayer.prevRotationYaw = r;
                RotationManager.r(r);
}
            V = RotationManager.p();
}
        if (G != RotationManager.s()) {
            RotationManager.m.thePlayer.prevRotationPitch = G = RotationManager.s();
}
        if (RotationManager.m.thePlayer.isRiding()) {
            I = r;
            K = G;
}
}
   private static Method d(long var0, long var2) {
      int var4 = a(var0, var2);
      Object var5 = f[var4];
      if (!(var5 instanceof String)) {
         return (Method)var5;
      }

      String var6 = g[var4];
      int var7 = var6.indexOf(8);
      Class var8 = b(Long.parseLong(var6.substring(0, var7), 36), 0L);
      int var9 = var6.indexOf(8, ++var7);
      String var10 = var6.substring(var7, var9);
      int var11 = -1;
      int var12 = var9;

      do {
         var11++;
         var12++;
      } while ((var12 = var6.indexOf(8, var12)) > -1);

      int var13;
      Class[] var14 = new Class[var13 = var11 - 1];
      Class var15 = null;
      var12 = var9 + 1;

      for (int var16 = 0; var16 < var11; var16++) {
         int var17 = var6.indexOf(8, var12);
         var15 = b(Long.parseLong(var6.substring(var12, var17), 36), 0L);
         if (var16 < var13) {
            var14[var16] = var15;
         }
      }

      Class var23 = var8;

      while (true) {
         Method var26 = a(var23, var10, var15, var13, var14);
         if (var26 != null) {
            f[var4] = var26;
            return var26;
         }

         if (var23.getName().equals("java.lang.Object")) {
            break;
         }

         if ((var23 = var23.getSuperclass()) == null) {
            var23 = b(892700819680181L, 0L);
            break;
         }
      }

      var23 = var8;

      while (true) {
         Class[] var27;
         if ((var27 = var23.getInterfaces()) != null) {
            for (int var18 = 0; var18 < var27.length; var18++) {
               Method var19 = b(var27[var18], var10, var15, var13, var14);
               if (var19 != null) {
                  f[var4] = var19;
                  return var19;
               }
            }
         }

         if (var23.getName().equals("java.lang.Object")) {
            StringBuffer var28 = new StringBuffer();
            var28.append("NoSuchMethodException in ").append(var8.getName()).append(' ').append(var15.getName()).append(' ').append(var10).append('(');
            int var29 = 0;

            while (var29 < var13) {
               var28.append(var14[var29].getName());
               if (++var29 < var13) {
                  var28.append(", ");
               }
            }

            var28.append(')');
            throw new RuntimeException(var28.toString());
         }

         if ((var23 = var23.getSuperclass()) == null) {
            var23 = b(892700819680181L, 0L);
         }
      }
   }
    public static float[] N(long var0) {
        return new float[]{RotationManager.p(), RotationManager.s()};
}
    private static void zkm$unresolved$0$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_oN_h_OR_Abyss_oN_f_y_slots_21_30(float var0, float var1, float var2, long var3, long var7) {
        try {
            MethodType var9 = MethodType.fromMethodDescriptorString("(FFFJJJ)V", RotationManager.class.getClassLoader());
            MethodHandles.explicitCastArguments(RotationManager.a(MethodHandles.lookup(), null, "H", var9, 6837697899622233875L, var7), var9).invoke(var0, var1, var2, var3, 6837697899622233875L, var7);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private static float zkm$unresolved$2$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_oN_p_OR_Abyss_oN_s_y_slots_18_26(long var2) {
        try {
            MethodType var4 = MethodType.fromMethodDescriptorString("(JJ)F", RotationManager.class.getClassLoader());
            return (float)MethodHandles.explicitCastArguments(RotationManager.a(MethodHandles.lookup(), null, "H", var4, 8569705496824988010L, var2), var4).invoke((long)8569705496824988010L, (long)var2);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private static float zkm$unresolved$3$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_oN_p_OR_Abyss_oN_s_y_slots_18_26(long var2) {
        try {
            MethodType var4 = MethodType.fromMethodDescriptorString("(JJ)F", RotationManager.class.getClassLoader());
            return (float)MethodHandles.explicitCastArguments(RotationManager.a(MethodHandles.lookup(), null, "H", var4, 8569705496824988010L, var2), var4).invoke((long)8569705496824988010L, (long)var2);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private static void zkm$clinit() {
        try {
            long var11 = a ^ 47693901268210L;
            f = new Object[54];
            g = new String[54];
            a();
            e = new HashMap(13);
            Cipher var0;
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var1 = 1; var1 < 8; ++var1) {
                var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
            }
            (var0 = Cipher.getInstance("DES/CBC/NoPadding")).init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var6 = new long[6];
            int var3 = 0;
            String var4 = "\u00f6\u0086G\u0081V\u00c98o\u0016\u008fS\u00c3\"\u000e\u00ec#\u00b0\u00e6\u0019O\u0000\u00b9\u00c9\u00ab\u00a5\u0095\u000b\u0098%\u0093V\u0094";
            int var5 = "\u00f6\u0086G\u0081V\u00c98o\u0016\u008fS\u00c3\"\u000e\u00ec#\u00b0\u00e6\u0019O\u0000\u00b9\u00c9\u00ab\u00a5\u0095\u000b\u0098%\u0093V\u0094".length();
            int var2 = 0;
            block6: while (true) {
                int var10001 = var2;
                byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                long[] var18 = var6;
                var10001 = var3++;
                long var21 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                int var23 = -1;
                while (true) {
                    long var8 = var21;
                    byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                    long var25 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                    switch (var23) {
                        case 0: {
                            var18[var10001] = var25;
                            if (var2 < var5) break;
                            c = var6;
                            d = new Integer[6];
                            return;
}
                        default: {
                            var18[var10001] = var25;
                            if (var2 < var5) continue block6;
                            var4 = "\u00caw\u0013\u0012\u00e4i<\u001c\u008f2\u0010mG\u00f9\u00b3r";
                            var5 = "\u00caw\u0013\u0012\u00e4i<\u001c\u008f2\u0010mG\u00f9\u00b3r".length();
                            var2 = 0;
}
}
                    int var20 = var2;
                    var7 = var4.substring(var20, var2 += 8).getBytes("ISO-8859-1");
                    var18 = var6;
                    var10001 = var3++;
                    var21 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                    var23 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var16) {
            throw new RuntimeException(var16);
}
}
    static {
        a = 115951112047268L;
        zkm$clinit();
        KEY_OFFSETS = new byte[]{29, 33, 22, 12, 21, 60, 20, 61, 56, 24, 11, 26, 45, 41, 58, 63, 50, 10, 15, 4, 25, 43, 19, 6, 23, 32, 46, 8, 0, 39, 34, 5, 37, 30, 48, 16, 27, 18, 31, 17, 59, 1, 57, 3, 35, 42, 47, 28, 44, 9, 40, 51, 49, 54, 53, 13, 52, 38, 55, 7, 36, 14, 62, 2};
        m = MinecraftRef.c((byte)0, 0L);
        o = RotationMode.NONE;
        X = false;
        J = false;
        R = Float.NaN;
        U = false;
}
}