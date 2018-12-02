package life.hepi.hepipixpic.ui.editor;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropFragment;
import com.yalantis.ucrop.UCropFragmentCallback;

import java.io.File;

import life.hepi.hepipixpic.BaseActivity;
import life.hepi.hepipixpic.PixPicCreator;
import life.hepi.hepipixpic.R;

public class ImageEditorActivity extends BaseActivity implements UCropFragmentCallback {

    UCropFragment ucrop;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editor);

        dialog  = new ProgressDialog(this);

        Uri imageUri;

        if(getIntent().getStringExtra("TYPE").equals("CAMERA")) {
            Uri temp = (Uri) getIntent().getParcelableExtra("IMAGE");
            imageUri = Uri.fromFile(new File(temp.getPath()));
        }
        else {
            imageUri = (Uri) getIntent().getParcelableExtra("IMAGE");
        }

        UCrop.Options options = new UCrop.Options();
        options.setSaturationEnabled(Boolean.FALSE);
        options.setSharpnessEnabled(Boolean.FALSE);

        Uri destinationUri = new Uri.Builder().path(pixton.editImageDestination +  File.separator + imageUri.getPathSegments().get(imageUri.getPathSegments().size()-1) + ".jpg").build();
        ucrop = UCrop.of(imageUri, destinationUri)
                .withAspectRatio(1,1)
                .withMaxResultSize(1920,1920)
                .withOptions(options)
                .getFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, ucrop).commit();

        getSupportActionBar().setHomeButtonEnabled(Boolean.TRUE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(Boolean.TRUE);
        getSupportActionBar().setTitle(R.string.edit_feature);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.multi_image_picker_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if(item.getItemId() == R.id.action_next) {
            dialog.show();
            ucrop.cropAndSaveImage();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadingProgress(boolean b) {

    }

    @Override
    public void onCropFinish(UCropFragment.UCropResult uCropResult) {
        setResult(RESULT_OK, uCropResult.mResultData);
        dialog.hide();
        finish();
    }
}
