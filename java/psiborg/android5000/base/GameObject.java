package psiborg.android5000.base;

public abstract class GameObject extends Loadable {
    public void step() {}
    protected void draw() {}
    public final void drawAsset() {
        if (isLoaded()) {
            draw();
        }
    }
}
