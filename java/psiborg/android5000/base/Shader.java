package psiborg.android5000.base;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class Shader extends Loadable {
    public abstract void draw();
	public static int instance(String vertex, String fragment) {
		int v = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		int f = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		GLES20.glShaderSource(v, vertex);
		GLES20.glCompileShader(v);
		GLES20.glShaderSource(f, fragment);
		GLES20.glCompileShader(f);
		int p = GLES20.glCreateProgram();
		GLES20.glAttachShader(p, v);
		GLES20.glAttachShader(p, f);
		GLES20.glLinkProgram(p);
		return p;
	}
    public static FloatBuffer bufferFloatArray(float[] floats) {
        ByteBuffer bb = ByteBuffer.allocateDirect(floats.length*4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(floats);
        fb.position(0);
        return fb;
    }
    public static IntBuffer bufferIntArray(int[] ints) {
        ByteBuffer bb = ByteBuffer.allocateDirect(ints.length*4);
        bb.order(ByteOrder.nativeOrder());
        IntBuffer ib = bb.asIntBuffer();
        ib.put(ints);
        ib.position(0);
        return ib;
    }
}
