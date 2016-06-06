package app.tutor.com.tutorapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.customview.RobotoBold;
import app.tutor.com.tutorapps.customview.RobotoLight;
import app.tutor.com.tutorapps.customview.RobotoMediam;

/**
 * Created by Saikat's Mac on 10/05/16.
 */

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> {

    JSONArray subName;
    Context mContext = null;

    public NoticeListAdapter(Context mContext, JSONArray subName) {
        super();
        this.subName = subName;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_notice_board, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.subText.setText(subName.getJSONObject(position).getString("title"));
            holder.contentText.setText(Html.fromHtml(subName.getJSONObject(position).getString("description")));
            String temp_[] = subName.getJSONObject(position).getString("date").split(" ");
            holder.dateText.setText(temp_[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return subName.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RobotoBold subText = null;
        RobotoMediam contentText = null;
        RobotoLight dateText = null;

        public ViewHolder(View itemView) {
            super(itemView);
            subText = (RobotoBold) itemView.findViewById(R.id.notice_header);
            contentText = (RobotoMediam) itemView.findViewById(R.id.content);
            dateText = (RobotoLight) itemView.findViewById(R.id.date);
        }

    }
}
