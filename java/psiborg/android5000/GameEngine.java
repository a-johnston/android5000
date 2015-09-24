package psiborg.android5000;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.Scene;
import psiborg.android5000.util.WaitOnReadyQueue;

public final class GameEngine implements GLSurfaceView.Renderer {
    private static double time, deltaTime;
    private static AssetManager assets;
    private static float aspect;
    private static double firstTime, stepTime, drawTime;
    private static GameEngine currentEngine;

    private WaitOnReadyQueue waitOnReady;
    private Scene scene;

	public GameEngine(Context context) {
        assets = context.getAssets();
        waitOnReady = new WaitOnReadyQueue();
        currentEngine = this;
    }

    public void doWhenReady(Runnable r) {
        waitOnReady.doWhenReady(r);
    }

    public static GameEngine getCurrentEngine() {
        return currentEngine;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
    	GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    	GLES20.glEnable(GLES20.GL_CULL_FACE);
    	GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    	scene = null;
        firstTime = System.currentTimeMillis()/1000.0;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        waitOnReady.runQueuedEvents();
    	GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    	GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
    	if (scene != null) {
            double currentTime = (System.currentTimeMillis()/1000.0)- firstTime;
            deltaTime = currentTime-time;
            time      = currentTime;
            scene.step();
            stepTime = ((System.currentTimeMillis()/1000.0)- firstTime) - currentTime;
            if (Camera.getActive() != null) {
                scene.draw();
            }
            drawTime = ((System.currentTimeMillis()/1000.0)- firstTime) - stepTime;
        }
    }

    public void setScene(Scene scene) {
        if (this.scene != null) {
            this.scene.unload();
        }
        this.scene = scene;
        if (this.scene != null) {
            this.scene.load();
        }
    }

    public static double getFPS() {
        return 1.0/ deltaTime;
    }

    public static double getStepTime() {
        return stepTime;
    }

    public static double getDrawTime() {
        return drawTime;
    }

    public static float getAspect() {
        return aspect;
    }

    public static AssetManager getAssets() {
        return assets;
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        aspect = (float) width / height;
        if (Camera.getActive() != null) {
            Camera.getActive().updateAspectRatio();
        }
    }
}