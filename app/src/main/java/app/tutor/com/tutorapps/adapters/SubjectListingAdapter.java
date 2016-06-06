package app.tutor.com.tutorapps.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import app.tutor.com.tutorapps.ClassTestManagement;
import app.tutor.com.tutorapps.CurrentAffairsActivity;
import app.tutor.com.tutorapps.NotesActivity;
import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.customview.RobotoBold;

/**
 * Created by Saikat's Mac on 05/05/16.
 */

public class SubjectListingAdapter extends RecyclerView.Adapter<SubjectListingAdapter.ViewHolder> {

    LinkedList<String> subName;
    LinkedList<String> subID;
    LinkedList<String> imageList;
    Context mContext = null;
    boolean isOpeningForExam = false;

    public SubjectListingAdapter(Context mContext, LinkedList<String> subName, LinkedList<String> imageList, LinkedList<String> subID, final boolean isOpeningForExam) {
        super();
        this.subName = subName;
        this.subID = subID;
        this.imageList = imageList;
        this.mContext = mContext;
        this.isOpeningForExam = isOpeningForExam;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_subject_listing, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(mContext).load(imageList.get(position)).fit().centerCrop().placeholder(R.drawable.place_cam).into(holder.iViewMain);
        holder.subText.setText(subName.get(position));

        holder.mainView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isOpeningForExam) {
                    Intent i = new Intent(mContext, ClassTestManagement.class);
                    i.setAction("classTest");
                    i.putExtra("SUB_ID", subID.get(position));
                    i.putExtra("SUB_NAME", subName.get(position));
                    AAApplication.getInstance().setSELECTED_TEST(subName.get(position));
                    mContext.startActivity(i);
                } else {
                    if (subID.get(position).equalsIgnoreCase("29")) {
                        Intent i = new Intent(mContext, CurrentAffairsActivity.class);
                        i.putExtra("SUB_NAME", subName.get(position));
                        mContext.startActivity(i);
                    } else {
                        Intent i = new Intent(mContext, NotesActivity.class);
                        i.putExtra("SUB_ID", subID.get(position));
                        i.putExtra("SUB_NAME", subName.get(position));
                        mContext.startActivity(i);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return subName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iViewMain = null;
        RobotoBold subText = null;
        View mainView = null;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mainView = itemView;
            iViewMain = (ImageView) itemView.findViewById(R.id.sub_img);
            subText = (RobotoBold) itemView.findViewById(R.id.subject_name);
        }

    }
}
