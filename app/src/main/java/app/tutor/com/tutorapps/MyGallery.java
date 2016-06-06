package app.tutor.com.tutorapps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.LinkedList;

import app.tutor.com.tutorapps.adapters.GalleryAdapter;
import app.tutor.com.tutorapps.customview.ExtendedViewPager;
import app.tutor.com.tutorapps.fragments.GalleryPagerFragment;

public class MyGallery extends AppCompatActivity {

    ExtendedViewPager exPager = null;
    RecyclerView galleryView = null;
    LinkedList<String> imageID = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageID=new LinkedList<String>();
        imageID.add("https://i.imgsafe.org/ac730e8.jpg");
        imageID.add("https://i.imgsafe.org/d206ee7.jpg");
        imageID.add("https://i.imgsafe.org/d69c6b0.jpg");
        imageID.add("https://i.imgsafe.org/dc99239.jpg");
        imageID.add("https://i.imgsafe.org/e21b4ab.jpg");
        imageID.add("https://i.imgsafe.org/e9c4519.jpg");
        imageID.add("https://i.imgsafe.org/ee29801.jpg");
        imageID.add("https://i.imgsafe.org/f2bdc59.jpg");
        imageID.add("https://i.imgsafe.org/f80de5f.jpg");
        imageID.add("https://i.imgsafe.org/fc96293.jpg");
        imageID.add("https://i.imgsafe.org/021aee0.jpg");
        imageID.add("https://i.imgsafe.org/06a1e5f.jpg");
        imageID.add("https://i.imgsafe.org/0d1ca5f.jpg");
        imageID.add("https://i.imgsafe.org/10aec9b.jpg");
        imageID.add("https://i.imgsafe.org/16310ab.jpg");
        imageID.add("https://i.imgsafe.org/19d7d96.jpg");


        exPager = (ExtendedViewPager) findViewById(R.id.main_g_pager);
        galleryView = (RecyclerView) findViewById(R.id.image_gallery);
        galleryView.setHasFixedSize(true);
        LinearLayoutManager lmanager = new LinearLayoutManager(getApplicationContext());
        lmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        galleryView.setLayoutManager(lmanager);
        galleryView.setAdapter(new GalleryAdapter(MyGallery.this, imageID));
        exPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

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
            return GalleryPagerFragment.getInstance(imageID.get(position));
        }

        @Override
        public int getCount() {
            return imageID.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }


    public void moveToPosition(final int position) {
        exPager.setCurrentItem(position, true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


}
