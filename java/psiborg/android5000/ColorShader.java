package psiborg.android5000;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.Shader;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.Mesh;

import android.opengl.GLES20;

public class ColorShader extends Shader {
	public static float[] lightDir   = new float[]{-5f,7f,-5f};
	public static float[] lightCol   = new float[]{1f,1f,1f};
	public static float[] ambientCol = new float[]{.8f,.8f,.8f};
	
	private static int sColor = -1;

	private static final byte DIM 	 = 3;
	private static final byte stride = DIM*4;
	private static int mPositionHandle, mNormalHandle, mColorHandle;
    private static Mesh mesh;

    public static void load() {
        if (sColor != -1) {
            return;
        }

		sColor = instance(IO.readFile("color_vertex"), IO.readFile("color_fragment"));

		mPositionHandle = GLES20.glGetAttribLocation(sColor, "v_Position");
		mNormalHandle   = GLES20.glGetAttribLocation(sColor, "v_Normal");
		mColorHandle    = GLES20.glGetAttribLocation(sColor, "v_Color");
    }

    public static void setMesh(Mesh mesh) {
        ColorShader.mesh = mesh;
    }

	public static void draw() {
		GLES20.glUseProgram(sColor);

		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glEnableVertexAttribArray(mNormalHandle);
		GLES20.glEnableVertexAttribArray(mColorHandle);

		//lighting
		GLES20.glUniform3fv(GLES20.glGetUniformLocation(sColor, "lightPos"), 1, lightDir, 0);
		GLES20.glUniform3fv(GLES20.glGetUniformLocation(sColor, "lightCol"), 1, lightCol, 0);
        GLES20.glUniform3fv(GLES20.glGetUniformLocation(sColor, "ambient"), 1, ambientCol, 0);
		GLES20.glUniform1f(GLES20.glGetUniformLocation(sColor, "dirInt"), 1f);
		GLES20.glUniform1f(GLES20.glGetUniformLocation(sColor, "dirRad"), 15f);

		//transform matrix
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(sColor, "uMVPMatrix"),
				1, false, Camera.getActiveMVP(), 0);

//		//draw command
//		GLES20.glDrawElements(
//				GLES20.GL_TRIANGLES, orderBuffer.capacity(),
//				GLES20.GL_UNSIGNED_INT, orderBuffer);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getPointVBO());
        GLES20.glVertexAttribPointer(mPositionHandle, Mesh.BYTES_PER_POINT, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getNormalVBO());
        GLES20.glVertexAttribPointer(mNormalHandle, Mesh.BYTES_PER_NORMAL, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getColorVBO());
        GLES20.glVertexAttribPointer(mColorHandle, Mesh.BYTES_PER_NORMAL, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getOrderVBO());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                mesh.getOrderBuffer().capacity(),
                GLES20.GL_UNSIGNED_INT,
                mesh.getOrderVBO());

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mNormalHandle);
		GLES20.glDisableVertexAttribArray(mColorHandle);
	}
}
