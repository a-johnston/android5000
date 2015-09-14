package psiborg.android5000.util;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Mesh {
    public static final int BYTES_PER_FLOAT  =   Float.SIZE / Byte.SIZE;
    public static final int BYTES_PER_INT    = Integer.SIZE / Byte.SIZE;

    public static final int BYTES_PER_POINT  = 3 * BYTES_PER_FLOAT;
    public static final int BYTES_PER_NORMAL = 3 * BYTES_PER_FLOAT;
    public static final int BYTES_PER_COLOR  = 4 * BYTES_PER_FLOAT;
    public static final int BYTES_PER_UV     = 2 * BYTES_PER_FLOAT;
    public static final int BYTES_PER_TRI    = 3 * BYTES_PER_INT;

    private List<Vector3> points, normals;
    private List<Color> color;
    private List<Vector2> uv;
    private List<IVector3> order;

    private int[] vbos;

    private boolean hasVBOs;

    public Mesh() {
        points  = new ArrayList<>();
        normals = new ArrayList<>();
        color   = new ArrayList<>();
        uv      = new ArrayList<>();
        order   = new ArrayList<>();
        vbos    = new int[5];
    }

    public synchronized void pushToGPU() {
        if (hasVBOs()) {
            return;
        }

        GLES20.glGenBuffers(vbos.length, vbos, 0);

        FloatBuffer floatBuffer;
        IntBuffer intBuffer;

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbos[0]);
        floatBuffer = getPointBuffer();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, floatBuffer.capacity() * BYTES_PER_FLOAT, floatBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbos[1]);
        floatBuffer = getNormalBuffer();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, floatBuffer.capacity() * BYTES_PER_FLOAT, floatBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbos[2]);
        floatBuffer = getColorBuffer();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, floatBuffer.capacity() * BYTES_PER_FLOAT, floatBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbos[3]);
        floatBuffer = getUvBuffer();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, floatBuffer.capacity() * BYTES_PER_FLOAT, floatBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbos[4]);
        intBuffer = getOrderBuffer();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, intBuffer.capacity() * BYTES_PER_INT, intBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        hasVBOs = true;
    }

    public synchronized void freeFromGPU() {
        if (!hasVBOs()) {
            return;
        }
        GLES20.glDeleteBuffers(vbos.length, vbos, 0);
        hasVBOs = false;
    }

    public synchronized void updateVBOs() {
        freeFromGPU(); //probably safe?
        pushToGPU();
    }

    public synchronized boolean hasVBOs() {
        return hasVBOs;
    }

    public synchronized int getPointVBO() {
        return vbos[0];
    }

    public synchronized int getNormalVBO() {
        return vbos[1];
    }

    public synchronized int getColorVBO() {
        return vbos[2];
    }

    public synchronized int getUvVBO() {
        return vbos[3];
    }

    public synchronized int getOrderVBO() {
        return vbos[4];
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

    public Mesh pad() {
        int size = MathUtil.max(points.size(), normals.size(), color.size(), uv.size());

        ListUtil.pad(points, size, Vector3.ZERO);
        ListUtil.pad(normals, size, Vector3.ZERO);
        ListUtil.pad(color, size, Color.BLANK);
        ListUtil.pad(uv, size, Vector2.ZERO);

        return this;
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

    public FloatBuffer getPointBuffer() {
        return buildFloatBuffer(getPoints());
    }

    public FloatBuffer getNormalBuffer() {
        return buildFloatBuffer(getNormals());
    }

    public FloatBuffer getColorBuffer() {
        return buildFloatBuffer(getColors());
    }

    public FloatBuffer getUvBuffer() {
        return buildFloatBuffer(getUV());
    }

    public IntBuffer getOrderBuffer() {
        return buildIntBuffer(getOrder());
    }

    private FloatBuffer buildFloatBuffer(float[] f) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(f.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(f);
        buffer.position(0);
        return buffer;
    }

    private IntBuffer buildIntBuffer(int[] i) {
        IntBuffer buffer = ByteBuffer.allocateDirect(i.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(i);
        buffer.position(0);
        return buffer;
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
                points.add(points.get(i.x));
                color.add(color.get(i.x));
                normals.add(n);
                i.x = points.size() - 1;
            }
            if (n.dot(normals.get(i.y)) < factor) {
                points.add(points.get(i.y));
                color.add(color.get(i.y));
                normals.add(n);
                i.y = points.size() - 1;
            }
            if (n.dot(normals.get(i.z)) < factor) {
                points.add(points.get(i.z));
                color.add(color.get(i.z));
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
        for (IVector3 v : order) {
            used[v.x] = true;
            used[v.y] = true;
            used[v.z] = true;
        }
        int n = 0;
        int[] map = new int[points.size()];
        Mesh mesh = new Mesh();
        for (int i = 0; i < points.size(); i++) {
            if (used[i]) {
                mesh.addPoint(points.get(i));
                mesh.addNormal(normals.get(i));
                mesh.addColor(color.get(i));
                mesh.addUV(uv.get(i));
                map[i] = n;
            }
        }
        for (IVector3 v : order) {
            mesh.addTriangle(new IVector3(map[v.x], map[v.y], map[v.z]));
        }
        clear();
        return mesh;
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
