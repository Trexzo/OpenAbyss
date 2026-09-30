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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public final class AbyssSettingStatics {
    private static final List<String> COMMON = new ArrayList<String>();
    private static int totalCalls;
    private static int totalBuilt;
    private static int totalSkipped;
    private static int totalFailed;

    private AbyssSettingStatics() {
}
    public static String apply(List<String> pending) {
        String note;
        int built = 0;
        int skipped = 0;
        int failed = 0;
        int modules = 0;
        ArrayList<String> notes = new ArrayList<String>();
        try {
            JsonObject cfg = AbyssConfig.read();
            for (Module m2 : ModuleManager.S) {
                if (m2 == null) continue;
                ++modules;
                int[] r2 = AbyssSettingStatics.fillFor(m2, cfg, notes);
                built += r2[0];
                skipped += r2[1];
                failed += r2[2];
}
            note = "Abyss.statics built " + built + " Setting objects into null statics of " + modules + " modules (skipped " + skipped + " with no carrier-free constructor -- the Abyss/setting/settings/HeaderSetting group, 0 read sites and absent from every config block; " + failed + " failed); ALL fillFor call sites: calls " + totalCalls + " built " + totalBuilt + " skipped " + totalSkipped + " failed " + totalFailed;
}
        catch (Throwable t2) {
            note = "Abyss.statics FAILED (" + t2 + ") -- the modules that read a null Setting static will still throw NullPointerException";
}
        pending.add(note);
        pending.addAll(notes);
        return note;
}
    public static int[] fillFor(Module m2, JsonObject cfg, List<String> notes) {
        int built = 0;
        int skipped = 0;
        int failed = 0;
        ++totalCalls;
        JsonObject block = AbyssSettingStatics.configBlock(cfg, m2);
        Envelope env = new Envelope(block);
        ArrayList<String> unvalued = new ArrayList<String>();
        for (Class<?> k = m2.getClass(); k != null && Module.class.isAssignableFrom(k); k = k.getSuperclass()) {
            for (Field f : k.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) || !Setting.class.isAssignableFrom(f.getType())) {
                    continue;
}
                try {
                    f.setAccessible(true);
                    if (f.get(null) != null) {
                        continue;
}
                    Setting s = AbyssSettingStatics.build(f.getType(), f.getName(), env, unvalued);
                    if (s == null) {
                        ++skipped;
                        continue;
}
                    f.set(null, s);
                    ++built;
}
                catch (Throwable t2) {
                    ++failed;
}
}
}
        if (!unvalued.isEmpty() && notes != null) {
            notes.add("Abyss.statics " + m2.b() + ": " + unvalued.size() + " numeric setting(s) seeded with this module's own minimum config value " + env.describe() + ", no factory value being recoverable; relabel overwrites the forced ones: " + unvalued);
}
        totalBuilt += built;
        totalSkipped += skipped;
        totalFailed += failed;
        return new int[]{built, skipped, failed};
}
    public static boolean buildable(Class<?> type) {
        return BooleanSetting.class.isAssignableFrom(type) || PercentageSetting.class.isAssignableFrom(type) || NumberSetting.class.isAssignableFrom(type) || ModeSetting.class.isAssignableFrom(type) || ColorSetting.class.isAssignableFrom(type) || TextSetting.class.isAssignableFrom(type);
}
    private static Setting build(Class<?> type, String fieldName, Envelope env, List<String> unvalued) throws Exception {
        if (!AbyssSettingStatics.buildable(type)) {
            return null;
}
        if (BooleanSetting.class.isAssignableFrom(type)) {
            return (Setting)AbyssSettingStatics.ctor(type, String.class, Boolean.TYPE).newInstance(fieldName, Boolean.FALSE);
}
        if (PercentageSetting.class.isAssignableFrom(type)) {
            int v2 = env.minInt();
            if (env.hasInts()) {
                unvalued.add(fieldName);
}
            return (Setting)AbyssSettingStatics.ctor(type, String.class, Integer.TYPE).newInstance(fieldName, v2);
}
        if (NumberSetting.class.isAssignableFrom(type)) {
            return AbyssSettingStatics.numberFor(type, fieldName, env, unvalued);
}
        if (ModeSetting.class.isAssignableFrom(type)) {
            return (Setting)AbyssSettingStatics.ctor(type, String.class, String[].class).newInstance(fieldName, AbyssSettingStatics.optionsFor(fieldName, env));
}
        if (ColorSetting.class.isAssignableFrom(type)) {
            return (Setting)AbyssSettingStatics.ctor(type, String.class, String.class).newInstance(fieldName, env.colour());
}
        if (TextSetting.class.isAssignableFrom(type)) {
            return (Setting)AbyssSettingStatics.ctor(type, String.class, String.class).newInstance(fieldName, "");
}
        return null;
}
    private static Setting numberFor(Class<?> type, String fieldName, Envelope env, List<String> unvalued) throws Exception {
        float v2 = env.minFloat();
        if (env.hasFloats()) {
            unvalued.add(fieldName);
}
        float hi = env.high();
        float lo = env.low();
        float step = env.step();
        return (Setting)AbyssSettingStatics.ctor(type, String.class, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE).newInstance(fieldName, Float.valueOf(v2), Float.valueOf(lo), Float.valueOf(hi), Float.valueOf(step));
}
    private static String[] optionsFor(String fieldName, Envelope env) {
        LinkedHashSet<String> out = new LinkedHashSet<String>();
        out.addAll(env.strings());
        if (out.isEmpty()) {
            out.add(fieldName);
}
        return out.toArray(new String[out.size()]);
}
    private static Constructor<?> ctor(Class<?> type, Class<?> ... sig) throws Exception {
        Constructor<?> c = type.getDeclaredConstructor(sig);
        c.setAccessible(true);
        return c;
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
    static {
        COMMON.add("status");
        COMMON.add("keyBind");
        COMMON.add("visible");
        COMMON.add("suffix-visible");
}
    private static final class Envelope {
        private final List<Float> floats = new ArrayList<Float>();
        private final List<Integer> ints = new ArrayList<Integer>();
        private final List<String> strings = new ArrayList<String>();
        private String colour;

        Envelope(JsonObject block) {
            if (block == null) {
                return;
}
            for (Map.Entry en : block.entrySet()) {
                if (COMMON.contains(en.getKey()) || !((JsonElement)en.getValue()).isJsonPrimitive()) continue;
                JsonPrimitive p = ((JsonElement)en.getValue()).getAsJsonPrimitive();
                if (p.isNumber()) {
                    String raw = p.getAsString();
                    if (raw.indexOf(46) >= 0 || raw.indexOf(101) >= 0 || raw.indexOf(69) >= 0) {
                        this.floats.add(Float.valueOf(p.getAsFloat()));
                        continue;
}
                    this.ints.add(p.getAsInt());
                    continue;
}
                if (!p.isString()) continue;
                String s = p.getAsString();
                if (AbyssSettingStatics.isHex6(s)) {
                    if (this.colour != null) continue;
                    this.colour = s;
                    continue;
}
                this.strings.add(s);
}
}
        boolean hasFloats() {
            return !this.floats.isEmpty();
}
        boolean hasInts() {
            return !this.ints.isEmpty();
}
        List<String> strings() {
            return this.strings;
}
        String colour() {
            return this.colour == null ? "FFFFFF" : this.colour;
}
        float minFloat() {
            if (this.floats.isEmpty()) {
                return 0.0f;
}
            float r2 = this.floats.get(0).floatValue();
            for (Float f : this.floats) {
                if (!(f.floatValue() < r2)) continue;
                r2 = f.floatValue();
}
            return r2;
}
        int minInt() {
            if (this.ints.isEmpty()) {
                return 0;
}
            int r2 = this.ints.get(0);
            for (Integer i : this.ints) {
                if (i >= r2) continue;
                r2 = i;
}
            return r2;
}
        float high() {
            float a = 1.0f;
            for (Float f : this.floats) {
                float x = Math.abs(f.floatValue());
                if (!(x > a)) continue;
                a = x;
}
            return 2.0f * a;
}
        float low() {
            for (Float f : this.floats) {
                if (!(f.floatValue() < 0.0f)) continue;
                return -this.high();
}
            return 0.0f;
}
        float step() {
            float span = this.high() - this.low();
            float want = span / 200.0f;
            float smallest = 0.0f;
            for (Float f : this.floats) {
                float x = Math.abs(f.floatValue());
                if (!(x > 0.0f) || smallest != 0.0f && !(x < smallest)) continue;
                smallest = x;
}
            if (smallest > 0.0f && smallest / 10.0f < want) {
                want = smallest / 10.0f;
}
            return Envelope.nice(want);
}
        private static float nice(float x) {
            if (x <= 0.001f) {
                return 0.001f;
}
            float p = 0.001f;
            while (p * 10.0f <= x) {
                p *= 10.0f;
}
            if (p * 5.0f <= x) {
                return p * 5.0f;
}
            if (p * 2.0f <= x) {
                return p * 2.0f;
}
            return p;
}
        String describe() {
            return "(range " + this.low() + ".." + this.high() + " step " + this.step() + ")";
}
}
}