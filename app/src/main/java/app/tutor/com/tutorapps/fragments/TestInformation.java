package app.tutor.com.tutorapps.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import app.tutor.com.tutorapps.ExamManager;
import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.customview.RobotoBold;
import app.tutor.com.tutorapps.customview.RobotoLight;

/**
 * Created by Saikat's Mac on 08/05/16.
 */

public class TestInformation extends Fragment {

    boolean isClassTest = false;
    String setID = "", setName = "", subjectID = "";

    public static TestInformation getInstance(boolean isClassTest, String setID, String setName, String subjectID) {
        TestInformation frag_ = new TestInformation();
        frag_.isClassTest = isClassTest;
        frag_.setID = setID;
        frag_.setName = setName;
        frag_.subjectID = subjectID;
        return frag_;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_test_info_classtest, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((RobotoLight) view.findViewById(R.id.set_header)).setText(setName);


        if (isClassTest == false) {
            ((RobotoBold) view.findViewById(R.id.qun_no)).setText("200");
            ((RobotoBold) view.findViewById(R.id.total_mrks)).setText("200");
            ((RobotoBold) view.findViewById(R.id.ttl_time)).setText("120");
        }

        view.findViewById(R.id.start_exam).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ExamManager.class);
                i.putExtra("SET_ID", setID);
                i.putExtra("SUBJECT_ID", subjectID);
                if (isClassTest) {
                    i.setAction("classtest");
                } else {
                    i.setAction(ExamManager.ACTION_MOCK);
                }
                startActivity(i);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
