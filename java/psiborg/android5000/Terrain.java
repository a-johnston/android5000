package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Color;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Meshes;
import psiborg.android5000.util.Vector3;

public class Terrain extends GameObject {
    private Mesh mesh;
    public Terrain() {
        mesh = Meshes.getMesh("terrain.obj").colorSides(.5f);
    }

    @Override
    protected void load() {
        ColorShader.load();
        mesh.pushToGPU();

        ColorShader.setLightPosition(new Vector3(-5, 7, -5));
        ColorShader.setLightColor(Color.WHITE);
        ColorShader.setLightColor(Color.BLACK.interpolate(Color.WHITE, .8f));
    }

    @Override
    protected void draw() {
        ColorShader.setMesh(mesh);
        ColorShader.draw();
    }
}
