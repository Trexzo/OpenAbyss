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
    public static void onUpdatePlayerMoveState(MovementInput var0, GameSettings var1, CallbackInfo var2) {
        var0.moveStrafe = 0.0f;
        var0.moveForward = 0.0f;
        if (var1.keyBindForward.isKeyDown()) {
            var0.moveForward += 1.0f;
}
        if (var1.keyBindBack.isKeyDown()) {
            var0.moveForward -= 1.0f;
}
        if (var1.keyBindLeft.isKeyDown()) {
            var0.moveStrafe += 1.0f;
}
        if (var1.keyBindRight.isKeyDown()) {
            var0.moveStrafe -= 1.0f;
}
        var0.jump = var1.keyBindJump.isKeyDown();
        var0.sneak = var1.keyBindSneak.isKeyDown();
        MoveInputEvent var11 = new MoveInputEvent(var0.moveForward, var0.moveStrafe, var0.jump, var0.sneak, 0.3);
        AbyssClient.w.e(var11, 18670087776179L);
        double var12 = var11.r();
        var0.moveForward = var11.t();
        var0.moveStrafe = var11.R();
        var0.jump = var11.d();
        var0.sneak = var11.b();
        if (var0.sneak) {
            var0.moveStrafe *= (float)var12;
            var0.moveForward *= (float)var12;
}
        AbyssClient.w.e(new PostMoveInputEvent(11185, 1025946335), 18670087776179L);
        var2.cancel();
}
}