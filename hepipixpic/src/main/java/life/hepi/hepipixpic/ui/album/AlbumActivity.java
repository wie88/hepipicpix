package life.hepi.hepipixpic.ui.album;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import life.hepi.hepipixpic.BaseActivity;
import life.hepi.hepipixpic.R;
import life.hepi.hepipixpic.adapter.view.AlbumAdapter;
import life.hepi.hepipixpic.bean.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends BaseActivity {

    private AlbumViewModel viewModel;
    private ArrayList<Album> albumList = new ArrayList<>();

    private RecyclerView recyclerAlbumList;
    private RelativeLayout relAlbumEmpty;

    private AlbumAdapter adapter;
    private TextView progressAlbumText;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (adapter != null) {
            outState.putParcelableArrayList(define.SAVE_INSTANCE_ALBUM_LIST, (ArrayList<? extends Parcelable>) adapter.getAlbumList());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(outState);
        // Restore state members from saved instance
        List<Album> albumList = outState.getParcelableArrayList(define.SAVE_INSTANCE_ALBUM_LIST);
        List<Uri> thumbList = outState.getParcelableArrayList(define.SAVE_INSTANCE_ALBUM_THUMB_LIST);

        if (albumList != null && thumbList != null && pixton.selectedImages != null) {
            adapter = new AlbumAdapter();
            adapter.setAlbumList(albumList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        viewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);

        initView();
        if (viewModel.checkPermission(this))
            viewModel.getAlbumList(pixton.titleAlbumAllView, pixton.isExceptGif);

        viewModel.status.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                switch (s)
                {
                    case "FR":
                        setAlbumList(viewModel.albumList);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerAlbumList != null &&
                recyclerAlbumList.getLayoutManager() != null) {
            if (uiUtil.isLandscape(this))
                ((GridLayoutManager) recyclerAlbumList.getLayoutManager())
                        .setSpanCount(pixton.albumLandscapeSpanCount);
            else
                ((GridLayoutManager) recyclerAlbumList.getLayoutManager())
                        .setSpanCount(pixton.albumPortraitSpanCount);
        }
    }

    private void initView() {
        LinearLayout linearAlbumCamera = findViewById(R.id.lin_album_camera);
        initToolBar();
    }

    private void initToolBar() {
        relAlbumEmpty = findViewById(R.id.rel_album_empty);
        progressAlbumText = findViewById(R.id.txt_album_msg);
        progressAlbumText.setText(R.string.msg_loading_image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            uiUtil.setStatusBarColor(this, pixton.colorStatusBar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(pixton.titleActionBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (pixton.drawableHomeAsUpIndicator != null)
                getSupportActionBar().setHomeAsUpIndicator(pixton.drawableHomeAsUpIndicator);
        }
    }


    private void initRecyclerView() {
        recyclerAlbumList = findViewById(R.id.recycler_album_list);

        GridLayoutManager layoutManager;
        if (uiUtil.isLandscape(this))
            layoutManager = new GridLayoutManager(this, pixton.albumLandscapeSpanCount);
        else
            layoutManager = new GridLayoutManager(this, pixton.albumPortraitSpanCount);

        if (recyclerAlbumList != null) {
            recyclerAlbumList.setLayoutManager(layoutManager);
        }
    }

    private void setAlbumListAdapter() {
        if (adapter == null) {
            adapter = new AlbumAdapter();
        }
        adapter.setAlbumList(albumList);
        recyclerAlbumList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    protected void setAlbumList(ArrayList<Album> albumList) {
        this.albumList = albumList;
        if (albumList.size() > 0) {
            relAlbumEmpty.setVisibility(View.GONE);
            initRecyclerView();
            setAlbumListAdapter();
        } else {
            relAlbumEmpty.setVisibility(View.VISIBLE);
            progressAlbumText.setText(R.string.msg_no_image);
        }
    }
}
