/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.ItemRenderer
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;

public final class ItemRendererAccessor {
    private static Accessor C;
    private static Accessor h;
    private static Accessor u;
    private static Accessor j;
    private static Accessor T;
    private static Accessor L;
    private static Accessor W;
    private static Accessor y;
    private static Accessor O;
    private static Accessor v;

    public static void x(ItemRenderer var0, EntityPlayerSP var1, float var2) {
        Accessor.v(W, new Object[]{var0, var1, Float.valueOf(var2)});
}
    public static void v(ItemRenderer var0, AbstractClientPlayer var1, float var2) {
        Accessor.v(h, new Object[]{var0, var1, Float.valueOf(var2)});
}
    public static void Z(ItemRenderer var0, AbstractClientPlayer var1, float var2, float var3, float var4) {
        Accessor.v(O, new Object[]{var0, var1, Float.valueOf(var2), Float.valueOf(var3), Float.valueOf(var4)});
}
    public static void s(ItemRenderer var0, float var1, float var2) {
        Accessor.v(j, new Object[]{var0, Float.valueOf(var1), Float.valueOf(var2)});
}
    public static void E(ItemRenderer var0, float var1, AbstractClientPlayer var2) {
        Accessor.v(L, new Object[]{var0, Float.valueOf(var1), var2});
}
    public static void q(ItemRenderer var0, AbstractClientPlayer var1, float var2, float var3) {
        Accessor.v(C, new Object[]{var0, var1, Float.valueOf(var2), Float.valueOf(var3)});
}
    public static void U(ItemRenderer var0, float var1) {
        Accessor.v(v, new Object[]{var0, Float.valueOf(var1)});
}
    public static void e(ItemRenderer var0) {
        Accessor.v(y, new Object[]{var0});
}
    public static void k(ItemRenderer var0, AbstractClientPlayer var1) {
        Accessor.v(u, new Object[]{var0, var1});
}
    public static void z(ItemRenderer var0, float var1, float var2) {
        Accessor.v(T, new Object[]{var0, Float.valueOf(var1), Float.valueOf(var2)});
}
    static {
        T = MethodAccessors.C(ItemRenderer.class, new Class[]{Float.TYPE, Float.TYPE}, new String[]{"rotateArroundXAndY", "rotateArroundXAndY"});
        u = MethodAccessors.C(ItemRenderer.class, new Class[]{AbstractClientPlayer.class}, new String[]{"setLightMapFromPlayer", "setLightMapFromPlayer"});
        W = MethodAccessors.C(ItemRenderer.class, new Class[]{EntityPlayerSP.class, Float.TYPE}, new String[]{"rotateWithPlayerRotations", "rotateWithPlayerRotations"});
        h = MethodAccessors.C(ItemRenderer.class, new Class[]{AbstractClientPlayer.class, Float.TYPE}, new String[]{"performDrinking", "performDrinking"});
        v = MethodAccessors.C(ItemRenderer.class, new Class[]{Float.TYPE}, new String[]{"doItemUsedTransformations", "doItemUsedTransformations"});
        C = MethodAccessors.C(ItemRenderer.class, new Class[]{AbstractClientPlayer.class, Float.TYPE, Float.TYPE}, new String[]{"renderPlayerArm", "renderPlayerArm"});
        y = MethodAccessors.C(ItemRenderer.class, new Class[0], new String[]{"doBlockTransformations", "doBlockTransformations"});
        L = MethodAccessors.C(ItemRenderer.class, new Class[]{Float.TYPE, AbstractClientPlayer.class}, new String[]{"doBowTransformations", "doBowTransformations"});
        j = MethodAccessors.C(ItemRenderer.class, new Class[]{Float.TYPE, Float.TYPE}, new String[]{"transformFirstPersonItem", "transformFirstPersonItem"});
        O = MethodAccessors.C(ItemRenderer.class, new Class[]{AbstractClientPlayer.class, Float.TYPE, Float.TYPE, Float.TYPE}, new String[]{"renderItemMap", "renderItemMap"});
}
}