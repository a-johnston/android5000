package psiborg.android5000.util;

public class MathUtil {
    public static <T extends Comparable> T max(T... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        T m = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i].compareTo(m) > 0) {
                m = values[i];
            }
        }
        return m;
    }
}
