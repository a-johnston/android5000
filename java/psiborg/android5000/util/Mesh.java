package psiborg.android5000.util;

import android.util.Log;

import java.util.ArrayList;

public class Mesh {
    public ArrayList<Vector3> points, normals;
    public ArrayList<Color> color;
    public ArrayList<Vector2> uv;
    public ArrayList<IVector3> order;
    public Mesh() {
        points  = new ArrayList<Vector3>();
        normals = new ArrayList<Vector3>();
        color   = new ArrayList<Color>();
        uv      = new ArrayList<Vector2>();
        order   = new ArrayList<IVector3>();
    }
    public void clear() {
        points.clear();
        normals.clear();
        color.clear();
        uv.clear();
        order.clear();
    }
    public float[] getPoints() {
        return Vector3.toFloatArray(points.toArray(new Vector3[0]));
    }
    public float[] getNormals() {
        return Vector3.toFloatArray(normals.toArray(new Vector3[0]));
    }
    public float[] getColors() {
        return Color.toFloatArray(color.toArray(new Color[0]));
    }
    public float[] getUV() {
        return Vector2.toFloatArray(uv.toArray(new Vector2[0]));
    }
    public int[] getOrder() {
        return IVector3.toIntArray(order.toArray(new IVector3[0]));
    }
    public short[] getShortOrder() {
        return IVector3.toShortArray(order.toArray(new IVector3[0]));
    }
    public void buildNormals() {
        normals.clear();
        for (Vector3 v : points) {
            normals.add(new Vector3());
        }
        for (IVector3 i : order) {
            Vector3 n = getNormal(points.get(i.x), points.get(i.y), points.get(i.z));
            normals.get(i.x).add(n);
            normals.get(i.y).add(n);
            normals.get(i.z).add(n);
        }
        for (Vector3 v : normals) {
            v.normalize();
        }
    }
    public void stupidColors() {
        color.clear();
        for (Vector3 v : points) {
            color.add(new Color(((float)v.x + 1f)/2f, ((float)v.y + 1f)/2f, ((float)v.z + 1f)/2f, .5f));
        }
    }
    public static Vector3[] getNormals(Vector3[] points, int[] order) {
        Vector3[] norms = new Vector3[points.length];
        for (int i=0; i<norms.length; i++) {
            norms[i] = new Vector3(0,0,0);
        }
        for (int i=0; i<order.length; i+=3) {
            Vector3 n = getNormal(points[order[i]],points[order[i+1]],points[order[i+2]]);
            norms[order[i]]   = Vector3.add(norms[order[i]],n);
            norms[order[i+1]] = Vector3.add(norms[order[i+1]],n);
            norms[order[i+2]] = Vector3.add(norms[order[i+2]],n);
        }
        for (int i=0; i<norms.length; i++) norms[i] = Vector3.normalize(norms[i]);
        return norms;
    }
    public static Vector3 getNormal(Vector3 p1, Vector3 p2, Vector3 p3) {
        return Vector3.normalize(Vector3.cross(Vector3.sub(p2, p1), Vector3.sub(p3, p1)));
    }
}
