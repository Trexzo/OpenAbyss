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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class SpeedMine
extends Module
implements EventSubscriber {
    private static long a = 40047299343839L;

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
       BlockPos var19 = PlayerControllerStateAccessor.Z(SpeedMine.f.playerController);
       if (SpeedMine.f.inGameHasFocus) {
          int var20 = (int)delay.L();
          if (var20 < 5.0 && (delayChance.k() == 100 || MathUtil.Q(delayChance.k(),0L) && var19 != this.b)) {
             if (var20 == 0.0) {
                PlayerControllerStateAccessor.w((byte)0, 7374982, 11824981, SpeedMine.f.playerController, 0);
             } else if (PlayerControllerStateAccessor.W(SpeedMine.f.playerController) > var20) {
                PlayerControllerStateAccessor.w((byte)0, 7374982, 11824981, SpeedMine.f.playerController, var20);
             }
          }

          double var21 = 1.0 + increaseSpeed.k() / 100.0;
          if (var21 > 1.0) {
             if (!SpeedMine.f.thePlayer.capabilities.isCreativeMode && KeyBindUtil.V(SpeedMine.f.gameSettings.keyBindAttack.getKeyCode(), 64165991731362L)) {
                float var23 = PlayerControllerStateAccessor.s(0L, SpeedMine.f.playerController);
                switch (mode.Y()) {
                   case "PRE":
                      if (MathUtil.Q(speedChance.k(),0L)) {
                         float var26 = (float)(1.0 - 1.0 / var21);
                         if (var23 > 0.0F && var23 < var26) {
                            PlayerControllerStateAccessor.e(0L, SpeedMine.f.playerController, var26);
                         }
                      }
                      break;
                   case "POST":
                      if (MathUtil.Q(speedChance.k(),0L)) {
                         double var27 = 1.0 / var21;
                         if (var23 < 1.0F && var23 >= var27) {
                            PlayerControllerStateAccessor.e(0L, SpeedMine.f.playerController, 1.0F);
                         }
                      }
                      break;
                   case "INCREASE":
                      float var29 = -1.0F;
                      if (MathUtil.Q(speedChance.k(),0L) && var23 < 1.0F) {
                         if (SpeedMine.f.objectMouseOver != null && var23 > this.J) {
                            var29 = (float)(
                               this.J
                                  + BlockUtil.g(
                                        SpeedMine.f.theWorld.getBlockState(SpeedMine.f.objectMouseOver.getBlockPos()).getBlock(),
                                        SpeedMine.f.thePlayer.inventory.getStackInSlot(SpeedMine.f.thePlayer.inventory.currentItem),
                                        false,
                                        false
                                     )
                                     * (var21 - 0.2152857 * (var21 - 1.0))
                            );
                         }

                         if (var29 != -1.0F && var23 > 0.0F) {
                            PlayerControllerStateAccessor.e(0L, SpeedMine.f.playerController, var29);
                         }
                      }

                      this.J = var23;
                }
             } else if (mode.R("INCREASE")) {
                this.J = 0.0F;
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