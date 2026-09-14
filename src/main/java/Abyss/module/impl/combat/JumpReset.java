/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.MathHelper
 */
package Abyss.module.impl.combat;

import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.JumpResetBinder;
import Abyss.event.events.KnockbackEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PostUpdateEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.internal.restore.AbyssNameMap;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.EntityUtil;
import Abyss.util.MathUtil;
import Abyss.util.MoveUtil;
import Abyss.util.RotationManager;
import Abyss.util.TimerUtil;
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
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public class JumpReset
extends Module
implements EventSubscriber {
    public static PercentageSetting chance;
    public static BooleanSetting enemies;
    
    private static Map m;
    private boolean J;
    private boolean d;
    public static BooleanSetting bots;
    public static BooleanSetting bosses;
    public static BooleanSetting animals;
    private static String[] u;
    public static BooleanSetting requireMoving;
        public static BooleanSetting teammates;
    private final TimerUtil t;
    public static BooleanSetting mobs;
    public static BooleanSetting reduce;
    public static HeaderSetting targetSettings;
    public static NumberSetting fov;
    public static NumberSetting range;
    public static BooleanSetting friends;
        private static long[] c;
    private int o;
    public static BooleanSetting players;
    private static final byte[] KEY_OFFSETS;
    private static long n;

    @Override
    public final void x(long var1, EventBus var3) {
        JumpResetBinder.K(var3, this);
}
    private double atan2(double var1, double var3) {
        double var5 = Math.toDegrees(Math.atan2(-var1, var3));
        return MathHelper.func_76138_g((double)(var5 - 180.0));
}
    private static int b(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x7F1;
        if (g[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = c[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])m.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    m.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/module/impl/combat/JumpReset", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            JumpReset.g[var3] = var15;
}
        return g[var3];
}
    private static boolean d(long var0) {
        var0 = a ^ var0;
        long var2 = var0 ^ 0x204E85C10EE7L;
        long var4 = var0 ^ 0x1ED69C1CE512L;
        List var6 = EntityUtil.K(EntityUtil.F(range.L(), var2, fov.L()), players.c(), var4, mobs.c(), animals.c(), bosses.c(), friends.c(), enemies.c(), teammates.c(), bots.c());
        return !var6.isEmpty();
}
    private static int a(long var0, long var2) {
        int var16;
        int var4 = (int)((var0 ^= var2 << 48 | var2) >>> 46);
        if (u[var4] != null) {
            return var4;
}
        Object var5 = s[var4];
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
        JumpReset.u[var4] = new String(var13);
        return var4;
}
    public static boolean C(long var0) {
        long var2 = var0 ^ 0x485B4A4E033AL;
        return (!requireMoving.c() || MoveUtil.o()) && MathUtil.h(0.0f, 99.0f) < (float)chance.k() && !JumpReset.f.field_71439_g.func_70644_a(Potion.field_76430_j) && JumpReset.d(var2) && JumpReset.f.field_71439_g.func_70051_ag();
}
    public void onWorldLoad(long var1, WorldLoadEvent var3) {
        this.q(0L);
}
    private static Field a(Class var0, String var1, Class var2) {
        for (Field var6 : var0.getDeclaredFields()) {
            if (!var6.getName().equals(var1) || var6.getType() != var2) continue;
            return var6;
}
        return null;
}
    @Override
    public String g(long var1) {
        return chance.k() + "%";
}
    private static Field c(long var0, long var2) {
        int var4 = JumpReset.a(var0, var2);
        Object var5 = s[var4];
        if (!(var5 instanceof String)) {
            return (Field)var5;
}
        String var6 = u[var4];
        int var7 = var6.indexOf(8);
        Class var8 = JumpReset.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
        int var9 = var6.indexOf(8, ++var7);
        String var10 = var6.substring(var7, var9);
        Class var11 = JumpReset.b(Long.parseLong(var6.substring(++var9), 36), 0L);
        Class var12 = var8;
        while (true) {
            Field var13;
            if ((var13 = JumpReset.a(var12, var10, var11)) != null) {
                JumpReset.s[var4] = var13;
                return var13;
}
            Class<?>[] var14 = var12.getInterfaces();
            if (var14 != null) {
                for (int var15 = 0; var15 < var14.length; ++var15) {
                    var13 = JumpReset.b(var14[var15], var10, var11);
                    if (var13 == null) continue;
                    JumpReset.s[var4] = var13;
                    return var13;
}
}
            if (var12.getName().equals("java.lang.Object")) {
                StringBuffer var19 = new StringBuffer();
                var19.append("NoSuchFieldException in ").append(var8.getName()).append(' ').append(var11.getName()).append(' ').append(var10);
                throw new RuntimeException(var19.toString());
}
            if ((var12 = var12.getSuperclass()) != null) continue;
            var12 = JumpReset.b(1039626631182229L, 0L);
}
}
    private static Field b(Class var0, String var1, Class var2) {
        Field var3 = JumpReset.a(var0, var1, var2);
        if (var3 != null) {
            return var3;
}
        Class<?>[] var4 = var0.getInterfaces();
        if (var4 != null) {
            for (int var5 = 0; var5 < var4.length; ++var5) {
                var3 = JumpReset.b(var4[var5], var1, var2);
                if (var3 == null) continue;
                return var3;
}
}
        return null;
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
    public JumpReset(long var1) {
        super(a ^ var1 ^ 0x3FAA8FAC9049L);
        this.declare("JumpReset", Category.Combat, "JumpReset and reduce knockback in combat", new Setting[0]);
        var1 = a ^ var1;
        this.t = new TimerUtil();
        this.d = false;
        this.J = false;
        this.o = 0;
}
    private void q(long var1) {
        this.d = false;
        this.o = 0;
        if (this.J) {
            RotationManager.O(123115463851087L);
            this.J = false;
}
}
    private void q(double var1, long var3, double var5) {
        this.d = true;
        this.o = 3;
        if (reduce.c() && !RotationManager.X) {
            RotationManager.n(RotationMode.SILENT);
            RotationManager.I((float)this.atan2(var1, var5), 0L);
            this.J = true;
            this.t.W();
}
}
    private static Object a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, Object[] var4) throws Throwable {
        int var5 = var4.length - 2;
        long var6 = (Long)var4[var5];
        long var9 = (Long)var4[++var5];
        MethodHandle var8 = JumpReset.a(var0, var1, var2, var3, var6, var9);
        var1.setTarget(MethodHandles.explicitCastArguments(var8, var3));
        return var8.asSpreader(Object[].class, var4.length).invoke(var4);
}
    @Override
    public void A(long var1) {
        this.q(0L);
}
    public void onPostUpdate(long var1, PostUpdateEvent var3) {
        if (this.o > 0) {
            --this.o;
        } else if (this.t.Q(n) && this.J) {
            if (!ModuleManager.I.o()) {
                RotationManager.O(123115463851087L);
}
            this.J = false;
}
}
    private static Method d(long var0, long var2) {
        Class var23;
        Class var15;
        Class[] var14;
        int var13;
        String var10;
        Class var8;
        block10: {
            int var4 = JumpReset.a(var0, var2);
            Object var5 = s[var4];
            if (!(var5 instanceof String)) {
                return (Method)var5;
}
            String var6 = u[var4];
            int var7 = var6.indexOf(8);
            var8 = JumpReset.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
            int var9 = var6.indexOf(8, ++var7);
            var10 = var6.substring(var7, var9);
            int var11 = -1;
            int var12 = var9;
            do {
                ++var11;
                ++var12;
            } while ((var12 = var6.indexOf(8, var12)) > -1);
            var13 = var11 - 1;
            var14 = new Class[var13];
            var15 = null;
            var12 = var9 + 1;
            for (int var16 = 0; var16 < var11; ++var16) {
                int var17 = var6.indexOf(8, var12);
                var15 = JumpReset.b(Long.parseLong(var6.substring(var12, var17), 36), 0L);
                if (var16 >= var13) continue;
                var14[var16] = var15;
}
            var23 = var8;
            do {
                Method var26;
                if ((var26 = JumpReset.a(var23, var10, var15, var13, var14)) != null) {
                    JumpReset.s[var4] = var26;
                    return var26;
}
                if (var23.getName().equals("java.lang.Object")) break block10;
            } while ((var23 = var23.getSuperclass()) != null);
            var23 = JumpReset.b(1039626631182229L, 0L);
}
        var23 = var8;
        while (true) {
            Class<?>[] var27;
            if ((var27 = var23.getInterfaces()) != null) {
                for (int var18 = 0; var18 < var27.length; ++var18) {
                    Method var19 = JumpReset.b(var27[var18], var10, var15, var13, var14);
                    if (var19 == null) continue;
                    JumpReset.s[var4] = var19;
                    return var19;
}
}
            if (var23.getName().equals("java.lang.Object")) {
                StringBuffer var28 = new StringBuffer();
                var28.append("NoSuchMethodException in ").append(var8.getName()).append(' ').append(var15.getName()).append(' ').append(var10).append('(');
                int var29 = 0;
                while (var29 < var13) {
                    var28.append(var14[var29].getName());
                    if (++var29 >= var13) continue;
                    var28.append(", ");
}
                var28.append(')');
                throw new RuntimeException(var28.toString());
}
            if ((var23 = var23.getSuperclass()) != null) continue;
            var23 = JumpReset.b(1039626631182229L, 0L);
}
}
    public void onKnockback(KnockbackEvent var1, long var2) {
        if (var1.f() > 0.0 && JumpReset.C(122264478076639L)) {
            this.q(var1.S(), 28209960205980L, var1.R());
}
}
    private static MethodHandle a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, long var4, long var6) {
        char var8 = var2.charAt(0);
        MethodHandle var9 = null;
        Field var10 = null;
        Method var11 = null;
        try {
            if (var8 != '\u00e9' && var8 != 'c' && var8 != '\u00ff' && var8 != 'Q') {
                var11 = JumpReset.d(var4, var6);
                Class<?> var17 = var11.getDeclaringClass();
                String var19 = var11.getName();
                MethodType var20 = MethodType.methodType(var11.getReturnType(), var11.getParameterTypes());
                var9 = var8 == 't' ? var0.findVirtual(var17, var19, var20) : (var8 == '\u00c7' ? var0.findStatic(var17, var19, var20) : var0.findSpecial(var17, var19, var20, var17));
            } else {
                var10 = JumpReset.c(var4, var6);
                Class<?> var12 = var10.getDeclaringClass();
                String var18 = var10.getName();
                Class<?> var14 = var10.getType();
                var9 = var8 == '\u00e9' ? var0.findGetter(var12, var18, var14) : (var8 == 'c' ? var0.findSetter(var12, var18, var14) : (var8 == '\u00ff' ? var0.findStaticGetter(var12, var18, var14) : var0.findStaticSetter(var12, var18, var14)));
}
            return MethodHandles.dropArguments(var9, var3.parameterCount() - 2, new Class[]{Long.TYPE, Long.TYPE});
}
        catch (Exception var15) {
            StringBuilder var13 = new StringBuilder();
            var13.append(var15.getClass().getName()).append(" : ").append(var10 != null ? var10.toString() : (var11 != null ? var11.toString() : " null ")).append(" : ").append(var15.toString());
            throw new RuntimeException(var13.toString());
}
}
    public void onMoveInput(long var1, MoveInputEvent var3) {
        if (this.d) {
            var3.i(1.0f);
            var3.O(true);
            this.d = false;
}
}
    private static Method b(Class var0, String var1, Class var2, int var3, Class[] var4) {
        Method var5 = JumpReset.a(var0, var1, var2, var3, var4);
        if (var5 != null) {
            return var5;
}
        Class<?>[] var6 = var0.getInterfaces();
        if (var6 != null) {
            for (int var7 = 0; var7 < var6.length; ++var7) {
                var5 = JumpReset.b(var6[var7], var1, var2, var3, var4);
                if (var5 == null) continue;
                return var5;
}
}
        return null;
}
    private static CallSite a(MethodHandles.Lookup var0, String var1, MethodType var2) {
        MutableCallSite var3 = new MutableCallSite(var2);
        try {
            var3.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(MethodHandles.lookup().findStatic(JumpReset.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/Object;", JumpReset.class.getClassLoader())).asCollector(Object[].class, var2.parameterCount()), 0, var0, var3, var1, var2), var2));
            return var3;
}
        catch (Exception var5) {
            throw new RuntimeException("Abyss/module/impl/combat/JumpReset : " + var1 + " : " + var2.toString(), var5);
}
}
    private static Class b(long var0, long var2) {
        Class<?> var5 = null;
        int var4 = JumpReset.a(var0, var2);
        Object var6 = s[var4];
        try {
            if (var6 instanceof String) {
                JumpReset.s[var4] = var5 = Class.forName(AbyssNameMap.map(u[var4]));
                return var5;
}
}
        catch (Exception var8) {
            throw new RuntimeException(var8.toString());
}
        return (Class)var6;
}
                Cipher var7 = Cipher.getInstance("DES/CBC/NoPadding");
            var7.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var13 = new long[5];
            int var10 = 0;
            String var11 = "1\u001d\u00b3Ww\u00ab \u00a9\u0098`\u00c8\u00d3\u00f1e\u00ab\u00a51\u001d?\u00f0U\u0004\u00e4\u009f";
            int var12 = "1\u001d\u00b3Ww\u00ab \u00a9\u0098`\u00c8\u00d3\u00f1e\u00ab\u00a51\u001d?\u00f0U\u0004\u00e4\u009f".length();
            int var9 = 0;
            block6: while (true) {
                int var10001 = var9;
                byte[] var14 = var11.substring(var10001, var9 += 8).getBytes("ISO-8859-1");
                long[] var20 = var13;
                var10001 = var10++;
                long var25 = ((long)var14[0] & 0xFFL) << 56 | ((long)var14[1] & 0xFFL) << 48 | ((long)var14[2] & 0xFFL) << 40 | ((long)var14[3] & 0xFFL) << 32 | ((long)var14[4] & 0xFFL) << 24 | ((long)var14[5] & 0xFFL) << 16 | ((long)var14[6] & 0xFFL) << 8 | (long)var14[7] & 0xFFL;
                int var29 = -1;
                while (true) {
                    long var15 = var25;
                    byte[] var17 = var7.doFinal(new byte[]{(byte)(var15 >>> 56), (byte)(var15 >>> 48), (byte)(var15 >>> 40), (byte)(var15 >>> 32), (byte)(var15 >>> 24), (byte)(var15 >>> 16), (byte)(var15 >>> 8), (byte)var15});
                    long var32 = ((long)var17[0] & 0xFFL) << 56 | ((long)var17[1] & 0xFFL) << 48 | ((long)var17[2] & 0xFFL) << 40 | ((long)var17[3] & 0xFFL) << 32 | ((long)var17[4] & 0xFFL) << 24 | ((long)var17[5] & 0xFFL) << 16 | ((long)var17[6] & 0xFFL) << 8 | (long)var17[7] & 0xFFL;
                    switch (var29) {
                        case 0: {
                            long var28;
                            var20[var10001] = var32;
                            if (var9 < var12) break;
                            c = var13;
                            g = new Integer[5];
                            var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                            for (int var1 = 1; var1 < 8; ++var1) {
                                var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
}
                            Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                            var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                            byte[] var4 = var0.doFinal(new byte[]{-85, -107, 103, -92, 3, -63, 6, -7});
                            n = var28 = ((long)var4[0] & 0xFFL) << 56 | ((long)var4[1] & 0xFFL) << 48 | ((long)var4[2] & 0xFFL) << 40 | ((long)var4[3] & 0xFFL) << 32 | ((long)var4[4] & 0xFFL) << 24 | ((long)var4[5] & 0xFFL) << 16 | ((long)var4[6] & 0xFFL) << 8 | (long)var4[7] & 0xFFL;
                            return;
}
                        default: {
                            var20[var10001] = var32;
                            if (var9 < var12) continue block6;
                            var11 = "\u00d4\u00e4\t\u00a3\u00bb,\u00cbW\u00c5[sa\u009b\u0087\u00aa\u00fd";
                            var12 = "\u00d4\u00e4\t\u00a3\u00bb,\u00cbW\u00c5[sa\u009b\u0087\u00aa\u00fd".length();
                            var9 = 0;
}
}
                    int var23 = var9;
                    var14 = var11.substring(var23, var9 += 8).getBytes("ISO-8859-1");
                    var20 = var13;
                    var10001 = var10++;
                    var25 = ((long)var14[0] & 0xFFL) << 56 | ((long)var14[1] & 0xFFL) << 48 | ((long)var14[2] & 0xFFL) << 40 | ((long)var14[3] & 0xFFL) << 32 | ((long)var14[4] & 0xFFL) << 24 | ((long)var14[5] & 0xFFL) << 16 | ((long)var14[6] & 0xFFL) << 8 | (long)var14[7] & 0xFFL;
                    var29 = 0;
}
                break;
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var18) {
            throw new RuntimeException(var18);
}
}
    static {
        KEY_OFFSETS = new byte[]{28, 45, 41, 18, 59, 53, 5, 24, 39, 54, 15, 33, 7, 40, 60, 52, 30, 55, 58, 11, 43, 16, 34, 20, 2, 49, 26, 56, 61, 63, 6, 23, 37, 29, 44, 47, 8, 32, 51, 46, 14, 62, 50, 57, 17, 10, 19, 4, 9, 0, 36, 27, 48, 3, 12, 21, 13, 42, 38, 31, 22, 1, 35, 25};
        chance = new PercentageSetting("Chance", 100);
        requireMoving = new BooleanSetting("Require-moving", true);
        reduce = new BooleanSetting("Reduce", false);
        players = new BooleanSetting("Players", true);
        mobs = new BooleanSetting("Mobs", false);
        animals = new BooleanSetting("Animals", false);
        bosses = new BooleanSetting("Bosses", false);
        friends = new BooleanSetting("Friends", false);
        enemies = new BooleanSetting("Enemies", true);
        teammates = new BooleanSetting("Teammates", false);
        bots = new BooleanSetting("Bots", false);
        fov = new NumberSetting("FOV", 180.0f, 0.0f, 360.0f, 1.0f);
        range = new NumberSetting("Range", 5.0f, 0.0f, 10.0f, 0.1f);
        targetSettings = new HeaderSetting("Target settings");
}
}