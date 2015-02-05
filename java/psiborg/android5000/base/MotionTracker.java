package psiborg.android5000.base;

import android.view.MotionEvent;
import java.util.ArrayList;
import psiborg.android5000.util.Vector2;

public class MotionTracker {
    private static ArrayList<Vector2> points = new ArrayList<Vector2>();
    private double threshold = 5;
    public static void motion(MotionEvent e) {
        switch (e.getAction()) {
            case (MotionEvent.ACTION_DOWN):
                Vector2 temp = new Vector2(e.getX(), e.getY());
                break;
            case (MotionEvent.ACTION_MOVE):
                break;
            case (MotionEvent.ACTION_UP):
                break;
        }
    }
    private static int match(Vector2 t) {
        for (Vector2 t : points) {
            if ()
        }
    }
}
