package psiborg.android5000.base;

public abstract class GameObject extends Asset {
    public Transform transform = new Transform();
    public void step()   {}
    public void draw() {}
}
