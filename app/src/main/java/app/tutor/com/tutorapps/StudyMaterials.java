package app.tutor.com.tutorapps;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import app.tutor.com.tutorapps.fragments.StudyMaterialFragments;
import app.tutor.com.tutorapps.helper.Logger;

public class StudyMaterials extends AppCompatActivity {

    ViewPager studyPager = null;
    TabLayout tabLayout;
    final String TAG = "StudyMaterials";


    private long enqueue;
    private long enqueue2;
    private long enqueue3;
    private DownloadManager dm;

    BroadcastReceiver receiver = null;

    ProgressDialog pDialog = null;
    StudyMaterialFragments noteFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_materials);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Study Material");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studyPager = (ViewPager) findViewById(R.id.study_pager);
        studyPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(studyPager);


        if (pDialog == null) {
            pDialog = new ProgressDialog(StudyMaterials.this);
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
                            noteFragment.refreshMe();
                        } else if (DownloadManager.STATUS_FAILED == c.getInt(columnIndex)) {
                            Toast.makeText(StudyMaterials.this, "Failed to download content..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };


    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            if (fm.getFragments() != null) {
                fm.getFragments().clear();
            }
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return (StudyMaterialFragments.getInstance(false));
                case 1:
                    noteFragment = (StudyMaterialFragments.getInstance(true));
                    return noteFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Current Affairs";
            } else {
                return "Notes";
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
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
        unregisterReceiver(receiver);
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
}
