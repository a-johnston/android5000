package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Meshes;

public class Suzanne extends GameObject {
    private Mesh mesh;
    public Suzanne() {
        mesh = Meshes.getMesh("suzanne.obj").stupidColors().pad();
    }

    @Override
    protected void load() {
        mesh.pushToGPU();
    }

    @Override
    protected void unload() {
        mesh.freeFromGPU();
    }

    @Override
    protected void drawAsset() {
        ColorShader.setMesh(mesh);
        ColorShader.draw();
    }
}
