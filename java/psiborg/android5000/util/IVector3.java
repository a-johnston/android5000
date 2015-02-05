package psiborg.android5000.util;

public class IVector3 {
	public int x, y, z;
    public IVector3() {
        this.set(0,0,0);
    }
    public IVector3(IVector3 v) {
        this.set(v.x, v.y, v.z);
    }
	public IVector3(int x, int y, int z) {
		this.set(x,y,z);
	}
    public IVector3 set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    public IVector3 mult(int n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        return this;
    }
    public IVector3 mult(IVector3 v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        return this;
    }
    public IVector3 add(IVector3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }
    public IVector3 sub(IVector3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }
    public int dot(IVector3 v) {
        return x*v.x + y*v.y + z*v.z;
    }
	public static float dot(IVector3 v1, IVector3 v2) {
		return v1.dot(v2);
	}
	public static IVector3 mult(IVector3 v, int n) {
		return new IVector3(v).mult(n);
	}
	public static IVector3 mult(IVector3 v1, IVector3 v2) {
		return new IVector3(v1).mult(v2);
	}
    public static IVector3 add(IVector3 v1, IVector3 v2) {
        return new IVector3(v1).add(v2);
    }
	public static IVector3 sub(IVector3 v1, IVector3 v2) {
        return new IVector3(v1).sub(v2);
	}
	public static int[] toIntArray(IVector3[] a) {
		int[] r = new int[a.length*3];
		for (int i=0; i<a.length; i++) {
			r[i*3]   = a[i].x;
			r[i*3+1] = a[i].y;
			r[i*3+2] = a[i].z;
		}
		return r;
	}
	public static short[] toShortArray(IVector3[] a) {
		short[] r = new short[a.length*3];
		for (int i=0; i<a.length; i++) {
			r[i*3]   = (short)a[i].x;
			r[i*3+1] = (short)a[i].y;
			r[i*3+2] = (short)a[i].z;
		}
		return r;
	}
	public int[] toIntArray() {
		return new int[]{x,y,z};
	}
}