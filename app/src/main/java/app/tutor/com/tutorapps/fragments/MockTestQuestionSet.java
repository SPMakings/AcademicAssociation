package app.tutor.com.tutorapps.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.adapters.QuestionSetAdapter;
import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.constants.AAConstants;
import app.tutor.com.tutorapps.helper.Logger;

/**
 * Created by apple on 08/05/16.
 */
public class MockTestQuestionSet extends Fragment {

    RecyclerView questionSet = null;
    public final String TAG = "ClassTestQunSet";
    ProgressBar pBAR = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calsstest_set, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pBAR = (ProgressBar) view.findViewById(R.id.progressBar2);
        questionSet = (RecyclerView) view.findViewById(R.id.class_test_set_list);
        questionSet.setHasFixedSize(true);
        questionSet.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getQuestionSet();
    }


    public void getQuestionSet() {
        pBAR.setVisibility(View.VISIBLE);
        Logger.showMessage(TAG, AAConstants.DOMAIN_URL_OTHER + "allmockTest.php");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, AAConstants.DOMAIN_URL_OTHER_NEW + "get_question_set?subject_id=&mode=M&page=1&limit=200",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pBAR.setVisibility(View.GONE);
                        Logger.showMessage(TAG, response.toString());
                        try {
                            if (response.getString("status").equalsIgnoreCase("fail")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Question set for this subject will available soon.");
                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        getActivity().onBackPressed();
                                    }
                                });
                                builder.setCancelable(false);
                                builder.show();
                            } else {
                                JSONArray innerTemp = response.getJSONArray("set_no");
                                if (response.getInt("last_set_count") < 25) {
                                    innerTemp.remove(0);
                                }
                                questionSet.setAdapter(new QuestionSetAdapter(getActivity(), innerTemp, false));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.showMessage(TAG, "Error: " + error.getMessage());
                pBAR.setVisibility(View.GONE);
            }
        });
        AAApplication.getInstance().addToRequestQueue(jsonObjReq);
    }
}
