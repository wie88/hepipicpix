package life.hepi.hepipixpic;


import android.net.Uri;

import java.util.ArrayList;

interface BaseProperty {

    PixPicCreator setSelectedImages(ArrayList<Uri> arrayPaths);

    PixPicCreator setPickerCount(int count);

    PixPicCreator setMaxCount(int count);

    PixPicCreator setMinCount(int count);

    PixPicCreator setRequestCode(int RequestCode);

    PixPicCreator setReachLimitAutomaticClose(boolean isAutomaticClose);

    PixPicCreator exceptGif(boolean isExcept);

//    void startAlbum();

}