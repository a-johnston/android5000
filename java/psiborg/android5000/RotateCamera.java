package psiborg.android5000;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.GameObject;

public class RotateCamera extends GameObject implements SimpleMotion.SimpleTouchListener {
    public static float yaw, pitch, radius = 5f;
    private Camera cam;

    @Override
    public void load() {
        cam = new Camera(
                new float[]{3.0f,  3.0f,  3.0f},
                new float[]{0.0f,  0.0f,  0.0f},
                new float[]{0.0f,  0.0f,  1.0f},
                70f,1f,100f);
        cam.setMain();
        SimpleMotion.addListener(this);
    }

    @Override
    public void step() {
        pitch = (float)(Math.max(Math.min(pitch, Math.PI/2-.001),-Math.PI/2+.001));
        cam.updateLook(new float[]{(float)(Math.cos(yaw)*Math.cos(pitch)*radius),
                        (float)(Math.sin(pitch)*radius),
                        (float)(Math.sin(yaw)*Math.cos(pitch)*radius)},
                new float[]{0,0,0},
                new float[]{0,1,0});
    }

    @Override
    public void unload() {
        cam.unsetMain();
        SimpleMotion.removeListener(this);
    }

    @Override
    public void accept(float dx, float dy) {
        yaw   += dx/200f;
        pitch += dy/200f;
    }
}
