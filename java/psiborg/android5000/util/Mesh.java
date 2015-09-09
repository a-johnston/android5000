package psiborg.android5000.util;

import java.util.ArrayList;

public class Mesh {
    public class Triangle {
        public Vector3 p1, p2, p3;

        public Triangle(Vector3 p1, Vector3 p2, Vector3 p3) {
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
        }

        public Vector3 normal() {
            return getNormal(this.p1, this.p2, this.p3);
        }
    }

    private ArrayList<Vector3> points, normals;
    private ArrayList<Color> color;
    private ArrayList<Vector2> uv;
    private ArrayList<IVector3> order;

    public Mesh() {
        points  = new ArrayList<>();
        normals = new ArrayList<>();
        color   = new ArrayList<>();
        uv      = new ArrayList<>();
        order   = new ArrayList<>();
    }

    public void addPoint(Vector3 v) {
        points.add(v);
    }

    public void addNormal(Vector3 v) {
        normals.add(v);
    }

    public void addColor(Color c) {
        color.add(c);
    }

    public void addUV(Vector2 v) {
        uv.add(v);
    }

    public void addTriangle(IVector3 v) {
        order.add(v);
    }

    public void clear() {
        points.clear();
        normals.clear();
        color.clear();
        uv.clear();
        order.clear();
    }

    public float[] getPoints() {
        return Vector3.toFloatArray(points.toArray(new Vector3[points.size()]));
    }

    public float[] getNormals() {
        return Vector3.toFloatArray(normals.toArray(new Vector3[normals.size()]));
    }

    public float[] getColors() {
        return Color.toFloatArray(color.toArray(new Color[color.size()]));
    }

    public float[] getUV() {
        return Vector2.toFloatArray(uv.toArray(new Vector2[uv.size()]));
    }

    public int[]   getOrder() {
        return IVector3.toIntArray(order.toArray(new IVector3[order.size()]));
    }

    public short[] getShortOrder() {
        return IVector3.toShortArray(order.toArray(new IVector3[order.size()]));
    }

    public Mesh buildNormals() {
        normals.clear();
        for (Vector3 a: points) {
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
        return this;
    }

    public Mesh sharpenEdges(double factor) {
        for (IVector3 i : order) {
            Vector3 n = getNormal(points.get(i.x), points.get(i.y), points.get(i.z));
            if (n.dot(normals.get(i.x)) < factor) {
                points.add(new Vector3(points.get(i.x)));
                normals.add(n);
                i.x = points.size() - 1;
            }
            if (n.dot(normals.get(i.y)) < factor) {
                points.add(new Vector3(points.get(i.y)));
                normals.add(n);
                i.y = points.size() - 1;
            }
            if (n.dot(normals.get(i.z)) < factor) {
                points.add(new Vector3(points.get(i.z)));
                normals.add(n);
                i.z = points.size() - 1;
            }
        }
        return this;
    }

    public Mesh stupidColors() {
        color.clear();
        for (Vector3 v : points) {
            color.add(new Color(((float)v.x + 1f)/2f, ((float)v.y + 1f)/2f, ((float)v.z + 1f)/2f, .5f));
        }
        return this;
    }

    public Mesh colorSides(double factor) {
        color.clear();
        for (Vector3 p : points) {
            color.add(new Color(.5f, .8f, .5f));
        }
        for (IVector3 i : order) {
            Vector3 n = getNormal(points.get(i.x), points.get(i.y), points.get(i.z));
            if (n.y < factor) {
                points.add(new Vector3(points.get(i.x)));
                normals.add(n);
                i.x = points.size()-1;
                points.add(new Vector3(points.get(i.y)));
                normals.add(n);
                i.y = points.size()-1;
                points.add(new Vector3(points.get(i.z)));
                normals.add(n);
                i.z = points.size()-1;

                color.add(new Color(.5f, .5f, .5f));
                color.add(new Color(.5f, .5f, .5f));
                color.add(new Color(.5f, .5f, .5f));

            }
        }
        return this;
    }

    public Mesh clean() {
        boolean[] used = new boolean[points.size()];
        //determine which points are actually referenced
        for (IVector3 v : order) {
            used[v.x] = true;
            used[v.y] = true;
            used[v.z] = true;
        }
        //remove
        return this;
    }

    public static Vector3[] getNormals(Vector3[] points, int[] order) {
        Vector3[] norms = new Vector3[points.length];
        for (int i=0; i<norms.length; i++) {
            norms[i] = new Vector3(0,0,0);
        }
        for (int i=0; i<order.length; i+=3) {
            Vector3 n = getNormal(points[order[i]],points[order[i+1]],points[order[i+2]]);
            norms[order[i]].add(n);
            norms[order[i+1]].add(n);
            norms[order[i+2]].add(n);
        }
        for (int i=0; i<norms.length; i++) norms[i] = Vector3.normalize(norms[i]);
        return norms;
    }

    public static Vector3 getNormal(Vector3 p1, Vector3 p2, Vector3 p3) {
        return Vector3.normalize(Vector3.cross(Vector3.sub(p2, p1), Vector3.sub(p3, p1)));
    }
}
