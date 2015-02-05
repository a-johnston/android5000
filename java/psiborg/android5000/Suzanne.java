package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.MeshData;

public class Suzanne extends GameObject {
    public static float yaw, pitch, radius = 2.5f;
    @Override
    public void load() {
        MeshData obj = IO.loadObj("suzanne.obj");
        shader = new ColorShader(obj);
    }
    @Override
    public void step() {
        ColorShader.lightDir = new float[]{ (float)(Math.cos(GameEngine.time)*3), 0, (float)(Math.sin(GameEngine.time)*3) };
    }
}
