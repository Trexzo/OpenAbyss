/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.effect.EntityLightningBolt
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.world.World
 */
package Abyss.util.render;

import Abyss.util.MinecraftRef;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class LightningRenderer {
    private static Minecraft E;
    
        

    private static void l(Entity var0) {
        LightningRenderer.E.field_71441_e.func_72942_c((Entity)new EntityLightningBolt((World)LightningRenderer.E.field_71441_e, var0.field_70165_t, var0.field_70163_u, var0.field_70161_v));
        LightningRenderer.E.field_71441_e.func_175731_a(new BlockPos(var0.field_70165_t, var0.field_70163_u, var0.field_70161_v), "ambient.weather.thunder", 5.0f, 1.0f, false);
}
    private static void emitParticleAtEntity(Entity var0, EnumParticleTypes var1) {
        LightningRenderer.E.field_71452_i.func_178926_a(var0, var1);
}
    private static void K(Entity var0, int var1) {
        BlockPos var4 = new BlockPos(var0.field_70165_t, var0.field_70163_u + (double)var0.func_70047_e(), var0.field_70161_v);
        LightningRenderer.E.field_71438_f.func_180439_a(null, 2001, var4, var1);
        LightningRenderer.E.field_71438_f.func_180439_a(null, 2001, var4, var1);
        LightningRenderer.E.field_71438_f.func_180439_a(null, 2001, var4, var1);
}
    private static void f(double var0, double var2, long var4, double var6, double var8, int var10) {
        BlockPos var11 = new BlockPos(var0, var2 + var8, var6);
        LightningRenderer.E.field_71438_f.func_180439_a(null, 2001, var11, var10);
        LightningRenderer.E.field_71438_f.func_180439_a(null, 2001, var11, var10);
        LightningRenderer.E.field_71438_f.func_180439_a(null, 2001, var11, var10);
}
    private static void playAuxSFX(World var0, double var1, double var5, double var7, double var9) {
        BlockPos var11 = new BlockPos(var1, var5 + var9, var7);
        var0.func_175718_b(2003, var11, 0);
        var0.func_175718_b(2003, var11, 0);
        var0.func_175718_b(2003, var11, 0);
}
    public static void f(int var0, long var1, double var3, double var5, double var7, double var9) {
        switch (var0) {
            default: {
                break;
}
            case 1: {
                LightningRenderer.f(var3, var5, 119683151908764L, var7, var9, 152);
                break;
}
            case 2: {
                LightningRenderer.playAuxSFX((World)LightningRenderer.E.field_71441_e, var3, var5, var7, var9);
                break;
}
            case 3: {
                LightningRenderer.N(var3, var5, var7);
}
}
}
    private static void N(double var0, double var2, double var7) {
        LightningRenderer.E.field_71441_e.func_72942_c((Entity)new EntityLightningBolt((World)LightningRenderer.E.field_71441_e, var0, var2, var7));
        LightningRenderer.E.field_71441_e.func_175731_a(new BlockPos(var0, var2, var7), "ambient.weather.thunder", 5.0f, 1.0f, false);
}
    private static void F(Entity var0) {
        BlockPos var3 = new BlockPos(var0.field_70165_t, var0.field_70163_u + (double)var0.func_70047_e(), var0.field_70161_v);
        var0.field_70170_p.func_175718_b(2003, var3, 0);
        var0.field_70170_p.func_175718_b(2003, var3, 0);
        var0.field_70170_p.func_175718_b(2003, var3, 0);
}
    private static void z(Entity var0, EnumParticleTypes var1) {
        var0.func_130014_f_().func_175688_a(var1, var0.field_70165_t, var0.field_70163_u, var0.field_70161_v, 0.0, 0.0, 0.0, new int[0]);
}
    public static void E(short var0, Entity var1, int var2, short var3, int var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        switch (var2) {
            default: {
                break;
}
            case 1: {
                LightningRenderer.K(var1, 152);
                break;
}
            case 2: {
                LightningRenderer.F(var1);
                break;
}
            case 3: {
                LightningRenderer.l(var1);
}
}
}
    static {
        E = MinecraftRef.c((byte)0, 0L);
}
}