package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.base.Transform;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Meshes;
import psiborg.android5000.util.Path;

public class WorldPath extends GameObject {
    private Path path;
    private Mesh mesh;

    public WorldPath(Path path) {
        this.path = path;
        this.mesh = Meshes.getNewMesh();
    }

    @Override
    protected void load() {
        ColorShader.load();
    }

    @Override
    protected void draw() {
        ColorShader.setMesh(mesh);
        ColorShader.setTransform(Transform.ID);
        ColorShader.draw();
    }
}
