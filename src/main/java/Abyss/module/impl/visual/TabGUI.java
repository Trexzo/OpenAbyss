/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.GlStateManager
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.TabGUIBinder;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.impl.configuration.Theme;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.FontRenderer;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class TabGUI
extends Module
implements EventSubscriber {
    public static HashMap<Category, Integer> E;
    public static ModeSetting color;
    private static long c;
    private boolean o;
    public static ColorSetting customColor;
    private Module s;
    private Category M;
    private int a;
    private int level;
    private int selectedModule;
    private int selectedProperty;
    private int selectedValue;
    private final IdentityHashMap<Object, Float> selectionOffsets = new IdentityHashMap();
    private final float t;
    public static BooleanSetting disableTabKey;
    private static boolean abyssTabFontReady;

    public TabGUI(long var1) {
        super(c ^ var1 ^ 0x7D855F23BE02L);
        this.declare("TabGUI", Category.Visual, "Use tab and arrow keys to toggle modules", new Setting[0]);
        var1 = c ^ var1;
        this.t = 0.5f;
        this.M = Category.Combat;
        this.s = null;
        this.a = 0;
        this.o = false;
}
    private int j() {
        return E.get((Object)this.M);
}
    private Module r$r2() {
        ArrayList<Module> var1 = new ArrayList<Module>();
        List<Module> var2 = ModuleManager.S;
        int var4 = var2.size();
        for (int var3 = 0; var3 < var4; ++var3) {
            Module var5 = var2.get(var3);
            if (!var5.f().equals((Object)this.M)) continue;
            var1.add(var5);
}
        return (Module)var1.get(this.j());
}
    @Override
    public final void x(long var1, EventBus var3) {
        TabGUIBinder.z(var3, this);
}
    public void onRender2D(long var1, Render2DEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!FontManager.isReady()) {
            return;
}
        FontRenderer font = FontManager.getSmall();
        List<Category> categories = Category.j();
        this.clampSelections(categories);
        int accent = color.Y().equals("THEME") ? Theme.S(0.0, 35338930340239L) : (color.Y().equals("THEME_CUSTOM") ? Theme.X(65301174328177L, 0.0) : customColor.k(96531491288662L));
        ArrayList<String> categoryLabels = new ArrayList<String>();
        for (Category category : categories) {
            categoryLabels.add(TabGUI.displayName(category.name()));
}
        int originX = 2;
        int originY = 15;
        this.drawPanel(font, originX, originY, 65, categoryLabels, this.a, null, categories.toArray(), accent);
        if (this.level >= 1) {
            List<Module> modules = this.currentModules(categories);
            ArrayList<String> labels = new ArrayList<String>();
            for (Module module : modules) {
                labels.add(module.b());
}
            int moduleX = originX + 66;
            int moduleWidth = this.panelWidth(font, labels, 22);
            this.drawPanel(font, moduleX, originY, moduleWidth, labels, this.selectedModule, modules, modules.toArray(), accent);
            if (this.level >= 2) {
                List<Setting> settings = this.currentSettings(categories);
                ArrayList<String> settingLabels = new ArrayList<String>();
                for (Setting setting : settings) {
                    settingLabels.add(setting.B());
}
                int settingX = moduleX + moduleWidth + 1;
                int settingWidth = this.panelWidth(font, settingLabels, 19);
                this.drawPanel(font, settingX, originY, settingWidth, settingLabels, this.selectedProperty, null, settings.toArray(), accent);
                if (this.level == 3 && !settings.isEmpty()) {
                    Setting setting = settings.get(this.selectedProperty);
                    List<String> values = this.settingValues(setting);
                    int valueX = settingX + settingWidth + 1;
                    this.drawPanel(font, valueX, originY, this.panelWidth(font, values, setting instanceof NumberSetting ? 10 : 20), values, this.selectedValue, null, values.toArray(), accent);
}
}
}
}
    private void drawPanel(FontRenderer font, int x, int y, int width, List<String> labels, int selected, List<Module> modules, Object[] identities, int accent) {
        for (int i = 0; i < labels.size(); ++i) {
            int rowY = y + i * 12;
            boolean active = i == selected;
            Object identity = identities[i];
            float current = this.selectionOffsets.containsKey(identity) ? this.selectionOffsets.get(identity).floatValue() : 0.0f;
            float step = 39.0f / (float)Math.max(1, Minecraft.getDebugFPS());
            current = active ? Math.min(3.0f, current + step) : Math.max(0.0f, current - step);
            this.selectionOffsets.put(identity, Float.valueOf(current));
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
            Gui.drawRect((int)x, (int)rowY, (int)(x + width), (int)(rowY + 12), (int)-1441524716);
            if (active) {
                Gui.drawRect((int)x, (int)rowY, (int)(x + width), (int)(rowY + 12), (int)accent);
}
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.resetColor();
            int textColor = -1;
            if (modules != null && !modules.get(i).o()) {
                textColor = -6052957;
}
            if (identity instanceof BooleanSetting && !((BooleanSetting)identity).c()) {
                textColor = -6052957;
}
            font.drawStringWithShadow(labels.get(i), (float)(x + 3) + current, rowY + 2, textColor);
}
}
    private int panelWidth(FontRenderer font, List<String> labels, int extra) {
        int width = extra;
        for (String label : labels) {
            width = Math.max(width, (int)font.getWidth(label) + extra);
}
        return width;
}
    private List<Module> currentModules(List<Category> categories) {
        ArrayList<Module> result = new ArrayList<Module>();
        Category category = categories.get(this.a);
        for (Module module : ModuleManager.S) {
            if (module.f() != category) continue;
            result.add(module);
}
        return result;
}
    private List<Setting> currentSettings(List<Category> categories) {
        List<Module> modules = this.currentModules(categories);
        if (modules.isEmpty()) {
            return new ArrayList<Setting>();
}
        return new ArrayList<Setting>(modules.get(this.selectedModule).settings());
}
    private List<String> settingValues(Setting setting) {
        ArrayList<String> values = new ArrayList<String>();
        if (setting instanceof BooleanSetting) {
            values.add(((BooleanSetting)setting).c() ? "ON" : "OFF");
        } else if (setting instanceof ModeSetting) {
            values.addAll(((ModeSetting)setting).S());
        } else if (setting instanceof NumberSetting) {
            values.add(String.valueOf(((NumberSetting)setting).L()));
        } else if (setting instanceof ColorSetting) {
            values.add("#" + ((ColorSetting)setting).Q());
}
        return values;
}
    private void clampSelections(List<Category> categories) {
        this.a = this.clamp(this.a, categories.size());
        this.M = categories.get(this.a);
        List<Module> modules = this.currentModules(categories);
        this.selectedModule = this.clamp(this.selectedModule, modules.size());
        if (modules.isEmpty() && this.level > 0) {
            this.level = 0;
}
        List<Setting> settings = this.currentSettings(categories);
        this.selectedProperty = this.clamp(this.selectedProperty, settings.size());
        if (settings.isEmpty() && this.level > 1) {
            this.level = 1;
}
        if (this.level == 3 && (settings.isEmpty() || this.settingValues(settings.get(this.selectedProperty)).isEmpty())) {
            this.level = 2;
}
}
    private int clamp(int value, int size) {
        return size <= 0 ? 0 : Math.max(0, Math.min(value, size - 1));
}
    private int wrap(int value, int size) {
        return size <= 0 ? 0 : (value % size + size) % size;
}
    private static String displayName(String value) {
        if (value == null) {
            return "";
}
        String clean = value.replace('_', ' ').trim();
        StringBuilder out = new StringBuilder(clean.length());
        boolean upper = true;
        for (int i = 0; i < clean.length(); ++i) {
            char ch = clean.charAt(i);
            if (Character.isWhitespace(ch)) {
                if (out.length() > 0 && out.charAt(out.length() - 1) != ' ') {
                    out.append(' ');
}
                upper = true;
                continue;
}
            out.append(upper ? Character.toUpperCase(ch) : Character.toLowerCase(ch));
            upper = false;
}
        return out.toString();
}
    private void P(int var1) {
        E.put(this.M, var1);
}
    private int L() {
        long var1 = 0L;
        List<Module> var3 = ModuleManager.S;
        int var5 = var3.size();
        for (int var4 = 0; var4 < var5; ++var4) {
            Module var6 = var3.get(var4);
            if (!var6.f().equals((Object)this.M)) continue;
            ++var1;
}
        return (int)var1;
}
    public void onSetKeyBindState(SetKeyBindStateEvent event, long var2) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (TabGUI.f.currentScreen != null) {
            return;
}
        int key = event.R;
        if (key != 28 && key != 200 && key != 203 && key != 205 && key != 208) {
            return;
}
        List<Category> categories = Category.j();
        this.clampSelections(categories);
        if (key == 203) {
            if (this.level == 3) {
                this.level = 2;
                this.selectedValue = 0;
            } else if (this.level == 2) {
                this.level = 1;
                this.selectedValue = 0;
                this.selectedProperty = 0;
            } else if (this.level == 1) {
                this.level = 0;
                this.selectedValue = 0;
                this.selectedProperty = 0;
                this.selectedModule = 0;
}
            return;
}
        if (key == 200 || key == 208) {
            int direction;
            int n2 = direction = key == 200 ? -1 : 1;
            if (this.level == 0) {
                this.a = this.wrap(this.a + direction, categories.size());
                this.selectedValue = 0;
                this.selectedProperty = 0;
                this.selectedModule = 0;
            } else if (this.level == 1) {
                this.selectedModule = this.wrap(this.selectedModule + direction, this.currentModules(categories).size());
                this.selectedValue = 0;
                this.selectedProperty = 0;
            } else if (this.level == 2) {
                this.selectedProperty = this.wrap(this.selectedProperty + direction, this.currentSettings(categories).size());
                this.selectedValue = 0;
            } else {
                this.editCurrentSetting(categories, direction);
}
            return;
}
        if (this.level == 0) {
            if (!this.currentModules(categories).isEmpty()) {
                this.level = 1;
}
        } else if (this.level == 1) {
            List<Module> modules = this.currentModules(categories);
            if (modules.isEmpty()) {
                return;
}
            Module module = modules.get(this.selectedModule);
            if (key == 28) {
                module.u((short)0, 139350548161835L);
            } else if (module.settings().isEmpty()) {
                module.u((short)0, 139350548161835L);
            } else {
                this.level = 2;
}
        } else if (this.level == 2) {
            List<Setting> settings = this.currentSettings(categories);
            if (!settings.isEmpty() && !this.settingValues(settings.get(this.selectedProperty)).isEmpty()) {
                this.selectedValue = 0;
                if (settings.get(this.selectedProperty) instanceof ModeSetting) {
                    this.selectedValue = ((ModeSetting)settings.get(this.selectedProperty)).G();
}
                this.level = 3;
}
        } else {
            this.activateCurrentSetting(categories);
}
}
    private void editCurrentSetting(List<Category> categories, int direction) throws Throwable {
        List<Setting> settings = this.currentSettings(categories);
        if (settings.isEmpty()) {
            return;
}
        Setting setting = settings.get(this.selectedProperty);
        if (setting instanceof NumberSetting) {
            NumberSetting number = (NumberSetting)setting;
            number.o((byte)0, 0L, number.L() + number.U() * (float)direction);
        } else if (setting instanceof ModeSetting) {
            ModeSetting mode = (ModeSetting)setting;
            if (direction < 0) {
                mode.X();
            } else {
                mode.w(53199746843302L);
}
            this.selectedValue = mode.G();
        } else if (setting instanceof BooleanSetting) {
            ((BooleanSetting)setting).W(112370683098682L);
}
}
    private void activateCurrentSetting(List<Category> categories) throws Throwable {
        List<Setting> settings = this.currentSettings(categories);
        if (settings.isEmpty()) {
            return;
}
        Setting setting = settings.get(this.selectedProperty);
        if (setting instanceof BooleanSetting) {
            ((BooleanSetting)setting).W(112370683098682L);
        } else if (setting instanceof ModeSetting) {
            ((ModeSetting)setting).M(this.selectedValue);
}
}
    static {
        abyssTabFontReady = false;
        c = 49436393407904L;
        E = new HashMap();
        customColor = new ColorSetting("Custom-color", "FFFFFF");
        disableTabKey = new BooleanSetting("Disable-tab-key", true);
        color = new ModeSetting("Color", "THEME", "THEME_CUSTOM", "CUSTOM");
}
}