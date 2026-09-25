/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonPrimitive
 *  net.minecraft.client.Minecraft
 */
package Abyss.internal.restore;

import Abyss.internal.restore.AbyssModuleRegistry;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.setting.settings.TextSetting;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
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
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;

public final class AbyssConfig {
    public static final long MODULE_I_CARRIER = 20724619369162L;
    private static final String FILE = "current.json";
    public static volatile int fieldFailures;
    public static final List<String> fieldFailureNotes;
    public static final String DESCRIPTION_KEY = "description";
    public static final String DEFAULT_DESCRIPTION = "Description of your config here";
    private static final String[] COMMON;
    private static final int K_BOOL = 0;
    private static final int K_INT = 1;
    private static final int K_FLOAT = 2;
    private static final int K_STR = 3;
    private static final Object SAVE_LOCK;
    public static volatile int saveCount;
    public static volatile String lastSaveNote;
    public static volatile SaveResult lastResult;
    private static volatile Map<Setting, String> boot;
    public static volatile int bootSnapshot;

    private AbyssConfig() {
}
    public static String apply(List<String> pending) {
        int configured = 0;
        int matched = 0;
        int meta = 0;
        int applied = 0;
        int skippedPlaceholder = 0;
        int unmatched = 0;
        String note;
        try {
            File f = AbyssConfig.locate();
            if (f == null) {
                note = "Abyss.config NOT APPLIED -- no current.json found; every module keeps its factory state";
                pending.add(note);
                return note;
}
            JsonObject root;
            try (InputStreamReader r2 = new InputStreamReader((InputStream)new FileInputStream(f), "UTF-8");) {
                root = new JsonParser().parse((Reader)r2).getAsJsonObject();
}
            for (Map.Entry e : root.entrySet()) {
                if (!((JsonElement)e.getValue()).isJsonObject() || !((JsonElement)e.getValue()).getAsJsonObject().has("status")) continue;
                ++configured;
}
            ArrayList<String> on = new ArrayList<String>();
            for (Module m2 : ModuleManager.S) {
                if (m2 == null) continue;
                String name = m2.b();
                if (name == null || name.startsWith("?")) {
                    ++skippedPlaceholder;
                    continue;
}
                JsonElement entry = root.get(name);
                if (entry == null || !entry.isJsonObject()) {
                    ++unmatched;
                    continue;
}
                JsonObject o2 = entry.getAsJsonObject();
                if (!o2.has("status")) {
                    ++unmatched;
                    continue;
}
                ++matched;
                boolean status = o2.get("status").getAsBoolean();
                m2.I(MODULE_I_CARRIER, status);
                if (status) {
                    ++applied;
                    on.add(name);
}
                meta += AbyssConfig.writeInt(m2, "j", o2, "keyBind");
                meta += AbyssConfig.writeBool(m2, "w", o2, "visible");
                meta += AbyssConfig.writeBool(m2, "q", o2, "suffix-visible");
}
            note = "Abyss.config applied from " + f.getPath() + " -- configured=" + configured + " matched=" + matched + " enabled=" + applied + " skipped_placeholder=" + skippedPlaceholder + " unmatched=" + unmatched + " meta=" + meta + " field_failures=" + fieldFailures + " on=" + on;
            if (fieldFailures > 0) {
                List<String> list = fieldFailureNotes;
                synchronized (list) {
                    for (String bad : fieldFailureNotes) {
                        pending.add(bad);
}
}
}
}
        catch (Throwable t2) {
            note = "Abyss.config FAILED (" + t2 + ") -- every module keeps its factory state";
}
        pending.add(note);
        return note;
}
    public static JsonObject read() {
        return AbyssConfig.parse(AbyssConfig.locate());
}
    private static void fieldFailure(String field, Class<?> want, String key, Throwable t2, String detail) {
        ++fieldFailures;
        String note = "AbyssConfig cannot persist '" + key + "': Module." + field + " (" + want.getSimpleName() + ") " + (t2 != null ? "threw " + t2 : detail) + " -- this key is silently dropped from every save AND load; if the field was renamed, Abyss/internal/restore/AbyssNameMap must be updated in the same edit";
        List<String> list = fieldFailureNotes;
        synchronized (list) {
            if (fieldFailureNotes.size() < 32) {
                fieldFailureNotes.add(note);
}
}
}
    private static int writeBool(Module m2, String field, JsonObject o2, String key) {
        if (!o2.has(key)) {
            return 0;
}
        try {
            Field f = Module.class.getDeclaredField(field);
            if (f.getType() != Boolean.TYPE) {
                AbyssConfig.fieldFailure(field, Boolean.TYPE, key, null, "is a " + f.getType().getName() + ", not boolean");
                return 0;
}
            f.setAccessible(true);
            f.setBoolean(m2, o2.get(key).getAsBoolean());
            if (f.getBoolean(m2) != o2.get(key).getAsBoolean()) {
                AbyssConfig.fieldFailure(field, Boolean.TYPE, key, null, "did not keep the value written to it");
                return 0;
}
            return 1;
}
        catch (Throwable t2) {
            AbyssConfig.fieldFailure(field, Boolean.TYPE, key, t2, null);
            return 0;
}
}
    private static int writeInt(Module m2, String field, JsonObject o2, String key) {
        if (!o2.has(key)) {
            return 0;
}
        try {
            Field f = Module.class.getDeclaredField(field);
            if (f.getType() != Integer.TYPE) {
                AbyssConfig.fieldFailure(field, Integer.TYPE, key, null, "is a " + f.getType().getName() + ", not int");
                return 0;
}
            f.setAccessible(true);
            f.setInt(m2, o2.get(key).getAsInt());
            if (f.getInt(m2) != o2.get(key).getAsInt()) {
                AbyssConfig.fieldFailure(field, Integer.TYPE, key, null, "did not keep the value written to it");
                return 0;
}
            return 1;
}
        catch (Throwable t2) {
            AbyssConfig.fieldFailure(field, Integer.TYPE, key, t2, null);
            return 0;
}
}
    // R15_SEMANTIC_RECOVERY_MARKER
    public static int snapshotBoot() {
        IdentityHashMap<Setting, String> snap = new IdentityHashMap<Setting, String>();
        try {
            List<Module> all = ModuleManager.S == null ? new ArrayList<Module>() : ModuleManager.S;
            for (Module m2 : all) {
                List<Setting> live;
                if (m2 == null) continue;
                try {
                    live = m2.w();
}
                catch (Throwable t2) {
                    continue;
}
                if (live == null) continue;
                for (Setting s : live) {
                    int kind;
                    JsonPrimitive v2;
                    if (s == null || (v2 = (kind = AbyssConfig.kindOf(s)) < 0 ? null : AbyssConfig.value(s)) == null) continue;
                    snap.put(s, AbyssConfig.canon(kind, (JsonElement)v2));
}
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        boot = snap;
        bootSnapshot = snap.size();
        return bootSnapshot;
}
    public static SaveResult save(String name) {
        SaveResult r2 = new SaveResult();
        Object object = SAVE_LOCK;
        synchronized (object) {
            try {
                File f = AbyssConfig.target(name);
                r2.path = f.getPath();
                JsonObject root = AbyssConfig.parse(f);
                if (root == null) {
                    root = AbyssConfig.parse(AbyssConfig.sibling(f, ".bak"));
}
                if (root == null) {
                    root = new JsonObject();
}
                if (!root.has(DESCRIPTION_KEY) || !root.get(DESCRIPTION_KEY).isJsonPrimitive()) {
                    root.addProperty(DESCRIPTION_KEY, DEFAULT_DESCRIPTION);
}
                r2.topLevelBefore = root.entrySet().size();
                List<Module> all = ModuleManager.S == null ? new ArrayList<Module>() : ModuleManager.S;
                for (Module m2 : all) {
                    JsonObject block;
                    if (m2 == null) continue;
                    ++r2.modules;
                    if (!AbyssModuleRegistry.isConfigPersistable(m2)) {
                        ++r2.skippedUnnamed;
                        continue;
}
                    String n2 = m2.b();
                    JsonElement e = root.get(n2);
                    if (e != null && e.isJsonObject()) {
                        block = e.getAsJsonObject();
                    } else {
                        block = new JsonObject();
                        root.add(n2, (JsonElement)block);
                        ++r2.created;
}
                    ++r2.blocks;
                    AbyssConfig.put(r2, block, n2, COMMON[0], 0, new JsonPrimitive(Boolean.valueOf(m2.o())));
                    AbyssConfig.put(r2, block, n2, COMMON[1], 1, new JsonPrimitive((Number)m2.h()));
                    AbyssConfig.put(r2, block, n2, COMMON[2], 0, new JsonPrimitive(Boolean.valueOf(m2.D())));
                    AbyssConfig.put(r2, block, n2, COMMON[3], 0, new JsonPrimitive(Boolean.valueOf(m2.r())));
                    r2.commonKeys += 4;
                    AbyssConfig.settings(r2, block, n2, m2);
}
                r2.topLevelAfter = root.entrySet().size();
                r2.ok = AbyssConfig.write(f, root);
                r2.note = r2.ok ? "written" : "write failed";
}
            catch (Throwable t2) {
                r2.ok = false;
                r2.note = "THREW " + t2;
}
            lastResult = r2;
            lastSaveNote = r2.toString();
            ++saveCount;
}
        return r2;
}
    private static void settings(SaveResult r2, JsonObject block, String module, Module m2) {
        List<Setting> live;
        try {
            live = m2.w();
}
        catch (Throwable t2) {
            return;
}
        if (live == null) {
            return;
}
        LinkedHashSet<String> done = new LinkedHashSet<String>();
        for (Setting s : live) {
            String key;
            int kind;
            JsonPrimitive v2;
            if (s == null) continue;
            String label = null;
            try {
                label = s.B();
}
            catch (Throwable throwable) {
                // empty catch block
}
            if (label == null || label.length() == 0 || (v2 = (kind = AbyssConfig.kindOf(s)) < 0 ? null : AbyssConfig.value(s)) == null) continue;
            String string = key = block.has(label) ? label : AbyssConfig.settingKey(label);
            if (!block.has(key)) {
                ++r2.settingsOutsideSchema;
                r2.outside.add(module + '.' + label);
                continue;
}
            if (!done.add(key)) {
                ++r2.settingsOutsideSchema;
                r2.outside.add(module + '.' + label + " (key " + key + " already taken)");
                continue;
}
            JsonElement cur = block.get(key);
            if (AbyssConfig.unrepresentable(s, cur)) {
                ++r2.modeValuesPreserved;
                r2.preserved.add(module + '.' + key + '=' + cur.getAsString());
                continue;
}
            Map<Setting, String> snap = boot;
            String was = snap == null ? null : snap.get(s);
            String now = AbyssConfig.canon(kind, (JsonElement)v2);
            if (was != null && was.equals(now) && !AbyssConfig.canon(kind, cur).equals(now)) {
                ++r2.loadGapsPreserved;
                r2.loadGaps.add(module + '.' + key + " file=" + AbyssConfig.canon(kind, cur) + " memory=" + now);
                continue;
}
            AbyssConfig.put(r2, block, module, key, kind, v2);
            ++r2.settingKeys;
}
}
    private static void put(SaveResult r2, JsonObject block, String module, String key, int kind, JsonPrimitive v2) {
        block.add(key, (JsonElement)v2);
        r2.written.add(new Object[]{module, key, kind, AbyssConfig.canon(kind, (JsonElement)v2)});
}
    private static boolean unrepresentable(Setting s, JsonElement cur) {
        if (!(s instanceof ModeSetting && cur != null && cur.isJsonPrimitive() && cur.getAsJsonPrimitive().isString())) {
            return false;
}
        try {
            List<String> opts = ((ModeSetting)s).S();
            return opts != null && !opts.isEmpty() && !opts.contains(cur.getAsString());
}
        catch (Throwable t2) {
            return false;
}
}
    public static String settingKey(String label) {
        if (label == null) {
            return null;
}
        int i = label.lastIndexOf(124);
        return i < 0 ? label : label.substring(i + 1);
}
    private static int kindOf(Setting s) {
        if (s instanceof BooleanSetting) {
            return 0;
}
        if (s instanceof PercentageSetting) {
            return 1;
}
        if (s instanceof NumberSetting) {
            return 2;
}
        if (s instanceof ModeSetting || s instanceof ColorSetting || s instanceof TextSetting) {
            return 3;
}
        return -1;
}
    private static JsonPrimitive value(Setting s) {
        try {
            if (s instanceof BooleanSetting) {
                return new JsonPrimitive(Boolean.valueOf(((BooleanSetting)s).c()));
}
            if (s instanceof PercentageSetting) {
                return new JsonPrimitive((Number)((PercentageSetting)s).k());
}
            if (s instanceof NumberSetting) {
                float v2 = ((NumberSetting)s).L();
                return Float.isNaN(v2) || Float.isInfinite(v2) ? null : new JsonPrimitive((Number)Float.valueOf(v2));
}
            String t2 = s instanceof ModeSetting ? ((ModeSetting)s).Y() : (s instanceof ColorSetting ? ((ColorSetting)s).Q() : ((TextSetting)s).X());
            return t2 == null ? null : new JsonPrimitive(t2);
}
        catch (Throwable t3) {
            return null;
}
}
    private static String canon(int kind, JsonElement e) {
        if (e == null || !e.isJsonPrimitive()) {
            return "<absent>";
}
        JsonPrimitive p = e.getAsJsonPrimitive();
        try {
            if (kind == 0) {
                return String.valueOf(p.getAsBoolean());
}
            if (kind == 1) {
                return String.valueOf(p.getAsInt());
}
            if (kind == 2) {
                return String.valueOf(p.getAsFloat());
}
            return p.getAsString();
}
        catch (Throwable t2) {
            return "<unreadable " + p + '>';
}
}
    public static List<String> verify(SaveResult r2, String corrupt) {
        ArrayList<String> bad = new ArrayList<String>();
        JsonObject root = AbyssConfig.parse(new File(r2.path));
        if (root == null) {
            bad.add("file unreadable: " + r2.path);
            return bad;
}
        for (Object[] rec : r2.written) {
            String module = (String)rec[0];
            String key = (String)rec[1];
            int kind = (Integer)rec[2];
            JsonElement be = root.get(module);
            String got = "<no block>";
            if (be != null && be.isJsonObject()) {
                got = AbyssConfig.canon(kind, be.getAsJsonObject().get(key));
}
            String want = (String)rec[3];
            if (corrupt != null && corrupt.equals(module + '/' + key)) {
                want = want + "#CORRUPT";
}
            if (want.equals(got)) continue;
            bad.add(module + '.' + key + " expected " + want + " got " + got);
}
        return bad;
}
    public static File target(String name) {
        String n2;
        String override = System.getProperty("abyss.config");
        String string = n2 = name == null || name.length() == 0 ? FILE : name;
        if (n2.indexOf(47) >= 0 || n2.indexOf(92) >= 0 || n2.indexOf(58) >= 0 || n2.contains("..")) {
            n2 = FILE;
}
        if (!n2.toLowerCase().endsWith(".json")) {
            n2 = n2 + ".json";
}
        if (override != null) {
            File f = new File(override);
            if (FILE.equals(n2) || f.getName().equalsIgnoreCase(n2)) {
                return f;
}
            File p = f.getParentFile();
            if (p != null) {
                return new File(p, n2);
}
}
        File dir = null;
        try {
            dir = Minecraft.getMinecraft().mcDataDir;
}
        catch (Throwable p) {
            // empty catch block
}
        File d = dir == null ? new File("Abyss") : new File(dir, "Abyss");
        try {
            d.mkdirs();
}
        catch (Throwable throwable) {
            // empty catch block
}
        return new File(d, n2);
}
    private static File sibling(File f, String suffix) {
        File a = f.getAbsoluteFile();
        return new File(a.getParentFile(), a.getName() + suffix);
}
    private static JsonObject parse(File f) {
        if (f == null || !f.isFile()) {
            return null;
}
        Reader r2 = null;
        try {
            r2 = new InputStreamReader((InputStream)new FileInputStream(f), "UTF-8");
            JsonElement e = new JsonParser().parse(r2);
            return e != null && e.isJsonObject() ? e.getAsJsonObject() : null;
}
        catch (Throwable t2) {
            return null;
}
        finally {
            if (r2 != null) {
                try {
                    r2.close();
}
                catch (Throwable ignored) {
                    // close failure does not change parse result
}
}
}
}
    private static boolean write(File f, JsonObject root) {
        File tmp = AbyssConfig.sibling(f, ".tmp");
        Writer w2 = null;
        try {
            w2 = new OutputStreamWriter((OutputStream)new FileOutputStream(tmp), "UTF-8");
            new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)root, (Appendable)w2);
            w2.close();
            w2 = null;
            if (f.isFile()) {
                File bak = AbyssConfig.sibling(f, ".bak");
                if (bak.isFile()) {
                    bak.delete();
}
                f.renameTo(bak);
}
            return tmp.renameTo(f);
}
        catch (Throwable t2) {
            return false;
}
        finally {
            if (w2 != null) {
                try {
                    w2.close();
}
                catch (Throwable ignored) {
                    // close failure cannot make a failed write successful
}
}
}
}
    private static File locate() {
        File[] candidates;
        String override = System.getProperty("abyss.config");
        if (override != null) {
            File f = new File(override);
            return f.isFile() ? f : null;
}
        File dir = null;
        try {
            dir = Minecraft.getMinecraft().mcDataDir;
}
        catch (Throwable throwable) {
            // empty catch block
}
        for (File c : candidates = new File[]{dir == null ? null : new File(new File(dir, "Abyss"), FILE), new File(new File("Abyss"), FILE), new File(FILE)}) {
            if (c == null || !c.isFile()) continue;
            return c;
}
        return null;
}
    static {
        fieldFailureNotes = new ArrayList<String>();
        COMMON = new String[]{"status", "keyBind", "visible", "suffix-visible"};
        SAVE_LOCK = new Object();
        lastSaveNote = "no save attempted";
        bootSnapshot = -1;
}
    public static final class SaveResult {
        public boolean ok;
        public String path = "?";
        public int modules;
        public int blocks;
        public int created;
        public int skippedUnnamed;
        public int commonKeys;
        public int settingKeys;
        public int settingsOutsideSchema;
        public int modeValuesPreserved;
        public int loadGapsPreserved;
        public int topLevelBefore;
        public int topLevelAfter;
        public final List<Object[]> written = new ArrayList<Object[]>();
        public final List<String> outside = new ArrayList<String>();
        public final List<String> preserved = new ArrayList<String>();
        public final List<String> loadGaps = new ArrayList<String>();
        public String note = "not run";

        public String toString() {
            return "ok=" + this.ok + " file=" + this.path + " modules=" + this.modules + " blocks=" + this.blocks + " created=" + this.created + " skipped_unnamed=" + this.skippedUnnamed + " common=" + this.commonKeys + " settings=" + this.settingKeys + " settings_outside_schema=" + this.settingsOutsideSchema + " mode_values_preserved=" + this.modeValuesPreserved + " load_gaps_preserved=" + this.loadGapsPreserved + " top_level=" + this.topLevelBefore + "+" + this.created + "->" + this.topLevelAfter + " note=" + this.note;
}
}
}