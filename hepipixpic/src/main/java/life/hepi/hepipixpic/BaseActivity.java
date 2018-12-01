package life.hepi.hepipixpic;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import life.hepi.hepipixpic.define.Define;
import life.hepi.hepipixpic.util.UiUtil;

public abstract class BaseActivity extends AppCompatActivity {
    protected Define define = new Define();
    protected UiUtil uiUtil = new UiUtil();
    protected Pixton pixton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        pixton = Pixton.getInstance();
    }
}