package life.hepi.hepipixpic.ui.picker;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import life.hepi.hepipixpic.permission.PermissionCheck;
import life.hepi.hepipixpic.util.RegexUtil;

public class ImagePickerViewModel extends AndroidViewModel {

    public Uri[] images;
    public MutableLiveData<String> status = new MutableLiveData<>();
    private ContentResolver resolver;
    private String pathDir = "";

    public ImagePickerViewModel(@NonNull Application application) {
        super(application);

        resolver = application.getContentResolver();
    }

    public boolean checkPermission(Context context) {
        PermissionCheck permissionCheck = new PermissionCheck(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionCheck.CheckStoragePermission())
                return true;
        } else
            return true;
        return false;
    }

    public void displayImage(Long bucketId,
                      Boolean exceptGif) {
        new DisplayImage(bucketId, exceptGif).execute();
    }

    private class DisplayImage extends AsyncTask<Void, Void, Uri[]> {
        private Long bucketId;
        Boolean exceptGif;

        DisplayImage(Long bucketId,
                     Boolean exceptGif) {
            this.bucketId = bucketId;
            this.exceptGif = exceptGif;
        }

        @Override
        protected Uri[] doInBackground(Void... params) {
            return getAllMediaThumbnailsPath(bucketId, exceptGif);
        }

        @Override
        protected void onPostExecute(Uri[] result) {
            super.onPostExecute(result);
            images = result;
            status.postValue("FR");
        }
    }

    @NonNull
    private Uri[] getAllMediaThumbnailsPath(long id,
                                            Boolean exceptGif) {
        String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        String bucketId = String.valueOf(id);
        String sort = MediaStore.Images.Media._ID + " DESC";
        String[] selectionArgs = {bucketId};

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor c;
        if (!bucketId.equals("0")) {
            c = resolver.query(images, null, selection, selectionArgs, sort);
        } else {
            c = resolver.query(images, null, null, null, sort);
        }
        Uri[] imageUris = new Uri[c == null ? 0 : c.getCount()];
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    setPathDir(c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA)),
                            c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                    int position = -1;
                    RegexUtil regexUtil = new RegexUtil();
                    do {
                        if (exceptGif &&
                                regexUtil.checkGif(c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA))))
                            continue;
                        int imgId = c.getInt(c.getColumnIndex(MediaStore.MediaColumns._ID));
                        Uri path = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + imgId);
                        imageUris[++position] = path;
                    } while (c.moveToNext());
                }
                c.close();
            } catch (Exception e) {
                if (!c.isClosed()) c.close();
            }
        }
        return imageUris;
    }

    private String setPathDir(String path, String fileName) {
        return pathDir = path.replace("/" + fileName, "");
    }

    public String getPathDir(Long bucketId) {
        if (pathDir.equals("") || bucketId == 0)
            pathDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM + "/Camera").getAbsolutePath();
        return pathDir;
    }
}
