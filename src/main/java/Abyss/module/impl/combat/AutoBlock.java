/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 *  net.minecraft.network.play.client.C09PacketHeldItemChange
 *  net.minecraft.network.play.server.S19PacketEntityStatus
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AutoBlockBinder;
import Abyss.event.events.ModuleTagEvent;
import Abyss.event.events.PostUpdateEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.RedirectIsUsingItemEvent;
import Abyss.event.events.TickEvent;
import Abyss.event.events.UpdateCameraAndRenderEvent;
import Abyss.internal.restore.AbyssNameMap;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.Modules;
import Abyss.module.impl.combat.KeepSprint;
import Abyss.module.impl.combat.KillAura;
import Abyss.module.impl.movement.NoSlow;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.CombatUtil;
import Abyss.util.EntityUtil;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.Pair;
import Abyss.util.RaytraceUtil;
import Abyss.util.ScoreboardReader;
import Abyss.util.Sneaky;
import Abyss.util.packet.OutgoingPacketState;
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
import java.util.Comparator;
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
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class AutoBlock
extends Module
implements EventSubscriber {
    public static BooleanSetting mobs;
    private static boolean m;
    public static NumberSetting targetRange;
    private boolean M;
    public static BooleanSetting smartUnblock;
    public static BooleanSetting golems;
    public static BooleanSetting players;
    private static boolean D;
    private static long mb;
    private EntityLivingBase F;
    public static ModeSetting apsMode;
    private static String[] ob;
    public static ModeSetting mode;
    private static String[] S;
    public static BooleanSetting manualLeftClick;
    private long Y;
    private static boolean B;
    private static Map ib;
    private static Integer[] hb;
    public static HeaderSetting targetSettings;
    private static Map bb;
    public static BooleanSetting allowNoSlow;
    public static BooleanSetting onlyUnblockWithoutNoSlow;
    public static BooleanSetting disableNoSlowInRange;
    public static NumberSetting noSlowDisableRange;
    public static PercentageSetting smartUnblockChance;
    private static int I;
    public static BooleanSetting friends;
    public static boolean C;
    public static BooleanSetting requireKillAura;
    public static BooleanSetting silverfishes;
    public static NumberSetting smartUnblockTicks;
    private int p;
    private static boolean K;
    private static boolean G;
    public static BooleanSetting bosses;
    private static String[] ab;
    public static BooleanSetting requireRightClick;
    public static BooleanSetting visualBlocking;
    public static BooleanSetting enemies;
    private static long o;
    public static BooleanSetting animals;
    private static long[] gb;
    public static int k;
    public static BooleanSetting teammates;
    public static NumberSetting fov;
    public static BooleanSetting bots;
    private boolean J;
    private static final byte[] KEY_OFFSETS;
    private static Object[] nb;

    public void onRedirectIsUsingItem(RedirectIsUsingItemEvent var1, long var2) {
        if (m) {
            switch (mode.Y()) {
                case "LAG": 
                case "LAG_PRE": 
                case "LAG_LEGIT": 
                case "LAG_LEGIT_PRE": 
                case "LAG_NEW": 
                case "LAG_NEW_PRE": {
                    if (AutoBlock.c()) break;
                    var1.I(21307, 3074332907L);
}
}
}
}
    private void o$r2() {
        PacketManager.j();
        this.Y = System.currentTimeMillis();
}
    private static Field b(Class var0, String var1, Class var2) {
        Field var3 = AutoBlock.a(var0, var1, var2);
        if (var3 != null) {
            return var3;
}
        Class<?>[] var4 = var0.getInterfaces();
        if (var4 != null) {
            for (int var5 = 0; var5 < var4.length; ++var5) {
                var3 = AutoBlock.b(var4[var5], var1, var2);
                if (var3 == null) continue;
                return var3;
}
}
        return null;
}
    private static boolean s(int var0, int var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return AutoBlock.noSlowLive();
}
    private static boolean noSlowLive() {
        return allowNoSlow.c() && Modules.J(NoSlow.class).o() && NoSlow.swordNoSlowLive() && !AutoBlock.noSlowInRange();
}
    private static boolean noSlowInRange() {
        if (!disableNoSlowInRange.c()) {
            return false;
}
        AutoBlock var0 = Modules.J(AutoBlock.class);
        return var0 != null && var0.F != null && AutoBlock.f.field_71439_g != null && AutoBlock.f.field_71439_g.func_70032_d((Entity)var0.F) <= noSlowDisableRange.L();
}
    public static boolean f$r2() {
        return D;
}
    private Pair n(boolean var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var1) {
            I = 0;
}
        this.M = I > 0;
        this.F = null;
        m = false;
        G = false;
        return this.B(22228944377438L);
}
    private void f(long var1) {
        K = false;
        D = false;
}
    private static boolean T(long var0) {
        switch (mode.Y()) {
            case "LAG": 
            case "LAG_PRE": 
            case "LAG_LEGIT": 
            case "LAG_LEGIT_PRE": 
            case "LAG_NEW": 
            case "LAG_NEW_PRE": {
                return ScoreboardReader.v(0L);
}
}
        return true;
}
    private Pair B(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        B = false;
        Pair<Boolean, Boolean> var16 = new Pair<Boolean, Boolean>(false, true);
        Pair<Boolean, Boolean> var17 = new Pair<Boolean, Boolean>(false, false);
        Pair<Boolean, Boolean> var18 = new Pair<Boolean, Boolean>(true, true);
        Pair<Boolean, Boolean> var19 = new Pair<Boolean, Boolean>(true, false);
        k = 0;
        if (this.J) {
            this.o$r2();
            this.n();
            this.J = false;
}
        if (!ItemUtil.d()) {
            if (OutgoingPacketState.O) {
                C = false;
}
            this.f(0L);
            return var16;
}
        if (C) {
            this.f(0L);
            if (this.y(0L)) {
                OutgoingPacketState.P = true;
                OutgoingPacketState.J(0L);
                return var18;
}
            return var17;
}
        Pair<Boolean, Boolean> var20 = var16;
        if (AutoBlock.f$r2() && AutoBlock.c()) {
            if (AutoBlock.s(26815, -704018324)) {
                if (!this.K(0L, true)) {
                    return var17;
}
                this.e(0L, false);
                AutoBlock.f.field_71439_g.func_71034_by();
                var20 = var19;
            } else {
                if (!this.K(0L, true)) {
                    return var17;
}
                AutoBlock.f.field_71439_g.func_71034_by();
                var20 = var18;
}
}
        this.f(0L);
        return var20;
}
    private static Field c(long var0, long var2) {
        int var4 = AutoBlock.a(var0, var2);
        Object var5 = nb[var4];
        if (!(var5 instanceof String)) {
            return (Field)var5;
}
        String var6 = ob[var4];
        int var7 = var6.indexOf(8);
        Class var8 = AutoBlock.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
        int var9 = var6.indexOf(8, ++var7);
        String var10 = var6.substring(var7, var9);
        Class var11 = AutoBlock.b(Long.parseLong(var6.substring(++var9), 36), 0L);
        Class var12 = var8;
        while (true) {
            Field var13;
            if ((var13 = AutoBlock.a(var12, var10, var11)) != null) {
                AutoBlock.nb[var4] = var13;
                return var13;
}
            Class<?>[] var14 = var12.getInterfaces();
            if (var14 != null) {
                for (int var15 = 0; var15 < var14.length; ++var15) {
                    var13 = AutoBlock.b(var14[var15], var10, var11);
                    if (var13 == null) continue;
                    AutoBlock.nb[var4] = var13;
                    return var13;
}
}
            if (var12.getName().equals("java.lang.Object")) {
                StringBuffer var19 = new StringBuffer();
                var19.append("NoSuchFieldException in ").append(var8.getName()).append(' ').append(var11.getName()).append(' ').append(var10);
                throw new RuntimeException(var19.toString());
}
            if ((var12 = var12.getSuperclass()) != null) continue;
            var12 = AutoBlock.b(1523196619010493L, 0L);
}
}
    public boolean Q(boolean var1, long var2, boolean var4, EntityLivingBase var5) {
        if (AutoBlock.zkm$unresolved$1$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_iT_c_OR_Abyss_yO_Y_y_slots_41_67(var2 = o ^ var2)) {
            return true;
}
        ItemStack var6 = AutoBlock.f.field_71439_g.func_70694_bm();
        if (ItemUtil.d() && (!var1 || OutgoingPacketState.Y())) {
            MovingObjectPosition var7;
            if (var4 && var5 != null && (var7 = RaytraceUtil.k(RaytraceUtil.S((Entity)var5), 8.0)) != null) {
                PacketManager.b(new C02PacketUseEntity((Entity)var5, new Vec3(var7.field_72307_f.field_72450_a - var5.field_70165_t, var7.field_72307_f.field_72448_b - var5.field_70163_u, var7.field_72307_f.field_72449_c - var5.field_70161_v)));
                PacketManager.b(new C02PacketUseEntity((Entity)var5, C02PacketUseEntity.Action.INTERACT));
}
            PacketManager.b(new C08PacketPlayerBlockPlacement(var6));
            AutoBlock.f.field_71439_g.func_71008_a(var6, var6.func_77988_m());
            this.k(true);
            return true;
}
        return false;
}
    @Override
    public void L(PreMouseInputEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.n(true, 0L);
}
    private static Class b(long var0, long var2) {
        Class<?> var5 = null;
        int var4 = AutoBlock.a(var0, var2);
        Object var6 = nb[var4];
        try {
            if (var6 instanceof String) {
                AutoBlock.nb[var4] = var5 = Class.forName(AbyssNameMap.map(ob[var4]));
                return var5;
}
}
        catch (Exception var8) {
            throw new RuntimeException(var8.toString());
}
        return (Class)var6;
}
    public static boolean c() {
        return AutoBlock.f$r2() ? K : AutoBlock.f.field_71439_g.func_71039_bw();
}
    private static Object a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, Object[] var4) throws Throwable {
        int var5 = var4.length - 2;
        long var6 = (Long)var4[var5];
        long var9 = (Long)var4[++var5];
        MethodHandle var8 = AutoBlock.a(var0, var1, var2, var3, var6, var9);
        var1.setTarget(MethodHandles.explicitCastArguments(var8, var3));
        return var8.asSpreader(Object[].class, var4.length).invoke(var4);
}
    private boolean M(int var1, char var2, short var3, EntityLivingBase var4, boolean var5) {
        long var6 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ o;
        int var8 = (int)((var6 ^ 0x12EBD981DE85L) >>> 32);
        long var9 = (var6 ^ 0x12EBD981DE85L) << 32 >>> 32;
        int var11 = (int)((var6 ^ 0x47A33DC8E34FL) >>> 48);
        long var12 = (var6 ^ 0x47A33DC8E34FL) << 16 >>> 16;
        if (var5 && !AutoBlock.zkm$unresolved$2$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_yO_Y_OR_Abyss_yO_f_y_slots_67_99(var6)) {
            return false;
}
        if (OutgoingPacketState.T) {
            return true;
}
        this.T((char)var11, var12, false);
        return CombatUtil.I((Entity)var4, var8, var9);
}
    private void k(boolean var1) {
        K = var1;
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    private static void a() {
        AutoBlock.nb[0] = "";
        AutoBlock.ob[0] = "Abyss.module.impl.combat.AutoBlock";
        AutoBlock.nb[1] = Boolean.TYPE;
        AutoBlock.ob[1] = "java/lang/Boolean";
        AutoBlock.nb[2] = "";
        AutoBlock.ob[2] = "Abyss.util.packet.PacketManager";
        AutoBlock.nb[3] = "";
        AutoBlock.ob[3] = "net.minecraft.network.Packet";
        AutoBlock.nb[4] = Void.TYPE;
        AutoBlock.ob[4] = "java/lang/Void";
        AutoBlock.nb[5] = "";
        AutoBlock.ob[5] = "Abyss.util.ItemUtil";
        AutoBlock.nb[6] = "";
        AutoBlock.ob[6] = "Abyss.util.EntityUtil";
        AutoBlock.nb[7] = Double.TYPE;
        AutoBlock.ob[7] = "java/lang/Double";
        AutoBlock.nb[8] = Long.TYPE;
        AutoBlock.ob[8] = "java/lang/Long";
        AutoBlock.nb[9] = "";
        AutoBlock.ob[9] = "java.util.List";
        AutoBlock.nb[10] = Character.TYPE;
        AutoBlock.ob[10] = "java/lang/Character";
        AutoBlock.nb[11] = "";
        AutoBlock.ob[11] = "Abyss.util.CombatUtil";
        AutoBlock.nb[12] = "";
        AutoBlock.ob[12] = "net.minecraft.entity.Entity";
        AutoBlock.nb[13] = Integer.TYPE;
        AutoBlock.ob[13] = "java/lang/Integer";
        AutoBlock.nb[14] = "";
        AutoBlock.ob[14] = "Abyss.util.packet.OutgoingPacketState";
        AutoBlock.nb[15] = "";
        AutoBlock.ob[15] = "Abyss.util.RaytraceUtil";
        AutoBlock.nb[16] = "";
        AutoBlock.ob[16] = "net.minecraft.util.AxisAlignedBB";
        AutoBlock.nb[17] = "";
        AutoBlock.ob[17] = "net.minecraft.util.MovingObjectPosition";
        AutoBlock.nb[18] = "";
        AutoBlock.ob[18] = "net.minecraft.client.entity.EntityPlayerSP";
        AutoBlock.nb[19] = "";
        AutoBlock.ob[19] = "net.minecraft.item.ItemStack";
        AutoBlock.nb[20] = "";
        AutoBlock.ob[20] = "java.lang.Boolean";
        AutoBlock.nb[21] = "";
        AutoBlock.ob[21] = "java.lang.Object";
        AutoBlock.nb[22] = "3\u0011id<5-\u0019s+t57\u0013kl}.w m`v):\u0011k`";
        AutoBlock.nb[23] = "";
        AutoBlock.ob[23] = "Abyss.setting.settings.BooleanSetting";
        AutoBlock.nb[24] = "\\}`%XnBuzj5t[lw6\u0017oYn";
        AutoBlock.nb[25] = "%gK6D\u000e;oQy\f\u000e!eI>\u0005\u0015aRR\u0013\u0005\u000e-jX\u0011\u001f\u0015,rT8\u0004";
        AutoBlock.nb[26] = "";
        AutoBlock.ob[26] = "Abyss.util.Pair";
        AutoBlock.nb[27] = "";
        AutoBlock.ob[27] = "Abyss.setting.settings.ModeSetting";
        AutoBlock.nb[28] = "";
        AutoBlock.ob[28] = "java.lang.String";
        AutoBlock.nb[29] = "";
        AutoBlock.ob[29] = "Abyss.util.ScoreboardReader";
        AutoBlock.nb[30] = Short.TYPE;
        AutoBlock.ob[30] = "java/lang/Short";
        AutoBlock.nb[31] = "*W$+3/!X5d^+!D\u0001/l6%X1/";
        AutoBlock.nb[32] = "";
        AutoBlock.ob[32] = "Abyss.event.binder.AutoBlockBinder";
        AutoBlock.nb[33] = "";
        AutoBlock.ob[33] = "Abyss.event.EventBus";
        AutoBlock.nb[34] = Byte.TYPE;
        AutoBlock.ob[34] = "java/lang/Byte";
        AutoBlock.nb[35] = "";
        AutoBlock.ob[35] = "Abyss.setting.settings.NumberSetting";
        AutoBlock.nb[36] = Float.TYPE;
        AutoBlock.ob[36] = "java/lang/Float";
        AutoBlock.nb[37] = "";
        AutoBlock.ob[37] = "Abyss.event.events.PreMouseInputEvent";
        AutoBlock.nb[38] = "";
        AutoBlock.ob[38] = "java.lang.System";
        AutoBlock.nb[39] = "";
        AutoBlock.ob[39] = "net.minecraft.entity.EntityLivingBase";
        AutoBlock.nb[40] = "p4^d\u0006++-^g:\u001f\u00175\u001c2\u00065v&\u0018lF[";
        AutoBlock.nb[41] = "3>9!%#1?pOpF+&zt|\"en*O";
        AutoBlock.nb[42] = "tn~\u001f\u007fWy+qDFUbv\u007fsy\u0003!!(s,^\u001f|uH|Gfzu\u001b!;";
        AutoBlock.nb[43] = "\u007f.ZqrV}/\u0013\u001f*3-}Eu6Pd:Q#L";
        AutoBlock.nb[44] = "";
        AutoBlock.ob[44] = "4coainygpp\bx\b6haxeci0ip\byz23ol72b\byz23ol72b\byz23ol72b\byz23ol72b\b5jx5xk0c48\byz23ol72b\byz23ol72b\byz23ol72b\byz23ol72b\byz23ol72b\byz23ol72b\b6haxeci0ip\b";
        AutoBlock.nb[45] = "R2h\u001d*\u001e_wgF\u0013\u001cD*iq,K\u0001w2qrr\u0002udLi\u0011K2p\u001a\u0013";
        AutoBlock.nb[46] = "]#8J!X_\"q$\\=\t+1\u0019o^\f\"}\u0014\u001f\f\u00050;\u0018yR\f#z$nO\u000er&@ \u0007^I0V%\u0006S-~\u001eu=";
        AutoBlock.nb[47] = "6\u0019m8F04\u0018$V\u0004Ub\u0011dk\b6g\u0018(fxdn\nnj\u001e:g\u0019/V";
        AutoBlock.nb[48] = "DX-uL.K\u0004p,)<^\u001c&(RQJ\u0013?!Wn\u0019\u001bt<) U[t+Mn\u001d\u000bO";
        AutoBlock.nb[49] = "jtf@U\u0010>h#\u0007:\u0005obeU}\u0015\u0006wj\u0002\u0001\u001cb9\"R:\u0017tx`WQCh='8";
        AutoBlock.nb[50] = "B%O\u001d3I\u00169\nZ\\HI0U\b5Dp>U\u00181\"_%\u000b^;F\u0011m[e";
        AutoBlock.nb[51] = "O\u00191@e\u0006M\u0018x.\u0011c\u001b\u00118\u0013+\u0000\u001e\u0018t\u001e[\u0002\u001bHpP2\u0019@\u00190.";
        AutoBlock.nb[52] = "7\u0014u<s\b8H(e\u0016\u0017=VwNq\u001b9-&4o\rhKx=|LT";
        AutoBlock.nb[53] = "QyKu%]\u001c}TdDum/\u001dc9Z\f*\u001d#:;Pp\\!4XUy\u0010,D\u0006\\mQ}%\u0003\\-R\u001cz[\u0004bAw%\u0003\fj,";
        AutoBlock.nb[54] = "V;\u0013mDX[~\u001c6}Z@#\u0012\u0001B\r\u0005~E\u0001\u0017E=~@0\u0017N^7\u0007$A4";
        AutoBlock.nb[55] = "i\u007fpgrgo\u007f#:\u000epxxw\n1!4.,\nk\u001e4/e'2xj&vf\u000e";
        AutoBlock.nb[56] = "";
        AutoBlock.ob[56] = "abqf6mcc8\bm\b99qr4ng0b3\bnqwnn214or\b99qr4ng0b3\b31nbrkzvz4\b";
        AutoBlock.nb[57] = "<RdG%?>S-)KZhZm\u0014k9mS!\u0019\u001b9*StWt0j\\c)j(o\u0003zM$`?8";
        AutoBlock.nb[58] = "\u0013DT&%\u0003\u000bQGw]\u001bz\u001d\u001b4 \u0018\u001b\u0018\u001bt#yD@C;0\u0012\u001b\u0018K3]";
        AutoBlock.nb[59] = "ww(w=tuva\u0019d=d'Q\":wtg2k}c\"\u001d";
        AutoBlock.nb[60] = "HD%`zh\u0017\u001c-h\u0017y\u001bA+nzB\u0018$\"\u007fufH\u0015.bpfvU>*,d\u0012\u001bvz\u0017";
        AutoBlock.nb[61] = "1!t@>g)4g\u0011FFXuxA?$d,1\u001c~\u001d2<i\u0012/#; tJF";
        AutoBlock.nb[62] = "}\u0015'\u0007w\u0012\u007f\u0014ni\u0015w)\u001d.T9\u0014,\u0014bYI\u0006fEe\u000e-H.\u0015^";
        AutoBlock.nb[63] = "\u001c;&qh\u0013\u001e:o\u001f0v\u0004#e$1\u0012Jk5\u001fk\u0014\u0005l/|n\u001dIa_~kMM/6e0\u001c\rQ";
        AutoBlock.nb[64] = "";
        AutoBlock.ob[64] = "abqf6mcc8\bM\b99qr4ng0b3\b7j2mq3aqnq\bkwcavgb7lv\brjyxslz8nh\byz23ol72b\byz23ol72b\b";
        AutoBlock.nb[65] = "\u0018_M!,M\u0000J^pTNq\u0006Q<iG\u0012\u0003Xpd7AI_5m\u000b\u0018\u0000\u0002tT\n@DN-5\u000f@\u0004ML%EK\u0000T(k\r\u001b;";
        AutoBlock.nb[66] = "";
        AutoBlock.ob[66] = "80n9rw5gu1\bI\b8zdq14a390\b99qr4ng0b3\b5jx5xk0c48\byz23ol72b\b";
        AutoBlock.nb[67] = "AX\rq\u001d\b\u000e^Uq|4pT\u0018:G\u0002\u0014\u001aPj|";
        AutoBlock.nb[68] = "V~sV\u0017LT\u007f:8D)\u0002vz\u0005YJ\u0007\u007f6\b)XM.1_M\u0016\u0005~\nI[\u0013\u0004sn\u0007\u0013C?";
        AutoBlock.nb[69] = "";
        AutoBlock.ob[69] = "alvepryye4\bi\b8zdq14a390\b59wuii097v\b5jx5xk0c48\byz23ol72b\byz23ol72b\b";
        AutoBlock.nb[70] = ">\u0000eD\u0004I<\u0001,*@,j\bl\u0017JOo\u0001 \u001a:\u0017n\fvPY^)\u0018 *";
        AutoBlock.nb[71] = "sD1\u0011=`,\u001c9\u0019Pd XXPar7\u0018>\u000ehav$5\u0013 f3\u001bf\u001bk{M";
        AutoBlock.nb[72] = ".!\tn\u0011\u000f37Wwr'O=\f~K\u001302P#\u0012v";
        AutoBlock.nb[73] = "\u0015\u0011\u0001{1!\u000eJP;O!tAH3\"6K\u0012@x?H";
        AutoBlock.nb[74] = "9K\u0012m[9\"\u0010C-%(X\u001b[%H.gHSnUP";
        AutoBlock.nb[75] = "\u001b}Q\u000bd\u001e\u0003hBZ\u001c\u0007rsV\u0005#\rLzJ\u0018{dO(P\u001b}\u0005J(\u0010\u0018\u001c\u000fO Q]b\u0002\u0013y^f";
        AutoBlock.nb[76] = "{_1~\u0004EcJ\"/|a\u0012\u000b=\u007f\u0005\u0006.Rt\"D?/Y?.\f\\*Ps#|\u0002#D2r\u001d\u0007#\u00041\u0013\rM(\u0000(wC\u0005x;";
        AutoBlock.nb[77] = "\u001f\u000be\u0015B\n\u0016\u001dy\u0017|_&L|\u0011\r\u000e@\u0012~\u001d\u00065\u001dO~\u0005\u0006VT\bjS|";
        AutoBlock.nb[78] = "\u00148\u000b\u0014j4Tn\u0018\u000e\u0004e(q\u0013^?nL?[\u000e\u0004";
        AutoBlock.nb[79] = "7,\t\u001e\u0010G:i\u0006E)E!4\br\u0016\u0012djSr@+03\u000f\u001fUR63\\B)\u001am+\u0019\u0019ODd8X%\u0012\u0012:8\u0019F[U.nc";
        AutoBlock.nb[80] = "NFEUg8LG\f;:]V^\u0006\u0000>9\u0018\u0016V;bdAFFX+#U\u0010<";
        AutoBlock.nb[81] = "/W#9}\n=\u0011r-\f\u001aW\u001c}%v_1Bt67cf\u001c5&0\u00058\u0015&g\f\\5\u0017)%7\n.K5\\7Z1G6?~\u001d%\u0011L";
        AutoBlock.nb[82] = "a@KO6D.F\u0013OWkP\u0000NNjY3\u0005G\u0002g)k\u0004JT-J\"C^\u0002W";
        AutoBlock.nb[83] = "#\u0001l\u007fF\n!\u0000%\u00113ow\te,\b\fr\u0000)!xTs\r\u007fk\u001b\u001d4\u0019)\u0011";
        AutoBlock.nb[84] = "\u0011%\fPOU\u0013$E>.0E-\u0005\u0003\u0001S@$I\u000eq\u000bA)\u001fD\u0012B\u0006=I>";
        AutoBlock.nb[85] = "nh]@S[-wQY=\u000bj\u007f\u001a][\u001cKd\u0005]x\u0001sa\u0001K=]uu]@^X|9P0";
        AutoBlock.nb[86] = "}sBl:q\u007fr\u000b\u0002J\u0014){K?tw,r\u00072\u0004/-\u007fQxgfjk\u0007\u0002";
        AutoBlock.nb[87] = "x5\u001bO4|`#\u0003FK\u0013\u001dkM\u000e1}{5D\u001dpA{5\u0017\u0013u'}'\u0018\u0012K'd%\u001a\u0011qx!=\u0006w\"+d4B\u0012 *-ZMF2;!<\u0013O!z\u001daE\u0011!;~(\u0002\u0005wA";
        AutoBlock.nb[88] = "\u0005xj\u0002 c\u0007y#lt\u0006QpcQneTy/\\\u001e=Uty\u0016}t\u0012`/l";
        AutoBlock.nb[89] = "H!4FhkJ }(\u001a\u000e\u001cz2U7o\u0019zrVVhX4+Nl7\u001d,7(g?X1qN96KpMY$4\u001a,)\u0017ld!z|Q,2G$uBm\u000e[)<X-jS{+HV";
        AutoBlock.nb[90] = "[Z-!\u0013+Y[dOCN\u000fR$r]-\n[h\u007f-u\u000bV>5N<LBhO";
        AutoBlock.nb[91] = "?\n\n1o\u0011=\u000bC_ tk\u0002\u0003b!\u0017n\u000bOoQ\u0005$ZH85Kl\ns";
        AutoBlock.nb[92] = "8/*s\u001607sw*s$<p4#\u0012) \u0016,+L=em-'\u001f2[g:pH(?)r s";
        AutoBlock.nb[93] = "\"X`;0{ Y)U`21\b\u0019$|$pU}j4tK";
        AutoBlock.nb[94] = "Z?FPW\u0013F|BWj\\9z[\u001eWRZ\u007fRRZ\"H5\u0003U\rF\u0006}Sn";
        AutoBlock.nb[95] = "\t`zNy<\u000ba3 \u001eY\u0011x9\u001b =_0i z;\u00107sC\u007f2\\:\u0003Q5c[mg\u001f}3`paQ7\"\u0004x3F'Y\u0011x9\u001b =_0i ";
        AutoBlock.nb[96] = "^t\\\\\bk\\u\u00152j\u000e\b|\u001fWO5^gCK63Un\u0018BU6\\\"\u00152G|\r%BV\t4]\u001eT@\f5Pz\u001a\b\\\u000e";
        AutoBlock.nb[97] = "$:lQ3K&;%?v.|alE1H\"h\u007f\u0004\r\u001f|)o\u0003kAu:.?|\\wkr[2\u0014'P";
        AutoBlock.nb[98] = "xwCmCD,\u007f[a;@\u0016tN>\u0000Lr:\u0006n;";
        AutoBlock.nb[99] = "\u0002\u00069@D/M\u0000a@%,3\n,\u000b\u001e%WDd[%";
        AutoBlock.nb[100] = "49\u0000Vzzf*@E\u0002|<-\u0002Cxv='6M\u007f}?%zJbh?9J\u0016~f\"H\u0015[xk#%GH8x[";
        AutoBlock.nb[101] = "[5b^\u000f\u001b\u0004mjVb\u000b\u0002/w.\r\u0001\u001f)sC_\u0012_:\u000b\u0015[\u0016\u000f/h\\\u001c\u0002YU";
        AutoBlock.nb[102] = "<9=yUWca5q8]y\u00141qDM\u0002(&3\u0003[ffnc8";
}
    public void onPreMouseInput(PreMouseInputEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var10001 = 104267827882366L;
        int var8 = (int)(var10001 << 40 >>> 40);
        var10001 = 91503647250490L;
        int var11 = (int)(var10001 << 40 >>> 40);
        var10001 = 42176070331598L;
        int var32 = 6007;
        B = false;
        if (I > 0) {
            --I;
            this.M = true;
        } else {
            this.M = false;
}
        int var37 = AutoBlock.t(81424435728200L);
        if (var37 != 0 && var37 != 2) {
            boolean var48;
            if (!AutoBlock.f$r2()) {
                K = AutoBlock.c();
                k = AutoBlock.c() ? 0 : 1;
                AutoBlock.m(21304, (byte)-38, var11);
                AutoBlock.f.field_71439_g.func_71008_a(AutoBlock.f.field_71439_g.func_70694_bm(), AutoBlock.f.field_71439_g.func_70694_bm().func_77988_m());
}
            float var47 = requireKillAura.c() ? KillAura.attackRange.L() : 3.0f;
            boolean var39 = !requireKillAura.c() || !KillAura.throughWall.c();
            this.F = this.D(var47, (byte)0, 6214846, var39, var8);
            m = true;
            boolean var40 = true;
            switch (mode.Y()) {
                case "LAG": 
                case "LAG_PRE": 
                case "LAG_LEGIT": 
                case "LAG_LEGIT_PRE": 
                case "LAG_NEW": 
                case "LAG_NEW_PRE": {
                    this.F(19386209426888L);
                    break;
}
                case "VANILLA": {
                    var40 = false;
                    if (AutoBlock.c() && (!manualLeftClick.c() || G)) {
                        this.e(0L, var40);
                        this.y(0L);
}
                    k = 2;
                    break;
}
                case "LEGIT": {
                    this.b(62049884109922L);
}
}
            boolean bl = var48 = mode.Y().contains("LAG") && System.currentTimeMillis() - this.Y >= mb;
            if (k == 12) {
                k = 7;
            } else if (k == 13) {
                k = 8;
            } else if (k == 9) {
                k = 0;
            } else if (k == 10) {
                k = 11;
            } else if (var48 || k == 2 || k == 5 || k == 6) {
                boolean var49 = false;
                boolean var43 = false;
                if (!manualLeftClick.c() || G) {
                    if (this.F != null) {
                        if (this.M(14410, '\u82a7', (short)var32, this.F, var40)) {
                            KillAura.x = true;
                            G = false;
                            var49 = true;
                            var43 = true;
                            B = true;
}
                    } else if (this.T('\u0000', 138059789010217L, var40)) {
                        KillAura.x = true;
                        G = false;
                        var49 = true;
}
}
                if (k != 5 && k != 6 && (var49 || var48) && (mode.Y().contains("LAG_NEW") || this.Q(var40, 73338735518706L, var43, this.F))) {
                    switch (mode.Y()) {
                        case "LAG": 
                        case "LAG_LEGIT": {
                            k = 4;
                            break;
}
                        case "LAG_PRE": {
                            this.o$r2();
                            this.W(0L);
                            this.J = true;
                            k = 0;
                            break;
}
                        case "LAG_LEGIT_PRE": {
                            this.W(0L);
                            this.J = true;
                            k = 0;
                            break;
}
                        case "LAG_NEW": {
                            if (AutoBlock.s(26815, -704018324)) {
                                k = 12;
                                break;
}
                            k = 7;
                            break;
}
                        case "LAG_NEW_PRE": {
                            if (AutoBlock.s(26815, -704018324)) {
                                k = 13;
                                break;
}
                            k = 8;
                            break;
}
                        default: {
                            k = 0;
}
}
}
}
            var1.q(9819, 57776);
        } else {
            Pair var38 = this.n(var37 != 2, 0L);
            if (var37 == 2 || ((Boolean)var38.a()).booleanValue() || !((Boolean)var38.p()).booleanValue()) {
                var1.q(9819, 57776);
}
}
}
    private static MethodHandle a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, long var4, long var6) {
        char var8 = var2.charAt(0);
        MethodHandle var9 = null;
        Field var10 = null;
        Method var11 = null;
        try {
            if (var8 != 'w' && var8 != 'm' && var8 != '\u00ee' && var8 != 'T') {
                var11 = AutoBlock.d(var4, var6);
                Class<?> var17 = var11.getDeclaringClass();
                String var19 = var11.getName();
                MethodType var20 = MethodType.methodType(var11.getReturnType(), var11.getParameterTypes());
                var9 = var8 == 'A' ? var0.findVirtual(var17, var19, var20) : (var8 == 'q' ? var0.findStatic(var17, var19, var20) : var0.findSpecial(var17, var19, var20, var17));
            } else {
                var10 = AutoBlock.c(var4, var6);
                Class<?> var12 = var10.getDeclaringClass();
                String var18 = var10.getName();
                Class<?> var14 = var10.getType();
                var9 = var8 == 'w' ? var0.findGetter(var12, var18, var14) : (var8 == 'm' ? var0.findSetter(var12, var18, var14) : (var8 == '\u00ee' ? var0.findStaticGetter(var12, var18, var14) : var0.findStaticSetter(var12, var18, var14)));
}
            return MethodHandles.dropArguments(var9, var3.parameterCount() - 2, new Class[]{Long.TYPE, Long.TYPE});
}
        catch (Exception var15) {
            StringBuilder var13 = new StringBuilder();
            var13.append(var15.getClass().getName()).append(" : ").append(var10 != null ? var10.toString() : (var11 != null ? var11.toString() : " null ")).append(" : ").append(var15.toString());
            throw new RuntimeException(var13.toString());
}
}
    private boolean y(long var1) {
        if (C) {
            PacketManager.b(new C09PacketHeldItemChange(AutoBlock.f.field_71439_g.field_71071_by.field_70461_c));
            C = false;
            this.k(false);
            return true;
}
        return true;
}
    public static boolean G(long var0) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (m && AutoBlock.s(26815, -704018324)) {
            switch (mode.Y()) {
                case "LAG": 
                case "LAG_PRE": 
                case "LAG_LEGIT": 
                case "LAG_LEGIT_PRE": 
                case "LAG_NEW": 
                case "LAG_NEW_PRE": 
                case "VANILLA": {
                    return true;
}
}
            return false;
}
        return false;
}
    private boolean e(long var1, boolean var3) {
        if (!var3 || !OutgoingPacketState.h && !OutgoingPacketState.P && !OutgoingPacketState.E) {
            int var5;
            int var4 = AutoBlock.f.field_71439_g.field_71071_by.field_70461_c;
            this.p = var5 = this.p + 1 >= 8 ? (var4 == 0 ? var4 + 1 : 0) : (this.p + 1 == var4 ? this.p + 2 : this.p + 1);
            PacketManager.b(new C09PacketHeldItemChange(var5));
            C = true;
            this.k(false);
            return true;
}
        return false;
}
                if (var5 < 224) {
                char var6 = (char)((char)(var5 & 0x1F) << 6);
                byte var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 0x3F));
                var3[var1++] = var6;
                continue;
}
            if (var4 >= var2 - 2) continue;
            char var12 = (char)((char)(var5 & 0xF) << 12);
            byte var9 = var0[++var4];
            var12 = (char)(var12 | (char)(var9 & 0x3F) << 6);
            var9 = var0[++var4];
            var12 = (char)(var12 | (char)(var9 & 0x3F));
            var3[var1++] = var12;
}
        return new String(var3, 0, var1);
}
    private static void m(int var0, byte var1, int var2) {
        D = true;
}
    private void F(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        String var14 = mode.Y();
        if (k != 0 && k != 3 && k != 5) {
            if (k == 1 || k == 6) {
                k = 2;
                if (AutoBlock.X(0L, (short)-10450) && this.F != null && RaytraceUtil.q(50051018191872L, (Entity)this.F, 3.5)) {
                    KeepSprint.t = 2;
                    KeepSprint.a = 0;
}
}
        } else {
            this.W(0L);
            if (var14.contains("LAG_NEW")) {
                this.C(10860524904695L);
            } else {
                this.r(38310564483000L);
}
            if (k == 1 || k == 6) {
                if (var14.equals("LAG_LEGIT") || var14.equals("LAG_LEGIT_PRE")) {
                    this.o$r2();
                    this.n();
                    AutoBlock.f.field_71439_g.func_71034_by();
                    this.J = false;
}
                if (AutoBlock.X(0L, (short)-10450) && this.F != null && RaytraceUtil.q(50051018191872L, (Entity)this.F, 3.5)) {
                    KeepSprint.t = 1;
                    KeepSprint.a = 0;
}
}
}
}
    private static int d(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x2CF;
        if (hb[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = gb[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])ib.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    ib.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/module/impl/combat/AutoBlock", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            AutoBlock.hb[var3] = var15;
}
        return hb[var3];
}
    private static Field a(Class var0, String var1, Class var2) {
        for (Field var6 : var0.getDeclaredFields()) {
            if (!var6.getName().equals(var1) || var6.getType() != var2) continue;
            return var6;
}
        return null;
}
    public void onUpdateCameraAndRender(long var1, UpdateCameraAndRenderEvent var3) {
        if ((m || this.M) && visualBlocking.c()) {
            var3.W(17984, 996510524L);
}
}
    public void onTick(TickEvent var1, long var2) {
        if (var1.v == AutoBlock.f.field_71474_y.field_74312_F.func_151463_i()) {
            AutoBlock.V(0L);
}
}
    private static boolean g$r1(long var0) {
        var0 = o ^ var0;
        long var2 = var0 ^ 0x77A2B4CE2059L;
        long var4 = var0 ^ 0x14ED6F311346L;
        List var6 = EntityUtil.x(EntityUtil.F(targetRange.L(), var4, fov.L()), players.c(), mobs.c(), animals.c(), bosses.c(), var2, friends.c(), enemies.c(), teammates.c(), bots.c(), silverfishes.c(), golems.c());
        return !var6.isEmpty();
}
    private void W(long var1) {
        PacketManager.M(true);
        this.J = true;
}
    private static Method b(Class var0, String var1, Class var2, int var3, Class[] var4) {
        Method var5 = AutoBlock.a(var0, var1, var2, var3, var4);
        if (var5 != null) {
            return var5;
}
        Class<?>[] var6 = var0.getInterfaces();
        if (var6 != null) {
            for (int var7 = 0; var7 < var6.length; ++var7) {
                var5 = AutoBlock.b(var6[var7], var1, var2, var3, var4);
                if (var5 == null) continue;
                return var5;
}
}
        return null;
}
    private static CallSite a(MethodHandles.Lookup var0, String var1, MethodType var2) {
        MutableCallSite var3 = new MutableCallSite(var2);
        try {
            var3.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(MethodHandles.lookup().findStatic(AutoBlock.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/Object;", AutoBlock.class.getClassLoader())).asCollector(Object[].class, var2.parameterCount()), 0, var0, var3, var1, var2), var2));
            return var3;
}
        catch (Exception var5) {
            throw new RuntimeException("Abyss/module/impl/combat/AutoBlock : " + var1 + " : " + var2.toString(), var5);
}
}
    private void C(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (AutoBlock.s(26815, -704018324)) {
            if (k == 0) {
                if (this.K(0L, true)) {
                    this.e(0L, false);
                    k = 3;
}
            } else {
                switch (apsMode.Y()) {
                    case "3APS": 
                    case "5APS": {
                        if (k == 3) {
                            OutgoingPacketState.P = true;
                            if (!this.y(0L)) break;
                            k = 5;
                            break;
}
                        if (k != 5) break;
                        OutgoingPacketState.P = true;
                        k = 1;
                        break;
}
                    case "7APS": {
                        if (k != 3 || !this.y(0L)) break;
                        k = 2;
                        break;
}
                    case "10APS": 
                    case "14APS": {
                        if (k == 3) {
                            if (!this.y(0L)) break;
                            k = 6;
                            break;
}
                        k = 1;
}
}
}
        } else if (k == 0) {
            if (this.K(0L, true)) {
                k = 3;
}
        } else {
            switch (apsMode.Y()) {
                case "3APS": {
                    if (k == 3) {
                        OutgoingPacketState.P = true;
                        k = 5;
                        break;
}
                    if (k != 5) break;
                    OutgoingPacketState.P = true;
                    k = 1;
                    break;
}
                case "5APS": {
                    if (k != 3) break;
                    OutgoingPacketState.P = true;
                    k = 1;
                    break;
}
                case "7APS": {
                    if (k != 3) break;
                    k = 2;
                    break;
}
                case "10APS": 
                case "14APS": {
                    k = k == 3 ? 6 : 1;
}
}
}
}
    public void onModuleTag(ModuleTagEvent var1, long var2) {
        var1.U("autoblockStage: " + k);
        var1.U("isBlocking: " + AutoBlock.c());
}
    private void r(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (AutoBlock.s(26815, -704018324)) {
            if (k == 0) {
                if (this.K(0L, true)) {
                    this.e(0L, false);
                    k = 3;
}
            } else {
                switch (apsMode.Y()) {
                    case "3APS": 
                    case "5APS": {
                        if (k == 3) {
                            OutgoingPacketState.P = true;
                            k = 5;
                            break;
}
                        if (k != 5) break;
                        OutgoingPacketState.P = true;
                        if (!this.y(0L)) break;
                        k = 1;
                        break;
}
                    case "7APS": {
                        if (k != 3) break;
                        OutgoingPacketState.P = true;
                        if (!this.y(0L)) break;
                        k = 1;
                        break;
}
                    case "10APS": {
                        if (k == 3) {
                            OutgoingPacketState.P = true;
                            if (!this.y(0L)) break;
                            k = 5;
                            break;
}
                        if (k != 5) break;
                        k = 1;
                        break;
}
                    case "14APS": {
                        if (k != 3 || !this.y(0L)) break;
                        k = 6;
}
}
}
        } else if (k == 0) {
            if (this.K(0L, true)) {
                k = 3;
}
        } else {
            switch (apsMode.Y()) {
                case "3APS": 
                case "5APS": {
                    if (k == 3) {
                        OutgoingPacketState.P = true;
                        k = 5;
                        break;
}
                    if (k != 5) break;
                    OutgoingPacketState.P = true;
                    k = 1;
                    break;
}
                case "7APS": {
                    if (k != 3) break;
                    OutgoingPacketState.P = true;
                    k = 1;
                    break;
}
                case "10APS": {
                    if (k == 3) {
                        OutgoingPacketState.P = true;
                        k = 5;
                        break;
}
                    k = 6;
                    break;
}
                case "14APS": {
                    if (k != 3) break;
                    k = 6;
}
}
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
            int var4 = AutoBlock.a(var0, var2);
            Object var5 = nb[var4];
            if (!(var5 instanceof String)) {
                return (Method)var5;
}
            String var6 = ob[var4];
            int var7 = var6.indexOf(8);
            var8 = AutoBlock.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
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
                var15 = AutoBlock.b(Long.parseLong(var6.substring(var12, var17), 36), 0L);
                if (var16 >= var13) continue;
                var14[var16] = var15;
}
            var23 = var8;
            do {
                Method var26;
                if ((var26 = AutoBlock.a(var23, var10, var15, var13, var14)) != null) {
                    AutoBlock.nb[var4] = var26;
                    return var26;
}
                if (var23.getName().equals("java.lang.Object")) break block10;
            } while ((var23 = var23.getSuperclass()) != null);
            var23 = AutoBlock.b(1523196619010493L, 0L);
}
        var23 = var8;
        while (true) {
            Class<?>[] var27;
            if ((var27 = var23.getInterfaces()) != null) {
                for (int var18 = 0; var18 < var27.length; ++var18) {
                    Method var19 = AutoBlock.b(var27[var18], var10, var15, var13, var14);
                    if (var19 == null) continue;
                    AutoBlock.nb[var4] = var19;
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
            var23 = AutoBlock.b(1523196619010493L, 0L);
}
}
    public static void V(long var0) {
        G = true;
}
    private void n() {
        if (onlyUnblockWithoutNoSlow.c() && AutoBlock.noSlowLive()) {
            return;
}
        PacketManager.M(false);
}
    public void onReceivePacket(ReceivePacketEvent var1, long var2) {
        S19PacketEntityStatus var6;
        if (smartUnblock.c() && var1.d instanceof S19PacketEntityStatus && (var6 = (S19PacketEntityStatus)var1.d).func_149161_a((World)AutoBlock.f.field_71441_e) instanceof EntityPlayerSP && var6.func_149160_c() == 2 && MathUtil.Q(smartUnblockChance.k(), 0L)) {
            I = (int)smartUnblockTicks.L();
}
}
    private void b(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        block26: {
            block28: {
                block27: {
                    block25: {
                        if (k != 1) break block25;
                        k = 2;
                        if (AutoBlock.X(0L, (short)-10450) && this.F != null && RaytraceUtil.q(50051018191872L, (Entity)this.F, 3.5)) {
                            KeepSprint.t = 2;
                            KeepSprint.a = 0;
}
                        break block26;
}
                    if (k != 0 && k != 3 && k != 5) break block26;
                    if (k != 0) break block27;
                    switch (apsMode.Y()) {
                        case "10APS": {
                            if (this.K(0L, true)) {
                                AutoBlock.f.field_71439_g.func_71034_by();
                                k = 7;
                                break;
}
                            break block28;
}
                        case "14APS": {
                            if (this.K(0L, true)) {
                                AutoBlock.f.field_71439_g.func_71034_by();
                                k = 3;
                                break;
}
                            break block28;
}
                        default: {
                            k = 3;
                            break;
}
}
                    break block28;
}
                switch (apsMode.Y()) {
                    case "3APS": 
                    case "5APS": {
                        if (k == 3) {
                            OutgoingPacketState.P = true;
                            k = 5;
                            break;
}
                        if (!this.K(0L, true)) break;
                        AutoBlock.f.field_71439_g.func_71034_by();
                        k = 7;
                        break;
}
                    case "7APS": {
                        if (!this.K(0L, true)) break;
                        AutoBlock.f.field_71439_g.func_71034_by();
                        k = 7;
                        break;
}
                    case "14APS": {
                        if (k != 3) break;
                        k = 7;
}
}
}
            if (k == 7) {
                k = 1;
                if (AutoBlock.X(0L, (short)-10450) && this.F != null && RaytraceUtil.q(50051018191872L, (Entity)this.F, 3.5)) {
                    KeepSprint.t = 1;
                    KeepSprint.a = 0;
}
}
}
}
    public static int t(long var0) {
        var0 = o ^ var0;
        long var2 = var0 ^ 0x13CC74CA33B1L;
        long var6 = var0 ^ 0x722CE674EEFBL;
        if (!Modules.J(AutoBlock.class).o()) {
            return 0;
}
        if (mode.R("NONE")) {
            return 0;
}
        if (!ItemUtil.d()) {
            return 0;
}
        if (requireKillAura.c() && !KillAura.a) {
            return 0;
}
        if (requireRightClick.c() && !KeyBindUtil.V(AutoBlock.f.field_71474_y.field_74313_G.func_151463_i(), var6)) {
            return 0;
}
        if (!AutoBlock.T(0L)) {
            return 0;
}
        if (I > 0) {
            return 2;
}
        return AutoBlock.g$r1(var2) ? 1 : 0;
}
    public void onPostUpdate(long var1, short var3, PostUpdateEvent var4) {
        long var5 = (var1 << 16 | (long)var3 << 48 >>> 48) ^ o;
        long var9 = var5 ^ 0x2D9DCAF87D55L;
        switch (k) {
            case 4: {
                switch (mode.Y()) {
                    case "LAG": {
                        this.o$r2();
                        this.W(0L);
                        k = 0;
                        break;
}
                    case "LAG_LEGIT": {
                        this.W(0L);
                        k = 0;
}
}
}
            default: {
                break;
}
            case 7: {
                this.Q(false, var9, B, this.F);
                k = 10;
                break;
}
            case 8: {
                this.Q(false, var9, B, this.F);
                this.o$r2();
                this.W(0L);
                k = 9;
                break;
}
            case 11: {
                this.o$r2();
                this.W(0L);
                k = 0;
}
}
}
    private static String b(int var0, long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var5 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x21C2;
        if (ab[var5] == null) {
            Object[] var4;
            try {
                Long var3 = Thread.currentThread().getId();
                var4 = (Object[])bb.get(var3);
                if (var4 == null) {
                    var4 = new Object[]{Cipher.getInstance("DES/CBC/PKCS5Padding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    bb.put(var3, var4);
}
}
            catch (Exception var10) {
                throw new RuntimeException("Abyss/module/impl/combat/AutoBlock", var10);
}
            byte[] var6 = new byte[8];
            var6[0] = (byte)(var1 >>> 56);
            for (int var7 = 1; var7 < 8; ++var7) {
                var6[var7] = (byte)(var1 << var7 * 8 >>> 56);
}
            DESKeySpec var11 = new DESKeySpec(var6);
            SecretKey var8 = ((SecretKeyFactory)var4[1]).generateSecret(var11);
            ((Cipher)var4[0]).init(2, (Key)var8, (IvParameterSpec)var4[2]);
            byte[] var9 = S[var5].getBytes("ISO-8859-1");
            AutoBlock.ab[var5] = AutoBlock.b(((Cipher)var4[0]).doFinal(var9));
}
        return ab[var5];
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
    private boolean K(long var1, boolean var4) {
        if (!AutoBlock.c()) {
            return true;
}
        if (!(!ItemUtil.d() || var4 && (OutgoingPacketState.h || OutgoingPacketState.P || OutgoingPacketState.E))) {
            PacketManager.b(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
            this.k(false);
            return true;
}
        return false;
}
    @Override
    public final void x(long var1, EventBus var3) {
        int var4 = (int)((var1 ^ 0x75EE19AC7C75L) >>> 32);
        int var5 = (int)((var1 ^ 0x75EE19AC7C75L) << 32 >>> 56);
        AutoBlockBinder.Z(var4, var3, (byte)var5, this);
}
    private EntityLivingBase D(double var1, byte var3, int var4, boolean var5, int var6) {
        long var7 = ((long)var3 << 56 | (long)var4 << 32 >>> 8 | (long)var6 << 40 >>> 40) ^ o;
        long var9 = var7 ^ 0x237CF9A41133L;
        long var11 = var7 ^ 0x72CD578384CFL;
        long var13 = var7 ^ 0x682319B67435L;
        EntityLivingBase var15 = null;
        if (KillAura.a) {
            var15 = KillAura.H6;
            if (!KillAura.throughWall.c() && RaytraceUtil.V((Entity)var15, var9, var1)) {
                return null;
}
            if (!RaytraceUtil.i((Entity)var15, var1, var13, var5)) {
                return null;
}
        } else {
            List var16 = EntityUtil.x(RaytraceUtil.j(var1), players.c(), mobs.c(), animals.c(), bosses.c(), var11, friends.c(), enemies.c(), teammates.c(), bots.c(), silverfishes.c(), golems.c());
            var16.removeIf(var2 -> {
                long var3x = 20827164685641L;
                long var5x = 140537582766428L;
                return RaytraceUtil.V((Entity)var2, var5x, var1);
            });
            var16.removeIf(var3x -> {
                long var4x = o ^ 0x3DE696B5DC09L;
                long var6x = var4x ^ 0xB1131D40142L;
                return !RaytraceUtil.i((Entity)var3x, var1, var6x, var5);
            });
            var16.sort(Comparator.comparingDouble(var3x -> {
                long var4x = o ^ 0x23DACE3AE3DDL;
                long var6x = var4x ^ 0x1A60723434ECL;
                return RaytraceUtil.M(var6x, (Entity)var3x, var1, var5);
            }));
            if (!var16.isEmpty()) {
                var15 = (EntityLivingBase)var16.get(0);
}
}
        return var15;
}
    private boolean T(char var1, long var2, boolean var4) {
        long var5 = ((long)var1 << 48 | var2 << 16 >>> 16) ^ o;
        if (var4 && !AutoBlock.zkm$unresolved$6$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_iT_c_OR_Abyss_yO_f_y_slots_41_99(var5)) {
            return false;
}
        if (OutgoingPacketState.T) {
            return true;
}
        AutoBlock.f.field_71439_g.func_71038_i();
        return true;
}
    public AutoBlock(byte var1, long var2) {
        super(((long)var1 << 56 | 0x2756EDCF4866L) ^ o ^ 0x3101BF5B21ECL);
        this.declare("AutoBlock", Category.Combat, "Allows you to combat while sword blocked", new Setting[0]);
        this.p = 0;
        this.J = false;
        this.F = null;
        this.Y = System.currentTimeMillis();
        this.M = false;
}
    private static boolean X(long var0, short var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return Modules.J(KeepSprint.class).o() && KeepSprint.mode.R("PREDICTION");
}
    private static int a(long var0, long var2) {
        int var16;
        int var4 = (int)((var0 ^= var2 << 48 | var2) >>> 46);
        if (ob[var4] != null) {
            return var4;
}
        Object var5 = nb[var4];
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
        AutoBlock.ob[var4] = new String(var13);
        return var4;
}
    private static boolean zkm$unresolved$1$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_iT_c_OR_Abyss_yO_Y_y_slots_41_67(long var2) {
        try {
            MethodType var4 = MethodType.fromMethodDescriptorString("(JJ)Z", AutoBlock.class.getClassLoader());
            return MethodHandles.explicitCastArguments(AutoBlock.a(MethodHandles.lookup(), null, "q", var4, 1362679790029856311L, var2), var4).invoke(1362679790029856311L, var2);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private static boolean zkm$unresolved$2$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_yO_Y_OR_Abyss_yO_f_y_slots_67_99(long var2) {
        try {
            MethodType var4 = MethodType.fromMethodDescriptorString("(JJ)Z", AutoBlock.class.getClassLoader());
            return MethodHandles.explicitCastArguments(AutoBlock.a(MethodHandles.lookup(), null, "q", var4, 7385552860468072201L, var2), var4).invoke(7385552860468072201L, var2);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private static boolean zkm$unresolved$6$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_iT_c_OR_Abyss_yO_f_y_slots_41_99(long var2) {
        try {
            MethodType var4 = MethodType.fromMethodDescriptorString("(JJ)Z", AutoBlock.class.getClassLoader());
            return MethodHandles.explicitCastArguments(AutoBlock.a(MethodHandles.lookup(), null, "q", var4, -855537796426212009L, var2), var4).invoke(-855537796426212009L, var2);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
                Cipher var16 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var16.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var23 = new String[29];
            int var21 = 0;
            String var20 = "P)\u00c7=@x\u0019\u00c8\u00b6\b\b\u008e\u00ca\u00e7\u00d1\u0016B.QW/\u00ef\u00e6*\u0010+,'\r\u00c4\u008c\u00d74\u00ca\u00fc\u00e9B\u0019\u00f9\u00aa\r\u0018\u0094\u00b4\u00ac\u00f1\u009a05\u00e5\u00eeb_2Rx\u00eb\u00a7Y!O\u00b2\u00a8S\u00e1\u0013\u0010\u00a7\u00dck\u0010S\u00c2I\u008bs\u00c0?\u0080\u001e\u00c5\u008a\u008c\u0010\u00da3\u00ce:\u00d0\u00dew\u00ce\u00cc\u00b3O\u00e8F\u008b\u00cf\u00ba\u0010f(\u00a7\u0083\u00fc8\u00bb\u00b71\u0080\u00a1$\u0017*\t\u00de\u0018\u0010\u0094\u00f5\u0086K\u0086t\u00fbQqL\u001f\u00ba9\u00e8V:\u0007\u00dfOH\u00fd\u0006\u00e0\u0010\u00bd\u0099 ;d\u001f\u00da\u008c\u00ba\u007f\u00c0\u00c9$\u00cb\bU\u0010U\u00fa\u0083\u008a\u0000e\n\u008b\u00b5\u008e,6\u00bb\u00b5<\u00fc\u0010\u00ae\u007f\u00c7\u0083\u007f!\u0011\u00bd-yz\u0007\u0093+\u0099\u00f4 \u00ef)\u00f4T\u00f3\u00e0\u0010\u00e6i\tc\u007f\u0089\u001d\u0018\u00d6d\u0096\u00a7\u001c\u00e7\u000eq\u00ad\u00e1\u00a8E\u00da\u00ec\u0095?8\u0010\u00ff$j\u00a6\u00e4\u0087{\u008e\u00d9\u0094\u00a0\u008fw\u0002\u00b1^\u0010\u00aa\u0010\u0088\u00a1\u00ad\u00e8\u00f4e\u00f2n'j\u00dc@M\u00cb b\u000e\u009e\u00bd\u00a8v\u00d5\u008e\"\u00a2\u0081\u0004\u0005\u00f6F\u00b7\u009aj\u0011\u00ab\u0016\u0006}\u00b9\u001eD\u00aeQ\u00a9\u008b\u009cw\u0010<\u00fc\u008b\u001c\u00b00\u00f3\u00f6\u00cd\u009c\u00f0s\u00d0\u0096\u00c9\u00c0\u0010\u00bf\u0094\u00c7\u0001\u0012\u0080\u0091\u0088#\u001fz\u009b\u0080\u0090\u00d9? \u0088[\u0096\u00023Zq\u0090\u00e1S<\u001bZ\u0015\u00f1\u00dbV\u009e~\r\u0091\u0098~\u00af\u00a2\u0018\u00f6hvG\u0086S\u0010\u00d7H\u0017\u00c4\u00fb;B$\u0083\u0011,\u00d9z\u008e\u00ba\u00a0\u0010\u00a7BP|\u001b>\u0016R-W\u00e9\u008e,\u00a3\u00ce\u00f1 \u00c9YIq\u00a8\u00d2\u00e8-l\u008a\u00c1\u00c9zA\u00b4\u00f33f\u00d29=\u0086\u00d239N/\u008d\u00a6\u00af3\u00db0_\u00ab\u00c9&\u00f2'g\u0093\u00b8\u0090\u00b3\u00f9\u00f7d\u0002c\u00c2\u00caI\u0001\u009d\u00b1\u00bf_\u009cf\u0007,\u00c7=}\u0000\u001d:b\u0017\u00c0_\u00cby\u00eb\u00dc\u0099Y\u00eb;\u008a?\u0010-\u001e=`r\u00ab\u0090\u00c9\u00aa\u00c9\u00a4\u00aa\u00f7\u00a3\u008b\u00c3\u0010<\r]ow1]\u0085\u00e7\u00b6[\u00f6\u0000!R\u00a1 I=\u00c8\u009f%(\u00b11\u00eb\u001e\u00b6qnVV\u000f\u00bd\u00bd\u0013:\u00daUZc\u0088\u00e0I\u0004A/K\u0096\u0010\u009e\u00c3\u00fb\u0094\u00f3<\u0094\b\u0010\u0088\u00a1\u001c\u00fbw\u0012^\u0010p\u00be\u00f5\u0084\u00b0\u00ca\u00b8\u00a4#\u00f8BO\u00f5oSk\u0010U)\u0002\u00a6\u0016\u00c8\u00b43pI\n\u00a3r\u00a1L\u00c7";
            int var22 = "P)\u00c7=@x\u0019\u00c8\u00b6\b\b\u008e\u00ca\u00e7\u00d1\u0016B.QW/\u00ef\u00e6*\u0010+,'\r\u00c4\u008c\u00d74\u00ca\u00fc\u00e9B\u0019\u00f9\u00aa\r\u0018\u0094\u00b4\u00ac\u00f1\u009a05\u00e5\u00eeb_2Rx\u00eb\u00a7Y!O\u00b2\u00a8S\u00e1\u0013\u0010\u00a7\u00dck\u0010S\u00c2I\u008bs\u00c0?\u0080\u001e\u00c5\u008a\u008c\u0010\u00da3\u00ce:\u00d0\u00dew\u00ce\u00cc\u00b3O\u00e8F\u008b\u00cf\u00ba\u0010f(\u00a7\u0083\u00fc8\u00bb\u00b71\u0080\u00a1$\u0017*\t\u00de\u0018\u0010\u0094\u00f5\u0086K\u0086t\u00fbQqL\u001f\u00ba9\u00e8V:\u0007\u00dfOH\u00fd\u0006\u00e0\u0010\u00bd\u0099 ;d\u001f\u00da\u008c\u00ba\u007f\u00c0\u00c9$\u00cb\bU\u0010U\u00fa\u0083\u008a\u0000e\n\u008b\u00b5\u008e,6\u00bb\u00b5<\u00fc\u0010\u00ae\u007f\u00c7\u0083\u007f!\u0011\u00bd-yz\u0007\u0093+\u0099\u00f4 \u00ef)\u00f4T\u00f3\u00e0\u0010\u00e6i\tc\u007f\u0089\u001d\u0018\u00d6d\u0096\u00a7\u001c\u00e7\u000eq\u00ad\u00e1\u00a8E\u00da\u00ec\u0095?8\u0010\u00ff$j\u00a6\u00e4\u0087{\u008e\u00d9\u0094\u00a0\u008fw\u0002\u00b1^\u0010\u00aa\u0010\u0088\u00a1\u00ad\u00e8\u00f4e\u00f2n'j\u00dc@M\u00cb b\u000e\u009e\u00bd\u00a8v\u00d5\u008e\"\u00a2\u0081\u0004\u0005\u00f6F\u00b7\u009aj\u0011\u00ab\u0016\u0006}\u00b9\u001eD\u00aeQ\u00a9\u008b\u009cw\u0010<\u00fc\u008b\u001c\u00b00\u00f3\u00f6\u00cd\u009c\u00f0s\u00d0\u0096\u00c9\u00c0\u0010\u00bf\u0094\u00c7\u0001\u0012\u0080\u0091\u0088#\u001fz\u009b\u0080\u0090\u00d9? \u0088[\u0096\u00023Zq\u0090\u00e1S<\u001bZ\u0015\u00f1\u00dbV\u009e~\r\u0091\u0098~\u00af\u00a2\u0018\u00f6hvG\u0086S\u0010\u00d7H\u0017\u00c4\u00fb;B$\u0083\u0011,\u00d9z\u008e\u00ba\u00a0\u0010\u00a7BP|\u001b>\u0016R-W\u00e9\u008e,\u00a3\u00ce\u00f1 \u00c9YIq\u00a8\u00d2\u00e8-l\u008a\u00c1\u00c9zA\u00b4\u00f33f\u00d29=\u0086\u00d239N/\u008d\u00a6\u00af3\u00db0_\u00ab\u00c9&\u00f2'g\u0093\u00b8\u0090\u00b3\u00f9\u00f7d\u0002c\u00c2\u00caI\u0001\u009d\u00b1\u00bf_\u009cf\u0007,\u00c7=}\u0000\u001d:b\u0017\u00c0_\u00cby\u00eb\u00dc\u0099Y\u00eb;\u008a?\u0010-\u001e=`r\u00ab\u0090\u00c9\u00aa\u00c9\u00a4\u00aa\u00f7\u00a3\u008b\u00c3\u0010<\r]ow1]\u0085\u00e7\u00b6[\u00f6\u0000!R\u00a1 I=\u00c8\u009f%(\u00b11\u00eb\u001e\u00b6qnVV\u000f\u00bd\u00bd\u0013:\u00daUZc\u0088\u00e0I\u0004A/K\u0096\u0010\u009e\u00c3\u00fb\u0094\u00f3<\u0094\b\u0010\u0088\u00a1\u001c\u00fbw\u0012^\u0010p\u00be\u00f5\u0084\u00b0\u00ca\u00b8\u00a4#\u00f8BO\u00f5oSk\u0010U)\u0002\u00a6\u0016\u00c8\u00b43pI\n\u00a3r\u00a1L\u00c7".length();
            int var19 = 24;
            int var30 = -1;
            block9: while (true) {
                String var31 = var20.substring(++var30, var30 + var19);
                int var10001 = -1;
                while (true) {
                    byte[] var24 = var16.doFinal(var31.getBytes("ISO-8859-1"));
                    String var44 = AutoBlock.b(var24).intern();
                    switch (var10001) {
                        case 0: {
                            var23[var21++] = var44;
                            if ((var30 += var19) >= var22) {
                                S = var23;
                                ab = new String[29];
                                ib = new HashMap(13);
                                var10003 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
                                for (int var6 = 1; var6 < 8; ++var6) {
                                    var10003[var6] = (byte)(56695932197746L << var6 * 8 >>> 56);
}
                                Cipher var5 = Cipher.getInstance("DES/CBC/NoPadding");
                                var5.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var11 = new long[27];
                                int var8 = 0;
                                String var9 = "\u0006\u007f6\u0006q\u008d\u0018\u001b)\u00a7<\u00bd`g\u00cf\u00ee{\u0087\u00cbi\u00d0>\u0001\u00cfI\u00edoc\u00fc\u00dd\u00c4\u00c6X\u00ff\u009d\u00e1f\u0096\u00aes\u00bebXrdi\u0016\u0010+h\u00a7q\u00b9\u00bf\u008d\u0017\u0004\u0081\u00b5\u0018\u00d4\u000f\u00c3\u0012\u000e\u00c3\u0098\u00f0\u00c7\u00e8\u00f3.d\u00b1\u0006\u00c7\u001bja\u00ab\u0007\u00f1\u0007\u00ef\u0083\u0011h\u00f3\u00cd^\u0087\u0000\u00de}I\u00b6\u00a0\u008b\u009dF\u00f7$\u00922o\u00fe:\u00e8lXn\u0082\u00cf\u00b2yK\u00d66h\u001a\u00f6xX\u00100\u0004\u0094\u00df\u00ff\u00e3\u00e8\u00b7\u00fd\u00a7t\u00f1S\u0080\u001e~\u00b5>\u001b\u0018L\u009c\u00ee\u00d5\u00bf\u00e9\u0093\u0018q\u0098\u0000\u0092`\u00f1\u0092&\u0097f7\u00bf\u008b\u00d3kmV\u00fd\r\u0002i;B\u00c5\u00e1\u0017\u0006\u0015\u0005$s\u0082\u00fb\u00cc\u0017Q\u00be\u00f8<\u00b2~\u0095\u00ce(\u00c5\u00bd\u00dby";
                                int var10 = "\u0006\u007f6\u0006q\u008d\u0018\u001b)\u00a7<\u00bd`g\u00cf\u00ee{\u0087\u00cbi\u00d0>\u0001\u00cfI\u00edoc\u00fc\u00dd\u00c4\u00c6X\u00ff\u009d\u00e1f\u0096\u00aes\u00bebXrdi\u0016\u0010+h\u00a7q\u00b9\u00bf\u008d\u0017\u0004\u0081\u00b5\u0018\u00d4\u000f\u00c3\u0012\u000e\u00c3\u0098\u00f0\u00c7\u00e8\u00f3.d\u00b1\u0006\u00c7\u001bja\u00ab\u0007\u00f1\u0007\u00ef\u0083\u0011h\u00f3\u00cd^\u0087\u0000\u00de}I\u00b6\u00a0\u008b\u009dF\u00f7$\u00922o\u00fe:\u00e8lXn\u0082\u00cf\u00b2yK\u00d66h\u001a\u00f6xX\u00100\u0004\u0094\u00df\u00ff\u00e3\u00e8\u00b7\u00fd\u00a7t\u00f1S\u0080\u001e~\u00b5>\u001b\u0018L\u009c\u00ee\u00d5\u00bf\u00e9\u0093\u0018q\u0098\u0000\u0092`\u00f1\u0092&\u0097f7\u00bf\u008b\u00d3kmV\u00fd\r\u0002i;B\u00c5\u00e1\u0017\u0006\u0015\u0005$s\u0082\u00fb\u00cc\u0017Q\u00be\u00f8<\u00b2~\u0095\u00ce(\u00c5\u00bd\u00dby".length();
                                int var7 = 0;
                                block12: while (true) {
                                    var10001 = var7;
                                    byte[] var12 = var9.substring(var10001, var7 += 8).getBytes("ISO-8859-1");
                                    long[] var34 = var11;
                                    var10001 = var8++;
                                    long var48 = ((long)var12[0] & 0xFFL) << 56 | ((long)var12[1] & 0xFFL) << 48 | ((long)var12[2] & 0xFFL) << 40 | ((long)var12[3] & 0xFFL) << 32 | ((long)var12[4] & 0xFFL) << 24 | ((long)var12[5] & 0xFFL) << 16 | ((long)var12[6] & 0xFFL) << 8 | (long)var12[7] & 0xFFL;
                                    int var53 = -1;
                                    while (true) {
                                        long var13 = var48;
                                        byte[] var15 = var5.doFinal(new byte[]{(byte)(var13 >>> 56), (byte)(var13 >>> 48), (byte)(var13 >>> 40), (byte)(var13 >>> 32), (byte)(var13 >>> 24), (byte)(var13 >>> 16), (byte)(var13 >>> 8), (byte)var13});
                                        long var56 = ((long)var15[0] & 0xFFL) << 56 | ((long)var15[1] & 0xFFL) << 48 | ((long)var15[2] & 0xFFL) << 40 | ((long)var15[3] & 0xFFL) << 32 | ((long)var15[4] & 0xFFL) << 24 | ((long)var15[5] & 0xFFL) << 16 | ((long)var15[6] & 0xFFL) << 8 | (long)var15[7] & 0xFFL;
                                        switch (var53) {
                                            case 0: {
                                                long var51;
                                                var34[var10001] = var56;
                                                if (var7 < var10) break;
                                                gb = var11;
                                                hb = new Integer[27];
                                                var10003 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
                                                for (int var1 = 1; var1 < 8; ++var1) {
                                                    var10003[var1] = (byte)(56695932197746L << var1 * 8 >>> 56);
}
                                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                                byte[] var4 = var0.doFinal(new byte[]{11, -77, -89, 58, 64, -16, -81, 51});
                                                mb = var51 = ((long)var4[0] & 0xFFL) << 56 | ((long)var4[1] & 0xFFL) << 48 | ((long)var4[2] & 0xFFL) << 40 | ((long)var4[3] & 0xFFL) << 32 | ((long)var4[4] & 0xFFL) << 24 | ((long)var4[5] & 0xFFL) << 16 | ((long)var4[6] & 0xFFL) << 8 | (long)var4[7] & 0xFFL;
                                                return;
}
                                            default: {
                                                var34[var10001] = var56;
                                                if (var7 < var10) continue block12;
                                                var9 = "^)\u00b72\u0015a\u00d5\u009d\u00a9\u0011\u00f0}\u009dF\u000f\u00c4";
                                                var10 = "^)\u00b72\u0015a\u00d5\u009d\u00a9\u0011\u00f0}\u009dF\u000f\u00c4".length();
                                                var7 = 0;
}
}
                                        int var41 = var7;
                                        var12 = var9.substring(var41, var7 += 8).getBytes("ISO-8859-1");
                                        var34 = var11;
                                        var10001 = var8++;
                                        var48 = ((long)var12[0] & 0xFFL) << 56 | ((long)var12[1] & 0xFFL) << 48 | ((long)var12[2] & 0xFFL) << 40 | ((long)var12[3] & 0xFFL) << 32 | ((long)var12[4] & 0xFFL) << 24 | ((long)var12[5] & 0xFFL) << 16 | ((long)var12[6] & 0xFFL) << 8 | (long)var12[7] & 0xFFL;
                                        var53 = 0;
}
                                    break;
}
}
                            var19 = var20.charAt(var30);
                            break;
}
                        default: {
                            var23[var21++] = var44;
                            if ((var30 += var19) < var22) {
                                var19 = var20.charAt(var30);
                                continue block9;
}
                            var20 = "\u0095\u00a8\u00c0h\u00f4\u00a7k;u\u001ax\u0085xj\u0081\u00e8\u0010\u00e1bp\u0013\u0014.m_\u00ea\u00f8\u0014\u0095\u009c\u00ae\u001dU";
                            var22 = "\u0095\u00a8\u00c0h\u00f4\u00a7k;u\u001ax\u0085xj\u0081\u00e8\u0010\u00e1bp\u0013\u0014.m_\u00ea\u00f8\u0014\u0095\u009c\u00ae\u001dU".length();
                            var19 = 16;
                            var30 = -1;
}
}
                    var31 = var20.substring(++var30, var30 + var19);
                    var10001 = 0;
}
                break;
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var27) {
            throw new RuntimeException(var27);
}
}
    static {
        KEY_OFFSETS = new byte[]{10, 4, 8, 14, 19, 28, 31, 45, 50, 12, 46, 11, 60, 47, 58, 44, 53, 5, 7, 23, 55, 9, 52, 16, 24, 2, 1, 35, 17, 6, 51, 3, 26, 0, 39, 49, 38, 59, 33, 43, 13, 48, 57, 18, 61, 54, 25, 40, 15, 29, 34, 21, 62, 63, 42, 32, 20, 30, 41, 36, 22, 27, 37, 56};
        o = 2719582613777L;
        k = 0;
        C = false;
        m = false;
        I = 0;
        G = false;
        D = false;
        K = false;
        B = false;
        smartUnblockChance = new PercentageSetting("Smart-unblock-chance", 100);
        manualLeftClick = new BooleanSetting("Manual-left-click", false);
        requireRightClick = new BooleanSetting("Require-right-click", false);
        requireKillAura = new BooleanSetting("Require-KillAura", true);
        smartUnblock = new BooleanSetting("Smart-unblock", false);
        allowNoSlow = new BooleanSetting("Allow-NoSlow", true);
        onlyUnblockWithoutNoSlow = new BooleanSetting("Only-unblock-without-NoSlow", true);
        disableNoSlowInRange = new BooleanSetting("Disable-NoSlow-in-range", true);
        visualBlocking = new BooleanSetting("Visual-blocking", true);
        players = new BooleanSetting("Players", true);
        mobs = new BooleanSetting("Mobs", false);
        animals = new BooleanSetting("Animals", false);
        bosses = new BooleanSetting("Bosses", false);
        friends = new BooleanSetting("Friends", false);
        enemies = new BooleanSetting("Enemies", true);
        teammates = new BooleanSetting("Teammates", false);
        bots = new BooleanSetting("Bots", false);
        silverfishes = new BooleanSetting("Silverfishes", false);
        golems = new BooleanSetting("Golems", false);
        fov = new NumberSetting("FOV", 360.0f, 1.0f, 360.0f, 1.0f);
        targetRange = new NumberSetting("Target-range", 5.0f, 1.0f, 8.0f, 0.01f);
        smartUnblockTicks = new NumberSetting("Smart-unblock-ticks", 8.0f, 0.0f, 15.0f, 1.0f);
        noSlowDisableRange = new NumberSetting("NoSlow-disable-range", 3.5f, 0.0f, 8.0f, 0.01f);
        mode = new ModeSetting("Mode", "LAG_NEW", "LAG_NEW_PRE", "LAG", "LAG_PRE", "LAG_LEGIT", "LAG_LEGIT_PRE", "LEGIT", "VANILLA", "NONE");
        apsMode = new ModeSetting("APS-mode", "3APS", "5APS", "7APS", "10APS", "14APS");
        targetSettings = new HeaderSetting("Target settings");
}
}