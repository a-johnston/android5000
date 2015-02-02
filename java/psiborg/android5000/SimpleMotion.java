package psiborg.android5000;

import android.view.MotionEvent;

public class SimpleMotion {
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
				TouchCamera.yaw   -= (px-x)/200;
				TouchCamera.pitch += (y-py)/200;
				px = x;
				py = y;
				break;
		}
	}
}
