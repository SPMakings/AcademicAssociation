package app.tutor.com.tutorapps;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import app.tutor.com.tutorapps.fragments.MyReportFragment;

public class MyReport extends AppCompatActivity {


    String header[] = new String[10];
    TabLayout tabs = null;
    ViewPager mainPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tabs = (TabLayout) findViewById(R.id.tabs);
        mainPager = (ViewPager) findViewById(R.id.fragment_bucket);


        header[0] = "Mock Test";
        header[1] = "Geography Of India";
        header[2] = "History Of India";
        header[3] = "English Composition";
        header[4] = "The Constitution Of India";
        header[5] = "Indian National Movements";
        header[6] = "Current Affairs";
        header[7] = "Indian Economy";
        header[8] = "General Intelligence";
        header[9] = "General Science";


        mainPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(mainPager);
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
            return MyReportFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return header[position];
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
