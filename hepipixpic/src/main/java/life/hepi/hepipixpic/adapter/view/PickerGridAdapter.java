package life.hepi.hepipixpic.adapter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;

import java.io.File;

import life.hepi.hepipixpic.Pixton;
import life.hepi.hepipixpic.R;
import life.hepi.hepipixpic.ui.editor.ImageEditorActivity;

public class PickerGridAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = Integer.MIN_VALUE;

    private Pixton fishton;
    private OnPhotoActionListener actionListener;


    private String saveDir;


    public PickerGridAdapter(String saveDir) {
        this.saveDir = saveDir;
        this.fishton = Pixton.getInstance();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thumb_item, parent, false);
        return new ViewHolderImage(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolderImage) {
            final int imagePos;

            imagePos = position;

            final ViewHolderImage vh = (ViewHolderImage) holder;
            final Uri image = fishton.pickerImages[imagePos];
            final Context context = vh.item.getContext();
            vh.item.setTag(image);

            initState(fishton.selectedImages.indexOf(image), vh);
            if (image != null)
                Pixton.getInstance().imageAdapter
                        .loadImage(vh.imgThumbImage.getContext(),
                                vh.imgThumbImage,
                                image);
            vh.imgThumbImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fishton.maxCount == 1) {
                        if(fishton.isEditorEnable) {
                            Intent intent = new Intent();
                            intent.setClass(context, ImageEditorActivity.class);
                            intent.putExtra("IMAGE", image);
                            ((Activity)context).startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    private void initState(int selectedIndex, ViewHolderImage vh) {
        if (selectedIndex != -1) {
            animScale(vh.imgThumbImage, true, false);
        } else {
            animScale(vh.imgThumbImage, false, false);
        }
    }

    private void animScale(View view,
                           final boolean isSelected,
                           final boolean isAnimation) {
        int duration = 200;
        if (!isAnimation) duration = 0;
        float toScale;
        if (isSelected)
            toScale = .8f;
        else
            toScale = 1.0f;

        ViewCompat.animate(view)
                .setDuration(duration)
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {

                    }
                })
                .scaleX(toScale)
                .scaleY(toScale)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        if (isAnimation && !isSelected) actionListener.onDeselect();
                    }
                })
                .start();

    }

    @Override
    public int getItemCount() {
        int count;
        if (fishton.pickerImages == null) count = 0;
        else count = fishton.pickerImages.length;

        if (fishton.isCamera)
            return count + 1;

        if (fishton.pickerImages == null) return 0;
        else return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && fishton.isCamera) {
            return TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }

    public void setActionListener(OnPhotoActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface OnPhotoActionListener {
        void onDeselect();
    }

    public class ViewHolderImage extends RecyclerView.ViewHolder {


        View item;
        ImageView imgThumbImage;

        public ViewHolderImage(View view) {
            super(view);
            item = view;
            imgThumbImage = view.findViewById(R.id.img_thumb_image);
        }
    }
}