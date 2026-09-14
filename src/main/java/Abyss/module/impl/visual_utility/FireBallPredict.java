/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityFireball
 *  net.minecraft.entity.projectile.EntityWitherSkull
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.FireBallPredictBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.visual_utility.FireBallPredictImpact;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.render.RenderUtil;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class FireBallPredict
extends Module
implements EventSubscriber {
    private static String[] k;
    private static long[] c;
    public static NumberSetting renderRadius;
    private static Object[] h;
    public static PercentageSetting opacity;
    private int b;
    private static final double r = 48.0;
    private static Map g;
        private static final double B = 24.0;
    private BlockPos o;
    public static NumberSetting predictRange;
    public static BooleanSetting heldFireCharges;
    private static final double n = 8.0;
    public static BooleanSetting realFireballs;

    public FireBallPredict(long var1) {
        super(a ^ var1 ^ 0x44F331B5DC8BL);
        this.declare("FireBallPredict", Category.Visual_utility, "Predict and render fireball impact positions", new Setting[0]);
        var1 = a ^ var1;
}
    private int I(long var1, double var3) {
        if (var3 <= 8.0) {
            return 0xFF0000;
}
        if (var3 >= 48.0) {
            return 65280;
}
        if (var3 <= 24.0) {
            float var10 = (float)((var3 - 8.0) / 16.0);
            return this.J(255, Math.round(255.0f * var10), 0);
}
        float var8 = (float)((var3 - 24.0) / 24.0);
        return this.J(Math.round(255.0f * (1.0f - var8)), 255, 0);
}
    private FireBallPredictImpact I(long var1) {
        WorldClient var3 = FireBallPredict.f.field_71441_e;
        List var4 = var3.field_73010_i;
        Vec3 var5 = new Vec3(FireBallPredict.f.field_71439_g.field_70165_t, FireBallPredict.f.field_71439_g.field_70163_u, FireBallPredict.f.field_71439_g.field_70161_v);
        FireBallPredictImpact var6 = null;
        double var7 = Double.MAX_VALUE;
        for (int var9 = 0; var9 < var4.size(); ++var9) {
            BlockPos var16;
            Vec3 var17;
            double var18;
            EntityPlayer var10 = (EntityPlayer)var4.get(var9);
            ItemStack var11 = var10.func_70694_bm();
            if (var11 == null || var11.func_77973_b() != Items.field_151059_bz) continue;
            Vec3 var12 = var10.func_174824_e(1.0f);
            Vec3 var13 = var10.func_70676_i(1.0f);
            Vec3 var14 = var12.func_72441_c(var13.field_72450_a * (double)predictRange.L(), var13.field_72448_b * (double)predictRange.L(), var13.field_72449_c * (double)predictRange.L());
            MovingObjectPosition var15 = var3.func_147447_a(var12, var14, false, true, false);
            if (var15 == null || var15.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK || (var18 = var5.func_72436_e(var17 = new Vec3((double)(var16 = var15.func_178782_a()).func_177958_n() + 0.5, (double)var16.func_177956_o() + 0.5, (double)var16.func_177952_p() + 0.5))) >= var7) continue;
            var7 = var18;
            var6 = new FireBallPredictImpact(var16, 0xFFFF00, null);
}
        return var6;
}
    @Override
    public final void x(long var1, EventBus var3) {
        FireBallPredictBinder.P(var3, this);
}
    private void i(World var1, int var2, long var3, int var5, int var6, int var7, int var8, boolean var9) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var3 = a ^ var3;
        long var10 = var3 ^ 0x316E9FE0A856L;
        long var12 = var3 ^ 0x14089AC058DL;
        for (int var14 = -var2; var14 <= var2; ++var14) {
            for (int var15 = -var2; var15 <= var2; ++var15) {
                for (int var16 = -var2; var16 <= var2; ++var16) {
                    BlockPos var17 = this.o.func_177982_a(var14, var15, var16);
                    if (!this.e(var1, var17)) continue;
                    if (var9) {
                        RenderUtil.C(var17, 1.0, var5, var12, var6, var7, var8);
                        continue;
}
                    RenderUtil.n(var17, 1.0, var5, var6, var7, var10, var8, 1.5f);
}
}
}
}
    private int j(int var1) {
        int var4 = var1 >> 16 & 0xFF;
        int var5 = var1 >> 8 & 0xFF;
        int var6 = var1 & 0xFF;
        return var4 > 200 && var5 < 96 && var6 < 96 ? 0xFFB000 : var1;
}
    private FireBallPredictImpact G(long var1) {
        WorldClient var5 = FireBallPredict.f.field_71441_e;
        List var6 = var5.field_72996_f;
        Vec3 var7 = new Vec3(FireBallPredict.f.field_71439_g.field_70165_t, FireBallPredict.f.field_71439_g.field_70163_u, FireBallPredict.f.field_71439_g.field_70161_v);
        FireBallPredictImpact var8 = null;
        double var9 = Double.MAX_VALUE;
        for (int var11 = 0; var11 < var6.size(); ++var11) {
            BlockPos var20;
            Vec3 var21;
            double var22;
            Entity var12 = (Entity)var6.get(var11);
            if (!(var12 instanceof EntityFireball) || var12 instanceof EntityWitherSkull) continue;
            EntityFireball var13 = (EntityFireball)var12;
            double var14 = var13.field_70159_w * var13.field_70159_w + var13.field_70181_x * var13.field_70181_x + var13.field_70179_y * var13.field_70179_y;
            if (var14 < 1.0E-4) continue;
            Vec3 var16 = new Vec3(var13.field_70165_t, var13.field_70163_u, var13.field_70161_v);
            Vec3 var17 = new Vec3(var13.field_70159_w, var13.field_70181_x, var13.field_70179_y).func_72432_b();
            Vec3 var18 = var16.func_72441_c(var17.field_72450_a * (double)predictRange.L(), var17.field_72448_b * (double)predictRange.L(), var17.field_72449_c * (double)predictRange.L());
            MovingObjectPosition var19 = var5.func_147447_a(var16, var18, false, true, false);
            if (var19 == null || var19.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK || (var22 = var7.func_72436_e(var21 = new Vec3((double)(var20 = var19.func_178782_a()).func_177958_n() + 0.5, (double)var20.func_177956_o() + 0.5, (double)var20.func_177952_p() + 0.5))) >= var9) continue;
            var9 = var22;
            var8 = new FireBallPredictImpact(var20, this.I(118876068591149L, var16.func_72438_d(var19.field_72307_f)), null);
}
        return var8;
}
    private int X() {
        return Math.max(1, Math.min(2, (int)renderRadius.L()));
}
    private boolean e(World var1, BlockPos var2) {
        if (var1.func_175623_d(var2)) {
            return false;
}
        Block var3 = var1.func_180495_p(var2).func_177230_c();
        return var3.func_149686_d();
}
    public void onPostTick(long var1, PostTickEvent var3) {
        FireBallPredictImpact var11 = null;
        if (realFireballs.c()) {
            var11 = this.G(75118909547976L);
}
        if (var11 == null && heldFireCharges.c()) {
            var11 = this.I(0L);
}
        if (var11 == null) {
            this.C(18634, '\u3ef4', '\u4c8c');
        } else {
            this.o = FireBallPredictImpact.m(var11);
            this.b = FireBallPredictImpact.M(var11);
}
}
    @Override
    public void A(long var1) {
        int var3 = (int)((var1 ^ 0x1D0B32841009L) >>> 32);
        int var4 = (int)((var1 ^ 0x1D0B32841009L) << 32 >>> 48);
        int var5 = (int)((var1 ^ 0x1D0B32841009L) << 48 >>> 48);
        this.C(var3, (char)var4, (char)var5);
}
    public void onRender3D(long var1, Render3DEvent var3) throws Throwable {
        if (FireBallPredict.f.field_71441_e != null && FireBallPredict.f.field_71439_g != null && this.o != null) {
            this.c('\u0000', 204245502, (short)-10313);
}
}
    private int Y(int var1, boolean var2) {
        double var3 = opacity.k();
        if (var2 && var3 > 0.0) {
            var3 = Math.min(100.0, var3 * 1.4);
}
        return Math.max(0, Math.min(var1, (int)((double)var1 * var3 / 100.0)));
}
    private int J(int var1, int var2, int var3) {
        return var1 << 16 | var2 << 8 | var3;
}
    private void C(int var1, char var2, char var3) {
        this.o = null;
        this.b = 0;
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void c(char var1, int var2, short var3) throws Throwable {
        long var4 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ a;
        long var8 = var4 ^ 0x57AEADE9DADAL;
        long var12 = var4 ^ 0x5548988C9AFEL;
        WorldClient var14 = FireBallPredict.f.field_71441_e;
        int var15 = this.X();
        int var16 = this.j(this.b);
        boolean var17 = var16 == 0xFFB000;
        int var18 = var16 >> 16 & 0xFF;
        int var19 = var16 >> 8 & 0xFF;
        int var20 = var16 & 0xFF;
        int var21 = this.Y(255, var17);
        int var22 = this.Y(var17 ? 70 : 42, var17);
        if (var21 > 0 || var22 > 0) {
            RenderUtil.L();
            try {
                if (var22 > 0) {
                    this.i((World)var14, var15, var12, var18, var19, var20, var22, true);
}
                if (var21 > 0) {
                    this.i((World)var14, var15, var12, var18, var19, var20, var21, false);
                    RenderUtil.n(this.o, 1.0, 255, 255, 255, var8, var21, 2.2f);
}
}
            finally {
                RenderUtil.X();
                RenderUtil.w();
}
}
}
    static {
        h = new Object[7];
        k = new String[7];
        g = new HashMap(13);
        c = new long[]{-5489346428128618421L, -4314703374732682513L, -5745213282544904297L, -4334071078402109778L, 7788256901447205536L, 4233381784929318969L, -2978953989575751051L, -8183968391784814800L, 7468032717364496881L, -5448091144287510570L, -7426750949437619414L, -6623744853995561005L, -4660984849290241194L, -5690810572757300751L, -8861979802741692739L, -2723728853984722903L, -2324990256193890920L};
        realFireballs = new BooleanSetting("Real-fireballs", true);
        renderRadius = new NumberSetting("Render-radius", 2.0f, 1.0f, 2.0f, 1.0f);
        heldFireCharges = new BooleanSetting("Held-fire-charges", true);
        predictRange = new NumberSetting("Predict-range", 100.0f, 16.0f, 200.0f, 1.0f);
        opacity = new PercentageSetting("Opacity", 50);
}
}