/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.ItemRenderer
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.ItemMap
 *  net.minecraft.util.MathHelper
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AnimationsBinder;
import Abyss.event.events.GetArmSwingAnimationEndEvent;
import Abyss.event.events.RenderItemInFirstPersonEvent;
import Abyss.internal.accessor.ItemRendererAccessor;
import Abyss.internal.synthetic.AnimationsSwitchMapEnumAction;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemMap;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class Animations
extends Module
implements EventSubscriber {
    private static long a;
    private static String[] d;
    static {
        a = 90915734307650L;
    }
    private static Map n;
    public static HeaderSetting offsetSettings;
    
    public static HeaderSetting scaleSettings;
    private static Map t;
    private static String[] x;
    public static NumberSetting offsetX;
    public static NumberSetting scaleX;
    public static NumberSetting swingSpeed;
    public static NumberSetting scaleY;
    private static Object[] v;
    public static ModeSetting mode;
    public static NumberSetting rotationZ;
    public static HeaderSetting rotationSettings;
    public static BooleanSetting noEquipReset;
    public static NumberSetting scaleZ;
        private static Map g;
    public static BooleanSetting noRotationsEffect;
    public static NumberSetting rotationX;
    public static NumberSetting rotationY;
    public static NumberSetting offsetY;
    public static NumberSetting offsetZ;

    public void onGetArmSwingAnimationEnd(GetArmSwingAnimationEndEvent var1) {
        var1.t((int)((float)var1.N() * (-this.d(swingSpeed.L()) / 100.0f + 1.0f)));
}
    public static void J() {
        GlStateManager.translate((float)(offsetX.L() / 100.0f), (float)(offsetY.L() / 100.0f), (float)(offsetZ.L() / 100.0f));
}
    private float d(float var1) {
        return var1 / 2.0f * 400.0f - 200.0f;
}
    public static void C() {
        GlStateManager.rotate((float)rotationX.L(), (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.rotate((float)rotationY.L(), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)rotationZ.L(), (float)0.0f, (float)0.0f, (float)1.0f);
}
    public static void U() {
        GlStateManager.scale((float)scaleX.L(), (float)scaleY.L(), (float)scaleZ.L());
}
    @Override
    public final void x(long var1, EventBus var3) {
        AnimationsBinder.A(var3, this);
}
    public void onRenderItemInFirstPerson(long var1, RenderItemInFirstPersonEvent var3) {
        if (!(var3.e.getItem() instanceof ItemMap) && var3.d.equals((Object)EnumAction.BLOCK) && !mode.R("1.7")) {
            EnumAction var7 = var3.d;
            ItemRenderer var8 = f.getItemRenderer();
            float var9 = var3.J;
            float var10 = var3.C;
            float var11 = MathHelper.sin((float)(MathHelper.sqrt_float((float)var10) * (float)Math.PI));
            block0 : switch (AnimationsSwitchMapEnumAction.j[var7.ordinal()]) {
                case 1: {
                    switch (mode.Y()) {
                        case "SMOOTH": {
                            ItemRendererAccessor.s(var8, var9, 0.0f);
                            float var14 = -var11 * 2.0f;
                            Animations.f(0.0, var14 / 10.0f + 0.1f, 0.0);
                            GlStateManager.rotate((float)(var14 * 10.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                            GlStateManager.rotate((float)250.0f, (float)0.2f, (float)1.0f, (float)-0.6f);
                            GlStateManager.rotate((float)-10.0f, (float)1.0f, (float)0.5f, (float)1.0f);
                            GlStateManager.rotate((float)(-var14 * 20.0f), (float)1.0f, (float)0.5f, (float)1.0f);
                            break block0;
}
                        case "EXHIBITION": {
                            ItemRendererAccessor.s(var8, var9 / 2.0f, 0.0f);
                            Animations.f(0.0, 0.3f, -0.0);
                            GlStateManager.rotate((float)(-var11 * 31.0f), (float)1.0f, (float)0.0f, (float)2.0f);
                            GlStateManager.rotate((float)(-var11 * 33.0f), (float)1.5f, (float)(var11 / 1.1f), (float)0.0f);
                            ItemRendererAccessor.e(var8);
                            break block0;
}
                        case "STAB": {
                            float var15 = MathHelper.sin((float)(MathHelper.sqrt_float((float)var10) * (float)Math.PI));
                            Animations.f(0.6f, 0.3f, (double)-0.6f + (double)(-var15) * 0.7);
                            GlStateManager.rotate((float)6090.0f, (float)0.0f, (float)0.0f, (float)0.1f);
                            GlStateManager.rotate((float)6085.0f, (float)0.0f, (float)0.1f, (float)0.0f);
                            GlStateManager.rotate((float)6110.0f, (float)0.1f, (float)0.0f, (float)0.0f);
                            ItemRendererAccessor.s(var8, 0.0f, 0.0f);
                            ItemRendererAccessor.e(var8);
                            break block0;
}
                        case "SPIN": {
                            ItemRendererAccessor.s(var8, var9, 0.0f);
                            Animations.f(0.0, 0.2f, -1.0);
                            GlStateManager.rotate((float)-59.0f, (float)-1.0f, (float)0.0f, (float)3.0f);
                            GlStateManager.rotate((float)(-(System.currentTimeMillis() / 2L % 360L)), (float)1.0f, (float)0.0f, (float)0.0f);
                            GlStateManager.rotate((float)60.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                            break block0;
}
                        case "SIGMA": {
                            ItemRendererAccessor.s(var8, var9, 0.0f);
                            Animations.f(0.0, 0.1f, 0.0);
                            ItemRendererAccessor.e(var8);
                            GlStateManager.rotate((float)(var11 * 35.0f / 2.0f), (float)0.0f, (float)1.0f, (float)1.5f);
                            GlStateManager.rotate((float)(-var11 * 135.0f / 4.0f), (float)1.0f, (float)1.0f, (float)0.0f);
                            break block0;
}
                        case "WOOD": {
                            ItemRendererAccessor.s(var8, var9 / 2.0f, 0.0f);
                            Animations.f(0.0, 0.3f, -0.0);
                            GlStateManager.rotate((float)(-var11 * 30.0f), (float)1.0f, (float)0.0f, (float)2.0f);
                            GlStateManager.rotate((float)(-var11 * 44.0f), (float)1.5f, (float)(var11 / 1.2f), (float)0.0f);
                            ItemRendererAccessor.e(var8);
                            break block0;
}
                        case "SWONG": {
                            ItemRendererAccessor.s(var8, var9 / 2.0f, var10);
                            GlStateManager.rotate((float)(var11 * 30.0f / 2.0f), (float)(-var11), (float)-0.0f, (float)9.0f);
                            GlStateManager.rotate((float)(var11 * 40.0f), (float)1.0f, (float)(-var11 / 2.0f), (float)-0.0f);
                            Animations.f(0.0, 0.2f, 0.0);
                            ItemRendererAccessor.e(var8);
                            break block0;
}
                        case "CHILL": {
                            ItemRendererAccessor.s(var8, -0.25f, 1.0f + var11 / 10.0f);
                            GL11.glRotated((double)(-var11 * 25.0f), (double)1.0, (double)0.0, (double)0.0);
                            ItemRendererAccessor.e(var8);
                            break block0;
}
                        case "KOMOREBI": {
                            Animations.f(0.41f, -0.25, -0.5555557012557983);
                            Animations.f(0.0, 0.0, 0.0);
                            GlStateManager.rotate((float)35.0f, (float)0.0f, (float)1.5f, (float)0.0f);
                            float var16 = MathHelper.sin((float)(var10 * var10 / 64.0f * (float)Math.PI));
                            GlStateManager.rotate((float)(var16 * -5.0f), (float)0.0f, (float)0.0f, (float)0.0f);
                            GlStateManager.rotate((float)(var11 * -12.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                            GlStateManager.rotate((float)(var11 * -65.0f), (float)1.0f, (float)0.0f, (float)0.0f);
                            ItemRendererAccessor.e(var8);
                            break block0;
}
                        case "RHYS": {
                            ItemRendererAccessor.s(var8, var9, var10);
                            ItemRendererAccessor.e(var8);
                            Animations.f(-0.3f, -0.1f, -0.0);
                            break block0;
}
                        case "ALLAH": {
                            ItemRendererAccessor.s(var8, var9, 0.0f);
                            ItemRendererAccessor.e(var8);
}
}
}
}
            Animations.U();
            var3.I(21307, 3074332907L);
}
}
    public Animations(long var1) {
        super(a ^ var1 ^ 0x1B20EEA3A7DDL);
        this.declare("Animations", Category.Visual, "Some 1.7 item using animations", new Setting[0]);
        var1 = a ^ var1;
}
    public static void f(double var0, double var2, double var4) {
        GlStateManager.translate((double)(var0 + (double)(offsetX.L() / 100.0f)), (double)(var2 + (double)(offsetY.L() / 100.0f)), (double)(var4 + (double)(offsetZ.L() / 100.0f)));
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    static {
        v = new Object[7];
        x = new String[7];
        g = new HashMap(13);
        d = new String[12];
        n = new HashMap(13);
        t = new HashMap(13);
        noRotationsEffect = new BooleanSetting("No-rotations-effect", true);
        offsetSettings = new HeaderSetting("Offset settings");
        scaleSettings = new HeaderSetting("Scale settings");
        rotationSettings = new HeaderSetting("Rotation settings");
        noEquipReset = new BooleanSetting("No-equip-reset", true);
        mode = new ModeSetting("Mode", true, "NONE", "NONE", "1.7", "SMOOTH", "SPIN", "STAB", "SWONG", "WOOD", "SIGMA", "RHYS", "CHILL", "EXHIBITION", "KOMOREBI", "ALLAH");
        rotationY = new NumberSetting("Rotation-Y", 0.0f, -180.0f, 180.0f, 1.0f);
        scaleZ = new NumberSetting("Scale-Z", 1.0f, 0.0f, 2.0f, 0.01f);
        scaleY = new NumberSetting("Scale-Y", 1.0f, 0.0f, 2.0f, 0.01f);
        rotationX = new NumberSetting("Rotation-X", 0.0f, -180.0f, 180.0f, 1.0f);
        scaleX = new NumberSetting("Scale-X", 1.0f, 0.0f, 2.0f, 0.01f);
        rotationZ = new NumberSetting("Rotation-Z", 0.0f, -180.0f, 180.0f, 1.0f);
        offsetZ = new NumberSetting("Offset-Z", 0.0f, -500.0f, 500.0f, 1.0f);
        swingSpeed = new NumberSetting("Swing-speed", 1.0f, 0.0f, 1.33f, 0.01f);
        offsetX = new NumberSetting("Offset-X", 0.0f, -500.0f, 500.0f, 1.0f);
        offsetY = new NumberSetting("Offset-Y", 0.0f, -500.0f, 500.0f, 1.0f);
}
}