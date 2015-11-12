package psiborg.android5000.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Scanner;

import psiborg.android5000.GameEngine;

public class IO {
    public static Mesh loadObj(final String filename) {
        final Mesh mesh = new Mesh();
        buildMesh(mesh, readFile(filename));
        mesh.buildNormals();
        return mesh;
    }

    public static Mesh loadObjAsync(final String filename) {
        final Mesh mesh = new Mesh();
        final Runnable loader = new Runnable() {
            @Override
            public void run() {
                buildMesh(mesh, readFile(filename));
                mesh.buildNormals();
            }
        };
        new Thread(loader).start();
        return mesh;
    }

    private static void buildMesh(Mesh mesh, String data) {
        Scanner in = new Scanner(data);
        String s;
        while (in.hasNext() && (s = in.nextLine()) != null) {
            String[] lineData = s.split(" ");
            if (lineData[0].equals("v")) {
                mesh.addPoint(new Vector3(
                        Float.parseFloat(lineData[1]),
                        Float.parseFloat(lineData[2]),
                        Float.parseFloat(lineData[3])));
            } else  if (lineData[0].equals("c")) {
                mesh.addColor(new Color(
                        Float.parseFloat(lineData[1]),
                        Float.parseFloat(lineData[2]),
                        Float.parseFloat(lineData[3]),1f));
            } else  if (lineData[0].equals("f")) {
                mesh.addTriangle(new IVector3(
                        Integer.parseInt(lineData[1])-1,
                        Integer.parseInt(lineData[2])-1,
                        Integer.parseInt(lineData[3])-1));
                if (lineData.length == 5) {
                    mesh.addTriangle(new IVector3(
                            Integer.parseInt(lineData[1])-1,
                            Integer.parseInt(lineData[3])-1,
                            Integer.parseInt(lineData[4])-1));
                }
            }
        }
        in.close();
        mesh.setReady();
    }

    public static String readFile(String filename) {
        String data = "";
        try {
			BufferedReader in = new BufferedReader(new InputStreamReader(GameEngine.getAssets().open(filename)));
            Scanner input = new Scanner(in);
            data = input.useDelimiter("\\A").next();
            input.close();
        } catch (FileNotFoundException e) {
            Log.e("android5k.IO", filename+" not found", e);
        } catch (IOException e) {
			Log.e("android5k.IO", filename + " IO Exception: ", e);
		}
		return data;
    }
}
