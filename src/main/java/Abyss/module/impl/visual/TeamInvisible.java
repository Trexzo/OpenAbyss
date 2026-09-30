/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.TeamInvisibleBinder;
import Abyss.event.events.EntityRenderStateEvent;
import Abyss.event.events.PostRenderCapeEvent;
import Abyss.event.events.PostRenderModelBipedEvent;
import Abyss.event.events.PreRenderCapeEvent;
import Abyss.event.events.PreRenderEntityEvent;
import Abyss.event.events.PreRenderModelBipedEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Teams;
import Abyss.setting.Setting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.RaytraceUtil;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class TeamInvisible
extends Module
implements EventSubscriber {
    private static Map d;

    private static long a = 110532025384406L;

    private static Object[] e;
    private static long[] b;
        public static PercentageSetting opacity;
    private static String[] g;
        public static NumberSetting range;

    @Override
    public final void x(long var1, EventBus var3) {
        TeamInvisibleBinder.y(var3, this);
}
    public void onPostRenderModelBiped(PostRenderModelBipedEvent var1, long var2) {
        this.j(var1.v, 41139269005963L);
        var1.G();
}
    public void onPreRenderModelBiped(long var1, PreRenderModelBipedEvent var3) {
        this.v(var3.O, 111893817914976L);
        var3.G();
}
    @Override
    public String g(long var1) {
        return opacity.k() + "%";
}
    public void v(Entity var1, long var2) {
        if (this.C(1481626796L, '\ub4eb', var1)) {
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)((float)opacity.k() / 100.0f));
            GlStateManager.depthMask((boolean)false);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc((int)770, (int)771);
            GlStateManager.alphaFunc((int)516, (float)0.003921569f);
}
}
    public void onPreRenderCape(PreRenderCapeEvent var1, long var2) {
        this.v((Entity)var1.F, 111893817914976L);
        var1.G();
}
    public void j(Entity var1, long var2) {
        if (this.C(1481626796L, '\ub4eb', var1)) {
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc((int)516, (float)0.1f);
            GlStateManager.depthMask((boolean)true);
}
}
    private boolean C(long var1, char var3, Entity var4) {
        long var5 = (0x584FD4AC0000L | (long)var3 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x114D839FBD3DL;
        return var4 != TeamInvisible.f.thePlayer && RaytraceUtil.q(var7, var4, range.L()) && Teams.g(0L, var4);
}
    public void onEntityRenderState(char var1, int var2, EntityRenderStateEvent var3, int var4) {
        long var5 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var4 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x39E01A9B25A4L;
        this.j(var3.k, var7);
        var3.G();
}
    public void onPreRenderEntity(PreRenderEntityEvent var1, long var2) {
        this.v(var1.O, 111893817914976L);
        var1.G();
}
    public TeamInvisible(char var1, long var2) {
        super(((long)var1 << 48 | 0L) ^ a ^ 0x61AF91DD8289L);
        this.declare("TeamInvisible", Category.Visual, "Let your teammates be \"Invisible\"", new Setting[0]);
}
    public void onPostRenderCape(long var1, byte var3, PostRenderCapeEvent var4) {
        long var5 = (var1 << 8 | (long)var3 << 56 >>> 56) ^ a;
        long var7 = var5 ^ 0x4686FA54D673L;
        this.j((Entity)var4.U, var7);
        var4.G();
}
    static {
        e = new Object[7];
        g = new String[7];
        d = new HashMap(13);
        b = new long[]{-5350843204115390627L, 3042616221696105688L, 634406712226758148L, -6769754617391115710L};
        range = new NumberSetting("Range", 20.0f, 1.0f, 64.0f, 1.0f);
        opacity = new PercentageSetting("Opacity", 20);
}
}