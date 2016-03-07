package psiborg.android5000.base;

import android.opengl.Matrix;
import android.util.Log;

import psiborg.android5000.GameEngine;
import psiborg.android5000.util.Vector3;

public class Camera {
    private static Camera active;

    private Vector3 from, to, up;
    private float fov, aspect, near, far;
	private float[] view = new float[16];
	private float[] per  = new float[16];
	private float[] vp = new float[16];

    public Camera(Vector3 from, Vector3 to, Vector3 up, float fov, float near, float far) {
        this.from = from;
        this.to   = to;
        this.up   = up;
        this.fov    = fov;
        this.aspect = GameEngine.getAspect();
        this.near   = near;
        this.far    = far;
        updateView();
        updatePer();
        updateVP();
    }

    public void setMain() {
        active = this;
    }

    public void unsetMain() {
        if (this == active) {
            active = null;
        }
    }

    public void updateView(Transform transform) {
        updateView(
                transform.position,
                transform.position.plus(transform.rotation.transform(Vector3.UNIT_X)),
                transform.position.plus(transform.rotation.transform(Vector3.UNIT_Z)));
    }

	public void updateView(Vector3 from, Vector3 to, Vector3 up) {
        this.from = from;
        this.to = to;
        this.up = up;
        updateView();
		updateVP();
	}

	public void updatePerspective(float fov, float near, float far) {
        this.fov    = fov;
        this.aspect = GameEngine.getAspect();
        this.near   = near;
        this.far    = far;
        updatePer();
        updateVP();
	}
    public void updateFrom(Vector3 from) {
        this.from = from;
        updateView();
        updateVP();
    }
    public void updateTo(Vector3 to) {
        this.to = to;
        updateView();
        updateVP();
    }
    public void translate(Vector3 vector) {
        from = from.plus(vector);
        to   = to.plus(vector);
        updateView();
        updateVP();
    }
    public void updateAspectRatio() {
        this.aspect = GameEngine.getAspect();
        updatePer();
        updateVP();
    }

	public float[] getVP() {
        return vp;
	}

    public float[] getMVP(float[] modelMat) {
        Matrix.multiplyMM(modelMat, 0, vp, 0, modelMat, 0);
        return modelMat;
    }

    private void updateView() {
        Matrix.setLookAtM(view, 0,
                (float) from.x, (float) from.y, (float) from.z,
                (float) to.x, (float) to.y, (float) to.z,
                (float) up.x, (float) up.y, (float) up.z);
    }

    private void updatePer() {
        Matrix.perspectiveM(per, 0, fov, aspect, near, far);
    }

    private void updateVP() {
        Matrix.multiplyMM(vp, 0, per, 0, view, 0);

        Log.i("vp", "View");
        printMatrix(view);
        Log.i("vp", "Per");
        printMatrix(per);
        Log.i("vp", "VP");
        printMatrix(vp);
    }

    public static void printMatrix(float[] mat) {
        for (int i = 0; i < 16; i += 4) {
            Log.i("matrix", "[ " + mat[i] + " " + mat[i+1] + " " + mat[i+2] + " " + mat[i+3] + " ]");
        }
    }

    public static float[] getActiveVP() {
        return active == null ? null : active.getVP();
    }

    public static float[] getActiveMVP(float[] modelMat) {
        return active == null ? null : active.getMVP(modelMat);
    }

    public static Camera getActive() {
        return active;
    }
}
