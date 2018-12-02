package life.hepi.hepipixpic.adapter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import life.hepi.hepipixpic.BaseActivity;
import life.hepi.hepipixpic.Pixton;
import life.hepi.hepipixpic.R;
import life.hepi.hepipixpic.bean.Album;
import life.hepi.hepipixpic.define.Define;

import java.util.List;

public class AlbumAdapter
        extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>  {

    private Pixton pixton;
    private List<Album> albumList;


    public AlbumAdapter() {
        pixton = Pixton.getInstance();
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_item, parent, false);
        return new ViewHolder(view, pixton.albumThumbnailSize);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imgAlbumThumb.setImageDrawable(null);
        Pixton.getInstance().imageAdapter
                .loadImage(holder.imgAlbumThumb.getContext(),
                        holder.imgAlbumThumb,
                        Uri.parse(albumList.get(position).thumbnailPath));

        holder.view.setTag(albumList.get(position));
        Album a = (Album) holder.view.getTag();
        holder.txtAlbumName.setText(albumList.get(position).bucketName);
        holder.txtAlbumCount.setText(String.valueOf(a.counter));


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Album a = (Album) v.getTag();
                Intent i = new Intent();
                i.putExtra(Define.BUNDLE_NAME.ALBUM.name(), a);
                i.putExtra(Define.BUNDLE_NAME.POSITION.name(), position);
                BaseActivity context = (BaseActivity) holder.view.getContext();
                context.setResult(Activity.RESULT_OK, i);
                context.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


    public List<Album> getAlbumList() {
        return albumList;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView imgAlbumThumb;
        private TextView txtAlbumName;
        private TextView txtAlbumCount;

        public ViewHolder(View view, int albumSize) {
            super(view);
            this.view = view;
            imgAlbumThumb = view.findViewById(R.id.img_album_thumb);
            imgAlbumThumb.setLayoutParams(new LinearLayout.LayoutParams(albumSize, albumSize));

            txtAlbumName = view.findViewById(R.id.txt_album_name);
            txtAlbumCount =  view.findViewById(R.id.txt_album_count);
        }
    }

}
