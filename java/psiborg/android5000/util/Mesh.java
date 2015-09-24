package psiborg.android5000.util;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import psiborg.android5000.GameEngine;
import psiborg.android5000.base.Shader;

public class Mesh {
    public static final int BYTES_PER_FLOAT  = Float.SIZE / Byte.SIZE;
    public static final int BYTES_PER_SHORT  = Short.SIZE / Byte.SIZE;

    public static final int VALUES_PER_POINT = 3;
    public static final int VALUES_PER_COLOR = 4;
    public static final int VALUES_PER_UV    = 2;

    public static final int BYTES_PER_POINT  = VALUES_PER_POINT * BYTES_PER_FLOAT;
    public static final int BYTES_PER_NORMAL = VALUES_PER_POINT * BYTES_PER_FLOAT;
    public static final int BYTES_PER_COLOR  = VALUES_PER_COLOR * BYTES_PER_FLOAT;
    public static final int BYTES_PER_UV     = VALUES_PER_UV * BYTES_PER_FLOAT;

    private static final int POINT_BUFFER   = 0;
    private static final int NORMAL_BUFFER  = 1;
    private static final int COLOR_BUFFER   = 2;
    private static final int UV_BUFFER      = 3;
    private static final int ORDER_BUFFER   = 4;

    private List<Vector3> points, normals;
    private List<Color> colors;
    private List<Vector2> uv;
    private List<IVector3> order;

    private int[] buffers;

    private boolean hasVBOs, VBOready;
    protected boolean meshReady;
    private WaitOnReadyQueue doWhenReadyQ;

    public Mesh() {
        points       = new ArrayList<>();
        normals      = new ArrayList<>();
        colors       = new ArrayList<>();
        uv           = new ArrayList<>();
        order        = new ArrayList<>();
        buffers      = new int[5];
        meshReady    = false;
        doWhenReadyQ = new WaitOnReadyQueue();
    }

    public synchronized void doWhenReady(Runnable r) {
        doWhenReadyQ.doWhenReady(r);
    }

    public synchronized void setReady() {
        doWhenReadyQ.setReady(true);
        meshReady = true;
    }

    public synchronized void pushToGPU() {
        final GameEngine engine = GameEngine.getCurrentEngine();
        doWhenReadyQ.doWhenReady(new Runnable() {
            @Override
            public void run() {
                engine.doWhenReady(new Runnable() {
                    @Override
                    public void run() {
                        if (hasVBOs()) {
                            return;
                        }
                        GLES20.glGenBuffers(buffers.length, buffers, 0);
                        for (int i : buffers) {
                            if (i == 0) {
                                Log.w("Mesh", "Failed to generate buffer index! Make sure valid OpenGL context exists");
                                return;
                            }
                        }
                        hasVBOs = true;
                        updateGPU();
                    }
                });
            }
        });
    }

    public synchronized void freeFromGPU() {
        if (!hasVBOs()) {
            return;
        }
        GLES20.glDeleteBuffers(buffers.length, buffers, 0);
        hasVBOs = false;
        VBOready = false;
    }

