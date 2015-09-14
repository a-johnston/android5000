package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Meshes;

public class Terrain extends GameObject {
    private Mesh mesh;
    public Terrain() {
        mesh = Meshes.getMesh("terrain.obj").colorSides(.5f);
    }

    @Override
    protected void drawAsset() {

    }
}
