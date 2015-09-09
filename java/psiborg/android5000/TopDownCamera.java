package psiborg.android5000;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Vector3;

public class TopDownCamera extends GameObject implements SimpleMotion.SimpleTouchListener {
    private Camera cam;

    @Override
    protected void loadAsset() {
        cam = new Camera(
                new float[]{0.0f,  3.0f,  5.0f},
                new float[]{0.0f,  0.0f,  0.0f},
                new float[]{0.0f,  1.0f,  0.0f},
                70f,1f,10f);
        cam.setMain();
        SimpleMotion.addListener(this);
    }

    @Override
    public void step() {
    }

    @Override
    protected void unloadAsset() {
        cam.unsetMain();
        SimpleMotion.removeListener(this);
    }

    @Override
    public void accept(float dx, float dy) {
        cam.translate(new Vector3(-dx/200f, 0, -dy/200f));
    }
}
