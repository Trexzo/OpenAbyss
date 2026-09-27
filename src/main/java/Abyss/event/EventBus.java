/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event;

import Abyss.event.Event;
import Abyss.event.EventInvoker;
import Abyss.event.EventSubscriber;
import Abyss.event.ListenerBinding;
import Abyss.event.events.StoppableEvent;
import Abyss.ui.ModuleTagRenderer;
import Abyss.util.ClientUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class EventBus {
    private static long a;
    static {
        a = 34531714106406L;
    }
        private final Map<Class<? extends Throwable>, Long> Y;
    private final Map<Class<?>, List<ListenerBinding<?>>> P;
    private static long e;
    private final Map<Object, List<ListenerBinding<?>>> U = new ConcurrentHashMap();
    private final Map<Object, Boolean> ownerActive = new ConcurrentHashMap();
    private final Map<ListenerBinding<?>, String> ownerNames = new ConcurrentHashMap();
    private final Map<String, Boolean> recordedFailures = new ConcurrentHashMap();
    private static final boolean RUNTIME_TRACE = Boolean.getBoolean("abyss.eventRuntimeTrace");
    private final Map<Class<?>, Boolean> tracedEventTypes = new ConcurrentHashMap();
    private boolean failureEvidenceEnabled = true;
    private static final Comparator<ListenerBinding> PRIORITY_DESC;
    private final Map<Class<?>, ListenerBinding<?>[]> snapshots = new ConcurrentHashMap();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private volatile boolean batchMode;

    public void s(Object var1, long var2) {
        long var4 = var2 ^ 0x2FC649B662ECL;
        if (var1 != null) {
            this.z(var4, var1);
            List<ListenerBinding<?>> var6 = this.U.get(var1);
            if (var6 != null) {
                this.ownerActive.put(var1, Boolean.TRUE);
                for (ListenerBinding listenerBinding : var6) {
                    ListenerBinding.S(listenerBinding, true);
}
}
}
}
    public void e(Event var1, long var2) {
        if (var1 == null) {
            return;
}
        Class<?> type = var1.getClass();
        ListenerBinding<?>[] snap = this.snapshots.get(type);
        if (snap == null || snap.length == 0) {
            return;
}
        for (ListenerBinding<?> var11 : snap) {
            if (!ListenerBinding.o(var11)) continue;
            try {
                EventBus.v(var11, var1, 3958);
                this.traceRuntimeDispatch(var1, var11);
}
            catch (Throwable var13) {
                this.recordFailure(var11, var1, var13);
                this.O(var13, 128094061068843L);
}
            if (var1.a() || var1 instanceof StoppableEvent && ((StoppableEvent)var1).p()) break;
}
}
    private synchronized void traceRuntimeDispatch(Event event, ListenerBinding<?> binding) {
        if (!RUNTIME_TRACE || event == null || binding == null) {
            return;
}
        Class<?> type = event.getClass();
        if (this.tracedEventTypes.putIfAbsent(type, Boolean.TRUE) != null) {
            return;
}
        String owner = this.ownerNames.get(binding);
        if (owner == null) {
            owner = "<unknown>";
}
        String invoker = "<unknown>";
        try {
            EventInvoker callback = ListenerBinding.d(binding);
            if (callback != null) {
                invoker = callback.getClass().getName();
}
}
        catch (Throwable ignored) {
}
        String line = System.currentTimeMillis() + "\t" + type.getName() + "\t" + owner + "\t" + invoker;
        try {
            try (OutputStreamWriter out = new OutputStreamWriter(
                    new FileOutputStream(new File("abyss-event-stage.txt"), true), "UTF-8")) {
                out.write(line + "\n");
}
}
        catch (Throwable ignored) {
}
}

    private void recordFailure(ListenerBinding<?> binding, Event event, Throwable failure) {
        String owner = this.ownerNames.get(binding);
        if (owner == null) {
            owner = "<unknown>";
}
        String eventType = event == null ? "<null>" : event.getClass().getName();
        String invoker = "<unknown>";
        try {
            EventInvoker callback = ListenerBinding.d(binding);
            if (callback != null) {
                invoker = callback.getClass().getName();
}
}
        catch (Throwable ignored) {
}
        String message = String.valueOf(failure.getMessage()).replace('\r', ' ').replace('\n', ' ');
        String signature = owner + "|" + eventType + "|" + failure.getClass().getName() + "|" + message;
        if (this.recordedFailures.putIfAbsent(signature, Boolean.TRUE) != null) {
            return;
}
        if (!this.failureEvidenceEnabled) {
            return;
}
        String line = System.currentTimeMillis() + "\t" + owner + "\t" + eventType + "\t" + invoker
                + "\t" + failure.getClass().getName() + "\t" + message;
        try {
            try (OutputStreamWriter out = new OutputStreamWriter((OutputStream)new FileOutputStream(new File("abyss-event-failure.txt"), true), "UTF-8");){
                out.write(line + "\n");
}
}
        catch (Throwable ignored) {
}
        System.err.println("[ABYSSDIAG] event callback failure " + line);
}

    private static void v(ListenerBinding var0, Object var1, int var2) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = ((long)var2 << 32 | 0x16D4329BL) ^ a;
        long var7 = var5 ^ 0x123FF3A27F1L;
        ListenerBinding.d(var0).c(var7, var1);
}
    public void z(long var1, Object var3) {
        long var4 = var1 ^ 0x7FD2D60C5916L;
        if (var3 != null && !this.U.containsKey(var3)) {
            boolean needInit = false;
            boolean locked = false;
            try {
                locked = this.rwLock.writeLock().tryLock(100L, TimeUnit.MILLISECONDS);
}
            catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
}
            if (!locked) {
                return;
}
            try {
                if (!this.U.containsKey(var3)) {
                    ArrayList var6 = new ArrayList();
                    this.U.put(var3, var6);
                    needInit = true;
}
}
            finally {
                this.rwLock.writeLock().unlock();
}
            if (needInit && var3 instanceof EventSubscriber) {
                try {
                    ((EventSubscriber)var3).x(var4, this);
}
                catch (RuntimeException | Error failure) {
                    this.rwLock.writeLock().lock();
                    try {
                        List<ListenerBinding<?>> failedBindings = this.U.remove(var3);
                        this.ownerActive.remove(var3);
                        if (failedBindings != null && !failedBindings.isEmpty()) {
                            for (ListenerBinding<?> failedBinding : failedBindings) {
                                this.ownerNames.remove(failedBinding);
}
                            for (Map.Entry<Class<?>, List<ListenerBinding<?>>> entry : this.P.entrySet()) {
                                List<ListenerBinding<?>> listeners = entry.getValue();
                                if (!listeners.removeAll(failedBindings)) {
                                    continue;
}
                                if (listeners.isEmpty()) {
                                    this.P.remove(entry.getKey(), listeners);
                                    this.snapshots.remove(entry.getKey());
}
                                else {
                                    listeners.sort(PRIORITY_DESC);
                                    this.snapshots.put(entry.getKey(), listeners.toArray(new ListenerBinding[0]));
}
}
}
}
                    finally {
                        this.rwLock.writeLock().unlock();
}
                    throw failure;
}
}
}
}
    private void O(Throwable var1, long var2) {
        if (ModuleTagRenderer.X) {
            long var6 = System.currentTimeMillis();
            Long var8 = this.Y.put(var1.getClass(), var6);
            if (var8 == null || var6 - var8 >= e) {
                StringWriter var9 = new StringWriter();
                var1.printStackTrace(new PrintWriter(var9));
                ClientUtil.t(48081174263320L, var9.toString());
}
}
}
    public EventBus() {
        this.P = new ConcurrentHashMap();
        this.Y = new ConcurrentHashMap<Class<? extends Throwable>, Long>();
}
    public void B(Object var1) {
        List<ListenerBinding<?>> var2;
        if (var1 != null && (var2 = this.U.get(var1)) != null) {
            this.ownerActive.put(var1, Boolean.FALSE);
            for (ListenerBinding listenerBinding : var2) {
                ListenerBinding.S(listenerBinding, false);
}
}
}
    public boolean isOwnerActive(Object owner) {
        if (owner == null || !Boolean.TRUE.equals(this.ownerActive.get(owner))) {
            return false;
}
        List<ListenerBinding<?>> bindings = this.U.get(owner);
        if (bindings == null) {
            return false;
}
        if (bindings.isEmpty()) {
            return !(owner instanceof EventSubscriber);
}
        for (ListenerBinding<?> binding : bindings) {
            if (!ListenerBinding.o(binding)) {
                return false;
}
}
        return true;
}
    public void R(Object var1, Class var2, int var5, EventInvoker var6) {
        if (var1 == null || var2 == null || var6 == null) throw new NullPointerException("owner, eventType and invoker are required");
        boolean locked = false;
        try {
            locked = this.rwLock.writeLock().tryLock(100L, TimeUnit.MILLISECONDS);
}
        catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
}
        if (!locked) {
            return;
}
        try {
            List<ListenerBinding<?>> var7 = this.U.get(var1);
            if (var7 == null) {
                throw new IllegalStateException("Generated listener bound outside buildCache");
}
            ListenerBinding var8 = new ListenerBinding(var6, var5);
            this.ownerNames.put(var8, var1.getClass().getName());
            var7.add(var8);
            List<ListenerBinding<?>> var9 = this.P.computeIfAbsent(var2, var0 -> new ArrayList<ListenerBinding<?>>());
            var9.add(var8);
            if (this.batchMode) return;
            var9.sort(PRIORITY_DESC);
            this.snapshots.put(var2, var9.toArray(new ListenerBinding[0]));
            return;
}
        finally {
            this.rwLock.writeLock().unlock();
}
}
    public void beginBatch() {
        this.batchMode = true;
}
    public void endBatch() {
        this.batchMode = false;
        boolean locked = false;
        try {
            locked = this.rwLock.writeLock().tryLock(100L, TimeUnit.MILLISECONDS);
}
        catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
}
        if (!locked) {
            return;
}
        try {
            for (Map.Entry<Class<?>, List<ListenerBinding<?>>> entry : this.P.entrySet()) {
                List<ListenerBinding<?>> list = entry.getValue();
                list.sort(PRIORITY_DESC);
                this.snapshots.put(entry.getKey(), list.toArray(new ListenerBinding[0]));
}
}
        finally {
            this.rwLock.writeLock().unlock();
}
}
    public static String selfTest() {
        try {
            final EventBus bus = new EventBus();
            final List<String> calls = new ArrayList<String>();
            class TestEvent extends Event {
}
            Object passiveOwner = new Object();
            bus.s(passiveOwner, 0L);
            if (!bus.isOwnerActive(passiveOwner)) {
                return "FAIL passive-owner-registration";
}
            bus.B(passiveOwner);
            if (bus.isOwnerActive(passiveOwner)) {
                return "FAIL passive-owner-disable";
}
            bus.s(passiveOwner, 0L);
            if (!bus.isOwnerActive(passiveOwner)) {
                return "FAIL passive-owner-resubscribe";
}
            EventSubscriber emptySubscriber = new EventSubscriber(){
                @Override
                public void x(long seed, EventBus target) {
}
            };
            bus.s(emptySubscriber, 0L);
            if (bus.isOwnerActive(emptySubscriber)) {
                return "FAIL empty-subscriber-proven-active";
}

            final EventBus rollbackBus = new EventBus();
            final List<String> retryCalls = new ArrayList<String>();
            final int[] registrationAttempts = new int[]{0};
            EventSubscriber flakySubscriber = new EventSubscriber(){
                @Override
                public void x(long seed, EventBus target) {
                    ++registrationAttempts[0];
                    target.R(this, TestEvent.class, 3, new EventInvoker(){
                        @Override
                        public void c(long callbackSeed, Object event) {
                            retryCalls.add("retry");
}
                    });
                    if (registrationAttempts[0] == 1) {
                        throw new IllegalStateException("registration-probe");
}
}
            };
            boolean registrationFailed = false;
            try {
                rollbackBus.s(flakySubscriber, 0L);
}
            catch (IllegalStateException expected) {
                registrationFailed = true;
}
            if (!registrationFailed || rollbackBus.isOwnerActive(flakySubscriber)) {
                return "FAIL registration-rollback-state attempts=" + registrationAttempts[0]
                        + " active=" + rollbackBus.isOwnerActive(flakySubscriber);
}
            rollbackBus.e(new TestEvent(), 0L);
            if (!retryCalls.isEmpty()) {
                return "FAIL registration-rollback-visible " + retryCalls;
}
            rollbackBus.s(flakySubscriber, 0L);
            if (!rollbackBus.isOwnerActive(flakySubscriber) || registrationAttempts[0] != 2) {
                return "FAIL registration-retry attempts=" + registrationAttempts[0]
                        + " active=" + rollbackBus.isOwnerActive(flakySubscriber);
}
            rollbackBus.e(new TestEvent(), 0L);
            if (!retryCalls.equals(Arrays.asList("retry"))) {
                return "FAIL registration-retry-dispatch " + retryCalls;
}

            EventSubscriber low = new EventSubscriber(){
                @Override
                public void x(long seed, EventBus target) {
                    target.R(this, TestEvent.class, 1, new EventInvoker(){
                        @Override
                        public void c(long callbackSeed, Object event) {
                            calls.add("low");
}
                    });
}
            };
            EventSubscriber high = new EventSubscriber(){
                @Override
                public void x(long seed, EventBus target) {
                    target.R(this, TestEvent.class, 5, new EventInvoker(){
                        @Override
                        public void c(long callbackSeed, Object event) {
                            calls.add("high");
}
                    });
}
            };
            bus.beginBatch();
            bus.s(low, 0L);
            bus.s(high, 0L);
            bus.e(new TestEvent(), 0L);
            if (!calls.isEmpty()) {
                return "FAIL batch-visible-before-end " + calls;
}
            bus.endBatch();
            bus.e(new TestEvent(), 0L);
            if (!calls.equals(Arrays.asList("high", "low"))) {
                return "FAIL priority " + calls;
}
            calls.clear();
            bus.B(high);
            bus.e(new TestEvent(), 0L);
            if (!calls.equals(Arrays.asList("low"))) {
                return "FAIL disable " + calls;
}
            calls.clear();
            bus.s(high, 0L);
            bus.s(high, 0L);
            if (!bus.isOwnerActive(high) || !bus.isOwnerActive(low)) {
                return "FAIL active-proof high=" + bus.isOwnerActive(high) + " low=" + bus.isOwnerActive(low);
}
            bus.e(new TestEvent(), 0L);
            if (!calls.equals(Arrays.asList("high", "low"))) {
                return "FAIL resubscribe-idempotence " + calls;
}
            calls.clear();
            bus.B(high);
            bus.B(high);
            if (bus.isOwnerActive(high) || !bus.isOwnerActive(low)) {
                return "FAIL disable-active-proof high=" + bus.isOwnerActive(high) + " low=" + bus.isOwnerActive(low);
}
            bus.e(new TestEvent(), 0L);
            if (!calls.equals(Arrays.asList("low"))) {
                return "FAIL double-disable " + calls;
}
            bus.s(high, 0L);
            calls.clear();
            EventSubscriber stopper = new EventSubscriber(){
                @Override
                public void x(long seed, EventBus target) {
                    target.R(this, TestEvent.class, 10, new EventInvoker(){
                        @Override
                        public void c(long callbackSeed, Object event) {
                            calls.add("stop");
                            ((Event)event).I(0, 0L);
}
                    });
}
            };
            bus.s(stopper, 0L);
            bus.e(new TestEvent(), 0L);
            if (!calls.equals(Arrays.asList("stop"))) {
                return "FAIL cancellation " + calls;
}

            calls.clear();
            class ThrowEvent extends Event {
}
            bus.failureEvidenceEnabled = false;
            EventSubscriber thrower = new EventSubscriber(){
                @Override
                public void x(long seed, EventBus target) {
                    target.R(this, ThrowEvent.class, 10, new EventInvoker(){
                        @Override
                        public void c(long callbackSeed, Object event) {
                            calls.add("throw");
                            throw new RuntimeException("eventbus-selftest");
}
                    });
}
            };
            EventSubscriber after = new EventSubscriber(){
                @Override
                public void x(long seed, EventBus target) {
                    target.R(this, ThrowEvent.class, 1, new EventInvoker(){
                        @Override
                        public void c(long callbackSeed, Object event) {
                            calls.add("after");
}
                    });
}
            };
            bus.s(thrower, 0L);
            bus.s(after, 0L);
            bus.e(new ThrowEvent(), 0L);
            if (!calls.equals(Arrays.asList("throw", "after"))) {
                return "FAIL callback-failure-continue " + calls;
}
            if (bus.recordedFailures.size() != 1) {
                return "FAIL callback-failure-recorded " + bus.recordedFailures.size();
}
            calls.clear();
            bus.e(new ThrowEvent(), 0L);
            if (!calls.equals(Arrays.asList("throw", "after"))) {
                return "FAIL callback-failure-repeat " + calls;
}
            if (bus.recordedFailures.size() != 1) {
                return "FAIL callback-failure-dedup " + bus.recordedFailures.size();
}
            return "PASS passive-owner-lifecycle priority cancellation resubscribe-idempotence callback-failure-isolation";
}
        catch (Throwable throwable) {
            return "FAIL " + throwable.getClass().getName() + ": " + throwable.getMessage();
}
}
    static {
        PRIORITY_DESC = Comparator.<ListenerBinding>comparingInt(var0 -> ListenerBinding.k(var0)).reversed();
        e = 500L;
}
}