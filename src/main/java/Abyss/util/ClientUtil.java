/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.ISound
 *  net.minecraft.client.audio.PositionedSoundRecord
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Timer
 */
package Abyss.util;

import Abyss.internal.accessor.MinecraftAccessor;
import Abyss.module.impl.misc.NameHider;
import Abyss.module.impl.misc.NoObfuscation;
import Abyss.ui.swing.ConfigManagerWindow;
import Abyss.util.BlockUtil;
import Abyss.util.BuildInfo;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.RaytraceUtil;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;

public class ClientUtil {
    private static Map<Integer, Boolean> G;
    
    private static Minecraft l;
    
    
    
    public static void I(String var0) {
        ConfigManagerWindow.D.add(var0);
}
    public static void t(long var0, String var2) {
        long var3 = var0 ^ 0x3A2AE767A7F0L;
        ClientUtil.l.field_71456_v.func_146158_b().func_146227_a((IChatComponent)new ChatComponentText(BuildInfo.y(var3) + var2));
        ConfigManagerWindow.D.add(BuildInfo.y(var3) + var2);
}
    public static boolean I(double var0) {
        return var0 == Math.floor(var0);
}
    public static boolean q() {
        return !ClientUtil.I() ? false : ClientUtil.l.field_71441_e.func_175623_d(new BlockPos(ClientUtil.l.field_71439_g.field_70165_t, ClientUtil.l.field_71439_g.field_70163_u - 1.0, ClientUtil.l.field_71439_g.field_70161_v));
}
    public static boolean P() {
        return ClientUtil.l.field_71441_e.func_72945_a((Entity)ClientUtil.l.field_71439_g, ClientUtil.l.field_71439_g.func_174813_aQ().func_72317_d(ClientUtil.l.field_71439_g.field_70159_w / 3.0, -1.0, ClientUtil.l.field_71439_g.field_70179_y / 3.0)).isEmpty();
}
    public static boolean b(int var0, long var1) {
        int var7 = KeyBindUtil.m(32881896332787L, var0);
        boolean var8 = KeyBindUtil.V(var7, 64165991731362L);
        boolean var9 = var8 && G.getOrDefault(var7, false) == false;
        G.put(var7, var8);
        return var9;
}
    public static String replaceString(String var0) {
        if (var0 == null) {
            return var0;
}
        var0 = NameHider.U(var0);
        return NoObfuscation.f(var0);
}
    public static void B(String var0) {
        l.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation(var0), (float)1.0f));
}
    public static float H(long var0) {
        return MinecraftAccessor.o((Minecraft)ClientUtil.l).field_74281_c;
}
    public static void b(String var0) {
        ClientUtil.l.field_71456_v.func_146158_b().func_146227_a((IChatComponent)new ChatComponentText(var0));
        ConfigManagerWindow.D.add(var0);
}
    public static void e(long var0) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        ClientUtil.l.field_71456_v.func_146158_b().func_146227_a((IChatComponent)new ChatComponentText("------------"));
        ConfigManagerWindow.D.add("------------");
}
    public static boolean I() {
        return ClientUtil.l.field_71439_g != null && ClientUtil.l.field_71441_e != null && l.func_147114_u() != null;
}
    public static boolean d() {
        return ClientUtil.l.field_71474_y.field_74314_A.func_151470_d();
}
    public static BlockPos p() {
        return BlockUtil.Z(RaytraceUtil.f());
}
    public static Timer b(long var0) {
        return MinecraftAccessor.o(l);
}
    static {
        G = new HashMap<Integer, Boolean>();
        l = MinecraftRef.c((byte)0, 0L);
}
}