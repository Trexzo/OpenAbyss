/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.restore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class AbyssGuiJson {
    private final String s;
    private int i;

    private AbyssGuiJson(String text) {
        this.s = text;
}
    public static Map<String, Object> parseObject(String text) {
        if (text == null) {
            return null;
}
        AbyssGuiJson p = new AbyssGuiJson(text);
        p.ws();
        Object v2 = p.value();
        p.ws();
        return v2 instanceof Map ? AbyssGuiJson.asMap(v2) : null;
}
    public static Map<String, Object> asMap(Object o2) {
        return o2 instanceof Map ? (Map)o2 : null;
}
    public static boolean asBool(Object o2, boolean def) {
        if (o2 instanceof Boolean) {
            return (Boolean)o2;
}
        if (o2 instanceof Double) {
            return (Double)o2 != 0.0;
}
        if (o2 instanceof String) {
            return Boolean.parseBoolean((String)o2);
}
        return def;
}
    public static double asNum(Object o2, double def) {
        if (o2 instanceof Double) {
            return (Double)o2;
}
        if (o2 instanceof Boolean) {
            return (Boolean)o2 != false ? 1.0 : 0.0;
}
        if (o2 instanceof String) {
            try {
                return Double.parseDouble((String)o2);
}
            catch (Throwable t2) {
                return def;
}
}
        return def;
}
    public static String write(Map<String, Object> root) {
        StringBuilder b = new StringBuilder();
        AbyssGuiJson.writeValue(root, b, 1);
        b.append('\n');
        return b.toString();
}
    private static void writeValue(Object v2, StringBuilder b, int depth) {
        if (v2 == null) {
            b.append("null");
        } else if (v2 instanceof Map) {
            Map<String, Object> m2 = AbyssGuiJson.asMap(v2);
            b.append("{\n");
            Iterator<Map.Entry<String, Object>> it = m2.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> e = it.next();
                AbyssGuiJson.indent(b, depth);
                AbyssGuiJson.quote(e.getKey(), b);
                b.append(": ");
                AbyssGuiJson.writeValue(e.getValue(), b, depth + 1);
                if (it.hasNext()) {
                    b.append(',');
}
                b.append('\n');
}
            AbyssGuiJson.indent(b, depth - 1);
            b.append('}');
        } else if (v2 instanceof List) {
            List l = (List)v2;
            b.append('[');
            for (int k = 0; k < l.size(); ++k) {
                if (k > 0) {
                    b.append(", ");
}
                AbyssGuiJson.writeValue(l.get(k), b, depth + 1);
}
            b.append(']');
        } else if (v2 instanceof Boolean) {
            b.append((Boolean)v2 != false ? "true" : "false");
        } else if (v2 instanceof Float) {
            float f = ((Float)v2).floatValue();
            b.append(Float.isNaN(f) || Float.isInfinite(f) ? "0.0" : Float.toString(f));
        } else if (v2 instanceof Number) {
            double d = ((Number)v2).doubleValue();
            if (v2 instanceof Integer || v2 instanceof Long || d == Math.rint(d) && !Double.isInfinite(d)) {
                if (v2 instanceof Integer || v2 instanceof Long) {
                    b.append(((Number)v2).longValue());
                } else {
                    b.append(d);
}
            } else {
                b.append(d);
}
        } else {
            AbyssGuiJson.quote(String.valueOf(v2), b);
}
}
    private static void indent(StringBuilder b, int depth) {
        for (int k = 0; k < depth; ++k) {
            b.append("  ");
}
}
    private static void quote(String v2, StringBuilder b) {
        b.append('\"');
        for (int k = 0; k < v2.length(); ++k) {
            char c = v2.charAt(k);
            if (c == '\"' || c == '\\') {
                b.append('\\').append(c);
                continue;
}
            if (c == '\n') {
                b.append("\\n");
                continue;
}
            if (c == '\r') {
                b.append("\\r");
                continue;
}
            if (c == '\t') {
                b.append("\\t");
                continue;
}
            if (c < ' ') {
                b.append(String.format("\\u%04x", c));
                continue;
}
            b.append(c);
}
        b.append('\"');
}
    private void ws() {
        while (this.i < this.s.length() && Character.isWhitespace(this.s.charAt(this.i))) {
            ++this.i;
}
}
    private Object value() {
        this.ws();
        if (this.i >= this.s.length()) {
            return null;
}
        char c = this.s.charAt(this.i);
        if (c == '{') {
            return this.object();
}
        if (c == '[') {
            return this.array();
}
        if (c == '\"') {
            return this.string();
}
        if (this.s.startsWith("true", this.i)) {
            this.i += 4;
            return Boolean.TRUE;
}
        if (this.s.startsWith("false", this.i)) {
            this.i += 5;
            return Boolean.FALSE;
}
        if (this.s.startsWith("null", this.i)) {
            this.i += 4;
            return null;
}
        return this.number();
}
    private Map<String, Object> object() {
        LinkedHashMap<String, Object> m2 = new LinkedHashMap<String, Object>();
        ++this.i;
        this.ws();
        if (this.i < this.s.length() && this.s.charAt(this.i) == '}') {
            ++this.i;
            return m2;
}
        while (this.i < this.s.length()) {
            String k;
            this.ws();
            String string = k = this.s.charAt(this.i) == '\"' ? this.string() : null;
            if (k == null) break;
            this.ws();
            if (this.i < this.s.length() && this.s.charAt(this.i) == ':') {
                ++this.i;
}
            m2.put(k, this.value());
            this.ws();
            if (this.i < this.s.length() && this.s.charAt(this.i) == ',') {
                ++this.i;
                continue;
}
            if (this.i >= this.s.length() || this.s.charAt(this.i) != '}') break;
            ++this.i;
            break;
}
        return m2;
}
    private List<Object> array() {
        ArrayList<Object> l = new ArrayList<Object>();
        ++this.i;
        this.ws();
        if (this.i < this.s.length() && this.s.charAt(this.i) == ']') {
            ++this.i;
            return l;
}
        while (this.i < this.s.length()) {
            l.add(this.value());
            this.ws();
            if (this.i < this.s.length() && this.s.charAt(this.i) == ',') {
                ++this.i;
                continue;
}
            if (this.i >= this.s.length() || this.s.charAt(this.i) != ']') break;
            ++this.i;
            break;
}
        return l;
}
    private String string() {
        char c;
        StringBuilder b = new StringBuilder();
        ++this.i;
        while (this.i < this.s.length() && (c = this.s.charAt(this.i++)) != '\"') {
            char e;
            if (c != '\\') {
                b.append(c);
                continue;
}
            if (this.i >= this.s.length()) break;
            if ((e = this.s.charAt(this.i++)) == 'n') {
                b.append('\n');
                continue;
}
            if (e == 'r') {
                b.append('\r');
                continue;
}
            if (e == 't') {
                b.append('\t');
                continue;
}
            if (e == 'b') {
                b.append('\b');
                continue;
}
            if (e == 'f') {
                b.append('\f');
                continue;
}
            if (e == 'u' && this.i + 4 <= this.s.length()) {
                b.append((char)Integer.parseInt(this.s.substring(this.i, this.i + 4), 16));
                this.i += 4;
                continue;
}
            b.append(e);
}
        return b.toString();
}
    private Object number() {
        char c;
        int start = this.i;
        while (this.i < this.s.length() && ((c = this.s.charAt(this.i)) == '-' || c == '+' || c == '.' || c == 'e' || c == 'E' || c >= '0' && c <= '9')) {
            ++this.i;
}
        if (this.i == start) {
            ++this.i;
            return null;
}
        try {
            return Double.parseDouble(this.s.substring(start, this.i));
}
        catch (Throwable t2) {
            return null;
}
}
}