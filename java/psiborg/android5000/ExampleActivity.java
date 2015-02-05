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
        scene.add(new TouchCamera());
        scene.add(new Suzanne());
        gameView.setScene(scene);
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
