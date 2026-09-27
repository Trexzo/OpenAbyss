/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.minecraft.client.Minecraft
 */
package Abyss.internal.restore;

import Abyss.AbyssClient;
import Abyss.module.impl.configuration.Teams;
import Abyss.ui.screen.MainMenuTheme;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;

public final class AbyssCommandData {
    public static final String MENU = "menu.txt";
    public static final String MENU_MUSIC = "menu_music.txt";
    public static final String FRIENDS = "friends.txt";
    public static final String ENEMIES = "enemies.txt";
    public static final String CURRENT = "current.json";
    static final String CHAT_BINDS = "chatBinds";
    public static volatile String lastLoadNote = "not loaded";

    private AbyssCommandData() {
}
    public static File dirFile() {
        File var1;
        String var0 = System.getProperty("abyss.config");
        if (var0 != null && (var1 = new File(var0).getParentFile()) != null) {
            return var1;
}
        try {
            Minecraft var2 = Minecraft.getMinecraft();
            if (var2 != null && var2.mcDataDir != null) {
                return new File(var2.mcDataDir, "Abyss");
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return new File("Abyss");
}
    static Path dir() {
        Path var0 = AbyssCommandData.dirFile().toPath();
        try {
            Files.createDirectories(var0, new FileAttribute[0]);
}
        catch (Throwable throwable) {
            // empty catch block
}
        return var0;
}
    public static Path resolve(String var0) {
        return AbyssCommandData.dir().resolve(var0);
}
    public static boolean exists(String var0) {
        try {
            return Files.isRegularFile(AbyssCommandData.resolve(var0), new LinkOption[0]);
}
        catch (Throwable var2) {
            return false;
}
}
    static String readText(String var0) {
        try {
            Path var1 = AbyssCommandData.resolve(var0);
            if (!Files.isRegularFile(var1, new LinkOption[0])) {
                return null;
}
            return new String(Files.readAllBytes(var1), StandardCharsets.UTF_8);
}
        catch (Throwable var2) {
            return null;
}
}
    static boolean writeText(String var0, String var1) {
        try {
            Files.write(AbyssCommandData.resolve(var0), (var1 == null ? "" : var1).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            return true;
}
        catch (Throwable var3) {
            return false;
}
}
    static List<String> readLines(String var0) {
        ArrayList<String> var1 = new ArrayList<String>();
        String var2 = AbyssCommandData.readText(var0);
        if (var2 != null) {
            String[] var3 = var2.split("\r\n|\r|\n");
            for (int var4 = 0; var4 < var3.length; ++var4) {
                String var5 = var3[var4].trim();
                if (var5.isEmpty()) continue;
                var1.add(var5);
}
}
        return var1;
}
    static boolean writeLines(String var0, Collection<String> var1) {
        StringBuilder var2 = new StringBuilder();
        if (var1 != null) {
            for (String var4 : var1) {
                if (var4 == null || var4.trim().isEmpty()) continue;
                if (var2.length() > 0) {
                    var2.append('\n');
}
                var2.append(var4.trim());
}
}
        return AbyssCommandData.writeText(var0, var2.toString());
}
    private static JsonObject parseJson(Path path) {
        try {
            if (path == null || !Files.isRegularFile(path, new LinkOption[0])) {
                return null;
}
            String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            JsonElement parsed = new JsonParser().parse(text);
            return parsed != null && parsed.isJsonObject() ? parsed.getAsJsonObject() : null;
}
        catch (Throwable ignored) {
            return null;
}
}
    public static JsonObject readJson(String var0) {
        try {
            Path var1 = AbyssCommandData.resolve(var0);
            if (!Files.isRegularFile(var1, new LinkOption[0])) {
                return null;
}
            JsonObject primary = AbyssCommandData.parseJson(var1);
            if (primary != null) {
                return primary;
}
            Path backup = var1.resolveSibling(var0 + ".bak");
            return AbyssCommandData.parseJson(backup);
}
        catch (Throwable var4) {
            return null;
}
}
    static boolean writeJson(String var0, JsonObject var1) {
        Path var2 = null;
        Path var3 = null;
        Path var5 = null;
        boolean rotated = false;
        try {
            var2 = AbyssCommandData.resolve(var0);
            var3 = var2.resolveSibling(var0 + ".tmp");
            var5 = var2.resolveSibling(var0 + ".bak");
            Files.deleteIfExists(var3);
            String var4 = new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)var1);
            Files.write(var3, var4.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            if (Files.isRegularFile(var2, new LinkOption[0])) {
                Files.deleteIfExists(var5);
                Files.move(var2, var5, new CopyOption[0]);
                rotated = true;
}
            try {
                Files.move(var3, var2, new CopyOption[0]);
                return true;
}
            catch (Throwable promoteFailure) {
                if (rotated && !Files.exists(var2, new LinkOption[0]) && Files.isRegularFile(var5, new LinkOption[0])) {
                    try {
                        Files.move(var5, var2, new CopyOption[0]);
}
                    catch (Throwable ignored) {
}
}
                Files.deleteIfExists(var3);
                return false;
}
}
        catch (Throwable var6) {
            try {
                if (rotated && var2 != null && var5 != null
                        && !Files.exists(var2, new LinkOption[0])
                        && Files.isRegularFile(var5, new LinkOption[0])) {
                    Files.move(var5, var2, new CopyOption[0]);
}
}
            catch (Throwable ignored) {
}
            try {
                if (var3 != null) {
                    Files.deleteIfExists(var3);
}
}
            catch (Throwable ignored) {
}
            return false;
}
}
    public static boolean patchCurrent(String var0, JsonElement var1) {
        JsonObject var2 = AbyssCommandData.readJson(CURRENT);
        if (var2 == null) {
            var2 = new JsonObject();
}
        var2.add(var0, var1);
        return AbyssCommandData.writeJson(CURRENT, var2);
}
    static JsonObject currentChild(String var0) {
        JsonObject var1 = AbyssCommandData.readJson(CURRENT);
        if (var1 == null) {
            return null;
}
        JsonElement var2 = var1.get(var0);
        return var2 != null && var2.isJsonObject() ? var2.getAsJsonObject() : null;
}
    public static boolean saveFriends() {
        try {
            return AbyssCommandData.writeLines(FRIENDS, Teams.a());
}
        catch (Throwable var1) {
            return false;
}
}
    public static boolean saveEnemies() {
        try {
            return AbyssCommandData.writeLines(ENEMIES, Teams.B());
}
        catch (Throwable var1) {
            return false;
}
}
    public static boolean saveMenu() {
        try {
            return AbyssCommandData.writeText(MENU, MainMenuTheme.mode.Y());
}
        catch (Throwable var1) {
            return false;
}
}
    public static boolean saveMenuMusic() {
        try {
            return AbyssCommandData.writeText(MENU_MUSIC, String.valueOf(MainMenuTheme.music.c()));
}
        catch (Throwable var1) {
            return false;
}
}
    public static boolean saveChatBinds() {
        try {
            Map<Integer, String> var0 = AbyssClient.H;
            JsonObject var1 = new JsonObject();
            if (var0 != null) {
                for (Map.Entry<Integer, String> var3 : var0.entrySet()) {
                    if (var3.getKey() == null) continue;
                    var1.addProperty(String.valueOf(var3.getKey()), var3.getValue());
}
}
            return AbyssCommandData.patchCurrent(CHAT_BINDS, (JsonElement)var1);
}
        catch (Throwable var4) {
            return false;
}
}
    public static void load() {
        String names = AbyssCommandData.loadNames();
        String menu = AbyssCommandData.loadMenu();
        String binds = AbyssCommandData.loadChatBinds();
        lastLoadNote = names + " | " + menu + " | " + binds;
}
    public static String selfTest() {
        String oldOverride = System.getProperty("abyss.config");
        String oldMode = null;
        boolean oldMusic = true;
        Map<Integer, String> oldBinds = null;
        LinkedHashSet<String> oldFriends = null;
        LinkedHashSet<String> oldEnemies = null;
        File testDir = new File(System.getProperty("java.io.tmpdir"),
                "openabyss-commanddata-selftest-" + System.nanoTime());
        File testCurrent = new File(testDir, CURRENT);
        try {
            oldMode = MainMenuTheme.mode.Y();
            oldMusic = MainMenuTheme.music.c();
            oldBinds = AbyssClient.H == null
                    ? new LinkedHashMap<Integer, String>()
                    : new LinkedHashMap<Integer, String>(AbyssClient.H);
            oldFriends = new LinkedHashSet<String>(Teams.a());
            oldEnemies = new LinkedHashSet<String>(Teams.B());

            if (!testDir.mkdirs() && !testDir.isDirectory()) {
                return "FAIL temp-dir " + testDir;
}
            System.setProperty("abyss.config", testCurrent.getAbsolutePath());

            MainMenuTheme.mode.i("RIDDLE_JOKER");
            MainMenuTheme.music.v(false, 0L);
            if (AbyssClient.H == null) {
                AbyssClient.H = new LinkedHashMap<Integer, String>();
}
            AbyssClient.H.clear();
            AbyssClient.H.put(Integer.valueOf(54), ".help");
            Teams.a().clear();
            Teams.B().clear();
            Teams.E("OpenAbyssFriendFixture");
            Teams.C("OpenAbyssEnemyFixture");

            if (!AbyssCommandData.saveMenu()) {
                return "FAIL save-menu";
}
            if (!AbyssCommandData.saveMenuMusic()) {
                return "FAIL save-menu-music";
}
            if (!AbyssCommandData.saveChatBinds()) {
                return "FAIL save-chat-binds";
}
            if (!AbyssCommandData.saveFriends()) {
                return "FAIL save-friends";
}
            if (!AbyssCommandData.saveEnemies()) {
                return "FAIL save-enemies";
}
            if (!AbyssCommandData.exists(MENU)
                    || !AbyssCommandData.exists(MENU_MUSIC)
                    || !AbyssCommandData.exists(CURRENT)
                    || !AbyssCommandData.exists(FRIENDS)
                    || !AbyssCommandData.exists(ENEMIES)) {
                return "FAIL files-missing";
}

            MainMenuTheme.mode.i("NONE");
            MainMenuTheme.music.v(true, 0L);
            AbyssClient.H.clear();
            Teams.a().clear();
            Teams.B().clear();

            AbyssCommandData.load();

            if (!"RIDDLE_JOKER".equals(MainMenuTheme.mode.Y())) {
                return "FAIL load-menu " + MainMenuTheme.mode.Y();
}
            if (MainMenuTheme.music.c()) {
                return "FAIL load-menu-music true";
}
            if (!".help".equals(AbyssClient.H.get(Integer.valueOf(54)))) {
                return "FAIL load-chat-bind " + String.valueOf(AbyssClient.H);
}
            if (!Teams.a().contains("OpenAbyssFriendFixture")) {
                return "FAIL load-friend " + String.valueOf(Teams.a());
}
            if (!Teams.B().contains("OpenAbyssEnemyFixture")) {
                return "FAIL load-enemy " + String.valueOf(Teams.B());
}

            MainMenuTheme.mode.i("RIDDLE_JOKER");
            MainMenuTheme.music.v(true, 0L);
            if (!AbyssCommandData.writeText(MENU, "__INVALID_THEME__")
                    || !AbyssCommandData.writeText(MENU_MUSIC, "not-a-boolean")) {
                return "FAIL malformed-menu-fixture-write";
}
            String malformedMenu = AbyssCommandData.loadMenu();
            if (!"RIDDLE_JOKER".equals(MainMenuTheme.mode.Y())) {
                return "FAIL malformed-menu-mutated " + MainMenuTheme.mode.Y() + " note=" + malformedMenu;
}
            if (!MainMenuTheme.music.c()) {
                return "FAIL malformed-menu-music-mutated note=" + malformedMenu;
}
            if (malformedMenu.indexOf("INVALID") < 0) {
                return "FAIL malformed-menu-not-reported " + malformedMenu;
}

            String probeName = "__openabyss_commanddata_json_selftest__.json";
            Path probePath = AbyssCommandData.resolve(probeName);
            Path probeBak = probePath.resolveSibling(probeName + ".bak");
            Path probeTmp = probePath.resolveSibling(probeName + ".tmp");
            Files.deleteIfExists(probePath);
            Files.deleteIfExists(probeBak);
            Files.deleteIfExists(probeTmp);
            JsonObject generation1 = new JsonObject();
            generation1.addProperty("generation", Integer.valueOf(1));
            JsonObject generation2 = new JsonObject();
            generation2.addProperty("generation", Integer.valueOf(2));
            if (!AbyssCommandData.writeJson(probeName, generation1)
                    || !AbyssCommandData.writeJson(probeName, generation2)) {
                return "FAIL json-rotation-write";
}
            JsonObject currentProbe = AbyssCommandData.parseJson(probePath);
            JsonObject backupProbe = AbyssCommandData.parseJson(probeBak);
            if (currentProbe == null || currentProbe.get("generation").getAsInt() != 2
                    || backupProbe == null || backupProbe.get("generation").getAsInt() != 1) {
                return "FAIL json-rotation current=" + currentProbe + " backup=" + backupProbe;
}
            Files.write(probePath, "{broken".getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            JsonObject recoveredProbe = AbyssCommandData.readJson(probeName);
            if (recoveredProbe == null || recoveredProbe.get("generation").getAsInt() != 1) {
                return "FAIL json-backup-read " + String.valueOf(recoveredProbe);
}
            Files.deleteIfExists(probePath);
            Files.deleteIfExists(probeBak);
            Files.deleteIfExists(probeTmp);

            return "PASS menu=RIDDLE_JOKER music=false chatBind=54:.help friends=1 enemies=1 malformed-menu-refused json-backup-recovery";
}
        catch (Throwable failure) {
            return "FAIL " + failure.getClass().getName() + ": " + failure.getMessage();
}
        finally {
            try {
                if (oldMode != null) {
                    MainMenuTheme.mode.i(oldMode);
}
                MainMenuTheme.music.v(oldMusic, 0L);
                if (AbyssClient.H == null) {
                    AbyssClient.H = new LinkedHashMap<Integer, String>();
}
                AbyssClient.H.clear();
                if (oldBinds != null) {
                    AbyssClient.H.putAll(oldBinds);
}
                Teams.a().clear();
                Teams.B().clear();
                if (oldFriends != null) {
                    Teams.a().addAll(oldFriends);
}
                if (oldEnemies != null) {
                    Teams.B().addAll(oldEnemies);
}
}
            catch (Throwable ignored) {
}
            if (oldOverride == null) {
                System.clearProperty("abyss.config");
}
            else {
                System.setProperty("abyss.config", oldOverride);
}
            try {
                new File(testDir, MENU).delete();
                new File(testDir, MENU_MUSIC).delete();
                new File(testDir, FRIENDS).delete();
                new File(testDir, ENEMIES).delete();
                new File(testDir, CURRENT).delete();
                new File(testDir, CURRENT + ".bak").delete();
                new File(testDir, CURRENT + ".tmp").delete();
                new File(testDir, "__openabyss_commanddata_json_selftest__.json").delete();
                new File(testDir, "__openabyss_commanddata_json_selftest__.json.bak").delete();
                new File(testDir, "__openabyss_commanddata_json_selftest__.json.tmp").delete();
                testDir.delete();
}
            catch (Throwable ignored) {
}
}
}
    private static String loadNames() {
        int var0 = 0;
        int var1 = 0;
        try {
            List<String> var2 = AbyssCommandData.readLines(FRIENDS);
            for (int var3 = 0; var3 < var2.size(); ++var3) {
                Teams.E(var2.get(var3));
                ++var0;
}
}
        catch (Throwable var4) {
            return "friends.txt FAILED (" + var4 + ")";
}
        try {
            List<String> var5 = AbyssCommandData.readLines(ENEMIES);
            for (int var6 = 0; var6 < var5.size(); ++var6) {
                Teams.C(var5.get(var6));
                ++var1;
}
}
        catch (Throwable var7) {
            return "friends.txt=" + var0 + " enemies.txt FAILED (" + var7 + ")";
}
        return "friends.txt=" + var0 + " enemies.txt=" + var1;
}
    private static String loadMenu() {
        String var0 = AbyssCommandData.readText(MENU);
        String var1 = AbyssCommandData.readText(MENU_MUSIC);
        StringBuilder var2 = new StringBuilder();
        try {
            if (var0 != null && !var0.trim().isEmpty()) {
                String requested = var0.trim();
                String accepted = null;
                List<String> options = MainMenuTheme.mode.S();
                if (options != null) {
                    for (String option : options) {
                        if (option != null && option.equalsIgnoreCase(requested)) {
                            accepted = option;
                            break;
}
}
}
                if (accepted != null) {
                    MainMenuTheme.mode.i(accepted);
                    var2.append("menu.txt=").append(MainMenuTheme.mode.Y());
} else {
                    var2.append("menu.txt INVALID(").append(requested).append(')');
}
            } else {
                var2.append("menu.txt absent");
}
}
        catch (Throwable var4) {
            var2.append("menu.txt FAILED (").append(var4).append(')');
}
        try {
            if (var1 != null && !var1.trim().isEmpty()) {
                String requested = var1.trim();
                if ("true".equalsIgnoreCase(requested) || "false".equalsIgnoreCase(requested)) {
                    MainMenuTheme.music.v(Boolean.parseBoolean(requested), 0L);
                    var2.append(" menu_music.txt=").append(MainMenuTheme.music.c());
} else {
                    var2.append(" menu_music.txt INVALID(").append(requested).append(')');
}
            } else {
                var2.append(" menu_music.txt absent");
}
}
        catch (Throwable var5) {
            var2.append(" menu_music.txt FAILED (").append(var5).append(')');
}
        return var2.toString();
}
    private static String loadChatBinds() {
        try {
            JsonObject var0;
            if (AbyssClient.H == null) {
                AbyssClient.H = new LinkedHashMap<Integer, String>();
}
            if ((var0 = AbyssCommandData.currentChild(CHAT_BINDS)) == null) {
                return "chatBinds absent";
}
            int var1 = 0;
            for (Map.Entry var3 : var0.entrySet()) {
                try {
                    AbyssClient.H.put(Integer.parseInt((String)var3.getKey()), ((JsonElement)var3.getValue()).getAsString());
                    ++var1;
}
                catch (Throwable throwable) {}
}
            return "chatBinds=" + var1;
}
        catch (Throwable var5) {
            return "chatBinds FAILED (" + var5 + ")";
}
}
}