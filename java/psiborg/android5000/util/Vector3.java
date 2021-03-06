package psiborg.android5000.util;

public class Vector3 {
    public static final Vector3 ZERO = new Vector3(0,0,0);
    public static final Vector3 ONE  = new Vector3(1,1,1);
    public static final Vector3 UNIT_X  = new Vector3(1,0,0);
    public static final Vector3 UNIT_Y  = new Vector3(0,1,0);
    public static final Vector3 UNIT_Z  = new Vector3(0,0,1);

    public final double x, y, z;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(Vector2 v) {
        this(v, 0f);
    }

    public Vector3(Vector2 v, float z) {
        this(v.x, v.y, z);
    }

    public Vector3(Quaternion q) {
        this(q.x, q.y, q.z);
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 addX(double x) {
        return new Vector3(this.x + x, this.y, this.z);
    }

    public Vector3 addY(double y) {
        return new Vector3(this.x, this.y + y, this.z);
    }

    public Vector3 addZ(double z) {
        return new Vector3(this.x, this.y, this.z + z);
    }

    public Vector3 mult(final double n) {
        return new Vector3(x*n, y*n, z*n);
    }

    public Vector3 mult(final Vector3 v) {
        return new Vector3(this.x * v.x, this.y * v.y, this.z * v.z);
    }

    public Vector3 plus(final Vector3 v) {
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public Vector3 minus(final Vector3 v) {
        return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public double len() {
        return Math.sqrt(this.len2());
    }

    public double len2() {
        return x*x+y*y+z*z;
    }

    public Vector3 normalize() {
        double l = len();
        if (l != 0) {
            return mult(1/l);
        }
        return this;
    }

    public double dot(final Vector3 v) {
        return this.x*v.x+this.y*v.y+this.z*v.z;
    }

    public Vector3 cross(final Vector3 v) {
        Vector3 result = new Vector3(
                (this.y * v.z) - (this.z * v.y),
                (this.z * v.x) - (this.x * v.z),
                (this.x * v.y) - (this.y * v.x));
        return result;
    }

    public static Vector3 lerp(final Vector3 a, final Vector3 b, final double i) {
        final double j = 1-i;
        return lincomb(a,b,i,j);
    }

    public static Vector3 nlerp(final Vector3 a, final Vector3 b, final double i) {
        return Vector3.lerp(a,b,i).normalize();
    }

    public static Vector3 slerp(final Vector3 a, final Vector3 b, final double i) {
        final double d = a.dot(b);
        final double angle = Math.acos(d);
        final double is = 1.0/Math.sin(angle);
        return lincomb(a, b, Math.sin((1 - i) * angle) * is, Math.sin((i * angle)) * is);
    }

    public static Vector3 lincomb(final Vector3 a, final Vector3 b, final double i, final double j) {
        return new Vector3(a.x*j + b.x*i,
                           a.y*j + b.y*i,
                           a.z*j + b.z*i);
    }

    public static Vector3 getNormalVector(Vector3 p1, Vector3 p2, Vector3 p3) {
        return p2.minus(p1).cross(p3.minus(p1)).normalize();
    }

    public static double distance(final Vector3 v1, final Vector3 v2) {
        return v1.minus(v2).len();
    }

	public float[] toFloatArray() {
		return new float[]{(float)x,(float)y,(float)z};
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

    @Override
    public String toString() {
        return "vector3< " + x + ", " + y + ", " + z + " >";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector3)) {
            return false;
        }
        Vector3 other = (Vector3) o;
        return x == other.x && y == other.y && z == other.z;
    }
}
