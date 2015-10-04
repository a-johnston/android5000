package psiborg.android5000;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Vector3;

public class RotateCamera extends GameObject {
    public static float yaw, pitch, radius = 17f;
    private Camera cam;

    public RotateCamera() {
        cam = new Camera(
                new Vector3(3.0,  3.0,  3.0),
                new Vector3(0.0,  0.0,  0.0),
                new Vector3(0.0,  1.0,  0.0),
                70f,1f,100f);
        cam.setMain();
    }

//    @Override
//    public void step() {
//        pitch = (float)(Math.max(Math.min(pitch, Math.PI/2-.001),-Math.PI/2+.001));
//        cam.updateFrom(new Vector3(
//                Math.cos(yaw)*Math.cos(pitch),
//                Math.sin(pitch),
//                Math.sin(yaw)*Math.cos(pitch)).mult(radius));
//    }

    @Override
    public void unload() {
        cam.unsetMain();
    }

    public void accept(float dx, float dy) {
        yaw   += dx/200f;
        pitch += dy/200f;
    }
}
