package psiborg.android5000.base;

import psiborg.android5000.util.Quaternion;
import psiborg.android5000.util.Vector3;

public class Transform {
    public Vector3 position;
    public Quaternion rotation;
    public Transform() {
        position = new Vector3();
        rotation = new Quaternion();
    }
}
