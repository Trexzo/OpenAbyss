/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 */
package Abyss.module.impl.world;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AutoToolBinder;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.Category;
import Abyss.module.PriorityModule;
import Abyss.module.impl.player.AutoWeapon;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.TimerUtil;
import Abyss.util.packet.OutgoingPacketState;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class AutoTool
extends PriorityModule
implements EventSubscriber {
    private static long a = 65895564979047L;
    private int J;
    private boolean I;
    private static Map g;
    private static Object[] h;
    public static BooleanSetting switchBackToSword;
    private static String[] k;
    public static BooleanSetting disableWhenHoldingSword;
    private static long[] d;
    public static BooleanSetting switchBack;
    public static NumberSetting delay;
    private final TimerUtil t;
    public static BooleanSetting requireSneak;
    private boolean S;
    
    @Override
    public void P(long var1) {
        this.J = AutoTool.f.thePlayer.inventory.currentItem;
}
    public AutoTool(long var1) {
        super((a ^ var1 ^ 0x3C326CF0AF43L) >>> 16, (char)((a ^ var1 ^ 0x3C326CF0AF43L) << 48 >>> 48));
        this.declare("AutoTool", Category.World, "Switch to the right tools when you are mining", new Setting[0]);
        var1 = a ^ var1;
        this.t = new TimerUtil();
        this.I = false;
        this.J = -1;
        this.S = false;
}
    @Override
    public String g(long var1) {
        return String.valueOf((int)delay.L());
}
    @Override
    public final void x(long var1, EventBus var3) {
        AutoToolBinder.v(var3, this);
}
    public void onPreMouseInput(long var1, PreMouseInputEvent var3) {
        if (!this.I) {
            this.J = AutoTool.f.thePlayer.inventory.currentItem;
}
        if (!(disableWhenHoldingSword.c() && AutoTool.f.thePlayer.getHeldItem() != null && AutoTool.f.thePlayer.getHeldItem().getItem() instanceof ItemSword || requireSneak.c() && !AutoTool.f.thePlayer.isSneaking() || !this.Y() || OutgoingPacketState.P || OutgoingPacketState.h)) {
            if (AutoTool.f.currentScreen == null && KeyBindUtil.V(AutoTool.f.gameSettings.keyBindAttack.getKeyCode(), 64165991731362L)) {
                if (AutoTool.f.objectMouseOver != null && AutoTool.f.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !this.S) {
                    this.t.W();
                    this.S = true;
}
                if (this.t.L((long)delay.L(), true)) {
                    BlockPos var12 = AutoTool.f.objectMouseOver.getBlockPos();
                    if (var12 == null) {
                        return;
}
                    Block var13 = AutoTool.f.theWorld.getBlockState(var12).getBlock();
                    if (var13 == null || var13 == Blocks.air) {
                        return;
}
                    if (ItemUtil.e(0L, var13) == -1) {
                        return;
}
                    this.I = true;
                    ItemUtil.P(ItemUtil.e(0L, var13));
                    this.S = false;
}
            } else if (switchBackToSword.c() && this.I) {
                if (AutoWeapon.M(93384294372710L) != -1) {
                    ItemUtil.P(AutoWeapon.M(93384294372710L));
                } else if (this.J != -1) {
                    ItemUtil.P(this.J);
}
                this.I = false;
                this.J = -1;
            } else if (switchBack.c() && this.I && this.J != -1) {
                ItemUtil.P(this.J);
                this.I = false;
                this.J = -1;
}
}
}
    static {
        h = new Object[10];
        k = new String[10];
        g = new HashMap(13);
        d = new long[]{6204913656527195237L, -3535756269773128318L, -8377926795496370906L, -3172277969201009195L, 8119325576134456104L, 4460089624490024534L};
        disableWhenHoldingSword = new BooleanSetting("Disable-when-holding-sword", true);
        delay = new NumberSetting("Delay", 0.0f, 0.0f, 1000.0f, 1.0f);
        switchBackToSword = new BooleanSetting("Switch-back-to-sword", false);
        switchBack = new BooleanSetting("Switch-back", true);
        requireSneak = new BooleanSetting("Require-sneak", false);
}
}