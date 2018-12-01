package life.hepi.hepipixpic;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

import life.hepi.hepipixpic.adapter.image.ImageAdapter;

public final class PixPic {

    protected WeakReference<Activity> activity = null;
    protected WeakReference<Fragment> fragment = null;


    public static PixPic with(Activity activity) {
        return new PixPic(activity, null);
    }

    public static PixPic with(Fragment fragment) {
        return new PixPic(null, fragment);
    }

    PixPic(Activity activity, Fragment fragment) {
        this.activity = new WeakReference<>(activity);
        this.fragment = new WeakReference<>(fragment);
    }

    public PixPicCreator setImageAdapter(ImageAdapter imageAdapter) {
        Pixton fishton = Pixton.getNewInstance();
        fishton.imageAdapter = imageAdapter;
        return new PixPicCreator(this);
    }
}