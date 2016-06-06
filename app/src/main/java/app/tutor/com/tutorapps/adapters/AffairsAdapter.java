package app.tutor.com.tutorapps.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import app.tutor.com.tutorapps.CurrentAffairsDetails;
import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.customview.RobotoMediam;

/**
 * Created by apple on 10/05/16.
 */
public class AffairsAdapter extends RecyclerView.Adapter<AffairsAdapter.ViewHolder> {

    JSONArray subName;
    Context mContext = null;

    public AffairsAdapter(Context mContext, JSONArray subName) {
        super();
        this.subName = subName;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_question_set, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.subText.setText(subName.getJSONObject(position).getString("Title"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.mainView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, CurrentAffairsDetails.class);
                try {
                    i.putExtra("A_ID", subName.getJSONObject(position).getString("ID"));
                    i.putExtra("HEADER", subName.getJSONObject(position).getString("Title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subName.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RobotoMediam subText = null;
        View mainView = null;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mainView = itemView;
            subText = (RobotoMediam) itemView.findViewById(R.id.txt2);
        }

    }
}
