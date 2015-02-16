package psiborg.android5000.util;

public class Color {
    public static final Color black = new Color(0f,0f,0f,1f),
                              white = new Color(1f,1f,1f,1f),
                              red   = new Color(1f,0f,0f,1f),
                              green = new Color(0f,1f,0f,1f),
                              blue  = new Color(0f,0f,1f,1f);
    public float r, g, b, a;
    public Color() {
        this.set(Color.black);
    }
    public Color(final Color c) {
        this.set(c);
    }
    public Color(final float r, final float g, final float b) {
        this.set(r,g,b);
    }
    public Color(final float r, final float g, final float b, final float a) {
        this.set(r,g,b,a);
    }
    public Color set(final Color c) {
        this.r = c.r;
        this.g = c.g;
        this.b = c.b;
        this.a = c.a;
        return this;
    }
    public Color set(final float r, final float g, final float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1f;
        return this;
    }
    public Color set(final float r, final float g, final float b, final float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }
    public static Color lerp(final Color a, final Color b, final float i) {
        final float j = 1-i;
        return new Color(a.r*j + b.r*i,
                         a.g*j + b.g*i,
                         a.b*j + b.b*i,
                         a.a*j + b.a*i);
    }
}
