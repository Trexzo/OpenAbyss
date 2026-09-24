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

    public static void O(long var0) {
        long var2 = var0 ^ 0x45C7DC4AED40L;
        try {
            JsonArray var4 = new JsonArray();
            for (Account var6 : Q) {
                var4.add((JsonElement)var6.F(var2));
}
            PrintWriter var9 = new PrintWriter(new FileWriter(i));
            var9.println(J.toJson((JsonElement)var4));
            var9.close();
}
        catch (IOException iOException) {
            // empty catch block
}
}
    public static void Q(int var0, short var1, short var2) {
        Q.clear();
        try {
            JsonElement var7 = new JsonParser().parse((Reader)new BufferedReader(new FileReader(i)));
            if (var7 instanceof JsonArray) {
                for (JsonElement var10 : var7.getAsJsonArray()) {
                    JsonObject var11 = var10.getAsJsonObject();
                    Q.add(Account.k(var11, 0L));
}
}
}
        catch (FileNotFoundException var7) {
}
        catch (JsonSyntaxException var13) {
            System.err.println("Error parsing accounts.json: " + var13.getMessage());
}
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