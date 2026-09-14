/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.KeepSprintBinder;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.PreSuperLivingUpdateEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.movement.Sprint;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.MinecraftRef;
import Abyss.util.ScoreboardReader;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class KeepSprint
extends Module
implements EventSubscriber {
    private static long b = 51037348104702L;
    private static long[] g;
    private static Minecraft S;
    public static ModeSetting mode;
        private boolean h;
    public static int t;
    public static int a;
    
    public static PercentageSetting slowdown;

    public void onAttackEntity(long var1, AttackEntityEvent var3) {
        if (mode.R("PREDICTION") && ScoreboardReader.v(0L)) {
            if (!this.h) {
                if (var3.O() instanceof EntityPlayer) {
                    switch (t) {
                        case 0: {
                            if (KeepSprint.S.field_71439_g.func_70051_ag()) {
                                var3.I(21307, 3074332907L);
                                t = 1;
                                a = 0;
                                break;
}
                            t = 2;
                            a = 0;
                            break;
}
                        case 1: {
                            KeepSprint.S.field_71439_g.func_70031_b(false);
                            a = 0;
                            t = 2;
}
}
}
                this.h = true;
}
        } else {
            this.J((short)0);
}
}
    public void onPreTick(long var1, PreTickEvent var3) {
        this.h = false;
}
    public static void k(long var0) {
        switch (mode.Y()) {
            case "PREDICTION": {
                if (!KeepSprint.S.field_71439_g.func_70051_ag()) break;
                if (t == 2) {
                    KeepSprint.S.field_71439_g.field_70159_w *= 1.0 - 0.4 * (double)slowdown.k() / 100.0;
                    KeepSprint.S.field_71439_g.field_70179_y *= 1.0 - 0.4 * (double)slowdown.k() / 100.0;
                    if (slowdown.k() != 60) break;
                    KeepSprint.S.field_71439_g.func_70031_b(false);
                    break;
}
                KeepSprint.S.field_71439_g.field_70159_w *= 0.6;
                KeepSprint.S.field_71439_g.field_70179_y *= 0.6;
                KeepSprint.S.field_71439_g.func_70031_b(false);
                break;
}
            default: {
                if (!KeepSprint.S.field_71439_g.func_70051_ag()) break;
                KeepSprint.S.field_71439_g.field_70159_w *= 1.0 - 0.4 * (double)slowdown.k() / 100.0;
                KeepSprint.S.field_71439_g.field_70179_y *= 1.0 - 0.4 * (double)slowdown.k() / 100.0;
                if (slowdown.k() != 60) break;
                KeepSprint.S.field_71439_g.func_70031_b(false);
}
}
}
    private void J(short var1) {
        t = 0;
        a = 0;
        this.h = false;
}
    public void onPreUpdate(PreUpdateEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (mode.R("PREDICTION") && ScoreboardReader.v(0L)) {
            if (a > 5) {
                this.J((short)0);
}
            switch (t) {
                case 1: {
                    KeepSprint.S.field_71439_g.func_70031_b(false);
                    ++a;
                    break;
}
                case 2: {
                    if (KeepSprint.S.field_71439_g.func_71039_bw()) {
                        if (Sprint.U(0L)) {
                            KeepSprint.S.field_71439_g.func_70031_b(true);
}
                    } else {
                        KeepSprint.S.field_71439_g.func_70031_b(true);
}
                    a = 0;
                    t = 0;
}
}
        } else {
            this.J((short)0);
}
}
    @Override
    public String g(long var1) {
        return mode.R("VANILLA") ? slowdown.k() + "%" : mode.Y();
}
    @Override
    public void A(long var1) {
        int var3 = (int)((var1 ^ 0x1A40F9A68B55L) >>> 48);
        this.J((short)var3);
}
    @Override
    public final void x(long var1, EventBus var3) {
        int var4 = (int)((var1 ^ 0xCBEDBBDFBC2L) >>> 48);
        int var5 = (int)((var1 ^ 0xCBEDBBDFBC2L) << 16 >>> 48);
        KeepSprintBinder.O(var3, (short)var4, (short)var5, this);
}
    public void onPreSuperLivingUpdate(PreSuperLivingUpdateEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (mode.R("PREDICTION") && ScoreboardReader.v(0L)) {
            switch (t) {
                case 1: {
                    KeepSprint.S.field_71439_g.func_70031_b(false);
                    break;
}
                case 2: {
                    if (KeepSprint.S.field_71439_g.func_71039_bw()) {
                        if (!Sprint.U(0L)) break;
                        KeepSprint.S.field_71439_g.func_70031_b(true);
                        break;
}
                    KeepSprint.S.field_71439_g.func_70031_b(true);
}
}
        } else {
            this.J((short)0);
}
}
    public KeepSprint(long var1) {
        super(b ^ var1 ^ 0x110042EFFD11L);
        this.declare("KeepSprint", Category.Combat, "Modify the slowdown while attacking", new Setting[0]);
        var1 = b ^ var1;
        this.h = false;
}
    static {
        t = 0;
        a = 0;
        S = MinecraftRef.c((byte)0, 0L);
        mode = new ModeSetting("Mode", false, "PREDICTION", "VANILLA", "PREDICTION");
        slowdown = new PercentageSetting("Slowdown", 0);
}
}