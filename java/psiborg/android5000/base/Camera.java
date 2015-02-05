package psiborg.android5000.base;

import android.opengl.Matrix;

import psiborg.android5000.GameEngine;
import psiborg.android5000.util.Vector3;

public class Camera {
    public static Camera active;
    private Vector3 from, to, up;
    private float fov, aspect, near, far;
	private float[] look = new float[16];
	private float[] per  = new float[16];
	private float[] mvp  = new float[16];

    public Camera(float[] from, float[] to, float[] up, float fov, float near, float far) {
        this.from = new Vector3(from);
        this.to   = new Vector3(to);
        this.up   = new Vector3(up);
        this.fov    = fov;
        this.aspect = GameEngine.aspect;
        this.near   = near;
        this.far    = far;
        updateLook();
        updatePer();
        updateMVP();
    }
    public Camera(Vector3 from, Vector3 to, Vector3 up, float fov, float near, float far) {
        this.from = new Vector3(from);
        this.to   = new Vector3(to);
        this.up   = new Vector3(up);
        this.fov    = fov;
        this.aspect = GameEngine.aspect;
        this.near   = near;
        this.far    = far;
        updateLook();
        updatePer();
        updateMVP();
    }
    public void setMain() {
        active = this;
    }
	public void updateLook(float[] from, float[] to, float[] up) {
        this.from.set(from);
        this.to.set(to);
        this.up.set(up);
        updateLook();
		updateMVP();
	}
	public void updatePerspective(float fov, float near, float far) {
        this.fov    = fov;
        this.aspect = GameEngine.aspect;
        this.near   = near;
        this.far    = far;
        updatePer();
        updateMVP();
	}
    public void updateAspectRatio() {
        this.aspect = GameEngine.aspect;
        updatePer();
        updateMVP();
    }
	public float[] getMVP() {
        return mvp;
	}
    private void updateLook() {
        Matrix.setLookAtM(look,0,
                (float)from.x, (float)from.y, (float)from.z,
                (float)to.x,   (float)to.y,   (float)to.z,
                (float)up.x,   (float)up.y,   (float)up.z);
    }
    private void updatePer() {
        Matrix.perspectiveM(per,0,fov,aspect,near,far);
    }
    private void updateMVP() {
        Matrix.multiplyMM(mvp, 0, per, 0, look,0);
    }
}
