/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.handshake.client.C00Handshake
 *  net.minecraft.network.login.client.C00PacketLoginStart
 *  net.minecraft.network.login.client.C01PacketEncryptionResponse
 *  net.minecraft.network.play.INetHandlerPlayClient
 *  net.minecraft.network.play.server.S00PacketKeepAlive
 *  net.minecraft.network.play.server.S01PacketJoinGame
 *  net.minecraft.network.play.server.S07PacketRespawn
 *  net.minecraft.network.play.server.S19PacketEntityStatus
 *  net.minecraft.network.status.client.C00PacketServerQuery
 *  net.minecraft.network.status.client.C01PacketPing
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 */
package Abyss.util.packet;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.IncomingPacketHoldBinder;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.util.ClientUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.packet.PacketManager;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class IncomingPacketHold
implements EventSubscriber {
    private static Object[] b;
    private static Minecraft h;
    private static List<Packet<INetHandlerPlayClient>> U;
    private static boolean g;
    public static Map<Integer, Vec3> i;
    
    

                Cipher var2 = Cipher.getInstance("DES/CBC/NoPadding");
            var2.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            byte[] var6 = var2.doFinal(new byte[]{-120, -122, -114, 110, -90, 1, 17, -89});
            long var0 = var13 = ((long)var6[0] & 0xFFL) << 56 | ((long)var6[1] & 0xFFL) << 48 | ((long)var6[2] & 0xFFL) << 40 | ((long)var6[3] & 0xFFL) << 32 | ((long)var6[4] & 0xFFL) << 24 | ((long)var6[5] & 0xFFL) << 16 | ((long)var6[6] & 0xFFL) << 8 | (long)var6[7] & 0xFFL;
            U = new ArrayList<Packet<INetHandlerPlayClient>>();
            i = new HashMap<Integer, Vec3>();
            g = (var0 & 1L) != 0L;
}
        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var12) {
            throw new RuntimeException(var12);
}
}
    public void onReceivePacket(ReceivePacketEvent var1, long var2) {
        if (ClientUtil.I() && !h.func_71356_B()) {
            if (g && this.K(var1.d)) {
                U.add(var1.d);
                var1.I(21307, 3074332907L);
}
        } else {
            IncomingPacketHold.s();
}
}
    public static void s() {
        U.clear();
        i.clear();
}
    public void onSendPacket(SendPacketEvent var1) {
        if (ClientUtil.I() && !h.func_71356_B()) {
            if (var1.B instanceof C00Handshake || var1.B instanceof C00PacketLoginStart || var1.B instanceof C00PacketServerQuery || var1.B instanceof C01PacketPing || var1.B instanceof C01PacketEncryptionResponse) {
                IncomingPacketHold.m();
}
        } else {
            IncomingPacketHold.s();
}
}
    public static void m() {
        if (ClientUtil.I() && !h.func_71356_B()) {
            ArrayList<Packet<INetHandlerPlayClient>> snapshot = new ArrayList<Packet<INetHandlerPlayClient>>(U);
            U.clear();
            for (Packet packet : snapshot) {
                PacketManager.M((Packet<INetHandlerPlayClient>)packet);
}
            i.clear();
        } else {
            IncomingPacketHold.s();
}
}
    public boolean K(Packet<?> var1) {
        if (!g) {
            return false;
}
        if (var1 instanceof S00PacketKeepAlive) {
            return false;
}
        if (var1 instanceof S01PacketJoinGame || var1 instanceof S07PacketRespawn) {
            IncomingPacketHold.m();
            return false;
}
        if (!(var1 instanceof S19PacketEntityStatus)) {
            return true;
}
        S19PacketEntityStatus var2 = (S19PacketEntityStatus)var1;
        Entity var3 = var2.func_149161_a((World)IncomingPacketHold.h.field_71441_e);
        return var3 == null || var3.equals((Object)IncomingPacketHold.h.field_71439_g) && var2.func_149160_c() == 2;
}
    public static List<Packet<INetHandlerPlayClient>> p() {
        return U;
}
    public static boolean r() {
        return g;
}
    public static void X(boolean var0) {
        g = var0;
}
    @Override
    public final void x(long var1, EventBus var3) {
        IncomingPacketHoldBinder.z(var3, this);
}
    private static void a() {
        IncomingPacketHold.b[0] = ".[O03s4";
        IncomingPacketHold.b[1] = "i\u0002&]j\u001c^\u0015\"W'8I\u001exK";
        IncomingPacketHold.b[2] = Long.TYPE;
        IncomingPacketHold.c[2] = "java/lang/Long";
        IncomingPacketHold.b[3] = "\u0006w\u0017mhK:";
        IncomingPacketHold.b[4] = Void.TYPE;
        IncomingPacketHold.c[4] = "java/lang/Void";
        IncomingPacketHold.b[5] = "xDxwm\\sKi8\fRx@mb";
        IncomingPacketHold.b[6] = "\\F%\\\u001e\u0016GS~?\u001dh\u0005\u0011wU\u0006\u0001\u0003EhDoQ\\Ks@\u0006W\u0002T~?U\u000e\u0007Sh\u0000\u000f\u0019\u0006W\u0018\u0004\u0001\n\u0001E\"A\u0005\u0014\u0003)";
}
    static {
        h = MinecraftRef.c((byte)0, 0L);
}
}