/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.network.play.server.S19PacketEntityStatus
 *  net.minecraft.world.World
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BlockHitBinder;
import Abyss.event.events.PostUpdateEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.UpdateCameraAndRenderEvent;
import Abyss.internal.restore.AbyssNameMap;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.Modules;
import Abyss.module.impl.combat.AutoClicker;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.CombatUtil;
import Abyss.util.EntityUtil;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.Sneaky;
import Abyss.util.packet.PacketManager;
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
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.world.World;

public class BlockHit
extends Module
implements EventSubscriber {
    public static boolean N;
    private long h;
    public static NumberSetting predictBlockTicks;
    public static BooleanSetting enemies;
    public static HeaderSetting targetSettings;
    private boolean v;
    private static long[] bb;
    public static NumberSetting fov;
    public static NumberSetting spamBPS;
    public static BooleanSetting mobs;
    public static BooleanSetting friends;
    private int y;
    private static String[] pb;
    private static long[] ib;
    private static Map ab;
    private static Map nb;
    public static NumberSetting predictEarlyTicks;
    private static long D;
    public static BooleanSetting players;
    private static Integer[] gb;
    private boolean O;
    private long t;
    private static String[] L;
    public static BooleanSetting animals;
    public static ModeSetting lagAfterBlockMode;
    public static NumberSetting predictRandomEarlyTicks;
    public static BooleanSetting requireLeftClick;
    public static NumberSetting predictHurtResistTicks;
    private boolean x;
    public static ModeSetting mode;
    private boolean E;
    public static BooleanSetting onlyAutoClicker;
    public static BooleanSetting allowNoSlow;
    public static BooleanSetting visualBlocking;
    private long H;
    public static BooleanSetting teammates;
    public static BooleanSetting requireRightClick;
    public static BooleanSetting bosses;
    private boolean k;
    public static NumberSetting predictMaxPingCompTicks;
    public static NumberSetting range;
    private static Map hb;
    private static Object[] ob;
    public static NumberSetting lagAfterBlockTime;
    public static NumberSetting spamBlockTime;
    private static String[] J;
    public static BooleanSetting bots;
    private static final byte[] KEY_OFFSETS;
    private int Y;

    private void isUsingItem(long var1) {
        var1 = D ^ var1;
        long var3 = var1 ^ 0x3BBBB51AA54L;
        int var5 = (int)((var1 ^ 0x2FA24FC85444L) >>> 32);
        int var6 = (int)((var1 ^ 0x2FA24FC85444L) << 32 >>> 48);
        int var7 = (int)((var1 ^ 0x2FA24FC85444L) << 48 >>> 48);
        this.X(var3);
        this.H = 0L;
        this.t = 0L;
        this.v = false;
        N = false;
        if (this.O) {
            PacketManager.j();
            PacketManager.M(false);
            this.O = false;
}
        if (this.k) {
            this.J(var5, (char)var6, (short)var7);
}
        BlockHit.f.thePlayer.isUsingItem();
}
    private void T(long var1) {
        var1 = D ^ var1;
        int var3 = (int)((var1 ^ 0x468688015C17L) >>> 32);
        int var4 = (int)((var1 ^ 0x468688015C17L) << 32 >>> 48);
        int var5 = (int)((var1 ^ 0x468688015C17L) << 48 >>> 48);
        this.x = false;
        this.Y = 0;
        this.J(var3, (char)var4, (short)var5);
}
    private static Field b(Class var0, String var1, Class var2) {
        Field var3 = BlockHit.a(var0, var1, var2);
        if (var3 != null) {
            return var3;
}
        Class<?>[] var4 = var0.getInterfaces();
        if (var4 != null) {
            for (int var5 = 0; var5 < var4.length; ++var5) {
                var3 = BlockHit.b(var4[var5], var1, var2);
                if (var3 == null) continue;
                return var3;
}
}
        return null;
}
    private int Y(short var1, char var2, int var3) {
        int var6 = Math.round(predictHurtResistTicks.L());
        int var7 = Math.round(predictEarlyTicks.L());
        int var8 = Math.round(predictRandomEarlyTicks.L());
        int var9 = var8 > 0 ? this.d(0, var8) : 0;
        int var10 = var6 - var7 - var9;
        return Math.max(0, var10);
}
    private static CallSite a(MethodHandles.Lookup var0, String var1, MethodType var2) {
        MutableCallSite var3 = new MutableCallSite(var2);
        try {
            var3.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(MethodHandles.lookup().findStatic(BlockHit.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/Object;", BlockHit.class.getClassLoader())).asCollector(Object[].class, var2.parameterCount()), 0, var0, var3, var1, var2), var2));
            return var3;
}
        catch (Exception var5) {
            throw new RuntimeException("Abyss/module/impl/combat/BlockHit : " + var1 + " : " + var2.toString(), var5);
}
}
    private int a() {
        int var1 = CombatUtil.q();
        if (var1 <= 0) {
            return 0;
}
        int var2 = (int)Math.ceil((double)var1 / 50.0);
        int var3 = Math.round(predictMaxPingCompTicks.L());
        return MathUtil.k(var2, 0, var3);
}
    private boolean Y(short var1, int var2, char var3) {
        long var4 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ D;
        long var6 = var4 ^ 0x75EFB5BFEA46L;
        long var8 = var4 ^ 0x4B77AC6201B3L;
        return !EntityUtil.K(EntityUtil.F(range.L(), var6, fov.L()), players.c(), var8, mobs.c(), animals.c(), bosses.c(), friends.c(), enemies.c(), teammates.c(), bots.c()).isEmpty();
}
    public void onPreUpdate(long var1, PreUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (lagAfterBlockMode.R("PRE")) {
            this.q(8138133633306L);
}
}
    private static void c() {
        BlockHit.ob[0] = "";
        BlockHit.pb[0] = "Abyss.module.impl.combat.BlockHit";
        BlockHit.ob[1] = Long.TYPE;
        BlockHit.pb[1] = "java/lang/Long";
        BlockHit.ob[2] = Void.TYPE;
        BlockHit.pb[2] = "java/lang/Void";
        BlockHit.ob[3] = Integer.TYPE;
        BlockHit.pb[3] = "java/lang/Integer";
        BlockHit.ob[4] = Character.TYPE;
        BlockHit.pb[4] = "java/lang/Character";
        BlockHit.ob[5] = Short.TYPE;
        BlockHit.pb[5] = "java/lang/Short";
        BlockHit.ob[6] = "";
        BlockHit.pb[6] = "Abyss.util.EntityUtil";
        BlockHit.ob[7] = Double.TYPE;
        BlockHit.pb[7] = "java/lang/Double";
        BlockHit.ob[8] = "";
        BlockHit.pb[8] = "java.util.List";
        BlockHit.ob[9] = Boolean.TYPE;
        BlockHit.pb[9] = "java/lang/Boolean";
        BlockHit.ob[10] = "";
        BlockHit.pb[10] = "Abyss.util.ItemUtil";
        BlockHit.ob[11] = "";
        BlockHit.pb[11] = "Abyss.util.KeyBindUtil";
        BlockHit.ob[12] = "";
        BlockHit.pb[12] = "net.minecraft.client.settings.KeyBinding";
        BlockHit.ob[13] = "";
        BlockHit.pb[13] = "Abyss.setting.settings.BooleanSetting";
        BlockHit.ob[14] = "";
        BlockHit.pb[14] = "Abyss.setting.settings.NumberSetting";
        BlockHit.ob[15] = Float.TYPE;
        BlockHit.pb[15] = "java/lang/Float";
        BlockHit.ob[16] = "";
        BlockHit.pb[16] = "Abyss.setting.settings.ModeSetting";
        BlockHit.ob[17] = "";
        BlockHit.pb[17] = "java.lang.String";
        BlockHit.ob[18] = "";
        BlockHit.pb[18] = "Abyss.util.packet.PacketManager";
        BlockHit.ob[19] = "";
        BlockHit.pb[19] = "java.lang.Object";
        BlockHit.ob[20] = "";
        BlockHit.pb[20] = "Abyss.event.binder.BlockHitBinder";
        BlockHit.ob[21] = "";
        BlockHit.pb[21] = "Abyss.event.EventBus";
        BlockHit.ob[22] = "qj l2Cw)`>\\:\u0018?p38\u0014w0z*c~";
        BlockHit.ob[23] = "QC~cjN\u0007D|\u0018K?\u0005H`sw\u0000\u0000F\u007f\u007f\u000b\u0004^Twd4\u0001PK{\u00181ZZUehkD\u000f\u0012\u001c&dZ\u0006H~}{F\u0002(";
        BlockHit.ob[24] = "L\u000bE9o_I\u000b^<\u0005t&\u0003]m?H\u001e\u0002\u0013xy%";
        BlockHit.ob[25] = "e87\u0019R\u001c3?5bUm3?/\u0007B\u000e`cd\f3Tz1/XYRa#/b";
        BlockHit.ob[26] = "";
        BlockHit.pb[26] = "e1ba8i0xnj\bs\b1drmykb89f\bewvuvz58sk\bgcjsiy1dh\b1xjr2b7cxr\b";
        BlockHit.ob[27] = "0l\u0004rO812\u0014su4\u000f3\u0017|M8mh\b`IX";
        BlockHit.ob[28] = "ltnl\u0018\u0001<{quq2S?eo\f\u0015#e{:Kljizn\u0000\u000f951eqR<`8k\u0013\t#|<\u000b";
        BlockHit.ob[29] = "I?.@\u0015\u0012\u001f8,;6c\u001c1(F\r\u0013F/}\u0001tXHmtF\u001e\u0011F$.;O\u0003Z?0\u0004J\rE3L\u0002\u0004\u0001\\n&\u0004\u001f\u0013\\T";
        BlockHit.ob[30] = "..\nBY,+.\u0011G3\fD&\u0012\u0016\t;|'\\\u0003OVz#\bAS4!<\u0014E3";
        BlockHit.ob[31] = "\u000b6I`\u001el]1K\u001b.\u001d_=Wp\u0003\"Z3H|\u007f'\u00019Vb\u000f}\u001fl\u0011\u001bDs]eVq\r}\u0014?+%\u0010x\\=I~\u000fdX]";
        BlockHit.ob[32] = "";
        BlockHit.pb[32] = "45tvb4jx8i\bK\b67ya891d7i\b6gm0hjmxq4\b1drmykb89f\b6gm0hjmxq4\b6gm0hjmxq4\b6gm0hjmxq4\b6gm0hjmxq4\b6gm0hjmxq4\b6gm0hjmxq4\b6gm0hjmxq4\b67ya891d7i\b";
        BlockHit.ob[33] = "\bX[\u000foc^_Ytw\u0012^_C\u0011\u007fq\r\u0003\b\u001a\u000e+\u0017QCNd-\fCCt";
        BlockHit.ob[34] = "\u001d*\u001d\u0012dtK-\u001fiT\u0005I!\u0003\u0002y:L/\u001c\u000e\u0005>\u001cxG\u0014ow\u00121\u001di?`\u0016<\u0006\u0019e~C{\u007fS`a\u000f8\u000f\t~4HA";
        BlockHit.ob[35] = "8$\u0016\"nhn#\u0014YR\u0019n#\u000e<~z=\u007fE7\u000f '-\u000ece&<?\u000eY";
        BlockHit.ob[36] = ",O-\u001aQ\bzH/alyzH5\u0004A\u001a)\u0014~\u000f0@3F5[ZF(T5a";
        BlockHit.ob[37] = "\u0013>l\\\"YE9n'\u001d(E9tB2K\u0016e?IC\u0011\f7t\u001d)\u0017\u0017%t'";
        BlockHit.ob[38] = "hH9${Ei\u0006,b\u0016\u0010{J?zm}nU;{\u007f\u0007>\u0006i}\u0016CmRn~t\u0018rNj\u001e";
        BlockHit.ob[39] = "[`J1\u0006~Z._wk&XdE@\f*\\\u001f\u001fn\u000f;HoEpZ|1";
        BlockHit.ob[40] = "\b\u0017\naZ\u001c^\u0010\b\u001a\u007fm^\u0010\u0012\u007fJ\u000e\rLYt;T\u0017\u001e\u0012 QR\f\f\u0012\u001a";
        BlockHit.ob[41] = "B:[2y}\u001cw\u0017-\u0013\u000f~:\u0016=nxEj\u0016#)AGk])b\"\u00147\u0016\"\u0013|OvZu(,Oh\u001dL-~\u000fn\u0017}*-Af'";
        BlockHit.ob[42] = ")=Ymm\u0015\u007f:[\u0016Cd\u007f:As}\u0007,f\nx\f]64A,f[-&A\u0016";
        BlockHit.ob[43] = "43\u000b\u001c\u000eZb4\tg\u0017+b4\u0013\u0002\u001eH1hX\to\u0012+:\u0013]\u0005\u00140(\u0013g";
        BlockHit.ob[44] = "`p \u0002\rE0<5\u001ahl\fr0\u0001PIn)/\u001dT)5<=\u001eRC3'/\u001eh";
        BlockHit.ob[45] = "<\u000b}\u0011\u001f]j\f\u007fj.,j\fe\u000f\u000fO9P.\u0004~\u0015#\u0002eP\u0014\u00138\u0010ej";
        BlockHit.ob[46] = "\u0007M\u0017h-eQX\u0014qKrJP\rXr!\u000e\nX4\u001c}7\f\u0003k6eGV\u001d>q\u001c";
        BlockHit.ob[47] = "L\u0016(t\u0013_EDt\"l\b}Cy{T\u0003\u001f\u0018fgPc";
        BlockHit.ob[48] = "y0\n\u0019\rf/7\bb\u0005\u0017,>\f\u001f\u0015gv YXl";
}
    private static MethodHandle a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, long var4, long var6) {
        char var8 = var2.charAt(0);
        MethodHandle var9 = null;
        Field var10 = null;
        Method var11 = null;
        try {
            if (var8 != '\u00c2' && var8 != 'x' && var8 != 'b' && var8 != '\u00df') {
                var11 = BlockHit.d(var4, var6);
                Class<?> var17 = var11.getDeclaringClass();
                String var19 = var11.getName();
                MethodType var20 = MethodType.methodType(var11.getReturnType(), var11.getParameterTypes());
                var9 = var8 == 'F' ? var0.findVirtual(var17, var19, var20) : (var8 == '\u00c4' ? var0.findStatic(var17, var19, var20) : var0.findSpecial(var17, var19, var20, var17));
            } else {
                var10 = BlockHit.c(var4, var6);
                Class<?> var12 = var10.getDeclaringClass();
                String var18 = var10.getName();
                Class<?> var14 = var10.getType();
                var9 = var8 == '\u00c2' ? var0.findGetter(var12, var18, var14) : (var8 == 'x' ? var0.findSetter(var12, var18, var14) : (var8 == 'b' ? var0.findStaticGetter(var12, var18, var14) : var0.findStaticSetter(var12, var18, var14)));
}
            return MethodHandles.dropArguments(var9, var3.parameterCount() - 2, new Class[]{Long.TYPE, Long.TYPE});
}
        catch (Exception var15) {
            StringBuilder var13 = new StringBuilder();
            var13.append(var15.getClass().getName()).append(" : ").append(var10 != null ? var10.toString() : (var11 != null ? var11.toString() : " null ")).append(" : ").append(var15.toString());
            throw new RuntimeException(var13.toString());
}
}
    private int d(int var1, int var2) {
        return Math.round(MathUtil.h(var1, var2));
}
    private void X(long var1) {
        this.y = 0;
        this.Y = 0;
        this.x = false;
        this.E = false;
}
    public void onReceivePacket(ReceivePacketEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        S19PacketEntityStatus var7;
        if (mode.R("PREDICT") && BlockHit.f.theWorld != null && var1.d instanceof S19PacketEntityStatus && (var7 = (S19PacketEntityStatus)var1.d).getEntity((World)BlockHit.f.theWorld) instanceof EntityPlayerSP && var7.getOpCode() == 2) {
            this.x = false;
            this.Y = 0;
            this.y = this.Y((short)0, 'b', -1348816909);
            this.E = true;
}
}
    private void n(long var1) {
        var1 = D ^ var1;
        int var3 = (int)((var1 ^ 0x52CFE68EF228L) >>> 32);
        int var4 = (int)((var1 ^ 0x52CFE68EF228L) << 32 >>> 48);
        int var5 = (int)((var1 ^ 0x52CFE68EF228L) << 48 >>> 48);
        long var6 = var1 ^ 0x5FB40839BD2CL;
        if (this.v) {
            this.H += 1000L / (long)spamBPS.L();
            if (this.t <= 0L) {
                this.v = false;
                this.J(var3, (char)var4, (short)var5);
}
        } else if (this.H <= 0L) {
            this.t += (long)spamBlockTime.L();
            this.v = true;
            this.V(var6);
        } else {
            this.V(var6);
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        BlockHitBinder.s(var3, this);
}
    private static Method b(Class var0, String var1, Class var2, int var3, Class[] var4) {
        Method var5 = BlockHit.a(var0, var1, var2, var3, var4);
        if (var5 != null) {
            return var5;
}
        Class<?>[] var6 = var0.getInterfaces();
        if (var6 != null) {
            for (int var7 = 0; var7 < var6.length; ++var7) {
                var5 = BlockHit.b(var6[var7], var1, var2, var3, var4);
                if (var5 == null) continue;
                return var5;
}
}
        return null;
}
    public void onPostUpdate(long var1, PostUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (lagAfterBlockMode.R("POST")) {
            this.q(8138133633306L);
}
}
    private static Field a(Class var0, String var1, Class var2) {
        for (Field var6 : var0.getDeclaredFields()) {
            if (!var6.getName().equals(var1) || var6.getType() != var2) continue;
            return var6;
}
        return null;
}
    @Override
    public void Z(long var1) {
        long var3 = var1 ^ 0x4DFD349D0644L;
        this.isUsingItem(var3);
}
    private void V(long var1) {
        long var3 = var1 ^ 0x7494148CB0DBL;
        this.k = true;
        KeyBindUtil.A(var3, BlockHit.f.gameSettings.keyBindUseItem.getKeyCode(), true);
}
    private static Class b(long var0, long var2) {
        Class<?> var5 = null;
        int var4 = BlockHit.a(var0, var2);
        Object var6 = ob[var4];
        try {
            if (var6 instanceof String) {
                BlockHit.ob[var4] = var5 = Class.forName(AbyssNameMap.map(pb[var4]));
                return var5;
}
}
        catch (Exception var8) {
            throw new RuntimeException(var8.toString());
}
        return (Class)var6;
}
    private int f$r3() {
        return Math.max(1, Math.round(predictBlockTicks.L()) + this.blockHoldLatencyTicks());
}
    private int blockHoldLatencyTicks() {
        int var1 = CombatUtil.q();
        return var1 <= 0 ? 0 : MathUtil.k((int)Math.ceil((double)var1 / 50.0), 0, 2);
}
    private void L(long var1) {
        long var3 = var1 ^ 0x6487F837E947L;
        this.x = true;
        this.E = false;
        this.Y = this.f$r3();
        this.y = 0;
        this.V(var3);
}
    private static String b(byte[] var0) {
        int var1 = 0;
        int var2;
        char[] var3 = new char[var2 = var0.length];
        for (int var4 = 0; var4 < var2; ++var4) {
            int var5;
            if ((var5 = 255 & var0[var4]) < 192) {
                var3[var1++] = (char)var5;
            } else if (var5 < 224) {
                char var6 = (char)((char)(var5 & 31) << 6);
                byte var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 63));
                var3[var1++] = var6;
            } else if (var4 < var2 - 2) {
                char var12 = (char)((char)(var5 & 15) << 12);
                byte var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63) << 6);
                var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63));
                var3[var1++] = var12;
            }
        }
        return new String(var3, 0, var1);
    }
    private void J(int var1, char var2, short var3) {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ D;
        long var6 = var4 ^ 0x4C8A69E5A4ABL;
        this.k = false;
        KeyBindUtil.A(var6, BlockHit.f.gameSettings.keyBindUseItem.getKeyCode(), false);
}
    private void G(long var1) {
        var1 = D ^ var1;
        long var3 = var1 ^ 0x3AE36A1BAD99L;
        int var5 = (int)((var1 ^ 0x531F7C9B0BDAL) >>> 32);
        int var6 = (int)((var1 ^ 0x531F7C9B0BDAL) << 32 >>> 48);
        int var7 = (int)((var1 ^ 0x531F7C9B0BDAL) << 48 >>> 48);
        long var8 = var1 ^ 0x20FC67440CB9L;
        long var10 = var1 ^ 0x5E64922C44DEL;
        if (this.x) {
            this.V(var10);
            --this.Y;
            if (this.Y <= 0) {
                this.T(var8);
}
        } else {
            this.J(var5, (char)var6, (short)var7);
            if (this.E) {
                if (this.y > 0) {
                    --this.y;
}
                if (this.y <= 0) {
                    this.L(var3);
}
}
}
}
    private static Method d(long var0, long var2) {
        int var4;
        Class var23;
        Class var15;
        Class[] var14;
        int var13;
        String var10;
        Class var8;
        block10: {
            var4 = BlockHit.a(var0, var2);
            Object var5 = ob[var4];
            if (!(var5 instanceof String)) {
                return (Method)var5;
}
            String var6 = pb[var4];
            int var7 = var6.indexOf(8);
            var8 = BlockHit.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
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
                var15 = BlockHit.b(Long.parseLong(var6.substring(var12, var17), 36), 0L);
                if (var16 >= var13) continue;
                var14[var16] = var15;
}
            var23 = var8;
            do {
                Method var26;
                if ((var26 = BlockHit.a(var23, var10, var15, var13, var14)) != null) {
                    BlockHit.ob[var4] = var26;
                    return var26;
}
                if (var23.getName().equals("java.lang.Object")) break block10;
            } while ((var23 = var23.getSuperclass()) != null);
            var23 = BlockHit.b(1375026162649760L, 0L);
}
        var23 = var8;
        while (true) {
            Class<?>[] var27;
            if ((var27 = var23.getInterfaces()) != null) {
                for (int var18 = 0; var18 < var27.length; ++var18) {
                    Method var19 = BlockHit.b(var27[var18], var10, var15, var13, var14);
                    if (var19 == null) continue;
                    BlockHit.ob[var4] = var19;
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
            var23 = BlockHit.b(1375026162649760L, 0L);
}
}
    private static int a(long var0, long var2) {
        int var16;
        int var4 = (int)((var0 ^= var2 << 48 | var2) >>> 46);
        if (pb[var4] != null) {
            return var4;
}
        Object var5 = ob[var4];
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
        BlockHit.pb[var4] = new String(var13);
        return var4;
}
    private static Object a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, Object[] var4) throws Throwable {
        int var5 = var4.length - 2;
        long var6 = (Long)var4[var5];
        long var9 = (Long)var4[++var5];
        MethodHandle var8 = BlockHit.a(var0, var1, var2, var3, var6, var9);
        var1.setTarget(MethodHandles.explicitCastArguments(var8, var3));
        return var8.asSpreader(Object[].class, var4.length).invoke(var4);
}
    private static Field c(long var0, long var2) {
        int var4 = BlockHit.a(var0, var2);
        Object var5 = ob[var4];
        if (!(var5 instanceof String)) {
            return (Field)var5;
}
        String var6 = pb[var4];
        int var7 = var6.indexOf(8);
        Class var8 = BlockHit.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
        int var9 = var6.indexOf(8, ++var7);
        String var10 = var6.substring(var7, var9);
        Class var11 = BlockHit.b(Long.parseLong(var6.substring(++var9), 36), 0L);
        Class var12 = var8;
        while (true) {
            Field var13;
            if ((var13 = BlockHit.a(var12, var10, var11)) != null) {
                BlockHit.ob[var4] = var13;
                return var13;
}
            Class<?>[] var14 = var12.getInterfaces();
            if (var14 != null) {
                for (int var15 = 0; var15 < var14.length; ++var15) {
                    var13 = BlockHit.b(var14[var15], var10, var11);
                    if (var13 == null) continue;
                    BlockHit.ob[var4] = var13;
                    return var13;
}
}
            if (var12.getName().equals("java.lang.Object")) {
                StringBuffer var19 = new StringBuffer();
                var19.append("NoSuchFieldException in ").append(var8.getName()).append(' ').append(var11.getName()).append(' ').append(var10);
                throw new RuntimeException(var19.toString());
}
            if ((var12 = var12.getSuperclass()) != null) continue;
            var12 = BlockHit.b(1375026162649760L, 0L);
}
}
    private void U(long var1) {
        if (this.H > 0L) {
            this.H -= 50L;
}
        if (this.t > 0L) {
            this.t -= 50L;
}
        if (this.h > 0L) {
            this.h -= 50L;
}
}
    public BlockHit(long var1) {
        super(D ^ var1 ^ 0x16CD1853452AL);
        this.declare("BlockHit", Category.Combat, "Block the sword when needed to decrease damage received", new Setting[0]);
        var1 = D ^ var1;
        this.H = 0L;
        this.t = 0L;
        this.h = 0L;
        this.y = 0;
        this.Y = 0;
        this.x = false;
        this.E = false;
        this.k = false;
        this.v = false;
        this.O = false;
}
    private boolean isGetKeyCode(short var1, short var2, int var3) {
        long var4 = ((long)var1 << 48 | (long)var2 << 48 >>> 16 | (long)var3 << 32 >>> 32) ^ D;
        int var6 = (int)((var4 ^ 0x1C06B9646820L) >>> 48);
        int var7 = (int)((var4 ^ 0x1C06B9646820L) << 16 >>> 32);
        int var8 = (int)((var4 ^ 0x1C06B9646820L) << 48 >>> 48);
        long var9 = var4 ^ 0x2BF8514B660FL;
        if (!ItemUtil.d()) {
            return false;
}
        if (!this.Y((short)var6, var7, (char)var8)) {
            return false;
}
        if (requireLeftClick.c() && !KeyBindUtil.V(BlockHit.f.gameSettings.keyBindAttack.getKeyCode(), var9)) {
            return false;
}
        return requireRightClick.c() && !KeyBindUtil.V(BlockHit.f.gameSettings.keyBindUseItem.getKeyCode(), var9) ? false : !onlyAutoClicker.c() || AutoClicker.I;
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    public static boolean noSlowLive() {
        BlockHit var0 = Modules.J(BlockHit.class);
        return allowNoSlow.c() && var0 != null && var0.o() && var0.k;
}
    public void onUpdateCameraAndRender(long var1, UpdateCameraAndRenderEvent var3) {
        if (this.k && visualBlocking.c()) {
            var3.W(17984, 996510524L);
}
}
    private void q(long var1) {
        if (this.O && this.h <= 0L) {
            PacketManager.j();
            PacketManager.M(false);
            N = false;
            this.O = false;
}
}
    public void onPreMouseInput(PreMouseInputEvent var1, long var2, short var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = (var2 << 16 | (long)var4 << 48 >>> 48) ^ D;
        int var7 = (int)((var5 ^ 0x5CB62B81D9CEL) >>> 48);
        int var8 = (int)((var5 ^ 0x5CB62B81D9CEL) << 16 >>> 48);
        int var9 = (int)((var5 ^ 0x5CB62B81D9CEL) << 32 >>> 32);
        long var10 = var5 ^ 0x2D7374DA5B83L;
        long var12 = var5 ^ 0x2CA3EECFA271L;
        long var16 = var5 ^ 0x51CE4789041DL;
        boolean var18 = this.k;
        this.U(0L);
        if (!this.isGetKeyCode((short)var7, (short)var8, var9)) {
            this.isUsingItem(var16);
        } else {
            this.k = true;
            switch (mode.Y()) {
                case "PREDICT": {
                    this.G(var10);
                    break;
}
                case "SPAM": {
                    this.n(var12);
}
}
            switch (lagAfterBlockMode.Y()) {
                case "PRE": 
                case "POST": {
                    if (!var18 || this.k || this.O) break;
                    this.h = (long)lagAfterBlockTime.L();
                    PacketManager.M(true);
                    N = true;
                    this.O = true;
}
}
}
}
    private static String b(int var0, long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var5 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x59E8;
        if (L[var5] == null) {
            Object[] var4;
            try {
                Long var3 = Thread.currentThread().getId();
                var4 = (Object[])ab.get(var3);
                if (var4 == null) {
                    var4 = new Object[]{Cipher.getInstance("DES/CBC/PKCS5Padding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    ab.put(var3, var4);
}
}
            catch (Exception var10) {
                throw new RuntimeException("Abyss/module/impl/combat/BlockHit", var10);
}
            byte[] var6 = new byte[8];
            var6[0] = (byte)(var1 >>> 56);
            for (int var7 = 1; var7 < 8; ++var7) {
                var6[var7] = (byte)(var1 << var7 * 8 >>> 56);
}
            DESKeySpec var11 = new DESKeySpec(var6);
            SecretKey var8 = ((SecretKeyFactory)var4[1]).generateSecret(var11);
            ((Cipher)var4[0]).init(2, (Key)var8, (IvParameterSpec)var4[2]);
            byte[] var9 = J[var5].getBytes("ISO-8859-1");
            BlockHit.L[var5] = BlockHit.b(((Cipher)var4[0]).doFinal(var9));
}
        return L[var5];
}
    private static int d(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x38BB;
        if (gb[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = bb[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])hb.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    hb.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/module/impl/combat/BlockHit", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            BlockHit.gb[var3] = var15;
}
        return gb[var3];
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
    private static void zkm$unresolved$0$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_ia_n_OR_Abyss_ia_U_OR_Abyss_ia_G_OR_Abyss_ia_X_y_slots_25_35_42_45(Object var0, long var1, long var5) {
        try {
            MethodType var7 = MethodType.fromMethodDescriptorString("(Ljava/lang/Object;JJJ)V", BlockHit.class.getClassLoader());
            MethodHandles.explicitCastArguments(BlockHit.a(MethodHandles.lookup(), null, "\u00f6", var7, 7743209299158848852L, var5), var7).invoke(var0, var1, 7743209299158848852L, var5);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private static void zkm$unresolved$3$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_ia_p_OR_Abyss_ia_X_y_slots_43_45(Object var0, long var1, long var5) {
        try {
            MethodType var7 = MethodType.fromMethodDescriptorString("(Ljava/lang/Object;JJJ)V", BlockHit.class.getClassLoader());
            MethodHandles.explicitCastArguments(BlockHit.a(MethodHandles.lookup(), null, "\u00f6", var7, 3910435423868124319L, var5), var7).invoke(var0, var1, 3910435423868124319L, var5);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private static void zkm$clinit() {
        try {
            ob = new Object[49]; pb = new String[49]; c(); ab = new HashMap(13);
            byte[] var10003 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
            for (int var23 = 1; var23 < 8; ++var23) { var10003[var23] = (byte)(12610113794430L << var23 * 8 >>> 56); }
            Cipher var22 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var22.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var29 = new String[7];
            int var27 = 0;
            String var26 = "<8iC%\u001bq\u00d4X\u00abx\u0097\u00a9\u00e7\u0087\u00c9\u0010\u00aa\u00a9Abi\u009c\u00c7aQ\u00af0Z\r\u0006\u0095\u0093\u0010^\u00a7\u008f\u0089\u0018tNG\u009c7\u00b5\u0094\u000e\"b\u0014\u0010\u00f5\u00fc\u00ad#\u0012\u008e\u000f@\u0001?\u00b1\u00bf\u00e2\u00d4H\u00f4\u0010\u00c8\u00d0\u0091\u00bdLt1?\u008bD\u00eb\u00cb\u00a0z\b?";
            int var28 = "<8iC%\u001bq\u00d4X\u00abx\u0097\u00a9\u00e7\u0087\u00c9\u0010\u00aa\u00a9Abi\u009c\u00c7aQ\u00af0Z\r\u0006\u0095\u0093\u0010^\u00a7\u008f\u0089\u0018tNG\u009c7\u00b5\u0094\u000e\"b\u0014\u0010\u00f5\u00fc\u00ad#\u0012\u008e\u000f@\u0001?\u00b1\u00bf\u00e2\u00d4H\u00f4\u0010\u00c8\u00d0\u0091\u00bdLt1?\u008bD\u00eb\u00cb\u00a0z\b?".length();
            int var25 = 16;
            int var36 = -1;
            block9: while (true) {
                String var37 = var26.substring(++var36, var36 + var25);
                int var10001 = -1;
                while (true) {
                    byte[] var30 = var22.doFinal(var37.getBytes("ISO-8859-1"));
                    String var51 = BlockHit.b(var30).intern();
                    switch (var10001) {
                        case 0: {
                            var29[var27++] = var51;
                            if ((var36 += var25) >= var28) {
                                J = var29;
                                L = new String[7];
                                hb = new HashMap(13);
                                var10003 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
                                for (int var12 = 1; var12 < 8; ++var12) {
                                    var10003[var12] = (byte)(12610113794430L << var12 * 8 >>> 56);
}
                                Cipher var11 = Cipher.getInstance("DES/CBC/NoPadding");
                                var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var17 = new long[4];
                                int var14 = 0;
                                String var15 = "\tN9\u00c1\u00c0\f\u0016D\u00a9\u0012\u00e6\u00cbQ\u00fe\u0081\u00b4";
                                int var16 = "\tN9\u00c1\u00c0\f\u0016D\u00a9\u0012\u00e6\u00cbQ\u00fe\u0081\u00b4".length();
                                int var13 = 0;
                                block12: while (true) {
                                    var10001 = var13;
                                    byte[] var18 = var15.substring(var10001, var13 += 8).getBytes("ISO-8859-1");
                                    long[] var40 = var17;
                                    var10001 = var14++;
                                    long var55 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                    int var59 = -1;
                                    while (true) {
                                        long var19 = var55;
                                        byte[] var21 = var11.doFinal(new byte[]{(byte)(var19 >>> 56), (byte)(var19 >>> 48), (byte)(var19 >>> 40), (byte)(var19 >>> 32), (byte)(var19 >>> 24), (byte)(var19 >>> 16), (byte)(var19 >>> 8), (byte)var19});
                                        long var63 = ((long)var21[0] & 0xFFL) << 56 | ((long)var21[1] & 0xFFL) << 48 | ((long)var21[2] & 0xFFL) << 40 | ((long)var21[3] & 0xFFL) << 32 | ((long)var21[4] & 0xFFL) << 24 | ((long)var21[5] & 0xFFL) << 16 | ((long)var21[6] & 0xFFL) << 8 | (long)var21[7] & 0xFFL;
                                        switch (var59) {
                                            case 0: {
                                                var40[var10001] = var63;
                                                if (var13 < var16) break;
                                                bb = var17;
                                                gb = new Integer[4];
                                                nb = new HashMap(13);
                                                var10003 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
                                                for (int var1 = 1; var1 < 8; ++var1) {
                                                    var10003[var1] = (byte)(12610113794430L << var1 * 8 >>> 56);
}
                                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                                long[] var6 = new long[3];
                                                int var3 = 0;
                                                String var4 = "\u00fa\u00ee\u00adj\u001f\u00d7\u0011\u00ab \u0085\u00ecW\u009c\u0086k\u00d5S:n\u00a0m\u00dfL}";
                                                int var5 = "\u00fa\u00ee\u00adj\u001f\u00d7\u0011\u00ab \u0085\u00ecW\u009c\u0086k\u00d5S:n\u00a0m\u00dfL}".length();
                                                int var2 = 0;
                                                do {
                                                    int var48 = var2;
                                                    byte[] var7 = var4.substring(var48, var2 += 8).getBytes("ISO-8859-1");
                                                    var48 = var3++;
                                                    long var8 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                                    byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                                    var6[var48] = var63 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                                } while (var2 < var5);
                                                ib = var6;
                                                return;
}
                                            default: {
                                                var40[var10001] = var63;
                                                if (var13 < var16) continue block12;
                                                var15 = "b\u00e5P\u00cf\u001fJ\u001e\u00f6\u0003\u00fa\u0088\u00cb\u0018zd\u0004";
                                                var16 = "b\u00e5P\u00cf\u001fJ\u001e\u00f6\u0003\u00fa\u0088\u00cb\u0018zd\u0004".length();
                                                var13 = 0;
}
}
                                        int var47 = var13;
                                        var18 = var15.substring(var47, var13 += 8).getBytes("ISO-8859-1");
                                        var40 = var17;
                                        var10001 = var14++;
                                        var55 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                        var59 = 0;
}
}
}
                            var25 = var26.charAt(var36);
                            break;
}
                        default: {
                            var29[var27++] = var51;
                            if ((var36 += var25) < var28) {
                                var25 = var26.charAt(var36);
                                continue block9;
}
                            var26 = ":<\u00a36EW\u0092\u00a6<\u00d8@\u0089\u0096\u0013\u00e1I\u0010\u00ce\u009d\u00b3*\u008e\u0080\u00e2\u007f*G<\u00d5\u0000Os\u009f";
                            var28 = ":<\u00a36EW\u0092\u00a6<\u00d8@\u0089\u0096\u0013\u00e1I\u0010\u00ce\u009d\u00b3*\u008e\u0080\u00e2\u007f*G<\u00d5\u0000Os\u009f".length();
                            var25 = 16;
                            var36 = -1;
}
}
                    var37 = var26.substring(++var36, var36 + var25);
                    var10001 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var33) {
            throw new RuntimeException(var33);
}
}
    static {
        KEY_OFFSETS = new byte[]{49, 50, 33, 43, 17, 44, 2, 27, 39, 0, 40, 8, 21, 10, 31, 15, 5, 16, 45, 59, 4, 1, 60, 3, 61, 38, 41, 63, 52, 57, 6, 42, 12, 37, 23, 36, 7, 9, 47, 25, 51, 29, 58, 62, 13, 26, 28, 48, 34, 18, 24, 46, 22, 20, 56, 19, 35, 30, 11, 53, 14, 32, 55, 54};
        D = 58710388792180L;
        zkm$clinit();
        N = false;
        allowNoSlow = new BooleanSetting("Allow-NoSlow", true);
        visualBlocking = new BooleanSetting("Visual-blocking", true);
        requireLeftClick = new BooleanSetting("Require-left-click", true);
        requireRightClick = new BooleanSetting("Require-right-click", false);
        onlyAutoClicker = new BooleanSetting("Only-AutoClicker", true);
        players = new BooleanSetting("Players", true);
        mobs = new BooleanSetting("Mobs", false);
        animals = new BooleanSetting("Animals", false);
        bosses = new BooleanSetting("Bosses", false);
        friends = new BooleanSetting("Friends", false);
        enemies = new BooleanSetting("Enemies", true);
        teammates = new BooleanSetting("Teammates", false);
        bots = new BooleanSetting("Bots", false);
        range = new NumberSetting("Range", 6.0f, 0.0f, 10.0f, 0.1f);
        fov = new NumberSetting("FOV", 180.0f, 0.0f, 360.0f, 1.0f);
        predictHurtResistTicks = new NumberSetting("Predict-hurt-resist-ticks", 10.0f, 1.0f, 20.0f, 1.0f);
        predictEarlyTicks = new NumberSetting("Predict-early-ticks", 3.0f, 0.0f, 8.0f, 1.0f);
        predictBlockTicks = new NumberSetting("Predict-block-ticks", 5.0f, 1.0f, 12.0f, 1.0f);
        predictRandomEarlyTicks = new NumberSetting("Predict-random-early-ticks", 1.0f, 0.0f, 4.0f, 1.0f);
        predictMaxPingCompTicks = new NumberSetting("Predict-max-ping-comp-ticks", 4.0f, 0.0f, 8.0f, 1.0f);
        spamBPS = new NumberSetting("Spam-BPS", 10.0f, 0.0f, 20.0f, 0.1f);
        spamBlockTime = new NumberSetting("Spam-block-time", 100.0f, 0.0f, 500.0f, 1.0f);
        lagAfterBlockTime = new NumberSetting("Lag-after-block-time", 100.0f, 0.0f, 1000.0f, 1.0f);
        mode = new ModeSetting("Mode", "PREDICT", "SPAM");
        lagAfterBlockMode = new ModeSetting("Lag-after-block-mode", "NONE", "POST", "PRE");
        targetSettings = new HeaderSetting("Target settings");
}
}