/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.server.S19PacketEntityStatus
 *  net.minecraft.potion.Potion
 *  net.minecraft.world.World
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.HitSelectBinder;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.AttackTracker;
import Abyss.util.MathUtil;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class HitSelect
extends Module
implements EventSubscriber {
    private int L;
    public static ModeSetting strategy;
    public static NumberSetting minPauseTick;
    private EntityPlayer D;
    private int m;
    public static NumberSetting maxPauseTick;
        public static PercentageSetting chance;

    @Override
    public String g(long var1) {
        return strategy.Y();
}
    public HitSelect(long var1) {
        super(a ^ var1 ^ 0x5C359E717B75L);
        this.declare("HitSelect", Category.Combat, "Modify your attacking strategy to get more hits in combat", new Setting[0]);
        var1 = a ^ var1;
        this.D = null;
        this.m = 0;
        this.L = 0;
}
    @Override
    public final void x(long var1, EventBus var3) {
        HitSelectBinder.J(var3, this);
}
    public void onPreMouseInput(long var1, PreMouseInputEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (this.m > 0) {
            --this.m;
}
        if (this.m <= 0) {
            this.D = null;
}
        if (this.D != null) {
            switch (strategy.Y()) {
                case "NORMAL": {
                    AttackTracker.Z(this.L <= 0);
                    break;
}
                case "CRITICALS": {
                    boolean var6;
                    if (HitSelect.f.field_71439_g.field_70122_E) {
                        AttackTracker.Z(true);
                        break;
}
                    boolean bl = var6 = HitSelect.f.field_71439_g.field_70143_R > 0.0f && !HitSelect.f.field_71439_g.func_70617_f_() && !HitSelect.f.field_71439_g.func_70090_H() && !HitSelect.f.field_71439_g.func_70644_a(Potion.field_76440_q) && HitSelect.f.field_71439_g.field_70154_o == null;
                    if (var6) {
                        AttackTracker.Z(this.L <= 0);
                        break;
}
                    AttackTracker.Z(false);
}
}
        } else {
            AttackTracker.Z(true);
}
        if (this.L > 0) {
            --this.L;
}
}
    @Override
    public void P(long var1) {
        AttackTracker.Z(true);
        this.m = 0;
        this.L = 0;
        this.D = null;
}
    public void onReceivePacket(char var1, ReceivePacketEvent var2, int var3, short var4) {
        S19PacketEntityStatus var9;
        if (var2.d instanceof S19PacketEntityStatus && (var9 = (S19PacketEntityStatus)var2.d).func_149161_a((World)HitSelect.f.field_71441_e) instanceof EntityPlayerSP && var9.func_149160_c() == 2 && MathUtil.Q(chance.k(), 0L)) {
            this.L = (int)MathUtil.h(minPauseTick.L(), maxPauseTick.L());
}
}
    public void onAttackEntity(long var1, AttackEntityEvent var3) {
        if (var3.O() instanceof EntityPlayer) {
            this.D = (EntityPlayer)var3.O();
            this.m = 60;
}
}
    static {
        chance = new PercentageSetting("Chance", 100);
        minPauseTick = new NumberSetting("Min-pause-tick", 5.0f, 1.0f, 20.0f, 1.0f);
        maxPauseTick = new NumberSetting("Max-pause-tick", 6.0f, 1.0f, 20.0f, 1.0f);
        strategy = new ModeSetting("Strategy", "NORMAL", "CRITICALS");
}
}