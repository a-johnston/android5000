package psiborg.android5000.base;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Shader {
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
        FloatBuffer fb = ByteBuffer.allocateDirect(floats.length * Float.SIZE/Byte.SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
        fb.put(floats);
        fb.position(0);
        return fb;
    }
    public static IntBuffer bufferIntArray(int[] ints) {
        IntBuffer ib = ByteBuffer.allocateDirect(ints.length * Integer.SIZE/Byte.SIZE).order(ByteOrder.nativeOrder()).asIntBuffer();
        ib.put(ints);
        ib.position(0);
        return ib;
    }

    public static ShortBuffer bufferShortArray(short[] shorts) {
        ShortBuffer buffer = ByteBuffer.allocateDirect(shorts.length * Short.SIZE/Byte.SIZE).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffer.put(shorts);
        buffer.position(0);
        return buffer;
    }
}
