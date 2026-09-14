/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.visual_utility;

import Abyss.enums.MinecraftColor;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.TargetHUDBinder;
import Abyss.event.events.AttackTargetEntityEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.combat.KillAura;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.configuration.Theme;
import Abyss.module.impl.visual_utility.TargetHUDSnapshot;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.AnimatedFloat;
import Abyss.util.CombatUtil;
import Abyss.util.EntityUtil;
import Abyss.util.MathUtil;
import Abyss.util.RaytraceUtil;
import Abyss.util.TimerUtil;
import Abyss.util.render.ColorUtil;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import Abyss.util.render.ShaderRenderer;
import Abyss.util.render.abyss.AbyssShaderUtils;
import Abyss.util.render.abyss.ColorBlendUtil;
import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.FontRenderer;
import Abyss.util.render.abyss.RenderingUtils;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class TargetHUD
extends Module
implements EventSubscriber {
    public static BooleanSetting bots;
    public static BooleanSetting mobs;
    public static HeaderSetting targetSettings;
    public static BooleanSetting chatPreview;
    public static NumberSetting range;
    private static DecimalFormat S;
    private static String[] k;
    private static DecimalFormat L;
    private EntityLivingBase K;
    public static ModeSetting style;
    public static PercentageSetting backgroundOpacity;
    public static BooleanSetting onlyWhenUsingKillaura;
    public static BooleanSetting enemies;
    private float d;
    public static BooleanSetting players;
    private EntityLivingBase E;
    public static NumberSetting scale;
    private ResourceLocation n;
    public static BooleanSetting animals;
    public static ColorSetting customColor;
    public static NumberSetting x2;
    public static ModeSetting color;
    public static BooleanSetting teammates;
    private float U;
    public static BooleanSetting healthAnimations;
    private static long c;
    public static BooleanSetting friends;
    public static BooleanSetting outline;
    public static NumberSetting y;
    public static BooleanSetting indicators;
    public static BooleanSetting textShadow;
    private float G;
    private final AnimatedFloat akrienHealthAnim = new AnimatedFloat();
    private final AnimatedFloat astolfoHealthAnim = new AnimatedFloat();
    private final AnimatedFloat riseHealthAnim = new AnimatedFloat();
    private final AnimatedFloat tenacityHealthAnim = new AnimatedFloat();
    private final AnimatedFloat oldTenacityHealthAnim = new AnimatedFloat();
    private final List<RiseParticle> riseParticles = new ArrayList<RiseParticle>();
    private boolean riseSentParticles;
    private long riseLastParticleMs;
    public static BooleanSetting customHealthColor;
    private final TimerUtil x;
    public static ModeSetting targetMode;
    private final TimerUtil v;
    public static NumberSetting stayTime;
    public static BooleanSetting bosses;
    private static boolean abyssThudFontReady;
    private boolean thudDragging;
    private float thudDragOffsetX;
    private float thudDragOffsetY;

    public void onAttackTargetEntity(long var1, AttackTargetEntityEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (targetMode.R("HIT") && var3.w instanceof EntityLivingBase && this.A(1893608662326L, (EntityLivingBase)var3.w)) {
            this.E = this.K;
            this.K = (EntityLivingBase)var3.w;
            this.v.W();
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        TargetHUDBinder.Y(var3, this);
}
    private void C(float var1, float var2, float var3, float var4, long var5, int var7) {
        long var8 = var5 ^ 0x3D3C523C9075L;
        if (var7 != 0) {
            RenderUtil.l(var7, var8);
            GL11.glBegin((int)9);
            GL11.glVertex2f((float)var1, (float)var2);
            GL11.glVertex2f((float)var1, (float)var4);
            GL11.glVertex2f((float)var3, (float)var4);
            GL11.glVertex2f((float)var3, (float)var2);
            GL11.glEnd();
            GlStateManager.func_179117_G();
}
}
    private void q(float var1, float var2, float var3, float var4, int var5, float var6, int var7, long var8) {
        long var10 = ((long)var5 << 32 | var8 << 32 >>> 32) ^ c;
        long var12 = var10 ^ 0x11487AD3656CL;
        if (var7 != 0) {
            RenderUtil.l(var7, var12);
            GL11.glLineWidth((float)var6);
            GL11.glEnable((int)2848);
            GL11.glHint((int)3154, (int)4354);
            GL11.glBegin((int)1);
            GL11.glVertex2f((float)var1, (float)var2);
            GL11.glVertex2f((float)var1, (float)var4);
            GL11.glVertex2f((float)var3, (float)var4);
            GL11.glVertex2f((float)var3, (float)var2);
            GL11.glVertex2f((float)var1, (float)var2);
            GL11.glVertex2f((float)var3, (float)var2);
            GL11.glVertex2f((float)var1, (float)var4);
            GL11.glVertex2f((float)var3, (float)var4);
            GL11.glEnd();
            GL11.glDisable((int)2848);
            GL11.glLineWidth((float)2.0f);
            GlStateManager.func_179117_G();
}
}
    private ResourceLocation j(EntityLivingBase var1) {
        NetworkPlayerInfo var2;
        if (var1 instanceof EntityPlayer && (var2 = f.func_147114_u().func_175104_a(var1.func_70005_c_())) != null) {
            return var2.func_178837_g();
}
        return null;
}
    private void W(CustomFont var1, char var2, String var3, float var4, float var5, float var6, int var7, short var8, float var9, float var10, int var11) {
        long var12 = ((long)var2 << 48 | (long)var7 << 32 >>> 16 | (long)var8 << 48 >>> 48) ^ c;
        long var14 = var12 ^ 0x335F42876D6AL;
        long var16 = var12 ^ 0x444636135ECEL;
        float var18 = Math.min(var9, var6 / Math.max(var1.R(var3, var16), 1.0f));
        if (var18 < var10) {
            var18 = var10;
            String var19 = "...";
            while (var3.length() > 1) {
                StringBuilder stringBuilder = new StringBuilder();
                if (!(var1.R(stringBuilder.append(var3).append(var19).toString(), var16) * var18 > var6)) break;
                var3 = var3.substring(0, var3.length() - 1);
}
            var3 = var3 + var19;
}
        this.O(var1, var3, var4, var5, var18, var11, var14);
}
    private Color k(Color var1, int var2) {
        return new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), var2);
}
    private boolean A(long var1, EntityLivingBase var3) {
        return EntityUtil.q((Entity)var3, players.c(), mobs.c(), animals.c(), bosses.c(), friends.c(), enemies.c(), teammates.c(), bots.c(), 21816078198602L);
}
    public TargetHUD(long var1) {
        super(c ^ var1 ^ 0x7B3FCAB1BDCEL);
        this.declare("TargetHUD", Category.Visual_utility, "Show basic information about the current attacking target", new Setting[0]);
        var1 = c ^ var1;
        this.v = new TimerUtil();
        this.x = new TimerUtil();
        this.K = null;
        this.E = null;
        this.n = null;
        this.U = 0.0f;
        this.d = 0.0f;
        this.G = 0.0f;
}
    private String T(Entity var1) {
        return var1.func_145748_c_().func_150254_d().replaceAll("\u00a7\\S$", "").replaceAll("(?i)\u00a7r", "\u00a7f").trim();
}
    private void L(EntityPlayer var1, float var2, float var3) {
        ArrayList<ItemStack> var4 = new ArrayList<ItemStack>();
        for (int var5 = 3; var5 >= 0; --var5) {
            ItemStack var6 = var1.field_71071_by.field_70460_b[var5];
            if (var6 == null) continue;
            var4.add(var6);
}
        ItemStack var7 = var1.func_70694_bm();
        if (var7 != null) {
            var4.add(var7);
}
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)0.55f, (float)0.55f, (float)1.0f);
        for (int var8 = 0; var8 < var4.size(); ++var8) {
            RenderUtil.m((ItemStack)var4.get(var8), (int)((var2 + (float)var8 * 10.0f) / 0.55f), (int)(var3 / 0.55f));
}
        GlStateManager.func_179121_F();
}
    private void O(CustomFont var1, String var2, float var3, float var4, float var5, float var6, int var7, long var8) {
        var8 = c ^ var8;
        long var10 = var8 ^ 0x3E7CE63768F5L;
        long var12 = var8 ^ 0x496592A35B51L;
        var6 = Math.min(var6, var5 / Math.max(var1.R(var2, var12), 1.0f));
        float var14 = var1.R(var2, var12) * var6;
        this.O(var1, var2, var3 + (var5 - var14) / 2.0f, var4, var6, var7, var10);
}
    private Color D(float var1) {
        if (var1 >= 0.9f) {
            return Color.GREEN;
}
        if (var1 >= 0.55f) {
            return this.n((var1 - 0.55f) / 0.35f, Color.YELLOW, Color.GREEN);
}
        if (var1 >= 0.45f) {
            return Color.YELLOW;
}
        return var1 >= 0.1f ? this.n((var1 - 0.1f) / 0.35f, Color.RED, Color.YELLOW) : Color.RED;
}
    private void Z(long var1, CustomFont var3, int var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var1 = c ^ var1;
        long var5 = var1 ^ 0x77E5F7A44141L;
        int var7 = (int)((var1 ^ 0x16020CBED7ADL) >>> 48);
        int var8 = (int)((var1 ^ 0x16020CBED7ADL) << 16 >>> 32);
        int var9 = (int)((var1 ^ 0x16020CBED7ADL) << 48 >>> 48);
        long var10 = var1 ^ 0x5661B4AFA578L;
        long var14 = var1 ^ 0x76E0ABAEE551L;
        long var16 = var1 ^ 0x31DD1267C10FL;
        long var18 = var1 ^ 0x4DF122220C38L;
        long var20 = var1 ^ 0x1B21A80ED232L;
        TargetHUDSnapshot var22 = this.H(var4, var16);
        float var23 = x2.L() / scale.L();
        float var24 = y.L() / scale.L();
        float var25 = 124.0f;
        float var26 = 48.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)scale.L(), (float)scale.L(), (float)1.0f);
        GlStateManager.func_179109_b((float)var23, (float)var24, (float)-450.0f);
        this.Y(0.0f, 0.0f, var5, var25, var26, var4);
        this.G(7, 8, 32);
        this.W(var3, (char)var7, TargetHUDSnapshot.k(var22), 44.0f, 5.0f, 75.0f, var8, (short)var9, 1.25f, 0.78f, -1);
        this.getRGB(44.0f, var14, 21.0f, 74.0f, 8.0f, TargetHUDSnapshot.j(var22), TargetHUDSnapshot.C(var22));
        this.O(var3, this.j(var22), 44.0f, 22.6f, 74.0f, 0.58f, -1, var20);
        this.O(var3, "HTK", 44.0f, 35.0f, 0.68f, -1, var10);
        this.O(var3, "\u00a7b" + TargetHUDSnapshot.H(var22), 59.0f, 35.0f, 0.68f, new Color(0, 210, 220, 230).getRGB(), var10);
        this.O(var3, CombatUtil.s(var18, this.K) ? " \u00a7aW" : " \u00a7c\u00a7lL", 108.0f, 33.0f, 1.0f, this.k(TargetHUDSnapshot.B(var22), 220).getRGB(), var10);
        GlStateManager.func_179121_F();
}
    private TargetHUDSnapshot H(int var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        ResourceLocation var15;
        var2 = c ^ var2;
        long var4 = var2 ^ 0x31D3B0A5CB52L;
        long var10 = var2 ^ 0xF10CAD3D288L;
        float var12 = TargetHUD.f.field_71439_g.func_110143_aJ() + TargetHUD.f.field_71439_g.func_110139_bj();
        float var13 = this.K.func_110139_bj();
        float var14 = this.K.func_110143_aJ() + var13;
        this.G = Math.max(this.K.func_110138_aP() + var13, 1.0f);
        if (this.K != this.E) {
            this.n = null;
            this.x.W();
            this.U = var14;
            this.d = var14;
}
        if (!healthAnimations.c() || this.x.Q(150L)) {
            this.U = this.d;
            this.d = var14;
            if (this.U != this.d) {
                this.x.W();
}
}
        if ((var15 = this.j(this.K)) != null) {
            this.n = var15;
}
        float var16 = Math.min(Math.max(this.x.s(), 0L), 150L);
        float var17 = Math.min(Math.max(MathUtil.k(this.d, this.U, var16 / 150.0f) / this.G, 0.0f), 1.0f);
        double var18 = this.K.func_110143_aJ() / this.K.func_110138_aP();
        Color var20 = var18 < 0.3 ? Color.RED : (var18 < 0.5 ? Color.ORANGE : (var18 < 0.7 ? Color.YELLOW : Color.GREEN));
        Color var21 = customHealthColor.c() ? new Color(var1) : var20;
        float var22 = Math.min(Math.max((var12 - var14 + 1.0f) / 2.0f, 0.0f), 1.0f);
        return new TargetHUDSnapshot(MinecraftColor.C(String.format("&r%s&r", this.T((Entity)this.K))), var14, var17, var21, CombatUtil.s(var10, this.K) ? Color.GREEN : Color.RED, CombatUtil.G(var4, this.K), MinecraftColor.C(String.format("&r&f%s%s\u2764&r", L.format(var14), var13 > 0.0f ? "&6" : "&c")), null);
}
    private void M(int var1, int var2, int var3) {
        GlStateManager.func_179126_j();
        GuiInventory.func_147046_a((int)var1, (int)var2, (int)var3, (float)18.0f, (float)4.0f, (EntityLivingBase)this.K);
        GlStateManager.func_179097_i();
}
    private Color n(float var1, Color var2, Color var3) {
        var1 = Math.min(Math.max(var1, 0.0f), 1.0f);
        return new Color((int)((float)var2.getRed() + var1 * (float)(var3.getRed() - var2.getRed())), (int)((float)var2.getGreen() + var1 * (float)(var3.getGreen() - var2.getGreen())), (int)((float)var2.getBlue() + var1 * (float)(var3.getBlue() - var2.getBlue())));
}
    public void onRender2D(long var1, Render2DEvent var3, short var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.handleDrag();
        long var5 = (var1 << 16 | (long)var4 << 48 >>> 48) ^ c;
        long var7 = var5 ^ 0x1E0D350F2622L;
        int var11 = (int)((var5 ^ 0x6E0B3CA48F8CL) >>> 32);
        long var12 = (var5 ^ 0x6E0B3CA48F8CL) << 32 >>> 32;
        long var16 = var5 ^ 0x641A97612DDL;
        long var18 = var5 ^ 0x3143EEDD652AL;
        long var20 = var5 ^ 0x3C8107845A76L;
        long var22 = var5 ^ 0x33CF0D639BC4L;
        long var24 = var5 ^ 0xD02D5D78843L;
        long var28 = var5 ^ 0x6EE7001409B9L;
        long var34 = var5 ^ 0x618B37CE12A7L;
        long var36 = var5 ^ 0x75A0E13B5347L;
        long var38 = var5 ^ 0x190F8148BE60L;
        CustomFont var40 = Font.F(0L);
        if (this.K != null) {
            switch (color.Y()) {
                case "THEME": {
                    int var41 = Theme.S(0.0, var28);
                    break;
}
                case "THEME_CUSTOM": {
                    int var41 = Theme.X(var36, 0.0);
                    break;
}
                default: {
                    int var41 = customColor.k(var38);
}
}
            switch (style.Y()) {
                case "AKRIEN": 
                case "ASTOLFO": 
                case "AUTUMN": 
                case "EXHIBITION": 
                case "NOVOLINE": 
                case "OLD_TENACITY": 
                case "RISE": 
                case "TENACITY": {
                    this.drawAbyssStyle(style.Y());
                    break;
}
                default: {
                    this.drawAbyssStyle("AUTUMN");
}
}
}
}
    private void getRGB(float var1, long var2, float var4, float var5, float var6, Color var7, float var8) {
        long var11 = var2 ^ 0xA925BDF3B5CL;
        RenderUtil.c(var11, var1, var4, var1 + var5, var4 + var6, ColorUtil.Z(0L, var7, 0.2f).getRGB());
        RenderUtil.c(var11, var1, var4, var1 + var5 * var8, var4 + var6, var7.getRGB());
}
    private void G(int var1, int var4, int var5) {
        if (this.n != null) {
            GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
            f.func_110434_K().func_110577_a(this.n);
            Gui.func_152125_a((int)var1, (int)var4, (float)8.0f, (float)8.0f, (int)8, (int)8, (int)var5, (int)var5, (float)64.0f, (float)64.0f);
            Gui.func_152125_a((int)var1, (int)var4, (float)40.0f, (float)8.0f, (int)8, (int)8, (int)var5, (int)var5, (float)64.0f, (float)64.0f);
            GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
}
}
    private void Y(float var1, float var2, long var3, float var5, float var6, int var7) {
        var3 = c ^ var3;
        int var10 = 255 * backgroundOpacity.k() / 100;
        Color var11 = new Color(var7);
        int var12 = new Color(8, 10, 12, Math.max(var10, 70)).getRGB();
        int var13 = outline.c() ? new Color(var11.getRed(), var11.getGreen(), var11.getBlue(), 145).getRGB() : new Color(255, 255, 255, 45).getRGB();
        RenderUtil.J(var1, var2, var1 + var5, var2 + var6, 6.0f, var12, var13, var13);
}
    private void E(long var1, CustomFont var3, int var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var1 = c ^ var1;
        long var5 = var1 ^ 0x426B53B1C858L;
        int var7 = (int)((var1 ^ 0x238CA8AB5EB4L) >>> 48);
        int var8 = (int)((var1 ^ 0x238CA8AB5EB4L) << 16 >>> 32);
        int var9 = (int)((var1 ^ 0x238CA8AB5EB4L) << 48 >>> 48);
        long var10 = var1 ^ 0x436E0FBB6C48L;
        long var12 = var1 ^ 0x453B6724816L;
        long var14 = var1 ^ 0x2EAF0C1B5B2BL;
        TargetHUDSnapshot var16 = this.H(var4, var12);
        float var17 = x2.L() / scale.L();
        float var18 = y.L() / scale.L();
        float var19 = 122.0f;
        float var20 = 46.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)scale.L(), (float)scale.L(), (float)1.0f);
        GlStateManager.func_179109_b((float)var17, (float)var18, (float)-450.0f);
        this.Y(0.0f, 0.0f, var5, var19, var20, var4);
        this.W(var3, (char)var7, TargetHUDSnapshot.k(var16), 43.0f, 6.0f, 74.0f, var8, (short)var9, 1.25f, 0.78f, -1);
        this.getRGB(43.0f, var10, 33.0f, 74.0f, 8.0f, TargetHUDSnapshot.j(var16), TargetHUDSnapshot.C(var16));
        this.O(var3, this.j(var16), 43.0f, 34.6f, 74.0f, 0.58f, -1, var14);
        if (this.K instanceof EntityPlayer) {
            this.L((EntityPlayer)this.K, 44.0f, 19.0f);
}
        this.M(22, 39, 18);
        GlStateManager.func_179121_F();
}
    private String j(TargetHUDSnapshot var1) {
        return L.format(TargetHUDSnapshot.m(var1)) + "/" + L.format(this.G);
}
    @Override
    public String g(long var1) {
        return targetMode.Y();
}
    public void onPreUpdate(PreUpdateEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (KillAura.H6 != null) {
            if (this.A(1893608662326L, KillAura.H6)) {
                this.E = this.K;
                this.K = KillAura.H6;
                this.v.W();
}
        } else {
            if (chatPreview.c() && TargetHUD.f.field_71462_r instanceof GuiChat) {
                this.E = this.K;
                this.K = TargetHUD.f.field_71439_g;
                return;
}
            if (onlyWhenUsingKillaura.c() && !KillAura.a && this.v.A(stayTime.L() * 1000.0f)) {
                this.E = this.K;
                this.K = null;
                return;
}
            if (targetMode.R("AIM")) {
                EntityLivingBase var6 = RaytraceUtil.Z((int)range.L());
                if (var6 != null && this.A(1893608662326L, var6)) {
                    this.E = this.K;
                    this.K = var6;
                    this.v.W();
                } else if (this.v.A(stayTime.L() * 1000.0f)) {
                    this.E = this.K;
                    this.K = null;
}
            } else if (targetMode.R("HIT") && this.v.A(stayTime.L() * 1000.0f)) {
                this.E = this.K;
                this.K = null;
}
}
}
    private void O(CustomFont var1, String var2, float var3, float var4, float var5, int var6, long var7) {
        long var9 = var7 ^ 0x89F76550721L;
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)var5, (float)var5, (float)1.0f);
        var1.v(var2, var3 / var5, var4 / var5, var6, var9, textShadow.c());
        GlStateManager.func_179121_F();
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void drawAbyssStyle(String mode) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        EntityLivingBase t2 = this.K;
        if (t2 == null) {
            return;
}
        if (!abyssThudFontReady && !(abyssThudFontReady = FontManager.warmStep())) {
            return;
}
        FontRenderer fr = FontManager.getSmall();
        float health = t2.func_110143_aJ();
        float max = Math.max(t2.func_110138_aP(), 1.0f);
        float pct = Math.max(0.0f, Math.min(1.0f, health / max));
        this.G += (pct - this.G) * 0.18f;
        float animated = Math.max(0.0f, Math.min(1.0f, this.G));
        String name = t2.func_70005_c_();
        float dist = TargetHUD.f.field_71439_g.func_70032_d((Entity)t2);
        int accent = Theme.S(0.0, 35338930340239L);
        int alphaVal = Math.max(70, 255 * backgroundOpacity.k() / 100);
        int bg = new Color(8, 10, 12, alphaVal).getRGB();
        int muted = -6052957;
        int accentDark = RenderingUtils.darkerClamped(accent, 0.6f) | 0xFF000000;
        ResourceLocation skin = this.j(t2);
        String hpText = L.format(health) + " HP";
        String pctText = Math.round(pct * 100.0f) + "%";
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)x2.L(), (float)y.L(), (float)0.0f);
        GlStateManager.func_179152_a((float)scale.L(), (float)scale.L(), (float)1.0f);
        try {
            GlStateManager.func_179097_i();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            GlStateManager.func_179141_d();
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (mode.equals("AUTUMN")) {
                Gui.func_73734_a((int)0, (int)0, (int)140, (int)40, (int)bg);
                Gui.func_73734_a((int)40, (int)15, (int)((int)(40.0f + 92.0f * animated)), (int)25, (int)accent);
                String hp = L.format(health / 2.0f);
                fr.drawStringWithShadow(hp, 86.0f - fr.getWidth(hp) / 2.0f, 16.0f, -1);
                fr.drawStringWithShadow(name, 40.0f, 2.0f, -1);
                GuiInventory.func_147046_a((int)13, (int)40, (int)20, (float)t2.field_70177_z, (float)t2.field_70125_A, (EntityLivingBase)t2);
            } else if (mode.equals("EXHIBITION")) {
                net.minecraft.client.gui.FontRenderer mcFont = TargetHUD.f.field_71466_p;
                double boxWidth = 40 + mcFont.func_78256_a(name);
                double renderWidth = Math.max(boxWidth, 120.0);
                RenderingUtils.rectangleBordered(-2.5, -2.5, renderWidth + 2.5, 42.5, 0.5, new Color(60, 60, 60).getRGB(), new Color(10, 10, 10).getRGB());
                RenderingUtils.rectangleBordered(-1.5, -1.5, renderWidth + 1.5, 41.5, 1.5, new Color(60, 60, 60).getRGB(), new Color(40, 40, 40).getRGB());
                RenderingUtils.rectangleBordered(0.0, 0.0, renderWidth, 40.0, 0.5, new Color(22, 22, 22).getRGB(), new Color(60, 60, 60).getRGB());
                RenderingUtils.rectangleBordered(2.0, 2.0, 38.0, 38.0, 0.5, new Color(0, 0, 0, 0).getRGB(), new Color(10, 10, 10).getRGB());
                RenderingUtils.rectangleBordered(2.5, 2.5, 37.5, 37.5, 0.5, new Color(17, 17, 17).getRGB(), new Color(48, 48, 48).getRGB());
                GlStateManager.func_179094_E();
                ScaledResolution exhScale = new ScaledResolution(f);
                int exhFactor = exhScale.func_78325_e();
                float exhTransX = x2.L();
                float exhTransY = y.L();
                float sv = scale.L();
                GL11.glScissor((int)((int)((exhTransX + 3.0f * sv) * (float)exhFactor)), (int)((int)(((float)exhScale.func_78328_b() - (exhTransY + 37.0f * sv)) * (float)exhFactor)), (int)((int)(34.0f * sv * (float)exhFactor)), (int)((int)(34.0f * sv * (float)exhFactor)));
                GL11.glEnable((int)3089);
                this.drawEntityOnScreenExhibition(t2);
                GL11.glDisable((int)3089);
                GlStateManager.func_179121_F();
                GlStateManager.func_179109_b((float)2.0f, (float)0.0f, (float)0.0f);
                mcFont.func_175063_a(name, 37.0f, 3.0f, -1);
                float[] exhFractions = new float[]{0.0f, 0.5f, 1.0f};
                Color[] exhColors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
                float exhAbsorption = t2.func_110139_bj();
                float exhProgress = health / (max + exhAbsorption);
                float exhRealProgress = health / max;
                Color exhCustomColor = health >= 0.0f ? ColorBlendUtil.blendColors(exhFractions, exhColors, exhRealProgress).brighter() : Color.RED;
                double exhWidth = Math.min(mcFont.func_78256_a(name), 60);
                exhWidth = (int)(exhWidth / 10.0) * 10;
                if (exhWidth < 60.0) {
                    exhWidth = 60.0;
}
                double exhHealthLoc = exhWidth * (double)exhProgress;
                RenderingUtils.rectangleBordered(37.0, 12.0, 39.0 + exhWidth, 16.0, 0.5, new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0).getRGB());
                RenderingUtils.drawRect(38.0f + (float)exhHealthLoc + 0.5f, 12.5f, 38.0f + (float)exhWidth + 0.5f, 15.5f, new Color(exhCustomColor.getRed(), exhCustomColor.getGreen(), exhCustomColor.getBlue(), 35).getRGB());
                RenderingUtils.drawRect(37.5f, 12.5f, 38.0f + (float)exhHealthLoc + 0.5f, 15.5f, exhCustomColor.getRGB());
                if (exhAbsorption > 0.0f) {
                    double exhAbsDiff = exhWidth * (double)(exhAbsorption / (max + exhAbsorption));
                    RenderingUtils.drawRect(38.0f + (float)exhHealthLoc + 0.5f, 12.5f, 38.0f + (float)exhHealthLoc + 0.5f + (float)exhAbsDiff, 15.5f, -2130728448);
}
                for (int i = 1; i < 10; ++i) {
                    double dThing = exhWidth / 10.0 * (double)i;
                    RenderingUtils.drawRect(38.0f + (float)dThing, 12.0f, 38.0f + (float)dThing + 0.5f, 16.0f, new Color(0, 0, 0).getRGB());
}
                mcFont.func_175063_a("HP: " + (int)health + " | Dist: " + (int)dist, 37.0f, 18.0f, -1);
                if (t2 instanceof EntityPlayer) {
                    this.L((EntityPlayer)t2, 37.0f, 28.0f);
}
                GlStateManager.func_179109_b((float)-2.0f, (float)0.0f, (float)0.0f);
            } else if (mode.equals("NOVOLINE")) {
                int w2 = Math.max(110, 74 + (int)fr.getWidth(name));
                Gui.func_73734_a((int)0, (int)0, (int)w2, (int)42, (int)RenderingUtils.withAlpha(-14145496, alphaVal));
                RenderingUtils.drawRoundedHead(1, 1, 40, 2.0f, skin);
                net.minecraft.client.gui.FontRenderer mcFont2 = TargetHUD.f.field_71466_p;
                mcFont2.func_175063_a(name, 44.0f, 9.0f, -1);
                Gui.func_73734_a((int)44, (int)22, (int)(w2 - 4), (int)33, (int)-1777003243);
                Gui.func_73734_a((int)44, (int)22, (int)((int)(44.0f + (float)(w2 - 48) * animated)), (int)33, (int)accent);
                fr.drawString(pctText, 44.0f + ((float)(w2 - 48) - fr.getWidth(pctText)) / 2.0f, 24.0f, -1);
            } else if (mode.equals("AKRIEN")) {
                double akArmor;
                FontRenderer boldFr = FontManager.get();
                int akW = (int)Math.max(100.0f, boldFr.getWidth(name) + 45.0f);
                float akH = 39.5f;
                float akHealthPct = Math.max(0.0f, Math.min(1.0f, (health + t2.func_110139_bj()) / (max + t2.func_110139_bj())));
                int akBg = new Color(0, 0, 0, 102).getRGB();
                RenderingUtils.drawRect(0.0f, 0.0f, akW, akH, akBg);
                RenderingUtils.drawRect(2.5f, 31.0f, akW - 2, 33.5f, akBg);
                RenderingUtils.drawRect(2.5f, 34.5f, akW - 2, 37.0f, akBg);
                float akEndWidth = (float)Math.max(0.0, ((double)akW - 3.5) * (double)akHealthPct);
                this.akrienHealthAnim.j(akEndWidth, 18);
                float akAnimOut = this.akrienHealthAnim.H();
                if (akAnimOut > 0.0f) {
                    RenderingUtils.drawGradientRectBordered(2.5, 31.0, 1.5 + (double)akAnimOut, 33.5, 0.74, -16737215, -7405631, akBg, akBg);
}
                if ((akArmor = (double)t2.func_70658_aO() / 20.0) > 0.0) {
                    RenderingUtils.drawGradientRectBordered(2.5, 34.5, 1.5 + ((double)akW - 3.5) * akArmor, 37.0, 0.74, -16750672, -12986881, akBg, akBg);
}
                GlStateManager.func_179094_E();
                if (t2 instanceof AbstractClientPlayer) {
                    GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    float akFScale = 0.8125f;
                    GlStateManager.func_179152_a((float)akFScale, (float)akFScale, (float)akFScale);
                    f.func_110434_K().func_110577_a(skin != null ? skin : new ResourceLocation("textures/entity/steve.png"));
                    Gui.func_152125_a((int)((int)(3.0f / akFScale)), (int)((int)(3.0f / akFScale)), (float)8.0f, (float)8.0f, (int)8, (int)8, (int)32, (int)32, (float)64.0f, (float)64.0f);
                    Gui.func_152125_a((int)((int)(3.0f / akFScale)), (int)((int)(3.0f / akFScale)), (float)40.0f, (float)8.0f, (int)8, (int)8, (int)32, (int)32, (float)64.0f, (float)64.0f);
                } else {
                    RenderingUtils.drawRect(3.0f, 3.0f, 28.0f, 28.0f, akBg);
                    GlStateManager.func_179152_a((float)2.0f, (float)2.0f, (float)2.0f);
                    boldFr.drawStringWithShadow("?", 5.5f, 5.5f, -1);
}
                GlStateManager.func_179121_F();
                boldFr.drawString(name, 31.0f, 5.0f, -1);
                fr.drawString("Health: " + L.format(health), 31.0f, 15.0f, -1);
                fr.drawString("Distance: " + L.format(dist) + "m", 31.0f, 22.0f, -1);
            } else if (mode.equals("ASTOLFO")) {
                net.minecraft.client.gui.FontRenderer mcFr = TargetHUD.f.field_71466_p;
                int asW = Math.max(110, mcFr.func_78256_a(name) + 70);
                float asHealthPct = Math.max(0.0f, Math.min(1.0f, (health + t2.func_110139_bj()) / (max + t2.func_110139_bj())));
                int asC1 = Theme.S(0.0, 35338930340239L);
                int asC2 = Theme.S(3.0, 35338930340239L);
                RenderingUtils.drawRect(0.0f, 0.0f, asW, 45.0f, new Color(0, 0, 0, 153).getRGB());
                Color asColor1 = new Color(asC1, true);
                Color asColor2 = new Color(asC2, true);
                RenderingUtils.drawGradientRect(34.0, 33.0, asW - 4, 40.0, true, asColor1.darker().darker().darker().darker().getRGB(), asColor2.darker().darker().darker().darker().getRGB());
                float asEndWidth = Math.max(0.0f, (float)(asW - 34) * asHealthPct);
                this.astolfoHealthAnim.j(asEndWidth, 18);
                float asHealthW = this.astolfoHealthAnim.H();
                RenderingUtils.drawGradientRect(34.0, 33.0, 30.0f + asHealthW, 40.0, true, asColor1.darker().darker().getRGB(), asColor2.darker().darker().getRGB());
                RenderingUtils.drawGradientRect(34.0, 33.0, 30.0f + Math.min(asEndWidth, asHealthW), 40.0, true, asC1, asC2);
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GuiInventory.func_147046_a((int)17, (int)40, (int)18, (float)t2.field_70177_z, (float)t2.field_70125_A, (EntityLivingBase)t2);
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                mcFr.func_175063_a(name, 34.0f, 4.0f, -1);
                float asScale = 1.75f;
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a((float)asScale, (float)asScale, (float)asScale);
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                String asHpStr = L.format(health) + " \u2764";
                mcFr.func_175063_a(asHpStr, 34.0f / asScale, 16.0f / asScale, asC1);
                GlStateManager.func_179121_F();
            } else if (mode.equals("RISE")) {
                long rsNow;
                FontRenderer riseFr20 = FontManager.getMedium();
                FontRenderer riseFr18 = FontManager.getSmall();
                int rsW = (int)Math.max(128.0f, riseFr20.getWidth("Name: " + name) + 60.0f);
                int rsH = 50;
                float rsHealthPct = Math.max(0.0f, Math.min(1.0f, (health + t2.func_110139_bj()) / (max + t2.func_110139_bj())));
                ShaderRenderer.F(0.0f, 0.0f, rsW, 2001336113403L, rsH, 6.0f, new Color(0, 0, 0, 110).getRGB(), true, true, true, true);
                int rsC1 = Theme.S(0.0, 35338930340239L);
                int rsC2 = Theme.S(3.0, 35338930340239L);
                float rsBarTarget = (float)(rsW - 28) * rsHealthPct;
                this.riseHealthAnim.j(rsBarTarget, 18);
                RenderingUtils.drawGradientRect(5.0, 40.0, 5.0f + this.riseHealthAnim.H(), 45.0, true, rsC1, rsC2);
                for (int pi = this.riseParticles.size() - 1; pi >= 0; --pi) {
                    RiseParticle rp = this.riseParticles.get(pi);
                    rp.baseX = 20.0f;
                    rp.baseY = 20.0f;
                    GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    if (!(rp.opacity > 4.0f)) continue;
                    ShaderRenderer.F(rp.baseX + rp.adjustedX, rp.baseY + rp.adjustedY, rp.size, 2001336113403L, rp.size, rp.size / 2.0f - 0.5f, RenderingUtils.applyOpacity(rp.color, rp.opacity / 255.0f), true, true, true, true);
}
                if (t2 instanceof AbstractClientPlayer) {
                    int rsHurtOffset = (int)((float)t2.field_70737_aN * 0.35f);
                    int rsRedOffset = -(t2.field_70737_aN * 23);
                    int rsFaceColor = new Color(255, Math.max(0, Math.min(255, 255 + rsRedOffset)), Math.max(0, Math.min(255, 255 + rsRedOffset))).getRGB();
                    GlStateManager.func_179098_w();
                    f.func_110434_K().func_110577_a(skin != null ? skin : new ResourceLocation("textures/entity/steve.png"));
                    GlStateManager.func_179131_c((float)((float)(rsFaceColor >> 16 & 0xFF) / 255.0f), (float)((float)(rsFaceColor >> 8 & 0xFF) / 255.0f), (float)((float)(rsFaceColor & 0xFF) / 255.0f), (float)1.0f);
                    Gui.func_152125_a((int)(5 + rsHurtOffset / 2), (int)(5 + rsHurtOffset / 2), (float)8.0f, (float)8.0f, (int)8, (int)8, (int)(30 - rsHurtOffset), (int)(30 - rsHurtOffset), (float)64.0f, (float)64.0f);
                    Gui.func_152125_a((int)(5 + rsHurtOffset / 2), (int)(5 + rsHurtOffset / 2), (float)40.0f, (float)8.0f, (int)8, (int)8, (int)(30 - rsHurtOffset), (int)(30 - rsHurtOffset), (float)64.0f, (float)64.0f);
                    GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
                if ((rsNow = System.currentTimeMillis()) - this.riseLastParticleMs > 16L) {
                    this.riseLastParticleMs = rsNow;
                    for (int pi = this.riseParticles.size() - 1; pi >= 0; --pi) {
                        RiseParticle rp = this.riseParticles.get(pi);
                        rp.updatePosition();
                        if (!(rp.opacity < 1.0f)) continue;
                        this.riseParticles.remove(pi);
}
}
                double rsHealthNum = (double)Math.round((double)(health + t2.func_110139_bj()) * 10.0) / 10.0;
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                riseFr18.drawString(String.valueOf(rsHealthNum), 8.0f + this.riseHealthAnim.H(), 38.0f, -1);
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                riseFr20.drawString("Name: " + name, 40.0f, 10.0f, -1);
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                double rsDist = (double)Math.round((double)dist * 10.0) / 10.0;
                riseFr20.drawString("Distance: " + rsDist + " Hurt: " + t2.field_70737_aN, 40.0f, 22.0f, -1);
                if (t2.field_70737_aN == 9 && !this.riseSentParticles) {
                    for (int i = 0; i <= 15; ++i) {
                        RiseParticle rp = new RiseParticle();
                        rp.baseX = 20.0f;
                        rp.baseY = 20.0f;
                        rp.deltaX = (float)((Math.random() - 0.5) * 2.0 * 1.4);
                        rp.deltaY = (float)((Math.random() - 0.5) * 2.0 * 1.4);
                        rp.size = (float)(Math.random() * 4.0);
                        rp.opacity = 254.0f;
                        rp.color = i % 2 == 0 ? rsC1 : rsC2;
                        this.riseParticles.add(rp);
}
                    this.riseSentParticles = true;
}
                if (t2.field_70737_aN == 8) {
                    this.riseSentParticles = false;
}
            } else if (mode.equals("OLD_TENACITY")) {
                FontRenderer otBoldFr = FontManager.get();
                int otW = (int)Math.max(145.0f, otBoldFr.getWidth(name) + 40.0f);
                int otH = 37;
                int otC1 = Theme.S(0.0, 35338930340239L);
                int otC2 = Theme.S(3.0, 35338930340239L);
                Color otBgColor = new Color(20, 18, 18, 90);
                ShaderRenderer.F(0.0f, 0.0f, otW, 2001336113403L, otH, 4.0f, otBgColor.getRGB(), true, true, true, true);
                if (t2 instanceof AbstractClientPlayer) {
                    RenderingUtils.drawRoundedHead(3, 3, 31, 4.0f, skin);
                } else {
                    otBoldFr.drawStringWithShadow("?", 20.0f - otBoldFr.getWidth("?") / 2.0f, 12.0f, -1);
}
                otBoldFr.drawStringWithShadow(name, 39.0f, 5.0f, -1);
                float otHealthPct = Math.max(0.0f, Math.min(1.0f, (health + t2.func_110139_bj()) / (max + t2.func_110139_bj())));
                float otRealHealthW = otW - 44;
                this.oldTenacityHealthAnim.j(otRealHealthW * otHealthPct, 18);
                float otHealthW = this.oldTenacityHealthAnim.H();
                ShaderRenderer.F(39.0f, otH - 12, 98.0f, 2001336113403L, 3.0f, 1.5f, new Color(0, 0, 0, 110).getRGB(), true, true, true, true);
                AbyssShaderUtils.drawGradientHorizontal(39.0f, otH - 12, otHealthW, 3.0f, 1.5f, new Color(otC1, true), new Color(otC2, true));
                String otPctText = (int)(otHealthPct * 100.0f) + "%";
                fr.drawStringWithShadow(otPctText, 34.0f + Math.min(Math.max(1.0f, otHealthW), otRealHealthW - 11.0f), (float)(otH - 14) - fr.getHeight(otPctText), -1);
            } else if (mode.equals("TENACITY")) {
                FontRenderer tBoldFr = FontManager.get();
                int tW = (int)Math.max(155.0f, tBoldFr.getWidth(name) + 75.0f);
                int tH = 50;
                int tC1 = Theme.S(0.0, 35338930340239L);
                int tC2 = Theme.S(2.0, 35338930340239L);
                int tC3 = Theme.S(4.0, 35338930340239L);
                int tC4 = Theme.S(6.0, 35338930340239L);
                float tAlpha = 0.8f;
                AbyssShaderUtils.drawGradientRound(0.0f, 0.0f, tW, tH, 6.0f, new Color(RenderingUtils.applyOpacity(tC1, tAlpha), true), new Color(RenderingUtils.applyOpacity(tC4, tAlpha), true), new Color(RenderingUtils.applyOpacity(tC2, tAlpha), true), new Color(RenderingUtils.applyOpacity(tC3, tAlpha), true));
                float tSize = 38.0f;
                if (t2 instanceof AbstractClientPlayer) {
                    RenderingUtils.drawRoundedHead(10, (int)((float)tH / 2.0f - tSize / 2.0f), (int)tSize, tSize / 2.0f, skin);
                } else {
                    tBoldFr.drawStringWithShadow("?", 30.0f - tBoldFr.getWidth("?") / 2.0f, 20.0f, -1);
}
                float tNameX = 10.0f + tSize + ((float)tW - (10.0f + tSize)) / 2.0f;
                tBoldFr.drawString(name, tNameX - tBoldFr.getWidth(name) / 2.0f, 10.0f, -1);
                float tHealthPct = Math.max(0.0f, Math.min(1.0f, (health + t2.func_110139_bj()) / (max + t2.func_110139_bj())));
                float tBarW = (float)tW - (tSize + 30.0f);
                this.tenacityHealthAnim.j(tBarW * tHealthPct, 18);
                ShaderRenderer.F(20.0f + tSize, 25.0f, tBarW, 2001336113403L, 4.0f, 2.0f, new Color(0, 0, 0, 76).getRGB(), true, true, true, true);
                ShaderRenderer.F(20.0f + tSize, 25.0f, this.tenacityHealthAnim.H(), 2001336113403L, 4.0f, 2.0f, new Color(255, 255, 255).getRGB(), true, true, true, true);
                String tPctText = Math.round(tHealthPct * 100.0f) + "%";
                String tInfoText = tPctText + " - " + Math.round(dist) + "m";
                fr.drawString(tInfoText, tNameX - fr.getWidth(tInfoText) / 2.0f, 35.0f, -1);
}
}
        finally {
            GlStateManager.func_179126_j();
            GlStateManager.func_179117_G();
            GlStateManager.func_179098_w();
            GlStateManager.func_179141_d();
            GlStateManager.func_179121_F();
}
}
    private void drawEntityOnScreenExhibition(EntityLivingBase ent) {
        GlStateManager.func_179142_g();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)20.0f, (float)36.0f, (float)50.0f);
        float largestSize = Math.max(ent.field_70131_O, ent.field_70130_N);
        float relativeScale = Math.max(largestSize / 1.8f, 1.0f);
        GlStateManager.func_179152_a((float)(-16.0f / relativeScale), (float)(16.0f / relativeScale), (float)(16.0f / relativeScale));
        GlStateManager.func_179114_b((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.func_74519_b();
        GlStateManager.func_179114_b((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(-((float)Math.atan(0.425)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)0.0f);
        RenderManager rm = Minecraft.func_71410_x().func_175598_ae();
        rm.func_178631_a(180.0f);
        rm.func_178633_a(false);
        rm.func_147940_a((Entity)ent, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        rm.func_178633_a(true);
        GlStateManager.func_179121_F();
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77478_a);
}
    private void handleDrag() {
        if (f == null || TargetHUD.f.field_71462_r == null || TargetHUD.f.field_71443_c <= 0 || TargetHUD.f.field_71440_d <= 0) {
            this.thudDragging = false;
            return;
}
        ScaledResolution sr = new ScaledResolution(f);
        int sw = sr.func_78326_a();
        int sh = sr.func_78328_b();
        int mx = Mouse.getX() * sw / TargetHUD.f.field_71443_c;
        int my = sh - Mouse.getY() * sh / TargetHUD.f.field_71440_d - 1;
        float s = Math.max(0.1f, scale.L());
        float boxW = 190.0f * s;
        float boxH = 52.0f * s;
        if (Mouse.isButtonDown((int)0)) {
            float bx = x2.L();
            float by = y.L();
            if (!this.thudDragging && (float)mx >= bx && (float)mx <= bx + boxW && (float)my >= by && (float)my <= by + boxH) {
                this.thudDragging = true;
                this.thudDragOffsetX = (float)mx - bx;
                this.thudDragOffsetY = (float)my - by;
}
            if (this.thudDragging) {
                float nx = (float)mx - this.thudDragOffsetX;
                float ny = (float)my - this.thudDragOffsetY;
                nx = Math.max(x2.i(), Math.min(nx, Math.min(x2.F(), (float)sw - boxW)));
                ny = Math.max(y.i(), Math.min(ny, Math.min(y.F(), (float)sh - boxH)));
                try {
                    x2.o((byte)0, 0L, nx);
                    y.o((byte)0, 0L, ny);
}
                catch (Throwable throwable) {}
}
        } else {
            this.thudDragging = false;
}
}
    @Override
    public void A(long var1) {
        this.E = null;
        this.K = null;
}
    static {
        c = 126705739374527L;
        L = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));
        S = new DecimalFormat("+0.0;-0.0", new DecimalFormatSymbols(Locale.US));
        abyssThudFontReady = false;
        style = new ModeSetting("Style", true, "AUTUMN", "AKRIEN", "ASTOLFO", "AUTUMN", "EXHIBITION", "NOVOLINE", "OLD_TENACITY", "RISE", "TENACITY");
        backgroundOpacity = new PercentageSetting("Background-opacity", 50);
        enemies = new BooleanSetting("Enemies", true);
        players = new BooleanSetting("Players", true);
        animals = new BooleanSetting("Animals", false);
        targetMode = new ModeSetting("Target-mode", false, "AIM", "HIT", "AIM");
        teammates = new BooleanSetting("Teammates", false);
        textShadow = new BooleanSetting("Text-shadow", true);
        bosses = new BooleanSetting("Bosses", false);
        onlyWhenUsingKillaura = new BooleanSetting("Only-when-using-killaura", false);
        stayTime = new NumberSetting("Stay-time", 2.0f, 0.1f, 20.0f, 0.1f);
        customHealthColor = new BooleanSetting("Custom-health-color", false);
        x2 = new NumberSetting("X", 350.0f, 0.0f, 800.0f, 1.0f);
        customColor = new ColorSetting("Custom-color", "FFFFFF");
        range = new NumberSetting("Range", 10.0f, 0.0f, 30.0f, 1.0f);
        scale = new NumberSetting("Scale", 1.0f, 0.5f, 3.0f, 0.01f);
        indicators = new BooleanSetting("Indicators", true);
        friends = new BooleanSetting("Friends", false);
        outline = new BooleanSetting("Outline", true);
        color = new ModeSetting("Color", "THEME", "THEME_CUSTOM", "CUSTOM");
        y = new NumberSetting("Y", 150.0f, 0.0f, 500.0f, 1.0f);
        healthAnimations = new BooleanSetting("Health-Animations", false);
        bots = new BooleanSetting("Bots", false);
        chatPreview = new BooleanSetting("Chat-preview", true);
        mobs = new BooleanSetting("Mobs", false);
        targetSettings = new HeaderSetting("Target settings");
}
    static class RiseParticle {
        float baseX;
        float baseY;
        float adjustedX;
        float adjustedY;
        float deltaX;
        float deltaY;
        float size;
        float opacity;
        int color;

        RiseParticle() {
}
        void updatePosition() {
            for (int i = 1; i <= 2; ++i) {
                this.adjustedX += this.deltaX;
                this.adjustedY += this.deltaY;
                this.deltaY = (float)((double)this.deltaY * 0.97);
                this.deltaX = (float)((double)this.deltaX * 0.97);
                this.opacity -= 1.0f;
                if (!(this.opacity < 1.0f)) continue;
                this.opacity = 1.0f;
}
}
}
}