package psiborg.android5000.util;

public class Vector3 {
	public double x, y, z;
    public static final Vector3 zero = new Vector3(0,0,0);
    public static final Vector3 one  = new Vector3(1,1,1);
    public Vector3() { set(0,0,0); }
    public Vector3(Vector3 v) { set(v); }
    public Vector3(float x, float y, float z) {
        set(x,y,z);
    }
    public Vector3(double x, double y, double z) {
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
    public Vector3 set(double x, double y, double z) {
        this.x = x; this.y = y; this.z = z;
        return this;
    }
    public Vector3 set(double[] x) {
        this.x = x[0]; this.y = x[1]; this.z = x[2];
        return this;
    }
    public Vector3 set(float[] x) {
        this.x = x[0]; this.y = x[1]; this.z = x[2];
        return this;
    }
    public Vector3 set(int[] x) {
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
    public Vector3 mult(final double n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        return this;
    }
    public Vector3 mult(final Vector3 v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        return this;
    }
    public Vector3 add(final Vector3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }
    public Vector3 sub(final Vector3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }
    public double len() {
        return Math.sqrt(this.len2());
    }
    public double len2() {
        return x*x+y*y+z*z;
    }
    public Vector3 normalized() {
        double l = len();
        if (l != 0) {
            this.mult(1/l);
        }
        return this;
    }
    public double dot(final Vector3 v) {
        return this.x*v.x+this.y*v.y+this.z*v.z;
    }
    public Vector3 cross(final Vector3 v) {
        Vector3 result = new Vector3(0, 0, 0);
        result.x = (this.y * v.z) - (this.z * v.y);
        result.y = (this.z * v.x) - (this.x * v.z);
        result.z = (this.x * v.y) - (this.y * v.x);
        return result;
    }
	public static Vector3 cross(Vector3 v1, Vector3 v2) {
		return v1.cross(v2);
	}
	public static double dot(Vector3 v1, Vector3 v2) {
		return v1.dot(v2);
	}
	public static Vector3 mult(Vector3 v, double s) {
		return new Vector3(v).mult(s);
	}
	public static Vector3 mult(Vector3 v1, Vector3 v2) {
		return new Vector3(v1).mult(v2);
	}
    public static Vector3 add(Vector3 v1, Vector3 v2) {
        return new Vector3(v1).add(v2);
    }
	public static Vector3 sub(Vector3 v1, Vector3 v2) {
		return new Vector3(v1).sub(v2);
	}
	public static Vector3 normalized(Vector3 v) {
		return new Vector3(v).normalized();
	}
	public float[] toFloatArray() {
		return new float[]{(float)x,(float)y,(float)z};
	}
	public String toString() {
		return "vector3["+x+" , "+y+" , "+z+"]";
	}
    public static float[] toFloatArray(Vector3[] a) {
        float[] r = new float[a.length*3];
        for (int i=0; i<a.length; i++) {
            r[i*3]   = (float)a[i].x;
            r[i*3+1] = (float)a[i].y;
            r[i*3+2] = (float)a[i].z;
        }
        return r;
    }
}
