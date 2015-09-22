package psiborg.android5000;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.Shader;
import psiborg.android5000.util.Color;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.Mesh;
import psiborg.android5000.util.Vector3;

import android.opengl.GLES20;

public class ColorShader extends Shader {
	private static int sColor = -1;

    private static Mesh mesh;
    private static Vector3 lightPosition = new Vector3(5,5,0);
    private static Color lightColor = Color.WHITE;
    private static Color ambientColor = new Color(.2f, .2f, .2f);
    private static float lightRadius = 10;


    private static int
            mPositionHandle, mNormalHandle, mColorHandle,
            mLightPosHandle, mLightRadHandle, mLightColHandle, mAmbientHandle,
            mMVPHandle;

    public synchronized static void load() {
        if (sColor != -1) {
            return;
        }

		sColor = instance(IO.readFile("color_vertex"), IO.readFile("color_fragment"));

        mPositionHandle = GLES20.glGetAttribLocation(sColor, "v_Position");
        mNormalHandle   = GLES20.glGetAttribLocation(sColor, "v_Normal");
        mColorHandle    = GLES20.glGetAttribLocation(sColor, "v_Color");

        mLightPosHandle = GLES20.glGetUniformLocation(sColor, "lightPos");
        mLightColHandle = GLES20.glGetUniformLocation(sColor, "lightCol");
        mLightRadHandle = GLES20.glGetUniformLocation(sColor, "dirRad");
        mAmbientHandle  = GLES20.glGetUniformLocation(sColor, "ambient");

        mMVPHandle      = GLES20.glGetUniformLocation(sColor, "uMVPMatrix");
    }

    public synchronized static void setMesh(Mesh mesh) {
        ColorShader.mesh = mesh;
    }

    public synchronized static void setLightPosition(Vector3 position) {
        lightPosition = position;
    }

    public synchronized static void setLightRadius(float radius) {
        lightRadius = radius;
    }

    public synchronized static void setLightColor(Color color) {
        lightColor = color;
    }

    public synchronized static void setAmbientColor(Color color) {
        ambientColor = color;
    }



    public synchronized static void draw() {
        if (sColor == -1 || mesh == null || !mesh.hasVBOs()) {
            return;
        }

		GLES20.glUseProgram(sColor);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getPointVBO());
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, 0);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getNormalVBO());
        GLES20.glVertexAttribPointer(mNormalHandle, 3, GLES20.GL_FLOAT, false, 0, 0);
        GLES20.glEnableVertexAttribArray(mNormalHandle);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getColorVBO());
        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false, 0, 0);
        GLES20.glEnableVertexAttribArray(mColorHandle);

		//transform matrix
		GLES20.glUniformMatrix4fv(mMVPHandle, 1, false, Camera.getActiveMVP(), 0);

        //lighting
        GLES20.glUniform3fv(mLightPosHandle, 1, lightPosition.toFloatArray(), 0);
        GLES20.glUniform3fv(mLightColHandle, 1, lightColor.toRGBFloatArray(), 0);
        GLES20.glUniform3fv(mAmbientHandle, 1, ambientColor.toRGBFloatArray(), 0);
        GLES20.glUniform1f(mLightRadHandle, 100f);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mesh.getOrderVBO());

        int indexCount = mesh.getOrderBuffer().capacity();

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexCount, GLES20.GL_UNSIGNED_SHORT, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mNormalHandle);
		GLES20.glDisableVertexAttribArray(mColorHandle);
	}
}
