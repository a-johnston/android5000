package psiborg.android5000;

import android.util.Log;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.base.Transform;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Meshes;
import psiborg.android5000.util.Quaternion;

public class Cube extends GameObject {
    private Mesh mesh;

    private Quaternion rot;

    public Cube() {
        mesh = Meshes.getNewMesh().buildCube().stupidColors();
        mesh.setReady();

        rot = Quaternion.fromEulerAngles(0, 0, .02f);
        Log.i("R", rot.x + " " + rot.y + " " + rot.z + " " + rot.w);
    }

    @Override
    protected void load() {
        SimpleShader.load();
        mesh.pushToGPU();
    }

    @Override
    public void step() {
        transform = new Transform(transform.position, transform.rotation.mult(rot).normalize());
    }

    @Override
    protected void draw() {
        SimpleShader.setMesh(mesh);
        SimpleShader.setTransform(transform);
        SimpleShader.draw();
    }
}
