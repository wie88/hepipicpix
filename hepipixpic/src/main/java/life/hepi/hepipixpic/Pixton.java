package life.hepi.hepipixpic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

import life.hepi.hepipixpic.adapter.image.ImageAdapter;

public class Pixton {

    private volatile static Pixton instance;
    public ImageAdapter imageAdapter;
    public Uri[] pickerImages;

    //BaseParams
    public int maxCount;
    public int minCount;
    public boolean isExceptGif;
    public ArrayList<Uri> selectedImages = new ArrayList<>();
    public Uri editImageDestination;
    public Uri cameraImageDestination;

    //CustomizationParams
    public int photoSpanCount;
    public int albumPortraitSpanCount;
    public int albumLandscapeSpanCount;

    public boolean isAutomaticClose;
    public boolean isButton;

    public int colorActionBar;
    public int colorActionBarTitle;
    public int colorStatusBar;

    public boolean isStatusBarLight;
    public boolean isCamera;

    public int albumThumbnailSize;

    public String messageNothingSelected;
    public String messageLimitReached;
    public String titleAlbumAllView;
    public String titleActionBar;

    public Boolean isEditorEnable;

    public Drawable drawableHomeAsUpIndicator;
    public Drawable drawableOkButton;

    public String strTextMenu;

    public int colorTextMenu;

    public boolean isUseDetailView;


    private Pixton() {
        init();
    }

    public static Pixton getNewInstance() {
        getInstance();
        instance.init();
        return instance;
    }

    public static Pixton getInstance() {
        if (instance == null) {
            synchronized (Pixton.class) {
                if (instance == null) {
                    instance = new Pixton();
                }
            }
        }
        return instance;
    }


    private void init() {
        //BaseParams
        maxCount = 1;
        minCount = 1;
        isExceptGif = true;
        isEditorEnable = true;
        selectedImages = new ArrayList<>();

        //CustomizationParams
        photoSpanCount = 3;
        albumPortraitSpanCount = 1;
        albumLandscapeSpanCount = 2;

        isAutomaticClose = false;
        isButton = false;

        colorActionBar = Color.parseColor("#3F51B5");
        colorActionBarTitle = Color.parseColor("#ffffff");
        colorStatusBar = Color.parseColor("#303F9F");

        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {

            File hepimamaFolder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + "Hepimama");
            if(!hepimamaFolder.exists()) {
                hepimamaFolder.mkdir();
            }
            File podcastFolder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + "Hepimama" + File.separator + "Photo");
            if(!podcastFolder.exists()) {
                podcastFolder.mkdir();
            }
            File editPhoto = new File(Environment.getExternalStorageDirectory() + File.separator
                    + "Hepimama" + File.separator + "Edit");
            if(!editPhoto.exists()) {
                editPhoto.mkdir();
            }

            cameraImageDestination = new Uri.Builder().path(podcastFolder.getPath()).build();
            editImageDestination = new Uri.Builder().path(editPhoto.getPath()).build();
        }

        isStatusBarLight = false;
        isCamera = false;

        albumThumbnailSize = Integer.MAX_VALUE;

        colorTextMenu = Integer.MAX_VALUE;

        isUseDetailView = true;

    }

    void setDefaultMessage(Context context) {
        if (messageNothingSelected == null)
            messageNothingSelected = context.getResources().getString(R.string.msg_no_selected);

        if (messageLimitReached == null)
            messageLimitReached = context.getResources().getString(R.string.msg_full_image);

        if (titleAlbumAllView == null)
            titleAlbumAllView = context.getResources().getString(R.string.str_all_view);

        if (titleActionBar == null)
            titleActionBar = context.getResources().getString(R.string.album);
    }

    void setMenuTextColor() {
        if (drawableOkButton != null
                || strTextMenu == null
                || colorTextMenu != Integer.MAX_VALUE)
            return;

        if (isStatusBarLight)
            colorTextMenu = Color.BLACK;
        else
            colorTextMenu = Color.WHITE;
    }

    void setDefaultDimen(Context context) {
        if (albumThumbnailSize == Integer.MAX_VALUE)
            albumThumbnailSize = (int) context.getResources().getDimension(R.dimen.album_thum_size);
    }

    public static void release() {
        instance = null;
    }
}
