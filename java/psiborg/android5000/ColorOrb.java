package psiborg.android5000;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Color;
import psiborg.android5000.util.Vector3;

public class ColorOrb extends GameObject {
    private Vector3 pos;
    private Color col;
    public ColorOrb() {
        pos = Vector3.ZERO;
        col = Color.WHITE;
    }
    @Override
    public void step() {
        updatePos(System.currentTimeMillis() / 2000.0, 4, 2);
        ColorShader.setLightPosition(pos);
        ColorShader.setLightColor(col);
    }

    private void updatePos(double t, double r, double h) {
        pos = new Vector3(Math.cos(t) * r, Math.sin(t * 2) * h, Math.sin(t) * r);
        col = Color.fromHSV(((float)Math.cos(t/5.0)+1f)/2f, 1f, 1f);
    }
}