    public synchronized void updateGPU() {
        if (!hasVBOs() || VBOready() || !meshReady()) {
            return;
        }

        FloatBuffer floatBuffer;
        ShortBuffer shortBuffer;

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[POINT_BUFFER]);
        floatBuffer = getPointBuffer();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, floatBuffer.capacity() * BYTES_PER_FLOAT, floatBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[NORMAL_BUFFER]);
        floatBuffer = getNormalBuffer();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, floatBuffer.capacity() * BYTES_PER_FLOAT, floatBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[COLOR_BUFFER]);
        floatBuffer = getColorBuffer();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, floatBuffer.capacity() * BYTES_PER_FLOAT, floatBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[UV_BUFFER]);
        floatBuffer = getUvBuffer();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, floatBuffer.capacity() * BYTES_PER_FLOAT, floatBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, buffers[ORDER_BUFFER]);
        shortBuffer = getOrderBuffer();
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, shortBuffer.capacity() * BYTES_PER_SHORT, shortBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

        VBOready = true;
    }

    public synchronized boolean meshReady() {
        return meshReady;
    }

    public synchronized boolean VBOready() {
        return VBOready;
    }

    public synchronized boolean hasVBOs() {
        return hasVBOs;
    }

    public synchronized int getPointVBO() {
        return buffers[POINT_BUFFER];
    }

    public synchronized int getNormalVBO() {
        return buffers[NORMAL_BUFFER];
    }

    public synchronized int getColorVBO() {
        return buffers[COLOR_BUFFER];
    }

    public synchronized int getUvVBO() {
        return buffers[UV_BUFFER];
    }

    public synchronized int getOrderVBO() {
        return buffers[ORDER_BUFFER];
    }

    public synchronized void addPoint(Vector3 v) {
        mutateMesh(points, v);
    }

    public synchronized void addNormal(Vector3 v) {
        mutateMesh(normals, v);
    }

    public synchronized void addColor(Color c) {
        mutateMesh(colors, c);
    }

    public synchronized void addUV(Vector2 v) {
        mutateMesh(uv, v);
    }

    public synchronized void addTriangle(IVector3 v) {
        mutateMesh(order, v);
    }

    private <E> void mutateMesh(List<E> list, E element) {
        list.add(element);
        VBOready = false;
    }

    public synchronized Mesh pad() {
        doWhenReadyQ.doWhenReady(new Runnable() {
            @Override
            public void run() {
                int size = MathUtil.max(points.size(), normals.size(), colors.size(), uv.size());

                ListUtil.pad(points, size, Vector3.ZERO);
                ListUtil.pad(normals, size, Vector3.ZERO);
                ListUtil.pad(colors, size, Color.BLANK);
                ListUtil.pad(uv, size, Vector2.ZERO);
                VBOready = false;
            }
        });
        return this;
    }

    public synchronized void clear() {
        points.clear();
        normals.clear();
        colors.clear();
        uv.clear();
        order.clear();
        freeFromGPU();
        doWhenReadyQ.clear();
    }

    private float[] getPoints() {
        return Vector3.toFloatArray(points.toArray(new Vector3[points.size()]));
    }

    private float[] getNormals() {
        return Vector3.toFloatArray(normals.toArray(new Vector3[normals.size()]));
    }

    private float[] getColors() {
        return Color.toFloatArray(colors.toArray(new Color[colors.size()]));
    }

    private float[] getUV() {
        return Vector2.toFloatArray(uv.toArray(new Vector2[uv.size()]));
    }

    private int[]   getOrder() {
        return IVector3.toIntArray(order.toArray(new IVector3[order.size()]));
    }

    private short[] getShortOrder() {
        return IVector3.toShortArray(order.toArray(new IVector3[order.size()]));
    }

    public synchronized FloatBuffer getPointBuffer() {
        return Shader.bufferFloatArray(getPoints());
    }

    public synchronized FloatBuffer getNormalBuffer() {
        return Shader.bufferFloatArray(getNormals());
    }

    public synchronized FloatBuffer getColorBuffer() {
        return Shader.bufferFloatArray(getColors());
    }

    public synchronized FloatBuffer getUvBuffer() {
        return Shader.bufferFloatArray(getUV());
    }

    public synchronized ShortBuffer getOrderBuffer() {
        return Shader.bufferShortArray(getShortOrder());
    }

    public synchronized Mesh buildNormals() {
        doWhenReadyQ.doWhenReady(new Runnable() {
            @Override
            public void run() {
                normals.clear();
                List<Vector3> newNorms = new ArrayList<>(normals.size());
                for (Vector3 a : points) {
                    newNorms.add(new Vector3());
                }
                for (IVector3 i : order) {
                    Vector3 n = Vector3.getNormalVector(points.get(i.getX()), points.get(i.getY()), points.get(i.getZ()));
                    newNorms.set(i.x, newNorms.get(i.x).plus(n));
                    newNorms.set(i.y, newNorms.get(i.y).plus(n));
                    newNorms.set(i.z, newNorms.get(i.z).plus(n));
                }
                for (Vector3 v : newNorms) {
                    normals.add(v.normalize());
                }
                VBOready = false;
            }
        });
        return this;
    }

    public synchronized Mesh sharpenEdges(final double factor) {
        doWhenReadyQ.doWhenReady(new Runnable() {
            @Override
            public void run() {
                for (IVector3 i : order) {
                    Vector3 n = Vector3.getNormalVector(points.get(i.x), points.get(i.y), points.get(i.z));
                    if (n.dot(normals.get(i.x)) < factor) {
                        points.add(points.get(i.x));
                        colors.add(colors.get(i.x));
                        normals.add(n);
                        i.x = points.size() - 1;
                    }
                    if (n.dot(normals.get(i.y)) < factor) {
                        points.add(points.get(i.y));
                        colors.add(colors.get(i.y));
                        normals.add(n);
                        i.y = points.size() - 1;
                    }
                    if (n.dot(normals.get(i.z)) < factor) {
                        points.add(points.get(i.z));
                        colors.add(colors.get(i.z));
                        normals.add(n);
                        i.z = points.size() - 1;
                    }
                }
                VBOready = false;
            }
        });
        return this;
    }

    public synchronized Mesh stupidColors() {
        doWhenReadyQ.doWhenReady(new Runnable() {
            @Override
            public void run() {
                colors.clear();
                for (Vector3 v : points) {
                    colors.add(new Color(((float) v.getX() + 1f) / 2f, ((float) v.getY() + 1f) / 2f, ((float) v.getZ() + 1f) / 2f, .5f));
                }
                VBOready = false;
            }
        });
        return this;
    }

    public synchronized Mesh solidColor(final Color color) {
        doWhenReadyQ.doWhenReady(new Runnable() {
            @Override
            public void run() {
                colors.clear();
                for (Vector3 v : points) {
                    colors.add(color);
                }
                VBOready = false;
            }
        });
        return this;
    }

    public synchronized Mesh colorSides(final double factor) {
        doWhenReadyQ.doWhenReady(new Runnable() {
            @Override
            public void run() {
                colors.clear();
                for (Vector3 p : points) {
                    colors.add(new Color(.5f, .8f, .5f));
                }
                for (IVector3 i : order) {
                    Vector3 n = Vector3.getNormalVector(points.get(i.x), points.get(i.y), points.get(i.z));
                    if (n.getY() < factor) {
                        points.add(new Vector3(points.get(i.x)));
                        normals.add(n);
                        i.x = points.size() - 1;
                        points.add(new Vector3(points.get(i.y)));
                        normals.add(n);
                        i.y = points.size() - 1;
                        points.add(new Vector3(points.get(i.z)));
                        normals.add(n);
                        i.z = points.size() - 1;

                        colors.add(new Color(.5f, .5f, .5f));
                        colors.add(new Color(.5f, .5f, .5f));
                        colors.add(new Color(.5f, .5f, .5f));

                    }
                }
            }
        });
        VBOready = false;
        return this;
    }

    public synchronized Mesh clean() {
        doWhenReadyQ.doWhenReady(new Runnable() {
            @Override
            public void run() {
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
                        mesh.addColor(colors.get(i));
                        mesh.addUV(uv.get(i));
                        map[i] = n;
                    }
                }
                for (IVector3 v : order) {
                    mesh.addTriangle(new IVector3(map[v.x], map[v.y], map[v.z]));
                }

                Mesh.this.order = mesh.order;
                Mesh.this.points = mesh.points;
                Mesh.this.normals = mesh.normals;
                Mesh.this.colors = mesh.colors;
                Mesh.this.uv = mesh.uv;

                VBOready = false;
            }
        });
        return this;
    }
}
