package psiborg.android5000.base;

import java.util.LinkedList;

public class Scene {
    private LinkedList<Object> list;
    public Scene() {
        list = new LinkedList<Object>();
    }
    public void add(Object o) {
        list.add(o);
    }
    public void remove(Object o) {
        list.remove(o);
    }
    public boolean contains(Object o) {
        return list.contains(o);
    }
    public void load() {
        for (Object o : list) {
            o.load();
        }
    }
    public void unload() {
        for (Object o : list) {
            o.unload();
        }
    }
    public void step() {
        for (Object o : list) {
            o.step();
        }
    }
    public void draw(float[] mvp) {
        for (Object o : list) {
            o.shader.draw(mvp);
        }
    }
}
