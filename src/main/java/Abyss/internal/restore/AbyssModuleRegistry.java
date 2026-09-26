/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package Abyss.internal.restore;

import Abyss.internal.CheaterDetector;
import Abyss.internal.restore.AbyssConfig;
import Abyss.internal.restore.AbyssCtorCache;
import Abyss.internal.restore.AbyssSettingStatics;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.impl.combat.AimAssist;
import Abyss.module.impl.combat.AutoBlock;
import Abyss.module.impl.combat.AutoClicker;
import Abyss.module.impl.combat.AutoProjectiles;
import Abyss.module.impl.combat.BlockHit;
import Abyss.module.impl.combat.FakeLag;
import Abyss.module.impl.combat.HitBox;
import Abyss.module.impl.combat.KeepSprint;
import Abyss.module.impl.combat.KillAura;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.module.impl.configuration.CustomCape;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.configuration.Gadgets;
import Abyss.module.impl.configuration.Language;
import Abyss.module.impl.configuration.Notifications;
import Abyss.module.impl.configuration.ScoreBoard;
import Abyss.module.impl.configuration.Teams;
import Abyss.module.impl.configuration.Theme;
import Abyss.module.impl.configuration.VisualSpoof;
import Abyss.module.impl.macro.Macro1;
import Abyss.module.impl.macro.Macro2;
import Abyss.module.impl.macro.Macro3;
import Abyss.module.impl.macro.Macro4;
import Abyss.module.impl.macro.Macro5;
import Abyss.module.impl.misc.AntiBot;
import Abyss.module.impl.misc.AntiNick;
import Abyss.module.impl.misc.CommandLine;
import Abyss.module.impl.misc.Denick;
import Abyss.module.impl.misc.InputFix;
import Abyss.module.impl.misc.NameHider;
import Abyss.module.impl.misc.NoObfuscation;
import Abyss.module.impl.misc.RawInput;
import Abyss.module.impl.movement.FastFall;
import Abyss.module.impl.movement.Fly;
import Abyss.module.impl.movement.NoJumpDelay;
import Abyss.module.impl.movement.NoSlow;
import Abyss.module.impl.movement.Speed;
import Abyss.module.impl.movement.Sprint;
import Abyss.module.impl.player.AutoWeapon;
import Abyss.module.impl.player.ChestStealer;
import Abyss.module.impl.player.FreeCam;
import Abyss.module.impl.player.GhostHand;
import Abyss.module.impl.player.InvManager;
import Abyss.module.impl.player.NoInteract;
import Abyss.module.impl.visual.Ambience;
import Abyss.module.impl.visual.Animations;
import Abyss.module.impl.visual.AntiDebuff;
import Abyss.module.impl.visual.BarrierVisible;
import Abyss.module.impl.visual.BindGUI;
import Abyss.module.impl.visual.BreakProgress;
import Abyss.module.impl.visual.CaveXray;
import Abyss.module.impl.visual.Freelook;
import Abyss.module.impl.visual.FullBright;
import Abyss.module.impl.visual.HUD;
import Abyss.module.impl.visual.ItemScale;
import Abyss.module.impl.visual.KeyStrokes;
import Abyss.module.impl.visual.KillEffect;
import Abyss.module.impl.visual.NoHurtCam;
import Abyss.module.impl.visual.TeamInvisible;
import Abyss.module.impl.visual.ViewClip;
import Abyss.module.impl.visual_utility.BedESP;
import Abyss.module.impl.visual_utility.BlocksESP;
import Abyss.module.impl.visual_utility.ChestESP;
import Abyss.module.impl.visual_utility.ClosestPlayerHUD;
import Abyss.module.impl.visual_utility.ESP;
import Abyss.module.impl.visual_utility.FKCounter;
import Abyss.module.impl.visual_utility.FireBallPredict;
import Abyss.module.impl.visual_utility.Indicators;
import Abyss.module.impl.visual_utility.ItemESP;
import Abyss.module.impl.visual_utility.ItemTags;
import Abyss.module.impl.visual_utility.MegaWallsDetector;
import Abyss.module.impl.visual_utility.NameTags;
import Abyss.module.impl.visual_utility.TargetHUD;
import Abyss.module.impl.visual_utility.Tracers;
import Abyss.module.impl.visual_utility.Trajectories;
import Abyss.module.impl.world.AutoTunnel;
import Abyss.module.impl.world.BedNuker;
import Abyss.module.impl.world.FastPlace;
import Abyss.module.impl.world.Scaffold;
import Abyss.module.impl.world.SpeedMine;
import Abyss.setting.Setting;
import com.google.gson.JsonObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class AbyssModuleRegistry {
    public static final String PLACEHOLDER_PREFIX = "?";
    public static Module PLAIN_LISTENER;
    public static final boolean PUBLISH_SOLVED_CARRIERS = true;
    public static final boolean PUBLISH_ANCHORED_CARRIERS = true;
    public static final List<String> PUBLISHED;
    public static final List<String> PENDING;
    private static final Set<Class<? extends Module>> PERSISTABLE;
    private static final Set<String> PERSISTABLE_NAMES;
    public static final int ORIGINAL_MODULE_COUNT = 112;
    private static boolean published;
    private static final Set<String> RETIRED;
    private static int retired;
    public static final List<String> MISSING;
    public static boolean countGateGreen;
    private static final String[] DISABLED_UPSTREAM;

    public static boolean isRetired(String name) {
        return name != null && RETIRED.contains(name);
}
    public static int expectedModuleCount() {
        return 112 - retired;
}
    private AbyssModuleRegistry() {
}
    public static void publish() {
        if (published) {
            return;
}
        published = true;
        if (ModuleManager.S == null) {
            ModuleManager.S = new ArrayList<Module>();
}
        if (ModuleManager.o == null) {
            ModuleManager.o = new HashMap();
}
        AbyssModuleRegistry.reg(new FKCounter(0L), FKCounter.class, "FKCounter", true);
        AbyssModuleRegistry.reg(new HUD(0L), HUD.class, "HUD", true);
        AbyssModuleRegistry.reg(ModuleManager.r, HitBox.class, "HitBox", true);
        AbyssModuleRegistry.reg(new Macro1(0L), Macro1.class, "Macro1", true);
        AbyssModuleRegistry.reg(new Macro2(0, 0, (short)0), Macro2.class, "Macro2", true);
        AbyssModuleRegistry.reg(new AimAssist(0L), AimAssist.class, "AimAssist", true);
        AbyssModuleRegistry.reg(new AutoProjectiles(0L, (short)0), AutoProjectiles.class, "AutoProjectiles", true);
        AbyssModuleRegistry.reg(new Macro3(0L), Macro3.class, "Macro3", true);
        AbyssModuleRegistry.reg(new Macro4(0L), Macro4.class, "Macro4", true);
        AbyssModuleRegistry.reg(new Macro5(0L), Macro5.class, "Macro5", true);
        AbyssModuleRegistry.reg(new ItemESP((short)0, '\u0000', 0), ItemESP.class, "ItemESP", true);
        AbyssModuleRegistry.reg(new Indicators(0L), Indicators.class, "Indicators", true);
        AbyssModuleRegistry.reg(new ChestESP(0L), ChestESP.class, "ChestESP", true);
        AbyssModuleRegistry.reg(new NameTags(0L), NameTags.class, "NameTags", true);
        AbyssModuleRegistry.reg(new ESP(0L), ESP.class, "ESP", true);
        AbyssModuleRegistry.reg(new SpeedMine((short)0, 0L), SpeedMine.class, "SpeedMine", true);
        AbyssModuleRegistry.reg(ModuleManager.h, ViewClip.class, "ViewClip", true);
        AbyssModuleRegistry.reg(new BedESP(0L), BedESP.class, "BedESP", true);
        ModuleManager.y = new TeamInvisible('\u0000', 0L);
        AbyssModuleRegistry.reg(ModuleManager.y, TeamInvisible.class, "TeamInvisible", true);
        AbyssModuleRegistry.reg(new ItemTags(0L), ItemTags.class, "ItemTags", true);
        AbyssModuleRegistry.reg(new TargetHUD(0L), TargetHUD.class, "TargetHUD", true);
        AbyssModuleRegistry.reg(new Tracers(0L), Tracers.class, "Tracers", true);
        AbyssModuleRegistry.reg(new AutoTunnel(0L), AutoTunnel.class, "AutoTunnel", true);
        AbyssModuleRegistry.reg(new BlocksESP(0L), BlocksESP.class, "BlocksESP", true);
        AbyssModuleRegistry.reg(new MegaWallsDetector(0L), MegaWallsDetector.class, "MegaWallsDetector", true);
        AbyssModuleRegistry.reg(new FireBallPredict(0L), FireBallPredict.class, "FireBallPredict", true);
        AbyssModuleRegistry.reg(new Trajectories(0L), Trajectories.class, "Trajectories", true);
        AbyssModuleRegistry.reg(new FastPlace(0L), FastPlace.class, "FastPlace", true);
        AbyssModuleRegistry.reg(new ClosestPlayerHUD(0L), ClosestPlayerHUD.class, "ClosestPlayerHUD", true);
        AbyssModuleRegistry.reg(new ScoreBoard(0L), ScoreBoard.class, "ScoreBoard", true);
        AbyssModuleRegistry.reg(new Teams(0L, (short)0), Teams.class, "Teams", true);
        AbyssModuleRegistry.reg(new Ambience(0L), Ambience.class, "Ambience", true);
        AbyssModuleRegistry.reg(ModuleManager.g, NoHurtCam.class, "NoHurtCam", true);
        AbyssModuleRegistry.reg(new FastFall(0L), FastFall.class, "FastFall", true);
        ModuleManager.k = new NoObfuscation(0L);
        AbyssModuleRegistry.reg(ModuleManager.k, NoObfuscation.class, "NoObfuscation", true);
        AbyssModuleRegistry.reg(new Speed(0L), Speed.class, "Speed", true);
        AbyssModuleRegistry.reg(new NoJumpDelay(0L), NoJumpDelay.class, "NoJumpDelay", true);
        AbyssModuleRegistry.reg(new Notifications(0L), Notifications.class, "Notifications", true);
        AbyssModuleRegistry.reg(new AutoWeapon(0L), AutoWeapon.class, "AutoWeapon", true);
        PLAIN_LISTENER = new CheaterDetector(0, '\u0000', 0);
        ModuleManager.o.put(CheaterDetector.class, PLAIN_LISTENER);
        AbyssModuleRegistry.reg(new CustomCape('\u0000', 0L), CustomCape.class, "CustomCape", true);
        AbyssModuleRegistry.reg(new Font(0L), Font.class, "Font", true);
        AbyssModuleRegistry.reg(new NoSlow(0L), NoSlow.class, "NoSlow", true);
        AbyssModuleRegistry.reg(new KillEffect(0L), KillEffect.class, "KillEffect", true);
        AbyssModuleRegistry.reg(new RawInput(0L), RawInput.class, "RawInput", true);
        AbyssModuleRegistry.reg(ModuleManager.d, Animations.class, "Animations", true);
        AbyssModuleRegistry.reg(new BreakProgress(0L), BreakProgress.class, "BreakProgress", true);
        AbyssModuleRegistry.reg(new Freelook(0L), Freelook.class, "Freelook", true);
        AbyssModuleRegistry.reg(new Theme(0, '\u0000', 0), Theme.class, "Theme", true);
        ModuleManager.f = new AntiNick(0L);
        AbyssModuleRegistry.reg(ModuleManager.f, AntiNick.class, "AntiNick", true);
        AbyssModuleRegistry.reg(new InputFix((short)0, 0, 0), InputFix.class, "InputFix", true);
        AbyssModuleRegistry.reg(new KeyStrokes((byte)0, 0, 0), KeyStrokes.class, "KeyStrokes", true);
        AbyssModuleRegistry.reg(ModuleManager.q, ChestStealer.class, "ChestStealer", true);
        AbyssModuleRegistry.reg(new Sprint((short)0, 0, (short)0), Sprint.class, "Sprint", true);
        AbyssModuleRegistry.reg(new Denick(0L), Denick.class, "Denick", true);
        AbyssModuleRegistry.reg(new Fly(0L), Fly.class, "Fly", true);
        AbyssModuleRegistry.reg(new FullBright(0L), FullBright.class, "FullBright", true);
        ModuleManager.c = new AntiBot(0, 0, (short)0);
        AbyssModuleRegistry.reg(ModuleManager.c, AntiBot.class, "AntiBot", true);
        AbyssModuleRegistry.reg(new FreeCam(0L), FreeCam.class, "FreeCam", true);
        AbyssModuleRegistry.reg(new Language('\u0000', 0, 0), Language.class, "Language", true);
        AbyssModuleRegistry.reg(new CommandLine(0L), CommandLine.class, "CommandLine", true);
        AbyssModuleRegistry.reg(new VisualSpoof((short)0, (short)0, 0), VisualSpoof.class, "VisualSpoof", true);
        AbyssModuleRegistry.reg(new NoInteract(0L), NoInteract.class, "NoInteract", true);
        ModuleManager.J = new NameHider(0L);
        AbyssModuleRegistry.reg(ModuleManager.J, NameHider.class, "NameHider", true);
        AbyssModuleRegistry.reg(new BindGUI(0L), BindGUI.class, "BindGUI", true);
        AbyssModuleRegistry.reg(new Gadgets(0L), Gadgets.class, "Gadgets", true);
        AbyssModuleRegistry.reg(new ClickGUI(0L), ClickGUI.class, "ClickGUI", true);
        AbyssModuleRegistry.reg(ModuleManager.v, ItemScale.class, "ItemScale", true);
        AbyssModuleRegistry.reg(ModuleManager.W, BarrierVisible.class, "BarrierVisible", true);
        AbyssModuleRegistry.reg(ModuleManager.Q, GhostHand.class, "GhostHand", true);
        AbyssModuleRegistry.reg(ModuleManager.O, AntiDebuff.class, "AntiDebuff", true);
        AbyssModuleRegistry.reg(ModuleManager.m, CaveXray.class, "CaveXray", true);
        AbyssModuleRegistry.publishSolvedCarriers();
        AbyssModuleRegistry.publishAnchoredCarriers();
        AbyssModuleRegistry.publishCachePrepopulated();
        PENDING.add("AbyssModuleRegistry  DENOMINATOR: the original registers 112 modules (qux capture modules.count=112, name set identical to the 112 config blocks).  Abyss/internal/CheaterDetector (twin Abyss/xq) and Abyss/module/unregistered/q8 (twin Abyss/KI) are leaf Module classes that the original does NOT register, so they have no name and no config block; \"AntiCheat\" is absent from the shipped config too.  This build registers zG_3, i.e. one module more than the original.");
        PENDING.add("AbyssModuleRegistry  THREE COUNTS, THREE MEANINGS -- do not gate on the wrong one.  The original registration denominator is 112 (= ORIGINAL_MODULE_COUNT).  This recovery keeps those original module classes class-addressable but intentionally retires " + retired + " from the active ModuleManager.S list, so the current active-list gate is " + AbyssModuleRegistry.expectedModuleCount() + ".  tD.o = 113 is the 112 original module classes (including retired ones) plus Abyss/internal/CheaterDetector, which is held out of the active list.  115 is the concrete leaf-class census: the 112 plus CheaterDetector plus Abyss/module/unregistered/q8 (never instantiated) plus Abyss/internal/restore/AbyssSweepCanary (added by this project).  isConfigWritable() therefore compares the active list against expectedModuleCount(), not against the original 112 or the leaf-class census.");
        PENDING.add("Abyss.module.impl.world.AutoDigPlace / Abyss.module.impl.world.AutoTool / Abyss.module.impl.misc.Timer / Abyss.module.impl.visual.Chams / Abyss.module.impl.movement.InvMove  no longer HELD: all five moved to AbyssCtorCache.plans() with values derived from well-formedness of their own code, not from the twin-instance matcher (which is NOMATCH for iK/ij_2/zU_2/zV_3 and a cheap 1-boolean match for zF_3).  Each plan comment names the two independent sites it rests on.  Slot feasibility pre-flighted by work/last10-agent/tool/slotfeas.py: 4 of 4 must-fail inputs rejected, and it is VACUOUS for iK and zF_3.");
        PENDING.add("Abyss.module.impl.world.BedNuker  ctor (SJ)V  carrier SOLVED (short)0,33171000103266 -- PUBLISHED.  The upstream NullPointerException is neutralised by one DECLARED DEVIATION in Abyss/ik_2.W: var12 now starts with !var10 (this.Do != null).  That edit is PROVABLY behaviour-preserving, not merely minimal: in the original bytecode var12 is stored at pc243 and loaded exactly once, at pc255, on the far side of pc245 'iload 10; ifne 317', so whenever Do == null the value of var12 is never consumed -- only the exception thrown while computing it is.  Everything the skipped call would have run before throwing is pure: aG_3.Y -> aG_3.p -> aG_3.n(.., Af_2.D(Do)) evaluates aG_3.f() (mc.thePlayer.getPositionEyes) and then Af_2.c derefs the null BlockPos at its first offset.  Upstream verified verbatim in abyss-plain.jar: Abyss/ik.W(J,Abyss/bo) pc196-243 computes var12 unconditionally and pc29-45 computes var10 = (Do == null) first, and Abyss/Af.c(BlockPos,Vec3) pc42-48 has no guard.  The event only reaches ik_2 while BedNuker is subscribed, i.e. enabled, so the throw was only ever reachable from the sweep; EventBus.e caught it per-binding, so no other listener was affected.");
        PENDING.add("Abyss.module.unregistered.q8  ctor (J)V  carrier LIVE: 29 invokestatic Abyss/module/unregistered/q8.b(IJ)I  -- NOT A MODULE, so nothing to solve: the CONTRADICTION (twin Abyss/KI's cache g[2] has 0 live entries) has a mechanism.  Abyss/KI is absent from the 112 modules the qux capture enumerates, so the twin never constructs it and its ctor decryptor never ran.  q8's own two fields (y:Z idx 4351, N:Z idx 29439) are also absent from the capture for the same reason.  This class is outside the 112 and must not be published.");
        PENDING.add("Abyss.module.impl.player.Blink  ctor (J)V  no longer HELD: the carrier is SOLVED, c = 8295, the only one of 32768 that satisfies 8 slot constraints across the class's three tables, and the 2.4.6 twin Abyss/xe yields its own unique c = 32377 under 10 constraints.  The conflict that held it is RESOLVED rather than broken: the twin's J=1 k=0 reading is post-construction state -- a Blink that was enabled and blinking in NORMAL mode -- corroborated by which twin slots are null, so it never contradicted the ctor being [J=0,k=1].  The step the old argument needed is still NOT established and is not used; what replaced it is rank-1 separability, lit(method,idx) = X(method) XOR D(idx), which holds in z3_2 (3 of 3 pairs) and fails in 2 of 25 groups tree-wide, so it is not vacuous.  Sites e(12421) and b(8718) remain unknown; both are PULSE-only and Mode ships NORMAL, so the shipped default is unaffected.  Evidence: work/seedharvest2-agent/out/Z3_2.md.");
        PENDING.add("Abyss.module.impl.player.InvClicker  ctor (J)V  carrier LIVE -- no longer HELD: p:I = 0 is DERIVED and the module is in AbyssCtorCache.plans().  The candidate pair {0,42} is the two LIVE VALUES of the twin's 4-slot cache, and the generator only knew the ctor site, so it could not say which of the class's FOUR numeric sites owns which value.  Naming the owner of the live 0 by elimination settles it: site 10460 (the `p = p - <it>` decrement at zK_3.java:51) must be >= 1, or p never returns to <=0 and the only click path that reads p fires at most once per client; site 454 (a key code fed to Zv_2.V at :55) cannot be 0, because Zv_2.V(int,long) opens with `if (var5 == 0) return false` at Zv_2.java:124 and the whole trigger chain would be dead.  That leaves the ctor or A(long)=onDisable, and if onDisable owned the 0 the twin must have been enabled (so j(PreUpdateEvent) ran every tick) while only 2 slots are live, which forces the ctor to share a cell -- sharing onDisable's cell gives 0 again.  Independent check that needs no twin arithmetic: p is MILLISECONDS (`p = (int)(1000.0/k.L())` at :62, CPS ships 10.0 -> 100 ms = 2 ticks at 50 ms) and `p <= 0` IS the ready-to-click state, so a fresh clicker is ready; 42 meanwhile has a natural owner at site 454, since LWJGL2 Keyboard.KEY_LSHIFT is 42 and that site is the second trigger key in `attack down && <it> down && in a container` -- the shift-click idiom.  STILL UNMEASURED: value(10460).  With Always-click=true (shipped) the rearm at :62 is unreachable, p stays at the ctor value and 10460 never decrypts; flip Always-click off with the module enabled in a container and slot(10460) fills.  50 would confirm the millisecond reading exactly.");
        PENDING.add("Abyss.module.impl.player.NoHitDelay  ctor (ICC)V  carrier LIVE -- no longer HELD: S:I = 0 is now DERIVED and the module is in AbyssCtorCache.plans().  The conflict that held it is resolved because the third module-hook site in AZ.r (bytecode pc630-638 / AZ.java:458, inside the NOT-ENABLED branch and unconditional there) is now known to be Abyss/iD.P(J)V, not A(J)V: ZKM's own runtime resolver picked it and AbyssSweepCanary recorded the choice as \"ABYSS_SWEEP_CANARY:P(long) at zkm$unresolved$6...Abyss_iD_A_OR_Abyss_iD_P\".  The same canary pins the other two sites empirically: ENABLE -> zkm$unresolved$2 -> i(long), DISABLE -> zkm$unresolved$3 -> A(long).  So A(long) (51 module overrides) is onDisable, i(long) (15) is onEnable, and P(long) (2: i9 and zk_4) is the every-tick-while-disabled baseline reset.  CORRECTION to the chain handed over: it argued the ctor value is dead because P(long) overwrites S every tick while the module is off -- but NoHitDelay ships status=true, so it never enters that branch and the ctor value IS live.  See the plan comment for the two judges that decide it.");
        PERSISTABLE_NAMES.addAll(AbyssModuleRegistry.namesOf(PERSISTABLE));
        AbyssModuleRegistry.markDisabledUpstream();
        AbyssModuleRegistry.countGate();
}
    private static void countGate() {
        MISSING.clear();
        AbyssCtorCache.Plan[] var0 = AbyssCtorCache.plans();
        for (int var1 = 0; var1 < var0.length; ++var1) {
            if (ModuleManager.o.containsKey(var0[var1].cls)) continue;
            MISSING.add(var0[var1].cls.getName());
}
        AbyssCtorCache.SPlan[] var2 = AbyssCtorCache.splans();
        for (int var3 = 0; var3 < var2.length; ++var3) {
            if (ModuleManager.o.containsKey(var2[var3].cls)) continue;
            MISSING.add(var2[var3].cls.getName());
}
        int var4 = ModuleManager.S == null ? -1 : ModuleManager.S.size();
        boolean bl = countGateGreen = var4 == AbyssModuleRegistry.expectedModuleCount() && MISSING.isEmpty();
        if (!countGateGreen) {
            PENDING.add("AbyssModuleRegistry  MODULE COUNT REGRESSION: tD.S holds " + var4 + " of " + AbyssModuleRegistry.expectedModuleCount() + " (" + 112 + " registered minus " + retired + " retired) and " + MISSING.size() + " pre-populated module(s) never reached tD.o" + AbyssModuleRegistry.report());
}
}
    private static String report() {
        if (MISSING.isEmpty()) {
            return "";
}
        StringBuilder var0 = new StringBuilder(" -- ");
        for (int var1 = 0; var1 < MISSING.size(); ++var1) {
            var0.append(var1 == 0 ? "" : ", ").append(MISSING.get(var1));
}
        return var0.toString();
}
    private static void markDisabledUpstream() {
        for (int i = 0; i < DISABLED_UPSTREAM.length; ++i) {
            String name = DISABLED_UPSTREAM[i];
            Module found = null;
            for (Module m2 : ModuleManager.S) {
                if (m2 == null || !name.equals(m2.getClass().getName())) continue;
                found = m2;
                break;
}
            if (found == null) {
                PENDING.add(name + "  not in tD.S, so it could not be marked non-toggleable");
                continue;
}
            found.M(false);
            if (found.I()) {
                PENDING.add(name + "  M(false) did not take -- it is still toggleable");
                continue;
}
            PENDING.add(name + "  marked non-toggleable (stock refuses to enable it)");
}
}
    private static boolean settingsUsable(Class<?> owner) {
        int total = 0;
        int live = 0;
        for (Field f : owner.getDeclaredFields()) {
            if (!Modifier.isStatic(f.getModifiers()) || !Setting.class.isAssignableFrom(f.getType()) || !AbyssSettingStatics.buildable(f.getType())) continue;
            ++total;
            try {
                f.setAccessible(true);
                if (f.get(null) == null) continue;
                ++live;
}
            catch (Throwable throwable) {
                // empty catch block
}
}
        return total == 0 || live == total;
}
    private static void publishSolvedCarriers() {
        Scaffold var0 = null;
        try {
            var0 = new Scaffold(74866151867512L);
}
        catch (Throwable var4) {
            PENDING.add("Abyss.module.impl.world.Scaffold  solved carrier 74866151867512 threw at publish(): " + var4);
}
        if (var0 != null) {
            AbyssSettingStatics.fillFor(var0, AbyssConfig.read(), null);
}
        if (var0 != null && !AbyssModuleRegistry.settingsUsable(Scaffold.class)) {
            PENDING.add("Abyss.module.impl.world.Scaffold  HELD: its Setting statics are null and its handlers deref them from AZ.r, which aborts the pump and the ClickGUI hotkey poll");
            var0 = null;
}
        if (var0 != null) {
            ModuleManager.I = var0;
            AbyssModuleRegistry.reg(ModuleManager.I, Scaffold.class, "Scaffold", true);
}
        AutoBlock var1 = null;
        try {
            var1 = new AutoBlock((byte)0, 43254310455398L);
}
        catch (Throwable var5) {
            PENDING.add("Abyss.module.impl.combat.AutoBlock  solved carrier (0,43254310455398) threw at publish(): " + var5);
}
        if (var1 != null) {
            AbyssSettingStatics.fillFor(var1, AbyssConfig.read(), null);
}
        if (var1 != null && !AbyssModuleRegistry.settingsUsable(AutoBlock.class)) {
            PENDING.add("Abyss.module.impl.combat.AutoBlock  HELD: its buildable Setting statics are still null after fillFor");
            var1 = null;
}
        if (var1 != null) {
            PENDING.add("Abyss.module.impl.combat.AutoBlock  ctor (BJ)V  carrier SOLVED (byte)0,43254310455398 -- PUBLISHED.  The NullPointerException that withheld it is upstream and is now neutralised by one DECLARED DEVIATION in Abyss/PY.d(): a z.thePlayer != null guard.  Chain: Abyss/AZ.t(J,bW) calls Module.L(bW,J) on every module whose o() is FALSE -- L is the reset-while-disabled hook (Abyss/i6.L is a pure reset: T(false), H7=null, b=0, C=0, a=false, H6=null, x=false) -- and AutoBlock ships status=false, so iT.L -> n(true) -> B() -> PY.d() runs on the main menu, where mc.thePlayer is null.  Verified upstream at three levels: AZ.t pc100-123 is ifne-skip on one boolean whose indy has exactly two candidates (Abyss/iD.o()Z and java/util/Iterator.hasNext()Z) of which only o() is type-compatible with the checkcast Abyss/iD at pc58; PY.d() pc0-39 in abyss-plain.jar has no guard; and the bus is published from the original N.F call site MinecraftHooks.t()V pc44, reached from onStartGame() inside Minecraft.startGame and from onPreTick() every runTick, so no install timing exists in which bW does not fire on the main menu.  The deviation is observationally empty here: the branch PY.d()==false takes is iT.f(J), which sets iT.K=false and iT.D=false, the values they already hold on the main menu.");
            AbyssModuleRegistry.reg(var1, AutoBlock.class, "AutoBlock", true);
}
        KeepSprint var2 = null;
        try {
            var2 = new KeepSprint(27765L);
}
        catch (Throwable var6) {
            PENDING.add("Abyss.module.impl.combat.KeepSprint  solved carrier 27765 threw at publish(): " + var6);
}
        if (var2 != null) {
            AbyssModuleRegistry.reg(var2, KeepSprint.class, "KeepSprint", true);
}
}
    private static void publishAnchoredCarriers() {
        JsonObject var0 = null;
        try {
            var0 = AbyssConfig.read();
}
        catch (Throwable var7) {
            PENDING.add("AbyssModuleRegistry  config read failed for the anchored carriers: " + var7);
}
        KillAura var1 = null;
        try {
            var1 = new KillAura(8510264096497L);
}
        catch (Throwable var8) {
            PENDING.add("Abyss.module.impl.combat.KillAura  anchored carrier 8510264096497 threw at publish(): " + var8);
}
        AbyssModuleRegistry.anchored(var1, KillAura.class, "KillAura", var0);
        BlockHit var2 = null;
        try {
            var2 = new BlockHit(61052605171397L);
}
        catch (Throwable var9) {
            PENDING.add("Abyss.module.impl.combat.BlockHit  anchored carrier 61052605171397 threw at publish(): " + var9);
}
        AbyssModuleRegistry.anchored(var2, BlockHit.class, "BlockHit", var0);
        AutoClicker var3 = null;
        try {
            var3 = new AutoClicker((short)0, '\u3539', 150955398);
}
        catch (Throwable var10) {
            PENDING.add("Abyss.module.impl.combat.AutoClicker  anchored carrier (0,13625,150955398) threw at publish(): " + var10);
}
        AbyssModuleRegistry.anchored(var3, AutoClicker.class, "AutoClicker", var0);
        InvManager var5 = null;
        try {
            var5 = new InvManager(11682536634362L);
}
        catch (Throwable var12) {
            PENDING.add("Abyss.module.impl.player.InvManager  anchored carrier 11682536634362 threw at publish(): " + var12);
}
        AbyssModuleRegistry.anchored(var5, InvManager.class, "InvManager", var0);
        FakeLag var6 = null;
        try {
            var6 = new FakeLag(0, 0, (short)0);
}
        catch (Throwable var13) {
            PENDING.add("Abyss.module.impl.combat.FakeLag  carrier-free constructor threw at publish(): " + var13);
}
        AbyssModuleRegistry.anchored(var6, FakeLag.class, "FakeLag", var0);
        BedNuker var4 = null;
        try {
            var4 = new BedNuker((short)0, 33171000103266L);
}
        catch (Throwable var11) {
            PENDING.add("Abyss.module.impl.world.BedNuker  anchored carrier (0,33171000103266) threw at publish(): " + var11);
}
        AbyssModuleRegistry.anchored(var4, BedNuker.class, "BedNuker", var0);
}
    private static void publishCachePrepopulated() {
        JsonObject var0 = null;
        try {
            var0 = AbyssConfig.read();
}
        catch (Throwable var2) {
            PENDING.add("AbyssModuleRegistry  config read failed for the pre-populated carriers: " + var2);
}
        try {
            AbyssCtorCache.publish(var0);
}
        catch (Throwable var1) {
            PENDING.add("AbyssCtorCache.publish threw: " + var1);
}
}
    static void publishPrepopulated(Module var0, Class<? extends Module> var1, String var2, JsonObject var3) {
        AbyssModuleRegistry.anchored(var0, var1, var2, var3);
}
    private static void anchored(Module var0, Class<? extends Module> var1, String var2, JsonObject var3) {
        if (var0 == null) {
            return;
}
        try {
            var0.K(var2);
            AbyssSettingStatics.fillFor(var0, var3, null);
}
        catch (Throwable var5) {
            PENDING.add(var1.getName() + "  Setting fill threw at publish(): " + var5);
            return;
}
        if (!AbyssModuleRegistry.settingsUsable(var1)) {
            PENDING.add(var1.getName() + "  HELD: Setting statics still null after fillFor");
            return;
}
        try {
            AbyssModuleRegistry.reg(var0, var1, var2, true);
}
        catch (Throwable var4) {
            PENDING.add(var1.getName() + "  reg() threw at publish(): " + var4);
}
}
    private static void reg(Module var0, Class<? extends Module> var1, String var2, boolean var3) {
        if (var0 == null) {
            throw new IllegalStateException("AbyssModuleRegistry: null instance for " + var1);
}
        if (var3 == var2.startsWith(PLACEHOLDER_PREFIX)) {
            throw new IllegalStateException("AbyssModuleRegistry: name/persistable mismatch for " + var1 + " -> " + var2);
}
        if (var0.name() == null) {
            var0.K(var2);
}
        ModuleManager.o.put(var1, var0);
        if (AbyssModuleRegistry.isRetired(var0.name()) || AbyssModuleRegistry.isRetired(var2)) {
            ++retired;
            var0.I(0L, false);
            var0.M(false);
            PENDING.add(var1.getName() + " retired: constructed and class-addressable, but kept out of ModuleManager.S so it is not shown, ticked or persisted, and forced off so the hooks that read it directly see it as disabled");
            return;
}
        ModuleManager.S.add(var0);
        PUBLISHED.add(var1.getName());
        if (var3) {
            PERSISTABLE.add(var1);
}
}
    private static Set<String> namesOf(Set<Class<? extends Module>> var0) {
        LinkedHashSet<String> r2 = new LinkedHashSet<String>();
        for (Class<? extends Module> c : var0) {
            Module m2 = ModuleManager.o.get(c);
            if (m2 == null) continue;
            r2.add(m2.b());
}
        return r2;
}
    public static boolean isConfigPersistable(Module var0) {
        return var0 != null && var0.b() != null && !var0.b().startsWith(PLACEHOLDER_PREFIX) && PERSISTABLE.contains(var0.getClass());
}
    public static Set<String> persistableNames() {
        return Collections.unmodifiableSet(PERSISTABLE_NAMES);
}
    public static boolean isConfigWritable() {
        return published && countGateGreen && MISSING.isEmpty() && ModuleManager.S != null && ModuleManager.S.size() == AbyssModuleRegistry.expectedModuleCount() && PERSISTABLE.size() == ModuleManager.S.size();
}
    public static void assertConfigWritable() {
        if (!AbyssModuleRegistry.isConfigWritable()) {
            throw new IllegalStateException("Abyss config write refused: " + AbyssModuleRegistry.writableNote());
}
}
    public static String writableNote() {
        int size = ModuleManager.S == null ? -1 : ModuleManager.S.size();
        return "writable=" + AbyssModuleRegistry.isConfigWritable() + " published=" + published + " tD.S=" + size + "/" + AbyssModuleRegistry.expectedModuleCount() + " retired=" + retired + " tD.o=" + (ModuleManager.o == null ? -1 : ModuleManager.o.size()) + " persistable=" + PERSISTABLE.size() + " missing=" + MISSING.size() + " pending=" + PENDING.size() + " countgate=" + (countGateGreen ? "OK" : "REGRESSION");
}
    static {
        PUBLISHED = new ArrayList<String>();
        PENDING = new ArrayList<String>();
        PERSISTABLE = new HashSet<Class<? extends Module>>();
        PERSISTABLE_NAMES = new LinkedHashSet<String>();
        RETIRED = new HashSet<String>();
        RETIRED.add("AntiNick");
        RETIRED.add("ContainerKeeper");
        RETIRED.add("InputFix");
        RETIRED.add("NoObfuscation");
        RETIRED.add("RawInput");
        RETIRED.add("VisualSpoof");
        RETIRED.add("BindGUI");
        RETIRED.add("CaveXray");
        RETIRED.add("ItemScale");
        RETIRED.add("KeyStrokes");
        RETIRED.add("TeamInvisible");
        RETIRED.add("ClosestPlayerHUD");
        RETIRED.add("FKCounter");
        RETIRED.add("FallIndicator");
        RETIRED.add("LeapModeHUD");
        MISSING = new ArrayList<String>();
        DISABLED_UPSTREAM = new String[]{"Abyss.module.impl.combat.AutoProjectiles", "Abyss.module.impl.player.NoFall", "Abyss.module.impl.player.FreeCam"};
}
}