package psiborg.android5000.util;


public class Vector2 {
    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 ONE  = new Vector2(1, 1);
    public static final Vector2 UNIT_X = new Vector2(1, 0);
    public static final Vector2 UNIT_Y = new Vector2(0, 1);

	private double x, y;

	public Vector2(final double x, final double y) {
		this.x = x;
        this.y = y;
	}

    public Vector2(final Vector2 v) {
        this(v.x, v.y);
    }

	public Vector2(final Vector3 v) {
        this(v.getX(), v.getY());
	}

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double dot(final Vector2 v) {
        return this.x*v.x + this.y*v.y;
    }

    public Vector2 mult(final double n) {
        return new Vector2(x*n, y*n);
    }

    public Vector2 mult(final Vector2 v) {
        return new Vector2(x*v.x, y*v.y);
    }

    public Vector2 add(final Vector2 v) {
        return new Vector2(x+v.x, y+v.y);
    }

    public Vector2 sub(final Vector2 v) {
        return new Vector2(x-v.x, y-v.y);
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
            return mult(1/v);
        }
        return this;
    }

    public static double distance(final Vector2 v1, final Vector2 v2) {
        return v1.sub(v2).len();
    }

    public static Vector2 lerp(final Vector2 a, final Vector2 b, final double i) {
        final double j = 1-i;
        return new Vector2(a.x*j + b.x*i,
                           a.y*j + b.y*i);
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
