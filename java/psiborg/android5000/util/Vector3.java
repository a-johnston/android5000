package psiborg.android5000.util;

public class Vector3 {
	public float x, y, z;
    public Vector3(Vector3 v) { set(v); }
	public Vector3(float x, float y, float z) {
        set(x,y,z);
	}
    public Vector3(Quaternion q) { set(q.x, q.y, q.z); }
    public Vector3(float[] x) {
        set(x);
    }
	public Vector3(Vector2 v) { set(v); }
	public Vector3(Vector2 v, float z) {
		set(v);
        this.z = z;
	}
    public Vector3 set(float x, float y, float z) {
        this.x = x; this.y = y; this.z = z;
        return this;
    }
    public Vector3 set(float[] x) {
        this.x = x[0]; this.y = x[1]; this.z = x[2];
        return this;
    }
    public Vector3 set(Vector3 v) {
        this.x = v.x; this.y = v.y; this.z = v.z;
        return this;
    }
    public Vector3 set(Vector2 v) {
        this.x = v.x; this.y = v.y; this.z = 0;
        return this;
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
	public String toString() {
		return "vector3["+x+" , "+y+" , "+z+"]";
	}
}
