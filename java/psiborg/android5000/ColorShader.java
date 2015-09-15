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

        setLightPosition(Vector3.ZERO);
        setLightRadius(0f);
        setLightColor(Color.BLACK);
        setAmbientColor(Color.BLACK);


    }

    public synchronized static void setMesh(Mesh mesh) {
        if (sColor == -1 || ColorShader.mesh == mesh) {
            return;
        }

        ColorShader.mesh = mesh;

        if (mesh == null) {
            return;
        }

        GLES20.glUseProgram(sColor);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mNormalHandle);
        GLES20.glEnableVertexAttribArray(mColorHandle);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getPointVBO());
        GLES20.glVertexAttribPointer(mPositionHandle, Mesh.BYTES_PER_POINT, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getNormalVBO());
        GLES20.glVertexAttribPointer(mNormalHandle, Mesh.BYTES_PER_NORMAL, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getColorVBO());
        GLES20.glVertexAttribPointer(mColorHandle, Mesh.BYTES_PER_NORMAL, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mNormalHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
    }

    public synchronized static void setLightPosition(Vector3 position) {
        GLES20.glUseProgram(sColor);
        GLES20.glUniform3fv(mLightPosHandle, 1, position.toFloatArray(), 0);
    }

    public synchronized static void setLightRadius(float radius) {
        GLES20.glUseProgram(sColor);
        GLES20.glUniform1f(mLightRadHandle, radius);
    }

    public synchronized static void setLightColor(Color color) {
        GLES20.glUseProgram(sColor);
        GLES20.glUniform3fv(mLightPosHandle, 1, color.toRGBFloatArray(), 0);
    }

    public synchronized static void setAmbientColor(Color color) {
        GLES20.glUseProgram(sColor);
        GLES20.glUniform3fv(mAmbientHandle, 1, color.toRGBFloatArray(), 0);
    }



    public synchronized static void draw() {
        if (sColor == -1 || mesh == null) {
            return;
        }

		GLES20.glUseProgram(sColor);

		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glEnableVertexAttribArray(mNormalHandle);
		GLES20.glEnableVertexAttribArray(mColorHandle);

		//transform matrix
		GLES20.glUniformMatrix4fv(mMVPHandle, 1, false, Camera.getActiveMVP(), 0);

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
