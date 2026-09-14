/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.restore;

import Abyss.command.AbyssCommands;
import Abyss.internal.restore.AbyssBootstrap;
import Abyss.internal.restore.AbyssConfig;
import Abyss.internal.restore.AbyssGuiJson;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.ui.swing.ConfigManagerWindow;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class AbyssGuiData {
    public static final String DESCRIPTION_KEY = "description";
    public static final String STATUS_KEY = "status";
    public static final String KEYBIND_KEY = "keyBind";
    public static final String VISIBLE_KEY = "visible";
    public static final String SUFFIX_KEY = "suffix-visible";
    public static final String DEFAULT_DESCRIPTION = "Description of your config here";
    private static final String BIND_NONE = "NONE";
    private static final String JSON_SUFFIX = ".json";

    private AbyssGuiData() {
}
    public static List<Module> modules() {
        ArrayList<Module> out = new ArrayList<Module>();
        try {
            if (ModuleManager.S == null) {
                return out;
}
            for (Module m2 : ModuleManager.S) {
                String n2;
                if (m2 == null || (n2 = AbyssGuiData.name(m2)) == null || n2.length() == 0 || n2.startsWith("?")) continue;
                out.add(m2);
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return out;
}
    public static String name(Module m2) {
        try {
            return m2.b();
}
        catch (Throwable t2) {
            return null;
}
}
    public static String category(Module m2) {
        try {
            Category c = m2.f();
            if (c != null) {
                return c.c();
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return "Misc";
}
    public static String[] categories() {
        try {
            Category[] v2 = Category.values();
            String[] out = new String[v2.length];
            for (int i = 0; i < v2.length; ++i) {
                out[i] = v2[i].c();
}
            return out;
}
        catch (Throwable t2) {
            return new String[0];
}
}
    public static String description(Module m2) {
        String s = (String)AbyssGuiData.readField(m2, Module.class, "W");
        return s == null ? "" : s;
}
    public static boolean enabled(Module m2) {
        try {
            return m2.o();
}
        catch (Throwable t2) {
            return false;
}
}
    public static boolean setEnabled(Module m2, boolean on) {
        try {
            m2.I(20724619369162L, on);
}
        catch (Throwable throwable) {
            // empty catch block
}
        return AbyssGuiData.enabled(m2);
}
    public static int keyBind(Module m2) {
        try {
            return m2.h();
}
        catch (Throwable t2) {
            return 0;
}
}
    public static boolean setKeyBind(Module m2, int code) {
        return AbyssGuiData.writeInt(m2, Module.class, "j", code);
}
    public static boolean visible(Module m2) {
        try {
            return m2.D();
}
        catch (Throwable t2) {
            return false;
}
}
    public static boolean suffixVisible(Module m2) {
        try {
            return m2.r();
}
        catch (Throwable t2) {
            return false;
}
}
    public static List<Setting> settings(Module m2) {
        try {
            List<Setting> l = m2.w();
            if (l != null) {
                return l;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return new ArrayList<Setting>();
}
    public static String settingName(Setting s) {
        try {
            String n2 = s.B();
            return n2 == null ? "" : n2;
}
        catch (Throwable t2) {
            return "";
}
}
    public static String configKey(Map<String, Object> block, Setting s) {
        String label = AbyssGuiData.settingName(s);
        if (block != null && block.containsKey(label)) {
            return label;
}
        String k = AbyssConfig.settingKey(label);
        return k == null ? "" : k;
}
    public static Object settingValue(Setting s) {
        try {
            if (s instanceof BooleanSetting) {
                return ((BooleanSetting)s).c();
}
            if (s instanceof ModeSetting) {
                return ((ModeSetting)s).Y();
}
            if (s instanceof NumberSetting) {
                return Float.valueOf(((NumberSetting)s).L());
}
            if (s instanceof PercentageSetting) {
                return ((PercentageSetting)s).k();
}
            if (s instanceof ColorSetting) {
                return ((ColorSetting)s).Q();
}
            if (s instanceof TextSetting) {
                return ((TextSetting)s).X();
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return null;
}
    public static boolean setBoolean(BooleanSetting s, boolean v2) {
        try {
            s.v(v2, 0L);
            return s.c() == v2;
}
        catch (Throwable t2) {
            return AbyssGuiData.writeBoolField(s, BooleanSetting.class, "h", v2);
}
}
    public static boolean setMode(ModeSetting s, String option) {
        try {
            s.i(option);
            if (option.equals(s.Y())) {
                return true;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        List<String> opts = AbyssGuiData.modeOptions(s);
        int idx = opts.indexOf(option);
        if (idx < 0) {
            return false;
}
        boolean ok = AbyssGuiData.writeField(s, ModeSetting.class, "Y", option);
        return ok &= AbyssGuiData.writeInt(s, ModeSetting.class, "p", idx);
}
    public static List<String> modeOptions(ModeSetting s) {
        try {
            List<String> l = s.S();
            if (l != null) {
                return l;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return new ArrayList<String>();
}
    public static boolean setNumber(NumberSetting s, float v2) {
        float lo = AbyssGuiData.numberMin(s);
        float hi = AbyssGuiData.numberMax(s);
        if (v2 < lo) {
            v2 = lo;
}
        if (v2 > hi) {
            v2 = hi;
}
        try {
            Field f = NumberSetting.class.getDeclaredField("z");
            f.setAccessible(true);
            f.setFloat(s, v2);
            return true;
}
        catch (Throwable t2) {
            return false;
}
}
    public static float numberValue(NumberSetting s) {
        try {
            return s.L();
}
        catch (Throwable t2) {
            return 0.0f;
}
}
    public static float numberMin(NumberSetting s) {
        try {
            return s.i();
}
        catch (Throwable t2) {
            return 0.0f;
}
}
    public static float numberMax(NumberSetting s) {
        try {
            return s.F();
}
        catch (Throwable t2) {
            return 1.0f;
}
}
    public static float numberStep(NumberSetting s) {
        try {
            float st = s.U();
            return st > 0.0f ? st : 0.1f;
}
        catch (Throwable t2) {
            return 0.1f;
}
}
    public static boolean setPercentage(PercentageSetting s, int v2) {
        if (v2 < 0) {
            v2 = 0;
}
        if (v2 > 100) {
            v2 = 100;
}
        try {
            s.d(v2);
            return s.k() == v2;
}
        catch (Throwable t2) {
            return false;
}
}
    public static int percentageValue(PercentageSetting s) {
        try {
            return s.k();
}
        catch (Throwable t2) {
            return 0;
}
}
    public static String colorValue(ColorSetting s) {
        try {
            String v2 = s.Q();
            return v2 == null ? "FFFFFF" : v2;
}
        catch (Throwable t2) {
            return "FFFFFF";
}
}
    public static boolean setColor(ColorSetting s, String hex) {
        try {
            s.e(hex);
            return hex.equals(s.Q());
}
        catch (Throwable t2) {
            return false;
}
}
    public static String textValue(TextSetting s) {
        try {
            String v2 = s.X();
            return v2 == null ? "" : v2;
}
        catch (Throwable t2) {
            return "";
}
}
    public static boolean setText(TextSetting s, String v2) {
        try {
            s.O(v2);
            return v2.equals(s.X());
}
        catch (Throwable t2) {
            return false;
}
}
    public static String keyName(int code) {
        if (code == 0) {
            return BIND_NONE;
}
        try {
            Class<?> k = Class.forName("org.lwjgl.input.Keyboard");
            Method m2 = k.getMethod("getKeyName", Integer.TYPE);
            Object n2 = m2.invoke(null, code);
            if (n2 != null) {
                return String.valueOf(n2);
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return "#" + code;
}
    public static int keyIndex(String lwjglName) {
        if (lwjglName == null) {
            return 0;
}
        try {
            Class<?> k = Class.forName("org.lwjgl.input.Keyboard");
            Method m2 = k.getMethod("getKeyIndex", String.class);
            Object n2 = m2.invoke(null, lwjglName);
            if (n2 instanceof Integer) {
                return (Integer)n2;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return 0;
}
    public static String sessionName() {
        try {
            Object n2;
            Object session;
            Class<?> mc = Class.forName("net.minecraft.client.Minecraft");
            Object inst = mc.getMethod("getMinecraft", new Class[0]).invoke(null, new Object[0]);
            if (inst != null && (session = mc.getMethod("getSession", new Class[0]).invoke(inst, new Object[0])) != null && (n2 = session.getClass().getMethod("getUsername", new Class[0]).invoke(session, new Object[0])) != null && String.valueOf(n2).length() > 0) {
                return String.valueOf(n2);
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return null;
}
    public static String userName() {
        String s = AbyssGuiData.sessionName();
        return s != null ? s : "b";
}
    public static File configDir() {
        String over = System.getProperty("abyss.gui.dir");
        if (over != null) {
            File f = new File(over);
            f.mkdirs();
            return f;
}
        File base = null;
        try {
            Object d;
            Class<?> mc = Class.forName("net.minecraft.client.Minecraft");
            Object inst = mc.getMethod("getMinecraft", new Class[0]).invoke(null, new Object[0]);
            if (inst != null && (d = mc.getField("mcDataDir").get(inst)) instanceof File) {
                base = (File)d;
}
}
        catch (Throwable mc) {
            // empty catch block
}
        File dir = base == null ? new File("Abyss") : new File(base, "Abyss");
        try {
            dir.mkdirs();
}
        catch (Throwable throwable) {
            // empty catch block
}
        return dir;
}
    public static List<File> configFiles() {
        ArrayList<File> out = new ArrayList<File>();
        try {
            File[] fs = AbyssGuiData.configDir().listFiles();
            if (fs != null) {
                for (int i = 0; i < fs.length; ++i) {
                    if (!fs[i].isFile() || !fs[i].getName().toLowerCase().endsWith(JSON_SUFFIX)) continue;
                    out.add(fs[i]);
}
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        Collections.sort(out, new Comparator<File>(){

            @Override
            public int compare(File a, File b) {
                return a.getName().compareToIgnoreCase(b.getName());
}
        });
        return out;
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String read(File f) {
        String string;
        Reader r2 = null;
        try {
            int k;
            r2 = new InputStreamReader((InputStream)new FileInputStream(f), "UTF-8");
            StringBuilder b = new StringBuilder();
            char[] buf = new char[4096];
            while ((k = r2.read(buf)) > 0) {
                b.append(buf, 0, k);
}
            string = b.toString();
            if (r2 == null) return string;
}
        catch (Throwable t2) {
            try {
                String string2 = null;
                return string2;
}
            catch (Throwable throwable) {
                throw throwable;
}
            finally {
                if (r2 != null) {
                    try {
                        r2.close();
}
                    catch (Throwable throwable) {}
}
}
}
        try {
            r2.close();
            return string;
}
        catch (Throwable throwable) {
            // empty catch block
}
        return string;
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean write(File f, String text) {
        boolean bl;
        Writer w2 = null;
        try {
            w2 = new OutputStreamWriter((OutputStream)new FileOutputStream(f), "UTF-8");
            w2.write(text);
            bl = true;
            if (w2 == null) return bl;
}
        catch (Throwable t2) {
            try {
                boolean bl2 = false;
                return bl2;
}
            catch (Throwable throwable) {
                throw throwable;
}
            finally {
                if (w2 != null) {
                    try {
                        w2.close();
}
                    catch (Throwable throwable) {}
}
}
}
        try {
            w2.close();
            return bl;
}
        catch (Throwable throwable) {
            // empty catch block
}
        return bl;
}
    public static Map<String, Object> merged(File target) {
        Map<Object, Object> root = null;
        try {
            if (target != null && target.isFile()) {
                root = AbyssGuiJson.parseObject(AbyssGuiData.read(target));
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        if (root == null) {
            root = new LinkedHashMap();
}
        if (!(root.get(DESCRIPTION_KEY) instanceof String)) {
            root.put(DESCRIPTION_KEY, DEFAULT_DESCRIPTION);
}
        for (Module m2 : AbyssGuiData.modules()) {
            String n2 = AbyssGuiData.name(m2);
            Map<String, Object> block = AbyssGuiJson.asMap(root.get(n2));
            if (block == null) {
                block = new LinkedHashMap<String, Object>();
                root.put(n2, block);
}
            block.put(STATUS_KEY, AbyssGuiData.enabled(m2));
            block.put(KEYBIND_KEY, AbyssGuiData.keyBind(m2));
            block.put(VISIBLE_KEY, AbyssGuiData.visible(m2));
            block.put(SUFFIX_KEY, AbyssGuiData.suffixVisible(m2));
            for (Setting s : AbyssGuiData.settings(m2)) {
                if (s == null) continue;
                String sn = AbyssGuiData.configKey(block, s);
                Object v2 = AbyssGuiData.settingValue(s);
                if (sn.length() <= 0 || v2 == null) continue;
                block.put(sn, v2);
}
}
        return root;
}
    public static Map<String, Object> snapshot(String description) {
        LinkedHashMap<String, Object> root = new LinkedHashMap<String, Object>();
        root.put(DESCRIPTION_KEY, description == null ? DEFAULT_DESCRIPTION : description);
        for (Module m2 : AbyssGuiData.modules()) {
            LinkedHashMap<String, Object> block = new LinkedHashMap<String, Object>();
            block.put(STATUS_KEY, AbyssGuiData.enabled(m2));
            block.put(KEYBIND_KEY, AbyssGuiData.keyBind(m2));
            block.put(VISIBLE_KEY, AbyssGuiData.visible(m2));
            block.put(SUFFIX_KEY, AbyssGuiData.suffixVisible(m2));
            for (Setting s : AbyssGuiData.settings(m2)) {
                if (s == null) continue;
                String n2 = AbyssConfig.settingKey(AbyssGuiData.settingName(s));
                Object v2 = AbyssGuiData.settingValue(s);
                if (n2.length() <= 0 || v2 == null || block.containsKey(n2)) continue;
                block.put(n2, v2);
}
            root.put(AbyssGuiData.name(m2), block);
}
        return root;
}
    public static int[] applyFile(File f) {
        int[] counts = new int[3];
        String text = AbyssGuiData.read(f);
        if (text == null) {
            return counts;
}
        Map<String, Object> root = AbyssGuiJson.parseObject(text);
        if (root == null) {
            return counts;
}
        for (Module m2 : AbyssGuiData.modules()) {
            Map<String, Object> block = AbyssGuiJson.asMap(root.get(AbyssGuiData.name(m2)));
            if (block == null) continue;
            counts[0] = counts[0] + 1;
            if (block.containsKey(STATUS_KEY)) {
                AbyssGuiData.setEnabled(m2, AbyssGuiJson.asBool(block.get(STATUS_KEY), AbyssGuiData.enabled(m2)));
                counts[1] = counts[1] + 1;
}
            if (block.containsKey(KEYBIND_KEY) && AbyssGuiData.setKeyBind(m2, (int)AbyssGuiJson.asNum(block.get(KEYBIND_KEY), AbyssGuiData.keyBind(m2)))) {
                counts[1] = counts[1] + 1;
}
            if (block.containsKey(VISIBLE_KEY) && AbyssGuiData.writeBoolField(m2, Module.class, "w", AbyssGuiJson.asBool(block.get(VISIBLE_KEY), AbyssGuiData.visible(m2)))) {
                counts[1] = counts[1] + 1;
}
            if (block.containsKey(SUFFIX_KEY) && AbyssGuiData.writeBoolField(m2, Module.class, "q", AbyssGuiJson.asBool(block.get(SUFFIX_KEY), AbyssGuiData.suffixVisible(m2)))) {
                counts[1] = counts[1] + 1;
}
            for (Setting s : AbyssGuiData.settings(m2)) {
                String n2;
                if (s == null || (n2 = AbyssGuiData.configKey(block, s)).length() == 0 || !block.containsKey(n2) || !AbyssGuiData.applySetting(s, block.get(n2))) continue;
                counts[2] = counts[2] + 1;
}
}
        return counts;
}
    public static boolean applySetting(Setting s, Object raw) {
        try {
            if (s instanceof BooleanSetting) {
                return AbyssGuiData.setBoolean((BooleanSetting)s, AbyssGuiJson.asBool(raw, ((BooleanSetting)s).c()));
}
            if (s instanceof ModeSetting) {
                return raw != null && AbyssGuiData.setMode((ModeSetting)s, String.valueOf(raw));
}
            if (s instanceof NumberSetting) {
                return AbyssGuiData.setNumber((NumberSetting)s, (float)AbyssGuiJson.asNum(raw, AbyssGuiData.numberValue((NumberSetting)s)));
}
            if (s instanceof PercentageSetting) {
                return AbyssGuiData.setPercentage((PercentageSetting)s, (int)AbyssGuiJson.asNum(raw, AbyssGuiData.percentageValue((PercentageSetting)s)));
}
            if (s instanceof ColorSetting) {
                return raw != null && AbyssGuiData.setColor((ColorSetting)s, String.valueOf(raw));
}
            if (s instanceof TextSetting) {
                if (raw == null) {
                    return false;
}
                ((TextSetting)s).O(String.valueOf(raw));
                return String.valueOf(raw).equals(((TextSetting)s).X());
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return false;
}
    public static List<String> transcript() {
        try {
            if (ConfigManagerWindow.D != null) {
                return ConfigManagerWindow.D;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return null;
}
    public static List<String> bootNotes() {
        try {
            return AbyssBootstrap.PENDING;
}
        catch (Throwable t2) {
            return new ArrayList<String>();
}
}
    public static boolean dispatch(String line) {
        try {
            return AbyssCommands.dispatch(line);
}
        catch (Throwable t2) {
            return false;
}
}
    private static Object readField(Object owner, Class<?> decl, String name) {
        try {
            Field f = decl.getDeclaredField(name);
            f.setAccessible(true);
            return f.get(owner);
}
        catch (Throwable t2) {
            return null;
}
}
    private static boolean writeField(Object owner, Class<?> decl, String name, Object value) {
        try {
            Field f = decl.getDeclaredField(name);
            f.setAccessible(true);
            f.set(owner, value);
            return true;
}
        catch (Throwable t2) {
            return false;
}
}
    private static boolean writeInt(Object owner, Class<?> decl, String name, int value) {
        try {
            Field f = decl.getDeclaredField(name);
            if (f.getType() != Integer.TYPE) {
                return false;
}
            f.setAccessible(true);
            f.setInt(owner, value);
            return f.getInt(owner) == value;
}
        catch (Throwable t2) {
            return false;
}
}
    private static boolean writeBoolField(Object owner, Class<?> decl, String name, boolean value) {
        try {
            Field f = decl.getDeclaredField(name);
            if (f.getType() != Boolean.TYPE) {
                return false;
}
            f.setAccessible(true);
            f.setBoolean(owner, value);
            return f.getBoolean(owner) == value;
}
        catch (Throwable t2) {
            return false;
}
}
}