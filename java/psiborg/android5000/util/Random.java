package psiborg.android5000.util;

public class Random {
    public static Vector3 vector() {
        return new Vector3(Math.random(), Math.random(), Math.random());
    }

    public static Color rgb() {
        return new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    public static Color rgba() {
        return new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    public static Vector3 unitPoint() {
        double y = Random.angle();
        double p = Random.angle()/2.0;
        return new Vector3(
                Math.cos(y) * Math.sin(p),
                Math.sin(y) * Math.sin(p),
                Math.cos(p));
    }

    public static Quaternion rotation() {
        return Quaternion.fromEulerAngles(Random.angle(), Random.angle(), Random.angle());
    }

    public static double angle() {
        return Math.random()*2.0*Math.PI;
    }
}
