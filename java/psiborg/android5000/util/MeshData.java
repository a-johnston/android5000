package psiborg.android5000.util;
public class MeshData {
	public float[] points;
	public float[] normals;
	public float[] color;
	public float[] uv;
	public int[]   order;
    public static Vector3[] getNormals(Vector3[] points, int[] order) {
        Vector3[] norms = new Vector3[points.length];
        for (int i=0; i<norms.length; i++) {
            norms[i] = new Vector3(0,0,0);
        }
        for (int i=0; i<order.length; i+=3) {
            Vector3 n = getNormal(points[order[i]],points[order[i+1]],points[order[i+2]]);
            norms[order[i]]   = Vector3.add(norms[order[i]],n);
            norms[order[i+1]] = Vector3.add(norms[order[i+1]],n);
            norms[order[i+2]] = Vector3.add(norms[order[i+2]],n);
        }
        for (int i=0; i<norms.length; i++) norms[i] = Vector3.norm(norms[i]);
        return norms;
    }
    public static Vector3 getNormal(Vector3 p1, Vector3 p2, Vector3 p3) {
        return Vector3.norm(Vector3.cross(Vector3.sub(p2,p1),Vector3.sub(p3,p1)));
    }
}
