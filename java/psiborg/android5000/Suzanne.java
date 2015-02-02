package psiborg.android5000;

import psiborg.android5000.base.Object;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.MeshData;

public class Suzanne extends Object {
	public Suzanne() {
		MeshData obj = IO.loadObj("suzanne.obj");
		shader = new ColorShader(obj);
	}
}
