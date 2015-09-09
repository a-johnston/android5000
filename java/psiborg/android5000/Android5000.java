package psiborg.android5000;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import psiborg.android5000.base.Scene;

public class Android5000 extends GLSurfaceView {
    private GameEngine renderer;
    public Android5000(Activity activity){
        super(activity);
        setEGLContextClientVersion(2);
        renderer = new GameEngine(activity);
        setRenderer(renderer);
    }

    public void setScene(Scene scene) {
        while (!renderer.setScene(scene)) {
            Log.w("scene", "failed to change scenes. reattempting scene change...");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        SimpleMotion.motion(e);
        return true;
    }
}