/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package Abyss.command.impl;

import Abyss.command.Command;
import Abyss.enums.DetectedCheat;
import Abyss.internal.CheaterDetector;
import Abyss.util.CheaterRegistry;
import Abyss.util.ClientUtil;
import Abyss.util.MinecraftRef;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class StockCommandCheaters
extends Command {
    private static Minecraft p = MinecraftRef.c((byte)0, 0L);
    private static String[] a;
    private static Map c;
    
    

    @Override
    public void j(String[] var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var2 ^ 0x3A45CD136DBDL;
        LinkedHashMap<UUID, CheaterRegistry> var6 = new LinkedHashMap<UUID, CheaterRegistry>();
        for (Map.Entry<UUID, EntityPlayer> entry : CheaterDetector.c.entrySet()) {
            CheaterRegistry var9;
            if (StockCommandCheaters.p.field_71441_e.func_152378_a(entry.getKey()) == null || (var9 = CheaterDetector.R.get(entry.getKey())) == null || !var9.M()) continue;
            var6.put(entry.getKey(), var9);
}
        if (var6.isEmpty()) {
            ClientUtil.t(var4, StockCommandCheaters.a(30577, 0x3A7D4509A5BBB00L ^ var2));
        } else {
            ClientUtil.t(var4, StockCommandCheaters.a(14757, 0x5340CFDB5ABF5D6L ^ var2));
            for (Map.Entry<UUID, Object> entry : var6.entrySet()) {
                EntityPlayer var16 = StockCommandCheaters.p.field_71441_e.func_152378_a(entry.getKey());
                ArrayList<String> var10 = new ArrayList<String>();
                for (Map.Entry<DetectedCheat, Boolean> var12 : ((CheaterRegistry)entry.getValue()).e.entrySet()) {
                    if (!var12.getValue().booleanValue()) continue;
                    DetectedCheat var13 = var12.getKey();
                    var10.add(var13.colorFormatCode + var13.name());
}
                ClientUtil.t(var4, StockCommandCheaters.a(1313, 0x5A62CA97ECC0C95CL ^ var2) + var16.func_145748_c_().func_150254_d() + StockCommandCheaters.a(397, 0x103EFF62D041CDF3L ^ var2) + String.join((CharSequence)StockCommandCheaters.a(10075, 0x4B9C8B3905A16B2BL ^ var2), var10));
}
}
}
    @Override
    public void h(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var3 = var1 ^ 0x42DDAA8EF4E6L;
        ClientUtil.t(var3, StockCommandCheaters.a(30904, 0x5A8D3125DF75AD9CL ^ var1));
        ClientUtil.t(var3, StockCommandCheaters.a(17697, 0x6B2338ECDAF39000L ^ var1));
        ClientUtil.b("");
        ClientUtil.b(StockCommandCheaters.a(31247, 0x263918500EADAF2FL ^ var1));
}
    @Override
    public String[] e(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return new String[]{StockCommandCheaters.a(3587, 0x21F2A3344EFA0B3BL ^ var1), StockCommandCheaters.a(15674, 0x575D34B151353807L ^ var1), StockCommandCheaters.a(11990, 0x35721C35B1A72BEAL ^ var1)};
}
    @Override
    public boolean J() {
        return false;
}
    static {
        c = new HashMap(13);
        a = new String[]{"\u00a9F\u00c5\\+\u00a8M\u0095", "C\u00b9c\u00874\u001fD\u0000]U\u00f5]3OKs", "K\u00e7-\u00e1\u00d7\u00f3\u00d3\u001a\u00ba\u00f36\u00eb3#\u00f5\u00a0", "<\u00f3xk6Y\u0091\u00d2", "N\u0005AN:7\u008c\u00cf\u0012\u00d6\u00d8$$\u00b7_\u0091", "\u00ba\u00ce\u009dl\u00beS.O\u00ee[\t\u0096h\f\u00a3\u0088", "0\u001f\u00be2?9\"*\u00c784N\u00d5A\u00deV\u00f5d\u00e5R\u0017\u0003\u009c\u0096\u00eb\u00cf1\u00f9\u00f7(\u0083H\u00aa\u00c6\u0017\u00c9l\u00d8\u001c\u00ff\u00f0;r6\u0006\u008b\u0014\u0096", "s1\u0011\u00bf,\u00913G_\u00b3\u00c0\u00b5\u00f8\u009c\u0096&", "3b\u0089F\u00c8w\u00c5T\u00c7\u00e2\u00c33\u00a8e\u0016\u001e\u0082\u0083\u00e6'\u00a5\u00f1d\u00af", "\u0094\u00df(^\u0099H\bX", "\u0010\u00e4\u00fdU\u00ce\u00cf\u00f5\u001a\u00c5)/\u00de[\u001a\u00bdR\u00c8!\u00be\u00cc\u001f\u00e0\u0017\u00b1"};
        b = new String[11];
}
}