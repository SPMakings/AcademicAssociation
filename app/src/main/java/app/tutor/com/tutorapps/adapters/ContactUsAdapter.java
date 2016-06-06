package app.tutor.com.tutorapps.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.Locale;

import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.customview.RobotoMediam;
import app.tutor.com.tutorapps.fragments.ContactUs;
import app.tutor.com.tutorapps.pojo.ContactUSDType;

/**
 * Created by Saikat's Mac on 13/05/16.
 */

public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.ViewHolder> {

    LinkedList<ContactUSDType> subName;
    Context mContext = null;
    ContactUs cFrag;

    public ContactUsAdapter(Context mContext, LinkedList<ContactUSDType> subName, ContactUs cFrag) {
        super();
        this.subName = subName;
        this.mContext = mContext;
        this.cFrag = cFrag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_contact_us, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.contentText.setText(subName.get(position).getShopName());
            Picasso.with(mContext).load(subName.get(position).getImageID()).fit().centerCrop().into(holder.map);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.direction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", subName.get(position).getLat(), subName.get(position).getLon(), "Academic Association");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    mContext.startActivity(intent);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });


        holder.callMe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    try {
                        cFrag.callMe("");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return subName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RobotoMediam contentText = null;
        ImageView direction = null, callMe = null, map = null;

        public ViewHolder(View itemView) {
            super(itemView);
            contentText = (RobotoMediam) itemView.findViewById(R.id.b_name);
            direction = (ImageView) itemView.findViewById(R.id.open_map);
            callMe = (ImageView) itemView.findViewById(R.id.call_me);
            map = (ImageView) itemView.findViewById(R.id.map_img);
        }

    }
}
