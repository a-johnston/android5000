package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.base.Transform;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Meshes;
import psiborg.android5000.util.Quaternion;
import psiborg.android5000.util.Random;

public class OrbitCube extends GameObject {
    private Mesh mesh;
    private Quaternion orbit, rot;

    public OrbitCube() {
        mesh = Meshes.getNewMesh().buildCube().scale(Random.vector()).solidColor(Random.rgb());
        mesh.setReady();

        transform = new Transform(Random.unitPoint().mult(7 + 20 * Math.random()), Random.rotation());

        orbit = Quaternion.fromEulerAngles(Math.random()/100, Math.random()/100, Math.random()/100);
        rot   = Quaternion.fromEulerAngles(Math.random()/50, Math.random()/50, Math.random()/50);
    }

    @Override
    protected void load() {
        ColorShader.load();
        mesh.pushToGPU();
    }

    @Override
    public void step() {
        transform = new Transform(orbit.transform(transform.position), transform.rotation.mult(rot).normalize());
    }

    @Override
    protected void draw() {
        ColorShader.setMesh(mesh);
        ColorShader.setTransform(transform);
        ColorShader.draw();
    }
}
