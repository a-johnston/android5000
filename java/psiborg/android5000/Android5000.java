package psiborg.android5000;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class Android5000 extends GLSurfaceView {
    public static AssetManager assetman;
    public Android5000(Context context){
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(new GameRenderer());
        assetman = context.getAssets();
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
    	SimpleMotion.motion(e);
    	return true;
    }
}
