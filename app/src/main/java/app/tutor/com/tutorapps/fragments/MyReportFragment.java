package app.tutor.com.tutorapps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.adapters.MyReportAdapters;
import app.tutor.com.tutorapps.database.AADatabaseManager;
import app.tutor.com.tutorapps.pojo.ReportDataModel;

/**
 * Created by Saikat's Mac on 14/05/16.
 */

public class MyReportFragment extends Fragment {

    RecyclerView graphListing = null;
    int POSITION = 0;
    LinkedList<ReportDataModel> mainListing = null;
    AADatabaseManager aDB = null;
    LinkedList<String> sub_name = null;
    View nothingFound = null;

    public static MyReportFragment getInstance(final int position) {
        MyReportFragment frag_ = new MyReportFragment();
        frag_.POSITION = position;
        return frag_;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (sub_name == null) {
            sub_name = new LinkedList<String>();
            sub_name.add("MockTest");
            sub_name.add("Geography Of India");
            sub_name.add("History Of India");
            sub_name.add("English Composition");
            sub_name.add("The Constitution Of India");
            sub_name.add("Indian National Movements");
            sub_name.add("Current Affairs");
            sub_name.add("Indian Economy");
            sub_name.add("General Intelligence");
            sub_name.add("General Science");
        }


        return inflater.inflate(R.layout.fragment_my_report, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aDB = new AADatabaseManager(getActivity());
        nothingFound = view.findViewById(R.id.not_found);
        graphListing = (RecyclerView) view.findViewById(R.id.my_report);
        graphListing.setHasFixedSize(true);
        graphListing.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainListing = aDB.getAllData(sub_name.get(POSITION));
        if (mainListing.size() > 0) {
            nothingFound.setVisibility(View.GONE);
            graphListing.setAdapter(new MyReportAdapters(getActivity(), mainListing));
        } else {
            nothingFound.setVisibility(View.VISIBLE);
        }

    }
}
