package psiborg.android5000.util;

public class IVector3 {
	public int x, y, z;
	public IVector3(int xi, int yi, int zi) {
		x = xi; y = yi; z = zi;
	}
	public static float dot(IVector3 v1, IVector3 v2) {
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
	}
	public static IVector3 mult(IVector3 v, int s) {
		return new IVector3(v.x*s, v.y*s, v.z*s);
	}
	public static IVector3 mult(IVector3 v1, IVector3 v2) {
		return new IVector3(v1.x*v2.x, v1.y*v2.y, v1.z*v2.z);
	}
	public static IVector3 sub(IVector3 v1, IVector3 v2) {
		return new IVector3(v1.x-v2.x , v1.y-v2.y , v1.z-v2.z);
	}
	public static IVector3 add(IVector3 v1, IVector3 v2) {
		return new IVector3(v1.x+v2.x , v1.y+v2.y , v1.z+v2.z);
	}
	public static int[] toIntArray(IVector3 v) {
		return new int[]{v.x,v.y,v.z};
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