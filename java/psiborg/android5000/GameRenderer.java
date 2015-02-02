package psiborg.android5000;

import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import psiborg.android5000.base.Object;
import psiborg.android5000.base.Scene;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class GameRenderer implements GLSurfaceView.Renderer {
	public static Camera camera = null;
	private Scene scene;
	
	private float time;
	
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
    	GLES20.glClearColor(0f, 0f, 0f, 1.0f);
    	GLES20.glEnable(GLES20.GL_CULL_FACE);
    	GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    	
    	scene = null;
    	
    	camera = new Camera(
    			new float[]{3.0f,  3.0f,  3.0f},
    			new float[]{0.0f,  0.0f,  0.0f},
    			new float[]{0.0f,  0.0f,  1.0f},
    			16f/9f,1f,10f);
    	
        time = 0;
    }
    
    public static void panic(float red, float green, float blue) {
    	GLES20.glClearColor(red, green, blue, 1.0f);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
    	GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    	GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
    	
    	pitch = (float)(Math.max(Math.min(pitch, Math.PI/2-.001),-Math.PI/2+.001));
    	
    	camera.updateLook(
    			new float[]{ (float)(Math.cos(yaw)*Math.cos(pitch)*2.5f), (float)(Math.sin(pitch)*2.5f), (float)(Math.sin(yaw)*Math.cos(pitch)*2.5f) },
    			new float[]{ 0f , 0f , 0f },
    			new float[]{ 0f , 1f , 0f });
    	ColorShader.lightDir = new float[]{ (float)(Math.cos(time)*3), 0, (float)(Math.sin(time)*3) };
    	time += 0.02f;
    	
        for (Object o : list) {
        	o.shader.draw(camera.getMVP());
        }
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        camera.updatePer(ratio, 1f, 100f);
    }
}