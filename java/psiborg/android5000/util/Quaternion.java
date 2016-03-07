package psiborg.android5000.util;

public class Quaternion {
    public static final Quaternion ID   = new Quaternion(0, 0, 0, 1);
    public static final Quaternion ONE  = new Quaternion(1, 1, 1, 1);
    public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);

    public final double x, y, z, w;

    private Quaternion(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static Quaternion from(final Vector3 v) {
        return new Quaternion(v.x, v.y, v.z, 0.0);
    }

    public static Quaternion fromAxisAngle(final Vector3 axis, double angle) {
        angle = Math.toRadians(angle) / 2.0;
        final double sin = Math.sin(angle);
        return new Quaternion(axis.x * sin, axis.y * sin, axis.z * sin, Math.cos(angle));
    }

    public static Quaternion fromEulerAngles(double yaw, double pitch, double roll) {
        yaw   = Math.toRadians(yaw)   / 2.0;
        pitch = Math.toRadians(pitch) / 2.0;
        roll  = Math.toRadians(roll)  / 2.0;

        final double shy = Math.sin(yaw);
        final double chy = Math.cos(yaw);
        final double shp = Math.sin(pitch);
        final double chp = Math.cos(pitch);
        final double shr = Math.sin(roll);
        final double chr = Math.cos(roll);
        final double chy_shp = chy * shp;
        final double shy_chp = shy * chp;
        final double chy_chp = chy * chp;
        final double shy_shp = shy * shp;

        return new Quaternion(
                (chy_shp * chr) + (shy_chp * shr),
                (shy_chp * chr) - (chy_shp * shr),
                (chy_chp * shr) - (shy_shp * chr),
                (chy_chp * chr) + (shy_shp * shr));
    }

    public Quaternion normalize() {
        double l = this.len();
        if (l != 0 && l != 1) {
            return mult(1/l);
        }
        return this;
    }

    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion mult(final double x) {
        return new Quaternion(
                this.x * x,
                this.y * y,
                this.z * z,
                this.w * w);
    }

    public Quaternion mult(final Quaternion q) {
        if (q == Quaternion.ID) {
            return this;
        }
        return new Quaternion(
            this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y,
            this.w * q.y + this.y * q.w + this.z * q.x - this.x * q.z,
            this.w * q.z + this.z * q.w + this.x * q.y - this.y * q.x,
            this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z);
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
        return new Vector3(this.mult(from(v)).mult(conjugate()));
    }

    public static Quaternion lerp(final Quaternion a, final Quaternion b, final double i) {
        return lincomb(a, b, i, 1-i);
    }

    public static Quaternion nlerp(final Quaternion a, final Quaternion b, final double i) {
        return Quaternion.lerp(a,b,i).normalize();
    }

    public static Quaternion slerp(final Quaternion a, final Quaternion b, final double i) {
        final double angle = Math.acos(a.dot(b));
        final double is = 1.0/Math.sin(angle);
        return lincomb(a,b,Math.sin((1-i) * angle)*is,Math.sin((i*angle))*is);
    }

    private static Quaternion lincomb(final Quaternion a, final Quaternion b, final double i, final double j) {
        return new Quaternion(
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
        return ((this.x == q.x)
             && (this.y == q.y)
             && (this.z == q.z)
             && (this.w == q.w));
    }

    @Override
    public String toString() {
        return "quaternion< " + x + ", " + y + ", " + z + ", " + w + " >";
    }
}
