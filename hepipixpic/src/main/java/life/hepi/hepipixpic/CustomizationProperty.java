package life.hepi.hepipixpic;

import android.graphics.drawable.Drawable;

interface CustomizationProperty {

    PixPicCreator setAlbumThumbnailSize(int size);

    PixPicCreator setPickerSpanCount(int spanCount);

    PixPicCreator setActionBarColor(int actionbarColor);

    PixPicCreator setActionBarTitleColor(int actionbarTitleColor);

    PixPicCreator setActionBarColor(int actionbarColor, int statusBarColor);

    PixPicCreator setActionBarColor(int actionbarColor, int statusBarColor, boolean isStatusBarLight);

    PixPicCreator setCamera(boolean isCamera);

    PixPicCreator textOnNothingSelected(String message);

    PixPicCreator textOnImagesSelectionLimitReached(String message);

    PixPicCreator setButtonInAlbumActivity(boolean isButton);

    PixPicCreator setAlbumSpanCount(int portraitSpanCount, int landscapeSpanCount);

    PixPicCreator setAlbumSpanCountOnlyLandscape(int landscapeSpanCount);

    PixPicCreator setAlbumSpanCountOnlPortrait(int portraitSpanCount);

    PixPicCreator setAllViewTitle(String allViewTitle);

    PixPicCreator setActionBarTitle(String actionBarTitle);

    PixPicCreator setHomeAsUpIndicatorDrawable(Drawable icon);

    PixPicCreator setOkButtonDrawable(Drawable icon);

    PixPicCreator setMenuText(String text);

    PixPicCreator setMenuTextColor(int color);

    PixPicCreator setIsUseDetailView(boolean isUse);
}
