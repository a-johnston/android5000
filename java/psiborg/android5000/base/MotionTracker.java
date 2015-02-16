package psiborg.android5000.base;

import android.view.MotionEvent;
import java.util.ArrayList;
import psiborg.android5000.util.Vector2;

public class MotionTracker {
    private static int maxPoints = 10;
    private static Vector2[] posCurrent = new Vector2[maxPoints], posPast = new Vector2[maxPoints];
    public static void motion(MotionEvent e) {
        switch (e.getAction()) {
            case (MotionEvent.ACTION_DOWN):
            case (MotionEvent.ACTION_POINTER_DOWN):
                //e.
                Vector2 temp = new Vector2(e.getX(), e.getY());

                break;
            case (MotionEvent.ACTION_MOVE):
                break;
            case (MotionEvent.ACTION_UP):
            case (MotionEvent.ACTION_POINTER_UP):
                break;
        }
    }
    private static Vector2 match(Vector2 t, double dist) {
        for (Vector2 p : posCurrent) {
            if (Vector2.distance(t,p) < dist) {
                return p;
            }
        }
        return null;
    }

}
