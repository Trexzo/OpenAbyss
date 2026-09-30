/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.combat;

import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.KillAuraBinder;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.events.SetAnglesEvent;
import Abyss.internal.accessor.RenderManagerAccessor;
import Abyss.module.Category;
import Abyss.module.Modules;
import Abyss.module.PriorityModule;
import Abyss.module.impl.combat.AutoBlock;
import Abyss.module.impl.combat.KeepSprint;
import Abyss.module.impl.configuration.Theme;
import Abyss.module.impl.movement.NoSlow;
import Abyss.module.impl.visual.Freelook;
import Abyss.module.impl.world.BedNuker;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.ClientUtil;
import Abyss.util.CombatUtil;
import Abyss.util.EntityUtil;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.Pair;
import Abyss.util.RaytraceUtil;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import Abyss.util.TimerUtil;
import Abyss.util.packet.OutgoingPacketState;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public class KillAura
extends PriorityModule
implements EventSubscriber {
    public static ModeSetting mode;
    public static TimerUtil I;
    private long C;
    private static Minecraft e;
    private double O;
    public static EntityLivingBase H6;
    private static Long[] qb;
    public static BooleanSetting showReachRing;
    public static NumberSetting attackRange;
    public static PercentageSetting rotationSmoothing;
    public static BooleanSetting players;
    public static ColorSetting customColor;
    public static BooleanSetting requireSword;
    public static BooleanSetting teammates;
    public static BooleanSetting requireClick;
    public static ModeSetting showTarget;
    private static Map rb;
    private boolean B;
    public static BooleanSetting enemies;
    private Pair<Float, Float> H7;
    public static BooleanSetting silverfishes;
    private static String[] gb;
    private static long bb;
    public static BooleanSetting animals;
    private static long[] pb;
    private static String[] hb;
    public static NumberSetting swingRange;
    public static NumberSetting minAPS;
    public static TimerUtil y;
    private boolean U;
    public static NumberSetting maxAPS;
    public static BooleanSetting throughWall;
    public static BooleanSetting golems;
    public static NumberSetting switchDelay;
    public static BooleanSetting screenCheck;
    private int m;
    public static BooleanSetting bots;
    public static ColorSetting showTargetDamageColor;
    private static Map ib;
    public static HeaderSetting blinkAutoblocksOnlyWorksInHypixel;
    public static PercentageSetting showTargetOpacity;
    public static NumberSetting angleStep;
    public static ModeSetting showTargetColor;
    public static ModeSetting moveFix;
    public static HeaderSetting targetSettings;
    public static ModeSetting sort;
    public static BooleanSetting friends;
    private static Map ob;
    private static long[] mb;
    public static BooleanSetting legit;
    private boolean t;
    public static NumberSetting fov;
    public static boolean x;
    public static boolean a;
    public static long b;
    public static ModeSetting rotation;
    public static BooleanSetting bosses;
    public static BooleanSetting mobs;
    private static final Comparator<EntityLivingBase> SORT_CMP;

    public void onRender3D(Render3DEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var12 = 11654630;
        int var15 = 54127;
        if (showReachRing.c()) {
            RenderUtil.s((EntityLivingBase)KillAura.e.thePlayer, attackRange.L(), 45, 1.5f, -1, 24156, '\u05bd', (char)var15);
}
        if (H6 != null) {
            int var20;
            switch (showTargetColor.Y()) {
                case "THEME": {
                    var20 = Theme.n(137969810508066L, 0.0, (int)((float)showTargetOpacity.k() / 100.0f * 255.0f));
                    break;
}
                case "THEME_CUSTOM": {
                    var20 = Theme.e(0.0, (int)((float)showTargetOpacity.k() / 100.0f * 255.0f), 87512972878444L);
                    break;
}
                default: {
                    var20 = customColor.x((int)((float)showTargetOpacity.k() / 100.0f * 255.0f));
}
}
            switch (showTarget.Y()) {
                case "BOX": {
                    RenderUtil.N(H6, 138251344894190L, var20);
                    break;
}
                case "BOX_WITH_DAMAGE": {
                    if (KillAura.H6.hurtTime > 5) {
                        var20 = showTargetDamageColor.x((int)((float)showTargetOpacity.k() / 100.0f * 255.0f));
}
                    RenderUtil.N(H6, 138251344894190L, var20);
                    break;
}
                case "HEAD_BOX": {
                    RenderUtil.R((Entity)H6, 47843098999105L, var20, var1.j);
                    break;
}
                case "HEAD_BOX_WITH_DAMAGE": {
                    if (KillAura.H6.hurtTime > 5) {
                        var20 = showTargetDamageColor.x((int)((float)showTargetOpacity.k() / 100.0f * 255.0f));
}
                    RenderUtil.R((Entity)H6, 47843098999105L, var20, var1.j);
                    break;
}
                case "RING": {
                    this.C(9764, (byte)75, var12, (Entity)H6, var20, var1.j);
}
}
}
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    @Override
    public final void x(long var1, EventBus var3) {
        KillAuraBinder.e(var3, this);
}
    private float k(Entity var1, int var2) {
        if (var1 == null) {
            return 0.0f;
}
        long var7 = y.p();
        long var9 = I.p();
        if (I.Q(1000L)) {
            this.t = false;
}
        if (!this.t) {
            if (var7 < 200L) {
                return (float)var7 / 200.0f;
}
            this.t = true;
            return 1.0f;
}
        return var9 > 800L ? Math.max(0.0f, (float)(1000L - var9) / 200.0f) : 1.0f;
}
    private void q(EntityLivingBase var1, int var2, int var3) {
        boolean var19;
        long var4 = ((long)var2 << 32 | (long)var3 << 32 >>> 32) ^ bb;
        long var6 = var4 ^ 0x4CE84BC8376CL;
        int var10 = (int)((var4 ^ 0x5BB253E1C20BL) >>> 32);
        int var11 = (int)((var4 ^ 0x5BB253E1C20BL) << 32 >>> 48);
        int var12 = (int)((var4 ^ 0x5BB253E1C20BL) << 48 >>> 48);
        long var13 = var4 ^ 0x3268C5F9D47EL;
        long var15 = var4 ^ 0xC6BEF0ED1B3L;
        long var17 = var4 ^ 0x6F0A873460FEL;
        this.U = this.S(var1, var10, (short)var11, (char)var12);
        if (AutoBlock.t(var6) == 1) {
            AutoBlock.V(0L);
        } else if (b <= 0L && (var19 = RaytraceUtil.i((Entity)var1, attackRange.L(), var13, !throughWall.c()) ? this.Y(var15, var1) : this.E(var1, var17))) {
            b += MathUtil.e(this.N()[0], this.N()[1]);
}
}
    private double g(double var1, double var3) {
        double var5 = Math.max(var1, var3);
        return Math.max(var1, var3) > (double)attackRange.L() ? var5 : (double)attackRange.L();
}
    private void Y(int var1, char var2, int var3) {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ bb;
        long var8 = var4 ^ 0x525D6D7FBF77L;
        this.T(false);
        this.H7 = null;
        this.m = 0;
        this.U = false;
        b = 0L;
        this.C = 0L;
        a = false;
        H6 = null;
        x = false;
        if (this.B) {
            RotationManager.k(0L);
            RotationManager.O(var8);
            this.B = false;
}
}
    private boolean isGetKeyCode(long var1) {
        EntityLivingBase var7;
        if (!this.Y()) {
            return false;
}
        if (requireSword.c() && !ItemUtil.d()) {
            return false;
}
        if (requireClick.c() && !KeyBindUtil.V(KillAura.e.gameSettings.keyBindAttack.getKeyCode(), 64165991731362L)) {
            return false;
}
        if (screenCheck.c() && KillAura.e.currentScreen != null) {
            return false;
}
        if (KillAura.e.thePlayer.isDead) {
            return false;
}
        H6 = var7 = this.b(14517823015178L);
        return var7 != null;
}
    private boolean y(char var1, char var2, int var3, EntityLivingBase var4, boolean var5) {
        long var6 = ((long)var1 << 48 | (long)var2 << 48 >>> 16 | (long)var3 << 32 >>> 32) ^ bb;
        int var8 = (int)((var6 ^ 0x78139923A04FL) >>> 56);
        int var9 = (int)((var6 ^ 0x78139923A04FL) << 8 >>> 32);
        int var10 = (int)((var6 ^ 0x78139923A04FL) << 40 >>> 40);
        int var11 = (int)((var6 ^ 0x3299ACC75E47L) >>> 32);
        int var12 = (int)((var6 ^ 0x3299ACC75E47L) << 32 >>> 48);
        int var13 = (int)((var6 ^ 0x3299ACC75E47L) << 48 >>> 48);
        int var14 = (int)((var6 ^ 0x708503894E86L) >>> 32);
        long var15 = (var6 ^ 0x708503894E86L) << 32 >>> 32;
        if (legit.c()) {
            KeyBindUtil.T(var11, (short)var12, KillAura.e.gameSettings.keyBindAttack.getKeyCode(), (short)var13);
            return true;
}
        if (OutgoingPacketState.E) {
            return true;
}
        if (!(!this.U || var5 && (KillAura.e.thePlayer.isUsingItem() || OutgoingPacketState.h || OutgoingPacketState.P))) {
            this.b(false, (byte)var8, var9, var10);
            return CombatUtil.I((Entity)var4, var14, var15);
}
        return false;
}
    private void g(PreMouseInputEvent var1) {
        if (OutgoingPacketState.T) {
            var1.Q(true);
            var1.G(true);
            var1.M(true);
}
}
    private EntityLivingBase b(long var1) {
        List<EntityLivingBase> var10;
        double var7 = attackRange.L();
        double var12 = this.w$r1();
        HashSet var9 = new HashSet();
        List<EntityLivingBase> var13 = null;
        if (rotation.R("MANUAL")) {
            var10 = RaytraceUtil.j(var7);
        } else {
            var13 = new ArrayList<EntityLivingBase>();
            var10 = EntityUtil.F2(var12, 84864282554303L, fov.L(), var7, var13);
}
        List<EntityLivingBase> var11 = EntityUtil.x(var10, players.c(), mobs.c(), animals.c(), bosses.c(), 50993518959776L, friends.c(), enemies.c(), teammates.c(), bots.c(), silverfishes.c(), golems.c());
        if (var13 != null) {
            var13 = EntityUtil.x(var13, players.c(), mobs.c(), animals.c(), bosses.c(), 50993518959776L, friends.c(), enemies.c(), teammates.c(), bots.c(), silverfishes.c(), golems.c());
}
        if (!throughWall.c()) {
            if (var13 == null) {
                var11.removeIf(var3x -> {
                    if (RaytraceUtil.V((Entity)var3x, bb ^ 0x15AB3F59C366L ^ 0x2B28FE9682C4L, var7)) {
                        var9.add(var3x);
                        return true;
}
                    return false;
                });
            } else {
                var11.removeIf(var3x -> RaytraceUtil.V((Entity)var3x, bb ^ 0x2CBB0203E738L ^ 0x1238C3CCA69AL, var12));
                if (!var13.isEmpty()) {
                    var13.removeIf(var3x -> RaytraceUtil.V((Entity)var3x, bb ^ 0x15AB3F59C366L ^ 0x2B28FE9682C4L, var7));
}
}
}
        if (var13 != null && !var13.isEmpty()) {
            var11 = var13;
        } else if (var11.isEmpty() && var13 == null) {
            var10 = RaytraceUtil.j(var12);
            var11 = EntityUtil.x(var10, players.c(), mobs.c(), animals.c(), bosses.c(), 50993518959776L, friends.c(), enemies.c(), teammates.c(), bots.c(), silverfishes.c(), golems.c());
            if (!throughWall.c()) {
                var11.removeIf(var3x -> var9.contains(var3x) || RaytraceUtil.V((Entity)var3x, bb ^ 0x2CBB0203E738L ^ 0x1238C3CCA69AL, var12));
}
}
        if (var11.isEmpty()) {
            return null;
}
        this.V(var11);
        switch (mode.Y()) {
            case "SINGLE": {
                return (EntityLivingBase)var11.get(0);
}
            case "SWITCH": {
                this.m = MathUtil.k(this.m, 0, var11.size() - 1);
                if (x && this.C <= 0L) {
                    this.m = this.m + 1 >= var11.size() ? 0 : ++this.m;
                    this.C += (long)switchDelay.L();
                    x = false;
}
                return (EntityLivingBase)var11.get(this.m);
}
}
        return (EntityLivingBase)var11.get(0);
}
    private double w$r1() {
        return this.g(swingRange.L(), this.g(swingRange.L(), attackRange.L()));
}
    private boolean S(EntityLivingBase var1, int var2, short var3, char var4) {
        long var5 = ((long)var2 << 32 | (long)var3 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ bb;
        long var7 = var5 ^ 0x6557928217C9L;
        long var9 = var5 ^ 0x4556FEE1BBC4L;
        int var15 = (int)((var5 ^ 0x79826A873F24L) >>> 48);
        int var16 = (int)((var5 ^ 0x79826A873F24L) << 16 >>> 48);
        long var18 = var5 ^ 0x7A399B79C16DL;
        if (BedNuker.y) {
            return false;
}
        if (!rotation.R("LOCK")) {
            RotationManager.k(0L);
}
        switch (moveFix.Y()) {
            case "SILENT": {
                RotationManager.n(RotationMode.SILENT);
                break;
}
            case "STRICT": {
                RotationManager.n(RotationMode.STRICT);
                break;
}
            case "NONE": {
                RotationManager.n(RotationMode.NONE);
}
}
        double var27 = this.r(var9, var1);
        switch (rotation.Y()) {
            case "MANUAL": {
                return RaytraceUtil.j(var27).contains(var1);
}
            case "NONE": {
                return true;
}
}
        if (rotation.R("NO_RENDER")) {
            RotationManager.w(true);
}
        float var24 = angleStep.L();
        float var25 = (float)rotationSmoothing.k() / 100.0f;
        float[] var26 = throughWall.c() ? RotationUtil.J((Entity)var1, var18, var27) : RotationUtil.p((Entity)var1, var27, var7);
        RotationManager.L(var26[0], var26[1], var24, var25);
        this.H7 = new Pair<Float, Float>(Float.valueOf(RotationManager.r), Float.valueOf(RotationManager.G));
        if (rotation.R("LOCK") && !Freelook.c()) {
            RotationManager.W((short)var15, (short)var16, this.H7.a().floatValue(), this.H7.p().floatValue());
        } else {
            RotationManager.k(0L);
}
        this.B = true;
        return RaytraceUtil.j(var27).contains(var1);
}
    private double[] N() {
        return new double[]{MathUtil.q(minAPS.L(), 1.0f, maxAPS.L()), MathUtil.q(maxAPS.L(), 1.0f, maxAPS.L())};
}
    private boolean Y(long var1, EntityLivingBase var3) {
        int var4 = (int)(((var1 = bb ^ var1) ^ 0x52D0D5395FF2L) >>> 48);
        int var5 = (int)((var1 ^ 0x52D0D5395FF2L) << 16 >>> 48);
        int var6 = (int)((var1 ^ 0x52D0D5395FF2L) << 32 >>> 32);
        boolean var7 = this.y((char)var4, (char)var5, var6, var3, true);
        if (var7) {
            x = true;
}
        return var7;
}
    private boolean b(boolean var1, byte var2, int var3, int var4) {
        long var5 = ((long)var2 << 56 | (long)var3 << 32 >>> 8 | (long)var4 << 40 >>> 40) ^ bb;
        int var7 = (int)((var5 ^ 0xBD8809976F6L) >>> 32);
        int var8 = (int)((var5 ^ 0xBD8809976F6L) << 32 >>> 48);
        int var9 = (int)((var5 ^ 0xBD8809976F6L) << 48 >>> 48);
        if (!OutgoingPacketState.E && !OutgoingPacketState.T) {
            if (!var1 || !KillAura.e.thePlayer.isUsingItem() && !OutgoingPacketState.h && !OutgoingPacketState.P) {
                if (!legit.c()) {
                    KillAura.e.thePlayer.swingItem();
}
                KeyBindUtil.T(var7, (short)var8, KillAura.e.gameSettings.keyBindAttack.getKeyCode(), (short)var9);
                return true;
}
            return false;
}
        return true;
}
    private void C(int var1, byte var2, int var3, Entity var4, int var5, float var6) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var7 = ((long)var1 << 32 | (long)var2 << 56 >>> 32 | (long)var3 << 40 >>> 40) ^ bb;
        long var9 = var7 ^ 0x125FD8F414EL;
        int var15 = (int)((var7 ^ 0x54C23B274AADL) >>> 32);
        int var18 = (int)((var7 ^ 0x293C0A2490C6L) >>> 32);
        double var21 = this.O;
        this.O += 0.8 * (double)ClientUtil.H(var9) * 0.05;
        float var23 = (float)(var4.getEntityBoundingBox().maxY - var4.getEntityBoundingBox().minY);
        double var24 = var21 + (this.O - var21) * (double)var6;
        double var26 = Math.abs(1.0 + Math.sin(var24 - 0.5)) / 2.0;
        double var28 = Math.abs(1.0 + Math.sin(var24)) / 2.0;
        double var30 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)var6 - RenderManagerAccessor.k(0L, e.getRenderManager());
        double var32 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)var6 - RenderManagerAccessor.y(var15, e.getRenderManager()) + var26 * (double)var23;
        double var34 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)var6 - RenderManagerAccessor.W(0L, e.getRenderManager());
        double var36 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)var6 - RenderManagerAccessor.y(var15, e.getRenderManager()) + var28 * (double)var23;
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3008);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)8);
        Color var39 = new Color(var5);
        for (int var38 = 0; var38 <= 360; ++var38) {
            float var40 = this.k(var4, var18);
            GL11.glColor4f((float)((float)var39.getRed() / 255.0f), (float)((float)var39.getGreen() / 255.0f), (float)((float)var39.getBlue() / 255.0f), (float)(0.6f * var40));
            GL11.glVertex3d((double)(var30 + Math.cos(Math.toRadians(var38)) * (double)var4.width * 0.8), (double)var36, (double)(var34 + Math.sin(Math.toRadians(var38)) * (double)var4.width * 0.8));
            GL11.glColor4f((float)((float)var39.getRed() / 255.0f), (float)((float)var39.getGreen() / 255.0f), (float)((float)var39.getBlue() / 255.0f), (float)(0.01f * var40));
            GL11.glVertex3d((double)(var30 + Math.cos(Math.toRadians(var38)) * (double)var4.width * 0.8), (double)var32, (double)(var34 + Math.sin(Math.toRadians(var38)) * (double)var4.width * 0.8));
}
        GL11.glEnd();
        GL11.glEnable((int)2848);
        GL11.glBegin((int)2);
        for (int var41 = 0; var41 <= 360; ++var41) {
            float var42 = this.k(var4, var18);
            GL11.glColor4f((float)((float)var39.getRed() / 255.0f), (float)((float)var39.getGreen() / 255.0f), (float)((float)var39.getBlue() / 255.0f), (float)(0.8f * var42));
            GL11.glVertex3d((double)(var30 + Math.cos(Math.toRadians(var41)) * (double)var4.width * 0.8), (double)var36, (double)(var34 + Math.sin(Math.toRadians(var41)) * (double)var4.width * 0.8));
}
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)2929);
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2884);
        GL11.glPopMatrix();
        GlStateManager.resetColor();
}
    public void onSetAngles(long var1, SetAnglesEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!a || !rotation.R("LOCK")) {
            this.H7 = null;
        } else if (!Freelook.c() && this.H7 != null) {
            var3.I(21307, 3074332907L);
}
}
    private static String b(int var0, long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var5 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x1986;
        if (hb[var5] == null) {
            Object[] var4;
            try {
                Long var3 = Thread.currentThread().getId();
                var4 = (Object[])ib.get(var3);
                if (var4 == null) {
                    var4 = new Object[]{Cipher.getInstance("DES/CBC/PKCS5Padding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    ib.put(var3, var4);
}
}
            catch (Exception var10) {
                throw new RuntimeException("Abyss/module/impl/combat/KillAura", var10);
}
            byte[] var6 = new byte[8];
            var6[0] = (byte)(var1 >>> 56);
            for (int var7 = 1; var7 < 8; ++var7) {
                var6[var7] = (byte)(var1 << var7 * 8 >>> 56);
}
            DESKeySpec var11 = new DESKeySpec(var6);
            SecretKey var8 = ((SecretKeyFactory)var4[1]).generateSecret(var11);
            ((Cipher)var4[0]).init(2, (Key)var8, (IvParameterSpec)var4[2]);
            byte[] var9 = gb[var5].getBytes("ISO-8859-1");
            KillAura.hb[var5] = KillAura.b(((Cipher)var4[0]).doFinal(var9));
}
        return hb[var5];
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
    private void K(int var1, int var2) {
        if (b > 0L) {
            b -= 50L;
}
        if (this.C > 0L) {
            this.C -= 50L;
}
}
    private static boolean Q(long var0) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return Modules.J(NoSlow.class).o() && NoSlow.swordNoSlowLive();
}
    private static boolean u(long var0) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var0 = bb ^ var0;
        return Modules.J(KeepSprint.class).o() && KeepSprint.mode.R(KillAura.b(14809, 0x30D4565F69182129L ^ var0));
}
    public void onPreMouseInput(long var1, PreMouseInputEvent var3) {
        int var13 = 3014;
        this.K(15041, 61729);
        boolean var14 = this.isGetKeyCode(64011875398272L);
        if (!var14) {
            this.Y(31990, '\ud57f', var13);
            this.g(var3);
        } else {
            this.T(true);
            a = true;
            this.q(H6, 18356, -467341094);
            this.g(var3);
}
}
    public KillAura(long var1) {
        super((bb ^ var1 ^ 0x6767BFEEE958L) >>> 16, (char)((bb ^ var1 ^ 0x6767BFEEE958L) << 48 >>> 48));
        this.declare("KillAura", Category.Combat, "Attack entities in range", new Setting[0]);
        var1 = bb ^ var1;
        this.B = false;
        this.U = false;
        this.C = 0L;
        this.m = 0;
        this.t = false;
        this.H7 = null;
}
    @Override
    public void L(PreMouseInputEvent var1, long var2) {
        int var4 = (int)((var2 ^ 0x31A3443C272DL) >>> 32);
        int var5 = (int)((var2 ^ 0x31A3443C272DL) << 32 >>> 48);
        int var6 = (int)((var2 ^ 0x31A3443C272DL) << 48 >>> 48);
        this.Y(var4, (char)var5, var6);
}
    private static double sortKey(EntityLivingBase var0) {
        switch (sort.Y()) {
            case "VIEW": {
                return RotationUtil.g((Entity)var0);
}
            case "HEALTH": {
                return var0.getHealth() + var0.getAbsorptionAmount();
}
            case "HURT_TIME": {
                return var0.hurtResistantTime;
}
            case "ARMOR": {
                return var0.getTotalArmorValue();
}
}
        return RaytraceUtil.i((Entity)var0);
}
    private void V(List<EntityLivingBase> var1) {
        var1.sort(SORT_CMP);
}
    public static boolean e(long var0, EntityLivingBase var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return H6 == var2 && !showTarget.R("NONE");
}
    private boolean E(EntityLivingBase var1, long var2) {
        var2 = bb ^ var2;
        int var4 = (int)((var2 ^ 0x8F0915DC60EL) >>> 56);
        int var5 = (int)((var2 ^ 0x8F0915DC60EL) << 8 >>> 32);
        int var6 = (int)((var2 ^ 0x8F0915DC60EL) << 40 >>> 40);
        long var7 = var2 ^ 0x1C30F7B03C7EL;
        boolean var9 = RaytraceUtil.i((Entity)var1, swingRange.L(), var7, !throughWall.c());
        boolean var10 = false;
        if (var9) {
            var10 = this.b(true, (byte)var4, var5, var6);
}
        return var10;
}
    private double r(long var1, EntityLivingBase var3) {
        long var4 = var1 ^ 0x6DDEDD84254FL;
        return RaytraceUtil.i((Entity)var3, attackRange.L(), var4, !throughWall.c()) ? (double)attackRange.L() : this.w$r1();
}
    static {
        bb = 71823488026878L;
        I = new TimerUtil();
        y = new TimerUtil();
        a = false;
        x = false;
        b = 0L;
        e = MinecraftRef.c((byte)0, 0L);
        ib = new HashMap(13);
        gb = new String[]{"\u00d7\u00f2%\u00ca\u00cb*w\u0019", "Ki\u00b9\u00a3_\u00ee?\u0081\u00b9\u00f2\u00f9\u00fb\u00ca\u000e\u008b\u00fd", "B\u0091\u009bEsj\u00dc1", "\u00da\u00c5q\u00f2\u00e0\u00ff\u00c3\u00db", "\u00d1\u0092`\u0017\u00c2\u008dx\u0012", "\b\u00df\u00acx\u00eb\u00eeP*", "f\u0090\u0090\u00c0\u00f1\u00ae\u00b1\u00ce\u00ad\u0081\u0097\u0083\u00be\u00ec\u00f2\u00d6\u00daM\u00c2\u0092Zt\u00fc\n", "\u001c\u00bf\u00e6\u00c5\u00cem\u0002\u007fT\\`\u00ab^\r7\u0002", "-\u00b4\u00d3\u0003\u0094\u0086\u00f7@", "L \u00aarP\u001f77", "\u00a5\u00ebN\u0015\u0098\u001cj\u00eai-\u008c/\u00d5m\u00cb\u009c", "\u0085\u001b\u009d\u00b1\u00b3g#\u000f", "\f\u00f4\u00fa\u00d2\u00f2\u0097F\u00c4", "1nWI\u00b7\\o\u0005\u001aF\u008e\u00e1\u00b0\u00cc\u00a2F", "\\\u00a5\"\u0017\u00ea2\u0093\u00c7", "\u00de\u0098\u0089\u00e0A\u008b\u00f9\u0090", "\"\u00c0x\u00d8\u009d\u00ea\u00ed3", "\u00e3\u0090\u0011!\u00d1\u00f2\u008f\u00f4\u00ed\u00a8\u00cd\u00a3\u00de\tI\u00de", "\u008d\u0015W\u00b2\u00da\u0013\u00f3F", "\u00fe\u0019\u008fv S\u00fe\u00a8", "\u0015\u00a0\u0001\u0010\u0083\u00cdYIr\u00baJ\u0096d6\u00e2}", "s9\u00d0p\u009d\u0092\u00df ", "\u00d3\u00d8\u00cb\u00e2\u00b9P\u009eK", "\u00e2\u00b3$\u00cc\u00aa+\u00ec\u00a2"};
        hb = new String[24];
        ob = new HashMap(13);
        mb = new long[]{4222651748112354234L, 1358759797698752134L, 6027566444030759557L, 6531994159110063519L, 4352892789842412532L, 4215198685318417151L, 4265623248262754419L, -8484462168072695861L, -4975961977512736667L, 5684919308909557051L, -4714451467247381270L, -7242730324709650935L, -1859447380455794541L, -5814833442236119007L, 8426417971733715765L, -3487626619764442578L, -8826877039400193592L, 2162156720661625334L, 2895595605367363828L, 3335883105485980950L, -1828796396622648081L, 5888032080245728409L, 4083632905029626501L};
        rb = new HashMap(13);
        pb = new long[]{8087929143410548367L, 4563174337710790695L, -8179287428373695482L, -5236949487611815791L, -2593048032935943668L, 7859560633349231800L};
        qb = new Long[6];
        SORT_CMP = new Comparator<EntityLivingBase>(){

            @Override
            public int compare(EntityLivingBase var0, EntityLivingBase var1) {
                return Double.compare(KillAura.sortKey(var0), KillAura.sortKey(var1));
}
        };
        rotationSmoothing = new PercentageSetting("Rotation-smoothing", 0);
        customColor = new ColorSetting("Custom-color", "FFFFFF");
        showTargetDamageColor = new ColorSetting("Show-target-damage-color", "FF0000");
        showTargetOpacity = new PercentageSetting("Show-target-opacity", 25);
        showReachRing = new BooleanSetting("Show-reach-ring", false);
        blinkAutoblocksOnlyWorksInHypixel = new HeaderSetting("Blink autoblocks only works in Hypixel");
        targetSettings = new HeaderSetting("Target settings");
        legit = new BooleanSetting("Legit", false);
        requireClick = new BooleanSetting("Require-click", false);
        requireSword = new BooleanSetting("Require-Sword", true);
        screenCheck = new BooleanSetting("Screen-check", true);
        throughWall = new BooleanSetting("Through-wall", false);
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
        minAPS = new NumberSetting("Min-APS", 20.0f, 1.0f, 20.0f, 0.1f);
        maxAPS = new NumberSetting("Max-APS", 20.0f, 1.0f, 20.0f, 0.1f);
        attackRange = new NumberSetting("Attack-range", 3.0f, 0.0f, 8.0f, 0.01f);
        swingRange = new NumberSetting("Swing-range", 6.0f, 0.0f, 8.0f, 0.01f);
        switchDelay = new NumberSetting("Switch-delay", 100.0f, 0.0f, 1000.0f, 1.0f);
        angleStep = new NumberSetting("Angle-step", 90.0f, 0.0f, 180.0f, 1.0f);
        mode = new ModeSetting("Mode", "SINGLE", "SWITCH");
        sort = new ModeSetting("Sort", false, "VIEW", "DISTANCE", "HEALTH", "VIEW", "HURT_TIME", "ARMOR");
        rotation = new ModeSetting("Rotation", "SILENT", "NO_RENDER", "LOCK", "MANUAL", "NONE");
        moveFix = new ModeSetting("Move-fix", "SILENT", "STRICT", "NONE");
        showTarget = new ModeSetting("Show-target", "BOX", "BOX_WITH_DAMAGE", "HEAD_BOX", "HEAD_BOX_WITH_DAMAGE", "RING", "NONE");
        showTargetColor = new ModeSetting("Show-target-color", "THEME", "THEME_CUSTOM", "CUSTOM");
}
}