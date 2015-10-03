package psiborg.android5000.base;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private boolean loaded;
    private List<GameObject> list;

    public Scene() {
        list = new ArrayList<>();
    }

    public void add(GameObject o) {
        list.add(o);
    }

    public void remove(GameObject o) {
        list.remove(o);
    }

    public boolean contains(GameObject o) {
        return list.contains(o);
    }

    public List<GameObject> objects() {
        return list;
    }

    public void load() {
        if (loaded) {
            return;
        }
        for (GameObject o : list) {
            o.loadAsset();
        }
        loaded = true;
    }

    public void unload() {
        if (!loaded) {
            return;
        }
        for (GameObject o : list) {
            o.unloadAsset();
        }
        loaded = false;
    }

    public void step() {
        for (GameObject o : list) {
            o.step();
        }
    }

    public void draw() {
        for (GameObject o : list) {
            o.drawAsset();
        }
    }
}
