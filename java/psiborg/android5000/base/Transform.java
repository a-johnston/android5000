package psiborg.android5000.base;

import android.opengl.Matrix;

import psiborg.android5000.util.Quaternion;
import psiborg.android5000.util.Vector3;

public class Transform {
    public static final Transform ID = new Transform();

    public Vector3 position;
    public Quaternion rotation;

    public Transform() {
        position = new Vector3();
        rotation = Quaternion.ID;
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
            Matrix.translateM(m, 0, (float)position.getX(), (float)position.getY(), (float)position.getZ());
        }
    }
}
