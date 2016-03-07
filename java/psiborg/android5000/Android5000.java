package psiborg.android5000;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import psiborg.android5000.base.Scene;

public class Android5000 extends GLSurfaceView {
    private GameEngine renderer;

    public Android5000(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        renderer = new GameEngine(context);
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
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}