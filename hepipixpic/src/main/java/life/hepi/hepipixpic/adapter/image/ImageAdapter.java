package life.hepi.hepipixpic.adapter.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

public interface ImageAdapter {
    void loadImage(Context context, ImageView target, Uri loadUrl);
    void loadDetailImage(Context context, ImageView target, Uri loadUrl);
}
