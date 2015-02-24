package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Vector3;

public class Terrain extends GameObject {
    private double[][] z;
    private int size;
    private ColorShader shader;
    private Mesh mesh;
    public Terrain(int size) {
        z = new double[size][size];
        mesh = new Mesh();
        populateRandomly(5);
        repeatSmooth(5);
        render(10,10,3);
    }
    public void load() {
        shader = new ColorShader(mesh);
    }
    public void draw() {
        shader.draw();
    }
    private void render(double w, double l, double h) {
        mesh.clear();
        //build vertexes
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                mesh.points.add(new Vector3((w*i)/size, (l*j)/size, getZ(i,j)*h));
            }
        }
        //build triangles
        for (int i=0; i<size*size-size; i++) {

        }
        mesh.buildNormals();
        mesh.stupidColors();
    }
    private void populateRandomly(double n) {
        for (double[] row : z) {
            for (int i=0; i<row.length; i++) {
                row[i] = Math.random()*n;
            }
        }
    }
    private void repeatSmooth(int times) {
        while (times-->0) {
            smooth();
        }
    }
    private void smooth() {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                z[i][j] = mean(getZ(i,j), getZ(i-1,j), getZ(i+1,j), getZ(i,j-1), getZ(i,j+1));
            }
        }
    }
    private double getZ(int x, int y) {
        return z[intClamp(x,0,size-1)][intClamp(y,0,size-1)];
    }
    private int intClamp(int n, int min, int max) {
        if (n > max) return max;
        if (n < min) return min;
        return n;
    }
    private double mean(double... d) {
        double r = 0;
        for (double t : d) {
            r += t;
        }
        return r/d.length;
    }
}
