package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.base.Transform;
import psiborg.android5000.util.Color;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Meshes;
import psiborg.android5000.util.Vector3;

public class Suzanne extends GameObject {
    private final Mesh mesh;

    public Suzanne() {
        mesh = Meshes.getMesh("suzanne.obj").doWhenLoaded(new Runnable() {
            @Override
            public void run() {
                mesh.scale(3.0).solidColor(Color.WHITE).pad();
            }
        });
    }

    @Override
    protected void load() {
        ColorShader.load();
        ColorShader.setLightPosition(new Vector3(-5, 7, -5));
        ColorShader.setLightColor(Color.WHITE);
        mesh.pushToGPU();
    }

    @Override
    protected void unload() {
        mesh.freeFromGPU();
    }

    @Override
    protected void draw() {
        ColorShader.setMesh(mesh);
        ColorShader.setTransform(Transform.ID);
        ColorShader.draw();
    }
}
