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
	private float[] mvp  = new float[16];

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
        updateMVP();
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
		updateMVP();
	}

	public void updatePerspective(float fov, float near, float far) {
        this.fov    = fov;
        this.aspect = GameEngine.getAspect();
        this.near   = near;
        this.far    = far;
        updatePer();
        updateMVP();
	}
    public void updateFrom(Vector3 from) {
        this.from = from;
        updateLook();
        updateMVP();
    }
    public void updateTo(Vector3 to) {
        this.to = to;
        updateLook();
        updateMVP();
    }
    public void translate(Vector3 vector) {
        from = from.plus(vector);
        to   = to.plus(vector);
        updateLook();
        updateMVP();
    }
    public void updateAspectRatio() {
        this.aspect = GameEngine.getAspect();
        updatePer();
        updateMVP();
    }
	public float[] getMVP() {
        return mvp;
	}
    private void updateLook() {
        Matrix.setLookAtM(look,0,
                (float)from.getX(), (float)from.getY(), (float)from.getZ(),
                (float)to.getX(),   (float)to.getY(),   (float)to.getZ(),
                (float)up.getX(),   (float)up.getY(),   (float)up.getZ());
    }
    private void updatePer() {
        Matrix.perspectiveM(per, 0, fov, aspect, near, far);
    }
    private void updateMVP() {
        Matrix.multiplyMM(mvp, 0, per, 0, look, 0);
    }

    public static float[] getActiveMVP() {
        return active == null ? null : active.getMVP();
    }

    public static Camera getActive() {
        return active;
    }
}
