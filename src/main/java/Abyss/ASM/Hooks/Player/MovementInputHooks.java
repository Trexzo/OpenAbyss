/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.util.MovementInput
 */
package Abyss.ASM.Hooks.Player;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.AbyssClient;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PostMoveInputEvent;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;

public class MovementInputHooks {
    private static final long public static void onUpdatePlayerMoveState(MovementInput var0, GameSettings var1, CallbackInfo var2) {
        var0.field_78902_a = 0.0f;
        var0.field_78900_b = 0.0f;
        if (var1.field_74351_w.func_151470_d()) {
            var0.field_78900_b += 1.0f;
}
        if (var1.field_74368_y.func_151470_d()) {
            var0.field_78900_b -= 1.0f;
}
        if (var1.field_74370_x.func_151470_d()) {
            var0.field_78902_a += 1.0f;
}
        if (var1.field_74366_z.func_151470_d()) {
            var0.field_78902_a -= 1.0f;
}
        var0.field_78901_c = var1.field_74314_A.func_151470_d();
        var0.field_78899_d = var1.field_74311_E.func_151470_d();
        MoveInputEvent var11 = new MoveInputEvent(var0.field_78900_b, var0.field_78902_a, var0.field_78901_c, var0.field_78899_d, 0.3);
        AbyssClient.w.e(var11, 18670087776179L);
        double var12 = var11.r();
        var0.field_78900_b = var11.t();
        var0.field_78902_a = var11.R();
        var0.field_78901_c = var11.d();
        var0.field_78899_d = var11.b();
        if (var0.field_78899_d) {
            var0.field_78902_a *= (float)var12;
            var0.field_78900_b *= (float)var12;
}
        AbyssClient.w.e(new PostMoveInputEvent(11185, 1025946335), 18670087776179L);
        var2.cancel();
}
}