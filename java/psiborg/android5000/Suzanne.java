package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Color;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Meshes;
import psiborg.android5000.util.Vector3;

public class Suzanne extends GameObject {
    private Mesh mesh;
    public Suzanne() {
        mesh = Meshes.getMesh("suzanne.obj").stupidColors().pad();
    }

    @Override
    protected void load() {
        mesh.pushToGPU();

        ColorShader.setLightPosition(new Vector3(-5, 7, -5));
        ColorShader.setLightColor(Color.WHITE);
        ColorShader.setLightColor(Color.lerp(Color.BLACK, Color.WHITE, .8f));
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
