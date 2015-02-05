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
    public double len() {
        return Math.sqrt(this.len2());
    }
    public double len2() {
        return x*x + y*y;
    }
    public Vector2 normalize() {
        double v = this.len();
        if (v != 0) {
            this.mult(1/v);
        }
        return this;
    }
	public static double dot(final Vector2 v1, final Vector2 v2) {
		return v1.dot(v2);
	}
	public static Vector2 mult(final Vector2 v, final float s) {
		return new Vector2(v).mult(s);
	}
	public static Vector2 mult(final Vector2 v1, final Vector2 v2) {
		return new Vector2(v1).mult(v2);
	}
    public static Vector2 add(final Vector2 v1, final Vector2 v2) {
        return new Vector2(v1).add(v2);
    }
	public static Vector2 sub(final Vector2 v1, final Vector2 v2) {
        return new Vector2(v1).sub(v2);
	}
	public static Vector2 normalize(final Vector2 v) {
		return new Vector2(v).normalize();
	}
    public static double distance(final Vector2 v1, final Vector2 v2) {
        return Vector2.sub(v1,v2).len();
    }
	public float[] toFloatArray() {
		return new float[]{(float)x, (float)y};
	}
    public String toString() {
        return "vector2["+x+" , "+y+"]";
    }
    public static float[] toFloatArray(Vector2[] a) {
        float[] r = new float[a.length*2];
        for (int i=0; i<a.length; i++) {
            r[i*2]   = (float)a[i].x;
            r[i*2+1] = (float)a[i].y;
        }
        return r;
    }
}
