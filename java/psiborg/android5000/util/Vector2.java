package psiborg.android5000.util;


public class Vector2 {
	public double x, y;
	public Vector2(final double x, final double y) {
		this.x = x;
        this.y = y;
	}
    public Vector2(final Vector2 v) {
        x = v.x;
        y = v.y;
    }
	public Vector2(final Vector3 v) {
		x = v.x;
        y = v.y;
	}
    public double dot(final Vector2 v) {
        return this.x*v.x + this.y*v.y;
    }
    public Vector2 mult(final double n) {
        this.x *= n;
        this.y *= n;
        return this;
    }
    public Vector2 mult(final Vector2 v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }
    public Vector2 add(final Vector2 v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }
    public Vector2 sub(final Vector2 v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }
	public static double dot(Vector2 v1, Vector2 v2) {
		return v1.dot(v2);
	}
	public static Vector2 mult(final Vector2 v, final float s) {
		return (new Vector2(v)).mult(s);
	}
	public static Vector2 mult(final Vector2 v1, final Vector2 v2) {
		return (new Vector2(v1)).mult(v2);
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
		return v.toFloatArray();
	}
	public static float[] toFloatArray(Vector2[] a) {
		float[] r = new float[a.length*2];
		for (int i=0; i<a.length; i++) {
			r[i*2]   = (float)a[i].x;
			r[i*2+1] = (float)a[i].y;
		}
		return r;
	}
	public float[] toFloatArray() {
		return new float[]{(float)x, (float)y};
	}
    public String toString() {
        return "vector2["+x+" , "+y+"]";
    }
}
