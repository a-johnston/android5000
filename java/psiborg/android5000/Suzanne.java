package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.base.Transform;
import psiborg.android5000.util.Color;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Meshes;
import psiborg.android5000.util.Quaternion;
import psiborg.android5000.util.Vector3;

public class Suzanne extends GameObject {
    private Mesh mesh;
    private Transform transform;
    public Suzanne() {
        transform = new Transform();
        transform.rotation = Quaternion.fromEulerAngles(Math.random()*2*Math.PI, Math.random()*2*Math.PI, Math.random()*2*Math.PI);
        transform.position = new Vector3(Math.random()*10-5, Math.random()*10-5, Math.random()*10-5);
        mesh = Meshes.getMesh("suzanne.obj").solidColor(Color.WHITE).pad();
    }

    @Override
    protected void load() {
        ColorShader.load();
        ColorShader.setLightPosition(new Vector3(-5, 7, -5));
        ColorShader.setLightColor(Color.WHITE);
        ColorShader.setLightColor(Color.BLACK.interpolate(Color.WHITE, .8f));
        mesh.pushToGPU();
    }

    @Override
    protected void unload() {
        mesh.freeFromGPU();
    }

    @Override
    protected void draw() {
        ColorShader.setMesh(mesh);
        ColorShader.setTransform(transform);
        ColorShader.draw();
    }
}
