package psiborg.android5000.util;

public class MathUtil {
    public static Integer max(Integer... ints) {
        if (ints == null || ints.length == 0) {
            return null;
        }
        Integer m = ints[0];
        for (int i = 1; i < ints.length; i++) {
            if (ints[i].compareTo(m) > 0) {
                m = ints[i];
            }
        }
        return m;
    }
}
