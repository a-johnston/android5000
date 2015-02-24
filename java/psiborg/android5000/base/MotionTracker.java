package psiborg.android5000.base;

import android.view.MotionEvent;
import java.util.ArrayList;
import psiborg.android5000.util.Vector2;

public class MotionTracker {
    private static int maxPoints = 10;
    private static Vector2[] pos = new Vector2[maxPoints];
    public static void motion(MotionEvent e) {
        int i = e.getActionIndex();
        switch (e.getAction()) {
            case (MotionEvent.ACTION_DOWN):
            case (MotionEvent.ACTION_POINTER_DOWN):
                pos[i] = new Vector2(e.getX(), e.getY());
                break;
            case (MotionEvent.ACTION_MOVE):
                pos[i].set(e.getX(), e.getY());
                break;
            case (MotionEvent.ACTION_UP):
            case (MotionEvent.ACTION_POINTER_UP):
                break;
        }
    }
}
