package psiborg.android5000;

import android.opengl.Matrix;

public class Camera {
	private float[] look = new float[16];
	private float[] per  = new float[16];
	private float[] mvp  = new float[16];
	public Camera(float[] from, float[] to, float[] up, float aspect, float near, float far) {
		Matrix.setLookAtM(look,0,
				   from[0], from[1], from[2],
		 			 to[0],   to[1],   to[2],
		 			 up[0],   up[1],   up[2]);
		Matrix.frustumM(per, 0, -aspect, aspect, -1, 1, near, far);
		Matrix.multiplyMM(mvp, 0, per, 0, look,0);
	}
	public void updateLook(float[] from, float[] to, float[] up) {
		Matrix.setLookAtM(look,0,
					   from[0], from[1], from[2],
			 			 to[0],   to[1],   to[2],
			 			 up[0],   up[1],   up[2]);
		Matrix.multiplyMM(mvp, 0, per, 0, look,0);
	}
	public void updatePer(float aspect, float near, float far) {
		Matrix.frustumM(per, 0, -aspect, aspect, -1, 1, near, far);
		Matrix.multiplyMM(mvp, 0, per, 0, look,0);
	}
	public float[] getMVP() {
		return mvp;
	}
}
