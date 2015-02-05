package psiborg.android5000;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.Scene;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class GameEngine implements GLSurfaceView.Renderer {
    public static double time, deltatime;
    public static AssetManager assetman;
    public static float aspect;
	private Scene scene, nextScene;
    private boolean scenelock;
	private static double firstTime, stepTime, drawTime;
	public GameEngine(Context context) {
        assetman = context.getAssets();
        scenelock = false;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
    	GLES20.glClearColor(0f, 0f, 0f, 1.0f);
    	GLES20.glEnable(GLES20.GL_CULL_FACE);
    	GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    	scene = null;
        firstTime = System.currentTimeMillis()/1000.0;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
    	GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    	GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
        if (!scenelock && (nextScene != null)) {
            if (scene != null) {
                scene.unload();
            }
            scene = nextScene;
            scene.load();
            nextScene = null;
        }
    	if (scene != null) {
            double currentTime = (System.currentTimeMillis()/1000.0)- firstTime;
            deltatime = currentTime-time;
            time      = currentTime;
            scene.step();
            stepTime = ((System.currentTimeMillis()/1000.0)- firstTime) - currentTime;
            if (Camera.active != null) {
                scene.draw(Camera.active.getMVP());
            }
            drawTime = ((System.currentTimeMillis()/1000.0)- firstTime) - stepTime;
        }
    }
    public boolean setScene(Scene scene) {
        if (scenelock || (nextScene != null)) {
            return false;
        }
        scenelock = true;
        nextScene = scene;
        scenelock = false;
        return true;
    }
    public static double getFPS() {
        return 1.0/deltatime;
    }
    public static double getStepTime() {
        return stepTime;
    }
    public static double getDrawTime() {
        return drawTime;
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        aspect = (float) width / height;
        if (Camera.active != null) {
            Camera.active.updateAspectRatio();
        }
    }
}