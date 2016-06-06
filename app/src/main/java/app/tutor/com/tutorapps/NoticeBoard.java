package app.tutor.com.tutorapps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import app.tutor.com.tutorapps.adapters.NoticeListAdapter;
import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.constants.AAConstants;
import app.tutor.com.tutorapps.helper.Logger;

public class NoticeBoard extends AppCompatActivity {


    RecyclerView noticeBoard;
    ProgressBar pBAR = null;
    final String TAG = "NoticeBoard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notice Board");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pBAR = (ProgressBar) findViewById(R.id.notice_loader);
        noticeBoard = (RecyclerView) findViewById(R.id.notice_board);
        noticeBoard.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        noticeBoard.setHasFixedSize(false);
        getNoticeboard();

    }


    public void getNoticeboard() {
        pBAR.setVisibility(View.VISIBLE);
        Logger.showMessage(TAG, AAConstants.DOMAIN_URL_OTHER + "notice.php");
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, AAConstants.DOMAIN_URL_OTHER + "notice.php",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pBAR.setVisibility(View.GONE);
                        Logger.showMessage(TAG, response.toString());
                        try {
                            noticeBoard.setAdapter(new NoticeListAdapter(NoticeBoard.this, response));
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
