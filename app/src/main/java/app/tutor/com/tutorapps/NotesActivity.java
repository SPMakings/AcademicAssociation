package app.tutor.com.tutorapps;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import app.tutor.com.tutorapps.adapters.NotesAdapter;
import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.constants.AAConstants;
import app.tutor.com.tutorapps.helper.Logger;

public class NotesActivity extends AppCompatActivity {


    private RecyclerView notesListing = null;
    private ProgressBar pBar = null;
    private final String TAG = "NotesActivity";
    private String notesID = "";


    private long enqueue;
    private DownloadManager dm;

    BroadcastReceiver receiver = null;

    ProgressDialog pDialog = null;
    NotesAdapter mainAdap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("SUB_NAME"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //===================


        notesID = getIntent().getStringExtra("SUB_ID");

        pBar = (ProgressBar) findViewById(R.id.notes_pbar);
        notesListing = (RecyclerView) findViewById(R.id.notes_listing);
        notesListing.setHasFixedSize(true);
        notesListing.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        if (pDialog == null) {
            pDialog = new ProgressDialog(NotesActivity.this);
            pDialog.setCanceledOnTouchOutside(false);
        }


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Logger.showMessage(TAG, action);
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    android.app.DownloadManager.Query query = new android.app.DownloadManager.Query();
                    query.setFilterById(enqueue);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                            if (pDialog != null) {
                                pDialog.dismiss();
                            }
                            mainAdap.notifyDataSetChanged();
                        } else if (DownloadManager.STATUS_FAILED == c.getInt(columnIndex)) {
                            Toast.makeText(NotesActivity.this, "Failed to download content..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };


        getNotes();

    }


    public void getNotes() {
        pBar.setVisibility(View.VISIBLE);
        Logger.showMessage(TAG, AAConstants.DOMAIN_URL_OTHER + "downloads.php?subject_id=" + notesID);
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, AAConstants.DOMAIN_URL_OTHER + "downloads.php?subject_id=" + notesID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pBar.setVisibility(View.GONE);
                        try {
                            Logger.showMessage(TAG, response);
                            JSONArray jArray = new JSONArray(response);
                            if (jArray.length() > 0) {
                                mainAdap = new NotesAdapter(NotesActivity.this, new JSONArray(response));
                                notesListing.setAdapter(mainAdap);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NotesActivity.this);
                                builder.setMessage("Notes of this subject will available soon.");
                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        onBackPressed();
                                    }
                                });
                                builder.setCancelable(false);
                                builder.show();
                            }

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


    //=============note download manager

    public void downloadImage(final String imgPath, final String imgName) {

        pDialog.setMessage("Downloading...");
        pDialog.show();
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(
                Uri.parse(imgPath));
        request.setTitle("Academic Association");
        request.setDescription("Notes : " + imgName);
        request.setVisibleInDownloadsUi(false);
        request.setDestinationInExternalFilesDir(this, "", imgName);
        enqueue = dm.enqueue(request);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
