package psiborg.android5000;

import android.view.MotionEvent;

import psiborg.android5000.base.MotionWrapper;

public class SimpleMotion extends MotionWrapper {
	private float px, py;
	public void motion(MotionEvent e) {
		switch (e.getAction()) {
			case (MotionEvent.ACTION_DOWN):
				px = e.getX();
				py = e.getY();
				break;
			case (MotionEvent.ACTION_MOVE):
				float x = e.getX();
				float y = e.getY();
				GameRenderer.yaw   -= (px-x)/200;
				GameRenderer.pitch += (y-py)/200;
				px = x;
				py = y;
				break;
		}
	}
}
