package psiborg.android5000.util;

public class IVector3 {
    public int x, y, z;

    public IVector3() {
        this(0, 0, 0);
    }

    public IVector3(IVector3 v) {
        this(v.x, v.y, v.z);
    }

	public IVector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
	}

    public IVector3 mult(int n) {
        return new IVector3(x*n, y*n, z*n);
    }

    public IVector3 mult(IVector3 v) {
        return new IVector3(x*v.x, y*v.y, z*v.z);
    }

    public IVector3 plus(IVector3 v) {
        return new IVector3(x+v.x, y+v.y, z+v.z);
    }

    public IVector3 minus(IVector3 v) {
        return new IVector3(x-v.x, y-v.y, z-v.z);
    }

    public int dot(IVector3 v) {
        return x*v.x + y*v.y + z*v.z;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IVector3)) {
            return false;
        }
        IVector3 other = (IVector3) o;
        return x == other.x && y == other.y && z == other.z;
    }
}