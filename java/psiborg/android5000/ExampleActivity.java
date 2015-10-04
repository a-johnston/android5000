package psiborg.android5000;

import android.os.Bundle;
import android.app.Activity;

import psiborg.android5000.base.Scene;

public class ExampleActivity extends Activity {
    private Android5000 gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new Android5000(this);

        Scene scene = new Scene();

        scene.add(new AttachableCamera());
        for (int i = 0; i < 200; i++) {
            scene.add(new Cube());
        }
        scene.add(new Suzanne());
        scene.add(new ColorOrb());

        scene.get(AttachableCamera.class).attach(scene.get(Cube.class));

        gameView.setScene(scene);
        setContentView(R.layout.activity_main);
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
