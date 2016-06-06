package app.tutor.com.tutorapps.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import app.tutor.com.tutorapps.ClassTestManagement;
import app.tutor.com.tutorapps.LandingActivity;
import app.tutor.com.tutorapps.MyReport;
import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.SubjectListing;
import app.tutor.com.tutorapps.adapters.PagerAdapter;
import app.tutor.com.tutorapps.pagertransformation.DepthPageTransformer;

/**
 * Created by apple on 04/05/16.
 */

public class LandingFragments extends Fragment {

    ViewPager landingPager = null;

    LinkedList<String> sub_name = null;
    LinkedList<String> imageID = null;
    View indicator[] = new View[9];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_landingpage, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((LandingActivity) getActivity()).getSupportActionBar().setTitle("Home");

        indicator[0] = view.findViewById(R.id.indic1);
        indicator[1] = view.findViewById(R.id.indic2);
        indicator[2] = view.findViewById(R.id.indic3);
        indicator[3] = view.findViewById(R.id.indic4);
        indicator[4] = view.findViewById(R.id.indic5);
        indicator[5] = view.findViewById(R.id.indic6);
        indicator[6] = view.findViewById(R.id.indic7);
        indicator[7] = view.findViewById(R.id.indic8);
        indicator[8] = view.findViewById(R.id.indic9);

        if (sub_name == null) {
            sub_name = new LinkedList<String>();
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

        if (imageID == null) {
            imageID = new LinkedList<String>();
            imageID.add("https://i.imgsafe.org/9361f0f.jpg");
            imageID.add("https://i.imgsafe.org/9d9cb0d.jpg");
            imageID.add("https://i.imgsafe.org/787d040.png");
            imageID.add("https://i.imgsafe.org/694fd34.jpg");
            imageID.add("https://i.imgsafe.org/a034cd9.jpg");
            imageID.add("https://i.imgsafe.org/a8ceb77.png");
            imageID.add("https://i.imgsafe.org/711c216.jpg");
            imageID.add("https://i.imgsafe.org/7e609c8.jpg");
            imageID.add("https://i.imgsafe.org/ab1ccdf.jpg");

        }


        landingPager = (ViewPager) view.findViewById(R.id.add_pager);
        landingPager.setAdapter(new PagerAdapter(getActivity(), sub_name, imageID));
        landingPager.setPageTransformer(true, new DepthPageTransformer());
        landingPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < 9; i++) {
                    if (position == i) {
                        indicator[i].setBackgroundResource(R.drawable.white_circle);
                    } else {
                        indicator[i].setBackgroundResource(R.drawable.white_circle_alpha);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        view.findViewById(R.id.class_test).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SubjectListing.class);
                i.setAction(SubjectListing.ACTION_EXAM);
                startActivity(i);
            }
        });

        view.findViewById(R.id.mock_test).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ClassTestManagement.class);
                i.setAction(ClassTestManagement.ACTION_MOCK);
                startActivity(i);
            }
        });


        view.findViewById(R.id.study_met).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SubjectListing.class);
                i.setAction(SubjectListing.ACTION_STUDY);
                startActivity(i);
            }
        });


        view.findViewById(R.id.my_report).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyReport.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
