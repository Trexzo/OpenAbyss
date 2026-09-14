/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.Vec3
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ESPBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.EntityRendererAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.combat.KillAura;
import Abyss.module.impl.configuration.Teams;
import Abyss.module.impl.configuration.Theme;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.EntityUtil;
import Abyss.util.render.RenderUtil;
import java.io.UnsupportedEncodingException;
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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class ESP
extends Module
implements EventSubscriber {
    private static long public static BooleanSetting friends;
    public static ModeSetting mode;
    public static ModeSetting healthBar;
    private static Map g;
    public static ColorSetting customColor;
    public static BooleanSetting showSelf;
    public static BooleanSetting mobs;
    public static BooleanSetting bots;
    private static Map n;
    public static BooleanSetting teammates;
    public static BooleanSetting bosses;
    public static BooleanSetting players;
    public static HeaderSetting targetSettings;
    public static ModeSetting color;
    private static String[] e;
    private static String[] r;
    private static long[] h;
    private static Object[] o;
    public static BooleanSetting animals;
    private final List<EntityLivingBase> L;
    public static BooleanSetting enemies;
    public static BooleanSetting hideTeammatesHealthBar;
        public static NumberSetting offset;

    @Override
    public final void x(long var1, EventBus var3) {
        ESPBinder.Y(var3, this);
}
    public void l(EntityLivingBase var1, long var2, int var4, double var5, float var7, ScaledResolution var8, boolean var9) {
        if (RenderUtil.l((Entity)var1)) {
            EntityRendererAccessor.k(ESP.f.field_71460_t, var7, 0);
            double var11 = var1.field_70142_S + (var1.field_70165_t - var1.field_70142_S) * (double)var7 - ESP.f.func_175598_ae().field_78730_l;
            double var13 = var1.field_70137_T + (var1.field_70163_u - var1.field_70137_T) * (double)var7 - ESP.f.func_175598_ae().field_78731_m;
            double var15 = var1.field_70136_U + (var1.field_70161_v - var1.field_70136_U) * (double)var7 - ESP.f.func_175598_ae().field_78728_n;
            AxisAlignedBB var17 = var1.func_174813_aQ().func_72314_b(0.1 + var5, 0.1 + var5, 0.1 + var5).func_72317_d(var11 - var1.field_70165_t, var13 - var1.field_70163_u, var15 - var1.field_70161_v);
            Vec3[] var10000 = new Vec3[]{new Vec3(var17.field_72340_a, var17.field_72338_b, var17.field_72339_c), new Vec3(var17.field_72340_a, var17.field_72337_e, var17.field_72339_c), new Vec3(var17.field_72336_d, var17.field_72338_b, var17.field_72339_c), new Vec3(var17.field_72336_d, var17.field_72337_e, var17.field_72339_c), new Vec3(var17.field_72340_a, var17.field_72338_b, var17.field_72334_f), new Vec3(var17.field_72340_a, var17.field_72337_e, var17.field_72334_f), new Vec3(var17.field_72336_d, var17.field_72338_b, var17.field_72334_f), new Vec3(var17.field_72336_d, var17.field_72337_e, var17.field_72334_f)};
            Vec3[] var18 = var10000;
            double var19 = Double.MAX_VALUE;
            double var21 = Double.MAX_VALUE;
            double var23 = -1.7976931348623157E308;
            double var25 = -1.7976931348623157E308;
            boolean var27 = false;
            for (Vec3 var31 : var18) {
                Vec3 var32 = RenderUtil.I(var8.func_78325_e(), var31.field_72450_a, var31.field_72448_b, var31.field_72449_c);
                if (var32 == null || var32.field_72449_c <= 0.0 || var32.field_72449_c >= 1.0) continue;
                var27 = true;
                var19 = Math.min(var19, var32.field_72450_a);
                var21 = Math.min(var21, var32.field_72448_b);
                var23 = Math.max(var23, var32.field_72450_a);
                var25 = Math.max(var25, var32.field_72448_b);
}
            if (var27) {
                ESP.f.field_71460_t.func_78478_c();
                var19 = Math.max(0.0, var19);
                var21 = Math.max(0.0, var21);
                var23 = Math.min((double)var8.func_78326_a(), var23);
                var25 = Math.min((double)var8.func_78328_b(), var25);
                float var38 = (float)(var4 >> 16 & 0xFF) / 255.0f;
                float var39 = (float)(var4 >> 8 & 0xFF) / 255.0f;
                float var40 = (float)(var4 & 0xFF) / 255.0f;
                GL11.glPushMatrix();
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2929);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                if (var9) {
                    GL11.glColor4f((float)var38, (float)var39, (float)var40, (float)0.25f);
                    GL11.glBegin((int)7);
                    GL11.glVertex2d((double)var19, (double)var21);
                    GL11.glVertex2d((double)var23, (double)var21);
                    GL11.glVertex2d((double)var23, (double)var25);
                    GL11.glVertex2d((double)var19, (double)var25);
                    GL11.glEnd();
}
                GL11.glLineWidth((float)2.5f);
                GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.95f);
                GL11.glBegin((int)2);
                GL11.glVertex2d((double)var19, (double)var21);
                GL11.glVertex2d((double)var23, (double)var21);
                GL11.glVertex2d((double)var23, (double)var25);
                GL11.glVertex2d((double)var19, (double)var25);
                GL11.glEnd();
                GL11.glLineWidth((float)1.3f);
                GL11.glColor4f((float)var38, (float)var39, (float)var40, (float)1.0f);
                GL11.glBegin((int)2);
                GL11.glVertex2d((double)var19, (double)var21);
                GL11.glVertex2d((double)var23, (double)var21);
                GL11.glVertex2d((double)var23, (double)var25);
                GL11.glVertex2d((double)var19, (double)var25);
                GL11.glEnd();
                GL11.glDisable((int)2848);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2929);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glPopMatrix();
}
}
}
    private void r(EntityLivingBase var1, long var2, ScaledResolution var4, float var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!KillAura.e(139955413975329L, var1) && RenderUtil.l((Entity)var1)) {
            int var24;
            switch (color.Y()) {
                case "THEME": {
                    var24 = Theme.S(0.0, 35338930340239L);
                    break;
}
                case "THEME_CUSTOM": {
                    var24 = Theme.X(65301174328177L, 0.0);
                    break;
}
                case "TEAM": {
                    var24 = Teams.d((short)0, var1);
                    break;
}
                default: {
                    var24 = customColor.k(96531491288662L);
}
}
            switch (mode.Y()) {
                case "2D": {
                    RenderUtil.N(17533, (Entity)var1, var24, var5);
                    break;
}
                case "2D_BOX": {
                    this.l(var1, 138240708914945L, var24, 0.0, var5, var4, false);
                    break;
}
                case "2D_BOX_FILL": {
                    this.l(var1, 138240708914945L, var24, 0.0, var5, var4, true);
                    break;
}
                case "3D": {
                    RenderUtil.A((Entity)var1, 9401974101981L, var24, 1.5f, 0.0);
                    break;
}
                case "3D_BIG": {
                    RenderUtil.A((Entity)var1, 9401974101981L, var24, 1.5f, 0.2);
                    break;
}
                case "3D_BIG_FILL": {
                    RenderUtil.A$fill((Entity)var1, 9401974101981L, var24, 1.5f, 0.2);
                    break;
}
                case "OUTLINE": {
                    RenderUtil.A((Entity)var1, 9401974101981L, var24, 3.0f, 0.0);
}
}
}
}
    public ESP(long var1) {
        super(a ^ var1 ^ 0x12FB3ADEF555L);
        this.declare("ESP", Category.Visual_utility, "Aka \"Extra sensory perception\"", new Setting[0]);
        var1 = a ^ var1;
        this.L = new ArrayList<EntityLivingBase>();
}
    private static void a() {
        ESP.o[0] = "?`XF{\u0017\u0003";
        ESP.o[1] = Long.TYPE;
        ESP.r[1] = "java/lang/Long";
        ESP.o[2] = Boolean.TYPE;
        ESP.r[2] = "java/lang/Boolean";
        ESP.o[3] = ")g'uil7o=:\u000bp0r";
        ESP.o[4] = "A}+{BrA}<'N}[6:;[r[aq\u0010AoFl&";
        ESP.o[5] = "O>\u0012K\u0005cO>\u0005\u0017\tlUu\u0003\u000b\u001ccU\"H\u0015\u0004kX>\u0014K-dU2\u0012\u001c8f@\"\u0003\u0017";
        ESP.o[6] = "y\u0001\u0003tVVy";
        ESP.o[7] = ",\nKUu>\u001b\u001dO_8\u001a\f\u0016\u0015C";
        ESP.o[8] = "fuYEV\u0004k";
        ESP.o[9] = Void.TYPE;
        ESP.r[9] = "java/lang/Void";
        ESP.o[10] = "o\u001a+R,[d\u0015:\u001dMUo\u001e>G";
        ESP.o[11] = "Y\u001d2\\\u000fxBR0=\b\u0012\n\u0012cE\u0001.\u000fMpGc.\u000f]kT\u000f+\u000e\u0011a=ZhASa\u0000\bi]\u001c\n\u0004\u0019`MF7V\u0018|\u0002-3G\u0011lX\u0010aF\r#3\u0014pO\u001dy\u000eFqSR\u0012\nWxC\b/XVd\fc";
        ESP.o[12] = "3zQ1p/paJ.L\u001c\u000f>\u0016+s(eyU:+M6<@9.q3cS;Lpv~L83!~>MAr#adB$**hj)";
        ESP.o[13] = "Vg1\u0004\u0017\u0011M(3e&{\u0005h`\u001d\u0019G\u00007s\u001f{BF%w\u000eF\u0010G98eA\u0017Z8v\u0019\tGS:\t";
        ESP.o[14] = "2py[Ow)?{:Z\u001dcp.F\u0019%:;|[#$\"2?Q\u001ev#.p:\u001ag*>*\u0007Hf6qA\u0003Yo&+|QXsi@x@Qc3}*AM,Xy;H]ve+:T\u0012\u001da:3DH 3;/\u000b#$\"2?Q\u001ev#.p:\u001ag*>*\u0007Hf6qA\u0003\u001ct \"}\u0006Cg\"@x@Qc3}*AM,X";
}
    public void onPostTick(int var1, char var2, PostTickEvent var3, short var4) {
        long var5 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ a;
        long var9 = var5 ^ 0x3EC7E69DA754L;
        long var11 = var5 ^ 0x3115D25435A6L;
        this.L.clear();
        boolean var13 = players.c();
        boolean var14 = mobs.c();
        boolean var15 = animals.c();
        boolean var16 = bosses.c();
        boolean var17 = friends.c();
        boolean var18 = enemies.c();
        boolean var19 = teammates.c();
        boolean var20 = bots.c();
        boolean var21 = var13 && !var14 && !var15 && !var16;
        List var22 = EntityUtil.U(var21);
        int var24 = var22.size();
        for (int var23 = 0; var23 < var24; ++var23) {
            EntityLivingBase var25 = (EntityLivingBase)var22.get(var23);
            if (!(var21 ? EntityUtil.c(var11, (EntityPlayer)var25, var17, var18, var19, var20) : EntityUtil.q((Entity)var25, var13, var14, var15, var16, var17, var18, var19, var20, var9))) continue;
            this.L.add(var25);
}
}
    @Override
    public void A(long var1) {
        this.L.clear();
}
    @Override
    public String g(long var1) {
        return mode.Y();
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
    private void E(long var1, EntityLivingBase var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!hideTeammatesHealthBar.c() || !Teams.g(0L, (Entity)var3)) {
            switch (healthBar.Y()) {
                case "NORMAL": {
                    RenderUtil.a(var3, 106028892044707L, offset.L(), 6.0f, 23);
                    break;
}
                case "THIN": {
                    RenderUtil.a(var3, 106028892044707L, offset.L(), 4.0f, 21);
}
}
}
}
    public void onRender3D(char var1, int var2, Render3DEvent var3, short var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var13;
        long var5 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var4 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x4BE84B79A306L;
        long var9 = var5 ^ 0x493684154B7EL;
        boolean var11 = mode.R("2D_BOX") || mode.R("2D_BOX_FILL");
        ArrayList<Object> var12 = new ArrayList<Object>(this.L.size());
        int var14 = this.L.size();
        for (var13 = 0; var13 < var14; ++var13) {
            EntityLivingBase var15 = this.L.get(var13);
            if (var11) {
                this.E(var7, var15);
                var12.add(var15);
                continue;
}
            this.r(var15, var9, var3.O, var3.j);
            this.E(var7, var15);
}
        if (showSelf.c() && ESP.f.field_71474_y.field_74320_O != 0) {
            if (var11) {
                this.E(var7, (EntityLivingBase)ESP.f.field_71439_g);
                var12.add(ESP.f.field_71439_g);
            } else {
                this.r((EntityLivingBase)ESP.f.field_71439_g, var9, var3.O, var3.j);
                this.E(var7, (EntityLivingBase)ESP.f.field_71439_g);
}
}
        if (var11) {
            int var17 = var12.size();
            for (var13 = 0; var13 < var17; ++var13) {
                EntityLivingBase var18 = (EntityLivingBase)var12.get(var13);
                this.r(var18, var9, var3.O, var3.j);
}
}
}
                Cipher var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var13.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var20 = new String[9];
            int var18 = 0;
            String var17 = "\u00d5\u00e2\u0089m\u00a7SC\u00e1e\u0091s\u00c3\u00a8u\u00d1A\u00102\u00980C\u00a5\u00afe\u001d[\u00d4\u008c?\u0000\u009d\u0018!\u0010\u00849\u00ec(\u0003\u00d8?\u00bf\u00d8\u00b0\u0084%v\u0097\u00df\u0094\u0010\u00ba\u0000l\u00a1\u00dd;V\u0004\u00d1:\u0096\u00a97\u0007\u00f6\u00f7 \u00f9\f\u00d36\u00a6\u00a642\u00df\u00a5]\u0019\u008d\u00c6\u00f9\u008bI}\u0098\u00ee\u00e7mj\u008fzq3<\u00d9\u0019\u000f\u008d\u0010hdO\u008f\u00bayJ?\u00c9\u00cf\u00a3\u00e9T\u00f1\u00e5\u0001\u0010\u0086\u0083k\u00ec[\u00e5\u00a0\u00fb\u00c6\u0094\u00c1\u00ee\u00be\u00c5+W";
            int var19 = "\u00d5\u00e2\u0089m\u00a7SC\u00e1e\u0091s\u00c3\u00a8u\u00d1A\u00102\u00980C\u00a5\u00afe\u001d[\u00d4\u008c?\u0000\u009d\u0018!\u0010\u00849\u00ec(\u0003\u00d8?\u00bf\u00d8\u00b0\u0084%v\u0097\u00df\u0094\u0010\u00ba\u0000l\u00a1\u00dd;V\u0004\u00d1:\u0096\u00a97\u0007\u00f6\u00f7 \u00f9\f\u00d36\u00a6\u00a642\u00df\u00a5]\u0019\u008d\u00c6\u00f9\u008bI}\u0098\u00ee\u00e7mj\u008fzq3<\u00d9\u0019\u000f\u008d\u0010hdO\u008f\u00bayJ?\u00c9\u00cf\u00a3\u00e9T\u00f1\u00e5\u0001\u0010\u0086\u0083k\u00ec[\u00e5\u00a0\u00fb\u00c6\u0094\u00c1\u00ee\u00be\u00c5+W".length();
            int var16 = 16;
            int var25 = -1;
            block9: while (true) {
                String var26 = var17.substring(++var25, var25 + var16);
                int var10001 = -1;
                while (true) {
                    byte[] var21 = var13.doFinal(var26.getBytes("ISO-8859-1"));
                    String var37 = ESP.b(var21).intern();
                    switch (var10001) {
                        case 0: {
                            var20[var18++] = var37;
                            if ((var25 += var16) >= var19) {
                                d = var20;
                                e = new String[9];
                                n = new HashMap(13);
                                var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var1 = 1; var1 < 8; ++var1) {
                                    var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
}
                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var6 = new long[20];
                                int var3 = 0;
                                String var4 = "\\\u00da/\r$\u0018W\u00bc\u00b2d\u00ccY\u00df\u00b6\u00da\u00ffEKQ\u001e\u001b\u00f4\u007f\u001bj\u00c8\u0000d#>+o\u00f2>'\u00f7\u00a6\u00052\u00ab\u0011+\u00d4\u00c6U\u00b0\b\u00a0d\u008e\u009d\b\u00b4l\u009f\u00dc\u0001\u008e\u0013\u009d\u00b91\t\u000f\u00b2f\u00f3y\u0083<\u00fe~\u00a6\u00c3\u001e\u00d4\u0085\u00ac\u00f9u\u00e0\u001dg\u001a\u008d\u009c\u000f\u001dt\u00a8Bo\u00f5\u00b1\u008b\u008c\u00c6\u00d0\u00e4\u00e8\u0085\u008d;\u00b6\u00a4K;\u009fvT\u0002i\u00dd\u00ca\u0098\u001f\u00c0l\u00c9\u009bZ\u00f1\u00c8\u00d3j\u0080m\u00d4\u0010\u00edKlP\u001e[\u00a7/*\b.?\u00e2L:";
                                int var5 = "\\\u00da/\r$\u0018W\u00bc\u00b2d\u00ccY\u00df\u00b6\u00da\u00ffEKQ\u001e\u001b\u00f4\u007f\u001bj\u00c8\u0000d#>+o\u00f2>'\u00f7\u00a6\u00052\u00ab\u0011+\u00d4\u00c6U\u00b0\b\u00a0d\u008e\u009d\b\u00b4l\u009f\u00dc\u0001\u008e\u0013\u009d\u00b91\t\u000f\u00b2f\u00f3y\u0083<\u00fe~\u00a6\u00c3\u001e\u00d4\u0085\u00ac\u00f9u\u00e0\u001dg\u001a\u008d\u009c\u000f\u001dt\u00a8Bo\u00f5\u00b1\u008b\u008c\u00c6\u00d0\u00e4\u00e8\u0085\u008d;\u00b6\u00a4K;\u009fvT\u0002i\u00dd\u00ca\u0098\u001f\u00c0l\u00c9\u009bZ\u00f1\u00c8\u00d3j\u0080m\u00d4\u0010\u00edKlP\u001e[\u00a7/*\b.?\u00e2L:".length();
                                int var2 = 0;
                                block12: while (true) {
                                    var10001 = var2;
                                    byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                                    long[] var29 = var6;
                                    var10001 = var3++;
                                    long var41 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                    int var44 = -1;
                                    while (true) {
                                        long var8 = var41;
                                        byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                        long var46 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                        switch (var44) {
                                            case 0: {
                                                var29[var10001] = var46;
                                                if (var2 < var5) break;
                                                h = var6;
                                                return;
}
                                            default: {
                                                var29[var10001] = var46;
                                                if (var2 < var5) continue block12;
                                                var4 = "\u00f8\u00a5,\u00ff\u00ee\b\u0098\u00dc\u00bbk\u00d3\u008f\u00a1'\u0095\u00f0";
                                                var5 = "\u00f8\u00a5,\u00ff\u00ee\b\u0098\u00dc\u00bbk\u00d3\u008f\u00a1'\u0095\u00f0".length();
                                                var2 = 0;
}
}
                                        int var35 = var2;
                                        var7 = var4.substring(var35, var2 += 8).getBytes("ISO-8859-1");
                                        var29 = var6;
                                        var10001 = var3++;
                                        var41 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                        var44 = 0;
}
                                    break;
}
}
                            var16 = var17.charAt(var25);
                            break;
}
                        default: {
                            var20[var18++] = var37;
                            if ((var25 += var16) < var19) {
                                var16 = var17.charAt(var25);
                                continue block9;
}
                            var17 = "^%\u00ea\u0007\u0083gfB\u00d5Z\u001f\u00e9\u00d5\u00a7j\u0019\u0010\u001e\u009f\u00a7\u00dc\u00bc\u007f\u009f\u00a6Q<Y\u0002\u000fq!\u0014";
                            var19 = "^%\u00ea\u0007\u0083gfB\u00d5Z\u001f\u00e9\u00d5\u00a7j\u0019\u0010\u001e\u009f\u00a7\u00dc\u00bc\u007f\u009f\u00a6Q<Y\u0002\u000fq!\u0014".length();
                            var16 = 16;
                            var25 = -1;
}
}
                    var26 = var17.substring(++var25, var25 + var16);
                    var10001 = 0;
}
                break;
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var22) {
            throw new RuntimeException(var22);
}
}
    static {
        customColor = new ColorSetting("Custom-color", "FFFFFF");
        showSelf = new BooleanSetting("Show-self", false);
        hideTeammatesHealthBar = new BooleanSetting("Hide-teammates-health-bar", true);
        players = new BooleanSetting("Players", true);
        mobs = new BooleanSetting("Mobs", false);
        animals = new BooleanSetting("Animals", false);
        bosses = new BooleanSetting("Bosses", false);
        friends = new BooleanSetting("Friends", true);
        enemies = new BooleanSetting("Enemies", true);
        teammates = new BooleanSetting("Teammates", true);
        bots = new BooleanSetting("Bots", false);
        offset = new NumberSetting("Offset", 0.0f, -50.0f, 25.0f, 1.0f);
        mode = new ModeSetting("Mode", "2D", "2D_BOX", "2D_BOX_FILL", "3D", "3D_BIG", "3D_BIG_FILL", "OUTLINE");
        color = new ModeSetting("Color", "TEAM", "THEME", "THEME_CUSTOM", "CUSTOM");
        healthBar = new ModeSetting("Health-bar", "NORMAL", "THIN", "NONE");
        targetSettings = new HeaderSetting("Target settings");
}
}