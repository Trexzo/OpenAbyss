/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.TrajectoriesBinder;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.RenderManagerAccessor;
import Abyss.internal.synthetic.TrajectoriesSwitchMapAxis;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Teams;
import Abyss.module.impl.misc.AntiBot;
import Abyss.module.impl.visual_utility.TrajectoriesViewerOffset;
import Abyss.module.impl.visual_utility.TrajectoryEntityHit;
import Abyss.module.impl.visual_utility.TrajectoryProjectileSpec;
import Abyss.module.impl.visual_utility.TrajectorySimulationResult;
import Abyss.module.impl.visual_utility.TrajectoryStep;
import Abyss.setting.Setting;
import Abyss.setting.settings.ColorSetting;
import Abyss.util.RotationManager;
import Abyss.util.Vector3d;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSnowball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Trajectories
extends Module
implements EventSubscriber {
    public static ColorSetting teammatesColor;
    public static ColorSetting baseColor;
            public static ColorSetting friendColor;
    public static ColorSetting botColor;
    public static ColorSetting nonePlayersColor;
    private static Object[] e;
    private static long[] b;
    public static ColorSetting enemyColor;
    private static String[] k;

    private void p(Vector3d var1, double var2, double var4, double var6) {
        Vector3d.d(var1, var2);
        Vector3d.z(var1, var4);
        Vector3d.c(var1, var6);
}
    private void x(int var1, TrajectorySimulationResult var2, int var3, byte var4) {
        long var5 = ((long)var1 << 32 | (long)var3 << 40 >>> 32 | (long)var4 << 56 >>> 56) ^ a;
        long var10 = var5 ^ 0x7F6F133C4FA5L;
        if (TrajectorySimulationResult.R$r1(var2) != null) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179137_b((double)TrajectorySimulationResult.l(var2), (double)TrajectorySimulationResult.R(var2), (double)TrajectorySimulationResult.B(var2));
            this.t(TrajectorySimulationResult.R$r1(var2));
            RenderUtil.I(-0.35f, -0.35f, 0.35f, 0.35f, TrajectorySimulationResult.K(var2), 60);
            RenderUtil.m(-0.35f, -0.35f, var10, 0.35f, 0.35f, 1.0f, TrajectorySimulationResult.K(var2));
            GlStateManager.func_179121_F();
}
}
    private double J(TrajectoryStep var1) {
        return TrajectoryStep.x(var1) - TrajectoryStep.P((TrajectoryStep)var1).field_72448_b;
}
    private int s(Entity var1, long var2) {
        var2 = a ^ var2;
        int var6 = (int)((var2 ^ 0x4FC4A4AD2534L) >>> 48);
        long var9 = var2 ^ 0x61154F1721EFL;
        if (var1 instanceof EntityPlayer) {
            EntityPlayer var11 = (EntityPlayer)var1;
            if (Teams.l((Entity)var11)) {
                return friendColor.k(var9);
}
            if (Teams.Y((Entity)var11)) {
                return enemyColor.k(var9);
}
            if (AntiBot.T((short)var6, var11)) {
                return botColor.k(var9);
}
            return Teams.g(0L, (Entity)var11) ? teammatesColor.k(var9) : baseColor.k(var9);
}
        return nonePlayersColor.k(var9);
}
    private void p(TrajectoryStep var1, TrajectoryProjectileSpec var2, int var3, TrajectoriesViewerOffset var4, TrajectorySimulationResult var5, char var6, int var7) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var8 = ((long)var3 << 32 | (long)var6 << 48 >>> 32 | (long)var7 << 48 >>> 48) ^ a;
        long var10 = var8 ^ 0x434B285D3DDFL;
        long var12 = var8 ^ 0x55CBF98DF0CDL;
        AxisAlignedBB var14 = this.p(var1, TrajectoryProjectileSpec.b(var2), this.Q(var1), this.J(var1), this.d(var1));
        ArrayList<Entity> var15 = this.n(var14);
        TrajectoryEntityHit var16 = this.L(var15, TrajectoryStep.P(var1), TrajectoryStep.g(var1), TrajectoryProjectileSpec.b(var2));
        if (var16 != null) {
            TrajectorySimulationResult.g(var5, this.s(TrajectoryEntityHit.d(var16), var10));
            RenderUtil.A(TrajectoryEntityHit.d(var16), var12, TrajectorySimulationResult.K(var5), 1.5f, TrajectoryEntityHit.d(var16).func_70111_Y());
            TrajectorySimulationResult.Q(var5, true);
            TrajectorySimulationResult.T(var5, true);
            TrajectorySimulationResult.E(var5, TrajectoryEntityHit.r(var16));
            this.W(TrajectoryEntityHit.r((TrajectoryEntityHit)var16).field_72307_f, var4, var5);
}
}
    private float getItemInUseDuration(float var1) {
        float var2 = (float)Trajectories.f.field_71439_g.func_71057_bx() + var1;
        float var3 = var2 / 20.0f;
        if ((var3 = (var3 * var3 + var3 * 2.0f) / 3.0f) > 1.0f) {
            var3 = 1.0f;
}
        return var3;
}
    private AxisAlignedBB p(TrajectoryStep var1, float var2, double var3, double var5, double var7) {
        return new AxisAlignedBB(TrajectoryStep.P((TrajectoryStep)var1).field_72450_a - (double)var2, TrajectoryStep.P((TrajectoryStep)var1).field_72448_b - (double)var2, TrajectoryStep.P((TrajectoryStep)var1).field_72449_c - (double)var2, TrajectoryStep.P((TrajectoryStep)var1).field_72450_a + (double)var2, TrajectoryStep.P((TrajectoryStep)var1).field_72448_b + (double)var2, TrajectoryStep.P((TrajectoryStep)var1).field_72449_c + (double)var2).func_72321_a(var3, var5, var7).func_72314_b(1.0, 1.0, 1.0);
}
    private TrajectoryEntityHit L(ArrayList<Entity> var1, Vec3 var2, Vec3 var3, float var4) {
        for (int var5 = 0; var5 < var1.size(); ++var5) {
            AxisAlignedBB var7;
            MovingObjectPosition var8;
            Entity var6 = var1.get(var5);
            if (!this.canBeCollidedWith(var6) || (var8 = (var7 = var6.func_174813_aQ().func_72314_b((double)var4, (double)var4, (double)var4)).func_72327_a(var2, var3)) == null) continue;
            return new TrajectoryEntityHit(var6, var8, null);
}
        return null;
}
    private boolean canBeCollidedWith(Entity var1) {
        return var1.func_70067_L() && var1 != Trajectories.f.field_71439_g;
}
    private void N(TrajectoryStep var1, TrajectorySimulationResult var2) {
        TrajectorySimulationResult.E(var2, Trajectories.f.field_71441_e.func_147447_a(TrajectoryStep.P(var1), TrajectoryStep.g(var1), false, true, false));
        if (TrajectorySimulationResult.R$r1(var2) != null) {
            TrajectorySimulationResult.T(var2, true);
            TrajectoryStep.y(var1, TrajectorySimulationResult.R$r1((TrajectorySimulationResult)var2).field_72307_f);
}
}
    private void t(MovingObjectPosition var1) {
        if (var1.field_178784_b != null) {
            switch (TrajectoriesSwitchMapAxis.t[var1.field_178784_b.func_176740_k().ordinal()]) {
                case 1: {
                    GlStateManager.func_179114_b((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    break;
}
                case 2: {
                    GlStateManager.func_179114_b((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
}
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        TrajectoriesBinder.U(var3, this);
}
    public Trajectories(long var1) {
        super(a ^ var1 ^ 0x7C1136ADBF1FL);
        this.declare("Trajectories", Category.Visual_utility, "Show trajectories of projectiles", new Setting[0]);
        var1 = a ^ var1;
}
    private boolean f$r4() {
        return Trajectories.f.field_71439_g != null && Trajectories.f.field_71441_e != null && Trajectories.f.field_71439_g.func_70694_bm() != null && Trajectories.f.field_71474_y.field_74320_O == 0;
}
    private double Q(TrajectoryStep var1) {
        return TrajectoryStep.L(var1) - TrajectoryStep.P((TrajectoryStep)var1).field_72450_a;
}
    private TrajectoriesViewerOffset P$r1(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        RenderManager var10 = f.func_175598_ae();
        return new TrajectoriesViewerOffset(RenderManagerAccessor.k(0L, var10), RenderManagerAccessor.y(13236, var10), RenderManagerAccessor.W(0L, var10), null);
}
    private void A(TrajectoryStep var1, TrajectoriesViewerOffset var2, TrajectorySimulationResult var3) {
        if (!TrajectorySimulationResult.L(var3) && TrajectorySimulationResult.R$r1(var3) != null) {
            this.W(TrajectorySimulationResult.R$r1((TrajectorySimulationResult)var3).field_72307_f, var2, var3);
}
}
    private TrajectoryProjectileSpec W(Item var1, float var2) {
        if (var1 instanceof ItemBow) {
            float var3 = this.getItemInUseDuration(var2);
            return var3 < 0.1f ? null : new TrajectoryProjectileSpec(true, var3 * 3.0f, 0.99f, 0.05f, 0.3f, null);
}
        if (var1 instanceof ItemFishingRod) {
            return new TrajectoryProjectileSpec(false, 1.5f, 0.92f, 0.04f, 0.25f, null);
}
        return !(var1 instanceof ItemSnowball) && !(var1 instanceof ItemEgg) && !(var1 instanceof ItemEnderPearl) ? null : new TrajectoryProjectileSpec(false, 1.5f, 0.99f, 0.03f, 0.25f, null);
}
    private TrajectorySimulationResult getRGB(long var1, Vector3d var3, TrajectoryProjectileSpec var4, TrajectoriesViewerOffset var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        TrajectorySimulationResult var9 = new TrajectorySimulationResult(null);
        TrajectorySimulationResult.g(var9, Color.WHITE.getRGB());
        while (!TrajectorySimulationResult.c(var9) && Vector3d.I(var3) > 0.0) {
            TrajectoryStep var10 = this.L(var3);
            this.N(var10, var9);
            this.p(var10, var4, 30101, var5, var9, '\u1a75', 27238);
            this.p(var3, TrajectoryStep.L(var10), TrajectoryStep.x(var10), TrajectoryStep.n(var10));
            this.A(var10, var5, var9);
            this.z(var3, TrajectoryProjectileSpec.Z(var4), TrajectoryProjectileSpec.T(var4));
            this.A(var3, var5, var9);
}
        return var9;
}
    private TrajectoryStep L(Vector3d var1) {
        double var2 = Vector3d.i(var1) + Vector3d.O(var1);
        double var4 = Vector3d.I(var1) + Vector3d.f(var1);
        double var6 = Vector3d.A(var1) + Vector3d.l(var1);
        Vec3 var8 = new Vec3(Vector3d.i(var1), Vector3d.I(var1), Vector3d.A(var1));
        Vec3 var9 = new Vec3(var2, var4, var6);
        return new TrajectoryStep(var8, var9, var2, var4, var6, null);
}
    private float Q(float var1) {
        return var1 * ((float)Math.PI / 180);
}
    private Vector3d t(TrajectoryProjectileSpec var1, TrajectoriesViewerOffset var2) {
        double var25;
        double var23;
        float var5 = RotationManager.p();
        float var6 = RotationManager.s();
        float var7 = this.Q(var5);
        float var8 = this.Q(var6);
        float var9 = MathHelper.func_76126_a((float)var7);
        float var10 = MathHelper.func_76134_b((float)var7);
        float var11 = MathHelper.func_76126_a((float)var8);
        float var12 = MathHelper.func_76134_b((float)var8);
        double var13 = TrajectoriesViewerOffset.q(var2) - (double)var10 * 0.16;
        double var15 = TrajectoriesViewerOffset.L(var2) + (double)Trajectories.f.field_71439_g.func_70047_e() - 0.1;
        double var17 = TrajectoriesViewerOffset.p(var2) - (double)var9 * 0.16;
        double var19 = TrajectoryProjectileSpec.A(var1) ? 1.0 : 0.4;
        double var21 = (double)(-var9 * var12) * var19;
        double var27 = Math.sqrt(var21 * var21 + (var23 = (double)(-var11) * var19) * var23 + (var25 = (double)(var10 * var12) * var19) * var25);
        if (var27 == 0.0) {
            return null;
}
        var21 = var21 / var27 * (double)TrajectoryProjectileSpec.I(var1);
        var23 = var23 / var27 * (double)TrajectoryProjectileSpec.I(var1);
        var25 = var25 / var27 * (double)TrajectoryProjectileSpec.I(var1);
        return new Vector3d(var13, var15, var17, var21, var23, var25, null);
}
    private void N(long var1, TrajectorySimulationResult var3) {
        WorldRenderer var11 = Tessellator.func_178181_a().func_178180_c();
        RenderUtil.L();
        RenderUtil.l(TrajectorySimulationResult.K(var3), 73372009905513L);
        GL11.glLineWidth((float)1.5f);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        var11.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        for (int var12 = 0; var12 < TrajectorySimulationResult.b(var3).size(); ++var12) {
            Vec3 var13 = (Vec3)TrajectorySimulationResult.b(var3).get(var12);
            var11.func_181662_b(var13.field_72450_a, var13.field_72448_b, var13.field_72449_c).func_181675_d();
}
        Tessellator.func_178181_a().func_78381_a();
        this.x(1175, var3, 8184540, (byte)-8);
        GL11.glDisable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GlStateManager.func_179117_G();
        RenderUtil.w();
}
    private double d(TrajectoryStep var1) {
        return TrajectoryStep.n(var1) - TrajectoryStep.P((TrajectoryStep)var1).field_72449_c;
}
    private void W(Vec3 var1, TrajectoriesViewerOffset var2, TrajectorySimulationResult var3) {
        TrajectorySimulationResult.M(var3, var1.field_72450_a - TrajectoriesViewerOffset.q(var2));
        TrajectorySimulationResult.N(var3, var1.field_72448_b - TrajectoriesViewerOffset.L(var2));
        TrajectorySimulationResult.B(var3, var1.field_72449_c - TrajectoriesViewerOffset.p(var2));
}
    private void A(Vector3d var1, TrajectoriesViewerOffset var2, TrajectorySimulationResult var3) {
        TrajectorySimulationResult.b(var3).add(new Vec3(Vector3d.i(var1) - TrajectoriesViewerOffset.q(var2), Vector3d.I(var1) - TrajectoriesViewerOffset.L(var2), Vector3d.A(var1) - TrajectoriesViewerOffset.p(var2)));
}
    private void z(Vector3d var1, float var2, float var3) {
        if (this.R(var1)) {
            Vector3d var4 = var1;
            Vector3d.B(var4, Vector3d.O(var4) * 0.6);
            var4 = var1;
            Vector3d.P(var4, Vector3d.f(var4) * 0.6);
            var4 = var1;
            Vector3d.U(var4, Vector3d.l(var4) * 0.6);
        } else {
            Vector3d var7 = var1;
            Vector3d.B(var7, Vector3d.O(var7) * (double)var2);
            var7 = var1;
            Vector3d.P(var7, Vector3d.f(var7) * (double)var2);
            var7 = var1;
            Vector3d.U(var7, Vector3d.l(var7) * (double)var2);
}
        Vector3d var10 = var1;
        Vector3d.P(var10, Vector3d.f(var10) - (double)var3);
}
    private ArrayList<Entity> n(AxisAlignedBB var1) {
        int var2 = MathHelper.func_76128_c((double)((var1.field_72340_a - 2.0) / 16.0));
        int var3 = MathHelper.func_76128_c((double)((var1.field_72336_d + 2.0) / 16.0));
        int var4 = MathHelper.func_76128_c((double)((var1.field_72339_c - 2.0) / 16.0));
        int var5 = MathHelper.func_76128_c((double)((var1.field_72334_f + 2.0) / 16.0));
        ArrayList<Entity> var6 = new ArrayList<Entity>();
        for (int var7 = var2; var7 <= var3; ++var7) {
            for (int var8 = var4; var8 <= var5; ++var8) {
                Trajectories.f.field_71441_e.func_72964_e(var7, var8).func_177414_a((Entity)Trajectories.f.field_71439_g, var1, var6, null);
}
}
        return var6;
}
    private boolean R(Vector3d var1) {
        return Trajectories.f.field_71441_e.func_180495_p(new BlockPos(Vector3d.i(var1), Vector3d.I(var1), Vector3d.A(var1))).func_177230_c().func_149688_o() == Material.field_151586_h;
}
    public void onRender3D(Render3DEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        TrajectorySimulationResult var16;
        TrajectoriesViewerOffset var14;
        Vector3d var15;
        Item var12;
        TrajectoryProjectileSpec var13;
        if (this.f$r4() && (var13 = this.W(var12 = Trajectories.f.field_71439_g.func_70694_bm().func_77973_b(), var1.j)) != null && (var15 = this.t(var13, var14 = this.P$r1(53469978203973L))) != null && TrajectorySimulationResult.b(var16 = this.getRGB(78770923882103L, var15, var13, var14)).size() > 1) {
            this.N(12020114583032L, var16);
}
}
    static {
        e = new Object[12];
        k = new String[12];
        d = new HashMap(13);
        b = new long[]{1885610206048317227L, -6062832488464918740L, 1199991079690601516L, -7825559100591298828L, 5120067646296097688L};
        nonePlayersColor = new ColorSetting("None-players-color", "FFFFFF");
        botColor = new ColorSetting("Bot-color", "FFFFFF");
        teammatesColor = new ColorSetting("Teammates-color", "FFFFFF");
        enemyColor = new ColorSetting("Enemy-color", "FF0000");
        friendColor = new ColorSetting("Friend-color", "00FF00");
        baseColor = new ColorSetting("Base-color", "FF0000");
}
}