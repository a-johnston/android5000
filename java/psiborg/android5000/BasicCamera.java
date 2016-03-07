package psiborg.android5000;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Vector3;

public class BasicCamera extends GameObject {
    public static float yaw, pitch, radius = 17f;
    private Camera cam;

    public BasicCamera() {
        cam = new Camera(
                new Vector3(3.0,  3.0,  3.0),
                new Vector3(0.0,  0.0,  0.0),
                new Vector3(0.0,  0.0,  1.0),
                70f,1f,100f);
        cam.setMain();
    }

    @Override
    public void unload() {
        cam.unsetMain();
    }
}
