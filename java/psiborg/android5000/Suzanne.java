package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.Mesh;

public class Suzanne extends GameObject {
    public static float yaw, pitch, radius = 2.5f;
    private ColorShader shader;
    public Suzanne() {
        Mesh obj = IO.loadObj("terrain.obj");
        shader = new ColorShader(obj.colorSides(.7f));
    }
    @Override
    protected void loadAsset() {
        shader.load();
    }
    @Override
    protected void unloadAsset() {
        shader.unload();
    }
    @Override
    public void step() {
        ColorShader.lightDir = new float[]{ (float)(Math.cos(GameEngine.time)*10), 0, (float)(Math.sin(GameEngine.time)*10) };
    }
    @Override
    protected void drawAsset() {
        shader.draw();
    }
}
