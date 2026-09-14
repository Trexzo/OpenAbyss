/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package Abyss.internal.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MojangApiClient {
        
    private static ConcurrentHashMap<UUID, String> P;
    

    private MojangApiClient() {
}
    public static void S() {
        P.clear();
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String X(UUID var0, boolean var1) throws Exception, Throwable {
        String var4;
        if (var1) {
            P.remove(var0);
}
        if ((var4 = P.get(var0)) != null) {
            return var4;
}
        HttpURLConnection var5 = null;
        BufferedReader var6 = null;
        try {
            String var9;
            URL var7 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + var0.toString().replace("-", ""));
            var5 = (HttpURLConnection)var7.openConnection();
            var5.setRequestMethod("GET");
            var5.setConnectTimeout(4000);
            var5.setReadTimeout(4000);
            var5.setUseCaches(false);
            if (var5.getResponseCode() != 200) {
                throw new Exception("HTTP " + var5.getResponseCode());
}
            var6 = new BufferedReader(new InputStreamReader(var5.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder var8 = new StringBuilder();
            while ((var9 = var6.readLine()) != null) {
                var8.append(var9);
}
            JsonObject var10 = new JsonParser().parse(var8.toString()).getAsJsonObject();
            if (!var10.has("name")) {
                throw new Exception("Invalid UUID");
}
            String var11 = var10.get("name").getAsString();
            P.put(var0, var11);
            String string = var11;
            return string;
}
        finally {
            if (var6 != null) {
                try {
                    var6.close();
}
                catch (Exception exception) {}
}
            if (var5 != null) {
                var5.disconnect();
}
}
}
    public static String d(int var0, UUID var2) throws Exception, Throwable {
        return MojangApiClient.X(var2, false);
}
    static {
        P = new ConcurrentHashMap();
}
}