package app.tutor.com.tutorapps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.LinkedList;

import app.tutor.com.tutorapps.adapters.SubjectListingAdapter;
import app.tutor.com.tutorapps.helper.Logger;

public class SubjectListing extends AppCompatActivity {


    //============

    public static final String ACTION_EXAM = "ACTION_EXAM";
    public static final String ACTION_STUDY = "ACTION_STUDY";


    //==========
    RecyclerView subListing = null;

    LinkedList<String> sub_name = null;
    LinkedList<String> sub_ID = null;
    LinkedList<String> imageID = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subListing = (RecyclerView) findViewById(R.id.sub_listing);
        subListing.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        subListing.setHasFixedSize(true);

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

        sub_ID = new LinkedList<String>();
        sub_ID.add("17");
        sub_ID.add("21");
        sub_ID.add("23");
        sub_ID.add("24");
        sub_ID.add("28");
        sub_ID.add("29");
        sub_ID.add("30");
        sub_ID.add("31");
        sub_ID.add("32");

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


        Logger.showMessage("ACTION", "Opening for : " + getIntent().getAction());

        if (getIntent().getAction().equals(ACTION_EXAM)) {
            getSupportActionBar().setTitle("Class Test");
            subListing.setAdapter(new SubjectListingAdapter(SubjectListing.this, sub_name, imageID, sub_ID, true));
        } else {
            getSupportActionBar().setTitle("Study Material");
            subListing.setAdapter(new SubjectListingAdapter(SubjectListing.this, sub_name, imageID, sub_ID, false));
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
