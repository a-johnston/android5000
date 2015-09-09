package psiborg.android5000;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.Shader;
import psiborg.android5000.base.StaticFiles;
import psiborg.android5000.util.Mesh;

import android.opengl.GLES20;

public class ColorShader extends Shader {
	public static float[] lightDir   = new float[]{-1f,1f,1f};
	public static float[] lightCol   = new float[]{1f,1f,1f};
	public static float[] ambientCol = new float[]{.5f,.5f,.5f};
	
	private int sColor;

    private static String vertex   = StaticFiles.load("color_vertex");
    private static String fragment = StaticFiles.load("color_fragment");

	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private FloatBuffer colorBuffer;
	private IntBuffer orderBuffer;

	static final byte DIM 	 = 3;
	static final byte stride = DIM*4;
	private int mPositionHandle, mNormalHandle, mColorHandle;
	public ColorShader(Mesh mesh) {
        vertexBuffer = Shader.bufferFloatArray(mesh.getPoints());
        normalBuffer = Shader.bufferFloatArray(mesh.getNormals());
        colorBuffer  = Shader.bufferFloatArray(mesh.getColors());
		orderBuffer  = Shader.bufferIntArray(mesh.getOrder());
	}
    @Override
    protected void loadAsset() {
		sColor = instance(vertex, fragment);

		mPositionHandle = GLES20.glGetAttribLocation(sColor, "v_Position");
		mNormalHandle   = GLES20.glGetAttribLocation(sColor, "v_Normal");
		mColorHandle    = GLES20.glGetAttribLocation(sColor, "v_Color");

		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glEnableVertexAttribArray(mNormalHandle);
		GLES20.glEnableVertexAttribArray(mColorHandle);

		//coordinate data
		GLES20.glVertexAttribPointer(
				mPositionHandle, DIM,
				GLES20.GL_FLOAT, false,
				stride, vertexBuffer);

		//normal data
		GLES20.glVertexAttribPointer(
				mNormalHandle, DIM,
				GLES20.GL_FLOAT, false,
				stride, normalBuffer);

		//color data
		GLES20.glVertexAttribPointer(
				mColorHandle, DIM+1,
				GLES20.GL_FLOAT, false,
				stride+4, colorBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mNormalHandle);
		GLES20.glDisableVertexAttribArray(mColorHandle);
    }

    @Override
    protected void unloadAsset() {

    }
	@Override
	public void draw() {
		GLES20.glUseProgram(sColor);
		
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glEnableVertexAttribArray(mNormalHandle);
		GLES20.glEnableVertexAttribArray(mColorHandle);

		//lighting
		GLES20.glUniform3fv(GLES20.glGetUniformLocation(sColor, "lightPos"), 1, lightDir, 0);
		GLES20.glUniform3fv(GLES20.glGetUniformLocation(sColor, "lightCol"), 1, lightCol, 0);
        GLES20.glUniform3fv(GLES20.glGetUniformLocation(sColor, "ambient"), 1, ambientCol, 0);
		GLES20.glUniform1f(GLES20.glGetUniformLocation(sColor, "dirInt"), 1f);
		GLES20.glUniform1f(GLES20.glGetUniformLocation(sColor, "dirRad"), 10f);

		//transform matrix
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(sColor, "uMVPMatrix"),
				1, false, Camera.getActiveMVP(), 0);

		//draw command
		GLES20.glDrawElements(
				GLES20.GL_TRIANGLES, orderBuffer.capacity(),
				GLES20.GL_UNSIGNED_INT, orderBuffer);
		
		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mNormalHandle);
		GLES20.glDisableVertexAttribArray(mColorHandle);
	}
}
