package psiborg.android5000;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Vector3;

public class AttachableCamera extends GameObject {
    private Camera cam;
    private GameObject object;

    public AttachableCamera() {
        cam = new Camera(
                new Vector3(-10.0, 0.0, 0.0),
                new Vector3(  0.0, 0.0, 0.0),
                new Vector3(  0.0, 1.0, 0.0),
                70f, .1f, 100f);
        cam.setMain();
    }

    public void attach(GameObject object) {
        this.object = object;
    }

    @Override
    public void step() {
        cam.updateView(object.transform);
    }

    @Override
    protected void unload() {
        cam.unsetMain();
    }

    public void accept(float dx, float dy) {
        cam.translate(new Vector3((dy+dx)/200f, 0, (dy-dx)/200f));
    }
}
