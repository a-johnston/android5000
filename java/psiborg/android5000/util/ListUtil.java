package psiborg.android5000.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    public static <E> List<E> pad(List<E> list, int desiredSize, E defaultValue) {
        while (list.size() < desiredSize) {
            list.add(defaultValue);
        }
        return list;
    }

    public static <E> List<E> copy(List<E> list) {
        List<E> r = new ArrayList<>(list.size());
        r.addAll(list);
        return r;
    }
}
