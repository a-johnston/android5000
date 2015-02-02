package psiborg.android5000.util;

public class Quaternion {
    public float x, y, z, w;
    public Quaternion() {
        set(0,0,0,0);
    }
    public Quaternion(float x, float y, float z, float w) {
        set(x, y, z, w);
    }
    public Quaternion(Vector3 axis, float angle) {

    }
    public Quaternion set(float x, float y, float z, float w) {
        this.x = x;
        this.x = y;
        this.x = z;
        this.x = w;
        return this;
    }
    public Quaternion set(Vector3 axis, float angle) {

        return this;
    }
}
