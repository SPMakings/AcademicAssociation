package app.tutor.com.tutorapps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.customview.RobotoLight;
import app.tutor.com.tutorapps.customview.RobotoMediam;
import app.tutor.com.tutorapps.fragments.AboutUs;
import app.tutor.com.tutorapps.fragments.ContactUs;
import app.tutor.com.tutorapps.fragments.LandingFragments;
import app.tutor.com.tutorapps.pojo.UserDetails;


public class LandingActivity extends AppCompatActivity {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    //========Drawer Mnagement
    RobotoMediam profileSignature, profileNmae;
    RobotoLight profileEMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        profileEMail = (RobotoLight) findViewById(R.id.prof_email);
        profileNmae = (RobotoMediam) findViewById(R.id.prof_name);
        profileSignature = (RobotoMediam) findViewById(R.id.profile_signature);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        findViewById(R.id.home_me).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                fireHome();
            }
        });

        findViewById(R.id.about_us).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                fireAboutUs();
            }
        });

        findViewById(R.id.contact_us).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                fireContactUs();
            }
        });


        findViewById(R.id.invite_others).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                inviteOthers();
            }
        });


        findViewById(R.id.rate_us).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=app.tutor.com.tutorappsnew"));
                startActivity(intent);
            }
        });


        findViewById(R.id.notice_board).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(LandingActivity.this, NoticeBoard.class);
                startActivity(i);
            }
        });

        findViewById(R.id.gallery).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(LandingActivity.this, MyGallery.class);
                startActivity(i);
            }
        });


        findViewById(R.id.log_out).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LandingActivity.this);
                alertDialogBuilder
                        .setMessage("Do you want log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AAApplication.getInstance().clearUserData();
                                Intent i = new Intent(LandingActivity.this, SplashActivity.class);
                                startActivity(i);
                                LandingActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        fireHome();


        //===============Drawar management
        UserDetails uDetaild = AAApplication.getInstance().getUserData();
        profileEMail.setText(uDetaild.getUserEmail());
        profileNmae.setText(uDetaild.getUserName());
        String temp[] = uDetaild.getUserName().split(" ");
        if (temp.length >= 2) {
            profileSignature.setText("" + temp[0].charAt(0) + temp[1].charAt(0));
        } else {
            profileSignature.setText("" + temp[0]);
        }

    }


    public void fireAboutUs() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.landing_bucket, new AboutUs());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void fireContactUs() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.landing_bucket, new ContactUs());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void fireHome() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.landing_bucket, new LandingFragments());
        fragmentTransaction.commit();
    }

    public void inviteOthers() {
        Intent s = new Intent(Intent.ACTION_SEND);
        s.setType("text/plain");
        s.putExtra(Intent.EXTRA_TEXT, "Hay, I am using this App for making myself exam ready. Come on install this from https://play.google.com/store/apps/details?id=app.tutor.com.tutorappsnew and move one step ahead for exam.");
        s.putExtra(Intent.EXTRA_SUBJECT, "I am inviting you!");
        startActivity(Intent.createChooser(s, "Academic Association"));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            try {
                super.onBackPressed();
            } catch (Exception e) {
                getSupportFragmentManager().getFragments().clear();
                fireHome();
            }
        }
    }


}
