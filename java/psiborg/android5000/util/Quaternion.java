package psiborg.android5000.util;

public class Quaternion {
    public float x, y, z, w;
    public Quaternion() {
        set(0,0,0,1);
    }
    public Quaternion(final float x, final float y, final float z, final float w) {
        set(x, y, z, w);
    }
    public Quaternion(final Quaternion q) { set(q); }
    public Quaternion(final Vector3 v) { set(v.x, v.y, v.z, 0); }
    public Quaternion(final Vector3 axis, final float angle) { set(axis, angle); }
    public Quaternion set(final float x, final float y, final float z, final float w) {
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
    public Quaternion set(final Vector3 axis, final float angle) {

        return this;
    }
    public Quaternion set(final float yaw, final float pitch, final float roll) {
        final float hy  = yaw * 0.5f;
        final float hp  = pitch * 0.5f;
        final float hr  = roll * 0.5f;
        final float shy = (float)Math.sin(hy);
        final float chy = (float)Math.cos(hy);
        final float shp = (float)Math.sin(hp);
        final float chp = (float)Math.cos(hp);
        final float shr = (float)Math.sin(hr);
        final float chr = (float)Math.cos(hr);
        final float chy_shp = chy * shp;
        final float shy_chp = shy * chp;
        final float chy_chp = chy * chp;
        final float shy_shp = shy * shp;

        x = (chy_shp * chr) + (shy_chp * shr);
        y = (shy_chp * chr) - (chy_shp * shr);
        z = (chy_chp * shr) - (shy_shp * chr);
        w = (chy_chp * chr) + (shy_shp * shr);
        return this;
    }
    public Quaternion normalize() {
        float l = this.len();
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
    public Quaternion mult(final float x) {
        this.x *= x;
        this.y *= x;
        this.z *= x;
        this.w *= x;
        return this;
    }
    public Quaternion mult(final Quaternion q) {
        final float nx = this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y;
        final float ny = this.w * q.y + this.y * q.w + this.z * q.x - this.x * q.z;
        final float nz = this.w * q.z + this.z * q.w + this.x * q.y - this.y * q.x;
        final float nw = this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z;
        return set(nx, ny, nz, nw);
    }
    public Quaternion multLeft (Quaternion q) {
        final float nx = q.w * this.x + q.x * this.w + q.y * this.z - q.z * y;
        final float ny = q.w * this.y + q.y * this.w + q.z * this.x - q.x * z;
        final float nz = q.w * this.z + q.z * this.w + q.x * this.y - q.y * x;
        final float nw = q.w * this.w - q.x * this.x - q.y * this.y - q.z * z;
        return set(nx, ny, nz, nw);
    }
    public static Quaternion mult(final Quaternion a, final Quaternion b) {
        Quaternion t = new Quaternion(a);
        return t.mult(b);
    }
    public float len() {
        return (float)Math.sqrt(len2());
    }
    public float len2() {
        return x*x+y*y+z*z+w*w;
    }
    //TODO: ensure the matrix is in correct order
    public void toMatrix (final float[] m) {
        if (m.length != 16) {
            return;
        }

        final float xx = x * x;
        final float xy = x * y;
        final float xz = x * z;
        final float xw = x * w;
        final float yy = y * y;
        final float yz = y * z;
        final float yw = y * w;
        final float zz = z * z;
        final float zw = z * w;

        m[0] = 1 - 2 * (yy + zz);
        m[1] = 2 * (xy + zw);
        m[2] = 2 * (xz - yw);
        m[3] = 0;

        m[4] = 2 * (xy - zw);
        m[5] = 1 - 2 * (xx + zz);
        m[6] = 2 * (yz + xw);
        m[7] = 0;

        m[8] = 2 * (xz + yw);
        m[9] = 2 * (yz - xw);
        m[10] = 1 - 2 * (xx + yy);
        m[11] = 0;

        m[12] = 0;
        m[13] = 0;
        m[14] = 0;
        m[15] = 1;
    }
    public Vector3 transform(Vector3 v) {
        Quaternion q1 = new Quaternion(this);
        Quaternion q2 = new Quaternion(v);
        q1.conjugate();
        q1.multLeft(q2.multLeft(this));
        Vector3 r = new Vector3(q2.x, q2.y, q2.z);
        return r;
    }
    public boolean equals(Quaternion q) {
        return ((this.x == q.x) && (this.y == q.y) && (this.z == q.z) && (this.w == q.w));
    }
    public String toString() {
        return "quaternion["+x+" , "+y+" , "+z+" , "+w+"]";
    }
}
