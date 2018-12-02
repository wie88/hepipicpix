package life.hepi.hepipixpic.ui.picker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import life.hepi.hepipixpic.BaseFragment;
import life.hepi.hepipixpic.R;
import life.hepi.hepipixpic.adapter.image.impl.GlideAdapter;
import life.hepi.hepipixpic.adapter.view.PickerGridAdapter;
import life.hepi.hepipixpic.bean.Album;
import life.hepi.hepipixpic.permission.PermissionCheck;
import life.hepi.hepipixpic.util.SquareFrameLayout;

public class ImagePickerFragment extends BaseFragment {

    private static final String TAG = "ImagePickerFragment";

    private RecyclerView recyclerView;
    private Album album;
    private int position;
    private PickerGridAdapter adapter;
    private GridLayoutManager layoutManager;
    private ImagePickerViewModel viewModel;

    public ImagePickerFragment() {
        // Required empty public constructor
    }

    private void initValue() {
        album = new Album(0, getResources().getString(R.string.all_media), null, 0);
        pixton.imageAdapter = new GlideAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container, savedInstanceState);
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_image_picker, container, false);

        initValue();

        viewModel = ViewModelProviders.of(this).get(ImagePickerViewModel.class);

        if (viewModel.checkPermission(getContext())) {
            viewModel.displayImage(album.bucketId, pixton.isExceptGif);
        }

        recyclerView = view.findViewById(R.id.recycler_picker_list);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.status.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                switch (s) {
                    case "FR":
                        initView();
                        setAdapter(viewModel.images);
                        break;
                }
            }
        });
    }

    public void init()
    {
        viewModel.displayImage(album.bucketId, pixton.isExceptGif);
    }

    private void initView() {
        layoutManager = new GridLayoutManager(getContext(), pixton.photoSpanCount, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(Uri[] result) {
        pixton.pickerImages = result;
        if (adapter == null) {
            adapter = new PickerGridAdapter(viewModel.getPathDir(album.bucketId));
            adapter.setActionListener(new PickerGridAdapter.OnPhotoActionListener() {
                @Override
                public void onDeselect() {
                    refreshThumb();
                }
            });
        }
        recyclerView.setAdapter(adapter);
    }

    private void refreshThumb() {
        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        int lastVisible = layoutManager.findLastVisibleItemPosition();
        for (int i = firstVisible; i <= lastVisible; i++) {
            View view = layoutManager.findViewByPosition(i);
            if (view instanceof SquareFrameLayout) {
                SquareFrameLayout item = (SquareFrameLayout) view;
                ImageView imgThumbImage = item.findViewById(R.id.img_thumb_image);
                Uri image = (Uri) item.getTag();
            }
        }
    }

}
