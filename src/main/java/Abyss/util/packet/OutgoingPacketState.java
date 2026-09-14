/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 *  net.minecraft.network.play.client.C09PacketHeldItemChange
 *  net.minecraft.network.play.client.C0APacketAnimation
 */
package Abyss.util.packet;

import Abyss.module.impl.combat.AutoBlock;
import Abyss.util.MinecraftRef;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;

public class OutgoingPacketState {
    public static boolean C;
    private static Minecraft l;
    public static boolean d;
    public static boolean O;
    public static boolean E;
    public static boolean P;
        public static boolean h;
    public static boolean R;
    public static boolean T;

    public static boolean f() {
        return !OutgoingPacketState.w() && !T && !P && !h;
}
    public static boolean w() {
        return AutoBlock.c();
}
    public static void D(long var0, Packet var2) {
        if (var2 instanceof C02PacketUseEntity) {
            E = true;
            if (((C02PacketUseEntity)var2).func_149565_c() == C02PacketUseEntity.Action.INTERACT) {
                h = true;
            } else if (((C02PacketUseEntity)var2).func_179712_b() != null) {
                h = true;
            } else if (((C02PacketUseEntity)var2).func_149565_c() == C02PacketUseEntity.Action.INTERACT_AT) {
                h = true;
}
}
        if (var2 instanceof C07PacketPlayerDigging) {
            P = true;
            R = true;
            if (((C07PacketPlayerDigging)var2).func_180762_c() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                C = true;
}
}
        if (var2 instanceof C08PacketPlayerBlockPlacement) {
            h = true;
}
        if (var2 instanceof C09PacketHeldItemChange) {
            if (((C09PacketHeldItemChange)var2).func_149614_c() != OutgoingPacketState.l.field_71439_g.field_71071_by.field_70461_c) {
                P = true;
}
            O = true;
}
        if (var2 instanceof C0APacketAnimation) {
            T = true;
}
        if (var2 instanceof C03PacketPlayer) {
            E = false;
            if (d) {
                P = true;
                d = false;
            } else {
                P = false;
}
            h = false;
            T = false;
            C = false;
            O = false;
}
}
    public static boolean Y() {
        return !OutgoingPacketState.w() && !P && !h;
}
    public static void J(long var0) {
        d = true;
}
    static {
        E = false;
        P = false;
        h = false;
        T = false;
        C = false;
        R = false;
        O = false;
        d = false;
        l = MinecraftRef.c((byte)0, 0L);
}
}