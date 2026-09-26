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
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.command.impl.AbyssCommandBind;
import Abyss.internal.restore.AbyssBootstrap;
import Abyss.internal.restore.AbyssCommandData;
import Abyss.internal.restore.AbyssConfig;
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
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;

public final class AbyssCommandConfig
extends Command {
    private static final String SUFFIX = ".json";

    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"config", "c", "cfg"};
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat("\u00a77Usage:");
        AbyssCommands.chat("\u00a7f  .config folder");
        AbyssCommands.chat("\u00a7f  .config list");
        AbyssCommands.chat("\u00a7f  .config description [description of config]");
        AbyssCommands.chat("\u00a7f  .config save <name>");
        AbyssCommands.chat("\u00a7f  .config load <file>");
        File var3 = AbyssCommandConfig.dir();
        AbyssCommands.chat("\u00a78Directory: " + (var3 == null ? "not found" : var3.getPath()));
        AbyssCommands.chat("\u00a78The stock save/load also take an optional [module... | category... | \"visibility\" | \"suffix\" | \"clickgui\"...] selector; that part is NOT restored -- save and load here always cover every named module.");
}
    private static void folder() {
        File var0 = AbyssCommandConfig.dir();
        if (var0 == null || !var0.isDirectory()) {
            AbyssCommands.chat("\u00a7cThe Abyss config directory does not exist yet.");
            return;
}
        try {
            if (!Desktop.isDesktopSupported()) {
                AbyssCommands.chat("\u00a7cThis JVM has no Desktop support. Path: \u00a7f" + var0.getPath());
                return;
}
            Desktop.getDesktop().open(var0);
            AbyssCommands.chat("\u00a77Opened \u00a7f" + var0.getPath());
}
        catch (Throwable var2) {
            AbyssCommands.chat("\u00a7cCould not open the folder (" + var2 + "). Path: \u00a7f" + var0.getPath());
}
}
    private static void description(String[] var0) {
        JsonObject var1 = AbyssCommandData.readJson("current.json");
        if (var0.length < 2) {
            JsonElement var2 = var1 == null ? null : var1.get("description");
            AbyssCommands.chat("\u00a77Description: \u00a7f" + (var2 == null ? "(none)" : var2.getAsString()));
            return;
}
        StringBuilder var3 = new StringBuilder();
        for (int var4 = 1; var4 < var0.length; ++var4) {
            if (var4 > 1) {
                var3.append(' ');
}
            var3.append(var0[var4]);
}
        if (AbyssCommandData.patchCurrent("description", (JsonElement)new JsonPrimitive(var3.toString()))) {
            AbyssCommands.chat("\u00a7aDescription set to \u00a7f" + var3);
        } else {
            AbyssCommands.chat("\u00a7cCould not write current.json.");
}
}
    @Override
    public void j(String[] var1, long var2) {
        String var4 = var1[0];
        if ("folder".equalsIgnoreCase(var4)) {
            AbyssCommandConfig.folder();
        } else if ("description".equalsIgnoreCase(var4)) {
            AbyssCommandConfig.description(var1);
        } else if ("list".equalsIgnoreCase(var4)) {
            AbyssCommandConfig.list();
        } else if ("load".equalsIgnoreCase(var4)) {
            if (var1.length < 2) {
                AbyssCommands.chat("\u00a7cUsage: \u00a7f.config load <name>");
            } else {
                AbyssCommandConfig.load(var1[1]);
}
        } else if ("save".equalsIgnoreCase(var4)) {
            if (var1.length < 2) {
                AbyssCommands.chat("\u00a7cUsage: \u00a7f.config save <name>");
            } else {
                AbyssCommandConfig.save(var1[1]);
}
        } else {
            this.h(0L);
}
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        ArrayList<String> var5 = new ArrayList<String>();
        if (var2 <= 1) {
            var5.addAll(Arrays.asList("folder", "list", "description", "save", "load"));
        } else if (var2 == 2) {
            var5.addAll(AbyssCommandConfig.configNames());
}
        return var5;
}
    private static void list() {
        List<String> var0 = AbyssCommandConfig.configNames();
        if (var0.isEmpty()) {
            AbyssCommands.chat("\u00a77No config found in " + (AbyssCommandConfig.dir() == null ? "?" : AbyssCommandConfig.dir().getPath()));
            return;
}
        AbyssCommands.chat("\u00a77Configs: \u00a7f" + AbyssCommandConfig.join(var0));
}
    private static List<String> configNames() {
        File[] var2;
        ArrayList<String> var0 = new ArrayList<String>();
        File var1 = AbyssCommandConfig.dir();
        if (var1 != null && (var2 = var1.listFiles()) != null) {
            for (int var3 = 0; var3 < var2.length; ++var3) {
                String var4 = var2[var3].getName();
                if (!var2[var3].isFile() || !var4.toLowerCase().endsWith(SUFFIX)) continue;
                var0.add(var4.substring(0, var4.length() - SUFFIX.length()));
}
}
        return var0;
}
    private static void load(String var0) {
        File var1 = AbyssCommandConfig.resolve(var0);
        if (var1 == null || !var1.isFile()) {
            AbyssCommands.chat("\u00a7cNo config named \u00a7f" + var0 + "\u00a7c. Try \u00a7f.config list");
            return;
}
        JsonObject var2 = AbyssCommandConfig.read(var1);
        if (var2 == null) {
            AbyssCommands.chat("\u00a7cCould not parse " + var1.getName());
            return;
}
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var12 = 0;
        for (Module var8 : ModuleManager.S == null ? new ArrayList<Module>() : ModuleManager.S) {
            if (var8 == null || !AbyssModuleRegistry.isConfigPersistable(var8)) {
                ++var6;
                continue;
}
            JsonElement var9 = var2.get(var8.b());
            if (var9 == null || !var9.isJsonObject()) {
                ++var5;
                continue;
}
            JsonObject var10 = var9.getAsJsonObject();
            if (var10.has("status")) {
                var8.I(20724619369162L, var10.get("status").getAsBoolean());
                ++var3;
}
            if (var10.has("keyBind") && !var8.S() && AbyssCommandConfig.gate()) {
                try {
                    var8.z(118276941480361L, var10.get("keyBind").getAsInt());
                    ++var4;
}
                catch (Throwable throwable) {
                    // empty catch block
}
}
            var12 += AbyssCommandConfig.applySettingValues(var8, var10);
}
        AbyssBootstrap.forceEnableCommandLine();
        AbyssCommands.chat("\u00a7bLoaded \u00a7f" + var1.getName());
}
    private static void save(String var0) {
        File var3;
        String var1 = AbyssCommandConfig.mergeRefusal();
        if (var1 != null) {
            AbyssCommands.chat("\u00a7cRefusing to save: " + var1);
            return;
}
        File var2 = AbyssCommandConfig.resolve(var0);
        if (var2 == null) {
            AbyssCommands.chat("\u00a7cCould not locate the Abyss config directory.");
            return;
}
        File file = var3 = var2.isFile() ? var2 : new File(var2.getParentFile(), "current.json");
        if (!var3.isFile()) {
            AbyssConfig.SaveResult fresh = AbyssConfig.save(var0);
            if (fresh != null && fresh.ok) {
                AbyssCommands.chat("\u00a7aSaved \u00a7f" + new File(fresh.path).getName() + "\u00a7a (created fresh config)");
            } else {
                AbyssCommands.chat("\u00a7cFresh config save failed: " + String.valueOf(fresh));
            }
            return;
}
        JsonObject var4 = AbyssCommandConfig.read(var3);
        if (var4 == null) {
            AbyssCommands.chat("\u00a7cCould not parse the template " + var3.getName() + "; refusing to overwrite.");
            return;
}
        int var5 = 0;
        int var6 = 0;
        int var13 = 0;
        boolean var7 = AbyssCommandConfig.gate();
        for (Module var9 : ModuleManager.S == null ? new ArrayList<Module>() : ModuleManager.S) {
            if (var9 == null || !AbyssModuleRegistry.isConfigPersistable(var9)) continue;
            JsonElement var10 = var4.get(var9.b());
            if (var10 == null || !var10.isJsonObject()) {
                ++var6;
                continue;
}
            JsonObject var11 = var10.getAsJsonObject();
            var11.addProperty("status", Boolean.valueOf(var9.o()));
            if (var7) {
                var11.addProperty("keyBind", (Number)var9.h());
}
            var13 += AbyssCommandConfig.writeSettingValues(var9, var11);
            ++var5;
}
        int var12 = var4.entrySet().size();
        if (!AbyssCommandConfig.write(var2, var4)) {
            AbyssCommands.chat("\u00a7cWrite failed; " + var2.getName() + " was left untouched.");
            return;
}
        AbyssCommands.chat("\u00a7aSaved \u00a7f" + var2.getName());
}
    public static String mergeRefusal() {
        if (ModuleManager.S == null || ModuleManager.S.isEmpty()) {
            return "no module is published (tD.S is empty), so a save would record nothing.";
}
        if (AbyssModuleRegistry.persistableNames().isEmpty()) {
            return "no module has a confirmed name yet; every key would be a placeholder.";
}
        for (Module var0 : ModuleManager.S) {
            if (var0 == null || !AbyssModuleRegistry.isConfigPersistable(var0) || !var0.b().startsWith("?")) continue;
            return "invariant broken: " + var0.b() + " is marked persistable but is a placeholder.";
}
        return null;
}
    private static File dir() {
        File var1;
        File var2;
        String var0 = System.getProperty("abyss.config");
        if (var0 != null && (var2 = (var1 = new File(var0)).getParentFile()) != null && var2.isDirectory()) {
            return var2;
}
        try {
            File var4;
            File var3 = Minecraft.getMinecraft().mcDataDir;
            if (var3 != null && (var4 = new File(var3, "Abyss")).isDirectory()) {
                return var4;
}
}
        catch (Throwable var3) {
            // empty catch block
}
        File var6 = new File("Abyss");
        return var6.isDirectory() ? var6 : null;
}
    private static File resolve(String var0) {
        File var1 = AbyssCommandConfig.dir();
        if (var1 == null || var0 == null || var0.isEmpty()) {
            return null;
}
        if (var0.indexOf(47) >= 0 || var0.indexOf(92) >= 0 || var0.indexOf(58) >= 0 || var0.contains("..")) {
            return null;
}
        String var2 = var0.toLowerCase().endsWith(SUFFIX) ? var0 : var0 + SUFFIX;
        return new File(var1, var2);
}
    private static JsonObject read(File var0) {
        Reader var1 = null;
        try {
            var1 = new InputStreamReader((InputStream)new FileInputStream(var0), "UTF-8");
            JsonElement var2 = new JsonParser().parse(var1);
            return var2 != null && var2.isJsonObject() ? var2.getAsJsonObject() : null;
}
        catch (Throwable var3) {
            return null;
}
        finally {
            if (var1 != null) {
                try {
                    var1.close();
}
                catch (Throwable ignored) {
                    // close failure does not change the read result
}
}
}
}
    private static boolean write(File var0, JsonObject var1) {
        File var2 = new File(var0.getParentFile(), var0.getName() + ".tmp");
        Writer var3 = null;

        try {
            var3 = new OutputStreamWriter(new FileOutputStream(var2), "UTF-8");
            new GsonBuilder().setPrettyPrinting().create().toJson(var1, var3);
            var3.close();
            var3 = null;

            if (var0.isFile()) {
                File var4 = new File(var0.getParentFile(), var0.getName() + ".bak");
                if (var4.isFile()) {
                    var4.delete();
                }
                var0.renameTo(var4);
            }

            return var2.renameTo(var0);
        } catch (Throwable var5) {
            return false;
        } finally {
            if (var3 != null) {
                try {
                    var3.close();
                } catch (Throwable var6) {
                }
            }
        }
    }
    private static boolean gate() {
        return AbyssCommandBind.gateOk();
}
    private static int writeSettingValues(Module var0, JsonObject var1) {
        List<Setting> var2;
        try {
            var2 = var0.w();
}
        catch (Throwable var9) {
            return 0;
}
        if (var2 == null) {
            return 0;
}
        int var3 = 0;
        for (Setting var5 : var2) {
            String var7;
            String var6;
            if (var5 == null) continue;
            try {
                var6 = var5.B();
}
            catch (Throwable var8) {
                continue;
}
            if (var6 == null || var6.length() == 0 || (var7 = var1.has(var6) ? var6 : AbyssConfig.settingKey(var6)) == null || var7.length() == 0) continue;
            try {
                if (var5 instanceof BooleanSetting) {
                    var1.add(var7, (JsonElement)new JsonPrimitive(Boolean.valueOf(((BooleanSetting)var5).c())));
                    ++var3;
                    continue;
}
                if (var5 instanceof PercentageSetting) {
                    var1.add(var7, (JsonElement)new JsonPrimitive((Number)((PercentageSetting)var5).k()));
                    ++var3;
                    continue;
}
                if (var5 instanceof NumberSetting) {
                    float var10 = ((NumberSetting)var5).L();
                    if (Float.isNaN(var10) || Float.isInfinite(var10)) continue;
                    var1.add(var7, (JsonElement)new JsonPrimitive((Number)Float.valueOf(var10)));
                    ++var3;
                    continue;
}
                if (var5 instanceof ColorSetting) {
                    var1.add(var7, (JsonElement)new JsonPrimitive(((ColorSetting)var5).Q()));
                    ++var3;
                    continue;
}
                if (var5 instanceof ModeSetting) {
                    var1.add(var7, (JsonElement)new JsonPrimitive(((ModeSetting)var5).Y()));
                    ++var3;
                    continue;
}
                if (!(var5 instanceof TextSetting)) continue;
                var1.add(var7, (JsonElement)new JsonPrimitive(((TextSetting)var5).X()));
                ++var3;
}
            catch (Throwable throwable) {}
}
        return var3;
}
    private static int applySettingValues(Module var0, JsonObject var1) {
        List<Setting> var2;
        try {
            var2 = var0.w();
}
        catch (Throwable var11) {
            return 0;
}
        if (var2 == null) {
            return 0;
}
        int var3 = 0;
        for (Setting var5 : var2) {
            JsonElement var8;
            String var7;
            String var6;
            if (var5 == null) continue;
            try {
                var6 = var5.B();
}
            catch (Throwable var10) {
                continue;
}
            if (var6 == null || var6.length() == 0 || (var7 = var1.has(var6) ? var6 : AbyssConfig.settingKey(var6)) == null || !var1.has(var7) || (var8 = var1.get(var7)) == null || !var8.isJsonPrimitive()) continue;
            JsonPrimitive var9 = var8.getAsJsonPrimitive();
            try {
                if (var5 instanceof BooleanSetting) {
                    if (!var9.isBoolean()) continue;
                    ((BooleanSetting)var5).v(var9.getAsBoolean(), 0L);
                    ++var3;
                    continue;
}
                if (var5 instanceof PercentageSetting) {
                    if (!var9.isNumber()) continue;
                    ((PercentageSetting)var5).d(var9.getAsInt());
                    ++var3;
                    continue;
}
                if (var5 instanceof NumberSetting) {
                    if (!var9.isNumber()) continue;
                    NumberSetting var12 = (NumberSetting)var5;
                    float var13 = var9.getAsFloat();
                    if (Float.isNaN(var13) || Float.isInfinite(var13) || !(var13 >= var12.i()) || !(var13 <= var12.F())) continue;
                    var12.o((byte)0, 0L, var13);
                    ++var3;
                    continue;
}
                if (var5 instanceof ColorSetting) {
                    if (!var9.isString() || !AbyssCommandConfig.isHex6(var9.getAsString())) continue;
                    ((ColorSetting)var5).e(var9.getAsString());
                    ++var3;
                    continue;
}
                if (var5 instanceof ModeSetting) {
                    if (!var9.isString()) continue;
                    ModeSetting var14 = (ModeSetting)var5;
                    List<String> var15 = var14.S();
                    String var16 = var9.getAsString();
                    if (var15 == null || !var15.contains(var16) && !var15.contains(var16.toUpperCase())) continue;
                    var14.i(var16);
                    ++var3;
                    continue;
}
                if (!(var5 instanceof TextSetting) || !var9.isString()) continue;
                ((TextSetting)var5).O(var9.getAsString());
                ++var3;
}
            catch (Throwable throwable) {}
}
        return var3;
}
    private static boolean isHex6(String var0) {
        if (var0 == null || var0.length() != 6) {
            return false;
}
        for (int var1 = 0; var1 < 6; ++var1) {
            char var2 = var0.charAt(var1);
            if (var2 >= '0' && var2 <= '9' || var2 >= 'a' && var2 <= 'f' || var2 >= 'A' && var2 <= 'F') continue;
            return false;
}
        return true;
}
    private static String join(List<String> var0) {
        StringBuilder var1 = new StringBuilder();
        for (int var2 = 0; var2 < var0.size(); ++var2) {
            if (var2 > 0) {
                var1.append(", ");
}
            var1.append(var0.get(var2));
}
        return var1.toString();
}
}