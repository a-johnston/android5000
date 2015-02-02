package psiborg.android5000.util;


public class Vector2 {
	public float x, y;
	public Vector2(float xi, float yi) {
		x = xi; y = yi;
	}
	public Vector2(Vector3 v) {
		x = v.x; y = v.y;
	}
	public static float dot(Vector2 v1, Vector2 v2) {
		return v1.x*v2.x + v1.y*v2.y;
	}
	public static Vector2 mult(Vector2 v, float s) {
		return new Vector2(v.x*s, v.y*s);
	}
	public static Vector2 mult(Vector2 v1, Vector2 v2) {
		return new Vector2(v1.x*v2.x, v1.y*v2.y);
	}
	public static Vector2 sub(Vector2 v1, Vector2 v2) {
		return new Vector2(v1.x-v2.x , v1.y-v2.y);
	}
	public static Vector2 norm(Vector2 v) {
		float m = (float)Math.sqrt(v.x*v.x + v.y*v.y);
		return mult(v,1/m);
	}
	public static Vector2 add(Vector2 v1, Vector2 v2) {
		return new Vector2(v1.x+v2.x , v1.y+v2.y);
	}
	public static float[] toFloatArray(Vector2 v) {
		return new float[]{v.x,v.y};
	}
	public static float[] toFloatArray(Vector2[] a) {
		float[] r = new float[a.length*2];
		for (int i=0; i<a.length; i++) {
			r[i*2]   = a[i].x;
			r[i*2+1] = a[i].y;
		}
		return r;
	}
	public float[] toFloatArray() {
		return new float[]{x,y};
	}
}
