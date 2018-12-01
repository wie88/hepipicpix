package life.hepi.hepipixpic.ui.picker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.yalantis.ucrop.UCrop;

import life.hepi.hepipixpic.BaseActivity;
import life.hepi.hepipixpic.R;
import life.hepi.hepipixpic.adapter.view.ViewPagerAdapter;

public class ImagePickerActivity extends BaseActivity implements View.OnClickListener {

    ImagePickerFragment imagePickerFragment;
    ImagePickerCameraFragment imagePickerCameraFragment;

    public static final int IMAGE_EDITOR_ACTIVITY = 1;

    ViewPager viewPager;

    TextView gallery, camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        viewPager = findViewById(R.id.view_pager);

        gallery = findViewById(R.id.gallery);
        camera = findViewById(R.id.camera);

        initViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(pixton.maxCount > 1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.multi_image_picker_menu, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void initViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        imagePickerFragment = new ImagePickerFragment();
        imagePickerCameraFragment = new ImagePickerCameraFragment();
        adapter.addFragment(imagePickerFragment, "Gallery");
        adapter.addFragment(imagePickerCameraFragment,"Camera");
        viewPager.setAdapter(adapter);

        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);

        enableState(gallery);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        disableState(camera);
                        enableState(gallery);
                        break;
                    case 1:
                        disableState(gallery);
                        enableState(camera);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==  R.id.gallery) {
            viewPager.setCurrentItem(0);
            disableState(camera);
            enableState(gallery);
        }
        else
        {
            viewPager.setCurrentItem(1);
            disableState(gallery);
            enableState(camera);
        }
    }

    private void enableState(TextView textview) {
        textview.setTypeface(null, Typeface.BOLD);
        textview.setTextColor(getResources().getColor(R.color.textDarkGrey));
    }

    private void disableState(TextView textview) {
        textview.setTypeface(null, Typeface.NORMAL);
        textview.setTextColor(getResources().getColor(R.color.textGrey));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_EDITOR_ACTIVITY :
                Intent intent = new Intent();
                intent.putExtra("IMAGE_URL", UCrop.getOutput(data));
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
