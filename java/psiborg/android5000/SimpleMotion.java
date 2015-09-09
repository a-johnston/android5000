package psiborg.android5000;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class SimpleMotion {
    public interface SimpleTouchListener {
        void accept(float dx, float dy);
    }
    private static List<SimpleTouchListener> listeners;
	private static float px, py;
	public static void motion(MotionEvent e) {
		switch (e.getAction()) {
            case (MotionEvent.ACTION_DOWN):
				px = e.getX();
				py = e.getY();
				break;
			case (MotionEvent.ACTION_MOVE):
				float x = e.getX();
				float y = e.getY();
                float dx = x - px;
                float dy = y - py;
                if (listeners != null) {
                    for (SimpleTouchListener listener : listeners) {
                        listener.accept(dx, dy);
                    }
                }
				px = x;
				py = y;
				break;
		}
	}

    public static void addListener(SimpleTouchListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public static void removeListener(SimpleTouchListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
        }
    }
}
