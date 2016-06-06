package app.tutor.com.tutorapps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.adapters.AffairsAdapter;
import app.tutor.com.tutorapps.adapters.NotesAdapter;
import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.constants.AAConstants;
import app.tutor.com.tutorapps.helper.Logger;

/**
 * Created by Saikat's Mac on 10/05/16.
 */

public class StudyMaterialFragments extends Fragment {

    RecyclerView studyMatrials = null;
    boolean isOpeningForNote = false;
    ProgressBar pBar = null;
    final String TAG = "StudyMaterialFragments";
    NotesAdapter noteAdap = null;

    public static StudyMaterialFragments getInstance(boolean isOpeningForNote) {
        StudyMaterialFragments frag_ = new StudyMaterialFragments();
        frag_.isOpeningForNote = isOpeningForNote;
        return frag_;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_studymaterials, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pBar = (ProgressBar) view.findViewById(R.id.spbar);
        studyMatrials = (RecyclerView) view.findViewById(R.id.studyMaterials);
        studyMatrials.setHasFixedSize(true);
        studyMatrials.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isOpeningForNote) {
            getNotes();
        } else {
            getAffairs();
        }

    }


    public void getNotes() {
        pBar.setVisibility(View.VISIBLE);
        Logger.showMessage(TAG, AAConstants.DOMAIN_URL_OTHER + "downloads.php");
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, AAConstants.DOMAIN_URL_OTHER + "downloads.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pBar.setVisibility(View.GONE);
                        try {
                            response = response.substring(5, response.length());
                            Logger.showMessage(TAG, response);
                            noteAdap = new NotesAdapter(getActivity(), new JSONArray(response));
                            studyMatrials.setAdapter(noteAdap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.showMessage(TAG, "Error: " + error.getMessage());
                pBar.setVisibility(View.GONE);
            }
        });
        AAApplication.getInstance().addToRequestQueue(jsonObjReq);
    }


    public void getAffairs() {
        pBar.setVisibility(View.VISIBLE);
        Logger.showMessage(TAG, AAConstants.DOMAIN_URL_OTHER + "currentAffairs.php");
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, AAConstants.DOMAIN_URL_OTHER + "currentAffairs.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pBar.setVisibility(View.GONE);
                        Logger.showMessage(TAG, response.toString());
                        try {
                            studyMatrials.setAdapter(new AffairsAdapter(getActivity(), new JSONArray(response)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.showMessage(TAG, "Error: " + error.getMessage());
                pBar.setVisibility(View.GONE);
            }
        });
        AAApplication.getInstance().addToRequestQueue(jsonObjReq);
    }


    public void refreshMe() {
        if (noteAdap != null) {
            noteAdap.notifyDataSetChanged();
        }
    }

}
