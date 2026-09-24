/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.player;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.NoHitDelayBinder;
import Abyss.event.events.ClickMouseEvent;
import Abyss.event.events.PostClickMouseEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.internal.accessor.MinecraftAccessor;
import Abyss.internal.accessor.PlayerControllerStateAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class NoHitDelay
extends Module
implements EventSubscriber {
    private static long a = 52192099837058L;

    private int S;

    public void onPostTick(long var1, PostTickEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (PlayerControllerStateAccessor.q(NoHitDelay.f.playerController) || PlayerControllerStateAccessor.s(0L, NoHitDelay.f.playerController) != 0.0f || NoHitDelay.f.playerController.getCurrentGameType().isCreative()) {
            this.S = 5;
        } else if (this.S <= 0) {
            PlayerControllerStateAccessor.w((byte)0, 7374982, 11824981, NoHitDelay.f.playerController, 0);
        } else {
            --this.S;
}
}
    public void onPostClickMouse(PostClickMouseEvent var1) {
        MinecraftAccessor.c(f, 0, 0L);
}
    public void onClickMouse(ClickMouseEvent var3) {
        MinecraftAccessor.c(f, 0, 0L);
}
    @Override
    public void P(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.S = PlayerControllerStateAccessor.W(NoHitDelay.f.playerController);
}
    public NoHitDelay(int var1, char var2, char var3) {
        super(((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a ^ 0x5CD679F2BAFDL);
        this.declare("NoHitDelay", Category.Player, "Remove 10 ticks hit delay", new Setting[0]);
        this.S = 0;
}
    @Override
    public final void x(long var1, EventBus var3) {
        NoHitDelayBinder.k(var3, this);
}
}