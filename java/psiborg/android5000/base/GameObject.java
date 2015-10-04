package psiborg.android5000.base;

public abstract class GameObject extends Loadable {
    public String name = this.getClass().getSimpleName();
    public String tag = "";
    public Transform transform = Transform.ID;

    public void step() {}
    protected void draw() {}
    public final void drawAsset() {
        if (isLoaded()) {
            draw();
        }
    }
}
