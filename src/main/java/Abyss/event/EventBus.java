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
}
            catch (Throwable var13) {
                this.O(var13, 128094061068843L);
}
            if (var1.a() || var1 instanceof StoppableEvent && ((StoppableEvent)var1).p()) break;
}
}
    private static void v(ListenerBinding var0, Object var1, int var2) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = ((long)var2 << 32 | 0x16D4329BL) ^ a;
        long var7 = var5 ^ 0x123FF3A27F1L;
        ListenerBinding.d(var0).c(var7, var1);
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
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
                ((EventSubscriber)var3).x(var4, this);
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
            for (ListenerBinding listenerBinding : var2) {
                ListenerBinding.S(listenerBinding, false);
}
}
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
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
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
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
            return "PASS";
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