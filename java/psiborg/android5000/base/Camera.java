package psiborg.android5000.base;

import android.opengl.Matrix;

import psiborg.android5000.GameEngine;
import psiborg.android5000.util.Vector3;

public class Camera {
    private static Camera active;

    private Vector3 from, to, up;
    private float fov, aspect, near, far;
	private float[] look = new float[16];
	private float[] per  = new float[16];
	private float[] mv = new float[16];

    public Camera(Vector3 from, Vector3 to, Vector3 up, float fov, float near, float far) {
        this.from = from;
        this.to   = to;
        this.up   = up;
        this.fov    = fov;
        this.aspect = GameEngine.getAspect();
        this.near   = near;
        this.far    = far;
        updateLook();
        updatePer();
        updateMV();
    }

    public void setMain() {
        active = this;
    }

    public void unsetMain() {
        if (this == active) {
            active = null;
        }
    }

	public void updateLook(Vector3 from, Vector3 to, Vector3 up) {
        this.from = from;
        this.to = to;
        this.up = up;
        updateLook();
		updateMV();
	}

	public void updatePerspective(float fov, float near, float far) {
        this.fov    = fov;
        this.aspect = GameEngine.getAspect();
        this.near   = near;
        this.far    = far;
        updatePer();
        updateMV();
	}
    public void updateFrom(Vector3 from) {
        this.from = from;
        updateLook();
        updateMV();
    }
    public void updateTo(Vector3 to) {
        this.to = to;
        updateLook();
        updateMV();
    }
    public void translate(Vector3 vector) {
        from = from.plus(vector);
        to   = to.plus(vector);
        updateLook();
        updateMV();
    }
    public void updateAspectRatio() {
        this.aspect = GameEngine.getAspect();
        updatePer();
        updateMV();
    }

	public float[] getMV() {
        return mv;
	}

    public float[] getMVP(float[] modelMat) {
        Matrix.multiplyMM(modelMat, 0, mv, 0, modelMat, 0);
        return modelMat;
    }

    private void updateLook() {
        Matrix.setLookAtM(look, 0,
                (float) from.getX(), (float) from.getY(), (float) from.getZ(),
                (float) to.getX(), (float) to.getY(), (float) to.getZ(),
                (float) up.getX(), (float) up.getY(), (float) up.getZ());
    }

    private void updatePer() {
        Matrix.perspectiveM(per, 0, fov, aspect, near, far);
    }

    private void updateMV() {
        Matrix.multiplyMM(mv, 0, per, 0, look, 0);
    }

    public static float[] getActiveMV() {
        return active == null ? null : active.getMV();
    }

    public static float[] getActiveMVP(float[] modelMat) {
        return active == null ? null : active.getMVP(modelMat);
    }

    public static Camera getActive() {
        return active;
    }
}
