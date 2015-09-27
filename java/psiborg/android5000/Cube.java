package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.base.Transform;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Quaternion;
import psiborg.android5000.util.Random;

public class Cube extends GameObject {
    private Mesh mesh;
    private Transform transform;
    private Quaternion rot;

    public Cube() {
        mesh = new Mesh().buildCube();
        mesh.setReady();
        mesh.pushToGPU();

        transform = new Transform();
        transform.position = Random.unitPoint().mult(10 * Math.random());
        transform.rotation = Random.rotation();

        rot = Quaternion.fromEulerAngles(Math.random()/100, Math.random()/100, Math.random()/100);
    }

    @Override
    protected void load() {
        ColorShader.load();
    }

    @Override
    public void step() {
        transform.rotation = transform.rotation.mult(rot);
    }

    @Override
    protected void draw() {
        ColorShader.setMesh(mesh);
        ColorShader.setTransform(transform);
        ColorShader.draw();
    }
}
