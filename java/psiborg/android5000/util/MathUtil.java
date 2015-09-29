package psiborg.android5000.util;

public class MathUtil {
    @SafeVarargs
    public static <T extends Comparable<T>> T max(T... values) {
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

    public static double removeEpsilon(double value) {
        return removeEpsilon(value, 5);
    }

    public static double removeEpsilon(double value, int places) {
        double tens = Math.pow(10.0, places);
        return Math.round(value * tens) / tens;
    }
}
