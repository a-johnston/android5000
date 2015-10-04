package psiborg.android5000.base;

import android.opengl.Matrix;

import psiborg.android5000.util.Quaternion;
import psiborg.android5000.util.Vector3;

public class Transform {
    public static final Transform ID = new Transform();

    public final Vector3 position;
    public final Quaternion rotation;

    public Transform() {
        this.position = new Vector3();
        this.rotation = Quaternion.ID;
    }

    public Transform(Vector3 position) {
        this.position = position;
        this.rotation = Quaternion.ID;
    }

    public Transform(Quaternion rotation) {
        this.position = new Vector3();
        this.rotation = rotation;
    }

    public Transform(Vector3 position, Quaternion rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public float[] toMatrix() {
        float[] mat = new float[16];
        toMatrix(mat);
        return mat;
    }

    public void toMatrix (final float[] m) {
        if (m.length != 16) {
            return;
        }
        rotation.toMatrix(m);
        if (!position.equals(Vector3.ZERO)) {
            float[] temp = new float[16];
            Matrix.setIdentityM(temp, 0);
            Matrix.translateM(temp, 0, (float) position.x, (float) position.y, (float) position.z);
            Matrix.multiplyMM(m, 0, temp, 0, m, 0);
        }
    }
}
