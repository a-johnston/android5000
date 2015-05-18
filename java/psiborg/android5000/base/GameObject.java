package psiborg.android5000.base;

public abstract class GameObject extends Loadable {
    public void step() {}
    protected abstract void drawAsset();
    public final void draw() {
        if (isLoaded()) {
            drawAsset();
        }
    }
}
