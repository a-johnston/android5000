package psiborg.android5000.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import psiborg.android5000.GameEngine;
import psiborg.android5000.base.StaticFiles;

public class IO {
	public static Mesh loadObj(String filename) {
		Mesh mesh = new Mesh();
		ArrayList<Vector3> verts = new ArrayList<Vector3>();
		ArrayList<Vector3> cols  = new ArrayList<Vector3>();
		ArrayList<Integer> point = new ArrayList<Integer>();
		Scanner in = new Scanner(StaticFiles.load(filename));
		String s;
		while (in.hasNext() && (s = in.nextLine()) != null) {
			String[] data = s.split(" ");
			if (data[0].equals("v") == true) {
				mesh.points.add(new Vector3(
						Float.parseFloat(data[1]),
						Float.parseFloat(data[2]),
						Float.parseFloat(data[3])));
			} else  if (data[0].equals("c") == true) {
				mesh.color.add(new Color(
						Float.parseFloat(data[1]),
						Float.parseFloat(data[2]),
						Float.parseFloat(data[3]),1f));
			} else  if (data[0].equals("f") == true) {
				mesh.order.add(new IVector3(
						Integer.parseInt(data[1])-1,
						Integer.parseInt(data[2])-1,
						Integer.parseInt(data[3])-1));
			}
		}
		in.close();
		mesh.buildNormals();
		return mesh;
	}
    public static String readFile(String filename) {
        String data = "";
        try {
			BufferedReader in = new BufferedReader(new InputStreamReader(GameEngine.assetman.open(filename)));
            Scanner input = new Scanner(in);
            data = input.useDelimiter("\\A").next();
            input.close();
        } catch (FileNotFoundException e) {
            Log.e("android5k.IO", filename+" not found");
        } catch (IOException e) {
			Log.e("android5k.IO", filename + " IO Exception: " + e.getStackTrace());
		}
		return data;
    }
}
