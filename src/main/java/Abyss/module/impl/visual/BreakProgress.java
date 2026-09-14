/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.BlockPos
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BreakProgressBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.PlayerControllerStateAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.setting.Setting;
import Abyss.util.LunarClientDetector;
import Abyss.util.render.CustomFont;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;

public class BreakProgress
extends Module
implements EventSubscriber {
    private BlockPos p;
    private String g;
    private static final long private double B;

    public void onRender3D(Render3DEvent var1) {
        if (this.B != 0.0 && this.p != null) {
            double var10 = (double)this.p.func_177958_n() + 0.5 - BreakProgress.f.func_175598_ae().field_78730_l;
            double var12 = (double)this.p.func_177956_o() + 0.5 - BreakProgress.f.func_175598_ae().field_78731_m;
            double var14 = (double)this.p.func_177952_p() + 0.5 - BreakProgress.f.func_175598_ae().field_78728_n;
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b((float)((float)var10), (float)((float)var12), (float)((float)var14));
            GlStateManager.func_179114_b((float)(-BreakProgress.f.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.func_179114_b((float)BreakProgress.f.func_175598_ae().field_78732_j, (float)(LunarClientDetector.q(0L) ? 1.0f : this.g()), (float)0.0f, (float)0.0f);
            GlStateManager.func_179152_a((float)-0.02266667f, (float)-0.02266667f, (float)-0.02266667f);
            GlStateManager.func_179132_a((boolean)false);
            GlStateManager.func_179097_i();
            CustomFont var16 = Font.s(0L);
            var16.v(this.g, -BreakProgress.f.field_71466_p.func_78256_a(this.g) / 2, -3.0f, -1, 88827598794260L, true);
            GlStateManager.func_179126_j();
            GlStateManager.func_179132_a((boolean)true);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179121_F();
}
}
    @Override
    public void A(long var1) {
        this.B();
}
    public BreakProgress(long var1) {
        super(0x81DF6475C99L ^ var1 ^ 0x57AE38EB1A9AL);
        this.declare("BreakProgress", Category.Visual, "Display the current breaking percentage right on the current breaking block", new Setting[0]);
        var1 = 0x81DF6475C99L ^ var1;
}
    private void B() {
        this.B = 0.0;
        this.p = null;
        this.g = "";
}
    private void w$r4() {
        this.g = (int)(100.0 * this.B) + "%";
}
    public void onPostTick(int var1, PostTickEvent var2, short var3, short var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!BreakProgress.f.field_71439_g.field_71075_bZ.field_75098_d && BreakProgress.f.field_71439_g.field_71075_bZ.field_75099_e) {
            this.B = PlayerControllerStateAccessor.s(0L, BreakProgress.f.field_71442_b);
            if (this.B == 0.0) {
                this.B();
            } else {
                this.p = PlayerControllerStateAccessor.Z(BreakProgress.f.field_71442_b);
                this.w$r4();
}
        } else {
            this.B();
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        BreakProgressBinder.Z(var3, this);
}
    private float g() {
        return BreakProgress.f.field_71474_y.field_74320_O == 2 ? -1.0f : 1.0f;
}
    private static void a() {
}
    static {
        BreakProgress.a();
}
}