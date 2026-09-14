/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.scoreboard.ScorePlayerTeam
 */
package Abyss.enums;

import Abyss.util.MinecraftRef;
import Abyss.util.TeamPrefixUtil;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScorePlayerTeam;

public enum MegaWallsClass {
    ANGEL("ANG", 0),
    ARCANIST("ARC", 0),
    ASSASSIN("ASN", 0),
    AUTOMATON("ATN", 0),
    BLAZE("BLA", 0),
    COW("COW", 0),
    CREEPER("CRE", 0),
    DRAGON("DRG", 0),
    DREADLORD("DRE", 0),
    ENDERMAN("END", 0),
    GOLEM("GOL", 0),
    HEROBRINE("HBR", 0),
    HUNTER("HUN", 0),
    MOLEMAN("MOL", 0),
    PHOENIX("PHX", 0),
    PIGMAN("PIG", 0),
    PIRATE("PIR", 0),
    RENEGADE("REN", 0),
    SHAMAN("SHA", 0),
    SHARK("SRK", 0),
    SHEEP("SHP", 0),
    SKELETON("SKE", 0),
    SNOWMAN("SNO", 0),
    SPIDER("SPI", 0),
    SQUID("SQU", 0),
    WEREWOLF("WER", 0),
    ZOMBIE("ZOM", 0);

    
    public final int healthPotionAmount;
    private static final Minecraft K;
    public final String tag;
    
    private static final Map<String, MegaWallsClass> v;
    public final String className;
        
    
    
    
    

    public boolean U() {
        return this.healthPotionAmount > 0;
}
    public static MegaWallsClass q(char var0, char var1, String var3) {
        return var3 == null ? null : MegaWallsClass.o(TeamPrefixUtil.u(var3).replaceAll("[\\[\\]\\s]", ""));
}
    public static MegaWallsClass s(String var0, long var1) {
        var1 = a ^ var1;
        int var3 = (int)((var1 ^ 0x6979613563B1L) >>> 48);
        int var4 = (int)((var1 ^ 0x6979613563B1L) << 16 >>> 48);
        if (MegaWallsClass.K.field_71441_e == null) {
            return null;
}
        ScorePlayerTeam var6 = MegaWallsClass.K.field_71441_e.func_96441_U().func_96509_i(var0);
        return var6 == null ? null : MegaWallsClass.q((char)var3, (char)var4, var6.func_96663_f());
}
    public static MegaWallsClass o(String var0) {
        return v.get(var0);
}
    private MegaWallsClass(String var3, int var4) {
        this.tag = var3;
        String var5 = this.name().toLowerCase(Locale.ROOT);
        this.className = Character.toUpperCase(var5.charAt(0)) + var5.substring(1);
        this.healthPotionAmount = var4;
}
    static {
        K = MinecraftRef.c((byte)MegaWallsClass.zkm$g22(), MegaWallsClass.zkm$g23());
        v = new HashMap<String, MegaWallsClass>();
        MegaWallsClass[] var10000 = new MegaWallsClass[0];
        var10000[0] = ANGEL;
        var10000[1] = ARCANIST;
        var10000[2] = ASSASSIN;
        var10000[3] = AUTOMATON;
        var10000[4] = BLAZE;
        var10000[5] = COW;
        var10000[0] = CREEPER;
        var10000[0] = DRAGON;
        var10000[0] = DREADLORD;
        var10000[0] = ENDERMAN;
        var10000[0] = GOLEM;
        var10000[0] = HEROBRINE;
        var10000[0] = HUNTER;
        var10000[0] = MOLEMAN;
        var10000[0] = PHOENIX;
        var10000[0] = PIGMAN;
        var10000[0] = PIRATE;
        var10000[0] = RENEGADE;
        var10000[0] = SHAMAN;
        var10000[0] = SHARK;
        var10000[0] = SHEEP;
        var10000[0] = SKELETON;
        var10000[0] = SNOWMAN;
        var10000[0] = SPIDER;
        var10000[0] = SQUID;
        var10000[0] = WEREWOLF;
        var10000[0] = ZOMBIE;
        for (MegaWallsClass var28 : MegaWallsClass.values()) {
            v.put(var28.tag, var28);
}
}
}