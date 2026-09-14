/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.play.server.S02PacketChat
 *  net.minecraft.network.play.server.S08PacketPlayerPosLook
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.BlockPos$MutableBlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3i
 */
package Abyss;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AbyssClientBinder;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.internal.restore.AbyssNameMap;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.Modules;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.module.impl.configuration.VisualSpoof;
import Abyss.module.impl.visual.Freelook;
import Abyss.module.impl.world.BedNuker;
import Abyss.ui.abyss.AbyssArrayListVisibility;
import Abyss.ui.swing.ConfigManagerWindow;
import Abyss.util.ClientUtil;
import Abyss.util.DeferredRendererReload;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.PlayerInfoCache;
import Abyss.util.Sneaky;
import Abyss.util.TimerUtil;
import Abyss.util.debug.StallWatchdog;
import Abyss.util.packet.PacketManager;
import Abyss.util.render.abyss.FontManager;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

public class AbyssClient
implements EventSubscriber {
                private final TimerUtil B;
            private final Minecraft c;
        public static Map<Integer, String> H;
    private boolean s = false;
            public static Set<BlockPos> G;
        private boolean N = false;
    public static ConfigManagerWindow T;
    
    private static Map k;
    private final ScheduledExecutorService U;
    private final BlockPos.MutableBlockPos bedScanPos = new BlockPos.MutableBlockPos();
    private boolean bedScanActive;
    private int bedScanCursor;
    private int bedScanMinX;
    private int bedScanMinY;
    private int bedScanMinZ;
    private int bedScanSpanY;
    private int bedScanSpanZ;
    private int bedScanVolume;
    private static long[] i;
    public static String I;
    private static final byte[] KEY_OFFSETS;
    public static EventBus w;

    public void onEntityJoinWorld(long var1, EntityJoinWorldEvent var3) {
        if (var3.H instanceof EntityPlayerSP) {
            BedNuker.D.clear();
            BedNuker.B = false;
            this.bedScanActive = false;
}
}
    public void onReceivePacket(ReceivePacketEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var1.d instanceof S02PacketChat) {
            String var4 = ((S02PacketChat)var1.d).func_148915_c().func_150254_d();
            if (var4.contains("\u00a7e\u00a7lProtect your bed and destroy the enemy bed") || var4.contains("\u00a7e\u00a7lDestroy the enemy bed and then eliminate them")) {
                BedNuker.B = true;
}
        } else if (var1.d instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook var6 = (S08PacketPlayerPosLook)var1.d;
            if (BedNuker.B) {
                BedNuker.B = false;
                this.U.schedule(() -> this.c.func_152344_a(() -> {
                    try {
                        if (this.c.field_71441_e == null) {
                            return;
}
                        int var4x = MathHelper.func_76128_c((double)var6.func_148932_c());
                        int var5x = MathHelper.func_76128_c((double)var6.func_148928_d());
                        int var6x = MathHelper.func_76128_c((double)var6.func_148933_e());
                        this.bedScanMinX = var4x - 35;
                        this.bedScanMinY = var5x - 15;
                        this.bedScanMinZ = var6x - 35;
                        this.bedScanSpanY = 31;
                        this.bedScanSpanZ = 71;
                        this.bedScanVolume = 156271;
                        this.bedScanCursor = 0;
                        this.bedScanActive = true;
}
                    catch (Throwable throwable) {
                        // empty catch block
}
                }), 3000L, TimeUnit.MILLISECONDS);
}
}
}
    public void onPreUpdate(long var1, PreUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        List<Module> var8 = ModuleManager.S;
        int var10 = var8.size();
        for (int var9 = 0; var9 < var10; ++var9) {
            Module var11 = var8.get(var9);
            if (var11.b().equalsIgnoreCase("Timer")) continue;
            if (var11.l()) {
                var11.h(122596698849654L);
            } else if (var11.K()) {
                var11.Z(110240354022990L);
}
            if (var11.o()) continue;
            var11.d();
}
}
    private static long c(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x7DA9;
        if (j[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = i[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])k.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    k.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/AbyssClient", var14);
}
            long var15 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
            AbyssClient.j[var3] = var15;
}
        return j[var3];
}
    private static Field c(long var0, long var2) {
        int var4 = AbyssClient.a(var0, var2);
        Object var5 = l[var4];
        if (!(var5 instanceof String)) {
            return (Field)var5;
}
        String var6 = m[var4];
        int var7 = var6.indexOf(8);
        Class var8 = AbyssClient.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
        int var9 = var6.indexOf(8, ++var7);
        String var10 = var6.substring(var7, var9);
        Class var11 = AbyssClient.b(Long.parseLong(var6.substring(++var9), 36), 0L);
        Class var12 = var8;
        while (true) {
            Field var13;
            if ((var13 = AbyssClient.a(var12, var10, var11)) != null) {
                AbyssClient.l[var4] = var13;
                return var13;
}
            Class<?>[] var14 = var12.getInterfaces();
            if (var14 != null) {
                for (int var15 = 0; var15 < var14.length; ++var15) {
                    var13 = AbyssClient.b(var14[var15], var10, var11);
                    if (var13 == null) continue;
                    AbyssClient.l[var4] = var13;
                    return var13;
}
}
            if (var12.getName().equals("java.lang.Object")) {
                StringBuffer var19 = new StringBuffer();
                var19.append("NoSuchFieldException in ").append(var8.getName()).append(' ').append(var11.getName()).append(' ').append(var10);
                throw new RuntimeException(var19.toString());
}
            if ((var12 = var12.getSuperclass()) != null) continue;
            var12 = AbyssClient.b(525810144067084L, 0L);
}
}
    public void onSetKeyBindState(SetKeyBindStateEvent var1, long var2) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (ClientUtil.I()) {
            List<Module> var9 = ModuleManager.S;
            int var11 = var9.size();
            for (int var10 = 0; var10 < var11; ++var10) {
                Module var12 = var9.get(var10);
                try {
                    if (var12.b().equalsIgnoreCase("Timer") || var12.b().equalsIgnoreCase("ClickGUI") || var12.h() == 0 || !KeyBindUtil.d(var12.h(), var1.R, 55909487137472L) || var12.b().equalsIgnoreCase("FREELOOK") && !Freelook.mode.R("TOGGLE")) continue;
                    var12.u((short)0, 139350548161835L);
                    continue;
}
                catch (Throwable throwable) {
                    // empty catch block
}
}
            for (Map.Entry<Integer, String> var18 : H.entrySet()) {
                if (!KeyBindUtil.d(var18.getKey(), var1.R, 55909487137472L)) continue;
                for (String var15 : var18.getValue().split("\\n")) {
                    this.c.field_71439_g.func_71165_d(var15);
}
}
}
}
    public AbyssClient(int var1, char var2, int var3) {
        this.U = Executors.newScheduledThreadPool(1);
        this.c = MinecraftRef.c((byte)0, 0L);
        this.B = new TimerUtil();
}
    public void onPostTick(PostTickEvent var1, long var2) throws Throwable {
        StallWatchdog.tick(ClientUtil.I());
        int var16 = 22243;
        int var21 = 12652;
        if (!ClientUtil.I()) {
            BedNuker.B = false;
            PacketManager.M(false);
            PacketManager.u.clear();
            PacketManager.v.clear();
            PacketManager.a.clear();
            I = null;
            List<Module> var32 = ModuleManager.S;
            int var34 = var32.size();
            for (int var33 = 0; var33 < var34; ++var33) {
                Module var35 = var32.get(var33);
                if (var35.b().equalsIgnoreCase("Timer") || !var35.P()) continue;
                w.B(var35);
                var35.A(false);
}
            this.s = false;
        } else {
            List<Module> var26 = ModuleManager.S;
            int subscribesBudget = 3;
            boolean batching = false;
            int var28 = var26.size();
            for (int var27 = 0; var27 < var28; ++var27) {
                Module var29 = var26.get(var27);
                if (var29.b().equalsIgnoreCase("Timer")) continue;
                if (var29.l()) {
                    var29.i(17998201765264L);
                    var29.n(false);
                } else if (var29.K()) {
                    var29.A(94287625739397L);
                    var29.E(false);
}
                if (var29.o()) {
                    if (var29.P() || subscribesBudget <= 0) continue;
                    if (!batching) {
                        w.beginBatch();
                        batching = true;
}
                    w.s(var29, 25046058167973L);
                    var29.A(true);
                    --subscribesBudget;
                    continue;
}
                if (var29.P()) {
                    w.B(var29);
                    var29.A(false);
}
                var29.P(11128156246666L);
}
            if (batching) {
                w.endBatch();
}
            if (this.c.field_71462_r == null) {
                if (ClickGUI.x(17550, (short)6998, (char)var16)) {
                    try {
                        ClickGUI.O(2169, 8663, (char)var21);
}
                    catch (NullPointerException nullPointerException) {
                        // empty catch block
}
}
                if (Freelook.mode.R("HOLD") && Modules.J(Freelook.class).h() != 0) {
                    Modules.J(Freelook.class).I(20724619369162L, KeyBindUtil.V(Modules.J(Freelook.class).h(), 64165991731362L));
}
}
            if (VisualSpoof.n(118536638251483L) && !this.s) {
                VisualSpoof.t.v(!VisualSpoof.t.c(), 64895789836511L);
}
            this.s = VisualSpoof.n(118536638251483L);
}
        FontManager.warmStep();
        AbyssArrayListVisibility.preload();
        PlayerInfoCache.refresh();
        Modules.flushPendingSave();
        DeferredRendererReload.flush();
        this.pumpBedScan();
}
    private void pumpBedScan() {
        if (!this.bedScanActive) {
            return;
}
        try {
            if (this.c.field_71441_e == null) {
                this.bedScanActive = false;
                return;
}
            long deadline = System.nanoTime() + 2000000L;
            int layerYZ = this.bedScanSpanY * this.bedScanSpanZ;
            while (this.bedScanCursor < this.bedScanVolume) {
                int idx = this.bedScanCursor;
                int dx = idx / layerYZ;
                int rem = idx % layerYZ;
                int dy = rem / this.bedScanSpanZ;
                int dz = rem % this.bedScanSpanZ;
                this.bedScanPos.func_181079_c(this.bedScanMinX + dx, this.bedScanMinY + dy, this.bedScanMinZ + dz);
                if (this.c.field_71441_e.func_180495_p((BlockPos)this.bedScanPos).func_177230_c() == Blocks.field_150324_C) {
                    BedNuker.D.add(new BlockPos((Vec3i)this.bedScanPos));
}
                ++this.bedScanCursor;
                if ((this.bedScanCursor & 0x1FFF) != 0 || System.nanoTime() < deadline) continue;
                return;
}
            this.bedScanActive = false;
}
        catch (Throwable ignored) {
            this.bedScanActive = false;
}
}
    public void d(long var1, PreUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (I != null && this.B.L(300L, true)) {
            if (this.N) {
                this.N = false;
                this.c.field_71439_g.func_71165_d("/p " + I);
            } else {
                this.N = true;
                this.c.field_71439_g.func_71165_d("/p leave");
}
}
}
    public void onPreMouseInput(long var1, PreMouseInputEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        List<Module> var6 = ModuleManager.S;
        int var8 = var6.size();
        for (int var7 = 0; var7 < var8; ++var7) {
            Module var9 = var6.get(var7);
            if (var9.b().equalsIgnoreCase("Timer") || var9.o()) continue;
            var9.L(var3, 85029904657643L);
}
}
    private static int b(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x325D;
        if (g[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = f[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])h.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    h.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/AbyssClient", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            AbyssClient.g[var3] = var15;
}
        return g[var3];
}
    private static Class b(long var0, long var2) {
        Class<?> var5 = null;
        int var4 = AbyssClient.a(var0, var2);
        Object var6 = l[var4];
        try {
            if (var6 instanceof String) {
                AbyssClient.l[var4] = var5 = Class.forName(AbyssNameMap.map(m[var4]));
                return var5;
}
}
        catch (Exception var8) {
            throw new RuntimeException(var8.toString());
}
        return (Class)var6;
}
    @Override
    public final void x(long var1, EventBus var3) {
        AbyssClientBinder.C(var3, this);
}
    private static Method b(Class var0, String var1, Class var2, int var3, Class[] var4) {
        Method var5 = AbyssClient.a(var0, var1, var2, var3, var4);
        if (var5 != null) {
            return var5;
}
        Class<?>[] var6 = var0.getInterfaces();
        if (var6 != null) {
            for (int var7 = 0; var7 < var6.length; ++var7) {
                var5 = AbyssClient.b(var6[var7], var1, var2, var3, var4);
                if (var5 == null) continue;
                return var5;
}
}
        return null;
}
    private static Method d(long var0, long var2) {
        Class var23;
        Class var15;
        Class[] var14;
        int var13;
        String var10;
        Class var8;
        block10: {
            int var4 = AbyssClient.a(var0, var2);
            Object var5 = l[var4];
            if (!(var5 instanceof String)) {
                return (Method)var5;
}
            String var6 = m[var4];
            int var7 = var6.indexOf(8);
            var8 = AbyssClient.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
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
                var15 = AbyssClient.b(Long.parseLong(var6.substring(var12, var17), 36), 0L);
                if (var16 >= var13) continue;
                var14[var16] = var15;
}
            var23 = var8;
            do {
                Method var26;
                if ((var26 = AbyssClient.a(var23, var10, var15, var13, var14)) != null) {
                    AbyssClient.l[var4] = var26;
                    return var26;
}
                if (var23.getName().equals("java.lang.Object")) break block10;
            } while ((var23 = var23.getSuperclass()) != null);
            var23 = AbyssClient.b(525810144067084L, 0L);
}
        var23 = var8;
        while (true) {
            Class<?>[] var27;
            if ((var27 = var23.getInterfaces()) != null) {
                for (int var18 = 0; var18 < var27.length; ++var18) {
                    Method var19 = AbyssClient.b(var27[var18], var10, var15, var13, var14);
                    if (var19 == null) continue;
                    AbyssClient.l[var4] = var19;
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
            var23 = AbyssClient.b(525810144067084L, 0L);
}
}
    private static Field b(Class var0, String var1, Class var2) {
        Field var3 = AbyssClient.a(var0, var1, var2);
        if (var3 != null) {
            return var3;
}
        Class<?>[] var4 = var0.getInterfaces();
        if (var4 != null) {
            for (int var5 = 0; var5 < var4.length; ++var5) {
                var3 = AbyssClient.b(var4[var5], var1, var2);
                if (var3 == null) continue;
                return var3;
}
}
        return null;
}
    private static boolean zkm$unresolved$0$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_iD_l_OR_Abyss_iD_K_y_slots_39_49_66_70(Object var0, long var3) {
        try {
            MethodType var5 = MethodType.fromMethodDescriptorString("(Ljava/lang/Object;JJ)Z", AbyssClient.class.getClassLoader());
            return MethodHandles.explicitCastArguments(AbyssClient.a(MethodHandles.lookup(), null, "\u00f4", var5, 2266045794134596627L, 9901644652386L), var5).invoke(var0, 2266045794134596627L, 9901644652386L);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
                Cipher var24 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var24.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var31 = new String[10];
            int var29 = 0;
            String var28 = " !\u00f0\u00fc$\u00ec\u00e2\u00b4\u0097\u00bf//\u00fa\u0089sK \u00df\u00f0\u00c02\u0019\u00ae\u00d8\u0006\u0080\rn0\u00f0 &H\u00fd$\u0097\r\u0019\u00fb\u009d\u001f\u00b0p\u00c1\u00e3\u00cdh\u00af\u00e5\u00107\u0003m\u00c0\u00ce^\u00b3\u001b\u00ea`\u00d6\u000e\u001b\b\u00ae\u00f3PMv\u008d\u00b0&:\u00d0\u00a4\u0087\u00bf}\u00cf.\u00c5\u00fc/\u00dd3/\n6M\u00cb\u0015\u00a5\u00cf\f\u00f0\u00ae=a\u008f\u00cb\u00aa\u00a4Z^.QD\u00d9\u00e1!\u0017\u00b0\u0016\u0080\u00d0\u00a0\u00fb\u00d0X\nq\u00bchi\u00fb\u0003\u00a9\u00de/\non\u008c\u0011?\u00ae+#C\u00f7D\u0015\u008bW\u00ae\u0087?\u0010\u0093\u00a7/\u00f2\u00ec\u00be\u00b9\u00045\u00e2@5\u00c9\u0012\u00f5\u00bc\u0010\u00e6\u00e08%\u009b\u00e3U,h\u00ac%\u0083\u0099Z\u0088\u00a5\u0010\u00f1\u00ac\u00b5\u0085\u00f6\u008b\u000b\u00f0\u00e7_\u00ba\u0081\u00db\u00b3\u008f8X/\u00c6u\u0087#5\u0000h7d\u00e7\u00f6\u00b0\u00c8\u00de\u00c4\u00fce\u00e9\u0018\u00a8\u00b9\u00cf\u0083\u00cdP\u00f2)\b\u0016&\u001f\u00e91\u00c2\u00e0\u00db\u00a2/\u00b2;G\u0088;\u0011\u0000\u0099\u00a0\u00e4\b3\u00fbw\u00e0\u00d7\u00be#\u00e6\u0016\u008b\u001a3\u0015!.\\@\u00c5\u00d7\u00de$\u00e9\u00b3\u00b1\u0094xE\u000bUj\u00d2\u001a\u000e\u00b6q2K\u00cf";
            int var30 = " !\u00f0\u00fc$\u00ec\u00e2\u00b4\u0097\u00bf//\u00fa\u0089sK \u00df\u00f0\u00c02\u0019\u00ae\u00d8\u0006\u0080\rn0\u00f0 &H\u00fd$\u0097\r\u0019\u00fb\u009d\u001f\u00b0p\u00c1\u00e3\u00cdh\u00af\u00e5\u00107\u0003m\u00c0\u00ce^\u00b3\u001b\u00ea`\u00d6\u000e\u001b\b\u00ae\u00f3PMv\u008d\u00b0&:\u00d0\u00a4\u0087\u00bf}\u00cf.\u00c5\u00fc/\u00dd3/\n6M\u00cb\u0015\u00a5\u00cf\f\u00f0\u00ae=a\u008f\u00cb\u00aa\u00a4Z^.QD\u00d9\u00e1!\u0017\u00b0\u0016\u0080\u00d0\u00a0\u00fb\u00d0X\nq\u00bchi\u00fb\u0003\u00a9\u00de/\non\u008c\u0011?\u00ae+#C\u00f7D\u0015\u008bW\u00ae\u0087?\u0010\u0093\u00a7/\u00f2\u00ec\u00be\u00b9\u00045\u00e2@5\u00c9\u0012\u00f5\u00bc\u0010\u00e6\u00e08%\u009b\u00e3U,h\u00ac%\u0083\u0099Z\u0088\u00a5\u0010\u00f1\u00ac\u00b5\u0085\u00f6\u008b\u000b\u00f0\u00e7_\u00ba\u0081\u00db\u00b3\u008f8X/\u00c6u\u0087#5\u0000h7d\u00e7\u00f6\u00b0\u00c8\u00de\u00c4\u00fce\u00e9\u0018\u00a8\u00b9\u00cf\u0083\u00cdP\u00f2)\b\u0016&\u001f\u00e91\u00c2\u00e0\u00db\u00a2/\u00b2;G\u0088;\u0011\u0000\u0099\u00a0\u00e4\b3\u00fbw\u00e0\u00d7\u00be#\u00e6\u0016\u008b\u001a3\u0015!.\\@\u00c5\u00d7\u00de$\u00e9\u00b3\u00b1\u0094xE\u000bUj\u00d2\u001a\u000e\u00b6q2K\u00cf".length();
            int var27 = 16;
            int var36 = -1;
            block9: while (true) {
                String var37 = var28.substring(++var36, var36 + var27);
                int var10001 = -1;
                while (true) {
                    byte[] var32 = var24.doFinal(var37.getBytes("ISO-8859-1"));
                    String var51 = AbyssClient.a(var32).intern();
                    switch (var10001) {
                        case 0: {
                            var31[var29++] = var51;
                            if ((var36 += var27) >= var30) {
                                b = var31;
                                d = new String[10];
                                h = new HashMap(13);
                                var10003 = new byte[]{(byte)(var22 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var12 = 1; var12 < 8; ++var12) {
                                    var10003[var12] = (byte)(var22 << var12 * 8 >>> 56);
}
                                Cipher var11 = Cipher.getInstance("DES/CBC/NoPadding");
                                var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var17 = new long[8];
                                int var14 = 0;
                                String var15 = "\u00dc\u00f5\u00cd\u009bzg\u00b0FA\u00d6\u00c0\u00d7\u00a1\u00dc\u00f3\u00f5\u008a\u001a\u00d0\u00a5\u008b\u00db<H\u00d3\u00cf\u00b9>\u00ca\u00c8\u00ad\u00b5\u0002\u00d4\u0001\u00cf\u00c4\bu\u0005+\u001b\u00fd\u0092\u00ac\u001b_\u00fb";
                                int var16 = "\u00dc\u00f5\u00cd\u009bzg\u00b0FA\u00d6\u00c0\u00d7\u00a1\u00dc\u00f3\u00f5\u008a\u001a\u00d0\u00a5\u008b\u00db<H\u00d3\u00cf\u00b9>\u00ca\u00c8\u00ad\u00b5\u0002\u00d4\u0001\u00cf\u00c4\bu\u0005+\u001b\u00fd\u0092\u00ac\u001b_\u00fb".length();
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
                                                f = var17;
                                                g = new Integer[8];
                                                k = new HashMap(13);
                                                var10003 = new byte[]{(byte)(var22 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                                for (int var1 = 1; var1 < 8; ++var1) {
                                                    var10003[var1] = (byte)(var22 << var1 * 8 >>> 56);
}
                                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                                long[] var6 = new long[2];
                                                int var3 = 0;
                                                String var4 = "\u0089\u009ej\u00db\u00e9P\u00b4~\u00cb\u000e\u00cbgZ\u00b1m\u007f";
                                                int var5 = "\u0089\u009ej\u00db\u00e9P\u00b4~\u00cb\u000e\u00cbgZ\u00b1m\u007f".length();
                                                int var2 = 0;
                                                do {
                                                    int var48 = var2;
                                                    byte[] var7 = var4.substring(var48, var2 += 8).getBytes("ISO-8859-1");
                                                    var48 = var3++;
                                                    long var8 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                                    byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                                    var6[var48] = var63 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                                } while (var2 < var5);
                                                i = var6;
                                                j = new Long[2];
                                                return;
}
                                            default: {
                                                var40[var10001] = var63;
                                                if (var13 < var16) continue block12;
                                                var15 = "&\u000b\r\u00a1(k\u009cJw \ba\u00b7\u00f7\u00c5k";
                                                var16 = "&\u000b\r\u00a1(k\u009cJw \ba\u00b7\u00f7\u00c5k".length();
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
                                    break;
}
}
                            var27 = var28.charAt(var36);
                            break;
}
                        default: {
                            var31[var29++] = var51;
                            if ((var36 += var27) < var30) {
                                var27 = var28.charAt(var36);
                                continue block9;
}
                            var28 = "\u0018\u0086\u00db\u0099G\u008b!\u00fe/\u0015\u00e2\u0000\u0010\u00b7\u00bb\u0080\u00f0\u00d3\u009d\u00d3\u0000\u009b(\t.\u00c3\u0087\u00c8Jbv\u00e0\u0010a\u00cc\u009e\u00fa\u00d7b\u00de\u00d5\u00fc\u00fb\u0084\u00a0c3\u0007\u0013";
                            var30 = "\u0018\u0086\u00db\u0099G\u008b!\u00fe/\u0015\u00e2\u0000\u0010\u00b7\u00bb\u0080\u00f0\u00d3\u009d\u00d3\u0000\u009b(\t.\u00c3\u0087\u00c8Jbv\u00e0\u0010a\u00cc\u009e\u00fa\u00d7b\u00de\u00d5\u00fc\u00fb\u0084\u00a0c3\u0007\u0013".length();
                            var27 = 32;
                            var36 = -1;
}
}
                    var37 = var28.substring(++var36, var36 + var27);
                    var10001 = 0;
}
                break;
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var33) {
            throw new RuntimeException(var33);
}
}
    static {
        KEY_OFFSETS = new byte[]{39, 57, 59, 32, 29, 12, 48, 9, 40, 35, 20, 47, 44, 1, 25, 42, 11, 5, 28, 36, 41, 27, 14, 60, 2, 45, 52, 31, 23, 38, 62, 33, 24, 17, 15, 0, 37, 8, 46, 53, 61, 21, 30, 6, 16, 49, 51, 3, 55, 18, 50, 34, 63, 22, 10, 58, 56, 26, 54, 19, 4, 13, 43, 7};
        H = new LinkedHashMap<Integer, String>();
        G = new CopyOnWriteArraySet<BlockPos>();
        I = null;
}
}