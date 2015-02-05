package psiborg.android5000.util;

public class Quaternion {
    public double x, y, z, w;
    public static final Quaternion identity = new Quaternion();
    public static final Quaternion one      = new Quaternion(1,1,1,1);
    public static final Quaternion zero     = new Quaternion(0,0,0,0);
    public Quaternion() {
        set(0,0,0,1);
    }
    public Quaternion(final double x, final double y, final double z, final double w) {
        set(x, y, z, w);
    }
    public Quaternion(final Quaternion q) { set(q); }
    public Quaternion(final Vector3 v) { set(v.x, v.y, v.z, 0); }
    public Quaternion(final Vector3 axis, final double angle) { set(axis, angle); }
    public Quaternion set(final double x, final double y, final double z, final double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }
    public Quaternion set(final Quaternion q) {
        this.set(q.x, q.y, q.z, q.w);
        return this;
    }
    public Quaternion set(final Vector3 axis, final double angle) {
        final double half = angle/2f;
        final double sina = Math.sin(half);
        return this.set(axis.x*sina, axis.y*sina, axis.z*sina, Math.cos(half));
    }
    public Quaternion set(final double yaw, final double pitch, final double roll) {
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

        x = (chy_shp * chr) + (shy_chp * shr);
        y = (shy_chp * chr) - (chy_shp * shr);
        z = (chy_chp * shr) - (shy_shp * chr);
        w = (chy_chp * chr) + (shy_shp * shr);
        return this;
    }
    public Quaternion normalize() {
        double l = this.len();
        if (l != 0) {
            this.mult(1/l);
        }
        return this;
    }
    public Quaternion conjugate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }
    public Quaternion mult(final double x) {
        this.x *= x;
        this.y *= x;
        this.z *= x;
        this.w *= x;
        return this;
    }
    public Quaternion mult(final Quaternion q) {
        final double nx = this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y;
        final double ny = this.w * q.y + this.y * q.w + this.z * q.x - this.x * q.z;
        final double nz = this.w * q.z + this.z * q.w + this.x * q.y - this.y * q.x;
        final double nw = this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z;
        return set(nx, ny, nz, nw);
    }
    public Quaternion multLeft (Quaternion q) {
        final double nx = q.w * this.x + q.x * this.w + q.y * this.z - q.z * y;
        final double ny = q.w * this.y + q.y * this.w + q.z * this.x - q.x * z;
        final double nz = q.w * this.z + q.z * this.w + q.x * this.y - q.y * x;
        final double nw = q.w * this.w - q.x * this.x - q.y * this.y - q.z * z;
        return set(nx, ny, nz, nw);
    }
    public static Quaternion mult(final Quaternion a, final Quaternion b) {
        Quaternion t = new Quaternion(a);
        return t.mult(b);
    }
    public double len() {
        return (float)Math.sqrt(len2());
    }
    public double len2() {
        return x*x+y*y+z*z+w*w;
    }
    //TODO: ensure the matrix is in correct order
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
    public Vector3 transform(Vector3 v) {
        Quaternion q1 = (new Quaternion(this)).conjugate();
        Quaternion q2 = new Quaternion(v);
        q1.multLeft(q2.multLeft(this));
        Vector3 r = new Vector3(q1.x, q1.y, q1.z);
        return r;
    }
    public boolean equals(Quaternion q) {
        return ((this.x == q.x) && (this.y == q.y) && (this.z == q.z) && (this.w == q.w));
    }
    public String toString() {
        return "quaternion["+x+" , "+y+" , "+z+" , "+w+"]";
    }
}
