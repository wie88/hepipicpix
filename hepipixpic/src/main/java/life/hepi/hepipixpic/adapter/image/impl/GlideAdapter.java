package life.hepi.hepipixpic.adapter.image.impl;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import life.hepi.hepipixpic.adapter.image.ImageAdapter;

public class GlideAdapter implements ImageAdapter {

    @Override
    public void loadImage(Context context,
                          ImageView target,
                          Uri loadUrl) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide
                .with(context)
                .load(loadUrl)
                .apply(options)
                .into(target);
    }

    @Override
    public void loadDetailImage(Context context, ImageView target, Uri loadUrl) {
        RequestOptions options = new RequestOptions();
        options.centerInside();
        Glide
                .with(context)
                .load(loadUrl)
                .apply(options)
                .into(target);
    }

}