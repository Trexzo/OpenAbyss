/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.FieldAccessors;
import Abyss.internal.accessor.FieldReader;
import Abyss.internal.synthetic.TypedValueStoreCtorMarker;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

final class TypedValueStore {
    private static long private final Field w;
    private final Field p;
    private byte y;

    private static Float lambda$getFloat$6(Object var0, Field var1) throws IllegalAccessException, Throwable {
        return Float.valueOf(var1.getFloat(var0));
}
    private static Boolean lambda$getBoolean$2(Object var0, Field var1) throws IllegalAccessException, Throwable {
        return var1.getBoolean(var0);
}
    private static Object lambda$setInt$5(Object var0, int var1, Field var2) throws IllegalAccessException, Throwable {
        var2.setInt(var0, var1);
        return null;
}
    private static Double lambda$getDouble$8(Object var0, Field var1) throws IllegalAccessException, Throwable {
        return var1.getDouble(var0);
}
    TypedValueStore(Field var1, Field var2, byte var3, TypedValueStoreCtorMarker var4) {
        this(var1, var2, var3);
}
    private <T> T Q(FieldReader<T> var1, String var2) {
        Field var3 = this.L();
        try {
            return var1.M(var3);
}
        catch (Throwable var9) {
            Field var5 = this.v();
            if (var5 == null) {
                throw FieldAccessors.j(var2, var9);
}
            try {
                T var6 = var1.M(var5);
                this.W(var5);
                return var6;
}
            catch (Throwable var8) {
                RuntimeException var7 = FieldAccessors.j(var2, var8);
                var7.addSuppressed(var9);
                throw var7;
}
}
}
    private static Object lambda$setDouble$9(Object var0, double var1, Field var3) throws IllegalAccessException, Throwable {
        var3.setDouble(var0, var1);
        return null;
}
    public float V(Object var1) {
        return this.Q(zkm$fld -> TypedValueStore.lambda$getFloat$6(var1, zkm$fld), "float field").floatValue();
}
    private static Object lambda$set$1(Object var0, Object var1, Field var2) throws IllegalAccessException, Throwable {
        var2.set(var0, var1);
        return null;
}
    private static Object lambda$setFloat$7(Object var0, float var1, Field var2) throws IllegalAccessException, Throwable {
        var2.setFloat(var0, var1);
        return null;
}
    public void C(Object var1, float var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.Q(zkm$fld -> TypedValueStore.lambda$setFloat$7(var1, var2, zkm$fld), "float field");
}
    private TypedValueStore(Field var1, Field var2, byte var3) {
        this.p = var1;
        this.w = var2;
        this.y = var3;
}
    private void W(Field var1) {
        if (var1 != null) {
            this.y = (byte)(var1 == this.p ? 1 : 2);
}
}
    public double w(Object var1) {
        return this.Q(zkm$fld -> TypedValueStore.lambda$getDouble$8(var1, zkm$fld), "double field");
}
    public boolean n(Object var3) {
        return this.Q(zkm$fld -> TypedValueStore.lambda$getBoolean$2(var3, zkm$fld), "boolean field");
}
    public void O(Object var1, boolean var4) {
        this.Q(zkm$fld -> TypedValueStore.lambda$setBoolean$3(var1, var4, zkm$fld), "boolean field");
}
    private static Object lambda$setBoolean$3(Object var0, boolean var1, Field var2) throws IllegalAccessException, Throwable {
        var2.setBoolean(var0, var1);
        return null;
}
    public void d(Object var1, Object var4) {
        this.Q(zkm$fld -> TypedValueStore.lambda$set$1(var1, var4, zkm$fld), "field");
}
    public Object v(Object var1) {
        return this.Q(zkm$fld -> TypedValueStore.lambda$get$0(var1, zkm$fld), "field");
}
    private Field v() {
        return this.y == 2 ? this.p : this.w;
}
    private Field L() {
        return this.y == 2 ? this.w : this.p;
}
    private static Object lambda$get$0(Object var0, Field var1) throws IllegalAccessException, Throwable {
        return var1.get(var0);
}
    public void f(Object var3, double var4) {
        this.Q(zkm$fld -> TypedValueStore.lambda$setDouble$9(var3, var4, zkm$fld), "double field");
}
    private static Integer lambda$getInt$4(Object var0, Field var1) throws IllegalAccessException, Throwable {
        return var1.getInt(var0);
}
    public void T(Object var1, int var2) {
        this.Q(zkm$fld -> TypedValueStore.lambda$setInt$5(var1, var2, zkm$fld), "int field");
}
    public int m(Object var1) {
        return this.Q(zkm$fld -> TypedValueStore.lambda$getInt$4(var1, zkm$fld), "int field");
}
}