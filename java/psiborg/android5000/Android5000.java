package psiborg.android5000;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import psiborg.android5000.base.Scene;
import psiborg.android5000.util.Consumer;
import psiborg.android5000.util.Meshes;

public class Android5000 extends GLSurfaceView {
    public class TouchEvent {
        final MotionEvent source;

        public TouchEvent(MotionEvent source) {
            this.source = source;
        }
    }

    private GameEngine renderer;
    private List<Consumer<TouchEvent>> listeners;
    public Android5000(Activity activity){
        super(activity);
        setEGLContextClientVersion(2);
        renderer = new GameEngine(activity);
        listeners = new ArrayList<>();
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

    public void addTouchListener(Consumer<TouchEvent> listener) {
        listeners.add(listener);
    }

    public void removeTouchListener(Consumer<TouchEvent> listener) {
        listeners.remove(listener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        Meshes.forgetVBOs();
        ColorShader.forgetProgram();
    }

    @Override
    public void onResume() {
        super.onResume();
        final Scene s = renderer.getScene();
        if (s != null) {
            renderer.doWhenReady(new Runnable() {
                @Override
                public void run() {
                    s.unload();
                    renderer.setScene(s);
                }
            });
        }
    }
}