/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.util.ResourceLocation
 */
package Abyss.ASM.Hooks.Network;

import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.internal.accessor.NetworkPlayerInfoAccessor;
import Abyss.module.impl.configuration.CustomCape;
import Abyss.util.MinecraftRef;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;

public class NetworkPlayerInfoHooks {
    private static String b;
    
    public static void getLocationCape(ResourceLocation var0, GameProfile var1, NetworkPlayerInfo var2, CallbackInfoReturnable<ResourceLocation> var3) {
        if (var1.getId().equals(MinecraftRef.c((byte)0, (long)0L).thePlayer.getGameProfile().getId()) && !CustomCape.cape.R(b)) {
            var3.setReturnValue(CustomCape.d(0L));
            var3.cancel();
        } else if (var0 == null) {
            NetworkPlayerInfoAccessor.W(var2);
}
}
    static {
        b = "NONE";
}
}