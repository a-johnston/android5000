package psiborg.android5000.base;

import android.opengl.GLES20;

public abstract class Shader implements Drawable {
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
}
