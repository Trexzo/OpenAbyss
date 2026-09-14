/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 */
package Abyss.module.impl.world;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BridgeAssistBinder;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.internal.restore.AbyssNameMap;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.world.BridgeAssistPlacement;
import Abyss.module.impl.world.BridgeAssistRotation;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.BlockUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.RotationManager;
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
import java.util.ArrayList;
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
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class BridgeAssist
extends Module
implements EventSubscriber {
    private int s;
    private boolean G;
    public static NumberSetting sneakOnJumpTime;
    
    public static BooleanSetting notMovingForward;
    private boolean H;
    private int n;
    private static String[] h;
        private boolean R;
    private static Object[] g;
    public static NumberSetting unsneakDelay;
    private boolean T;
    public static NumberSetting edgeOffset;
    private static long[] b;
    private int Y;
    private static Integer[] d;
    private static EnumFacing[] c;
    private int y;
    public static BooleanSetting requireSneak;
    public static BooleanSetting requireLookingDown;
    public static BooleanSetting silentRotation;
    private static final byte[] KEY_OFFSETS;
    public static BooleanSetting requireHoldingBlocks;

    @Override
    public String g(long var1) {
        double var3 = edgeOffset.L();
        return var3 == Math.rint(var3) ? Integer.toString((int)var3) : Double.toString((double)Math.round(var3 * 100.0) / 100.0);
}
    private void P(MoveInputEvent var3, boolean var4) {
        var3.x(true);
        this.T = true;
        if (var4) {
            this.s = -1;
}
        this.m(var3, 0L);
}
    private void O(long var1, MoveInputEvent var3, boolean var4) {
        long var5 = var1 ^ 0x7BC1AF741E82L;
        if (!requireSneak.c()) {
            var3.x(false);
        } else if (!this.T || !this.isGetKeyCode(var5) || !this.H && BridgeAssist.f.field_71439_g.field_70122_E) {
            if (this.R) {
                var3.x(false);
}
        } else {
            KeyBinding.func_74510_a((int)BridgeAssist.f.field_71474_y.field_74311_E.func_151463_i(), (boolean)false);
            var3.x(false);
            this.R = true;
}
        this.T = false;
        this.H = false;
        if (var4) {
            this.p(0L);
}
}
    private static CallSite a(MethodHandles.Lookup var0, String var1, MethodType var2) {
        MutableCallSite var3 = new MutableCallSite(var2);
        try {
            var3.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(MethodHandles.lookup().findStatic(BridgeAssist.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/Object;", BridgeAssist.class.getClassLoader())).asCollector(Object[].class, var2.parameterCount()), 0, var0, var3, var1, var2), var2));
            return var3;
}
        catch (Exception var5) {
            throw new RuntimeException("Abyss/module/impl/world/BridgeAssist : " + var1 + " : " + var2.toString(), var5);
}
}
    private void C(short var1, char var2, MoveInputEvent var3, int var4, boolean var5) {
        long var6 = ((long)var1 << 48 | (long)var2 << 48 >>> 16 | (long)var4 << 32 >>> 32) ^ a;
        long var8 = var6 ^ 0x5C79FAAC1ECDL;
        int var12 = BridgeAssist.f.field_71439_g.field_70173_aa;
        if (this.s == -1 && this.Y == -1) {
            this.s = var12;
            this.n = this.q(Math.max(0.0f, unsneakDelay.L() - 50.0f));
}
        if (this.Y != -1 && var12 - this.Y < this.y) {
            this.P(var3, false);
        } else if (this.s != -1 && var12 - this.s < this.n) {
            this.P(var3, false);
        } else {
            this.O(var8, var3, var5);
}
}
    private static Field a(Class var0, String var1, Class var2) {
        for (Field var6 : var0.getDeclaredFields()) {
            if (!var6.getName().equals(var1) || var6.getType() != var2) continue;
            return var6;
}
        return null;
}
    private double b(AxisAlignedBB var1) {
        AxisAlignedBB var2 = new AxisAlignedBB(var1.field_72340_a, var1.field_72338_b - 0.01, var1.field_72339_c, var1.field_72336_d, var1.field_72338_b, var1.field_72334_f);
        List var3 = BridgeAssist.f.field_71441_e.func_72945_a((Entity)BridgeAssist.f.field_71439_g, var2);
        if (var3.isEmpty()) {
            return Double.NaN;
}
        double var4 = (var1.field_72340_a + var1.field_72336_d) / 2.0;
        double var6 = (var1.field_72339_c + var1.field_72334_f) / 2.0;
        double var8 = Double.MAX_VALUE;
        for (AxisAlignedBB var11 : var3) {
            double var12 = Math.max(var11.field_72340_a, Math.min(var4, var11.field_72336_d));
            double var14 = Math.max(var11.field_72339_c, Math.min(var6, var11.field_72334_f));
            double var16 = Math.abs(var4 - var12);
            double var18 = Math.abs(var6 - var14);
            var8 = Math.min(var8, Math.max(var16, var18));
}
        return var8;
}
    private float[] Y(float var1, float var2, float var3, float var4, float var5) {
        var5 = MathHelper.func_76131_a((float)var5, (float)1.0f, (float)(var5 * 2.0f));
        float var6 = MathHelper.func_76142_g((float)(var3 - var1));
        float var7 = var4 - var2;
        float var8 = var1 + MathHelper.func_76131_a((float)var6, (float)(-var5), (float)var5);
        float var9 = MathHelper.func_76131_a((float)(var2 + MathHelper.func_76131_a((float)var7, (float)(-var5), (float)var5)), (float)-90.0f, (float)90.0f);
        return new float[]{var8, var9};
}
    @Override
    public final void x(long var1, EventBus var3) {
        BridgeAssistBinder.v(var3, this);
}
    private void K(MoveInputEvent var1, long var2) {
        this.T = false;
        this.p(0L);
        if (requireSneak.c()) {
            this.m(var1, 0L);
}
}
    private void p(long var1) {
        this.s = -1;
        this.Y = -1;
        this.y = -1;
        this.n = -1;
}
    private static Field b(Class var0, String var1, Class var2) {
        Field var3 = BridgeAssist.a(var0, var1, var2);
        if (var3 != null) {
            return var3;
}
        Class<?>[] var4 = var0.getInterfaces();
        if (var4 != null) {
            for (int var5 = 0; var5 < var4.length; ++var5) {
                var3 = BridgeAssist.b(var4[var5], var1, var2);
                if (var3 == null) continue;
                return var3;
}
}
        return null;
}
    private static Object a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, Object[] var4) throws Throwable {
        int var5 = var4.length - 2;
        long var6 = (Long)var4[var5];
        long var9 = (Long)var4[++var5];
        MethodHandle var8 = BridgeAssist.a(var0, var1, var2, var3, var6, var9);
        var1.setTarget(MethodHandles.explicitCastArguments(var8, var3));
        return var8.asSpreader(Object[].class, var4.length).invoke(var4);
}
    public void onPreMouseInput(int var1, int var2, PreMouseInputEvent var3, char var4) {
        long var5 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x63A275F4F037L;
        int var9 = (int)((var5 ^ 0x22E90FE1DD2EL) >>> 32);
        int var10 = (int)((var5 ^ 0x22E90FE1DD2EL) << 32 >>> 48);
        int var11 = (int)((var5 ^ 0x22E90FE1DD2EL) << 48 >>> 48);
        long var14 = var5 ^ 0x2B2F249E1D3AL;
        if (silentRotation.c() && BridgeAssist.f.field_71462_r == null && !BridgeAssist.f.field_71439_g.field_71075_bZ.field_75100_b) {
            ItemStack var16 = BridgeAssist.f.field_71439_g.func_70694_bm();
            if (var16 != null && var16.func_77973_b() instanceof ItemBlock) {
                if (requireLookingDown.c() && RotationManager.s() < 70.0f) {
                    this.o(var7);
                } else if (notMovingForward.c() && BridgeAssist.f.field_71439_g.field_71158_b.field_78900_b > 0.0f) {
                    this.o(var7);
                } else {
                    BridgeAssistRotation var17 = this.s(var9, RotationManager.G, (short)var10, var11, BridgeAssist.f.field_71442_b.func_78757_d());
                    if (var17 == null) {
                        this.o(var7);
                    } else {
                        float[] var18 = this.Y(RotationManager.r, RotationManager.G, var17.r, var17.l, 15.0f);
                        RotationManager.I(var18[0], 0L);
                        RotationManager.A(var14, var18[1]);
                        this.G = true;
}
}
            } else {
                this.o(var7);
}
        } else {
            this.o(var7);
}
}
    private static void a() {
        BridgeAssist.g[0] = "";
        BridgeAssist.h[0] = "Abyss.util.RotationManager";
        BridgeAssist.g[1] = Float.TYPE;
        BridgeAssist.h[1] = "java/lang/Float";
        BridgeAssist.g[2] = Long.TYPE;
        BridgeAssist.h[2] = "java/lang/Long";
        BridgeAssist.g[3] = Void.TYPE;
        BridgeAssist.h[3] = "java/lang/Void";
        BridgeAssist.g[4] = "";
        BridgeAssist.h[4] = "Abyss.event.binder.BridgeAssistBinder";
        BridgeAssist.g[5] = "";
        BridgeAssist.h[5] = "Abyss.event.EventBus";
        BridgeAssist.g[6] = "";
        BridgeAssist.h[6] = "Abyss.module.impl.world.BridgeAssist";
        BridgeAssist.g[7] = "";
        BridgeAssist.h[7] = "java.lang.Object";
        BridgeAssist.g[8] = "Rm5\u0003XUAl.c.5\b*=\u0000\u0017[W#4\rgLUcqZ\u001aGW4MY\u0003_H#*\u0011WITR";
        BridgeAssist.g[9] = "\u000eJmK\n\u0010\u001dKv+Mp\u0014\u0011$\u0017\f\r\u001f\u0013s+";
        BridgeAssist.g[10] = "R\u0005\u001fq\u001efA\u0004\u0004\u0011f\u0006\bB\u0017rQhWK\u001e\u007f!<UP\u001e`Ft\u0001F\u0002\u0011";
        BridgeAssist.g[11] = "!P\u0019L\u000ff!\bEHd(\u001aU\u000f\u000e\u00199b\u001f\u000e]\u0019V#\u0016\u0005S\u00148|\u001f\f^dj|\r\u0018T\u00073|\u0010\u000e0^2p\u0017\u0004W\u0016ff\u000bu";
        BridgeAssist.g[12] = "6\rbtU\f%\fy\u0014\u0011l,V+(S\u0011'T|\u0014";
        BridgeAssist.g[13] = "\"!\u00067\f\u00181 \u001dWrx8zOk\n\u00053x\u0018W\n\u00001}\u000e9U\t8p~mW\u00128o\u0019%\u0003\u0004$\u001e";
}
    private boolean isGetKeyCode(long var1) {
        long var3 = var1 ^ 0x5A878CE532C5L;
        return KeyBindUtil.V(BridgeAssist.f.field_71474_y.field_74311_E.func_151463_i(), var3);
}
    private static Class b(long var0, long var2) {
        Class<?> var5 = null;
        int var4 = BridgeAssist.a(var0, var2);
        Object var6 = g[var4];
        try {
            if (var6 instanceof String) {
                BridgeAssist.g[var4] = var5 = Class.forName(AbyssNameMap.map(h[var4]));
                return var5;
}
}
        catch (Exception var8) {
            throw new RuntimeException(var8.toString());
}
        return (Class)var6;
}
    private int q(double var1) {
        double var3 = var1 / 50.0;
        int var5 = (int)var3;
        return var5 + (Math.random() < var3 - (double)var5 ? 1 : 0);
}
    private BridgeAssistRotation s(int var1, float var2, short var3, int var4, double var5) {
        float var9 = RotationManager.p();
        AxisAlignedBB var10 = BridgeAssist.f.field_71439_g.func_174813_aQ();
        int var11 = MathHelper.func_76128_c((double)var10.field_72338_b) - 1;
        int var12 = MathHelper.func_76128_c((double)var10.field_72340_a);
        int var13 = MathHelper.func_76128_c((double)var10.field_72336_d);
        int var14 = MathHelper.func_76128_c((double)var10.field_72339_c);
        int var15 = MathHelper.func_76128_c((double)var10.field_72334_f);
        ArrayList<BridgeAssistPlacement> var16 = new ArrayList<BridgeAssistPlacement>();
        for (int var17 = var12; var17 <= var13; ++var17) {
            for (int var18 = var14; var18 <= var15; ++var18) {
                BlockPos var19 = new BlockPos(var17, var11, var18);
                if (BlockUtil.a$r1(var19)) continue;
                for (EnumFacing var23 : c) {
                    BlockPos var24 = var19.func_177972_a(var23);
                    if (!BlockUtil.a$r1(var24)) continue;
                    var16.add(new BridgeAssistPlacement(var19, var23));
}
}
}
        if (var16.isEmpty()) {
            return null;
}
        float var31 = Float.MAX_VALUE;
        float var32 = Float.NaN;
        BlockPos var33 = null;
        EnumFacing var34 = null;
        float var35 = 0.2f;
        float var36 = 60.0f;
        while (var36 <= 90.0f) {
            EnumFacing var26;
            float var37 = 1.0f + (float)(Math.random() * 2.0 - 1.0) * (0.3f + var35 * 0.4f);
            float var39 = Math.min(var36 += (var37 = MathHelper.func_76131_a((float)var37, (float)0.4f, (float)1.8f)), 90.0f);
            MovingObjectPosition var25 = BlockUtil.F(new float[]{var9, var39}, var5);
            if (var25 == null || var25.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK || (var26 = var25.field_178784_b) == EnumFacing.UP || var26 == EnumFacing.DOWN) continue;
            BlockPos var27 = var25.func_178782_a();
            for (BridgeAssistPlacement var29 : var16) {
                if (!var27.equals((Object)var29.Z) || var26 != var29.U) continue;
                float var30 = Math.abs(var39 - var2);
                if (!(var30 < var31)) break;
                var31 = var30;
                var32 = var39;
                var33 = var29.Z;
                var34 = var29.U;
                break;
}
            if (!(var36 >= 90.0f)) continue;
            return var33 != null && var34 != null && !Float.isNaN(var32) ? new BridgeAssistRotation(var9, var32) : null;
}
        return var33 != null && var34 != null && !Float.isNaN(var32) ? new BridgeAssistRotation(var9, var32) : null;
}
    public void onSendPacket(long var1, SendPacketEvent var3) {
        C08PacketPlayerBlockPlacement var4;
        if (var3.B instanceof C08PacketPlayerBlockPlacement && (var4 = (C08PacketPlayerBlockPlacement)var3.B).func_149568_f() != 255 && this.T && requireSneak.c()) {
            this.H = true;
}
}
    private static MethodHandle a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, long var4, long var6) {
        char var8 = var2.charAt(0);
        MethodHandle var9 = null;
        Field var10 = null;
        Method var11 = null;
        try {
            if (var8 != 'Y' && var8 != '\u00e2' && var8 != 'F' && var8 != '\u00ff') {
                var11 = BridgeAssist.d(var4, var6);
                Class<?> var17 = var11.getDeclaringClass();
                String var19 = var11.getName();
                MethodType var20 = MethodType.methodType(var11.getReturnType(), var11.getParameterTypes());
                var9 = var8 == 'C' ? var0.findVirtual(var17, var19, var20) : (var8 == 'O' ? var0.findStatic(var17, var19, var20) : var0.findSpecial(var17, var19, var20, var17));
            } else {
                var10 = BridgeAssist.c(var4, var6);
                Class<?> var12 = var10.getDeclaringClass();
                String var18 = var10.getName();
                Class<?> var14 = var10.getType();
                var9 = var8 == 'Y' ? var0.findGetter(var12, var18, var14) : (var8 == '\u00e2' ? var0.findSetter(var12, var18, var14) : (var8 == 'F' ? var0.findStaticGetter(var12, var18, var14) : var0.findStaticSetter(var12, var18, var14)));
}
            return MethodHandles.dropArguments(var9, var3.parameterCount() - 2, new Class[]{Long.TYPE, Long.TYPE});
}
        catch (Exception var15) {
            StringBuilder var13 = new StringBuilder();
            var13.append(var15.getClass().getName()).append(" : ").append(var10 != null ? var10.toString() : (var11 != null ? var11.toString() : " null ")).append(" : ").append(var15.toString());
            throw new RuntimeException(var13.toString());
}
}
    private static int b(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x5B60;
        if (d[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = b[var3];
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
                throw new RuntimeException("Abyss/module/impl/world/BridgeAssist", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            BridgeAssist.d[var3] = var15;
}
        return d[var3];
}
    private void o(long var1) {
        long var3 = var1 ^ 0x360C77841D92L;
        if (this.G) {
            RotationManager.O(var3);
            this.G = false;
}
}
    private static Method b(Class var0, String var1, Class var2, int var3, Class[] var4) {
        Method var5 = BridgeAssist.a(var0, var1, var2, var3, var4);
        if (var5 != null) {
            return var5;
}
        Class<?>[] var6 = var0.getInterfaces();
        if (var6 != null) {
            for (int var7 = 0; var7 < var6.length; ++var7) {
                var5 = BridgeAssist.b(var6[var7], var1, var2, var3, var4);
                if (var5 == null) continue;
                return var5;
}
}
        return null;
}
    private static int a(long var0, long var2) {
        int var16;
        int var4 = (int)((var0 ^= var2 << 48 | var2) >>> 46);
        if (h[var4] != null) {
            return var4;
}
        Object var5 = g[var4];
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
        BridgeAssist.h[var4] = new String(var13);
        return var4;
}
    private static Method d(long var0, long var2) {
        Class var23;
        Class var15;
        Class[] var14;
        int var13;
        String var10;
        Class var8;
        block10: {
            int var4 = BridgeAssist.a(var0, var2);
            Object var5 = g[var4];
            if (!(var5 instanceof String)) {
                return (Method)var5;
}
            String var6 = h[var4];
            int var7 = var6.indexOf(8);
            var8 = BridgeAssist.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
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
                var15 = BridgeAssist.b(Long.parseLong(var6.substring(var12, var17), 36), 0L);
                if (var16 >= var13) continue;
                var14[var16] = var15;
}
            var23 = var8;
            do {
                Method var26;
                if ((var26 = BridgeAssist.a(var23, var10, var15, var13, var14)) != null) {
                    BridgeAssist.g[var4] = var26;
                    return var26;
}
                if (var23.getName().equals("java.lang.Object")) break block10;
            } while ((var23 = var23.getSuperclass()) != null);
            var23 = BridgeAssist.b(497560263219879L, 0L);
}
        var23 = var8;
        while (true) {
            Class<?>[] var27;
            if ((var27 = var23.getInterfaces()) != null) {
                for (int var18 = 0; var18 < var27.length; ++var18) {
                    Method var19 = BridgeAssist.b(var27[var18], var10, var15, var13, var14);
                    if (var19 == null) continue;
                    BridgeAssist.g[var4] = var19;
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
            var23 = BridgeAssist.b(497560263219879L, 0L);
}
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
    private static Field c(long var0, long var2) {
        int var4 = BridgeAssist.a(var0, var2);
        Object var5 = g[var4];
        if (!(var5 instanceof String)) {
            return (Field)var5;
}
        String var6 = h[var4];
        int var7 = var6.indexOf(8);
        Class var8 = BridgeAssist.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
        int var9 = var6.indexOf(8, ++var7);
        String var10 = var6.substring(var7, var9);
        Class var11 = BridgeAssist.b(Long.parseLong(var6.substring(++var9), 36), 0L);
        Class var12 = var8;
        while (true) {
            Field var13;
            if ((var13 = BridgeAssist.a(var12, var10, var11)) != null) {
                BridgeAssist.g[var4] = var13;
                return var13;
}
            Class<?>[] var14 = var12.getInterfaces();
            if (var14 != null) {
                for (int var15 = 0; var15 < var14.length; ++var15) {
                    var13 = BridgeAssist.b(var14[var15], var10, var11);
                    if (var13 == null) continue;
                    BridgeAssist.g[var4] = var13;
                    return var13;
}
}
            if (var12.getName().equals("java.lang.Object")) {
                StringBuffer var19 = new StringBuffer();
                var19.append("NoSuchFieldException in ").append(var8.getName()).append(' ').append(var11.getName()).append(' ').append(var10);
                throw new RuntimeException(var19.toString());
}
            if ((var12 = var12.getSuperclass()) != null) continue;
            var12 = BridgeAssist.b(497560263219879L, 0L);
}
}
    public BridgeAssist(long var1) {
        super(a ^ var1 ^ 0x756A8105DBAL);
        this.declare("BridgeAssist", Category.World, "Sneak when you get close to the edge of the blocks", new Setting[0]);
        var1 = a ^ var1;
        this.y = -1;
        this.Y = -1;
        this.n = -1;
        this.s = -1;
        this.G = false;
}
    private AxisAlignedBB Y(float var1, float var4) {
        AxisAlignedBB var5 = BridgeAssist.f.field_71439_g.func_174813_aQ();
        if (var1 == 0.0f && var4 == 0.0f) {
            return var5.func_72317_d(BridgeAssist.f.field_71439_g.field_70159_w, 0.0, BridgeAssist.f.field_71439_g.field_70179_y);
}
        float var6 = MathHelper.func_76129_c((float)(var1 * var1 + var4 * var4));
        if (var6 < 1.0f) {
            var6 = 1.0f;
}
        double var7 = BridgeAssist.f.field_71439_g.func_70051_ag() ? 0.2873 : 0.221;
        float var9 = RotationManager.p();
        float var10 = MathHelper.func_76126_a((float)(var9 * (float)Math.PI / 180.0f));
        float var11 = MathHelper.func_76134_b((float)(var9 * (float)Math.PI / 180.0f));
        double var12 = (double)((var4 /= var6) * var11 - (var1 /= var6) * var10) * var7;
        double var14 = (double)(var1 * var11 + var4 * var10) * var7;
        return var5.func_72317_d(var12, 0.0, var14);
}
    private void m(MoveInputEvent var1, long var2) {
        if (this.R && this.isGetKeyCode(106499145851495L)) {
            KeyBinding.func_74510_a((int)BridgeAssist.f.field_71474_y.field_74311_E.func_151463_i(), (boolean)true);
            var1.x(true);
}
        this.R = false;
}
    public void onMoveInput(MoveInputEvent var1, long var2) {
        if (BridgeAssist.f.field_71462_r == null && !BridgeAssist.f.field_71439_g.field_71075_bZ.field_75100_b) {
            boolean var19 = this.isGetKeyCode(106499145851495L);
            boolean var20 = requireSneak.c();
            if (var19 && !var20) {
                this.p(0L);
            } else if (!var20 || var19 && (var1.t() != 0.0f || var1.R() != 0.0f)) {
                if (notMovingForward.c() && var1.t() > 0.0f) {
                    this.K(var1, 0L);
                } else if (requireLookingDown.c() && RotationManager.s() < 70.0f) {
                    this.K(var1, 0L);
                } else {
                    ItemStack var21;
                    if (requireHoldingBlocks.c() && ((var21 = BridgeAssist.f.field_71439_g.func_70694_bm()) == null || !(var21.func_77973_b() instanceof ItemBlock))) {
                        this.K(var1, 0L);
                        return;
}
                    if (!var1.d() || !BridgeAssist.f.field_71439_g.field_70122_E || var1.t() == 0.0f && var1.R() == 0.0f || !(sneakOnJumpTime.L() > 0.0f) || var20 && !this.R) {
                        AxisAlignedBB var25 = this.Y(var1.t(), var1.R());
                        double var22 = this.b(var25);
                        if (!Double.isNaN(var22)) {
                            if (var22 > (double)edgeOffset.L()) {
                                this.P(var1, true);
                            } else if (this.T) {
                                this.C((short)0, '\u2d98', var1, -1795877129, true);
}
                        } else if (var1.d() && (sneakOnJumpTime.L() <= 0.0f || var1.t() == 0.0f && var1.R() == 0.0f)) {
                            if (this.T) {
                                this.C((short)0, '\u2d98', var1, -1795877129, true);
}
                        } else if (BridgeAssist.f.field_71439_g.field_70122_E) {
                            this.P(var1, true);
                        } else if (this.T) {
                            this.C((short)0, '\u2d98', var1, -1795877129, true);
}
                    } else {
                        this.Y = BridgeAssist.f.field_71439_g.field_70173_aa;
                        this.y = this.q(sneakOnJumpTime.L());
                        this.P(var1, true);
}
}
            } else {
                if (!var19) {
                    this.p(0L);
}
                this.m(var1, 0L);
}
}
}
    @Override
    public void A(long var1) {
        long var3 = var1 ^ 0xC3476897D58L;
        long var7 = var1 ^ 0xF9FDC7ACA5AL;
        this.T = false;
        this.p(0L);
        this.o(var3);
        KeyBindUtil.o(var7, BridgeAssist.f.field_71474_y.field_74311_E.func_151463_i());
}
                Cipher var2 = Cipher.getInstance("DES/CBC/NoPadding");
            var2.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var8 = new long[7];
            int var5 = 0;
            String var6 = "\u00f2\\\u00854\u0003\u00d0\u00cc\u00a7\u00e8\u0019\u008d\u00d5G\u008es`?{\u00e9\u00e8\bV\nw\u00e1\u00f0'\u0011\u001e\u0001*w\u0012\u009c'\u009f!\u00bap\u00f3";
            int var7 = "\u00f2\\\u00854\u0003\u00d0\u00cc\u00a7\u00e8\u0019\u008d\u00d5G\u008es`?{\u00e9\u00e8\bV\nw\u00e1\u00f0'\u0011\u001e\u0001*w\u0012\u009c'\u009f!\u00bap\u00f3".length();
            int var4 = 0;
            block6: while (true) {
                int var10001 = var4;
                byte[] var9 = var6.substring(var10001, var4 += 8).getBytes("ISO-8859-1");
                long[] var15 = var8;
                var10001 = var5++;
                long var18 = ((long)var9[0] & 0xFFL) << 56 | ((long)var9[1] & 0xFFL) << 48 | ((long)var9[2] & 0xFFL) << 40 | ((long)var9[3] & 0xFFL) << 32 | ((long)var9[4] & 0xFFL) << 24 | ((long)var9[5] & 0xFFL) << 16 | ((long)var9[6] & 0xFFL) << 8 | (long)var9[7] & 0xFFL;
                int var20 = -1;
                while (true) {
                    long var10 = var18;
                    byte[] var12 = var2.doFinal(new byte[]{(byte)(var10 >>> 56), (byte)(var10 >>> 48), (byte)(var10 >>> 40), (byte)(var10 >>> 32), (byte)(var10 >>> 24), (byte)(var10 >>> 16), (byte)(var10 >>> 8), (byte)var10});
                    long var22 = ((long)var12[0] & 0xFFL) << 56 | ((long)var12[1] & 0xFFL) << 48 | ((long)var12[2] & 0xFFL) << 40 | ((long)var12[3] & 0xFFL) << 32 | ((long)var12[4] & 0xFFL) << 24 | ((long)var12[5] & 0xFFL) << 16 | ((long)var12[6] & 0xFFL) << 8 | (long)var12[7] & 0xFFL;
                    switch (var20) {
                        case 0: {
                            var15[var10001] = var22;
                            if (var4 < var7) break;
                            b = var8;
                            d = new Integer[7];
                            c = new EnumFacing[]{EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST};
                            return;
}
                        default: {
                            var15[var10001] = var22;
                            if (var4 < var7) continue block6;
                            var6 = "I\u009e'\u00b1\u0088\u00ee\u00b9Os\u00c9\u00e8\u0019=\u00b4\u00e68";
                            var7 = "I\u009e'\u00b1\u0088\u00ee\u00b9Os\u00c9\u00e8\u0019=\u00b4\u00e68".length();
                            var4 = 0;
}
}
                    int var17 = var4;
                    var9 = var6.substring(var17, var4 += 8).getBytes("ISO-8859-1");
                    var15 = var8;
                    var10001 = var5++;
                    var18 = ((long)var9[0] & 0xFFL) << 56 | ((long)var9[1] & 0xFFL) << 48 | ((long)var9[2] & 0xFFL) << 40 | ((long)var9[3] & 0xFFL) << 32 | ((long)var9[4] & 0xFFL) << 24 | ((long)var9[5] & 0xFFL) << 16 | ((long)var9[6] & 0xFFL) << 8 | (long)var9[7] & 0xFFL;
                    var20 = 0;
}
                break;
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var13) {
            throw new RuntimeException(var13);
}
}
    static {
        KEY_OFFSETS = new byte[]{23, 0, 59, 41, 45, 56, 43, 44, 36, 11, 17, 26, 9, 4, 61, 5, 10, 50, 1, 14, 40, 8, 35, 29, 34, 16, 63, 49, 58, 3, 30, 15, 25, 7, 39, 18, 31, 47, 32, 52, 46, 28, 33, 51, 62, 24, 12, 53, 20, 19, 2, 54, 48, 22, 37, 21, 6, 27, 38, 13, 55, 60, 42, 57};
        silentRotation = new BooleanSetting("Silent-rotation", false);
        requireSneak = new BooleanSetting("Require-sneak", false);
        requireHoldingBlocks = new BooleanSetting("Require-holding-blocks", false);
        requireLookingDown = new BooleanSetting("Require-looking-down", false);
        notMovingForward = new BooleanSetting("Not-moving-forward", false);
        edgeOffset = new NumberSetting("Edge-offset", 0.0f, 0.0f, 0.3f, 0.01f);
        unsneakDelay = new NumberSetting("Unsneak-delay", 50.0f, 50.0f, 300.0f, 5.0f);
        sneakOnJumpTime = new NumberSetting("Sneak-on-jump-time", 0.0f, 0.0f, 500.0f, 5.0f);
}
}