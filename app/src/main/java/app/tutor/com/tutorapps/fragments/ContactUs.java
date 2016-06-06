package app.tutor.com.tutorapps.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Locale;

import app.tutor.com.tutorapps.LandingActivity;
import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.adapters.ContactUsAdapter;
import app.tutor.com.tutorapps.pojo.ContactUSDType;

/**
 * Created by apple on 05/05/16.
 */
public class ContactUs extends Fragment {


    LinkedList<ContactUSDType> contactUsData = null;
    RecyclerView shopListing;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    /*
        > head office- 22.574400, 88.362931
    */

    public void makeReady() {
        ContactUSDType te_ = new ContactUSDType();
        te_.setLat(27.007534f);
        te_.setLon(88.260491f);
        te_.setPhoneNo("");
        te_.setShopName("Darjeeling");
        te_.setImageID(R.drawable.darjeeling);
        contactUsData.add(te_);

        te_ = new ContactUSDType();
        te_.setLat(22.419954f);
        te_.setLon(87.302116f);
        te_.setPhoneNo("");
        te_.setShopName("Midnapore");
        te_.setImageID(R.drawable.midnapore);
        contactUsData.add(te_);


        te_ = new ContactUSDType();
        te_.setLat(24.083843f);
        te_.setLon(88.248579f);
        te_.setPhoneNo("");
        te_.setShopName("Berhampur");
        te_.setImageID(R.drawable.berhampur);
        contactUsData.add(te_);

        te_ = new ContactUSDType();
        te_.setLat(22.716139f);
        te_.setLon(88.482933f);
        te_.setPhoneNo("");
        te_.setShopName("Barasat");
        te_.setImageID(R.drawable.barasat);
        contactUsData.add(te_);

        te_ = new ContactUSDType();
        te_.setLat(22.468798f);
        te_.setLon(88.094915f);
        te_.setPhoneNo("");
        te_.setShopName("Uluberia");
        te_.setImageID(R.drawable.uluberia);
        contactUsData.add(te_);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_contactus, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((LandingActivity) getActivity()).getSupportActionBar().setTitle("Contact Us");

        contactUsData = new LinkedList<ContactUSDType>();
        makeReady();
        shopListing = (RecyclerView) view.findViewById(R.id.shop_listing);
        shopListing.setHasFixedSize(true);
        shopListing.setLayoutManager(new LinearLayoutManager(getActivity()));
        shopListing.setAdapter(new ContactUsAdapter(getActivity(), contactUsData, this));


        view.findViewById(R.id.mail_to).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("plain/text");
                intent.setData(Uri.parse("mailto:academic2003@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback/Query");
                intent.putExtra(Intent.EXTRA_TEXT, String.format("Hello"));
                startActivity(intent);
            }
        });


        view.findViewById(R.id.open_web).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.academicassociation.in/"));
                startActivity(intent);
            }
        });


        view.findViewById(R.id.open_maphead).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", 22.574400f, 88.362931f, "Academic Association");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });


        view.findViewById(R.id.open_phok_head).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                callMe("");

            }
        });


    }

    public void callMe(final String telNO) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
            return;
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:9674478644"));
            getActivity().startActivity(callIntent);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:9674478644"));
                        startActivity(callIntent);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Unable to call this Number", Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//                    alertDialogBuilder.setMessage("With out these permissions, this app will not run. Would you like to continue?");
//                    alertDialogBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            permissionChk();
//                        }
//                    });
//                    alertDialogBuilder.setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    });
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();
                }
                return;
            }
        }
    }
}