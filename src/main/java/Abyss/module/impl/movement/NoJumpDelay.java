/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package Abyss.module.impl.movement;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.NoJumpDelayBinder;
import Abyss.event.events.PreTickEvent;
import Abyss.internal.accessor.EntityLivingBaseStateAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.NumberSetting;
import java.io.UnsupportedEncodingException;
import net.minecraft.entity.EntityLivingBase;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class NoJumpDelay
extends Module
implements EventSubscriber {
    public static NumberSetting jumpTicks;

    private static void a() {
}
    public void onPreTick(PreTickEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        EntityLivingBaseStateAccessor.x(14848, (EntityLivingBase)NoJumpDelay.f.thePlayer, Math.min(EntityLivingBaseStateAccessor.C((EntityLivingBase)NoJumpDelay.f.thePlayer), (int)jumpTicks.L() + 1));
}
    public NoJumpDelay(long var1) {
        super(0x27E68309D5DFL ^ var1 ^ 0x754F2FE8E058L);
        this.declare("NoJumpDelay", Category.Movement, "Remove vanilla hold-space jump delay", new Setting[0]);
        var1 = 0x27E68309D5DFL ^ var1;
}
    @Override
    public final void x(long var1, EventBus var3) {
        NoJumpDelayBinder.T(var3, this);
}
    @Override
    public String g(long var1) {
        return String.valueOf((int)jumpTicks.L());
}
    static {
        NoJumpDelay.a();
        jumpTicks = new NumberSetting("Jump-ticks", 0.0f, 0.0f, 10.0f, 1.0f);
}
}