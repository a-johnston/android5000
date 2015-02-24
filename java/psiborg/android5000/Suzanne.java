package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.Mesh;

public class Suzanne extends GameObject {
    public static float yaw, pitch, radius = 2.5f;
    private ColorShader shader;
    @Override
    public void load() {
        Mesh obj = IO.loadObj("suzanne.obj");
        shader = new ColorShader(obj);
    }
    @Override
    public void step() {
        ColorShader.lightDir = new float[]{ (float)(Math.cos(GameEngine.time)*3), 0, (float)(Math.sin(GameEngine.time)*3) };
    }
    @Override
    public void draw() {
        shader.draw();
    }
}
