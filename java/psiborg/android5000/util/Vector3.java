package psiborg.android5000.util;

public class Vector3 {
	public float x, y, z;
	public Vector3(float xi, float yi, float zi) {
		x = xi; y = yi; z = zi;
	}
	public Vector3(Vector2 v) {
		x = v.x; y = v.y; z = 0f;
	}
	public Vector3(Vector2 v, float zi) {
		x = v.x; y = v.y; z = zi;
	}
	public static Vector3 cross(Vector3 v1, Vector3 v2) {
		Vector3 result = new Vector3(0, 0, 0);
		result.x = (v1.y * v2.z) - (v1.z * v2.y);
		result.y = (v1.z * v2.x) - (v1.x * v2.z);
		result.z = (v1.x * v2.y) - (v1.y * v2.x);
		return result;
	}
	public static float dot(Vector3 v1, Vector3 v2) {
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
	}
	public static Vector3 mult(Vector3 v, float s) {
		return new Vector3(v.x*s, v.y*s, v.z*s);
	}
	public static Vector3 mult(Vector3 v1, Vector3 v2) {
		return new Vector3(v1.x*v2.x, v1.y*v2.y, v1.z*v2.z);
	}
	public static Vector3 sub(Vector3 v1, Vector3 v2) {
		return new Vector3(v1.x-v2.x , v1.y-v2.y , v1.z-v2.z);
	}
	public static Vector3 norm(Vector3 v) {
		float m = (float)Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
		return mult(v,1/m);
	}
	public static Vector3 add(Vector3 v1, Vector3 v2) {
		return new Vector3(v1.x+v2.x , v1.y+v2.y , v1.z+v2.z);
	}
	public static float[] toFloatArray(Vector3 v) {
		return new float[]{v.x,v.y,v.z};
	}
	public static float[] toFloatArray(Vector3[] a) {
		float[] r = new float[a.length*3];
		for (int i=0; i<a.length; i++) {
			r[i*3]   = a[i].x;
			r[i*3+1] = a[i].y;
			r[i*3+2] = a[i].z;
		}
		return r;
	}
	public float[] toFloatArray() {
		return new float[]{x,y,z};
	}
	public static Vector3 getNormal(Vector3 p1, Vector3 p2, Vector3 p3) {
		
		return norm(cross(sub(p2,p1),sub(p3,p1)));
	}
	public static Vector3[] getNormals(Vector3[] points, int[] order) {
		Vector3[] norms = new Vector3[points.length];
		for (int i=0; i<norms.length; i++) {
			norms[i] = new Vector3(0,0,0);
		}
		for (int i=0; i<order.length; i+=3) {
			Vector3 n = getNormal(points[order[i]],points[order[i+1]],points[order[i+2]]);
			norms[order[i]]   = add(norms[order[i]],n);
			norms[order[i+1]] = add(norms[order[i+1]],n);
			norms[order[i+2]] = add(norms[order[i+2]],n);
		}
		for (int i=0; i<norms.length; i++) norms[i] = norm(norms[i]);
		return norms;
	}
	public String toString() {
		return "["+x+" "+y+" "+z+"]";
	}
}
