package life.hepi.hepipixpic;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.util.ArrayList;

public final class PixPicCreator implements BaseProperty, CustomizationProperty {

    private PixPic fishBun;
    private Pixton fishton;

    private int requestCode = 27;


    PixPicCreator(PixPic fishBun) {
        this.fishBun = fishBun;
        this.fishton = Pixton.getInstance();
    }

    public PixPicCreator setSelectedImages(ArrayList<Uri> selectedImages) {
        fishton.selectedImages = selectedImages;
        return this;
    }

    public PixPicCreator setAlbumThumbnailSize(int size) {
        fishton.albumThumbnailSize = size;
        return this;
    }

    @Override
    public PixPicCreator setPickerSpanCount(int spanCount) {
        if (spanCount <= 0)
            spanCount = 3;

        fishton.photoSpanCount = spanCount;
        return this;

    }

    @Deprecated
    @Override
    public PixPicCreator setPickerCount(int count) {
        if (count <= 0)
            count = 1;

        fishton.maxCount = count;

        return this;
    }

    @Override
    public PixPicCreator setMaxCount(int count) {
        if (count <= 0)
            count = 1;

        fishton.maxCount = count;
        return this;
    }

    @Override
    public PixPicCreator setMinCount(int count) {
        if (count <= 0)
            count = 1;
        fishton.minCount = count;
        return this;
    }

    @Override
    public PixPicCreator setActionBarColor(int actionbarColor) {
        fishton.colorActionBar = actionbarColor;
        return this;
    }

    @Override
    public PixPicCreator setActionBarTitleColor(int actionbarTitleColor) {
        fishton.colorActionBarTitle = actionbarTitleColor;
        return this;
    }

    @Override
    public PixPicCreator setActionBarColor(int actionbarColor, int statusBarColor) {
        fishton.colorActionBar = actionbarColor;
        fishton.colorStatusBar = statusBarColor;
        return this;
    }

    @Override
    public PixPicCreator setActionBarColor(int actionbarColor, int statusBarColor, boolean isStatusBarLight) {
        fishton.colorActionBar = actionbarColor;
        fishton.colorStatusBar = statusBarColor;
        fishton.isStatusBarLight = isStatusBarLight;
        return this;
    }

    @Override
    public PixPicCreator setCamera(boolean isCamera) {
        fishton.isCamera = isCamera;
        return this;
    }

    @Override
    public PixPicCreator setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    @Override
    public PixPicCreator textOnNothingSelected(String message) {
        fishton.messageNothingSelected = message;
        return this;
    }

    @Override
    public PixPicCreator textOnImagesSelectionLimitReached(String message) {
        fishton.messageLimitReached = message;
        return this;
    }

    @Override
    public PixPicCreator setButtonInAlbumActivity(boolean isButton) {
        fishton.isButton = isButton;
        return this;
    }

    @Override
    public PixPicCreator setReachLimitAutomaticClose(boolean isAutomaticClose) {
        fishton.isAutomaticClose = isAutomaticClose;
        return this;
    }

    @Override
    public PixPicCreator setAlbumSpanCount(int portraitSpanCount, int landscapeSpanCount) {
        fishton.albumPortraitSpanCount = portraitSpanCount;
        fishton.albumLandscapeSpanCount = landscapeSpanCount;
        return this;
    }

    @Override
    public PixPicCreator setAlbumSpanCountOnlyLandscape(int landscapeSpanCount) {
        fishton.albumLandscapeSpanCount = landscapeSpanCount;
        return this;
    }

    @Override
    public PixPicCreator setAlbumSpanCountOnlPortrait(int portraitSpanCount) {
        fishton.albumPortraitSpanCount = portraitSpanCount;
        return this;
    }

    @Override
    public PixPicCreator setAllViewTitle(String allViewTitle) {
        fishton.titleAlbumAllView = allViewTitle;
        return this;
    }

    @Override
    public PixPicCreator setActionBarTitle(String actionBarTitle) {
        fishton.titleActionBar = actionBarTitle;
        return this;
    }

    @Override
    public PixPicCreator setHomeAsUpIndicatorDrawable(Drawable icon) {
        fishton.drawableHomeAsUpIndicator = icon;
        return this;
    }

    @Override
    public PixPicCreator setOkButtonDrawable(Drawable icon) {
        fishton.drawableOkButton = icon;
        return this;
    }

    @Override
    public PixPicCreator exceptGif(boolean isExcept) {
        fishton.isExceptGif = isExcept;
        return this;
    }

    @Override
    public PixPicCreator setMenuText(String text) {
        fishton.strTextMenu = text;
        return this;
    }

    @Override
    public PixPicCreator setMenuTextColor(int textColor) {
        fishton.colorTextMenu = textColor;
        return this;
    }

    @Override
    public PixPicCreator setIsUseDetailView(boolean isUse) {
        fishton.isUseDetailView = isUse;
        return this;
    }

//    public void startAlbum() {
//        Context context = null;
//        Activity activity = fishBun.activity.get();
//        Fragment fragment = fishBun.fragment.get();
//        if (activity != null)
//            context = activity;
//        else if (fragment != null)
//            context = fragment.getActivity();
//        else
//            try {
//                throw new Exception("Activity or Fragment Null");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        fishton.setDefaultMessage(context);
//        fishton.setMenuTextColor();
//        fishton.setDefaultDimen(context);
//
//        Intent i = new Intent(context, AlbumActivity.class);
//        if (activity != null) activity.startActivityForResult(i, requestCode);
//        else if (fragment != null) fragment.startActivityForResult(i, requestCode);
//
//    }
}