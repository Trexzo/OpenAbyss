/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.server.S0BPacketAnimation
 *  net.minecraft.network.play.server.S14PacketEntity
 *  net.minecraft.network.play.server.S18PacketEntityTeleport
 *  net.minecraft.network.play.server.S22PacketMultiBlockChange
 *  net.minecraft.network.play.server.S22PacketMultiBlockChange$BlockUpdateData
 *  net.minecraft.network.play.server.S23PacketBlockChange
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.world.World
 */
package Abyss.internal;

import Abyss.enums.DetectedAction;
import Abyss.enums.DetectedCheat;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.CheaterDetectorBinder;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.GetDisplayNameEvent;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.internal.CheaterDetectionSample;
import Abyss.module.Module;
import Abyss.util.CheaterRegistry;
import Abyss.util.ClientUtil;
import Abyss.util.MathUtil;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CheaterDetector
extends Module
implements EventSubscriber {
    private static long private static Map o;
        private long J;
    private static Integer[] h;
    public static Map<UUID, CheaterRegistry> R;
    public static Map<UUID, EntityPlayer> c;
        private static long[] g;
    private static long[] m;
        private static Object[] p;
    private static String[] r;
    private static Map k;

    public void onPostTick(long var1, PostTickEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var13 = CheaterDetector.f.field_71441_e.field_73010_i.size();
        for (int var12 = 0; var12 < var13; ++var12) {
            EntityPlayer var14 = (EntityPlayer)CheaterDetector.f.field_71441_e.field_73010_i.get(var12);
            UUID var15 = var14.func_146103_bH().getId();
            c.putIfAbsent(var15, var14);
            if (var14 instanceof EntityPlayerSP) continue;
            CheaterRegistry var16 = R.computeIfAbsent(var15, var0 -> {
                long var3x = 139461843194438L;
                return new CheaterRegistry(var3x);
            });
            this.q(var14, var16, 130408358797926L);
            this.V(var14, 91960239010806L, var16);
            this.c(var14, var16);
}
        for (Map.Entry<UUID, CheaterRegistry> var21 : R.entrySet()) {
            EntityPlayer var22 = c.get(var21.getKey());
            if (var22 != null) {
                for (DetectedCheat var18 : DetectedCheat.values()) {
                    if (var21.getValue().g(var18) < var18.FLAG_VL) continue;
                    var21.getValue().C(118087748409822L, var18);
                    ClientUtil.t(48081174263320L, var22.func_145748_c_().func_150254_d() + "\u00a7r flagged " + var18.colorFormatCode + var18.name());
                    var21.getValue().D(var18, -9999);
}
}
            var21.getValue().d.clear();
            var21.getValue().d.putAll(var21.getValue().c);
}
}
    private boolean K(short var1, int var2, List var3, char var4, long var5, long var7) {
        float[] var10000 = new float[]{30.0f, 35.0f, 45.0f, 90.0f, 135.0f, 180.0f};
        float[] var11 = var10000;
        int[] var12 = new int[var11.length];
        float[] var13 = new float[var11.length];
        float[] var14 = new float[var11.length];
        block0: for (CheaterDetectionSample var16 : var3) {
            if (CheaterDetectionSample.u(var16) < var5) continue;
            if (CheaterDetectionSample.u(var16) > var7) break;
            if (CheaterDetectionSample.h(var16) != DetectedAction.ROTATION || CheaterDetectionSample.R(var16) < 24.0f) continue;
            for (int var17 = 0; var17 < var11.length; ++var17) {
                float var18;
                float f = var18 = var11[var17] >= 90.0f ? 8.0f : 6.0f;
                if (!this.N(CheaterDetectionSample.R(var16), var11[var17], var18)) continue;
                int n2 = var17;
                var12[n2] = var12[n2] + 1;
                int n3 = var17;
                var13[n3] = var13[n3] + CheaterDetectionSample.R(var16);
                int n4 = var17;
                var14[n4] = var14[n4] + CheaterDetectionSample.R(var16) * CheaterDetectionSample.R(var16);
                continue block0;
}
}
        for (int var19 = 0; var19 < var11.length; ++var19) {
            float var20;
            float var21;
            if (var12[var19] < 2 || !((var21 = var14[var19] / (float)var12[var19] - (var20 = var13[var19] / (float)var12[var19]) * var20) <= 12.0f)) continue;
            return true;
}
        return false;
}
    private void E(byte var1, int var2, int var3, S14PacketEntity var4) {
        Entity var9;
        long var5 = ((long)var1 << 56 | (long)var2 << 32 >>> 8 | (long)var3 << 40 >>> 40) ^ a;
        long var7 = var5 ^ 0x6FDBE17B3C7DL;
        if (var4.func_149060_h() && CheaterDetector.f.field_71441_e != null && (var9 = var4.func_149065_a((World)CheaterDetector.f.field_71441_e)) instanceof EntityPlayer && var9 != CheaterDetector.f.field_71439_g) {
            double var10 = Math.hypot(var4.func_149062_c(), var4.func_149064_e()) / 32.0;
            this.K((EntityPlayer)var9, this.W(var4.func_149066_f()), this.W(var4.func_149063_g()), var4.func_179742_g(), var7, var10);
}
}
    private boolean B(List var1, long var2, int var4) {
        var2 = a ^ var2;
        int var5 = Math.min(var1.size(), var4 + CheaterDetector.d(25319, 0x1152E23A0AB937FFL ^ var2));
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        for (int var9 = var4; var9 < var5; ++var9) {
            float var10 = this.Z(var1, var9);
            if (this.N(var10, 90.0f, 8.0f)) {
                ++var6;
}
            if (var10 >= 28.0f && var10 <= 40.0f) {
                ++var7;
}
            if (!this.N(var10, 135.0f, 10.0f) && !this.N(var10, 180.0f, 8.0f)) continue;
            ++var8;
}
        return var6 >= 2 || var6 >= 1 && var7 >= 2 || var8 >= 1;
}
    private boolean w(long var1, List var3, int var4) {
        var1 = a ^ var1;
        int var5 = Math.min(var3.size(), var4 + CheaterDetector.d(27240, 0x484D7156DA84096CL ^ var1));
        int var6 = 0;
        for (int var7 = var4; var7 < var5; ++var7) {
            if (!CheaterDetectionSample.N((CheaterDetectionSample)var3.get(var7)) || !this.d(var3, var7, 2)) continue;
            ++var6;
}
        return var6 >= 1;
}
    @Override
    public void A(long var1) {
        c.clear();
}
    private void P(long var1, S18PacketEntityTeleport var3) {
        Entity var6;
        if (CheaterDetector.f.field_71441_e != null && (var6 = CheaterDetector.f.field_71441_e.func_73045_a(var3.func_149451_c())) instanceof EntityPlayer && var6 != CheaterDetector.f.field_71439_g) {
            this.K((EntityPlayer)var6, this.W(var3.func_149450_g()), this.W(var3.func_149447_h()), var3.func_179697_g(), 126069368889640L, 0.0);
}
}
    public CheaterDetector(int var1, char var2, int var3) {
        super(((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a ^ 0x1DDA9C27FA77L);
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a;
        this.J = 0L;
}
    private boolean e(List<CheaterDetectionSample> var1, int var2) {
        CheaterDetectionSample var4;
        long var5;
        for (int var3 = var1.size() - 1; var3 >= 0 && (var5 = this.J - CheaterDetectionSample.u(var4 = var1.get(var3))) <= (long)var2; --var3) {
            if (CheaterDetectionSample.h(var4) != DetectedAction.SWING) continue;
            return true;
}
        return false;
}
    public void onPlayerGetName(int var1, PlayerGetNameEvent var2, short var3, int var4) {
        CheaterRegistry var7 = R.get(var2.u.func_178845_a().getId());
        if (var7 != null && var7.M()) {
            var2.d("\u00a7b\u00a7l\u26a0\u00a7r ");
}
}
    private void o(short var1, BlockPos var2, int var3, Block var4, short var5) {
        EntityPlayer var12;
        Block var11;
        long var6 = ((long)var1 << 48 | (long)var3 << 32 >>> 16 | (long)var5 << 48 >>> 48) ^ a;
        int var8 = (int)((var6 ^ 0x2E467092DF0DL) >>> 48);
        if (CheaterDetector.f.field_71441_e != null && var4 != Blocks.field_150350_a && ((var11 = CheaterDetector.f.field_71441_e.func_180495_p(var2).func_177230_c()) == Blocks.field_150350_a || var11.func_149688_o().func_76222_j()) && (var12 = this.Q(var2, var4, 3.35)) != null) {
            CheaterRegistry var13 = R.computeIfAbsent(var12.func_146103_bH().getId(), var0 -> {
                long var3x = 139461843194438L;
                return new CheaterRegistry(var3x);
            });
            if (this.A(var13.G, 18)) {
                this.L((short)var8, var13, new CheaterDetectionSample(DetectedAction.PLACE, this.J, 0.0f, 0.0f, Math.abs(var12.field_70125_A), false, var12.field_70122_E, 0.0));
}
}
}
    private void B(long var1, List var3) {
        long var4 = this.J - 120L;
        while (!var3.isEmpty() && CheaterDetectionSample.u((CheaterDetectionSample)var3.get(0)) < var4) {
            var3.remove(0);
}
        if (var3.size() > 40) {
            var3.subList(0, var3.size() - 40).clear();
}
}
    private boolean Z(long var1, List var3, int var4, DetectedAction var5, int var6) {
        CheaterDetectionSample var10;
        var1 = a ^ var1;
        long var7 = CheaterDetectionSample.u((CheaterDetectionSample)var3.get(var4));
        for (int var9 = var4; var9 < var3.size() && CheaterDetectionSample.u(var10 = (CheaterDetectionSample)var3.get(var9)) - var7 <= 24L; ++var9) {
            CheaterDetectionSample var12;
            if (CheaterDetectionSample.h(var10) != var5) continue;
            for (int var11 = var4; var11 < var3.size() && CheaterDetectionSample.u(var12 = (CheaterDetectionSample)var3.get(var11)) - var7 <= 24L; ++var11) {
                if (CheaterDetectionSample.h(var12) != DetectedAction.ROTATION || Math.abs(CheaterDetectionSample.u(var12) - CheaterDetectionSample.u(var10)) > (long)var6 || !(CheaterDetectionSample.R(var12) >= 28.0f) && !(CheaterDetectionSample.v(var12) >= 55.0f)) continue;
                return true;
}
}
        return false;
}
    private boolean T(List var1, long var2, int var4) {
        var2 = a ^ var2;
        int var5 = Math.min(var1.size(), var4 + CheaterDetector.d(27590, 0x10FAD9D258BFC3CDL ^ var2));
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;
        int var9 = 0;
        int var10 = 0;
        int var11 = 0;
        int var12 = 0;
        int var13 = 0;
        for (int var14 = var4; var14 < var5; ++var14) {
            float var15 = this.Z(var1, var14);
            CheaterDetectionSample var16 = (CheaterDetectionSample)var1.get(var14);
            float var17 = CheaterDetectionSample.g(var16);
            boolean var18 = CheaterDetectionSample.N(var16);
            if (!CheaterDetectionSample.z(var16)) {
                ++var12;
}
            if (CheaterDetectionSample.E(var16) > 0.05) {
                ++var13;
}
            if (CheaterDetectionSample.v(var16) >= 55.0f) {
                ++var11;
}
            if (var15 <= 10.0f) {
                if (!var18 || !this.d(var1, var14, 3)) continue;
                var8 = true;
                continue;
}
            if (!var6) {
                if (!(var15 >= 75.0f) && !this.N(var15, 90.0f, 8.0f)) continue;
                var6 = true;
                if (!(var15 >= 110.0f) && !this.N(var15, 135.0f, 10.0f) && !this.N(var15, 180.0f, 8.0f)) continue;
                var7 = true;
                continue;
}
            if (var15 >= 70.0f && var15 <= 135.0f || this.N(var15, 180.0f, 8.0f)) {
                var7 = true;
}
            if (var15 >= 24.0f && var15 <= 42.0f) {
                ++var9;
}
            if (var15 > 10.0f && var15 < 24.0f) {
                ++var9;
}
            if (var18) {
                ++var10;
                if (this.d(var1, var14, 2)) {
                    var8 = true;
}
}
            if (!(var17 >= 20.0f)) continue;
            ++var11;
}
        return !(!var6 || var13 < 4 || !var7 && var9 < 2 && var12 < 2 || !var8 && var10 < 2 || var11 < 2);
}
    public void onReceivePacket(ReceivePacketEvent var1, long var2) {
        int var6 = 6780733;
        int var11 = 53536;
        ++this.J;
        if (var1.d instanceof S14PacketEntity) {
            this.E((byte)0, 555061, var6, (S14PacketEntity)var1.d);
        } else if (var1.d instanceof S18PacketEntityTeleport) {
            this.P(94234514260642L, (S18PacketEntityTeleport)var1.d);
        } else if (var1.d instanceof S0BPacketAnimation) {
            this.V((S0BPacketAnimation)var1.d, 112917762571575L);
        } else if (var1.d instanceof S23PacketBlockChange) {
            S23PacketBlockChange var14 = (S23PacketBlockChange)var1.d;
            this.o((short)0, var14.func_179827_b(), 732582211, var14.func_180728_a().func_177230_c(), (short)var11);
        } else if (var1.d instanceof S22PacketMultiBlockChange) {
            for (S22PacketMultiBlockChange.BlockUpdateData var17 : ((S22PacketMultiBlockChange)var1.d).func_179844_a()) {
                this.o((short)0, var17.func_180090_a(), 732582211, var17.func_180088_c().func_177230_c(), (short)var11);
}
}
}
    private boolean S(List var1, long var2, long var4, long var6) {
        int var8 = 0;
        for (CheaterDetectionSample var10 : var1) {
            if (CheaterDetectionSample.u(var10) < var2) continue;
            if (CheaterDetectionSample.u(var10) > var4) break;
            if (CheaterDetectionSample.h(var10) != DetectedAction.PLACE) continue;
            boolean var11 = false;
            boolean var12 = false;
            for (CheaterDetectionSample var14 : var1) {
                if (CheaterDetectionSample.u(var14) < CheaterDetectionSample.u(var10) - 8L) continue;
                if (CheaterDetectionSample.u(var14) > CheaterDetectionSample.u(var10) + 8L) break;
                if (CheaterDetectionSample.h(var14) == DetectedAction.SWING) {
                    var11 = true;
                    continue;
}
                if (CheaterDetectionSample.h(var14) != DetectedAction.ROTATION || !(CheaterDetectionSample.E(var14) > 0.015) || !(CheaterDetectionSample.R(var14) >= 24.0f) && !(CheaterDetectionSample.v(var14) >= 70.0f) && !(CheaterDetectionSample.g(var14) >= 18.0f)) continue;
                var12 = true;
}
            if (!var11 || !var12) continue;
            ++var8;
}
        return var8 >= 2;
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
    private void L(short var1, CheaterRegistry var4, CheaterDetectionSample var5) {
        var4.G.add(var5);
        if (var4.G.size() > 100) {
            var4.G.remove(0);
}
}
    private static int d(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x2EF2;
        if (h[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = g[var3];
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
                throw new RuntimeException("Abyss/internal/CheaterDetector", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            CheaterDetector.h[var3] = var15;
}
        return h[var3];
}
    private void K(EntityPlayer var1, float var2, float var3, boolean var4, long var5, double var7) {
        var5 = a ^ var5;
        int var9 = (int)((var5 ^ 0x7744EE222F05L) >>> 48);
        CheaterRegistry var12 = R.computeIfAbsent(var1.func_146103_bH().getId(), var0 -> {
            long var3x = 139461843194438L;
            return new CheaterRegistry(var3x);
        });
        float var13 = var12.j ? Math.abs(MathUtil.M(var12.p, var2)) : 0.0f;
        float var14 = var12.j ? Math.abs(MathUtil.M(var12.J, var3)) : 0.0f;
        var12.j = true;
        var12.p = var2;
        var12.J = var3;
        this.L((short)var9, var12, new CheaterDetectionSample(DetectedAction.ROTATION, this.J, var13, var14, Math.abs(var3), false, var4, var7));
}
    private boolean v(long var1, List var3, int var4) {
        CheaterDetectionSample var16;
        var1 = a ^ var1;
        long var5 = CheaterDetectionSample.u((CheaterDetectionSample)var3.get(var4));
        int var7 = 0;
        int var8 = 0;
        int var9 = 0;
        int var10 = 0;
        int var11 = 0;
        int var12 = 0;
        int var13 = 0;
        int var14 = 0;
        for (int var15 = var4; var15 < var3.size() && CheaterDetectionSample.u(var16 = (CheaterDetectionSample)var3.get(var15)) - var5 <= 24L; ++var15) {
            if (CheaterDetectionSample.h(var16) == DetectedAction.PLACE) {
                ++var10;
                continue;
}
            if (CheaterDetectionSample.h(var16) == DetectedAction.SWING) {
                ++var11;
                continue;
}
            if (CheaterDetectionSample.h(var16) != DetectedAction.ROTATION) continue;
            ++var7;
            if (!CheaterDetectionSample.z(var16)) {
                ++var12;
}
            if (CheaterDetectionSample.E(var16) > 0.015) {
                ++var14;
}
            if (CheaterDetectionSample.v(var16) >= 55.0f || CheaterDetectionSample.g(var16) >= 18.0f) {
                ++var13;
}
            if (CheaterDetectionSample.R(var16) >= 75.0f || this.N(CheaterDetectionSample.R(var16), 90.0f, 8.0f) || this.N(CheaterDetectionSample.R(var16), 135.0f, 10.0f) || this.N(CheaterDetectionSample.R(var16), 180.0f, 8.0f)) {
                ++var8;
}
            if (!(CheaterDetectionSample.R(var16) >= 24.0f) || !(CheaterDetectionSample.R(var16) <= 42.0f)) continue;
            ++var9;
}
        return !(var7 < 2 || var10 < 1 || var11 < 1 || var13 < 1 || var14 < 1 || var8 < 1 && var9 < 2 || var12 < 1 && var9 < 2);
}
    public void onGetDisplayName(GetDisplayNameEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        CheaterRegistry var4 = R.get(var1.u.func_146103_bH().getId());
        if (var4 != null && var4.M()) {
            var1.Q("\u00a7b\u00a7l\u26a0\u00a7r ");
}
}
    public void onWorldLoad(WorldLoadEvent var1) {
        c.clear();
        this.J = 0L;
}
    private boolean A(List<CheaterDetectionSample> var1, int var2) {
        CheaterDetectionSample var4;
        long var5;
        for (int var3 = var1.size() - 1; var3 >= 0 && (var5 = this.J - CheaterDetectionSample.u(var4 = var1.get(var3))) <= (long)var2; --var3) {
            if (CheaterDetectionSample.h(var4) != DetectedAction.ROTATION || !(CheaterDetectionSample.E(var4) > 0.015) || !(CheaterDetectionSample.v(var4) >= 55.0f) && !(CheaterDetectionSample.g(var4) >= 18.0f) || !(CheaterDetectionSample.R(var4) >= 24.0f) && !(CheaterDetectionSample.v(var4) >= 70.0f)) {
                continue;
}
            return true;
}
        return false;
}
    private int t(List<CheaterDetectionSample> var1, int var2, long var3) {
        while (var2 + 1 < var1.size() && CheaterDetectionSample.u(var1.get(var2 + 1)) <= var3) {
            ++var2;
}
        return var2;
}
    private float Z(List<CheaterDetectionSample> var1, int var2) {
        return CheaterDetectionSample.R(var1.get(var2));
}
    private boolean d(List<CheaterDetectionSample> var1, int var2, int var3) {
        int var4 = Math.max(0, var2 - var3);
        int var5 = Math.min(var1.size() - 1, var2 + var3);
        for (int var6 = var4; var6 <= var5; ++var6) {
            float var7 = this.Z(var1, var6);
            CheaterDetectionSample var8 = var1.get(var6);
            if (!(var7 >= 28.0f) || !(CheaterDetectionSample.g(var8) >= 8.0f) && !(CheaterDetectionSample.v(var8) >= 55.0f)) continue;
            return true;
}
        return false;
}
    private void V(S0BPacketAnimation var1, long var2) {
        Entity var7;
        if (CheaterDetector.f.field_71441_e != null && var1.func_148977_d() == 0 && (var7 = CheaterDetector.f.field_71441_e.func_73045_a(var1.func_148978_c())) instanceof EntityPlayer && var7 != CheaterDetector.f.field_71439_g) {
            CheaterRegistry var8 = R.computeIfAbsent(((EntityPlayer)var7).func_146103_bH().getId(), var0 -> new CheaterRegistry(139461843194438L));
            this.L((short)0, var8, new CheaterDetectionSample(DetectedAction.SWING, this.J, 0.0f, 0.0f, Math.abs(var7.field_70125_A), true, var7.field_70122_E, 0.0));
}
}
    private float W(byte var1) {
        return (float)var1 * 360.0f / 256.0f;
}
    private static void a() {
        CheaterDetector.p[0] = "_YuB Ms";
        CheaterDetector.p[1] = "#DQ3\u0003\u001a\u0014SU9N>\u0003X\u000f%";
        CheaterDetector.p[2] = "\u001f>\u0002ORg\u001d";
        CheaterDetector.p[3] = Long.TYPE;
        CheaterDetector.r[3] = "java/lang/Long";
        CheaterDetector.p[4] = Void.TYPE;
        CheaterDetector.r[4] = "java/lang/Void";
        CheaterDetector.p[5] = "<0\b\u0003eY7?\u0019L\u0004W<4\u001d\u0016";
        CheaterDetector.p[6] = "#PRc\u0000Z1JF\t!4x\fW3\u001aU+ZGdd\r'\nW9X_q^^\t^U-\fYoUQ,[92_J!\fI7ZU!0";
}
    public void V(EntityPlayer var1, long var2, CheaterRegistry var4) {
        if (var4.Y > 0L) {
            --var4.Y;
        } else {
            int var12 = var4.g(DetectedCheat.SCAFFOLD);
            List<CheaterDetectionSample> var13 = var4.G;
            long var14 = this.J - 12L;
            if (var14 <= var4.R) {
                var4.D(DetectedCheat.SCAFFOLD, -5);
                this.B(99456092162304L, var13);
                var4.Y = 6L;
            } else {
                for (int var16 = 0; var16 < var13.size(); ++var16) {
                    int var18;
                    CheaterDetectionSample var17 = var13.get(var16);
                    if (CheaterDetectionSample.u(var17) <= var4.R) continue;
                    if (CheaterDetectionSample.u(var17) > var14) break;
                    if (CheaterDetectionSample.h(var17) != DetectedAction.PLACE || !this.S(var13, var18 = this.Q(var13, var16, 30), CheaterDetectionSample.u(var17), 62175884037630L)) continue;
                    var4.D(DetectedCheat.SCAFFOLD, 2);
                    if (this.K((short)0, 1948585579, var13, '\u2279', CheaterDetectionSample.u(var17) - 30L, CheaterDetectionSample.u(var17) + 12L)) {
                        var4.D(DetectedCheat.SCAFFOLD, 2);
}
                    var16 = this.t(var13, var16, CheaterDetectionSample.u(var17) + 12L);
}
                var4.R = var14;
                if (var12 == var4.g(DetectedCheat.SCAFFOLD)) {
                    var4.D(DetectedCheat.SCAFFOLD, -5);
}
                this.B(99456092162304L, var13);
                var4.Y = 6L;
}
}
}
    public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
        if (var1.H instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)var1.H;
            UUID var3 = var2.func_146103_bH().getId();
            c.put(var3, var2);
}
}
    private int Q(List<CheaterDetectionSample> var1, int var2, int var3) {
        int var6;
        long var4 = CheaterDetectionSample.u(var1.get(var2));
        for (var6 = var2; var6 > 0 && var4 - CheaterDetectionSample.u(var1.get(var6 - 1)) <= (long)var3; --var6) {
}
        return var6;
}
    public void c(EntityPlayer var1, CheaterRegistry var2) {
        if (var1.func_71039_bw()) {
            if (var1.func_70051_ag()) {
                var2.D(DetectedCheat.NOSLOW, 1);
}
        } else {
            var2.D(DetectedCheat.NOSLOW, -1);
}
}
    private boolean N(float var1, float var2, float var3) {
        return Math.abs(var1 - var2) <= var3;
}
    @Override
    public final void x(long var1, EventBus var3) {
        CheaterDetectorBinder.M(var3, this);
}
    private boolean S(List var1, int var2, long var3, long var5) {
        long var12 = var3 - 30L;
        long var14 = var3 + 12L;
        int var16 = 0;
        int var17 = 0;
        int var18 = 0;
        int var19 = 0;
        int var20 = 0;
        for (int var21 = var2; var21 < var1.size(); ++var21) {
            CheaterDetectionSample var22 = (CheaterDetectionSample)var1.get(var21);
            if (CheaterDetectionSample.u(var22) < var12) continue;
            if (CheaterDetectionSample.u(var22) > var14) break;
            if (CheaterDetectionSample.h(var22) == DetectedAction.PLACE) {
                ++var16;
                continue;
}
            if (CheaterDetectionSample.h(var22) == DetectedAction.SWING) {
                ++var17;
                continue;
}
            if (CheaterDetectionSample.h(var22) != DetectedAction.ROTATION) continue;
            if (CheaterDetectionSample.E(var22) > 0.015) {
                ++var18;
}
            if (CheaterDetectionSample.v(var22) >= 60.0f || CheaterDetectionSample.g(var22) >= 18.0f) {
                ++var19;
}
            if (!(CheaterDetectionSample.R(var22) >= 24.0f)) continue;
            ++var20;
}
        return var16 >= 2 && var17 >= 2 && var18 >= 2 && var19 >= 2 && var20 >= 2 && this.S(var1, var12, var14, 40076018100272L) && this.K((short)0, 1948585579, var1, '\u2279', var12, var14);
}
    private boolean O(long var1, List var3, char var4, int var5) {
        long var6 = (var1 << 16 | (long)var4 << 48 >>> 48) ^ a;
        int var8 = Math.min(var3.size(), var5 + CheaterDetector.d(25319, 0x1152B6057496E1CAL ^ var6));
        float var9 = 0.0f;
        int var10 = 0;
        for (int var11 = var5; var11 < var8; ++var11) {
            float var12 = this.Z(var3, var11);
            if (!(var12 >= 24.0f)) continue;
            var9 += var12;
            ++var10;
}
        if (var10 < 3) {
            return false;
}
        float var17 = var9 / (float)var10;
        float var18 = 0.0f;
        int var13 = 0;
        for (int var14 = var5; var14 < var8; ++var14) {
            float var15 = this.Z(var3, var14);
            if (var15 < 24.0f) continue;
            float var16 = var15 - var17;
            var18 += var16 * var16;
            if (!this.N(var15, 30.0f, 7.0f) && !this.N(var15, 35.0f, 7.0f) && !this.N(var15, 45.0f, 7.0f) && !this.N(var15, 90.0f, 8.0f) && !this.N(var15, 135.0f, 10.0f) && !this.N(var15, 180.0f, 8.0f)) continue;
            ++var13;
}
        return var13 >= 3 && (var18 /= (float)var10) <= 90.0f;
}
    private boolean m(int var1, List var2, int var3, long var4) {
        long var6 = ((long)var1 << 32 | var4 << 32 >>> 32) ^ a;
        long var8 = var6 ^ 0x5693D6CD55CEL;
        return this.Z(var8, var2, var3, DetectedAction.SWING, 6) && this.Z(var8, var2, var3, DetectedAction.PLACE, 10);
}
    public EntityPlayer Q(BlockPos var1, Block var2, double var3) {
        AxisAlignedBB var5 = new AxisAlignedBB((double)var1.func_177958_n() + 0.5 - var3, (double)var1.func_177956_o() + 0.5 - 3.0, (double)var1.func_177952_p() + 0.5 - var3, (double)var1.func_177958_n() + 0.5 + var3, (double)var1.func_177956_o() + 0.5 + 3.0, (double)var1.func_177952_p() + 0.5 + var3);
        List var6 = CheaterDetector.f.field_71441_e.func_72872_a(EntityPlayer.class, var5);
        EntityPlayer var7 = null;
        double var8 = Double.MAX_VALUE;
        for (EntityPlayer var11 : var6) {
            double var22;
            ItemStack var12;
            if (var11 == CheaterDetector.f.field_71439_g || (var12 = var11.func_70694_bm()) == null || !(var12.func_77973_b() instanceof ItemBlock) || ((ItemBlock)var12.func_77973_b()).func_179223_d() != var2) continue;
            int var13 = (int)Math.floor(var11.field_70163_u);
            if (var1.func_177956_o() > var13 || var1.func_177956_o() < var13 - 2) continue;
            double var14 = var11.field_70165_t - ((double)var1.func_177958_n() + 0.5);
            double var16 = var11.field_70161_v - ((double)var1.func_177952_p() + 0.5);
            double var18 = Math.abs(var11.field_70163_u - (double)var1.func_177956_o());
            double var20 = var14 * var14 + var16 * var16;
            if (var20 > var3 * var3 || !((var22 = var14 * var14 + var16 * var16 + var18 * 0.35) < var8)) continue;
            var8 = var22;
            var7 = var11;
}
        return var7;
}
    public void q(EntityPlayer var1, CheaterRegistry var2, long var3) {
        if (var1.func_71039_bw()) {
            ++var2.f;
            if (var2.f >= 3 && var1.field_82175_bq && var1.field_110158_av == 1) {
                var2.D(DetectedCheat.AUTOBLOCK, 5);
}
        } else {
            var2.I = true;
            var2.f = 0;
            var2.D(DetectedCheat.AUTOBLOCK, -1);
}
        if (var2.g(DetectedCheat.AUTOBLOCK) >= DetectedCheat.AUTOBLOCK.FLAG_VL) {
            var2.C(118087748409822L, DetectedCheat.AUTOBLOCK);
}
}
                Cipher var24 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var24.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var31 = new String[3];
            int var29 = 0;
            String var28 = "\u0013J\u00fa*R\u009c\u00ca\u00d7; \u00d0\u00d0\u0096y\u0018JK\u00ef\u0088\u000f:\u00d4\u00a8\u00d6 #\u00ab\u00a8,\u00eb\"\r\u00b76o\u00a5s7\u00afez\u00b1m\n\u00f3>+\u0093\u008fuGw\u0016\u00a6:g)\u0018\u0015_m9\u0017\u00f9\u00c7\u00a2\u0093kk1+\u00f5\u00c4\u00f3z\u0086\u001c\u008ei\u008aD=";
            int var30 = "\u0013J\u00fa*R\u009c\u00ca\u00d7; \u00d0\u00d0\u0096y\u0018JK\u00ef\u0088\u000f:\u00d4\u00a8\u00d6 #\u00ab\u00a8,\u00eb\"\r\u00b76o\u00a5s7\u00afez\u00b1m\n\u00f3>+\u0093\u008fuGw\u0016\u00a6:g)\u0018\u0015_m9\u0017\u00f9\u00c7\u00a2\u0093kk1+\u00f5\u00c4\u00f3z\u0086\u001c\u008ei\u008aD=".length();
            int var27 = 24;
            int var26 = -1;
            while (true) {
                byte[] var32 = var24.doFinal(var28.substring(++var26, var26 + var27).getBytes("ISO-8859-1"));
                String var48 = CheaterDetector.b(var32).intern();
                int var10001 = -1;
                var31[var29++] = var48;
                if ((var26 += var27) >= var30) {
                    b = var31;
                    d = new String[3];
                    k = new HashMap(13);
                    var10003 = new byte[]{(byte)(var22 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                    for (int var12 = 1; var12 < 8; ++var12) {
                        var10003[var12] = (byte)(var22 << var12 * 8 >>> 56);
}
                    Cipher var11 = Cipher.getInstance("DES/CBC/NoPadding");
                    var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                    long[] var17 = new long[17];
                    int var14 = 0;
                    String var15 = "JG+\u00d6\u00b7\u0097qS\u0018-\u0097a\u00fb\fa\u0011\u00d5\u00f5G[\u00cb\u00ce\u00f6\u00ee\u00ebk\u0010\u009c\u00ea\u00c1E|\u0010g[\u00d3\u00f0.R\u00fe\"\u00e9\u00d6\u00f1G\b}\u0006\u00ae\u001bF\"\u00d6\u001d\u00a5\u00a57i\u0084zW\u0093g^H\u009cYH\u00d5\u00a5N\u00c5I\u00f6Y?\u00d6kf\u00a7xN\u00f6\u0011\u0099\u001f\u00ff\u001f\u00d7\u00dfxB\b\u00a3\u00f0\u00bc\u00ab\u00f7-\u00ec\u0007(\u00fcr\u001cL\n\u0006\u00a8U\u00be\u00be\u00b8\u0085{x\u00d0\u001cM\u00aa";
                    int var16 = "JG+\u00d6\u00b7\u0097qS\u0018-\u0097a\u00fb\fa\u0011\u00d5\u00f5G[\u00cb\u00ce\u00f6\u00ee\u00ebk\u0010\u009c\u00ea\u00c1E|\u0010g[\u00d3\u00f0.R\u00fe\"\u00e9\u00d6\u00f1G\b}\u0006\u00ae\u001bF\"\u00d6\u001d\u00a5\u00a57i\u0084zW\u0093g^H\u009cYH\u00d5\u00a5N\u00c5I\u00f6Y?\u00d6kf\u00a7xN\u00f6\u0011\u0099\u001f\u00ff\u001f\u00d7\u00dfxB\b\u00a3\u00f0\u00bc\u00ab\u00f7-\u00ec\u0007(\u00fcr\u001cL\n\u0006\u00a8U\u00be\u00be\u00b8\u0085{x\u00d0\u001cM\u00aa".length();
                    int var13 = 0;
                    block11: while (true) {
                        var10001 = var13;
                        byte[] var18 = var15.substring(var10001, var13 += 8).getBytes("ISO-8859-1");
                        long[] var38 = var17;
                        var10001 = var14++;
                        long var51 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                        int var57 = -1;
                        while (true) {
                            long var19 = var51;
                            byte[] var21 = var11.doFinal(new byte[]{(byte)(var19 >>> 56), (byte)(var19 >>> 48), (byte)(var19 >>> 40), (byte)(var19 >>> 32), (byte)(var19 >>> 24), (byte)(var19 >>> 16), (byte)(var19 >>> 8), (byte)var19});
                            long var62 = ((long)var21[0] & 0xFFL) << 56 | ((long)var21[1] & 0xFFL) << 48 | ((long)var21[2] & 0xFFL) << 40 | ((long)var21[3] & 0xFFL) << 32 | ((long)var21[4] & 0xFFL) << 24 | ((long)var21[5] & 0xFFL) << 16 | ((long)var21[6] & 0xFFL) << 8 | (long)var21[7] & 0xFFL;
                            switch (var57) {
                                case 0: {
                                    var38[var10001] = var62;
                                    if (var13 < var16) break;
                                    g = var17;
                                    h = new Integer[17];
                                    o = new HashMap(13);
                                    var10003 = new byte[]{(byte)(var22 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                    for (int var1 = 1; var1 < 8; ++var1) {
                                        var10003[var1] = (byte)(var22 << var1 * 8 >>> 56);
}
                                    Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                    var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                    long[] var6 = new long[11];
                                    int var3 = 0;
                                    String var4 = "H\u000b\u00a5\b\u0081\u00ab\u00f1\u00b4\u009f\u00e3\u0081\u00a3\u00f2\u00b7\u0083\u00b9Ee\u00bd%\u00b5K\u00eb\u00e4\u00b1\u00ea\u00c0\u001eL|9\u00ed\u00b6l\u00b3\u009a\u00e2\u0011T\u00e3\u0000\u008b\r8\u00f7\u0099\u00f4\u00ea3t\u0013\u0004\u0088\u00a2\u0098\u00f2\u0095\u00e1\u00ac\u00c3\u00ed\u00b6 6\u00c2\u00c7\u008c\"g\u00adr\u001b";
                                    int var5 = "H\u000b\u00a5\b\u0081\u00ab\u00f1\u00b4\u009f\u00e3\u0081\u00a3\u00f2\u00b7\u0083\u00b9Ee\u00bd%\u00b5K\u00eb\u00e4\u00b1\u00ea\u00c0\u001eL|9\u00ed\u00b6l\u00b3\u009a\u00e2\u0011T\u00e3\u0000\u008b\r8\u00f7\u0099\u00f4\u00ea3t\u0013\u0004\u0088\u00a2\u0098\u00f2\u0095\u00e1\u00ac\u00c3\u00ed\u00b6 6\u00c2\u00c7\u008c\"g\u00adr\u001b".length();
                                    int var2 = 0;
                                    block14: while (true) {
                                        int var45 = var2;
                                        byte[] var7 = var4.substring(var45, var2 += 8).getBytes("ISO-8859-1");
                                        long[] var40 = var6;
                                        var45 = var3++;
                                        long var54 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                        int var60 = -1;
                                        while (true) {
                                            long var8 = var54;
                                            byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                            var62 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                            switch (var60) {
                                                case 0: {
                                                    var40[var45] = var62;
                                                    if (var2 < var5) break;
                                                    m = var6;
                                                    return;
}
                                                default: {
                                                    var40[var45] = var62;
                                                    if (var2 < var5) continue block14;
                                                    var4 = "\f[\u00b0~\u00995\u00dbn\n\u0082\u00a5+x\u0007\u007f\u00bd";
                                                    var5 = "\f[\u00b0~\u00995\u00dbn\n\u0082\u00a5+x\u0007\u007f\u00bd".length();
                                                    var2 = 0;
}
}
                                            int var47 = var2;
                                            var7 = var4.substring(var47, var2 += 8).getBytes("ISO-8859-1");
                                            var40 = var6;
                                            var45 = var3++;
                                            var54 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                            var60 = 0;
}
                                        break;
}
}
                                default: {
                                    var38[var10001] = var62;
                                    if (var13 < var16) continue block11;
                                    var15 = "\u00d9<l\u0015@\u008bQyuA\u00ab\u0017k't\u00a7";
                                    var16 = "\u00d9<l\u0015@\u008bQyuA\u00ab\u0017k't\u00a7".length();
                                    var13 = 0;
}
}
                            int var44 = var13;
                            var18 = var15.substring(var44, var13 += 8).getBytes("ISO-8859-1");
                            var38 = var17;
                            var10001 = var14++;
                            var51 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                            var57 = 0;
}
                        break;
}
}
                var27 = var28.charAt(var26);
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var33) {
            throw new RuntimeException(var33);
}
}
    static {
        R = new LinkedHashMap<UUID, CheaterRegistry>();
        c = new LinkedHashMap<UUID, EntityPlayer>();
}
}