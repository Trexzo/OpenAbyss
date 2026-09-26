/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AimAssistBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.SetAnglesEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.visual.Freelook;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.EntityUtil;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.Pair;
import Abyss.util.RaytraceUtil;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import java.io.UnsupportedEncodingException;
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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;

public class AimAssist
extends Module
implements EventSubscriber {
    public static BooleanSetting lock;
    public static NumberSetting horizontalSpeed;
    public static BooleanSetting friends;
    private static String[] m;
    private static String[] n;
    private static long k;
        public static NumberSetting verticalSpeed;
    public static BooleanSetting breakBlocks;
    public static BooleanSetting bosses;
    public static BooleanSetting players;
    public static BooleanSetting bots;
    public static ModeSetting sort;
    public static BooleanSetting teammates;
    public static BooleanSetting ignoreBehindWall;
        public static BooleanSetting animals;
        private Pair<Float, Float> g;
    public static BooleanSetting swordOnly;
        public static NumberSetting fov;
    public static BooleanSetting enemies;
    public static NumberSetting range;
    public static HeaderSetting targetSettings;
        public static BooleanSetting mobs;
    
    @Override
    public String g(long var1) {
        return String.valueOf(horizontalSpeed.L());
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
    @Override
    public void A(long var1) {
        this.g = null;
}
    private void J(long var1, EntityLivingBase var3) {
        float[] var13 = ignoreBehindWall.c() ? RotationUtil.p((Entity)var3, range.L(), 133389424731416L) : RotationUtil.J((Entity)var3, 112421519553468L, (double)range.L());
        float var14 = var13[0];
        float var15 = var13[1];
        float var16 = MathUtil.M(RotationManager.p(), var14) / (20.0f / (lock.c() ? 20.0f : horizontalSpeed.L()));
        float var17 = MathUtil.M(RotationManager.s(), var15) / (20.0f / (lock.c() ? 20.0f : verticalSpeed.L()));
        float var18 = RotationManager.s() + var17;
        if (var18 > 90.0f) {
            var17 = 90.0f - RotationManager.s();
        } else if (var18 < -90.0f) {
            var17 = -90.0f - RotationManager.s();
}
        RotationManager.V(31564L, -1928233425, RotationManager.p() + var16);
        RotationManager.v(74908232914960L, RotationManager.s() + var17);
        this.g = new Pair<Float, Float>(Float.valueOf(RotationManager.p()), Float.valueOf(RotationManager.s()));
}
    public void onSetAngles(SetAnglesEvent var1, long var2) {
        if (lock.c() && !Freelook.c() && this.g != null) {
            var1.t(this.g.a().floatValue());
            var1.m(this.g.p().floatValue());
}
}
    public void onPostTick(long var1, PostTickEvent var3) {
        if (AimAssist.f.currentScreen != null) {
            this.g = null;
        } else if (!KeyBindUtil.V(AimAssist.f.gameSettings.keyBindAttack.getKeyCode(), 64165991731362L)) {
            this.g = null;
        } else if (breakBlocks.c() && AimAssist.f.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            this.g = null;
        } else if (swordOnly.c() && !ItemUtil.d()) {
            this.g = null;
        } else {
            EntityLivingBase var12 = this.L(94518397476333L);
            if (var12 == null) {
                this.g = null;
            } else if (!RotationUtil.b(126426268413036L, (Entity)var12, (double)fov.L())) {
                this.g = null;
            } else {
                this.J(92947820359428L, var12);
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        AimAssistBinder.u(var3, this);
}
    private EntityLivingBase L(long var1) {
        float var7 = range.L();
        float var8 = fov.L();
        boolean var9 = players.c();
        boolean var10 = mobs.c();
        boolean var11 = animals.c();
        boolean var12 = bosses.c();
        boolean var13 = friends.c();
        boolean var14 = enemies.c();
        boolean var15 = teammates.c();
        boolean var16 = bots.c();
        List<EntityLivingBase> var17 = EntityUtil.K(EntityUtil.F(var7 > 3.0f ? 3.0 : (double)var7, 84864282554303L, var8), var9, 127230230889546L, var10, var11, var12, var13, var14, var15, var16);
        if (var7 > 3.0f && var17.isEmpty()) {
            var17 = EntityUtil.K(EntityUtil.F(var7, 84864282554303L, var8), var9, 127230230889546L, var10, var11, var12, var13, var14, var15, var16);
}
        if (ignoreBehindWall.c()) {
            var17.removeIf(var1x -> RaytraceUtil.V((Entity)var1x, 140537582766428L, var7));
}
        if (var17.isEmpty()) {
            return null;
}
        switch (sort.Y()) {
            case "HEALTH": {
                var17.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
                break;
}
            case "DISTANCE": {
                var17.sort(Comparator.comparingDouble(arg_0 -> ((EntityPlayerSP)AimAssist.f.thePlayer).getDistanceToEntity(arg_0)));
                break;
}
            case "VIEW": {
                var17.sort(Comparator.comparingDouble(RotationUtil::g));
                break;
}
            case "HURT_TIME": {
                var17.sort(Comparator.comparingInt(var0 -> var0.hurtTime));
                break;
}
            case "ARMOR": {
                var17.sort(Comparator.comparingInt(EntityLivingBase::getTotalArmorValue));
}
}
        return (EntityLivingBase)var17.get(0);
}
    public AimAssist(long var1) {
        super((k ^ var1) ^ 101115674713218L);
        this.declare("AimAssist", Category.Combat, "Help you aim BETTER when you click");
        var1 = k ^ var1;
        this.g = null;
    }
    private static void zkm$clinit() {
        try {
            long var0 = k ^ 130649753332591L;
            byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var3 = 1; var3 < 8; ++var3) {
                var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
            }
            Cipher var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var2.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var9 = new String[5];
            int var7 = 0;
            String var6 = "=\u00ea\u0016\u00e0\u0013\u0082:\u0081\u00dbO\u0081\u008a\u0010;g\u00c3\u001d\u00e4H\u00a4\u00d7\u0018\b;\u00de\u00d7\f\u00bfsn\u00c0d\u0010\u00ffW\u0094g'\u008f@\u0080\u0098\u00e9\u00ef\b`\u00c0[\u00f9\u0010R\u00acQL\u00d6a\u0002\u00c1\u00ab\u000f\u00f3C\u0091\u00d7-2";
            int var8 = "=\u00ea\u0016\u00e0\u0013\u0082:\u0081\u00dbO\u0081\u008a\u0010;g\u00c3\u001d\u00e4H\u00a4\u00d7\u0018\b;\u00de\u00d7\f\u00bfsn\u00c0d\u0010\u00ffW\u0094g'\u008f@\u0080\u0098\u00e9\u00ef\b`\u00c0[\u00f9\u0010R\u00acQL\u00d6a\u0002\u00c1\u00ab\u000f\u00f3C\u0091\u00d7-2".length();
            int var5 = 32;
            int var13 = -1;
            block6: while (true) {
                String var14 = var6.substring(++var13, var13 + var5);
                int var10001 = -1;
                while (true) {
                    byte[] var10 = var2.doFinal(var14.getBytes("ISO-8859-1"));
                    String var20 = AimAssist.b(var10).intern();
                    switch (var10001) {
                        case 0: {
                            var9[var7++] = var20;
                            if ((var13 += var5) >= var8) {
                                m = var9;
                                n = new String[5];
                                return;
}
                            var5 = var6.charAt(var13);
                            break;
}
                        default: {
                            var9[var7++] = var20;
                            if ((var13 += var5) < var8) {
                                var5 = var6.charAt(var13);
                                continue block6;
}
                            var6 = "m\u009a\u00d7U\u008c\u00e3\u00a961\u0012\u00ab\u00b3\u007f\"\u00e4Q \u009b0(\u007f\u00ec\u00d6\u007ftt\u00b8\to\u0091\u00cf'\u0085\u00db\u00e7m\u0092\t\u0081\u009a)\u00efrG-{\u00c7\u00c4\u0091";
                            var8 = "m\u009a\u00d7U\u008c\u00e3\u00a961\u0012\u00ab\u00b3\u007f\"\u00e4Q \u009b0(\u007f\u00ec\u00d6\u007ftt\u00b8\to\u0091\u00cf'\u0085\u00db\u00e7m\u0092\t\u0081\u009a)\u00efrG-{\u00c7\u00c4\u0091".length();
                            var5 = 16;
                            var13 = -1;
}
}
                    var14 = var6.substring(++var13, var13 + var5);
                    var10001 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var11) {
            throw new RuntimeException(var11);
}
}
    static {
        k = 103167649702968L;
        zkm$clinit();
        lock = new BooleanSetting("Lock", false);
        breakBlocks = new BooleanSetting("Break-blocks", true);
        ignoreBehindWall = new BooleanSetting("Ignore-behind-wall", true);
        swordOnly = new BooleanSetting("Sword-only", false);
        players = new BooleanSetting("Players", true);
        mobs = new BooleanSetting("Mobs", false);
        animals = new BooleanSetting("Animals", false);
        bosses = new BooleanSetting("Bosses", false);
        friends = new BooleanSetting("Friends", false);
        enemies = new BooleanSetting("Enemies", true);
        teammates = new BooleanSetting("Teammates", false);
        bots = new BooleanSetting("Bots", false);
        range = new NumberSetting("Range", 6.0f, 0.1f, 10.0f, 0.1f);
        fov = new NumberSetting("FOV", 180.0f, 1.0f, 360.0f, 1.0f);
        horizontalSpeed = new NumberSetting("Horizontal-speed", 15.0f, 1.0f, 20.0f, 0.1f);
        verticalSpeed = new NumberSetting("Vertical-speed", 5.0f, 1.0f, 20.0f, 0.1f);
        sort = new ModeSetting("Sort", "DISTANCE", "HEALTH", "VIEW", "HURT_TIME", "ARMOR");
        targetSettings = new HeaderSetting("Target settings");
}
}