package psiborg.android5000;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import psiborg.android5000.base.Scene;

public class ExampleGame extends GLSurfaceView {
    private Android5000 renderer;
    public ExampleGame(Context context){
        super(context);
        setEGLContextClientVersion(2);
        renderer = new Android5000(context);

        Scene scene = new Scene();
        scene.add(new TouchCamera());
        scene.add(new Suzanne());
        renderer.setScene(scene);

        setRenderer(renderer);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
    	SimpleMotion.motion(e);
    	return true;
    }
}
