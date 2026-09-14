/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package Abyss.module.impl.world;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.SpeedMineBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.internal.accessor.PlayerControllerStateAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.BlockUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import java.io.UnsupportedEncodingException;
import net.minecraft.util.BlockPos;

public class SpeedMine
extends Module
implements EventSubscriber {
    public static PercentageSetting delayChance;
        private BlockPos b;
    private float J;
    public static ModeSetting mode;
    public static NumberSetting delay;
    public static PercentageSetting speedChance;
    public static PercentageSetting increaseSpeed;

    @Override
    public final void x(long var1, EventBus var3) {
        SpeedMineBinder.H(var3, this);
}
    public SpeedMine(short var1, long var2) {
        super(((long)var1 << 48 | 0L) ^ a ^ 0x3EECAB424E51L);
        this.declare("SpeedMine", Category.World, "Increase your mining speed", new Setting[0]);
        this.b = null;
}
    @Override
    public String g(long var1) {
        if (increaseSpeed.k() != 0) {
            return increaseSpeed.k() + 100 + "%";
}
        return delay.L() != 5.0f ? String.valueOf((int)delay.L()) : "";
}
    public void onPostTick(PostTickEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        block20: {
            BlockPos var19;
            block21: {
                block22: {
                    double var21;
                    var19 = PlayerControllerStateAccessor.Z(SpeedMine.f.field_71442_b);
                    if (!SpeedMine.f.field_71415_G) break block20;
                    int var20 = (int)delay.L();
                    if ((double)var20 < 5.0 && (delayChance.k() == 100 || MathUtil.Q(delayChance.k(), 0L) && var19 != this.b)) {
                        if ((double)var20 == 0.0) {
                            PlayerControllerStateAccessor.w((byte)0, 7374982, 11824981, SpeedMine.f.field_71442_b, 0);
                        } else if (PlayerControllerStateAccessor.W(SpeedMine.f.field_71442_b) > var20) {
                            PlayerControllerStateAccessor.w((byte)0, 7374982, 11824981, SpeedMine.f.field_71442_b, var20);
}
}
                    if (!((var21 = 1.0 + (double)increaseSpeed.k() / 100.0) > 1.0)) break block21;
                    if (SpeedMine.f.field_71439_g.field_71075_bZ.field_75098_d || !KeyBindUtil.V(SpeedMine.f.field_71474_y.field_74312_F.func_151463_i(), 64165991731362L)) break block22;
                    float var23 = PlayerControllerStateAccessor.s(0L, SpeedMine.f.field_71442_b);
                    switch (mode.Y()) {
                        case "PRE": {
                            if (!MathUtil.Q(speedChance.k(), 0L)) break;
                            float var26 = (float)(1.0 - 1.0 / var21);
                            if (var23 > 0.0f && var23 < var26) {
                                PlayerControllerStateAccessor.e(0L, SpeedMine.f.field_71442_b, var26);
                                break;
}
                            break block21;
}
                        case "POST": {
                            if (!MathUtil.Q(speedChance.k(), 0L)) break;
                            double var27 = 1.0 / var21;
                            if (var23 < 1.0f && (double)var23 >= var27) {
                                PlayerControllerStateAccessor.e(0L, SpeedMine.f.field_71442_b, 1.0f);
                                break;
}
                            break block21;
}
                        case "INCREASE": {
                            float var29 = -1.0f;
                            if (MathUtil.Q(speedChance.k(), 0L) && var23 < 1.0f) {
                                if (SpeedMine.f.field_71476_x != null && var23 > this.J) {
                                    var29 = (float)((double)this.J + (double)BlockUtil.g(SpeedMine.f.field_71441_e.func_180495_p(SpeedMine.f.field_71476_x.func_178782_a()).func_177230_c(), SpeedMine.f.field_71439_g.field_71071_by.func_70301_a(SpeedMine.f.field_71439_g.field_71071_by.field_70461_c), false, false) * (var21 - 0.2152857 * (var21 - 1.0)));
}
                                if (var29 != -1.0f && var23 > 0.0f) {
                                    PlayerControllerStateAccessor.e(0L, SpeedMine.f.field_71442_b, var29);
}
}
                            this.J = var23;
}
}
                    break block21;
}
                if (mode.R("INCREASE")) {
                    this.J = 0.0f;
}
}
            this.b = var19;
}
}
    static {
        delayChance = new PercentageSetting("Delay-chance", 100);
        increaseSpeed = new PercentageSetting("Increase-speed", 10);
        speedChance = new PercentageSetting("Speed-chance", 100);
        delay = new NumberSetting("Delay", 0.0f, 0.0f, 5.0f, 1.0f);
        mode = new ModeSetting("Mode", "POST", "PRE", "INCREASE");
}
}