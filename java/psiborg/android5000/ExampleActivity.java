package psiborg.android5000;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;

import psiborg.android5000.base.Scene;

public class ExampleActivity extends Activity {
    private GLSurfaceView gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Scene scene = new Scene();
        scene.add(new Suzanne());

        gameView = new Android5000(this);
        setContentView(gameView);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.onResume();
    }
}
