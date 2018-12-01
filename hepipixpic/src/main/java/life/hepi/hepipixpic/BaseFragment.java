package life.hepi.hepipixpic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import life.hepi.hepipixpic.define.Define;
import life.hepi.hepipixpic.util.UiUtil;

public class BaseFragment extends Fragment {

    protected Define define = new Define();
    protected UiUtil uiUtil = new UiUtil();
    protected Pixton pixton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pixton = Pixton.getInstance();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}