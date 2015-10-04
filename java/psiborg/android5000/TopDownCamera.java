package psiborg.android5000;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Vector3;

public class TopDownCamera extends GameObject {
    private Camera cam;

    public TopDownCamera() {
        cam = new Camera(
                new Vector3(-5, 8.0, -5.0),
                new Vector3(0.0, 0.0, 0.0),
                new Vector3(0.0, 1.0, 0.0),
                70f, 1f, 100f);
        cam.setMain();
    }

    @Override
    protected void unload() {
        cam.unsetMain();
    }

    public void accept(float dx, float dy) {
        cam.translate(new Vector3((dy+dx)/200f, 0, (dy-dx)/200f));
    }
}
