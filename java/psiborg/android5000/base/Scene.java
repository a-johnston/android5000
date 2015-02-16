package psiborg.android5000.base;

import java.util.LinkedList;

public class Scene {
    private LinkedList<GameObject> list;
    public Scene() {
        list = new LinkedList<GameObject>();
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
    public void load() {
        for (GameObject o : list) {
            o.load();
        }
    }
    public void unload() {
        for (GameObject o : list) {
            o.unload();
        }
    }
    public void step() {
        for (GameObject o : list) {
            o.step();
        }
    }
    public void draw() {
        for (GameObject o : list) {
            o.draw();
        }
    }
}
