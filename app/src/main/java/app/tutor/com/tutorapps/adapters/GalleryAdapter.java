package app.tutor.com.tutorapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import app.tutor.com.tutorapps.MyGallery;
import app.tutor.com.tutorapps.R;

/**
 * Created by apple on 15/05/16.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    LinkedList<String> imageList;
    Context mContext = null;

    public GalleryAdapter(Context mContext, LinkedList<String> imageList) {
        super();
        this.imageList = imageList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_gallery, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(mContext).load(imageList.get(position)).resize(120, 120).centerCrop().placeholder(R.drawable.place_cam).into(holder.iViewMain);

        holder.mainView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((MyGallery) mContext).moveToPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iViewMain = null;
        View mainView = null;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mainView = itemView;
            iViewMain = (ImageView) itemView.findViewById(R.id.img_gallery);
        }

    }
}