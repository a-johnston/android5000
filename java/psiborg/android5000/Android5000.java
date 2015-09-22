package psiborg.android5000;

import android.app.Activity;
import android.opengl.GLSurfaceView;
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

    public void setScene(final Scene scene) {
        renderer.doWhenReady(new Runnable() {
            @Override
            public void run() {
                renderer.setScene(scene);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        SimpleMotion.motion(e);
        return true;
    }
}