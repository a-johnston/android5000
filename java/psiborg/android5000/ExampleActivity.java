package psiborg.android5000;

import android.os.Bundle;
import android.app.Activity;

import psiborg.android5000.base.Scene;

public class ExampleActivity extends Activity {
    private ExampleGame gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new ExampleGame(this);
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
