package psiborg.android5000.util;

public class Color {
    public static final Color
            BLACK  = new Color(0f, 0f, 0f, 1f),
            WHITE  = new Color(1f, 1f, 1f, 1f),
            RED    = new Color(1f, 0f, 0f, 1f),
            GREEN  = new Color(0f, 1f, 0f, 1f),
            BLUE   = new Color(0f, 0f, 1f, 1f),
            YELLOW = new Color(1f, 1f, 0f, 1f),
            PURPLE = new Color(1f, 0f, 1f, 1f),
            CYAN   = new Color(0f, 1f, 1f, 1f),
            BLANK  = new Color(0f, 0f, 0f, 0f);    private float r, g, b, a;

    public Color() {
        this(Color.BLANK);
    }

    public Color(final Color c) {
        this(c.r, c.g, c.b, c.a);
    }

    public Color(final float r, final float g, final float b) {
        this(r, g, b, 1f);
    }

    public Color(final float r, final float g, final float b, final float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float getRed() {
        return r;
    }

    public float getGreen() {
        return g;
    }

    public float getBlue() {
        return b;
    }

    public float getAlpha() {
        return a;
    }

    public Color interpolate(final Color col, final float i) {
        final float j = 1-i;
        return new Color(r*j + col.r*i,
                         g*j + col.g*i,
                         b*j + col.b*i,
                         a*j + col.a*i);
    }

    public Color setRed(float red) {
        return new Color(red, g, b, a);
    }

    public Color setGreen(float green) {
        return new Color(r, green, b, a);
    }

    public Color setBlue(float blue) {
        return new Color(r, g, blue, a);
    }

    public Color setAlpha(float alpha) {
        return new Color(r, g, b, alpha);
    }

    public Color intensity(float i) {
        return new Color(r*i, g*i, b*i, a);
    }

    public float[] toRGBAFloatArray() {
        return new float[]{r,g,b,a};
    }
    public float[] toRGBFloatArray() {
        return new float[]{r,g,b};
    }
    public String toString() {
        return "color["+r+" , "+g+" , "+b+" , "+a+"]";
    }
    public static float[] toFloatArray(Color... a) {
        float[] r = new float[a.length*4];
        for (int i=0; i<a.length; i++) {
            r[i*4]   = a[i].r;
            r[i*4+1] = a[i].g;
            r[i*4+2] = a[i].b;
            r[i*4+3] = a[i].a;
        }
        return r;
    }

    public static Color fromHSV(float hue, float sat, float val) {
        int i;
        float f, p, q, t;

        if(sat == 0) {
            return new Color(val, val, val);
        }

        hue *= 6;
        i = (int)Math.floor(hue);
        f = hue - i;
        p = val * (1 - sat);
        q = val * (1 - sat * f);
        t = val * (1 - sat * (1 - f));

        switch(i) {
            case 0:
                return new Color(val, t, p);
            case 1:
                return new Color(q, val, p);
            case 2:
                return new Color(p, val, t);
            case 3:
                return new Color(p, q, val);
            case 4:
                return new Color(t, p, val);
        }
        return new Color(val, p, q);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Color)) {
            return false;
        }
        Color other = (Color) o;
        return r == other.r && g == other.g && b == other.b && a == other.a;
    }
}
