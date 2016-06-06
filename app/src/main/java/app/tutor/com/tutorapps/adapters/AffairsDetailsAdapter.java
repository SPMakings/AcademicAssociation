package app.tutor.com.tutorapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.customview.RobotoMediam;

/**
 * Created by apple on 11/05/16.
 */
public class AffairsDetailsAdapter extends RecyclerView.Adapter<AffairsDetailsAdapter.ViewHolder> {

    JSONArray subName;
    Context mContext = null;

    public AffairsDetailsAdapter(Context mContext, JSONArray subName) {
        super();
        this.subName = subName;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_affairs_details, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.subText.setText(subName.getJSONObject(position).getString("Detials"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return subName.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RobotoMediam subText = null;

        public ViewHolder(View itemView) {
            super(itemView);
            subText = (RobotoMediam) itemView.findViewById(R.id.content);
        }

    }
}

