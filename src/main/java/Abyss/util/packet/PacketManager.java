/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.INetHandler
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.INetHandlerPlayClient
 *  net.minecraft.network.play.INetHandlerPlayServer
 */
package Abyss.util.packet;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.PacketManagerBinder;
import Abyss.event.events.SendPacketEvent;
import Abyss.util.ClientUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.packet.OutgoingPacketState;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.INetHandlerPlayServer;

public class PacketManager
implements EventSubscriber {
    public static Set<Packet<INetHandlerPlayServer>> a;
    private static Minecraft T;
    public static Set<Packet<INetHandlerPlayServer>> v;
    public static boolean Z;
    private static long b;
    public static List<Packet<?>> u;

    public static void M(Packet<INetHandlerPlayClient> var0) {
        try {
            if (!ClientUtil.I() || T.func_71356_B()) {
                return;
}
            a.add(PacketManager.s(var0));
            var0.func_148833_a((INetHandler)T.func_147114_u());
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        PacketManagerBinder.N(var3, this);
}
    public static void M(boolean var0) {
        Z = var0;
}
    public static void X(Packet<?> var0) {
        try {
            v.add(PacketManager.s(var0));
            T.func_147114_u().func_147297_a(var0);
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    public static void k(Packet<INetHandlerPlayClient> var0) {
        try {
            if (!ClientUtil.I() || T.func_71356_B()) {
                return;
}
            var0.func_148833_a((INetHandler)T.func_147114_u());
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    public void onSendPacket(long var1, SendPacketEvent var3) {
        if (ClientUtil.I() && !T.func_71356_B()) {
            if (Z) {
                OutgoingPacketState.D(0L, var3.B);
                u.add(var3.B);
                var3.I(21307, 3074332907L);
}
        } else {
            PacketManager.j();
            v.clear();
            a.clear();
}
}
    public static void b(Packet<?> var0) {
        try {
            T.func_147114_u().func_147297_a(var0);
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    public static boolean e() {
        return Z;
}
    public static void j() {
        try {
            if (T.func_71356_B()) {
                u.clear();
}
            ArrayList snapshot = new ArrayList(u);
            u.clear();
            for (Packet packet : snapshot) {
                PacketManager.X(packet);
}
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    public static <H extends INetHandler> Packet<H> s(Packet<?> var0) {
        return var0;
}
    static void $jnicClinit() throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        long var12;
        b = 116394823986266L;
        long var7 = b ^ 0x6406B435698CL;
        byte[] var10003 = new byte[]{(byte)(var7 >>> 56), 0, 0, 0, 0, 0, 0, 0};
        for (int var3 = 1; var3 < 8; ++var3) {
            var10003[var3] = (byte)(var7 << var3 * 8 >>> 56);
}
        Cipher var2 = Cipher.getInstance("DES/CBC/NoPadding");
        var2.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
        byte[] var6 = var2.doFinal(new byte[]{92, -107, -48, 59, 103, 94, 108, -127});
        long var0 = var12 = ((long)var6[0] & 0xFFL) << 56 | ((long)var6[1] & 0xFFL) << 48 | ((long)var6[2] & 0xFFL) << 40 | ((long)var6[3] & 0xFFL) << 32 | ((long)var6[4] & 0xFFL) << 24 | ((long)var6[5] & 0xFFL) << 16 | ((long)var6[6] & 0xFFL) << 8 | (long)var6[7] & 0xFFL;
        Z = var0 != 0L;
}
    static {
        try {
            PacketManager.$jnicClinit();
            u = new ArrayList();
            v = new HashSet<Packet<INetHandlerPlayServer>>();
            a = new HashSet<Packet<INetHandlerPlayServer>>();
}
        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var0) {
            throw new RuntimeException(var0);
}
        T = MinecraftRef.c((byte)0, 0L);
}
}