package app.tutor.com.tutorapps.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;

import java.io.File;

import app.tutor.com.tutorapps.NotesActivity;
import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.customview.RobotoMediam;
import app.tutor.com.tutorapps.helper.Logger;

/**
 * Created by Saikat's Mac on 10/05/16.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    JSONArray subName;
    Context mContext = null;

    public NotesAdapter(Context mContext, JSONArray subName) {
        super();
        this.subName = subName;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_notes_lissting, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.contentText.setText(Html.fromHtml(subName.getJSONObject(position).getString("title")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String temp_ = subName.getJSONObject(position).getString("download_link").trim();
            String te_[] = temp_.split("/");
            File filesDir = new File(mContext.getExternalFilesDir(null).getAbsolutePath().toString() + "/" + te_[te_.length - 1]);
            if (filesDir.exists()) {
                holder.downloaded.setVisibility(View.VISIBLE);
                holder.download.setVisibility(View.GONE);
            } else {
                holder.downloaded.setVisibility(View.GONE);
                holder.download.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.mainView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String temp_ = subName.getJSONObject(position).getString("download_link").trim();
                    String te_[] = temp_.split("/");
                    File internalFile = new File(mContext.getExternalFilesDir(null).getAbsolutePath().toString() + "/" + te_[te_.length - 1]);
                    Logger.showMessage("StudyMaterials", internalFile.getAbsolutePath().toString());
                    if (internalFile.exists()) {
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction(Intent.ACTION_VIEW);
                        String type = "application/*";
                        intent.setDataAndType(Uri.fromFile(internalFile), type);
                        mContext.startActivity(intent);
                    } else {
                        ((NotesActivity) mContext).downloadImage(temp_, te_[te_.length - 1]);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return subName.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RobotoMediam contentText = null;
        ImageView download = null;
        ImageView downloaded = null;
        View mainView = null;

        public ViewHolder(View itemView) {
            super(itemView);
            mainView = itemView;
            contentText = (RobotoMediam) itemView.findViewById(R.id.notice_header);
            download = (ImageView) itemView.findViewById(R.id.rightpnl);
            downloaded = (ImageView) itemView.findViewById(R.id.rightpnl_down);
        }

    }
}
