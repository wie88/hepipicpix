package life.hepi.hepipixpic.ui.picker;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.configuration.CameraConfiguration;
import io.fotoapparat.configuration.UpdateConfiguration;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.selector.FlashSelectorsKt;
import io.fotoapparat.selector.LensPositionSelectorsKt;
import io.fotoapparat.view.CameraView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import life.hepi.hepipixpic.BaseFragment;
import life.hepi.hepipixpic.R;
import life.hepi.hepipixpic.ui.editor.ImageEditorActivity;

import static io.fotoapparat.selector.AspectRatioSelectorsKt.standardRatio;
import static io.fotoapparat.selector.FlashSelectorsKt.autoFlash;
import static io.fotoapparat.selector.FlashSelectorsKt.autoRedEye;
import static io.fotoapparat.selector.FlashSelectorsKt.off;
import static io.fotoapparat.selector.FlashSelectorsKt.torch;
import static io.fotoapparat.selector.FocusModeSelectorsKt.autoFocus;
import static io.fotoapparat.selector.FocusModeSelectorsKt.continuousFocusPicture;
import static io.fotoapparat.selector.FocusModeSelectorsKt.fixed;
import static io.fotoapparat.selector.PreviewFpsRangeSelectorsKt.highestFps;
import static io.fotoapparat.selector.ResolutionSelectorsKt.highestResolution;
import static io.fotoapparat.selector.SelectorsKt.firstAvailable;
import static io.fotoapparat.selector.SensorSensitivitySelectorsKt.highestSensorSensitivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagePickerCameraFragment extends BaseFragment {

    CameraView cameraView;
    Fotoapparat fotoapparat;

    ImageView flashButton, shutterButton, switchButton;


    CameraConfiguration config;

    private Integer flashStatus = 1;
    private Integer cameraStatus = 1;

    public ImagePickerCameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_picker_camera, container, false);

        cameraView = view.findViewById(R.id.camera_view);
        flashButton = view.findViewById(R.id.flash_button);
        shutterButton = view.findViewById(R.id.shutter_button);
        switchButton = view.findViewById(R.id.switch_button);

        initCamera();
        initButtons();

        return view;
    }

    private void initButtons()
    {
        shutterButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                PhotoResult result = fotoapparat.takePicture();
                result.toBitmap()
                        .whenAvailable(new Function1<BitmapPhoto, Unit>() {
                            @Override
                            public Unit invoke(BitmapPhoto bitmapPhoto) {
                                try {
                                    String id = UUID.randomUUID().toString();
                                    File file = new File(pixton.cameraImageDestination + File.separator + id + ".jpg");
                                    if (!file.exists()) {
                                        file.createNewFile();
                                    }
                                    FileOutputStream out = new FileOutputStream(file);
                                    bitmapPhoto.bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                    out.flush();
                                    out.close();
                                    file.setReadable(Boolean.TRUE);
                                    Intent intent =  new Intent();
                                    intent.setClass(getContext(), ImageEditorActivity.class);
                                    intent.putExtra("IMAGE", new Uri.Builder().path(file.getPath()).build());
                                    getActivity().startActivity(intent);
                                }
                                catch (IOException ex) {

                                }
                                return null;
                            }
                        });
            }

        });
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraStatus = (cameraStatus == 1)?2:1;
                switch (cameraStatus)
                {
                    case 1:
                        fotoapparat.switchTo(LensPositionSelectorsKt.back(), config);
                        break;
                    case 2:
                        fotoapparat.switchTo(LensPositionSelectorsKt.front(), config);
                        break;
                }
            }
        });
        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flashStatus == 3)
                {
                    flashStatus = 1;
                }
                else
                {
                    flashStatus += 1;
                }
                switch (flashStatus) {
                    case 1:
                        Glide
                            .with(getContext())
                            .load(R.drawable.flash_auto)
                            .into(flashButton);
                        fotoapparat.updateConfiguration(UpdateConfiguration.builder().flash(FlashSelectorsKt.autoFlash()).build());
                        break;
                    case 2:
                        Glide
                                .with(getContext())
                                .load(R.drawable.flash_active)
                                .into(flashButton);
                        fotoapparat.updateConfiguration(UpdateConfiguration.builder().flash(FlashSelectorsKt.on()).build());
                        break;
                    case 3:
                        Glide
                                .with(getContext())
                                .load(R.drawable.flash_disable)
                                .into(flashButton);
                        fotoapparat.updateConfiguration(UpdateConfiguration.builder().flash(FlashSelectorsKt.off()).build());
                        break;
                }
            }
        });
    }

    private void initCamera()
    {
        config = CameraConfiguration
            .builder()
            .photoResolution(standardRatio(
                    highestResolution()
            ))
            .focusMode(firstAvailable(
                    continuousFocusPicture(),
                    autoFocus(),
                    fixed()
            ))
            .flash(firstAvailable(
                    autoRedEye(),
                    autoFlash(),
                    torch(),
                    off()
            ))
            .previewFpsRange(highestFps())
            .sensorSensitivity(highestSensorSensitivity())
            .build();

        fotoapparat = Fotoapparat
                .with(getContext())
                .into(cameraView)
                .previewScaleType(ScaleType.CenterCrop)
                .lensPosition(LensPositionSelectorsKt.back())
                .build();
    }



    @Override
    public void onStart() {
        super.onStart();
        fotoapparat.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        fotoapparat.start();
    }

    @Override
    public void onStop() {
        fotoapparat.stop();
        super.onStop();
    }

}
