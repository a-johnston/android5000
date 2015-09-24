package psiborg.android5000.util;

import java.util.HashMap;
import java.util.Map;

public class Meshes {
    private static Map<String, Mesh> map;

    public static Mesh getMesh(String asset) {
        if (map == null) {
            map = new HashMap<>();
        }
        Mesh r = map.get(asset);
        if (r == null) {
            r = IO.loadObjAsync(asset);
            map.put(asset, r);
        }
        return r;
    }
}
