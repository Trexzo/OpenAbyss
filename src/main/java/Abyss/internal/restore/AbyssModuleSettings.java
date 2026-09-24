/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 */
package Abyss.internal.restore;

import Abyss.internal.restore.AbyssConfig;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.setting.settings.TextSetting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class AbyssModuleSettings {
    private static final List<String> COMMON = new ArrayList<String>();
    private static int relabelForced;
    private static int relabelJudged;
    private static int relabelSkippedEmpty;
    private static int relabelWouldOverwrite;
    private static String relabelModule;
    private static final List<String> relabelOverwrites;
    private static final int T_BOOL = 0;
    private static final int T_PCT = 1;
    private static final int T_NUM = 2;
    private static final int T_COLOR = 3;
    private static final int T_MODE = 4;
    private static final int T_TEXT = 5;
    private static final int T_NONE = -1;

    private AbyssModuleSettings() {
}
    public static String apply(List<String> pending) {
        String note;
        int modules = 0;
        int filled = 0;
        int added = 0;
        int nullStatics = 0;
        int orderedByConfig = 0;
        int renamed = 0;
        int revalued = 0;
        int unresolved = 0;
        ArrayList<String> deficits = new ArrayList<String>();
        try {
            JsonObject cfg = AbyssConfig.read();
            relabelForced = 0;
            relabelJudged = 0;
            relabelSkippedEmpty = 0;
            relabelWouldOverwrite = 0;
            relabelOverwrites.clear();
            for (Module m2 : ModuleManager.S) {
                if (m2 == null) continue;
                ++modules;
                List<Setting> live = m2.w();
                if (live == null || !live.isEmpty()) continue;
                LinkedHashMap<String, Setting> byName = new LinkedHashMap<String, Setting>();
                ArrayList<Setting> declared = new ArrayList<Setting>();
                nullStatics += AbyssModuleSettings.collect(m2.getClass(), byName, declared);
                JsonObject block = AbyssModuleSettings.configBlock(cfg, m2);
                if (block != null) {
                    relabelModule = m2.b();
                    if (System.getProperty("abyss.settings.relabel") != null) {
                        int[] relabelResult = AbyssModuleSettings.relabel(block, declared);
                        renamed += relabelResult[0];
                        revalued += relabelResult[1];
                        unresolved += relabelResult[2];
}
                    byName.clear();
                    for (Setting s : declared) {
                        String n2 = AbyssModuleSettings.name(s);
                        if (n2 == null || byName.containsKey(n2)) continue;
                        byName.put(n2, s);
}
}
                List<String> order = AbyssModuleSettings.configOrder(cfg, m2);
                ArrayList<Setting> out = new ArrayList<Setting>();
                IdentityHashMap<Setting, Boolean> seen = new IdentityHashMap<Setting, Boolean>();
                if (order != null) {
                    ++orderedByConfig;
                    for (String key : order) {
                        Setting s = (Setting)byName.get(key);
                        if (s == null || seen.put(s, Boolean.TRUE) != null) continue;
                        out.add(s);
}
}
                Setting prev = null;
                for (Setting s : declared) {
                    if (seen.put(s, Boolean.TRUE) == null) {
                        int at = out.size();
                        if (prev != null) {
                            for (int i = 0; i < out.size(); ++i) {
                                if (out.get(i) != prev) continue;
                                at = i + 1;
                                break;
}
}
                        out.add(at, s);
}
                    prev = s;
}
                if (!out.isEmpty()) {
                    live.addAll(out);
                    ++filled;
                    added += out.size();
}
                if (order == null || out.size() >= order.size()) continue;
                deficits.add(m2.b() + ": " + out.size() + "/" + order.size());
}
            if (!deficits.isEmpty()) {
                // empty if block
}
            note = "Abyss.settings filled " + filled + "/" + modules + " modules with " + added + " settings (" + orderedByConfig + " ordered from config, rest in declaration order); skipped " + nullStatics + " null statics; forced matches " + renamed + " (already correct in source, relabel no longer writes), values restored " + revalued + ", " + unresolved + " left obfuscated because their Setting type group holds more than one candidate";
            String g1 = "Abyss.settings relabel G1 forced " + relabelForced + " / judged " + relabelJudged + " / empty " + relabelSkippedEmpty + " / wouldOverwrite " + relabelWouldOverwrite + " (must be 0)" + (relabelWouldOverwrite == 0 ? "" : " " + relabelOverwrites);
            pending.add(g1);
}
        catch (Throwable t2) {
            note = "Abyss.settings FAILED (" + t2 + ") -- the ClickGUI will show no settings";
}
        pending.add(note);
        return note;
}
    public static String applyByName(List<String> pending) {
        int[] real = AbyssModuleSettings.byName(false);
        String note = "Abyss.settings by-name applied " + real[0] + " config value(s) onto settings whose label is a key of their own block (" + real[1] + " labels matched a key; refused " + real[2] + " mode value(s) outside the recovered option table, " + real[3] + " number(s) outside the shipped range, " + real[4] + " type mismatch(es), " + real[5] + " duplicate key(s); " + real[6] + " label(s) are not a key of their block; " + real[7] + " write(s) did not read back)";
        if (System.getProperty("abyss.byname.control") != null) {
            int[] ctl = AbyssModuleSettings.byName(true);
            note = note + "; NEGATIVE CONTROL (labels rotated by one, dry run) would have matched " + ctl[1] + " and applied " + ctl[0] + (ctl[0] < real[0] ? " -- control is weaker, as it must be" : " -- CONTROL IS NOT WEAKER, this gate proves nothing");
}
        pending.add(note);
        return note;
}
    private static int[] byName(boolean control) {
        int[] r2 = new int[8];
        try {
            JsonObject cfg = AbyssConfig.read();
            if (cfg == null) {
                return r2;
}
            for (Module m2 : ModuleManager.S) {
                List<Setting> live;
                JsonObject block;
                if (m2 == null || (block = AbyssModuleSettings.configBlock(cfg, m2)) == null) continue;
                try {
                    live = m2.w();
}
                catch (Throwable t2) {
                    continue;
}
                if (live == null || live.isEmpty()) continue;
                HashMap<String, Boolean> used = new HashMap<String, Boolean>();
                for (int i = 0; i < live.size(); ++i) {
                    String key;
                    Setting labelFrom;
                    String label;
                    Setting s = live.get(i);
                    if (s == null || (label = AbyssModuleSettings.name(labelFrom = control ? live.get((i + 1) % live.size()) : s)) == null || label.length() == 0 || AbyssModuleSettings.bucketOf(s) == -1) continue;
                    String string = key = block.has(label) ? label : AbyssConfig.settingKey(label);
                    if (key == null || !block.has(key)) {
                        r2[6] = r2[6] + 1;
                        continue;
}
                    JsonElement v2 = block.get(key);
                    int bs = AbyssModuleSettings.bucketOf(s);
                    int bv = AbyssModuleSettings.bucketOf(v2);
                    if (bv == -1 || (bs == 4 || bs == 5 ? bv != 4 : bs != bv)) {
                        r2[4] = r2[4] + 1;
                        continue;
}
                    if (used.put(key, Boolean.TRUE) != null) {
                        r2[5] = r2[5] + 1;
                        continue;
}
                    r2[1] = r2[1] + 1;
                    int verdict = AbyssModuleSettings.write(s, v2.getAsJsonPrimitive(), control);
                    if (verdict == 0) {
                        r2[0] = r2[0] + 1;
                        continue;
}
                    int n2 = verdict + 1;
                    r2[n2] = r2[n2] + 1;
}
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return r2;
}
    private static int write(Setting s, JsonPrimitive p, boolean dryRun) {
        try {
            if (s instanceof BooleanSetting) {
                boolean v2 = p.getAsBoolean();
                if (dryRun) {
                    return 0;
}
                ((BooleanSetting)s).v(v2, 0L);
                return ((BooleanSetting)s).c() == v2 ? 0 : 6;
}
            if (s instanceof PercentageSetting) {
                int v3 = p.getAsInt();
                if (dryRun) {
                    return 0;
}
                ((PercentageSetting)s).d(v3);
                return ((PercentageSetting)s).k() == v3 ? 0 : 6;
}
            if (s instanceof NumberSetting) {
                NumberSetting ns = (NumberSetting)s;
                float v4 = p.getAsFloat();
                if (Float.isNaN(v4) || Float.isInfinite(v4) || v4 < ns.i() || v4 > ns.F()) {
                    return 2;
}
                if (dryRun) {
                    return 0;
}
                return AbyssModuleSettings.setFloat(ns, v4) && ns.L() == v4 ? 0 : 6;
}
            if (s instanceof ColorSetting) {
                String v5 = p.getAsString();
                if (!AbyssModuleSettings.isHex6(v5)) {
                    return 2;
}
                if (dryRun) {
                    return 0;
}
                ((ColorSetting)s).e(v5);
                return v5.equals(((ColorSetting)s).Q()) ? 0 : 6;
}
            if (s instanceof ModeSetting) {
                ModeSetting ms = (ModeSetting)s;
                String v6 = p.getAsString();
                List<String> opts = ms.S();
                if (opts == null || !opts.contains(v6)) {
                    return 1;
}
                if (dryRun) {
                    return 0;
}
                ms.i(v6);
                return v6.equals(ms.Y()) ? 0 : 6;
}
            if (s instanceof TextSetting) {
                String v7 = p.getAsString();
                if (dryRun) {
                    return 0;
}
                ((TextSetting)s).O(v7);
                return v7.equals(((TextSetting)s).X()) ? 0 : 6;
}
}
        catch (Throwable t2) {
            return 6;
}
        return 6;
}
    private static int collect(Class<?> c, Map<String, Setting> byName, List<Setting> declared) {
        int nulls = 0;
        for (Class<?> k = c; k != null && Module.class.isAssignableFrom(k); k = k.getSuperclass()) {
            for (Field f : k.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) || !Setting.class.isAssignableFrom(f.getType())) continue;
                try {
                    f.setAccessible(true);
                    Setting s = (Setting)f.get(null);
                    if (s == null) {
                        ++nulls;
                        continue;
}
                    declared.add(s);
                    String n2 = AbyssModuleSettings.name(s);
                    if (n2 == null || byName.containsKey(n2)) continue;
                    byName.put(n2, s);
}
                catch (Throwable throwable) {
                    // empty catch block
}
}
}
        return nulls;
}
    private static int bucketOf(Setting s) {
        if (s instanceof BooleanSetting) {
            return 0;
}
        if (s instanceof PercentageSetting) {
            return 1;
}
        if (s instanceof NumberSetting) {
            return 2;
}
        if (s instanceof ColorSetting) {
            return 3;
}
        if (s instanceof ModeSetting) {
            return 4;
}
        if (s instanceof TextSetting) {
            return 5;
}
        return -1;
}
    private static int bucketOf(JsonElement e) {
        if (e == null || !e.isJsonPrimitive()) {
            return -1;
}
        JsonPrimitive p = e.getAsJsonPrimitive();
        if (p.isBoolean()) {
            return 0;
}
        if (p.isNumber()) {
            String raw = p.getAsString();
            return raw.indexOf(46) >= 0 || raw.indexOf(101) >= 0 || raw.indexOf(69) >= 0 ? 2 : 1;
}
        if (p.isString()) {
            return AbyssModuleSettings.isHex6(p.getAsString()) ? 3 : 4;
}
        return -1;
}
    private static boolean isHex6(String s) {
        if (s == null || s.length() != 6) {
            return false;
}
        for (int i = 0; i < 6; ++i) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F') continue;
            return false;
}
        return true;
}
    private static boolean isUpper(String s) {
        if (s == null || s.length() == 0) {
            return false;
}
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == '_') continue;
            return false;
}
        return true;
}
    private static int[] relabel(JsonObject block, List<Setting> declared) {
        ArrayList<Setting> ss = new ArrayList<Setting>();
        for (Setting s : declared) {
            if (AbyssModuleSettings.bucketOf(s) == -1) continue;
            ss.add(s);
}
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<JsonElement> vals = new ArrayList<JsonElement>();
        for (Map.Entry<String, JsonElement> en : block.entrySet()) {
            if (COMMON.contains(en.getKey()) || AbyssModuleSettings.bucketOf((JsonElement)en.getValue()) == -1) continue;
            keys.add(en.getKey());
            vals.add(en.getValue());
}
        int n2 = ss.size();
        int m2 = keys.size();
        if (n2 == 0 || m2 == 0) {
            return new int[]{0, 0, n2};
}
        int cMode = 0;
        int cText = 0;
        for (Setting s : ss) {
            if (AbyssModuleSettings.bucketOf(s) == 4) {
                ++cMode;
                continue;
}
            if (AbyssModuleSettings.bucketOf(s) != 5) continue;
            ++cText;
}
        int kUpper = 0;
        int kOther = 0;
        for (JsonElement v2 : vals) {
            if (AbyssModuleSettings.bucketOf(v2) != 4) continue;
            if (AbyssModuleSettings.isUpper(v2.getAsString())) {
                ++kUpper;
                continue;
}
            ++kOther;
}
        boolean splitStrings = cMode == kUpper && cText == kOther;
        boolean[][] adj = new boolean[n2][m2];
        for (int i = 0; i < n2; ++i) {
            int bs = AbyssModuleSettings.bucketOf((Setting)ss.get(i));
            for (int j = 0; j < m2; ++j) {
                int bv = AbyssModuleSettings.bucketOf((JsonElement)vals.get(j));
                if (bs == 4 || bs == 5) {
                    if (bv != 4) continue;
                    boolean bl = !splitStrings || bs == 4 == AbyssModuleSettings.isUpper(((JsonElement)vals.get(j)).getAsString()) ? true : (adj[i][j] = false);
                    if (!adj[i][j]) continue;
                    adj[i][j] = AbyssModuleSettings.optionAllows((Setting)ss.get(i), ((JsonElement)vals.get(j)).getAsString());
                    continue;
}
                adj[i][j] = bs == bv;
}
}
        int[] base = new int[n2];
        int size = AbyssModuleSettings.match(adj, n2, m2, base, -1, -1);
        int forced = 0;
        int revalued = 0;
        boolean[] doneS = new boolean[n2];
        boolean[] doneK = new boolean[m2];
        for (int i = 0; i < n2; ++i) {
            int[] probe;
            if (base[i] < 0 || AbyssModuleSettings.match(adj, n2, m2, probe = new int[n2], i, base[i]) >= size) continue;
            Setting s = (Setting)ss.get(i);
            String key = (String)keys.get(base[i]);
            ++relabelForced;
            try {
                String before = AbyssModuleSettings.name(s);
                ++forced;
                if (before == null || before.length() == 0) {
                    ++relabelSkippedEmpty;
                } else {
                    ++relabelJudged;
                    if (!before.equals(key)) {
                        ++relabelWouldOverwrite;
                        if (relabelOverwrites.size() < 40) {
                            relabelOverwrites.add(relabelModule + "." + before + " -> " + key);
}
}
}
                doneS[i] = true;
                doneK[base[i]] = true;
                if (!AbyssModuleSettings.applyValue(s, (JsonElement)vals.get(base[i]))) continue;
                ++revalued;
                continue;
}
            catch (Throwable before) {
                // empty catch block
}
}
        for (int g = 0; g <= 5; ++g) {
            int i;
            int nS = 0;
            int nK = 0;
            String only = null;
            boolean uniform = true;
            for (int j = 0; j < m2; ++j) {
                if (doneK[j] || AbyssModuleSettings.groupOf(AbyssModuleSettings.bucketOf((JsonElement)vals.get(j)), (JsonElement)vals.get(j), splitStrings) != g) continue;
                ++nK;
                String raw = ((JsonElement)vals.get(j)).toString();
                if (only == null) {
                    only = raw;
                    continue;
}
                if (only.equals(raw)) continue;
                uniform = false;
}
            for (i = 0; i < n2; ++i) {
                if (doneS[i] || AbyssModuleSettings.groupOf(AbyssModuleSettings.bucketOf((Setting)ss.get(i)), null, splitStrings) != g) continue;
                ++nS;
}
            if (!uniform || nK == 0 || nS == 0 || nS > nK) continue;
            block12: for (i = 0; i < n2; ++i) {
                if (doneS[i] || AbyssModuleSettings.groupOf(AbyssModuleSettings.bucketOf((Setting)ss.get(i)), null, splitStrings) != g) continue;
                for (int j = 0; j < m2; ++j) {
                    if (doneK[j] || AbyssModuleSettings.groupOf(AbyssModuleSettings.bucketOf((JsonElement)vals.get(j)), (JsonElement)vals.get(j), splitStrings) != g) continue;
                    if (!AbyssModuleSettings.applyValue((Setting)ss.get(i), (JsonElement)vals.get(j))) continue block12;
                    ++revalued;
                    continue block12;
}
}
}
        return new int[]{forced, revalued, n2 - forced};
}
    private static boolean optionAllows(Setting s, String v2) {
        if (!(s instanceof ModeSetting)) {
            return true;
}
        try {
            List<String> o2 = ((ModeSetting)s).S();
            if (o2 == null || o2.isEmpty() || o2.contains("UNSET")) {
                return true;
}
            return o2.contains(v2);
}
        catch (Throwable t2) {
            return true;
}
}
    private static int groupOf(int bucket, JsonElement v2, boolean splitStrings) {
        if (bucket != 4 && bucket != 5) {
            return bucket;
}
        if (!splitStrings) {
            return 4;
}
        if (v2 == null) {
            return bucket;
}
        return AbyssModuleSettings.isUpper(v2.getAsString()) ? 4 : 5;
}
    private static int match(boolean[][] adj, int n2, int m2, int[] out, int banI, int banJ) {
        int[] keyOwner = new int[m2];
        for (int j = 0; j < m2; ++j) {
            keyOwner[j] = -1;
}
        for (int i = 0; i < n2; ++i) {
            out[i] = -1;
}
        int size = 0;
        for (int i = 0; i < n2; ++i) {
            if (!AbyssModuleSettings.augment(adj, i, new boolean[m2], keyOwner, m2, banI, banJ)) continue;
            ++size;
}
        for (int j = 0; j < m2; ++j) {
            if (keyOwner[j] < 0) continue;
            out[keyOwner[j]] = j;
}
        return size;
}
    private static boolean augment(boolean[][] adj, int i, boolean[] visited, int[] keyOwner, int m2, int banI, int banJ) {
        for (int j = 0; j < m2; ++j) {
            if (!adj[i][j] || visited[j] || i == banI && j == banJ) continue;
            visited[j] = true;
            if (keyOwner[j] >= 0 && !AbyssModuleSettings.augment(adj, keyOwner[j], visited, keyOwner, m2, banI, banJ)) continue;
            keyOwner[j] = i;
            return true;
}
        return false;
}
    private static boolean applyValue(Setting s, JsonElement e) {
        try {
            JsonPrimitive p = e.getAsJsonPrimitive();
            if (s instanceof BooleanSetting) {
                ((BooleanSetting)s).v(p.getAsBoolean(), 0L);
                return true;
}
            if (s instanceof PercentageSetting) {
                ((PercentageSetting)s).d(p.getAsInt());
                return true;
}
            if (s instanceof NumberSetting) {
                return AbyssModuleSettings.setFloat((NumberSetting)s, p.getAsFloat());
}
            if (s instanceof ColorSetting) {
                ((ColorSetting)s).e(p.getAsString());
                return true;
}
            if (s instanceof ModeSetting) {
                ModeSetting ms = (ModeSetting)s;
                String v2 = p.getAsString();
                List<String> opts = ms.S();
                if (opts != null && !opts.contains(v2)) {
                    if (opts.size() != 1 || !"UNSET".equals(opts.get(0))) {
                        return false;
}
                    opts.add(v2);
                    opts.remove("UNSET");
}
                ms.i(v2);
                return v2.equals(ms.Y());
}
            if (s instanceof TextSetting) {
                ((TextSetting)s).O(p.getAsString());
                return true;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return false;
}
    private static boolean setFloat(NumberSetting s, float v2) {
        Field target = null;
        for (Field f : NumberSetting.class.getDeclaredFields()) {
            if (f.getType() != Float.TYPE || Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers())) continue;
            if (target != null) {
                return false;
}
            target = f;
}
        if (target == null) {
            return false;
}
        try {
            target.setAccessible(true);
            target.setFloat(s, v2);
            return true;
}
        catch (Throwable t2) {
            return false;
}
}
    private static JsonObject configBlock(JsonObject cfg, Module m2) {
        if (cfg == null) {
            return null;
}
        String moduleName = m2.b();
        if (moduleName == null || moduleName.startsWith("?")) {
            return null;
}
        JsonElement e = cfg.get(moduleName);
        return e != null && e.isJsonObject() ? e.getAsJsonObject() : null;
}
    private static String name(Setting s) {
        try {
            return s.B();
}
        catch (Throwable t2) {
            return null;
}
}
    private static List<String> configOrder(JsonObject cfg, Module m2) {
        if (cfg == null) {
            return null;
}
        String moduleName = m2.b();
        if (moduleName == null || moduleName.startsWith("?")) {
            return null;
}
        JsonElement e = cfg.get(moduleName);
        if (e == null || !e.isJsonObject()) {
            return null;
}
        ArrayList<String> order = new ArrayList<String>();
        for (Map.Entry entry : e.getAsJsonObject().entrySet()) {
            if (COMMON.contains(entry.getKey())) continue;
            order.add((String)entry.getKey());
}
        return order.isEmpty() ? null : order;
}
    static {
        COMMON.add("status");
        COMMON.add("keyBind");
        COMMON.add("visible");
        COMMON.add("suffix-visible");
        relabelModule = "?";
        relabelOverwrites = new ArrayList<String>();
}
}