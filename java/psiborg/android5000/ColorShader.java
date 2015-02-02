package psiborg.android5000;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import psiborg.android5000.base.Shader;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.MeshData;

import android.opengl.GLES20;

public class ColorShader extends Shader {
	public static float[] lightDir   = new float[]{-1f,1f,1f};
	public static float[] lightCol   = new float[]{1f,1f,1f};
	public static float[] ambientCol = new float[]{1f,1f,1f};
	
	private int sColor;

    private static String vertex = "color_vertex";
    private static String fragment = "color_fragment";

	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private FloatBuffer colorBuffer;
	private IntBuffer orderBuffer;
	static final byte DIM 	 = 3;
	static final byte stride = DIM*4;
	private int mPositionHandle, mNormalHandle, mColorHandle;
	public ColorShader(MeshData mesh) {
		sColor = instance(IO.readFile(vertex), IO.readFile(fragment));
		
		//add coordinates to buffer
		ByteBuffer bb = ByteBuffer.allocateDirect(mesh.points.length*4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(mesh.points);
		vertexBuffer.position(0);
		
		//add normal vectors to buffer
		ByteBuffer nb = ByteBuffer.allocateDirect(mesh.normals.length*4);
		nb.order(ByteOrder.nativeOrder());
		normalBuffer = nb.asFloatBuffer();
		normalBuffer.put(mesh.normals);
		normalBuffer.position(0);
		
		//add color data to buffer
		ByteBuffer cb = ByteBuffer.allocateDirect(mesh.color.length*4);
		cb.order(ByteOrder.nativeOrder());
		colorBuffer = cb.asFloatBuffer();
		colorBuffer.put(mesh.color);
		colorBuffer.position(0);

		bb = ByteBuffer.allocateDirect(mesh.order.length*4);
		bb.order(ByteOrder.nativeOrder());
		orderBuffer = bb.asIntBuffer();
		orderBuffer.put(mesh.order);
		orderBuffer.position(0);
		
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
				mColorHandle, DIM,
				GLES20.GL_FLOAT, false,
				stride, colorBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mNormalHandle);
		GLES20.glDisableVertexAttribArray(mColorHandle);
	}
	@Override
	public void draw(float[] mvpMatrix) {
		GLES20.glUseProgram(sColor);
		
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glEnableVertexAttribArray(mNormalHandle);
		GLES20.glEnableVertexAttribArray(mColorHandle);

		//lighting
		GLES20.glUniform3fv(GLES20.glGetUniformLocation(sColor, "lightPos"), 1, lightDir, 0);
		GLES20.glUniform3fv(GLES20.glGetUniformLocation(sColor, "lightCol"), 1, lightCol, 0);
        GLES20.glUniform3fv(GLES20.glGetUniformLocation(sColor, "ambient"), 1, ambientCol, 0);
		GLES20.glUniform1f(GLES20.glGetUniformLocation(sColor, "dirInt"), 1f);
		GLES20.glUniform1f(GLES20.glGetUniformLocation(sColor, "dirRad"), 4f);
		

		//transform matrix
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(sColor, "uMVPMatrix"),
				1, false, mvpMatrix, 0);

		//draw command
		GLES20.glDrawElements(
				GLES20.GL_TRIANGLES, orderBuffer.capacity(),
				GLES20.GL_UNSIGNED_INT, orderBuffer);
		
		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mNormalHandle);
		GLES20.glDisableVertexAttribArray(mColorHandle);
	}
}
