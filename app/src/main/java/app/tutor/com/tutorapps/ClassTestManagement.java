package app.tutor.com.tutorapps;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import app.tutor.com.tutorapps.fragments.ClassTestQunSet;
import app.tutor.com.tutorapps.fragments.MockTestQuestionSet;
import app.tutor.com.tutorapps.fragments.TestInformation;

public class ClassTestManagement extends AppCompatActivity {

    String SUBJECT_ID = "";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public static String ACTION_MOCK = "ACTION_MOCK";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().getAction().equalsIgnoreCase(ACTION_MOCK)) {

            getSupportActionBar().setTitle("Mock Test");

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.class_test_fholder, new MockTestQuestionSet());
            fragmentTransaction.commit();

        } else {
            SUBJECT_ID = getIntent().getStringExtra("SUB_ID");
            getSupportActionBar().setTitle(getIntent().getStringExtra("SUB_NAME"));

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.class_test_fholder, new ClassTestQunSet());
            fragmentTransaction.commit();
        }


    }


    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    public void fireTestInfo(final String setID, final String setName) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (getIntent().getAction().equalsIgnoreCase(ACTION_MOCK)) {
            fragmentTransaction.replace(R.id.class_test_fholder, TestInformation.getInstance(false, setID, setName,SUBJECT_ID));
        } else {
            fragmentTransaction.replace(R.id.class_test_fholder, TestInformation.getInstance(true, setID, setName,SUBJECT_ID));
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
