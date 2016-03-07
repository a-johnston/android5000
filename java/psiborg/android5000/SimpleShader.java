package psiborg.android5000;

import android.opengl.GLES20;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.Shader;
import psiborg.android5000.base.Transform;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.Mesh;

public class SimpleShader extends Shader {
	private static int sColor = -1;

    private static Mesh mesh;
    private static Transform transform;

    private static int mPositionHandle, mMVPHandle;

    public synchronized static void load() {
        if (sColor != -1) {
            return;
        }

        transform = new Transform();

		sColor = instance(IO.readFile("simple_vertex"), IO.readFile("simple_fragment"));

        mPositionHandle = GLES20.glGetAttribLocation(sColor, "v_Position");

        mMVPHandle      = GLES20.glGetUniformLocation(sColor, "uMVPMatrix");
    }

    public static void forgetProgram() {
        sColor = -1;
    }

    public synchronized static void setMesh(Mesh mesh) {
        SimpleShader.mesh = mesh;
    }

    public synchronized static void setTransform(Transform transform) {
        SimpleShader.transform = transform;
    }

    public synchronized static void draw() {
        if (sColor == -1 || mesh == null || !mesh.hasVBOs() || !mesh.meshReady()) {
            return;
        }

		GLES20.glUseProgram(sColor);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getPointVBO());
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, 0);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

		//transform (shortcuts identity transforms in mediocre fashion)
        GLES20.glUniformMatrix4fv(mMVPHandle, 1, false,
                transform == Transform.ID || transform == null ?
                        Camera.getActiveVP() :
                        Camera.getActiveMVP(transform.toMatrix()), 0);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mesh.getOrderVBO());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mesh.getTriCount()*3, GLES20.GL_UNSIGNED_SHORT, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
}
