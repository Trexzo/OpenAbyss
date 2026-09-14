/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 */
package Abyss.ui.abyss;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.impl.configuration.Theme;
import Abyss.module.impl.visual.ArrayList;
import Abyss.ui.abyss.AbyssArrayListVisibility;
import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.FontRenderer;
import Abyss.util.render.abyss.RenderingUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public final class AbyssArrayListRenderer {
    private static final float DARK_FACTOR = 0.49f;
    private static final int ROW_HEIGHT = 12;
    private static final Map<Module, Entry> ENTRIES = new HashMap<Module, Entry>();
    private static final java.util.ArrayList<Module> SORT_BUF = new java.util.ArrayList();
    private static final Comparator<Module> CMP_ALPHA = new Comparator<Module>(){

        @Override
        public int compare(Module a, Module b) {
            return AbyssArrayListRenderer.label(a).compareToIgnoreCase(AbyssArrayListRenderer.label(b));
}
    };
    private static FontRenderer cachedFont;
    private static final Comparator<Module> CMP_WIDTH;

    private AbyssArrayListRenderer() {
}
    public static void tick() {
        if (!FontManager.isReady()) {
            return;
}
        FontRenderer font = FontManager.get();
        for (Module module : ModuleManager.modules()) {
            Entry entry = ENTRIES.get(module);
            if (entry == null) {
                entry = new Entry();
                ENTRIES.put(module, entry);
}
            entry.label = AbyssArrayListRenderer.label(module);
            entry.target = AbyssArrayListRenderer.shouldShow(module) ? 1.0f : 0.0f;
}
}
    private static boolean shouldShow(Module module) {
        if ("CommandLine".equals(module.name())) {
            return false;
}
        if (!module.o()) {
            return false;
}
        if (module.f() == Category.Macro) {
            return false;
}
        if (!AbyssArrayListVisibility.isShown(module)) {
            return false;
}
        return !ArrayList.onlyShowSuffixModules.c() || AbyssArrayListRenderer.hasSuffix(module);
}
    private static boolean hasSuffix(Module module) {
        if (!module.r()) {
            return false;
}
        try {
            String suffix = module.g(0L);
            return suffix != null && !suffix.isEmpty();
}
        catch (Throwable ignored) {
            return false;
}
}
    public static void render(ScaledResolution resolution, float partialTicks) {
        if (!FontManager.isReady()) {
            return;
}
        AbyssArrayListRenderer.tick();
        FontRenderer font = FontManager.get();
        SORT_BUF.clear();
        SORT_BUF.addAll(ModuleManager.modules());
        boolean alphabetical = ArrayList.abyssSort.R("ALPHABETICAL");
        cachedFont = font;
        Collections.sort(SORT_BUF, alphabetical ? CMP_ALPHA : CMP_WIDTH);
        int screenX = resolution.func_78326_a();
        int screenY = resolution.func_78328_b();
        boolean top = ArrayList.mode.R("TOP");
        boolean background = ArrayList.abyssBackground.c();
        boolean line = ArrayList.abyssLine.c();
        boolean outline = ArrayList.abyssOutline.c();
        long now = System.currentTimeMillis();
        long period = Math.max(100L, (long)(ArrayList.abyssFadeSpeed.L() * 1000.0f));
        int y = top ? 2 : screenY - 10;
        int visibleIndex = 0;
        float previousWidth = -1.0f;
        Entry previous = null;
        for (Module module : SORT_BUF) {
            Entry entry = ENTRIES.get(module);
            if (entry == null) continue;
            float lerpFactor = entry.target > entry.progress ? 0.04f : 0.14f;
            entry.progress += (entry.target - entry.progress) * Math.min(1.0f, lerpFactor * Math.max(1.0f, partialTicks));
            if (Math.abs(entry.target - entry.progress) < 0.003f) {
                entry.progress = entry.target;
}
            if (entry.progress <= 0.001f) continue;
            String name = entry.label;
            float width = font.getWidth(name);
            float eased = 1.0f - (float)Math.pow(1.0f - entry.progress, 3.0);
            float x = (float)screenX - (width + (line ? 2.0f : 1.0f)) * eased;
            int color = AbyssArrayListRenderer.color(now, period, visibleIndex);
            float topY = y - 2;
            float bottomY = y + 10;
            if (background) {
                Gui.func_73734_a((int)((int)(x - 1.0f)), (int)((int)topY), (int)screenX, (int)((int)bottomY), (int)2014121229);
}
            font.drawStringWithShadow(name, x, (float)y - (ArrayList.abyssCustomFont.c() ? 1.0f : 0.0f), color);
            if (outline) {
                float seam;
                Gui.func_73734_a((int)((int)(x - 2.0f)), (int)((int)topY), (int)((int)(x - 1.0f)), (int)((int)bottomY), (int)color);
                float f = seam = top ? topY - 1.0f : bottomY;
                if (previous != null && width > previousWidth) {
                    Gui.func_73734_a((int)((int)(x - 2.0f)), (int)((int)seam), (int)((int)((float)screenX - previousWidth - 3.0f)), (int)((int)(seam + 1.0f)), (int)color);
}
}
            if (line) {
                Gui.func_73734_a((int)(screenX - 1), (int)((int)topY), (int)screenX, (int)((int)bottomY), (int)color);
}
            previous = entry;
            previousWidth = width;
            y += top ? 12 : -12;
            ++visibleIndex;
}
        if (outline && previous != null) {
            float edge = top ? (float)(y - 12) + 10.0f : (float)(y + 12) - 3.0f;
            Gui.func_73734_a((int)((int)((float)screenX - previousWidth - 4.0f)), (int)((int)edge), (int)screenX, (int)((int)(edge + 1.0f)), (int)AbyssArrayListRenderer.color(now, period, Math.max(0, visibleIndex - 1)));
}
}
    private static int color(long now, long period, int index) {
        int primary = Theme.S((float)index * Theme.offset.L(), 35338930340239L);
        float phase = (float)((now + (long)index * 100L) % period) / ((float)period / 2.0f);
        if (ArrayList.abyssColorMode.R("RAINBOW")) {
            return RenderingUtils.getRainbow(now, 2000, index);
}
        if (ArrayList.abyssColorMode.R("BLEND")) {
            return RenderingUtils.fadeBetween(primary, Theme.S(((double)index + 1.0) * (double)Theme.offset.L(), 35338930340239L), phase);
}
        if (ArrayList.abyssColorMode.R("STATIC")) {
            return primary;
}
        return RenderingUtils.fadeBetween(primary, RenderingUtils.darker(primary, 0.49f), phase);
}
    private static String label(Module module) {
        String name = module.name() == null ? "" : module.name();
        name = AbyssArrayListRenderer.styleCase(name, ArrayList.moduleNameLowercase.c());
        if (ArrayList.showSuffix.c() && module.r()) {
            try {
                String suffix = module.g(0L);
                if (suffix != null && !suffix.isEmpty()) {
                    suffix = AbyssArrayListRenderer.styleSuffix(suffix, ArrayList.suffixNameLowercase.c());
                    name = ArrayList.splitSuffixAndName.c() ? name + " \u00a77" + suffix : name + " " + suffix;
}
}
            catch (Throwable throwable) {
                // empty catch block
}
}
        return name;
}
    private static String styleCase(String s, boolean lower) {
        if (s == null || s.isEmpty()) {
            return s;
}
        if (ArrayList.capitalize != null && ArrayList.capitalize.c()) {
            return AbyssArrayListRenderer.titleCase(s);
}
        if (lower) {
            return s.toLowerCase();
}
        return s;
}
    private static String styleSuffix(String s, boolean lower) {
        if (s == null || s.isEmpty()) {
            return s;
}
        if (ArrayList.capitalize != null && ArrayList.capitalize.c()) {
            return AbyssArrayListRenderer.titleCase(s.toLowerCase());
}
        if (lower) {
            return s.toLowerCase();
}
        return s;
}
    private static String titleCase(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        boolean wordStart = true;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (Character.isWhitespace(c)) {
                wordStart = true;
                sb.append(c);
                continue;
}
            if (wordStart) {
                sb.append(Character.toUpperCase(c));
                wordStart = false;
                continue;
}
            sb.append(c);
}
        return sb.toString();
}
    static {
        CMP_WIDTH = new Comparator<Module>(){

            @Override
            public int compare(Module a, Module b) {
                return Float.compare(cachedFont.getWidth(AbyssArrayListRenderer.label(b)), cachedFont.getWidth(AbyssArrayListRenderer.label(a)));
}
        };
}
    private static final class Entry {
        String label = "";
        float progress;
        float target;

        private Entry() {
}
}
}