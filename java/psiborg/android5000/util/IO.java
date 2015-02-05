package psiborg.android5000.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import psiborg.android5000.GameEngine;

public class IO {
	public static MeshData loadObj(String filename) {
		MeshData mesh = new MeshData();
		ArrayList<Vector3> verts = new ArrayList<Vector3>();
		ArrayList<Vector3> cols  = new ArrayList<Vector3>();
		ArrayList<Integer> point = new ArrayList<Integer>();
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(GameEngine.assetman.open(filename)));
			String s = in.readLine();
			while (s != null) {
				String[] data = s.split(" ");
				if (data[0].equals("v") == true) {
					verts.add(new Vector3(Float.parseFloat(data[1]),
										  Float.parseFloat(data[2]),
										  Float.parseFloat(data[3])));
				} else  if (data[0].equals("c") == true) {
					cols.add(new Vector3(Float.parseFloat(data[1]),
										 Float.parseFloat(data[2]),
										 Float.parseFloat(data[3])));
				} else  if (data[0].equals("f") == true) {
					point.add(Integer.parseInt(data[1])-1);
					point.add(Integer.parseInt(data[2])-1);
					point.add(Integer.parseInt(data[3])-1);
				}
				s = in.readLine();
			}
			in.close();
			
		} catch (IOException e) {
            Log.e("loadObj","Could not open file: "+filename);
        }
		mesh.order   = new int[point.size()];
		int i=0;
		
		for (Integer f : point) mesh.order[i++] = (short)(int)f;
		mesh.points  = Vector3.toFloatArray(verts.toArray(new Vector3[verts.size()]));
		//mesh.color   = Vector3.toFloatArray(cols.toArray(new Vector3[verts.size()]));
		mesh.normals = Vector3.toFloatArray(MeshData.getNormals(verts.toArray(new Vector3[verts.size()]), mesh.order));
		
		mesh.color = new float[mesh.points.length];
		Random rand = new Random();
		for (i=0; i<mesh.color.length; i++) {
			//mesh.color[i] = rand.nextFloat();
			mesh.color[i] = (mesh.points[i]+1f)/2f;
			//mesh.color[i] = 1.0f;
		}
		
		return mesh;
	}
	public static String readFile(String filename) {
		String r = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(GameEngine.assetman.open(filename)));
			String s = in.readLine();
			while (s != null) {
				r += s + "\n";
				s = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			//nothing doing
		}
		return r;
	}
}
