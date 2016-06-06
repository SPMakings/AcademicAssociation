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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import app.tutor.com.tutorapps.adapters.AffairsDetailsAdapter;
import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.constants.AAConstants;
import app.tutor.com.tutorapps.helper.Logger;

public class CurrentAffairsDetails extends AppCompatActivity {

    RecyclerView affairsListing = null;
    ProgressBar pBar = null;
    final String TAG = "CurrentAffairsDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_affairs_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("HEADER"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pBar = (ProgressBar) findViewById(R.id.prog_affairs);
        affairsListing = (RecyclerView) findViewById(R.id.affeirs_details);
        affairsListing.setHasFixedSize(false);
        affairsListing.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getAffairsDetails();

    }


    public void getAffairsDetails() {
        pBar.setVisibility(View.VISIBLE);
        Logger.showMessage(TAG, AAConstants.DOMAIN_URL_OTHER + "currentAffairsDetails.php?caID=" + getIntent().getStringExtra("A_ID"));
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, AAConstants.DOMAIN_URL_OTHER + "currentAffairsDetails.php?caID=" + getIntent().getStringExtra("A_ID"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pBar.setVisibility(View.GONE);
                        Logger.showMessage(TAG, response.toString());
                        try {
                            affairsListing.setAdapter(new AffairsDetailsAdapter(CurrentAffairsDetails.this, new JSONArray(response)));
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
