/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package Abyss.ui.abyss;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.module.impl.configuration.Theme;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.ui.abyss.AbyssArrayListVisibility;
import Abyss.ui.abyss.AbyssColorPicker;
import Abyss.util.KeyBindUtil;
import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.FontRenderer;
import Abyss.util.render.abyss.RenderingUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public final class AbyssClickGuiScreen
extends GuiScreen {
    public static final AbyssClickGuiScreen INSTANCE = new AbyssClickGuiScreen();
    private static final int W = 130;
    private static final int HEAD = 16;
    private static final int ROW = 14;
    private static final int SETTING = 14;
    private static final int SLIDER_SETTING = 22;
    private static final int GAP = 4;
    private static final int PANEL = -267317227;
    private static final int ROW_BG = -267119847;
    private static final int ENABLED = -266593758;
    private static final int HOVER_BG = -266462428;
    private static final int SETTING_BG = -267448813;
    private static final int SETTING_BG_ALT = -267317226;
    private static final float OPEN_MS = 240.0f;
    private static final float STAGGER_MS = 45.0f;
    private final List<Panel> panels = new ArrayList<Panel>();
    private String search = "";
    private long openedAt;
    private long lastFrame = this.openedAt = System.currentTimeMillis();
    private float dt;
    private float gs = 1.0f;
    private float latchedGs = 1.0f;
    private boolean latched;
    private int sw;
    private int sh;
    private AbyssColorPicker picker;
    private boolean pickerOpen;
    private float pickerAnim;
    private TextSetting editing;
    private String editBuffer = "";
    private String editBefore = "";

    private AbyssClickGuiScreen() {
}
    private void ensurePanels() {
        if (!this.panels.isEmpty()) {
            return;
}
        int i = 0;
        for (Category c : Category.values()) {
            if (c == Category.Configuration || c == Category.Macro) continue;
            this.panels.add(new Panel(c, 5 + i++ * 134, 5.0f));
}
}
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.ensurePanels();
        this.metrics();
        this.lastFrame = this.openedAt = System.currentTimeMillis();
        int cols = Math.max(1, (this.sw - 10 + 4) / 134);
        for (int i = 0; i < this.panels.size(); ++i) {
            Panel p = this.panels.get(i);
            if (!p.moved) {
                p.x = 5 + i % cols * 134;
                p.y = 5 + i / cols * 20;
}
            p.clamp();
}
}
    private void metrics() {
        if (!this.latched) {
            float s = ClickGUI.scale == null ? 1.0f : ClickGUI.scale.L();
            this.latchedGs = s <= 0.05f ? 1.0f : s;
}
        this.gs = this.latchedGs;
        this.sw = Math.max(140, (int)((float)this.field_146294_l / this.gs));
        this.sh = Math.max(56, (int)((float)this.field_146295_m / this.gs));
}
    public boolean func_73868_f() {
        return false;
}
    public void func_146281_b() {
        Keyboard.enableRepeatEvents((boolean)false);
        this.commitText();
        if (this.picker != null) {
            this.picker.closed();
}
        this.picker = null;
        this.pickerOpen = false;
        this.pickerAnim = 0.0f;
        this.latched = false;
}
    public void func_73863_a(int mx, int my, float pt) {
        try {
            this.drawScreenImpl(mx, my, pt);
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    private void drawScreenImpl(int mx, int my, float pt) {
        long now = System.currentTimeMillis();
        this.dt = Math.min(0.1f, (float)(now - this.lastFrame) / 1000.0f);
        this.lastFrame = now;
        float was = this.gs;
        this.metrics();
        if (was != this.gs) {
            for (Panel p : this.panels) {
                p.clamp();
}
}
        this.pickerAnim = this.approach(this.pickerAnim, this.pickerOpen ? 1.0f : 0.0f, 13.0f);
        if (!this.pickerOpen && this.pickerAnim < 0.02f) {
            this.pickerAnim = 0.0f;
            this.picker = null;
}
        float in = AbyssClickGuiScreen.ease(AbyssClickGuiScreen.clamp01((float)(now - this.openedAt) / 240.0f));
        AbyssClickGuiScreen.rect(0.0f, 0.0f, this.field_146294_l, this.field_146295_m, AbyssClickGuiScreen.alpha(Integer.MIN_VALUE, in));
        int pmx = (int)((float)mx / this.gs);
        int pmy = (int)((float)my / this.gs);
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)this.gs, (float)this.gs, (float)1.0f);
        AbyssClickGuiScreen.font().drawStringWithShadow("Abyss", 6.0f, (float)this.sh - 12.0f, AbyssClickGuiScreen.alpha(AbyssClickGuiScreen.accent(), in * 0.7f));
        AbyssClickGuiScreen.font().drawStringWithShadow("  ESC close   type to search", 6.0f + AbyssClickGuiScreen.font().getWidth("Abyss"), (float)this.sh - 12.0f, AbyssClickGuiScreen.alpha(-10855837, in));
        if (!this.search.isEmpty()) {
            AbyssClickGuiScreen.font().drawStringWithShadow("Search: " + this.search, 6.0f, (float)this.sh - 23.0f, AbyssClickGuiScreen.alpha(AbyssClickGuiScreen.accent(), in));
}
        for (int i = 0; i < this.panels.size(); ++i) {
            this.panels.get(i).draw(pmx, pmy, AbyssClickGuiScreen.ease(AbyssClickGuiScreen.clamp01(((float)(now - this.openedAt) - (float)i * 45.0f) / 240.0f)));
}
        GlStateManager.func_179121_F();
}
    public void func_146274_d() {
        super.func_146274_d();
        int d = Mouse.getEventDWheel();
        if (d == 0) {
            return;
}
        int mx = (int)((float)(Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c) / this.gs);
        int my = (int)((float)(this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1) / this.gs);
        for (Panel p : this.panels) {
            if (!p.hit(mx, my)) continue;
            p.scroll = p.scroll + (d > 0 ? 28.0f : -28.0f);
            p.limitScroll();
            break;
}
}
    protected void func_73864_a(int mx, int my, int b) {
        try {
            this.mouseClickedImpl(mx, my, b);
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    private void mouseClickedImpl(int mx, int my, int b) {
        this.latched = true;
        int pmx = (int)((float)mx / this.gs);
        int pmy = (int)((float)my / this.gs);
        for (Panel p : this.panels) {
            if (!p.click(pmx, pmy, b)) continue;
            return;
}
        this.commitText();
}
    protected void func_146286_b(int mx, int my, int state) {
        this.latched = false;
        for (Panel p : this.panels) {
            p.release();
}
        if (this.picker != null) {
            this.picker.release();
}
}
    protected void func_73869_a(char ch, int key) {
        block2: {
            try {
                this.keyTypedImpl(ch, key);
}
            catch (Throwable ignored) {
                if (key != 1) break block2;
                this.field_146297_k.func_147108_a(null);
}
}
}
    private void keyTypedImpl(char ch, int key) {
        for (Panel p : this.panels) {
            if (!p.key(key)) continue;
            return;
}
        if (this.picker != null && this.pickerOpen && this.picker.keyTyped(ch, key)) {
            return;
}
        if (this.editing != null && this.editKey(ch, key)) {
            return;
}
        if (key == 1) {
            this.field_146297_k.func_147108_a(null);
            return;
}
        if (key == 14) {
            if (!this.search.isEmpty()) {
                this.search = this.search.substring(0, this.search.length() - 1);
}
        } else if (!Character.isISOControl(ch)) {
            this.search = this.search + Character.toLowerCase(ch);
}
}
    private boolean editKey(char ch, int key) {
        if (key == 1) {
            this.editing.O(this.editBefore);
            this.editing = null;
            return true;
}
        if (key == 28 || key == 156) {
            this.commitText();
            return true;
}
        if (GuiScreen.func_175279_e((int)key)) {
            String clip = GuiScreen.func_146277_j();
            if (clip != null) {
                this.editBuffer = this.editBuffer + clip.replace("\n", "").replace("\r", "");
}
        } else if (key == 14) {
            if (!this.editBuffer.isEmpty()) {
                this.editBuffer = this.editBuffer.substring(0, this.editBuffer.length() - 1);
}
        } else if (!Character.isISOControl(ch)) {
            this.editBuffer = this.editBuffer + ch;
}
        this.editing.O(this.editBuffer);
        return true;
}
    private void commitText() {
        if (this.editing == null) {
            return;
}
        this.editing.O(this.editBuffer);
        this.editing = null;
}
    private void focus(TextSetting s) {
        this.commitText();
        this.clearBinds();
        this.editing = s;
        this.editBefore = this.editBuffer = s.X() == null ? "" : s.X();
}
    private void clearBinds() {
        for (Panel p : this.panels) {
            for (RowEntry r2 : p.rows) {
                r2.bind = false;
}
}
}
    private void togglePicker(ColorSetting s) {
        boolean same;
        boolean bl = same = this.picker != null && this.picker.setting() == s && this.pickerOpen;
        if (this.picker != null) {
            this.picker.closed();
}
        if (same) {
            this.pickerOpen = false;
            return;
}
        this.commitText();
        this.clearBinds();
        this.picker = new AbyssColorPicker(s);
        this.pickerOpen = true;
        this.pickerAnim = 0.0f;
}
    private static int accent() {
        return Theme.S(0.0, 35338930340239L);
}
    private static FontRenderer font() {
        return FontManager.getSmall();
}
    private static int headerFg() {
        return AbyssClickGuiScreen.luma(AbyssClickGuiScreen.accent()) > 0.6f ? -15461606 : -1;
}
    private static float luma(int c) {
        return ((float)(c >> 16 & 0xFF) * 0.299f + (float)(c >> 8 & 0xFF) * 0.587f + (float)(c & 0xFF) * 0.114f) / 255.0f;
}
    private static float clamp01(float v2) {
        return v2 < 0.0f ? 0.0f : (v2 > 1.0f ? 1.0f : v2);
}
    private static float ease(float t2) {
        float i = 1.0f - AbyssClickGuiScreen.clamp01(t2);
        return 1.0f - i * i * i;
}
    private float approach(float from, float to, float speed) {
        return from + (to - from) * AbyssClickGuiScreen.clamp01(this.dt * speed);
}
    private static int alpha(int argb, float f) {
        return argb & 0xFFFFFF | (int)((float)(argb >>> 24) * AbyssClickGuiScreen.clamp01(f)) << 24;
}
    private static int lerp(int a, int b, float t2) {
        t2 = AbyssClickGuiScreen.clamp01(t2);
        return (int)((float)(a >>> 24) + (float)((b >>> 24) - (a >>> 24)) * t2) << 24 | (int)((float)(a >> 16 & 0xFF) + (float)((b >> 16 & 0xFF) - (a >> 16 & 0xFF)) * t2) << 16 | (int)((float)(a >> 8 & 0xFF) + (float)((b >> 8 & 0xFF) - (a >> 8 & 0xFF)) * t2) << 8 | (int)((float)(a & 0xFF) + (float)((b & 0xFF) - (a & 0xFF)) * t2);
}
    private void scissor(float left, float top, float right, float bottom) {
        float ppu = (float)this.field_146297_k.field_71443_c / (float)this.field_146294_l * this.gs;
        int px = Math.round(left * ppu);
        int py = Math.round((float)this.field_146297_k.field_71440_d - bottom * ppu);
        int pw = Math.max(0, Math.round((right - left) * ppu));
        int ph = Math.max(0, Math.round((bottom - top) * ppu));
        GL11.glEnable((int)3089);
        GL11.glScissor((int)px, (int)py, (int)pw, (int)ph);
}
    private static void icon(Category c, float x, float y, float size, int color) {
        GlStateManager.func_179098_w();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)((float)(color >>> 24) / 255.0f));
        Minecraft.func_71410_x().func_110434_K().func_110577_a(Category.n(c));
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        Gui.func_146110_a((int)((int)x), (int)((int)y), (float)0.0f, (float)0.0f, (int)((int)size), (int)((int)size), (float)size, (float)size);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179117_G();
}
    static void rect(float x1, float y1, float x2, float y2, int color) {
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        Gui.func_73734_a((int)((int)x1), (int)((int)y1), (int)((int)x2), (int)((int)y2), (int)color);
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179117_G();
}
    private static String tabLabel(Category c) {
        return c == Category.Visual_utility ? "Other" : c.name().replace('_', ' ');
}
    private static String pretty(String s) {
        return s == null ? "" : s.replace('-', ' ');
}
    private static String trim(float v2) {
        if (v2 == (float)((long)v2)) {
            return String.valueOf((long)v2);
}
        String s = String.format(Locale.ROOT, "%.2f", Float.valueOf(v2));
        while (s.endsWith("0")) {
            s = s.substring(0, s.length() - 1);
}
        return s.endsWith(".") ? s.substring(0, s.length() - 1) : s;
}
    private static String fit(String s, float max) {
        if (s == null) {
            return "";
}
        if (AbyssClickGuiScreen.font().getWidth(s) <= max) {
            return s;
}
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            if (AbyssClickGuiScreen.font().getWidth(b.toString() + s.charAt(i) + "..") > max) break;
            b.append(s.charAt(i));
}
        return b.length() == 0 ? "" : b.append("..").toString();
}
    private static String tail(String s, float max) {
        if (s == null) {
            return "";
}
        if (AbyssClickGuiScreen.font().getWidth(s) <= max) {
            return s;
}
        for (int i = 1; i < s.length(); ++i) {
            String t2 = ".." + s.substring(i);
            if (!(AbyssClickGuiScreen.font().getWidth(t2) <= max)) continue;
            return t2;
}
        return "..";
}
    private final class RowEntry {
        final Module m;
        boolean expanded;
        boolean bind;
        Setting sliding;
        private float anim;
        private float on;
        private float hover;
        private List<Setting> vis;
        private float[] slide;

        RowEntry(Module m2) {
            this.m = m2;
}
        List<Setting> settings() {
            if (this.vis == null) {
                this.vis = new ArrayList<Setting>();
                try {
                    for (Setting s : this.m.settings()) {
                        if (s instanceof HeaderSetting) continue;
                        this.vis.add(s);
}
}
                catch (Throwable throwable) {
                    // empty catch block
}
                this.slide = new float[this.vis.size()];
                for (int i = 0; i < this.vis.size(); ++i) {
                    this.slide[i] = this.fraction(this.vis.get(i));
}
}
            return this.vis;
}
        boolean ownsPicker() {
            return AbyssClickGuiScreen.this.picker != null && this.settings().contains(AbyssClickGuiScreen.this.picker.setting());
}
        float extra() {
            float h = 14.0f;
            for (Setting s : this.settings()) {
                h += s instanceof NumberSetting || s instanceof PercentageSetting ? 22.0f : 14.0f;
}
            return h + (this.ownsPicker() ? 64.0f * AbyssClickGuiScreen.this.pickerAnim : 0.0f);
}
        float height() {
            return 14.0f + this.anim * this.extra();
}
        void draw(float x, float y, int mx, int my, float cl, float ct, float cr, float cb) {
            boolean over = (float)mx >= x && (float)mx <= x + 130.0f && (float)my >= y && (float)my <= y + 14.0f;
            this.hover = AbyssClickGuiScreen.this.approach(this.hover, over ? 1.0f : 0.0f, 12.0f);
            this.on = AbyssClickGuiScreen.this.approach(this.on, this.m.o() ? 1.0f : 0.0f, 11.0f);
            this.anim = AbyssClickGuiScreen.this.approach(this.anim, this.expanded ? 1.0f : 0.0f, 13.0f);
            AbyssClickGuiScreen.rect(x, y, x + 130.0f, y + 14.0f, AbyssClickGuiScreen.lerp(AbyssClickGuiScreen.lerp(-267119847, -266462428, this.hover), -266593758, this.on));
            AbyssClickGuiScreen.rect(x, y + 14.0f - 0.5f, x + 130.0f, y + 14.0f, 0x20FFFFFF);
            float pad = 14.0f * (1.0f - this.on) / 2.0f;
            if (this.on > 0.01f) {
                AbyssClickGuiScreen.rect(x, y + pad, x + 2.5f, y + 14.0f - pad, AbyssClickGuiScreen.alpha(AbyssClickGuiScreen.accent(), 0.7f + 0.3f * this.on));
}
            AbyssClickGuiScreen.font().drawStringWithShadow(this.m.name(), x + 6.0f, y + 3.5f, AbyssClickGuiScreen.lerp(-6513498, -1, Math.max(this.on, this.hover * 0.6f)));
            if (!AbyssArrayListVisibility.isShown(this.m)) {
                AbyssClickGuiScreen.rect(x + 130.0f - 16.0f, y + 5.0f, x + 130.0f - 12.0f, y + 14.0f - 5.0f, -3454133);
}
            if (!this.settings().isEmpty()) {
                AbyssClickGuiScreen.font().drawString(this.expanded ? "-" : "+", x + 130.0f - 9.0f, y + 3.5f, -9803149);
}
            if (this.anim <= 0.01f) {
                return;
}
            float low = Math.min(cb, y + 14.0f + this.anim * this.extra());
            AbyssClickGuiScreen.this.scissor(cl, Math.max(ct, y + 14.0f), cr, low);
            float yy = y + 14.0f;
            AbyssClickGuiScreen.rect(x, yy, x + 130.0f, yy + 14.0f, -267580399);
            AbyssClickGuiScreen.font().drawStringWithShadow("Bind", x + 8.0f, yy + 3.5f, -8750461);
            String k = this.bind ? "..." : (this.m.h() == 0 ? "None" : KeyBindUtil.p(0L, '\u0000', this.m.h()));
            AbyssClickGuiScreen.font().drawStringWithShadow(k, x + 130.0f - 6.0f - AbyssClickGuiScreen.font().getWidth(k), yy + 3.5f, this.bind ? AbyssClickGuiScreen.accent() : -3684402);
            yy += 14.0f;
            List<Setting> list = this.settings();
            for (int i = 0; i < list.size(); ++i) {
                Setting s = list.get(i);
                boolean isSlider = s instanceof NumberSetting || s instanceof PercentageSetting;
                int rowH = isSlider ? 22 : 14;
                this.drawSetting(s, i, x, yy, mx, isSlider);
                yy += (float)rowH;
                if (AbyssClickGuiScreen.this.picker == null || AbyssClickGuiScreen.this.picker.setting() != s || !(AbyssClickGuiScreen.this.pickerAnim > 0.01f)) continue;
                float ph = 64.0f * AbyssClickGuiScreen.this.pickerAnim;
                AbyssClickGuiScreen.this.scissor(cl, Math.max(ct, yy), cr, Math.min(low, yy + ph));
                AbyssClickGuiScreen.this.picker.draw(x, yy, 130, mx, my);
                AbyssClickGuiScreen.this.scissor(cl, Math.max(ct, y + 14.0f), cr, low);
                yy += ph;
}
            AbyssClickGuiScreen.this.scissor(cl, ct, cr, cb);
}
        private void drawSetting(Setting s, int i, float x, float y, int mx, boolean isSlider) {
            boolean edit = AbyssClickGuiScreen.this.editing == s;
            boolean swatch = s instanceof ColorSetting;
            String value = this.value(s, edit);
            float vw = value.isEmpty() ? 0.0f : AbyssClickGuiScreen.font().getWidth(value);
            int rowH = isSlider ? 22 : 14;
            AbyssClickGuiScreen.rect(x, y, x + 130.0f, y + (float)rowH, i % 2 == 0 ? -267448813 : -267317226);
            if (isSlider) {
                AbyssClickGuiScreen.font().drawStringWithShadow(AbyssClickGuiScreen.fit(AbyssClickGuiScreen.pretty(s.B()), 112.0f - vw), x + 8.0f, y + 2.0f, -4934467);
            } else {
                AbyssClickGuiScreen.font().drawStringWithShadow(AbyssClickGuiScreen.fit(AbyssClickGuiScreen.pretty(s.B()), 112.0f - vw - (swatch ? 12.0f : 0.0f)), x + 8.0f, y + 3.0f, -4934467);
}
            if (swatch) {
                float sx = x + 130.0f - 6.0f - vw - 12.0f;
                AbyssClickGuiScreen.rect(sx, y + 3.0f, sx + 9.0f, y + 14.0f - 3.0f, -16777216);
                AbyssClickGuiScreen.rect(sx + 1.0f, y + 4.0f, sx + 8.0f, y + 14.0f - 4.0f, this.rgbOf((ColorSetting)s));
}
            if (!value.isEmpty() && !isSlider) {
                AbyssClickGuiScreen.font().drawStringWithShadow(value, x + 130.0f - 6.0f - vw, y + 3.0f, this.valueColor(s, edit));
}
            if (s instanceof NumberSetting) {
                if (this.sliding == s) {
                    this.setNumber((NumberSetting)s, mx, x);
}
                this.slide[i] = AbyssClickGuiScreen.this.approach(this.slide[i], this.fraction(s), 16.0f);
                String sv = AbyssClickGuiScreen.trim(((NumberSetting)s).L());
                AbyssClickGuiScreen.font().drawString(sv, x + 130.0f - 6.0f - AbyssClickGuiScreen.font().getWidth(sv), y + 2.0f, -3684402);
                this.bar(x, y + 12.0f, this.slide[i]);
            } else if (s instanceof PercentageSetting) {
                if (this.sliding == s) {
                    this.setPercent((PercentageSetting)s, mx, x);
}
                this.slide[i] = AbyssClickGuiScreen.this.approach(this.slide[i], this.fraction(s), 16.0f);
                String sv = ((PercentageSetting)s).k() + "%";
                AbyssClickGuiScreen.font().drawString(sv, x + 130.0f - 6.0f - AbyssClickGuiScreen.font().getWidth(sv), y + 2.0f, -3684402);
                this.bar(x, y + 12.0f, this.slide[i]);
}
}
        private void bar(float x, float y, float p) {
            float left = x + 8.0f;
            float right = x + 130.0f - 8.0f;
            float trackY = y + 3.0f;
            float gx = left + (right - left) * AbyssClickGuiScreen.clamp01(p);
            AbyssClickGuiScreen.rect(left, trackY, right, trackY + 2.0f, -14540760);
            AbyssClickGuiScreen.rect(left, trackY, gx, trackY + 2.0f, AbyssClickGuiScreen.alpha(AbyssClickGuiScreen.accent(), 0.85f));
            RenderingUtils.drawLoop(gx, trackY + 1.0f, 2.5, 24, 1.0f, -1513235, true);
}
        private float fraction(Setting s) {
            if (s instanceof NumberSetting) {
                NumberSetting v2 = (NumberSetting)s;
                float span = v2.F() - v2.i();
                return span <= 0.0f ? 0.0f : (v2.L() - v2.i()) / span;
}
            return s instanceof PercentageSetting ? (float)((PercentageSetting)s).k() / 100.0f : 0.0f;
}
        private int valueColor(Setting s, boolean edit) {
            if (edit) {
                return AbyssClickGuiScreen.accent();
}
            if (s instanceof BooleanSetting) {
                return ((BooleanSetting)s).c() ? AbyssClickGuiScreen.accent() : -9079426;
}
            return -3684402;
}
        private int rgbOf(ColorSetting s) {
            try {
                return s.k(0L);
}
            catch (RuntimeException e) {
                return -1;
}
}
        private String value(Setting s, boolean edit) {
            if (s instanceof BooleanSetting) {
                return ((BooleanSetting)s).c() ? "ON" : "OFF";
}
            if (s instanceof ModeSetting) {
                return String.valueOf(((ModeSetting)s).Y());
}
            if (s instanceof NumberSetting) {
                return AbyssClickGuiScreen.trim(((NumberSetting)s).L());
}
            if (s instanceof PercentageSetting) {
                return ((PercentageSetting)s).k() + "%";
}
            if (s instanceof ColorSetting) {
                String v2 = ((ColorSetting)s).Q();
                return v2 == null ? "#______" : (v2.startsWith("#") ? v2 : "#" + v2);
}
            if (s instanceof TextSetting) {
                String t2;
                String string = t2 = edit ? AbyssClickGuiScreen.this.editBuffer : ((TextSetting)s).X();
                if (t2 == null) {
                    t2 = "";
}
                if (edit) {
                    boolean caret = (System.currentTimeMillis() / 500L & 1L) == 0L;
                    return AbyssClickGuiScreen.tail(caret ? t2 + "_" : t2, 71.5f);
}
                return t2.isEmpty() ? "..." : AbyssClickGuiScreen.tail(t2, 71.5f);
}
            return "";
}
        boolean click(float x, float y, int mx, int my, int b) {
            if ((float)mx < x || (float)mx > x + 130.0f) {
                return false;
}
            if ((float)my >= y && (float)my <= y + 14.0f) {
                try {
                    if (b == 0) {
                        this.m.u((short)0, 0L);
                    } else if (b == 1) {
                        boolean bl = this.expanded = !this.expanded;
                        if (!this.expanded) {
                            this.closeEditors();
}
                    } else if (b == 2) {
                        AbyssArrayListVisibility.toggle(this.m);
}
}
                catch (Throwable throwable) {
                    // empty catch block
}
                return true;
}
            if (!this.expanded) {
                return false;
}
            float yy = y + 14.0f;
            if ((float)my >= yy && (float)my <= yy + 14.0f) {
                if (b == 0) {
                    this.bind = !this.bind;
                } else if (b == 1) {
                    this.m.z(0L, 0);
}
                return true;
}
            yy += 14.0f;
            List<Setting> list = this.settings();
            for (int i = 0; i < list.size(); ++i) {
                int rowH;
                Setting s = list.get(i);
                boolean isSlider = s instanceof NumberSetting || s instanceof PercentageSetting;
                int n2 = rowH = isSlider ? 22 : 14;
                if ((float)my >= yy && (float)my <= yy + (float)rowH) {
                    this.activate(s, b, mx, x);
                    return true;
}
                yy += (float)rowH;
                if (AbyssClickGuiScreen.this.picker == null || AbyssClickGuiScreen.this.picker.setting() != s || !(AbyssClickGuiScreen.this.pickerAnim > 0.01f)) continue;
                if (AbyssClickGuiScreen.this.picker.click(x, yy, 130, mx, my, b)) {
                    return true;
}
                yy += 64.0f * AbyssClickGuiScreen.this.pickerAnim;
}
            return false;
}
        private void closeEditors() {
            this.bind = false;
            if (AbyssClickGuiScreen.this.editing != null && this.settings().contains(AbyssClickGuiScreen.this.editing)) {
                AbyssClickGuiScreen.this.commitText();
}
            if (this.ownsPicker()) {
                AbyssClickGuiScreen.this.picker.closed();
                AbyssClickGuiScreen.this.pickerOpen = false;
}
}
        private void activate(Setting s, int b, int mx, float x) {
            try {
                if (s instanceof BooleanSetting) {
                    ((BooleanSetting)s).W(0L);
                } else if (s instanceof ModeSetting) {
                    if (b == 1) {
                        ((ModeSetting)s).X();
                    } else {
                        ((ModeSetting)s).w(0L);
}
                } else if (s instanceof NumberSetting) {
                    this.sliding = s;
                    this.setNumber((NumberSetting)s, mx, x);
                } else if (s instanceof PercentageSetting) {
                    this.sliding = s;
                    this.setPercent((PercentageSetting)s, mx, x);
                } else if (s instanceof ColorSetting) {
                    AbyssClickGuiScreen.this.togglePicker((ColorSetting)s);
                } else if (s instanceof TextSetting) {
                    AbyssClickGuiScreen.this.focus((TextSetting)s);
}
}
            catch (Throwable throwable) {
                // empty catch block
}
}
        private void setNumber(NumberSetting s, int mx, float x) {
            s.o((byte)0, 0L, s.i() + (s.F() - s.i()) * this.track(mx, x));
}
        private void setPercent(PercentageSetting s, int mx, float x) {
            s.b(0, 0L, Math.round(this.track(mx, x) * 100.0f));
}
        private float track(int mx, float x) {
            return AbyssClickGuiScreen.clamp01(((float)mx - (x + 8.0f)) / 114.0f);
}
}
    private final class Panel {
        final Category category;
        final List<RowEntry> rows = new ArrayList<RowEntry>();
        float x;
        float y;
        float scroll;
        float grabX;
        float grabY;
        boolean open = true;
        boolean drag;
        boolean moved;

        Panel(Category c, float px, float py) {
            this.category = c;
            this.x = px;
            this.y = py;
            for (Module m2 : ModuleManager.modules()) {
                try {
                    boolean mine;
                    if (m2 == null || m2.f() == null || !(mine = m2.f() == c || c == Category.Visual && m2.f() == Category.Configuration) || "CommandLine".equals(m2.name())) continue;
                    this.rows.add(new RowEntry(m2));
}
                catch (Throwable throwable) {}
}
}
        boolean shown(RowEntry r2) {
            return AbyssClickGuiScreen.this.search.isEmpty() || r2.m.name().toLowerCase().contains(AbyssClickGuiScreen.this.search);
}
        float content() {
            float h = 0.0f;
            for (RowEntry r2 : this.rows) {
                if (!this.shown(r2)) continue;
                h += r2.height();
}
            return h;
}
        float bottom() {
            return Math.max(this.y + 16.0f, (float)(AbyssClickGuiScreen.this.sh - 28));
}
        void limitScroll() {
            this.scroll = Math.max(Math.min(0.0f, this.bottom() - this.y - 16.0f - this.content()), Math.min(0.0f, this.scroll));
}
        void clamp() {
            this.x = Math.max(5.0f, Math.min(this.x, (float)(AbyssClickGuiScreen.this.sw - 130) - 5.0f));
            this.y = Math.max(5.0f, Math.min(this.y, (float)(AbyssClickGuiScreen.this.sh - 16) - 28.0f));
            this.limitScroll();
}
        boolean hit(int mx, int my) {
            return (float)mx >= this.x && (float)mx <= this.x + 130.0f && (float)my >= this.y && (float)my <= this.bottom();
}
        void draw(int mx, int my, float in) {
            if (this.drag) {
                this.x = (float)mx - this.grabX;
                this.y = (float)my - this.grabY;
                this.moved = true;
                this.clamp();
}
            int fg = AbyssClickGuiScreen.alpha(-855638017, in);
            int acc = AbyssClickGuiScreen.accent();
            AbyssClickGuiScreen.rect(this.x, this.y, this.x + 130.0f * (0.3f + 0.7f * in), this.y + 16.0f, AbyssClickGuiScreen.alpha(acc, 0.45f + 0.55f * in));
            AbyssClickGuiScreen.rect(this.x, this.y + 16.0f - 1.0f, this.x + 130.0f * (0.3f + 0.7f * in), this.y + 16.0f, AbyssClickGuiScreen.alpha(0x40000000, in));
            AbyssClickGuiScreen.font().drawStringWithShadow(AbyssClickGuiScreen.tabLabel(this.category), this.x + 6.0f, this.y + 4.0f, fg);
            if (!this.open || in <= 0.01f) {
                return;
}
            this.limitScroll();
            float top = this.y + 16.0f;
            float low = top + (this.bottom() - top) * in;
            AbyssClickGuiScreen.this.scissor(this.x, top, this.x + 130.0f, low);
            AbyssClickGuiScreen.rect(this.x, top, this.x + 130.0f, Math.min(this.bottom(), top + this.content()), -267317227);
            float yy = top + this.scroll;
            for (RowEntry r2 : this.rows) {
                if (!this.shown(r2)) continue;
                r2.draw(this.x, yy, mx, my, this.x, top, this.x + 130.0f, low);
                yy += r2.height();
}
            GL11.glDisable((int)3089);
}
        boolean click(int mx, int my, int b) {
            if ((float)mx >= this.x && (float)mx <= this.x + 130.0f && (float)my >= this.y && (float)my <= this.y + 16.0f) {
                if (b == 0) {
                    this.drag = true;
                    this.grabX = (float)mx - this.x;
                    this.grabY = (float)my - this.y;
                } else if (b == 1) {
                    this.open = !this.open;
}
                return true;
}
            if (!this.open || (float)mx < this.x || (float)mx > this.x + 130.0f || (float)my < this.y + 16.0f || (float)my > this.bottom()) {
                return false;
}
            float yy = this.y + 16.0f + this.scroll;
            for (RowEntry r2 : this.rows) {
                if (!this.shown(r2)) continue;
                if (r2.click(this.x, yy, mx, my, b)) {
                    return true;
}
                yy += r2.height();
}
            return true;
}
        boolean key(int key) {
            for (RowEntry r2 : this.rows) {
                if (!r2.bind) continue;
                r2.m.z(0L, key == 1 ? 0 : key);
                r2.bind = false;
                return true;
}
            return false;
}
        void release() {
            this.drag = false;
            for (RowEntry r2 : this.rows) {
                r2.sliding = null;
}
}
}
}