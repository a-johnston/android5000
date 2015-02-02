package psiborg.android5000.util;


public class Point {
	public Vector3 vertex;
	public Vector3 normal;
	public Vector3 color;
	public Vector2 uv;
	public static Vector3[] getVerticies(Point[] a) {
		Vector3[] r = new Vector3[a.length];
		for (int i=0; i<a.length; i++) r[i] = a[i].vertex;
		return r;
	}
	public static Vector3[] getNormals(Point[] a) {
		Vector3[] r = new Vector3[a.length];
		for (int i=0; i<a.length; i++) r[i] = a[i].normal;
		return r;
	}
	public static Vector3[] getColors(Point[] a) {
		Vector3[] r = new Vector3[a.length];
		for (int i=0; i<a.length; i++) r[i] = a[i].color;
		return r;
	}
	public static Vector2[] getUVs(Point[] a) {
		Vector2[] r = new Vector2[a.length];
		for (int i=0; i<a.length; i++) r[i] = a[i].uv;
		return r;
	}
}
