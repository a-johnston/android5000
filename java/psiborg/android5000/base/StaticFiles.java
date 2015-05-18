package psiborg.android5000.base;

import java.util.HashMap;

import psiborg.android5000.util.IO;

public class StaticFiles {
    private static HashMap<String, String> map = new HashMap<String, String>();
    public static String load(String filename) {
        if (map.containsKey(filename)) {
            return map.get(filename);
        } else {
            String contents = IO.readFile(filename);
            map.put(filename, contents);
            return contents;
        }
    }
    public static void unload(String filename) {
        map.remove(filename);
    }
}
