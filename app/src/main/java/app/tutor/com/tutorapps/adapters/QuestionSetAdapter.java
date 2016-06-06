package app.tutor.com.tutorapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import app.tutor.com.tutorapps.ClassTestManagement;
import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.customview.RobotoMediam;

/**
 * Created by Saikat's Mac on 08/05/16.
 */
public class QuestionSetAdapter extends RecyclerView.Adapter<QuestionSetAdapter.ViewHolder> {

    JSONArray subName;
    Context mContext = null;
    boolean isClassTest = false;

    public QuestionSetAdapter(Context mContext, JSONArray subName, boolean isClassTest) {
        super();
        this.subName = subName;
        this.mContext = mContext;
        this.isClassTest = isClassTest;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_question_set, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.subText.setText("Question Set " + subName.getInt(position));
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.mainView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    AAApplication.getInstance().setSELECTED_TEST_SET("Question Set " + subName.getInt(position));
                    ((ClassTestManagement) mContext).fireTestInfo("" + subName.getInt(position), "Question " + subName.getInt(position));
                    if (isClassTest == false) {
                        AAApplication.getInstance().setSELECTED_TEST("MockTest");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
