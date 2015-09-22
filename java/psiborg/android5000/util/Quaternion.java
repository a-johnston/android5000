package psiborg.android5000.util;

public class Quaternion {
    public static final Quaternion ID   = new Quaternion();
    public static final Quaternion ONE  = Quaternion.createQuaternion(1, 1, 1, 1);
    public static final Quaternion ZERO = Quaternion.createQuaternion(0, 0, 0, 0);

    private double x, y, z, w;

    private Quaternion() {}

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    public static Quaternion createQuaternion(final double x, final double y, final double z, final double w) {
        Quaternion q = new Quaternion();
        q.x = x;
        q.y = y;
        q.z = z;
        q.w = w;
        return q;
    }

    public static Quaternion from(final Quaternion q) {
        return createQuaternion(q.x, q.y, q.z, q.w);
    }

    public static Quaternion from(final Vector3 v) {
        return createQuaternion(v.getX(), v.getY(), v.getZ(), 0.0);
    }

    public static Quaternion fromAxisAngle(final Vector3 axis, final double angle) {
        final double half = angle/2f;
        final double sina = Math.sin(half);
        return createQuaternion(axis.getX() * sina, axis.getY() * sina, axis.getZ() * sina, Math.cos(half));
    }

    public static Quaternion fromEulerAngles(final double yaw, final double pitch, final double roll) {
        final double hy  = yaw * 0.5f;
        final double hp  = pitch * 0.5f;
        final double hr  = roll * 0.5f;
        final double shy = Math.sin(hy);
        final double chy = Math.cos(hy);
        final double shp = Math.sin(hp);
        final double chp = Math.cos(hp);
        final double shr = Math.sin(hr);
        final double chr = Math.cos(hr);
        final double chy_shp = chy * shp;
        final double shy_chp = shy * chp;
        final double chy_chp = chy * chp;
        final double shy_shp = shy * shp;

        return createQuaternion(
                (chy_shp * chr) + (shy_chp * shr),
                (shy_chp * chr) - (chy_shp * shr),
                (chy_chp * shr) - (shy_shp * chr),
                (chy_chp * chr) + (shy_shp * shr));
    }

    public Quaternion normalize() {
        double l = this.len();
        if (l != 0) {
            return mult(1/l);
        }
        return this;
    }

    public Quaternion conjugate() {
        return createQuaternion(-x, -y, -z, w);
    }

    public Quaternion mult(final double x) {
        Quaternion r = Quaternion.from(this);
        r.x *= x;
        r.y *= x;
        r.z *= x;
        r.w *= x;
        return r;
    }

    public Quaternion mult(final Quaternion q) {
        final double nx = this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y;
        final double ny = this.w * q.y + this.y * q.w + this.z * q.x - this.x * q.z;
        final double nz = this.w * q.z + this.z * q.w + this.x * q.y - this.y * q.x;
        final double nw = this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z;
        return createQuaternion(nx, ny, nz, nw);
    }

    public Quaternion multLeft(final Quaternion q) {
        final double nx = q.w * this.x + q.x * this.w + q.y * this.z - q.z * y;
        final double ny = q.w * this.y + q.y * this.w + q.z * this.x - q.x * z;
        final double nz = q.w * this.z + q.z * this.w + q.x * this.y - q.y * x;
        final double nw = q.w * this.w - q.x * this.x - q.y * this.y - q.z * z;
        return createQuaternion(nx, ny, nz, nw);
    }

    public double dot(final Quaternion q) {
        return this.x*q.x + this.y*q.y + this.z*q.z + this.w*q.w;
    }

    public double len() {
        return (float)Math.sqrt(len2());
    }

    public double len2() {
        return x*x+y*y+z*z+w*w;
    }
    public void toMatrix (final float[] m) {
        if (m.length != 16) {
            return;
        }

        final double xx = x * x;
        final double xy = x * y;
        final double xz = x * z;
        final double xw = x * w;
        final double yy = y * y;
        final double yz = y * z;
        final double yw = y * w;
        final double zz = z * z;
        final double zw = z * w;

        m[0] = (float)(1 - 2 * (yy + zz));
        m[1] = (float)(2 * (xy + zw));
        m[2] = (float)(2 * (xz - yw));
        m[3] = 0;

        m[4] = (float)(2 * (xy - zw));
        m[5] = (float)(1 - 2 * (xx + zz));
        m[6] = (float)(2 * (yz + xw));
        m[7] = 0;

        m[8] = (float)(2 * (xz + yw));
        m[9] = (float)(2 * (yz - xw));
        m[10] = (float)(1 - 2 * (xx + yy));
        m[11] = 0;

        m[12] = 0;
        m[13] = 0;
        m[14] = 0;
        m[15] = 1;
    }

    public Vector3 transform(final Vector3 v) {
        Quaternion q1 = conjugate();
        Quaternion q2 = from(v);
        return new Vector3(q1.multLeft(q2.multLeft(this)));
    }

    public static Quaternion lerp(final Quaternion a, final Quaternion b, final double i) {
        final double j = 1-i;
        return lincomb(a, b, i, j);
    }

    public static Quaternion nlerp(final Quaternion a, final Quaternion b, final double i) {
        return Quaternion.lerp(a,b,i).normalize();
    }

    public static Quaternion slerp(final Quaternion a, final Quaternion b, final double i) {
        final double d = a.dot(b);
        final double angle = Math.acos(d);
        final double is = 1.0/Math.sin(angle);
        return lincomb(a,b,Math.sin((1-i) * angle)*is,Math.sin((i*angle))*is);
    }

    private static Quaternion lincomb(final Quaternion a, final Quaternion b, final double i, final double j) {
        return createQuaternion(
                a.x*j + b.x*i,
                a.y*j + b.y*i,
                a.z*j + b.z*i,
                a.w*j + b.w*i);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Quaternion)) {
            return false;
        }
        Quaternion q = (Quaternion) o;
        return ((this.x == q.x) && (this.y == q.y) && (this.z == q.z) && (this.w == q.w));
    }

    public String toString() {
        return "quaternion["+x+" , "+y+" , "+z+" , "+w+"]";
    }
}
