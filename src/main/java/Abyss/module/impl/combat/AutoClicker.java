/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AutoClickerBinder;
import Abyss.event.events.PreTickEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.combat.KillAura;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import java.util.Map;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition;

public class AutoClicker
extends Module
implements EventSubscriber {
    private static long a;
    static {
        a = 116760755216853L;
    }
    private static Map g;
    private static long[] c;
    public static boolean I;
    private long o;
    public static NumberSetting sagUnblockDuration;
    public static NumberSetting maxcps;
    public static NumberSetting sagBlockingTicks;
        public static BooleanSetting sag;
    private boolean J;
    private long M;
    public static NumberSetting mincps;
    public static BooleanSetting breakBlocks;
    private long e;

    @Override
    public final void x(long var1, EventBus var3) {
        AutoClickerBinder.N(var3, this);
}
    @Override
    public void A(long var1) {
        I = false;
        this.o = 0L;
        this.M = 0L;
        this.e = 0L;
        this.J = false;
}
    public AutoClicker(short var1, char var2, int var3) {
        super(((long)var1 << 48 | (long)var2 << 48 >>> 16 | (long)var3 << 32 >>> 32) ^ a ^ 0x4B2612FCDCC8L);
        this.declare("AutoClicker", Category.Combat, "Automatically left click", new Setting[0]);
        this.o = 0L;
        this.M = 0L;
        this.e = 0L;
        this.J = false;
}
    public void onPreTick(long var1, PreTickEvent var3) {
        if (this.o > 0L) {
            this.o -= 50L;
}
        if (this.M > 0L) {
            this.M -= 50L;
}
        if (this.e > 0L) {
            this.e -= 50L;
}
        if (KillAura.a || !KeyBindUtil.V(AutoClicker.f.gameSettings.keyBindAttack.getKeyCode(), 64165991731362L) || AutoClicker.f.currentScreen != null) {
            I = false;
            this.J = false;
        } else if (breakBlocks.c() && AutoClicker.f.objectMouseOver != null && AutoClicker.f.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && AutoClicker.f.objectMouseOver.entityHit == null) {
            KeyBindUtil.A(82009306480869L, AutoClicker.f.gameSettings.keyBindAttack.getKeyCode(), true);
            this.J = false;
            I = false;
        } else {
            I = true;
            if (!(sag.c() && KeyBindUtil.V(AutoClicker.f.gameSettings.keyBindUseItem.getKeyCode(), 64165991731362L) && AutoClicker.f.thePlayer.getHeldItem() != null && AutoClicker.f.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
                this.J = false;
                if (this.o <= 0L) {
                    this.o += MathUtil.e(mincps.L(), maxcps.L());
                    KeyBindUtil.A(82009306480869L, AutoClicker.f.gameSettings.keyBindAttack.getKeyCode(), false);
                    KeyBindUtil.T(27332, (short)-14423, AutoClicker.f.gameSettings.keyBindAttack.getKeyCode(), (short)-22494);
}
            } else if (!this.J && this.M <= 0L) {
                KeyBindUtil.T(27332, (short)-14423, AutoClicker.f.gameSettings.keyBindAttack.getKeyCode(), (short)-22494);
                KeyBindUtil.A(82009306480869L, AutoClicker.f.gameSettings.keyBindUseItem.getKeyCode(), true);
                this.J = true;
                this.e += (long)sagBlockingTicks.L() * 50L;
            } else if (this.J && this.e <= 0L) {
                KeyBindUtil.A(82009306480869L, AutoClicker.f.gameSettings.keyBindUseItem.getKeyCode(), false);
                this.J = false;
                this.M += (long)sagUnblockDuration.L() * 50L;
}
}
}
    @Override
    public String g(long var1) {
        if (mincps.L() != maxcps.L()) {
            return (float)Math.round(mincps.L()) == mincps.L() && (float)Math.round(maxcps.L()) == maxcps.L() ? Math.round(mincps.L()) + "-" + Math.round(maxcps.L()) : mincps.L() + "-" + maxcps.L();
}
        return (float)Math.round(maxcps.L()) == maxcps.L() ? String.valueOf(Math.round(maxcps.L())) : mincps.L() + "-" + maxcps.L();
}
    static {
        I = false;
        sagBlockingTicks = new NumberSetting("Sag-blocking-ticks", 4.0f, 0.0f, 20.0f, 1.0f);
        sag = new BooleanSetting("Sag", false);
        sagUnblockDuration = new NumberSetting("Sag-unblock-duration", 0.0f, 0.0f, 20.0f, 1.0f);
        mincps = new NumberSetting("MinCPS", 13.0f, 1.0f, 20.0f, 0.1f);
        breakBlocks = new BooleanSetting("Break-blocks", true);
        maxcps = new NumberSetting("MaxCPS", 15.0f, 1.0f, 20.0f, 0.1f);
}
}