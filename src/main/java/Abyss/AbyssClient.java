/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.play.server.S02PacketChat
 *  net.minecraft.network.play.server.S08PacketPlayerPosLook
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.BlockPos$MutableBlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3i
 */
package Abyss;

import Abyss.command.AbyssCommands;
import Abyss.ASM.Hooks.Entity.EntityRendererHooks;
import Abyss.ASM.Hooks.Render.ItemRendererHooks;
import Abyss.ASM.Hooks.VisGraphHooks;
import Abyss.ASM.Hooks.Block.BlockBarrierHooks;
import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.ASM.Hooks.Gui.GuiScreenHooks;
import Abyss.ASM.Hooks.Render.EffectRendererHooks;
import Abyss.ASM.Hooks.Render.LoadingScreenRendererHooks;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AbyssClientBinder;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.KnockbackEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.MoveFlyingEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.event.events.ClickMouseEvent;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PostRenderEvent;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.MoveFlyingEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.event.events.PostUpdateWalkingPlayerEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.event.events.PreRenderEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.RedirectIsUsingItemEvent;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.event.events.SetAnglesEvent;
import Abyss.internal.accessor.EntityLivingBaseStateAccessor;
import Abyss.internal.accessor.MinecraftAccessor;
import Abyss.internal.accessor.PlayerControllerStateAccessor;
import Abyss.internal.restore.AbyssConfig;
import Abyss.internal.restore.AbyssNameMap;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.Modules;
import Abyss.module.impl.combat.AimAssist;
import Abyss.module.impl.combat.AntiFireball;
import Abyss.module.impl.combat.AutoBlock;
import Abyss.module.impl.combat.AutoClicker;
import Abyss.module.impl.combat.BlockHit;
import Abyss.module.impl.combat.BackTrack;
import Abyss.module.impl.combat.HitBox;
import Abyss.module.impl.combat.HitSelect;
import Abyss.module.impl.combat.JumpReset;
import Abyss.module.impl.combat.LagRange;
import Abyss.module.impl.combat.SprintReset;
import Abyss.module.impl.combat.KeepSprint;
import Abyss.module.impl.combat.KillAura;
import Abyss.module.impl.combat.Velocity;
import Abyss.module.impl.combat.WTap;
import Abyss.module.impl.macro.Macro1;
import Abyss.module.impl.misc.AntiNick;
import Abyss.module.impl.misc.CommandLine;
import Abyss.module.impl.misc.InputFix;
import Abyss.module.impl.misc.NoObfuscation;
import Abyss.module.impl.misc.RawInput;
import Abyss.module.impl.misc.ContainerKeeper;
import Abyss.module.impl.misc.NameHider;
import Abyss.module.impl.misc.Timer;
import Abyss.module.impl.movement.FastFall;
import Abyss.module.impl.movement.Fly;
import Abyss.module.impl.movement.InvMove;
import Abyss.module.impl.movement.NoJumpDelay;
import Abyss.module.impl.movement.NoSlow;
import Abyss.module.impl.movement.Speed;
import Abyss.module.impl.movement.Sprint;
import Abyss.module.impl.player.AutoWeapon;
import Abyss.module.impl.player.Blink;
import Abyss.module.impl.player.InvClicker;
import Abyss.module.impl.player.InvManager;
import Abyss.module.impl.player.NoHitDelay;
import Abyss.module.impl.player.NoInteract;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.module.impl.configuration.CustomCape;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.configuration.Gadgets;
import Abyss.module.impl.configuration.Language;
import Abyss.module.impl.configuration.Notifications;
import Abyss.module.impl.configuration.Theme;
import Abyss.module.impl.configuration.Teams;
import Abyss.module.impl.configuration.VisualSpoof;
import Abyss.module.impl.visual.Ambience;
import Abyss.module.impl.visual.Animations;
import Abyss.module.impl.visual.AntiDebuff;
import Abyss.module.impl.visual.BarrierVisible;
import Abyss.module.impl.visual.BindGUI;
import Abyss.module.impl.visual.Chams;
import Abyss.module.impl.visual.CaveXray;
import Abyss.module.impl.visual.Freelook;
import Abyss.module.impl.visual.FullBright;
import Abyss.module.impl.visual.ItemScale;
import Abyss.module.impl.visual.KeyStrokes;
import Abyss.module.impl.visual.NoHurtCam;
import Abyss.module.impl.visual.TeamInvisible;
import Abyss.module.impl.visual.ViewClip;
import Abyss.module.impl.visual_utility.InventoryHUD;
import Abyss.module.impl.visual_utility.LeapModeHUD;
import Abyss.module.impl.visual_utility.ChestESP;
import Abyss.module.impl.world.AutoTool;
import Abyss.module.impl.world.BedNuker;
import Abyss.module.impl.world.FastPlace;
import Abyss.module.impl.world.Scaffold;
import Abyss.module.impl.world.SpeedMine;
import Abyss.ui.abyss.AbyssArrayListVisibility;
import Abyss.ui.swing.ConfigManagerWindow;
import Abyss.util.AttackTracker;
import Abyss.util.BlockUtil;
import Abyss.util.ClientUtil;
import Abyss.util.ItemUtil;
import Abyss.util.DeferredRendererReload;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.MoveUtil;
import Abyss.util.Pair;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.PlacementTarget;
import Abyss.util.PlayerInfoCache;
import Abyss.util.Sneaky;
import Abyss.util.SmoothMouseHelper;
import Abyss.util.TimerUtil;
import Abyss.util.debug.StallWatchdog;
import Abyss.util.packet.IncomingPacketHold;
import Abyss.util.packet.OutgoingPacketState;
import Abyss.util.packet.PacketManager;
import Abyss.util.render.CustomFont;
import Abyss.util.render.VisualSpoofRenderer;
import Abyss.util.render.abyss.FontManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.FloatBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class AbyssClient
implements EventSubscriber {
    private static Map e;
    private static Object[] l;
    private static String[] m;
    private static String[] d;
    private static Map h;
    private static Long[] j;
    private static long[] f;
    private static Integer[] g;
    private static String[] b;
    private static long a;                private final TimerUtil B;
            private final Minecraft c;
        public static Map<Integer, String> H;
    private boolean s = false;
            public static Set<BlockPos> G;
        private boolean N = false;
    public static ConfigManagerWindow T;
    
    private static Map k;
    private final ScheduledExecutorService U;
    private final BlockPos.MutableBlockPos bedScanPos = new BlockPos.MutableBlockPos();
    private boolean bedScanActive;
    private int bedScanCursor;
    private int bedScanMinX;
    private int bedScanMinY;
    private int bedScanMinZ;
    private int bedScanSpanY;
    private int bedScanSpanZ;
    private int bedScanVolume;
    private int worldFunctionalProbeStage;
    private int worldFunctionalProbeWaitTicks;
    private float worldFunctionalProbeOriginalGamma;
    private boolean persistenceProbeSeeded;
    private boolean persistencePromotedLiveVerified;
    private int categoryLifecycleProbeIndex;
    private int categoryLifecycleProbePhase;
    private int categoryLifecycleProbeWaitTicks;
    private boolean categoryLifecycleProbeOriginalEnabled;
    private float categoryLifecycleProbeHitBoxOppositeBorder;
    private boolean categoryLifecycleProbeHitBoxMeasured;
    private static final String[] CATEGORY_LIFECYCLE_PROBE_MODULES = new String[]{
            "HitBox", "Notifications", "Macro1", "NameHider", "NoJumpDelay",
            "NoHitDelay", "NoHurtCam", "Tracers", "AutoTool"
    };
    private int promotedRegistryProbeIndex;
    private int promotedRegistryProbePhase;
    private int promotedRegistryProbeWaitTicks;
    private boolean promotedRegistryProbeOriginalEnabled;
    private static final String[] PROMOTED_REGISTRY_PROBE_MODULES = new String[]{
            "CustomCape", "Font", "Gadgets", "Language", "Theme",
            "InputFix", "NoObfuscation", "RawInput", "VisualSpoof", "CaveXray", "ItemScale",
            "AntiNick", "ContainerKeeper", "BindGUI", "KeyStrokes", "TeamInvisible",
            "ClosestPlayerHUD", "FKCounter", "FallIndicator", "LeapModeHUD"
    };
    private int eventFunctionalProbeStage;
    private int eventFunctionalProbeWaitTicks;
    private boolean eventFunctionalProbeOriginalEnabled;
    private boolean eventFunctionalProbeOriginalFocus;
    private float eventFunctionalProbeOriginalBlockDelay;
    private int eventFunctionalProbeOriginalRightClickDelay;
    private int eventFunctionalProbeInventorySlot = -1;
    private ItemStack eventFunctionalProbeOriginalItem;
    private int movementFunctionalProbeStage;
    private int movementFunctionalProbeWaitTicks;
    private boolean movementFunctionalProbeOriginalEnabled;
    private float movementFunctionalProbeOriginalSetting;
    private int movementFunctionalProbeOriginalJumpTicks;
    private int playerFunctionalProbeStage;
    private int playerFunctionalProbeWaitTicks;
    private boolean playerFunctionalProbeOriginalEnabled;
    private int playerFunctionalProbeOriginalLeftClickCounter;
    private int combatFunctionalProbeStage;
    private int combatFunctionalProbeWaitTicks;
    private boolean combatFunctionalProbeOriginalEnabled;
    private String combatFunctionalProbeOriginalMode;
    private int combatFunctionalProbeOriginalSlowdown;
    private double combatFunctionalProbeOriginalMotionX;
    private double combatFunctionalProbeOriginalMotionZ;
    private boolean combatFunctionalProbeOriginalSprinting;
    private int combatFunctionalProbeOriginalT;
    private int combatFunctionalProbeOriginalA;
    private int packetFunctionalProbeStage;
    private int packetFunctionalProbeWaitTicks;
    private boolean packetFunctionalProbeOriginalEnabled;
    private float packetFunctionalProbeOriginalTimeSetting;
    private float packetFunctionalProbeOriginalSpeedSetting;
    private long packetFunctionalProbeOriginalWorldTime;
    private int macroFunctionalProbeStage;
    private int macroFunctionalProbeWaitTicks;
    private boolean macroFunctionalProbeOriginalEnabled;
    private String macroFunctionalProbeOriginalMode;
    private String macroFunctionalProbeOriginalMessage;
    private static final String MACRO_FUNCTIONAL_PROBE_SENTINEL = "OPENABYSS_MACRO_PROBE_7E51";
    private int visualUtilityFunctionalProbeStage;
    private int visualUtilityFunctionalProbeWaitTicks;
    private boolean visualUtilityFunctionalProbeOriginalEnabled;
    private ItemStack visualUtilityFunctionalProbeOriginalSlot9;
    private int commandRuntimeProbeStage;
    private int commandRuntimeProbeWaitTicks;
    private boolean commandRuntimeProbeOriginalEnabled;
    private float commandRuntimeProbeOriginalGamma;
    private int commandRuntimeProbeOutputStart;
    private int networkCommandProbeStage;
    private int networkCommandProbeWaitTicks;
    private boolean networkCommandProbeOriginalEnabled;
    private boolean reconnectCommandLineSubscriptionVerified;
    private int clickGuiModeProbeIndex;
    private int clickGuiModeProbePhase;
    private int clickGuiModeProbeWaitTicks;
    private String clickGuiModeProbeOriginalMode;
    private static final String[] CLICKGUI_MODE_PROBE_MODES = new String[]{"STUDIO", "RAVEN", "VESTIGE"};
    private int highRiskFunctionalProbeStage;
    private int highRiskFunctionalProbe2Stage;
    private int highRiskFunctionalProbe3Stage;
    private int highRiskFunctionalProbe4Stage;
    private int highRiskFunctionalProbe5Stage;
    private int highRiskFunctionalProbe5WaitTicks;
    private boolean highRiskFunctionalProbe5OriginalEnabled;
    private boolean highRiskFunctionalProbe5Saved;
    private int highRiskFunctionalProbe6Stage;
    private int highRiskFunctionalProbe6WaitTicks;
    private boolean highRiskFunctionalProbe6OriginalEnabled;
    private int highRiskFunctionalProbe6OriginalEffect;
    private boolean highRiskFunctionalProbe6Saved;
    private int highRiskFunctionalProbe7Stage;
    private int highRiskFunctionalProbe7WaitTicks;
    private boolean highRiskFunctionalProbe7OriginalEnabled;
    private boolean highRiskFunctionalProbe7Saved;
    private int highRiskFunctionalProbe8Stage;
    private int highRiskFunctionalProbe8WaitTicks;
    private boolean highRiskFunctionalProbe8OriginalEnabled;
    private boolean highRiskFunctionalProbe8Saved;
    private int highRiskFunctionalProbe9Stage;
    private int highRiskFunctionalProbe9WaitTicks;
    private boolean highRiskFunctionalProbe9OriginalEnabled;
    private boolean highRiskFunctionalProbe9OriginalNoRotations;
    private boolean highRiskFunctionalProbe9Saved;
    private int highRiskFunctionalProbe10Stage;
    private int highRiskFunctionalProbe10WaitTicks;
    private boolean highRiskFunctionalProbe10OriginalEnabled;
    private float highRiskFunctionalProbe10OriginalSetting;
    private float highRiskFunctionalProbe10OriginalTimerSpeed;
    private float highRiskFunctionalProbe10DisabledBaseline;
    private boolean highRiskFunctionalProbe10Saved;
    private int highRiskFunctionalProbe11Stage;
    private int highRiskFunctionalProbe11WaitTicks;
    private boolean highRiskFunctionalProbe11OriginalEnabled;
    private boolean highRiskFunctionalProbe11OriginalPolygonOffsetEnabled;
    private float highRiskFunctionalProbe11OriginalPolygonOffsetFactor;
    private float highRiskFunctionalProbe11OriginalPolygonOffsetUnits;
    private boolean highRiskFunctionalProbe11Saved;
    private int highRiskFunctionalProbe12Stage;
    private int highRiskFunctionalProbe12WaitTicks;
    private boolean highRiskFunctionalProbe12OriginalEnabled;
    private float highRiskFunctionalProbe12OriginalYaw;
    private float highRiskFunctionalProbe12OriginalPitch;
    private int highRiskFunctionalProbe12OriginalThirdPersonView;
    private boolean highRiskFunctionalProbe12OriginalActive;
    private float highRiskFunctionalProbe12OriginalCameraYaw;
    private float highRiskFunctionalProbe12OriginalCameraPitch;
    private int highRiskFunctionalProbe12OriginalSavedView;
    private float highRiskFunctionalProbe12OriginalSavedYaw;
    private float highRiskFunctionalProbe12OriginalSavedPitch;
    private boolean highRiskFunctionalProbe12Saved;
    private int highRiskFunctionalProbe13Stage;
    private int highRiskFunctionalProbe13WaitTicks;
    private boolean highRiskFunctionalProbe13OriginalEnabled;
    private boolean highRiskFunctionalProbe13OriginalViewClipEnabled;
    private boolean highRiskFunctionalProbe13OriginalReloadRenderer;
    private boolean highRiskFunctionalProbe13Saved;
    private int highRiskFunctionalProbe14Stage;
    private int highRiskFunctionalProbe15Stage;
    private int highRiskFunctionalProbe16Stage;
    private int highRiskFunctionalProbe17Stage;
    private int highRiskFunctionalProbe18Stage;
    private int highRiskFunctionalProbe19Stage;
    private int highRiskFunctionalProbe20Stage;
    private int highRiskFunctionalProbe21Stage;
    private int highRiskFunctionalProbe22Stage;
    private int highRiskFunctionalProbe23Stage;
    private int highRiskFunctionalProbe24Stage;
    private int highRiskFunctionalProbe25Stage;
    private int highRiskFunctionalProbe26Stage;
    private int highRiskFunctionalProbe27Stage;
    private int highRiskFunctionalProbe28Stage;
    private int highRiskFunctionalProbe29Stage;
    private int highRiskFunctionalProbe29WaitTicks;
    private int highRiskFunctionalProbe30Stage;
    private int highRiskFunctionalProbe31Stage;
    private int highRiskFunctionalProbe32Stage;
    private int highRiskFunctionalProbe32WaitTicks;
    private boolean highRiskFunctionalProbe32Saved;
    private boolean highRiskFunctionalProbe32EnableStateSaved;
    private boolean highRiskFunctionalProbe32SavedEnabled;
    private BlockPos highRiskFunctionalProbe32Fixture;
    private IBlockState highRiskFunctionalProbe32OriginalState;
    private ItemStack highRiskFunctionalProbe32SavedSlot0;
    private ItemStack highRiskFunctionalProbe32SavedSlot4;
    private int highRiskFunctionalProbe32SavedCurrentItem;
    private MovingObjectPosition highRiskFunctionalProbe32SavedMouseOver;
    private boolean highRiskFunctionalProbe32SavedPriority;
    private boolean highRiskFunctionalProbe32SavedDisableSword;
    private boolean highRiskFunctionalProbe32SavedSwitchBackToSword;
    private boolean highRiskFunctionalProbe32SavedSwitchBack;
    private boolean highRiskFunctionalProbe32SavedRequireSneak;
    private float highRiskFunctionalProbe32SavedDelay;
    private boolean highRiskFunctionalProbe32SavedSwitching;
    private int highRiskFunctionalProbe32SavedOriginalSlot;
    private boolean highRiskFunctionalProbe32SavedPrimed;
    private long highRiskFunctionalProbe32SavedTimerStart;
    private int highRiskFunctionalProbe33Stage;
    private int highRiskFunctionalProbe33WaitTicks;
    private boolean highRiskFunctionalProbe33Saved;
    private boolean highRiskFunctionalProbe33OriginalEnabled;
    private String highRiskFunctionalProbe33OriginalStrategy;
    private boolean highRiskFunctionalProbe33OriginalAttackGate;
    private Object highRiskFunctionalProbe33OriginalTarget;
    private int highRiskFunctionalProbe33OriginalTargetTicks;
    private int highRiskFunctionalProbe33OriginalPauseTicks;
    private int highRiskFunctionalProbe34Stage;
    private int highRiskFunctionalProbe34WaitTicks;
    private boolean highRiskFunctionalProbe34Saved;
    private boolean highRiskFunctionalProbe34OriginalEnabled;
    private boolean highRiskFunctionalProbe34OriginalLock;
    private float highRiskFunctionalProbe34OriginalHorizontalSpeed;
    private float highRiskFunctionalProbe34OriginalVerticalSpeed;
    private boolean highRiskFunctionalProbe34OriginalPlayers;
    private boolean highRiskFunctionalProbe34OriginalMobs;
    private boolean highRiskFunctionalProbe34OriginalAnimals;
    private boolean highRiskFunctionalProbe34OriginalBosses;
    private boolean highRiskFunctionalProbe34OriginalFriends;
    private boolean highRiskFunctionalProbe34OriginalEnemies;
    private boolean highRiskFunctionalProbe34OriginalTeammates;
    private boolean highRiskFunctionalProbe34OriginalBots;
    private boolean highRiskFunctionalProbe34OriginalBreakBlocks;
    private boolean highRiskFunctionalProbe34OriginalSwordOnly;
    private boolean highRiskFunctionalProbe34OriginalIgnoreBehindWall;
    private float highRiskFunctionalProbe34OriginalFov;
    private float highRiskFunctionalProbe34OriginalRange;
    private String highRiskFunctionalProbe34OriginalSort;
    private float highRiskFunctionalProbe34OriginalYaw;
    private float highRiskFunctionalProbe34OriginalPitch;
    private Object highRiskFunctionalProbe34OriginalCachedAngles;
    private int highRiskFunctionalProbe34FixtureId;
    private int highRiskFunctionalProbe35Stage;
    private int highRiskFunctionalProbe35WaitTicks;
    private boolean highRiskFunctionalProbe35Saved;
    private boolean highRiskFunctionalProbe35OriginalEnabled;
    private int highRiskFunctionalProbe35OriginalChance;
    private boolean highRiskFunctionalProbe35OriginalRequireMoving;
    private boolean highRiskFunctionalProbe35OriginalReduce;
    private boolean highRiskFunctionalProbe35OriginalPlayers;
    private boolean highRiskFunctionalProbe35OriginalMobs;
    private boolean highRiskFunctionalProbe35OriginalAnimals;
    private boolean highRiskFunctionalProbe35OriginalBosses;
    private boolean highRiskFunctionalProbe35OriginalFriends;
    private boolean highRiskFunctionalProbe35OriginalEnemies;
    private boolean highRiskFunctionalProbe35OriginalTeammates;
    private boolean highRiskFunctionalProbe35OriginalBots;
    private float highRiskFunctionalProbe35OriginalFov;
    private float highRiskFunctionalProbe35OriginalRange;
    private boolean highRiskFunctionalProbe35OriginalSprinting;
    private int highRiskFunctionalProbe35FixtureId;
    private int highRiskFunctionalProbe36Stage;
    private int highRiskFunctionalProbe36WaitTicks;
    private boolean highRiskFunctionalProbe36Saved;
    private boolean highRiskFunctionalProbe36OriginalEnabled;
    private String highRiskFunctionalProbe36OriginalMode;
    private float highRiskFunctionalProbe36OriginalInterval;
    private float highRiskFunctionalProbe36OriginalDuration;
    private boolean highRiskFunctionalProbe36OriginalRequireTargetDamage;
    private boolean highRiskFunctionalProbe36OriginalSprinting;
    private float highRiskFunctionalProbe36OriginalMoveForward;
    private float highRiskFunctionalProbe36OriginalMoveStrafe;
    private boolean highRiskFunctionalProbe36OriginalForwardBinding;
    private int highRiskFunctionalProbe36FixtureId;
    private int highRiskFunctionalProbe37Stage;
    private boolean highRiskFunctionalProbe37Saved;
    private boolean highRiskFunctionalProbe37OriginalEnabled;
    private boolean highRiskFunctionalProbe37OriginalSwing;
    private float highRiskFunctionalProbe37OriginalRange;
    private float highRiskFunctionalProbe37OriginalFov;
    private String highRiskFunctionalProbe37OriginalMoveFix;
    private boolean highRiskFunctionalProbe37OriginalAllowFlying;
    private int highRiskFunctionalProbe37CloseFixtureId;
    private int highRiskFunctionalProbe37FarFixtureId;
    private EntityLargeFireball highRiskFunctionalProbe37FarFixture;
    private int highRiskFunctionalProbe37WaitTicks;
    private int highRiskFunctionalProbe38Stage;
    private int highRiskFunctionalProbe38WaitTicks;
    private boolean highRiskFunctionalProbe38Saved;
    private boolean highRiskFunctionalProbe38OriginalEnabled;
    private String highRiskFunctionalProbe38OriginalMode;
    private float highRiskFunctionalProbe38OriginalHurtTicks;
    private float highRiskFunctionalProbe38OriginalEarlyTicks;
    private float highRiskFunctionalProbe38OriginalRandomTicks;
    private boolean highRiskFunctionalProbe38OriginalE;
    private boolean highRiskFunctionalProbe38OriginalX;
    private int highRiskFunctionalProbe38OriginalY;
    private int highRiskFunctionalProbe38OriginalSmallY;
    private int highRiskFunctionalProbe39Stage;
    private int highRiskFunctionalProbe39WaitTicks;
    private boolean highRiskFunctionalProbe39Saved;
    private boolean highRiskFunctionalProbe39OriginalEnabled;
    private float highRiskFunctionalProbe39OriginalDelay;
    private int highRiskFunctionalProbe39OriginalDelayChance;
    private BlockPos highRiskFunctionalProbe39OriginalLastBlock;
    private float highRiskFunctionalProbe39OriginalPreviousDamage;
    private int highRiskFunctionalProbe39OriginalBlockHitDelay;
    private boolean highRiskFunctionalProbe39OriginalInGameHasFocus;
    private int highRiskFunctionalProbe40Stage;
    private int highRiskFunctionalProbe40WaitTicks;
    private boolean highRiskFunctionalProbe40Saved;
    private boolean highRiskFunctionalProbe40OriginalEnabled;
    private boolean highRiskFunctionalProbe40OriginalAxe;
    private boolean highRiskFunctionalProbe40OriginalStick;
    private boolean highRiskFunctionalProbe40OriginalFishingRod;
    private ItemStack highRiskFunctionalProbe40OriginalSlot0;
    private ItemStack highRiskFunctionalProbe40OriginalSlot4;
    private int highRiskFunctionalProbe40OriginalCurrentItem;
    private MovingObjectPosition highRiskFunctionalProbe40OriginalMouseOver;
    private MovingObjectPosition highRiskFunctionalProbe40FixtureMouseOver;
    private int highRiskFunctionalProbe41Stage;
    private int highRiskFunctionalProbe41WaitTicks;
    private boolean highRiskFunctionalProbe41Saved;
    private boolean highRiskFunctionalProbe41OriginalEnabled;
    private boolean highRiskFunctionalProbe41OriginalPlayers;
    private boolean highRiskFunctionalProbe41OriginalMobs;
    private boolean highRiskFunctionalProbe41OriginalAnimals;
    private boolean highRiskFunctionalProbe41OriginalBosses;
    private boolean highRiskFunctionalProbe41OriginalFriends;
    private boolean highRiskFunctionalProbe41OriginalEnemies;
    private boolean highRiskFunctionalProbe41OriginalTeammates;
    private boolean highRiskFunctionalProbe41OriginalBots;
    private boolean highRiskFunctionalProbe41OriginalSwordOnly;
    private float highRiskFunctionalProbe41OriginalDelay;
    private float highRiskFunctionalProbe41OriginalTargetRange;
    private float highRiskFunctionalProbe41OriginalDisableRange;
    private float highRiskFunctionalProbe41OriginalFov;
    private boolean highRiskFunctionalProbe41OriginalPacketBuffer;
    private int highRiskFunctionalProbe41FixtureId;
    private int highRiskFunctionalProbe42Stage;
    private int highRiskFunctionalProbe42WaitTicks;
    private boolean highRiskFunctionalProbe42Saved;
    private boolean highRiskFunctionalProbe42OriginalEnabled;
    private float highRiskFunctionalProbe42OriginalHorizontalSpeed;
    private float highRiskFunctionalProbe42OriginalVerticalSpeed;
    private boolean highRiskFunctionalProbe42OriginalSneakPressed;
    private int highRiskFunctionalProbe43Stage;
    private int highRiskFunctionalProbe43WaitTicks;
    private boolean highRiskFunctionalProbe43Saved;
    private boolean highRiskFunctionalProbe43OriginalEnabled;
    private boolean highRiskFunctionalProbe43OriginalPriority;
    private boolean[] highRiskFunctionalProbe43OriginalPriorityEntries;
    private String highRiskFunctionalProbe43OriginalMode;
    private boolean[] highRiskFunctionalProbe43OriginalBooleans;
    private float[] highRiskFunctionalProbe43OriginalNumbers;
    private ItemStack[] highRiskFunctionalProbe43OriginalMainInventory;
    private int highRiskFunctionalProbe43OriginalCurrentItem;
    private net.minecraft.client.gui.GuiScreen highRiskFunctionalProbe43OriginalScreen;
    private int highRiskFunctionalProbe44Stage;
    private int highRiskFunctionalProbe44WaitTicks;
    private boolean highRiskFunctionalProbe44Saved;
    private boolean highRiskFunctionalProbe44OriginalEnabled;
    private boolean highRiskFunctionalProbe44OriginalAlwaysClick;
    private float highRiskFunctionalProbe44OriginalCps;
    private ItemStack highRiskFunctionalProbe44OriginalSlot0;
    private ItemStack highRiskFunctionalProbe44OriginalSlot9;
    private ItemStack highRiskFunctionalProbe44OriginalCursor;
    private int highRiskFunctionalProbe44OriginalCurrentItem;
    private net.minecraft.client.gui.GuiScreen highRiskFunctionalProbe44OriginalScreen;
    private net.minecraft.world.WorldSettings.GameType highRiskFunctionalProbe44OriginalGameType;
    private boolean highRiskFunctionalProbe29Saved;
    private boolean highRiskFunctionalProbe29OriginalEnabled;
    private int highRiskFunctionalProbe25WaitTicks;
    private boolean highRiskFunctionalProbe25OriginalEnabled;
    private boolean highRiskFunctionalProbe25Saved;
    private long highRiskFunctionalProbe25TakeoverBaseline;
    private boolean highRiskFunctionalProbe22Saved;
    private boolean highRiskFunctionalProbe22OriginalRawEnabled;
    private MouseHelper highRiskFunctionalProbe22OriginalMouseHelper;
    private ScheduledExecutorService highRiskFunctionalProbe22RawExecutor;
    private boolean highRiskFunctionalProbe22OriginalDisableRenderVisual;
    private boolean highRiskFunctionalProbe22OriginalScreenshotBypass;
    private String highRiskFunctionalProbe22OriginalTheme;
    private String highRiskFunctionalProbe22OriginalCustomTheme;
    private String highRiskFunctionalProbe22OriginalColor1;
    private String highRiskFunctionalProbe22OriginalColor2;
    private String highRiskFunctionalProbe22OriginalColor3;
    private int invMovePhysicalProbeStage;
    private int invMovePhysicalProbeWaitTicks;
    private boolean invMovePhysicalOriginalEnabled;
    private String invMovePhysicalOriginalInventoryMode;
    private String invMovePhysicalOriginalContainerMode;
    private boolean invMovePhysicalOriginalForwardPressed;
    private boolean invMovePhysicalSaved;
    private int physicalInputFunctionalProbeStage;
    private int physicalInputFunctionalProbeWaitTicks;
    private boolean physicalInputAutoSaved;
    private boolean physicalInputAutoOriginalEnabled;
    private boolean physicalInputAutoOriginalBreakBlocks;
    private boolean physicalInputAutoOriginalSag;
    private boolean physicalInputFastFallSaved;
    private boolean physicalInputFastFallOriginalEnabled;
    private boolean physicalInputFastFallOriginalRequireScaffold;
    private boolean physicalInputFastFallOriginalHorizontalRestriction;
    private boolean physicalInputPlayerStateSaved;
    private boolean physicalInputOriginalOnGround;
    private double physicalInputOriginalMotionY;
    private static long[] i;
    public static String I;
    private static final byte[] KEY_OFFSETS;
    public static EventBus w;
    private static final Set<String> RUNTIME_MILESTONES = new CopyOnWriteArraySet<String>();
    private long runtimeWorldHeartbeatTicks;
    private int runtimeWorldSessionCount;
    private boolean runtimeWorldSessionActive;
    private static final Set<String> MODULE_FAILURE_SIGNATURES = new CopyOnWriteArraySet<String>();
    private static final Set<String> FEATURE_FAILURE_SIGNATURES = new CopyOnWriteArraySet<String>();

    private static void runtimeMilestone(String name) {
        if (name == null || !RUNTIME_MILESTONES.add(name)) {
            return;
}
        try {
            try (OutputStreamWriter out = new OutputStreamWriter((OutputStream)new FileOutputStream(new File("abyss-runtime-stage.txt"), true), "UTF-8");){
                out.write(System.currentTimeMillis() + "\t" + name + "\n");
}
}
        catch (Throwable ignored) {
}
}
    private static void moduleFailure(String phase, Module module, Throwable failure) {
        String name = "<null>";
        try {
            if (module != null && module.b() != null) {
                name = module.b();
}
}
        catch (Throwable ignored) {
}
        String message = String.valueOf(failure.getMessage()).replace('\r', ' ').replace('\n', ' ');
        String signature = phase + "|" + name + "|" + failure.getClass().getName() + "|" + message;
        if (!MODULE_FAILURE_SIGNATURES.add(signature)) {
            return;
}
        String line = System.currentTimeMillis() + "\t" + phase + "\t" + name + "\t"
                + failure.getClass().getName() + "\t" + message;
        try {
            try (OutputStreamWriter out = new OutputStreamWriter((OutputStream)new FileOutputStream(new File("abyss-module-failure.txt"), true), "UTF-8");){
                out.write(line + "\n");
}
}
        catch (Throwable ignored) {
}
        System.err.println("[ABYSSDIAG] module failure " + line);
}
    public static void recordFeatureFailure(String owner, String operation, Throwable failure) {
        String safeOwner = owner == null ? "<unknown>" : owner;
        String safeOperation = operation == null ? "<unknown>" : operation;
        String failureType = failure == null ? "<null>" : failure.getClass().getName();
        String message = failure == null ? "<null>"
                : String.valueOf(failure.getMessage()).replace('\r', ' ').replace('\n', ' ');
        String signature = safeOwner + "|" + safeOperation + "|" + failureType + "|" + message;
        if (!FEATURE_FAILURE_SIGNATURES.add(signature)) {
            return;
}
        String line = System.currentTimeMillis() + "\t" + safeOwner + "\t" + safeOperation + "\t"
                + failureType + "\t" + message;
        try {
            try (OutputStreamWriter out = new OutputStreamWriter(
                    (OutputStream)new FileOutputStream(new File("abyss-feature-failure.txt"), true), "UTF-8")) {
                out.write(line + "\n");
}
}
        catch (Throwable ignored) {
}
        System.err.println("[ABYSSDIAG] feature failure " + line);
}

    private float measureHitBoxProbeBorder(String phase) throws Throwable {
        if (this.c.theWorld == null) {
            throw new IllegalStateException("HitBox probe has no client world");
}
        if (HitBox.mobs == null || HitBox.expand == null) {
            throw new IllegalStateException("HitBox settings are unavailable");
}
        boolean originalMobs = HitBox.mobs.c();
        float originalExpand = HitBox.expand.L();
        try {
            HitBox.mobs.v(true, 0L);
            HitBox.expand.o((byte)0, 0L, 0.35f);
            EntityZombie target = new EntityZombie(this.c.theWorld);
            float border = target.getCollisionBorderSize();
            runtimeMilestone("category-lifecycle-probe-hitbox-border:" + phase
                    + ":enabled=" + ModuleManager.r.o() + ":border=" + border + ":expand=" + HitBox.expand.L());
            return border;
}
        finally {
            HitBox.mobs.v(originalMobs, 0L);
            HitBox.expand.o((byte)0, 0L, originalExpand);
}
}

    private void verifyNameHiderProbeEffect(boolean expectedEnabled, String phase) {
        if (this.c.thePlayer == null) {
            throw new IllegalStateException("NameHider probe has no local player");
}
        String playerName = this.c.thePlayer.getName();
        if (playerName == null || playerName.length() == 0) {
            throw new IllegalStateException("NameHider probe local player has no name");
}
        if (NameHider.name == null || NameHider.name.X() == null) {
            throw new IllegalStateException("NameHider replacement setting is unavailable");
}
        String input = "OPENABYSS_NAMEHIDER_" + playerName + "_END";
        String expected = expectedEnabled
                ? input.replace(playerName, NameHider.name.X())
                : input;
        String actual = NameHider.U(input);
        if (!expected.equals(actual)) {
            throw new IllegalStateException("NameHider transform mismatch phase=" + phase
                    + " enabled=" + expectedEnabled + " expected=" + expected + " actual=" + actual);
}
        runtimeMilestone("category-lifecycle-probe-namehider-effect-pass:" + phase
                + ":enabled=" + expectedEnabled + ":alias=" + NameHider.name.X());
}

    private void pumpCategoryLifecycleProbe() {
        if (!Boolean.getBoolean("abyss.categoryLifecycleProbe")
                || this.categoryLifecycleProbeIndex >= CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
}
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
}
        String name = CATEGORY_LIFECYCLE_PROBE_MODULES[this.categoryLifecycleProbeIndex];
        try {
            Module probe = ModuleManager.byName(name);
            if (probe == null) {
                throw new IllegalStateException("Module is missing: " + name);
}
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);

            if (this.categoryLifecycleProbePhase == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.categoryLifecycleProbeWaitTicks > 100) {
                        throw new IllegalStateException("Initial module state did not settle: " + name
                                + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                                + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                                + " ownerActive=" + w.isOwnerActive(probe));
}
                    return;
}
                this.categoryLifecycleProbeOriginalEnabled = stableEnabled;
                this.categoryLifecycleProbeWaitTicks = 0;
                probe.I(0L, !this.categoryLifecycleProbeOriginalEnabled);
                this.categoryLifecycleProbePhase = 1;
                runtimeMilestone("category-lifecycle-probe-transition-request:" + name
                        + ":target=" + (!this.categoryLifecycleProbeOriginalEnabled));
                return;
}

            if (this.categoryLifecycleProbePhase == 1) {
                boolean targetReached = this.categoryLifecycleProbeOriginalEnabled ? stableDisabled : stableEnabled;
                if (!targetReached) {
                    if (++this.categoryLifecycleProbeWaitTicks > 100) {
                        throw new IllegalStateException("Opposite module state timed out: " + name
                                + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                                + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                                + " ownerActive=" + w.isOwnerActive(probe));
}
                    return;
}
                runtimeMilestone("category-lifecycle-probe-opposite-pass:" + name
                        + ":enabled=" + probe.o());
                if ("NameHider".equals(name)) {
                    this.verifyNameHiderProbeEffect(probe.o(), "opposite");
}
                if ("HitBox".equals(name)) {
                    this.categoryLifecycleProbeHitBoxOppositeBorder = this.measureHitBoxProbeBorder("opposite");
                    this.categoryLifecycleProbeHitBoxMeasured = true;
}
                this.categoryLifecycleProbeWaitTicks = 0;
                probe.I(0L, this.categoryLifecycleProbeOriginalEnabled);
                this.categoryLifecycleProbePhase = 2;
                return;
}

            boolean restored = this.categoryLifecycleProbeOriginalEnabled ? stableEnabled : stableDisabled;
            if (!restored) {
                if (++this.categoryLifecycleProbeWaitTicks > 100) {
                    throw new IllegalStateException("Original module state restore timed out: " + name
                            + " originalEnabled=" + this.categoryLifecycleProbeOriginalEnabled
                            + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                            + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                            + " ownerActive=" + w.isOwnerActive(probe));
}
                return;
}
            if ("NameHider".equals(name)) {
                this.verifyNameHiderProbeEffect(this.categoryLifecycleProbeOriginalEnabled, "restored");
}
            if ("HitBox".equals(name)) {
                if (!this.categoryLifecycleProbeHitBoxMeasured) {
                    throw new IllegalStateException("HitBox opposite border was not measured");
}
                float restoredBorder = this.measureHitBoxProbeBorder("restored");
                float enabledBorder = this.categoryLifecycleProbeOriginalEnabled
                        ? restoredBorder : this.categoryLifecycleProbeHitBoxOppositeBorder;
                float disabledBorder = this.categoryLifecycleProbeOriginalEnabled
                        ? this.categoryLifecycleProbeHitBoxOppositeBorder : restoredBorder;
                float delta = enabledBorder - disabledBorder;
                if (Math.abs(delta - 0.35f) > 0.001f) {
                    throw new IllegalStateException("HitBox collision border delta mismatch"
                            + " enabled=" + enabledBorder + " disabled=" + disabledBorder
                            + " delta=" + delta + " expected=0.35");
}
                runtimeMilestone("category-lifecycle-probe-hitbox-effect-pass:delta=" + delta);
                this.categoryLifecycleProbeHitBoxMeasured = false;
}
            runtimeMilestone("category-lifecycle-probe-module-pass:" + name
                    + ":restored=" + this.categoryLifecycleProbeOriginalEnabled);
            ++this.categoryLifecycleProbeIndex;
            this.categoryLifecycleProbePhase = 0;
            this.categoryLifecycleProbeWaitTicks = 0;
            if (this.categoryLifecycleProbeIndex >= CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
                runtimeMilestone("category-lifecycle-probe-pass:" + CATEGORY_LIFECYCLE_PROBE_MODULES.length);
}
}
        catch (Throwable failure) {
            this.categoryLifecycleProbeIndex = CATEGORY_LIFECYCLE_PROBE_MODULES.length;
            recordFeatureFailure("CategoryLifecycleProbe:" + name, "transition-restore", failure);
            runtimeMilestone("category-lifecycle-probe-fail:" + name + ":" + failure.getClass().getName());
}
}


    private void pumpPromotedRegistryProbe() {
        if (!Boolean.getBoolean("abyss.promotedRegistryProbe")
                || this.promotedRegistryProbeIndex >= PROMOTED_REGISTRY_PROBE_MODULES.length) {
            return;
}
        if (this.runtimeWorldHeartbeatTicks < 250L) {
            return;
}
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
}
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.eventFunctionalProbe") && this.eventFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.movementFunctionalProbe") && this.movementFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.playerFunctionalProbe") && this.playerFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.combatFunctionalProbe") && this.combatFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.packetFunctionalProbe") && this.packetFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.macroFunctionalProbe") && this.macroFunctionalProbeStage < 4) {
            return;
}
        if (Boolean.getBoolean("abyss.visualUtilityFunctionalProbe")
                && this.visualUtilityFunctionalProbeStage < 4) {
            return;
}
        if (Boolean.getBoolean("abyss.commandRuntimeProbe") && this.commandRuntimeProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.networkCommandProbe") && this.networkCommandProbeStage < 4) {
            return;
}
        if (Boolean.getBoolean("abyss.clickGuiModeProbe")
                && this.clickGuiModeProbeIndex < CLICKGUI_MODE_PROBE_MODES.length) {
            return;
}
        String name = PROMOTED_REGISTRY_PROBE_MODULES[this.promotedRegistryProbeIndex];
        Module probe = null;
        try {
            probe = ModuleManager.byName(name);
            if (probe == null) {
                throw new IllegalStateException("Promoted module is missing: " + name);
}
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K()
                    && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K()
                    && !probe.P() && !w.isOwnerActive(probe);

            if (this.promotedRegistryProbePhase == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.promotedRegistryProbeWaitTicks > 120) {
                        throw new IllegalStateException("Initial promoted module state did not settle: " + name
                                + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                                + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                                + " ownerActive=" + w.isOwnerActive(probe));
}
                    return;
}
                this.promotedRegistryProbeOriginalEnabled = stableEnabled;
                this.promotedRegistryProbeWaitTicks = 0;
                probe.I(0L, !this.promotedRegistryProbeOriginalEnabled);
                this.promotedRegistryProbePhase = 1;
                runtimeMilestone("promoted-registry-probe-transition-request:" + name
                        + ":target=" + (!this.promotedRegistryProbeOriginalEnabled));
                return;
}

            if (this.promotedRegistryProbePhase == 1) {
                boolean targetReached = this.promotedRegistryProbeOriginalEnabled ? stableDisabled : stableEnabled;
                if (!targetReached) {
                    if (++this.promotedRegistryProbeWaitTicks > 120) {
                        throw new IllegalStateException("Opposite promoted module state timed out: " + name
                                + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                                + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                                + " ownerActive=" + w.isOwnerActive(probe));
}
                    return;
}
                runtimeMilestone("promoted-registry-probe-opposite-pass:" + name
                        + ":enabled=" + probe.o()
                        + ":subscribed=" + probe.P()
                        + ":ownerActive=" + w.isOwnerActive(probe));
                this.promotedRegistryProbeWaitTicks = 0;
                probe.I(0L, this.promotedRegistryProbeOriginalEnabled);
                this.promotedRegistryProbePhase = 2;
                return;
}

            boolean restored = this.promotedRegistryProbeOriginalEnabled ? stableEnabled : stableDisabled;
            if (!restored) {
                if (++this.promotedRegistryProbeWaitTicks > 120) {
                    throw new IllegalStateException("Promoted module restore timed out: " + name
                            + " originalEnabled=" + this.promotedRegistryProbeOriginalEnabled
                            + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                            + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                            + " ownerActive=" + w.isOwnerActive(probe));
}
                return;
}
            runtimeMilestone("promoted-registry-probe-module-pass:" + name
                    + ":restored=" + this.promotedRegistryProbeOriginalEnabled);
            ++this.promotedRegistryProbeIndex;
            this.promotedRegistryProbePhase = 0;
            this.promotedRegistryProbeWaitTicks = 0;
            if (this.promotedRegistryProbeIndex >= PROMOTED_REGISTRY_PROBE_MODULES.length) {
                runtimeMilestone("promoted-registry-probe-pass:" + PROMOTED_REGISTRY_PROBE_MODULES.length);
}
}
        catch (Throwable failure) {
            if (probe != null) {
                try {
                    if (probe.o() != this.promotedRegistryProbeOriginalEnabled || probe.l() || probe.K()) {
                        probe.I(0L, this.promotedRegistryProbeOriginalEnabled);
}
}
                catch (Throwable restoreFailure) {
                    recordFeatureFailure("PromotedRegistryProbe:" + name, "restore-after-failure", restoreFailure);
}
}
            this.promotedRegistryProbeIndex = PROMOTED_REGISTRY_PROBE_MODULES.length;
            recordFeatureFailure("PromotedRegistryProbe:" + name, "transition-restore", failure);
            runtimeMilestone("promoted-registry-probe-fail:" + name + ":" + failure.getClass().getName());
}
}

    private void restoreEventFunctionalProbeState(FastPlace probe) {
        try {
            if (FastPlace.blockDelay != null) {
                FastPlace.blockDelay.o((byte)0, 0L, this.eventFunctionalProbeOriginalBlockDelay);
            }
            if (this.eventFunctionalProbeInventorySlot >= 0 && this.c.thePlayer != null
                    && this.c.thePlayer.inventory != null
                    && this.eventFunctionalProbeInventorySlot < this.c.thePlayer.inventory.mainInventory.length) {
                this.c.thePlayer.inventory.mainInventory[this.eventFunctionalProbeInventorySlot] = this.eventFunctionalProbeOriginalItem;
            }
            this.c.inGameHasFocus = this.eventFunctionalProbeOriginalFocus;
            MinecraftAccessor.j(0L, this.c, this.eventFunctionalProbeOriginalRightClickDelay);
            if (probe != null && probe.o() != this.eventFunctionalProbeOriginalEnabled) {
                probe.I(0L, this.eventFunctionalProbeOriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure("EventFunctionalProbe:FastPlace", "restore", restoreFailure);
        }
    }

    private void verifyEventFunctionalProbeRestored() throws Exception {
        if (FastPlace.blockDelay == null
                || Math.abs(FastPlace.blockDelay.L() - this.eventFunctionalProbeOriginalBlockDelay) > 0.001f) {
            throw new IllegalStateException("FastPlace Block-Delay was not restored");
        }
        if (this.c.inGameHasFocus != this.eventFunctionalProbeOriginalFocus) {
            throw new IllegalStateException("Minecraft focus state was not restored after FastPlace probe");
        }
        if (MinecraftAccessor.C(this.c) != this.eventFunctionalProbeOriginalRightClickDelay) {
            throw new IllegalStateException("rightClickDelayTimer was not restored after FastPlace probe");
        }
        if (this.eventFunctionalProbeInventorySlot >= 0 && this.c.thePlayer != null
                && this.c.thePlayer.inventory != null
                && this.c.thePlayer.inventory.mainInventory[this.eventFunctionalProbeInventorySlot]
                        != this.eventFunctionalProbeOriginalItem) {
            throw new IllegalStateException("Held inventory slot was not restored after FastPlace probe");
        }
        runtimeMilestone("event-functional-probe-restore-state-pass:FastPlace");
    }

    private void pumpEventFunctionalProbe() {
        if (!Boolean.getBoolean("abyss.eventFunctionalProbe")
                || this.eventFunctionalProbeStage < 0
                || this.eventFunctionalProbeStage >= 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
        }

        FastPlace probe = Modules.J(FastPlace.class);
        try {
            if (probe == null || FastPlace.blockDelay == null) {
                throw new IllegalStateException("FastPlace module/Block-Delay setting is missing");
            }
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);

            if (this.eventFunctionalProbeStage == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.eventFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("Initial FastPlace state did not settle");
                    }
                    return;
                }
                this.eventFunctionalProbeOriginalEnabled = stableEnabled;
                this.eventFunctionalProbeOriginalFocus = this.c.inGameHasFocus;
                this.eventFunctionalProbeOriginalBlockDelay = FastPlace.blockDelay.L();
                this.eventFunctionalProbeOriginalRightClickDelay = MinecraftAccessor.C(this.c);
                this.eventFunctionalProbeInventorySlot = this.c.thePlayer.inventory.currentItem;
                this.eventFunctionalProbeOriginalItem =
                        this.c.thePlayer.inventory.mainInventory[this.eventFunctionalProbeInventorySlot];

                if (!stableEnabled) {
                    probe.I(0L, true);
                    this.eventFunctionalProbeStage = 1;
                    this.eventFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("event-functional-probe-enable-request:FastPlace");
                    return;
                }
                this.eventFunctionalProbeStage = 1;
            }

            if (this.eventFunctionalProbeStage == 1) {
                if (!stableEnabled) {
                    if (++this.eventFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("FastPlace did not enable/subscribe");
                    }
                    return;
                }

                FastPlace.blockDelay.o((byte)0, 0L, 1.0f);
                this.c.thePlayer.inventory.mainInventory[this.eventFunctionalProbeInventorySlot] =
                        new ItemStack(Blocks.stone);
                this.c.inGameHasFocus = true;
                MinecraftAccessor.j(0L, this.c, 4);
                runtimeMilestone("event-functional-probe-dispatch:FastPlace:rightClickDelay=4");
                w.e(new PreUpdateEvent(0, 0, 0), 0L);
                int actual = MinecraftAccessor.C(this.c);
                if (actual != 1) {
                    throw new IllegalStateException("FastPlace PreUpdate did not change rightClickDelayTimer: " + actual);
                }
                runtimeMilestone("event-functional-probe-effect-pass:FastPlace:rightClickDelay=1");

                FastPlace.blockDelay.o((byte)0, 0L, this.eventFunctionalProbeOriginalBlockDelay);
                this.c.thePlayer.inventory.mainInventory[this.eventFunctionalProbeInventorySlot] =
                        this.eventFunctionalProbeOriginalItem;
                this.c.inGameHasFocus = this.eventFunctionalProbeOriginalFocus;
                MinecraftAccessor.j(0L, this.c, this.eventFunctionalProbeOriginalRightClickDelay);

                if (!this.eventFunctionalProbeOriginalEnabled) {
                    probe.I(0L, false);
                    this.eventFunctionalProbeStage = 2;
                    this.eventFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("event-functional-probe-restore-request:FastPlace:enabled=false");
                    return;
                }

                verifyEventFunctionalProbeRestored();
                this.eventFunctionalProbeStage = 3;
                runtimeMilestone("event-functional-probe-pass:FastPlace:restored=true");
                return;
            }

            if (!stableDisabled) {
                if (++this.eventFunctionalProbeWaitTicks > 120) {
                    throw new IllegalStateException("FastPlace did not restore disabled state");
                }
                return;
            }
            verifyEventFunctionalProbeRestored();
            this.eventFunctionalProbeStage = 3;
            runtimeMilestone("event-functional-probe-pass:FastPlace:restored=false");
        }
        catch (Throwable failure) {
            this.eventFunctionalProbeStage = -1;
            restoreEventFunctionalProbeState(probe);
            recordFeatureFailure("EventFunctionalProbe:FastPlace", "preupdate-delay-effect", failure);
            runtimeMilestone("event-functional-probe-fail:FastPlace:" + failure.getClass().getName());
        }
    }


    private void restoreMovementFunctionalProbeState(NoJumpDelay probe) {
        try {
            if (NoJumpDelay.jumpTicks != null) {
                NoJumpDelay.jumpTicks.o((byte)0, 0L, this.movementFunctionalProbeOriginalSetting);
            }
            if (this.c.thePlayer != null) {
                EntityLivingBaseStateAccessor.x(0, this.c.thePlayer, this.movementFunctionalProbeOriginalJumpTicks);
            }
            if (probe != null && probe.o() != this.movementFunctionalProbeOriginalEnabled) {
                probe.I(0L, this.movementFunctionalProbeOriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure("MovementFunctionalProbe:NoJumpDelay", "restore", restoreFailure);
        }
    }

    private void verifyMovementFunctionalProbeRestored() throws Exception {
        if (NoJumpDelay.jumpTicks == null
                || Math.abs(NoJumpDelay.jumpTicks.L() - this.movementFunctionalProbeOriginalSetting) > 0.001f) {
            throw new IllegalStateException("NoJumpDelay Jump-ticks setting was not restored");
        }
        if (this.c.thePlayer != null
                && EntityLivingBaseStateAccessor.C(this.c.thePlayer) != this.movementFunctionalProbeOriginalJumpTicks) {
            throw new IllegalStateException("player jumpTicks was not restored after NoJumpDelay probe");
        }
        runtimeMilestone("movement-functional-probe-restore-state-pass:NoJumpDelay");
    }

    private void pumpMovementFunctionalProbe() {
        if (!Boolean.getBoolean("abyss.movementFunctionalProbe")
                || this.movementFunctionalProbeStage < 0
                || this.movementFunctionalProbeStage >= 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
        }
        if (Boolean.getBoolean("abyss.eventFunctionalProbe") && this.eventFunctionalProbeStage < 3) {
            return;
        }

        NoJumpDelay probe = Modules.J(NoJumpDelay.class);
        try {
            if (probe == null || NoJumpDelay.jumpTicks == null) {
                throw new IllegalStateException("NoJumpDelay module/Jump-ticks setting is missing");
            }
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);

            if (this.movementFunctionalProbeStage == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.movementFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("Initial NoJumpDelay state did not settle");
                    }
                    return;
                }
                this.movementFunctionalProbeOriginalEnabled = stableEnabled;
                this.movementFunctionalProbeOriginalSetting = NoJumpDelay.jumpTicks.L();
                this.movementFunctionalProbeOriginalJumpTicks =
                        EntityLivingBaseStateAccessor.C(this.c.thePlayer);

                if (!stableEnabled) {
                    probe.I(0L, true);
                    this.movementFunctionalProbeStage = 1;
                    this.movementFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("movement-functional-probe-enable-request:NoJumpDelay");
                    return;
                }
                this.movementFunctionalProbeStage = 1;
            }

            if (this.movementFunctionalProbeStage == 1) {
                if (!stableEnabled) {
                    if (++this.movementFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("NoJumpDelay did not enable/subscribe");
                    }
                    return;
                }

                NoJumpDelay.jumpTicks.o((byte)0, 0L, 0.0f);
                EntityLivingBaseStateAccessor.x(0, this.c.thePlayer, 7);
                runtimeMilestone("movement-functional-probe-dispatch:NoJumpDelay:jumpTicks=7");
                w.e(new PreTickEvent(), 0L);
                int actual = EntityLivingBaseStateAccessor.C(this.c.thePlayer);
                if (actual != 1) {
                    throw new IllegalStateException("NoJumpDelay PreTick did not clamp jumpTicks to 1: " + actual);
                }
                runtimeMilestone("movement-functional-probe-effect-pass:NoJumpDelay:jumpTicks=1");

                NoJumpDelay.jumpTicks.o((byte)0, 0L, this.movementFunctionalProbeOriginalSetting);
                EntityLivingBaseStateAccessor.x(0, this.c.thePlayer, this.movementFunctionalProbeOriginalJumpTicks);

                if (!this.movementFunctionalProbeOriginalEnabled) {
                    probe.I(0L, false);
                    this.movementFunctionalProbeStage = 2;
                    this.movementFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("movement-functional-probe-restore-request:NoJumpDelay:enabled=false");
                    return;
                }

                verifyMovementFunctionalProbeRestored();
                this.movementFunctionalProbeStage = 3;
                runtimeMilestone("movement-functional-probe-pass:NoJumpDelay:restored=true");
                return;
            }

            if (!stableDisabled) {
                if (++this.movementFunctionalProbeWaitTicks > 120) {
                    throw new IllegalStateException("NoJumpDelay did not restore disabled state");
                }
                return;
            }
            verifyMovementFunctionalProbeRestored();
            this.movementFunctionalProbeStage = 3;
            runtimeMilestone("movement-functional-probe-pass:NoJumpDelay:restored=false");
        }
        catch (Throwable failure) {
            this.movementFunctionalProbeStage = -1;
            restoreMovementFunctionalProbeState(probe);
            recordFeatureFailure("MovementFunctionalProbe:NoJumpDelay", "pretick-jump-delay-effect", failure);
            runtimeMilestone("movement-functional-probe-fail:NoJumpDelay:" + failure.getClass().getName());
        }
    }



    private int readLeftClickCounterForProbe() throws Exception {
        Field storeField = MinecraftAccessor.class.getDeclaredField("B");
        storeField.setAccessible(true);
        Object store = storeField.get(null);
        Method getter = store.getClass().getDeclaredMethod("m", Object.class);
        getter.setAccessible(true);
        return ((Integer)getter.invoke(store, this.c)).intValue();
    }

    private void restorePlayerFunctionalProbeState(NoHitDelay probe) {
        try {
            MinecraftAccessor.c(this.c, this.playerFunctionalProbeOriginalLeftClickCounter, 0L);
            if (probe != null && probe.o() != this.playerFunctionalProbeOriginalEnabled) {
                probe.I(0L, this.playerFunctionalProbeOriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure("PlayerFunctionalProbe:NoHitDelay", "restore", restoreFailure);
        }
    }

    private void verifyPlayerFunctionalProbeRestored() throws Exception {
        int actual = readLeftClickCounterForProbe();
        if (actual != this.playerFunctionalProbeOriginalLeftClickCounter) {
            throw new IllegalStateException("leftClickCounter was not restored after NoHitDelay probe: " + actual);
        }
        runtimeMilestone("player-functional-probe-restore-state-pass:NoHitDelay");
    }

    private void pumpPlayerFunctionalProbe() {
        if (!Boolean.getBoolean("abyss.playerFunctionalProbe")
                || this.playerFunctionalProbeStage < 0
                || this.playerFunctionalProbeStage >= 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
        }
        if (Boolean.getBoolean("abyss.eventFunctionalProbe") && this.eventFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.movementFunctionalProbe") && this.movementFunctionalProbeStage < 3) {
            return;
        }

        NoHitDelay probe = Modules.J(NoHitDelay.class);
        try {
            if (probe == null) {
                throw new IllegalStateException("NoHitDelay module is missing");
            }
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);

            if (this.playerFunctionalProbeStage == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.playerFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("Initial NoHitDelay state did not settle");
                    }
                    return;
                }
                this.playerFunctionalProbeOriginalEnabled = stableEnabled;
                this.playerFunctionalProbeOriginalLeftClickCounter =
                        readLeftClickCounterForProbe();
                if (!stableEnabled) {
                    probe.I(0L, true);
                    this.playerFunctionalProbeStage = 1;
                    this.playerFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("player-functional-probe-enable-request:NoHitDelay");
                    return;
                }
                this.playerFunctionalProbeStage = 1;
            }

            if (this.playerFunctionalProbeStage == 1) {
                if (!stableEnabled) {
                    if (++this.playerFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("NoHitDelay did not enable/subscribe");
                    }
                    return;
                }

                MinecraftAccessor.c(this.c, 7, 0L);
                runtimeMilestone("player-functional-probe-dispatch:NoHitDelay:leftClickCounter=7");
                w.e(new ClickMouseEvent(), 0L);
                int actual = readLeftClickCounterForProbe();
                if (actual != 0) {
                    throw new IllegalStateException("NoHitDelay ClickMouse did not clear leftClickCounter: " + actual);
                }
                runtimeMilestone("player-functional-probe-effect-pass:NoHitDelay:leftClickCounter=0");

                MinecraftAccessor.c(this.c, this.playerFunctionalProbeOriginalLeftClickCounter, 0L);
                if (!this.playerFunctionalProbeOriginalEnabled) {
                    probe.I(0L, false);
                    this.playerFunctionalProbeStage = 2;
                    this.playerFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("player-functional-probe-restore-request:NoHitDelay:enabled=false");
                    return;
                }

                verifyPlayerFunctionalProbeRestored();
                this.playerFunctionalProbeStage = 3;
                runtimeMilestone("player-functional-probe-pass:NoHitDelay:restored=true");
                return;
            }

            if (!stableDisabled) {
                if (++this.playerFunctionalProbeWaitTicks > 120) {
                    throw new IllegalStateException("NoHitDelay did not restore disabled state");
                }
                return;
            }
            verifyPlayerFunctionalProbeRestored();
            this.playerFunctionalProbeStage = 3;
            runtimeMilestone("player-functional-probe-pass:NoHitDelay:restored=false");
        }
        catch (Throwable failure) {
            this.playerFunctionalProbeStage = -1;
            restorePlayerFunctionalProbeState(probe);
            recordFeatureFailure("PlayerFunctionalProbe:NoHitDelay", "click-left-counter-effect", failure);
            runtimeMilestone("player-functional-probe-fail:NoHitDelay:" + failure.getClass().getName());
        }
    }


    private void restoreCombatFunctionalProbeState(KeepSprint probe) {
        try {
            if (KeepSprint.mode != null && this.combatFunctionalProbeOriginalMode != null) {
                KeepSprint.mode.i(this.combatFunctionalProbeOriginalMode);
            }
            if (KeepSprint.slowdown != null) {
                KeepSprint.slowdown.d(this.combatFunctionalProbeOriginalSlowdown);
            }
            if (this.c.thePlayer != null) {
                this.c.thePlayer.motionX = this.combatFunctionalProbeOriginalMotionX;
                this.c.thePlayer.motionZ = this.combatFunctionalProbeOriginalMotionZ;
                this.c.thePlayer.setSprinting(this.combatFunctionalProbeOriginalSprinting);
            }
            KeepSprint.t = this.combatFunctionalProbeOriginalT;
            KeepSprint.a = this.combatFunctionalProbeOriginalA;
            if (probe != null && probe.o() != this.combatFunctionalProbeOriginalEnabled) {
                probe.I(0L, this.combatFunctionalProbeOriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure("CombatFunctionalProbe:KeepSprint", "restore", restoreFailure);
        }
    }

    private void verifyCombatFunctionalProbeRestored() {
        if (KeepSprint.mode == null || KeepSprint.slowdown == null
                || !KeepSprint.mode.R(this.combatFunctionalProbeOriginalMode)
                || KeepSprint.slowdown.k() != this.combatFunctionalProbeOriginalSlowdown) {
            throw new IllegalStateException("KeepSprint settings were not restored");
        }
        if (this.c.thePlayer != null) {
            if (Math.abs(this.c.thePlayer.motionX - this.combatFunctionalProbeOriginalMotionX) > 1.0E-9
                    || Math.abs(this.c.thePlayer.motionZ - this.combatFunctionalProbeOriginalMotionZ) > 1.0E-9
                    || this.c.thePlayer.isSprinting() != this.combatFunctionalProbeOriginalSprinting) {
                throw new IllegalStateException("Player motion/sprint state was not restored after KeepSprint probe");
            }
        }
        if (KeepSprint.t != this.combatFunctionalProbeOriginalT
                || KeepSprint.a != this.combatFunctionalProbeOriginalA) {
            throw new IllegalStateException("KeepSprint static state was not restored");
        }
        runtimeMilestone("combat-functional-probe-restore-state-pass:KeepSprint");
    }

    private void pumpCombatFunctionalProbe() {
        if (!Boolean.getBoolean("abyss.combatFunctionalProbe")
                || this.combatFunctionalProbeStage < 0
                || this.combatFunctionalProbeStage >= 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
        }
        if (Boolean.getBoolean("abyss.eventFunctionalProbe") && this.eventFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.movementFunctionalProbe") && this.movementFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.playerFunctionalProbe") && this.playerFunctionalProbeStage < 3) {
            return;
        }

        KeepSprint probe = Modules.J(KeepSprint.class);
        try {
            if (probe == null || KeepSprint.mode == null || KeepSprint.slowdown == null) {
                throw new IllegalStateException("KeepSprint module/settings are missing");
            }
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);

            if (this.combatFunctionalProbeStage == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.combatFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("Initial KeepSprint state did not settle");
                    }
                    return;
                }
                this.combatFunctionalProbeOriginalEnabled = stableEnabled;
                this.combatFunctionalProbeOriginalMode = KeepSprint.mode.Y();
                this.combatFunctionalProbeOriginalSlowdown = KeepSprint.slowdown.k();
                this.combatFunctionalProbeOriginalMotionX = this.c.thePlayer.motionX;
                this.combatFunctionalProbeOriginalMotionZ = this.c.thePlayer.motionZ;
                this.combatFunctionalProbeOriginalSprinting = this.c.thePlayer.isSprinting();
                this.combatFunctionalProbeOriginalT = KeepSprint.t;
                this.combatFunctionalProbeOriginalA = KeepSprint.a;

                if (!stableEnabled) {
                    probe.I(0L, true);
                    this.combatFunctionalProbeStage = 1;
                    this.combatFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("combat-functional-probe-enable-request:KeepSprint");
                    return;
                }
                this.combatFunctionalProbeStage = 1;
            }

            if (this.combatFunctionalProbeStage == 1) {
                if (!stableEnabled) {
                    if (++this.combatFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("KeepSprint did not enable/subscribe");
                    }
                    return;
                }

                KeepSprint.mode.i("VANILLA");
                KeepSprint.slowdown.d(50);
                KeepSprint.t = 0;
                KeepSprint.a = 0;
                this.c.thePlayer.motionX = 1.25;
                this.c.thePlayer.motionZ = -0.75;
                this.c.thePlayer.setSprinting(true);
                runtimeMilestone("combat-functional-probe-dispatch:KeepSprint:motion=1.25,-0.75:slowdown=50");
                KeepSprint.k(0L);

                double actualX = this.c.thePlayer.motionX;
                double actualZ = this.c.thePlayer.motionZ;
                if (Math.abs(actualX - 1.0) > 1.0E-9 || Math.abs(actualZ + 0.6) > 1.0E-9
                        || !this.c.thePlayer.isSprinting()) {
                    throw new IllegalStateException("KeepSprint hook effect mismatch: motion="
                            + actualX + "," + actualZ + " sprinting=" + this.c.thePlayer.isSprinting());
                }
                runtimeMilestone("combat-functional-probe-effect-pass:KeepSprint:motion=1.0,-0.6:sprinting=true");

                KeepSprint.mode.i(this.combatFunctionalProbeOriginalMode);
                KeepSprint.slowdown.d(this.combatFunctionalProbeOriginalSlowdown);
                this.c.thePlayer.motionX = this.combatFunctionalProbeOriginalMotionX;
                this.c.thePlayer.motionZ = this.combatFunctionalProbeOriginalMotionZ;
                this.c.thePlayer.setSprinting(this.combatFunctionalProbeOriginalSprinting);
                KeepSprint.t = this.combatFunctionalProbeOriginalT;
                KeepSprint.a = this.combatFunctionalProbeOriginalA;

                if (!this.combatFunctionalProbeOriginalEnabled) {
                    probe.I(0L, false);
                    this.combatFunctionalProbeStage = 2;
                    this.combatFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("combat-functional-probe-restore-request:KeepSprint:enabled=false");
                    return;
                }

                verifyCombatFunctionalProbeRestored();
                this.combatFunctionalProbeStage = 3;
                runtimeMilestone("combat-functional-probe-pass:KeepSprint:restored=true");
                return;
            }

            if (!stableDisabled) {
                if (++this.combatFunctionalProbeWaitTicks > 120) {
                    throw new IllegalStateException("KeepSprint did not restore disabled state");
                }
                return;
            }
            verifyCombatFunctionalProbeRestored();
            this.combatFunctionalProbeStage = 3;
            runtimeMilestone("combat-functional-probe-pass:KeepSprint:restored=false");
        }
        catch (Throwable failure) {
            this.combatFunctionalProbeStage = -1;
            restoreCombatFunctionalProbeState(probe);
            recordFeatureFailure("CombatFunctionalProbe:KeepSprint", "attack-slowdown-hook-effect", failure);
            runtimeMilestone("combat-functional-probe-fail:KeepSprint:" + failure.getClass().getName());
        }
    }

    private void restorePacketFunctionalProbeState(Ambience probe) {
        try {
            if (Ambience.time != null) {
                Ambience.time.o((byte)0, 0L, this.packetFunctionalProbeOriginalTimeSetting);
            }
            if (Ambience.speed != null) {
                Ambience.speed.o((byte)0, 0L, this.packetFunctionalProbeOriginalSpeedSetting);
            }
            if (this.c.theWorld != null) {
                this.c.theWorld.setWorldTime(this.packetFunctionalProbeOriginalWorldTime);
            }
            if (probe != null && probe.o() != this.packetFunctionalProbeOriginalEnabled) {
                probe.I(0L, this.packetFunctionalProbeOriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure("PacketFunctionalProbe:Ambience", "restore", restoreFailure);
        }
    }

    private void verifyPacketFunctionalProbeRestored() {
        if (Ambience.time == null || Ambience.speed == null) {
            throw new IllegalStateException("Ambience settings disappeared during restore");
        }
        if (Math.abs(Ambience.time.L() - this.packetFunctionalProbeOriginalTimeSetting) > 0.001f
                || Math.abs(Ambience.speed.L() - this.packetFunctionalProbeOriginalSpeedSetting) > 0.001f) {
            throw new IllegalStateException("Ambience time/speed settings were not restored");
        }
        runtimeMilestone("packet-functional-probe-restore-state-pass:Ambience");
    }

    private void pumpPacketFunctionalProbe() {
        if (!Boolean.getBoolean("abyss.packetFunctionalProbe")
                || this.packetFunctionalProbeStage < 0
                || this.packetFunctionalProbeStage >= 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
        }
        if (Boolean.getBoolean("abyss.eventFunctionalProbe") && this.eventFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.movementFunctionalProbe") && this.movementFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.playerFunctionalProbe") && this.playerFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.combatFunctionalProbe") && this.combatFunctionalProbeStage < 3) {
            return;
        }

        Ambience probe = Modules.J(Ambience.class);
        try {
            if (probe == null) {
                throw new IllegalStateException("Ambience module is missing");
            }
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);

            if (this.packetFunctionalProbeStage == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.packetFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("Initial Ambience state did not settle");
                    }
                    return;
                }
                this.packetFunctionalProbeOriginalEnabled = stableEnabled;
                if (Ambience.time == null || Ambience.speed == null || this.c.theWorld == null) {
                    throw new IllegalStateException("Ambience settings/world unavailable");
                }
                this.packetFunctionalProbeOriginalTimeSetting = Ambience.time.L();
                this.packetFunctionalProbeOriginalSpeedSetting = Ambience.speed.L();
                this.packetFunctionalProbeOriginalWorldTime = this.c.theWorld.getWorldTime();
                if (!stableEnabled) {
                    probe.I(0L, true);
                    this.packetFunctionalProbeStage = 1;
                    this.packetFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("packet-functional-probe-enable-request:Ambience");
                    return;
                }
                this.packetFunctionalProbeStage = 1;
            }

            if (this.packetFunctionalProbeStage == 1) {
                if (!stableEnabled) {
                    if (++this.packetFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("Ambience did not enable/subscribe");
                    }
                    return;
                }

                ReceivePacketEvent event =
                        new ReceivePacketEvent(new S03PacketTimeUpdate(100L, 200L, true));
                runtimeMilestone("packet-functional-probe-dispatch:Ambience:S03PacketTimeUpdate");
                w.e(event, 0L);
                if (!event.a()) {
                    throw new IllegalStateException("Ambience did not cancel S03PacketTimeUpdate");
                }
                runtimeMilestone("packet-functional-probe-effect-pass:Ambience:S03PacketTimeUpdate:cancelled=true");

                Ambience.time.o((byte)0, 0L, 6000.0f);
                Ambience.speed.o((byte)0, 0L, 0.0f);
                this.c.theWorld.setWorldTime(1234L);
                runtimeMilestone("render-functional-probe-dispatch:Ambience:worldTime=1234");
                w.e(new Render2DEvent(0, (short)0, 0.0f, (short)0, null), 0L);
                long renderWorldTime = this.c.theWorld.getWorldTime();
                if (renderWorldTime != 6000L) {
                    throw new IllegalStateException("Ambience Render2D did not set world time to 6000: "
                            + renderWorldTime);
                }
                runtimeMilestone("render-functional-probe-effect-pass:Ambience:worldTime=6000");

                Ambience.time.o((byte)0, 0L, this.packetFunctionalProbeOriginalTimeSetting);
                Ambience.speed.o((byte)0, 0L, this.packetFunctionalProbeOriginalSpeedSetting);
                this.c.theWorld.setWorldTime(this.packetFunctionalProbeOriginalWorldTime);
                if (this.c.theWorld.getWorldTime() != this.packetFunctionalProbeOriginalWorldTime) {
                    throw new IllegalStateException("Ambience world time did not restore immediately: "
                            + this.c.theWorld.getWorldTime());
                }
                runtimeMilestone("render-functional-probe-worldtime-restore-pass:Ambience");

                if (!this.packetFunctionalProbeOriginalEnabled) {
                    probe.I(0L, false);
                    this.packetFunctionalProbeStage = 2;
                    this.packetFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("packet-functional-probe-restore-request:Ambience:enabled=false");
                    return;
                }

                verifyPacketFunctionalProbeRestored();
                this.packetFunctionalProbeStage = 3;
                runtimeMilestone("packet-functional-probe-pass:Ambience:restored=true");
                return;
            }

            if (!stableDisabled) {
                if (++this.packetFunctionalProbeWaitTicks > 120) {
                    throw new IllegalStateException("Ambience did not restore disabled state");
                }
                return;
            }
            verifyPacketFunctionalProbeRestored();
            this.packetFunctionalProbeStage = 3;
            runtimeMilestone("packet-functional-probe-pass:Ambience:restored=false");
        }
        catch (Throwable failure) {
            this.packetFunctionalProbeStage = -1;
            restorePacketFunctionalProbeState(probe);
            recordFeatureFailure("PacketFunctionalProbe:Ambience", "receive-cancel", failure);
            runtimeMilestone("packet-functional-probe-fail:Ambience:" + failure.getClass().getName());
        }
    }


    private void restoreMacroFunctionalProbeState(Macro1 probe) {
        try {
            if (Macro1.mode != null && this.macroFunctionalProbeOriginalMode != null) {
                Macro1.mode.i(this.macroFunctionalProbeOriginalMode);
            }
            if (Macro1.chatMessage != null && this.macroFunctionalProbeOriginalMessage != null) {
                Macro1.chatMessage.O(this.macroFunctionalProbeOriginalMessage);
            }
            if (probe != null && probe.o() != this.macroFunctionalProbeOriginalEnabled) {
                probe.I(0L, this.macroFunctionalProbeOriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure("MacroFunctionalProbe:Macro1", "restore", restoreFailure);
        }
    }

    private void verifyMacroFunctionalProbeSettingsRestored() {
        if (Macro1.mode == null || Macro1.chatMessage == null
                || !Macro1.mode.R(this.macroFunctionalProbeOriginalMode)
                || !String.valueOf(this.macroFunctionalProbeOriginalMessage).equals(Macro1.chatMessage.X())) {
            throw new IllegalStateException("Macro1 settings were not restored");
        }
        runtimeMilestone("macro-functional-probe-restore-state-pass:Macro1");
    }

    private void pumpMacroFunctionalProbe() {
        if (!Boolean.getBoolean("abyss.macroFunctionalProbe")
                || this.macroFunctionalProbeStage < 0
                || this.macroFunctionalProbeStage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
        }
        if (Boolean.getBoolean("abyss.eventFunctionalProbe") && this.eventFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.movementFunctionalProbe") && this.movementFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.playerFunctionalProbe") && this.playerFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.combatFunctionalProbe") && this.combatFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.packetFunctionalProbe") && this.packetFunctionalProbeStage < 3) {
            return;
        }

        Macro1 probe = Modules.J(Macro1.class);
        try {
            if (probe == null || Macro1.mode == null || Macro1.chatMessage == null) {
                throw new IllegalStateException("Macro1 module/settings are missing");
            }
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);

            if (this.macroFunctionalProbeStage == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.macroFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("Initial Macro1 state did not settle");
                    }
                    return;
                }
                this.macroFunctionalProbeOriginalEnabled = stableEnabled;
                this.macroFunctionalProbeOriginalMode = Macro1.mode.Y();
                this.macroFunctionalProbeOriginalMessage = Macro1.chatMessage.X();

                Macro1.mode.i("CHAT");
                Macro1.chatMessage.O(MACRO_FUNCTIONAL_PROBE_SENTINEL);
                if (!stableEnabled) {
                    probe.I(0L, true);
                    this.macroFunctionalProbeStage = 1;
                    this.macroFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("macro-functional-probe-enable-request:Macro1");
                    return;
                }
                this.macroFunctionalProbeStage = 1;
            }

            if (this.macroFunctionalProbeStage == 1) {
                if (!stableEnabled) {
                    if (++this.macroFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("Macro1 did not enable/subscribe");
                    }
                    return;
                }
                runtimeMilestone("macro-functional-probe-dispatch:Macro1:CHAT:" + MACRO_FUNCTIONAL_PROBE_SENTINEL);
                runtimeMilestone("macro-functional-probe-packet-buffer:" + PacketManager.e());
                w.e(new PreTickEvent(), 0L);
                if (probe.o()) {
                    throw new IllegalStateException("Macro1 CHAT PreTick did not request self-disable");
                }
                runtimeMilestone("macro-functional-probe-client-send-pass:Macro1:" + MACRO_FUNCTIONAL_PROBE_SENTINEL);
                this.macroFunctionalProbeStage = 2;
                this.macroFunctionalProbeWaitTicks = 0;
                return;
            }

            if (this.macroFunctionalProbeStage == 2) {
                if (!stableDisabled) {
                    if (++this.macroFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("Macro1 did not settle disabled after CHAT send");
                    }
                    return;
                }
                Macro1.mode.i(this.macroFunctionalProbeOriginalMode);
                Macro1.chatMessage.O(this.macroFunctionalProbeOriginalMessage);
                if (this.macroFunctionalProbeOriginalEnabled) {
                    probe.I(0L, true);
                    this.macroFunctionalProbeStage = 3;
                    this.macroFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("macro-functional-probe-restore-request:Macro1:enabled=true");
                    return;
                }
                verifyMacroFunctionalProbeSettingsRestored();
                this.macroFunctionalProbeStage = 4;
                runtimeMilestone("macro-functional-probe-pass:Macro1:restored=false");
                return;
            }

            if (!stableEnabled) {
                if (++this.macroFunctionalProbeWaitTicks > 120) {
                    throw new IllegalStateException("Macro1 did not restore enabled state");
                }
                return;
            }
            verifyMacroFunctionalProbeSettingsRestored();
            this.macroFunctionalProbeStage = 4;
            runtimeMilestone("macro-functional-probe-pass:Macro1:restored=true");
        }
        catch (Throwable failure) {
            this.macroFunctionalProbeStage = -1;
            restoreMacroFunctionalProbeState(probe);
            recordFeatureFailure("MacroFunctionalProbe:Macro1", "chat-send", failure);
            runtimeMilestone("macro-functional-probe-fail:Macro1:" + failure.getClass().getName());
        }
    }


    private ItemStack[] inventoryHudCacheForProbe(InventoryHUD probe) throws Exception {
        Field cacheField = InventoryHUD.class.getDeclaredField("p");
        cacheField.setAccessible(true);
        return (ItemStack[])cacheField.get(probe);
    }

    private boolean inventoryHudCacheMatches(ItemStack cached, ItemStack expected) {
        if (cached == null || expected == null) {
            return cached == null && expected == null;
        }
        return cached.getItem() == expected.getItem()
                && cached.getItemDamage() == expected.getItemDamage()
                && cached.stackSize == expected.stackSize;
    }

    private void restoreVisualUtilityFunctionalProbeState(InventoryHUD probe) {
        try {
            if (this.c.thePlayer != null && this.c.thePlayer.inventory != null) {
                this.c.thePlayer.inventory.mainInventory[9] = this.visualUtilityFunctionalProbeOriginalSlot9;
            }
            if (probe != null && probe.o() != this.visualUtilityFunctionalProbeOriginalEnabled) {
                probe.I(0L, this.visualUtilityFunctionalProbeOriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure("VisualUtilityFunctionalProbe:InventoryHUD", "restore", restoreFailure);
        }
    }

    private void verifyVisualUtilityFunctionalProbeRestored(InventoryHUD probe) throws Exception {
        if (this.c.thePlayer == null || this.c.thePlayer.inventory == null
                || this.c.thePlayer.inventory.mainInventory[9] != this.visualUtilityFunctionalProbeOriginalSlot9) {
            throw new IllegalStateException("Inventory slot 9 was not restored after InventoryHUD probe");
        }
        ItemStack[] cache = inventoryHudCacheForProbe(probe);
        if (cache == null || cache.length != 27) {
            throw new IllegalStateException("InventoryHUD cache shape changed");
        }
        if (this.visualUtilityFunctionalProbeOriginalEnabled) {
            if (!inventoryHudCacheMatches(cache[0], this.visualUtilityFunctionalProbeOriginalSlot9)) {
                throw new IllegalStateException("InventoryHUD cache did not return to restored slot 9 state");
            }
        } else {
            for (ItemStack item : cache) {
                if (item != null) {
                    throw new IllegalStateException("InventoryHUD disabled cache was not cleared");
                }
            }
        }
        runtimeMilestone("visual-utility-functional-probe-restore-state-pass:InventoryHUD");
    }

    private void pumpVisualUtilityFunctionalProbe() {
        if (!Boolean.getBoolean("abyss.visualUtilityFunctionalProbe")
                || this.visualUtilityFunctionalProbeStage < 0
                || this.visualUtilityFunctionalProbeStage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
        }
        if (Boolean.getBoolean("abyss.eventFunctionalProbe") && this.eventFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.movementFunctionalProbe") && this.movementFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.playerFunctionalProbe") && this.playerFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.combatFunctionalProbe") && this.combatFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.packetFunctionalProbe") && this.packetFunctionalProbeStage < 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.macroFunctionalProbe") && this.macroFunctionalProbeStage < 4) {
            return;
        }

        InventoryHUD probe = Modules.J(InventoryHUD.class);
        try {
            if (probe == null || this.c.thePlayer == null || this.c.thePlayer.inventory == null) {
                throw new IllegalStateException("InventoryHUD/player inventory is unavailable");
            }
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);

            if (this.visualUtilityFunctionalProbeStage == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.visualUtilityFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("Initial InventoryHUD state did not settle");
                    }
                    return;
                }
                this.visualUtilityFunctionalProbeOriginalEnabled = stableEnabled;
                this.visualUtilityFunctionalProbeOriginalSlot9 =
                        this.c.thePlayer.inventory.mainInventory[9];

                if (!stableEnabled) {
                    probe.I(0L, true);
                    this.visualUtilityFunctionalProbeStage = 1;
                    this.visualUtilityFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("visual-utility-functional-probe-enable-request:InventoryHUD");
                    return;
                }
                this.visualUtilityFunctionalProbeStage = 1;
            }

            if (this.visualUtilityFunctionalProbeStage == 1) {
                if (!stableEnabled) {
                    if (++this.visualUtilityFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("InventoryHUD did not enable/subscribe");
                    }
                    return;
                }
                this.c.thePlayer.inventory.mainInventory[9] = new ItemStack(Blocks.stone, 3);
                this.visualUtilityFunctionalProbeStage = 2;
                this.visualUtilityFunctionalProbeWaitTicks = 0;
                runtimeMilestone("visual-utility-functional-probe-await-natural-posttick:InventoryHUD:slot9=stone*3");
                return;
            }

            if (this.visualUtilityFunctionalProbeStage == 2) {
                ItemStack[] cache = inventoryHudCacheForProbe(probe);
                ItemStack cached = cache == null || cache.length == 0 ? null : cache[0];
                boolean copied = cached != null
                        && cached.getItem() == ItemStack.class.cast(new ItemStack(Blocks.stone)).getItem()
                        && cached.stackSize == 3;
                if (!copied) {
                    if (++this.visualUtilityFunctionalProbeWaitTicks > 120) {
                        throw new IllegalStateException("InventoryHUD natural PostTick did not cache slot 9 stone*3");
                    }
                    return;
                }
                runtimeMilestone("visual-utility-functional-probe-effect-pass:InventoryHUD:cache0=stone*3");
                this.c.thePlayer.inventory.mainInventory[9] = this.visualUtilityFunctionalProbeOriginalSlot9;

                if (!this.visualUtilityFunctionalProbeOriginalEnabled) {
                    probe.I(0L, false);
                }
                this.visualUtilityFunctionalProbeStage = 3;
                this.visualUtilityFunctionalProbeWaitTicks = 0;
                runtimeMilestone("visual-utility-functional-probe-restore-request:InventoryHUD:enabled="
                        + this.visualUtilityFunctionalProbeOriginalEnabled);
                return;
            }

            boolean moduleRestored = this.visualUtilityFunctionalProbeOriginalEnabled ? stableEnabled : stableDisabled;
            if (!moduleRestored) {
                if (++this.visualUtilityFunctionalProbeWaitTicks > 120) {
                    throw new IllegalStateException("InventoryHUD module state did not restore");
                }
                return;
            }

            try {
                verifyVisualUtilityFunctionalProbeRestored(probe);
            }
            catch (IllegalStateException notReady) {
                if (++this.visualUtilityFunctionalProbeWaitTicks <= 120) {
                    return;
                }
                throw notReady;
            }
            this.visualUtilityFunctionalProbeStage = 4;
            runtimeMilestone("visual-utility-functional-probe-pass:InventoryHUD:restored="
                    + this.visualUtilityFunctionalProbeOriginalEnabled);
        }
        catch (Throwable failure) {
            this.visualUtilityFunctionalProbeStage = -1;
            restoreVisualUtilityFunctionalProbeState(probe);
            recordFeatureFailure("VisualUtilityFunctionalProbe:InventoryHUD", "natural-posttick-cache-effect", failure);
            runtimeMilestone("visual-utility-functional-probe-fail:InventoryHUD:" + failure.getClass().getName());
        }
    }

    private void pumpCommandRuntimeProbe() {
        if (!Boolean.getBoolean("abyss.commandRuntimeProbe")
                || this.commandRuntimeProbeStage < 0
                || this.commandRuntimeProbeStage >= 3) {
            return;
}
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
}
        if (Boolean.getBoolean("abyss.eventFunctionalProbe") && this.eventFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.movementFunctionalProbe") && this.movementFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.packetFunctionalProbe") && this.packetFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.macroFunctionalProbe") && this.macroFunctionalProbeStage < 4) {
            return;
}
        if (Boolean.getBoolean("abyss.visualUtilityFunctionalProbe")
                && this.visualUtilityFunctionalProbeStage < 4) {
            return;
}
        try {
            Module probe = Modules.J(FullBright.class);
            if (probe == null) {
                throw new IllegalStateException("FullBright module is missing");
}
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);

            if (this.commandRuntimeProbeStage == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.commandRuntimeProbeWaitTicks > 120) {
                        throw new IllegalStateException("Initial command probe module state did not settle"
                                + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                                + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                                + " ownerActive=" + w.isOwnerActive(probe));
}
                    return;
}
                this.commandRuntimeProbeOriginalEnabled = stableEnabled;
                this.commandRuntimeProbeOriginalGamma = this.c.gameSettings.gammaSetting;
                this.commandRuntimeProbeWaitTicks = 0;
                this.commandRuntimeProbeOutputStart = ConfigManagerWindow.D == null ? 0 : ConfigManagerWindow.D.size();
                runtimeMilestone("command-runtime-probe-fullbright-original:enabled="
                        + this.commandRuntimeProbeOriginalEnabled + ":gamma=" + this.commandRuntimeProbeOriginalGamma);

                boolean help = AbyssCommands.dispatch(".help");
                boolean list = AbyssCommands.dispatch(".list");
                boolean binds = AbyssCommands.dispatch(".bind list");
                boolean configs = AbyssCommands.dispatch(".config list");
                boolean unknown = AbyssCommands.dispatch(".__openabyss_unknown_command__");
                if (!help || !list || !binds || !configs || !unknown) {
                    throw new IllegalStateException("Command dispatch was not consumed"
                            + " help=" + help + " list=" + list + " bind=" + binds
                            + " config=" + configs + " unknown=" + unknown);
}
                int outputNow = ConfigManagerWindow.D == null ? 0 : ConfigManagerWindow.D.size();
                if (outputNow <= this.commandRuntimeProbeOutputStart) {
                    throw new IllegalStateException("Command output did not reach local chat buffer"
                            + " before=" + this.commandRuntimeProbeOutputStart + " after=" + outputNow);
}

                if (!AbyssCommands.dispatch(".toggle FullBright")) {
                    throw new IllegalStateException("Toggle command was not consumed");
}
                this.commandRuntimeProbeStage = 1;
                runtimeMilestone("command-runtime-probe-toggle-request:FullBright:target="
                        + (!this.commandRuntimeProbeOriginalEnabled));
                return;
}

            if (this.commandRuntimeProbeStage == 1) {
                boolean opposite = this.commandRuntimeProbeOriginalEnabled ? stableDisabled : stableEnabled;
                if (!opposite) {
                    if (++this.commandRuntimeProbeWaitTicks > 120) {
                        throw new IllegalStateException("Command toggle did not reach opposite stable state"
                                + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                                + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                                + " ownerActive=" + w.isOwnerActive(probe));
}
                    return;
}
                float oppositeGamma = this.c.gameSettings.gammaSetting;
                if (!this.commandRuntimeProbeOriginalEnabled
                        && Math.abs(oppositeGamma - 15.0f) > 0.0001f) {
                    throw new IllegalStateException("FullBright enable did not set gamma to 15.0: " + oppositeGamma);
}
                runtimeMilestone("command-runtime-probe-toggle-pass:FullBright:enabled=" + probe.o());
                runtimeMilestone("command-runtime-probe-fullbright-effect-pass:enabled="
                        + probe.o() + ":gamma=" + oppositeGamma);
                this.commandRuntimeProbeWaitTicks = 0;
                if (!AbyssCommands.dispatch(".toggle FullBright")) {
                    throw new IllegalStateException("Restore toggle command was not consumed");
}
                this.commandRuntimeProbeStage = 2;
                runtimeMilestone("command-runtime-probe-restore-request:FullBright:target="
                        + this.commandRuntimeProbeOriginalEnabled);
                return;
}

            boolean restored = this.commandRuntimeProbeOriginalEnabled ? stableEnabled : stableDisabled;
            if (!restored) {
                if (++this.commandRuntimeProbeWaitTicks > 120) {
                    throw new IllegalStateException("Command toggle did not restore original stable state"
                            + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                            + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                            + " ownerActive=" + w.isOwnerActive(probe));
}
                return;
}
            float restoredGamma = this.c.gameSettings.gammaSetting;
            float expectedRestoredGamma = this.commandRuntimeProbeOriginalEnabled
                    ? 15.0f : this.commandRuntimeProbeOriginalGamma;
            if (Math.abs(restoredGamma - expectedRestoredGamma) > 0.0001f) {
                throw new IllegalStateException("FullBright gamma did not restore"
                        + " expected=" + expectedRestoredGamma + " actual=" + restoredGamma
                        + " originalEnabled=" + this.commandRuntimeProbeOriginalEnabled);
}
            runtimeMilestone("command-runtime-probe-fullbright-restore-pass:enabled="
                    + probe.o() + ":gamma=" + restoredGamma);
            if (!AbyssCommands.dispatch(".FullBright")) {
                throw new IllegalStateException("Module-setting command was not consumed");
}
            int outputNow = ConfigManagerWindow.D == null ? 0 : ConfigManagerWindow.D.size();
            int outputDelta = outputNow - this.commandRuntimeProbeOutputStart;
            if (outputDelta <= 0) {
                throw new IllegalStateException("Command output delta is not positive: " + outputDelta);
}
            this.commandRuntimeProbeStage = 3;
            runtimeMilestone("command-runtime-probe-pass:commands=7:outputDelta=" + outputDelta
                    + ":restored=" + this.commandRuntimeProbeOriginalEnabled);
}
        catch (Throwable failure) {
            this.commandRuntimeProbeStage = -1;
            recordFeatureFailure("CommandRuntimeProbe", "dispatch-lifecycle", failure);
            runtimeMilestone("command-runtime-probe-fail:" + failure.getClass().getName());
}
}

    private void pumpNetworkCommandProbe() {
        if (!Boolean.getBoolean("abyss.networkCommandProbe")
                || this.networkCommandProbeStage < 0
                || this.networkCommandProbeStage >= 4) {
            return;
}
        if (Boolean.getBoolean("abyss.commandRuntimeProbe") && this.commandRuntimeProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
}
        try {
            Module probe = Modules.J(CommandLine.class);
            if (probe == null) {
                throw new IllegalStateException("CommandLine module is missing");
}
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe);
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);
            File done = new File("abyss-network-command-probe-done");

            if (this.networkCommandProbeStage == 0) {
                if (!stableEnabled && !stableDisabled) {
                    if (++this.networkCommandProbeWaitTicks > 160) {
                        throw new IllegalStateException("Initial CommandLine state did not settle"
                                + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                                + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                                + " ownerActive=" + w.isOwnerActive(probe));
}
                    return;
}
                this.networkCommandProbeOriginalEnabled = stableEnabled;
                this.networkCommandProbeWaitTicks = 0;
                if (done.exists() && !done.delete()) {
                    throw new IllegalStateException("Could not clear stale network command probe handshake");
}
                if (this.networkCommandProbeOriginalEnabled) {
                    this.networkCommandProbeStage = 2;
                    runtimeMilestone("network-command-probe-ready:CommandLine:original=true");
                    return;
}
                probe.I(0L, true);
                this.networkCommandProbeStage = 1;
                runtimeMilestone("network-command-probe-enable-request:CommandLine");
                return;
}

            if (this.networkCommandProbeStage == 1) {
                if (!stableEnabled) {
                    if (++this.networkCommandProbeWaitTicks > 160) {
                        throw new IllegalStateException("CommandLine did not enable/subscribe"
                                + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                                + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                                + " ownerActive=" + w.isOwnerActive(probe));
}
                    return;
}
                this.networkCommandProbeWaitTicks = 0;
                this.networkCommandProbeStage = 2;
                runtimeMilestone("network-command-probe-ready:CommandLine:original=false");
                return;
}

            if (this.networkCommandProbeStage == 2) {
                if (!done.isFile()) {
                    if (++this.networkCommandProbeWaitTicks > 2400) {
                        throw new IllegalStateException("Harness did not complete typed command probe");
}
                    return;
}
                this.networkCommandProbeWaitTicks = 0;
                if (this.networkCommandProbeOriginalEnabled) {
                    this.networkCommandProbeStage = 4;
                    runtimeMilestone("network-command-probe-pass:CommandLine:restored=true");
                    return;
}
                probe.I(0L, false);
                this.networkCommandProbeStage = 3;
                runtimeMilestone("network-command-probe-restore-request:CommandLine");
                return;
}

            if (!stableDisabled) {
                if (++this.networkCommandProbeWaitTicks > 160) {
                    throw new IllegalStateException("CommandLine did not restore disabled state"
                            + " enabled=" + probe.o() + " pendingEnable=" + probe.l()
                            + " pendingDisable=" + probe.K() + " subscribed=" + probe.P()
                            + " ownerActive=" + w.isOwnerActive(probe));
}
                return;
}
            this.networkCommandProbeStage = 4;
            runtimeMilestone("network-command-probe-pass:CommandLine:restored=false");
}
        catch (Throwable failure) {
            this.networkCommandProbeStage = -1;
            recordFeatureFailure("NetworkCommandProbe:CommandLine", "enable-intercept-restore", failure);
            runtimeMilestone("network-command-probe-fail:" + failure.getClass().getName());
}
}

    private void pumpReconnectSubscriptionHealth() {
        if (this.reconnectCommandLineSubscriptionVerified
                || !Boolean.getBoolean("abyss.runtimeSelfTest")
                || !Boolean.getBoolean("abyss.networkCommandProbe")
                || this.runtimeWorldSessionCount < 2
                || this.runtimeWorldHeartbeatTicks < 100L
                || this.networkCommandProbeStage < 4) {
            return;
}
        this.reconnectCommandLineSubscriptionVerified = true;
        try {
            CommandLine probe = Modules.J(CommandLine.class);
            if (probe == null) {
                throw new IllegalStateException("CommandLine module is missing after reconnect");
}
            boolean ownerActive = w != null && w.isOwnerActive(probe);
            boolean stableEnabled = probe.o() && !probe.l() && !probe.K() && probe.P() && ownerActive;
            boolean stableDisabled = !probe.o() && !probe.l() && !probe.K() && !probe.P() && !ownerActive;
            boolean healthy = this.networkCommandProbeOriginalEnabled ? stableEnabled : stableDisabled;
            if (!healthy) {
                throw new IllegalStateException("CommandLine reconnect subscription state mismatch"
                        + " expectedEnabled=" + this.networkCommandProbeOriginalEnabled
                        + " enabled=" + probe.o()
                        + " pendingEnable=" + probe.l()
                        + " pendingDisable=" + probe.K()
                        + " subscribed=" + probe.P()
                        + " ownerActive=" + ownerActive
                        + " session=" + this.runtimeWorldSessionCount
                        + " heartbeat=" + this.runtimeWorldHeartbeatTicks);
}
            runtimeMilestone("world-session-commandline-subscription-pass:"
                    + this.runtimeWorldSessionCount
                    + ":expectedEnabled=" + this.networkCommandProbeOriginalEnabled
                    + ":enabled=" + probe.o()
                    + ":subscribed=" + probe.P()
                    + ":ownerActive=" + ownerActive);
}
        catch (Throwable failure) {
            recordFeatureFailure("ReconnectSubscriptionProbe:CommandLine", "session2-eventbus", failure);
            runtimeMilestone("world-session-commandline-subscription-fail:"
                    + this.runtimeWorldSessionCount + ":" + failure.getClass().getName());
}
}

    private void pumpClickGuiModeProbe() {
        if (!Boolean.getBoolean("abyss.clickGuiModeProbe")
                || this.clickGuiModeProbeIndex >= CLICKGUI_MODE_PROBE_MODES.length
                || !new File("abyss-clickgui-mode-probe-go").isFile()) {
            return;
}
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) {
            return;
}
        if (Boolean.getBoolean("abyss.commandRuntimeProbe") && this.commandRuntimeProbeStage < 3) {
            return;
}
        if (Boolean.getBoolean("abyss.networkCommandProbe") && this.networkCommandProbeStage < 4) {
            return;
}
        if (!FontManager.isReady()) {
            if (++this.clickGuiModeProbeWaitTicks > 400) {
                RuntimeException failure = new RuntimeException("Font textures did not become ready before ClickGUI visual probe");
                this.clickGuiModeProbeIndex = CLICKGUI_MODE_PROBE_MODES.length;
                recordFeatureFailure("ClickGuiModeProbe", "font-ready", failure);
                runtimeMilestone("clickgui-mode-probe-fail:FONT_NOT_READY");
}
            return;
}
        runtimeMilestone("clickgui-font-ready");
        this.clickGuiModeProbeWaitTicks = 0;
        String mode = CLICKGUI_MODE_PROBE_MODES[this.clickGuiModeProbeIndex];
        try {
            if (ClickGUI.mode == null) {
                throw new IllegalStateException("ClickGUI.mode is null");
}
            if (this.clickGuiModeProbeOriginalMode == null) {
                this.clickGuiModeProbeOriginalMode = ClickGUI.mode.Y();
}

            if (this.clickGuiModeProbePhase == 0) {
                if (this.c.currentScreen != null) {
                    if (++this.clickGuiModeProbeWaitTicks > 400) {
                        throw new IllegalStateException("Screen remained open before probe mode " + mode
                                + ": " + this.c.currentScreen.getClass().getName());
}
                    return;
}
                this.clickGuiModeProbeWaitTicks = 0;
                ClickGUI.mode.i(mode);
                runtimeMilestone("clickgui-mode-probe-request:" + mode);
                ClickGUI.O(2169, 8663, (char)12652);
                if (this.c.currentScreen == null) {
                    throw new IllegalStateException("ClickGUI screen stayed null for mode " + mode);
}
                runtimeMilestone("clickgui-mode-probe-open:" + mode + ":" + this.c.currentScreen.getClass().getName());
                this.clickGuiModeProbePhase = 1;
                return;
}

            if (this.c.currentScreen != null) {
                if (++this.clickGuiModeProbeWaitTicks > 1200) {
                    throw new IllegalStateException("Probe mode was not closed by harness: " + mode
                            + " screen=" + this.c.currentScreen.getClass().getName());
}
                return;
}

            runtimeMilestone("clickgui-mode-probe-close:" + mode);
            ++this.clickGuiModeProbeIndex;
            this.clickGuiModeProbePhase = 0;
            this.clickGuiModeProbeWaitTicks = 0;
            if (this.clickGuiModeProbeIndex >= CLICKGUI_MODE_PROBE_MODES.length) {
                ClickGUI.mode.i(this.clickGuiModeProbeOriginalMode);
                runtimeMilestone("clickgui-mode-probe-pass:3:restored=" + this.clickGuiModeProbeOriginalMode);
}
}
        catch (Throwable failure) {
            this.clickGuiModeProbeIndex = CLICKGUI_MODE_PROBE_MODES.length;
            if (ClickGUI.mode != null && this.clickGuiModeProbeOriginalMode != null) {
                try {
                    ClickGUI.mode.i(this.clickGuiModeProbeOriginalMode);
}
                catch (Throwable ignored) {
}
}
            recordFeatureFailure("ClickGuiModeProbe:" + mode, "open-render-close", failure);
            runtimeMilestone("clickgui-mode-probe-fail:" + mode + ":" + failure.getClass().getName());
}
}

    private void pumpPromotedPersistenceLiveVerify() {
        if (this.persistencePromotedLiveVerified
                || !Boolean.getBoolean("abyss.persistenceProbeMatrix")
                || System.getProperty("abyss.persistenceProbeExpectedClickGuiScale") == null
                || this.runtimeWorldHeartbeatTicks < 100L) {
            return;
}
        this.persistencePromotedLiveVerified = true;
        try {
            AntiNick antiNick = Modules.J(AntiNick.class);
            if (antiNick == null) {
                throw new IllegalStateException("Persisted AntiNick module is missing");
}
            boolean ownerActive = w != null && w.isOwnerActive(antiNick);
            if (!antiNick.o() || antiNick.l() || antiNick.K() || !antiNick.P() || !ownerActive) {
                throw new IllegalStateException("Persisted AntiNick did not settle active after restart"
                        + " enabled=" + antiNick.o()
                        + " pendingEnable=" + antiNick.l()
                        + " pendingDisable=" + antiNick.K()
                        + " subscribed=" + antiNick.P()
                        + " ownerActive=" + ownerActive);
}
            String suffix = AntiNick.suffix == null ? null : AntiNick.suffix.X();
            if (!"OPENABYSS_PROMOTED_PERSIST_7E51".equals(suffix)) {
                throw new IllegalStateException("Persisted AntiNick suffix changed after restart: " + suffix);
}
            runtimeMilestone("persistence-promoted-live-pass:AntiNick:enabled=true:subscribed=true:ownerActive=true:suffix=OPENABYSS_PROMOTED_PERSIST_7E51");
}
        catch (Throwable failure) {
            recordFeatureFailure("PersistenceProbe:AntiNick", "restart-live-subscription", failure);
            runtimeMilestone("persistence-promoted-live-fail:AntiNick:" + failure.getClass().getName());
}
}

    private void pumpPersistenceSeedProbe() {
        if (this.persistenceProbeSeeded || !Boolean.getBoolean("abyss.persistenceProbeSeed")) {
            return;
}
        this.persistenceProbeSeeded = true;
        try {
            if (ClickGUI.scale == null || ClickGUI.mode == null || ClickGUI.keybind == null
                    || Notifications.textShadow == null || Velocity.horizontal == null
                    || Theme.customColor1 == null || AntiNick.suffix == null) {
                throw new IllegalStateException("Persistence matrix setting is null");
}
            ClickGUI.scale.o((byte)0, 0L, 1.75f);
            ClickGUI.mode.i("RAVEN");
            ClickGUI.keybind.O("LSHIFT");
            Notifications.textShadow.v(false, 0L);
            Velocity.horizontal.d(67);
            Theme.customColor1.e("A1B2C3");
            AntiNick.suffix.O("OPENABYSS_PROMOTED_PERSIST_7E51");

            if (Math.abs(ClickGUI.scale.L() - 1.75f) > 0.001f
                    || !ClickGUI.mode.R("RAVEN")
                    || !"LSHIFT".equals(ClickGUI.keybind.X())
                    || Notifications.textShadow.c()
                    || Velocity.horizontal.k() != 67
                    || !"A1B2C3".equals(Theme.customColor1.Q())
                    || !"OPENABYSS_PROMOTED_PERSIST_7E51".equals(AntiNick.suffix.X())) {
                throw new IllegalStateException("Persistence matrix did not accept seed values"
                        + " scale=" + ClickGUI.scale.L()
                        + " mode=" + ClickGUI.mode.Y()
                        + " keybind=" + ClickGUI.keybind.X()
                        + " textShadow=" + Notifications.textShadow.c()
                        + " horizontal=" + Velocity.horizontal.k()
                        + " color=" + Theme.customColor1.Q()
                        + " antiNickSuffix=" + AntiNick.suffix.X());
}
            FullBright persistedModule = Modules.J(FullBright.class);
            if (persistedModule == null) {
                throw new IllegalStateException("FullBright module is missing");
}
            persistedModule.I(0L, true);
            if (!persistedModule.o()) {
                throw new IllegalStateException("FullBright did not enter enabled state before save");
}
            AntiNick promotedPersistedModule = Modules.J(AntiNick.class);
            if (promotedPersistedModule == null) {
                throw new IllegalStateException("AntiNick module is missing");
}
            promotedPersistedModule.I(0L, true);
            if (!promotedPersistedModule.o()) {
                throw new IllegalStateException("AntiNick did not enter enabled state before save");
}
            AbyssConfig.SaveResult result = AbyssConfig.save("current");
            if (result == null || !result.ok) {
                throw new IllegalStateException("current config save failed: " + String.valueOf(result));
}
            runtimeMilestone("persistence-probe-seed-pass:ClickGUI.Scale=1.75,FullBright=true,AntiNick=true");
            runtimeMilestone("persistence-matrix-seed-pass:boolean=false,percentage=67,number=1.75,mode=RAVEN,color=A1B2C3,text=LSHIFT,module=true,promotedModule=true,promotedText=OPENABYSS_PROMOTED_PERSIST_7E51");
}
        catch (Throwable failure) {
            recordFeatureFailure("PersistenceProbe:ClickGUI", "seed-save", failure);
            runtimeMilestone("persistence-probe-seed-fail:" + failure.getClass().getName());
}
}

    private void probeVelocityPacketEffect() throws Throwable {
        Velocity probe = Modules.J(Velocity.class);
        if (probe == null || this.c.thePlayer == null) {
            throw new IllegalStateException("Velocity module/player unavailable");
        }
        boolean originalModify = Velocity.modifyVelocity.c();
        boolean originalDelay = Velocity.delayVelocity.c();
        int originalHorizontal = Velocity.horizontal.k();
        int originalVertical = Velocity.vertical.k();
        try {
            Velocity.modifyVelocity.v(true, 0L);
            Velocity.delayVelocity.v(false, 0L);
            Velocity.horizontal.d(0);
            Velocity.vertical.d(0);

            ReceivePacketEvent event = new ReceivePacketEvent(
                    new S12PacketEntityVelocity(this.c.thePlayer.getEntityId(), 0.8, 0.4, -0.6));
            runtimeMilestone("high-risk-functional-probe-dispatch:Velocity:S12PacketEntityVelocity");
            probe.onReceivePacket(0, (char)0, 0, event);
            if (!event.a()) {
                throw new IllegalStateException("Velocity did not cancel local S12PacketEntityVelocity");
            }
            runtimeMilestone("high-risk-functional-probe-effect-pass:Velocity:cancelled=true");
        }
        finally {
            Velocity.modifyVelocity.v(originalModify, 0L);
            Velocity.delayVelocity.v(originalDelay, 0L);
            Velocity.horizontal.d(originalHorizontal);
            Velocity.vertical.d(originalVertical);
        }
    }

    private void probeNoSlowSwordEffect() throws Throwable {
        NoSlow probe = Modules.J(NoSlow.class);
        if (probe == null || this.c.thePlayer == null) {
            throw new IllegalStateException("NoSlow module/player unavailable");
        }
        String originalMode = NoSlow.mode.Y();
        String originalSwordMode = NoSlow.swordMode.Y();
        int originalSlowDown = NoSlow.slowDown.k();
        boolean originalOnlyAutoblock = NoSlow.onlyEnableWhenAutoblock.c();
        boolean originalSword = NoSlow.sword.c();
        int slot = this.c.thePlayer.inventory.currentItem;
        ItemStack originalStack = this.c.thePlayer.inventory.getStackInSlot(slot);
        try {
            NoSlow.mode.i("VANILLA");
            NoSlow.swordMode.i("VANILLA");
            NoSlow.slowDown.d(0);
            NoSlow.onlyEnableWhenAutoblock.v(false, 0L);
            NoSlow.sword.v(true, 0L);
            this.c.thePlayer.inventory.setInventorySlotContents(slot, new ItemStack(Items.diamond_sword));

            RedirectIsUsingItemEvent event = new RedirectIsUsingItemEvent(0.2f);
            runtimeMilestone("high-risk-functional-probe-dispatch:NoSlow:RedirectIsUsingItemEvent");
            probe.onRedirectIsUsingItem((byte)0, 0, 0, event);
            if (Math.abs(event.q() - 1.0f) > 0.0001f || event.v()) {
                throw new IllegalStateException("NoSlow slowdown mismatch multiplier=" + event.q()
                        + " cancelled=" + event.v());
            }
            runtimeMilestone("high-risk-functional-probe-effect-pass:NoSlow:multiplier=1.0");
        }
        finally {
            this.c.thePlayer.inventory.setInventorySlotContents(slot, originalStack);
            NoSlow.mode.i(originalMode);
            NoSlow.swordMode.i(originalSwordMode);
            NoSlow.slowDown.d(originalSlowDown);
            NoSlow.onlyEnableWhenAutoblock.v(originalOnlyAutoblock, 0L);
            NoSlow.sword.v(originalSword, 0L);
        }
    }

    private void probeBlinkBufferEffect() throws Throwable {
        Blink probe = Modules.J(Blink.class);
        if (probe == null) {
            throw new IllegalStateException("Blink module unavailable");
        }
        if (PacketManager.e() || !PacketManager.u.isEmpty()) {
            throw new IllegalStateException("Blink probe requires clean packet buffer state buffering="
                    + PacketManager.e() + " queued=" + PacketManager.u.size());
        }
        String originalMode = Blink.mode.Y();
        boolean originalAutoDisable = Blink.autoDisable.c();
        try {
            Blink.mode.i("NORMAL");
            Blink.autoDisable.v(false, 0L);
            runtimeMilestone("high-risk-functional-probe-dispatch:Blink:PostUpdateWalkingPlayerEvent");
            probe.onPostUpdateWalkingPlayer(new PostUpdateWalkingPlayerEvent(0), 0L);
            if (!PacketManager.e()) {
                throw new IllegalStateException("Blink NORMAL handler did not enable packet buffering");
            }
            runtimeMilestone("high-risk-functional-probe-effect-pass:Blink:buffering=true");
            probe.A(0L);
            if (PacketManager.e() || !PacketManager.u.isEmpty()) {
                throw new IllegalStateException("Blink disable did not restore clean packet state buffering="
                        + PacketManager.e() + " queued=" + PacketManager.u.size());
            }
            runtimeMilestone("high-risk-functional-probe-restore-pass:Blink:buffering=false");
        }
        finally {
            if (PacketManager.e()) {
                PacketManager.j();
                PacketManager.M(false);
            }
            Blink.mode.i(originalMode);
            Blink.autoDisable.v(originalAutoDisable, 0L);
        }
    }

    private void probeSpeedAutoJumpEffect() throws Throwable {
        Speed probe = Modules.J(Speed.class);
        if (probe == null || this.c.thePlayer == null) {
            throw new IllegalStateException("Speed module/player unavailable");
        }
        String originalMode = Speed.mode.Y();
        float originalSpeed = Speed.speed.L();
        boolean originalForward = this.c.gameSettings.keyBindForward.isKeyDown();
        boolean originalOnGround = this.c.thePlayer.onGround;
        double originalMotionX = this.c.thePlayer.motionX;
        double originalMotionY = this.c.thePlayer.motionY;
        double originalMotionZ = this.c.thePlayer.motionZ;
        try {
            Speed.mode.i("AUTO_JUMP");
            Speed.speed.o((byte)0, 0L, 1.0f);
            this.c.thePlayer.onGround = true;
            this.c.thePlayer.motionY = 0.0;
            KeyBindUtil.A(0L, this.c.gameSettings.keyBindForward.getKeyCode(), true);

            runtimeMilestone("high-risk-functional-probe-dispatch:Speed:AUTO_JUMP");
            probe.onPreUpdate(0L, new PreUpdateEvent(0, 0, 0));
            if (this.c.thePlayer.motionY < 0.39) {
                throw new IllegalStateException("Speed AUTO_JUMP did not jump motionY=" + this.c.thePlayer.motionY);
            }
            runtimeMilestone("high-risk-functional-probe-effect-pass:Speed:motionY="
                    + this.c.thePlayer.motionY);
        }
        finally {
            KeyBindUtil.A(0L, this.c.gameSettings.keyBindForward.getKeyCode(), originalForward);
            this.c.thePlayer.onGround = originalOnGround;
            this.c.thePlayer.motionX = originalMotionX;
            this.c.thePlayer.motionY = originalMotionY;
            this.c.thePlayer.motionZ = originalMotionZ;
            Speed.mode.i(originalMode);
            Speed.speed.o((byte)0, 0L, originalSpeed);
        }
    }

    private void probeSprintKeyEffect() throws Throwable {
        Sprint probe = Modules.J(Sprint.class);
        if (probe == null || this.c.thePlayer == null) {
            throw new IllegalStateException("Sprint module/player unavailable");
        }
        int sprintKey = this.c.gameSettings.keyBindSprint.getKeyCode();
        boolean originalKey = this.c.gameSettings.keyBindSprint.isKeyDown();
        try {
            KeyBindUtil.A(0L, sprintKey, false);
            if (this.c.gameSettings.keyBindSprint.isKeyDown()) {
                throw new IllegalStateException("Sprint probe could not clear sprint key precondition");
            }
            runtimeMilestone("high-risk-functional-probe2-dispatch:Sprint:PreUpdateEvent");
            probe.onPreUpdate((short)0, new PreUpdateEvent(0, 0, 0), (char)0, 0);
            if (!this.c.gameSettings.keyBindSprint.isKeyDown()) {
                throw new IllegalStateException("Sprint pre-update did not set sprint key");
            }
            runtimeMilestone("high-risk-functional-probe2-effect-pass:Sprint:key=true");
        }
        finally {
            KeyBindUtil.A(0L, sprintKey, originalKey);
        }
    }

    private void probeBackTrackSelectionAndCleanup() throws Throwable {
        BackTrack probe = Modules.J(BackTrack.class);
        if (probe == null || this.c.thePlayer == null || this.c.theWorld == null) {
            throw new IllegalStateException("BackTrack module/player/world unavailable");
        }
        boolean originalPlayers = BackTrack.players.c();
        boolean originalMobs = BackTrack.mobs.c();
        boolean originalAnimals = BackTrack.animals.c();
        boolean originalBosses = BackTrack.bosses.c();
        float originalMinRange = BackTrack.minRange.L();
        float originalMaxRange = BackTrack.maxRange.L();
        float originalMinDelay = BackTrack.minDelay.L();
        float originalMaxDelay = BackTrack.maxDelay.L();
        boolean originalHold = IncomingPacketHold.r();
        EntityZombie target = new EntityZombie(this.c.theWorld);
        try {
            BackTrack.N = null;
            IncomingPacketHold.s();
            IncomingPacketHold.X(false);
            BackTrack.players.v(false, 0L);
            BackTrack.mobs.v(true, 0L);
            BackTrack.animals.v(false, 0L);
            BackTrack.bosses.v(false, 0L);
            BackTrack.minRange.o((byte)0, 0L, 1.0f);
            BackTrack.maxRange.o((byte)0, 0L, 5.0f);
            BackTrack.minDelay.o((byte)0, 0L, 100.0f);
            BackTrack.maxDelay.o((byte)0, 0L, 100.0f);
            target.setPosition(this.c.thePlayer.posX + 3.0, this.c.thePlayer.posY, this.c.thePlayer.posZ);

            runtimeMilestone("high-risk-functional-probe2-dispatch:BackTrack:AttackEntityEvent");
            probe.onAttackEntity(new AttackEntityEvent(target, (char)0, (short)0, 0), 0L);
            if (BackTrack.N != target) {
                throw new IllegalStateException("BackTrack did not select controlled target");
            }
            runtimeMilestone("high-risk-functional-probe2-effect-pass:BackTrack:selected=true");

            probe.onRender2D((char)0, 0,
                    new Render2DEvent(0, (short)0, 0.0f, (short)0, new ScaledResolution(this.c)),
                    (char)0);
            if (!IncomingPacketHold.r()) {
                throw new IllegalStateException("BackTrack render path did not engage incoming packet hold");
            }
            runtimeMilestone("high-risk-functional-probe2-effect-pass:BackTrack:hold=true");

            target.setPosition(this.c.thePlayer.posX + 0.25, this.c.thePlayer.posY, this.c.thePlayer.posZ);
            probe.onPreUpdate((char)0, 0, new PreUpdateEvent(0, 0, 0), (short)0);
            if (BackTrack.N != null || IncomingPacketHold.r()) {
                throw new IllegalStateException("BackTrack cleanup failed target=" + BackTrack.N
                        + " hold=" + IncomingPacketHold.r());
            }
            runtimeMilestone("high-risk-functional-probe2-restore-pass:BackTrack:target=null:hold=false");
        }
        finally {
            BackTrack.N = null;
            IncomingPacketHold.s();
            IncomingPacketHold.X(originalHold);
            BackTrack.players.v(originalPlayers, 0L);
            BackTrack.mobs.v(originalMobs, 0L);
            BackTrack.animals.v(originalAnimals, 0L);
            BackTrack.bosses.v(originalBosses, 0L);
            BackTrack.minRange.o((byte)0, 0L, originalMinRange);
            BackTrack.maxRange.o((byte)0, 0L, originalMaxRange);
            BackTrack.minDelay.o((byte)0, 0L, originalMinDelay);
            BackTrack.maxDelay.o((byte)0, 0L, originalMaxDelay);
        }
    }

    private void probeNoInteractContainerEffect() throws Throwable {
        NoInteract probe = Modules.J(NoInteract.class);
        if (probe == null || this.c.thePlayer == null || this.c.theWorld == null) {
            throw new IllegalStateException("NoInteract module/player/world unavailable");
        }
        BlockPos pos = new BlockPos(
                MathHelper.floor_double(this.c.thePlayer.posX) + 2,
                MathHelper.floor_double(this.c.thePlayer.posY),
                MathHelper.floor_double(this.c.thePlayer.posZ));
        IBlockState original = this.c.theWorld.getBlockState(pos);
        try {
            if (!this.c.theWorld.setBlockState(pos, Blocks.chest.getDefaultState(), 3)) {
                throw new IllegalStateException("NoInteract probe could not place temporary chest");
            }
            PlayerRightClickEvent event = new PlayerRightClickEvent(
                    this.c.theWorld,
                    this.c.thePlayer.getHeldItem(),
                    pos,
                    EnumFacing.UP,
                    new Vec3(pos).addVector(0.5, 1.0, 0.5));
            runtimeMilestone("high-risk-functional-probe2-dispatch:NoInteract:PlayerRightClickEvent");
            probe.onPlayerRightClick(event);
            if (!event.a()) {
                throw new IllegalStateException("NoInteract did not cancel temporary chest interaction");
            }
            runtimeMilestone("high-risk-functional-probe2-effect-pass:NoInteract:cancelled=true");
        }
        finally {
            this.c.theWorld.setBlockState(pos, original, 3);
        }
    }

    private void restorePhysicalInputFunctionalProbe() {
        try {
            AutoClicker autoClicker = Modules.J(AutoClicker.class);
            if (this.physicalInputAutoSaved) {
                AutoClicker.breakBlocks.v(this.physicalInputAutoOriginalBreakBlocks, 0L);
                AutoClicker.sag.v(this.physicalInputAutoOriginalSag, 0L);
                if (autoClicker != null && autoClicker.o() != this.physicalInputAutoOriginalEnabled) {
                    autoClicker.I(0L, this.physicalInputAutoOriginalEnabled);
                }
            }
        }
        catch (Throwable failure) {
            recordFeatureFailure("PhysicalInputFunctionalProbe:AutoClicker", "restore", failure);
        }
        try {
            FastFall fastFall = Modules.J(FastFall.class);
            if (this.physicalInputFastFallSaved) {
                FastFall.requireScaffold.v(this.physicalInputFastFallOriginalRequireScaffold, 0L);
                FastFall.horizontalSpeedRestriction.v(this.physicalInputFastFallOriginalHorizontalRestriction, 0L);
                if (fastFall != null && fastFall.o() != this.physicalInputFastFallOriginalEnabled) {
                    fastFall.I(0L, this.physicalInputFastFallOriginalEnabled);
                }
            }
        }
        catch (Throwable failure) {
            recordFeatureFailure("PhysicalInputFunctionalProbe:FastFall", "restore", failure);
        }
        if (this.physicalInputPlayerStateSaved && this.c.thePlayer != null) {
            this.c.thePlayer.onGround = this.physicalInputOriginalOnGround;
            this.c.thePlayer.motionY = this.physicalInputOriginalMotionY;
        }
        AutoClicker.I = false;
    }

    private void pumpPhysicalInputFunctionalProbe() {
        if (!Boolean.getBoolean("abyss.physicalInputFunctionalProbe")
                || this.physicalInputFunctionalProbeStage < 0
                || this.physicalInputFunctionalProbeStage >= 7) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe2")
                && this.highRiskFunctionalProbe2Stage < 3) return;

        try {
            AutoClicker autoClicker = Modules.J(AutoClicker.class);
            FastFall fastFall = Modules.J(FastFall.class);
            switch (this.physicalInputFunctionalProbeStage) {
                case 0:
                    if (autoClicker == null) {
                        throw new IllegalStateException("AutoClicker module unavailable");
                    }
                    this.physicalInputAutoOriginalEnabled = autoClicker.o();
                    this.physicalInputAutoOriginalBreakBlocks = AutoClicker.breakBlocks.c();
                    this.physicalInputAutoOriginalSag = AutoClicker.sag.c();
                    this.physicalInputAutoSaved = true;
                    AutoClicker.breakBlocks.v(false, 0L);
                    AutoClicker.sag.v(false, 0L);
                    AutoClicker.I = false;
                    if (!autoClicker.o()) {
                        autoClicker.I(0L, true);
                    }
                    ++this.physicalInputFunctionalProbeStage;
                    this.physicalInputFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("physical-input-functional-probe-enable-request:AutoClicker");
                    return;
                case 1:
                    ++this.physicalInputFunctionalProbeWaitTicks;
                    if (autoClicker != null && autoClicker.o() && autoClicker.P() && w.isOwnerActive(autoClicker)) {
                        ++this.physicalInputFunctionalProbeStage;
                        this.physicalInputFunctionalProbeWaitTicks = 0;
                        runtimeMilestone("physical-input-functional-probe-ready:AutoClicker");
                        return;
                    }
                    if (this.physicalInputFunctionalProbeWaitTicks > 160) {
                        throw new IllegalStateException("AutoClicker did not become active/subscribed");
                    }
                    return;
                case 2:
                    ++this.physicalInputFunctionalProbeWaitTicks;
                    if (AutoClicker.I) {
                        runtimeMilestone("physical-input-functional-probe-effect-pass:AutoClicker:physicalAttack=true");
                        AutoClicker.breakBlocks.v(this.physicalInputAutoOriginalBreakBlocks, 0L);
                        AutoClicker.sag.v(this.physicalInputAutoOriginalSag, 0L);
                        if (autoClicker != null && !this.physicalInputAutoOriginalEnabled) {
                            autoClicker.I(0L, false);
                        }
                        ++this.physicalInputFunctionalProbeStage;
                        this.physicalInputFunctionalProbeWaitTicks = 0;
                        return;
                    }
                    if (this.physicalInputFunctionalProbeWaitTicks > 600) {
                        throw new IllegalStateException("AutoClicker did not observe physical attack input");
                    }
                    return;
                case 3:
                    ++this.physicalInputFunctionalProbeWaitTicks;
                    if (this.physicalInputAutoOriginalEnabled
                            || autoClicker != null && !autoClicker.o() && !autoClicker.P() && !w.isOwnerActive(autoClicker)) {
                        runtimeMilestone("physical-input-functional-probe-restore-pass:AutoClicker");
                        if (fastFall == null) {
                            throw new IllegalStateException("FastFall module unavailable");
                        }
                        this.physicalInputFastFallOriginalEnabled = fastFall.o();
                        this.physicalInputFastFallOriginalRequireScaffold = FastFall.requireScaffold.c();
                        this.physicalInputFastFallOriginalHorizontalRestriction = FastFall.horizontalSpeedRestriction.c();
                        this.physicalInputFastFallSaved = true;
                        FastFall.requireScaffold.v(false, 0L);
                        FastFall.horizontalSpeedRestriction.v(false, 0L);
                        if (!fastFall.o()) {
                            fastFall.I(0L, true);
                        }
                        ++this.physicalInputFunctionalProbeStage;
                        this.physicalInputFunctionalProbeWaitTicks = 0;
                        runtimeMilestone("physical-input-functional-probe-enable-request:FastFall");
                        return;
                    }
                    if (this.physicalInputFunctionalProbeWaitTicks > 160) {
                        throw new IllegalStateException("AutoClicker did not restore disabled state");
                    }
                    return;
                case 4:
                    ++this.physicalInputFunctionalProbeWaitTicks;
                    if (fastFall != null && fastFall.o() && fastFall.P() && w.isOwnerActive(fastFall)) {
                        if (this.c.thePlayer == null) {
                            throw new IllegalStateException("Player disappeared before FastFall physical input probe");
                        }
                        this.physicalInputOriginalOnGround = this.c.thePlayer.onGround;
                        this.physicalInputOriginalMotionY = this.c.thePlayer.motionY;
                        this.physicalInputPlayerStateSaved = true;
                        this.c.thePlayer.onGround = false;
                        this.c.thePlayer.motionY = 0.0;
                        ++this.physicalInputFunctionalProbeStage;
                        this.physicalInputFunctionalProbeWaitTicks = 0;
                        runtimeMilestone("physical-input-functional-probe-ready:FastFall");
                        return;
                    }
                    if (this.physicalInputFunctionalProbeWaitTicks > 160) {
                        throw new IllegalStateException("FastFall did not become active/subscribed");
                    }
                    return;
                case 5:
                    ++this.physicalInputFunctionalProbeWaitTicks;
                    if (this.c.thePlayer == null) {
                        throw new IllegalStateException("Player disappeared during FastFall physical input probe");
                    }
                    boolean physicalJumpDown = KeyBindUtil.V(
                            this.c.gameSettings.keyBindJump.getKeyCode(), 64165991731362L);
                    if (physicalJumpDown) {
                        runtimeMilestone("physical-input-functional-probe-input-seen:FastFall:jump=true");
                    }
                    if (this.c.thePlayer.motionY <= -0.9) {
                        runtimeMilestone("physical-input-functional-probe-effect-pass:FastFall:motionY="
                                + this.c.thePlayer.motionY);
                        this.c.thePlayer.onGround = this.physicalInputOriginalOnGround;
                        this.c.thePlayer.motionY = this.physicalInputOriginalMotionY;
                        FastFall.requireScaffold.v(this.physicalInputFastFallOriginalRequireScaffold, 0L);
                        FastFall.horizontalSpeedRestriction.v(this.physicalInputFastFallOriginalHorizontalRestriction, 0L);
                        if (fastFall != null && !this.physicalInputFastFallOriginalEnabled) {
                            fastFall.I(0L, false);
                        }
                        ++this.physicalInputFunctionalProbeStage;
                        this.physicalInputFunctionalProbeWaitTicks = 0;
                        return;
                    }
                    this.c.thePlayer.onGround = false;
                    this.c.thePlayer.motionY = 0.0;
                    if (this.physicalInputFunctionalProbeWaitTicks > 600) {
                        throw new IllegalStateException("FastFall did not produce effect; physicalJumpDown="
                                + physicalJumpDown + " motionY=" + this.c.thePlayer.motionY);
                    }
                    return;
                case 6:
                    ++this.physicalInputFunctionalProbeWaitTicks;
                    if (this.physicalInputFastFallOriginalEnabled
                            || fastFall != null && !fastFall.o() && !fastFall.P() && !w.isOwnerActive(fastFall)) {
                        runtimeMilestone("physical-input-functional-probe-restore-pass:FastFall");
                        runtimeMilestone("physical-input-functional-probe-pass:2");
                        ++this.physicalInputFunctionalProbeStage;
                        return;
                    }
                    if (this.physicalInputFunctionalProbeWaitTicks > 160) {
                        throw new IllegalStateException("FastFall did not restore disabled state");
                    }
                    return;
                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.restorePhysicalInputFunctionalProbe();
            this.physicalInputFunctionalProbeStage = -1;
            recordFeatureFailure("PhysicalInputFunctionalProbe", "stage", failure);
            runtimeMilestone("physical-input-functional-probe-fail:" + failure.getClass().getName());
        }
    }

    private void probeWTapMovementPauseEffect() throws Throwable {
        WTap probe = Modules.J(WTap.class);
        if (probe == null || this.c.theWorld == null || this.c.thePlayer == null) {
            throw new IllegalStateException("WTap module/player/world unavailable");
        }

        boolean originalRequireTargetDamage = WTap.requireTargetDamage.c();
        boolean originalRequireOnGround = WTap.requireOnGround.c();
        boolean originalUseBlockInstead = WTap.useBlockInstead.c();
        float originalMinPause = WTap.minPauseTick.L();
        float originalMaxPause = WTap.maxPauseTick.L();
        float originalInterval = WTap.interval.L();
        EntityOtherPlayerMP target = new EntityOtherPlayerMP(
                this.c.theWorld,
                new GameProfile(new UUID(0L, 0x57544150L), "OpenAbyssWTapProbe"));
        try {
            WTap.requireTargetDamage.v(false, 0L);
            WTap.requireOnGround.v(false, 0L);
            WTap.useBlockInstead.v(false, 0L);
            WTap.minPauseTick.o((byte)0, 0L, 1.0f);
            WTap.maxPauseTick.o((byte)0, 0L, 1.0f);
            WTap.interval.o((byte)0, 0L, 0.0f);

            runtimeMilestone("high-risk-functional-probe3-dispatch:WTap:AttackEntityEvent");
            probe.onAttackEntity(0L, new AttackEntityEvent(target, (char)0, (short)0, 0));
            probe.onPreMouseInput(0L, new PreMouseInputEvent());

            MoveInputEvent move = new MoveInputEvent(1.0f, 1.0f, false, false, 0.0);
            probe.onMoveInput(move, 0L);
            if (Math.abs(move.t()) > 0.0001f || Math.abs(move.R()) > 0.0001f) {
                throw new IllegalStateException("WTap movement pause mismatch forward="
                        + move.t() + " strafe=" + move.R());
            }
            runtimeMilestone("high-risk-functional-probe3-effect-pass:WTap:forward=0.0:strafe=0.0");

            probe.A(0L);
            MoveInputEvent restored = new MoveInputEvent(1.0f, 1.0f, false, false, 0.0);
            probe.onMoveInput(restored, 0L);
            if (Math.abs(restored.t() - 1.0f) > 0.0001f || Math.abs(restored.R() - 1.0f) > 0.0001f) {
                throw new IllegalStateException("WTap reset hook did not clear movement pause forward="
                        + restored.t() + " strafe=" + restored.R());
            }
            runtimeMilestone("high-risk-functional-probe3-restore-pass:WTap");
        }
        finally {
            try {
                probe.A(0L);
            }
            catch (Throwable ignored) {
            }
            WTap.requireTargetDamage.v(originalRequireTargetDamage, 0L);
            WTap.requireOnGround.v(originalRequireOnGround, 0L);
            WTap.useBlockInstead.v(originalUseBlockInstead, 0L);
            WTap.minPauseTick.o((byte)0, 0L, originalMinPause);
            WTap.maxPauseTick.o((byte)0, 0L, originalMaxPause);
            WTap.interval.o((byte)0, 0L, originalInterval);
        }
    }

    @SuppressWarnings("unchecked")
    private void probeChestEspOpenedFilterEffect() throws Throwable {
        ChestESP probe = Modules.J(ChestESP.class);
        if (probe == null || this.c.theWorld == null || this.c.thePlayer == null) {
            throw new IllegalStateException("ChestESP module/player/world unavailable");
        }

        Field boxesField = null;
        Field openedField = null;
        for (Field field : ChestESP.class.getDeclaredFields()) {
            if (List.class.isAssignableFrom(field.getType())) {
                boxesField = field;
            } else if (Set.class.isAssignableFrom(field.getType())) {
                openedField = field;
            }
        }
        if (boxesField == null || openedField == null) {
            throw new IllegalStateException("ChestESP cache fields were not found");
        }
        boxesField.setAccessible(true);
        openedField.setAccessible(true);
        List<AxisAlignedBB> boxes = (List<AxisAlignedBB>)boxesField.get(probe);
        Set<BlockPos> opened = (Set<BlockPos>)openedField.get(probe);
        if (boxes == null || opened == null) {
            throw new IllegalStateException("ChestESP cache fields are null");
        }

        BlockPos pos = null;
        int baseX = MathHelper.floor_double(this.c.thePlayer.posX);
        int baseY = MathHelper.floor_double(this.c.thePlayer.posY);
        int baseZ = MathHelper.floor_double(this.c.thePlayer.posZ);
        outer:
        for (int dy = 0; dy <= 2; ++dy) {
            for (int dx = 3; dx <= 6; ++dx) {
                BlockPos candidate = new BlockPos(baseX + dx, baseY + dy, baseZ);
                if (this.c.theWorld.isAirBlock(candidate)) {
                    pos = candidate;
                    break outer;
                }
            }
        }
        if (pos == null) {
            throw new IllegalStateException("ChestESP probe found no temporary air position");
        }

        IBlockState originalState = this.c.theWorld.getBlockState(pos);
        TileEntity originalTile = this.c.theWorld.getTileEntity(pos);
        boolean originalIgnoreOpened = ChestESP.ignoreOpened.c();
        try {
            probe.A(0L);
            ChestESP.ignoreOpened.v(false, 0L);
            if (!this.c.theWorld.setBlockState(pos, Blocks.chest.getDefaultState(), 3)) {
                throw new IllegalStateException("ChestESP probe could not place temporary chest");
            }
            TileEntity tile = this.c.theWorld.getTileEntity(pos);
            if (!(tile instanceof TileEntityChest)) {
                TileEntityChest replacement = new TileEntityChest();
                this.c.theWorld.setTileEntity(pos, replacement);
                tile = replacement;
            }
            ((TileEntityChest)tile).numPlayersUsing = 0;

            runtimeMilestone("high-risk-functional-probe4-dispatch:ChestESP:PostTickEvent");
            probe.onPostTick(new PostTickEvent());
            boolean visible = false;
            for (AxisAlignedBB box : boxes) {
                if (box.minX < pos.getX() + 0.5 && box.maxX > pos.getX() + 0.5
                        && box.minY <= pos.getY() + 0.5 && box.maxY >= pos.getY() + 0.5
                        && box.minZ < pos.getZ() + 0.5 && box.maxZ > pos.getZ() + 0.5) {
                    visible = true;
                    break;
                }
            }
            if (!visible) {
                throw new IllegalStateException("ChestESP did not cache temporary chest boxes=" + boxes.size());
            }
            runtimeMilestone("high-risk-functional-probe4-effect-pass:ChestESP:visible=true");

            ChestESP.ignoreOpened.v(true, 0L);
            PlayerRightClickEvent click = new PlayerRightClickEvent(
                    this.c.theWorld,
                    this.c.thePlayer.getHeldItem(),
                    pos,
                    EnumFacing.UP,
                    new Vec3(pos).addVector(0.5, 1.0, 0.5));
            probe.onPlayerRightClick(click);

            boolean stillVisible = false;
            for (AxisAlignedBB box : boxes) {
                if (box.minX < pos.getX() + 0.5 && box.maxX > pos.getX() + 0.5
                        && box.minY <= pos.getY() + 0.5 && box.maxY >= pos.getY() + 0.5
                        && box.minZ < pos.getZ() + 0.5 && box.maxZ > pos.getZ() + 0.5) {
                    stillVisible = true;
                    break;
                }
            }
            if (stillVisible || !opened.contains(pos)) {
                throw new IllegalStateException("ChestESP ignore-opened mismatch visible="
                        + stillVisible + " opened=" + opened.contains(pos));
            }
            runtimeMilestone("high-risk-functional-probe4-effect-pass:ChestESP:ignoreOpened=true:hidden=true");
        }
        finally {
            try {
                probe.A(0L);
            }
            catch (Throwable ignored) {
            }
            ChestESP.ignoreOpened.v(originalIgnoreOpened, 0L);
            this.c.theWorld.setBlockState(pos, originalState, 3);
            if (originalTile != null) {
                this.c.theWorld.setTileEntity(pos, originalTile);
            } else {
                this.c.theWorld.removeTileEntity(pos);
            }
        }

        if (!boxes.isEmpty() || !opened.isEmpty()) {
            throw new IllegalStateException("ChestESP reset hook did not clear caches boxes="
                    + boxes.size() + " opened=" + opened.size());
        }
        runtimeMilestone("high-risk-functional-probe4-restore-pass:ChestESP");
    }

    private void verifyAntiDebuffHookEffect(boolean expectBypass, String phase) {
        if (ModuleManager.O != Modules.J(AntiDebuff.class)) {
            throw new IllegalStateException("AntiDebuff hook singleton differs from registry singleton");
        }
        EntityZombie target = new EntityZombie(this.c.theWorld);
        target.addPotionEffect(new PotionEffect(Potion.blindness.id, 200, 0));
        target.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));

        boolean blindnessVisible = EntityRendererHooks.bypassBlindnessIfNeeded(Potion.blindness, target);
        boolean confusionVisible = EntityRendererHooks.bypassConfusionIfNeeded(Potion.confusion, target);
        boolean expectedVisible = !expectBypass;
        if (blindnessVisible != expectedVisible || confusionVisible != expectedVisible) {
            throw new IllegalStateException("AntiDebuff hook mismatch phase=" + phase
                    + " moduleEnabled=" + ModuleManager.O.o()
                    + " blindnessVisible=" + blindnessVisible
                    + " confusionVisible=" + confusionVisible
                    + " expectedVisible=" + expectedVisible);
        }
        runtimeMilestone("high-risk-functional-probe5-effect-pass:AntiDebuff:" + phase
                + ":blindnessVisible=" + blindnessVisible
                + ":confusionVisible=" + confusionVisible);
    }

    private void restoreHighRiskFunctionalProbe5() {
        if (!this.highRiskFunctionalProbe5Saved) {
            return;
        }
        try {
            AntiDebuff probe = Modules.J(AntiDebuff.class);
            if (probe != null && probe.o() != this.highRiskFunctionalProbe5OriginalEnabled) {
                probe.I(0L, this.highRiskFunctionalProbe5OriginalEnabled);
            }
        }
        catch (Throwable failure) {
            recordFeatureFailure("HighRiskFunctionalProbe5:AntiDebuff", "restore", failure);
        }
    }

    private void pumpHighRiskFunctionalProbe5() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe5")
                || this.highRiskFunctionalProbe5Stage < 0
                || this.highRiskFunctionalProbe5Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.invMovePhysicalProbe")
                && this.invMovePhysicalProbeStage < 6) return;

        try {
            AntiDebuff probe = Modules.J(AntiDebuff.class);
            if (probe == null || this.c.theWorld == null) {
                throw new IllegalStateException("AntiDebuff module/world unavailable");
            }
            if (ModuleManager.O != probe) {
                throw new IllegalStateException("AntiDebuff ModuleManager singleton mismatch");
            }

            switch (this.highRiskFunctionalProbe5Stage) {
                case 0:
                    this.highRiskFunctionalProbe5OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe5Saved = true;
                    if (probe.o()) {
                        probe.I(0L, false);
                    }
                    ++this.highRiskFunctionalProbe5Stage;
                    this.highRiskFunctionalProbe5WaitTicks = 0;
                    runtimeMilestone("high-risk-functional-probe5-state-request:AntiDebuff:enabled=false");
                    return;

                case 1:
                    ++this.highRiskFunctionalProbe5WaitTicks;
                    if (!probe.o() && !probe.l() && !probe.K()) {
                        this.verifyAntiDebuffHookEffect(false, "disabled");
                        probe.I(0L, true);
                        ++this.highRiskFunctionalProbe5Stage;
                        this.highRiskFunctionalProbe5WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe5-state-request:AntiDebuff:enabled=true");
                        return;
                    }
                    if (this.highRiskFunctionalProbe5WaitTicks > 160) {
                        throw new IllegalStateException("AntiDebuff did not settle disabled enabled="
                                + probe.o() + " pendingEnable=" + probe.l()
                                + " pendingDisable=" + probe.K());
                    }
                    return;

                case 2:
                    ++this.highRiskFunctionalProbe5WaitTicks;
                    if (probe.o() && !probe.l() && !probe.K()) {
                        this.verifyAntiDebuffHookEffect(true, "enabled");
                        if (!this.highRiskFunctionalProbe5OriginalEnabled) {
                            probe.I(0L, false);
                        }
                        ++this.highRiskFunctionalProbe5Stage;
                        this.highRiskFunctionalProbe5WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe5-restore-request:AntiDebuff:enabled="
                                + this.highRiskFunctionalProbe5OriginalEnabled);
                        return;
                    }
                    if (this.highRiskFunctionalProbe5WaitTicks > 160) {
                        throw new IllegalStateException("AntiDebuff did not settle enabled enabled="
                                + probe.o() + " pendingEnable=" + probe.l()
                                + " pendingDisable=" + probe.K());
                    }
                    return;

                case 3:
                    ++this.highRiskFunctionalProbe5WaitTicks;
                    if (probe.o() == this.highRiskFunctionalProbe5OriginalEnabled
                            && !probe.l() && !probe.K()) {
                        this.verifyAntiDebuffHookEffect(
                                this.highRiskFunctionalProbe5OriginalEnabled, "restored");
                        runtimeMilestone("high-risk-functional-probe5-restore-pass:AntiDebuff:enabled="
                                + this.highRiskFunctionalProbe5OriginalEnabled);
                        ++this.highRiskFunctionalProbe5Stage;
                        runtimeMilestone("high-risk-functional-probe5-module-pass:AntiDebuff");
                        runtimeMilestone("high-risk-functional-probe5-pass:1");
                        return;
                    }
                    if (this.highRiskFunctionalProbe5WaitTicks > 160) {
                        throw new IllegalStateException("AntiDebuff original state did not restore enabled="
                                + probe.o() + " expected=" + this.highRiskFunctionalProbe5OriginalEnabled);
                    }
                    return;

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.restoreHighRiskFunctionalProbe5();
            this.highRiskFunctionalProbe5Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe5:AntiDebuff", "asm-hook", failure);
            runtimeMilestone("high-risk-functional-probe5-fail:" + failure.getClass().getName());
        }
    }

    private float[] captureNoHurtCamMatrix() {
        int previousMode = GL11.glGetInteger(GL11.GL_MATRIX_MODE);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        try {
            GL11.glLoadIdentity();
            if (!EntityRendererHooks.hurtCameraEffect(this.c, 0.0f)) {
                throw new IllegalStateException("NoHurtCam renderer hook declined local living player");
            }
            FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
            GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, matrix);
            float[] values = new float[16];
            matrix.get(values);
            return values;
        }
        finally {
            GL11.glPopMatrix();
            GL11.glMatrixMode(previousMode);
        }
    }

    private static float matrixIdentityDelta(float[] matrix) {
        if (matrix == null || matrix.length != 16) {
            throw new IllegalArgumentException("Expected 4x4 matrix");
        }
        float delta = 0.0f;
        for (int index = 0; index < 16; ++index) {
            float expected = index % 5 == 0 ? 1.0f : 0.0f;
            delta += Math.abs(matrix[index] - expected);
        }
        return delta;
    }

    private void verifyNoHurtCamHookEffect(boolean expectSuppressed, String phase) {
        if (ModuleManager.g != Modules.J(NoHurtCam.class)) {
            throw new IllegalStateException("NoHurtCam hook singleton differs from registry singleton");
        }
        if (this.c.thePlayer == null) {
            throw new IllegalStateException("NoHurtCam probe has no local player");
        }

        int originalHurtTime = this.c.thePlayer.hurtTime;
        int originalMaxHurtTime = this.c.thePlayer.maxHurtTime;
        float originalAttackedAtYaw = this.c.thePlayer.attackedAtYaw;
        try {
            this.c.thePlayer.hurtTime = 5;
            this.c.thePlayer.maxHurtTime = 10;
            this.c.thePlayer.attackedAtYaw = 37.0f;

            float[] matrix = this.captureNoHurtCamMatrix();
            float delta = matrixIdentityDelta(matrix);
            if (expectSuppressed) {
                if (delta > 0.0005f) {
                    throw new IllegalStateException("NoHurtCam expected identity transform phase=" + phase
                            + " delta=" + delta + " effect=" + NoHurtCam.effect.k());
                }
            }
            else if (delta < 0.01f) {
                throw new IllegalStateException("NoHurtCam disabled hook did not rotate hurt camera phase="
                        + phase + " delta=" + delta);
            }
            runtimeMilestone("high-risk-functional-probe6-effect-pass:NoHurtCam:" + phase
                    + ":identityDelta=" + delta + ":effect=" + NoHurtCam.effect.k());
        }
        finally {
            this.c.thePlayer.hurtTime = originalHurtTime;
            this.c.thePlayer.maxHurtTime = originalMaxHurtTime;
            this.c.thePlayer.attackedAtYaw = originalAttackedAtYaw;
        }
    }

    private void restoreHighRiskFunctionalProbe6() {
        if (!this.highRiskFunctionalProbe6Saved) {
            return;
        }
        try {
            NoHurtCam.effect.d(this.highRiskFunctionalProbe6OriginalEffect);
            NoHurtCam probe = Modules.J(NoHurtCam.class);
            if (probe != null && probe.o() != this.highRiskFunctionalProbe6OriginalEnabled) {
                probe.I(0L, this.highRiskFunctionalProbe6OriginalEnabled);
            }
        }
        catch (Throwable failure) {
            recordFeatureFailure("HighRiskFunctionalProbe6:NoHurtCam", "restore", failure);
        }
    }

    private void pumpHighRiskFunctionalProbe6() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe6")
                || this.highRiskFunctionalProbe6Stage < 0
                || this.highRiskFunctionalProbe6Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe5")
                && this.highRiskFunctionalProbe5Stage < 4) return;

        try {
            NoHurtCam probe = Modules.J(NoHurtCam.class);
            if (probe == null || this.c.thePlayer == null) {
                throw new IllegalStateException("NoHurtCam module/player unavailable");
            }
            if (ModuleManager.g != probe) {
                throw new IllegalStateException("NoHurtCam ModuleManager singleton mismatch");
            }

            switch (this.highRiskFunctionalProbe6Stage) {
                case 0:
                    this.highRiskFunctionalProbe6OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe6OriginalEffect = NoHurtCam.effect.k();
                    this.highRiskFunctionalProbe6Saved = true;
                    NoHurtCam.effect.d(0);
                    if (probe.o()) {
                        probe.I(0L, false);
                    }
                    ++this.highRiskFunctionalProbe6Stage;
                    this.highRiskFunctionalProbe6WaitTicks = 0;
                    runtimeMilestone("high-risk-functional-probe6-state-request:NoHurtCam:enabled=false");
                    return;

                case 1:
                    ++this.highRiskFunctionalProbe6WaitTicks;
                    if (!probe.o() && !probe.l() && !probe.K()) {
                        this.verifyNoHurtCamHookEffect(false, "disabled");
                        probe.I(0L, true);
                        ++this.highRiskFunctionalProbe6Stage;
                        this.highRiskFunctionalProbe6WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe6-state-request:NoHurtCam:enabled=true");
                        return;
                    }
                    if (this.highRiskFunctionalProbe6WaitTicks > 160) {
                        throw new IllegalStateException("NoHurtCam did not settle disabled");
                    }
                    return;

                case 2:
                    ++this.highRiskFunctionalProbe6WaitTicks;
                    if (probe.o() && !probe.l() && !probe.K()) {
                        this.verifyNoHurtCamHookEffect(true, "enabled");
                        NoHurtCam.effect.d(this.highRiskFunctionalProbe6OriginalEffect);
                        if (!this.highRiskFunctionalProbe6OriginalEnabled) {
                            probe.I(0L, false);
                        }
                        ++this.highRiskFunctionalProbe6Stage;
                        this.highRiskFunctionalProbe6WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe6-restore-request:NoHurtCam:enabled="
                                + this.highRiskFunctionalProbe6OriginalEnabled);
                        return;
                    }
                    if (this.highRiskFunctionalProbe6WaitTicks > 160) {
                        throw new IllegalStateException("NoHurtCam did not settle enabled");
                    }
                    return;

                case 3:
                    ++this.highRiskFunctionalProbe6WaitTicks;
                    if (probe.o() == this.highRiskFunctionalProbe6OriginalEnabled
                            && !probe.l() && !probe.K()) {
                        runtimeMilestone("high-risk-functional-probe6-restore-pass:NoHurtCam:enabled="
                                + this.highRiskFunctionalProbe6OriginalEnabled
                                + ":effect=" + NoHurtCam.effect.k());
                        ++this.highRiskFunctionalProbe6Stage;
                        runtimeMilestone("high-risk-functional-probe6-module-pass:NoHurtCam");
                        runtimeMilestone("high-risk-functional-probe6-pass:1");
                        return;
                    }
                    if (this.highRiskFunctionalProbe6WaitTicks > 160) {
                        throw new IllegalStateException("NoHurtCam original state did not restore");
                    }
                    return;

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.restoreHighRiskFunctionalProbe6();
            this.highRiskFunctionalProbe6Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe6:NoHurtCam", "renderer-hook", failure);
            runtimeMilestone("high-risk-functional-probe6-fail:" + failure.getClass().getName());
        }
    }

    private void verifyBarrierVisibleHookEffect(boolean expectOverride, String phase) {
        BarrierVisible probe = Modules.J(BarrierVisible.class);
        if (probe == null || ModuleManager.W != probe) {
            throw new IllegalStateException("BarrierVisible hook singleton differs from registry singleton");
        }

        final int sentinel = 71;
        CallbackInfoReturnable<Integer> callback = new CallbackInfoReturnable<Integer>(sentinel);
        BlockBarrierHooks.getRenderType(callback);

        if (expectOverride) {
            if (!callback.isCancelled()) {
                throw new IllegalStateException("BarrierVisible hook did not cancel phase=" + phase);
            }
            if (callback.getReturnValue() == null || callback.getReturnValue().intValue() != 3) {
                throw new IllegalStateException("BarrierVisible hook return mismatch phase=" + phase
                        + " value=" + callback.getReturnValue());
            }
        }
        else {
            if (callback.isCancelled()) {
                throw new IllegalStateException("BarrierVisible disabled hook unexpectedly cancelled phase=" + phase);
            }
            if (callback.getReturnValue() == null || callback.getReturnValue().intValue() != sentinel) {
                throw new IllegalStateException("BarrierVisible disabled hook changed return phase=" + phase
                        + " value=" + callback.getReturnValue());
            }
        }

        runtimeMilestone("high-risk-functional-probe7-effect-pass:BarrierVisible:" + phase
                + ":cancelled=" + callback.isCancelled()
                + ":returnValue=" + callback.getReturnValue());
    }

    private void restoreHighRiskFunctionalProbe7() {
        if (!this.highRiskFunctionalProbe7Saved) {
            return;
        }
        try {
            BarrierVisible probe = Modules.J(BarrierVisible.class);
            if (probe != null && probe.o() != this.highRiskFunctionalProbe7OriginalEnabled) {
                probe.I(0L, this.highRiskFunctionalProbe7OriginalEnabled);
            }
        }
        catch (Throwable failure) {
            recordFeatureFailure("HighRiskFunctionalProbe7:BarrierVisible", "restore", failure);
        }
    }

    private void pumpHighRiskFunctionalProbe7() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe7")
                || this.highRiskFunctionalProbe7Stage < 0
                || this.highRiskFunctionalProbe7Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe6")
                && this.highRiskFunctionalProbe6Stage < 4) return;

        try {
            BarrierVisible probe = Modules.J(BarrierVisible.class);
            if (probe == null) {
                throw new IllegalStateException("BarrierVisible module unavailable");
            }
            if (ModuleManager.W != probe) {
                throw new IllegalStateException("BarrierVisible ModuleManager singleton mismatch");
            }

            switch (this.highRiskFunctionalProbe7Stage) {
                case 0:
                    this.highRiskFunctionalProbe7OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe7Saved = true;
                    if (probe.o()) {
                        probe.I(0L, false);
                    }
                    ++this.highRiskFunctionalProbe7Stage;
                    this.highRiskFunctionalProbe7WaitTicks = 0;
                    runtimeMilestone("high-risk-functional-probe7-state-request:BarrierVisible:enabled=false");
                    return;

                case 1:
                    ++this.highRiskFunctionalProbe7WaitTicks;
                    if (!probe.o() && !probe.l() && !probe.K()) {
                        this.verifyBarrierVisibleHookEffect(false, "disabled");
                        probe.I(0L, true);
                        ++this.highRiskFunctionalProbe7Stage;
                        this.highRiskFunctionalProbe7WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe7-state-request:BarrierVisible:enabled=true");
                        return;
                    }
                    if (this.highRiskFunctionalProbe7WaitTicks > 160) {
                        throw new IllegalStateException("BarrierVisible did not settle disabled");
                    }
                    return;

                case 2:
                    ++this.highRiskFunctionalProbe7WaitTicks;
                    if (probe.o() && !probe.l() && !probe.K()) {
                        this.verifyBarrierVisibleHookEffect(true, "enabled");
                        if (!this.highRiskFunctionalProbe7OriginalEnabled) {
                            probe.I(0L, false);
                        }
                        ++this.highRiskFunctionalProbe7Stage;
                        this.highRiskFunctionalProbe7WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe7-restore-request:BarrierVisible:enabled="
                                + this.highRiskFunctionalProbe7OriginalEnabled);
                        return;
                    }
                    if (this.highRiskFunctionalProbe7WaitTicks > 160) {
                        throw new IllegalStateException("BarrierVisible did not settle enabled");
                    }
                    return;

                case 3:
                    ++this.highRiskFunctionalProbe7WaitTicks;
                    if (probe.o() == this.highRiskFunctionalProbe7OriginalEnabled
                            && !probe.l() && !probe.K()) {
                        this.verifyBarrierVisibleHookEffect(
                                this.highRiskFunctionalProbe7OriginalEnabled, "restored");
                        runtimeMilestone("high-risk-functional-probe7-restore-pass:BarrierVisible:enabled="
                                + this.highRiskFunctionalProbe7OriginalEnabled);
                        ++this.highRiskFunctionalProbe7Stage;
                        runtimeMilestone("high-risk-functional-probe7-module-pass:BarrierVisible");
                        runtimeMilestone("high-risk-functional-probe7-pass:1");
                        return;
                    }
                    if (this.highRiskFunctionalProbe7WaitTicks > 160) {
                        throw new IllegalStateException("BarrierVisible original state did not restore");
                    }
                    return;

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.restoreHighRiskFunctionalProbe7();
            this.highRiskFunctionalProbe7Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe7:BarrierVisible", "render-type-hook", failure);
            runtimeMilestone("high-risk-functional-probe7-fail:" + failure.getClass().getName());
        }
    }

    private void verifyViewClipHookEffect(boolean expectTakeover, String phase) {
        ViewClip probe = Modules.J(ViewClip.class);
        if (probe == null || ModuleManager.h != probe) {
            throw new IllegalStateException("ViewClip hook singleton differs from registry singleton");
        }
        if (this.c.getRenderViewEntity() == null || this.c.renderGlobal == null) {
            throw new IllegalStateException("ViewClip probe render state unavailable");
        }

        int originalThirdPerson = this.c.gameSettings.thirdPersonView;
        boolean originalDebugCam = this.c.gameSettings.debugCamEnable;
        int previousMode = GL11.glGetInteger(GL11.GL_MATRIX_MODE);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        Boolean result = null;
        try {
            this.c.gameSettings.thirdPersonView = 1;
            this.c.gameSettings.debugCamEnable = false;
            result = EntityRendererHooks.orientCamera(this.c, 0.0f, 4.0f, 4.0f);
        }
        finally {
            GL11.glPopMatrix();
            GL11.glMatrixMode(previousMode);
            this.c.gameSettings.thirdPersonView = originalThirdPerson;
            this.c.gameSettings.debugCamEnable = originalDebugCam;
        }

        if (expectTakeover) {
            if (result == null) {
                throw new IllegalStateException("ViewClip enabled hook did not take over phase=" + phase);
            }
        }
        else if (result != null) {
            throw new IllegalStateException("ViewClip disabled hook unexpectedly took over phase="
                    + phase + " result=" + result);
        }

        runtimeMilestone("high-risk-functional-probe8-effect-pass:ViewClip:" + phase
                + ":takeover=" + (result != null)
                + ":result=" + String.valueOf(result));
    }

    private void restoreHighRiskFunctionalProbe8() {
        if (!this.highRiskFunctionalProbe8Saved) {
            return;
        }
        try {
            ViewClip probe = Modules.J(ViewClip.class);
            if (probe != null && probe.o() != this.highRiskFunctionalProbe8OriginalEnabled) {
                probe.I(0L, this.highRiskFunctionalProbe8OriginalEnabled);
            }
        }
        catch (Throwable failure) {
            recordFeatureFailure("HighRiskFunctionalProbe8:ViewClip", "restore", failure);
        }
    }

    private void pumpHighRiskFunctionalProbe8() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe8")
                || this.highRiskFunctionalProbe8Stage < 0
                || this.highRiskFunctionalProbe8Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe7")
                && this.highRiskFunctionalProbe7Stage < 4) return;

        try {
            ViewClip probe = Modules.J(ViewClip.class);
            if (probe == null || ModuleManager.h != probe) {
                throw new IllegalStateException("ViewClip module/singleton unavailable");
            }

            switch (this.highRiskFunctionalProbe8Stage) {
                case 0:
                    this.highRiskFunctionalProbe8OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe8Saved = true;
                    if (probe.o()) {
                        probe.I(0L, false);
                    }
                    ++this.highRiskFunctionalProbe8Stage;
                    this.highRiskFunctionalProbe8WaitTicks = 0;
                    runtimeMilestone("high-risk-functional-probe8-state-request:ViewClip:enabled=false");
                    return;

                case 1:
                    ++this.highRiskFunctionalProbe8WaitTicks;
                    if (!probe.o() && !probe.l() && !probe.K()) {
                        this.verifyViewClipHookEffect(false, "disabled");
                        probe.I(0L, true);
                        ++this.highRiskFunctionalProbe8Stage;
                        this.highRiskFunctionalProbe8WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe8-state-request:ViewClip:enabled=true");
                        return;
                    }
                    if (this.highRiskFunctionalProbe8WaitTicks > 160) {
                        throw new IllegalStateException("ViewClip did not settle disabled");
                    }
                    return;

                case 2:
                    ++this.highRiskFunctionalProbe8WaitTicks;
                    if (probe.o() && !probe.l() && !probe.K()) {
                        this.verifyViewClipHookEffect(true, "enabled");
                        if (!this.highRiskFunctionalProbe8OriginalEnabled) {
                            probe.I(0L, false);
                        }
                        ++this.highRiskFunctionalProbe8Stage;
                        this.highRiskFunctionalProbe8WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe8-restore-request:ViewClip:enabled="
                                + this.highRiskFunctionalProbe8OriginalEnabled);
                        return;
                    }
                    if (this.highRiskFunctionalProbe8WaitTicks > 160) {
                        throw new IllegalStateException("ViewClip did not settle enabled");
                    }
                    return;

                case 3:
                    ++this.highRiskFunctionalProbe8WaitTicks;
                    if (probe.o() == this.highRiskFunctionalProbe8OriginalEnabled
                            && !probe.l() && !probe.K()) {
                        this.verifyViewClipHookEffect(
                                this.highRiskFunctionalProbe8OriginalEnabled, "restored");
                        runtimeMilestone("high-risk-functional-probe8-restore-pass:ViewClip:enabled="
                                + this.highRiskFunctionalProbe8OriginalEnabled);
                        ++this.highRiskFunctionalProbe8Stage;
                        runtimeMilestone("high-risk-functional-probe8-module-pass:ViewClip");
                        runtimeMilestone("high-risk-functional-probe8-pass:1");
                        return;
                    }
                    if (this.highRiskFunctionalProbe8WaitTicks > 160) {
                        throw new IllegalStateException("ViewClip original state did not restore");
                    }
                    return;

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.restoreHighRiskFunctionalProbe8();
            this.highRiskFunctionalProbe8Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe8:ViewClip", "camera-hook", failure);
            runtimeMilestone("high-risk-functional-probe8-fail:" + failure.getClass().getName());
        }
    }

    private void verifyAnimationsRotationHookEffect(boolean expectCancel, String phase) {
        Animations probe = Modules.J(Animations.class);
        if (probe == null || ModuleManager.d != probe) {
            throw new IllegalStateException("Animations hook singleton differs from registry singleton");
        }

        int previousMode = GL11.glGetInteger(GL11.GL_MATRIX_MODE);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        CallbackInfo callback = new CallbackInfo();
        try {
            ItemRendererHooks.onFunc_178110_a(callback);
        }
        finally {
            GL11.glPopMatrix();
            GL11.glMatrixMode(previousMode);
        }

        if (callback.isCancelled() != expectCancel) {
            throw new IllegalStateException("Animations rotation hook mismatch phase=" + phase
                    + " cancelled=" + callback.isCancelled()
                    + " expected=" + expectCancel
                    + " enabled=" + probe.o()
                    + " noRotations=" + Animations.noRotationsEffect.c());
        }

        runtimeMilestone("high-risk-functional-probe9-effect-pass:Animations:" + phase
                + ":cancelled=" + callback.isCancelled()
                + ":noRotations=" + Animations.noRotationsEffect.c());
    }

    private void restoreHighRiskFunctionalProbe9() {
        if (!this.highRiskFunctionalProbe9Saved) {
            return;
        }
        try {
            Animations.noRotationsEffect.v(this.highRiskFunctionalProbe9OriginalNoRotations, 0L);
            Animations probe = Modules.J(Animations.class);
            if (probe != null && probe.o() != this.highRiskFunctionalProbe9OriginalEnabled) {
                probe.I(0L, this.highRiskFunctionalProbe9OriginalEnabled);
            }
        }
        catch (Throwable failure) {
            recordFeatureFailure("HighRiskFunctionalProbe9:Animations", "restore", failure);
        }
    }

    private void pumpHighRiskFunctionalProbe9() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe9")
                || this.highRiskFunctionalProbe9Stage < 0
                || this.highRiskFunctionalProbe9Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe8")
                && this.highRiskFunctionalProbe8Stage < 4) return;

        try {
            Animations probe = Modules.J(Animations.class);
            if (probe == null || ModuleManager.d != probe) {
                throw new IllegalStateException("Animations module/singleton unavailable");
            }

            switch (this.highRiskFunctionalProbe9Stage) {
                case 0:
                    this.highRiskFunctionalProbe9OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe9OriginalNoRotations = Animations.noRotationsEffect.c();
                    this.highRiskFunctionalProbe9Saved = true;
                    Animations.noRotationsEffect.v(true, 0L);
                    if (probe.o()) {
                        probe.I(0L, false);
                    }
                    ++this.highRiskFunctionalProbe9Stage;
                    this.highRiskFunctionalProbe9WaitTicks = 0;
                    runtimeMilestone("high-risk-functional-probe9-state-request:Animations:enabled=false:noRotations=true");
                    return;

                case 1:
                    ++this.highRiskFunctionalProbe9WaitTicks;
                    if (!probe.o() && !probe.l() && !probe.K()) {
                        this.verifyAnimationsRotationHookEffect(false, "disabled");
                        probe.I(0L, true);
                        ++this.highRiskFunctionalProbe9Stage;
                        this.highRiskFunctionalProbe9WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe9-state-request:Animations:enabled=true:noRotations=true");
                        return;
                    }
                    if (this.highRiskFunctionalProbe9WaitTicks > 160) {
                        throw new IllegalStateException("Animations did not settle disabled");
                    }
                    return;

                case 2:
                    ++this.highRiskFunctionalProbe9WaitTicks;
                    if (probe.o() && !probe.l() && !probe.K()) {
                        this.verifyAnimationsRotationHookEffect(true, "enabled");
                        Animations.noRotationsEffect.v(
                                this.highRiskFunctionalProbe9OriginalNoRotations, 0L);
                        if (!this.highRiskFunctionalProbe9OriginalEnabled) {
                            probe.I(0L, false);
                        }
                        ++this.highRiskFunctionalProbe9Stage;
                        this.highRiskFunctionalProbe9WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe9-restore-request:Animations:enabled="
                                + this.highRiskFunctionalProbe9OriginalEnabled
                                + ":noRotations=" + this.highRiskFunctionalProbe9OriginalNoRotations);
                        return;
                    }
                    if (this.highRiskFunctionalProbe9WaitTicks > 160) {
                        throw new IllegalStateException("Animations did not settle enabled");
                    }
                    return;

                case 3:
                    ++this.highRiskFunctionalProbe9WaitTicks;
                    if (probe.o() == this.highRiskFunctionalProbe9OriginalEnabled
                            && !probe.l() && !probe.K()) {
                        this.verifyAnimationsRotationHookEffect(
                                this.highRiskFunctionalProbe9OriginalEnabled
                                        && this.highRiskFunctionalProbe9OriginalNoRotations,
                                "restored");
                        runtimeMilestone("high-risk-functional-probe9-restore-pass:Animations:enabled="
                                + this.highRiskFunctionalProbe9OriginalEnabled
                                + ":noRotations=" + Animations.noRotationsEffect.c());
                        ++this.highRiskFunctionalProbe9Stage;
                        runtimeMilestone("high-risk-functional-probe9-module-pass:Animations");
                        runtimeMilestone("high-risk-functional-probe9-pass:1");
                        return;
                    }
                    if (this.highRiskFunctionalProbe9WaitTicks > 160) {
                        throw new IllegalStateException("Animations original state did not restore");
                    }
                    return;

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.restoreHighRiskFunctionalProbe9();
            this.highRiskFunctionalProbe9Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe9:Animations", "item-renderer-hook", failure);
            runtimeMilestone("high-risk-functional-probe9-fail:" + failure.getClass().getName());
        }
    }

    private static boolean floatNear(float actual, float expected, float tolerance) {
        return Math.abs(actual - expected) <= tolerance;
    }

    private void setTimerProbeEnabled(Timer probe, boolean enabled) {
        probe.I(0L, enabled);
        // Timer is deliberately always subscribed and excluded from generic lifecycle processing.
        // Its transition flags are not semantically used, so diagnostics normalize them explicitly.
        probe.n(false);
        probe.E(false);
    }

    private void restoreHighRiskFunctionalProbe10() {
        if (!this.highRiskFunctionalProbe10Saved) {
            return;
        }
        try {
            Timer.speed.o((byte)0, 0L, this.highRiskFunctionalProbe10OriginalSetting);
            Timer probe = Modules.J(Timer.class);
            if (probe != null) {
                this.setTimerProbeEnabled(probe, this.highRiskFunctionalProbe10OriginalEnabled);
            }
            MinecraftAccessor.o(this.c).timerSpeed = this.highRiskFunctionalProbe10OriginalTimerSpeed;
        }
        catch (Throwable failure) {
            recordFeatureFailure("HighRiskFunctionalProbe10:Timer", "restore", failure);
        }
    }

    private void pumpHighRiskFunctionalProbe10() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe10")
                || this.highRiskFunctionalProbe10Stage < 0
                || this.highRiskFunctionalProbe10Stage >= 5) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe9")
                && this.highRiskFunctionalProbe9Stage < 4) return;

        try {
            Timer probe = Modules.J(Timer.class);
            if (probe == null || Timer.speed == null) {
                throw new IllegalStateException("Timer module/Speed setting unavailable");
            }

            float liveTimerSpeed = MinecraftAccessor.o(this.c).timerSpeed;
            switch (this.highRiskFunctionalProbe10Stage) {
                case 0:
                    this.highRiskFunctionalProbe10OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe10OriginalSetting = Timer.speed.L();
                    this.highRiskFunctionalProbe10OriginalTimerSpeed = liveTimerSpeed;
                    this.highRiskFunctionalProbe10Saved = true;

                    this.setTimerProbeEnabled(probe, false);
                    ++this.highRiskFunctionalProbe10Stage;
                    this.highRiskFunctionalProbe10WaitTicks = 0;
                    runtimeMilestone("high-risk-functional-probe10-state-request:Timer:enabled=false");
                    return;

                case 1:
                    ++this.highRiskFunctionalProbe10WaitTicks;
                    if (!probe.o() && this.highRiskFunctionalProbe10WaitTicks >= 4) {
                        this.highRiskFunctionalProbe10DisabledBaseline =
                                MinecraftAccessor.o(this.c).timerSpeed;
                        Timer.speed.o((byte)0, 0L, 1.37f);
                        this.setTimerProbeEnabled(probe, true);
                        ++this.highRiskFunctionalProbe10Stage;
                        this.highRiskFunctionalProbe10WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe10-baseline-pass:Timer:timerSpeed="
                                + this.highRiskFunctionalProbe10DisabledBaseline);
                        runtimeMilestone("high-risk-functional-probe10-state-request:Timer:enabled=true:speed=1.37");
                        return;
                    }
                    if (this.highRiskFunctionalProbe10WaitTicks > 120) {
                        throw new IllegalStateException("Timer did not settle disabled for baseline");
                    }
                    return;

                case 2:
                    ++this.highRiskFunctionalProbe10WaitTicks;
                    liveTimerSpeed = MinecraftAccessor.o(this.c).timerSpeed;
                    if (probe.o() && floatNear(liveTimerSpeed, 1.37f, 0.001f)) {
                        runtimeMilestone("high-risk-functional-probe10-effect-pass:Timer:enabled:timerSpeed="
                                + liveTimerSpeed);
                        this.setTimerProbeEnabled(probe, false);
                        ++this.highRiskFunctionalProbe10Stage;
                        this.highRiskFunctionalProbe10WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe10-state-request:Timer:enabled=false:restore-baseline");
                        return;
                    }
                    if (this.highRiskFunctionalProbe10WaitTicks > 160) {
                        throw new IllegalStateException("Timer Render2D did not apply speed; actual="
                                + liveTimerSpeed);
                    }
                    return;

                case 3:
                    ++this.highRiskFunctionalProbe10WaitTicks;
                    liveTimerSpeed = MinecraftAccessor.o(this.c).timerSpeed;
                    if (!probe.o()
                            && floatNear(liveTimerSpeed,
                                    this.highRiskFunctionalProbe10DisabledBaseline, 0.001f)) {
                        runtimeMilestone("high-risk-functional-probe10-effect-pass:Timer:disabled:timerSpeed="
                                + liveTimerSpeed);
                        Timer.speed.o((byte)0, 0L, this.highRiskFunctionalProbe10OriginalSetting);
                        this.setTimerProbeEnabled(probe, this.highRiskFunctionalProbe10OriginalEnabled);
                        ++this.highRiskFunctionalProbe10Stage;
                        this.highRiskFunctionalProbe10WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe10-restore-request:Timer:enabled="
                                + this.highRiskFunctionalProbe10OriginalEnabled
                                + ":speed=" + this.highRiskFunctionalProbe10OriginalSetting);
                        return;
                    }
                    if (this.highRiskFunctionalProbe10WaitTicks > 160) {
                        throw new IllegalStateException("Timer Render2D did not restore disabled baseline; actual="
                                + liveTimerSpeed + " expected="
                                + this.highRiskFunctionalProbe10DisabledBaseline);
                    }
                    return;

                case 4:
                    ++this.highRiskFunctionalProbe10WaitTicks;
                    liveTimerSpeed = MinecraftAccessor.o(this.c).timerSpeed;
                    if (probe.o() == this.highRiskFunctionalProbe10OriginalEnabled
                            && floatNear(Timer.speed.L(),
                                    this.highRiskFunctionalProbe10OriginalSetting, 0.001f)
                            && floatNear(liveTimerSpeed,
                                    this.highRiskFunctionalProbe10OriginalTimerSpeed, 0.001f)) {
                        runtimeMilestone("high-risk-functional-probe10-restore-pass:Timer:enabled="
                                + this.highRiskFunctionalProbe10OriginalEnabled
                                + ":setting=" + Timer.speed.L()
                                + ":timerSpeed=" + liveTimerSpeed);
                        ++this.highRiskFunctionalProbe10Stage;
                        runtimeMilestone("high-risk-functional-probe10-module-pass:Timer");
                        runtimeMilestone("high-risk-functional-probe10-pass:1");
                        return;
                    }
                    if (this.highRiskFunctionalProbe10WaitTicks > 160) {
                        throw new IllegalStateException("Timer original state did not restore; enabled="
                                + probe.o() + " setting=" + Timer.speed.L()
                                + " timerSpeed=" + liveTimerSpeed);
                    }
                    return;

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.restoreHighRiskFunctionalProbe10();
            this.highRiskFunctionalProbe10Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe10:Timer", "render2d-timer-speed", failure);
            runtimeMilestone("high-risk-functional-probe10-fail:" + failure.getClass().getName());
        }
    }

    private void restoreHighRiskFunctionalProbe11() {
        if (!this.highRiskFunctionalProbe11Saved) {
            return;
        }
        try {
            Chams probe = Modules.J(Chams.class);
            if (probe != null && probe.o() != this.highRiskFunctionalProbe11OriginalEnabled) {
                probe.I(0L, this.highRiskFunctionalProbe11OriginalEnabled);
            }
            GL11.glPolygonOffset(
                    this.highRiskFunctionalProbe11OriginalPolygonOffsetFactor,
                    this.highRiskFunctionalProbe11OriginalPolygonOffsetUnits);
            if (this.highRiskFunctionalProbe11OriginalPolygonOffsetEnabled) {
                GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            }
            else {
                GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
            }
        }
        catch (Throwable failure) {
            recordFeatureFailure("HighRiskFunctionalProbe11:Chams", "restore", failure);
        }
    }

    private void verifyChamsEventEffect(boolean enabledPhase, String phase) {
        if (this.c.thePlayer == null) {
            throw new IllegalStateException("Chams probe requires local player");
        }

        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPolygonOffset(0.0f, 0.0f);

        w.e(new PreRenderEvent(this.c.thePlayer), 0L);
        boolean afterPre = GL11.glIsEnabled(GL11.GL_POLYGON_OFFSET_FILL);
        if (afterPre != enabledPhase) {
            throw new IllegalStateException("Chams PreRender polygon-offset mismatch phase=" + phase
                    + " enabled=" + enabledPhase + " actual=" + afterPre);
        }

        w.e(new PostRenderEvent(this.c.thePlayer), 0L);
        boolean afterPost = GL11.glIsEnabled(GL11.GL_POLYGON_OFFSET_FILL);
        if (afterPost) {
            throw new IllegalStateException("Chams PostRender did not disable polygon offset phase=" + phase);
        }

        runtimeMilestone("high-risk-functional-probe11-effect-pass:Chams:" + phase
                + ":pre=" + afterPre + ":post=" + afterPost);
    }

    private void pumpHighRiskFunctionalProbe11() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe11")
                || this.highRiskFunctionalProbe11Stage < 0
                || this.highRiskFunctionalProbe11Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe10")
                && this.highRiskFunctionalProbe10Stage < 5) return;

        try {
            Chams probe = Modules.J(Chams.class);
            if (probe == null
                    || ModuleManager.byClass(Chams.class) != probe
                    || ModuleManager.byName("Chams") != probe) {
                throw new IllegalStateException("Chams live registry authority unavailable");
            }

            switch (this.highRiskFunctionalProbe11Stage) {
                case 0:
                    this.highRiskFunctionalProbe11OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe11OriginalPolygonOffsetEnabled =
                            GL11.glIsEnabled(GL11.GL_POLYGON_OFFSET_FILL);
                    this.highRiskFunctionalProbe11OriginalPolygonOffsetFactor =
                            GL11.glGetFloat(GL11.GL_POLYGON_OFFSET_FACTOR);
                    this.highRiskFunctionalProbe11OriginalPolygonOffsetUnits =
                            GL11.glGetFloat(GL11.GL_POLYGON_OFFSET_UNITS);
                    this.highRiskFunctionalProbe11Saved = true;

                    if (probe.o()) {
                        probe.I(0L, false);
                    }
                    ++this.highRiskFunctionalProbe11Stage;
                    this.highRiskFunctionalProbe11WaitTicks = 0;
                    runtimeMilestone("high-risk-functional-probe11-state-request:Chams:enabled=false");
                    return;

                case 1:
                    ++this.highRiskFunctionalProbe11WaitTicks;
                    if (!probe.o() && !probe.l() && !probe.K()
                            && !probe.P() && !w.isOwnerActive(probe)) {
                        this.verifyChamsEventEffect(false, "disabled");
                        probe.I(0L, true);
                        ++this.highRiskFunctionalProbe11Stage;
                        this.highRiskFunctionalProbe11WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe11-state-request:Chams:enabled=true");
                        return;
                    }
                    if (this.highRiskFunctionalProbe11WaitTicks > 160) {
                        throw new IllegalStateException("Chams did not settle disabled");
                    }
                    return;

                case 2:
                    ++this.highRiskFunctionalProbe11WaitTicks;
                    if (probe.o() && !probe.l() && !probe.K()
                            && probe.P() && w.isOwnerActive(probe)) {
                        this.verifyChamsEventEffect(true, "enabled");
                        if (!this.highRiskFunctionalProbe11OriginalEnabled) {
                            probe.I(0L, false);
                        }
                        ++this.highRiskFunctionalProbe11Stage;
                        this.highRiskFunctionalProbe11WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe11-restore-request:Chams:enabled="
                                + this.highRiskFunctionalProbe11OriginalEnabled);
                        return;
                    }
                    if (this.highRiskFunctionalProbe11WaitTicks > 160) {
                        throw new IllegalStateException("Chams did not settle enabled");
                    }
                    return;

                case 3:
                    ++this.highRiskFunctionalProbe11WaitTicks;
                    boolean stableRestored = this.highRiskFunctionalProbe11OriginalEnabled
                            ? probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe)
                            : !probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe);
                    if (stableRestored) {
                        this.verifyChamsEventEffect(
                                this.highRiskFunctionalProbe11OriginalEnabled, "restored");
                        GL11.glPolygonOffset(
                                this.highRiskFunctionalProbe11OriginalPolygonOffsetFactor,
                                this.highRiskFunctionalProbe11OriginalPolygonOffsetUnits);
                        if (this.highRiskFunctionalProbe11OriginalPolygonOffsetEnabled) {
                            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
                        }
                        else {
                            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
                        }
                        ++this.highRiskFunctionalProbe11Stage;
                        runtimeMilestone("high-risk-functional-probe11-restore-pass:Chams:enabled="
                                + this.highRiskFunctionalProbe11OriginalEnabled);
                        runtimeMilestone("high-risk-functional-probe11-module-pass:Chams");
                        runtimeMilestone("high-risk-functional-probe11-pass:1");
                        return;
                    }
                    if (this.highRiskFunctionalProbe11WaitTicks > 160) {
                        throw new IllegalStateException("Chams original state did not restore");
                    }
                    return;

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.restoreHighRiskFunctionalProbe11();
            this.highRiskFunctionalProbe11Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe11:Chams", "pre-post-render-event", failure);
            runtimeMilestone("high-risk-functional-probe11-fail:" + failure.getClass().getName());
        }
    }

    private Field freelookProbeField(String name) throws Exception {
        Field field = Freelook.class.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    private int readFreelookSavedView(Freelook probe) throws Exception {
        return this.freelookProbeField("T").getInt(probe);
    }

    private float readFreelookSavedYaw(Freelook probe) throws Exception {
        return this.freelookProbeField("p").getFloat(probe);
    }

    private float readFreelookSavedPitch(Freelook probe) throws Exception {
        return this.freelookProbeField("s").getFloat(probe);
    }

    private void writeFreelookPrivateSnapshot(Freelook probe, int view, float yaw, float pitch)
            throws Exception {
        this.freelookProbeField("T").setInt(probe, view);
        this.freelookProbeField("p").setFloat(probe, yaw);
        this.freelookProbeField("s").setFloat(probe, pitch);
    }

    private void restoreHighRiskFunctionalProbe12Exact(Freelook probe) {
        if (!this.highRiskFunctionalProbe12Saved) {
            return;
        }
        try {
            if (probe != null && probe.o() != this.highRiskFunctionalProbe12OriginalEnabled) {
                probe.I(0L, this.highRiskFunctionalProbe12OriginalEnabled);
            }
            if (this.c.thePlayer != null) {
                this.c.thePlayer.rotationYaw = this.highRiskFunctionalProbe12OriginalYaw;
                this.c.thePlayer.rotationPitch = this.highRiskFunctionalProbe12OriginalPitch;
            }
            this.c.gameSettings.thirdPersonView = this.highRiskFunctionalProbe12OriginalThirdPersonView;
            Freelook.L(this.highRiskFunctionalProbe12OriginalActive);
            Freelook.B(this.highRiskFunctionalProbe12OriginalCameraYaw);
            Freelook.v(this.highRiskFunctionalProbe12OriginalCameraPitch);
            if (probe != null) {
                this.writeFreelookPrivateSnapshot(
                        probe,
                        this.highRiskFunctionalProbe12OriginalSavedView,
                        this.highRiskFunctionalProbe12OriginalSavedYaw,
                        this.highRiskFunctionalProbe12OriginalSavedPitch);
            }
        }
        catch (Throwable failure) {
            recordFeatureFailure("HighRiskFunctionalProbe12:Freelook", "restore", failure);
        }
    }

    private void pumpHighRiskFunctionalProbe12() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe12")
                || this.highRiskFunctionalProbe12Stage < 0
                || this.highRiskFunctionalProbe12Stage >= 5) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe11")
                && this.highRiskFunctionalProbe11Stage < 4) return;

        final float controlledYaw = 37.25f;
        final float controlledPitch = -18.5f;
        try {
            Freelook probe = Modules.J(Freelook.class);
            if (probe == null
                    || ModuleManager.byClass(Freelook.class) != probe
                    || ModuleManager.byName("Freelook") != probe
                    || this.c.thePlayer == null) {
                throw new IllegalStateException("Freelook live registry/player unavailable");
            }

            switch (this.highRiskFunctionalProbe12Stage) {
                case 0:
                    this.highRiskFunctionalProbe12OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe12OriginalYaw = this.c.thePlayer.rotationYaw;
                    this.highRiskFunctionalProbe12OriginalPitch = this.c.thePlayer.rotationPitch;
                    this.highRiskFunctionalProbe12OriginalThirdPersonView =
                            this.c.gameSettings.thirdPersonView;
                    this.highRiskFunctionalProbe12OriginalActive = Freelook.c();
                    this.highRiskFunctionalProbe12OriginalCameraYaw = Freelook.v();
                    this.highRiskFunctionalProbe12OriginalCameraPitch = Freelook.M();
                    this.highRiskFunctionalProbe12OriginalSavedView =
                            this.readFreelookSavedView(probe);
                    this.highRiskFunctionalProbe12OriginalSavedYaw =
                            this.readFreelookSavedYaw(probe);
                    this.highRiskFunctionalProbe12OriginalSavedPitch =
                            this.readFreelookSavedPitch(probe);
                    this.highRiskFunctionalProbe12Saved = true;

                    if (probe.o()) {
                        probe.I(0L, false);
                    }
                    ++this.highRiskFunctionalProbe12Stage;
                    this.highRiskFunctionalProbe12WaitTicks = 0;
                    runtimeMilestone("high-risk-functional-probe12-state-request:Freelook:enabled=false");
                    return;

                case 1:
                    ++this.highRiskFunctionalProbe12WaitTicks;
                    if (!probe.o() && !probe.l() && !probe.K()) {
                        this.c.thePlayer.rotationYaw = controlledYaw;
                        this.c.thePlayer.rotationPitch = controlledPitch;
                        this.c.gameSettings.thirdPersonView = 0;
                        probe.I(0L, true);
                        ++this.highRiskFunctionalProbe12Stage;
                        this.highRiskFunctionalProbe12WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe12-state-request:Freelook:enabled=true"
                                + ":yaw=" + controlledYaw + ":pitch=" + controlledPitch + ":view=0");
                        return;
                    }
                    if (this.highRiskFunctionalProbe12WaitTicks > 160) {
                        throw new IllegalStateException("Freelook did not settle disabled");
                    }
                    return;

                case 2:
                    ++this.highRiskFunctionalProbe12WaitTicks;
                    if (probe.o() && !probe.l() && !probe.K()) {
                        if (this.c.gameSettings.thirdPersonView != 1
                                || !Freelook.c()
                                || !floatNear(Freelook.v(), controlledYaw, 0.001f)
                                || !floatNear(Freelook.M(), controlledPitch, 0.001f)
                                || !floatNear(this.readFreelookSavedYaw(probe), controlledYaw, 0.001f)
                                || !floatNear(this.readFreelookSavedPitch(probe), controlledPitch, 0.001f)
                                || this.readFreelookSavedView(probe) != 0) {
                            throw new IllegalStateException("Freelook enable effect mismatch"
                                    + " view=" + this.c.gameSettings.thirdPersonView
                                    + " active=" + Freelook.c()
                                    + " cameraYaw=" + Freelook.v()
                                    + " cameraPitch=" + Freelook.M()
                                    + " savedView=" + this.readFreelookSavedView(probe)
                                    + " savedYaw=" + this.readFreelookSavedYaw(probe)
                                    + " savedPitch=" + this.readFreelookSavedPitch(probe));
                        }
                        runtimeMilestone("high-risk-functional-probe12-effect-pass:Freelook:enabled"
                                + ":view=1:active=true:yaw=" + Freelook.v()
                                + ":pitch=" + Freelook.M());

                        // Prove disable restores the values captured by Freelook.i(...), not merely
                        // whatever values happen to be live when the module is switched off.
                        this.c.thePlayer.rotationYaw = 91.0f;
                        this.c.thePlayer.rotationPitch = 22.0f;
                        this.c.gameSettings.thirdPersonView = 2;
                        probe.I(0L, false);
                        ++this.highRiskFunctionalProbe12Stage;
                        this.highRiskFunctionalProbe12WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe12-state-request:Freelook:enabled=false"
                                + ":mutated-before-disable=true");
                        return;
                    }
                    if (this.highRiskFunctionalProbe12WaitTicks > 160) {
                        throw new IllegalStateException("Freelook did not settle enabled");
                    }
                    return;

                case 3:
                    ++this.highRiskFunctionalProbe12WaitTicks;
                    if (!probe.o() && !probe.l() && !probe.K()) {
                        if (Freelook.c()
                                || this.c.gameSettings.thirdPersonView != 0
                                || !floatNear(this.c.thePlayer.rotationYaw, controlledYaw, 0.001f)
                                || !floatNear(this.c.thePlayer.rotationPitch, controlledPitch, 0.001f)) {
                            throw new IllegalStateException("Freelook disable restore mismatch"
                                    + " active=" + Freelook.c()
                                    + " view=" + this.c.gameSettings.thirdPersonView
                                    + " yaw=" + this.c.thePlayer.rotationYaw
                                    + " pitch=" + this.c.thePlayer.rotationPitch);
                        }
                        runtimeMilestone("high-risk-functional-probe12-effect-pass:Freelook:disabled"
                                + ":view=0:active=false:yaw=" + this.c.thePlayer.rotationYaw
                                + ":pitch=" + this.c.thePlayer.rotationPitch);

                        if (this.highRiskFunctionalProbe12OriginalEnabled) {
                            probe.I(0L, true);
                        }
                        ++this.highRiskFunctionalProbe12Stage;
                        this.highRiskFunctionalProbe12WaitTicks = 0;
                        runtimeMilestone("high-risk-functional-probe12-restore-request:Freelook:enabled="
                                + this.highRiskFunctionalProbe12OriginalEnabled);
                        return;
                    }
                    if (this.highRiskFunctionalProbe12WaitTicks > 160) {
                        throw new IllegalStateException("Freelook did not settle disabled after effect");
                    }
                    return;

                case 4:
                    ++this.highRiskFunctionalProbe12WaitTicks;
                    boolean stableOriginal = this.highRiskFunctionalProbe12OriginalEnabled
                            ? probe.o() && !probe.l() && !probe.K()
                            : !probe.o() && !probe.l() && !probe.K();
                    if (stableOriginal) {
                        this.c.thePlayer.rotationYaw = this.highRiskFunctionalProbe12OriginalYaw;
                        this.c.thePlayer.rotationPitch = this.highRiskFunctionalProbe12OriginalPitch;
                        this.c.gameSettings.thirdPersonView =
                                this.highRiskFunctionalProbe12OriginalThirdPersonView;
                        Freelook.L(this.highRiskFunctionalProbe12OriginalActive);
                        Freelook.B(this.highRiskFunctionalProbe12OriginalCameraYaw);
                        Freelook.v(this.highRiskFunctionalProbe12OriginalCameraPitch);
                        this.writeFreelookPrivateSnapshot(
                                probe,
                                this.highRiskFunctionalProbe12OriginalSavedView,
                                this.highRiskFunctionalProbe12OriginalSavedYaw,
                                this.highRiskFunctionalProbe12OriginalSavedPitch);

                        if (probe.o() != this.highRiskFunctionalProbe12OriginalEnabled
                                || this.c.gameSettings.thirdPersonView
                                        != this.highRiskFunctionalProbe12OriginalThirdPersonView
                                || !floatNear(this.c.thePlayer.rotationYaw,
                                        this.highRiskFunctionalProbe12OriginalYaw, 0.001f)
                                || !floatNear(this.c.thePlayer.rotationPitch,
                                        this.highRiskFunctionalProbe12OriginalPitch, 0.001f)
                                || Freelook.c() != this.highRiskFunctionalProbe12OriginalActive
                                || !floatNear(Freelook.v(),
                                        this.highRiskFunctionalProbe12OriginalCameraYaw, 0.001f)
                                || !floatNear(Freelook.M(),
                                        this.highRiskFunctionalProbe12OriginalCameraPitch, 0.001f)
                                || this.readFreelookSavedView(probe)
                                        != this.highRiskFunctionalProbe12OriginalSavedView
                                || !floatNear(this.readFreelookSavedYaw(probe),
                                        this.highRiskFunctionalProbe12OriginalSavedYaw, 0.001f)
                                || !floatNear(this.readFreelookSavedPitch(probe),
                                        this.highRiskFunctionalProbe12OriginalSavedPitch, 0.001f)) {
                            throw new IllegalStateException("Freelook exact original state did not restore");
                        }

                        ++this.highRiskFunctionalProbe12Stage;
                        runtimeMilestone("high-risk-functional-probe12-restore-pass:Freelook:enabled="
                                + this.highRiskFunctionalProbe12OriginalEnabled);
                        runtimeMilestone("high-risk-functional-probe12-module-pass:Freelook");
                        runtimeMilestone("high-risk-functional-probe12-pass:1");
                        return;
                    }
                    if (this.highRiskFunctionalProbe12WaitTicks > 160) {
                        throw new IllegalStateException("Freelook original enabled state did not settle");
                    }
                    return;

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            Freelook probe = Modules.J(Freelook.class);
            this.restoreHighRiskFunctionalProbe12Exact(probe);
            this.highRiskFunctionalProbe12Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe12:Freelook", "camera-lifecycle", failure);
            runtimeMilestone("high-risk-functional-probe12-fail:" + failure.getClass().getName());
        }
    }

    private Field moduleEnabledProbeField() throws Exception {
        Field field = Module.class.getDeclaredField("V");
        field.setAccessible(true);
        return field;
    }

    private void setModuleEnabledRawForProbe(Module module, boolean enabled) throws Exception {
        this.moduleEnabledProbeField().setBoolean(module, enabled);
    }

    private void restoreHighRiskFunctionalProbe13Raw(
            CaveXray probe, ViewClip viewClip) {
        if (!this.highRiskFunctionalProbe13Saved) {
            return;
        }
        try {
            if (probe != null) {
                this.setModuleEnabledRawForProbe(
                        probe, this.highRiskFunctionalProbe13OriginalEnabled);
            }
            if (viewClip != null) {
                this.setModuleEnabledRawForProbe(
                        viewClip, this.highRiskFunctionalProbe13OriginalViewClipEnabled);
            }
            CaveXray.reloadRenderer.v(
                    this.highRiskFunctionalProbe13OriginalReloadRenderer, 0L);
        }
        catch (Throwable failure) {
            recordFeatureFailure("HighRiskFunctionalProbe13:CaveXray", "raw-restore", failure);
        }
    }

    private void verifyCaveXrayVisGraphEffect(boolean expectCancel, String phase) {
        BlockPos pos = new BlockPos(3, 7, 11);
        BitSet opaque = new BitSet(4096);
        CallbackInfo callback = new CallbackInfo();
        VisGraphHooks.func_178606_a(pos, opaque, callback);

        int index = (pos.getX() & 0xF)
                | ((pos.getY() & 0xF) << 8)
                | ((pos.getZ() & 0xF) << 4);
        boolean bitSet = opaque.get(index);
        if (callback.isCancelled() != expectCancel || bitSet != expectCancel) {
            throw new IllegalStateException("CaveXray VisGraph hook mismatch phase=" + phase
                    + " cancelled=" + callback.isCancelled()
                    + " bitSet=" + bitSet
                    + " expected=" + expectCancel);
        }

        runtimeMilestone("high-risk-functional-probe13-effect-pass:CaveXray:" + phase
                + ":cancelled=" + callback.isCancelled()
                + ":opaqueBit=" + bitSet);
    }

    private void pumpHighRiskFunctionalProbe13() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe13")
                || this.highRiskFunctionalProbe13Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe12")
                && this.highRiskFunctionalProbe12Stage < 5) return;

        CaveXray probe = Modules.J(CaveXray.class);
        ViewClip viewClip = Modules.J(ViewClip.class);
        try {
            if (probe == null
                    || ModuleManager.m != probe
                    || ModuleManager.byClass(CaveXray.class) != probe
                    || ModuleManager.byName("CaveXray") != probe
                    || viewClip == null
                    || ModuleManager.h != viewClip) {
                throw new IllegalStateException(
                        "CaveXray/ViewClip live hook authority unavailable");
            }
            if (probe.l() || probe.K() || viewClip.l() || viewClip.K()) {
                return;
            }

            this.highRiskFunctionalProbe13OriginalEnabled = probe.o();
            this.highRiskFunctionalProbe13OriginalViewClipEnabled = viewClip.o();
            this.highRiskFunctionalProbe13OriginalReloadRenderer =
                    CaveXray.reloadRenderer.c();
            this.highRiskFunctionalProbe13Saved = true;

            // Probe only the VisGraph ASM predicate. CaveXray's normal lifecycle is
            // independently covered by PromotedRegistryProbe after the world renderer
            // has stabilized. Raw same-tick state avoids the module's intentionally huge
            // 900-block render invalidation from dominating the early llvmpipe test.
            this.setModuleEnabledRawForProbe(probe, false);
            this.setModuleEnabledRawForProbe(viewClip, false);
            this.verifyCaveXrayVisGraphEffect(false, "disabled");

            this.setModuleEnabledRawForProbe(probe, true);
            this.verifyCaveXrayVisGraphEffect(true, "enabled");

            this.setModuleEnabledRawForProbe(
                    probe, this.highRiskFunctionalProbe13OriginalEnabled);
            this.setModuleEnabledRawForProbe(
                    viewClip, this.highRiskFunctionalProbe13OriginalViewClipEnabled);
            CaveXray.reloadRenderer.v(
                    this.highRiskFunctionalProbe13OriginalReloadRenderer, 0L);

            boolean expectedRestoredHook =
                    this.highRiskFunctionalProbe13OriginalEnabled
                    || this.highRiskFunctionalProbe13OriginalViewClipEnabled;
            this.verifyCaveXrayVisGraphEffect(expectedRestoredHook, "restored");

            if (probe.o() != this.highRiskFunctionalProbe13OriginalEnabled
                    || viewClip.o() != this.highRiskFunctionalProbe13OriginalViewClipEnabled
                    || CaveXray.reloadRenderer.c()
                            != this.highRiskFunctionalProbe13OriginalReloadRenderer
                    || probe.l() || probe.K() || viewClip.l() || viewClip.K()) {
                throw new IllegalStateException(
                        "CaveXray/ViewClip raw hook state did not restore exactly");
            }

            this.highRiskFunctionalProbe13Stage = 1;
            runtimeMilestone("high-risk-functional-probe13-restore-pass:CaveXray="
                    + this.highRiskFunctionalProbe13OriginalEnabled
                    + ":ViewClip="
                    + this.highRiskFunctionalProbe13OriginalViewClipEnabled
                    + ":reloadRenderer="
                    + this.highRiskFunctionalProbe13OriginalReloadRenderer);
            runtimeMilestone("high-risk-functional-probe13-module-pass:CaveXray");
            runtimeMilestone("high-risk-functional-probe13-pass:1");
        }
        catch (Throwable failure) {
            this.restoreHighRiskFunctionalProbe13Raw(probe, viewClip);
            this.highRiskFunctionalProbe13Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe13:CaveXray", "visgraph-hook-raw-state", failure);
            runtimeMilestone("high-risk-functional-probe13-fail:"
                    + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe14() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe14")
                || this.highRiskFunctionalProbe14Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe13")
                && this.highRiskFunctionalProbe13Stage < 1) return;

        ItemScale probe = Modules.J(ItemScale.class);
        boolean saved = false;
        boolean originalEnabled = false;
        boolean originalNbtOnly = false;
        boolean originalMegawalls = false;
        boolean originalWeapons = false;
        boolean originalBedwars = false;
        boolean originalGapples = false;
        boolean originalAll = false;
        try {
            if (probe == null
                    || ModuleManager.v != probe
                    || ModuleManager.byClass(ItemScale.class) != probe
                    || ModuleManager.byName("ItemScale") != probe
                    || ItemScale.nbtOnly == null
                    || ItemScale.megawallsItems == null
                    || ItemScale.renderSwordsAndBows == null
                    || ItemScale.bedwarsResources == null
                    || ItemScale.renderGoldenApples == null
                    || ItemScale.renderALL == null
                    || ItemScale.scale == null) {
                throw new IllegalStateException("ItemScale live selection authority unavailable");
            }
            if (probe.l() || probe.K()) {
                return;
            }

            originalEnabled = probe.o();
            originalNbtOnly = ItemScale.nbtOnly.c();
            originalMegawalls = ItemScale.megawallsItems.c();
            originalWeapons = ItemScale.renderSwordsAndBows.c();
            originalBedwars = ItemScale.bedwarsResources.c();
            originalGapples = ItemScale.renderGoldenApples.c();
            originalAll = ItemScale.renderALL.c();
            saved = true;

            ItemStack stone = new ItemStack(Blocks.stone);
            ItemStack goldenApple = new ItemStack(Items.golden_apple);

            this.setModuleEnabledRawForProbe(probe, false);
            ItemScale.nbtOnly.v(false, 0L);
            ItemScale.megawallsItems.v(false, 0L);
            ItemScale.renderSwordsAndBows.v(false, 0L);
            ItemScale.bedwarsResources.v(false, 0L);
            ItemScale.renderGoldenApples.v(false, 0L);
            ItemScale.renderALL.v(false, 0L);
            if (ItemScale.c(stone) || ItemScale.c(goldenApple)) {
                throw new IllegalStateException("ItemScale NONE selector accepted an item");
            }
            runtimeMilestone("high-risk-functional-probe14-effect-pass:ItemScale:none=false");

            this.setModuleEnabledRawForProbe(probe, true);
            ItemScale.renderALL.v(true, 0L);
            if (!probe.o() || !ItemScale.c(stone) || !ItemScale.c(goldenApple)) {
                throw new IllegalStateException("ItemScale ALL selector did not accept items");
            }
            runtimeMilestone("high-risk-functional-probe14-effect-pass:ItemScale:all=true");

            ItemScale.renderALL.v(false, 0L);
            ItemScale.renderGoldenApples.v(true, 0L);
            if (ItemScale.c(stone) || !ItemScale.c(goldenApple)) {
                throw new IllegalStateException("ItemScale GAPPLES selector mismatch");
            }
            runtimeMilestone("high-risk-functional-probe14-effect-pass:ItemScale:gapples=true");

            this.setModuleEnabledRawForProbe(probe, originalEnabled);
            ItemScale.nbtOnly.v(originalNbtOnly, 0L);
            ItemScale.megawallsItems.v(originalMegawalls, 0L);
            ItemScale.renderSwordsAndBows.v(originalWeapons, 0L);
            ItemScale.bedwarsResources.v(originalBedwars, 0L);
            ItemScale.renderGoldenApples.v(originalGapples, 0L);
            ItemScale.renderALL.v(originalAll, 0L);

            if (probe.o() != originalEnabled
                    || ItemScale.nbtOnly.c() != originalNbtOnly
                    || ItemScale.megawallsItems.c() != originalMegawalls
                    || ItemScale.renderSwordsAndBows.c() != originalWeapons
                    || ItemScale.bedwarsResources.c() != originalBedwars
                    || ItemScale.renderGoldenApples.c() != originalGapples
                    || ItemScale.renderALL.c() != originalAll
                    || probe.l() || probe.K()) {
                throw new IllegalStateException("ItemScale probe state did not restore exactly");
            }

            this.highRiskFunctionalProbe14Stage = 1;
            runtimeMilestone("high-risk-functional-probe14-restore-pass:ItemScale:enabled="
                    + originalEnabled + ":all=" + originalAll + ":gapples=" + originalGapples);
            runtimeMilestone("high-risk-functional-probe14-module-pass:ItemScale");
            runtimeMilestone("high-risk-functional-probe14-pass:1");
        }
        catch (Throwable failure) {
            if (saved) {
                try {
                    this.setModuleEnabledRawForProbe(probe, originalEnabled);
                    ItemScale.nbtOnly.v(originalNbtOnly, 0L);
                    ItemScale.megawallsItems.v(originalMegawalls, 0L);
                    ItemScale.renderSwordsAndBows.v(originalWeapons, 0L);
                    ItemScale.bedwarsResources.v(originalBedwars, 0L);
                    ItemScale.renderGoldenApples.v(originalGapples, 0L);
                    ItemScale.renderALL.v(originalAll, 0L);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure("HighRiskFunctionalProbe14:ItemScale", "restore", restoreFailure);
                }
            }
            this.highRiskFunctionalProbe14Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe14:ItemScale", "selection-policy", failure);
            runtimeMilestone("high-risk-functional-probe14-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe15() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe15")
                || this.highRiskFunctionalProbe15Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe14")
                && this.highRiskFunctionalProbe14Stage < 1) return;

        AntiNick probe = Modules.J(AntiNick.class);
        boolean saved = false;
        boolean originalEnabled = false;
        String originalSuffix = null;
        try {
            if (probe == null
                    || ModuleManager.f != probe
                    || ModuleManager.byClass(AntiNick.class) != probe
                    || ModuleManager.byName("AntiNick") != probe
                    || AntiNick.suffix == null) {
                throw new IllegalStateException("AntiNick live name-decoration authority unavailable");
            }

            originalEnabled = probe.o();
            originalSuffix = AntiNick.suffix.X();
            saved = true;

            final String sentinel = "[OPENABYSS_ANTINICK_PROBE]";
            AntiNick.suffix.O(sentinel);

            NetworkPlayerInfo nicked = new NetworkPlayerInfo(new GameProfile(
                    UUID.fromString("00000000-0000-1000-8000-000000000001"), "NickProbe"));
            PlayerGetNameEvent nickedEvent = new PlayerGetNameEvent(nicked, "NickProbe");
            probe.onPlayerGetName(nickedEvent);
            if (!("NickProbe " + sentinel).equals(nickedEvent.d())) {
                throw new IllegalStateException("AntiNick version-1 profile decoration mismatch: " + nickedEvent.d());
            }
            runtimeMilestone("high-risk-functional-probe15-effect-pass:AntiNick:version1=true");

            NetworkPlayerInfo ordinary = new NetworkPlayerInfo(new GameProfile(
                    UUID.fromString("00000000-0000-4000-8000-000000000001"), "OrdinaryProbe"));
            PlayerGetNameEvent ordinaryEvent = new PlayerGetNameEvent(ordinary, "OrdinaryProbe");
            probe.onPlayerGetName(ordinaryEvent);
            if (!"OrdinaryProbe".equals(ordinaryEvent.d())) {
                throw new IllegalStateException("AntiNick non-version-1 profile was decorated: " + ordinaryEvent.d());
            }
            runtimeMilestone("high-risk-functional-probe15-effect-pass:AntiNick:version4=false");

            AntiNick.suffix.O(originalSuffix);
            this.setModuleEnabledRawForProbe(probe, originalEnabled);
            if (probe.o() != originalEnabled
                    || !String.valueOf(originalSuffix).equals(String.valueOf(AntiNick.suffix.X()))
                    || probe.l() || probe.K()) {
                throw new IllegalStateException("AntiNick probe state did not restore exactly");
            }

            this.highRiskFunctionalProbe15Stage = 1;
            runtimeMilestone("high-risk-functional-probe15-restore-pass:AntiNick:enabled=" + originalEnabled);
            runtimeMilestone("high-risk-functional-probe15-module-pass:AntiNick");
            runtimeMilestone("high-risk-functional-probe15-pass:1");
        }
        catch (Throwable failure) {
            if (saved) {
                try {
                    AntiNick.suffix.O(originalSuffix);
                    this.setModuleEnabledRawForProbe(probe, originalEnabled);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure("HighRiskFunctionalProbe15:AntiNick", "restore", restoreFailure);
                }
            }
            this.highRiskFunctionalProbe15Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe15:AntiNick", "player-name-decoration", failure);
            runtimeMilestone("high-risk-functional-probe15-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe16() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe16")
                || this.highRiskFunctionalProbe16Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe15")
                && this.highRiskFunctionalProbe15Stage < 1) return;

        KeyStrokes probe = Modules.J(KeyStrokes.class);
        Map<KeyBinding, Long> timestamps = null;
        Map<KeyBinding, Long> originalTimestamps = null;
        boolean originalEnabled = false;
        boolean saved = false;
        try {
            if (probe == null
                    || ModuleManager.byClass(KeyStrokes.class) != probe
                    || ModuleManager.byName("KeyStrokes") != probe
                    || KeyStrokes.offsetX == null
                    || KeyStrokes.offsetY == null
                    || KeyStrokes.backgroundOpacity == null) {
                throw new IllegalStateException("KeyStrokes live input-display authority unavailable");
            }

            Field timestampsField = KeyStrokes.class.getDeclaredField("h");
            timestampsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<KeyBinding, Long> liveTimestamps = (Map<KeyBinding, Long>)timestampsField.get(null);
            timestamps = liveTimestamps;
            if (timestamps == null) {
                throw new IllegalStateException("KeyStrokes timestamp map is null");
            }

            originalTimestamps = new HashMap<KeyBinding, Long>(timestamps);
            originalEnabled = probe.o();
            saved = true;

            KeyStrokes.T();
            KeyBinding forward = this.c.gameSettings.keyBindForward;
            if (!timestamps.containsKey(forward)) {
                throw new IllegalStateException("KeyStrokes forward binding was not initialized");
            }

            timestamps.put(forward, Long.valueOf(0L));
            long before = System.currentTimeMillis();
            probe.onSetKeyBindState(new SetKeyBindStateEvent(forward.getKeyCode()));
            Long stamped = timestamps.get(forward);
            if (stamped == null || stamped.longValue() < before) {
                throw new IllegalStateException("KeyStrokes SetKeyBindState event did not stamp the forward key");
            }
            runtimeMilestone("high-risk-functional-probe16-effect-pass:KeyStrokes:setKeyBindState=true");

            timestamps.put(forward, Long.valueOf(0L));
            probe.onIsPressed(new Abyss.event.events.IsPressedEvent(forward.getKeyCode(), true));
            stamped = timestamps.get(forward);
            if (stamped == null || stamped.longValue() <= 0L) {
                throw new IllegalStateException("KeyStrokes IsPressed event did not stamp the forward key");
            }
            runtimeMilestone("high-risk-functional-probe16-effect-pass:KeyStrokes:isPressed=true");

            timestamps.clear();
            timestamps.putAll(originalTimestamps);
            this.setModuleEnabledRawForProbe(probe, originalEnabled);
            if (probe.o() != originalEnabled || probe.l() || probe.K()
                    || !timestamps.equals(originalTimestamps)) {
                throw new IllegalStateException("KeyStrokes probe state did not restore exactly");
            }

            this.highRiskFunctionalProbe16Stage = 1;
            runtimeMilestone("high-risk-functional-probe16-restore-pass:KeyStrokes:enabled=" + originalEnabled);
            runtimeMilestone("high-risk-functional-probe16-module-pass:KeyStrokes");
            runtimeMilestone("high-risk-functional-probe16-pass:1");
        }
        catch (Throwable failure) {
            if (saved) {
                try {
                    if (timestamps != null && originalTimestamps != null) {
                        timestamps.clear();
                        timestamps.putAll(originalTimestamps);
                    }
                    this.setModuleEnabledRawForProbe(probe, originalEnabled);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure("HighRiskFunctionalProbe16:KeyStrokes", "restore", restoreFailure);
                }
            }
            this.highRiskFunctionalProbe16Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe16:KeyStrokes", "input-timestamp-events", failure);
            runtimeMilestone("high-risk-functional-probe16-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe17() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe17")
                || this.highRiskFunctionalProbe17Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe16")
                && this.highRiskFunctionalProbe16Stage < 1) return;

        ContainerKeeper probe = Modules.J(ContainerKeeper.class);
        boolean saved = false;
        boolean originalEnabled = false;
        boolean originalRequireShift = false;
        String originalToggleKey = null;
        net.minecraft.client.gui.GuiScreen originalScreen = null;
        boolean originalFocus = false;
        try {
            if (probe == null
                    || ModuleManager.byClass(ContainerKeeper.class) != probe
                    || ModuleManager.byName("ContainerKeeper") != probe
                    || ContainerKeeper.toggleKey == null
                    || ContainerKeeper.requireShiftToSave == null
                    || this.c.thePlayer == null) {
                throw new IllegalStateException("ContainerKeeper live container authority unavailable");
            }

            originalEnabled = probe.o();
            originalRequireShift = ContainerKeeper.requireShiftToSave.c();
            originalToggleKey = ContainerKeeper.toggleKey.X();
            originalScreen = this.c.currentScreen;
            originalFocus = this.c.inGameHasFocus;
            saved = true;

            ContainerKeeper.requireShiftToSave.v(false, 0L);
            Field armedField = ContainerKeeper.class.getDeclaredField("v");
            Field savedField = ContainerKeeper.class.getDeclaredField("t");
            Field debounceField = ContainerKeeper.class.getDeclaredField("T");
            Field screenField = ContainerKeeper.class.getDeclaredField("H");
            armedField.setAccessible(true);
            savedField.setAccessible(true);
            debounceField.setAccessible(true);
            screenField.setAccessible(true);
            Method toggle = ContainerKeeper.class.getDeclaredMethod("W", Boolean.TYPE, Long.TYPE);
            toggle.setAccessible(true);

            GuiChest fixture = new GuiChest(
                    this.c.thePlayer.inventory,
                    new InventoryBasic("OpenAbyss ContainerKeeper Probe", false, 9));
            this.c.displayGuiScreen(fixture);
            armedField.setBoolean(probe, true);
            debounceField.setBoolean(probe, false);
            toggle.invoke(probe, Boolean.TRUE, Long.valueOf(0L));

            if (this.c.currentScreen != null
                    || !savedField.getBoolean(probe)
                    || screenField.get(probe) != fixture) {
                throw new IllegalStateException("ContainerKeeper did not hide and retain the active container");
            }
            runtimeMilestone("high-risk-functional-probe17-effect-pass:ContainerKeeper:hide=true");

            debounceField.setBoolean(probe, false);
            toggle.invoke(probe, Boolean.TRUE, Long.valueOf(0L));
            if (this.c.currentScreen != fixture
                    || savedField.getBoolean(probe)) {
                throw new IllegalStateException("ContainerKeeper did not restore the retained container");
            }
            runtimeMilestone("high-risk-functional-probe17-effect-pass:ContainerKeeper:restoreGui=true");

            probe.A(0L);
            if (savedField.getBoolean(probe)
                    || armedField.getBoolean(probe)
                    || debounceField.getBoolean(probe)
                    || screenField.get(probe) != null) {
                throw new IllegalStateException("ContainerKeeper disable reset did not clear retained state");
            }
            runtimeMilestone("high-risk-functional-probe17-effect-pass:ContainerKeeper:disableReset=true");

            ContainerKeeper.requireShiftToSave.v(originalRequireShift, 0L);
            ContainerKeeper.toggleKey.O(originalToggleKey);
            this.c.displayGuiScreen(originalScreen);
            this.c.inGameHasFocus = originalFocus;
            this.setModuleEnabledRawForProbe(probe, originalEnabled);

            if (probe.o() != originalEnabled
                    || ContainerKeeper.requireShiftToSave.c() != originalRequireShift
                    || !String.valueOf(originalToggleKey).equals(String.valueOf(ContainerKeeper.toggleKey.X()))
                    || probe.l() || probe.K()) {
                throw new IllegalStateException("ContainerKeeper probe state did not restore exactly");
            }

            this.highRiskFunctionalProbe17Stage = 1;
            runtimeMilestone("high-risk-functional-probe17-restore-pass:ContainerKeeper:enabled=" + originalEnabled);
            runtimeMilestone("high-risk-functional-probe17-module-pass:ContainerKeeper");
            runtimeMilestone("high-risk-functional-probe17-pass:1");
        }
        catch (Throwable failure) {
            if (saved) {
                try {
                    probe.A(0L);
                    ContainerKeeper.requireShiftToSave.v(originalRequireShift, 0L);
                    ContainerKeeper.toggleKey.O(originalToggleKey);
                    this.c.displayGuiScreen(originalScreen);
                    this.c.inGameHasFocus = originalFocus;
                    this.setModuleEnabledRawForProbe(probe, originalEnabled);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure("HighRiskFunctionalProbe17:ContainerKeeper", "restore", restoreFailure);
                }
            }
            this.highRiskFunctionalProbe17Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe17:ContainerKeeper", "retain-reopen-container", failure);
            runtimeMilestone("high-risk-functional-probe17-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe18() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe18")
                || this.highRiskFunctionalProbe18Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe17")
                && this.highRiskFunctionalProbe17Stage < 1) return;

        BindGUI probe = Modules.J(BindGUI.class);
        boolean saved = false;
        boolean originalEnabled = false;
        List<Module> liveEntries = null;
        List<Module> originalEntries = null;
        int originalWidth = 0;
        try {
            if (probe == null
                    || ModuleManager.byClass(BindGUI.class) != probe
                    || ModuleManager.byName("BindGUI") != probe
                    || BindGUI.scale == null
                    || BindGUI.offsetX == null
                    || BindGUI.offsetY == null) {
                throw new IllegalStateException("BindGUI live bind-list authority unavailable");
            }

            Field entriesField = BindGUI.class.getDeclaredField("m");
            Field widthField = BindGUI.class.getDeclaredField("o");
            entriesField.setAccessible(true);
            widthField.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<Module> reflectedEntries = (List<Module>)entriesField.get(probe);
            liveEntries = reflectedEntries;
            if (liveEntries == null) {
                throw new IllegalStateException("BindGUI entry list is null");
            }

            originalEntries = new java.util.ArrayList<Module>(liveEntries);
            originalWidth = widthField.getInt(probe);
            originalEnabled = probe.o();
            saved = true;

            Method rebuild = BindGUI.class.getDeclaredMethod("O", Long.TYPE);
            rebuild.setAccessible(true);
            rebuild.invoke(probe, Long.valueOf(0L));

            ClickGUI clickGui = Modules.J(ClickGUI.class);
            if (clickGui == null || clickGui.h() == 0) {
                throw new IllegalStateException("ClickGUI bound fixture unavailable");
            }
            if (!liveEntries.contains(clickGui)) {
                throw new IllegalStateException("BindGUI rebuilt list omitted bound ClickGUI");
            }
            for (Module entry : liveEntries) {
                if (entry == null || entry.h() == 0) {
                    throw new IllegalStateException("BindGUI rebuilt list contains an unbound module");
                }
            }
            int rebuiltWidth = widthField.getInt(probe);
            if (rebuiltWidth <= 0) {
                throw new IllegalStateException("BindGUI rebuilt width was not positive");
            }
            runtimeMilestone("high-risk-functional-probe18-effect-pass:BindGUI:entries="
                    + liveEntries.size() + ":width=" + rebuiltWidth);

            probe.A(0L);
            if (!liveEntries.isEmpty() || widthField.getInt(probe) != 0) {
                throw new IllegalStateException("BindGUI disable reset did not clear cached entries");
            }
            runtimeMilestone("high-risk-functional-probe18-effect-pass:BindGUI:disableReset=true");

            liveEntries.clear();
            liveEntries.addAll(originalEntries);
            widthField.setInt(probe, originalWidth);
            this.setModuleEnabledRawForProbe(probe, originalEnabled);
            if (probe.o() != originalEnabled
                    || !liveEntries.equals(originalEntries)
                    || widthField.getInt(probe) != originalWidth
                    || probe.l() || probe.K()) {
                throw new IllegalStateException("BindGUI probe state did not restore exactly");
            }

            this.highRiskFunctionalProbe18Stage = 1;
            runtimeMilestone("high-risk-functional-probe18-restore-pass:BindGUI:enabled=" + originalEnabled);
            runtimeMilestone("high-risk-functional-probe18-module-pass:BindGUI");
            runtimeMilestone("high-risk-functional-probe18-pass:1");
        }
        catch (Throwable failure) {
            if (saved) {
                try {
                    if (liveEntries != null && originalEntries != null) {
                        liveEntries.clear();
                        liveEntries.addAll(originalEntries);
                    }
                    Field widthField = BindGUI.class.getDeclaredField("o");
                    widthField.setAccessible(true);
                    widthField.setInt(probe, originalWidth);
                    this.setModuleEnabledRawForProbe(probe, originalEnabled);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure("HighRiskFunctionalProbe18:BindGUI", "restore", restoreFailure);
                }
            }
            this.highRiskFunctionalProbe18Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe18:BindGUI", "bound-module-cache", failure);
            runtimeMilestone("high-risk-functional-probe18-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe19() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe19")
                || this.highRiskFunctionalProbe19Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe18")
                && this.highRiskFunctionalProbe18Stage < 1) return;

        LeapModeHUD probe = Modules.J(LeapModeHUD.class);
        boolean saved = false;
        boolean originalEnabled = false;
        String originalMode = null;
        try {
            if (probe == null
                    || ModuleManager.byClass(LeapModeHUD.class) != probe
                    || ModuleManager.byName("LeapModeHUD") != probe
                    || LeapModeHUD.scale == null
                    || LeapModeHUD.offsetX == null
                    || LeapModeHUD.offsetY == null
                    || LeapModeHUD.backgroundOpacity == null) {
                throw new IllegalStateException("LeapModeHUD live packet-state authority unavailable");
            }

            Field modeField = LeapModeHUD.class.getDeclaredField("h");
            modeField.setAccessible(true);
            originalMode = (String)modeField.get(probe);
            originalEnabled = probe.o();
            saved = true;

            S02PacketChat arcedPacket = new S02PacketChat(new ChatComponentText(
                    "Your primary Leap skill switched to Arced mode."));
            probe.onReceivePacket(0L, new ReceivePacketEvent(arcedPacket));
            String mode = (String)modeField.get(probe);
            if (!(EnumChatFormatting.AQUA + "Arced").equals(mode)) {
                throw new IllegalStateException("LeapModeHUD did not enter Arced mode: " + mode);
            }
            runtimeMilestone("high-risk-functional-probe19-effect-pass:LeapModeHUD:arced=true");

            S02PacketChat arrowPacket = new S02PacketChat(new ChatComponentText(
                    "Your primary Leap skill switched to Arrow mode."));
            probe.onReceivePacket(0L, new ReceivePacketEvent(arrowPacket));
            mode = (String)modeField.get(probe);
            if (!(EnumChatFormatting.GOLD + "Arrow").equals(mode)) {
                throw new IllegalStateException("LeapModeHUD did not return to Arrow mode: " + mode);
            }
            runtimeMilestone("high-risk-functional-probe19-effect-pass:LeapModeHUD:arrow=true");

            modeField.set(probe, originalMode);
            this.setModuleEnabledRawForProbe(probe, originalEnabled);
            if (probe.o() != originalEnabled
                    || !String.valueOf(originalMode).equals(String.valueOf(modeField.get(probe)))
                    || probe.l() || probe.K()) {
                throw new IllegalStateException("LeapModeHUD probe state did not restore exactly");
            }

            this.highRiskFunctionalProbe19Stage = 1;
            runtimeMilestone("high-risk-functional-probe19-restore-pass:LeapModeHUD:enabled=" + originalEnabled);
            runtimeMilestone("high-risk-functional-probe19-module-pass:LeapModeHUD");
            runtimeMilestone("high-risk-functional-probe19-pass:1");
        }
        catch (Throwable failure) {
            if (saved) {
                try {
                    Field modeField = LeapModeHUD.class.getDeclaredField("h");
                    modeField.setAccessible(true);
                    modeField.set(probe, originalMode);
                    this.setModuleEnabledRawForProbe(probe, originalEnabled);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure("HighRiskFunctionalProbe19:LeapModeHUD", "restore", restoreFailure);
                }
            }
            this.highRiskFunctionalProbe19Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe19:LeapModeHUD", "chat-packet-mode", failure);
            runtimeMilestone("high-risk-functional-probe19-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe20() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe20")
                || this.highRiskFunctionalProbe20Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe19")
                && this.highRiskFunctionalProbe19Stage < 1) return;

        TeamInvisible probe = Modules.J(TeamInvisible.class);
        boolean saved = false;
        boolean originalEnabled = false;
        String originalSortMode = null;
        String originalPattern = null;
        float originalRange = 0.0f;
        int originalOpacity = 0;
        EntityOtherPlayerMP fixture = null;
        try {
            if (probe == null
                    || ModuleManager.y != probe
                    || ModuleManager.byClass(TeamInvisible.class) != probe
                    || ModuleManager.byName("TeamInvisible") != probe
                    || Teams.sortMode == null
                    || Teams.customPatternRegex == null
                    || TeamInvisible.range == null
                    || TeamInvisible.opacity == null
                    || this.c.thePlayer == null
                    || this.c.theWorld == null) {
                throw new IllegalStateException("TeamInvisible live team-selection authority unavailable");
            }

            originalEnabled = probe.o();
            originalSortMode = Teams.sortMode.Y();
            originalPattern = Teams.customPatternRegex.X();
            originalRange = TeamInvisible.range.L();
            originalOpacity = TeamInvisible.opacity.k();
            saved = true;

            String localName = this.c.thePlayer.getName();
            char firstLetter = 'A';
            for (int index = 0; index < localName.length(); ++index) {
                char candidate = localName.charAt(index);
                if (Character.isLetter(candidate)) {
                    firstLetter = candidate;
                    break;
                }
            }
            String fixtureName = String.valueOf(firstLetter) + "OpenAbyssTeamProbe";
            fixture = new EntityOtherPlayerMP(this.c.theWorld, new GameProfile(
                    UUID.fromString("00000000-0000-4000-8000-000000000020"), fixtureName));
            fixture.setPosition(this.c.thePlayer.posX + 1.0, this.c.thePlayer.posY, this.c.thePlayer.posZ);
            this.c.theWorld.addEntityToWorld(-2147483620, fixture);

            Teams.sortMode.i("PATTERN");
            Teams.customPatternRegex.O("[A-Za-z]");
            TeamInvisible.range.o((byte)0, 0L, 64.0f);
            TeamInvisible.opacity.d(20);

            Method selector = TeamInvisible.class.getDeclaredMethod(
                    "C", Long.TYPE, Character.TYPE, net.minecraft.entity.Entity.class);
            selector.setAccessible(true);
            boolean selected = ((Boolean)selector.invoke(
                    probe, Long.valueOf(0L), Character.valueOf('\ub4eb'), fixture)).booleanValue();
            if (!selected) {
                throw new IllegalStateException("TeamInvisible PATTERN teammate fixture was not selected");
            }
            runtimeMilestone("high-risk-functional-probe20-effect-pass:TeamInvisible:pattern=true");

            Teams.sortMode.i("NONE");
            boolean disabledByTeams = ((Boolean)selector.invoke(
                    probe, Long.valueOf(0L), Character.valueOf('\ub4eb'), fixture)).booleanValue();
            if (disabledByTeams) {
                throw new IllegalStateException("TeamInvisible selected fixture while Teams mode was NONE");
            }
            runtimeMilestone("high-risk-functional-probe20-effect-pass:TeamInvisible:none=false");

            this.c.theWorld.removeEntityFromWorld(-2147483620);
            fixture = null;
            Teams.sortMode.i(originalSortMode);
            Teams.customPatternRegex.O(originalPattern);
            TeamInvisible.range.o((byte)0, 0L, originalRange);
            TeamInvisible.opacity.d(originalOpacity);
            this.setModuleEnabledRawForProbe(probe, originalEnabled);

            if (probe.o() != originalEnabled
                    || !String.valueOf(originalSortMode).equals(String.valueOf(Teams.sortMode.Y()))
                    || !String.valueOf(originalPattern).equals(String.valueOf(Teams.customPatternRegex.X()))
                    || Math.abs(TeamInvisible.range.L() - originalRange) > 0.001f
                    || TeamInvisible.opacity.k() != originalOpacity
                    || probe.l() || probe.K()) {
                throw new IllegalStateException("TeamInvisible probe state did not restore exactly");
            }

            this.highRiskFunctionalProbe20Stage = 1;
            runtimeMilestone("high-risk-functional-probe20-restore-pass:TeamInvisible:enabled=" + originalEnabled);
            runtimeMilestone("high-risk-functional-probe20-module-pass:TeamInvisible");
            runtimeMilestone("high-risk-functional-probe20-pass:1");
        }
        catch (Throwable failure) {
            if (fixture != null && this.c.theWorld != null) {
                try {
                    this.c.theWorld.removeEntityFromWorld(-2147483620);
                }
                catch (Throwable ignored) {
                }
            }
            if (saved) {
                try {
                    Teams.sortMode.i(originalSortMode);
                    Teams.customPatternRegex.O(originalPattern);
                    TeamInvisible.range.o((byte)0, 0L, originalRange);
                    TeamInvisible.opacity.d(originalOpacity);
                    this.setModuleEnabledRawForProbe(probe, originalEnabled);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure("HighRiskFunctionalProbe20:TeamInvisible", "restore", restoreFailure);
                }
            }
            this.highRiskFunctionalProbe20Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe20:TeamInvisible", "team-selection", failure);
            runtimeMilestone("high-risk-functional-probe20-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe21() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe21")
                || this.highRiskFunctionalProbe21Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe20")
                && this.highRiskFunctionalProbe20Stage < 1) return;

        CustomCape customCape = Modules.J(CustomCape.class);
        Font font = Modules.J(Font.class);
        Gadgets gadgets = Modules.J(Gadgets.class);
        NoObfuscation noObfuscation = Modules.J(NoObfuscation.class);
        Language language = Modules.J(Language.class);

        boolean saved = false;
        String originalCape = null;
        String originalOthersFont = null;
        String originalScoreboardFont = null;
        String originalNotificationsFont = null;
        String originalClickGuiFont = null;
        String originalHudFont = null;
        String originalArrayListFont = null;
        boolean originalNoMiningParticles = false;
        boolean originalBetterWorldSwapping = false;
        boolean originalNoScreenBackground = false;

        try {
            if (customCape == null || font == null || gadgets == null
                    || noObfuscation == null || language == null
                    || CustomCape.cape == null || CustomCape.O == null
                    || Font.othersFont == null || Font.scoreboardFont == null
                    || Font.notificationsFont == null || Font.clickguiFont == null
                    || Font.hudFont == null || Font.arraylistFont == null
                    || Gadgets.noMiningParticles == null
                    || Gadgets.betterWorldSwapping == null
                    || Gadgets.noScreenBackground == null
                    || Language.language == null) {
                throw new IllegalStateException("promoted helper/config authority unavailable");
            }

            originalCape = CustomCape.cape.Y();
            originalOthersFont = Font.othersFont.Y();
            originalScoreboardFont = Font.scoreboardFont.Y();
            originalNotificationsFont = Font.notificationsFont.Y();
            originalClickGuiFont = Font.clickguiFont.Y();
            originalHudFont = Font.hudFont.Y();
            originalArrayListFont = Font.arraylistFont.Y();
            originalNoMiningParticles = Gadgets.noMiningParticles.c();
            originalBetterWorldSwapping = Gadgets.betterWorldSwapping.c();
            originalNoScreenBackground = Gadgets.noScreenBackground.c();
            saved = true;

            if (CustomCape.O.size() != 23) {
                throw new IllegalStateException("CustomCape mapping count was " + CustomCape.O.size());
            }
            for (Map.Entry<String, String> entry : CustomCape.O.entrySet()) {
                if (!CustomCape.cape.S().contains(entry.getKey())) {
                    throw new IllegalStateException("CustomCape mode missing mapping key " + entry.getKey());
                }
                CustomCape.cape.i(entry.getKey());
                net.minecraft.util.ResourceLocation location = CustomCape.d(0L);
                String expectedPath = "capes/" + entry.getValue() + ".png";
                if (!"minecraft".equals(location.getResourceDomain())
                        || !expectedPath.equals(location.getResourcePath())) {
                    throw new IllegalStateException("CustomCape path mismatch "
                            + entry.getKey() + " -> " + location);
                }
                if (CustomCape.class.getResource("/assets/minecraft/" + expectedPath) == null) {
                    throw new IllegalStateException("CustomCape resource missing " + expectedPath);
                }
            }
            runtimeMilestone("high-risk-functional-probe21-effect-pass:CustomCape:mappings=23");

            Font.othersFont.i("NONE");
            Font.scoreboardFont.i("NONE");
            Font.notificationsFont.i("NONE");
            Font.clickguiFont.i("NONE");
            Font.hudFont.i("NONE");
            Font.arraylistFont.i("NONE");
            CustomFont noneOthers = Font.s(0L);
            CustomFont noneScoreboard = Font.J();
            CustomFont noneNotifications = Font.O((short)0, 0);
            CustomFont noneClickGui = Font.m(0L);
            CustomFont noneHud = Font.F(0L);
            CustomFont noneArrayList = Font.Q(0L);

            Font.othersFont.i("PRODUCT_SANS");
            Font.scoreboardFont.i("PRODUCT_SANS");
            Font.notificationsFont.i("PRODUCT_SANS");
            Font.clickguiFont.i("PRODUCT_SANS");
            Font.hudFont.i("PRODUCT_SANS");
            Font.arraylistFont.i("PRODUCT_SANS");
            CustomFont product = Font.s(0L);
            if (product == null
                    || product == noneOthers
                    || Font.J() != product
                    || Font.O((short)0, 0) != product
                    || Font.m(0L) != product
                    || Font.F(0L) != product
                    || Font.Q(0L) != product
                    || noneScoreboard != noneOthers
                    || noneNotifications != noneOthers
                    || noneClickGui != noneOthers
                    || noneHud != noneOthers
                    || noneArrayList != noneOthers) {
                throw new IllegalStateException("Font mode routing did not select shared PRODUCT_SANS authority");
            }
            Font.hudFont.i("TAHOMA");
            if (Font.F(0L) == product || Font.F(0L) == noneHud) {
                throw new IllegalStateException("Font HUD TAHOMA selector did not change authority");
            }
            runtimeMilestone("high-risk-functional-probe21-effect-pass:Font:selectors=6");

            Gadgets.noMiningParticles.v(false, 0L);
            CallbackInfo particlesOff = new CallbackInfo();
            EffectRendererHooks.cancelDestroyParticles(particlesOff);
            if (particlesOff.isCancelled()) {
                throw new IllegalStateException("Gadgets cancelled mining particles while disabled");
            }
            Gadgets.noMiningParticles.v(true, 0L);
            CallbackInfo destroyOn = new CallbackInfo();
            CallbackInfo hitOn = new CallbackInfo();
            EffectRendererHooks.cancelDestroyParticles(destroyOn);
            EffectRendererHooks.cancelHitParticles(hitOn);
            if (!destroyOn.isCancelled() || !hitOn.isCancelled()) {
                throw new IllegalStateException("Gadgets mining-particle hooks did not cancel");
            }

            Gadgets.betterWorldSwapping.v(false, 0L);
            CallbackInfo loadingOff = new CallbackInfo();
            LoadingScreenRendererHooks.forSkipProgress(0, loadingOff);
            if (loadingOff.isCancelled()) {
                throw new IllegalStateException("Gadgets loading hook cancelled while disabled");
            }
            Gadgets.betterWorldSwapping.v(true, 0L);
            CallbackInfo loadingOn = new CallbackInfo();
            LoadingScreenRendererHooks.forSkipProgress(0, loadingOn);
            if (!loadingOn.isCancelled()) {
                throw new IllegalStateException("Gadgets loading hook did not cancel");
            }

            Gadgets.noScreenBackground.v(false, 0L);
            if (GuiScreenHooks.shouldCancel()) {
                throw new IllegalStateException("Gadgets screen background cancelled while disabled");
            }
            Gadgets.noScreenBackground.v(true, 0L);
            if (!GuiScreenHooks.shouldCancel()) {
                throw new IllegalStateException("Gadgets screen background did not cancel in-world");
            }
            runtimeMilestone("high-risk-functional-probe21-effect-pass:Gadgets:hooks=3");

            String obfuscated = "A\u00a7kB\u00a7kC";
            if (!"ABC".equals(NoObfuscation.f(obfuscated)) || NoObfuscation.f(null) != null) {
                throw new IllegalStateException("NoObfuscation formatter contract failed");
            }
            runtimeMilestone("high-risk-functional-probe21-effect-pass:NoObfuscation:strip=true");

            if (!"Set".equals(Language.z("clickgui.studio.set", 0L))
                    || !"openabyss.unknown.key".equals(Language.z("openabyss.unknown.key", 0L))) {
                throw new IllegalStateException("Language known/fallback lookup contract failed");
            }
            runtimeMilestone("high-risk-functional-probe21-effect-pass:Language:lookup=true");

            CustomCape.cape.i(originalCape);
            Font.othersFont.i(originalOthersFont);
            Font.scoreboardFont.i(originalScoreboardFont);
            Font.notificationsFont.i(originalNotificationsFont);
            Font.clickguiFont.i(originalClickGuiFont);
            Font.hudFont.i(originalHudFont);
            Font.arraylistFont.i(originalArrayListFont);
            Gadgets.noMiningParticles.v(originalNoMiningParticles, 0L);
            Gadgets.betterWorldSwapping.v(originalBetterWorldSwapping, 0L);
            Gadgets.noScreenBackground.v(originalNoScreenBackground, 0L);

            if (!originalCape.equals(CustomCape.cape.Y())
                    || !originalOthersFont.equals(Font.othersFont.Y())
                    || !originalScoreboardFont.equals(Font.scoreboardFont.Y())
                    || !originalNotificationsFont.equals(Font.notificationsFont.Y())
                    || !originalClickGuiFont.equals(Font.clickguiFont.Y())
                    || !originalHudFont.equals(Font.hudFont.Y())
                    || !originalArrayListFont.equals(Font.arraylistFont.Y())
                    || Gadgets.noMiningParticles.c() != originalNoMiningParticles
                    || Gadgets.betterWorldSwapping.c() != originalBetterWorldSwapping
                    || Gadgets.noScreenBackground.c() != originalNoScreenBackground) {
                throw new IllegalStateException("promoted helper/config probe state did not restore exactly");
            }

            this.highRiskFunctionalProbe21Stage = 1;
            runtimeMilestone("high-risk-functional-probe21-restore-pass:modules=5");
            runtimeMilestone("high-risk-functional-probe21-module-pass:CustomCape");
            runtimeMilestone("high-risk-functional-probe21-module-pass:Font");
            runtimeMilestone("high-risk-functional-probe21-module-pass:Gadgets");
            runtimeMilestone("high-risk-functional-probe21-module-pass:NoObfuscation");
            runtimeMilestone("high-risk-functional-probe21-module-pass:Language");
            runtimeMilestone("high-risk-functional-probe21-pass:5");
        }
        catch (Throwable failure) {
            if (saved) {
                try {
                    CustomCape.cape.i(originalCape);
                    Font.othersFont.i(originalOthersFont);
                    Font.scoreboardFont.i(originalScoreboardFont);
                    Font.notificationsFont.i(originalNotificationsFont);
                    Font.clickguiFont.i(originalClickGuiFont);
                    Font.hudFont.i(originalHudFont);
                    Font.arraylistFont.i(originalArrayListFont);
                    Gadgets.noMiningParticles.v(originalNoMiningParticles, 0L);
                    Gadgets.betterWorldSwapping.v(originalBetterWorldSwapping, 0L);
                    Gadgets.noScreenBackground.v(originalNoScreenBackground, 0L);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure("HighRiskFunctionalProbe21", "restore", restoreFailure);
                }
            }
            this.highRiskFunctionalProbe21Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe21", "promoted-helper-config", failure);
            runtimeMilestone("high-risk-functional-probe21-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe22() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe22")
                || this.highRiskFunctionalProbe22Stage < 0
                || this.highRiskFunctionalProbe22Stage >= 5) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe21")
                && this.highRiskFunctionalProbe21Stage < 1) return;

        RawInput rawInput = Modules.J(RawInput.class);
        VisualSpoof visualSpoof = Modules.J(VisualSpoof.class);
        Theme theme = Modules.J(Theme.class);
        try {
            if (rawInput == null
                    || ModuleManager.byClass(RawInput.class) != rawInput
                    || ModuleManager.byName("RawInput") != rawInput
                    || visualSpoof == null
                    || ModuleManager.byClass(VisualSpoof.class) != visualSpoof
                    || ModuleManager.byName("VisualSpoof") != visualSpoof
                    || theme == null
                    || ModuleManager.byClass(Theme.class) != theme
                    || ModuleManager.byName("Theme") != theme
                    || VisualSpoof.t == null
                    || VisualSpoof.o == null
                    || Theme.theme == null
                    || Theme.customTheme == null
                    || Theme.customColor1 == null
                    || Theme.customColor2 == null
                    || Theme.customColor3 == null) {
                throw new IllegalStateException("promoted infrastructure/config authority unavailable");
            }

            if (this.highRiskFunctionalProbe22Stage == 0) {
                if (rawInput.l() || rawInput.K()) {
                    return;
                }
                this.highRiskFunctionalProbe22OriginalRawEnabled = rawInput.o();
                this.highRiskFunctionalProbe22OriginalMouseHelper = this.c.mouseHelper;
                this.highRiskFunctionalProbe22OriginalDisableRenderVisual = VisualSpoof.t.c();
                this.highRiskFunctionalProbe22OriginalScreenshotBypass = VisualSpoof.o.c();
                this.highRiskFunctionalProbe22OriginalTheme = Theme.theme.Y();
                this.highRiskFunctionalProbe22OriginalCustomTheme = Theme.customTheme.Y();
                this.highRiskFunctionalProbe22OriginalColor1 = Theme.customColor1.Q();
                this.highRiskFunctionalProbe22OriginalColor2 = Theme.customColor2.Q();
                this.highRiskFunctionalProbe22OriginalColor3 = Theme.customColor3.Q();
                this.highRiskFunctionalProbe22Saved = true;

                if (rawInput.o()) {
                    rawInput.I(0L, false);
                    this.highRiskFunctionalProbe22Stage = 1;
                    runtimeMilestone("high-risk-functional-probe22-request:RawInput:disable-original");
                } else {
                    rawInput.I(0L, true);
                    this.highRiskFunctionalProbe22Stage = 2;
                    runtimeMilestone("high-risk-functional-probe22-request:RawInput:enable");
                }
                return;
            }

            if (rawInput.l() || rawInput.K()) {
                return;
            }

            if (this.highRiskFunctionalProbe22Stage == 1) {
                if (rawInput.o() || this.c.mouseHelper instanceof SmoothMouseHelper) {
                    throw new IllegalStateException("RawInput original-enabled disable transition did not complete");
                }
                runtimeMilestone("high-risk-functional-probe22-effect-pass:RawInput:disable=true");
                rawInput.I(0L, true);
                this.highRiskFunctionalProbe22Stage = 2;
                runtimeMilestone("high-risk-functional-probe22-request:RawInput:enable");
                return;
            }

            if (this.highRiskFunctionalProbe22Stage == 2) {
                if (!rawInput.o() || !(this.c.mouseHelper instanceof SmoothMouseHelper)) {
                    throw new IllegalStateException("RawInput enable transition did not install SmoothMouseHelper");
                }
                SmoothMouseHelper helper = (SmoothMouseHelper)this.c.mouseHelper;
                Field executorField = SmoothMouseHelper.class.getDeclaredField("A");
                executorField.setAccessible(true);
                this.highRiskFunctionalProbe22RawExecutor =
                        (ScheduledExecutorService)executorField.get(helper);
                if (this.highRiskFunctionalProbe22RawExecutor == null
                        || this.highRiskFunctionalProbe22RawExecutor.isShutdown()) {
                    throw new IllegalStateException("RawInput worker executor was not live after enable");
                }
                int workers = 0;
                for (Thread thread : Thread.getAllStackTraces().keySet()) {
                    if ("OpenAbyss-RawInput".equals(thread.getName()) && thread.isAlive()) {
                        if (!thread.isDaemon()) {
                            throw new IllegalStateException("RawInput worker was not daemon");
                        }
                        ++workers;
                    }
                }
                if (workers < 1) {
                    throw new IllegalStateException("RawInput worker thread was not observed");
                }
                runtimeMilestone("high-risk-functional-probe22-effect-pass:RawInput:enable=true:workers=" + workers);
                rawInput.I(0L, false);
                this.highRiskFunctionalProbe22Stage = 3;
                runtimeMilestone("high-risk-functional-probe22-request:RawInput:disable");
                return;
            }

            if (this.highRiskFunctionalProbe22Stage == 3) {
                if (rawInput.o() || this.c.mouseHelper instanceof SmoothMouseHelper) {
                    throw new IllegalStateException("RawInput disable transition did not restore a vanilla helper");
                }
                if (this.highRiskFunctionalProbe22RawExecutor == null
                        || !this.highRiskFunctionalProbe22RawExecutor.isShutdown()) {
                    throw new IllegalStateException("RawInput worker executor did not shut down");
                }
                runtimeMilestone("high-risk-functional-probe22-effect-pass:RawInput:shutdown=true");

                if (this.highRiskFunctionalProbe22OriginalRawEnabled) {
                    rawInput.I(0L, true);
                    this.highRiskFunctionalProbe22Stage = 4;
                    runtimeMilestone("high-risk-functional-probe22-request:RawInput:restore-enable");
                    return;
                }
                this.verifyHighRiskFunctionalProbe22Config(rawInput, visualSpoof, theme);
                return;
            }

            if (this.highRiskFunctionalProbe22Stage == 4) {
                if (!rawInput.o() || !(this.c.mouseHelper instanceof SmoothMouseHelper)) {
                    throw new IllegalStateException("RawInput original enabled state did not restore");
                }
                runtimeMilestone("high-risk-functional-probe22-rawinput-restore-enabled:true");
                this.verifyHighRiskFunctionalProbe22Config(rawInput, visualSpoof, theme);
            }
        }
        catch (Throwable failure) {
            this.restoreHighRiskFunctionalProbe22(rawInput);
            this.highRiskFunctionalProbe22Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe22", "rawinput-visualspoof-theme", failure);
            runtimeMilestone("high-risk-functional-probe22-fail:" + failure.getClass().getName());
        }
    }

    private void verifyHighRiskFunctionalProbe22Config(
            RawInput rawInput, VisualSpoof visualSpoof, Theme theme) throws Throwable {
        boolean oppositeDisableRender =
                !this.highRiskFunctionalProbe22OriginalDisableRenderVisual;
        VisualSpoof.t.v(oppositeDisableRender, 0L);
        if (VisualSpoofRenderer.x() != oppositeDisableRender) {
            throw new IllegalStateException("VisualSpoof disable-render setting did not reach renderer state");
        }
        VisualSpoof.t.v(
                this.highRiskFunctionalProbe22OriginalDisableRenderVisual, 0L);
        if (VisualSpoofRenderer.x()
                != this.highRiskFunctionalProbe22OriginalDisableRenderVisual) {
            throw new IllegalStateException("VisualSpoof disable-render state did not restore");
        }

        boolean oppositeScreenshot =
                !this.highRiskFunctionalProbe22OriginalScreenshotBypass;
        VisualSpoof.o.v(oppositeScreenshot, 0L);
        if (VisualSpoofRenderer.B() != oppositeScreenshot) {
            throw new IllegalStateException("VisualSpoof screenshot setting did not reach renderer state");
        }
        VisualSpoof.o.v(
                this.highRiskFunctionalProbe22OriginalScreenshotBypass, 0L);
        if (VisualSpoofRenderer.B()
                != this.highRiskFunctionalProbe22OriginalScreenshotBypass) {
            throw new IllegalStateException("VisualSpoof screenshot state did not restore");
        }
        runtimeMilestone("high-risk-functional-probe22-effect-pass:VisualSpoof:settings=2");

        final String sentinelColor = "123456";
        Theme.theme.i("CUSTOM");
        Theme.customTheme.i("CUSTOM");
        Theme.customColor1.e(sentinelColor);
        Theme.customColor2.e(sentinelColor);
        Theme.customColor3.e(sentinelColor);
        int sentinelRgb = new java.awt.Color(Integer.parseInt(sentinelColor, 16)).getRGB();

        List palette = Theme.w(0L, 2.0, 1.0, "CUSTOM");
        if (palette == null || palette.size() != 3
                || ((Integer)palette.get(0)).intValue() != sentinelRgb
                || ((Integer)palette.get(1)).intValue() != sentinelRgb
                || ((Integer)palette.get(2)).intValue() != sentinelRgb) {
            throw new IllegalStateException("Theme custom palette did not route configured RGB authority");
        }
        List livePalette = Theme.k(0, 0, (short)0);
        if (livePalette == null || !palette.equals(livePalette)) {
            throw new IllegalStateException("Theme live palette did not follow CUSTOM setting");
        }
        if (Theme.S(0.0, 0L) != sentinelRgb || Theme.X(0L, 0.0) != sentinelRgb) {
            throw new IllegalStateException("Theme custom gradient did not preserve uniform configured RGB");
        }
        runtimeMilestone("high-risk-functional-probe22-effect-pass:Theme:custom=123456");

        Theme.theme.i(this.highRiskFunctionalProbe22OriginalTheme);
        Theme.customTheme.i(this.highRiskFunctionalProbe22OriginalCustomTheme);
        Theme.customColor1.e(this.highRiskFunctionalProbe22OriginalColor1);
        Theme.customColor2.e(this.highRiskFunctionalProbe22OriginalColor2);
        Theme.customColor3.e(this.highRiskFunctionalProbe22OriginalColor3);

        if (!this.highRiskFunctionalProbe22OriginalTheme.equals(Theme.theme.Y())
                || !this.highRiskFunctionalProbe22OriginalCustomTheme.equals(Theme.customTheme.Y())
                || !this.highRiskFunctionalProbe22OriginalColor1.equals(Theme.customColor1.Q())
                || !this.highRiskFunctionalProbe22OriginalColor2.equals(Theme.customColor2.Q())
                || !this.highRiskFunctionalProbe22OriginalColor3.equals(Theme.customColor3.Q())
                || VisualSpoof.t.c()
                        != this.highRiskFunctionalProbe22OriginalDisableRenderVisual
                || VisualSpoof.o.c()
                        != this.highRiskFunctionalProbe22OriginalScreenshotBypass
                || rawInput.o() != this.highRiskFunctionalProbe22OriginalRawEnabled) {
            throw new IllegalStateException("promoted infrastructure/config state did not restore exactly");
        }

        this.highRiskFunctionalProbe22Stage = 5;
        runtimeMilestone("high-risk-functional-probe22-restore-pass:modules=3");
        runtimeMilestone("high-risk-functional-probe22-module-pass:RawInput");
        runtimeMilestone("high-risk-functional-probe22-module-pass:VisualSpoof");
        runtimeMilestone("high-risk-functional-probe22-module-pass:Theme");
        runtimeMilestone("high-risk-functional-probe22-pass:3");
    }

    private void restoreHighRiskFunctionalProbe22(RawInput rawInput) {
        if (!this.highRiskFunctionalProbe22Saved) {
            return;
        }
        try {
            if (rawInput != null) {
                if (this.c.mouseHelper instanceof SmoothMouseHelper) {
                    try {
                        rawInput.A(0L);
                    }
                    catch (Throwable ignored) {
                    }
                }
                this.setModuleEnabledRawForProbe(
                        rawInput, this.highRiskFunctionalProbe22OriginalRawEnabled);
                if (this.highRiskFunctionalProbe22OriginalRawEnabled
                        && !(this.c.mouseHelper instanceof SmoothMouseHelper)) {
                    rawInput.i(17998201765264L);
                    this.setModuleEnabledRawForProbe(rawInput, true);
                }
                if (!this.highRiskFunctionalProbe22OriginalRawEnabled
                        && this.highRiskFunctionalProbe22OriginalMouseHelper != null) {
                    this.c.mouseHelper = this.highRiskFunctionalProbe22OriginalMouseHelper;
                }
            }
            VisualSpoof.t.v(
                    this.highRiskFunctionalProbe22OriginalDisableRenderVisual, 0L);
            VisualSpoof.o.v(
                    this.highRiskFunctionalProbe22OriginalScreenshotBypass, 0L);
            Theme.theme.i(this.highRiskFunctionalProbe22OriginalTheme);
            Theme.customTheme.i(this.highRiskFunctionalProbe22OriginalCustomTheme);
            Theme.customColor1.e(this.highRiskFunctionalProbe22OriginalColor1);
            Theme.customColor2.e(this.highRiskFunctionalProbe22OriginalColor2);
            Theme.customColor3.e(this.highRiskFunctionalProbe22OriginalColor3);
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe22", "restore", restoreFailure);
        }
    }

    private void pumpHighRiskFunctionalProbe23() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe23")
                || this.highRiskFunctionalProbe23Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe22")
                && this.highRiskFunctionalProbe22Stage < 5) return;

        Abyss.module.impl.visual_utility.ClosestPlayerHUD probe =
                Modules.J(Abyss.module.impl.visual_utility.ClosestPlayerHUD.class);
        EntityOtherPlayerMP fixture = null;
        net.minecraft.scoreboard.ScorePlayerTeam team = null;
        final int fixtureId = -2147483607;
        final String fixtureName = "AbyssHUDProbe";
        final String teamName = "abyssHudProbe";

        try {
            if (probe == null
                    || ModuleManager.byClass(Abyss.module.impl.visual_utility.ClosestPlayerHUD.class) != probe
                    || ModuleManager.byName("ClosestPlayerHUD") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null) {
                throw new IllegalStateException("ClosestPlayerHUD live-world authority unavailable");
            }

            net.minecraft.scoreboard.Scoreboard scoreboard = this.c.theWorld.getScoreboard();
            net.minecraft.scoreboard.ScorePlayerTeam stale = scoreboard.getTeam(teamName);
            if (stale != null) {
                scoreboard.removeTeam(stale);
            }
            team = scoreboard.createTeam(teamName);
            team.setNamePrefix("[R]");
            scoreboard.addPlayerToTeam(fixtureName, teamName);

            fixture = new EntityOtherPlayerMP(
                    this.c.theWorld,
                    new GameProfile(
                            UUID.fromString("9e8a1d0a-7e51-4a23-9b11-2e6f9c210023"),
                            fixtureName));
            fixture.setPosition(
                    this.c.thePlayer.posX + 3.0,
                    this.c.thePlayer.posY,
                    this.c.thePlayer.posZ);
            this.c.theWorld.addEntityToWorld(fixtureId, fixture);

            probe.onPostTick((byte)0, 0L, new PostTickEvent());

            Field listField =
                    Abyss.module.impl.visual_utility.ClosestPlayerHUD.class.getDeclaredField("I");
            listField.setAccessible(true);
            List selected = (List)listField.get(probe);
            if (selected == null || selected.size() != 1) {
                throw new IllegalStateException(
                        "ClosestPlayerHUD selected list size was "
                                + (selected == null ? "<null>" : selected.size()));
            }
            Object selectedObject = selected.get(0);
            if (!(selectedObject instanceof Abyss.module.impl.visual_utility.ClosestPlayerEntry)) {
                throw new IllegalStateException(
                        "ClosestPlayerHUD selected unexpected entry "
                                + String.valueOf(selectedObject));
            }
            Abyss.module.impl.visual_utility.ClosestPlayerEntry entry =
                    (Abyss.module.impl.visual_utility.ClosestPlayerEntry)selectedObject;
            if (entry.a != fixture
                    || !"§c".equals(entry.F)
                    || entry.l != 1
                    || Math.abs(entry.D - 3.0) > 0.05) {
                throw new IllegalStateException(
                        "ClosestPlayerHUD entry mismatch entity=" + (entry.a == fixture)
                                + " prefix=" + entry.F
                                + " teamSize=" + entry.l
                                + " distance=" + entry.D);
            }
            runtimeMilestone(
                    "high-risk-functional-probe23-effect-pass:ClosestPlayerHUD:"
                            + "prefix=red:distance=" + entry.D + ":teamSize=" + entry.l);

            probe.A(0L);
            if (!((List)listField.get(probe)).isEmpty()) {
                throw new IllegalStateException(
                        "ClosestPlayerHUD disable/reset path did not clear selected entries");
            }
            runtimeMilestone(
                    "high-risk-functional-probe23-effect-pass:ClosestPlayerHUD:reset=true");

            this.highRiskFunctionalProbe23Stage = 1;
            runtimeMilestone("high-risk-functional-probe23-module-pass:ClosestPlayerHUD");
            runtimeMilestone("high-risk-functional-probe23-pass:1");
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe23Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe23:ClosestPlayerHUD",
                    "nearest-team-selection",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe23-fail:" + failure.getClass().getName());
        }
        finally {
            if (this.c.theWorld != null) {
                try {
                    this.c.theWorld.removeEntityFromWorld(fixtureId);
                }
                catch (Throwable ignored) {
                }
                try {
                    net.minecraft.scoreboard.Scoreboard scoreboard =
                            this.c.theWorld.getScoreboard();
                    net.minecraft.scoreboard.ScorePlayerTeam cleanup =
                            scoreboard.getTeam(teamName);
                    if (cleanup != null) {
                        scoreboard.removeTeam(cleanup);
                    }
                }
                catch (Throwable ignored) {
                }
            }
            if (probe != null && probe.o() && this.c.theWorld != null) {
                try {
                    probe.onPostTick((byte)0, 0L, new PostTickEvent());
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure(
                            "HighRiskFunctionalProbe23:ClosestPlayerHUD",
                            "restore-live-cache",
                            restoreFailure);
                }
            }
        }
    }

    private void pumpHighRiskFunctionalProbe24() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe24")
                || this.highRiskFunctionalProbe24Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe23")
                && this.highRiskFunctionalProbe23Stage < 1) return;

        Abyss.module.impl.visual_utility.FallIndicator probe =
                Modules.J(Abyss.module.impl.visual_utility.FallIndicator.class);
        boolean saved = false;
        double originalX = 0.0;
        double originalY = 0.0;
        double originalZ = 0.0;
        float originalYaw = 0.0f;
        float originalPitch = 0.0f;
        boolean originalOnGround = false;
        boolean originalCreative = false;
        boolean originalAllowFlying = false;
        int originalThirdPerson = 0;
        boolean originalDebug = false;
        int originalMinDamage = 0;
        boolean originalShowDistance = false;
        boolean originalOnlySneaking = false;

        try {
            if (probe == null
                    || ModuleManager.byClass(Abyss.module.impl.visual_utility.FallIndicator.class) != probe
                    || ModuleManager.byName("FallIndicator") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || Abyss.module.impl.visual_utility.FallIndicator.minDamagePercentage == null
                    || Abyss.module.impl.visual_utility.FallIndicator.showFallDistance == null
                    || Abyss.module.impl.visual_utility.FallIndicator.onlyWhileSneaking == null) {
                throw new IllegalStateException("FallIndicator live-world authority unavailable");
            }

            originalX = this.c.thePlayer.posX;
            originalY = this.c.thePlayer.posY;
            originalZ = this.c.thePlayer.posZ;
            originalYaw = this.c.thePlayer.rotationYaw;
            originalPitch = this.c.thePlayer.rotationPitch;
            originalOnGround = this.c.thePlayer.onGround;
            originalCreative = this.c.thePlayer.capabilities.isCreativeMode;
            originalAllowFlying = this.c.thePlayer.capabilities.allowFlying;
            originalThirdPerson = this.c.gameSettings.thirdPersonView;
            originalDebug = this.c.gameSettings.showDebugInfo;
            originalMinDamage =
                    Abyss.module.impl.visual_utility.FallIndicator.minDamagePercentage.k();
            originalShowDistance =
                    Abyss.module.impl.visual_utility.FallIndicator.showFallDistance.c();
            originalOnlySneaking =
                    Abyss.module.impl.visual_utility.FallIndicator.onlyWhileSneaking.c();
            saved = true;

            int baseX = net.minecraft.util.MathHelper.floor_double(originalX);
            int baseZ = net.minecraft.util.MathHelper.floor_double(originalZ);
            int startY = net.minecraft.util.MathHelper.floor_double(originalY);
            BlockPos floor = null;
            for (int y = startY; y >= Math.max(0, startY - 32); --y) {
                BlockPos candidate = new BlockPos(baseX, y, baseZ);
                if (!this.c.theWorld.isAirBlock(candidate)) {
                    floor = candidate;
                    break;
                }
            }
            if (floor == null) {
                throw new IllegalStateException("FallIndicator probe could not find floor below player");
            }

            Abyss.module.impl.visual_utility.FallIndicator.minDamagePercentage.d(0);
            Abyss.module.impl.visual_utility.FallIndicator.showFallDistance.v(true, 0L);
            Abyss.module.impl.visual_utility.FallIndicator.onlyWhileSneaking.v(false, 0L);
            this.c.gameSettings.thirdPersonView = 0;
            this.c.gameSettings.showDebugInfo = false;
            this.c.thePlayer.capabilities.isCreativeMode = false;
            this.c.thePlayer.capabilities.allowFlying = false;
            this.c.thePlayer.rotationPitch = 90.0f;
            this.c.thePlayer.setPosition(
                    originalX,
                    floor.getY() + 12.0,
                    originalZ);
            this.c.thePlayer.onGround = true;

            probe.onPostTick(new PostTickEvent(), 0L);

            Field visibleField =
                    Abyss.module.impl.visual_utility.FallIndicator.class.getDeclaredField("L");
            Field damageField =
                    Abyss.module.impl.visual_utility.FallIndicator.class.getDeclaredField("M");
            Field distanceField =
                    Abyss.module.impl.visual_utility.FallIndicator.class.getDeclaredField("O");
            Field colorField =
                    Abyss.module.impl.visual_utility.FallIndicator.class.getDeclaredField("r");
            visibleField.setAccessible(true);
            damageField.setAccessible(true);
            distanceField.setAccessible(true);
            colorField.setAccessible(true);

            boolean visible = visibleField.getBoolean(probe);
            String damage = (String)damageField.get(probe);
            String distance = (String)distanceField.get(probe);
            int color = colorField.getInt(probe);
            if (!visible || damage == null || damage.length() == 0
                    || distance == null || !distance.endsWith("m")
                    || color == -1) {
                throw new IllegalStateException(
                        "FallIndicator prediction mismatch visible=" + visible
                                + " damage=" + damage
                                + " distance=" + distance
                                + " color=" + color);
            }
            runtimeMilestone(
                    "high-risk-functional-probe24-effect-pass:FallIndicator:"
                            + "visible=true:damage=" + damage.replace(':', '_')
                            + ":distance=" + distance + ":color=" + color);

            probe.A(0L);
            if (visibleField.getBoolean(probe)
                    || damageField.get(probe) != null
                    || distanceField.get(probe) != null
                    || colorField.getInt(probe) != -1) {
                throw new IllegalStateException(
                        "FallIndicator reset path did not clear prediction state");
            }
            runtimeMilestone(
                    "high-risk-functional-probe24-effect-pass:FallIndicator:reset=true");

            this.highRiskFunctionalProbe24Stage = 1;
            runtimeMilestone("high-risk-functional-probe24-module-pass:FallIndicator");
            runtimeMilestone("high-risk-functional-probe24-pass:1");
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe24Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe24:FallIndicator",
                    "downward-raytrace-damage",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe24-fail:" + failure.getClass().getName());
        }
        finally {
            if (saved) {
                try {
                    Abyss.module.impl.visual_utility.FallIndicator.minDamagePercentage.d(
                            originalMinDamage);
                    Abyss.module.impl.visual_utility.FallIndicator.showFallDistance.v(
                            originalShowDistance, 0L);
                    Abyss.module.impl.visual_utility.FallIndicator.onlyWhileSneaking.v(
                            originalOnlySneaking, 0L);
                    this.c.gameSettings.thirdPersonView = originalThirdPerson;
                    this.c.gameSettings.showDebugInfo = originalDebug;
                    this.c.thePlayer.capabilities.isCreativeMode = originalCreative;
                    this.c.thePlayer.capabilities.allowFlying = originalAllowFlying;
                    this.c.thePlayer.rotationYaw = originalYaw;
                    this.c.thePlayer.rotationPitch = originalPitch;
                    this.c.thePlayer.setPosition(originalX, originalY, originalZ);
                    this.c.thePlayer.onGround = originalOnGround;
                    if (probe.o()) {
                        probe.onPostTick(new PostTickEvent(), 0L);
                    }
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure(
                            "HighRiskFunctionalProbe24:FallIndicator",
                            "restore",
                            restoreFailure);
                }
            }
        }
    }

    private static long inputFixTakeoverCountForProbe() throws Throwable {
        Field field =
                Abyss.ASM.Hooks.Gui.GuiScreenHooks.class.getDeclaredField(
                        "inputFixTakeoverCount");
        field.setAccessible(true);
        return field.getLong(null);
    }

    private void finishHighRiskFunctionalProbe25(InputFix probe) {
        if (!this.highRiskFunctionalProbe25OriginalEnabled) {
            if (probe.o() || probe.l() || probe.K()) {
                return;
            }
        }
        this.highRiskFunctionalProbe25Stage = 5;
        runtimeMilestone("high-risk-functional-probe25-restore-pass:InputFix:enabled="
                + this.highRiskFunctionalProbe25OriginalEnabled);
        runtimeMilestone("high-risk-functional-probe25-module-pass:InputFix");
        runtimeMilestone("high-risk-functional-probe25-pass:1");
    }

    private void pumpHighRiskFunctionalProbe25() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe25")
                || this.highRiskFunctionalProbe25Stage < 0
                || this.highRiskFunctionalProbe25Stage >= 5) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe24")
                && this.highRiskFunctionalProbe24Stage < 1) return;

        InputFix probe = Modules.J(InputFix.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(InputFix.class) != probe
                    || ModuleManager.byName("InputFix") != probe) {
                throw new IllegalStateException("InputFix live module authority unavailable");
            }

            if (this.highRiskFunctionalProbe25Stage == 0) {
                if (probe.l() || probe.K()) {
                    return;
                }
                this.highRiskFunctionalProbe25OriginalEnabled = probe.o();
                this.highRiskFunctionalProbe25Saved = true;
                if (!probe.o()) {
                    probe.I(0L, true);
                    this.highRiskFunctionalProbe25Stage = 1;
                    runtimeMilestone("high-risk-functional-probe25-request:InputFix:enable");
                    return;
                }
                this.highRiskFunctionalProbe25Stage = 2;
            }

            if (this.highRiskFunctionalProbe25Stage == 1) {
                if (probe.l() || probe.K()) {
                    return;
                }
                if (!probe.o()) {
                    throw new IllegalStateException("InputFix enable transition did not complete");
                }
                this.highRiskFunctionalProbe25Stage = 2;
            }

            if (this.highRiskFunctionalProbe25Stage == 2) {
                this.highRiskFunctionalProbe25TakeoverBaseline =
                        AbyssClient.inputFixTakeoverCountForProbe();
                this.highRiskFunctionalProbe25WaitTicks = 0;
                this.highRiskFunctionalProbe25Stage = 3;
                runtimeMilestone("high-risk-functional-probe25-ready:InputFix:"
                        + "baseline=" + this.highRiskFunctionalProbe25TakeoverBaseline);
                return;
            }

            if (this.highRiskFunctionalProbe25Stage == 3) {
                ++this.highRiskFunctionalProbe25WaitTicks;
                if (this.c.currentScreen instanceof net.minecraft.client.gui.GuiChat) {
                    String text = Abyss.internal.accessor.GuiChatAccessor.z(
                            '\u0000', '\u2876', 245891786,
                            (net.minecraft.client.gui.GuiChat)this.c.currentScreen).getText();
                    if ("OPENABYSS_INPUTFIX_7E51".equals(text)) {
                        long takeovers =
                                AbyssClient.inputFixTakeoverCountForProbe()
                                        - this.highRiskFunctionalProbe25TakeoverBaseline;
                        if (takeovers <= 0L) {
                            throw new IllegalStateException(
                                    "InputFix text arrived without ASM keyboard takeover");
                        }
                        runtimeMilestone(
                                "high-risk-functional-probe25-effect-pass:InputFix:"
                                        + "text=true:takeovers=" + takeovers);
                        if (this.highRiskFunctionalProbe25OriginalEnabled) {
                            this.finishHighRiskFunctionalProbe25(probe);
                        } else {
                            probe.I(0L, false);
                            this.highRiskFunctionalProbe25Stage = 4;
                            runtimeMilestone(
                                    "high-risk-functional-probe25-request:InputFix:disable");
                        }
                        return;
                    }
                }
                if (this.highRiskFunctionalProbe25WaitTicks > 600) {
                    throw new IllegalStateException(
                            "InputFix physical text sentinel was not observed in time");
                }
                return;
            }

            if (this.highRiskFunctionalProbe25Stage == 4) {
                if (probe.l() || probe.K()) {
                    return;
                }
                if (probe.o()) {
                    throw new IllegalStateException(
                            "InputFix original-disabled state did not restore");
                }
                this.finishHighRiskFunctionalProbe25(probe);
            }
        }
        catch (Throwable failure) {
            if (this.highRiskFunctionalProbe25Saved && probe != null) {
                try {
                    this.setModuleEnabledRawForProbe(
                            probe, this.highRiskFunctionalProbe25OriginalEnabled);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure(
                            "HighRiskFunctionalProbe25:InputFix",
                            "restore",
                            restoreFailure);
                }
            }
            this.highRiskFunctionalProbe25Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe25:InputFix",
                    "physical-keyboard",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe25-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe26() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe26")
                || this.highRiskFunctionalProbe26Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe25")
                && this.highRiskFunctionalProbe25Stage < 5) return;

        Abyss.module.impl.visual_utility.FKCounter probe =
                Modules.J(Abyss.module.impl.visual_utility.FKCounter.class);
        Field jField = null;
        Field dField = null;
        Field nField = null;
        Field gField = null;
        Field yField = null;
        Field bField = null;
        Field eField = null;
        Field widthField = null;
        String[] savedJ = null;
        int[] savedD = null;
        List<Map<String, Integer>> savedN = null;
        Map<String, Integer> savedG = null;
        java.util.Set<String> savedY = null;
        String savedB = null;
        List savedE = null;
        int savedWidth = 0;
        boolean saved = false;

        try {
            if (probe == null
                    || ModuleManager.byClass(Abyss.module.impl.visual_utility.FKCounter.class) != probe
                    || ModuleManager.byName("FKCounter") != probe) {
                throw new IllegalStateException("FKCounter live module authority unavailable");
            }

            Class<?> type = Abyss.module.impl.visual_utility.FKCounter.class;
            jField = type.getDeclaredField("J");
            dField = type.getDeclaredField("D");
            nField = type.getDeclaredField("N");
            gField = type.getDeclaredField("g");
            yField = type.getDeclaredField("y");
            bField = type.getDeclaredField("B");
            eField = type.getDeclaredField("e");
            widthField = type.getDeclaredField("n");
            for (Field field : new Field[]{
                    jField, dField, nField, gField, yField, bField, eField, widthField}) {
                field.setAccessible(true);
            }

            String[] teams = (String[])jField.get(probe);
            int[] totals = (int[])dField.get(probe);
            List<Map<String, Integer>> pending =
                    (List<Map<String, Integer>>)nField.get(probe);
            Map<String, Integer> global =
                    (Map<String, Integer>)gField.get(probe);
            java.util.Set<String> finalized =
                    (java.util.Set<String>)yField.get(probe);
            List entries = (List)eField.get(probe);

            savedJ = Arrays.copyOf(teams, teams.length);
            savedD = Arrays.copyOf(totals, totals.length);
            savedN = new ArrayList<Map<String, Integer>>(pending.size());
            for (Map<String, Integer> map : pending) {
                savedN.add(new HashMap<String, Integer>(map));
            }
            savedG = new HashMap<String, Integer>(global);
            savedY = new java.util.HashSet<String>(finalized);
            savedB = (String)bField.get(probe);
            savedE = new ArrayList(entries);
            savedWidth = widthField.getInt(probe);
            saved = true;

            String[] fixtureTeams = new String[]{"c", "a", "e", "9"};
            System.arraycopy(fixtureTeams, 0, teams, 0, 4);
            Arrays.fill(totals, 0);
            global.clear();
            finalized.clear();
            entries.clear();
            for (Map<String, Integer> map : pending) {
                map.clear();
            }

            Method mergeMethod = type.getDeclaredMethod("merge", String.class, String.class);
            mergeMethod.setAccessible(true);
            Method removeMethod = type.getDeclaredMethod("i", String.class, String.class);
            removeMethod.setAccessible(true);
            Method rebuildMethod = type.getDeclaredMethod("v", long.class);
            rebuildMethod.setAccessible(true);

            final String victim = "AbyssFKProbe";
            mergeMethod.invoke(probe, victim, "c");
            if (totals[0] != 1
                    || !Integer.valueOf(1).equals(pending.get(0).get(victim))
                    || !Integer.valueOf(1).equals(global.get(victim))
                    || finalized.contains(victim)) {
                throw new IllegalStateException(
                        "FKCounter merge state mismatch total=" + totals[0]
                                + " pending=" + pending.get(0).get(victim)
                                + " global=" + global.get(victim)
                                + " finalized=" + finalized.contains(victim));
            }
            rebuildMethod.invoke(probe, 0L);
            String rendered = (String)bField.get(probe);
            if (rendered == null || rendered.length() == 0 || !rendered.contains("1")) {
                throw new IllegalStateException(
                        "FKCounter rendered state did not expose incremented count: "
                                + rendered);
            }
            runtimeMilestone(
                    "high-risk-functional-probe26-effect-pass:FKCounter:merge=1");

            int removed = ((Integer)removeMethod.invoke(probe, victim, "c")).intValue();
            if (removed != 1
                    || totals[0] != 0
                    || pending.get(0).containsKey(victim)
                    || global.containsKey(victim)
                    || !finalized.contains(victim)) {
                throw new IllegalStateException(
                        "FKCounter final-kill state mismatch removed=" + removed
                                + " total=" + totals[0]
                                + " pending=" + pending.get(0).containsKey(victim)
                                + " global=" + global.containsKey(victim)
                                + " finalized=" + finalized.contains(victim));
            }
            rebuildMethod.invoke(probe, 0L);
            runtimeMilestone(
                    "high-risk-functional-probe26-effect-pass:FKCounter:finalize=1");

            this.highRiskFunctionalProbe26Stage = 1;
            runtimeMilestone("high-risk-functional-probe26-module-pass:FKCounter");
            runtimeMilestone("high-risk-functional-probe26-pass:1");
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe26Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe26:FKCounter",
                    "kill-accounting-core",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe26-fail:" + failure.getClass().getName());
        }
        finally {
            if (saved && probe != null) {
                try {
                    String[] teams = (String[])jField.get(probe);
                    int[] totals = (int[])dField.get(probe);
                    List<Map<String, Integer>> pending =
                            (List<Map<String, Integer>>)nField.get(probe);
                    Map<String, Integer> global =
                            (Map<String, Integer>)gField.get(probe);
                    java.util.Set<String> finalized =
                            (java.util.Set<String>)yField.get(probe);
                    List entries = (List)eField.get(probe);

                    System.arraycopy(savedJ, 0, teams, 0, savedJ.length);
                    System.arraycopy(savedD, 0, totals, 0, savedD.length);
                    global.clear();
                    global.putAll(savedG);
                    finalized.clear();
                    finalized.addAll(savedY);
                    for (int i = 0; i < pending.size(); ++i) {
                        pending.get(i).clear();
                        pending.get(i).putAll(savedN.get(i));
                    }
                    entries.clear();
                    entries.addAll(savedE);
                    bField.set(probe, savedB);
                    widthField.setInt(probe, savedWidth);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure(
                            "HighRiskFunctionalProbe26:FKCounter",
                            "restore",
                            restoreFailure);
                }
            }
        }
    }

    private void pumpHighRiskFunctionalProbe27() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe27")
                || this.highRiskFunctionalProbe27Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe26")
                && this.highRiskFunctionalProbe26Stage < 1) return;

        Abyss.module.impl.combat.FakeLag probe =
                Modules.J(Abyss.module.impl.combat.FakeLag.class);
        Field targetSetField = null;
        Field intervalField = null;
        Field durationField = null;
        Field intervalTimerField = null;
        Field durationTimerField = null;
        Field bufferingFlagField = null;
        Field activeFlagField = null;
        Field flushFlagField = null;
        java.util.Set savedTargets = null;
        long savedInterval = 0L;
        long savedDuration = 0L;
        long savedIntervalTimer = 0L;
        long savedDurationTimer = 0L;
        boolean savedBufferingFlag = false;
        boolean savedActiveFlag = false;
        boolean savedFlushFlag = false;
        boolean savedSwordOnly = false;
        float savedAllowedTargets = 0.0f;
        boolean saved = false;

        try {
            if (probe == null
                    || ModuleManager.byClass(Abyss.module.impl.combat.FakeLag.class) != probe
                    || ModuleManager.byName("FakeLag") != probe
                    || this.c.theWorld == null
                    || PacketManager.e()
                    || !PacketManager.u.isEmpty()) {
                throw new IllegalStateException(
                        "FakeLag probe requires live world and clean packet buffer");
            }

            Class<?> type = Abyss.module.impl.combat.FakeLag.class;
            targetSetField = type.getDeclaredField("m");
            intervalField = type.getDeclaredField("b");
            durationField = type.getDeclaredField("k");
            intervalTimerField = type.getDeclaredField("s");
            durationTimerField = type.getDeclaredField("M");
            bufferingFlagField = type.getDeclaredField("K");
            activeFlagField = type.getDeclaredField("G");
            flushFlagField = type.getDeclaredField("t");
            for (Field field : new Field[]{
                    targetSetField, intervalField, durationField,
                    intervalTimerField, durationTimerField,
                    bufferingFlagField, activeFlagField, flushFlagField}) {
                field.setAccessible(true);
            }

            java.util.Set targets = (java.util.Set)targetSetField.get(probe);
            Abyss.util.TimerUtil intervalTimer =
                    (Abyss.util.TimerUtil)intervalTimerField.get(probe);
            Abyss.util.TimerUtil durationTimer =
                    (Abyss.util.TimerUtil)durationTimerField.get(probe);

            savedTargets = new java.util.HashSet(targets);
            savedInterval = intervalField.getLong(probe);
            savedDuration = durationField.getLong(probe);
            savedIntervalTimer = intervalTimer.I;
            savedDurationTimer = durationTimer.I;
            savedBufferingFlag = bufferingFlagField.getBoolean(null);
            savedActiveFlag = activeFlagField.getBoolean(null);
            savedFlushFlag = flushFlagField.getBoolean(null);
            savedSwordOnly = Abyss.module.impl.combat.FakeLag.swordOnly.c();
            savedAllowedTargets =
                    Abyss.module.impl.combat.FakeLag.allowedTargetsAmount.L();
            saved = true;

            if (savedBufferingFlag || savedActiveFlag || savedFlushFlag) {
                throw new IllegalStateException(
                        "FakeLag static state was not clean before probe K="
                                + savedBufferingFlag + " G=" + savedActiveFlag
                                + " t=" + savedFlushFlag);
            }

            EntityZombie fixture = new EntityZombie(this.c.theWorld);
            fixture.setPosition(
                    this.c.thePlayer.posX + 4.0,
                    this.c.thePlayer.posY,
                    this.c.thePlayer.posZ);

            targets.clear();
            targets.add(fixture);
            Abyss.module.impl.combat.FakeLag.swordOnly.v(false, 0L);
            Abyss.module.impl.combat.FakeLag.allowedTargetsAmount.o(
                    (byte)0, 0L, 3.0f);
            intervalField.setLong(probe, 0L);
            durationField.setLong(probe, 60000L);
            intervalTimer.F(0L);
            durationTimer.W();

            probe.onRender2D(0L, null);

            boolean active = activeFlagField.getBoolean(null);
            boolean buffering = bufferingFlagField.getBoolean(null);
            boolean flushArmed = flushFlagField.getBoolean(null);
            if (!PacketManager.e() || !active || !buffering || !flushArmed) {
                throw new IllegalStateException(
                        "FakeLag activation mismatch packetBuffer=" + PacketManager.e()
                                + " K=" + buffering + " G=" + active
                                + " t=" + flushArmed);
            }
            runtimeMilestone(
                    "high-risk-functional-probe27-effect-pass:FakeLag:"
                            + "buffering=true:flags=true");

            probe.onSendPacket(
                    0L,
                    new Abyss.event.events.SendPacketEvent(
                            new net.minecraft.network.play.client.C02PacketUseEntity(
                                    fixture,
                                    net.minecraft.network.play.client.C02PacketUseEntity.Action.ATTACK)));

            if (PacketManager.e()
                    || !PacketManager.u.isEmpty()
                    || bufferingFlagField.getBoolean(null)
                    || activeFlagField.getBoolean(null)
                    || flushFlagField.getBoolean(null)) {
                throw new IllegalStateException(
                        "FakeLag attack reset mismatch packetBuffer=" + PacketManager.e()
                                + " queued=" + PacketManager.u.size()
                                + " K=" + bufferingFlagField.getBoolean(null)
                                + " G=" + activeFlagField.getBoolean(null)
                                + " t=" + flushFlagField.getBoolean(null));
            }
            runtimeMilestone(
                    "high-risk-functional-probe27-effect-pass:FakeLag:"
                            + "attackFlush=true");

            this.highRiskFunctionalProbe27Stage = 1;
            runtimeMilestone("high-risk-functional-probe27-module-pass:FakeLag");
            runtimeMilestone("high-risk-functional-probe27-pass:1");
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe27Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe27:FakeLag",
                    "packet-buffer-state-machine",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe27-fail:"
                            + failure.getClass().getName());
        }
        finally {
            if (PacketManager.e() || !PacketManager.u.isEmpty()) {
                try {
                    PacketManager.j();
                    PacketManager.M(false);
                }
                catch (Throwable ignored) {
                }
            }
            if (saved && probe != null) {
                try {
                    java.util.Set targets =
                            (java.util.Set)targetSetField.get(probe);
                    targets.clear();
                    targets.addAll(savedTargets);
                    intervalField.setLong(probe, savedInterval);
                    durationField.setLong(probe, savedDuration);
                    ((Abyss.util.TimerUtil)intervalTimerField.get(probe)).F(
                            savedIntervalTimer);
                    ((Abyss.util.TimerUtil)durationTimerField.get(probe)).F(
                            savedDurationTimer);
                    bufferingFlagField.setBoolean(null, savedBufferingFlag);
                    activeFlagField.setBoolean(null, savedActiveFlag);
                    flushFlagField.setBoolean(null, savedFlushFlag);
                    Abyss.module.impl.combat.FakeLag.swordOnly.v(
                            savedSwordOnly, 0L);
                    Abyss.module.impl.combat.FakeLag.allowedTargetsAmount.o(
                            (byte)0, 0L, savedAllowedTargets);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure(
                            "HighRiskFunctionalProbe27:FakeLag",
                            "restore",
                            restoreFailure);
                }
            }
        }
    }

    private void pumpHighRiskFunctionalProbe28() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe28")
                || this.highRiskFunctionalProbe28Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe27")
                && this.highRiskFunctionalProbe27Stage < 1) return;

        KillAura probe = Modules.J(KillAura.class);
        final int nearId = -2147483501;
        final int farId = -2147483502;
        EntityZombie near = null;
        EntityZombie far = null;
        Method selector = null;
        Field indexField = null;
        Field switchTimerField = null;

        String savedMode = null;
        String savedSort = null;
        float savedAttackRange = 0.0f;
        float savedSwingRange = 0.0f;
        float savedFov = 0.0f;
        float savedSwitchDelay = 0.0f;
        boolean savedThroughWall = false;
        boolean savedPlayers = false;
        boolean savedMobs = false;
        boolean savedAnimals = false;
        boolean savedBosses = false;
        boolean savedFriends = false;
        boolean savedEnemies = false;
        boolean savedTeammates = false;
        boolean savedBots = false;
        boolean savedSilverfishes = false;
        boolean savedGolems = false;
        int savedIndex = 0;
        long savedSwitchTimer = 0L;
        boolean savedSwitchAdvance = false;
        net.minecraft.entity.EntityLivingBase savedTarget = null;
        boolean saved = false;

        try {
            if (probe == null
                    || ModuleManager.byClass(KillAura.class) != probe
                    || ModuleManager.byName("KillAura") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || KillAura.mode == null
                    || KillAura.sort == null
                    || KillAura.attackRange == null
                    || KillAura.swingRange == null
                    || KillAura.fov == null
                    || KillAura.switchDelay == null
                    || KillAura.throughWall == null
                    || KillAura.players == null
                    || KillAura.mobs == null
                    || KillAura.animals == null
                    || KillAura.bosses == null
                    || KillAura.friends == null
                    || KillAura.enemies == null
                    || KillAura.teammates == null
                    || KillAura.bots == null
                    || KillAura.silverfishes == null
                    || KillAura.golems == null) {
                throw new IllegalStateException(
                        "KillAura target-selector authority unavailable");
            }
            if (this.c.theWorld.getEntityByID(nearId) != null
                    || this.c.theWorld.getEntityByID(farId) != null) {
                throw new IllegalStateException(
                        "KillAura fixture entity ids are already occupied");
            }

            selector = KillAura.class.getDeclaredMethod("b", Long.TYPE);
            selector.setAccessible(true);
            indexField = KillAura.class.getDeclaredField("m");
            indexField.setAccessible(true);
            switchTimerField = KillAura.class.getDeclaredField("C");
            switchTimerField.setAccessible(true);

            savedMode = KillAura.mode.Y();
            savedSort = KillAura.sort.Y();
            savedAttackRange = KillAura.attackRange.L();
            savedSwingRange = KillAura.swingRange.L();
            savedFov = KillAura.fov.L();
            savedSwitchDelay = KillAura.switchDelay.L();
            savedThroughWall = KillAura.throughWall.c();
            savedPlayers = KillAura.players.c();
            savedMobs = KillAura.mobs.c();
            savedAnimals = KillAura.animals.c();
            savedBosses = KillAura.bosses.c();
            savedFriends = KillAura.friends.c();
            savedEnemies = KillAura.enemies.c();
            savedTeammates = KillAura.teammates.c();
            savedBots = KillAura.bots.c();
            savedSilverfishes = KillAura.silverfishes.c();
            savedGolems = KillAura.golems.c();
            savedIndex = indexField.getInt(probe);
            savedSwitchTimer = switchTimerField.getLong(probe);
            savedSwitchAdvance = KillAura.x;
            savedTarget = KillAura.H6;
            saved = true;

            KillAura.players.v(false, 0L);
            KillAura.mobs.v(true, 0L);
            KillAura.animals.v(false, 0L);
            KillAura.bosses.v(false, 0L);
            KillAura.friends.v(false, 0L);
            KillAura.enemies.v(false, 0L);
            KillAura.teammates.v(false, 0L);
            KillAura.bots.v(false, 0L);
            KillAura.silverfishes.v(false, 0L);
            KillAura.golems.v(false, 0L);
            KillAura.throughWall.v(true, 0L);
            KillAura.attackRange.o((byte)0, 0L, 8.0f);
            KillAura.swingRange.o((byte)0, 0L, 8.0f);
            KillAura.fov.o((byte)0, 0L, 360.0f);
            KillAura.switchDelay.o((byte)0, 0L, 0.0f);

            near = new EntityZombie(this.c.theWorld);
            near.setHealth(18.0f);
            near.setPosition(
                    this.c.thePlayer.posX + 2.0,
                    this.c.thePlayer.posY,
                    this.c.thePlayer.posZ);
            far = new EntityZombie(this.c.theWorld);
            far.setHealth(4.0f);
            far.setPosition(
                    this.c.thePlayer.posX + 5.0,
                    this.c.thePlayer.posY,
                    this.c.thePlayer.posZ);
            this.c.theWorld.addEntityToWorld(nearId, near);
            this.c.theWorld.addEntityToWorld(farId, far);

            KillAura.mode.i("SINGLE");
            KillAura.sort.i("DISTANCE");
            Object distanceSelected = selector.invoke(probe, 0L);
            if (distanceSelected != near) {
                throw new IllegalStateException(
                        "KillAura DISTANCE selector did not choose near fixture: "
                                + String.valueOf(distanceSelected));
            }
            runtimeMilestone(
                    "high-risk-functional-probe28-effect-pass:KillAura:"
                            + "distance=near");

            KillAura.sort.i("HEALTH");
            Object healthSelected = selector.invoke(probe, 0L);
            if (healthSelected != far) {
                throw new IllegalStateException(
                        "KillAura HEALTH selector did not choose low-health fixture: "
                                + String.valueOf(healthSelected));
            }
            runtimeMilestone(
                    "high-risk-functional-probe28-effect-pass:KillAura:"
                            + "health=low");

            KillAura.mode.i("SWITCH");
            KillAura.sort.i("DISTANCE");
            indexField.setInt(probe, 0);
            switchTimerField.setLong(probe, 0L);
            KillAura.x = true;
            Object switched = selector.invoke(probe, 0L);
            if (switched != far || indexField.getInt(probe) != 1) {
                throw new IllegalStateException(
                        "KillAura SWITCH selector did not advance to second fixture"
                                + " selected=" + String.valueOf(switched)
                                + " index=" + indexField.getInt(probe));
            }
            runtimeMilestone(
                    "high-risk-functional-probe28-effect-pass:KillAura:"
                            + "switch=second");

            this.highRiskFunctionalProbe28Stage = 1;
            runtimeMilestone(
                    "high-risk-functional-probe28-module-pass:KillAura");
            runtimeMilestone("high-risk-functional-probe28-pass:1");
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe28Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe28:KillAura",
                    "target-selector",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe28-fail:"
                            + failure.getClass().getName());
        }
        finally {
            if (this.c.theWorld != null) {
                try {
                    this.c.theWorld.removeEntityFromWorld(nearId);
                    this.c.theWorld.removeEntityFromWorld(farId);
                }
                catch (Throwable ignored) {
                }
            }
            if (saved && probe != null) {
                try {
                    KillAura.mode.i(savedMode);
                    KillAura.sort.i(savedSort);
                    KillAura.attackRange.o((byte)0, 0L, savedAttackRange);
                    KillAura.swingRange.o((byte)0, 0L, savedSwingRange);
                    KillAura.fov.o((byte)0, 0L, savedFov);
                    KillAura.switchDelay.o((byte)0, 0L, savedSwitchDelay);
                    KillAura.throughWall.v(savedThroughWall, 0L);
                    KillAura.players.v(savedPlayers, 0L);
                    KillAura.mobs.v(savedMobs, 0L);
                    KillAura.animals.v(savedAnimals, 0L);
                    KillAura.bosses.v(savedBosses, 0L);
                    KillAura.friends.v(savedFriends, 0L);
                    KillAura.enemies.v(savedEnemies, 0L);
                    KillAura.teammates.v(savedTeammates, 0L);
                    KillAura.bots.v(savedBots, 0L);
                    KillAura.silverfishes.v(savedSilverfishes, 0L);
                    KillAura.golems.v(savedGolems, 0L);
                    indexField.setInt(probe, savedIndex);
                    switchTimerField.setLong(probe, savedSwitchTimer);
                    KillAura.x = savedSwitchAdvance;
                    KillAura.H6 = savedTarget;
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure(
                            "HighRiskFunctionalProbe28:KillAura",
                            "restore",
                            restoreFailure);
                }
            }
        }
    }

    private void pumpHighRiskFunctionalProbe29() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe29")
                || this.highRiskFunctionalProbe29Stage < 0
                || this.highRiskFunctionalProbe29Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe28")
                && this.highRiskFunctionalProbe28Stage < 1) return;

        AutoBlock probe = Modules.J(AutoBlock.class);
        final int fixtureId = -2147483503;

        try {
            if (probe == null
                    || ModuleManager.byClass(AutoBlock.class) != probe
                    || ModuleManager.byName("AutoBlock") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null) {
                throw new IllegalStateException(
                        "AutoBlock readiness authority unavailable");
            }

            if (this.highRiskFunctionalProbe29Stage == 0) {
                if (probe.l() || probe.K()) {
                    return;
                }

                this.highRiskFunctionalProbe29OriginalEnabled = probe.o();
                this.highRiskFunctionalProbe29Saved = true;

                if (probe.o()) {
                    probe.I(0L, false);
                    this.highRiskFunctionalProbe29Stage = 1;
                    this.highRiskFunctionalProbe29WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe29-request:AutoBlock:disable");
                    return;
                }
                this.highRiskFunctionalProbe29Stage = 1;
            }

            if (this.highRiskFunctionalProbe29Stage == 1) {
                if (probe.l() || probe.K() || probe.o() || probe.P()
                        || w.isOwnerActive(probe)) {
                    if (++this.highRiskFunctionalProbe29WaitTicks > 160) {
                        throw new IllegalStateException(
                                "AutoBlock did not settle disabled");
                    }
                    return;
                }

                int disabled = AutoBlock.t(81424435728200L);
                if (disabled != 0) {
                    throw new IllegalStateException(
                            "AutoBlock disabled readiness was " + disabled);
                }
                runtimeMilestone(
                        "high-risk-functional-probe29-effect-pass:AutoBlock:"
                                + "disabled=0");

                probe.I(0L, true);
                this.highRiskFunctionalProbe29Stage = 2;
                this.highRiskFunctionalProbe29WaitTicks = 0;
                runtimeMilestone(
                        "high-risk-functional-probe29-request:AutoBlock:enable");
                return;
            }

            if (this.highRiskFunctionalProbe29Stage == 2) {
                if (probe.l() || probe.K() || !probe.o() || !probe.P()
                        || !w.isOwnerActive(probe)) {
                    if (++this.highRiskFunctionalProbe29WaitTicks > 160) {
                        throw new IllegalStateException(
                                "AutoBlock did not settle enabled");
                    }
                    return;
                }

                Field smartCounter = AutoBlock.class.getDeclaredField("I");
                smartCounter.setAccessible(true);

                String originalMode = AutoBlock.mode.Y();
                boolean originalRequireKillAura = AutoBlock.requireKillAura.c();
                boolean originalRequireRightClick = AutoBlock.requireRightClick.c();
                boolean originalPlayers = AutoBlock.players.c();
                boolean originalMobs = AutoBlock.mobs.c();
                boolean originalAnimals = AutoBlock.animals.c();
                boolean originalBosses = AutoBlock.bosses.c();
                boolean originalFriends = AutoBlock.friends.c();
                boolean originalEnemies = AutoBlock.enemies.c();
                boolean originalTeammates = AutoBlock.teammates.c();
                boolean originalBots = AutoBlock.bots.c();
                boolean originalSilverfishes = AutoBlock.silverfishes.c();
                boolean originalGolems = AutoBlock.golems.c();
                float originalTargetRange = AutoBlock.targetRange.L();
                float originalFov = AutoBlock.fov.L();
                int originalSmartCounter = smartCounter.getInt(null);
                int slot = this.c.thePlayer.inventory.currentItem;
                ItemStack originalStack =
                        this.c.thePlayer.inventory.getStackInSlot(slot);
                EntityZombie fixture = null;

                try {
                    if (this.c.theWorld.getEntityByID(fixtureId) != null) {
                        throw new IllegalStateException(
                                "AutoBlock fixture entity id already occupied");
                    }

                    AutoBlock.mode.i("VANILLA");
                    AutoBlock.requireKillAura.v(false, 0L);
                    AutoBlock.requireRightClick.v(false, 0L);
                    AutoBlock.players.v(false, 0L);
                    AutoBlock.mobs.v(true, 0L);
                    AutoBlock.animals.v(false, 0L);
                    AutoBlock.bosses.v(false, 0L);
                    AutoBlock.friends.v(false, 0L);
                    AutoBlock.enemies.v(false, 0L);
                    AutoBlock.teammates.v(false, 0L);
                    AutoBlock.bots.v(false, 0L);
                    AutoBlock.silverfishes.v(false, 0L);
                    AutoBlock.golems.v(false, 0L);
                    AutoBlock.targetRange.o((byte)0, 0L, 8.0f);
                    AutoBlock.fov.o((byte)0, 0L, 360.0f);

                    this.c.thePlayer.inventory.setInventorySlotContents(
                            slot, new ItemStack(Items.diamond_sword));

                    fixture = new EntityZombie(this.c.theWorld);
                    fixture.setPosition(
                            this.c.thePlayer.posX + 2.0,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ);
                    this.c.theWorld.addEntityToWorld(fixtureId, fixture);

                    smartCounter.setInt(null, 0);
                    int ready = AutoBlock.t(81424435728200L);
                    if (ready != 1) {
                        throw new IllegalStateException(
                                "AutoBlock ready state mismatch expected=1 actual="
                                        + ready);
                    }
                    runtimeMilestone(
                            "high-risk-functional-probe29-effect-pass:AutoBlock:"
                                    + "ready=1");

                    smartCounter.setInt(null, 3);
                    int smart = AutoBlock.t(81424435728200L);
                    if (smart != 2) {
                        throw new IllegalStateException(
                                "AutoBlock smart-unblock state mismatch expected=2 actual="
                                        + smart);
                    }
                    runtimeMilestone(
                            "high-risk-functional-probe29-effect-pass:AutoBlock:"
                                    + "smartUnblock=2");

                    AutoBlock.mode.i("NONE");
                    int none = AutoBlock.t(81424435728200L);
                    if (none != 0) {
                        throw new IllegalStateException(
                                "AutoBlock NONE mode readiness mismatch expected=0 actual="
                                        + none);
                    }
                    runtimeMilestone(
                            "high-risk-functional-probe29-effect-pass:AutoBlock:"
                                    + "modeNone=0");
                }
                finally {
                    try {
                        this.c.theWorld.removeEntityFromWorld(fixtureId);
                    }
                    catch (Throwable ignored) {
                    }
                    this.c.thePlayer.inventory.setInventorySlotContents(
                            slot, originalStack);
                    AutoBlock.mode.i(originalMode);
                    AutoBlock.requireKillAura.v(originalRequireKillAura, 0L);
                    AutoBlock.requireRightClick.v(originalRequireRightClick, 0L);
                    AutoBlock.players.v(originalPlayers, 0L);
                    AutoBlock.mobs.v(originalMobs, 0L);
                    AutoBlock.animals.v(originalAnimals, 0L);
                    AutoBlock.bosses.v(originalBosses, 0L);
                    AutoBlock.friends.v(originalFriends, 0L);
                    AutoBlock.enemies.v(originalEnemies, 0L);
                    AutoBlock.teammates.v(originalTeammates, 0L);
                    AutoBlock.bots.v(originalBots, 0L);
                    AutoBlock.silverfishes.v(originalSilverfishes, 0L);
                    AutoBlock.golems.v(originalGolems, 0L);
                    AutoBlock.targetRange.o(
                            (byte)0, 0L, originalTargetRange);
                    AutoBlock.fov.o((byte)0, 0L, originalFov);
                    smartCounter.setInt(null, originalSmartCounter);
                }

                if (!this.highRiskFunctionalProbe29OriginalEnabled) {
                    probe.I(0L, false);
                }
                this.highRiskFunctionalProbe29Stage = 3;
                this.highRiskFunctionalProbe29WaitTicks = 0;
                runtimeMilestone(
                        "high-risk-functional-probe29-request:AutoBlock:restore:"
                                + this.highRiskFunctionalProbe29OriginalEnabled);
                return;
            }

            boolean restored = this.highRiskFunctionalProbe29OriginalEnabled
                    ? probe.o() && !probe.l() && !probe.K() && probe.P()
                            && w.isOwnerActive(probe)
                    : !probe.o() && !probe.l() && !probe.K() && !probe.P()
                            && !w.isOwnerActive(probe);
            if (!restored) {
                if (++this.highRiskFunctionalProbe29WaitTicks > 160) {
                    throw new IllegalStateException(
                            "AutoBlock enabled/subscriber state did not restore");
                }
                return;
            }

            this.highRiskFunctionalProbe29Stage = 4;
            runtimeMilestone(
                    "high-risk-functional-probe29-restore-pass:AutoBlock:"
                            + "enabled="
                            + this.highRiskFunctionalProbe29OriginalEnabled);
            runtimeMilestone(
                    "high-risk-functional-probe29-module-pass:AutoBlock");
            runtimeMilestone("high-risk-functional-probe29-pass:1");
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe29Stage = -1;
            if (this.highRiskFunctionalProbe29Saved && probe != null) {
                try {
                    if (probe.o()
                            != this.highRiskFunctionalProbe29OriginalEnabled) {
                        probe.I(
                                0L,
                                this.highRiskFunctionalProbe29OriginalEnabled);
                    }
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure(
                            "HighRiskFunctionalProbe29:AutoBlock",
                            "restore-enabled-state",
                            restoreFailure);
                }
            }
            recordFeatureFailure(
                    "HighRiskFunctionalProbe29:AutoBlock",
                    "readiness-state-machine",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe29-fail:"
                            + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe30() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe30")
                || this.highRiskFunctionalProbe30Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe29")
                && this.highRiskFunctionalProbe29Stage < 4) return;

        Scaffold probe = Modules.J(Scaffold.class);
        BlockPos target = null;
        BlockPos support = null;
        IBlockState targetOriginal = null;
        IBlockState supportOriginal = null;
        String savedMode = null;
        String savedRotation = null;
        boolean savedStrictAim = false;
        float savedOffset = 0.0f;
        boolean saved = false;

        try {
            if (probe == null
                    || ModuleManager.byClass(Scaffold.class) != probe
                    || ModuleManager.byName("Scaffold") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || Scaffold.mode == null
                    || Scaffold.normalModeRotation == null
                    || Scaffold.strictAimCheck == null
                    || Scaffold.offsetRotationOffset == null) {
                throw new IllegalStateException(
                        "Scaffold placement authority unavailable");
            }

            int baseX = MathHelper.floor_double(this.c.thePlayer.posX);
            int baseY = MathHelper.floor_double(this.c.thePlayer.posY);
            int baseZ = MathHelper.floor_double(this.c.thePlayer.posZ);

            outer:
            for (int dy = 4; dy <= 7; ++dy) {
                for (int dx = 3; dx <= 7; ++dx) {
                    for (int dz = -3; dz <= 3; ++dz) {
                        BlockPos candidate =
                                new BlockPos(baseX + dx, baseY + dy, baseZ + dz);
                        boolean clear = true;
                        for (int ox = -2; ox <= 2 && clear; ++ox) {
                            for (int oz = -2; oz <= 2; ++oz) {
                                if (!this.c.theWorld.isAirBlock(
                                        candidate.add(ox, 0, oz))) {
                                    clear = false;
                                    break;
                                }
                            }
                        }
                        if (!clear) continue;
                        target = candidate;
                        support = candidate.east();
                        break outer;
                    }
                }
            }
            if (target == null || support == null) {
                throw new IllegalStateException(
                        "Scaffold probe found no clear placement fixture");
            }

            targetOriginal = this.c.theWorld.getBlockState(target);
            supportOriginal = this.c.theWorld.getBlockState(support);
            savedMode = Scaffold.mode.Y();
            savedRotation = Scaffold.normalModeRotation.Y();
            savedStrictAim = Scaffold.strictAimCheck.c();
            savedOffset = Scaffold.offsetRotationOffset.L();
            saved = true;

            Scaffold.mode.i("NORMAL");
            Scaffold.normalModeRotation.i("OFFSET");
            Scaffold.strictAimCheck.v(false, 0L);
            Scaffold.offsetRotationOffset.o((byte)0, 0L, 0.15f);

            if (!this.c.theWorld.setBlockState(
                    support, Blocks.stone.getDefaultState(), 3)) {
                throw new IllegalStateException(
                        "Scaffold probe could not place support block");
            }

            PlacementTarget placement =
                    BlockUtil.x((double)target.getY(), null, false);
            if (placement == null) {
                throw new IllegalStateException(
                        "Scaffold BlockUtil.x returned null");
            }
            if (!BlockUtil.p(placement.q, support)
                    || placement.Z != EnumFacing.WEST
                    || placement.o) {
                throw new IllegalStateException(
                        "Scaffold placement target mismatch q="
                                + placement.q
                                + " expected=" + support
                                + " face=" + placement.Z
                                + " expanded=" + placement.o);
            }
            runtimeMilestone(
                    "high-risk-functional-probe30-effect-pass:Scaffold:"
                            + "candidate=support-west");

            Method rotationMethod = Scaffold.class.getDeclaredMethod(
                    "J", BlockPos.class, Long.TYPE, EnumFacing.class);
            rotationMethod.setAccessible(true);
            float[] actual = (float[])rotationMethod.invoke(
                    probe, placement.q, 0L, placement.Z);
            Vec3 hit = RotationUtil.h(
                    placement.q, placement.Z, Scaffold.offsetRotationOffset.L());
            float[] expected = RotationUtil.L(hit);

            if (actual == null || actual.length < 2
                    || Float.isNaN(actual[0]) || Float.isInfinite(actual[0])
                    || Float.isNaN(actual[1]) || Float.isInfinite(actual[1])) {
                throw new IllegalStateException(
                        "Scaffold rotation result was invalid");
            }
            float yawDelta = Math.abs(MathUtil.M(actual[0], expected[0]));
            float pitchDelta = Math.abs(actual[1] - expected[1]);
            if (yawDelta > 0.01f || pitchDelta > 0.01f) {
                throw new IllegalStateException(
                        "Scaffold OFFSET rotation mismatch yawDelta="
                                + yawDelta + " pitchDelta=" + pitchDelta);
            }
            runtimeMilestone(
                    "high-risk-functional-probe30-effect-pass:Scaffold:"
                            + "rotation=offset");

            this.highRiskFunctionalProbe30Stage = 1;
            runtimeMilestone(
                    "high-risk-functional-probe30-module-pass:Scaffold");
            runtimeMilestone("high-risk-functional-probe30-pass:1");
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe30Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe30:Scaffold",
                    "placement-candidate-rotation",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe30-fail:"
                            + failure.getClass().getName());
        }
        finally {
            if (this.c.theWorld != null) {
                try {
                    if (support != null && supportOriginal != null) {
                        this.c.theWorld.setBlockState(
                                support, supportOriginal, 3);
                    }
                    if (target != null && targetOriginal != null) {
                        this.c.theWorld.setBlockState(
                                target, targetOriginal, 3);
                    }
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure(
                            "HighRiskFunctionalProbe30:Scaffold",
                            "restore-world",
                            restoreFailure);
                }
            }
            if (saved) {
                try {
                    Scaffold.mode.i(savedMode);
                    Scaffold.normalModeRotation.i(savedRotation);
                    Scaffold.strictAimCheck.v(savedStrictAim, 0L);
                    Scaffold.offsetRotationOffset.o(
                            (byte)0, 0L, savedOffset);
                }
                catch (Throwable restoreFailure) {
                    recordFeatureFailure(
                            "HighRiskFunctionalProbe30:Scaffold",
                            "restore-settings",
                            restoreFailure);
                }
            }
        }
    }

    private void pumpHighRiskFunctionalProbe31() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe31")
                || this.highRiskFunctionalProbe31Stage != 0) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe30")
                && this.highRiskFunctionalProbe30Stage < 1) return;

        BlockPos fixture = null;
        IBlockState originalState = null;
        List<BlockPos> savedBeds = null;
        boolean savedActive = this.bedScanActive;
        int savedCursor = this.bedScanCursor;
        int savedMinX = this.bedScanMinX;
        int savedMinY = this.bedScanMinY;
        int savedMinZ = this.bedScanMinZ;
        int savedSpanY = this.bedScanSpanY;
        int savedSpanZ = this.bedScanSpanZ;
        int savedVolume = this.bedScanVolume;

        try {
            if (ModuleManager.byName("BedNuker") == null
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || BedNuker.D == null) {
                throw new IllegalStateException(
                        "BedNuker scan authority unavailable");
            }
            savedBeds = new ArrayList<BlockPos>(BedNuker.D);

            int baseX = MathHelper.floor_double(this.c.thePlayer.posX);
            int baseY = MathHelper.floor_double(this.c.thePlayer.posY);
            int baseZ = MathHelper.floor_double(this.c.thePlayer.posZ);
            for (int dy = 3; dy <= 6 && fixture == null; ++dy) {
                for (int dx = 2; dx <= 6; ++dx) {
                    BlockPos candidate =
                            new BlockPos(baseX + dx, baseY + dy, baseZ + 2);
                    if (this.c.theWorld.isAirBlock(candidate)) {
                        fixture = candidate;
                        break;
                    }
                }
            }
            if (fixture == null) {
                throw new IllegalStateException(
                        "BedNuker probe found no temporary air position");
            }

            originalState = this.c.theWorld.getBlockState(fixture);
            if (!this.c.theWorld.setBlockState(
                    fixture, Blocks.bed.getDefaultState(), 3)) {
                throw new IllegalStateException(
                        "BedNuker probe could not place temporary bed");
            }

            BedNuker.D.clear();
            this.bedScanMinX = fixture.getX();
            this.bedScanMinY = fixture.getY();
            this.bedScanMinZ = fixture.getZ();
            this.bedScanSpanY = 1;
            this.bedScanSpanZ = 1;
            this.bedScanVolume = 1;
            this.bedScanCursor = 0;
            this.bedScanActive = true;

            this.pumpBedScan();

            if (this.bedScanActive
                    || this.bedScanCursor != 1
                    || BedNuker.D.size() != 1
                    || !BedNuker.D.contains(fixture)) {
                throw new IllegalStateException(
                        "BedNuker one-cell scan mismatch active="
                                + this.bedScanActive
                                + " cursor=" + this.bedScanCursor
                                + " beds=" + BedNuker.D);
            }

            runtimeMilestone(
                    "high-risk-functional-probe31-effect-pass:BedNuker:"
                            + "scanFixture=1:cursor=1:active=false");
            this.highRiskFunctionalProbe31Stage = 1;
            runtimeMilestone(
                    "high-risk-functional-probe31-module-pass:BedNuker");
            runtimeMilestone("high-risk-functional-probe31-pass:1");
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe31Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe31:BedNuker",
                    "one-cell-scan",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe31-fail:"
                            + failure.getClass().getName());
        }
        finally {
            try {
                if (this.c.theWorld != null
                        && fixture != null
                        && originalState != null) {
                    this.c.theWorld.setBlockState(
                            fixture, originalState, 3);
                }
            }
            catch (Throwable restoreFailure) {
                recordFeatureFailure(
                        "HighRiskFunctionalProbe31:BedNuker",
                        "restore-world",
                        restoreFailure);
            }

            if (BedNuker.D != null && savedBeds != null) {
                BedNuker.D.clear();
                BedNuker.D.addAll(savedBeds);
            }
            this.bedScanActive = savedActive;
            this.bedScanCursor = savedCursor;
            this.bedScanMinX = savedMinX;
            this.bedScanMinY = savedMinY;
            this.bedScanMinZ = savedMinZ;
            this.bedScanSpanY = savedSpanY;
            this.bedScanSpanZ = savedSpanZ;
            this.bedScanVolume = savedVolume;
        }
    }

    private void restoreHighRiskFunctionalProbe32() {
        if (!this.highRiskFunctionalProbe32Saved
                && !this.highRiskFunctionalProbe32EnableStateSaved) {
            return;
        }

        AutoTool probe = Modules.J(AutoTool.class);
        try {
            if (this.highRiskFunctionalProbe32Saved && probe != null) {
                Field fieldOriginalSlot =
                        AutoTool.class.getDeclaredField("J");
                Field fieldSwitching =
                        AutoTool.class.getDeclaredField("I");
                Field fieldPrimed =
                        AutoTool.class.getDeclaredField("S");
                Field fieldTimer =
                        AutoTool.class.getDeclaredField("t");
                fieldOriginalSlot.setAccessible(true);
                fieldSwitching.setAccessible(true);
                fieldPrimed.setAccessible(true);
                fieldTimer.setAccessible(true);

                AutoTool.disableWhenHoldingSword.v(
                        this.highRiskFunctionalProbe32SavedDisableSword, 0L);
                AutoTool.switchBackToSword.v(
                        this.highRiskFunctionalProbe32SavedSwitchBackToSword,
                        0L);
                AutoTool.switchBack.v(
                        this.highRiskFunctionalProbe32SavedSwitchBack, 0L);
                AutoTool.requireSneak.v(
                        this.highRiskFunctionalProbe32SavedRequireSneak, 0L);
                AutoTool.delay.o(
                        (byte)0, 0L,
                        this.highRiskFunctionalProbe32SavedDelay);
                probe.T(this.highRiskFunctionalProbe32SavedPriority);

                fieldSwitching.setBoolean(
                        probe,
                        this.highRiskFunctionalProbe32SavedSwitching);
                fieldOriginalSlot.setInt(
                        probe,
                        this.highRiskFunctionalProbe32SavedOriginalSlot);
                fieldPrimed.setBoolean(
                        probe,
                        this.highRiskFunctionalProbe32SavedPrimed);

                TimerUtil timer = (TimerUtil)fieldTimer.get(probe);
                if (timer != null) {
                    timer.p(this.highRiskFunctionalProbe32SavedTimerStart);
                }
            }

            if (this.highRiskFunctionalProbe32Saved
                    && this.c.thePlayer != null) {
                this.c.thePlayer.inventory.mainInventory[0] =
                        this.highRiskFunctionalProbe32SavedSlot0;
                this.c.thePlayer.inventory.mainInventory[4] =
                        this.highRiskFunctionalProbe32SavedSlot4;
                this.c.thePlayer.inventory.currentItem =
                        this.highRiskFunctionalProbe32SavedCurrentItem;
            }
            if (this.highRiskFunctionalProbe32Saved) {
                this.c.objectMouseOver =
                        this.highRiskFunctionalProbe32SavedMouseOver;
            }
            if (this.highRiskFunctionalProbe32EnableStateSaved
                    && probe != null
                    && probe.o()
                            != this.highRiskFunctionalProbe32SavedEnabled) {
                probe.I(
                        0L,
                        this.highRiskFunctionalProbe32SavedEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe32:AutoTool",
                    "restore-state",
                    restoreFailure);
        }

        if (this.c.theWorld != null
                && this.highRiskFunctionalProbe32Fixture != null
                && this.highRiskFunctionalProbe32OriginalState != null) {
            try {
                this.c.theWorld.setBlockState(
                        this.highRiskFunctionalProbe32Fixture,
                        this.highRiskFunctionalProbe32OriginalState,
                        3);
            }
            catch (Throwable restoreFailure) {
                recordFeatureFailure(
                        "HighRiskFunctionalProbe32:AutoTool",
                        "restore-world",
                        restoreFailure);
            }
        }

        this.highRiskFunctionalProbe32Saved = false;
        this.highRiskFunctionalProbe32EnableStateSaved = false;
        this.highRiskFunctionalProbe32Fixture = null;
        this.highRiskFunctionalProbe32OriginalState = null;
        this.highRiskFunctionalProbe32SavedSlot0 = null;
        this.highRiskFunctionalProbe32SavedSlot4 = null;
        this.highRiskFunctionalProbe32SavedMouseOver = null;
        this.highRiskFunctionalProbe32WaitTicks = 0;
    }

    private void pointAutoToolProbeAtFixture() {
        BlockPos fixture = this.highRiskFunctionalProbe32Fixture;
        if (fixture == null) {
            return;
        }
        this.c.objectMouseOver = new MovingObjectPosition(
                new Vec3(
                        (double)fixture.getX() + 0.5,
                        (double)fixture.getY() + 0.5,
                        (double)fixture.getZ() + 0.5),
                EnumFacing.UP,
                fixture);
    }

    private void pumpHighRiskFunctionalProbe32() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe32")
                || this.highRiskFunctionalProbe32Stage < 0
                || this.highRiskFunctionalProbe32Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe31")
                && this.highRiskFunctionalProbe31Stage < 1) return;

        AutoTool probe = Modules.J(AutoTool.class);

        try {
            if (probe == null
                    || ModuleManager.byClass(AutoTool.class) != probe
                    || ModuleManager.byName("AutoTool") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || AutoTool.disableWhenHoldingSword == null
                    || AutoTool.switchBackToSword == null
                    || AutoTool.switchBack == null
                    || AutoTool.requireSneak == null
                    || AutoTool.delay == null) {
                throw new IllegalStateException(
                        "AutoTool mining authority unavailable");
            }
            if (this.c.currentScreen != null) {
                return;
            }

            Field fieldOriginalSlot =
                    AutoTool.class.getDeclaredField("J");
            Field fieldSwitching =
                    AutoTool.class.getDeclaredField("I");
            Field fieldPrimed =
                    AutoTool.class.getDeclaredField("S");
            Field fieldTimer =
                    AutoTool.class.getDeclaredField("t");
            fieldOriginalSlot.setAccessible(true);
            fieldSwitching.setAccessible(true);
            fieldPrimed.setAccessible(true);
            fieldTimer.setAccessible(true);
            TimerUtil probeTimer = (TimerUtil)fieldTimer.get(probe);
            if (probeTimer == null) {
                throw new IllegalStateException(
                        "AutoTool timer authority unavailable");
            }

            switch (this.highRiskFunctionalProbe32Stage) {
                case 0: {
                    this.highRiskFunctionalProbe32SavedEnabled = probe.o();
                    this.highRiskFunctionalProbe32EnableStateSaved = true;
                    if (!probe.o()) {
                        probe.I(0L, true);
                    }
                    this.highRiskFunctionalProbe32Stage = 1;
                    this.highRiskFunctionalProbe32WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe32-enable-request:"
                                    + "AutoTool");
                    return;
                }

                case 1: {
                    ++this.highRiskFunctionalProbe32WaitTicks;
                    boolean active =
                            probe.o()
                                    && probe.P()
                                    && w.isOwnerActive(probe);
                    if (!active) {
                        if (this.highRiskFunctionalProbe32WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "AutoTool did not enter enabled/subscribed state"
                                            + " enabled=" + probe.o()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive=" + w.isOwnerActive(probe));
                        }
                        return;
                    }
                    runtimeMilestone(
                            "high-risk-functional-probe32-enabled-active:"
                                    + "AutoTool");

                    BlockPos fixture = null;
                    int baseX =
                            MathHelper.floor_double(this.c.thePlayer.posX);
                    int baseY =
                            MathHelper.floor_double(this.c.thePlayer.posY);
                    int baseZ =
                            MathHelper.floor_double(this.c.thePlayer.posZ);
                    for (int dy = 3; dy <= 6 && fixture == null; ++dy) {
                        for (int dx = 2; dx <= 6; ++dx) {
                            BlockPos candidate = new BlockPos(
                                    baseX + dx,
                                    baseY + dy,
                                    baseZ + 1);
                            if (this.c.theWorld.isAirBlock(candidate)) {
                                fixture = candidate;
                                break;
                            }
                        }
                    }
                    if (fixture == null) {
                        throw new IllegalStateException(
                                "AutoTool probe found no temporary air position");
                    }

                    this.highRiskFunctionalProbe32Fixture = fixture;
                    this.highRiskFunctionalProbe32OriginalState =
                            this.c.theWorld.getBlockState(fixture);
                    this.highRiskFunctionalProbe32SavedSlot0 =
                            this.c.thePlayer.inventory.mainInventory[0];
                    this.highRiskFunctionalProbe32SavedSlot4 =
                            this.c.thePlayer.inventory.mainInventory[4];
                    this.highRiskFunctionalProbe32SavedCurrentItem =
                            this.c.thePlayer.inventory.currentItem;
                    this.highRiskFunctionalProbe32SavedMouseOver =
                            this.c.objectMouseOver;
                    this.highRiskFunctionalProbe32SavedPriority = probe.Y();
                    this.highRiskFunctionalProbe32SavedDisableSword =
                            AutoTool.disableWhenHoldingSword.c();
                    this.highRiskFunctionalProbe32SavedSwitchBackToSword =
                            AutoTool.switchBackToSword.c();
                    this.highRiskFunctionalProbe32SavedSwitchBack =
                            AutoTool.switchBack.c();
                    this.highRiskFunctionalProbe32SavedRequireSneak =
                            AutoTool.requireSneak.c();
                    this.highRiskFunctionalProbe32SavedDelay =
                            AutoTool.delay.L();
                    this.highRiskFunctionalProbe32SavedSwitching =
                            fieldSwitching.getBoolean(probe);
                    this.highRiskFunctionalProbe32SavedOriginalSlot =
                            fieldOriginalSlot.getInt(probe);
                    this.highRiskFunctionalProbe32SavedPrimed =
                            fieldPrimed.getBoolean(probe);
                    this.highRiskFunctionalProbe32SavedTimerStart =
                            probeTimer.I;
                    this.highRiskFunctionalProbe32Saved = true;

                    if (!this.c.theWorld.setBlockState(
                            fixture, Blocks.stone.getDefaultState(), 3)) {
                        throw new IllegalStateException(
                                "AutoTool probe could not place temporary stone");
                    }

                    this.c.thePlayer.inventory.mainInventory[0] =
                            new ItemStack(Items.stick);
                    this.c.thePlayer.inventory.mainInventory[4] =
                            new ItemStack(Items.iron_pickaxe);
                    this.c.thePlayer.inventory.currentItem = 0;

                    AutoTool.disableWhenHoldingSword.v(false, 0L);
                    AutoTool.switchBackToSword.v(false, 0L);
                    AutoTool.switchBack.v(true, 0L);
                    AutoTool.requireSneak.v(false, 0L);
                    AutoTool.delay.o((byte)0, 0L, 0.0f);
                    probe.T(true);
                    fieldSwitching.setBoolean(probe, false);
                    fieldOriginalSlot.setInt(probe, -1);
                    fieldPrimed.setBoolean(probe, false);
                    probeTimer.p(0L);

                    int best = ItemUtil.e(0L, Blocks.stone);
                    if (best != 4) {
                        throw new IllegalStateException(
                                "AutoTool ItemUtil ranking mismatch"
                                        + " expected=4 actual=" + best);
                    }

                    this.pointAutoToolProbeAtFixture();
                    this.highRiskFunctionalProbe32Stage = 2;
                    this.highRiskFunctionalProbe32WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe32-ready:"
                                    + "AutoTool:mouse=left");
                    return;
                }

                case 2: {
                    ++this.highRiskFunctionalProbe32WaitTicks;
                    int attackKey =
                            this.c.gameSettings.keyBindAttack.getKeyCode();
                    if (!KeyBindUtil.V(
                            attackKey, 64165991731362L)) {
                        if (this.highRiskFunctionalProbe32WaitTicks > 480) {
                            throw new IllegalStateException(
                                    "AutoTool physical mouse-down timeout");
                        }
                        return;
                    }
                    if (OutgoingPacketState.P || OutgoingPacketState.h) {
                        return;
                    }

                    this.pointAutoToolProbeAtFixture();
                    probe.onPreMouseInput(
                            0L, new PreMouseInputEvent());

                    if (this.c.thePlayer.inventory.currentItem == 4
                            && fieldSwitching.getBoolean(probe)
                            && fieldOriginalSlot.getInt(probe) == 0) {
                        runtimeMilestone(
                                "high-risk-functional-probe32-effect-pass:"
                                        + "AutoTool:switch=0->4");
                        this.highRiskFunctionalProbe32Stage = 3;
                        this.highRiskFunctionalProbe32WaitTicks = 0;
                        runtimeMilestone(
                                "high-risk-functional-probe32-ready-release:"
                                        + "AutoTool:mouse=left");
                        return;
                    }

                    if (this.highRiskFunctionalProbe32WaitTicks > 160) {
                        throw new IllegalStateException(
                                "AutoTool did not switch to ranked pickaxe"
                                        + " slot="
                                        + this.c.thePlayer.inventory.currentItem
                                        + " switching="
                                        + fieldSwitching.getBoolean(probe)
                                        + " original="
                                        + fieldOriginalSlot.getInt(probe));
                    }
                    return;
                }

                case 3: {
                    ++this.highRiskFunctionalProbe32WaitTicks;
                    int attackKey =
                            this.c.gameSettings.keyBindAttack.getKeyCode();
                    if (KeyBindUtil.V(
                            attackKey, 64165991731362L)) {
                        if (this.highRiskFunctionalProbe32WaitTicks > 480) {
                            throw new IllegalStateException(
                                    "AutoTool physical mouse-release timeout");
                        }
                        return;
                    }

                    this.pointAutoToolProbeAtFixture();
                    probe.onPreMouseInput(
                            0L, new PreMouseInputEvent());

                    int releaseOriginalSlot =
                            fieldOriginalSlot.getInt(probe);
                    if (this.c.thePlayer.inventory.currentItem != 0
                            || fieldSwitching.getBoolean(probe)
                            || (releaseOriginalSlot != -1
                                    && releaseOriginalSlot != 0)) {
                        throw new IllegalStateException(
                                "AutoTool switch-back mismatch"
                                        + " slot="
                                        + this.c.thePlayer.inventory.currentItem
                                        + " switching="
                                        + fieldSwitching.getBoolean(probe)
                                        + " original="
                                        + releaseOriginalSlot);
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe32-effect-pass:"
                                    + "AutoTool:switchBack=4->0:original="
                                    + releaseOriginalSlot);
                    this.highRiskFunctionalProbe32Stage = 4;
                    runtimeMilestone(
                            "high-risk-functional-probe32-module-pass:"
                                    + "AutoTool");
                    runtimeMilestone(
                            "high-risk-functional-probe32-pass:1");
                    this.restoreHighRiskFunctionalProbe32();
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe32Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe32:AutoTool",
                    "physical-tool-selection-switchback",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe32-fail:"
                            + failure.getClass().getName());
            this.restoreHighRiskFunctionalProbe32();
        }
    }

    private void restoreHighRiskFunctionalProbe34() {
        if (!this.highRiskFunctionalProbe34Saved) {
            return;
        }

        AimAssist probe = Modules.J(AimAssist.class);
        try {
            if (this.c.theWorld != null && this.highRiskFunctionalProbe34FixtureId != 0) {
                this.c.theWorld.removeEntityFromWorld(this.highRiskFunctionalProbe34FixtureId);
            }

            Field cachedAngles = AimAssist.class.getDeclaredField("g");
            cachedAngles.setAccessible(true);

            AimAssist.lock.v(this.highRiskFunctionalProbe34OriginalLock, 0L);
            AimAssist.horizontalSpeed.o((byte)0, 0L, this.highRiskFunctionalProbe34OriginalHorizontalSpeed);
            AimAssist.verticalSpeed.o((byte)0, 0L, this.highRiskFunctionalProbe34OriginalVerticalSpeed);
            AimAssist.players.v(this.highRiskFunctionalProbe34OriginalPlayers, 0L);
            AimAssist.mobs.v(this.highRiskFunctionalProbe34OriginalMobs, 0L);
            AimAssist.animals.v(this.highRiskFunctionalProbe34OriginalAnimals, 0L);
            AimAssist.bosses.v(this.highRiskFunctionalProbe34OriginalBosses, 0L);
            AimAssist.friends.v(this.highRiskFunctionalProbe34OriginalFriends, 0L);
            AimAssist.enemies.v(this.highRiskFunctionalProbe34OriginalEnemies, 0L);
            AimAssist.teammates.v(this.highRiskFunctionalProbe34OriginalTeammates, 0L);
            AimAssist.bots.v(this.highRiskFunctionalProbe34OriginalBots, 0L);
            AimAssist.breakBlocks.v(this.highRiskFunctionalProbe34OriginalBreakBlocks, 0L);
            AimAssist.swordOnly.v(this.highRiskFunctionalProbe34OriginalSwordOnly, 0L);
            AimAssist.ignoreBehindWall.v(this.highRiskFunctionalProbe34OriginalIgnoreBehindWall, 0L);
            AimAssist.fov.o((byte)0, 0L, this.highRiskFunctionalProbe34OriginalFov);
            AimAssist.range.o((byte)0, 0L, this.highRiskFunctionalProbe34OriginalRange);
            AimAssist.sort.i(this.highRiskFunctionalProbe34OriginalSort);
            cachedAngles.set(probe, this.highRiskFunctionalProbe34OriginalCachedAngles);

            if (this.c.thePlayer != null) {
                this.c.thePlayer.rotationYaw = this.highRiskFunctionalProbe34OriginalYaw;
                this.c.thePlayer.prevRotationYaw = this.highRiskFunctionalProbe34OriginalYaw;
                this.c.thePlayer.rotationPitch = this.highRiskFunctionalProbe34OriginalPitch;
                this.c.thePlayer.prevRotationPitch = this.highRiskFunctionalProbe34OriginalPitch;
            }

            if (probe != null && probe.o() != this.highRiskFunctionalProbe34OriginalEnabled) {
                probe.I(0L, this.highRiskFunctionalProbe34OriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe34:AimAssist",
                    "restore-state",
                    restoreFailure);
        }

        this.highRiskFunctionalProbe34Saved = false;
        this.highRiskFunctionalProbe34WaitTicks = 0;
        this.highRiskFunctionalProbe34FixtureId = 0;
    }

    private void pumpHighRiskFunctionalProbe34() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe34")
                || this.highRiskFunctionalProbe34Stage < 0
                || this.highRiskFunctionalProbe34Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe33")
                && this.highRiskFunctionalProbe33Stage < 4) {
            return;
        }

        AimAssist probe = Modules.J(AimAssist.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(AimAssist.class) != probe
                    || ModuleManager.byName("AimAssist") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null) {
                throw new IllegalStateException("AimAssist live-world authority unavailable");
            }

            Field cachedAngles = AimAssist.class.getDeclaredField("g");
            cachedAngles.setAccessible(true);

            switch (this.highRiskFunctionalProbe34Stage) {
                case 0: {
                    this.highRiskFunctionalProbe34OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe34OriginalLock = AimAssist.lock.c();
                    this.highRiskFunctionalProbe34OriginalHorizontalSpeed = AimAssist.horizontalSpeed.L();
                    this.highRiskFunctionalProbe34OriginalVerticalSpeed = AimAssist.verticalSpeed.L();
                    this.highRiskFunctionalProbe34OriginalPlayers = AimAssist.players.c();
                    this.highRiskFunctionalProbe34OriginalMobs = AimAssist.mobs.c();
                    this.highRiskFunctionalProbe34OriginalAnimals = AimAssist.animals.c();
                    this.highRiskFunctionalProbe34OriginalBosses = AimAssist.bosses.c();
                    this.highRiskFunctionalProbe34OriginalFriends = AimAssist.friends.c();
                    this.highRiskFunctionalProbe34OriginalEnemies = AimAssist.enemies.c();
                    this.highRiskFunctionalProbe34OriginalTeammates = AimAssist.teammates.c();
                    this.highRiskFunctionalProbe34OriginalBots = AimAssist.bots.c();
                    this.highRiskFunctionalProbe34OriginalBreakBlocks = AimAssist.breakBlocks.c();
                    this.highRiskFunctionalProbe34OriginalSwordOnly = AimAssist.swordOnly.c();
                    this.highRiskFunctionalProbe34OriginalIgnoreBehindWall = AimAssist.ignoreBehindWall.c();
                    this.highRiskFunctionalProbe34OriginalFov = AimAssist.fov.L();
                    this.highRiskFunctionalProbe34OriginalRange = AimAssist.range.L();
                    this.highRiskFunctionalProbe34OriginalSort = AimAssist.sort.Y();
                    this.highRiskFunctionalProbe34OriginalYaw = this.c.thePlayer.rotationYaw;
                    this.highRiskFunctionalProbe34OriginalPitch = this.c.thePlayer.rotationPitch;
                    this.highRiskFunctionalProbe34OriginalCachedAngles = cachedAngles.get(probe);
                    this.highRiskFunctionalProbe34Saved = true;

                    AimAssist.lock.v(true, 0L);
                    AimAssist.horizontalSpeed.o((byte)0, 0L, 20.0f);
                    AimAssist.verticalSpeed.o((byte)0, 0L, 20.0f);
                    AimAssist.players.v(false, 0L);
                    AimAssist.mobs.v(true, 0L);
                    AimAssist.animals.v(false, 0L);
                    AimAssist.bosses.v(false, 0L);
                    AimAssist.friends.v(false, 0L);
                    AimAssist.enemies.v(false, 0L);
                    AimAssist.teammates.v(false, 0L);
                    AimAssist.bots.v(false, 0L);
                    AimAssist.breakBlocks.v(false, 0L);
                    AimAssist.swordOnly.v(false, 0L);
                    AimAssist.ignoreBehindWall.v(false, 0L);
                    AimAssist.fov.o((byte)0, 0L, 360.0f);
                    AimAssist.range.o((byte)0, 0L, 8.0f);
                    AimAssist.sort.i("DISTANCE");
                    cachedAngles.set(probe, null);

                    this.c.thePlayer.rotationYaw = 0.0f;
                    this.c.thePlayer.prevRotationYaw = 0.0f;
                    this.c.thePlayer.rotationPitch = 0.0f;
                    this.c.thePlayer.prevRotationPitch = 0.0f;

                    this.highRiskFunctionalProbe34FixtureId = -2147483598;
                    EntityZombie target = new EntityZombie(this.c.theWorld);
                    target.setPosition(
                            this.c.thePlayer.posX + 2.0,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ + 2.0);
                    this.c.theWorld.addEntityToWorld(
                            this.highRiskFunctionalProbe34FixtureId,
                            target);

                    if (!probe.o()) {
                        probe.I(0L, true);
                    }
                    this.highRiskFunctionalProbe34Stage = 1;
                    this.highRiskFunctionalProbe34WaitTicks = 0;
                    runtimeMilestone("high-risk-functional-probe34-enable-request:AimAssist");
                    return;
                }

                case 1: {
                    ++this.highRiskFunctionalProbe34WaitTicks;
                    boolean active = probe.o() && probe.P() && w.isOwnerActive(probe);
                    if (!active) {
                        if (this.highRiskFunctionalProbe34WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "AimAssist did not enter enabled/subscribed state"
                                            + " enabled=" + probe.o()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive=" + w.isOwnerActive(probe));
                        }
                        return;
                    }

                    runtimeMilestone("high-risk-functional-probe34-ready:AimAssist:mouse=left");
                    this.highRiskFunctionalProbe34Stage = 2;
                    return;
                }

                case 2: {
                    int attackKey = this.c.gameSettings.keyBindAttack.getKeyCode();
                    if (!KeyBindUtil.V(attackKey, 64165991731362L)) {
                        return;
                    }

                    Object cached = cachedAngles.get(probe);
                    float yaw = this.c.thePlayer.rotationYaw;
                    float pitch = this.c.thePlayer.rotationPitch;
                    if (!(cached instanceof Pair) || Math.abs(yaw) < 5.0f) {
                        return;
                    }

                    Pair pair = (Pair)cached;
                    float cachedYaw = ((Float)pair.a()).floatValue();
                    float cachedPitch = ((Float)pair.p()).floatValue();
                    if (Math.abs(yaw - cachedYaw) > 0.05f
                            || Math.abs(pitch - cachedPitch) > 0.05f) {
                        throw new IllegalStateException(
                                "AimAssist camera/cache mismatch"
                                        + " camera=" + yaw + "," + pitch
                                        + " cached=" + cachedYaw + "," + cachedPitch);
                    }

                    SetAnglesEvent angles = new SetAnglesEvent(123.0f, 45.0f);
                    probe.onSetAngles(angles, 0L);
                    if (!angles.l()
                            || Math.abs(angles.x() - cachedYaw) > 0.05f
                            || Math.abs(angles.s() - cachedPitch) > 0.05f) {
                        throw new IllegalStateException(
                                "AimAssist lock-angle propagation mismatch"
                                        + " changed=" + angles.l()
                                        + " event=" + angles.x() + "," + angles.s()
                                        + " cached=" + cachedYaw + "," + cachedPitch);
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe34-effect-pass:"
                                    + "AimAssist:yawChanged=true:lockAngles=true:"
                                    + "yaw=" + yaw + ":pitch=" + pitch);
                    runtimeMilestone("high-risk-functional-probe34-ready-release:AimAssist:mouse=left");
                    this.highRiskFunctionalProbe34Stage = 3;
                    return;
                }

                case 3: {
                    int attackKey = this.c.gameSettings.keyBindAttack.getKeyCode();
                    if (KeyBindUtil.V(attackKey, 64165991731362L)) {
                        return;
                    }

                    if (cachedAngles.get(probe) != null) {
                        probe.onPostTick(0L, new PostTickEvent());
                    }
                    if (cachedAngles.get(probe) != null) {
                        throw new IllegalStateException(
                                "AimAssist cached lock state did not clear after attack release");
                    }

                    runtimeMilestone("high-risk-functional-probe34-release-pass:AimAssist:cacheCleared=true");
                    this.highRiskFunctionalProbe34Stage = 4;
                    runtimeMilestone("high-risk-functional-probe34-module-pass:AimAssist");
                    runtimeMilestone("high-risk-functional-probe34-pass:1");
                    this.restoreHighRiskFunctionalProbe34();
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe34Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe34:AimAssist",
                    "physical-target-rotation",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe34-fail:" + failure.getClass().getName());
            this.restoreHighRiskFunctionalProbe34();
        }
    }

    private void restoreHighRiskFunctionalProbe33() {
        if (!this.highRiskFunctionalProbe33Saved) {
            return;
        }

        HitSelect probe = Modules.J(HitSelect.class);
        try {
            if (probe != null) {
                Field fieldTarget = HitSelect.class.getDeclaredField("D");
                Field fieldTargetTicks = HitSelect.class.getDeclaredField("m");
                Field fieldPauseTicks = HitSelect.class.getDeclaredField("L");
                fieldTarget.setAccessible(true);
                fieldTargetTicks.setAccessible(true);
                fieldPauseTicks.setAccessible(true);

                if (this.highRiskFunctionalProbe33OriginalStrategy != null) {
                    HitSelect.strategy.i(
                            this.highRiskFunctionalProbe33OriginalStrategy);
                }
                fieldTarget.set(
                        probe,
                        this.highRiskFunctionalProbe33OriginalTarget);
                fieldTargetTicks.setInt(
                        probe,
                        this.highRiskFunctionalProbe33OriginalTargetTicks);
                fieldPauseTicks.setInt(
                        probe,
                        this.highRiskFunctionalProbe33OriginalPauseTicks);
                AttackTracker.Z(
                        this.highRiskFunctionalProbe33OriginalAttackGate);

                if (probe.o()
                        != this.highRiskFunctionalProbe33OriginalEnabled) {
                    probe.I(
                            0L,
                            this.highRiskFunctionalProbe33OriginalEnabled);
                }
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe33:HitSelect",
                    "restore-state",
                    restoreFailure);
        }

        this.highRiskFunctionalProbe33Saved = false;
        this.highRiskFunctionalProbe33WaitTicks = 0;
    }

    private void pumpHighRiskFunctionalProbe33() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe33")
                || this.highRiskFunctionalProbe33Stage < 0
                || this.highRiskFunctionalProbe33Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe32")
                && this.highRiskFunctionalProbe32Stage < 4) {
            return;
        }

        HitSelect probe = Modules.J(HitSelect.class);

        try {
            if (probe == null
                    || ModuleManager.byClass(HitSelect.class) != probe
                    || ModuleManager.byName("HitSelect") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || HitSelect.strategy == null) {
                throw new IllegalStateException(
                        "HitSelect authority unavailable");
            }

            Field fieldTarget = HitSelect.class.getDeclaredField("D");
            Field fieldTargetTicks = HitSelect.class.getDeclaredField("m");
            Field fieldPauseTicks = HitSelect.class.getDeclaredField("L");
            fieldTarget.setAccessible(true);
            fieldTargetTicks.setAccessible(true);
            fieldPauseTicks.setAccessible(true);

            switch (this.highRiskFunctionalProbe33Stage) {
                case 0: {
                    this.highRiskFunctionalProbe33OriginalEnabled =
                            probe.o();
                    this.highRiskFunctionalProbe33OriginalStrategy =
                            HitSelect.strategy.Y();
                    this.highRiskFunctionalProbe33OriginalAttackGate =
                            AttackTracker.J();
                    this.highRiskFunctionalProbe33OriginalTarget =
                            fieldTarget.get(probe);
                    this.highRiskFunctionalProbe33OriginalTargetTicks =
                            fieldTargetTicks.getInt(probe);
                    this.highRiskFunctionalProbe33OriginalPauseTicks =
                            fieldPauseTicks.getInt(probe);
                    this.highRiskFunctionalProbe33Saved = true;

                    HitSelect.strategy.i("NORMAL");
                    if (!probe.o()) {
                        probe.I(0L, true);
                    }
                    this.highRiskFunctionalProbe33Stage = 1;
                    this.highRiskFunctionalProbe33WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe33-enable-request:"
                                    + "HitSelect");
                    return;
                }

                case 1: {
                    ++this.highRiskFunctionalProbe33WaitTicks;
                    boolean active =
                            probe.o()
                                    && probe.P()
                                    && w.isOwnerActive(probe);
                    if (!active) {
                        if (this.highRiskFunctionalProbe33WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "HitSelect did not enter enabled/subscribed state"
                                            + " enabled=" + probe.o()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }

                    EntityOtherPlayerMP target =
                            new EntityOtherPlayerMP(
                                    this.c.theWorld,
                                    new GameProfile(
                                            UUID.randomUUID(),
                                            "OpenAbyssHitSelectProbe"));
                    target.setPosition(
                            this.c.thePlayer.posX + 1.0,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ);

                    probe.onAttackEntity(
                            0L,
                            new AttackEntityEvent(
                                    target,
                                    (char)0,
                                    (short)0,
                                    0));

                    if (fieldTarget.get(probe) != target
                            || fieldTargetTicks.getInt(probe) != 60) {
                        throw new IllegalStateException(
                                "HitSelect attack acquisition mismatch"
                                        + " target="
                                        + (fieldTarget.get(probe) == target)
                                        + " ticks="
                                        + fieldTargetTicks.getInt(probe));
                    }

                    fieldPauseTicks.setInt(probe, 20);
                    AttackTracker.Z(true);
                    runtimeMilestone(
                            "high-risk-functional-probe33-acquire-pass:"
                                    + "HitSelect:targetTicks=60:pause=20");
                    this.highRiskFunctionalProbe33Stage = 2;
                    return;
                }

                case 2: {
                    int beforeTargetTicks =
                            fieldTargetTicks.getInt(probe);
                    int beforePauseTicks =
                            fieldPauseTicks.getInt(probe);
                    if (beforeTargetTicks <= 1
                            || beforePauseTicks <= 0
                            || fieldTarget.get(probe) == null) {
                        throw new IllegalStateException(
                                "HitSelect organic pre-dispatch consumed fixture"
                                        + " targetTicks="
                                        + beforeTargetTicks
                                        + " pauseTicks="
                                        + beforePauseTicks
                                        + " targetPresent="
                                        + (fieldTarget.get(probe) != null));
                    }

                    probe.onPreMouseInput(
                            0L,
                            new PreMouseInputEvent());

                    int targetTicks =
                            fieldTargetTicks.getInt(probe);
                    int pauseTicks =
                            fieldPauseTicks.getInt(probe);
                    if (AttackTracker.J()
                            || targetTicks != beforeTargetTicks - 1
                            || pauseTicks != beforePauseTicks - 1
                            || fieldTarget.get(probe) == null) {
                        throw new IllegalStateException(
                                "HitSelect NORMAL gate mismatch"
                                        + " attackAllowed="
                                        + AttackTracker.J()
                                        + " targetTicks="
                                        + beforeTargetTicks
                                        + "->"
                                        + targetTicks
                                        + " pauseTicks="
                                        + beforePauseTicks
                                        + "->"
                                        + pauseTicks
                                        + " targetPresent="
                                        + (fieldTarget.get(probe) != null));
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe33-effect-pass:"
                                    + "HitSelect:attackAllowed=false:"
                                    + "targetDelta=1:pauseDelta=1:"
                                    + "before="
                                    + beforeTargetTicks
                                    + ","
                                    + beforePauseTicks
                                    + ":after="
                                    + targetTicks
                                    + ","
                                    + pauseTicks);
                    this.highRiskFunctionalProbe33Stage = 3;
                    return;
                }

                case 3: {
                    probe.P(0L);
                    if (!AttackTracker.J()
                            || fieldTarget.get(probe) != null
                            || fieldTargetTicks.getInt(probe) != 0
                            || fieldPauseTicks.getInt(probe) != 0) {
                        throw new IllegalStateException(
                                "HitSelect reset mismatch"
                                        + " attackAllowed="
                                        + AttackTracker.J()
                                        + " target="
                                        + fieldTarget.get(probe)
                                        + " targetTicks="
                                        + fieldTargetTicks.getInt(probe)
                                        + " pauseTicks="
                                        + fieldPauseTicks.getInt(probe));
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe33-reset-pass:"
                                    + "HitSelect");
                    this.highRiskFunctionalProbe33Stage = 4;
                    runtimeMilestone(
                            "high-risk-functional-probe33-module-pass:"
                                    + "HitSelect");
                    runtimeMilestone(
                            "high-risk-functional-probe33-pass:1");
                    this.restoreHighRiskFunctionalProbe33();
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe33Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe33:HitSelect",
                    "attack-gate-state-machine",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe33-fail:"
                            + failure.getClass().getName());
            this.restoreHighRiskFunctionalProbe33();
        }
    }

    private void restoreHighRiskFunctionalProbe35() {
        if (!this.highRiskFunctionalProbe35Saved) {
            return;
        }
        try {
            JumpReset probe = Modules.J(JumpReset.class);

            if (this.highRiskFunctionalProbe35FixtureId != 0
                    && this.c.theWorld != null) {
                this.c.theWorld.removeEntityFromWorld(
                        this.highRiskFunctionalProbe35FixtureId);
            }

            JumpReset.chance.d(this.highRiskFunctionalProbe35OriginalChance);
            JumpReset.requireMoving.v(
                    this.highRiskFunctionalProbe35OriginalRequireMoving, 0L);
            JumpReset.reduce.v(
                    this.highRiskFunctionalProbe35OriginalReduce, 0L);
            JumpReset.players.v(
                    this.highRiskFunctionalProbe35OriginalPlayers, 0L);
            JumpReset.mobs.v(
                    this.highRiskFunctionalProbe35OriginalMobs, 0L);
            JumpReset.animals.v(
                    this.highRiskFunctionalProbe35OriginalAnimals, 0L);
            JumpReset.bosses.v(
                    this.highRiskFunctionalProbe35OriginalBosses, 0L);
            JumpReset.friends.v(
                    this.highRiskFunctionalProbe35OriginalFriends, 0L);
            JumpReset.enemies.v(
                    this.highRiskFunctionalProbe35OriginalEnemies, 0L);
            JumpReset.teammates.v(
                    this.highRiskFunctionalProbe35OriginalTeammates, 0L);
            JumpReset.bots.v(
                    this.highRiskFunctionalProbe35OriginalBots, 0L);
            JumpReset.fov.o(
                    (byte)0, 0L, this.highRiskFunctionalProbe35OriginalFov);
            JumpReset.range.o(
                    (byte)0, 0L, this.highRiskFunctionalProbe35OriginalRange);

            if (this.c.thePlayer != null) {
                this.c.thePlayer.setSprinting(
                        this.highRiskFunctionalProbe35OriginalSprinting);
            }

            if (probe != null) {
                probe.A(0L);
                if (probe.o() != this.highRiskFunctionalProbe35OriginalEnabled) {
                    probe.I(0L, this.highRiskFunctionalProbe35OriginalEnabled);
                }
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe35:JumpReset",
                    "restore-state",
                    restoreFailure);
        }

        this.highRiskFunctionalProbe35Saved = false;
        this.highRiskFunctionalProbe35WaitTicks = 0;
        this.highRiskFunctionalProbe35FixtureId = 0;
    }

    private void pumpHighRiskFunctionalProbe35() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe35")
                || this.highRiskFunctionalProbe35Stage < 0
                || this.highRiskFunctionalProbe35Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe34")
                && this.highRiskFunctionalProbe34Stage < 4) {
            return;
        }

        JumpReset probe = Modules.J(JumpReset.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(JumpReset.class) != probe
                    || ModuleManager.byName("JumpReset") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null) {
                throw new IllegalStateException(
                        "JumpReset live-world authority unavailable");
            }

            switch (this.highRiskFunctionalProbe35Stage) {
                case 0: {
                    this.highRiskFunctionalProbe35OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe35OriginalChance =
                            JumpReset.chance.k();
                    this.highRiskFunctionalProbe35OriginalRequireMoving =
                            JumpReset.requireMoving.c();
                    this.highRiskFunctionalProbe35OriginalReduce =
                            JumpReset.reduce.c();
                    this.highRiskFunctionalProbe35OriginalPlayers =
                            JumpReset.players.c();
                    this.highRiskFunctionalProbe35OriginalMobs =
                            JumpReset.mobs.c();
                    this.highRiskFunctionalProbe35OriginalAnimals =
                            JumpReset.animals.c();
                    this.highRiskFunctionalProbe35OriginalBosses =
                            JumpReset.bosses.c();
                    this.highRiskFunctionalProbe35OriginalFriends =
                            JumpReset.friends.c();
                    this.highRiskFunctionalProbe35OriginalEnemies =
                            JumpReset.enemies.c();
                    this.highRiskFunctionalProbe35OriginalTeammates =
                            JumpReset.teammates.c();
                    this.highRiskFunctionalProbe35OriginalBots =
                            JumpReset.bots.c();
                    this.highRiskFunctionalProbe35OriginalFov =
                            JumpReset.fov.L();
                    this.highRiskFunctionalProbe35OriginalRange =
                            JumpReset.range.L();
                    this.highRiskFunctionalProbe35OriginalSprinting =
                            this.c.thePlayer.isSprinting();
                    this.highRiskFunctionalProbe35Saved = true;

                    JumpReset.chance.d(100);
                    JumpReset.requireMoving.v(false, 0L);
                    JumpReset.reduce.v(false, 0L);
                    JumpReset.players.v(false, 0L);
                    JumpReset.mobs.v(true, 0L);
                    JumpReset.animals.v(false, 0L);
                    JumpReset.bosses.v(false, 0L);
                    JumpReset.friends.v(false, 0L);
                    JumpReset.enemies.v(false, 0L);
                    JumpReset.teammates.v(false, 0L);
                    JumpReset.bots.v(false, 0L);
                    JumpReset.fov.o((byte)0, 0L, 360.0f);
                    JumpReset.range.o((byte)0, 0L, 8.0f);

                    if (!probe.o()) {
                        probe.I(0L, true);
                    }

                    this.highRiskFunctionalProbe35Stage = 1;
                    this.highRiskFunctionalProbe35WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe35-enable-request:"
                                    + "JumpReset");
                    return;
                }

                case 1: {
                    ++this.highRiskFunctionalProbe35WaitTicks;
                    boolean active =
                            probe.o()
                                    && probe.P()
                                    && w.isOwnerActive(probe);
                    if (!active) {
                        if (this.highRiskFunctionalProbe35WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "JumpReset did not enter enabled/subscribed state"
                                            + " enabled=" + probe.o()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }

                    if (this.c.thePlayer.isPotionActive(Potion.jump)) {
                        throw new IllegalStateException(
                                "JumpReset fixture player unexpectedly has jump potion");
                    }

                    EntityZombie target =
                            new EntityZombie(this.c.theWorld);
                    target.setPosition(
                            this.c.thePlayer.posX + 2.0,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ);
                    this.highRiskFunctionalProbe35FixtureId = -73535;
                    this.c.theWorld.addEntityToWorld(
                            this.highRiskFunctionalProbe35FixtureId,
                            target);
                    this.c.thePlayer.setSprinting(true);

                    probe.onKnockback(
                            new KnockbackEvent(1.0, 0.5, 0.0),
                            0L);

                    MoveInputEvent movement =
                            new MoveInputEvent(
                                    0.25f,
                                    -0.5f,
                                    false,
                                    false,
                                    0.0);
                    probe.onMoveInput(0L, movement);

                    if (Math.abs(movement.t() - 1.0f) > 0.0001f
                            || !movement.d()) {
                        throw new IllegalStateException(
                                "JumpReset move-input effect mismatch"
                                        + " forward=" + movement.t()
                                        + " strafe=" + movement.R()
                                        + " jump=" + movement.d());
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe35-effect-pass:"
                                    + "JumpReset:forward="
                                    + movement.t()
                                    + ":jump="
                                    + movement.d()
                                    + ":target=mobs");
                    this.highRiskFunctionalProbe35Stage = 2;
                    return;
                }

                case 2: {
                    MoveInputEvent cleared =
                            new MoveInputEvent(
                                    0.25f,
                                    -0.5f,
                                    false,
                                    false,
                                    0.0);
                    probe.onMoveInput(0L, cleared);
                    if (Math.abs(cleared.t() - 0.25f) > 0.0001f
                            || cleared.d()) {
                        throw new IllegalStateException(
                                "JumpReset one-shot reset mismatch"
                                        + " forward=" + cleared.t()
                                        + " jump=" + cleared.d());
                    }
                    runtimeMilestone(
                            "high-risk-functional-probe35-reset-pass:"
                                    + "JumpReset:oneShot=true");
                    this.highRiskFunctionalProbe35Stage = 3;
                    return;
                }

                case 3: {
                    runtimeMilestone(
                            "high-risk-functional-probe35-module-pass:"
                                    + "JumpReset");
                    runtimeMilestone(
                            "high-risk-functional-probe35-pass:1");
                    this.highRiskFunctionalProbe35Stage = 4;
                    this.restoreHighRiskFunctionalProbe35();
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe35Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe35:JumpReset",
                    "knockback-moveinput-state-machine",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe35-fail:"
                            + failure.getClass().getName()
                            + ":"
                            + String.valueOf(failure.getMessage()));
            this.restoreHighRiskFunctionalProbe35();
        }
    }

    private void restoreHighRiskFunctionalProbe36() {
        if (!this.highRiskFunctionalProbe36Saved) {
            return;
        }
        try {
            SprintReset probe = Modules.J(SprintReset.class);

            if (this.highRiskFunctionalProbe36FixtureId != 0
                    && this.c.theWorld != null) {
                this.c.theWorld.removeEntityFromWorld(
                        this.highRiskFunctionalProbe36FixtureId);
            }

            SprintReset.mode.i(this.highRiskFunctionalProbe36OriginalMode);
            SprintReset.interval.o(
                    (byte)0, 0L,
                    this.highRiskFunctionalProbe36OriginalInterval);
            SprintReset.duration.o(
                    (byte)0, 0L,
                    this.highRiskFunctionalProbe36OriginalDuration);
            SprintReset.requireTargetDamage.v(
                    this.highRiskFunctionalProbe36OriginalRequireTargetDamage,
                    0L);

            if (this.c.thePlayer != null) {
                this.c.thePlayer.setSprinting(
                        this.highRiskFunctionalProbe36OriginalSprinting);
                if (this.c.thePlayer.movementInput != null) {
                    this.c.thePlayer.movementInput.moveForward =
                            this.highRiskFunctionalProbe36OriginalMoveForward;
                    this.c.thePlayer.movementInput.moveStrafe =
                            this.highRiskFunctionalProbe36OriginalMoveStrafe;
                }
            }

            KeyBindUtil.A(
                    0L,
                    this.c.gameSettings.keyBindForward.getKeyCode(),
                    this.highRiskFunctionalProbe36OriginalForwardBinding);

            if (probe != null) {
                probe.A(0L);
                KeyBindUtil.A(
                        0L,
                        this.c.gameSettings.keyBindForward.getKeyCode(),
                        this.highRiskFunctionalProbe36OriginalForwardBinding);
                if (probe.o() != this.highRiskFunctionalProbe36OriginalEnabled) {
                    probe.I(
                            0L,
                            this.highRiskFunctionalProbe36OriginalEnabled);
                }
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe36:SprintReset",
                    "restore-state",
                    restoreFailure);
        }

        this.highRiskFunctionalProbe36Saved = false;
        this.highRiskFunctionalProbe36WaitTicks = 0;
        this.highRiskFunctionalProbe36FixtureId = 0;
    }

    private void pumpHighRiskFunctionalProbe36() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe36")
                || this.highRiskFunctionalProbe36Stage < 0
                || this.highRiskFunctionalProbe36Stage >= 6) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe35")
                && this.highRiskFunctionalProbe35Stage < 4) {
            return;
        }

        SprintReset probe = Modules.J(SprintReset.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(SprintReset.class) != probe
                    || ModuleManager.byName("SprintReset") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || this.c.thePlayer.movementInput == null) {
                throw new IllegalStateException(
                        "SprintReset live-world authority unavailable");
            }

            switch (this.highRiskFunctionalProbe36Stage) {
                case 0: {
                    this.highRiskFunctionalProbe36OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe36OriginalMode =
                            SprintReset.mode.Y();
                    this.highRiskFunctionalProbe36OriginalInterval =
                            SprintReset.interval.L();
                    this.highRiskFunctionalProbe36OriginalDuration =
                            SprintReset.duration.L();
                    this.highRiskFunctionalProbe36OriginalRequireTargetDamage =
                            SprintReset.requireTargetDamage.c();
                    this.highRiskFunctionalProbe36OriginalSprinting =
                            this.c.thePlayer.isSprinting();
                    this.highRiskFunctionalProbe36OriginalMoveForward =
                            this.c.thePlayer.movementInput.moveForward;
                    this.highRiskFunctionalProbe36OriginalMoveStrafe =
                            this.c.thePlayer.movementInput.moveStrafe;
                    this.highRiskFunctionalProbe36OriginalForwardBinding =
                            this.c.gameSettings.keyBindForward.isKeyDown();
                    this.highRiskFunctionalProbe36Saved = true;

                    SprintReset.mode.i("LEGIT");
                    SprintReset.interval.o((byte)0, 0L, 0.0f);
                    SprintReset.duration.o((byte)0, 0L, 0.0f);
                    SprintReset.requireTargetDamage.v(false, 0L);

                    if (!probe.o()) {
                        probe.I(0L, true);
                    }

                    this.highRiskFunctionalProbe36Stage = 1;
                    this.highRiskFunctionalProbe36WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe36-enable-request:"
                                    + "SprintReset");
                    return;
                }

                case 1: {
                    ++this.highRiskFunctionalProbe36WaitTicks;
                    boolean active =
                            probe.o()
                                    && probe.P()
                                    && w.isOwnerActive(probe);
                    if (!active) {
                        if (this.highRiskFunctionalProbe36WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "SprintReset did not enter enabled/subscribed state"
                                            + " enabled=" + probe.o()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe36-ready:"
                                    + "SprintReset:key=w");
                    this.highRiskFunctionalProbe36Stage = 2;
                    this.highRiskFunctionalProbe36WaitTicks = 0;
                    return;
                }

                case 2: {
                    ++this.highRiskFunctionalProbe36WaitTicks;
                    int forwardKey =
                            this.c.gameSettings.keyBindForward.getKeyCode();
                    if (!KeyBindUtil.V(forwardKey, 0L)) {
                        if (this.highRiskFunctionalProbe36WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "SprintReset did not observe physical W input");
                        }
                        return;
                    }

                    EntityZombie target =
                            new EntityZombie(this.c.theWorld);
                    target.setPosition(
                            this.c.thePlayer.posX + 2.0,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ);
                    this.highRiskFunctionalProbe36FixtureId = -73636;
                    this.c.theWorld.addEntityToWorld(
                            this.highRiskFunctionalProbe36FixtureId,
                            target);

                    this.c.thePlayer.setSprinting(true);
                    this.c.thePlayer.movementInput.moveForward = 0.8f;
                    this.c.thePlayer.movementInput.moveStrafe = 0.4f;
                    KeyBindUtil.A(0L, forwardKey, true);

                    probe.onAttackEntity(
                            new AttackEntityEvent(
                                    target,
                                    (char)0,
                                    (short)0,
                                    0));

                    if (Math.abs(
                                    this.c.thePlayer.movementInput.moveForward)
                                > 0.0001f
                            || Math.abs(
                                    this.c.thePlayer.movementInput.moveStrafe)
                                > 0.0001f
                            || this.c.gameSettings.keyBindForward.isKeyDown()) {
                        throw new IllegalStateException(
                                "SprintReset LEGIT attack effect mismatch"
                                        + " forward="
                                        + this.c.thePlayer.movementInput.moveForward
                                        + " strafe="
                                        + this.c.thePlayer.movementInput.moveStrafe
                                        + " logicalW="
                                        + this.c.gameSettings.keyBindForward.isKeyDown());
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe36-effect-pass:"
                                    + "SprintReset:forward=0.0:strafe=0.0:"
                                    + "logicalW=false:physicalW=true");

                    /*
                     * Do not invoke SprintReset.onPostTick() manually here.
                     * Return to the real EventBus so its subscribed PostTick
                     * handler performs the duration restore on a later tick.
                     * This proves the production event ordering and avoids
                     * sampling X11 keyboard state twice in the attack tick.
                     */
                    this.highRiskFunctionalProbe36Stage = 3;
                    this.highRiskFunctionalProbe36WaitTicks = 0;
                    return;
                }

                case 3: {
                    ++this.highRiskFunctionalProbe36WaitTicks;
                    int forwardKey =
                            this.c.gameSettings.keyBindForward.getKeyCode();
                    boolean physicalHeld = KeyBindUtil.V(forwardKey, 0L);
                    boolean logicalHeld =
                            this.c.gameSettings.keyBindForward.isKeyDown();

                    if (!physicalHeld || !logicalHeld) {
                        if (this.highRiskFunctionalProbe36WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "SprintReset subscribed duration restore did not settle"
                                            + " physicalW=" + physicalHeld
                                            + " logicalW=" + logicalHeld);
                        }
                        return;
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe36-duration-pass:"
                                    + "SprintReset:logicalW=true:physicalW=true");
                    runtimeMilestone(
                            "high-risk-functional-probe36-ready-release:"
                                    + "SprintReset:key=w");
                    this.highRiskFunctionalProbe36Stage = 4;
                    this.highRiskFunctionalProbe36WaitTicks = 0;
                    return;
                }

                case 4: {
                    ++this.highRiskFunctionalProbe36WaitTicks;
                    int forwardKey =
                            this.c.gameSettings.keyBindForward.getKeyCode();
                    if (KeyBindUtil.V(forwardKey, 0L)) {
                        if (this.highRiskFunctionalProbe36WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "SprintReset physical W release was not observed");
                        }
                        return;
                    }

                    KeyBindUtil.o(0L, forwardKey);
                    if (this.c.gameSettings.keyBindForward.isKeyDown()) {
                        throw new IllegalStateException(
                                "SprintReset logical W remained pressed after physical release");
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe36-release-pass:"
                                    + "SprintReset:logicalW=false:physicalW=false");
                    this.highRiskFunctionalProbe36Stage = 5;
                    return;
                }

                case 5: {
                    runtimeMilestone(
                            "high-risk-functional-probe36-module-pass:"
                                    + "SprintReset");
                    runtimeMilestone(
                            "high-risk-functional-probe36-pass:1");
                    this.highRiskFunctionalProbe36Stage = 6;
                    this.restoreHighRiskFunctionalProbe36();
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe36Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe36:SprintReset",
                    "legit-physical-forward-reset",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe36-fail:"
                            + failure.getClass().getName()
                            + ":"
                            + String.valueOf(failure.getMessage()));
            this.restoreHighRiskFunctionalProbe36();
        }
    }

    @SuppressWarnings("unchecked")
    private List<EntityLargeFireball> antiFireballProbeList(
            AntiFireball probe, String fieldName) throws Exception {
        Field field = AntiFireball.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (List<EntityLargeFireball>)field.get(probe);
    }

    private EntityLargeFireball antiFireballProbeTarget(
            AntiFireball probe) throws Exception {
        Field field = AntiFireball.class.getDeclaredField("G");
        field.setAccessible(true);
        return (EntityLargeFireball)field.get(probe);
    }

    private void restoreHighRiskFunctionalProbe37() {
        if (!this.highRiskFunctionalProbe37Saved) {
            return;
        }
        try {
            AntiFireball probe = Modules.J(AntiFireball.class);

            if (this.c.theWorld != null) {
                if (this.highRiskFunctionalProbe37CloseFixtureId != 0) {
                    this.c.theWorld.removeEntityFromWorld(
                            this.highRiskFunctionalProbe37CloseFixtureId);
                }
                if (this.highRiskFunctionalProbe37FarFixture != null) {
                    this.highRiskFunctionalProbe37FarFixture.setDead();
                    this.c.theWorld.removeEntity(
                            this.highRiskFunctionalProbe37FarFixture);
                }
                if (this.highRiskFunctionalProbe37FarFixtureId != 0) {
                    this.c.theWorld.removeEntityFromWorld(
                            this.highRiskFunctionalProbe37FarFixtureId);
                }
            }

            AntiFireball.swing.v(
                    this.highRiskFunctionalProbe37OriginalSwing, 0L);
            AntiFireball.range.o(
                    (byte)0, 0L,
                    this.highRiskFunctionalProbe37OriginalRange);
            AntiFireball.fov.o(
                    (byte)0, 0L,
                    this.highRiskFunctionalProbe37OriginalFov);
            AntiFireball.moveFix.i(
                    this.highRiskFunctionalProbe37OriginalMoveFix);

            if (this.c.thePlayer != null) {
                this.c.thePlayer.capabilities.allowFlying =
                        this.highRiskFunctionalProbe37OriginalAllowFlying;
            }

            if (probe != null) {
                if (this.c.theWorld != null && this.c.thePlayer != null) {
                    probe.onPreTick(
                            (char)0,
                            0,
                            (short)0,
                            new PreTickEvent());
                }
                probe.A(0L);
                if (probe.o() != this.highRiskFunctionalProbe37OriginalEnabled) {
                    probe.I(
                            0L,
                            this.highRiskFunctionalProbe37OriginalEnabled);
                }
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe37:AntiFireball",
                    "restore-state",
                    restoreFailure);
        }

        this.highRiskFunctionalProbe37Saved = false;
        this.highRiskFunctionalProbe37CloseFixtureId = 0;
        this.highRiskFunctionalProbe37FarFixtureId = 0;
        this.highRiskFunctionalProbe37FarFixture = null;
        this.highRiskFunctionalProbe37WaitTicks = 0;
    }

    private void pumpHighRiskFunctionalProbe37() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe37")
                || this.highRiskFunctionalProbe37Stage < 0
                || this.highRiskFunctionalProbe37Stage >= 5) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe36")
                && this.highRiskFunctionalProbe36Stage < 6) {
            return;
        }

        AntiFireball probe = Modules.J(AntiFireball.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(AntiFireball.class) != probe
                    || ModuleManager.byName("AntiFireball") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null) {
                throw new IllegalStateException(
                        "AntiFireball live-world authority unavailable");
            }

            List<EntityLargeFireball> tracked =
                    this.antiFireballProbeList(probe, "K");
            List<EntityLargeFireball> ignored =
                    this.antiFireballProbeList(probe, "r");

            switch (this.highRiskFunctionalProbe37Stage) {
                case 0: {
                    this.highRiskFunctionalProbe37OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe37OriginalSwing =
                            AntiFireball.swing.c();
                    this.highRiskFunctionalProbe37OriginalRange =
                            AntiFireball.range.L();
                    this.highRiskFunctionalProbe37OriginalFov =
                            AntiFireball.fov.L();
                    this.highRiskFunctionalProbe37OriginalMoveFix =
                            AntiFireball.moveFix.Y();
                    this.highRiskFunctionalProbe37OriginalAllowFlying =
                            this.c.thePlayer.capabilities.allowFlying;
                    this.highRiskFunctionalProbe37Saved = true;

                    AntiFireball.swing.v(true, 0L);
                    AntiFireball.range.o((byte)0, 0L, 5.0f);
                    AntiFireball.fov.o((byte)0, 0L, 360.0f);
                    AntiFireball.moveFix.i("NONE");
                    this.c.thePlayer.capabilities.allowFlying = false;

                    probe.onWorldLoad(new WorldLoadEvent());
                    if (!tracked.isEmpty()
                            || !ignored.isEmpty()
                            || this.antiFireballProbeTarget(probe) != null) {
                        throw new IllegalStateException(
                                "AntiFireball tracker did not start clean");
                    }

                    if (!probe.o()) {
                        probe.I(0L, true);
                    }

                    this.highRiskFunctionalProbe37Stage = 1;
                    runtimeMilestone(
                            "high-risk-functional-probe37-enable-request:"
                                    + "AntiFireball");
                    return;
                }

                case 1: {
                    if (!probe.o()
                            || !probe.P()
                            || !w.isOwnerActive(probe)) {
                        return;
                    }

                    EntityLargeFireball close =
                            new EntityLargeFireball(this.c.theWorld);
                    close.setPosition(
                            this.c.thePlayer.posX + 2.0,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ);
                    this.highRiskFunctionalProbe37CloseFixtureId = -73701;
                    this.c.theWorld.addEntityToWorld(
                            this.highRiskFunctionalProbe37CloseFixtureId,
                            close);

                    probe.onPreTick(
                            (char)0,
                            0,
                            (short)0,
                            new PreTickEvent());

                    if (!ignored.contains(close)
                            || tracked.contains(close)
                            || this.antiFireballProbeTarget(probe) != null) {
                        throw new IllegalStateException(
                                "AntiFireball close-spawn ignore mismatch"
                                        + " ignored=" + ignored.contains(close)
                                        + " tracked=" + tracked.contains(close)
                                        + " target="
                                        + (this.antiFireballProbeTarget(probe) == close));
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe37-effect-pass:"
                                    + "AntiFireball:closeSpawnIgnored=true");

                    this.c.theWorld.removeEntityFromWorld(
                            this.highRiskFunctionalProbe37CloseFixtureId);
                    this.highRiskFunctionalProbe37CloseFixtureId = 0;
                    probe.onPreTick(
                            (char)0,
                            0,
                            (short)0,
                            new PreTickEvent());

                    EntityLargeFireball far =
                            new EntityLargeFireball(this.c.theWorld);
                    far.setPosition(
                            this.c.thePlayer.posX + 6.0,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ);
                    this.highRiskFunctionalProbe37FarFixtureId = -73702;
                    this.c.theWorld.addEntityToWorld(
                            this.highRiskFunctionalProbe37FarFixtureId,
                            far);

                    probe.onPreTick(
                            (char)0,
                            0,
                            (short)0,
                            new PreTickEvent());

                    if (!tracked.contains(far)
                            || ignored.contains(far)
                            || this.antiFireballProbeTarget(probe) != far) {
                        throw new IllegalStateException(
                                "AntiFireball far tracker/selector mismatch"
                                        + " tracked=" + tracked.contains(far)
                                        + " ignored=" + ignored.contains(far)
                                        + " selected="
                                        + (this.antiFireballProbeTarget(probe) == far));
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe37-effect-pass:"
                                    + "AntiFireball:farTracked=true:selected=true");

                    this.highRiskFunctionalProbe37Stage = 2;
                    return;
                }

                case 2: {
                    EntityLargeFireball far =
                            (EntityLargeFireball)this.c.theWorld.getEntityByID(
                                    this.highRiskFunctionalProbe37FarFixtureId);
                    if (far == null) {
                        throw new IllegalStateException(
                                "AntiFireball tracked fixture disappeared");
                    }

                    far.setPosition(
                            this.c.thePlayer.posX + 2.0,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ);
                    probe.onPreTick(
                            (char)0,
                            0,
                            (short)0,
                            new PreTickEvent());

                    if (!tracked.contains(far)
                            || this.antiFireballProbeTarget(probe) != far) {
                        throw new IllegalStateException(
                                "AntiFireball tracked projectile lost on approach");
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe37-effect-pass:"
                                    + "AntiFireball:approachSelected=true:range=2.0");

                    this.highRiskFunctionalProbe37FarFixture = far;
                    far.setDead();
                    this.c.theWorld.removeEntity(far);
                    this.c.theWorld.removeEntityFromWorld(
                            this.highRiskFunctionalProbe37FarFixtureId);
                    this.highRiskFunctionalProbe37FarFixtureId = 0;
                    this.highRiskFunctionalProbe37WaitTicks = 0;
                    this.highRiskFunctionalProbe37Stage = 3;
                    runtimeMilestone(
                            "high-risk-functional-probe37-removal-request:"
                                    + "AntiFireball:farFixture");
                    return;
                }

                case 3: {
                    ++this.highRiskFunctionalProbe37WaitTicks;
                    EntityLargeFireball far =
                            this.highRiskFunctionalProbe37FarFixture;
                    boolean stillLoaded =
                            far != null
                                    && this.c.theWorld.loadedEntityList.contains(far);
                    if (stillLoaded) {
                        if (this.highRiskFunctionalProbe37WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "AntiFireball fixture remained in loadedEntityList"
                                            + " ticks="
                                            + this.highRiskFunctionalProbe37WaitTicks);
                        }
                        return;
                    }

                    probe.onPreTick(
                            (char)0,
                            0,
                            (short)0,
                            new PreTickEvent());

                    if (!tracked.isEmpty()
                            || !ignored.isEmpty()
                            || this.antiFireballProbeTarget(probe) != null) {
                        throw new IllegalStateException(
                                "AntiFireball tracker did not clear after world removal"
                                        + " tracked=" + tracked.size()
                                        + " ignored=" + ignored.size()
                                        + " target="
                                        + String.valueOf(
                                                this.antiFireballProbeTarget(probe)));
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe37-reset-pass:"
                                    + "AntiFireball:tracked=0:ignored=0:target=null");
                    this.highRiskFunctionalProbe37FarFixture = null;
                    this.highRiskFunctionalProbe37Stage = 4;
                    return;
                }

                case 4: {
                    runtimeMilestone(
                            "high-risk-functional-probe37-module-pass:"
                                    + "AntiFireball");
                    runtimeMilestone(
                            "high-risk-functional-probe37-pass:1");
                    this.highRiskFunctionalProbe37Stage = 5;
                    this.restoreHighRiskFunctionalProbe37();
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe37Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe37:AntiFireball",
                    "tracked-projectile-selection",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe37-fail:"
                            + failure.getClass().getName()
                            + ":"
                            + String.valueOf(failure.getMessage()));
            this.restoreHighRiskFunctionalProbe37();
        }
    }


    private Field blockHitProbeField(String name) throws Exception {
        Field field = BlockHit.class.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    private void restoreHighRiskFunctionalProbe38() {
        if (!this.highRiskFunctionalProbe38Saved) {
            return;
        }
        try {
            BlockHit probe = Modules.J(BlockHit.class);
            if (probe != null) {
                BlockHit.mode.i(this.highRiskFunctionalProbe38OriginalMode);
                BlockHit.predictHurtResistTicks.o(
                        (byte)0, 0L,
                        this.highRiskFunctionalProbe38OriginalHurtTicks);
                BlockHit.predictEarlyTicks.o(
                        (byte)0, 0L,
                        this.highRiskFunctionalProbe38OriginalEarlyTicks);
                BlockHit.predictRandomEarlyTicks.o(
                        (byte)0, 0L,
                        this.highRiskFunctionalProbe38OriginalRandomTicks);

                this.blockHitProbeField("E").setBoolean(
                        probe, this.highRiskFunctionalProbe38OriginalE);
                this.blockHitProbeField("x").setBoolean(
                        probe, this.highRiskFunctionalProbe38OriginalX);
                this.blockHitProbeField("Y").setInt(
                        probe, this.highRiskFunctionalProbe38OriginalY);
                this.blockHitProbeField("y").setInt(
                        probe, this.highRiskFunctionalProbe38OriginalSmallY);

                if (probe.o() != this.highRiskFunctionalProbe38OriginalEnabled) {
                    probe.I(
                            0L,
                            this.highRiskFunctionalProbe38OriginalEnabled);
                }
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe38:BlockHit",
                    "restore-state",
                    restoreFailure);
        }
        this.highRiskFunctionalProbe38Saved = false;
        this.highRiskFunctionalProbe38WaitTicks = 0;
    }

    private void pumpHighRiskFunctionalProbe38() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe38")
                || this.highRiskFunctionalProbe38Stage < 0
                || this.highRiskFunctionalProbe38Stage >= 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe37")
                && this.highRiskFunctionalProbe37Stage < 5) {
            return;
        }

        BlockHit probe = Modules.J(BlockHit.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(BlockHit.class) != probe
                    || ModuleManager.byName("BlockHit") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || BlockHit.mode == null
                    || BlockHit.predictHurtResistTicks == null
                    || BlockHit.predictEarlyTicks == null
                    || BlockHit.predictRandomEarlyTicks == null) {
                throw new IllegalStateException(
                        "BlockHit live-world prediction authority unavailable");
            }

            Field fieldE = this.blockHitProbeField("E");
            Field fieldX = this.blockHitProbeField("x");
            Field fieldY = this.blockHitProbeField("Y");
            Field fieldSmallY = this.blockHitProbeField("y");

            switch (this.highRiskFunctionalProbe38Stage) {
                case 0: {
                    this.highRiskFunctionalProbe38OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe38OriginalMode =
                            BlockHit.mode.Y();
                    this.highRiskFunctionalProbe38OriginalHurtTicks =
                            BlockHit.predictHurtResistTicks.L();
                    this.highRiskFunctionalProbe38OriginalEarlyTicks =
                            BlockHit.predictEarlyTicks.L();
                    this.highRiskFunctionalProbe38OriginalRandomTicks =
                            BlockHit.predictRandomEarlyTicks.L();
                    this.highRiskFunctionalProbe38OriginalE =
                            fieldE.getBoolean(probe);
                    this.highRiskFunctionalProbe38OriginalX =
                            fieldX.getBoolean(probe);
                    this.highRiskFunctionalProbe38OriginalY =
                            fieldY.getInt(probe);
                    this.highRiskFunctionalProbe38OriginalSmallY =
                            fieldSmallY.getInt(probe);
                    this.highRiskFunctionalProbe38Saved = true;
                    this.highRiskFunctionalProbe38WaitTicks = 0;

                    BlockHit.mode.i("PREDICT");
                    BlockHit.predictHurtResistTicks.o(
                            (byte)0, 0L, 10.0f);
                    BlockHit.predictEarlyTicks.o(
                            (byte)0, 0L, 3.0f);
                    BlockHit.predictRandomEarlyTicks.o(
                            (byte)0, 0L, 0.0f);

                    fieldE.setBoolean(probe, false);
                    fieldX.setBoolean(probe, true);
                    fieldY.setInt(probe, 99);
                    fieldSmallY.setInt(probe, 99);

                    if (!probe.o()) {
                        probe.I(0L, true);
                    }
                    this.highRiskFunctionalProbe38Stage = 1;
                    runtimeMilestone(
                            "high-risk-functional-probe38-enable-request:"
                                    + "BlockHit");
                    return;
                }

                case 1: {
                    if (!probe.o()
                            || probe.l()
                            || probe.K()
                            || !probe.P()
                            || !w.isOwnerActive(probe)) {
                        if (++this.highRiskFunctionalProbe38WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "BlockHit did not enable/subscribe");
                        }
                        return;
                    }

                    EventBus fixtureBus = new EventBus();
                    fixtureBus.s(probe, 0L);
                    if (!fixtureBus.isOwnerActive(probe)) {
                        throw new IllegalStateException(
                                "BlockHit fixture EventBus binding inactive");
                    }

                    ReceivePacketEvent event = new ReceivePacketEvent(
                            new S19PacketEntityStatus(
                                    this.c.thePlayer,
                                    (byte)2));
                    fixtureBus.e(event, 0L);

                    boolean effectE = fieldE.getBoolean(probe);
                    boolean effectX = fieldX.getBoolean(probe);
                    int effectY = fieldY.getInt(probe);
                    int effectSmallY = fieldSmallY.getInt(probe);
                    if (!effectE
                            || effectX
                            || effectY != 0
                            || effectSmallY != 7) {
                        throw new IllegalStateException(
                                "BlockHit prediction state mismatch"
                                        + " E=" + effectE
                                        + " x=" + effectX
                                        + " Y=" + effectY
                                        + " y=" + effectSmallY);
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe38-effect-pass:"
                                    + "BlockHit:E=true:x=false:Y=0:y=7");

                    this.restoreHighRiskFunctionalProbe38();
                    this.highRiskFunctionalProbe38Stage = 2;
                    this.highRiskFunctionalProbe38WaitTicks = 0;
                    return;
                }

                case 2: {
                    boolean stableOriginal =
                            this.highRiskFunctionalProbe38OriginalEnabled
                                    ? probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && probe.P()
                                            && w.isOwnerActive(probe)
                                    : !probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && !probe.P()
                                            && !w.isOwnerActive(probe);
                    if (!stableOriginal) {
                        if (++this.highRiskFunctionalProbe38WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "BlockHit did not restore original lifecycle"
                                            + " enabled=" + probe.o()
                                            + " pendingEnable=" + probe.l()
                                            + " pendingDisable=" + probe.K()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe38-restore-pass:"
                                    + "BlockHit:enabled="
                                    + this.highRiskFunctionalProbe38OriginalEnabled);
                    runtimeMilestone(
                            "high-risk-functional-probe38-module-pass:BlockHit");
                    runtimeMilestone(
                            "high-risk-functional-probe38-pass:1");
                    this.highRiskFunctionalProbe38Stage = 3;
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe38Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe38:BlockHit",
                    "predict-status-packet",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe38-fail:"
                            + failure.getClass().getName()
                            + ":"
                            + String.valueOf(failure.getMessage()));
            this.restoreHighRiskFunctionalProbe38();
        }
    }


    private Field speedMineProbeField(String name) throws Exception {
        Field field = SpeedMine.class.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    private void restoreHighRiskFunctionalProbe39() {
        if (!this.highRiskFunctionalProbe39Saved) {
            return;
        }
        try {
            SpeedMine probe = Modules.J(SpeedMine.class);
            SpeedMine.delay.o(
                    (byte)0, 0L,
                    this.highRiskFunctionalProbe39OriginalDelay);
            SpeedMine.delayChance.d(
                    this.highRiskFunctionalProbe39OriginalDelayChance);
            if (probe != null) {
                this.speedMineProbeField("b").set(
                        probe,
                        this.highRiskFunctionalProbe39OriginalLastBlock);
                this.speedMineProbeField("J").setFloat(
                        probe,
                        this.highRiskFunctionalProbe39OriginalPreviousDamage);
                PlayerControllerStateAccessor.w(
                        (byte)0,
                        7374982,
                        11824981,
                        this.c.playerController,
                        this.highRiskFunctionalProbe39OriginalBlockHitDelay);
                this.c.inGameHasFocus =
                        this.highRiskFunctionalProbe39OriginalInGameHasFocus;
                if (probe.o() != this.highRiskFunctionalProbe39OriginalEnabled) {
                    probe.I(
                            0L,
                            this.highRiskFunctionalProbe39OriginalEnabled);
                }
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe39:SpeedMine",
                    "restore-state",
                    restoreFailure);
        }
        this.highRiskFunctionalProbe39Saved = false;
        this.highRiskFunctionalProbe39WaitTicks = 0;
    }

    private void pumpHighRiskFunctionalProbe39() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe39")
                || this.highRiskFunctionalProbe39Stage < 0
                || this.highRiskFunctionalProbe39Stage >= 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe38")
                && this.highRiskFunctionalProbe38Stage < 3) {
            return;
        }

        SpeedMine probe = Modules.J(SpeedMine.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(SpeedMine.class) != probe
                    || ModuleManager.byName("SpeedMine") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || this.c.playerController == null
                    || SpeedMine.delay == null
                    || SpeedMine.delayChance == null) {
                throw new IllegalStateException(
                        "SpeedMine live-world controller authority unavailable");
            }

            Field lastBlockField = this.speedMineProbeField("b");
            Field previousDamageField = this.speedMineProbeField("J");

            switch (this.highRiskFunctionalProbe39Stage) {
                case 0: {
                    this.highRiskFunctionalProbe39OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe39OriginalDelay =
                            SpeedMine.delay.L();
                    this.highRiskFunctionalProbe39OriginalDelayChance =
                            SpeedMine.delayChance.k();
                    this.highRiskFunctionalProbe39OriginalLastBlock =
                            (BlockPos)lastBlockField.get(probe);
                    this.highRiskFunctionalProbe39OriginalPreviousDamage =
                            previousDamageField.getFloat(probe);
                    this.highRiskFunctionalProbe39OriginalBlockHitDelay =
                            PlayerControllerStateAccessor.W(
                                    this.c.playerController);
                    this.highRiskFunctionalProbe39OriginalInGameHasFocus =
                            this.c.inGameHasFocus;
                    this.highRiskFunctionalProbe39Saved = true;
                    this.highRiskFunctionalProbe39WaitTicks = 0;

                    SpeedMine.delay.o((byte)0, 0L, 0.0f);
                    SpeedMine.delayChance.d(100);
                    lastBlockField.set(probe, null);
                    previousDamageField.setFloat(probe, 0.0f);
                    PlayerControllerStateAccessor.w(
                            (byte)0,
                            7374982,
                            11824981,
                            this.c.playerController,
                            4);

                    if (!probe.o()) {
                        probe.I(0L, true);
                    }
                    this.highRiskFunctionalProbe39Stage = 1;
                    runtimeMilestone(
                            "high-risk-functional-probe39-enable-request:"
                                    + "SpeedMine:blockHitDelay=4");
                    return;
                }

                case 1: {
                    if (!probe.o()
                            || probe.l()
                            || probe.K()
                            || !probe.P()
                            || !w.isOwnerActive(probe)) {
                        if (++this.highRiskFunctionalProbe39WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "SpeedMine did not enable/subscribe");
                        }
                        return;
                    }

                    PlayerControllerStateAccessor.w(
                            (byte)0,
                            7374982,
                            11824981,
                            this.c.playerController,
                            4);
                    this.c.inGameHasFocus = true;

                    EventBus fixtureBus = new EventBus();
                    fixtureBus.s(probe, 0L);
                    if (!fixtureBus.isOwnerActive(probe)) {
                        throw new IllegalStateException(
                                "SpeedMine fixture EventBus binding inactive");
                    }
                    fixtureBus.e(new PostTickEvent(), 0L);

                    int effectDelay = PlayerControllerStateAccessor.W(
                            this.c.playerController);
                    if (effectDelay != 0) {
                        throw new IllegalStateException(
                                "SpeedMine did not clear blockHitDelay"
                                        + " expected=0 actual="
                                        + effectDelay);
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe39-effect-pass:"
                                    + "SpeedMine:blockHitDelay=4->0"
                                    + ":fixtureFocus=true"
                                    + ":originalFocus="
                                    + this.highRiskFunctionalProbe39OriginalInGameHasFocus);

                    this.restoreHighRiskFunctionalProbe39();
                    this.highRiskFunctionalProbe39Stage = 2;
                    this.highRiskFunctionalProbe39WaitTicks = 0;
                    return;
                }

                case 2: {
                    boolean stableOriginal =
                            this.highRiskFunctionalProbe39OriginalEnabled
                                    ? probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && probe.P()
                                            && w.isOwnerActive(probe)
                                    : !probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && !probe.P()
                                            && !w.isOwnerActive(probe);
                    if (!stableOriginal) {
                        if (++this.highRiskFunctionalProbe39WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "SpeedMine did not restore original lifecycle"
                                            + " enabled=" + probe.o()
                                            + " pendingEnable=" + probe.l()
                                            + " pendingDisable=" + probe.K()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }

                    SpeedMine.delay.o(
                            (byte)0, 0L,
                            this.highRiskFunctionalProbe39OriginalDelay);
                    SpeedMine.delayChance.d(
                            this.highRiskFunctionalProbe39OriginalDelayChance);
                    lastBlockField.set(
                            probe,
                            this.highRiskFunctionalProbe39OriginalLastBlock);
                    previousDamageField.setFloat(
                            probe,
                            this.highRiskFunctionalProbe39OriginalPreviousDamage);
                    PlayerControllerStateAccessor.w(
                            (byte)0,
                            7374982,
                            11824981,
                            this.c.playerController,
                            this.highRiskFunctionalProbe39OriginalBlockHitDelay);
                    this.c.inGameHasFocus =
                            this.highRiskFunctionalProbe39OriginalInGameHasFocus;

                    int restoredDelay = PlayerControllerStateAccessor.W(
                            this.c.playerController);
                    if (restoredDelay
                            != this.highRiskFunctionalProbe39OriginalBlockHitDelay) {
                        throw new IllegalStateException(
                                "SpeedMine controller delay restore mismatch"
                                        + " expected="
                                        + this.highRiskFunctionalProbe39OriginalBlockHitDelay
                                        + " actual="
                                        + restoredDelay);
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe39-restore-pass:"
                                    + "SpeedMine:enabled="
                                    + this.highRiskFunctionalProbe39OriginalEnabled
                                    + ":blockHitDelay="
                                    + restoredDelay);
                    runtimeMilestone(
                            "high-risk-functional-probe39-module-pass:SpeedMine");
                    runtimeMilestone(
                            "high-risk-functional-probe39-pass:1");
                    this.highRiskFunctionalProbe39Stage = 3;
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe39Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe39:SpeedMine",
                    "posttick-block-hit-delay",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe39-fail:"
                            + failure.getClass().getName()
                            + ":"
                            + String.valueOf(failure.getMessage()));
            this.restoreHighRiskFunctionalProbe39();
        }
    }


    private void applyHighRiskFunctionalProbe40Fixture() {
        if (this.c.thePlayer == null
                || this.highRiskFunctionalProbe40FixtureMouseOver == null) {
            return;
        }
        this.c.thePlayer.inventory.mainInventory[0] =
                new ItemStack(Items.wooden_sword);
        this.c.thePlayer.inventory.mainInventory[4] =
                new ItemStack(Items.diamond_sword);
        this.c.objectMouseOver =
                this.highRiskFunctionalProbe40FixtureMouseOver;
    }

    private void restoreHighRiskFunctionalProbe40() {
        if (!this.highRiskFunctionalProbe40Saved) {
            return;
        }
        try {
            AutoWeapon probe = Modules.J(AutoWeapon.class);
            AutoWeapon.axeIsWeapon.v(
                    this.highRiskFunctionalProbe40OriginalAxe, 0L);
            AutoWeapon.stickIsWeapon.v(
                    this.highRiskFunctionalProbe40OriginalStick, 0L);
            AutoWeapon.fishingrodIsWeapon.v(
                    this.highRiskFunctionalProbe40OriginalFishingRod, 0L);

            if (this.c.thePlayer != null) {
                this.c.thePlayer.inventory.mainInventory[0] =
                        this.highRiskFunctionalProbe40OriginalSlot0;
                this.c.thePlayer.inventory.mainInventory[4] =
                        this.highRiskFunctionalProbe40OriginalSlot4;
                ItemUtil.P(
                        this.highRiskFunctionalProbe40OriginalCurrentItem);
            }
            this.c.objectMouseOver =
                    this.highRiskFunctionalProbe40OriginalMouseOver;

            if (probe != null
                    && probe.o()
                            != this.highRiskFunctionalProbe40OriginalEnabled) {
                probe.I(
                        0L,
                        this.highRiskFunctionalProbe40OriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe40:AutoWeapon",
                    "restore-state",
                    restoreFailure);
        }
        this.highRiskFunctionalProbe40Saved = false;
        this.highRiskFunctionalProbe40WaitTicks = 0;
    }

    private void pumpHighRiskFunctionalProbe40() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe40")
                || this.highRiskFunctionalProbe40Stage < 0
                || this.highRiskFunctionalProbe40Stage >= 5) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe39")
                && this.highRiskFunctionalProbe39Stage < 3) {
            return;
        }

        AutoWeapon probe = Modules.J(AutoWeapon.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(AutoWeapon.class) != probe
                    || ModuleManager.byName("AutoWeapon") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || this.c.playerController == null
                    || AutoWeapon.axeIsWeapon == null
                    || AutoWeapon.stickIsWeapon == null
                    || AutoWeapon.fishingrodIsWeapon == null) {
                throw new IllegalStateException(
                        "AutoWeapon live-world inventory authority unavailable");
            }

            switch (this.highRiskFunctionalProbe40Stage) {
                case 0: {
                    this.highRiskFunctionalProbe40OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe40OriginalAxe =
                            AutoWeapon.axeIsWeapon.c();
                    this.highRiskFunctionalProbe40OriginalStick =
                            AutoWeapon.stickIsWeapon.c();
                    this.highRiskFunctionalProbe40OriginalFishingRod =
                            AutoWeapon.fishingrodIsWeapon.c();
                    this.highRiskFunctionalProbe40OriginalSlot0 =
                            this.c.thePlayer.inventory.mainInventory[0];
                    this.highRiskFunctionalProbe40OriginalSlot4 =
                            this.c.thePlayer.inventory.mainInventory[4];
                    this.highRiskFunctionalProbe40OriginalCurrentItem =
                            this.c.thePlayer.inventory.currentItem;
                    this.highRiskFunctionalProbe40OriginalMouseOver =
                            this.c.objectMouseOver;

                    EntityZombie target =
                            new EntityZombie(this.c.theWorld);
                    target.setPosition(
                            this.c.thePlayer.posX + 1.5,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ + 1.5);
                    this.highRiskFunctionalProbe40FixtureMouseOver =
                            new MovingObjectPosition(target);
                    this.highRiskFunctionalProbe40Saved = true;
                    this.highRiskFunctionalProbe40WaitTicks = 0;

                    AutoWeapon.axeIsWeapon.v(false, 0L);
                    AutoWeapon.stickIsWeapon.v(false, 0L);
                    AutoWeapon.fishingrodIsWeapon.v(false, 0L);
                    this.applyHighRiskFunctionalProbe40Fixture();
                    ItemUtil.P(0);

                    if (!probe.o()) {
                        probe.I(0L, true);
                    }
                    this.highRiskFunctionalProbe40Stage = 1;
                    runtimeMilestone(
                            "high-risk-functional-probe40-enable-request:"
                                    + "AutoWeapon");
                    return;
                }

                case 1: {
                    if (!probe.o()
                            || probe.l()
                            || probe.K()
                            || !probe.P()
                            || !w.isOwnerActive(probe)) {
                        if (++this.highRiskFunctionalProbe40WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "AutoWeapon did not enable/subscribe");
                        }
                        return;
                    }

                    this.applyHighRiskFunctionalProbe40Fixture();
                    ItemUtil.P(0);
                    int best = AutoWeapon.M(93384294372710L);
                    if (best != 4) {
                        throw new IllegalStateException(
                                "AutoWeapon weapon ranking mismatch"
                                        + " expected=4 actual="
                                        + best);
                    }

                    this.highRiskFunctionalProbe40Stage = 2;
                    this.highRiskFunctionalProbe40WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe40-ready:"
                                    + "AutoWeapon:mouse=left:best=4");
                    return;
                }

                case 2: {
                    ++this.highRiskFunctionalProbe40WaitTicks;
                    int attackKey =
                            this.c.gameSettings.keyBindAttack.getKeyCode();
                    if (!KeyBindUtil.V(
                            attackKey, 64165991731362L)) {
                        if (this.highRiskFunctionalProbe40WaitTicks > 480) {
                            throw new IllegalStateException(
                                    "AutoWeapon physical mouse-down timeout");
                        }
                        return;
                    }

                    this.applyHighRiskFunctionalProbe40Fixture();
                    ItemUtil.P(0);

                    EventBus fixtureBus = new EventBus();
                    fixtureBus.s(probe, 0L);
                    if (!fixtureBus.isOwnerActive(probe)) {
                        throw new IllegalStateException(
                                "AutoWeapon fixture EventBus binding inactive");
                    }
                    fixtureBus.e(new PreUpdateEvent(0, 0, 0), 0L);

                    int selected =
                            this.c.thePlayer.inventory.currentItem;
                    if (selected != 4) {
                        throw new IllegalStateException(
                                "AutoWeapon did not switch to best weapon"
                                        + " expected=4 actual="
                                        + selected);
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe40-effect-pass:"
                                    + "AutoWeapon:switch=0->4:physicalLmb=true");
                    runtimeMilestone(
                            "high-risk-functional-probe40-ready-release:"
                                    + "AutoWeapon:mouse=left");
                    this.highRiskFunctionalProbe40Stage = 3;
                    this.highRiskFunctionalProbe40WaitTicks = 0;
                    return;
                }

                case 3: {
                    ++this.highRiskFunctionalProbe40WaitTicks;
                    int attackKey =
                            this.c.gameSettings.keyBindAttack.getKeyCode();
                    if (KeyBindUtil.V(
                            attackKey, 64165991731362L)) {
                        if (this.highRiskFunctionalProbe40WaitTicks > 480) {
                            throw new IllegalStateException(
                                    "AutoWeapon physical mouse-release timeout");
                        }
                        return;
                    }

                    this.restoreHighRiskFunctionalProbe40();
                    this.highRiskFunctionalProbe40Stage = 4;
                    this.highRiskFunctionalProbe40WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe40-restore-request:"
                                    + "AutoWeapon:enabled="
                                    + this.highRiskFunctionalProbe40OriginalEnabled);
                    return;
                }

                case 4: {
                    boolean stableOriginal =
                            this.highRiskFunctionalProbe40OriginalEnabled
                                    ? probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && probe.P()
                                            && w.isOwnerActive(probe)
                                    : !probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && !probe.P()
                                            && !w.isOwnerActive(probe);
                    if (!stableOriginal) {
                        if (++this.highRiskFunctionalProbe40WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "AutoWeapon did not restore original lifecycle"
                                            + " enabled=" + probe.o()
                                            + " pendingEnable=" + probe.l()
                                            + " pendingDisable=" + probe.K()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }

                    AutoWeapon.axeIsWeapon.v(
                            this.highRiskFunctionalProbe40OriginalAxe, 0L);
                    AutoWeapon.stickIsWeapon.v(
                            this.highRiskFunctionalProbe40OriginalStick, 0L);
                    AutoWeapon.fishingrodIsWeapon.v(
                            this.highRiskFunctionalProbe40OriginalFishingRod,
                            0L);
                    this.c.thePlayer.inventory.mainInventory[0] =
                            this.highRiskFunctionalProbe40OriginalSlot0;
                    this.c.thePlayer.inventory.mainInventory[4] =
                            this.highRiskFunctionalProbe40OriginalSlot4;
                    ItemUtil.P(
                            this.highRiskFunctionalProbe40OriginalCurrentItem);
                    this.c.objectMouseOver =
                            this.highRiskFunctionalProbe40OriginalMouseOver;

                    if (this.c.thePlayer.inventory.mainInventory[0]
                                    != this.highRiskFunctionalProbe40OriginalSlot0
                            || this.c.thePlayer.inventory.mainInventory[4]
                                    != this.highRiskFunctionalProbe40OriginalSlot4
                            || this.c.thePlayer.inventory.currentItem
                                    != this.highRiskFunctionalProbe40OriginalCurrentItem
                            || this.c.objectMouseOver
                                    != this.highRiskFunctionalProbe40OriginalMouseOver) {
                        throw new IllegalStateException(
                                "AutoWeapon inventory/mouse fixture did not restore");
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe40-restore-pass:"
                                    + "AutoWeapon:enabled="
                                    + this.highRiskFunctionalProbe40OriginalEnabled
                                    + ":slot="
                                    + this.highRiskFunctionalProbe40OriginalCurrentItem);
                    runtimeMilestone(
                            "high-risk-functional-probe40-module-pass:AutoWeapon");
                    runtimeMilestone(
                            "high-risk-functional-probe40-pass:1");
                    this.highRiskFunctionalProbe40Stage = 5;
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe40Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe40:AutoWeapon",
                    "physical-best-weapon-switch",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe40-fail:"
                            + failure.getClass().getName()
                            + ":"
                            + String.valueOf(failure.getMessage()));
            this.restoreHighRiskFunctionalProbe40();
        }
    }

    private void restoreHighRiskFunctionalProbe41() {
        if (!this.highRiskFunctionalProbe41Saved) {
            return;
        }
        try {
            LagRange probe = Modules.J(LagRange.class);
            if (this.highRiskFunctionalProbe41FixtureId != 0
                    && this.c.theWorld != null) {
                this.c.theWorld.removeEntityFromWorld(
                        this.highRiskFunctionalProbe41FixtureId);
            }
            this.highRiskFunctionalProbe41FixtureId = 0;

            PacketManager.j();
            PacketManager.M(this.highRiskFunctionalProbe41OriginalPacketBuffer);

            LagRange.players.v(this.highRiskFunctionalProbe41OriginalPlayers, 0L);
            LagRange.mobs.v(this.highRiskFunctionalProbe41OriginalMobs, 0L);
            LagRange.animals.v(this.highRiskFunctionalProbe41OriginalAnimals, 0L);
            LagRange.bosses.v(this.highRiskFunctionalProbe41OriginalBosses, 0L);
            LagRange.friends.v(this.highRiskFunctionalProbe41OriginalFriends, 0L);
            LagRange.enemies.v(this.highRiskFunctionalProbe41OriginalEnemies, 0L);
            LagRange.teammates.v(this.highRiskFunctionalProbe41OriginalTeammates, 0L);
            LagRange.bots.v(this.highRiskFunctionalProbe41OriginalBots, 0L);
            LagRange.s.v(this.highRiskFunctionalProbe41OriginalSwordOnly, 0L);
            LagRange.delay.o((byte)0, 0L, this.highRiskFunctionalProbe41OriginalDelay);
            LagRange.targetRange.o((byte)0, 0L, this.highRiskFunctionalProbe41OriginalTargetRange);
            LagRange.disableRange.o((byte)0, 0L, this.highRiskFunctionalProbe41OriginalDisableRange);
            LagRange.fov.o((byte)0, 0L, this.highRiskFunctionalProbe41OriginalFov);

            if (probe != null
                    && probe.o() != this.highRiskFunctionalProbe41OriginalEnabled) {
                probe.I(0L, this.highRiskFunctionalProbe41OriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe41:LagRange",
                    "restore-state",
                    restoreFailure);
        }
        this.highRiskFunctionalProbe41Saved = false;
        this.highRiskFunctionalProbe41WaitTicks = 0;
    }

    @SuppressWarnings("unchecked")
    private void pumpHighRiskFunctionalProbe41() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe41")
                || this.highRiskFunctionalProbe41Stage < 0
                || this.highRiskFunctionalProbe41Stage >= 5) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe40")
                && this.highRiskFunctionalProbe40Stage < 5) {
            return;
        }

        LagRange probe = Modules.J(LagRange.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(LagRange.class) != probe
                    || ModuleManager.byName("LagRange") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null) {
                throw new IllegalStateException(
                        "LagRange live-world buffering authority unavailable");
            }

            switch (this.highRiskFunctionalProbe41Stage) {
                case 0: {
                    this.highRiskFunctionalProbe41OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe41OriginalPlayers = LagRange.players.c();
                    this.highRiskFunctionalProbe41OriginalMobs = LagRange.mobs.c();
                    this.highRiskFunctionalProbe41OriginalAnimals = LagRange.animals.c();
                    this.highRiskFunctionalProbe41OriginalBosses = LagRange.bosses.c();
                    this.highRiskFunctionalProbe41OriginalFriends = LagRange.friends.c();
                    this.highRiskFunctionalProbe41OriginalEnemies = LagRange.enemies.c();
                    this.highRiskFunctionalProbe41OriginalTeammates = LagRange.teammates.c();
                    this.highRiskFunctionalProbe41OriginalBots = LagRange.bots.c();
                    this.highRiskFunctionalProbe41OriginalSwordOnly = LagRange.s.c();
                    this.highRiskFunctionalProbe41OriginalDelay = LagRange.delay.L();
                    this.highRiskFunctionalProbe41OriginalTargetRange = LagRange.targetRange.L();
                    this.highRiskFunctionalProbe41OriginalDisableRange = LagRange.disableRange.L();
                    this.highRiskFunctionalProbe41OriginalFov = LagRange.fov.L();
                    this.highRiskFunctionalProbe41OriginalPacketBuffer = PacketManager.e();
                    this.highRiskFunctionalProbe41Saved = true;

                    PacketManager.j();
                    PacketManager.M(false);

                    LagRange.players.v(false, 0L);
                    LagRange.mobs.v(true, 0L);
                    LagRange.animals.v(false, 0L);
                    LagRange.bosses.v(false, 0L);
                    LagRange.friends.v(false, 0L);
                    LagRange.enemies.v(true, 0L);
                    LagRange.teammates.v(false, 0L);
                    LagRange.bots.v(false, 0L);
                    LagRange.s.v(false, 0L);
                    LagRange.delay.o((byte)0, 0L, 1000.0f);
                    LagRange.targetRange.o((byte)0, 0L, 8.0f);
                    LagRange.disableRange.o((byte)0, 0L, 3.0f);
                    LagRange.fov.o((byte)0, 0L, 360.0f);

                    EntityZombie fixture = new EntityZombie(this.c.theWorld);
                    fixture.setPosition(
                            this.c.thePlayer.posX + 5.0,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ);
                    this.highRiskFunctionalProbe41FixtureId = -74141;
                    this.c.theWorld.addEntityToWorld(
                            this.highRiskFunctionalProbe41FixtureId,
                            fixture);

                    if (!probe.o()) {
                        probe.I(0L, true);
                    }
                    this.highRiskFunctionalProbe41WaitTicks = 0;
                    this.highRiskFunctionalProbe41Stage = 1;
                    runtimeMilestone(
                            "high-risk-functional-probe41-enable-request:LagRange");
                    return;
                }

                case 1: {
                    if (!probe.o()
                            || probe.l()
                            || probe.K()
                            || !probe.P()
                            || !w.isOwnerActive(probe)) {
                        if (++this.highRiskFunctionalProbe41WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "LagRange did not enable/subscribe");
                        }
                        return;
                    }

                    net.minecraft.entity.Entity fixture =
                            this.c.theWorld.getEntityByID(
                                    this.highRiskFunctionalProbe41FixtureId);
                    if (!(fixture instanceof EntityZombie)) {
                        throw new IllegalStateException(
                                "LagRange target fixture disappeared");
                    }

                    probe.onPreLivingUpdate(new PreLivingUpdateEvent(0, (byte)0, 0), 0L);

                    Field targetsField = null;
                    for (Field field : LagRange.class.getDeclaredFields()) {
                        if (Set.class.isAssignableFrom(field.getType())) {
                            targetsField = field;
                            break;
                        }
                    }
                    if (targetsField == null) {
                        throw new IllegalStateException(
                                "LagRange target-set field not found");
                    }
                    targetsField.setAccessible(true);
                    Set<net.minecraft.entity.EntityLivingBase> targets =
                            (Set<net.minecraft.entity.EntityLivingBase>)
                                    targetsField.get(probe);
                    if (targets == null || !targets.contains(fixture)) {
                        throw new IllegalStateException(
                                "LagRange did not select fixture in lag band"
                                        + " size="
                                        + (targets == null ? -1 : targets.size()));
                    }

                    Field timerField = null;
                    for (Field field : LagRange.class.getDeclaredFields()) {
                        if (TimerUtil.class.isAssignableFrom(field.getType())) {
                            timerField = field;
                            break;
                        }
                    }
                    if (timerField == null) {
                        throw new IllegalStateException(
                                "LagRange delay timer field not found");
                    }
                    timerField.setAccessible(true);
                    TimerUtil delayTimer = (TimerUtil)timerField.get(probe);
                    if (delayTimer == null) {
                        throw new IllegalStateException(
                                "LagRange delay timer was null");
                    }
                    delayTimer.W();

                    runtimeMilestone(
                            "high-risk-functional-probe41-target-pass:"
                                    + "LagRange:distance=5.0");
                    this.highRiskFunctionalProbe41Stage = 2;
                    return;
                }

                case 2: {
                    probe.onRender2D(
                            (short)0,
                            new Render2DEvent(
                                    0,
                                    (short)0,
                                    0.0f,
                                    (short)0,
                                    new ScaledResolution(this.c)),
                            0L);

                    if (!PacketManager.e()) {
                        throw new IllegalStateException(
                                "LagRange did not enable outgoing packet buffer");
                    }
                    runtimeMilestone(
                            "high-risk-functional-probe41-effect-pass:"
                                    + "LagRange:packetBuffer=true");

                    net.minecraft.entity.Entity fixture =
                            this.c.theWorld.getEntityByID(
                                    this.highRiskFunctionalProbe41FixtureId);
                    if (!(fixture instanceof EntityZombie)) {
                        throw new IllegalStateException(
                                "LagRange target fixture disappeared before clear");
                    }
                    fixture.setPosition(
                            this.c.thePlayer.posX + 1.0,
                            this.c.thePlayer.posY,
                            this.c.thePlayer.posZ);
                    this.highRiskFunctionalProbe41Stage = 3;
                    return;
                }

                case 3: {
                    probe.onPreLivingUpdate(new PreLivingUpdateEvent(0, (byte)0, 0), 0L);
                    probe.onRender2D(
                            (short)0,
                            new Render2DEvent(
                                    0,
                                    (short)0,
                                    0.0f,
                                    (short)0,
                                    new ScaledResolution(this.c)),
                            0L);

                    if (PacketManager.e()) {
                        throw new IllegalStateException(
                                "LagRange did not disable/flush packet buffer"
                                        + " after target entered disable range");
                    }
                    runtimeMilestone(
                            "high-risk-functional-probe41-effect-pass:"
                                    + "LagRange:packetBuffer=false:distance=1.0");

                    this.restoreHighRiskFunctionalProbe41();
                    this.highRiskFunctionalProbe41Stage = 4;
                    this.highRiskFunctionalProbe41WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe41-restore-request:"
                                    + "LagRange:enabled="
                                    + this.highRiskFunctionalProbe41OriginalEnabled);
                    return;
                }

                case 4: {
                    boolean stableOriginal =
                            this.highRiskFunctionalProbe41OriginalEnabled
                                    ? probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && probe.P()
                                            && w.isOwnerActive(probe)
                                    : !probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && !probe.P()
                                            && !w.isOwnerActive(probe);
                    if (!stableOriginal) {
                        if (++this.highRiskFunctionalProbe41WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "LagRange did not restore original lifecycle"
                                            + " enabled=" + probe.o()
                                            + " pendingEnable=" + probe.l()
                                            + " pendingDisable=" + probe.K()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }

                    if (PacketManager.e()
                            != this.highRiskFunctionalProbe41OriginalPacketBuffer
                            || LagRange.players.c()
                                    != this.highRiskFunctionalProbe41OriginalPlayers
                            || LagRange.mobs.c()
                                    != this.highRiskFunctionalProbe41OriginalMobs
                            || Math.abs(
                                    LagRange.targetRange.L()
                                            - this.highRiskFunctionalProbe41OriginalTargetRange)
                                    > 0.0001f
                            || Math.abs(
                                    LagRange.disableRange.L()
                                            - this.highRiskFunctionalProbe41OriginalDisableRange)
                                    > 0.0001f) {
                        throw new IllegalStateException(
                                "LagRange settings/buffer state did not restore");
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe41-restore-pass:"
                                    + "LagRange:enabled="
                                    + this.highRiskFunctionalProbe41OriginalEnabled
                                    + ":packetBuffer="
                                    + this.highRiskFunctionalProbe41OriginalPacketBuffer);
                    runtimeMilestone(
                            "high-risk-functional-probe41-module-pass:LagRange");
                    runtimeMilestone(
                            "high-risk-functional-probe41-pass:1");
                    this.highRiskFunctionalProbe41Stage = 5;
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe41Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe41:LagRange",
                    "target-band-packet-buffer-state-machine",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe41-fail:"
                            + failure.getClass().getName()
                            + ":"
                            + String.valueOf(failure.getMessage()));
            this.restoreHighRiskFunctionalProbe41();
        }
    }

    private void restoreHighRiskFunctionalProbe42() {
        if (!this.highRiskFunctionalProbe42Saved) {
            return;
        }
        try {
            Fly probe = Modules.J(Fly.class);
            if (probe != null) {
                this.setModuleEnabledRawForProbe(probe, false);
            }
            Fly.horizontalSpeed.o(
                    (byte)0, 0L,
                    this.highRiskFunctionalProbe42OriginalHorizontalSpeed);
            Fly.verticalSpeed.o(
                    (byte)0, 0L,
                    this.highRiskFunctionalProbe42OriginalVerticalSpeed);
            KeyBindUtil.A(
                    0L,
                    this.c.gameSettings.keyBindSneak.getKeyCode(),
                    this.highRiskFunctionalProbe42OriginalSneakPressed);
            if (probe != null
                    && probe.o()
                            != this.highRiskFunctionalProbe42OriginalEnabled) {
                probe.I(0L, this.highRiskFunctionalProbe42OriginalEnabled);
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe42:Fly",
                    "restore-state",
                    restoreFailure);
        }
        this.highRiskFunctionalProbe42WaitTicks = 0;
    }

    private void pumpHighRiskFunctionalProbe42() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe42")
                || this.highRiskFunctionalProbe42Stage < 0
                || this.highRiskFunctionalProbe42Stage >= 6) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe41")
                && this.highRiskFunctionalProbe41Stage < 5) {
            return;
        }

        Fly probe = Modules.J(Fly.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(Fly.class) != probe
                    || ModuleManager.byName("Fly") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || Fly.horizontalSpeed == null
                    || Fly.verticalSpeed == null) {
                throw new IllegalStateException(
                        "Fly live-world movement authority unavailable");
            }

            switch (this.highRiskFunctionalProbe42Stage) {
                case 0: {
                    this.highRiskFunctionalProbe42OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe42OriginalHorizontalSpeed =
                            Fly.horizontalSpeed.L();
                    this.highRiskFunctionalProbe42OriginalVerticalSpeed =
                            Fly.verticalSpeed.L();
                    this.highRiskFunctionalProbe42OriginalSneakPressed =
                            this.c.gameSettings.keyBindSneak.isKeyDown();
                    this.highRiskFunctionalProbe42Saved = true;

                    Fly.horizontalSpeed.o((byte)0, 0L, 1.5f);
                    Fly.verticalSpeed.o((byte)0, 0L, 2.0f);

                    if (probe.o()) {
                        probe.I(0L, false);
                        this.highRiskFunctionalProbe42Stage = 1;
                        this.highRiskFunctionalProbe42WaitTicks = 0;
                        runtimeMilestone(
                                "high-risk-functional-probe42-isolate-request:"
                                        + "Fly");
                        return;
                    }
                    this.highRiskFunctionalProbe42Stage = 2;
                    this.highRiskFunctionalProbe42WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe42-ready:"
                                    + "Fly:key=space");
                    return;
                }

                case 1: {
                    if (probe.o()
                            || probe.l()
                            || probe.K()
                            || probe.P()
                            || w.isOwnerActive(probe)) {
                        if (++this.highRiskFunctionalProbe42WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "Fly did not isolate from live lifecycle"
                                            + " enabled=" + probe.o()
                                            + " pendingEnable=" + probe.l()
                                            + " pendingDisable=" + probe.K()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }
                    this.highRiskFunctionalProbe42Stage = 2;
                    this.highRiskFunctionalProbe42WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe42-ready:"
                                    + "Fly:key=space");
                    return;
                }

                case 2: {
                    ++this.highRiskFunctionalProbe42WaitTicks;
                    int jumpKey =
                            this.c.gameSettings.keyBindJump.getKeyCode();
                    if (!KeyBindUtil.V(jumpKey, 64165991731362L)) {
                        if (this.highRiskFunctionalProbe42WaitTicks > 480) {
                            throw new IllegalStateException(
                                    "Fly physical Space key-down timeout");
                        }
                        return;
                    }

                    double originalX = this.c.thePlayer.posX;
                    double originalY = this.c.thePlayer.posY;
                    double originalZ = this.c.thePlayer.posZ;
                    double originalMotionX = this.c.thePlayer.motionX;
                    double originalMotionY = this.c.thePlayer.motionY;
                    double originalMotionZ = this.c.thePlayer.motionZ;
                    float originalMoveForward = this.c.thePlayer.moveForward;
                    float originalMoveStrafing = this.c.thePlayer.moveStrafing;

                    try {
                        this.setModuleEnabledRawForProbe(probe, true);
                        EventBus fixtureBus = new EventBus();
                        fixtureBus.s(probe, 0L);
                        if (!fixtureBus.isOwnerActive(probe)) {
                            throw new IllegalStateException(
                                    "Fly fixture EventBus binding inactive");
                        }

                        fixtureBus.e(new PreUpdateEvent(0, 0, 0), 0L);

                        Field verticalField =
                                Fly.class.getDeclaredField("K");
                        verticalField.setAccessible(true);
                        double vertical =
                                verticalField.getDouble(probe);
                        double expectedVertical =
                                (double)(Fly.verticalSpeed.L() * 0.42f);
                        if (Math.abs(vertical - expectedVertical) > 0.00001) {
                            throw new IllegalStateException(
                                    "Fly vertical accumulator mismatch"
                                            + " expected=" + expectedVertical
                                            + " actual=" + vertical);
                        }
                        runtimeMilestone(
                                "high-risk-functional-probe42-effect-pass:"
                                        + "Fly:verticalAccumulator="
                                        + vertical
                                        + ":physicalSpace=true");

                        double fixtureY =
                                Math.floor(originalY) + 0.25;
                        this.c.thePlayer.setPosition(
                                originalX,
                                fixtureY,
                                originalZ);

                        MoveFlyingEvent moveEvent =
                                new MoveFlyingEvent(0.0f, 0.0f, 0.0f);
                        fixtureBus.e(moveEvent, 0L);

                        double expectedHorizontal =
                                MoveUtil.A()
                                        * (double)Fly.horizontalSpeed.L();
                        if (Math.abs(
                                        this.c.thePlayer.motionY
                                                - expectedVertical)
                                        > 0.00001
                                || Math.abs(
                                        (double)moveEvent.p()
                                                - expectedHorizontal)
                                        > 0.0001) {
                            throw new IllegalStateException(
                                    "Fly MoveFlying effect mismatch"
                                            + " motionY="
                                            + this.c.thePlayer.motionY
                                            + " expectedY="
                                            + expectedVertical
                                            + " speed="
                                            + moveEvent.p()
                                            + " expectedSpeed="
                                            + expectedHorizontal);
                        }

                        runtimeMilestone(
                                "high-risk-functional-probe42-effect-pass:"
                                        + "Fly:motionY="
                                        + this.c.thePlayer.motionY
                                        + ":speed="
                                        + moveEvent.p()
                                        + ":physicalSpace=true");
                    }
                    finally {
                        this.setModuleEnabledRawForProbe(probe, false);
                        this.c.thePlayer.setPosition(
                                originalX,
                                originalY,
                                originalZ);
                        this.c.thePlayer.motionX = originalMotionX;
                        this.c.thePlayer.motionY = originalMotionY;
                        this.c.thePlayer.motionZ = originalMotionZ;
                        this.c.thePlayer.moveForward = originalMoveForward;
                        this.c.thePlayer.moveStrafing = originalMoveStrafing;
                        KeyBindUtil.A(
                                0L,
                                this.c.gameSettings.keyBindSneak.getKeyCode(),
                                this.highRiskFunctionalProbe42OriginalSneakPressed);
                    }

                    this.highRiskFunctionalProbe42Stage = 3;
                    this.highRiskFunctionalProbe42WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe42-ready-release:"
                                    + "Fly:key=space");
                    return;
                }

                case 3: {
                    ++this.highRiskFunctionalProbe42WaitTicks;
                    int jumpKey =
                            this.c.gameSettings.keyBindJump.getKeyCode();
                    if (KeyBindUtil.V(jumpKey, 64165991731362L)) {
                        if (this.highRiskFunctionalProbe42WaitTicks > 480) {
                            throw new IllegalStateException(
                                    "Fly physical Space key-release timeout");
                        }
                        return;
                    }

                    Fly.horizontalSpeed.o(
                            (byte)0, 0L,
                            this.highRiskFunctionalProbe42OriginalHorizontalSpeed);
                    Fly.verticalSpeed.o(
                            (byte)0, 0L,
                            this.highRiskFunctionalProbe42OriginalVerticalSpeed);
                    KeyBindUtil.A(
                            0L,
                            this.c.gameSettings.keyBindSneak.getKeyCode(),
                            this.highRiskFunctionalProbe42OriginalSneakPressed);
                    if (this.highRiskFunctionalProbe42OriginalEnabled) {
                        probe.I(0L, true);
                    }
                    this.highRiskFunctionalProbe42Stage = 4;
                    this.highRiskFunctionalProbe42WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe42-restore-request:"
                                    + "Fly:enabled="
                                    + this.highRiskFunctionalProbe42OriginalEnabled);
                    return;
                }

                case 4: {
                    boolean stableOriginal =
                            this.highRiskFunctionalProbe42OriginalEnabled
                                    ? probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && probe.P()
                                            && w.isOwnerActive(probe)
                                    : !probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && !probe.P()
                                            && !w.isOwnerActive(probe);
                    if (!stableOriginal) {
                        if (++this.highRiskFunctionalProbe42WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "Fly did not restore original lifecycle"
                                            + " enabled=" + probe.o()
                                            + " pendingEnable=" + probe.l()
                                            + " pendingDisable=" + probe.K()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }

                    if (Math.abs(
                                    Fly.horizontalSpeed.L()
                                            - this.highRiskFunctionalProbe42OriginalHorizontalSpeed)
                                    > 0.0001f
                            || Math.abs(
                                    Fly.verticalSpeed.L()
                                            - this.highRiskFunctionalProbe42OriginalVerticalSpeed)
                                    > 0.0001f) {
                        throw new IllegalStateException(
                                "Fly settings did not restore");
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe42-restore-pass:"
                                    + "Fly:enabled="
                                    + this.highRiskFunctionalProbe42OriginalEnabled);
                    runtimeMilestone(
                            "high-risk-functional-probe42-module-pass:Fly");
                    runtimeMilestone(
                            "high-risk-functional-probe42-pass:1");
                    this.highRiskFunctionalProbe42Saved = false;
                    this.highRiskFunctionalProbe42Stage = 5;
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe42Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe42:Fly",
                    "physical-space-binder-movement",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe42-fail:"
                            + failure.getClass().getName()
                            + ":"
                            + String.valueOf(failure.getMessage()));
            this.restoreHighRiskFunctionalProbe42();
        }
    }

    private String describeProbeStack(ItemStack stack) {
        if (stack == null) {
            return "<null>";
        }
        return stack.getItem().getUnlocalizedName()
                + "*"
                + stack.stackSize
                + "@"
                + stack.getItemDamage();
    }

    private void syncCreativeProbeSlot(int inventoryIndex, ItemStack stack) {
        ItemStack local = stack == null ? null : stack.copy();
        this.c.thePlayer.inventory.setInventorySlotContents(
                inventoryIndex,
                local);
        int containerSlot =
                inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex;
        this.c.thePlayer.sendQueue.addToSendQueue(
                new C10PacketCreativeInventoryAction(
                        containerSlot,
                        stack == null ? null : stack.copy()));
    }

    private String highRiskFunctionalProbe43MachineState(InvManager probe) {
        try {
            Field fieldJ = InvManager.class.getDeclaredField("J");
            Field fieldL = InvManager.class.getDeclaredField("L");
            Field fieldRunning = InvManager.class.getDeclaredField("machineRunning");
            Field fieldPhase = InvManager.class.getDeclaredField("machinePhase");
            Field fieldNext = InvManager.class.getDeclaredField("machineNextActionAt");
            fieldJ.setAccessible(true);
            fieldL.setAccessible(true);
            fieldRunning.setAccessible(true);
            fieldPhase.setAccessible(true);
            fieldNext.setAccessible(true);
            return "enabled=" + probe.o()
                    + ":priorityAvailable=" + probe.Y()
                    + ":J=" + fieldJ.getBoolean(probe)
                    + ":L=" + fieldL.getBoolean(probe)
                    + ":running=" + fieldRunning.getBoolean(probe)
                    + ":phase=" + fieldPhase.getInt(probe)
                    + ":nextDelta="
                    + (fieldNext.getLong(probe) - System.currentTimeMillis())
                    + ":screen="
                    + (this.c.currentScreen == null
                            ? "<null>"
                            : this.c.currentScreen.getClass().getSimpleName())
                    + ":escape=" + KeyBindUtil.V(1, 64165991731362L);
        }
        catch (Throwable failure) {
            return "state-read-failed="
                    + failure.getClass().getName()
                    + ":"
                    + String.valueOf(failure.getMessage());
        }
    }

    private void saveHighRiskFunctionalProbe43PriorityEntries() {
        if (Abyss.module.ModulePriority.l == null) {
            throw new IllegalStateException("InvManager priority registry unavailable");
        }
        this.highRiskFunctionalProbe43OriginalPriorityEntries =
                new boolean[Abyss.module.ModulePriority.l.size()];
        for (int priorityIndex = 0;
                priorityIndex < Abyss.module.ModulePriority.l.size();
                ++priorityIndex) {
            Abyss.module.ModulePriorityEntry entry =
                    Abyss.module.ModulePriority.l.get(priorityIndex);
            this.highRiskFunctionalProbe43OriginalPriorityEntries[priorityIndex] =
                    entry.d();
        }
    }

    private int isolateHighRiskFunctionalProbe43PriorityChain() {
        if (Abyss.module.ModulePriority.l == null) {
            throw new IllegalStateException("InvManager priority registry unavailable");
        }
        int invManagerPriority = -1;
        for (Abyss.module.ModulePriorityEntry entry
                : Abyss.module.ModulePriority.l) {
            if (entry.P == InvManager.class) {
                invManagerPriority = entry.L;
                break;
            }
        }
        if (invManagerPriority < 0) {
            throw new IllegalStateException("InvManager priority entry unavailable");
        }

        int cleared = 0;
        for (Abyss.module.ModulePriorityEntry entry
                : Abyss.module.ModulePriority.l) {
            if (entry.L > invManagerPriority && entry.d()) {
                entry.N(false);
                ++cleared;
            }
        }
        InvManager probe = Modules.J(InvManager.class);
        if (probe != null) {
            probe.T(false);
        }
        if (probe == null || !probe.Y()) {
            throw new IllegalStateException(
                    "InvManager priority isolation failed"
                            + " cleared=" + cleared
                            + " available=" + (probe != null && probe.Y()));
        }
        return cleared;
    }

    private void restoreHighRiskFunctionalProbe43PriorityEntries() {
        if (this.highRiskFunctionalProbe43OriginalPriorityEntries == null
                || Abyss.module.ModulePriority.l == null) {
            return;
        }
        if (this.highRiskFunctionalProbe43OriginalPriorityEntries.length
                != Abyss.module.ModulePriority.l.size()) {
            throw new IllegalStateException(
                    "InvManager priority registry size changed"
                            + " expected="
                            + this.highRiskFunctionalProbe43OriginalPriorityEntries.length
                            + " actual="
                            + Abyss.module.ModulePriority.l.size());
        }
        for (int priorityIndex = 0;
                priorityIndex < Abyss.module.ModulePriority.l.size();
                ++priorityIndex) {
            Abyss.module.ModulePriority.l
                    .get(priorityIndex)
                    .N(this.highRiskFunctionalProbe43OriginalPriorityEntries[priorityIndex]);
        }
    }

    private boolean highRiskFunctionalProbe43PriorityEntriesRestored() {
        if (this.highRiskFunctionalProbe43OriginalPriorityEntries == null
                || Abyss.module.ModulePriority.l == null
                || this.highRiskFunctionalProbe43OriginalPriorityEntries.length
                        != Abyss.module.ModulePriority.l.size()) {
            return false;
        }
        for (int priorityIndex = 0;
                priorityIndex < Abyss.module.ModulePriority.l.size();
                ++priorityIndex) {
            if (Abyss.module.ModulePriority.l.get(priorityIndex).d()
                    != this.highRiskFunctionalProbe43OriginalPriorityEntries[priorityIndex]) {
                return false;
            }
        }
        return true;
    }

    private void restoreHighRiskFunctionalProbe43() {
        if (!this.highRiskFunctionalProbe43Saved) {
            return;
        }
        try {
            InvManager probe = Modules.J(InvManager.class);
            if (probe != null) {
                this.setModuleEnabledRawForProbe(probe, false);
            }

            if (this.c.thePlayer != null
                    && this.highRiskFunctionalProbe43OriginalMainInventory != null) {
                for (int inventoryIndex = 0;
                        inventoryIndex
                                < this.highRiskFunctionalProbe43OriginalMainInventory.length;
                        ++inventoryIndex) {
                    this.syncCreativeProbeSlot(
                            inventoryIndex,
                            this.highRiskFunctionalProbe43OriginalMainInventory[inventoryIndex]);
                }
                this.c.thePlayer.inventory.currentItem =
                        this.highRiskFunctionalProbe43OriginalCurrentItem;
            }

            if (this.c.currentScreen
                    != this.highRiskFunctionalProbe43OriginalScreen) {
                // Probe43 may hold an intentionally uninitialized GuiInventory
                // directly in currentScreen. Do not route that synthetic screen
                // through displayGuiScreen(), because onGuiClosed expects its
                // Minecraft reference to have been initialized.
                this.c.currentScreen = null;
                if (this.highRiskFunctionalProbe43OriginalScreen != null) {
                    this.c.displayGuiScreen(
                            this.highRiskFunctionalProbe43OriginalScreen);
                }
            }

            if (this.highRiskFunctionalProbe43OriginalMode != null) {
                InvManager.mode.i(
                        this.highRiskFunctionalProbe43OriginalMode);
            }
            if (this.highRiskFunctionalProbe43OriginalBooleans != null) {
                InvManager.autoArmor.v(
                        this.highRiskFunctionalProbe43OriginalBooleans[0],
                        0L);
                InvManager.throwTrash.v(
                        this.highRiskFunctionalProbe43OriginalBooleans[1],
                        0L);
                InvManager.autoClose.v(
                        this.highRiskFunctionalProbe43OriginalBooleans[2],
                        0L);
                InvManager.onlySortOnce.v(
                        this.highRiskFunctionalProbe43OriginalBooleans[3],
                        0L);
            }
            if (this.highRiskFunctionalProbe43OriginalNumbers != null) {
                float[] n = this.highRiskFunctionalProbe43OriginalNumbers;
                InvManager.startDelay.o((byte)0, 0L, n[0]);
                InvManager.minDelay.o((byte)0, 0L, n[1]);
                InvManager.maxDelay.o((byte)0, 0L, n[2]);
                InvManager.swordSlot.o((byte)0, 0L, n[3]);
                InvManager.projectilesSlot.o((byte)0, 0L, n[4]);
                InvManager.blockSlot.o((byte)0, 0L, n[5]);
                InvManager.bowSlot.o((byte)0, 0L, n[6]);
                InvManager.pickaxeSlot.o((byte)0, 0L, n[7]);
                InvManager.axeSlot.o((byte)0, 0L, n[8]);
                InvManager.shovelSlot.o((byte)0, 0L, n[9]);
                InvManager.foodSlot.o((byte)0, 0L, n[10]);
                InvManager.potionSlot.o((byte)0, 0L, n[11]);
                InvManager.fireballSlot.o((byte)0, 0L, n[12]);
                InvManager.enderPearlSlot.o((byte)0, 0L, n[13]);
                InvManager.shearsSlot.o((byte)0, 0L, n[14]);
            }

            if (probe != null) {
                if (probe.o()
                        != this.highRiskFunctionalProbe43OriginalEnabled) {
                    probe.I(
                            0L,
                            this.highRiskFunctionalProbe43OriginalEnabled);
                }
            }
            this.restoreHighRiskFunctionalProbe43PriorityEntries();
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe43:InvManager",
                    "restore-state",
                    restoreFailure);
        }
        this.highRiskFunctionalProbe43WaitTicks = 0;
    }

    private void pumpHighRiskFunctionalProbe43() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe43")
                || this.highRiskFunctionalProbe43Stage < 0
                || this.highRiskFunctionalProbe43Stage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe42")
                && this.highRiskFunctionalProbe42Stage < 5) {
            return;
        }

        InvManager probe = Modules.J(InvManager.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(InvManager.class) != probe
                    || ModuleManager.byName("InvManager") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || !this.c.playerController.isInCreativeMode()) {
                throw new IllegalStateException(
                        "InvManager creative inventory authority unavailable");
            }

            switch (this.highRiskFunctionalProbe43Stage) {
                case 0: {
                    this.highRiskFunctionalProbe43OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe43OriginalPriority = probe.Y();
                    this.saveHighRiskFunctionalProbe43PriorityEntries();
                    this.highRiskFunctionalProbe43OriginalMode =
                            InvManager.mode.Y();
                    this.highRiskFunctionalProbe43OriginalBooleans =
                            new boolean[] {
                                InvManager.autoArmor.c(),
                                InvManager.throwTrash.c(),
                                InvManager.autoClose.c(),
                                InvManager.onlySortOnce.c()
                            };
                    this.highRiskFunctionalProbe43OriginalNumbers =
                            new float[] {
                                InvManager.startDelay.L(),
                                InvManager.minDelay.L(),
                                InvManager.maxDelay.L(),
                                InvManager.swordSlot.L(),
                                InvManager.projectilesSlot.L(),
                                InvManager.blockSlot.L(),
                                InvManager.bowSlot.L(),
                                InvManager.pickaxeSlot.L(),
                                InvManager.axeSlot.L(),
                                InvManager.shovelSlot.L(),
                                InvManager.foodSlot.L(),
                                InvManager.potionSlot.L(),
                                InvManager.fireballSlot.L(),
                                InvManager.enderPearlSlot.L(),
                                InvManager.shearsSlot.L()
                            };
                    this.highRiskFunctionalProbe43OriginalMainInventory =
                            new ItemStack[36];
                    for (int inventoryIndex = 0;
                            inventoryIndex
                                    < this.highRiskFunctionalProbe43OriginalMainInventory.length;
                            ++inventoryIndex) {
                        ItemStack original =
                                this.c.thePlayer.inventory.getStackInSlot(
                                        inventoryIndex);
                        this.highRiskFunctionalProbe43OriginalMainInventory[inventoryIndex] =
                                original == null ? null : original.copy();
                    }
                    this.highRiskFunctionalProbe43OriginalCurrentItem =
                            this.c.thePlayer.inventory.currentItem;
                    this.highRiskFunctionalProbe43OriginalScreen =
                            this.c.currentScreen;
                    this.highRiskFunctionalProbe43Saved = true;

                    if (probe.o()) {
                        probe.I(0L, false);
                        this.highRiskFunctionalProbe43Stage = 1;
                        this.highRiskFunctionalProbe43WaitTicks = 0;
                        runtimeMilestone(
                                "high-risk-functional-probe43-isolate-request:"
                                        + "InvManager");
                        return;
                    }
                    this.highRiskFunctionalProbe43Stage = 2;
                    return;
                }

                case 1: {
                    if (probe.o()
                            || probe.l()
                            || probe.K()
                            || probe.P()
                            || w.isOwnerActive(probe)) {
                        if (++this.highRiskFunctionalProbe43WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "InvManager did not isolate from live lifecycle"
                                            + " enabled=" + probe.o()
                                            + " pendingEnable=" + probe.l()
                                            + " pendingDisable=" + probe.K()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }
                    this.highRiskFunctionalProbe43Stage = 2;
                    return;
                }

                case 2: {
                    InvManager.mode.i("OPEN_INV");
                    InvManager.autoArmor.v(false, 0L);
                    InvManager.throwTrash.v(false, 0L);
                    InvManager.autoClose.v(false, 0L);
                    InvManager.onlySortOnce.v(false, 0L);
                    InvManager.startDelay.o((byte)0, 0L, 0.0f);
                    InvManager.minDelay.o((byte)0, 0L, 0.0f);
                    InvManager.maxDelay.o((byte)0, 0L, 0.0f);
                    InvManager.swordSlot.o((byte)0, 0L, 1.0f);
                    InvManager.projectilesSlot.o((byte)0, 0L, 0.0f);
                    InvManager.blockSlot.o((byte)0, 0L, 0.0f);
                    InvManager.bowSlot.o((byte)0, 0L, 0.0f);
                    InvManager.pickaxeSlot.o((byte)0, 0L, 0.0f);
                    InvManager.axeSlot.o((byte)0, 0L, 0.0f);
                    InvManager.shovelSlot.o((byte)0, 0L, 0.0f);
                    InvManager.foodSlot.o((byte)0, 0L, 0.0f);
                    InvManager.potionSlot.o((byte)0, 0L, 0.0f);
                    InvManager.fireballSlot.o((byte)0, 0L, 0.0f);
                    InvManager.enderPearlSlot.o((byte)0, 0L, 0.0f);
                    InvManager.shearsSlot.o((byte)0, 0L, 0.0f);

                    for (int inventoryIndex = 0;
                            inventoryIndex < 36;
                            ++inventoryIndex) {
                        this.syncCreativeProbeSlot(inventoryIndex, null);
                    }
                    this.syncCreativeProbeSlot(
                            0,
                            new ItemStack(Items.wooden_sword));
                    this.syncCreativeProbeSlot(
                            10,
                            new ItemStack(Items.diamond_sword));
                    this.c.thePlayer.inventory.currentItem = 0;

                    // Normalize InvManager's private machine/J/L state through
                    // the same production handler before opening GuiInventory.
                    // In particular, onPreUpdate clears the only-sort-once
                    // latch whenever the inventory GUI is not open, while a
                    // stale running machine is allowed to finish/abort normally.
                    this.c.displayGuiScreen(null);
                    this.setModuleEnabledRawForProbe(probe, true);
                    EventBus fixtureBus = new EventBus();
                    fixtureBus.s(probe, 0L);
                    if (!fixtureBus.isOwnerActive(probe)) {
                        throw new IllegalStateException(
                                "InvManager fixture EventBus binding inactive");
                    }
                    fixtureBus.e(new PreUpdateEvent(0, 0, 0), 0L);
                    runtimeMilestone(
                            "high-risk-functional-probe43-normalize-pass:"
                                    + "InvManager:screen=<null>");
                    runtimeMilestone(
                            "high-risk-functional-probe43-machine-state:normalize:"
                                    + this.highRiskFunctionalProbe43MachineState(probe));

                    int clearedPriorityEntries =
                            this.isolateHighRiskFunctionalProbe43PriorityChain();
                    runtimeMilestone(
                            "high-risk-functional-probe43-priority-isolate-pass:"
                                    + "cleared=" + clearedPriorityEntries
                                    + ":available=" + probe.Y());

                    // GuiInventory.initGui() redirects to GuiContainerCreative
                    // when the local controller is creative. InvManager OPEN_INV
                    // intentionally accepts only GuiInventory, while this probe
                    // intentionally needs creative authority to seed exact slots.
                    // Hold an uninitialized GuiInventory as currentScreen only for
                    // these synchronous handler calls; sorting itself uses
                    // playerController + inventoryContainer and does not depend on
                    // GUI rendering coordinates.
                    this.c.currentScreen =
                            new GuiInventory(this.c.thePlayer);
                    runtimeMilestone(
                            "high-risk-functional-probe43-screen-fixture-pass:"
                                    + this.c.currentScreen.getClass().getSimpleName());

                    Pair bestSwordBefore =
                            ItemUtil.q(
                                    0L,
                                    (net.minecraft.inventory.IInventory)
                                            this.c.thePlayer.inventory);
                    ItemStack bestSwordStackBefore =
                            bestSwordBefore == null
                                    ? null
                                    : (ItemStack)bestSwordBefore.a();
                    Object bestSwordIndexBefore =
                            bestSwordBefore == null
                                    ? null
                                    : bestSwordBefore.p();
                    ItemStack containerHotbarBefore =
                            this.c.thePlayer.inventoryContainer
                                    .getSlot(36)
                                    .getStack();
                    ItemStack containerSourceBefore =
                            this.c.thePlayer.inventoryContainer
                                    .getSlot(10)
                                    .getStack();
                    runtimeMilestone(
                            "high-risk-functional-probe43-decision:"
                                    + "swordSlot=" + InvManager.swordSlot.L()
                                    + ":bestIndex=" + String.valueOf(bestSwordIndexBefore)
                                    + ":bestItem="
                                    + (bestSwordStackBefore == null
                                            ? "<null>"
                                            : bestSwordStackBefore.getItem().getUnlocalizedName())
                                    + ":inv0=" + this.describeProbeStack(
                                            this.c.thePlayer.inventory.getStackInSlot(0))
                                    + ":inv10=" + this.describeProbeStack(
                                            this.c.thePlayer.inventory.getStackInSlot(10))
                                    + ":container36=" + this.describeProbeStack(containerHotbarBefore)
                                    + ":container10=" + this.describeProbeStack(containerSourceBefore));

                    // InvManager's recovered machine deliberately yields once
                    // between the armor phase and slot-sorting phase. The first
                    // event starts the machine, the second advances through the
                    // disabled armor phase, and the third executes stepSlots().
                    fixtureBus.e(new PreUpdateEvent(0, 0, 0), 0L);
                    runtimeMilestone(
                            "high-risk-functional-probe43-machine-state:event1:"
                                    + this.highRiskFunctionalProbe43MachineState(probe));
                    fixtureBus.e(new PreUpdateEvent(0, 0, 0), 0L);
                    runtimeMilestone(
                            "high-risk-functional-probe43-machine-state:event2:"
                                    + this.highRiskFunctionalProbe43MachineState(probe));
                    fixtureBus.e(new PreUpdateEvent(0, 0, 0), 0L);
                    runtimeMilestone(
                            "high-risk-functional-probe43-machine-state:event3:"
                                    + this.highRiskFunctionalProbe43MachineState(probe));
                    runtimeMilestone(
                            "high-risk-functional-probe43-machine-advance-pass:"
                                    + "events=3");

                    ItemStack sortedHotbar =
                            this.c.thePlayer.inventory.getStackInSlot(0);
                    ItemStack displacedSword =
                            this.c.thePlayer.inventory.getStackInSlot(10);
                    Pair bestSwordAfter =
                            ItemUtil.q(
                                    0L,
                                    (net.minecraft.inventory.IInventory)
                                            this.c.thePlayer.inventory);
                    runtimeMilestone(
                            "high-risk-functional-probe43-post:"
                                    + "bestIndex="
                                    + String.valueOf(
                                            bestSwordAfter == null
                                                    ? null
                                                    : bestSwordAfter.p())
                                    + ":inv0=" + this.describeProbeStack(sortedHotbar)
                                    + ":inv10=" + this.describeProbeStack(displacedSword)
                                    + ":container36=" + this.describeProbeStack(
                                            this.c.thePlayer.inventoryContainer
                                                    .getSlot(36)
                                                    .getStack())
                                    + ":container10=" + this.describeProbeStack(
                                            this.c.thePlayer.inventoryContainer
                                                    .getSlot(10)
                                                    .getStack()));
                    if (sortedHotbar == null
                            || sortedHotbar.getItem()
                                    != Items.diamond_sword
                            || displacedSword == null
                            || displacedSword.getItem()
                                    != Items.wooden_sword) {
                        throw new IllegalStateException(
                                "InvManager sword-slot swap mismatch");
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe43-effect-pass:"
                                    + "InvManager:swordSwap=10->0"
                                    + ":best=diamond_sword");

                    // Detach the synthetic uninitialized GuiInventory directly
                    // before any normal Minecraft screen lifecycle is invoked.
                    this.c.currentScreen = null;
                    fixtureBus.e(new PreUpdateEvent(0, 0, 0), 0L);
                    this.setModuleEnabledRawForProbe(probe, false);

                    for (int inventoryIndex = 0;
                            inventoryIndex
                                    < this.highRiskFunctionalProbe43OriginalMainInventory.length;
                            ++inventoryIndex) {
                        this.syncCreativeProbeSlot(
                                inventoryIndex,
                                this.highRiskFunctionalProbe43OriginalMainInventory[inventoryIndex]);
                    }
                    this.c.thePlayer.inventory.currentItem =
                            this.highRiskFunctionalProbe43OriginalCurrentItem;
                    if (this.highRiskFunctionalProbe43OriginalScreen != null) {
                        this.c.displayGuiScreen(
                                this.highRiskFunctionalProbe43OriginalScreen);
                    }

                    float[] n =
                            this.highRiskFunctionalProbe43OriginalNumbers;
                    boolean[] b =
                            this.highRiskFunctionalProbe43OriginalBooleans;
                    InvManager.mode.i(
                            this.highRiskFunctionalProbe43OriginalMode);
                    InvManager.autoArmor.v(b[0], 0L);
                    InvManager.throwTrash.v(b[1], 0L);
                    InvManager.autoClose.v(b[2], 0L);
                    InvManager.onlySortOnce.v(b[3], 0L);
                    InvManager.startDelay.o((byte)0, 0L, n[0]);
                    InvManager.minDelay.o((byte)0, 0L, n[1]);
                    InvManager.maxDelay.o((byte)0, 0L, n[2]);
                    InvManager.swordSlot.o((byte)0, 0L, n[3]);
                    InvManager.projectilesSlot.o((byte)0, 0L, n[4]);
                    InvManager.blockSlot.o((byte)0, 0L, n[5]);
                    InvManager.bowSlot.o((byte)0, 0L, n[6]);
                    InvManager.pickaxeSlot.o((byte)0, 0L, n[7]);
                    InvManager.axeSlot.o((byte)0, 0L, n[8]);
                    InvManager.shovelSlot.o((byte)0, 0L, n[9]);
                    InvManager.foodSlot.o((byte)0, 0L, n[10]);
                    InvManager.potionSlot.o((byte)0, 0L, n[11]);
                    InvManager.fireballSlot.o((byte)0, 0L, n[12]);
                    InvManager.enderPearlSlot.o((byte)0, 0L, n[13]);
                    InvManager.shearsSlot.o((byte)0, 0L, n[14]);
                    if (this.highRiskFunctionalProbe43OriginalEnabled) {
                        probe.I(0L, true);
                    }
                    this.restoreHighRiskFunctionalProbe43PriorityEntries();

                    this.highRiskFunctionalProbe43Stage = 3;
                    this.highRiskFunctionalProbe43WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe43-restore-request:"
                                    + "InvManager:enabled="
                                    + this.highRiskFunctionalProbe43OriginalEnabled);
                    return;
                }

                case 3: {
                    boolean stableOriginal =
                            this.highRiskFunctionalProbe43OriginalEnabled
                                    ? probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && probe.P()
                                            && w.isOwnerActive(probe)
                                    : !probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && !probe.P()
                                            && !w.isOwnerActive(probe);
                    if (!stableOriginal) {
                        if (++this.highRiskFunctionalProbe43WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "InvManager did not restore original lifecycle");
                        }
                        return;
                    }

                    boolean inventoryRestored = true;
                    for (int inventoryIndex = 0;
                            inventoryIndex
                                    < this.highRiskFunctionalProbe43OriginalMainInventory.length;
                            ++inventoryIndex) {
                        if (!ItemStack.areItemStacksEqual(
                                this.c.thePlayer.inventory
                                        .getStackInSlot(inventoryIndex),
                                this.highRiskFunctionalProbe43OriginalMainInventory[inventoryIndex])) {
                            inventoryRestored = false;
                            break;
                        }
                    }
                    boolean priorityEntriesRestored =
                            this.highRiskFunctionalProbe43PriorityEntriesRestored();
                    if (!inventoryRestored
                            || !priorityEntriesRestored
                            || probe.Y()
                                    != this.highRiskFunctionalProbe43OriginalPriority
                            || !InvManager.mode.Y().equals(
                                    this.highRiskFunctionalProbe43OriginalMode)
                            || Math.abs(
                                    InvManager.swordSlot.L()
                                            - this.highRiskFunctionalProbe43OriginalNumbers[3])
                                    > 0.0001f) {
                        throw new IllegalStateException(
                                "InvManager fixture state did not restore"
                                        + " inventory=" + inventoryRestored
                                        + " priorityEntries=" + priorityEntriesRestored
                                        + " priorityAvailable=" + probe.Y()
                                        + " expectedPriorityAvailable="
                                        + this.highRiskFunctionalProbe43OriginalPriority
                                        + " mode=" + InvManager.mode.Y());
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe43-restore-pass:"
                                    + "InvManager:enabled="
                                    + this.highRiskFunctionalProbe43OriginalEnabled
                                    + ":inventory36=true"
                                    + ":priorityEntries=true");
                    runtimeMilestone(
                            "high-risk-functional-probe43-module-pass:"
                                    + "InvManager");
                    runtimeMilestone(
                            "high-risk-functional-probe43-pass:1");
                    this.highRiskFunctionalProbe43Saved = false;
                    this.highRiskFunctionalProbe43Stage = 4;
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe43Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe43:InvManager",
                    "best-sword-hotbar-sort",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe43-fail:"
                            + failure.getClass().getName()
                            + ":"
                            + String.valueOf(failure.getMessage()));
            this.restoreHighRiskFunctionalProbe43();
        }
    }

    private void restoreHighRiskFunctionalProbe44() {
        if (!this.highRiskFunctionalProbe44Saved) {
            return;
        }
        try {
            InvClicker probe = Modules.J(InvClicker.class);
            if (this.highRiskFunctionalProbe44OriginalGameType != null
                    && this.c.playerController != null
                    && this.c.playerController.getCurrentGameType()
                            != this.highRiskFunctionalProbe44OriginalGameType) {
                this.c.playerController.setGameType(
                        this.highRiskFunctionalProbe44OriginalGameType);
            }
            if (probe != null) {
                if (probe.P()) {
                    w.B(probe);
                    probe.A(false);
                }
                this.setModuleEnabledRawForProbe(probe, false);
            }

            if (this.c.thePlayer != null) {
                this.syncCreativeProbeSlot(
                        0,
                        this.highRiskFunctionalProbe44OriginalSlot0);
                this.syncCreativeProbeSlot(
                        9,
                        this.highRiskFunctionalProbe44OriginalSlot9);
                this.c.thePlayer.inventory.setItemStack(
                        this.highRiskFunctionalProbe44OriginalCursor == null
                                ? null
                                : this.highRiskFunctionalProbe44OriginalCursor.copy());
                this.c.thePlayer.inventory.currentItem =
                        this.highRiskFunctionalProbe44OriginalCurrentItem;
            }

            if (this.c.currentScreen
                    != this.highRiskFunctionalProbe44OriginalScreen) {
                this.c.displayGuiScreen(
                        this.highRiskFunctionalProbe44OriginalScreen);
            }

            InvClicker.alwaysClick.v(
                    this.highRiskFunctionalProbe44OriginalAlwaysClick,
                    0L);
            InvClicker.cps.o(
                    (byte)0,
                    0L,
                    this.highRiskFunctionalProbe44OriginalCps);

            if (probe != null) {
                if (this.highRiskFunctionalProbe44OriginalEnabled) {
                    probe.I(0L, true);
                }
            }
        }
        catch (Throwable restoreFailure) {
            recordFeatureFailure(
                    "HighRiskFunctionalProbe44:InvClicker",
                    "restore-state",
                    restoreFailure);
        }
        this.highRiskFunctionalProbe44WaitTicks = 0;
    }

    private void pumpHighRiskFunctionalProbe44() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe44")
                || this.highRiskFunctionalProbe44Stage < 0
                || this.highRiskFunctionalProbe44Stage >= 6) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe43")
                && this.highRiskFunctionalProbe43Stage < 4) {
            return;
        }

        InvClicker probe = Modules.J(InvClicker.class);
        try {
            if (probe == null
                    || ModuleManager.byClass(InvClicker.class) != probe
                    || ModuleManager.byName("InvClicker") != probe
                    || this.c.theWorld == null
                    || this.c.thePlayer == null
                    || (this.highRiskFunctionalProbe44Stage != 4
                            && !this.c.playerController.isInCreativeMode())) {
                throw new IllegalStateException(
                        "InvClicker creative inventory authority unavailable"
                                + " stage=" + this.highRiskFunctionalProbe44Stage
                                + " gameType="
                                + (this.c.playerController == null
                                        ? "<null-controller>"
                                        : this.c.playerController
                                                .getCurrentGameType()));
            }

            switch (this.highRiskFunctionalProbe44Stage) {
                case 0: {
                    this.highRiskFunctionalProbe44OriginalEnabled = probe.o();
                    this.highRiskFunctionalProbe44OriginalAlwaysClick =
                            InvClicker.alwaysClick.c();
                    this.highRiskFunctionalProbe44OriginalCps =
                            InvClicker.cps.L();

                    ItemStack slot0 =
                            this.c.thePlayer.inventory.getStackInSlot(0);
                    ItemStack slot9 =
                            this.c.thePlayer.inventory.getStackInSlot(9);
                    ItemStack cursor =
                            this.c.thePlayer.inventory.getItemStack();
                    this.highRiskFunctionalProbe44OriginalSlot0 =
                            slot0 == null ? null : slot0.copy();
                    this.highRiskFunctionalProbe44OriginalSlot9 =
                            slot9 == null ? null : slot9.copy();
                    this.highRiskFunctionalProbe44OriginalCursor =
                            cursor == null ? null : cursor.copy();
                    this.highRiskFunctionalProbe44OriginalCurrentItem =
                            this.c.thePlayer.inventory.currentItem;
                    this.highRiskFunctionalProbe44OriginalScreen =
                            this.c.currentScreen;
                    this.highRiskFunctionalProbe44OriginalGameType =
                            this.c.playerController.getCurrentGameType();
                    if (this.highRiskFunctionalProbe44OriginalGameType
                            != net.minecraft.world.WorldSettings.GameType.CREATIVE) {
                        throw new IllegalStateException(
                                "InvClicker probe expected creative authority, got "
                                        + this.highRiskFunctionalProbe44OriginalGameType);
                    }
                    this.highRiskFunctionalProbe44Saved = true;

                    if (probe.o()) {
                        probe.I(0L, false);
                        this.highRiskFunctionalProbe44Stage = 1;
                        this.highRiskFunctionalProbe44WaitTicks = 0;
                        runtimeMilestone(
                                "high-risk-functional-probe44-isolate-request:"
                                        + "InvClicker");
                        return;
                    }
                    this.highRiskFunctionalProbe44Stage = 2;
                    return;
                }

                case 1: {
                    if (probe.o()
                            || probe.l()
                            || probe.K()
                            || probe.P()
                            || w.isOwnerActive(probe)) {
                        if (++this.highRiskFunctionalProbe44WaitTicks > 160) {
                            throw new IllegalStateException(
                                    "InvClicker did not isolate from live lifecycle"
                                            + " enabled=" + probe.o()
                                            + " pendingEnable=" + probe.l()
                                            + " pendingDisable=" + probe.K()
                                            + " subscribed=" + probe.P()
                                            + " ownerActive="
                                            + w.isOwnerActive(probe));
                        }
                        return;
                    }
                    this.highRiskFunctionalProbe44Stage = 2;
                    return;
                }

                case 2: {
                    InvClicker.alwaysClick.v(true, 0L);
                    InvClicker.cps.o((byte)0, 0L, 20.0f);
                    this.c.thePlayer.inventory.setItemStack(null);
                    this.syncCreativeProbeSlot(0, null);
                    this.syncCreativeProbeSlot(
                            9,
                            new ItemStack(Items.apple, 3));
                    this.c.thePlayer.inventory.currentItem = 1;
                    this.highRiskFunctionalProbe44Stage = 3;
                    this.highRiskFunctionalProbe44WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe44-ready:"
                                    + "InvClicker:input=shift+lmb:slot=9");
                    return;
                }

                case 3: {
                    int attackKey =
                            this.c.gameSettings.keyBindAttack.getKeyCode();
                    boolean physicalAttack =
                            KeyBindUtil.V(
                                    attackKey,
                                    64165991731362L);
                    boolean physicalShift =
                            KeyBindUtil.V(
                                    42,
                                    64165991731362L);
                    if (!physicalAttack || !physicalShift) {
                        if (++this.highRiskFunctionalProbe44WaitTicks > 600) {
                            throw new IllegalStateException(
                                    "InvClicker physical input not observed"
                                            + " attack=" + physicalAttack
                                            + " shift=" + physicalShift
                                            + " attackKey=" + attackKey);
                        }
                        return;
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe44-input-pass:"
                                    + "InvClicker:physicalLmb=true"
                                    + ":physicalShift=true");

                    // Vanilla redirects GuiInventory to GuiContainerCreative
                    // when the local controller reports creative mode. This
                    // probe needs creative authority only for deterministic
                    // inventory seeding; InvClicker itself is meant to exercise
                    // the normal GuiInventory mouse path. Temporarily present
                    // the local controller as survival while vanilla initializes
                    // the real 176x166 inventory GUI, then immediately restore
                    // the original game type before any probe click is handled.
                    this.c.playerController.setGameType(
                            net.minecraft.world.WorldSettings.GameType.SURVIVAL);
                    this.c.displayGuiScreen(
                            new GuiInventory(this.c.thePlayer));
                    if (!(this.c.currentScreen instanceof GuiInventory)) {
                        throw new IllegalStateException(
                                "InvClicker GuiInventory did not open"
                                        + " actual="
                                        + (this.c.currentScreen == null
                                                ? "<null>"
                                                : this.c.currentScreen
                                                        .getClass()
                                                        .getName())
                                        + " restoredGameType="
                                        + this.c.playerController
                                                .getCurrentGameType());
                    }
                    runtimeMilestone(
                            "high-risk-functional-probe44-gui-fixture-pass:"
                                    + "screen="
                                    + this.c.currentScreen
                                            .getClass()
                                            .getSimpleName()
                                    + ":gameType="
                                    + this.c.playerController
                                            .getCurrentGameType()
                                    + ":originalGameType="
                                    + this.highRiskFunctionalProbe44OriginalGameType);

                    probe.I(0L, true);
                    this.highRiskFunctionalProbe44Stage = 4;
                    this.highRiskFunctionalProbe44WaitTicks = 0;
                    runtimeMilestone(
                            "high-risk-functional-probe44-gui-pass:"
                                    + "InvClicker:"
                                    + this.c.currentScreen
                                            .getClass()
                                            .getName());
                    return;
                }

                case 4: {
                    boolean active =
                            probe.o()
                                    && !probe.l()
                                    && !probe.K()
                                    && probe.P()
                                    && w.isOwnerActive(probe);
                    ItemStack hotbar =
                            this.c.thePlayer.inventory.getStackInSlot(0);
                    ItemStack source =
                            this.c.thePlayer.inventory.getStackInSlot(9);
                    boolean moved =
                            hotbar != null
                                    && hotbar.getItem() == Items.apple
                                    && hotbar.stackSize == 3
                                    && source == null;
                    if (!moved) {
                        if (++this.highRiskFunctionalProbe44WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "InvClicker synthetic shift-click did not move fixture"
                                            + " active=" + active
                                            + " screen="
                                            + (this.c.currentScreen == null
                                                    ? "<null>"
                                                    : this.c.currentScreen
                                                            .getClass()
                                                            .getName())
                                            + " slot0="
                                            + (hotbar == null
                                                    ? "<null>"
                                                    : hotbar.getDisplayName()
                                                            + "*"
                                                            + hotbar.stackSize)
                                            + " slot9="
                                            + (source == null
                                                    ? "<null>"
                                                    : source.getDisplayName()
                                                            + "*"
                                                            + source.stackSize));
                        }
                        return;
                    }
                    if (!active) {
                        throw new IllegalStateException(
                                "InvClicker fixture moved before module became active");
                    }

                    boolean physicalAttack =
                            KeyBindUtil.V(
                                    this.c.gameSettings.keyBindAttack
                                            .getKeyCode(),
                                    64165991731362L);
                    boolean physicalShift =
                            KeyBindUtil.V(
                                    42,
                                    64165991731362L);
                    if (!physicalAttack || !physicalShift) {
                        throw new IllegalStateException(
                                "InvClicker fixture moved after physical input release"
                                        + " attack=" + physicalAttack
                                        + " shift=" + physicalShift);
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe44-effect-pass:"
                                    + "InvClicker:shiftClick=9->0"
                                    + ":item=apple*3"
                                    + ":physicalLmb=true"
                                    + ":physicalShift=true");
                    runtimeMilestone(
                            "high-risk-functional-probe44-ready-release:"
                                    + "InvClicker:input=shift+lmb");

                    probe.I(0L, false);
                    this.c.playerController.setGameType(
                            this.highRiskFunctionalProbe44OriginalGameType);
                    runtimeMilestone(
                            "high-risk-functional-probe44-game-type-restore-pass:"
                                    + this.c.playerController.getCurrentGameType());
                    this.c.displayGuiScreen(null);
                    this.syncCreativeProbeSlot(
                            0,
                            this.highRiskFunctionalProbe44OriginalSlot0);
                    this.syncCreativeProbeSlot(
                            9,
                            this.highRiskFunctionalProbe44OriginalSlot9);
                    this.c.thePlayer.inventory.setItemStack(
                            this.highRiskFunctionalProbe44OriginalCursor == null
                                    ? null
                                    : this.highRiskFunctionalProbe44OriginalCursor.copy());
                    this.c.thePlayer.inventory.currentItem =
                            this.highRiskFunctionalProbe44OriginalCurrentItem;
                    if (this.highRiskFunctionalProbe44OriginalScreen != null) {
                        this.c.displayGuiScreen(
                                this.highRiskFunctionalProbe44OriginalScreen);
                    }

                    InvClicker.alwaysClick.v(
                            this.highRiskFunctionalProbe44OriginalAlwaysClick,
                            0L);
                    InvClicker.cps.o(
                            (byte)0,
                            0L,
                            this.highRiskFunctionalProbe44OriginalCps);
                        if (this.highRiskFunctionalProbe44OriginalEnabled) {
                        probe.I(0L, true);
                    }

                    this.highRiskFunctionalProbe44Stage = 5;
                    this.highRiskFunctionalProbe44WaitTicks = 0;
                    return;
                }

                case 5: {
                    boolean physicalAttack =
                            KeyBindUtil.V(
                                    this.c.gameSettings.keyBindAttack
                                            .getKeyCode(),
                                    64165991731362L);
                    boolean physicalShift =
                            KeyBindUtil.V(
                                    42,
                                    64165991731362L);
                    boolean stableOriginal =
                            this.highRiskFunctionalProbe44OriginalEnabled
                                    ? probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && probe.P()
                                            && w.isOwnerActive(probe)
                                    : !probe.o()
                                            && !probe.l()
                                            && !probe.K()
                                            && !probe.P()
                                            && !w.isOwnerActive(probe);
                    boolean slot0Restored =
                            ItemStack.areItemStacksEqual(
                                    this.c.thePlayer.inventory
                                            .getStackInSlot(0),
                                    this.highRiskFunctionalProbe44OriginalSlot0);
                    boolean slot9Restored =
                            ItemStack.areItemStacksEqual(
                                    this.c.thePlayer.inventory
                                            .getStackInSlot(9),
                                    this.highRiskFunctionalProbe44OriginalSlot9);
                    boolean cursorRestored =
                            ItemStack.areItemStacksEqual(
                                    this.c.thePlayer.inventory
                                            .getItemStack(),
                                    this.highRiskFunctionalProbe44OriginalCursor);
                    boolean gameTypeRestored =
                            this.c.playerController.getCurrentGameType()
                                    == this.highRiskFunctionalProbe44OriginalGameType;

                    if (physicalAttack
                            || physicalShift
                            || !stableOriginal
                            || !slot0Restored
                            || !slot9Restored
                            || !cursorRestored
                            || !gameTypeRestored) {
                        if (++this.highRiskFunctionalProbe44WaitTicks > 240) {
                            throw new IllegalStateException(
                                    "InvClicker fixture did not restore"
                                            + " attack=" + physicalAttack
                                            + " shift=" + physicalShift
                                            + " lifecycle=" + stableOriginal
                                            + " slot0=" + slot0Restored
                                            + " slot9=" + slot9Restored
                                            + " cursor=" + cursorRestored
                                            + " gameType=" + gameTypeRestored);
                        }
                        return;
                    }

                    runtimeMilestone(
                            "high-risk-functional-probe44-restore-pass:"
                                    + "InvClicker:enabled="
                                    + this.highRiskFunctionalProbe44OriginalEnabled
                                    + ":inventory=true"
                                    + ":cursor=true"
                                    + ":gameType="
                                    + this.c.playerController
                                            .getCurrentGameType());
                    runtimeMilestone(
                            "high-risk-functional-probe44-module-pass:"
                                    + "InvClicker");
                    runtimeMilestone(
                            "high-risk-functional-probe44-pass:1");
                    this.highRiskFunctionalProbe44Saved = false;
                    this.highRiskFunctionalProbe44Stage = 6;
                    return;
                }

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe44Stage = -1;
            recordFeatureFailure(
                    "HighRiskFunctionalProbe44:InvClicker",
                    "physical-shift-click",
                    failure);
            runtimeMilestone(
                    "high-risk-functional-probe44-fail:"
                            + failure.getClass().getName()
                            + ":"
                            + String.valueOf(failure.getMessage()));
            this.restoreHighRiskFunctionalProbe44();
        }
    }

    private void restoreInvMovePhysicalProbe() {
        if (!this.invMovePhysicalSaved) {
            return;
        }
        try {
            InvMove probe = Modules.J(InvMove.class);
            if (this.invMovePhysicalOriginalInventoryMode != null) {
                InvMove.inventoryMode.i(this.invMovePhysicalOriginalInventoryMode);
            }
            if (this.invMovePhysicalOriginalContainerMode != null) {
                InvMove.containerMode.i(this.invMovePhysicalOriginalContainerMode);
            }
            KeyBindUtil.A(0L, this.c.gameSettings.keyBindForward.getKeyCode(),
                    this.invMovePhysicalOriginalForwardPressed);
            if (probe != null && probe.o() != this.invMovePhysicalOriginalEnabled) {
                probe.I(0L, this.invMovePhysicalOriginalEnabled);
            }
        }
        catch (Throwable failure) {
            recordFeatureFailure("InvMovePhysicalProbe", "restore", failure);
        }
    }

    private void pumpInvMovePhysicalProbe() {
        if (!Boolean.getBoolean("abyss.invMovePhysicalProbe")
                || this.invMovePhysicalProbeStage < 0
                || this.invMovePhysicalProbeStage >= 6) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe4")
                && this.highRiskFunctionalProbe4Stage < 1) return;

        try {
            InvMove probe = Modules.J(InvMove.class);
            if (probe == null || this.c.thePlayer == null) {
                throw new IllegalStateException("InvMove module/player unavailable");
            }
            int forwardKey = this.c.gameSettings.keyBindForward.getKeyCode();

            switch (this.invMovePhysicalProbeStage) {
                case 0:
                    this.invMovePhysicalOriginalEnabled = probe.o();
                    this.invMovePhysicalOriginalInventoryMode = InvMove.inventoryMode.Y();
                    this.invMovePhysicalOriginalContainerMode = InvMove.containerMode.Y();
                    this.invMovePhysicalOriginalForwardPressed =
                            this.c.gameSettings.keyBindForward.isKeyDown();
                    this.invMovePhysicalSaved = true;
                    InvMove.inventoryMode.i("VANILLA");
                    InvMove.containerMode.i("VANILLA");
                    KeyBindUtil.A(0L, forwardKey, false);
                    if (!probe.o()) {
                        probe.I(0L, true);
                    }
                    ++this.invMovePhysicalProbeStage;
                    this.invMovePhysicalProbeWaitTicks = 0;
                    runtimeMilestone("invmove-physical-probe-enable-request");
                    return;

                case 1:
                    ++this.invMovePhysicalProbeWaitTicks;
                    if (probe.o() && probe.P() && w.isOwnerActive(probe)) {
                        ++this.invMovePhysicalProbeStage;
                        this.invMovePhysicalProbeWaitTicks = 0;
                        runtimeMilestone("invmove-physical-probe-ready:open-inventory");
                        return;
                    }
                    if (this.invMovePhysicalProbeWaitTicks > 160) {
                        throw new IllegalStateException("InvMove did not become active/subscribed");
                    }
                    return;

                case 2:
                    ++this.invMovePhysicalProbeWaitTicks;
                    int inventoryKey = this.c.gameSettings.keyBindInventory.getKeyCode();
                    boolean physicalInventory = KeyBindUtil.V(inventoryKey, 64165991731362L);
                    if (physicalInventory) {
                        runtimeMilestone("invmove-physical-probe-input-seen:inventory=true");
                    }
                    if (this.c.currentScreen instanceof GuiContainer) {
                        ++this.invMovePhysicalProbeStage;
                        this.invMovePhysicalProbeWaitTicks = 0;
                        runtimeMilestone("invmove-physical-probe-container-open:"
                                + this.c.currentScreen.getClass().getName());
                        runtimeMilestone("invmove-physical-probe-ready:forward-input");
                        return;
                    }
                    if (this.invMovePhysicalProbeWaitTicks > 600) {
                        throw new IllegalStateException("InvMove did not observe physical container open physicalInventory="
                                + physicalInventory + " inventoryKey=" + inventoryKey
                                + " currentScreen=" + (this.c.currentScreen == null
                                ? "<null>" : this.c.currentScreen.getClass().getName()));
                    }
                    return;

                case 3:
                    ++this.invMovePhysicalProbeWaitTicks;
                    boolean physicalForward = KeyBindUtil.V(forwardKey, 64165991731362L);
                    if (physicalForward && this.c.gameSettings.keyBindForward.isKeyDown()) {
                        runtimeMilestone("invmove-physical-probe-input-seen:forward=true");
                        runtimeMilestone("invmove-physical-probe-effect-pass:forwardBinding=true:screen="
                                + this.c.currentScreen.getClass().getName());
                        ++this.invMovePhysicalProbeStage;
                        this.invMovePhysicalProbeWaitTicks = 0;
                        runtimeMilestone("invmove-physical-probe-ready:release-close");
                        return;
                    }
                    if (this.invMovePhysicalProbeWaitTicks > 600) {
                        throw new IllegalStateException("InvMove did not mirror physical forward input physical="
                                + physicalForward + " binding="
                                + this.c.gameSettings.keyBindForward.isKeyDown());
                    }
                    return;

                case 4:
                    ++this.invMovePhysicalProbeWaitTicks;
                    boolean forwardStillDown = KeyBindUtil.V(forwardKey, 64165991731362L);
                    if (!forwardStillDown && this.c.currentScreen == null
                            && !this.c.gameSettings.keyBindForward.isKeyDown()) {
                        InvMove.inventoryMode.i(this.invMovePhysicalOriginalInventoryMode);
                        InvMove.containerMode.i(this.invMovePhysicalOriginalContainerMode);
                        if (!this.invMovePhysicalOriginalEnabled) {
                            probe.I(0L, false);
                        }
                        ++this.invMovePhysicalProbeStage;
                        this.invMovePhysicalProbeWaitTicks = 0;
                        runtimeMilestone("invmove-physical-probe-close-effect-pass:forwardBinding=false");
                        return;
                    }
                    if (this.invMovePhysicalProbeWaitTicks > 600) {
                        throw new IllegalStateException("InvMove close/reset did not settle physical="
                                + forwardStillDown + " screen="
                                + (this.c.currentScreen == null ? "<null>" : this.c.currentScreen.getClass().getName())
                                + " binding=" + this.c.gameSettings.keyBindForward.isKeyDown());
                    }
                    return;

                case 5:
                    ++this.invMovePhysicalProbeWaitTicks;
                    boolean restored = this.invMovePhysicalOriginalEnabled
                            ? probe.o() && probe.P() && w.isOwnerActive(probe)
                            : !probe.o() && !probe.P() && !w.isOwnerActive(probe);
                    if (restored) {
                        KeyBindUtil.A(0L, forwardKey, this.invMovePhysicalOriginalForwardPressed);
                        runtimeMilestone("invmove-physical-probe-restore-pass:enabled="
                                + this.invMovePhysicalOriginalEnabled + ":inventoryMode="
                                + this.invMovePhysicalOriginalInventoryMode + ":containerMode="
                                + this.invMovePhysicalOriginalContainerMode);
                        ++this.invMovePhysicalProbeStage;
                        runtimeMilestone("invmove-physical-probe-pass:1");
                        return;
                    }
                    if (this.invMovePhysicalProbeWaitTicks > 160) {
                        throw new IllegalStateException("InvMove module state did not restore enabled="
                                + probe.o() + " subscribed=" + probe.P()
                                + " ownerActive=" + w.isOwnerActive(probe));
                    }
                    return;

                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.restoreInvMovePhysicalProbe();
            this.invMovePhysicalProbeStage = -1;
            recordFeatureFailure("InvMovePhysicalProbe", "inventory-forward", failure);
            runtimeMilestone("invmove-physical-probe-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe4() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe4")
                || this.highRiskFunctionalProbe4Stage < 0
                || this.highRiskFunctionalProbe4Stage >= 1) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe3")
                && this.highRiskFunctionalProbe3Stage < 1) return;

        try {
            this.probeChestEspOpenedFilterEffect();
            runtimeMilestone("high-risk-functional-probe4-module-pass:ChestESP");
            ++this.highRiskFunctionalProbe4Stage;
            runtimeMilestone("high-risk-functional-probe4-pass:1");
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe4Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe4:ChestESP", "opened-filter", failure);
            runtimeMilestone("high-risk-functional-probe4-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe3() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe3")
                || this.highRiskFunctionalProbe3Stage < 0
                || this.highRiskFunctionalProbe3Stage >= 1) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe2")
                && this.highRiskFunctionalProbe2Stage < 3) return;
        if (Boolean.getBoolean("abyss.physicalInputFunctionalProbe")
                && this.physicalInputFunctionalProbeStage < 7) return;

        try {
            this.probeWTapMovementPauseEffect();
            runtimeMilestone("high-risk-functional-probe3-module-pass:WTap");
            ++this.highRiskFunctionalProbe3Stage;
            runtimeMilestone("high-risk-functional-probe3-pass:1");
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe3Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe3:WTap", "movement-pause", failure);
            runtimeMilestone("high-risk-functional-probe3-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe2() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe2")
                || this.highRiskFunctionalProbe2Stage < 0
                || this.highRiskFunctionalProbe2Stage >= 3) {
            return;
        }
        if (Boolean.getBoolean("abyss.highRiskFunctionalProbe")
                && this.highRiskFunctionalProbeStage < 4) return;

        try {
            switch (this.highRiskFunctionalProbe2Stage) {
                case 0:
                    this.probeSprintKeyEffect();
                    runtimeMilestone("high-risk-functional-probe2-module-pass:Sprint");
                    ++this.highRiskFunctionalProbe2Stage;
                    return;
                case 1:
                    this.probeBackTrackSelectionAndCleanup();
                    runtimeMilestone("high-risk-functional-probe2-module-pass:BackTrack");
                    ++this.highRiskFunctionalProbe2Stage;
                    return;
                case 2:
                    this.probeNoInteractContainerEffect();
                    runtimeMilestone("high-risk-functional-probe2-module-pass:NoInteract");
                    ++this.highRiskFunctionalProbe2Stage;
                    runtimeMilestone("high-risk-functional-probe2-pass:3");
                    return;
                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbe2Stage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe2", "stage", failure);
            runtimeMilestone("high-risk-functional-probe2-fail:" + failure.getClass().getName());
        }
    }

    private void pumpHighRiskFunctionalProbe() {
        if (!Boolean.getBoolean("abyss.highRiskFunctionalProbe")
                || this.highRiskFunctionalProbeStage < 0
                || this.highRiskFunctionalProbeStage >= 4) {
            return;
        }
        if (Boolean.getBoolean("abyss.worldFunctionalProbe") && this.worldFunctionalProbeStage < 3) return;
        if (Boolean.getBoolean("abyss.categoryLifecycleProbe")
                && this.categoryLifecycleProbeIndex < CATEGORY_LIFECYCLE_PROBE_MODULES.length) return;
        if (Boolean.getBoolean("abyss.promotedRegistryProbe")
                && this.promotedRegistryProbeIndex < PROMOTED_REGISTRY_PROBE_MODULES.length) return;
        if (Boolean.getBoolean("abyss.eventFunctionalProbe") && this.eventFunctionalProbeStage < 3) return;
        if (Boolean.getBoolean("abyss.movementFunctionalProbe") && this.movementFunctionalProbeStage < 3) return;
        if (Boolean.getBoolean("abyss.playerFunctionalProbe") && this.playerFunctionalProbeStage < 3) return;
        if (Boolean.getBoolean("abyss.combatFunctionalProbe") && this.combatFunctionalProbeStage < 3) return;
        if (Boolean.getBoolean("abyss.packetFunctionalProbe") && this.packetFunctionalProbeStage < 3) return;
        if (Boolean.getBoolean("abyss.macroFunctionalProbe") && this.macroFunctionalProbeStage < 4) return;
        if (Boolean.getBoolean("abyss.visualUtilityFunctionalProbe")
                && this.visualUtilityFunctionalProbeStage < 4) return;

        try {
            switch (this.highRiskFunctionalProbeStage) {
                case 0:
                    this.probeVelocityPacketEffect();
                    runtimeMilestone("high-risk-functional-probe-module-pass:Velocity");
                    ++this.highRiskFunctionalProbeStage;
                    return;
                case 1:
                    this.probeNoSlowSwordEffect();
                    runtimeMilestone("high-risk-functional-probe-module-pass:NoSlow");
                    ++this.highRiskFunctionalProbeStage;
                    return;
                case 2:
                    this.probeBlinkBufferEffect();
                    runtimeMilestone("high-risk-functional-probe-module-pass:Blink");
                    ++this.highRiskFunctionalProbeStage;
                    return;
                case 3:
                    this.probeSpeedAutoJumpEffect();
                    runtimeMilestone("high-risk-functional-probe-module-pass:Speed");
                    ++this.highRiskFunctionalProbeStage;
                    runtimeMilestone("high-risk-functional-probe-pass:4");
                    return;
                default:
                    return;
            }
        }
        catch (Throwable failure) {
            this.highRiskFunctionalProbeStage = -1;
            recordFeatureFailure("HighRiskFunctionalProbe", "stage", failure);
            runtimeMilestone("high-risk-functional-probe-fail:" + failure.getClass().getName());
        }
    }

    private void pumpWorldFunctionalProbe() {
        runtimeMilestone("world-functional-pump-enter:enabled="
                + Boolean.getBoolean("abyss.worldFunctionalProbe")
                + ":stage=" + this.worldFunctionalProbeStage);
        if (!Boolean.getBoolean("abyss.worldFunctionalProbe") || this.worldFunctionalProbeStage < 0
                || this.worldFunctionalProbeStage >= 3) {
            runtimeMilestone("world-functional-pump-skip:enabled="
                    + Boolean.getBoolean("abyss.worldFunctionalProbe")
                    + ":stage=" + this.worldFunctionalProbeStage);
            return;
}
        try {
            runtimeMilestone("world-functional-probe-lookup-start");
            FullBright probe = Modules.J(FullBright.class);
            runtimeMilestone("world-functional-probe-lookup-complete:" + (probe == null ? "null" : probe.b()));
            if (probe == null) {
                throw new IllegalStateException("FullBright module is missing");
}
            if (this.worldFunctionalProbeStage == 0) {
                if (probe.o() || probe.l() || probe.K() || probe.P() || w.isOwnerActive(probe)) {
                    throw new IllegalStateException("FullBright did not start disabled/idle");
}
                this.worldFunctionalProbeOriginalGamma = this.c.gameSettings.gammaSetting;
                runtimeMilestone("world-functional-probe-toggle-enable-start");
                probe.I(0L, true);
                runtimeMilestone("world-functional-probe-toggle-enable-complete");
                ++this.worldFunctionalProbeStage;
                this.worldFunctionalProbeWaitTicks = 0;
                runtimeMilestone("world-functional-probe-enable-request");
                return;
}
            if (this.worldFunctionalProbeStage == 1) {
                ++this.worldFunctionalProbeWaitTicks;
                if (probe.o() && !probe.l() && !probe.K() && probe.P() && w.isOwnerActive(probe)
                        && Math.abs(this.c.gameSettings.gammaSetting - 15.0f) < 0.001f) {
                    runtimeMilestone("world-functional-probe-enabled");
                    probe.I(0L, false);
                    ++this.worldFunctionalProbeStage;
                    this.worldFunctionalProbeWaitTicks = 0;
                    runtimeMilestone("world-functional-probe-disable-request");
                    return;
}
                if (this.worldFunctionalProbeWaitTicks > 80) {
                    throw new IllegalStateException("FullBright enable/subscribe lifecycle timed out enabled="
                            + probe.o() + " pendingEnable=" + probe.l() + " pendingDisable=" + probe.K()
                            + " subscribed=" + probe.P() + " ownerActive=" + w.isOwnerActive(probe)
                            + " gamma=" + this.c.gameSettings.gammaSetting);
}
                return;
}
            ++this.worldFunctionalProbeWaitTicks;
            if (!probe.o() && !probe.l() && !probe.K() && !probe.P() && !w.isOwnerActive(probe)
                    && Math.abs(this.c.gameSettings.gammaSetting - this.worldFunctionalProbeOriginalGamma) < 0.001f) {
                runtimeMilestone("world-functional-probe-pass");
                ++this.worldFunctionalProbeStage;
                return;
}
            if (this.worldFunctionalProbeWaitTicks > 80) {
                throw new IllegalStateException("FullBright disable/unsubscribe lifecycle timed out enabled="
                        + probe.o() + " pendingEnable=" + probe.l() + " pendingDisable=" + probe.K()
                        + " subscribed=" + probe.P() + " ownerActive=" + w.isOwnerActive(probe)
                        + " gamma=" + this.c.gameSettings.gammaSetting
                        + " expectedGamma=" + this.worldFunctionalProbeOriginalGamma);
}
}
        catch (Throwable failure) {
            this.worldFunctionalProbeStage = -1;
            recordFeatureFailure("WorldFunctionalProbe:FullBright", "lifecycle", failure);
            runtimeMilestone("world-functional-probe-fail:" + failure.getClass().getName());
}
}

    public void onEntityJoinWorld(long var1, EntityJoinWorldEvent var3) {
        if (var3.H instanceof EntityPlayerSP) {
            runtimeMilestone("entity-player-join-world");
            BedNuker.D.clear();
            BedNuker.B = false;
            this.bedScanActive = false;
}
}
    public void onReceivePacket(ReceivePacketEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var1.d instanceof S02PacketChat) {
            String var4 = ((S02PacketChat)var1.d).getChatComponent().getFormattedText();
            if (var4.contains("\u00a7e\u00a7lProtect your bed and destroy the enemy bed") || var4.contains("\u00a7e\u00a7lDestroy the enemy bed and then eliminate them")) {
                BedNuker.B = true;
}
        } else if (var1.d instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook var6 = (S08PacketPlayerPosLook)var1.d;
            if (BedNuker.B) {
                BedNuker.B = false;
                this.U.schedule(() -> this.c.addScheduledTask(() -> {
                    try {
                        if (this.c.theWorld == null) {
                            return;
}
                        int var4x = MathHelper.floor_double((double)var6.getX());
                        int var5x = MathHelper.floor_double((double)var6.getY());
                        int var6x = MathHelper.floor_double((double)var6.getZ());
                        this.bedScanMinX = var4x - 35;
                        this.bedScanMinY = var5x - 15;
                        this.bedScanMinZ = var6x - 35;
                        this.bedScanSpanY = 31;
                        this.bedScanSpanZ = 71;
                        this.bedScanVolume = 156271;
                        this.bedScanCursor = 0;
                        this.bedScanActive = true;
}
                    catch (Throwable throwable) {
                        AbyssClient.recordFeatureFailure("BedNuker", "schedule-bed-scan", throwable);
}
                }), 3000L, TimeUnit.MILLISECONDS);
}
}
}
    public void onPreUpdate(long var1, PreUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        List<Module> var8 = ModuleManager.S;
        int var10 = var8.size();
        for (int var9 = 0; var9 < var10; ++var9) {
            Module var11 = var8.get(var9);
            try {
                if (var11.b().equalsIgnoreCase("Timer")) continue;
                if (var11.l()) {
                    var11.h(122596698849654L);
                } else if (var11.K()) {
                    var11.Z(110240354022990L);
}
                if (var11.o()) continue;
                var11.d();
}
            catch (Throwable failure) {
                moduleFailure("pre-update", var11, failure);
}
}
}
    private static long c(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x7DA9;
        if (j[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = i[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])k.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    k.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/AbyssClient", var14);
}
            long var15 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
            AbyssClient.j[var3] = var15;
}
        return j[var3];
}
    private static Object a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, Object[] var4) throws Throwable {
        int var5 = var4.length - 2;
        long var6 = (Long)var4[var5];
        long var9 = (Long)var4[++var5];
        MethodHandle var8 = a(var0, var1, var2, var3, var6, var9);
        var1.setTarget(MethodHandles.explicitCastArguments(var8, var3));
        return (Object)var8.asSpreader(Object[].class, var4.length).invoke(var4);
    }

    private static CallSite a(MethodHandles.Lookup var0, String var1, MethodType var2) {
        MutableCallSite var3 = new MutableCallSite(var2);
        try {
            var3.setTarget(
                MethodHandles.explicitCastArguments(
                    MethodHandles.insertArguments(
                        MethodHandles.lookup().findStatic(
                            AbyssClient.class,
                            "a",
                            MethodType.fromMethodDescriptorString(
                                "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/Object;",
                                AbyssClient.class.getClassLoader()
                            )
                        ).asCollector(Object[].class, var2.parameterCount()),
                        0, var0, var3, var1, var2
                    ),
                    var2
                )
            );
            return var3;
        } catch (Exception var5) {
            throw new RuntimeException("Abyss/AbyssClient" + " : " + var1 + " : " + var2.toString(), var5);
        }
    }

    private static Field a(Class var0, String var1, Class var2) {
        for (Field var6 : var0.getDeclaredFields()) {
            if (var6.getName().equals(var1) && var6.getType() == var2) {
                return var6;
            }
        }
        return null;
    }

    private static int a(long var0, long var2) {
        var0 ^= var2 << 48 | var2;
        int var4 = (int)(var0 >>> 46);
        if (m[var4] != null) {
            return var4;
        }

        Object var5 = l[var4];
        if (!(var5 instanceof String)) {
            return var4;
        }

        byte var6 = KEY_OFFSETS[(int)(var0 >>> 42 & 63L)];
        int[] var7 = new int[6];

        for (int var8 = 0; var8 < 6; var8++) {
            int var9 = 7 * (5 - var8);
            int var10 = (int)(var0 >>> var9 & 127L);
            var10 -= var6;
            if (var10 < 0) {
                var10 += 128;
            }
            var7[var8] = var10;
        }

        char[] var13 = ((String)var5).toCharArray();
        for (int var14 = 0; var14 < var13.length; var14++) {
            int var16 = var7[var14 % var7.length];
            if (var16 == 0) {
                break;
            }
            var13[var14] = (char)(var13[var14] ^ var16);
        }

        m[var4] = new String(var13);
        return var4;
    }

    private static Method a(Class var0, String var1, Class var2, int var3, Class[] var4) {
        for (Method var8 : var0.getDeclaredMethods()) {
            if (!var8.getName().equals(var1) || var8.getReturnType() != var2) {
                continue;
            }
            Class[] var9 = var8.getParameterTypes();
            if (var9.length != var3) {
                continue;
            }
            boolean matches = true;
            for (int var10 = 0; var10 < var3; var10++) {
                if (var9[var10] != var4[var10]) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                return var8;
            }
        }
        return null;
    }

    private static MethodHandle a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, long var4, long var6) {
        char var8 = var2.charAt(0);
        MethodHandle var9 = null;
        Field var10 = null;
        Method var11 = null;

        try {
            if (var8 != 204 && var8 != 200 && var8 != 'K' && var8 != 219) {
                var11 = d(var4, var6);
                Class var17 = var11.getDeclaringClass();
                String var19 = var11.getName();
                MethodType var20 = MethodType.methodType(var11.getReturnType(), var11.getParameterTypes());
                if (var8 == 244) {
                    var9 = var0.findVirtual(var17, var19, var20);
                } else if (var8 == 254) {
                    var9 = var0.findStatic(var17, var19, var20);
                } else {
                    var9 = var0.findSpecial(var17, var19, var20, var17);
                }
            } else {
                var10 = c(var4, var6);
                Class var12 = var10.getDeclaringClass();
                String var18 = var10.getName();
                Class var14 = var10.getType();
                if (var8 == 204) {
                    var9 = var0.findGetter(var12, var18, var14);
                } else if (var8 == 200) {
                    var9 = var0.findSetter(var12, var18, var14);
                } else if (var8 == 'K') {
                    var9 = var0.findStaticGetter(var12, var18, var14);
                } else {
                    var9 = var0.findStaticSetter(var12, var18, var14);
                }
            }
            return MethodHandles.dropArguments(var9, var3.parameterCount() - 2, long.class, long.class);
        } catch (Exception var15) {
            StringBuilder var13 = new StringBuilder();
            var13.append(var15.getClass().getName())
                .append(" : ")
                .append(var10 != null ? var10.toString() : (var11 != null ? var11.toString() : " null "))
                .append(" : ")
                .append(var15.toString());
            throw new RuntimeException(var13.toString());
        }
    }

    private static void a() {
        l[0] = "";
        m[0] = "Abyss.event.binder.AbyssClientBinder";
        l[1] = long.class;
        m[1] = "java/lang/Long";
        l[2] = "";
        m[2] = "Abyss.event.EventBus";
        l[3] = "";
        m[3] = "Abyss.AbyssClient";
        l[4] = void.class;
        m[4] = "java/lang/Void";
        l[5] = "";
        m[5] = "java.util.List";
        l[6] = int.class;
        m[6] = "java/lang/Integer";
        l[7] = "";
        m[7] = "java.lang.Object";
        l[8] = "";
        m[8] = "Abyss.module.impl.configuration.VisualSpoof";
        l[9] = boolean.class;
        m[9] = "java/lang/Boolean";
        l[10] = "";
        m[10] = "Abyss.module.Modules";
        l[11] = "";
        m[11] = "java.lang.Class";
        l[12] = "";
        m[12] = "Abyss.module.Module";
        l[13] = "";
        m[13] = "Abyss.setting.settings.DisableRenderVisualSetting";
        l[14] = "";
        m[14] = "Abyss.util.KeyBindUtil";
        l[15] = "";
        m[15] = "Abyss.setting.settings.ModeSetting";
        l[16] = "";
        m[16] = "java.lang.String";
        l[17] = "";
        m[17] = "Abyss.util.ClientUtil";
        l[18] = "";
        m[18] = "java.util.Set";
        l[19] = "";
        m[19] = "Abyss.module.impl.configuration.ClickGUI";
        l[20] = char.class;
        m[20] = "java/lang/Character";
        l[21] = short.class;
        m[21] = "java/lang/Short";
        l[22] = "";
        m[22] = "Abyss.util.packet.PacketManager";
        l[23] = "";
        m[23] = "Abyss.setting.settings.BooleanSetting";
        l[24] = "XUJ6&\u0017F]PyE\u0003B\u0010y9|\u0010K";
        l[25] = "";
        m[25] = "java.lang.Integer";
        l[26] = "~E$T!J`M>\u001bFKqV3A`M";
        l[27] = "";
        m[27] = "java.util.Map";
        l[28] = "";
        m[28] = "net.minecraft.client.entity.EntityPlayerSP";
        l[29] = "vn\u000f\u0011?q\u0003N\u0004\u001e.>~V\u0017\u0019'w\u0016";
        l[30] = "";
        m[30] = "Abyss.event.events.PreMouseInputEvent";
        l[31] = "KM\u001da\u0001Z\u0015EZu:D\u001dTe%\\HHH\u0004{TZ\u001d(X'VW\rF\u001fwUPp";
        l[32] = "m;B\n\f-=7L]l)P~JS\u000f!h(\bW\u001cOn \u001fJ\u0012\u007f!(\u0015Ml";
        l[33] = "<kz]v/7f-V\u000b)>fP\u0000zFn$\u007f\u0011v()t|\u0016\u000b";
        l[34] = "kC5.\u0005p0G%(lNT\u001d$\u007fS`2F:%\u0005\fdZ&5]rjD\"6l";
        l[35] = "Q58|Ec_+<\u007ft`a !g\u0014m[|<7\b\u001dXu&m\u001a%\u000e7\"~t'\u001f. 5\u0012v\u00136=\u000e";
        l[36] = "U2c|l\u0012\u000b:$hW\b\n:r~WY\u00104y?1\b\u001c,d\u0004";
        l[37] = "\u0018.N8,$U$Z!K#) Q(6#\u0019oY\"1]\u0010&R&%eFdV5KgW}T~-6[eIE";
        l[38] = "_\u0010\u0015\u001ax(LE\u0011\u0018\u0011\u00116\u0014\u0013\u001e)/WJ\u001b\f|O\u000f\u0010\u0011\u001e\u007fwYR\u0015\r\u0011qQE\b\u0003!>YO\u000f}";
        l[39] = "gsoQ*\nimkR\u001b\u0010W5rNf\ngzzDat";
        l[40] = "Wu\b\r\u007f\u0004Yk\f\u000eN>gn\u000f\u0003?\u0002\u001a0\b\u0016sz^5\u0016\u001c B\bw\u0012\u000fN@\u0019n\u0010D(\u0011\u0015v\r\u007f";
        l[41] = "^}A\u0019\u0012M\t\u007fBXq,7h_\u0013\u001f\bVx[\u0010\u000bv\teTT\u000fFFm^Sq";
        l[42] = "\u0019q?svy\u0017o;pG`)7\"l:y\u0019x*f=\u0007";
        l[43] = "\u0002j\u0000\u000fej\ft\u0004\fTt2.\u001c\u001eltSp\u0014\f9\u0014";
        l[44] = "H\rwmW\u0005F\u0013snf#xKjr\u001b\u0005H\u0004bx\u001c{";
        l[45] = "#MG\u000fA\u0016-SC\fp!\u0013\f\u0005\u0019\u0013\u0006+ZG\u001d\u0000h)K^\u001fK\u000exGF\u0002p";
        l[46] = "\u000b\u0000[R\u0014\u000f\u001a\u0014HPi7a@CG\u0014\bQ\u000fKM\u0013v";
        l[47] = "\u0005\u007fzF;][w=R\u0000W_ho><J]\"b_bBOw\u0002";
        l[48] = "XX\u001e_C!TGUW\"0GP\u00051\u001dg\u0003\bS1N^PPT\b\\?@TW\u001c\"dDU\f]D5HM\u0011f";
        l[49] = "\u0002[^\u0017\u001f\u0014R\u0005\u0001\btE\n\u001dw\u001b\u0004YcXV\u001b\t[S\u0017^\u0011\u000e%";
        l[50] = "%^\u0011\u0003s7+@\u0015\u0000B(\u0015\u001fS\u0015!'-I\u0011\u00112I/X\b\u0013y/~T\u0010\u000eB";
        l[51] = "\u007fZ\u0013I$HqD\u0017J\u0015wO\u001bQ_vXwM\u0013[e6qE\u0004Fk\u0006>M\u000eA\u0015\f1A\u000b\u0000s]=Y\u0016;";
        l[52] = "s\u0004\u0004DE8%V\u0011Fz8cQ\u0002R\u0006>e<\u0019\u0006\u00158t\rIXJ'\u001f";
        l[53] = "\u0011T>sg \u0002\u0001:q\u000e+xP8w6'\u0019\u000e0ecGD\n=,n&\u001a\u0002/y\u000e~@\b=z6(\u0002\f.\u00140 \u0015\u0011 $\u007f(\u001f\u0016^";
        l[54] = "D\u001e\u0015\u00123\b\u001a\u000e\u000e\fJ\u0001}_Z\u00106\u0007\u0013\u0018\n\u00131zDZ\u0000\u001f$B\u0012\u0018\u0004\fJ@\u0003\u0001\u0006G,\u0011\u000f\u0019\u001b|";
        l[55] = "'n-\\D\u0006.s8ffhx5 \u0005CP.w$\u0016-Q=|*\u001f_\u000f-g4f\u0017T3v/\u000f\\\u0012|5D\\S\u000b#6\"\r_\u0013>\r";
        l[56] = "$%Z9E#*;^:t\u0014\u0014cG&\t#$,O,\u000e].#C)O;\u007f/[4t";
        l[57] = "M{^\u001bYP\u001b)K\u0019fZE.K\u001ef\u000b_ @_\u0000ZS8]d";
        l[58] = "7\u001eH\b\u001aNm\u0015\u0006PyE1\u0010'\u0001\u001dY:lE\u000e\u001a\u001c7\r\u001b\u0006\bIW";
        l[59] = "@',HxZN9(KIvpfn^*JH0,Z9$J!5XrB\u001b--EI";
        l[60] = "}UC\u001e'8vX\u0014\u0015Z>\u007fXtG>,\u007f$\u0017\u00106-oJP@5*\u0012";
        l[61] = "ME<,r\"]A?8\f.^^o&w\u0002HEa8a\bNXkBf%\u001dMx#v!\u001eY\u0006|k.Z]63c$]#";
        l[62] = "\u0007k\u000e)X=\u0019j\u000e4%\u0016k1W4\u001d1\no_&HQWkRoE0\tc@:%=\u0019sClU0\f3QW\u001f/\bo\n1N#\u0010r1";
        l[63] = "\"J!xb\u0003<K!e\u001f\u001fN\u0010xe'\u000f/Npwro#OwfoU\u007fR'z\u001f\u0003<Rl=o\u000e)\u0012~\u0006!\b#Q`6n\u0000)V\u001e";
        l[64] = "tMyjj\u0003p\u0014rp\u0013w\u001a\u0014%wnL*[-}i2 T!x(TqX9e\u0013";
        l[65] = "6lW]whf2\bB\u001c?:!D<!g;-ERf78*8";
        l[66] = "\u0014\"kZG\u001f\u001a<oYv\"$dvE\u000b\u001f\u0014+~O\fa";
        l[67] = "C{\rnWxOb\\16qDwFpmq^\u000bP9Jc\u001dj\u0006k_a\"";
        l[68] = "\u0013GJbf\u0015\u001dYNaW\r#\u0001W}*\u0015\u0013N_w-k\u0019ASrl\rHMKoW";
        l[69] = "\u000e\u000b:\u0004\u0019\u000e\u0000\u0015>\u0007((>Jx\u0012K\u001e\u0006\u001c:\u0016Xp\u0004\r#\u0014\u0013\u0016U\u0001;\t(";
        l[70] = "s\u0000F0\u000f\tuWL1~\u001b\u001c\u0003\u0011a\u0003\u000e,L\u0019k\u0004p";
        l[71] = "Z!;QJkT??R{Xjg&N\u0006kZ(.D\u0001\u0015P'\"A@s\u0001+:\\{";
        l[72] = "\u0019\u0017\u007fof\u001a\u0017\t{lW\u0004)V=y4\n\u0011\u0000\u007f}'d\u0013\u0011f\u007fl\u0002B\u001d~bW";
        l[73] = "\b\u0010\u007f l\u0019\u0006\u000e{#]\u000b8R{1?\\^\u0003w)\"g";
        l[74] = "N\u000b(\rnN@\u0015,\u000e_Z~\u00194E1N\u001f\t0F%0";
        l[75] = "\\zu\u0010\t)\u0002jn\u000ep\u0011e;:\u0012\f&\u000b|j\u0011\u000b[_xg\u001cK=\u000et\u007f\u0001p";
        l[76] = "C+\u007fezkS/|q\u0004qQ)$w\u0004`Ow+uepKt?\u000bg7K&tmt;\u0010=E";
    }
    private static Field c(long var0, long var2) {
        int var4 = AbyssClient.a(var0, var2);
        Object var5 = l[var4];
        if (!(var5 instanceof String)) {
            return (Field)var5;
}
        String var6 = m[var4];
        int var7 = var6.indexOf(8);
        Class var8 = AbyssClient.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
        int var9 = var6.indexOf(8, ++var7);
        String var10 = var6.substring(var7, var9);
        Class var11 = AbyssClient.b(Long.parseLong(var6.substring(++var9), 36), 0L);
        Class var12 = var8;
        while (true) {
            Field var13;
            if ((var13 = AbyssClient.a(var12, var10, var11)) != null) {
                AbyssClient.l[var4] = var13;
                return var13;
}
            Class<?>[] var14 = var12.getInterfaces();
            if (var14 != null) {
                for (int var15 = 0; var15 < var14.length; ++var15) {
                    var13 = AbyssClient.b(var14[var15], var10, var11);
                    if (var13 == null) continue;
                    AbyssClient.l[var4] = var13;
                    return var13;
}
}
            if (var12.getName().equals("java.lang.Object")) {
                StringBuffer var19 = new StringBuffer();
                var19.append("NoSuchFieldException in ").append(var8.getName()).append(' ').append(var11.getName()).append(' ').append(var10);
                throw new RuntimeException(var19.toString());
}
            if ((var12 = var12.getSuperclass()) != null) continue;
            var12 = AbyssClient.b(525810144067084L, 0L);
}
}
    public void onSetKeyBindState(SetKeyBindStateEvent var1, long var2) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (ClientUtil.I()) {
            List<Module> var9 = ModuleManager.S;
            int var11 = var9.size();
            for (int var10 = 0; var10 < var11; ++var10) {
                Module var12 = var9.get(var10);
                try {
                    if (var12.b().equalsIgnoreCase("Timer") || var12.b().equalsIgnoreCase("ClickGUI") || var12.h() == 0 || !KeyBindUtil.d(var12.h(), var1.R, 55909487137472L) || var12.b().equalsIgnoreCase("FREELOOK") && !Freelook.mode.R("TOGGLE")) continue;
                    var12.u((short)0, 139350548161835L);
                    continue;
}
                catch (Throwable failure) {
                    moduleFailure("keybind-toggle", var12, failure);
}
}
            for (Map.Entry<Integer, String> var18 : H.entrySet()) {
                if (!KeyBindUtil.d(var18.getKey(), var1.R, 55909487137472L)) continue;
                for (String var15 : var18.getValue().split("\\n")) {
                    this.c.thePlayer.sendChatMessage(var15);
}
}
}
}
    private static ScheduledExecutorService newBedScanScheduler() {
        return Executors.newScheduledThreadPool(1, runnable -> {
            Thread worker = new Thread(runnable, "OpenAbyss-BedScan");
            worker.setDaemon(true);
            return worker;
        });
}
    public static String schedulerSelfTest() {
        ScheduledExecutorService scheduler = null;
        try {
            scheduler = AbyssClient.newBedScanScheduler();
            boolean daemon = scheduler.submit(() -> Boolean.valueOf(Thread.currentThread().isDaemon()))
                    .get(5L, TimeUnit.SECONDS).booleanValue();
            if (!daemon) {
                return "FAIL bed-scan-worker-not-daemon";
}
            return "PASS daemon-bed-scan";
}
        catch (Throwable failure) {
            return "FAIL " + failure.getClass().getName() + ": " + String.valueOf(failure.getMessage());
}
        finally {
            if (scheduler != null) {
                scheduler.shutdownNow();
}
}
}
    public AbyssClient(int var1, char var2, int var3) {
        this.U = AbyssClient.newBedScanScheduler();
        this.c = MinecraftRef.c((byte)0, 0L);
        this.B = new TimerUtil();
}
    public void onPostTick(PostTickEvent var1, long var2) throws Throwable {
        boolean inWorld = ClientUtil.I();
        StallWatchdog.tick(inWorld);
        runtimeMilestone(inWorld ? "world-ready-tick" : "menu-no-world-tick");
        int var16 = 22243;
        int var21 = 12652;
        if (!inWorld) {
            int exitedWorldSession = 0;
            if (this.runtimeWorldSessionActive) {
                this.runtimeWorldSessionActive = false;
                exitedWorldSession = this.runtimeWorldSessionCount;
                runtimeMilestone("world-session-exit:" + exitedWorldSession);
}
            BedNuker.B = false;
            if (this.highRiskFunctionalProbe32Saved
                    || this.highRiskFunctionalProbe32EnableStateSaved) {
                this.restoreHighRiskFunctionalProbe32();
                runtimeMilestone(
                        "high-risk-functional-probe32-menu-restore");
            }
            PacketManager.M(false);
            PacketManager.u.clear();
            PacketManager.v.clear();
            PacketManager.a.clear();
            I = null;
            List<Module> var32 = ModuleManager.S;
            int var34 = var32.size();
            for (int var33 = 0; var33 < var34; ++var33) {
                Module var35 = var32.get(var33);
                if (var35.b().equalsIgnoreCase("Timer") || !var35.P()) continue;
                w.B(var35);
                var35.A(false);
}
            this.s = false;
            this.runtimeWorldHeartbeatTicks = 0L;
            runtimeMilestone("menu-cleanup-complete:packetBuffer=" + PacketManager.e()
                    + ":u=" + PacketManager.u.size()
                    + ":v=" + PacketManager.v.size()
                    + ":a=" + PacketManager.a.size());
            if (exitedWorldSession > 0) {
                runtimeMilestone("world-session-menu-cleanup:" + exitedWorldSession
                        + ":packetBuffer=" + PacketManager.e()
                        + ":u=" + PacketManager.u.size()
                        + ":v=" + PacketManager.v.size()
                        + ":a=" + PacketManager.a.size());
}
        } else {
            if (!this.runtimeWorldSessionActive) {
                this.runtimeWorldSessionActive = true;
                ++this.runtimeWorldSessionCount;
                this.runtimeWorldHeartbeatTicks = 0L;
                runtimeMilestone("world-session-join:" + this.runtimeWorldSessionCount);
}
            runtimeMilestone("world-module-lifecycle-start");
            List<Module> var26 = ModuleManager.S;
            int subscribesBudget = 3;
            boolean batching = false;
            int var28 = var26.size();
            for (int var27 = 0; var27 < var28; ++var27) {
                Module var29 = var26.get(var27);
                if (var29.b().equalsIgnoreCase("Timer")) continue;
                if (var29.l()) {
                    try {
                        var29.i(17998201765264L);
                    }
                    catch (Throwable failure) {
                        moduleFailure("enable", var29, failure);
                        throw failure;
                    }
                    var29.n(false);
                } else if (var29.K()) {
                    try {
                        var29.A(94287625739397L);
                    }
                    catch (Throwable failure) {
                        moduleFailure("disable", var29, failure);
                        throw failure;
                    }
                    var29.E(false);
}
                if (var29.o()) {
                    if (var29.P() || subscribesBudget <= 0) continue;
                    if (!batching) {
                        w.beginBatch();
                        batching = true;
}
                    try {
                        w.s(var29, 25046058167973L);
                        var29.A(w.isOwnerActive(var29));
}
                    catch (Throwable failure) {
                        moduleFailure("subscribe", var29, failure);
                        w.B(var29);
                        var29.A(false);
}
                    --subscribesBudget;
                    continue;
}
                if (var29.P()) {
                    w.B(var29);
                    var29.A(false);
}
                try {
                    var29.P(11128156246666L);
                }
                catch (Throwable failure) {
                    moduleFailure("disabled-reset", var29, failure);
                    throw failure;
                }
}
            runtimeMilestone("world-module-lifecycle-complete");
            runtimeMilestone("world-session-lifecycle-complete:" + this.runtimeWorldSessionCount);
            if (Boolean.getBoolean("abyss.runtimeSelfTest")) {
                ++this.runtimeWorldHeartbeatTicks;
                if (this.runtimeWorldHeartbeatTicks % 100L == 0L) {
                    runtimeMilestone("world-heartbeat:" + this.runtimeWorldHeartbeatTicks);
                    runtimeMilestone("world-session-heartbeat:" + this.runtimeWorldSessionCount
                            + ":" + this.runtimeWorldHeartbeatTicks);
                }
            }
            if (batching) {
                runtimeMilestone("world-eventbus-endbatch-start");
                try {
                    w.endBatch();
                    runtimeMilestone("world-eventbus-endbatch-complete");
                }
                catch (Throwable failure) {
                    recordFeatureFailure("AbyssClient", "eventbus-end-batch", failure);
                    runtimeMilestone("world-eventbus-endbatch-fail:" + failure.getClass().getName());
                    throw failure;
                }
}
            runtimeMilestone("world-functional-pump-call");
            this.pumpWorldFunctionalProbe();
            this.pumpCategoryLifecycleProbe();
            this.pumpPromotedRegistryProbe();
            this.pumpEventFunctionalProbe();
            this.pumpMovementFunctionalProbe();
            this.pumpPlayerFunctionalProbe();
            this.pumpCombatFunctionalProbe();
            this.pumpPacketFunctionalProbe();
            this.pumpMacroFunctionalProbe();
            this.pumpVisualUtilityFunctionalProbe();
            this.pumpHighRiskFunctionalProbe();
            this.pumpHighRiskFunctionalProbe2();
            this.pumpPhysicalInputFunctionalProbe();
            this.pumpHighRiskFunctionalProbe3();
            this.pumpHighRiskFunctionalProbe4();
            this.pumpInvMovePhysicalProbe();
            this.pumpHighRiskFunctionalProbe5();
        this.pumpHighRiskFunctionalProbe6();
        this.pumpHighRiskFunctionalProbe7();
        this.pumpHighRiskFunctionalProbe8();
        this.pumpHighRiskFunctionalProbe9();
        this.pumpHighRiskFunctionalProbe10();
        this.pumpHighRiskFunctionalProbe11();
        this.pumpHighRiskFunctionalProbe12();
        this.pumpHighRiskFunctionalProbe13();
        this.pumpHighRiskFunctionalProbe14();
        this.pumpHighRiskFunctionalProbe15();
        this.pumpHighRiskFunctionalProbe16();
        this.pumpHighRiskFunctionalProbe17();
        this.pumpHighRiskFunctionalProbe18();
        this.pumpHighRiskFunctionalProbe19();
        this.pumpHighRiskFunctionalProbe20();
        this.pumpHighRiskFunctionalProbe21();
        this.pumpHighRiskFunctionalProbe22();
        this.pumpHighRiskFunctionalProbe23();
        this.pumpHighRiskFunctionalProbe24();
        this.pumpHighRiskFunctionalProbe25();
        this.pumpHighRiskFunctionalProbe26();
        this.pumpHighRiskFunctionalProbe27();
        this.pumpHighRiskFunctionalProbe28();
        this.pumpHighRiskFunctionalProbe29();
        this.pumpHighRiskFunctionalProbe30();
        this.pumpHighRiskFunctionalProbe31();
        this.pumpHighRiskFunctionalProbe32();
        this.pumpHighRiskFunctionalProbe33();
        this.pumpHighRiskFunctionalProbe34();
        this.pumpHighRiskFunctionalProbe35();
        this.pumpHighRiskFunctionalProbe36();
        this.pumpHighRiskFunctionalProbe37();
        this.pumpHighRiskFunctionalProbe38();
        this.pumpHighRiskFunctionalProbe39();
        this.pumpHighRiskFunctionalProbe40();
        this.pumpHighRiskFunctionalProbe41();
        this.pumpHighRiskFunctionalProbe42();
        this.pumpHighRiskFunctionalProbe43();
        this.pumpHighRiskFunctionalProbe44();
            this.pumpCommandRuntimeProbe();
            this.pumpNetworkCommandProbe();
            this.pumpReconnectSubscriptionHealth();
            this.pumpClickGuiModeProbe();
            this.pumpPersistenceSeedProbe();
            this.pumpPromotedPersistenceLiveVerify();
            if (this.c.currentScreen == null) {
                if (ClickGUI.x(17550, (short)6998, (char)var16)) {
                    runtimeMilestone("clickgui-open-request");
                    try {
                        ClickGUI.O(2169, 8663, (char)var21);
                        runtimeMilestone("clickgui-open-success:" + (this.c.currentScreen == null ? "<null>" : this.c.currentScreen.getClass().getName()));
}
                    catch (NullPointerException nullPointerException) {
                        runtimeMilestone("clickgui-open-nullpointer:" + String.valueOf(nullPointerException.getMessage()));
}
}
                if (Freelook.mode.R("HOLD") && Modules.J(Freelook.class).h() != 0) {
                    Modules.J(Freelook.class).I(20724619369162L, KeyBindUtil.V(Modules.J(Freelook.class).h(), 64165991731362L));
}
}
            if (VisualSpoof.n(118536638251483L) && !this.s) {
                VisualSpoof.t.v(!VisualSpoof.t.c(), 64895789836511L);
}
            this.s = VisualSpoof.n(118536638251483L);
}
        FontManager.warmStep();
        AbyssArrayListVisibility.preload();
        PlayerInfoCache.refresh();
        Modules.flushPendingSave();
        DeferredRendererReload.flush();
        this.pumpBedScan();
}
    private void pumpBedScan() {
        if (!this.bedScanActive) {
            return;
}
        try {
            if (this.c.theWorld == null) {
                this.bedScanActive = false;
                return;
}
            long deadline = System.nanoTime() + 2000000L;
            int layerYZ = this.bedScanSpanY * this.bedScanSpanZ;
            while (this.bedScanCursor < this.bedScanVolume) {
                int idx = this.bedScanCursor;
                int dx = idx / layerYZ;
                int rem = idx % layerYZ;
                int dy = rem / this.bedScanSpanZ;
                int dz = rem % this.bedScanSpanZ;
                this.bedScanPos.set(this.bedScanMinX + dx, this.bedScanMinY + dy, this.bedScanMinZ + dz);
                if (this.c.theWorld.getBlockState((BlockPos)this.bedScanPos).getBlock() == Blocks.bed) {
                    BedNuker.D.add(new BlockPos((Vec3i)this.bedScanPos));
}
                ++this.bedScanCursor;
                if ((this.bedScanCursor & 0x1FFF) != 0 || System.nanoTime() < deadline) continue;
                return;
}
            this.bedScanActive = false;
}
        catch (Throwable ignored) {
            this.bedScanActive = false;
}
}
    public void d(long var1, PreUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (I != null && this.B.L(300L, true)) {
            if (this.N) {
                this.N = false;
                this.c.thePlayer.sendChatMessage("/p " + I);
            } else {
                this.N = true;
                this.c.thePlayer.sendChatMessage("/p leave");
}
}
}
    public void onPreMouseInput(long var1, PreMouseInputEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        List<Module> var6 = ModuleManager.S;
        int var8 = var6.size();
        for (int var7 = 0; var7 < var8; ++var7) {
            Module var9 = var6.get(var7);
            try {
                if (var9.b().equalsIgnoreCase("Timer") || var9.o()) continue;
                var9.L(var3, 85029904657643L);
}
            catch (Throwable failure) {
                moduleFailure("pre-mouse-input", var9, failure);
}
}
}
    private static int b(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x325D;
        if (g[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = f[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])h.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    h.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/AbyssClient", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            AbyssClient.g[var3] = var15;
}
        return g[var3];
}
    private static Class b(long var0, long var2) {
        Class<?> var5 = null;
        int var4 = AbyssClient.a(var0, var2);
        Object var6 = l[var4];
        try {
            if (var6 instanceof String) {
                AbyssClient.l[var4] = var5 = Class.forName(AbyssNameMap.map(m[var4]));
                return var5;
}
}
        catch (Exception var8) {
            throw new RuntimeException(var8.toString());
}
        return (Class)var6;
}
    @Override
    public final void x(long var1, EventBus var3) {
        AbyssClientBinder.C(var3, this);
}
    private static Method b(Class var0, String var1, Class var2, int var3, Class[] var4) {
        Method var5 = AbyssClient.a(var0, var1, var2, var3, var4);
        if (var5 != null) {
            return var5;
}
        Class<?>[] var6 = var0.getInterfaces();
        if (var6 != null) {
            for (int var7 = 0; var7 < var6.length; ++var7) {
                var5 = AbyssClient.b(var6[var7], var1, var2, var3, var4);
                if (var5 == null) continue;
                return var5;
}
}
        return null;
}
    private static Method d(long var0, long var2) {
        int var4 = a(var0, var2);
        Object var5 = l[var4];
        if (!(var5 instanceof String)) {
            return (Method)var5;
        }

        String var6 = m[var4];
        int var7 = var6.indexOf(8);
        Class var8 = b(Long.parseLong(var6.substring(0, var7), 36), 0L);
        int var9 = var6.indexOf(8, ++var7);
        String var10 = var6.substring(var7, var9);
        int var11 = -1;
        int var12 = var9;

        do {
            var11++;
            var12++;
        } while ((var12 = var6.indexOf(8, var12)) > -1);

        int var13;
        Class[] var14 = new Class[var13 = var11 - 1];
        Class var15 = null;
        var12 = var9 + 1;

        for (int var16 = 0; var16 < var11; var16++) {
            int var17 = var6.indexOf(8, var12);
            var15 = b(Long.parseLong(var6.substring(var12, var17), 36), 0L);
            if (var16 < var13) {
                var14[var16] = var15;
            }
        }

        Class var23 = var8;
        while (true) {
            Method var26 = a(var23, var10, var15, var13, var14);
            if (var26 != null) {
                l[var4] = var26;
                return var26;
            }

            if (var23.getName().equals("java.lang.Object")) {
                break;
            }

            if ((var23 = var23.getSuperclass()) == null) {
                var23 = b(525810144067084L, 0L);
                break;
            }
        }

        var23 = var8;
        while (true) {
            Class[] var27;
            if ((var27 = var23.getInterfaces()) != null) {
                for (int var18 = 0; var18 < var27.length; var18++) {
                    Method var19 = b(var27[var18], var10, var15, var13, var14);
                    if (var19 != null) {
                        l[var4] = var19;
                        return var19;
                    }
                }
            }

            if (var23.getName().equals("java.lang.Object")) {
                StringBuffer var28 = new StringBuffer();
                var28.append("NoSuchMethodException in ")
                    .append(var8.getName())
                    .append(' ')
                    .append(var15.getName())
                    .append(' ')
                    .append(var10)
                    .append('(');
                int var29 = 0;
                while (var29 < var13) {
                    var28.append(var14[var29].getName());
                    if (++var29 < var13) {
                        var28.append(", ");
                    }
                }
                var28.append(')');
                throw new RuntimeException(var28.toString());
            }

            if ((var23 = var23.getSuperclass()) == null) {
                var23 = b(525810144067084L, 0L);
            }
        }
    }
    private static Field b(Class var0, String var1, Class var2) {
        Field var3 = AbyssClient.a(var0, var1, var2);
        if (var3 != null) {
            return var3;
}
        Class<?>[] var4 = var0.getInterfaces();
        if (var4 != null) {
            for (int var5 = 0; var5 < var4.length; ++var5) {
                var3 = AbyssClient.b(var4[var5], var1, var2);
                if (var3 == null) continue;
                return var3;
}
}
        return null;
}
    private static boolean zkm$unresolved$0$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_iD_l_OR_Abyss_iD_K_y_slots_39_49_66_70(Object var0, long var3) {
        try {
            MethodType var5 = MethodType.fromMethodDescriptorString("(Ljava/lang/Object;JJ)Z", AbyssClient.class.getClassLoader());
            return (boolean)MethodHandles.explicitCastArguments(AbyssClient.a(MethodHandles.lookup(), null, "\u00f4", var5, 2266045794134596627L, 9901644652386L), var5).invoke((Object)var0, 2266045794134596627L, 9901644652386L);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private static String a(byte[] var0) {
        int var1 = 0;
        int var2;
        char[] var3 = new char[var2 = var0.length];
        for (int var4 = 0; var4 < var2; ++var4) {
            int var5;
            if ((var5 = 255 & var0[var4]) < 192) {
                var3[var1++] = (char)var5;
            } else if (var5 < 224) {
                char var6 = (char)((char)(var5 & 31) << 6);
                int var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 63));
                var3[var1++] = var6;
            } else if (var4 < var2 - 2) {
                char var12 = (char)((char)(var5 & 15) << 12);
                int var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63) << 6);
                var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63));
                var3[var1++] = var12;
            }
        }
        return new String(var3, 0, var1);
    }    private static void zkm$clinit() {
        try {
            l = new Object[77];
            m = new String[77];
            a();
            e = new HashMap(13);
            long var22 = a ^ 20790936441576L;
            byte[] var10003 = new byte[]{(byte)(var22 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var25 = 1; var25 < 8; ++var25) {
                var10003[var25] = (byte)(var22 << var25 * 8 >>> 56);
            }
            Cipher var24 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var24.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var31 = new String[10];
            int var29 = 0;
            String var28 = " !\u00f0\u00fc$\u00ec\u00e2\u00b4\u0097\u00bf//\u00fa\u0089sK \u00df\u00f0\u00c02\u0019\u00ae\u00d8\u0006\u0080\rn0\u00f0 &H\u00fd$\u0097\r\u0019\u00fb\u009d\u001f\u00b0p\u00c1\u00e3\u00cdh\u00af\u00e5\u00107\u0003m\u00c0\u00ce^\u00b3\u001b\u00ea`\u00d6\u000e\u001b\b\u00ae\u00f3PMv\u008d\u00b0&:\u00d0\u00a4\u0087\u00bf}\u00cf.\u00c5\u00fc/\u00dd3/\n6M\u00cb\u0015\u00a5\u00cf\f\u00f0\u00ae=a\u008f\u00cb\u00aa\u00a4Z^.QD\u00d9\u00e1!\u0017\u00b0\u0016\u0080\u00d0\u00a0\u00fb\u00d0X\nq\u00bchi\u00fb\u0003\u00a9\u00de/\non\u008c\u0011?\u00ae+#C\u00f7D\u0015\u008bW\u00ae\u0087?\u0010\u0093\u00a7/\u00f2\u00ec\u00be\u00b9\u00045\u00e2@5\u00c9\u0012\u00f5\u00bc\u0010\u00e6\u00e08%\u009b\u00e3U,h\u00ac%\u0083\u0099Z\u0088\u00a5\u0010\u00f1\u00ac\u00b5\u0085\u00f6\u008b\u000b\u00f0\u00e7_\u00ba\u0081\u00db\u00b3\u008f8X/\u00c6u\u0087#5\u0000h7d\u00e7\u00f6\u00b0\u00c8\u00de\u00c4\u00fce\u00e9\u0018\u00a8\u00b9\u00cf\u0083\u00cdP\u00f2)\b\u0016&\u001f\u00e91\u00c2\u00e0\u00db\u00a2/\u00b2;G\u0088;\u0011\u0000\u0099\u00a0\u00e4\b3\u00fbw\u00e0\u00d7\u00be#\u00e6\u0016\u008b\u001a3\u0015!.\\@\u00c5\u00d7\u00de$\u00e9\u00b3\u00b1\u0094xE\u000bUj\u00d2\u001a\u000e\u00b6q2K\u00cf";
            int var30 = " !\u00f0\u00fc$\u00ec\u00e2\u00b4\u0097\u00bf//\u00fa\u0089sK \u00df\u00f0\u00c02\u0019\u00ae\u00d8\u0006\u0080\rn0\u00f0 &H\u00fd$\u0097\r\u0019\u00fb\u009d\u001f\u00b0p\u00c1\u00e3\u00cdh\u00af\u00e5\u00107\u0003m\u00c0\u00ce^\u00b3\u001b\u00ea`\u00d6\u000e\u001b\b\u00ae\u00f3PMv\u008d\u00b0&:\u00d0\u00a4\u0087\u00bf}\u00cf.\u00c5\u00fc/\u00dd3/\n6M\u00cb\u0015\u00a5\u00cf\f\u00f0\u00ae=a\u008f\u00cb\u00aa\u00a4Z^.QD\u00d9\u00e1!\u0017\u00b0\u0016\u0080\u00d0\u00a0\u00fb\u00d0X\nq\u00bchi\u00fb\u0003\u00a9\u00de/\non\u008c\u0011?\u00ae+#C\u00f7D\u0015\u008bW\u00ae\u0087?\u0010\u0093\u00a7/\u00f2\u00ec\u00be\u00b9\u00045\u00e2@5\u00c9\u0012\u00f5\u00bc\u0010\u00e6\u00e08%\u009b\u00e3U,h\u00ac%\u0083\u0099Z\u0088\u00a5\u0010\u00f1\u00ac\u00b5\u0085\u00f6\u008b\u000b\u00f0\u00e7_\u00ba\u0081\u00db\u00b3\u008f8X/\u00c6u\u0087#5\u0000h7d\u00e7\u00f6\u00b0\u00c8\u00de\u00c4\u00fce\u00e9\u0018\u00a8\u00b9\u00cf\u0083\u00cdP\u00f2)\b\u0016&\u001f\u00e91\u00c2\u00e0\u00db\u00a2/\u00b2;G\u0088;\u0011\u0000\u0099\u00a0\u00e4\b3\u00fbw\u00e0\u00d7\u00be#\u00e6\u0016\u008b\u001a3\u0015!.\\@\u00c5\u00d7\u00de$\u00e9\u00b3\u00b1\u0094xE\u000bUj\u00d2\u001a\u000e\u00b6q2K\u00cf".length();
            int var27 = 16;
            int var36 = -1;
            block9: while (true) {
                String var37 = var28.substring(++var36, var36 + var27);
                int var10001 = -1;
                while (true) {
                    byte[] var32 = var24.doFinal(var37.getBytes("ISO-8859-1"));
                    String var51 = AbyssClient.a(var32).intern();
                    switch (var10001) {
                        case 0: {
                            var31[var29++] = var51;
                            if ((var36 += var27) >= var30) {
                                b = var31;
                                d = new String[10];
                                h = new HashMap(13);
                                var10003 = new byte[]{(byte)(var22 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var12 = 1; var12 < 8; ++var12) {
                                    var10003[var12] = (byte)(var22 << var12 * 8 >>> 56);
}
                                Cipher var11 = Cipher.getInstance("DES/CBC/NoPadding");
                                var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var17 = new long[8];
                                int var14 = 0;
                                String var15 = "\u00dc\u00f5\u00cd\u009bzg\u00b0FA\u00d6\u00c0\u00d7\u00a1\u00dc\u00f3\u00f5\u008a\u001a\u00d0\u00a5\u008b\u00db<H\u00d3\u00cf\u00b9>\u00ca\u00c8\u00ad\u00b5\u0002\u00d4\u0001\u00cf\u00c4\bu\u0005+\u001b\u00fd\u0092\u00ac\u001b_\u00fb";
                                int var16 = "\u00dc\u00f5\u00cd\u009bzg\u00b0FA\u00d6\u00c0\u00d7\u00a1\u00dc\u00f3\u00f5\u008a\u001a\u00d0\u00a5\u008b\u00db<H\u00d3\u00cf\u00b9>\u00ca\u00c8\u00ad\u00b5\u0002\u00d4\u0001\u00cf\u00c4\bu\u0005+\u001b\u00fd\u0092\u00ac\u001b_\u00fb".length();
                                int var13 = 0;
                                block12: while (true) {
                                    var10001 = var13;
                                    byte[] var18 = var15.substring(var10001, var13 += 8).getBytes("ISO-8859-1");
                                    long[] var40 = var17;
                                    var10001 = var14++;
                                    long var55 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                    int var59 = -1;
                                    while (true) {
                                        long var19 = var55;
                                        byte[] var21 = var11.doFinal(new byte[]{(byte)(var19 >>> 56), (byte)(var19 >>> 48), (byte)(var19 >>> 40), (byte)(var19 >>> 32), (byte)(var19 >>> 24), (byte)(var19 >>> 16), (byte)(var19 >>> 8), (byte)var19});
                                        long var63 = ((long)var21[0] & 0xFFL) << 56 | ((long)var21[1] & 0xFFL) << 48 | ((long)var21[2] & 0xFFL) << 40 | ((long)var21[3] & 0xFFL) << 32 | ((long)var21[4] & 0xFFL) << 24 | ((long)var21[5] & 0xFFL) << 16 | ((long)var21[6] & 0xFFL) << 8 | (long)var21[7] & 0xFFL;
                                        switch (var59) {
                                            case 0: {
                                                var40[var10001] = var63;
                                                if (var13 < var16) break;
                                                f = var17;
                                                g = new Integer[8];
                                                k = new HashMap(13);
                                                var10003 = new byte[]{(byte)(var22 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                                for (int var1 = 1; var1 < 8; ++var1) {
                                                    var10003[var1] = (byte)(var22 << var1 * 8 >>> 56);
}
                                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                                long[] var6 = new long[2];
                                                int var3 = 0;
                                                String var4 = "\u0089\u009ej\u00db\u00e9P\u00b4~\u00cb\u000e\u00cbgZ\u00b1m\u007f";
                                                int var5 = "\u0089\u009ej\u00db\u00e9P\u00b4~\u00cb\u000e\u00cbgZ\u00b1m\u007f".length();
                                                int var2 = 0;
                                                do {
                                                    int var48 = var2;
                                                    byte[] var7 = var4.substring(var48, var2 += 8).getBytes("ISO-8859-1");
                                                    var48 = var3++;
                                                    long var8 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                                    byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                                    var6[var48] = var63 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                                } while (var2 < var5);
                                                i = var6;
                                                j = new Long[2];
                                                return;
}
                                            default: {
                                                var40[var10001] = var63;
                                                if (var13 < var16) continue block12;
                                                var15 = "&\u000b\r\u00a1(k\u009cJw \ba\u00b7\u00f7\u00c5k";
                                                var16 = "&\u000b\r\u00a1(k\u009cJw \ba\u00b7\u00f7\u00c5k".length();
                                                var13 = 0;
}
}
                                        int var47 = var13;
                                        var18 = var15.substring(var47, var13 += 8).getBytes("ISO-8859-1");
                                        var40 = var17;
                                        var10001 = var14++;
                                        var55 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                        var59 = 0;
}
}
}
                            var27 = var28.charAt(var36);
                            break;
}
                        default: {
                            var31[var29++] = var51;
                            if ((var36 += var27) < var30) {
                                var27 = var28.charAt(var36);
                                continue block9;
}
                            var28 = "\u0018\u0086\u00db\u0099G\u008b!\u00fe/\u0015\u00e2\u0000\u0010\u00b7\u00bb\u0080\u00f0\u00d3\u009d\u00d3\u0000\u009b(\t.\u00c3\u0087\u00c8Jbv\u00e0\u0010a\u00cc\u009e\u00fa\u00d7b\u00de\u00d5\u00fc\u00fb\u0084\u00a0c3\u0007\u0013";
                            var30 = "\u0018\u0086\u00db\u0099G\u008b!\u00fe/\u0015\u00e2\u0000\u0010\u00b7\u00bb\u0080\u00f0\u00d3\u009d\u00d3\u0000\u009b(\t.\u00c3\u0087\u00c8Jbv\u00e0\u0010a\u00cc\u009e\u00fa\u00d7b\u00de\u00d5\u00fc\u00fb\u0084\u00a0c3\u0007\u0013".length();
                            var27 = 32;
                            var36 = -1;
}
}
                    var37 = var28.substring(++var36, var36 + var27);
                    var10001 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var33) {
            throw new RuntimeException(var33);
}
}
    // R11_SEMANTIC_RECOVERY_MARKER
    static {
        KEY_OFFSETS = new byte[]{39, 57, 59, 32, 29, 12, 48, 9, 40, 35, 20, 47, 44, 1, 25, 42, 11, 5, 28, 36, 41, 27, 14, 60, 2, 45, 52, 31, 23, 38, 62, 33, 24, 17, 15, 0, 37, 8, 46, 53, 61, 21, 30, 6, 16, 49, 51, 3, 55, 18, 50, 34, 63, 22, 10, 58, 56, 26, 54, 19, 4, 13, 43, 7};
        a = 55479544243313L;
        zkm$clinit();
        H = new LinkedHashMap<Integer, String>();
        G = new CopyOnWriteArraySet<BlockPos>();
        I = null;
}
}