/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonPrimitive
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.http.HttpEntity
 *  org.apache.http.client.entity.UrlEncodedFormEntity
 *  org.apache.http.client.methods.CloseableHttpResponse
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.conn.socket.LayeredConnectionSocketFactory
 *  org.apache.http.conn.ssl.BrowserCompatHostnameVerifier
 *  org.apache.http.conn.ssl.SSLConnectionSocketFactory
 *  org.apache.http.conn.ssl.X509HostnameVerifier
 *  org.apache.http.entity.StringEntity
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.apache.http.impl.client.HttpClients
 *  org.apache.http.message.BasicNameValuePair
 *  org.apache.http.util.EntityUtils
 */
package Abyss.internal.auth;

import Abyss.enums.AccountType;
import Abyss.internal.auth.Account;
import Abyss.internal.auth.AuthService;
import Abyss.internal.auth.TimedStatusMessage;
import Abyss.internal.auth.TrustAllSslContext;
import Abyss.ui.screen.AccountManagerScreen;
import Abyss.util.ChatFormatting;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import javax.net.ssl.SSLSocketFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public final class AccountLookupService {
    private static String i;
    private static Map<Long, String> j;
    private static long[] e;
    
    private static String C;
    private static Map g;
    
    private static CompletableFuture<String> g(String var0, String var1, Executor var2) {
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient var9 = AccountLookupService.f();){
                AccountManagerScreen.q = new TimedStatusMessage(ChatFormatting.y("&7Acquiring Minecraft access token..."), 5000L);
                HttpPost var11 = new HttpPost(URI.create("https://api.minecraftservices.com/authentication/login_with_xbox"));
                var11.setConfig(AuthService.e);
                var11.setHeader("Content-Type", "application/json");
                var11.setEntity((HttpEntity)new StringEntity(String.format("{\"identityToken\":\"XBL3.0 x=%s;%s\"}", var1, var0)));
                CloseableHttpResponse var12 = var9.execute((HttpUriRequest)var11);
                JsonObject var13 = new JsonParser().parse(EntityUtils.toString((HttpEntity)var12.getEntity())).getAsJsonObject();
                String string = Optional.ofNullable(var13.get("access_token")).map(JsonElement::getAsString).filter(var0xx -> !StringUtils.isBlank((CharSequence)var0xx)).orElseThrow(() -> new Exception(var13.has("error") ? var13.get("error").getAsString() + ": " + var13.get("errorMessage").getAsString() : "Minecraft access token missing from response."));
                return string;
}
            catch (InterruptedException var27) {
                throw new CancellationException("Minecraft access token acquisition was cancelled.");
}
            catch (Exception var28) {
                throw new CompletionException("Unable to acquire Minecraft access token.", var28);
}
        }, var2);
}
    private static CompletableFuture<Map<String, String>> C(String var0, Executor var1) {
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient var8 = AccountLookupService.f();){
                AccountManagerScreen.q = new TimedStatusMessage(ChatFormatting.y("&7Acquiring Xbox access token..."), 5000L);
                JsonObject var10 = new JsonObject();
                JsonObject var11 = new JsonObject();
                var11.addProperty("AuthMethod", "RPS");
                var11.addProperty("SiteName", "user.auth.xboxlive.com");
                var11.addProperty("RpsTicket", "t=" + var0);
                var10.add("Properties", (JsonElement)var11);
                var10.addProperty("RelyingParty", "http://auth.xboxlive.com");
                var10.addProperty("TokenType", "JWT");
                HttpPost var12 = new HttpPost(URI.create("https://user.auth.xboxlive.com/user/authenticate"));
                var12.setConfig(AuthService.e);
                var12.setHeader("Content-Type", "application/json");
                var12.setEntity((HttpEntity)new StringEntity(var10.toString()));
                CloseableHttpResponse var13 = var8.execute((HttpUriRequest)var12);
                JsonObject var14 = new JsonParser().parse(EntityUtils.toString((HttpEntity)var13.getEntity())).getAsJsonObject();
                String var15 = Optional.ofNullable(var14.get("Token")).map(JsonElement::getAsString).filter(var0xx -> !StringUtils.isBlank((CharSequence)var0xx)).orElseThrow(() -> new Exception("Xbox Live token missing from response."));
                String var16 = var14.get("DisplayClaims").getAsJsonObject().get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString();
                HashMap<String, String> var17 = new HashMap<String, String>();
                var17.put("Token", var15);
                var17.put("uhs", var16);
                HashMap<String, String> hashMap = var17;
                return hashMap;
}
            catch (InterruptedException var31) {
                throw new CancellationException("Xbox Live token acquisition was cancelled.");
}
            catch (Exception var32) {
                throw new CompletionException("Unable to acquire Xbox Live token.", var32);
}
        }, var1);
}
    public static CompletableFuture<Account> Y(String var0, Executor var1) {
        return AccountLookupService.m(var0, var1).thenComposeAsync(var1x -> AccountLookupService.C((String)var1x.get("access_token"), var1).thenComposeAsync(var2x -> AccountLookupService.w((String)var2x.get("Token"), var1).thenComposeAsync(var3x -> AccountLookupService.g((String)var3x.get("Token"), (String)var2x.get("uhs"), var1).thenComposeAsync(var2xxx -> AuthService.i(var2xxx, var1).thenApply(var2xxxx -> new Account((String)var1x.get("refresh_token"), (String)var2xxx, var2xxxx.getUsername(), var2xxxx.getPlayerID(), 0L, AccountType.MINECRAFT))))), var1);
}
    private static CompletableFuture<Map<String, String>> m(String var0, Executor var1) {
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient var5 = AccountLookupService.f();){
                HttpPost var7 = new HttpPost(URI.create("https://login.live.com/oauth20_token.srf"));
                var7.setConfig(AuthService.e);
                var7.setHeader("Content-Type", "application/x-www-form-urlencoded");
                var7.setEntity((HttpEntity)new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("client_id", "00000000402b5328"), new BasicNameValuePair("grant_type", "refresh_token"), new BasicNameValuePair("redirect_uri", "https://login.live.com/oauth20_desktop.srf"), new BasicNameValuePair("refresh_token", var0), new BasicNameValuePair("scope", "service::user.auth.xboxlive.com::MBI_SSL")), "UTF-8"));
                CloseableHttpResponse var8 = var5.execute((HttpUriRequest)var7);
                JsonObject var9 = new JsonParser().parse(EntityUtils.toString((HttpEntity)var8.getEntity())).getAsJsonObject();
                if (var9.has("error")) {
                    String var28 = var9.get("error").getAsString();
                    String var29 = var9.has("error_description") ? var9.get("error_description").getAsString() : var28;
                    throw new Exception(var28 + ": " + var29);
}
                String var10 = Optional.ofNullable(var9.get("access_token")).map(JsonElement::getAsString).filter(var0xx -> !StringUtils.isBlank((CharSequence)var0xx)).orElseThrow(() -> {
                    long var0xx = 119779331596804L;
                    return new Exception("Microsoft access token missing from refresh response.");
                });
                String var11 = Optional.ofNullable(var9.get("refresh_token")).map(JsonElement::getAsString).filter(var0xx -> !StringUtils.isBlank((CharSequence)var0xx)).orElse(var0);
                HashMap<String, String> var12 = new HashMap<String, String>();
                var12.put("access_token", var10);
                var12.put("refresh_token", var11);
                HashMap<String, String> hashMap = var12;
                return hashMap;
}
            catch (InterruptedException var26) {
                throw new CancellationException("Refresh token exchange was cancelled.");
}
            catch (Exception var27) {
                throw new CompletionException("Unable to refresh Microsoft OAuth token.", var27);
}
        }, var1);
}
    private AccountLookupService() {
}
    private static CompletableFuture<Map<String, String>> w(String var0, Executor var1) {
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient var8 = AccountLookupService.f();){
                AccountManagerScreen.q = new TimedStatusMessage(ChatFormatting.y("&7Acquiring Xbox XSTS token..."), 5000L);
                JsonObject var10 = new JsonObject();
                JsonObject var11 = new JsonObject();
                JsonArray var12 = new JsonArray();
                var12.add((JsonElement)new JsonPrimitive(var0));
                var11.addProperty("SandboxId", "RETAIL");
                var11.add("UserTokens", (JsonElement)var12);
                var10.add("Properties", (JsonElement)var11);
                var10.addProperty("RelyingParty", "rp://api.minecraftservices.com/");
                var10.addProperty("TokenType", "JWT");
                HttpPost var13 = new HttpPost(URI.create("https://xsts.auth.xboxlive.com/xsts/authorize"));
                var13.setConfig(AuthService.e);
                var13.setHeader("Content-Type", "application/json");
                var13.setEntity((HttpEntity)new StringEntity(var10.toString()));
                CloseableHttpResponse var14 = var8.execute((HttpUriRequest)var13);
                JsonObject var15 = new JsonParser().parse(EntityUtils.toString((HttpEntity)var14.getEntity())).getAsJsonObject();
                if (var15.has("XErr")) {
                    long var33 = var15.get("XErr").getAsLong();
                    String var18 = j.containsKey(var33) ? j.get(var33) : "Unknown Xbox error (" + var33 + ")";
                    throw new Exception(var18);
}
                String var16 = Optional.ofNullable(var15.get("Token")).map(JsonElement::getAsString).filter(var0xx -> !StringUtils.isBlank((CharSequence)var0xx)).orElseThrow(() -> new Exception("XSTS token missing from response."));
                HashMap<String, String> var17 = new HashMap<String, String>();
                var17.put("Token", var16);
                HashMap<String, String> hashMap = var17;
                return hashMap;
}
            catch (InterruptedException var31) {
                throw new CancellationException("XSTS token acquisition was cancelled.");
}
            catch (Exception var32) {
                throw new CompletionException("Unable to acquire XSTS token.", var32);
}
        }, var1);
}
    private static CloseableHttpClient f() {
        try {
            SSLSocketFactory var2 = TrustAllSslContext.j().getSocketFactory();
            SSLConnectionSocketFactory var3 = new SSLConnectionSocketFactory(var2, new String[]{"TLSv1.2"}, null, (X509HostnameVerifier)new BrowserCompatHostnameVerifier());
            return HttpClientBuilder.create().setSSLSocketFactory((LayeredConnectionSocketFactory)var3).build();
}
        catch (Exception var4) {
            return HttpClients.createDefault();
}
}
    static {
        j = new HashMap<Long, String>();
        j.put(2148916227L, "The account is banned from Xbox");
        j.put(2148916233L, "The account doesn't have an Xbox account (never signed in)");
        j.put(2148916235L, "The account is from a country where Xbox Live is not available/banned");
        j.put(2148916236L, "The account needs adult verification on Xbox page. (South Korea)");
        j.put(2148916237L, "The account needs adult verification on Xbox page. (South Korea)");
        j.put(2148916238L, "The account is a child (under 18) and cannot proceed unless the account is added to a Family by an adult");
        j.put(2148916262L, "Unknown error");
}
}