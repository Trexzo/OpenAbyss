/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderPearl
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraft.entity.projectile.EntityEgg
 *  net.minecraft.entity.projectile.EntityFireball
 *  net.minecraft.entity.projectile.EntityLargeFireball
 *  net.minecraft.entity.projectile.EntitySnowball
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.Vec3
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.IndicatorsBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.internal.accessor.EntityArrowAccessor;
import Abyss.internal.accessor.EntityRendererAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.ClientUtil;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Indicators
extends Module
implements EventSubscriber {
    public static BooleanSetting renderArrows;
    private static long b;
    public static NumberSetting circleRadius;
    public static BooleanSetting renderDistance;
    private final Set<Entity> m;
    public static BooleanSetting renderFireballs;
    public static BooleanSetting itemColors;
    private int Y;
    public static BooleanSetting renderSnowballs;
    public static BooleanSetting renderOnlyOffscreen;
    public static BooleanSetting renderEnderPearls;
    public static BooleanSetting onlyWhenApproaching;
    private final Map<Entity, Vec3> p;
    private static final double O = 1.0;
    public static BooleanSetting renderEggs;

    public void onPostTick(long var1, PostTickEvent var3) {
        if (ClientUtil.I()) {
            ++this.Y;
            if (this.Y % 5 == 0) {
                HashSet<Entity> var10 = new HashSet<Entity>();
                this.m.clear();
                double var11 = Indicators.f.field_71439_g.field_70165_t;
                double var13 = Indicators.f.field_71439_g.field_70163_u;
                double var15 = Indicators.f.field_71439_g.field_70161_v;
                for (Object var18 : Indicators.f.field_71441_e.field_72996_f) {
                    ItemStack var20;
                    Entity var19;
                    if (!(var18 instanceof Entity) || (var19 = (Entity)var18) == null || var19 == Indicators.f.field_71439_g || (var20 = this.H((short)0, var19)) == null || !this.d(var19, (short)0)) continue;
                    var10.add(var19);
                    Vec3 var21 = this.p.get(var19);
                    if (onlyWhenApproaching.c()) {
                        double var24;
                        if (var21 == null) {
                            this.p.put(var19, new Vec3(var19.field_70165_t, var19.field_70163_u, var19.field_70161_v));
                            continue;
}
                        double var22 = Math.sqrt((var11 - var21.field_72450_a) * (var11 - var21.field_72450_a) + (var13 - var21.field_72448_b) * (var13 - var21.field_72448_b) + (var15 - var21.field_72449_c) * (var15 - var21.field_72449_c));
                        if (var22 - (var24 = (double)Indicators.f.field_71439_g.func_70032_d(var19)) <= 1.0) {
                            this.p.put(var19, new Vec3(var19.field_70165_t, var19.field_70163_u, var19.field_70161_v));
                            continue;
}
}
                    this.m.add(var19);
                    this.p.put(var19, new Vec3(var19.field_70165_t, var19.field_70163_u, var19.field_70161_v));
}
                this.p.keySet().retainAll(var10);
}
}
}
    private void getRGB(int var1, Entity var2, ItemStack var3, char var4, float var5, char var6) {
        long var7 = ((long)var1 << 32 | (long)var4 << 48 >>> 32 | (long)var6 << 48 >>> 48) ^ b;
        int var11 = (int)((var7 ^ 0xAB4C6C9D8D8L) >>> 48);
        if (!(!this.d(var2, (short)var11) || renderOnlyOffscreen.c() && RenderUtil.l(var2))) {
            int var16 = itemColors.c() ? this.getItem(var3).getRGB() : -1;
            Indicators.F(var2, var16, var5, circleRadius.L(), renderDistance.c());
}
}
    public Indicators(long var1) {
        super(b ^ var1 ^ 0x249D9846EA2EL);
        this.declare("Indicators", Category.Visual_utility, "Show projectiles that is going to hit you on screen", new Setting[0]);
        var1 = b ^ var1;
        this.p = new HashMap<Entity, Vec3>();
        this.m = new HashSet<Entity>();
}
    private boolean d(Entity var1, short var2) {
        if (var1 instanceof EntityArrow && !EntityArrowAccessor.E(0L, (EntityArrow)var1) && renderArrows.c()) {
            return true;
}
        if (var1 instanceof EntityLargeFireball && renderFireballs.c()) {
            return true;
}
        if (var1 instanceof EntityEnderPearl && renderEnderPearls.c()) {
            return true;
}
        return var1 instanceof EntityEgg && renderEggs.c() ? true : var1 instanceof EntitySnowball && renderSnowballs.c();
}
    @Override
    public void A(long var1) {
        this.p.clear();
        this.m.clear();
}
    private Color getItem(ItemStack var3) {
        if (var3 == null) {
            return Color.WHITE;
}
        if (var3.func_77973_b() == Items.field_151079_bi) {
            return new Color(62, 127, 94);
}
        if (var3.func_77973_b() == Items.field_151059_bz) {
            return new Color(255, 150, 0);
}
        if (var3.func_77973_b() == Items.field_151110_aK) {
            return new Color(255, 238, 154);
}
        return var3.func_77973_b() == Items.field_151126_ay ? new Color(200, 220, 255) : Color.WHITE;
}
    @Override
    public final void x(long var1, EventBus var3) {
        IndicatorsBinder.J(var3, this);
}
    private ItemStack H(short var1, Entity var4) {
        if (var4 == null) {
            return null;
}
        if (var4 instanceof EntityArrow) {
            return EntityArrowAccessor.E(0L, (EntityArrow)var4) ? null : new ItemStack(Items.field_151032_g);
}
        if (var4 instanceof EntityFireball) {
            return new ItemStack(Items.field_151059_bz);
}
        if (var4 instanceof EntityEnderPearl) {
            return new ItemStack(Items.field_151079_bi);
}
        if (var4 instanceof EntityEgg) {
            return new ItemStack(Items.field_151110_aK);
}
        return var4 instanceof EntitySnowball ? new ItemStack(Items.field_151126_ay) : null;
}
    public void onRender2D(long var1, Render2DEvent var3) {
        if (Indicators.f.field_71462_r == null && ClientUtil.I()) {
            try {
                for (Entity var11 : this.m) {
                    ItemStack var12 = this.H((short)0, var11);
                    if (var12 == null) continue;
                    this.getRGB(20855, var11, var12, '\u74eb', var3.r, '\u849a');
}
}
            catch (Exception exception) {
                // empty catch block
}
}
}
    private static void A(int var0) {
        int var6;
        int var5;
        int var4;
        int var3 = var0 >> 24 & 0xFF;
        if (var0 == -1) {
            var4 = 255;
            var5 = 255;
            var6 = 255;
        } else {
            var4 = var0 >> 16 & 0xFF;
            var5 = var0 >> 8 & 0xFF;
            var6 = var0 & 0xFF;
}
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)9);
        GL11.glColor4f((float)((float)var4 / 255.0f), (float)((float)var5 / 255.0f), (float)((float)var6 / 255.0f), (float)((float)var3 / 255.0f));
        GL11.glVertex2d((double)0.0, (double)-5.0);
        GL11.glVertex2d((double)-5.0, (double)5.0);
        GL11.glVertex2d((double)0.0, (double)3.0);
        GL11.glVertex2d((double)5.0, (double)5.0);
        GL11.glEnd();
        GL11.glLineWidth((float)1.5f);
        GL11.glBegin((int)2);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glVertex2d((double)0.0, (double)-5.0);
        GL11.glVertex2d((double)-5.0, (double)5.0);
        GL11.glVertex2d((double)0.0, (double)3.0);
        GL11.glVertex2d((double)5.0, (double)5.0);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
}
    public static void F(Entity var0, int var1, float var2, double var5, boolean var7) {
        double var12 = var0.field_70142_S + (var0.field_70165_t - var0.field_70142_S) * (double)var2 - Indicators.f.func_175598_ae().field_78730_l;
        double var14 = var0.field_70137_T + (var0.field_70163_u - var0.field_70137_T) * (double)var2 - Indicators.f.func_175598_ae().field_78731_m + (double)var0.field_70131_O / 2.0;
        double var16 = var0.field_70136_U + (var0.field_70161_v - var0.field_70136_U) * (double)var2 - Indicators.f.func_175598_ae().field_78728_n;
        EntityRendererAccessor.k(Indicators.f.field_71460_t, var2, 0);
        ScaledResolution var18 = new ScaledResolution(f);
        Vec3 var19 = RenderUtil.I(var18.func_78325_e(), var12, var14, var16);
        if (var19 != null) {
            boolean var25;
            Indicators.f.field_71460_t.func_78478_c();
            ScaledResolution var20 = new ScaledResolution(f);
            double var21 = var19.field_72450_a - (double)var20.func_78326_a() / 2.0;
            double var23 = var19.field_72448_b - (double)var20.func_78328_b() / 2.0;
            boolean bl = var25 = var19.field_72449_c < 1.0003684;
            if (!var25) {
                var21 *= -1.0;
                var23 *= -1.0;
}
            double var26 = Math.atan2(var21, var23);
            double var28 = Math.atan2(var23, var21) * (double)57.29578f + 90.0;
            double var30 = Math.hypot(var21, var23);
            if (!var25 || !(var30 < var5 + 15.0)) {
                boolean var47;
                double var32 = (double)var20.func_78326_a() / 2.0;
                double var34 = (double)var20.func_78328_b() / 2.0;
                double var36 = Math.sin(var26);
                double var38 = Math.cos(var26);
                double var40 = var32 + var5 * var36;
                double var42 = var34 + var5 * var38;
                GlStateManager.func_179094_E();
                GlStateManager.func_179137_b((double)var40, (double)var42, (double)0.0);
                GlStateManager.func_179114_b((float)((float)var28), (float)0.0f, (float)0.0f, (float)1.0f);
                GlStateManager.func_179152_a((float)1.0f, (float)1.0f, (float)1.0f);
                double var44 = Indicators.f.field_71439_g.func_70032_d(var0);
                boolean var46 = var44 <= 10.0;
                boolean bl2 = var47 = var46 && System.currentTimeMillis() % 400L < 200L;
                if (!var47) {
                    Indicators.A(var1);
}
                GlStateManager.func_179121_F();
                var40 = var32 + (var5 - 13.0) * var36;
                var42 = var34 + (var5 - 13.0) * var38;
                GlStateManager.func_179094_E();
                GlStateManager.func_179137_b((double)var40, (double)var42, (double)0.0);
                GlStateManager.func_179139_a((double)0.8, (double)0.8, (double)0.8);
                if (var7) {
                    String var48 = (int)var44 + "m";
                    FontRenderer var49 = Indicators.f.field_71466_p;
                    int var50 = var46 ? -65536 : -1;
                    var49.func_175063_a(var48, (float)(-var49.func_78256_a(var48) / 2), -4.0f, var50);
}
                GlStateManager.func_179121_F();
}
}
}
    static {
        b = 54273370022170L;
        renderEnderPearls = new BooleanSetting("Render-ender-pearls", true);
        renderEggs = new BooleanSetting("Render-eggs", false);
        renderFireballs = new BooleanSetting("Render-fireballs", true);
        renderSnowballs = new BooleanSetting("Render-snowballs", false);
        renderOnlyOffscreen = new BooleanSetting("Render-only-offscreen", false);
        itemColors = new BooleanSetting("Item-colors", true);
        renderArrows = new BooleanSetting("Render-arrows", true);
        onlyWhenApproaching = new BooleanSetting("Only-when-approaching", false);
        renderDistance = new BooleanSetting("Render-distance", true);
        circleRadius = new NumberSetting("Circle-radius", 50.0f, 30.0f, 200.0f, 5.0f);
}
}