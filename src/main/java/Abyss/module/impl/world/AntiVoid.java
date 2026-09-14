/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraft.util.AxisAlignedBB
 */
package Abyss.module.impl.world;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AntiVoidBinder;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.Modules;
import Abyss.module.impl.movement.Stuck;
import Abyss.module.impl.world.Scaffold;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.ClientUtil;
import Abyss.util.CombatUtil;
import Abyss.util.MathUtil;
import Abyss.util.Sneaky;
import Abyss.util.packet.PacketManager;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class AntiVoid
extends Module
implements EventSubscriber {
    private double[] s;
    private boolean D;
    private boolean k;
    private boolean t;
    private static long public static ModeSetting mode;
    private boolean U;
    public static NumberSetting blinkFallDistance;

    private void j(int var1, short var2, short var3) {
        if (this.U) {
            PacketManager.j();
            PacketManager.M(false);
            this.U = false;
}
        this.s = null;
}
    public void onSetKeyBindState(SetKeyBindStateEvent var1, long var2, char var4) {
        ItemStack var10;
        long var5 = (var2 << 16 | (long)var4 << 48 >>> 48) ^ a;
        int var7 = (int)((var5 ^ 0x5D6AF6DA685L) >>> 32);
        int var8 = (int)((var5 ^ 0x5D6AF6DA685L) << 32 >>> 48);
        int var9 = (int)((var5 ^ 0x5D6AF6DA685L) << 48 >>> 48);
        if (var1.R == AntiVoid.f.field_71474_y.field_74313_G.func_151463_i() && (var10 = AntiVoid.f.field_71439_g.field_71071_by.func_70448_g()) != null && var10.func_77973_b() instanceof ItemEnderPearl) {
            this.j(var7, (short)var8, (short)var9);
}
}
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent var1, long var2) {
        try {
            var2 = a ^ var2;
            int var4 = (int)((var2 ^ 0x33FCAA07CA7L) >>> 48);
            long var5 = (var2 ^ 0x33FCAA07CA7L) << 16 >>> 16;
            long var7 = var2 ^ 0x56381C03AF94L;
            int var9 = (int)((var2 ^ 0x14D7C68A99E8L) >>> 32);
            int var10 = (int)((var2 ^ 0x14D7C68A99E8L) << 32 >>> 48);
            int var11 = (int)((var2 ^ 0x14D7C68A99E8L) << 48 >>> 48);
            boolean bl = this.D = !AntiVoid.f.field_71439_g.field_71075_bZ.field_75101_c && CombatUtil.u();
            if (!this.D) {
                this.j(var9, (short)var10, (short)var11);
}
            switch (mode.Y()) {
                case "TOGGLE_STUCK": {
                    Stuck var19 = Modules.J(Stuck.class);
                    if (var19.h() == 0) {
                        ClientUtil.t(var7, "You must bind module \"\u00a7l" + var19.b() + "\u00a7r\" to a key to keep using " + mode.Y() + " mode");
                        this.u((short)var4, var5);
                        return;
}
                    if (!this.k && this.D) {
                        if (!var19.o()) {
                            var19.u((short)var4, var5);
}
                        this.t = true;
                        break;
}
                    if (!this.t || this.D && !AntiVoid.f.field_71439_g.field_70122_E) break;
                    if (var19.o()) {
                        var19.u((short)var4, var5);
}
                    this.t = false;
                    break;
}
                case "TOGGLE_SCAFFOLD": {
                    Scaffold var18 = ModuleManager.I;
                    if (var18.h() == 0) {
                        ClientUtil.t(var7, "You must bind module \"\u00a7l" + var18.b() + "\u00a7r\" to a key to keep using " + mode.Y() + " mode");
                        this.u((short)var4, var5);
                        return;
}
                    if (!this.k && this.D) {
                        if (!var18.o()) {
                            var18.u((short)var4, var5);
}
                        this.t = true;
                        break;
}
                    if (!this.t || !AntiVoid.f.field_71439_g.field_70122_E) break;
                    if (var18.o()) {
                        var18.u((short)var4, var5);
}
                    this.t = false;
                    break;
}
                case "BLINK": {
                    float var15;
                    float var14;
                    if (this.s != null && CombatUtil.T(new AxisAlignedBB(this.s[0] - (double)(var14 = AntiVoid.f.field_71439_g.field_70130_N / 2.0f), this.s[1], this.s[2] - (double)var14, this.s[0] + (double)var14, this.s[1] + (double)(var15 = AntiVoid.f.field_71439_g.field_70131_O), this.s[2] + (double)var14))) {
                        this.j(var9, (short)var10, (short)var11);
}
                    if (!this.k && this.D) {
                        PacketManager.M(true);
                        this.U = true;
                        this.s = new double[]{AntiVoid.f.field_71439_g.field_70169_q, AntiVoid.f.field_71439_g.field_70167_r, AntiVoid.f.field_71439_g.field_70166_s};
}
                    if (!PacketManager.Z || this.s == null || !(this.s[1] - (double)blinkFallDistance.L() > AntiVoid.f.field_71439_g.field_70163_u)) break;
                    double var17 = this.s[1] - (double)MathUtil.h(10.0f, 20.0f);
                    PacketManager.u.add(0, (Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(this.s[0], var17, this.s[2], false));
                    this.j(var9, (short)var10, (short)var11);
}
}
            this.k = this.D;
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    @Override
    public String g(long var1) {
        return String.valueOf(blinkFallDistance.L());
}
    @Override
    public final void x(long var1, EventBus var3) {
        AntiVoidBinder.Q(var3, this);
}
    public AntiVoid(long var1) {
        super(a ^ var1 ^ 0x56B07065A00BL);
        this.declare("AntiVoid", Category.World, "Prevent you from falling into the void", new Setting[0]);
        var1 = a ^ var1;
        this.D = false;
        this.k = false;
        this.s = null;
        this.U = false;
        this.t = false;
}
    @Override
    public void A(long var1) {
        int var3 = (int)((var1 ^ 0x3C9413B69CE1L) >>> 32);
        int var4 = (int)((var1 ^ 0x3C9413B69CE1L) << 32 >>> 48);
        int var5 = (int)((var1 ^ 0x3C9413B69CE1L) << 48 >>> 48);
        this.D = false;
        this.k = false;
        this.t = false;
        this.j(var3, (short)var4, (short)var5);
}
    static {
        blinkFallDistance = new NumberSetting("Blink-fall-distance", 4.0f, 0.0f, 8.0f, 0.1f);
        mode = new ModeSetting("Mode", false, "TOGGLE_STUCK", "TOGGLE_SCAFFOLD", "TOGGLE_STUCK", "BLINK");
}
}