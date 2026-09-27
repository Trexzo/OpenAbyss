/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package Abyss.internal.auth;

import Abyss.AbyssClient;
import Abyss.enums.AccountType;
import Abyss.internal.auth.Account;
import Abyss.internal.auth.CookieAuthService;
import Abyss.internal.auth.TrustAllSslContext;
import Abyss.ui.screen.CookieLoginScreen;
import Abyss.ui.screen.ReconnectHandler;
import Abyss.util.MinecraftRef;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class AltManager {
    private static long a;

    public static ArrayList<Account> Q;
    private static File i;
    private static Gson J;
    
    private static boolean I;
        private static long[] e;
    private static Minecraft X;
    public static volatile String lastPersistenceNote = "NOT_RUN";

    public static void O(long var0) {
        if (AltManager.writeAccounts(i, Q)) {
            lastPersistenceNote = "PASS path=" + i.getAbsolutePath() + " accounts=" + Q.size();
        } else {
            lastPersistenceNote = "FAIL path=" + i.getAbsolutePath();
            System.err.println("[AltManager] Failed to persist accounts.json: " + i.getAbsolutePath());
}
}
    private static boolean writeAccounts(File target, Iterable<Account> accounts) {
        if (target == null || accounts == null) {
            return false;
}
        File parent = target.getParentFile();
        if (parent != null && !parent.isDirectory() && !parent.mkdirs()) {
            return false;
}
        File tmp = new File(target.getAbsolutePath() + ".tmp");
        try {
            JsonArray array = new JsonArray();
            for (Account account : accounts) {
                if (account != null) {
                    array.add((JsonElement)account.F(0L));
}
}
            try (PrintWriter writer = new PrintWriter(new FileWriter(tmp))) {
                writer.println(J.toJson((JsonElement)array));
}
            try {
                Files.move(tmp.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
}
            catch (IOException atomicFailure) {
                Files.move(tmp.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
}
            return target.isFile();
}
        catch (Throwable failure) {
            if (tmp.isFile()) {
                tmp.delete();
}
            return false;
}
}
    private static ArrayList<Account> readAccounts(File source) {
        ArrayList<Account> out = new ArrayList<Account>();
        if (source == null || !source.isFile()) {
            return out;
}
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            JsonElement root = new JsonParser().parse((Reader)reader);
            if (root instanceof JsonArray) {
                for (JsonElement element : root.getAsJsonArray()) {
                    if (element != null && element.isJsonObject()) {
                        out.add(Account.k(element.getAsJsonObject(), 0L));
}
}
}
}
        catch (Throwable failure) {
            return new ArrayList<Account>();
}
        return out;
}
    public static void Q(int var0, short var1, short var2) {
        Q.clear();
        Q.addAll(AltManager.readAccounts(i));
}
    public static void e(short var0, long var1, String var3) {
        long var4 = ((long)var0 << 48 | 0x45D5706E51D9L) ^ a;
        long var6 = var4 ^ 0x4C3E910D10C3L;
        Optional<Account> var8 = Q.stream().filter(var1x -> var1x.h().equalsIgnoreCase(var3) && var1x.v() == AccountType.OFFLINE).findFirst();
        if (!var8.isPresent()) {
            Q.add(new Account("", "accessToken", var3, "", 0L, AccountType.OFFLINE));
            AltManager.O(var6);
}
}
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void M(long var0) {
        if (!I) {
            TrustAllSslContext.j();
            if (!i.exists()) {
                try {
                    if ((i.getParentFile().exists() || i.getParentFile().mkdirs()) && !i.createNewFile()) {
                        // empty if block
}
}
                catch (IOException iOException) {
                    // empty catch block
}
}
            if (AbyssClient.w != null) {
                AbyssClient.w.s(new ReconnectHandler(), 25046058167973L);
                I = true;
}
}
}
    public static String selfTest() {
        File probe = null;
        try {
            probe = File.createTempFile("openabyss-accounts-", ".json");
            if (!probe.delete()) {
                return "FAIL temp-delete";
}
            ArrayList<Account> sample = new ArrayList<Account>();
            sample.add(new Account("", "access-a", "OfflineProbe", "", 0L, AccountType.OFFLINE));
            sample.add(new Account("refresh-b", "access-b", "MicrosoftProbe", "0123456789abcdef", 42L, AccountType.MINECRAFT));
            if (!AltManager.writeAccounts(probe, sample)) {
                return "FAIL write";
}
            ArrayList<Account> restored = AltManager.readAccounts(probe);
            if (restored.size() != 2) {
                return "FAIL count=" + restored.size();
}
            Account first = restored.get(0);
            Account second = restored.get(1);
            if (!"OfflineProbe".equals(first.h()) || first.v() != AccountType.OFFLINE) {
                return "FAIL offline-roundtrip";
}
            if (!"MicrosoftProbe".equals(second.h()) || !"refresh-b".equals(second.d())
                    || !"access-b".equals(second.Y()) || second.F() != 42L
                    || second.v() != AccountType.MINECRAFT) {
                return "FAIL minecraft-roundtrip";
}
            return "PASS file-roundtrip";
}
        catch (Throwable failure) {
            return "FAIL " + failure.getClass().getName() + ": " + String.valueOf(failure.getMessage());
}
        finally {
            if (probe != null && probe.isFile()) {
                probe.delete();
}
}
}
    public static boolean isInitialized() {
        return I;
}
    public static void h(int var0, short var1, File var2, char var3, GuiScreen var4) {
        long var5 = ((long)var0 << 32 | (long)var1 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x56B35D472FC1L;
        CookieLoginScreen var9 = new CookieLoginScreen(var4, var7);
        CookieAuthService.C(var2, var9);
}
    static {
        a = 94244023323350L;
        X = MinecraftRef.c((byte)0, 0L);
        i = new File(AltManager.X.mcDataDir, "accounts.json");
        J = new GsonBuilder().setPrettyPrinting().create();
        Q = new ArrayList();
        I = false;
}
}