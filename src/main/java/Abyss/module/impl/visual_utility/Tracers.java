/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.boss.IBossDisplayData
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.IAnimals
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.Vec3
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.TracersBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Teams;
import Abyss.module.impl.misc.AntiBot;
import Abyss.module.impl.visual_utility.Indicators;
import Abyss.module.impl.visual_utility.TracersFilterFlags;
import Abyss.module.impl.visual_utility.TracersTarget;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.util.EntityUtil;
import Abyss.util.render.RenderUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class Tracers
extends Module
implements EventSubscriber {
    public static ColorSetting animalsColor;
    public static BooleanSetting enemies;
    public static HeaderSetting colorSettings;
    public static HeaderSetting targetSettings;
    public static BooleanSetting animals;
    public static ColorSetting friendsColor;
    public static ColorSetting playersColor;
    public static ColorSetting teammatesColor;
    public static BooleanSetting bosses;
    private final List<TracersTarget> Y;
    public static BooleanSetting mobs;
    public static BooleanSetting bots;
    public static BooleanSetting teammates;
    public static BooleanSetting friends;
    public static ModeSetting mode;
    public static BooleanSetting players;
    public static ColorSetting mobsColor;
    public static ColorSetting bossesColor;
    public static ModeSetting colorMode;
    public static ColorSetting enemiesColor;
    private static long c;
    public static ColorSetting botsColor;

    private int u(char var1, int var2, short var3, EntityLivingBase var4) {
        long var5 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ c;
        long var7 = var5 ^ 0x628E93354B05L;
        long var11 = var5 ^ 0x28E89BA8D2F8L;
        if (Teams.l((Entity)var4)) {
            return friendsColor.k(var11);
}
        if (Teams.Y((Entity)var4)) {
            return enemiesColor.k(var11);
}
        if (Teams.g(0L, (Entity)var4)) {
            return botsColor.k(var11);
}
        return this.u(var7, var4) ? teammatesColor.k(var11) : playersColor.k(var11);
}
    private boolean c(EntityLivingBase var1) {
        return var1 instanceof IBossDisplayData;
}
    @Override
    public void A(long var1) {
        this.Y.clear();
}
    private Vec3 s(float var1) {
        Vec3 var2 = this.q();
        Vec3 var3 = this.G(var2, var1);
        return new Vec3(var3.field_72450_a, var3.field_72448_b + (double)f.func_175606_aa().func_70047_e(), var3.field_72449_c);
}
    private boolean u(long var1, EntityLivingBase var3) {
        var1 = c ^ var1;
        int var4 = (int)((var1 ^ 0x2B665AFBB1DFL) >>> 48);
        return var3 instanceof EntityPlayer && AntiBot.T((short)var4, (EntityPlayer)var3);
}
    private boolean B(EntityLivingBase var1) {
        return var1 == Tracers.f.field_71439_g || var1 == f.func_175606_aa();
}
    public void onRender3D(Render3DEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (mode.R("LINE")) {
            RenderUtil.L();
            Vec3 var8 = this.s(var1.j);
            for (int var9 = 0; var9 < this.Y.size(); ++var9) {
                TracersTarget var10 = this.Y.get(var9);
                this.B(var8, 30320126760008L, var10, var1.j);
}
            RenderUtil.w();
}
}
    private int B(long var1, EntityLivingBase var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var12 = 34903;
        if (this.Z(23952)) {
            return Teams.d((short)0, var3);
}
        if (this.A(var3)) {
            return this.u('\u0000', 821190475, (short)var12, var3);
}
        if (this.c(var3)) {
            return bossesColor.k(96531491288662L);
}
        if (this.x(var3)) {
            return mobsColor.k(96531491288662L);
}
        return this.h(var3) ? animalsColor.k(96531491288662L) : -1;
}
    private boolean h(long var1, EntityLivingBase var3, TracersFilterFlags var4, short var5) {
        long var6 = (0x51ACA7B0000L | (long)var5 << 48 >>> 48) ^ c;
        long var8 = var6 ^ 0x591C003012C0L;
        if (this.g(var3)) {
            return false;
}
        return this.B(var3) ? false : EntityUtil.q((Entity)var3, TracersFilterFlags.g(var4), TracersFilterFlags.I(var4), TracersFilterFlags.c(var4), TracersFilterFlags.E(var4), TracersFilterFlags.M(var4), TracersFilterFlags.h(var4), TracersFilterFlags.l(var4), TracersFilterFlags.d(var4), var8);
}
    private boolean h(EntityLivingBase var1) {
        return var1 instanceof IAnimals && !this.c(var1) && !this.x(var1);
}
    private Vec3 q() {
        return this.F() ? new Vec3(0.0, 0.0, 1.0) : new Vec3(0.0, 0.0, 0.0);
}
    private boolean M(TracersFilterFlags var1) {
        return TracersFilterFlags.g(var1) && !TracersFilterFlags.I(var1) && !TracersFilterFlags.c(var1) && !TracersFilterFlags.E(var1);
}
    private double isSneaking(EntityLivingBase var1) {
        return var1.func_70093_af() ? 0.125 : 0.0;
}
    private boolean g(EntityLivingBase var1) {
        return f.func_175606_aa().func_70032_d((Entity)var1) > 512.0f;
}
    private Vec3 G(Vec3 var1, float var2) {
        float var3 = this.getRenderViewEntity(var2);
        float var4 = this.B(var2);
        return var1.func_178789_a((float)(-Math.toRadians(var3))).func_178785_b((float)(-Math.toRadians(var4)));
}
    private TracersFilterFlags s() {
        return new TracersFilterFlags(players.c(), mobs.c(), animals.c(), bosses.c(), friends.c(), enemies.c(), teammates.c(), bots.c(), null);
}
    static void $jnicClinit() throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        c = 87762184973561L;
}
    private boolean A(EntityLivingBase var1) {
        return var1 instanceof EntityPlayer;
}
    private float g(float var1, float var2, float var3) {
        return (var2 - var1) * var3 + var1;
}
    private double D(double var1, double var3, float var5) {
        return (double)var5 * (var3 - var1) + var1;
}
    private boolean x(EntityLivingBase var1) {
        return var1 instanceof EntityMob && !this.c(var1);
}
    public void onPostTick(long var1, PostTickEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.Y.clear();
        TracersFilterFlags var11 = this.s();
        List var12 = EntityUtil.U(this.M(var11));
        for (int var13 = 0; var13 < var12.size(); ++var13) {
            EntityLivingBase var14 = (EntityLivingBase)var12.get(var13);
            if (!this.h(85641851L, var14, var11, (short)22899)) continue;
            this.Y.add(new TracersTarget(var14, this.B(21752513251269L, var14), null));
}
}
    public Tracers(long var1) {
        super(c ^ var1 ^ 0x548483937339L);
        this.declare("Tracers", Category.Visual_utility, "Draw lines which traced to players", new Setting[0]);
        var1 = c ^ var1;
        this.Y = new ArrayList<TracersTarget>();
}
    private float B(float var1) {
        return this.F() ? this.g(Tracers.f.func_175606_aa().field_70126_B, Tracers.f.func_175606_aa().field_70177_z, var1) : this.g(Tracers.f.field_71439_g.field_71107_bF, Tracers.f.field_71439_g.field_71109_bG, var1);
}
    private boolean Z(int var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return "TEAM".equals(colorMode.Y());
}
    private float getRenderViewEntity(float var1) {
        return this.F() ? this.g(Tracers.f.func_175606_aa().field_70127_C, Tracers.f.func_175606_aa().field_70125_A, var1) : this.g(Tracers.f.field_71439_g.field_70727_aS, Tracers.f.field_71439_g.field_70726_aT, var1);
}
    @Override
    public final void x(long var1, EventBus var3) {
        TracersBinder.Z(var3, this);
}
    private void B(Vec3 var1, long var2, TracersTarget var4, float var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        EntityLivingBase var8 = TracersTarget.L(var4);
        double var9 = this.D(var8.field_70142_S, var8.field_70165_t, var5);
        double var11 = this.D(var8.field_70137_T, var8.field_70163_u, var5) - this.isSneaking(var8);
        double var13 = this.D(var8.field_70136_U, var8.field_70161_v, var5);
        RenderUtil.J(var1, var9, var11 + (double)var8.func_70047_e(), var13, TracersTarget.Y(var4), 1.5f, 133584403222966L);
}
    public void onRender2D(long var1, Render2DEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (mode.R("ARROW") && Tracers.f.field_71462_r == null) {
            for (int var6 = 0; var6 < this.Y.size(); ++var6) {
                TracersTarget var7 = this.Y.get(var6);
                Indicators.F((Entity)TracersTarget.L(var7), TracersTarget.Y(var7), var3.r, 50.0, true);
}
}
}
    private boolean F() {
        return Tracers.f.field_71474_y.field_74320_O == 0;
}
    static {
        try {
            Tracers.$jnicClinit();
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var0) {
            throw new RuntimeException(var0);
}
        mobsColor = new ColorSetting("Mobs-color", "FFFFFF");
        enemiesColor = new ColorSetting("Enemies-color", "FF0000");
        botsColor = new ColorSetting("Bots-color", "FFFFFF");
        animals = new BooleanSetting("Animals", false);
        colorSettings = new HeaderSetting("Color settings");
        targetSettings = new HeaderSetting("Target settings");
        mobs = new BooleanSetting("Mobs", false);
        players = new BooleanSetting("Players", true);
        enemies = new BooleanSetting("Enemies", true);
        friendsColor = new ColorSetting("Friends-color", "00FF00");
        friends = new BooleanSetting("Friends", true);
        playersColor = new ColorSetting("Players-color", "FFFFFF");
        animalsColor = new ColorSetting("Animals-color", "FFFFFF");
        bossesColor = new ColorSetting("Bosses-color", "B22222");
        colorMode = new ModeSetting("Color-mode", "TEAM", "CUSTOM");
        teammatesColor = new ColorSetting("Teammates-color", "00FFFF");
        mode = new ModeSetting("Mode", "LINE", "ARROW");
        bosses = new BooleanSetting("Bosses", false);
        teammates = new BooleanSetting("Teammates", true);
        bots = new BooleanSetting("Bots", false);
}
}