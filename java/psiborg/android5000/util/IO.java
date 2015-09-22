package psiborg.android5000.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Scanner;

import psiborg.android5000.GameEngine;

public class IO {
    public static Mesh loadObj(String filename) {
        Mesh mesh = new Mesh();
        Scanner in = new Scanner(readFile(filename));
        String s;
        while (in.hasNext() && (s = in.nextLine()) != null) {
            String[] data = s.split(" ");
            if (data[0].equals("v")) {
                mesh.addPoint(new Vector3(
                        Float.parseFloat(data[1]),
                        Float.parseFloat(data[2]),
                        Float.parseFloat(data[3])));
            } else  if (data[0].equals("c")) {
                mesh.addColor(new Color(
                        Float.parseFloat(data[1]),
                        Float.parseFloat(data[2]),
                        Float.parseFloat(data[3]),1f));
            } else  if (data[0].equals("f")) {
                mesh.addTriangle(new IVector3(
                        Integer.parseInt(data[1])-1,
                        Integer.parseInt(data[2])-1,
                        Integer.parseInt(data[3])-1));
                if (data.length == 5) {
                    mesh.addTriangle(new IVector3(
                            Integer.parseInt(data[1])-1,
                            Integer.parseInt(data[3])-1,
                            Integer.parseInt(data[4])-1));
                }
            }
        }
        in.close();
        mesh.buildNormals();
        return mesh;
    }

    public static Mesh loadObjAsync(String filename) {
        Mesh mesh = new Mesh();
        Scanner in = new Scanner(readFile(filename));
        String s;
        while (in.hasNext() && (s = in.nextLine()) != null) {
            String[] data = s.split(" ");
            if (data[0].equals("v")) {
                mesh.addPoint(new Vector3(
                        Float.parseFloat(data[1]),
                        Float.parseFloat(data[2]),
                        Float.parseFloat(data[3])));
            } else  if (data[0].equals("c")) {
                mesh.addColor(new Color(
                        Float.parseFloat(data[1]),
                        Float.parseFloat(data[2]),
                        Float.parseFloat(data[3]),1f));
            } else  if (data[0].equals("f")) {
                mesh.addTriangle(new IVector3(
                        Integer.parseInt(data[1])-1,
                        Integer.parseInt(data[2])-1,
                        Integer.parseInt(data[3])-1));
                if (data.length == 5) {
                    mesh.addTriangle(new IVector3(
                            Integer.parseInt(data[1])-1,
                            Integer.parseInt(data[3])-1,
                            Integer.parseInt(data[4])-1));
                }
            }
        }
        in.close();
        mesh.buildNormals();
        return mesh;
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
